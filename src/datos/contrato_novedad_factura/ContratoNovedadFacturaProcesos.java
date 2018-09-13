package datos.contrato_novedad_factura;

import java.util.Calendar;

import java.util.List;
import java.util.Vector;

import datos.contrato.Contrato;
import datos.factura_item.FacturaItem;
import datos.factura_item.FacturaItemDAO;

import datos.persona.Persona;

import framework.ui.generales.exception.ReglasDeNegocioException;

public class ContratoNovedadFacturaProcesos {

	/**
	 * Busca las novedades 
	 * @param oContrato
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<ContratoNovedadFactura> buscarNovedadesPorContrato(Contrato oContrato) {
		
		//System.out.println("buscarNovedadesCobroPorContrato 1 " + vigentes);
		
		List<ContratoNovedadFactura> lista_datos = new Vector<ContratoNovedadFactura>();
		
		ContratoNovedadFacturaDAO oDAO = new ContratoNovedadFacturaDAO();
		lista_datos = oDAO.findByProperty("contrato", oContrato);
		
		return lista_datos;
		
	}
	
	
	/**
	 * Busca las novedades de FACTURA de una PERSONA
	 * @param oPersona
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<ContratoNovedadFactura> buscarNovedadesFacturaPorPersona(Persona oPersona, short mes, short anio) {
		
		//ver lo de los log !!!!!!!!!!!!!!
		System.out.println("ContratoNovedadFacturaProcesos.buscarNovedadesFacturaPorPersona " + oPersona.getIdPersona());
		
		// Las NOVEDADES DE FACTURA no tienen vigencia, todo lo que esta sin facturar hay q facturarlo !!! 29/01/2013
		boolean vigentes = false;
		
		List<ContratoNovedadFactura> lista_datos = new Vector<ContratoNovedadFactura>();
		
		lista_datos = ContratoNovedadFacturaDAO.findByProperty("persona", oPersona);
		System.out.println("buscarNovedadesFacturaPorPersona 1.1 " + lista_datos.size());
		
		// Paso las novedades que aún no fueron facturadas y que están vigentes a la fecha y NO FACTURADAS
		List<ContratoNovedadFactura> lista_datos_completa = new Vector<ContratoNovedadFactura>();		
		for(ContratoNovedadFactura oNovedad: lista_datos) {
			
			boolean procesar = true;
			
			// Evaluo si ya no se FACTURO la NOVEDAD
			if (ContratoNovedadFacturaProcesos.novedadYaFacturada(oNovedad)) {
				
				// Ya facturada
				procesar = false;
				
			} else {
				System.out.println("pasooo");
				
				/**
				 * Es necesario evaluar la fecha de vencimiento de una novedad para ser facturada
				 * o con el solo hecho de estar en la tabla ya directamente lo facturo.
				 * Pensar en que momento se dan de alta las novedades para facturar ¿?
				 * - ALTA DE CONTRATO
				 * - SE PRESENTA EL INQUILINO A PAGAR
				 * - SE PRESENTA EL PROPIETARIO A COBRAR
				 * 
				 */
				
				
				if (vigentes) {
					
					// lo evaluo con la fecha del día ... pero tengo en cuenta el mes completo
					Calendar hoy = Calendar.getInstance();
					hoy.add(Calendar.MONTH, 1);
					hoy.set(Calendar.DAY_OF_MONTH, 1); // seteo el primer día del mes siguiente
					
					// Lo vigente ahora es lo vencido !!!
					
					// Hay movimientos que no tienen fecha de vencimiento ... por ejemplo las COMISIONES DE ALQUILER ¿?
					if (oNovedad.getFechaVencimiento() != null) {
						System.out.println("buscarNovedadesPagoPorPropietario 2 " 
								+ oNovedad.getFechaVencimiento() + " - " 
								+ oNovedad.getFechaVencimiento().after(hoy.getTime()) + " - "
								+ oNovedad.getContratoCuota());
						
						if (oNovedad.getFechaVencimiento().after(hoy.getTime())) {					
							procesar = false;
						}
					} else {
						// Si no tiene fecha .... no lo muestro
						procesar = false;
					}				
				}
				
				if (procesar) 
					if (mes != 0 || anio != 0) {
						
						Calendar oFechaCalendar = Calendar.getInstance();
						oFechaCalendar.setTime(oNovedad.getFechaVencimiento());
						
						short mesNovedad = (short)oFechaCalendar.get(Calendar.MONTH);
						mesNovedad++;
						
						if(mesNovedad != mes ) {
							procesar = false;
						}						
						
						if((short)oFechaCalendar.get(Calendar.YEAR) != anio ) {
							procesar = false;
						}					
					}
				
			}
				
			if (procesar) {
				lista_datos_completa.add(oNovedad);		
			}
			
		} // Fin for
		
		return lista_datos_completa;
		
	}
	
		
	@SuppressWarnings("unchecked")
	private static boolean novedadYaFacturada(ContratoNovedadFactura oNovedad) {		
		
		// Busco si algún ITEM de FACTURA hace referencia a la NOVEDAD - El monto siempre es por el total, no existe
		// la facturación parcial !!!
		List<FacturaItem> cNovedadesFactura = FacturaItemDAO.findByProperty("contratoNovedadFactura", oNovedad);
		
		System.out.println("CONTROL novedadYaFacturada: " + cNovedadesFactura.size());
		
		// Evaluo si no está/n anuladas las facturas que referencian a la novedad 
		boolean yaFacturada = false;
		for(FacturaItem oItem: cNovedadesFactura) {
			if (!oItem.getFactura().getAnulada()) {
				yaFacturada = true;
			}
		}
		
		return yaFacturada;
		
	}
	
	
	public static void delete(ContratoNovedadFactura oNovedad) throws ReglasDeNegocioException {
		
		/*
		 * Regla de negocio
		 * 1º Que ya no esté facturada la novedad 
		 */
		if (novedadYaFacturada(oNovedad)) {
			throw new ReglasDeNegocioException("No se puede borrar la NOVEDAD por que ya fue facturada.");
		}
		
		try {
			ContratoNovedadFacturaDAO.delete(oNovedad);
		} catch (Exception e) {			
			e.printStackTrace();
			throw new ReglasDeNegocioException(e);			
		}
		
	}
	

	
//	public static ContratoNovedadPago buscarPorId(int id) throws Exception {
//		
//		System.out.println("ContratoNovedadPagoProcesos.buscarPorId: " + id);
//		
//		ContratoNovedadPagoDAO oDAO = new ContratoNovedadPagoDAO();
//		ContratoNovedadPago oContratoNovedadPago = oDAO.findById(new Integer(id));
//		
//		oContratoNovedadPago = completarDatosImpresion(oContratoNovedadPago);
//		
//		return oContratoNovedadPago;
//		
//	}
//	
//	
//	@SuppressWarnings("unchecked")
//	public static ContratoNovedadPago completarDatosImpresion(ContratoNovedadPago oNovedad) throws Exception {
//		
//		//System.out.println("ContratoNovedadCobroProcesos.completarDatosImpresion: " + oNovedad.getMonto() + " - " + oNovedad.getPeriodoCuota());
//		System.out.println("ContratoNovedadPagoProcesos.completarDatosImpresion: ");
//		
//		// Ahora inicializo las relaciones lazy
//		Session oSessionH = SessionFactory.currentSession();
//		
//		try {
//			oSessionH.refresh(oNovedad);			
//			
//			// Completo los datos del saldo
//			oNovedad = completarConSaldo(oNovedad);
//			
//			/* 
//			 * Cuando consulta los datos en el DAO creo que cierra la sesión de datos
//			 * Esta bien cerrar constantemente la sesion de datos ¿??¿? y especialmente en los
//			 * DAO ¿?¿?¿?¿
//			 * 6/8/2009 
//			 */
//			
//			//oSessionH.refresh(oNovedad);
//			Hibernate.initialize(oNovedad.getContrato());
//			oNovedad.getContrato().getInmueble().getDireccion_completa();
//		
//		} catch (Exception e) {
//			
//			e.printStackTrace();
//			throw e;
//			
//		} finally { 
//			// dio error de que la session ya estaba cerrada
//			// try{oSessionH.close();} catch(Exception e) {}
//		}
//		
//		//System.out.println("CONTROL ContratoNovedadCobro: " + cItems.size());
//		
//		return oNovedad;
//		
//	}
//	
//	
//	/**
//	 * Graba una novedad de pago (PROPIETARIO)
//	 * Se lo tengo que detallar en el recibo que va para el propietario (Liquidación Pago)
//	 * 
//	 * @param oContrato
//	 * @param oPersona (Propietario)
//	 * @param contrato_cuota
//	 * @param fecha_vencimiento
//	 * @param monto
//	 * @param periodo_anio
//	 * @param periodo_mes
//	 * @param novedad_tipo
//	 * @param observaciones
//	 */
//	public static void grabarNovedad(	Contrato oContrato,
//										Persona oPersona,
//										short contrato_cuota,
//										Date fecha_vencimiento,
//										double monto,
//										short periodo_anio,
//										short periodo_mes,
//										short novedad_tipo,
//										String observaciones,
//										short id_impuesto,
//										short impuesto_cuota,
//										short impuesto_anio) 
//	 throws ReglasDeNegocioException {
//		
//		System.out.println("ContratoNovedadPagoProcesos.grabarNovedad: ");
//		
//		// Reglas de NEGOCIO
//		
//		/** 
//		 * 1º Como valido unicidad, por ejemplo si ya ingresé el impuesto 
//		 * 2º Tengo que validar que el periodo ya no este liquidado para este CONTRATO
//		 * 3º Validar la unicidad del impuesto (id_impuesto, cuota, anio)
//		 * 
//		 * **/
//		
//		
//		
//		/* 	Grabo */
//			
//		ContratoNovedadPago oNovedadPago = new ContratoNovedadPago();
//			
//		oNovedadPago.setContrato(oContrato);
//		oNovedadPago.setPersona(oPersona);
//		
//		oNovedadPago.setContratoCuota(contrato_cuota);
//		oNovedadPago.setFechaLiquidacion(fecha_vencimiento);
//		oNovedadPago.setMonto(monto);
//		oNovedadPago.setPeriodoAnio(periodo_anio);
//		oNovedadPago.setPeriodoMes(periodo_mes);
//		oNovedadPago.setIdNovedadTipo(novedad_tipo);
//		oNovedadPago.setObservaciones(observaciones);
//		
//		oNovedadPago.setImpuestoId(id_impuesto);
//		oNovedadPago.setImpuestoCuota(impuesto_cuota);
//		oNovedadPago.setImpuestoAnio(impuesto_anio);
//		
//		ContratoNovedadPagoDAO.save(oNovedadPago);			
//		
//	} // Fin grabarNovedad
	
	
} //  Fin clase