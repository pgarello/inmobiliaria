package datos.contrato_novedad_pago;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.hibernate.Hibernate;
import org.hibernate.Session;

import app.beans.ItemTipo;
import app.beans.NovedadTipo;

import datos.SessionFactory;
import datos.contrato.Contrato;
import datos.contrato.ContratoNovedadDTO;

import datos.persona.Persona;

import datos.recibo_pago_item.ReciboPagoItem;

import datos.recibo_pago_item.ReciboPagoItemDAO;

import framework.ui.generales.exception.ReglasDeNegocioException;

public class ContratoNovedadPagoProcesos {

	/**
	 * Busca las novedades de pago de un Alquiler
	 * @param oContrato
	 * @param con_saldo boolean que especifica si quiero todos los movimientos (cuotas) o solo los que tienen deuda
	 * @param vigentes booleana que especifica si quiero las novedades vigentes solamente
	 * @param mes
	 * @param año
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<ContratoNovedadPago> buscarNovedadesPagoPorContrato(Contrato oContrato, boolean con_saldo, boolean vigentes, short mes, short anio) {
		
		System.out.println("buscarNovedadesPagoPorContrato 1 " + vigentes + " " + mes + " " + anio);
		
		List<ContratoNovedadPago> lista_datos = new Vector<ContratoNovedadPago>();
		
		ContratoNovedadPagoDAO oDAO = new ContratoNovedadPagoDAO();
		lista_datos = oDAO.findByProperty("contrato", oContrato);
		
		// Para que este completa la novedad tendría que sacar el saldo de cada cuota
		List<ContratoNovedadPago> lista_datos_completa = new Vector<ContratoNovedadPago>();
		for(ContratoNovedadPago oNovedad: lista_datos) {
			
			boolean procesar = true;
			if (vigentes) {
				
				// lo evaluo con la fecha del día mas 1 mes
				Calendar hoy = Calendar.getInstance();
				hoy.add(Calendar.MONTH, 1);
				
				//System.out.println("buscarNovedadesPagoPorContrato 2 " + oNovedad.getFechaLiquidacion().after(hoy.getTime()));
				if (oNovedad.getFechaLiquidacion().after(hoy.getTime())) {					
					procesar = false;					
				}
				
			}
			
			if (procesar)
				if (mes != 0 || anio != 0) {
					
					Calendar oFechaCalendar = Calendar.getInstance();
					oFechaCalendar.setTime(oNovedad.getFechaLiquidacion());
					
					short mesNovedad = (short)oFechaCalendar.get(Calendar.MONTH);
					mesNovedad++;
					
					if(mesNovedad != mes ) {
						procesar = false;
					}						
					
					if((short)oFechaCalendar.get(Calendar.YEAR) != anio ) {
						procesar = false;
					}					
				}
			
			if (procesar) {
				oNovedad = completarConSaldo(oNovedad);
				if (con_saldo) {
					
					// Evaluo el saldo solo de los tipos de ALQUILER
					// if (oNovedad.getSaldo() > 0) {
					if ((oNovedad.getSaldo() > 0 && oNovedad.getIdNovedadTipo() == NovedadTipo.Alquiler) ||
						(oNovedad.getSaldo() != 0 && oNovedad.getIdNovedadTipo() == NovedadTipo.Impuesto) ||
						(oNovedad.getSaldo() < 0 && oNovedad.getIdNovedadTipo() != NovedadTipo.Alquiler)) {
					
						// Con deuda
						lista_datos_completa.add(oNovedad);		
					}
				} else {
					// Todos
					lista_datos_completa.add(oNovedad);
				}
			}
			
		} // Fin for
		
		return lista_datos_completa;
		
	}
	
	
	/**
	 * Busca las novedades de cobro de un Alquiler
	 * @param oInquilino
	 * @param con_saldo boolean que especifica si quiero todos los movimientos (cuotas) o solo los que tienen deuda
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<ContratoNovedadPago> buscarNovedadesPagoPorPropietario(Persona oPropietario, boolean con_saldo, boolean vigentes, short mes, short anio) {
		
		//ver lo de los log !!!!!!!!!!!!!!
		System.out.println("ContratoNovedadPagoProcesos.buscarNovedadesPagoPorPropietario 1 vigente:" + vigentes + " - con_saldo:" + con_saldo);
		
		List<ContratoNovedadPago> lista_datos = new Vector<ContratoNovedadPago>();
		
		lista_datos = ContratoNovedadPagoDAO.findByPersona(oPropietario); // levanta todas las novedades del PROPIETARIO .. debería buscar solo las NO SALDADAS
		System.out.println("buscarNovedadesPagoPorPropietario 1.1 " + lista_datos.size());
		
		// Para que este completa la novedad tendría que sacar el saldo de cada cuota
		List<ContratoNovedadPago> lista_datos_completa = new Vector<ContratoNovedadPago>();
		for(ContratoNovedadPago oNovedad: lista_datos) {
			
			boolean procesar = true;
			if (vigentes) {
				
				// lo evaluo con la fecha del día ... pero tengo en cuenta el mes completo
				Calendar hoy = Calendar.getInstance();
				hoy.add(Calendar.MONTH, 1);
				hoy.set(Calendar.DAY_OF_MONTH, 1); // seteo el primer día del mes siguiente
				
				// Lo vigente ahora es lo vencido !!!
				
				// Hay movimientos que no tienen fecha de vencimiento ... por ejemplo las COMISIONES DE ALQUILER ¿?
				if (oNovedad.getFechaLiquidacion() != null) {
					System.out.println("buscarNovedadesPagoPorPropietario 2 " 
							+ oNovedad.getFechaLiquidacion() + " - " 
							+ oNovedad.getFechaLiquidacion().after(hoy.getTime()) + " - "
							+ oNovedad.getContratoCuota());
					
					if (oNovedad.getFechaLiquidacion().after(hoy.getTime())) {					
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
					oFechaCalendar.setTime(oNovedad.getFechaLiquidacion());
					
					short mesNovedad = (short)oFechaCalendar.get(Calendar.MONTH);
					mesNovedad++;
					
					if(mesNovedad != mes ) {
						procesar = false;
					}						
					
					if((short)oFechaCalendar.get(Calendar.YEAR) != anio ) {
						procesar = false;
					}					
				}
			
			if (procesar) {
				oNovedad = completarConSaldo(oNovedad);
				if (con_saldo) {
					
					// El saldo lo miro si el tipo de movimiento es  oNovedad.getIdNovedadTipo() == NovedadTipo.Alquiler && 
					System.out.println("Saldo " + oNovedad.getSaldo() + " - " + oNovedad.getIdNovedadTipo());
					if ((oNovedad.getSaldo() > 0 && oNovedad.getIdNovedadTipo() == NovedadTipo.Alquiler) ||
						(oNovedad.getSaldo() != 0 && oNovedad.getIdNovedadTipo() == NovedadTipo.Impuesto) ||
						(oNovedad.getSaldo() < 0 && oNovedad.getIdNovedadTipo() != NovedadTipo.Alquiler)) {
						// Con deuda
						lista_datos_completa.add(oNovedad);
					} 
					
				} else {
					// Todos
					lista_datos_completa.add(oNovedad);
				}
			}
			
		} // Fin for
		
		return lista_datos_completa;
		
	}
	
	
	@SuppressWarnings("unchecked")
	private static ContratoNovedadPago completarConSaldo(ContratoNovedadPago oNovedad) {		
		
		/**
		 * Tener en cuenta que los items de IVA estan asociados a la novedad que los origina
		 * pero no se tienen que tener en cuenta para calcular el saldo de la novedad
		 * Pgarello 23/01/2013
		 */
		
		// Busco el cobro de un recibo de Liquidación Cobranza
		List<ReciboPagoItem> cItems = ReciboPagoItemDAO.findByProperty("contratoNovedadPago", oNovedad);
		
		System.out.println("ContratoNovedadPagoProcesos.completarConSaldo: " + cItems.size());
		
		double suma_montos = 0;
		for(ReciboPagoItem oItem: cItems) {
			
			if (oItem.getIdItemTipo() != ItemTipo.ItemIVA)
				suma_montos += oItem.getMonto();
		
		}
		
		double saldo = oNovedad.getMonto() - suma_montos;
		System.out.println("ContratoNovedadPagoProcesos.completarConSaldo SALDO: " + saldo);
		
		oNovedad.setSaldo(saldo);
		
		return oNovedad;
		
	}
	
	
	public static ContratoNovedadPago buscarPorId(int id) throws Exception {
		
		System.out.println("ContratoNovedadPagoProcesos.buscarPorId: " + id);
		
		ContratoNovedadPagoDAO oDAO = new ContratoNovedadPagoDAO();
		ContratoNovedadPago oContratoNovedadPago = oDAO.findById(new Integer(id));
		
		oContratoNovedadPago = completarDatosImpresion(oContratoNovedadPago);
		
		return oContratoNovedadPago;
		
	}
	
	
	@SuppressWarnings("unchecked")
	public static ContratoNovedadPago completarDatosImpresion(ContratoNovedadPago oNovedad) throws Exception {
		
		//System.out.println("ContratoNovedadCobroProcesos.completarDatosImpresion: " + oNovedad.getMonto() + " - " + oNovedad.getPeriodoCuota());
		System.out.println("ContratoNovedadPagoProcesos.completarDatosImpresion: ");
		
		// Ahora inicializo las relaciones lazy
		Session oSessionH = SessionFactory.currentSession();
		
		try {
			oSessionH.refresh(oNovedad);			
			
			// Completo los datos del saldo
			oNovedad = completarConSaldo(oNovedad);
			
			/* 
			 * Cuando consulta los datos en el DAO creo que cierra la sesión de datos
			 * Esta bien cerrar constantemente la sesion de datos ¿??¿? y especialmente en los
			 * DAO ¿?¿?¿?¿
			 * 6/8/2009 
			 */
			
			//oSessionH.refresh(oNovedad);
			Hibernate.initialize(oNovedad.getContrato());
			oNovedad.getContrato().getInmueble().getDireccion_completa();
		
		} catch (Exception e) {
			
			e.printStackTrace();
			throw e;
			
		} finally { 
			// dio error de que la session ya estaba cerrada
			// try{oSessionH.close();} catch(Exception e) {}
		}
		
		//System.out.println("CONTROL ContratoNovedadCobro: " + cItems.size());
		
		return oNovedad;
		
	}
	
	
	/**
	 * Graba una novedad de pago (PROPIETARIO)
	 * Se lo tengo que detallar en el recibo que va para el propietario (Liquidación Pago)
	 * 
	 * @param oContrato
	 * @param oPersona (Propietario)
	 * @param contrato_cuota
	 * @param fecha_vencimiento
	 * @param monto
	 * @param periodo_anio
	 * @param periodo_mes
	 * @param novedad_tipo
	 * @param observaciones
	 */
	public static void grabarNovedad(	Contrato oContrato,
										Persona oPersona,
										short contrato_cuota,
										Date fecha_vencimiento,
										double monto,
										short periodo_anio,
										short periodo_mes,
										short novedad_tipo,
										String observaciones,
										short id_impuesto,
										short impuesto_cuota,
										short impuesto_anio) 
	 throws ReglasDeNegocioException {
		
		System.out.println("ContratoNovedadPagoProcesos.grabarNovedad: ");
		
		// Reglas de NEGOCIO
		
		/** 
		 * 1º Como valido unicidad, por ejemplo si ya ingresé el impuesto 
		 * 2º Tengo que validar que el periodo ya no este liquidado para este CONTRATO
		 * 3º Validar la unicidad del impuesto (id_impuesto, cuota, anio)
		 * 
		 * **/
		
		
		
		/* 	Grabo */
			
		ContratoNovedadPago oNovedadPago = new ContratoNovedadPago();
			
		oNovedadPago.setContrato(oContrato);
		oNovedadPago.setPersona(oPersona);
		
		oNovedadPago.setContratoCuota(contrato_cuota);
		oNovedadPago.setFechaLiquidacion(fecha_vencimiento);
		
		// Como se lo tengo que descontar al PROPIETARIO lo pongo en NEGATIVO --> NO POR QUE TENGO QUE CAMBIAR LA PANTALLA DE EDICION
		oNovedadPago.setMonto(monto * -1);
		oNovedadPago.setPeriodoAnio(periodo_anio);
		oNovedadPago.setPeriodoMes(periodo_mes);
		oNovedadPago.setIdNovedadTipo(novedad_tipo);
		oNovedadPago.setObservaciones(observaciones);
		
		oNovedadPago.setImpuestoId(id_impuesto);
		oNovedadPago.setImpuestoCuota(impuesto_cuota);
		oNovedadPago.setImpuestoAnio(impuesto_anio);
		
		ContratoNovedadPagoDAO.save(oNovedadPago);			
		
	} // Fin grabarNovedad
	
	
	
	public static void borrarNovedad(ContratoNovedadDTO oNovedad) {
		
		// Tengo que buscar la novedad para poder borrarla
		
		
		
	}
	
	
} //  Fin clase