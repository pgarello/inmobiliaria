package datos.contrato_novedad_cobro;

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
import datos.contrato_novedad_cobro.ContratoNovedadCobro;
import datos.contrato_novedad_pago.ContratoNovedadPago;
import datos.contrato_novedad_pago.ContratoNovedadPagoProcesos;

import datos.persona.Persona;
import datos.recibo_cobro_item.ReciboCobroItem;
import datos.recibo_cobro_item.ReciboCobroItemDAO;
import framework.ui.generales.exception.ReglasDeNegocioException;

public class ContratoNovedadCobroProcesos {

	/**
	 * Busca las novedades de cobro de un Alquiler
	 * @param oContrato
	 * @param con_saldo boolean que especifica si quiero todos los movimientos (cuotas) o solo los que tienen deuda
	 * @param vigentes booleana que especifica si quiero las novedades vigentes solamente
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<ContratoNovedadCobro> buscarNovedadesCobroPorContrato(Contrato oContrato, boolean con_saldo, boolean vigentes) {
		
		System.out.println("buscarNovedadesCobroPorContrato 1 " + vigentes);
		
		List<ContratoNovedadCobro> lista_datos = new Vector<ContratoNovedadCobro>();
		
		ContratoNovedadCobroDAO oDAO = new ContratoNovedadCobroDAO();
		lista_datos = oDAO.findByProperty("contrato", oContrato);
		
		// Para que este completa la novedad tendría que sacar el saldo de cada cuota
		List<ContratoNovedadCobro> lista_datos_completa = new Vector<ContratoNovedadCobro>();
		for(ContratoNovedadCobro oNovedad: lista_datos) {
			
			boolean procesar = true;
			if (vigentes) {
				
				// lo evaluo con la fecha del día mas 1 mes
				Calendar hoy = Calendar.getInstance();
				hoy.add(Calendar.MONTH, 1);
				
				//System.out.println("buscarNovedadesCobroPorContrato 2 " + oNovedad.getFechaVencimiento() + " - " + oNovedad.getFechaVencimiento().after(hoy.getTime()));
				// ANTES - si el vencimiento es anterior a la fecha de hoy no lo muestro
				// DESPUES - si el vencimiento es después a la fecha (sumado 1 mes) no lo muesto - 5/09/2012
				if (oNovedad.getFechaVencimiento().after(hoy.getTime())) {					
					procesar = false;					
				}
				
			}
			
			if (procesar) {
				oNovedad = completarConSaldo(oNovedad);
				completarConPagado(oNovedad);
				if (con_saldo) {
					if (oNovedad.getSaldo() > 0) {
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
	 * @param vigentes VERDADERO si quiero filtrar por los movimientos vigentes, FALSO todos
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<ContratoNovedadCobro> buscarNovedadesCobroPorInquilino(Persona oInquilino, boolean con_saldo, boolean vigentes) {
		
		//ver lo de los log !!!!!!!!!!!!!!
		System.out.println("buscarNovedadesCobroPorInquilino 1 " + vigentes);
		
		List<ContratoNovedadCobro> lista_datos = new Vector<ContratoNovedadCobro>();
		
		lista_datos = ContratoNovedadCobroDAO.findByPersona(oInquilino);
		
		// Para que este completa la novedad tendría que sacar el saldo de cada cuota
		List<ContratoNovedadCobro> lista_datos_completa = new Vector<ContratoNovedadCobro>();
		for(ContratoNovedadCobro oNovedad: lista_datos) {
			
			boolean procesar = true;
			if (vigentes) {
				
				// lo evaluo con la fecha del día mas 1 mes
				Calendar hoy = Calendar.getInstance();
				hoy.add(Calendar.MONTH, 1);
				//System.out.println("buscarNovedadesCobroPorInquilino 2 " + oNovedad.getFechaVencimiento().after(hoy.getTime()));
				if (oNovedad.getFechaVencimiento().after(hoy.getTime())) {					
					procesar = false;
				}
				
			}
			
			if (procesar) {
				oNovedad = completarConSaldo(oNovedad);
				if (con_saldo) {				
					if (oNovedad.getSaldo() > 0) {
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
	
	
	public static List<ContratoNovedadCobro> buscarNovedadesCobroDeuda(short mes, short anio) {
		
		//ver lo de los log !!!!!!!!!!!!!!
		System.out.println("buscarNovedadesCobroDeuda 1 ");
		
		List<ContratoNovedadCobro> lista_datos = new Vector<ContratoNovedadCobro>();
		
		lista_datos = ContratoNovedadCobroDAO.findByPeriodo(mes, anio);
		
		// Para que este completa la novedad tendría que sacar el saldo de cada cuota
		List<ContratoNovedadCobro> lista_datos_completa = new Vector<ContratoNovedadCobro>();
		for(ContratoNovedadCobro oNovedad: lista_datos) {
			
			boolean procesar = true;
 
			// evaluo que el contrato no este ANULADO o RESCINDIDO para esa fecha O SOLO MIRO LOS Q ESTAN VIGENTES
			// al rescindirse un contrato, toda la DEUDA queda pendiente ¿?¿?¿?¿ NO, solo se borra los movimientos posteriores a la
			// fecha de rescisión
			// fecha_desde < hoy < fecha_rescision
			Calendar hoy = Calendar.getInstance();
			hoy.set(Calendar.MONTH, mes-1);
			hoy.set(Calendar.YEAR, anio);
//			System.out.println("buscarNovedadesCobro con DEUDA " + oNovedad.getContrato().getFechaRescision() + " - " + hoy.getTime());
//			if (oNovedad.getContrato().getFechaRescision() != null ) {				
//				procesar = false;
//			}
			
			if (procesar) {
				oNovedad = completarConSaldo(oNovedad);
				if (oNovedad.getSaldo() > 0) {
					// Con deuda
					lista_datos_completa.add(oNovedad);
					//System.out.println("CONTROL DE DATOS : " + oNovedad.getMonto() + " - " + oNovedad.getSaldo());
				}
			}
			
		} // Fin for
		
		return lista_datos_completa;
		
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	private static ContratoNovedadCobro completarConSaldo(ContratoNovedadCobro oNovedad) {
		
		/**
		 * Tener en cuenta que los items de IVA estan asociados a la novedad que los origina
		 * pero no se tienen que tener en cuenta para calcular el saldo de la novedad
		 * Pgarello 23/01/2013
		 */
		
		// Busco el cobro de un recibo de Liquidación Cobranza
		List<ReciboCobroItem> cItems = ReciboCobroItemDAO.findByProperty("contratoNovedadCobro", oNovedad);
		
		//System.out.println("CONTROL ContratoNovedadCobro: " + cItems.size());
		
		double suma_montos = 0;
		for(ReciboCobroItem oItem: cItems) {
			
			if (oItem.getIdItemTipo() != ItemTipo.ItemIVA)			
				suma_montos += oItem.getMonto();
		
		}
		
		double saldo = oNovedad.getMonto() - suma_montos;
		//System.out.println("ContratoNovedadCobroProcesos.completarConSaldo SALDO: " + saldo);
		
		oNovedad.setSaldo(saldo);
		
		return oNovedad;
		
	}

	
	/** 
	 * Busca y asocia si se pago la NOVEDAD
	 * @param oNovedad
	 * @return
	 */
	public static void completarConPagado(ContratoNovedadCobro oNovedad) {
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		System.out.println("ContratoNovedadCobroProcesos.completarConPagado START");
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		
		/**
		 * Tengo que buscar la Novedad de Pago asociada y ver si tiene un recibo item
		 */
		
		Contrato oContrato = oNovedad.getContrato();
		boolean con_saldo = false, vigentes = false;
		short mes = oNovedad.getPeriodoMes();
		short anio = oNovedad.getPeriodoAnio();
		
		// Busco la novedad de PAGO asociada
		List<ContratoNovedadPago> cNovedadPagos = ContratoNovedadPagoProcesos.buscarNovedadesPagoPorContrato(oContrato, con_saldo, vigentes, mes, anio);
		
		System.out.println("CONTROL ContraContratoNovedadCobroProcesos.completarConPagado: " + cNovedadPagos.size() + " - " + oContrato.getInmueble().getDireccion_completa());
		// Recorro las novedades y saco el monto del tipo ALQUILER
		double suma_montos = 0;
		for(ContratoNovedadPago oNovedadP: cNovedadPagos) {
			System.out.println(oNovedadP.getNoLiquidada() + " - " + oNovedadP.getIdNovedadTipo());
			if (oNovedadP.getIdNovedadTipo() == NovedadTipo.Alquiler) {
				
				// Tengo que buscar los recibos o cuanto se pagó de esta novedad, si el saldo es CERO se pago el 100%
				if (oNovedadP.getSaldo() == 0) {				
					suma_montos += oNovedadP.getMonto();
				} else {
					System.out.println("PAGADO: " + oNovedadP.getMonto() + " - " + oNovedadP.getSaldo());
					suma_montos += oNovedadP.getMonto() - oNovedadP.getSaldo();
				}
			}
				
		
		}
		
		//System.out.println("ContratoNovedadCobroProcesos.completarConSaldo SALDO: " + saldo);		
		oNovedad.setPagado(suma_montos);
		
	}
	
	
	public static ContratoNovedadCobro buscarPorId(int id) throws Exception {
		
		System.out.println("ContratoNovedadCobroProcesos.buscarPorId: " + id);
		
		ContratoNovedadCobroDAO oDAO = new ContratoNovedadCobroDAO();
		ContratoNovedadCobro oContratoNovedadCobro = oDAO.findById(new Integer(id));
		
		oContratoNovedadCobro = completarDatosImpresion(oContratoNovedadCobro);
		
		return oContratoNovedadCobro;
		
	}
	
	
	@SuppressWarnings("unchecked")
	public static ContratoNovedadCobro completarDatosImpresion(ContratoNovedadCobro oNovedad) throws Exception {
		
		//System.out.println("ContratoNovedadCobroProcesos.completarDatosImpresion: " + oNovedad.getMonto() + " - " + oNovedad.getPeriodoCuota());
		System.out.println("ContratoNovedadCobroProcesos.completarDatosImpresion: ");
		
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
	 * Graba una novedad de cobro (INQUILINO)
	 * Se lo tengo que detallar en el recibo que va para el inquilino (Liquidación Cobro)
	 * 
	 * @param oContrato
	 * @param oPersona (Inquilino)
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
		
		System.out.println("ContratoNovedadCobroProcesos.grabarNovedad: ");
		
		// Reglas de NEGOCIO
		
		/** 
		 * 1º Como valido unicidad, por ejemplo si ya ingresé el impuesto 
		 * 2º Tengo que validar que el periodo ya no este liquidado para este CONTRATO
		 * 3º Validar la unicidad del impuesto (id_impuesto, cuota, anio)
		 * 
		 * **/
		
		
		
		/* 	Grabo */
			
		ContratoNovedadCobro oNovedadCobro = new ContratoNovedadCobro();
			
		oNovedadCobro.setContrato(oContrato);
		oNovedadCobro.setPersona(oPersona);
		
		oNovedadCobro.setContratoCuota(contrato_cuota);
		oNovedadCobro.setFechaVencimiento(fecha_vencimiento);
		oNovedadCobro.setMonto(monto);
		oNovedadCobro.setPeriodoAnio(periodo_anio);
		oNovedadCobro.setPeriodoMes(periodo_mes);
		oNovedadCobro.setIdNovedadTipo(novedad_tipo);
		oNovedadCobro.setObservaciones(observaciones);
		
		oNovedadCobro.setImpuestoId(id_impuesto);
		oNovedadCobro.setImpuestoCuota(impuesto_cuota);
		oNovedadCobro.setImpuestoAnio(impuesto_anio);
		
		ContratoNovedadCobroDAO.save(oNovedadCobro);
		
	} // Fin grabarNovedad
	
	/**
	 * Actualiza el valor de una cuota del alquiler para el INQUILINO
	 * @param oContrato
	 * @param contrato_cuota
	 * @param monto
	 */
	public static void actualizarMontoCuota(Contrato oContrato, short contrato_cuota, double monto) {
		
		System.out.println("ContratoNovedadCobroProcesos.actualizarMontoCuota: " + oContrato + " / " + contrato_cuota);
		
		List<ContratoNovedadCobro> lContratoNovedadCobro = ContratoNovedadCobroDAO.findByContratoCuota(oContrato, contrato_cuota, NovedadTipo.Alquiler);
		
		ContratoNovedadCobro oNovedadCobro = lContratoNovedadCobro.get(0);
		oNovedadCobro.setMonto(monto);
		
		ContratoNovedadCobroDAO.update(oNovedadCobro);
		
	}
	
	
} //  Fin clase