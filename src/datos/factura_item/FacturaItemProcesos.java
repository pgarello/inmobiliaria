package datos.factura_item;

import java.util.List;
import java.util.Vector;

import app.beans.NovedadTipo;

import datos.contrato_novedad_factura.ContratoNovedadFactura;

public class FacturaItemProcesos {

	public static List<FacturaItem> convertirLista(List<ContratoNovedadFactura> lNovedad) {

		List<FacturaItem> lItem = new Vector<FacturaItem>();
		
		for(ContratoNovedadFactura oNovedad: lNovedad) {
			
			FacturaItem oItem = new FacturaItem();
			
			oItem.setContratoNovedadFactura(oNovedad);
			
			// Ahora al item lo lleno con el saldo
			oItem.setMonto(oNovedad.getMonto());
			
			oItem.setIdItemTipo(oNovedad.getIdNovedadTipo());
			
			
			// Actualizo la descripción por si es un impuesto
			String descripcion = NovedadTipo.getDescripcion(oNovedad.getIdNovedadTipo());
//			if (oNovedad.getIdNovedadTipo() == ItemTipo.ItemImpuestos) {
//				descripcion += " " 	+ ComboImpuesto.getImpuesto(oNovedad.getImpuestoId())
//									+ " (" 
//									+ oNovedad.getImpuestoCuota() 
//									+ " - "
//									+ oNovedad.getImpuestoAnio() 
//									+ ")";
//			}
			oItem.setDescripcion(descripcion);
			
			
			lItem.add(oItem);
			
		}
		
		return lItem;
		
	}
	

//	public static ReciboCobroItem completarDatos(ReciboCobroItem oReciboCobroItem) throws Exception {
//		
//		System.out.println("ReciboCobroItemProcesos.completarDatos: " + oReciboCobroItem.getIdReciboCobroItem());
//		
//		Session oSessionH = SessionFactory.currentSession();
//		
//		try {
//			oSessionH.refresh(oReciboCobroItem);
//			//Hibernate.initialize(oReciboCobroItem);
//						
//			/** -------------------------------------------------------- */
//			/** 
//			 * Hay items que no estan asociados a una novedad
//			 *  
//			 */
//			Hibernate.initialize(oReciboCobroItem.getContratoNovedadCobro());
//			int id = oReciboCobroItem.getContratoNovedadCobro().getIdContratoNovedadCobro();
//			ContratoNovedadCobro oContratoNovedadCobro = ContratoNovedadCobroProcesos.buscarPorId(id);
//			
//			/** -------------------------------------------------------- */
//						
//			//System.out.println("CONTROL DE DATOS -------------- " + oContratoNovedadCobro.getContrato().getInmueble().getDireccion_completa());
//						
//		} catch (Exception e) {
//			
//			//e.printStackTrace();
//			//throw e;
//			
//		} finally { 
//			// dio error de que la session ya estaba cerrada
//			// try{oSessionH.close();} catch(Exception e) {}
//		}
//				
//		return oReciboCobroItem;
//		
//	}
	
	
} //  Fin clase