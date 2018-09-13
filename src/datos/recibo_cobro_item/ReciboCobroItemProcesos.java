package datos.recibo_cobro_item;

import java.util.List;
import java.util.Vector;

import org.hibernate.Hibernate;
import org.hibernate.Session;

import app.beans.ItemTipo;
import app.beans.NovedadTipo;
import app.combos.ComboImpuesto;

import datos.SessionFactory;
import datos.contrato_novedad_cobro.ContratoNovedadCobro;
import datos.contrato_novedad_cobro.ContratoNovedadCobroProcesos;

import datos.setup.SetupDAO;

public class ReciboCobroItemProcesos {

	public static List<ReciboCobroItem> convertirLista(List<ContratoNovedadCobro> lNovedad) {

		List<ReciboCobroItem> lItem = new Vector<ReciboCobroItem>();
		
		for(ContratoNovedadCobro oNovedad: lNovedad) {
			
			ReciboCobroItem oItem = new ReciboCobroItem();
			
			oItem.setContratoNovedadCobro(oNovedad);
			
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
	
	
	public static ReciboCobroItem completarDatos(ReciboCobroItem oReciboCobroItem) throws Exception {
		
		System.out.println("ReciboCobroItemProcesos.completarDatos: " + oReciboCobroItem.getIdReciboCobroItem());
		
		Session oSessionH = SessionFactory.currentSession();
		
		try {
			oSessionH.refresh(oReciboCobroItem);
			//Hibernate.initialize(oReciboCobroItem);
						
			/** -------------------------------------------------------- */
			/** 
			 * Hay items que no estan asociados a una novedad
			 *  
			 */
			Hibernate.initialize(oReciboCobroItem.getContratoNovedadCobro());
			int id = oReciboCobroItem.getContratoNovedadCobro().getIdContratoNovedadCobro();
			ContratoNovedadCobroProcesos.buscarPorId(id);
			
			/** -------------------------------------------------------- */
						
			//System.out.println("CONTROL DE DATOS -------------- " + oContratoNovedadCobro.getContrato().getInmueble().getDireccion_completa());
						
		} catch (Exception e) {
			
			//e.printStackTrace();
			//throw e;
			
		} finally { 
			// dio error de que la session ya estaba cerrada
			// try{oSessionH.close();} catch(Exception e) {}
		}
				
		return oReciboCobroItem;
		
	}
	
	
	/**
	 * Recorre los items de un comprobante de COBRO y por cada item del tipo COMISION CONTRATO
	 * agrega el IVA correspondiente
	 * Se agrega un nuevo tipo de novedad a la que se le aplica IVA - COMISION POR RESCISION DE CONTRATO
	 * @param lItems List<ReciboCobroItem>
	 * @return La lista ingresada mas el item del IVA
	 */
	public static List<ReciboCobroItem> completarItemsCobroConIVA(List<ReciboCobroItem> lItems) {

		List<ReciboCobroItem> lItemCompleto = new Vector<ReciboCobroItem>();
		
		float IVAactual = SetupDAO.findIVAActual();
		
		for(ReciboCobroItem oItemCobro: lItems) {
			
			if (oItemCobro.getIdItemTipo() != NovedadTipo.IVA) {
				lItemCompleto.add(oItemCobro);
			}
			
			// lItemCompleto.add(oItemCobro);
			// Puedo agregar un solo ITEM de IVA. Pero para mas detalle agrago los movimiento por separado
			if (oItemCobro.getIdItemTipo() == NovedadTipo.ComisionContrato ||
				oItemCobro.getIdItemTipo() == NovedadTipo.ComisionRescision) {
										
				ReciboCobroItem oItem = new ReciboCobroItem();
			
				// Calculo el monto del IVA
				Double dMontoIVA = oItemCobro.getMonto() * (IVAactual / 100);
				
				// Personalizo la descripcion
				String descripcion = "IVA " + IVAactual + "%";
				
				oItem.setContratoNovedadCobro(oItemCobro.getContratoNovedadCobro());
				oItem.setMonto(dMontoIVA);
				oItem.setIdItemTipo(ItemTipo.ItemIVA);
				oItem.setDescripcion(descripcion);
							
				lItemCompleto.add(oItem);
			}
			
		}
		
		return lItemCompleto;
		
	}
	
	
} //  Fin clase