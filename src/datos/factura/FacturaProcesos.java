package datos.factura;

import java.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;


import datos.Page;
import datos.SessionFactory;

import datos.factura_item.FacturaItem;
import datos.factura_item.FacturaItemDAO;
import datos.recibo_pago.ReciboPago;
import datos.recibo_pago.ReciboPagoDAO;
import datos.recibo_pago.ReciboPagoProcesos;


public class FacturaProcesos {
	
	private static final Log log = LogFactory.getLog(FacturaProcesos.class);

	public static void save(Factura oFactura, List<FacturaItem> lDatos) throws Exception {

		// Manejo de la transacción global
		Session oSessionH = SessionFactory.currentSession();
		Transaction tx = oSessionH.beginTransaction();
		
		/*
		 *
		 */
		
		try {
						
			/** 1º Grabo la cabecera del recibo */
			
			// Tengo que buscar el número que le corresponde al comprobante ¿?
			// Lo tengo que confirmar cuando este se haya impreso NO ????
			// oReciboCobro.setNumero(numero)
			Set<FacturaItem> sItems = new HashSet<FacturaItem>();
			for (FacturaItem obj: lDatos) {
				sItems.add(obj);
			}
			
			oFactura.setFacturaItems(sItems);			
			FacturaDAO oDAO = new FacturaDAO();			
			oDAO.save(oFactura);
			
			System.out.println("Factura.anulada " + oFactura.getAnulada());
			
			/** 2º Grabo los items */
			for (FacturaItem obj: lDatos) {
				
				/**  
				 *  FAS FACTURAS NO TIENEN SALDO YA QUE NO SE PUEDEN EMITIR UN ITEM EN FORMA PARCIAL, CON LO CUAL
				 *  SIEMPRE SE FACTURA EL TOTAL Y EL SALDO SIEMPRE ES CEROOOO
				 *  
				 *  VALIDAR el IVA !!!
				 *  
				 */				
				
				obj.setFactura(oFactura);				
				
				FacturaItemDAO oDAOfi = new FacturaItemDAO();
				oDAOfi.save(obj);								
				
			}
						
			// Fin grabación --------------------------------
			
			tx.commit();
			log.debug("FACTURA grabado en forma correcta");
			
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
									int filtro_persona,
									int filtro_nro_factura,
									Date filtro_fecha_emision) {

		Session oSessionH = SessionFactory.currentSession();

		Page pagina;
		try {
			String queryString = 	"SELECT DISTINCT model " +
									"FROM Factura model, FacturaItem fi " +
									"WHERE model.idFactura IS NOT NULL " +
									"AND model = fi.factura ";

//			if (filtro_inmueble != 0) queryString += " AND inm.idInmueble = " + filtro_inmueble;
			//if (filtro_vigente) queryString += " AND now() BETWEEN model.fechaDesde AND model.fechaHasta ";
			if (filtro_persona != 0) queryString += " AND model.persona.idPersona = " + filtro_persona;
			if (filtro_nro_factura != 0) queryString += " AND model.numero = " + filtro_nro_factura;
			if (filtro_fecha_emision != null) queryString += " AND model.fechaEmision >= :fechaEmision ";

			queryString+= " ORDER BY model.numero ";

//			filtro_calle += "%";
			
			//System.out.println("Query " + queryString);
			Query oQuery = oSessionH.createQuery(queryString);
			if (filtro_fecha_emision != null) oQuery.setDate("fechaEmision", filtro_fecha_emision);
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
	 * Se usa para completar los datos en el LISTADO - Calcula el total de la Factura
	 * @param lista_a_completar
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static List<Factura> completar(List<Factura> lista_a_completar) throws Exception {
		
		List<Factura> lista_completa = new Vector<Factura>();
		
		Session oSessionH = SessionFactory.currentSession();
		
		try {
			
			for(Factura oFactura: lista_a_completar) {
				
				oSessionH.refresh(oFactura);
				
				oFactura.getPersona().getDescripcion();
								
				double suma_montos = 0;
				Set<FacturaItem> items = oFactura.getFacturaItems();
				for(FacturaItem oItem: items) {
					suma_montos += oItem.getMonto();
					
					// Si es manual no tiene contrato asociado !!!!!!!!!!!!!!!
					try {
						oItem.getContratoNovedadFactura().getContrato().getInmueble().getDireccion_completa();
					} catch(Exception e) {}
				}
				
				oFactura.setTotal(suma_montos);
				
				lista_completa.add(oFactura);
				
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
	
	
	public static Factura buscarFacturaPorID(int id) throws Exception {
		
		System.out.println("FacturaProcesos.buscarFacturaPorID " + id);
		
		// 1º Abro una sesion de datos
		// Session oSessionH = SessionFactory.currentSession();
		
		FacturaDAO oDAO = new FacturaDAO();
		Factura oFactura = oDAO.findById(new Integer(id));
	
    	// El recibo se busca con todos los datos completos ...
//    	try {
//    		oFactura = FacturaProcesos.completarConSaldo(oFactura);
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw e;
//		}
    	
		// Dejo cerrada la sesion de datos
		// try{oSessionH.close();} catch(Exception e) {}
		
    	return oFactura;
    	
	}

	
} //  Fin clase