/*
 * AcercaDeModel.java
 *
 * Created on 2 de mayo de 2007, 06:14
 *
 */

package app.abms.comprobante.factura;

import java.text.DecimalFormat;
import java.util.List;

import app.beans.ItemTipo;

import datos.factura_item.FacturaItem;

import nextapp.echo2.app.table.AbstractTableModel;



@SuppressWarnings("serial")
public class FacturaItemEditModel extends AbstractTableModel {


	private List dataList;

	/**
     * @return the dataList
     */
    public List getDataList() {
        return dataList;
    }

    /**
     * @param dataList the dataList to set
     */
    public void setDataList(List dataList) {
        this.dataList = dataList;
    }

	
	/** Cantidad de Columnas */
	public int getColumnCount() { 
		return 6; 
	} 
	
	/** Cantidad de Filas */
	public int getRowCount() { 
		return dataList.size(); 
	} 
	
	
	/** Aca va el valor de devolución */
	public Object getValueAt(int column, int row) {

		FacturaItem currentObject = (FacturaItem) dataList.get(row);
		DecimalFormat moneda = new DecimalFormat("$###,##0.00");
		
		//System.out.println("MONTO TOTAL ITEM: " + currentObject.getMonto());
		
		// Completo Datos		
		
		String direccion_inmueble = "";
		String periodo_cuota = "";
		String monto_novedad = "0,00";
		
		
		/* Que pasa cuando la factura es genérica ... no esta atada a un contrato 
		 * 30/03/2013
		 */
		
		try {
			direccion_inmueble = currentObject.getContratoNovedadFactura().getContrato().getInmueble().getDireccion_completa();
			periodo_cuota = currentObject.getContratoNovedadFactura().getPeriodoCuota();
			monto_novedad = moneda.format(currentObject.getContratoNovedadFactura().getMonto());
		} catch(NullPointerException npe) {
			npe.printStackTrace();
			// Aca no hago nothing ?????
		}
		
		// Proceso el tipo para ver si es un impuesto buscar cual
		String tipo = ItemTipo.getDescripcion(currentObject.getIdItemTipo());
//		if (currentObject.getIdItemTipo() == ItemTipo.ItemImpuestos) {
//			tipo += " " + ComboImpuesto.getImpuesto(currentObject.getContratoNovedadCobro().getImpuestoId());
//		}
		
        switch (column) {
        	case 0: return row+1;	
        	case 1: return direccion_inmueble;
        	case 2: return periodo_cuota;
        	case 3: return tipo;
        	case 4: return currentObject.getDescripcion();
        	case 5: return moneda.format(currentObject.getMonto());
        	default: return "error";
        }

		//return new Integer((column + 1) * (row + 1)); 
	}
	
	
	/** En este método seteo el encabezado de la Columna */
	public String getColumnName(int column) {
        switch (column) {
        	case 0: return new String("");
            case 1: return new String("Dirección");
            case 2: return new String("Periodo - Cuota");
            case 3: return new String("Tipo");
            case 4: return new String("Leyenda");
            case 5: return new String("Total Item");
            default: return new String("ERROR");
        }
    }


}