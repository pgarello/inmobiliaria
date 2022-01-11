package datos.contrato;

import java.util.HashSet;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import app.beans.Cuota;
import app.beans.NovedadTipo;
import app.beans.Utiles;

import datos.Page;
import datos.SessionFactory;
import datos.contrato_actor.*;
import datos.contrato_novedad_cobro.ContratoNovedadCobro;
import datos.contrato_novedad_cobro.ContratoNovedadCobroDAO;
import datos.contrato_novedad_cobro.ContratoNovedadCobroProcesos;
import datos.contrato_novedad_factura.ContratoNovedadFactura;
import datos.contrato_novedad_factura.ContratoNovedadFacturaDAO;
import datos.contrato_novedad_factura.ContratoNovedadFacturaProcesos;
import datos.contrato_novedad_pago.ContratoNovedadPago;
import datos.contrato_novedad_pago.ContratoNovedadPagoDAO;
import datos.contrato_novedad_pago.ContratoNovedadPagoProcesos;
import datos.inmueble.Inmueble;
import datos.persona.Persona;
import framework.ui.generales.exception.ReglasDeNegocioException;

public class ContratoProcesos {

	/**
	 * Busca los datos de Contrato
	 * @param filtro_vigente true: si busco los contratos vigentes a la fecha
	 * @param filtro_inmueble el id del inmueble
	 * @param filtro_inquilino el id de la persona
	 * @param filtro_propietario el id de la persona
	 * @param page_number primera página 0 (cero)
	 * @param page_size
	 * @param fecha_desde Fecha desde para el inicio del CONTRATO
	 * @param fecha_hasta Fecha hasta para el fin del CONTRATO
	 * @param fecha_desdeVencimiento Fecha desde para el vencimiento de una cuota en particular del CONTRATO
	 * @param fecha_hastaVencimiento Fecha hasta para el vencimiento de una cuota en particular del CONTRATO
	 * @param cuota_vencimiento Cuota a evaluar cuando vence
	 * @return Page<Contrato>
	 */
	public static Page findByFilter(boolean filtro_vigente,
									int filtro_inmueble,
									int filtro_inquilino,
									int filtro_propietario,
									int page_number, 
									int page_size,
									Date filtro_fecha_desde,
									Date filtro_fecha_hasta,
									boolean filtro_rescindido,
									boolean filtro_comercial,
									Date filtro_fecha_desdeVencimiento,
									Date filtro_fecha_hastaVencimiento,
									int cuota_vencimiento) {
			
		Session oSessionH = SessionFactory.currentSession();
		
		Page pagina;
		try {
			
			String joinCuotas = ", ContratoNovedadCobro AS model2 ";
			//if ()
			
			String queryString = 	"SELECT DISTINCT model " +
									"FROM Contrato AS model " + joinCuotas +
									"	JOIN model.contratoActors AS actorInquilino " +
									"		WITH actorInquilino.id.idActorTipo = " + ContratoActor.ActorTipoInquilino + " " + 
									"	JOIN model.contratoActors AS actorPropietarios " +
									"		WITH actorPropietarios.id.idActorTipo = " + ContratoActor.ActorTipoPropietario + " " +
									"WHERE model.idContrato IS NOT NULL " +
									"AND model = model2.contrato ";
									//"AND UPPER(model.direccionEdificio) LIKE :edificioValue ";
			
			if (filtro_inmueble != 0) queryString += " AND model.inmueble.idInmueble = " + filtro_inmueble;
			if (filtro_inquilino != 0) queryString += " AND actorInquilino.id.persona.idPersona = " + filtro_inquilino;
			if (filtro_propietario != 0) queryString += " AND actorPropietarios.id.persona.idPersona = " + filtro_propietario;
			//if (filtro_propietario != 0) queryString += " AND model.inmueble.propietario.idPersona = " + filtro_propietario;
			
			/** Se agrego la fecha de rescision ... ahora puede que este vigente, pero hay que evaluar esta fecha */
			if (filtro_vigente) queryString += " AND now() BETWEEN model.fechaDesde AND model.fechaHasta " +
											   " AND now() <= COALESCE(model.fechaRescision, now()) ";
			
			if (filtro_rescindido) queryString += " AND model.fechaRescision IS NOT NULL ";
			if (filtro_comercial) queryString += " AND model.comercial IS TRUE ";
			
			if (filtro_fecha_desde != null) queryString += " AND model.fechaDesde >= :fechaDesde ";
			if (filtro_fecha_hasta != null) queryString += " AND model.fechaHasta <= :fechaHasta ";
			
			if (filtro_fecha_desdeVencimiento != null) queryString += " AND model2.fechaVencimiento >= :fechaDesdeV";													   
			if (filtro_fecha_hastaVencimiento != null) queryString += " AND model2.fechaVencimiento <= :fechaHastaV";
			if (cuota_vencimiento != 0) queryString += " AND model2.contratoCuota = " + cuota_vencimiento;
			
			queryString+= " ORDER BY model.fechaDesde ";
			
			//filtro_calle += "%";			
			
			Query oQuery = oSessionH.createQuery(queryString);
			
			if (filtro_fecha_desde != null) oQuery.setDate("fechaDesde", filtro_fecha_desde);
			if (filtro_fecha_hasta != null) oQuery.setDate("fechaHasta", filtro_fecha_hasta);

			if (filtro_fecha_desdeVencimiento != null) oQuery.setDate("fechaDesdeV", filtro_fecha_desdeVencimiento);													   
			if (filtro_fecha_hastaVencimiento != null) oQuery.setDate("fechaHastaV", filtro_fecha_hastaVencimiento);			

			
			//System.out.println("Query " + oQuery.getQueryString());
			
			pagina = new Page(oQuery, page_number, page_size);
			
		} catch (RuntimeException re) {
			throw re;
		} finally {
			oSessionH.close();
		}		
		
		return pagina;
	} 
	
	
	/**
	 * Busca los datos de los Contratos por VENCER. La fecha de finalización está en el mes actual o en el siguiente
	 * @return List<Contrato>
	 */
	@SuppressWarnings("unchecked")
	public static List<Contrato> findPorVencer() {
			
		Session oSessionH = SessionFactory.currentSession();
						
		System.out.print("ContratoProcess.findPorVencer");
		
		List<Contrato> lista_datos = new Vector<Contrato>();
		List<Contrato> lista_datosAProcesar = new Vector<Contrato>();
		
		/* 	
		 	Necesito saber si el vencimiento del contrato (fecha_hasta) no cae en el mes actual ¿?
			a - Puedo comparar la fecha solamente el mes y año (SQL)
			b - Que la fechaHasta > now() día 1 y fecha_hasta < now() mas 1 mes, dia 1 (JAVA)
				Le agrego el mes siguiente.
		*/
		Calendar primerDiaMes = Calendar.getInstance();
		primerDiaMes.set(Calendar.DATE, 1);
		
		Calendar primerDiaMesSiguiente = (Calendar)primerDiaMes.clone();
		primerDiaMesSiguiente.add(Calendar.MONTH, 2);
		
		
		try {
			String queryString = 	"SELECT model " +
									"FROM Contrato AS model " +
									"WHERE model.idContrato IS NOT NULL " +
									" AND now() BETWEEN model.fechaDesde AND model.fechaHasta " + // VIGENTES
									" AND now() <= COALESCE(model.fechaRescision, now()) " + // NO RESCINDIDO
									"" ;
			
			queryString+= " ORDER BY model.fechaHasta ";
			
			Query oQuery = oSessionH.createQuery(queryString);			
			lista_datosAProcesar = oQuery.list();
			
		} catch (RuntimeException re) {
			throw re;
		} finally {
			oSessionH.close();
		}		
		
		
		// Recorro los datos y filtro
		for (Contrato oContrato : lista_datosAProcesar) {			
			if(	oContrato.getFechaHasta().after(primerDiaMes.getTime()) &&
				oContrato.getFechaHasta().before(primerDiaMesSiguiente.getTime() ) ) {
				
				lista_datos.add(oContrato);
			}			
		}
		
		System.out.println(" cantidad:" + lista_datos.size());
		
		return lista_datos;
	} 
	
	
	
	/**
	 * Se usa en la grilla que muestra los datos de los contratos, es una vista parcial de los datos
	 * se completa con la dirección del inmueble y los actores
	 * @param lista_a_completar
	 * @return
	 * @throws Exception
	 */
	public static List<Contrato> completar(List<Contrato> lista_a_completar) throws Exception {
		
		List<Contrato> lista_completa = new Vector<Contrato>();
		
		Session oSessionH = SessionFactory.currentSession();
		
		try {
			
			for(Contrato oContrato: lista_a_completar) {
				
				oSessionH.refresh(oContrato);
				
				oContrato.getInmueble().getDireccion_completa();
				
				Set<ContratoActor> actores = oContrato.getContratoActors();
				for(ContratoActor oActor: actores) {
					oActor.getId().getPersona().getDescripcion();
				}
				
				lista_completa.add(oContrato);
				
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
	
	
	/**
	 * Actualiza las cuotas de un CONTRATO, a partir del 2021 los contratos se actualizan según un porcentaje
	 * @param oContrato
	 * @param vCuotas
	 * @throws ReglasDeNegocioException
	 */
	public static void actualizarCuotasContrato(Contrato oContrato, List<Cuota> vCuotas) throws ReglasDeNegocioException {
		
		System.out.println("ContratoProcess.actualizarCuotasContrato " + oContrato);
		
		// Debería chequear que el contrato no esté vencido !!!
		Calendar hoy = Calendar.getInstance();
		if (oContrato.getFechaHasta().before(hoy.getTime()) ) {
			//System.out.println("ContratoProcess.actualizarCuotasContrato fechaHasta:" + oContrato.getFechaHasta().toLocaleString() + "//" + hoy.getTime().toLocaleString());
			if (oContrato.getFechaExtension() != null) {
				if (oContrato.getFechaExtension().before(hoy.getTime()) ) {
					// Error
					throw new ReglasDeNegocioException("A un contrato VENCIDO/EXTENDIDO no se le puede modificar las cuotas");
				}
			} else {
				// Error
				throw new ReglasDeNegocioException("A un contrato VENCIDO no se le puede modificar las cuotas");
			}
		}
		
		if (oContrato.getFechaRescision() != null)
			if (oContrato.getFechaRescision().before(hoy.getTime()) ) {
				// Error
				throw new ReglasDeNegocioException("A un contrato RESCINDIDO no se le puede modificar las cuotas");			
			}
		
		// Reseteo la colección para que busque en la base de datos nuevamente
		oContrato.limpiarListaCuotas();
		
		// Pasé reglas de negocio
		for (Cuota oCuota: vCuotas) {
			
			/* Tengo que recorrer todas las cuotas y evaluar el monto si se modificó, en ese caso chequear las 
			 * reglas de negocio y si se cumplen modificar el monto de la cuota */						
			
			short cuota_numero = oCuota.getCuota();
			double monto_cuota = oCuota.getValor();									
			
			/* Busco la cuota en el contrato, debo saber si está paga 
			 * NO ES OPTIMO, RECORRE LA COLECCIÓN POR CADA UNA DE LAS CUOTAS */
			Cuota oCuotaContrato = oContrato.getCuotaxNumero(cuota_numero);
						
//			System.out.println("ContratoProcess.actualizarCuotasContrato cuota_numero:" + cuota_numero);
//			System.out.println("ContratoProcess.actualizarCuotasContrato monto_cuota:" + monto_cuota);
//			System.out.println("ContratoProcess.actualizarCuotasContrato oCuotaContrato.getValor():" + oCuotaContrato.getValor());
//			System.out.println("ContratoProcess.actualizarCuotasContrato oCuotaContrato.getSaldo():" + oCuotaContrato.getSaldo());
//			System.out.println("ContratoProcess.actualizarCuotasContrato oCuotaContrato.getPagado():" + oCuotaContrato.getPagado());
			
			if (oCuotaContrato.getValor() != monto_cuota) {
				
				// valido que no se haya cobrado y tampoco pagado
//				System.out.println("******************************************************");
//				System.out.println("ContratoProcess.actualizarCuotasContrato MODIFICAR cuota_numero:" + cuota_numero);
//				System.out.println("******************************************************");
				
				// Si el SALDO != MONTO CUOTA => se cobró algo
				// Si PAGADO es != 0 => ya se pagó algo al propietario
				if ( oCuotaContrato.yaSeCobroAlgoINQUILINO()|| oCuotaContrato.yaSePagoAlgoPROPIETARIO() ) {
					System.out.println("ContratoProcess.actualizarCuotasContrato yaSePagoAlgoPROPIETARIO:" + cuota_numero);
					throw new ReglasDeNegocioException("Se modificaron CUOTAS ya cobradas y/o ya pagadas");	
				}

				// grabo
				// Tengo que grabar en:
				// ContratoNovedadCobroProcesos
				// ContratoNovedadPagoProcesos
				ContratoNovedadCobroProcesos.actualizarMontoCuota(oContrato, cuota_numero, monto_cuota);
				ContratoNovedadPagoProcesos.actualizarMontoCuota(oContrato, cuota_numero, monto_cuota);
			}
			
		} // Fin for
		
	}
	
	
	/**
	 * Es el alta de un contrato
	 * Tiene que grabar en todas las tablas relacionadas
	 */	
	@SuppressWarnings({ "unchecked", "deprecation" })
	public static void save(Contrato oContrato, Persona oInquilino, List<Cuota> vCuotas) throws ReglasDeNegocioException {
		
		/** 
		 * Reglas de negocio
		 * 1º No se pueden solapar dos contratos sobre la misma propiedad
		 * 2° La diferencia entre las fechas desde y hasta (en meses) tiene que ser igual a la cantidad de cuotas
		 */
		
		// REGLA 1°
		Inmueble oInmueble = oContrato.getInmueble();
		Page pagina = findByFilter(true,oInmueble.getIdInmueble(), 0, 0, 0, 1, null, null, false, false, null, null, 0);
		List<Contrato> lista = pagina.getList();
		if(lista.size() > 0) {
			
			// valido las fechas para ver si existe solapamiento
			// ojo con la fecha de extención del CONTRATO
			Contrato oContratoVigente = lista.get(0);
			//System.out.println("Control de Fechas: " + oContratoVigente.getFechaHasta() + " - " + oContrato.getFechaDesde());
			if(oContratoVigente.getFechaHasta().after(oContrato.getFechaDesde())) {
				
				if (oContratoVigente.getFechaRescision() == null) {								
					// Error
					throw new ReglasDeNegocioException("Inmueble con CONTRATO vigente");				
				} else if (oContratoVigente.getFechaRescision().after(oContrato.getFechaDesde()) ) {
					// Error
					throw new ReglasDeNegocioException("Inmueble con CONTRATO vigente para esas fechas");
				}				
			}
		}

		// REGLA 2°
		Calendar fechaDesde = Calendar.getInstance();
		fechaDesde.setTime(oContrato.getFechaDesde());
		
		Calendar fechaHasta = Calendar.getInstance();
		fechaHasta.setTime(oContrato.getFechaHasta());
		
		short diferencia_en_meses = Utiles.diferenciaEnMeses(fechaDesde, fechaHasta);
		short cantidad_de_cuotas = oContrato.getCantidadCuota();
		
		if (diferencia_en_meses != cantidad_de_cuotas) {
			// Error
			throw new ReglasDeNegocioException("Las fechas desde y hasta no condicen con la cantidad de cuotas");
		}
		
		
		
		// Manejo de la transacción global
		Session oSessionH = SessionFactory.currentSession();
		Transaction tx = null;
		
		try {
			
			tx = oSessionH.beginTransaction();		
			
		/**
		 * Voy grabando
		 */
		
   		// Tengo que armar los datos de la tabla contrato_actor
		// Inquilino y Propietario, luego hacer un set (coleccion) y pasarcelo al objeto Contrato
		Set<ContratoActor> sContratoActor = new HashSet<ContratoActor>();
		
		ContratoActorId oContratoIdInquilino = new ContratoActorId(oContrato, ContratoActor.ActorTipoInquilino ,oInquilino);
		ContratoActor oCA_Inquilino = new ContratoActor(oContratoIdInquilino);		
		sContratoActor.add(oCA_Inquilino);
		
		Persona oPropietario = oContrato.getInmueble().getPropietario();
		ContratoActorId oContratoIdPropietario = new ContratoActorId(oContrato, ContratoActor.ActorTipoPropietario ,oPropietario);
		ContratoActor oCA_Propietario = new ContratoActor(oContratoIdPropietario);		
		sContratoActor.add(oCA_Propietario);
		
		oContrato.setContratoActors(sContratoActor);
		
		
		// Grabo el Contrato		
   		ContratoFacade.save(oContrato);
		
		// Grabo en ContratoActor 		
   		ContratoActorFacade.save(oCA_Inquilino);
   		ContratoActorFacade.save(oCA_Propietario);
   		
		/* 	
		 Genero la tabla de novedades 
		 1 - Liquidación PAGO - ALQUILER (Propietario) 1 x cuota
		 2 - Liquidación PAGO - COMISION ALQUILER (Propietario) 1 x cuota
		 3 - Liquidación FACTURA - COMISION ALQUILER (Propietario) 1 x cuota ()
		 4 - Liquidación COBRO - ALQUILER (Inquilino) 1 x cuota
		 5 - Liquidación COBRO - COMISION CONTRATO (Inquilino) 1 x contrato --> NUEVO
		 6 - Liquidación FACTURA - COMISION CONTRATO (Inquilino) 1 x contrato
		 */
   		
   			for (Cuota oCuota: vCuotas) {
   				
   				/** 1º Liquidación PAGO - Propietario */
   				
   				// MOVIMIENTO ALQUILER
   				ContratoNovedadPago oNovedadPago = new ContratoNovedadPago();
   				
   				oNovedadPago.setContrato(oContrato);
   				oNovedadPago.setPersona(oPropietario);
   				
   				oNovedadPago.setContratoCuota(oCuota.getCuota());
   				//oNovedadPago.setFechaVencimiento(oCuota.getFecha_vencimiento()); // no es necesaria
   				oNovedadPago.setMonto(oCuota.getValor());
   				oNovedadPago.setPeriodoAnio(oCuota.getPeriodo_anio());
   				oNovedadPago.setPeriodoMes(oCuota.getPeriodo_mes());
   				oNovedadPago.setIdNovedadTipo((short) NovedadTipo.Alquiler);
   				
   				/* La fecha de liquidación es el 15 de cada mes, correspondiente al periodo 
   				 * le cambio el día a la fecha de vencimiento que es el 10 de cada mes, si es mayor, entonces uso esa fecha 
   				 * ESTO ES PARA EL COMPROBANTE DEL PROPIETARIO */
   				Date fechaVencimiento = (Date) oCuota.getFecha_vencimiento().clone();
   				
   				Date fechaLiquidacion = fechaVencimiento;
   				if (fechaVencimiento.getDate() == 10) {
   					fechaLiquidacion.setDate(15);
   				}
   				
   				Date fechaFacturacion = (Date) oCuota.getFecha_vencimiento().clone();
   				fechaFacturacion.setDate(1);
   				   				
   				oNovedadPago.setFechaLiquidacion(fechaLiquidacion);
   				ContratoNovedadPagoDAO.save(oNovedadPago);
   				
   				// MOVIMIENTO COMISION ALQUILER ... es un nuevo movimiento --> agrego el IVA sobre la comisión
   				/** VALIDAR QUE SI EL MONTO ES 0 NO GRABAR ??? */
   				   				
   				double monto_comision = 0;
   				if (oContrato.getComisionPropFija() > 0) {
   					monto_comision = oContrato.getComisionPropFija();
   				} else if (oContrato.getComisionPropPorc() > 0) {
   					monto_comision = (oCuota.getValor() * oContrato.getComisionPropPorc()) / 100;
   				}
   				
   				if (monto_comision > 0) {
	   				ContratoNovedadPago oNovedadPago2 = new ContratoNovedadPago();
	   				
	   				oNovedadPago2.setContrato(oContrato);
	   				oNovedadPago2.setPersona(oPropietario);   				
	   				oNovedadPago2.setContratoCuota(oCuota.getCuota());
	   				oNovedadPago2.setMonto(-monto_comision);
	   				
	   				oNovedadPago2.setPeriodoAnio(oCuota.getPeriodo_anio());
	   				oNovedadPago2.setPeriodoMes(oCuota.getPeriodo_mes());
	   				oNovedadPago2.setIdNovedadTipo((short) NovedadTipo.ComisionAlquiler);
	   				oNovedadPago2.setFechaLiquidacion(fechaLiquidacion);
	   				
	   				ContratoNovedadPagoDAO.save(oNovedadPago2);	   				
	   				
   				}
   				
   				/** 2º Liquidación FACTURA - PROPIETARIO (Lo que ingresa) 
   				 * ahora se liquida cuando se le paga al propietario, es por el IVA q no se puede determinar de antemano */
   				/*
   				ContratoNovedadFactura oNovedadFactura = new ContratoNovedadFactura();
   				
   				oNovedadFactura.setContrato(oContrato);
   				oNovedadFactura.setPersona(oPropietario);
   				
   				oNovedadFactura.setContratoCuota(oCuota.getCuota());
   				oNovedadFactura.setFechaVencimiento(fechaFacturacion);
   				oNovedadFactura.setMonto(monto_comision);
   				oNovedadFactura.setPeriodoAnio(oCuota.getPeriodo_anio());
   				oNovedadFactura.setPeriodoMes(oCuota.getPeriodo_mes());
   				oNovedadFactura.setIdNovedadTipo((short) NovedadTipo.ComisionAlquiler);
   				   				
   				ContratoNovedadFacturaDAO.save(oNovedadFactura);
   				*/
   				
   				
   				/** 3º Liquidación COBRO - Inquilino */
   				ContratoNovedadCobro oNovedadCobro = new ContratoNovedadCobro();
   				
   				oNovedadCobro.setContrato(oContrato);
   				oNovedadCobro.setPersona(oInquilino);
   				
   				oNovedadCobro.setContratoCuota(oCuota.getCuota());
   				oNovedadCobro.setFechaVencimiento(oCuota.getFecha_vencimiento());
   				oNovedadCobro.setMonto(oCuota.getValor());
   				oNovedadCobro.setPeriodoAnio(oCuota.getPeriodo_anio());
   				oNovedadCobro.setPeriodoMes(oCuota.getPeriodo_mes());
   				oNovedadCobro.setIdNovedadTipo((short) NovedadTipo.Alquiler);
   				
   				ContratoNovedadCobroDAO.save(oNovedadCobro);
   				
   				// Movimiento COMISION CONTRATO (va solo en la cuota 1) -- VALIDO QUE NO SEA CERO
   				if (oCuota.getCuota() == 1 && oContrato.getComisionInquilino() > 0) {
   					// COMISION
	   				ContratoNovedadCobro oNovedadCobro2 = new ContratoNovedadCobro();
	   				
	   				oNovedadCobro2.setContrato(oContrato);
	   				oNovedadCobro2.setPersona(oInquilino);
	   				
	   				oNovedadCobro2.setContratoCuota(oCuota.getCuota());
	   				oNovedadCobro2.setFechaVencimiento(oCuota.getFecha_vencimiento());
	   				oNovedadCobro2.setMonto(oContrato.getComisionInquilino());
	   				oNovedadCobro2.setPeriodoAnio(oCuota.getPeriodo_anio());
	   				oNovedadCobro2.setPeriodoMes(oCuota.getPeriodo_mes());
	   				oNovedadCobro2.setIdNovedadTipo((short) NovedadTipo.ComisionContrato);
	   				
	   				ContratoNovedadCobroDAO.save(oNovedadCobro2);
	   				
   				}
   				
   			} // Fin FOR
   		
   			/** 4º Liquidación FACTURA - INQUILINO 
   			 *  Al igual q la comisión que le cobro al propietario, la comisión al inquilino se factura cuando 
   			 *  va pagando, asi puedo calcularle el IVA 
   			 *  (14/08/2013)ojo AHORA le tengo que ir cobrando el IVA en la liquidación COBRANZA */
   			/*
   			ContratoNovedadFactura oNovedadFactura = new ContratoNovedadFactura();
   			
   			oNovedadFactura.setContrato(oContrato);
			oNovedadFactura.setPersona(oInquilino);
				
			oNovedadFactura.setFechaVencimiento(oContrato.getFechaDesde());
			oNovedadFactura.setMonto(oContrato.getComisionInquilino());
			oNovedadFactura.setIdNovedadTipo((short) NovedadTipo.ComisionContrato);
				   				
			ContratoNovedadFacturaDAO.save(oNovedadFactura);
			*/
   		
   		// Fin grabación de novedades --------------------------------
			
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
	
	
	
	
	
	public static void rescindirContrato(Contrato oContrato, float monto, Date fecha) throws ReglasDeNegocioException {
		
		/**
		 * Dado un contrato
		 * a. actualizar el valor de la fecha de rescisión
		 * b. generar una novedad de cobro por la rescisión
		 * c. borrar las novedades de cobro superiores a la fecha de rescisión
		 * d. borrar las novedades de pago superiores a la fecha de rescisión
		 */
		
		/**
		 * Reglas de NEGOCIO
		 * a. Que haya un recibo de cobro de un periodo o cuota superior a la fecha de rescisión
		 * b. Que haya un recibo de pago de un periodo o cuota superior a la fecha de rescisión
		 */
		
		/* Regla de NEGOCIO a. ---------------------------------------------------------- */
		List<ContratoNovedadCobro> dataListCobro = ContratoNovedadCobroProcesos.buscarNovedadesCobroPorContrato(oContrato, false, false);
		Iterator<ContratoNovedadCobro> iCobros = dataListCobro.iterator();
		boolean cumple_regla_a = true;
		while(iCobros.hasNext() && cumple_regla_a) {
			ContratoNovedadCobro oCobro = iCobros.next();
			//System.out.println("Cobro: " + oCobro.getSaldo() + "-" + oCobro.getMonto() + "-" + oCobro.getIdNovedadTipo());
			if(!oCobro.getSaldo().equals(oCobro.getMonto())) {
				// Tengo algun cobro
				//System.out.println("FECHAS : " + oCobro.getFechaVencimiento().after(fecha));
				if (oCobro.getFechaVencimiento().after(fecha) ) {
					// La fecha de vencimiento es posterior a la fecha de RESCISION
					cumple_regla_a = false;
				}
			}
		}		
		if (!cumple_regla_a) {
			throw new ReglasDeNegocioException("Existen COBROS realizados con fecha posterior a la fecha de RESCISION.");
		}
		
		/* Regla de NEGOCIO b. ---------------------------------------------------------- */
		List<ContratoNovedadPago> dataListPago = ContratoNovedadPagoProcesos.buscarNovedadesPagoPorContrato(oContrato, false, false, (short)0, (short)0);
		Iterator<ContratoNovedadPago> iPagos = dataListPago.iterator();
		boolean cumple_regla_b = true;
		while(iPagos.hasNext() && cumple_regla_b) {
			ContratoNovedadPago oPago = iPagos.next();
			//System.out.println("Cobro: " + oCobro.getSaldo() + "-" + oCobro.getMonto() + "-" + oCobro.getIdNovedadTipo());
			if(!oPago.getSaldo().equals(oPago.getMonto())) {
				// Tengo algun cobro
				//System.out.println("FECHAS : " + oCobro.getFechaVencimiento().after(fecha));
				if (oPago.getFechaLiquidacion().after(fecha) ) {
					// La fecha de vencimiento es posterior a la fecha de RESCISION
					cumple_regla_b = false;
				}
			}
		}		
		if (!cumple_regla_b) {
			throw new ReglasDeNegocioException("Existen PAGOS realizados con fecha posterior a la fecha de RESCISION.");
		}
		
		// PROCESOOOOO
		// Manejo de la transacción global
		Session oSessionH = SessionFactory.currentSession();
		Transaction tx = null;
		
		try {
			
			tx = oSessionH.beginTransaction();
			
			/** c. borrar las novedades de cobro superiores a la fecha de rescisión */
			List<ContratoNovedadCobro> dataListCobro2 = ContratoNovedadCobroProcesos.buscarNovedadesCobroPorContrato(oContrato, false, false);
			for(ContratoNovedadCobro oCobro2 : dataListCobro2){
				if (oCobro2.getFechaVencimiento().after(fecha)) {
					ContratoNovedadCobroDAO.delete(oCobro2);
				}
			}
			
			/** d. borrar las novedades de pago superiores a la fecha de rescisión */
			List<ContratoNovedadPago> dataListPago2 = ContratoNovedadPagoProcesos.buscarNovedadesPagoPorContrato(oContrato, false, false, (short)0, (short)0);
			for(ContratoNovedadPago oPago2 : dataListPago2){
				if (oPago2.getFechaLiquidacion().after(fecha)) {
					ContratoNovedadPagoDAO.delete(oPago2);
				}
			}
			
			/** b. generar una novedad de cobro por la rescisión */
			if (monto > 0) {
				ContratoNovedadCobro oNovedadCobro = new ContratoNovedadCobro();
				
				oNovedadCobro.setContrato(oContrato);
				oNovedadCobro.setPersona(oContrato.getInquilino());					
				oNovedadCobro.setContratoCuota((short)0);
				oNovedadCobro.setFechaVencimiento(fecha);
				oNovedadCobro.setMonto((double)monto);
				oNovedadCobro.setIdNovedadTipo((short) NovedadTipo.ComisionRescision);
				
				Calendar cFecha = Calendar.getInstance();
				cFecha.setTime(fecha);				
				oNovedadCobro.setPeriodoAnio((short)cFecha.get(Calendar.YEAR));
				oNovedadCobro.setPeriodoMes((short)cFecha.get(Calendar.MONTH));
								
				ContratoNovedadCobroDAO.save(oNovedadCobro);
			}
			
			/** a. actualizar el valor de la fecha de rescisión */
			oContrato.setFechaRescision(fecha);
			ContratoFacade.update(oContrato);
			
			// Fin grabación de novedades --------------------------------			
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
	
	
	public static void modificarContrato(Contrato oContrato, Persona oPropietario) throws ReglasDeNegocioException {
		
		/**
		 * Dado un contrato
		 * a. por ahora lo que vamos a modificar el el PROPIETARIO
		 */
		
		/**
		 * Reglas de NEGOCIO
		 * a. Para realizar la modificación valido que el contrato esté vigente
		 */
		
		/* Regla de NEGOCIO a. ---------------------------------------------------------- */
		
		
		
		
		
		// PROCESOOOOO
		// Manejo de la transacción global --> NO ES NECESARIO POR QUE NO GRABAMOS NINGÚN DATO !!!
		Session oSessionH = SessionFactory.currentSession();
		Transaction tx = null;
		
		try {
			
			//tx = oSessionH.beginTransaction();
			
			oSessionH.refresh(oContrato);
			
			/** a. Al PROPIETARIO anterior le seteo la fecha de baja a hoy */
			Set<ContratoActor> cActores = oContrato.getContratoActors();
			for (ContratoActor obj : cActores) {
				if (obj.getId().getIdActorTipo() == ContratoActor.ActorTipoPropietario) {
					obj.setFechaBaja(new Date());
				}
			}
			
			/** b. Grabar el nuevo actor el en contrato */
			ContratoActorId oContratoIdPropietario = new ContratoActorId(oContrato, ContratoActor.ActorTipoPropietario ,oPropietario);
			ContratoActor oCA_Propietario = new ContratoActor(oContratoIdPropietario);

			// GRABO EL NUEVO ACTOR EN EL CONTRATO
			ContratoActorFacade.save(oCA_Propietario);
			
			// MANTENGO ACTUALIZADA LA COLECCIÓN
			cActores.add(oCA_Propietario);
			
			// GRABO LOS DATOS DEL CONTRATO MODIFICADOS -- Podría hacer el update directamente sobre CONTRATO_ACTOR
			ContratoFacade.update(oContrato);
			
			
			// Ahora tengo que modificar las NOVEDADES DE PAGO pendientes para que estén asociadas al nuevo PROPIETARIO
			List<ContratoNovedadPago> dataListPago2 = ContratoNovedadPagoProcesos.buscarNovedadesPagoPorContrato(oContrato, false, false, (short)0, (short)0);
			for(ContratoNovedadPago oPago : dataListPago2){
				
				if (oPago.getNoLiquidada()) {
					oPago.setPersona(oPropietario);
					ContratoNovedadPagoDAO.save(oPago);					
				}
			}
			
			
			// Fin grabación de novedades --------------------------------			
			// tx.commit();
			
		} catch (Exception e) {
			
			e.printStackTrace();
			//tx.rollback();
			throw new ReglasDeNegocioException(e);
			
		} finally { 
			// dio error de que la session ya estaba cerrada
			try{oSessionH.close();} catch(Exception e) {}
		}
		
	}
	
	
	
	public static void delete(Contrato oContrato) throws ReglasDeNegocioException {
		
		/* 
		 * Reglas de Negocio 
		 * 1º Que no haya cobrado, ni pagado ningún periodo -- Que no tenga movimientos el CONTRATO
		 */
		
		List<ContratoNovedadCobro> cNovedadCobro = ContratoNovedadCobroProcesos.buscarNovedadesCobroPorContrato(oContrato, false, false);
		
		boolean tiene_algun_movimiento_COBRO = false;

		Iterator<ContratoNovedadCobro> iNovedadCobro = cNovedadCobro.iterator();
		while(iNovedadCobro.hasNext() && !tiene_algun_movimiento_COBRO) {
			ContratoNovedadCobro oNovedadCobro = iNovedadCobro.next();
			
			if (!oNovedadCobro.getSaldo().equals(oNovedadCobro.getMonto())) {
				tiene_algun_movimiento_COBRO = true;
			}
		}
		
		// ------------------------------------------------------------------------
		
		List<ContratoNovedadPago> cNovedadPago = new ArrayList<ContratoNovedadPago>();
		boolean tiene_algun_movimiento_PAGO = false;
		if (!tiene_algun_movimiento_COBRO) {
			short mes = 0, anio = 0;
			cNovedadPago = ContratoNovedadPagoProcesos.buscarNovedadesPagoPorContrato(oContrato, false, false, mes, anio);
			
			Iterator<ContratoNovedadPago> iNovedadPago = cNovedadPago.iterator();
			while(iNovedadPago.hasNext() && !tiene_algun_movimiento_PAGO) {
				ContratoNovedadPago oNovedadPago = iNovedadPago.next();
				
				if (!oNovedadPago.getSaldo().equals(oNovedadPago.getMonto())) {
					tiene_algun_movimiento_PAGO = true;
				}
			}
			
		}
		
		// ------------------------------------------------------------------------
		
		List<ContratoNovedadFactura> cNovedadFactura = new ArrayList<ContratoNovedadFactura>();
		@SuppressWarnings("unused")
		boolean tiene_algun_movimiento_FACTURA = false;
		if (!tiene_algun_movimiento_COBRO || !tiene_algun_movimiento_PAGO) {
			cNovedadFactura = ContratoNovedadFacturaProcesos.buscarNovedadesPorContrato(oContrato);
			
			/** POR AHORA NO SE EMITE ESTE FORMULARIO - FACTURA */
			
		}
		
		
		// ------------------------------------------------------------------------
		
		if (!tiene_algun_movimiento_COBRO && !tiene_algun_movimiento_PAGO) {
			
			// Manejo de la transacción global
			Session oSessionH = SessionFactory.currentSession();
			Transaction tx = null;
			
			try {
			
				tx = oSessionH.beginTransaction();		
				
				// puedo borrar
				System.out.println("SE PUEDE BORRAR EL CONTRATO");
				
				/** 1º Borro los PAGOS */
				for (ContratoNovedadPago oNovedadPago : cNovedadPago) {				
					ContratoNovedadPagoDAO.delete(oNovedadPago);		
				}
							
				/** 2º Borro los COBROS */
				for (ContratoNovedadCobro oNovedadCobro : cNovedadCobro) {
					ContratoNovedadCobroDAO.delete(oNovedadCobro);
				}
				
				/** 3º Borro las FACTURAS - No tendría que tener facturas emitidas ya que no COBRE nada del CONTRATO !!! ver */
				for (ContratoNovedadFactura oNovedadFactura : cNovedadFactura) {
					ContratoNovedadFacturaDAO.delete(oNovedadFactura);
				}
				
				/** 4º Borro los ACTORES */
				Set<ContratoActor> cActores = oContrato.getContratoActors();
				for (ContratoActor oActor : cActores) {
					ContratoActorFacade.delete(oActor);
				}
				
				/** 5º Borro el CONTRATO */
				ContratoFacade.delete(oContrato);
				
				tx.commit();
				
			} catch (Exception e) {
				
				e.printStackTrace();
				try{tx.rollback();} catch(Exception ex) {}
				throw new ReglasDeNegocioException(e);
				
			} finally { 
				// dio error de que la session ya estaba cerrada
				try{oSessionH.close();} catch(Exception e) {}
			}
			
			
		} else {
			System.out.println("NO!!! SE PUEDE BORRAR EL CONTRATO");
			throw new ReglasDeNegocioException("No se puede borrar el CONTRATO por que ya existen movimientos asociados (COBROS/PAGOS)");
		}
		
		return;
	}
	
	
	public static List<ContratoNovedadPago> buscarNovedadesPago(Contrato oContrato) {
		
		List<ContratoNovedadPago> lista_datos = new Vector<ContratoNovedadPago>();
		
		ContratoNovedadPagoDAO oDAO = new ContratoNovedadPagoDAO();
		lista_datos = oDAO.findByProperty("contrato", oContrato);
		
		return lista_datos;
		
	}
	
	/**
	 * Busca todos los periodos del contrato - CUOTAS
	 * @param oContrato
	 * @param vigentes
	 * @return
	 */
	public static List<Cuota> buscarPeriodosContrato(Contrato oContrato, boolean vigentes) {
		
		List<Cuota> lCuotas = new ArrayList<Cuota>();
		
		// Para buscar las cuotas del contrato recorro las NOVEDADES DE COBRO y del tipo ALQUILER
		boolean con_saldo = false;
		List<ContratoNovedadCobro> lNovedades = ContratoNovedadCobroProcesos.buscarNovedadesCobroPorContrato(oContrato, vigentes, con_saldo);
		
		for (ContratoNovedadCobro oNovedad: lNovedades) {
			
			if (oNovedad.getIdNovedadTipo() == NovedadTipo.Alquiler) {
				
				Cuota oCuota = new Cuota();
				
				oCuota.setCuota(oNovedad.getContratoCuota());
				oCuota.setPeriodo_anio(oNovedad.getPeriodoAnio());
				oCuota.setPeriodo_mes(oNovedad.getPeriodoMes());
				oCuota.setValor(oNovedad.getMonto());
				oCuota.setFecha_vencimiento(oNovedad.getFechaVencimiento());
				
				lCuotas.add(oCuota);
			}
			
		}
		
		return lCuotas;
		
	}

}