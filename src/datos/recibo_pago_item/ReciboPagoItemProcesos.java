package datos.recibo_pago_item;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Vector;

import org.hibernate.Hibernate;
import org.hibernate.Session;

import app.beans.ItemTipo;
import app.beans.NovedadTipo;
import app.combos.ComboGANANCIAS;
import app.combos.ComboImpuesto;

import datos.SessionFactory;

import datos.contrato_novedad_pago.ContratoNovedadPago;
import datos.contrato_novedad_pago.ContratoNovedadPagoProcesos;
import datos.persona.Persona;
import datos.setup.SetupDAO;

public class ReciboPagoItemProcesos {

	public static List<ReciboPagoItem> convertirLista(List<ContratoNovedadPago> lNovedad) {

		List<ReciboPagoItem> lItem = new Vector<ReciboPagoItem>();
		
		for(ContratoNovedadPago oNovedad: lNovedad) {
			
			ReciboPagoItem oItem = new ReciboPagoItem();
			
			oItem.setContratoNovedadPago(oNovedad);
			
			// Ahora al item lo lleno con el saldo
			oItem.setMonto(oNovedad.getSaldo());
			
			oItem.setIdItemTipo(oNovedad.getIdNovedadTipo());
			
			
			// Actualizo la descripción por si es un impuesto
			String descripcion = NovedadTipo.getDescripcion(oNovedad.getIdNovedadTipo());
			if (oNovedad.getIdNovedadTipo() == ItemTipo.ItemImpuestos) {
				descripcion += " " 	+ ComboImpuesto.getImpuesto(oNovedad.getImpuestoId())
									+ " (" 
									+ oNovedad.getImpuestoCuota() 
									+ " - "
									+ oNovedad.getImpuestoAnio() 
									+ ")";
			}
			oItem.setDescripcion(descripcion);
			
			
			lItem.add(oItem);
			
		}
		
		return lItem;
		
	}
	
	
	public static ReciboPagoItem completarDatos(ReciboPagoItem oReciboPagoItem) throws Exception {
		
		System.out.println("ReciboPagoItemProcesos.completarDatos: " + oReciboPagoItem.getIdReciboPagoItem());
		
		Session oSessionH = SessionFactory.currentSession();
		
		try {
			oSessionH.refresh(oReciboPagoItem);
			//Hibernate.initialize(oReciboPagoItem);
						
			/** -------------------------------------------------------- */
			/** 
			 * Hay items que no estan asociados a una novedad
			 *  
			 */
			Hibernate.initialize(oReciboPagoItem.getContratoNovedadPago());
			int id = oReciboPagoItem.getContratoNovedadPago().getIdContratoNovedadPago();
			ContratoNovedadPagoProcesos.buscarPorId(id);
			
			/** -------------------------------------------------------- */
						
			//System.out.println("CONTROL DE DATOS -------------- " + oContratoNovedadCobro.getContrato().getInmueble().getDireccion_completa());
						
		} catch (Exception e) {
			
			//e.printStackTrace();
			//throw e;
			
		} finally { 
			// dio error de que la session ya estaba cerrada
			// try{oSessionH.close();} catch(Exception e) {}
		}
				
		return oReciboPagoItem;
		
	}

	
	public static List<ReciboPagoItem> completarItemsPagoConIVA(List<ReciboPagoItem> lItems) {

		List<ReciboPagoItem> lItemCompleto = new Vector<ReciboPagoItem>();
		
		float IVAactual = SetupDAO.findIVAActual();
		
		for(ReciboPagoItem oItemPago: lItems) {
			
			if (oItemPago.getIdItemTipo() != ItemTipo.ItemIVA) 
				lItemCompleto.add(oItemPago);
			
			if (oItemPago.getIdItemTipo() == NovedadTipo.ComisionAlquiler) {
				
				/**
				 * Tengo que validar que ya no esté el IVA sobre este ITEM
				 * 11/05/2014
				 * 
				 * Ahora lo valido distinto, vuelvo a calcular todos los IVAS
				 * 13/01/2015
				 */
//				boolean ya_esta_agregado = false;
//				for(ReciboPagoItem oItemPago2: lItems) {					
//					if (oItemPago2.getIdItemTipo().equals(ItemTipo.ItemIVA) &&
//						oItemPago2.getContratoNovedadPago().equals(oItemPago.getContratoNovedadPago()) ) {
//						
//						ya_esta_agregado = true;
//					}
//				}
//				
//				if (!ya_esta_agregado) {
				
					ReciboPagoItem oItem = new ReciboPagoItem();
				
					// Calculo el monto del IVA
					Double dMontoIVA = oItemPago.getMonto() * (IVAactual / 100);
					
					// Personalizo la descripcion
					String descripcion = "IVA " + IVAactual + "%";
					
					oItem.setContratoNovedadPago(oItemPago.getContratoNovedadPago());
					oItem.setMonto(dMontoIVA);
					oItem.setIdItemTipo(ItemTipo.ItemIVA);
					oItem.setDescripcion(descripcion);
								
					lItemCompleto.add(oItem);
//				}
			}
			
		}

		return lItemCompleto;
		
	}

	/**
	 * Recorro los items para sacar sobre que monto tengo que aplicar la retención y agrego al final
	 * el movimiento de RETENCIÓN DE GANANCIA
	 * 
	 * Operatoria:
	 * 		10.000 (Sum. Alquileres) - 1.200 (Exento en GANANCIA) = 8.800
	 * 		
	 * 		8.800 (Porcentaje de Retención [0% - 6% - 28%]) = (6%) $ 528 => El item de retención va a ser de $ 528  
	 * 
	 * @param lItems
	 * @param oPropietario
	 * 
	 * @return lista de items de un recibo de pago
	 */
	public static List<ReciboPagoItem> completarItemsPagoConGANANCIA(List<ReciboPagoItem> lItems, Persona oPropietario) {				
		
		List<ReciboPagoItem> lItemCompleto = new Vector<ReciboPagoItem>();
				
		/* Obtengo sobre que valor voy a aplicar la retención de GANANCIA 
		   Recorro la lista de items y sumo los items que son de ALQUILERES */
		BigDecimal sumatoriaAlquileres = new BigDecimal(0.0);
		for(ReciboPagoItem oItemPago: lItems) {
					
			if (oItemPago.getIdItemTipo() == NovedadTipo.Alquiler) {
				sumatoriaAlquileres = sumatoriaAlquileres.add(new BigDecimal(oItemPago.getMonto()));
			}
			
		}
		sumatoriaAlquileres = sumatoriaAlquileres.setScale(2, RoundingMode.HALF_UP);
		System.out.println("completarItemsPagoConGANANCIA.sumatoriaAlquileres " + sumatoriaAlquileres);
		
		
		/* Busco el porcentaje de descuento que le tengo que aplicar al propietario */
		float porcentaje_retencion = 0;
		float ganancia_exento = 0;
		if (oPropietario != null) {
			porcentaje_retencion = getPorcentajeRetencionPorPropietario(oPropietario.getIdInscripcionGanancias());
			if (oPropietario.getIdInscripcionGanancias() != ComboGANANCIAS.NoInscripto )
				ganancia_exento = getMontoExentoGananciaPorPropietarioMes();
		}
		System.out.println("completarItemsPagoConGANANCIA.porcentaje_retencion " + porcentaje_retencion);
		
		
		/* Busco el monto que está exento de GANANCIAS 
		   El monto es mensual, debería chequear que ya no se haya utilizado en este mes 
		   
		   Solo para los que están inscriptos - los NO INSCRIPTOS no tienen mínimo ganancia_exento = 0 
		   Pgarello 3/01/2020
		   */
				
		System.out.println("completarItemsPagoConGANANCIA.ganancia_exento " + ganancia_exento);
		
		/**
		 * EVALUO SI TENGO QUE AGREGAR LA RETENCION
		 */
		float monto_retencion = 0;
		if (porcentaje_retencion > 0) {
			
			// realizo el cálculo del monto de la retención
			BigDecimal retencion = new BigDecimal(sumatoriaAlquileres.floatValue() - ganancia_exento);
			System.out.println("completarItemsPagoConGANANCIA.retencion " + retencion);
			
			if (retencion.floatValue() > 0) {
				float porcentaje = porcentaje_retencion / 100; 
				retencion = retencion.multiply(new BigDecimal(porcentaje));
				
				retencion = retencion.setScale(2, RoundingMode.HALF_UP);
				monto_retencion = retencion.floatValue();
			}
			
			/*
			 * Nuevo control - Mínimo del impuesto
			 * INSCRIPTO 240
			 * NO INSCRIPTO 1020
			 * Si no alcanza este monto va CEROOOOO
			 * Pgarello 03/01/2020
			 */
			float monto_minimo = 0;
			if (oPropietario != null) {
				monto_minimo = getMinimoGANANICASPorPropietario(oPropietario.getIdInscripcionGanancias());
				if ( monto_minimo > monto_retencion )
					monto_retencion = 0;
			}	
			
		}
		System.out.println("completarItemsPagoConGANANCIA.monto_retencion " + monto_retencion);
		
		/*
		 * Paso todos los items a una nueva colección y reescribo el item de RETENCION DE GANANCIA
		 */
		
		for(ReciboPagoItem oItemPago: lItems) {			
			if (oItemPago.getIdItemTipo() != ItemTipo.ItemRetencionGanancia) {				
				lItemCompleto.add(oItemPago);							
			}			
		}
	
		DecimalFormat moneda = new DecimalFormat("$###,##0.00");
		if (monto_retencion > 0) {
			
			ReciboPagoItem oItem = new ReciboPagoItem();
		
			// Personalizo la descripcion
			String descripcion = "Retención GANANCIAS (" + porcentaje_retencion + "%) BRUTO: " + moneda.format(sumatoriaAlquileres);
	
			oItem.setMonto(new Double(monto_retencion * -1));
			oItem.setIdItemTipo(ItemTipo.ItemRetencionGanancia);
			oItem.setDescripcion(descripcion);
				
			lItemCompleto.add(oItem);

		}

		return lItemCompleto;
	}
	
	
	private static float getMinimoGANANICASPorPropietario(Short idInscripcionGanancias) {
		float minimo = 0;
		
		if (idInscripcionGanancias == ComboGANANCIAS.Inscripto || idInscripcionGanancias == ComboGANANCIAS.Monotributo ) {
			minimo = SetupDAO.findGANANCIA("GANANCIA-MINIMO-INSCRIPTO");
			
		} else if(idInscripcionGanancias == ComboGANANCIAS.NoInscripto ) {
			minimo = SetupDAO.findGANANCIA("GANANCIA-MINIMO-NO_INSCRIPTO");
			
		} else if(idInscripcionGanancias == ComboGANANCIAS.Exento ) {
			// va en 0			
		}
		
		return minimo;
	}


	private static float getPorcentajeRetencionPorPropietario(int id_inscripcion_ganancia) {
		
		float retencion = 0;
		
		if (id_inscripcion_ganancia == ComboGANANCIAS.Inscripto ) {
			retencion = SetupDAO.findGANANCIA("GANANCIA-INSCRIPTO");
			
		} else if(id_inscripcion_ganancia == ComboGANANCIAS.NoInscripto ) {
			retencion = SetupDAO.findGANANCIA("GANANCIA-NO_INSCRIPTO");
			
		} else if(id_inscripcion_ganancia == ComboGANANCIAS.Exento ) {
			// va en 0
			
		} else if(id_inscripcion_ganancia == ComboGANANCIAS.Monotributo ) {
			// va en 0
		}
		
		return retencion;
	}

	
	/**
	 * Debe chequear que para este periodo (mes y año) la persona (PROPIETARIO) ya no haya
	 * usado el monto que tiene EXENTO de la aplicación del IMPUESTO A LAS GANANCIAS
	 */
	private static float getMontoExentoGananciaPorPropietarioMes() {
		return SetupDAO.findGANANCIA("GANANCIA-EXENTO");	
	}
	

} //  Fin clase