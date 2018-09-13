package datos.recibo_cobro;

import java.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import app.beans.ItemTipo;
import app.beans.NovedadTipo;

import datos.Page;
import datos.SessionFactory;

import datos.contrato_actor.ContratoActor;
import datos.contrato_novedad_cobro.ContratoNovedadCobro;
import datos.contrato_novedad_cobro.ContratoNovedadCobroProcesos;
import datos.contrato_novedad_factura.ContratoNovedadFactura;
import datos.contrato_novedad_factura.ContratoNovedadFacturaDAO;
import datos.recibo_cobro_item.ReciboCobroItem;
import datos.recibo_cobro_item.ReciboCobroItemDAO;
import datos.recibo_cobro_item.ReciboCobroItemProcesos;

public class ReciboCobroProcesos {
	
	private static final Log log = LogFactory.getLog(ReciboCobroProcesos.class);

	public static void save(ReciboCobro oReciboCobro, List<ReciboCobroItem> lDatos) throws Exception {

		// Manejo de la transacción global
		Session oSessionH = SessionFactory.currentSession();
		Transaction tx = oSessionH.beginTransaction();
		
		/*
		 * A este proceso le tengo que agregar que grave en FACTURA NOVEDAD ... Creo que solo agrego el IVA
		 * tengo q recorrer los items, buscar un tipo especial (las comisiones de CONTRATO) y grabar la novedad 14/01/2013
		 * cuando grabo también agrego el IVA a estos movimientos, agrego 1 solo movimiento con la sumatoria del IVA ¿?
		 */
		
		try {
						
			/** 1º Grabo la cabecera del recibo */
			
			// Tengo que buscar el número que le corresponde al comprobante ¿?
			// Lo tengo que confirmar cuando este se haya impreso NO ????
			// oReciboCobro.setNumero(numero)
			Set<ReciboCobroItem> sItems = new HashSet<ReciboCobroItem>();
			for (ReciboCobroItem obj: lDatos) {
				sItems.add(obj);
			}
			
			oReciboCobro.setReciboCobroItems(sItems);
			ReciboCobroDAO.save(oReciboCobro);
			
			
			/** 2º Grabo los items */
			for (ReciboCobroItem obj: lDatos) {
				
				// valido el saldo de cada item incluido en el recibo ¿? 12/01/2012
				
				// Este saldo esta desactualizado
				// double saldo = obj.getContratoNovedadCobro().getSaldo();
				
				/** Cuando el item lo agrego directamente en pantalla no tiene asignado un CONTRATO. No tiene saldo 
				 *  Cuando el ITEM es de IVA (se aplica sobre una COMISION DE CONTRATO) no tiene SALDO
				 */				
				double monto = obj.getMonto();
				double saldo = monto;
				
				if (obj.getIdItemTipo() != ItemTipo.ItemIVA) { 
				
					try {
						ContratoNovedadCobro oNovedadActualizada = ContratoNovedadCobroProcesos.buscarPorId(obj.getContratoNovedadCobro().getIdContratoNovedadCobro());
					
						saldo = oNovedadActualizada.getSaldo();
						
					} catch(NullPointerException npe) {}
					
				}
				
				//System.out.println("CONTROL DATOS " + saldo + " - " + monto);
				
				if (monto > saldo) {
					// errooooorrrr !!!!!!!!!!
					tx.rollback();
					throw new Exception("El monto supera el saldo del item.");
				}
				
				obj.setReciboCobro(oReciboCobro);				
				ReciboCobroItemDAO.save(obj);
			
				
				/** 3º Grabo la novedad de factura (si corresponde) */
				
				if (obj.getIdItemTipo() == NovedadTipo.ComisionContrato) {
					
					ContratoNovedadFactura oNovedadFactura = new ContratoNovedadFactura();
	   				
	   				oNovedadFactura.setContrato(obj.getContratoNovedadCobro().getContrato());
	   				oNovedadFactura.setPersona(oReciboCobro.getPersona()); // INQUILINO
	   				
	   				oNovedadFactura.setContratoCuota(obj.getContratoNovedadCobro().getContratoCuota());
	   				oNovedadFactura.setFechaVencimiento(obj.getContratoNovedadCobro().getContrato().getFechaDesde());
	   				oNovedadFactura.setMonto(monto);
	   				oNovedadFactura.setPeriodoAnio(obj.getContratoNovedadCobro().getPeriodoAnio());
	   				oNovedadFactura.setPeriodoMes(obj.getContratoNovedadCobro().getPeriodoMes());
	   				oNovedadFactura.setIdNovedadTipo(obj.getIdItemTipo());
	   				   				
	   				ContratoNovedadFacturaDAO.save(oNovedadFactura);
				}
				
				if (obj.getIdItemTipo() == NovedadTipo.ComisionRescision) {
					
					ContratoNovedadFactura oNovedadFactura1 = new ContratoNovedadFactura();
	   				
	   				oNovedadFactura1.setContrato(obj.getContratoNovedadCobro().getContrato());
	   				oNovedadFactura1.setPersona(oReciboCobro.getPersona()); // INQUILINO
	   				
	   				oNovedadFactura1.setContratoCuota(obj.getContratoNovedadCobro().getContratoCuota());
	   				oNovedadFactura1.setFechaVencimiento(obj.getContratoNovedadCobro().getContrato().getFechaDesde());
	   				oNovedadFactura1.setMonto(monto);
	   				oNovedadFactura1.setPeriodoAnio(obj.getContratoNovedadCobro().getPeriodoAnio());
	   				oNovedadFactura1.setPeriodoMes(obj.getContratoNovedadCobro().getPeriodoMes());
	   				//oNovedadFactura1.setIdNovedadTipo(ItemTipo.ItemComisionRescision);
	   				oNovedadFactura1.setIdNovedadTipo(obj.getIdItemTipo());
	   				   				
	   				ContratoNovedadFacturaDAO.save(oNovedadFactura1);
				}
	   			
				if (obj.getIdItemTipo() == NovedadTipo.IVA) {
					
	   				ContratoNovedadFactura oNovedadFactura2 = new ContratoNovedadFactura();
	   				
	   				oNovedadFactura2.setContrato(obj.getContratoNovedadCobro().getContrato());
	   				oNovedadFactura2.setPersona(oReciboCobro.getPersona()); // INQUILINO
	   				
	   				oNovedadFactura2.setContratoCuota(obj.getContratoNovedadCobro().getContratoCuota());
	   				oNovedadFactura2.setFechaVencimiento(obj.getContratoNovedadCobro().getFechaVencimiento());
	   				oNovedadFactura2.setMonto(monto);
	   				oNovedadFactura2.setPeriodoAnio(obj.getContratoNovedadCobro().getPeriodoAnio());
	   				oNovedadFactura2.setPeriodoMes(obj.getContratoNovedadCobro().getPeriodoMes());
	   				oNovedadFactura2.setIdNovedadTipo(obj.getIdItemTipo());
	   				   				
	   				ContratoNovedadFacturaDAO.save(oNovedadFactura2);
					
				}
								
			}
						
			// Fin grabación --------------------------------
			
			tx.commit();
			log.debug("Recibo Cobro grabado en forma correcta");
			
		} catch (Exception e) {
			
			e.printStackTrace();
			tx.rollback();
			throw e;
			
		} finally { 
			// dio error de que la session ya estaba cerrada
			try{oSessionH.close();} catch(Exception e) {}
		}
		
	}
	
	
	public static Page findByFilter(int page_number, 
									int page_size,
									int filtro_inmueble,
									int filtro_inquilino,
									int filtro_nro_recibo,
									Date filtro_fecha_desde,
									Date filtro_fecha_hasta) {

		Session oSessionH = SessionFactory.currentSession();

		Page pagina;
		try {
			String queryString = 	"SELECT DISTINCT model " +
									"FROM ReciboCobro model, ReciboCobroItem rci, ContratoNovedadCobro cnc, Contrato con, Inmueble inm, " +
									"ContratoActor ca, Persona per " +
									"WHERE model.idReciboCobro IS NOT NULL " +
									"AND model = rci.reciboCobro " +
									"AND rci.contratoNovedadCobro = cnc " +
									"AND cnc.contrato = con " +
									"AND con.inmueble = inm " +
									"AND con = ca.id.contrato AND ca.id.idActorTipo = " + ContratoActor.ActorTipoInquilino + " " +
									"AND ca.id.persona = per ";

			if (filtro_inmueble != 0) queryString += " AND inm.idInmueble = " + filtro_inmueble;
			//if (filtro_vigente) queryString += " AND now() BETWEEN model.fechaDesde AND model.fechaHasta ";
			if (filtro_inquilino != 0) queryString += " AND per.idPersona = " + filtro_inquilino;
			if (filtro_nro_recibo != 0) queryString += " AND model.numero = " + filtro_nro_recibo;
			if (filtro_fecha_desde != null) queryString += " AND model.fechaEmision >= :fechaDesde ";
			if (filtro_fecha_hasta != null) queryString += " AND model.fechaEmision <= :fechaHasta ";

			queryString+= " ORDER BY model.numero ";

//			filtro_calle += "%";
			
			//System.out.println("Query " + queryString);
			Query oQuery = oSessionH.createQuery(queryString);
			if (filtro_fecha_desde != null) oQuery.setDate("fechaDesde", filtro_fecha_desde);
			if (filtro_fecha_hasta != null) oQuery.setDate("fechaHasta", filtro_fecha_hasta);
//			oQuery.setParameter("calleValue", "%" + filtro_calle.toUpperCase());
//			oQuery.setParameter("edificioValue", "%" + filtro_edificio.toUpperCase());
			
			pagina = new Page(oQuery, page_number, page_size);

		} catch (RuntimeException re) {
			throw re;
		} finally {
			oSessionH.close();
		}		

		return pagina;
	} 
	
	
	/**
	 * Se usa para completar los datos en el LISTADO 
	 * @param lista_a_completar
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static List<ReciboCobro> completar(List<ReciboCobro> lista_a_completar) throws Exception {
		
		List<ReciboCobro> lista_completa = new Vector<ReciboCobro>();
		
		Session oSessionH = SessionFactory.currentSession();
		
		try {
			
			for(ReciboCobro oReciboCobro: lista_a_completar) {
				
				oSessionH.refresh(oReciboCobro);				
				
				oReciboCobro.getPersona().getDescripcion();
				
				double suma_montos = 0;
				Set<ReciboCobroItem> items = oReciboCobro.getReciboCobroItems();
				for(ReciboCobroItem oItem: items) {
					suma_montos += oItem.getMonto();					
				}
				
				oReciboCobro.setTotal(suma_montos);
				
				lista_completa.add(oReciboCobro);
				
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
			throw e;
			
		} finally { 
			// dio error de que la session ya estaba cerrada
			try{oSessionH.close();} catch(Exception e) {}
		}
		
		return lista_completa;
	}
	
	
	public static ReciboCobro buscarReciboPorID(int id_recibo_cobro) throws Exception {
		
		System.out.println("ReciboCobroProcesos.buscarReciboPorID " + id_recibo_cobro);
		
		// 1º Abro una sesion de datos
		// Session oSessionH = SessionFactory.currentSession();
		
		ReciboCobroDAO oDAO = new ReciboCobroDAO();
    	ReciboCobro oRecibo = oDAO.findById(new Integer(id_recibo_cobro));
	
    	// El recibo se busca con todos los datos completos ...
    	try {
			oRecibo = ReciboCobroProcesos.completarConSaldo(oRecibo);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    	
		// Dejo cerrada la sesion de datos
		// try{oSessionH.close();} catch(Exception e) {}
		
    	return oRecibo;
    	
	}
	
	
	/**
	 * Se usa para visualizar un recibo 
	 * @param oReciboCobro
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static ReciboCobro completarConSaldo(ReciboCobro oReciboCobro) throws Exception {
		
		// Busco el cobro de un recibo de Liquidación Cobranza
		// ReciboCobroItemDAO oDAO = new ReciboCobroItemDAO();
		// List<ReciboCobroItem> cItems = oDAO.findByProperty("reciboCobro", oRecibo);
		
		//System.out.println("CONTROL ContratoNovedadCobro: " + cItems.size());
		
		Session oSessionH = SessionFactory.currentSession();
		double suma_montos = 0;
		try {
			oSessionH.refresh(oReciboCobro);
//			Hibernate.initialize(oReciboCobro);
		
			oReciboCobro.getPersona().getDescripcion();
					
			Set<ReciboCobroItem> items = oReciboCobro.getReciboCobroItems();
			for(ReciboCobroItem oItem: items) {
				suma_montos += oItem.getMonto();
				
				// Ejecuto las relaciones lazy !!!
//				oItem.getContratoNovedadCobro().getContrato().getInmueble().getDireccion_completa();
//				oItem.getContratoNovedadCobro().getPeriodoCuota();
//				oItem.getContratoNovedadCobro().getSaldo();
				
				ReciboCobroItemProcesos.completarDatos(oItem);
				
			}
			
			oReciboCobro.setTotal(suma_montos);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			throw e;
			
		} finally { 
			// dio error de que la session ya estaba cerrada
			// try{oSessionH.close();} catch(Exception e) {}
		}
		
		System.out.println("Total recibo: " + suma_montos);
		
		return oReciboCobro;
		
	}
	
} //  Fin clase