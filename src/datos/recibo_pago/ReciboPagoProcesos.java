package datos.recibo_pago;

import java.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import app.beans.ItemTipo;
import app.beans.Utiles;

import datos.Page;
import datos.SessionFactory;

import datos.contrato.Contrato;


import datos.contrato_novedad_factura.ContratoNovedadFactura;
import datos.contrato_novedad_factura.ContratoNovedadFacturaDAO;
import datos.contrato_novedad_factura.ContratoNovedadFacturaProcesos;
import datos.contrato_novedad_pago.ContratoNovedadPago;

import datos.contrato_novedad_pago.ContratoNovedadPagoProcesos;

import datos.recibo_pago_item.ReciboPagoItem;
import datos.recibo_pago_item.ReciboPagoItemDAO;
import datos.recibo_pago_item.ReciboPagoItemProcesos;
import framework.ui.generales.exception.ReglasDeNegocioException;

public class ReciboPagoProcesos {
	
	private static final Log log = LogFactory.getLog(ReciboPagoProcesos.class);

	public static void save(ReciboPago oReciboPago, List<ReciboPagoItem> lDatos) throws Exception {

		// Manejo de la transacción global
		
		Session oSessionH = SessionFactory.currentSession();
		Transaction tx = null;
		
		/*
		 * A este proceso le tengo que agregar que grave en FACTURA NOVEDAD ... Creo que solo agrego el IVA
		 * tengo q recorrer los items, buscar un tipo especial (las comisiones de alquiler) y grabar la novedad 14/01/2013
		 * cuando grabo también agrego el IVA a estos movimientos, agrego 1 solo movimiento con la sumatoria del IVA
		 */
		
		try {
			
			tx = oSessionH.beginTransaction();
			
			/** 1º Grabo la cabecera del recibo */
			
			// Tengo que buscar el número que le corresponde al comprobante ¿?
			// Lo tengo que confirmar cuando este se haya impreso NO ????
			// oReciboCobro.setNumero(numero)
			Set<ReciboPagoItem> sItems = new HashSet<ReciboPagoItem>();
			for (ReciboPagoItem obj: lDatos) {
				sItems.add(obj);
				//System.out.println("CHEQUEO " + obj.getIdNovedad());
			}
			
			oReciboPago.setReciboPagoItems(sItems);
			
			//ReciboPagoDAO.save(oReciboPago);
			ReciboPagoDAO oDAO = new ReciboPagoDAO();
			oDAO.save(oReciboPago);
			
			@SuppressWarnings("unused")
			float sumatoria_IVA = 0;
			
			/** 2º Grabo los items */
			for (ReciboPagoItem obj: lDatos) {
				
				// valido el saldo de cada item incluido en el recibo ¿? 12/01/2012
				
				// Este saldo esta desactualizado
				// double saldo = obj.getContratoNovedadCobro().getSaldo();
				
				/** Cuando el item lo agrego directamente en pantalla no tiene asignado un CONTRATO. No tiene saldo 
				 *  Cuando el item es de IVA apunta al IdContratoNovedadPago de la COMISION DE ALQUILER (quien la generó) --> NO MIRO EL SALDO ¿?
				 * */				
								
				double monto = obj.getMonto();
				double saldo = monto;
				
				if (obj.getIdItemTipo() != ItemTipo.ItemIVA) {
					try {
					
						ContratoNovedadPago oNovedadActualizada = ContratoNovedadPagoProcesos.buscarPorId(obj.getContratoNovedadPago().getIdContratoNovedadPago());
						saldo = oNovedadActualizada.getSaldo();
						
					} catch(NullPointerException npe) {}
					
					
					//System.out.println("CONTROL DATOS " + saldo + " - " + monto);
					// El monto puede ser NEGATIVO entonces lo comparo en VALOR ABSOLUTO
					
					if ( Math.abs(monto) > Math.abs(saldo) ) {
						// errooooorrrr !!!!!!!!!!
						oSessionH.getTransaction().rollback();
						throw new Exception("El monto supera el saldo del item.");
					}
				} // Fin IF 
				
				obj.setReciboPago(oReciboPago);
				ReciboPagoItemDAO.save(obj);
				
				/** 3º Grabo la novedad de factura (si corresponde) 
				 *  Los MONTOS son negativos por que restan en el RECIBO, le invierto el signo para la FACTURA 
				 */
				
				// Ahora puedo cargar un Item de Comisión MANUAL !!!!
				// Tendría que generar los nuevos comprobantes y no modificar este código - 20/10/2014
				// Los items manuales no los paso a FACTURA ??? no debería ....
				if (obj.getIdItemTipo() == ItemTipo.ItemComisionAlquiler) {
					
					/** 
					 * Esta reventando con una NULLPOINTEREXCEPTION !!!
					 * Necesito el contrato para poder atar el movimiento en la NOVEDAD DE LA FACTURA
					 * pgarello 14/07/2015
					 * 
					 * Como estoy cargando manualmente un movimiento de liquidación de IVA ¿?¿?¿? ESTA BIEN QUE HAGA ESTO ¿?¿?¿?
					 */										
					
					if (obj.getContratoNovedadPago().getContrato() != null) {
					
						ContratoNovedadFactura oNovedadFactura = new ContratoNovedadFactura();
		   					   				
	   					oNovedadFactura.setContrato(obj.getContratoNovedadPago().getContrato());
	   					oNovedadFactura.setContratoCuota(obj.getContratoNovedadPago().getContratoCuota());
	   					oNovedadFactura.setFechaVencimiento(obj.getContratoNovedadPago().getFechaLiquidacion());
	   					oNovedadFactura.setPeriodoAnio(obj.getContratoNovedadPago().getPeriodoAnio());
		   				oNovedadFactura.setPeriodoMes(obj.getContratoNovedadPago().getPeriodoMes());
		   				oNovedadFactura.setPersona(oReciboPago.getPersona()); // Propietario
		   				oNovedadFactura.setMonto(monto * -1);	   				
		   				oNovedadFactura.setIdNovedadTipo(ItemTipo.ItemComisionAlquiler);
		   				   				
		   				ContratoNovedadFacturaDAO.save(oNovedadFactura);
					}
				}
	   			
				if (obj.getIdItemTipo() == ItemTipo.ItemIVA) {
					
					if (obj.getContratoNovedadPago().getContrato() != null) {
						
						ContratoNovedadFactura oNovedadFactura2 = new ContratoNovedadFactura();
	   					   				
	   					oNovedadFactura2.setContrato(obj.getContratoNovedadPago().getContrato());
		   				oNovedadFactura2.setContratoCuota(obj.getContratoNovedadPago().getContratoCuota());
		   				oNovedadFactura2.setFechaVencimiento(obj.getContratoNovedadPago().getFechaLiquidacion());
		   				oNovedadFactura2.setPeriodoAnio(obj.getContratoNovedadPago().getPeriodoAnio());
		   				oNovedadFactura2.setPeriodoMes(obj.getContratoNovedadPago().getPeriodoMes());
		   				oNovedadFactura2.setPersona(oReciboPago.getPersona()); // Propietario	   				
		   				oNovedadFactura2.setMonto(monto * -1);
		   				oNovedadFactura2.setIdNovedadTipo(ItemTipo.ItemIVA);
	   				   				
		   				ContratoNovedadFacturaDAO.save(oNovedadFactura2);	   				
					}
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
	
	
	/**
	 * Devuelve una página de objetos ReciboPago
	 * @param page_number
	 * @param page_size
	 * @param filtro_inmueble
	 * @param filtro_propietario
	 * @param filtro_nro_recibo
	 * @param filtro_fecha_desde
	 * @param filtro_fecha_hasta
	 * @return
	 */
	public static Page findByFilter(int page_number, 
									int page_size,
									int filtro_inmueble,
									int filtro_propietario,
									int filtro_nro_recibo,
									Date filtro_fecha_desde,
									Date filtro_fecha_hasta) {

		Session oSessionH = SessionFactory.currentSession();

		Page pagina;
		try {
			String queryString = 	"SELECT DISTINCT model " +
									//"FROM ReciboPago model, ReciboPagoItem rci, Persona per ";
									
									"FROM ReciboPago model " +
									"	LEFT JOIN model.reciboPagoItems rci " + //ON (model = rci.reciboPago) " +
									"	LEFT JOIN model.persona per "; // ON (model.Persona = per) ";
			
			
			if (filtro_inmueble != 0) 
				//queryString += ", ContratoNovedadPago cnp, Contrato con, Inmueble inm "; //, ContratoActor ca ";
				
				queryString += 	"	LEFT JOIN rci.contratoNovedadPago cnp " + //ON (cnp.contrato = con) " +
								"	LEFT JOIN cnp.contrato con " + //ON (con.inmueble = inm) " +
								"	LEFT JOIN con.inmueble inm "; //ON (rci.contratoNovedadPago = cnp) "; //, ContratoActor ca ";
				
//									"FROM ReciboPago model " +
//									"	LEFT JOIN model.reciboPagoItems rci " +
//									"		LEFT JOIN rci.contratoNovedadPago cnp " +
//									"LEFT JOIN Contrato con " +
//									"LEFT JOIN Inmueble inm " +
//									"LEFT JOIN ContratoActor ca " +
//									"LEFT JOIN Persona per " +
									
			queryString += 	"WHERE model.idReciboPago IS NOT NULL " +
							"AND model = rci.reciboPago " +
							"AND model.persona = per ";
//							"AND inm.propietario = per ";
										
			if (filtro_inmueble != 0)
				queryString += 	"AND cnp.contrato = con " +
								"AND con.inmueble = inm " +
								"AND rci.contratoNovedadPago = cnp ";
								//"AND con = ca.id.contrato AND ca.id.idActorTipo = " + ContratoActor.ActorTipoInquilino + " " +
								

			if (filtro_inmueble != 0) queryString += " AND inm.idInmueble = " + filtro_inmueble;
			//if (filtro_vigente) queryString += " AND now() BETWEEN model.fechaDesde AND model.fechaHasta ";
			if (filtro_propietario != 0) queryString += " AND per.idPersona = " + filtro_propietario;
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
	 * Devuelve una página de objetos ReciboPago
	 * @param page_number
	 * @param page_size
	 * @param filtro_ganancia_tipo Se obtiene del combo de GANANCIA, son los tipos de inscripciones de los propietarios ante dicho impuesto
	 * @param filtro_propietario
	 * @param filtro_nro_recibo
	 * @param filtro_fecha_desde
	 * @param filtro_fecha_hasta
	 * @return
	 */
	public static Page findGananciaByFilter(int page_number, 
											int page_size,
											int filtro_ganancia_tipo,
											int filtro_propietario,
											int filtro_nro_recibo,
											Date filtro_fecha_desde,
											Date filtro_fecha_hasta) {

		Session oSessionH = SessionFactory.currentSession();

		Page pagina;
		try {
			
			// pensar que 
			
			String queryString = 	"SELECT rci " +
									"FROM ReciboPago model, ReciboPagoItem rci, Persona per " +
									/*, ContratoNovedadPago cnp, Contrato con, Inmueble inm, ContratoActor ca*/
									"WHERE model.idReciboPago IS NOT NULL " +
									"AND model = rci.reciboPago AND rci.idItemTipo = " + ItemTipo.ItemRetencionGanancia + " " +
 									//"AND rci.contratoNovedadPago = cnp " +
									//"AND cnp.contrato = con " +
									//"AND con.inmueble = inm " +
									//"AND con = ca.id.contrato AND ca.id.idActorTipo = " + ContratoActor.ActorTipoPropietario + " " +								
									//"AND ca.id.persona = per "; //"AND inm.propietario = per "; /** Lo tomo del CONTRATO no del INMUEBLE */
									"AND model.persona = per ";

			if (filtro_ganancia_tipo != 0) queryString += " AND per.idInscripcionGanancias = " + filtro_ganancia_tipo;
			if (filtro_propietario != 0) queryString += " AND per.idPersona = " + filtro_propietario;
			//if (filtro_nro_recibo != 0) queryString += " AND model.numero = " + filtro_nro_recibo;
			if (filtro_fecha_desde != null) queryString += " AND model.fechaEmision >= :fechaDesde ";
			if (filtro_fecha_hasta != null) queryString += " AND model.fechaEmision <= :fechaHasta ";

			queryString+= " ORDER BY model.numero ";
			
			System.out.println("Query " + queryString);
			Query oQuery = oSessionH.createQuery(queryString);
			if (filtro_fecha_desde != null) oQuery.setDate("fechaDesde", filtro_fecha_desde);
			if (filtro_fecha_hasta != null) oQuery.setDate("fechaHasta", filtro_fecha_hasta);
			
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
	public static List<ReciboPago> completar(List<ReciboPago> lista_a_completar) throws Exception {
		
		List<ReciboPago> lista_completa = new Vector<ReciboPago>();
		
		Session oSessionH = SessionFactory.currentSession();
		
		try {
			
			for(ReciboPago oReciboPago: lista_a_completar) {
				
				oSessionH.refresh(oReciboPago);				
				
				oReciboPago.getPersona().getDescripcion();
				
				double suma_montos = 0;
				Set<ReciboPagoItem> items = oReciboPago.getReciboPagoItems();
				for(ReciboPagoItem oItem: items) {
					suma_montos += oItem.getMonto();					
				}
				
				oReciboPago.setTotal(suma_montos);
				
				lista_completa.add(oReciboPago);
				
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
	
	
	public static List<ReciboPagoItem> completarItems(List<ReciboPagoItem> lista_a_completar) throws Exception {
		
		List<ReciboPagoItem> lista_completa = new Vector<ReciboPagoItem>();
		
		Session oSessionH = SessionFactory.currentSession();
		
		try {
			
			for(ReciboPagoItem obj: lista_a_completar) {
				
				oSessionH.refresh(obj);				
				
				obj.getReciboPago().getPersona().getDescripcion();
				
				lista_completa.add(obj);
				
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
	
	
	public static ReciboPago buscarReciboPorID(int id_recibo_cobro) throws Exception {
		
		System.out.println("ReciboPagoProcesos.buscarReciboPorID " + id_recibo_cobro);
		
		// 1º Abro una sesion de datos
		// Session oSessionH = SessionFactory.currentSession();
		
		ReciboPagoDAO oDAO = new ReciboPagoDAO();
		ReciboPago oRecibo = oDAO.findById(new Integer(id_recibo_cobro));
	
    	// El recibo se busca con todos los datos completos ...
    	try {
			oRecibo = ReciboPagoProcesos.completarConSaldo(oRecibo);
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
	 * @param oReciboPago
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static ReciboPago completarConSaldo(ReciboPago oReciboPago) throws Exception {
		
		// Busco el cobro de un recibo de Liquidación Cobranza
		// ReciboCobroItemDAO oDAO = new ReciboCobroItemDAO();
		// List<ReciboCobroItem> cItems = oDAO.findByProperty("reciboCobro", oRecibo);
		
		System.out.println("CONTROL ContratoNovedadPago: ");
		
		Session oSessionH = SessionFactory.currentSession();
		double suma_montos = 0;
		try {
			oSessionH.refresh(oReciboPago);
//			Hibernate.initialize(oReciboCobro);
		
			oReciboPago.getPersona().getDescripcion();
					
			Set<ReciboPagoItem> items = oReciboPago.getReciboPagoItems();
			for(ReciboPagoItem oItem: items) {
				suma_montos += oItem.getMonto();
				
				// Ejecuto las relaciones lazy !!!
//				oItem.getContratoNovedadCobro().getContrato().getInmueble().getDireccion_completa();
//				oItem.getContratoNovedadCobro().getPeriodoCuota();
//				oItem.getContratoNovedadCobro().getSaldo();
				
				ReciboPagoItemProcesos.completarDatos(oItem);
				
			}
			
			oReciboPago.setTotal(suma_montos);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			throw e;
			
		} finally { 
			// dio error de que la session ya estaba cerrada
			// try{oSessionH.close();} catch(Exception e) {}
		}
		
		System.out.println("Total recibo: " + suma_montos);
		
		return oReciboPago;
		
	}
	
	
	/**
	 * Borra un RECIBO DE PAGO
	 * Debe borrar en las siguientes tablas:
	 * 		1º ReciboPago
	 * 		2º ReciboPagoItem
	 * 		3º ContratoNovedadFactura
	 * 
	 * @param oReciboPago
	 * @throws ReglasDeNegocioException
	 */
	public static void delete(ReciboPago oReciboPago) throws ReglasDeNegocioException {
		
		/* 
		 * Reglas de Negocio 
		 * 1º Que la fecha de emisión sea de hoy
		 */
		Calendar cFechaEmision = Calendar.getInstance();
		cFechaEmision.setTime(oReciboPago.getFechaEmision());
		if (Utiles.diasQueFaltan(cFechaEmision) != 0 ) {
			System.out.println("NO!!! SE PUEDE BORRAR EL RECIBO DE PAGO");
			throw new ReglasDeNegocioException("No se puede borrar el RECIBO DE PAGO \n por que la fecha de emisión no es del día de hoy.");
		}
		
		/*
		 * 2º Tengo que validar que no se hayan facturado las novedades generadas por el recibo
		 */
		
		
		// ACA BORRO
		// Manejo de la transacción global
		Session oSessionH = SessionFactory.currentSession();
		Transaction tx = null;
		
		try {
		
			tx = oSessionH.beginTransaction();		
			
			// puedo borrar			
			oReciboPago = completarConSaldo(oReciboPago);
			System.out.println("SE PUEDE BORRAR EL RECIBO DE PAGO " + oReciboPago.getReciboPagoItems().size());
			
			/** 1º Borro los ITEMS - 
			 * 		Debo previamente borrar las novedades de factura generadas  
			 */
			for (ReciboPagoItem oItems : oReciboPago.getReciboPagoItems()) {
				
				//ContratoNovedadPago oContratoNovedadPago = ContratoNovedadPagoProcesos.buscarPorId(oItems.getContratoNovedadPago().getIdContratoNovedadPago());								
				if (oItems.getContratoNovedadPago() != null) {
					
					oSessionH.refresh(oItems.getContratoNovedadPago());
					
					// Ojo que si el item no está asociado a un CONTRATO (por ejemplo un cobro de pintura) da error !!! 15/01/2015				
					Hibernate.initialize(oItems.getContratoNovedadPago().getContrato());
					
					Contrato oContrato = oItems.getContratoNovedadPago().getContrato();
					Short mes = oItems.getContratoNovedadPago().getPeriodoMes();
					Short anio = oItems.getContratoNovedadPago().getPeriodoAnio();
					
					List<ContratoNovedadFactura> lNovedades = ContratoNovedadFacturaProcesos.buscarNovedadesPorContrato(oContrato);
					System.out.println("Novedades " + lNovedades.size());
					for (ContratoNovedadFactura oNovedad : lNovedades) {
						if (	oNovedad.getIdNovedadTipo() == ItemTipo.ItemComisionAlquiler || 
								oNovedad.getIdNovedadTipo() == ItemTipo.ItemIVA) {
							
							// avaluo el mes y año
							if (oNovedad.getPeriodoAnio() == anio && oNovedad.getPeriodoMes() == mes) {
								
								// borro la NOVEDAD
								ContratoNovedadFacturaProcesos.delete(oNovedad);
							}
							
						}
					}
				}
				
				// Si pasa borro el ITEM
				ReciboPagoItemDAO.delete(oItems);		
			}
						
			/** 3º Borro el RECIBO PAGO */
			ReciboPagoDAO.delete(oReciboPago);
			
			tx.commit();
			
		} catch (Exception e) {
			
			e.printStackTrace();
			try{tx.rollback();} catch(Exception ex) {}
			throw new ReglasDeNegocioException(e);
			
		} finally { 
			// dio error de que la session ya estaba cerrada
			try{oSessionH.close();} catch(Exception e) {}
		}
		
		
	}
	
	
} //  Fin clase