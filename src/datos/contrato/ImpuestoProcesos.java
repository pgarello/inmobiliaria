package datos.contrato;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.hibernate.Session;
import org.hibernate.Transaction;

import datos.SessionFactory;
import datos.contrato_novedad_cobro.ContratoNovedadCobro;
import datos.contrato_novedad_cobro.ContratoNovedadCobroDAO;
import datos.contrato_novedad_pago.ContratoNovedadPago;
import datos.contrato_novedad_pago.ContratoNovedadPagoDAO;
import datos.contrato_novedad_pago.ContratoNovedadPagoProcesos;
import framework.ui.generales.exception.ReglasDeNegocioException;

public class ImpuestoProcesos {

	/**
	 * Busca las novedades DEL TIPO IMPUESTO de un Contrato
	 * 
	 * Tengo que buscar en Novedades de Cobro, como en Novedades de Pago ... hay que crear un objeto superior (herencia) sobre
	 * ambos. Ojo no sirve herencia ya que puedo tener datos en ambos (pagos y cobros) 
	 * 09/01/2012
	 * 
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<ContratoNovedadDTO> buscarNovedades(Contrato oContrato) {
		
		//System.out.println("buscarNovedadesCobroPorContrato 1 " + vigentes);
		List<ContratoNovedadDTO> lista_datos = new Vector<ContratoNovedadDTO>();
		
		// Busco las novedades de PAGO
		List<ContratoNovedadPago> lista_datosPAGOS = new Vector<ContratoNovedadPago>();
		ContratoNovedadPagoDAO oDAO = new ContratoNovedadPagoDAO();
		lista_datosPAGOS = oDAO.findImpuestosPorContrato(oContrato);
		
		/** 
		 * Tendría que completar los datos de los impuestos asociados JOIN 
		 * Lo puedo hacer procesando las 2 colecciones en vez de volver a consultar la BD
		 * Asi puedo ir limpiando los datos y no mostrar en forma duplicada un impuesto ...
		 */
		
		// Busco las novedades de COBRO
		List<ContratoNovedadCobro> lista_datosCOBROS = new Vector<ContratoNovedadCobro>();
		ContratoNovedadCobroDAO oDAO2 = new ContratoNovedadCobroDAO();
		lista_datosCOBROS = oDAO2.findImpuestosPorContrato(oContrato);
		
		// Recorro ambas colecciones y armo una única que devuelvo como salida del método
		/* Busco cada impuesto de la colección de PAGOS (propietarios) en los COBROS (inquilinos) */
		for (ContratoNovedadPago oPAGO : lista_datosPAGOS) {
			
			// Busco si tiene asociado algún registro de Cobro
			short id_impuesto = oPAGO.getImpuestoId();
			short impuesto_anio = oPAGO.getImpuestoAnio();
			short impuesto_cuota = oPAGO.getImpuestoCuota();
			short contrato_cuota = oPAGO.getContratoCuota();
			
			double monto_inquilino = 0;
			boolean salir = false;
			Iterator iCOBROS = lista_datosCOBROS.iterator();
			while (iCOBROS.hasNext() && !salir) {
				
				ContratoNovedadCobro oCOBRO = (ContratoNovedadCobro) iCOBROS.next();
				
				short id_impuesto2 = oCOBRO.getImpuestoId();
				short impuesto_anio2 = oCOBRO.getImpuestoAnio();
				short impuesto_cuota2 = oCOBRO.getImpuestoCuota();
				short contrato_cuota2 = oCOBRO.getContratoCuota();
				
				if (id_impuesto == id_impuesto2 &&
					impuesto_anio == impuesto_anio2 &&
					impuesto_cuota == impuesto_cuota2 &&
					contrato_cuota == contrato_cuota2 ) {
					
					salir = true;
					monto_inquilino = oCOBRO.getMonto();
					
					lista_datosCOBROS.remove(oCOBRO);
					
				}
				
			}
			
			// Armo el DTO
			ContratoNovedadDTO oDTO = new ContratoNovedadDTO(	oPAGO.getContrato(),
																oPAGO.getPeriodoMes(),
																oPAGO.getPeriodoAnio(),
																oPAGO.getContratoCuota(),
																oPAGO.getMonto().doubleValue(),
																monto_inquilino,
																oPAGO.getFechaLiquidacion(),
																oPAGO.getObservaciones(),
																id_impuesto,
																impuesto_cuota,
																impuesto_anio);
			
			lista_datos.add(oDTO);
			
			
		}
		
		// Recorro la 2º colección y agrego en forma directa los datos a la salida
		for (ContratoNovedadCobro oCOBRO : lista_datosCOBROS) {
			
			// Armo el DTO
			ContratoNovedadDTO oDTO = new ContratoNovedadDTO(	oCOBRO.getContrato(),
																oCOBRO.getPeriodoMes(),
																oCOBRO.getPeriodoAnio(),
																oCOBRO.getContratoCuota(),
																0,
																oCOBRO.getMonto().doubleValue(),
																oCOBRO.getFechaVencimiento(),
																oCOBRO.getObservaciones(),
																oCOBRO.getImpuestoId(),
																oCOBRO.getImpuestoCuota(),
																oCOBRO.getImpuestoAnio());
			
			lista_datos.add(oDTO);
			
		}
		
		
		return lista_datos;
		
	}
	
	/**
	 * Borra una NOVEDAD de Impuesto/Varios.
	 * Tiene que evaluar donde borrar ¿En las novedades de COBRO y/o de PAGO?
	 * Tengo que manejar una transacción
	 * @param oNovedad
	 * @throws ReglasDeNegocioException 
	 */
	public static void borrarNovedad(ContratoNovedadDTO oNovedad) throws ReglasDeNegocioException {
		
		// Manejo de la transacción global
		Session oSessionH = SessionFactory.currentSession();
		Transaction tx = null;
		
		try {
			
			tx = oSessionH.beginTransaction();		
		
			if (oNovedad.getMonto_inquilino() > 0) {
				// Borro en ContratoNovedadCobro
				
			}
			
			if (oNovedad.getMonto_propietario() > 0) {
				
				// Borro en ContratoNovedadPago
				ContratoNovedadPagoProcesos.borrarNovedad(oNovedad);
				
			}
			
			tx.commit();
			
		} catch (Exception e) {
			
			e.printStackTrace();
			tx.rollback();
			throw new ReglasDeNegocioException(e);
			
		} finally { 
			// dio error de que la session ya estaba cerrada
			try{oSessionH.close();} catch(Exception e) {}
		}
		
	}
	
	
} //  Fin clase