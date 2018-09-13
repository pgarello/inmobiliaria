/*
 * AcercaDeModel.java
 *
 * Created on 2 de mayo de 2007, 06:14
 *
 */

package app.abms.comprobante.pago;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import app.beans.ItemTipo;

import datos.recibo_pago_item.ReciboPagoItem;
import nextapp.echo2.app.table.AbstractTableModel;



@SuppressWarnings("serial")
public class PagoItemGananciaModel extends AbstractTableModel {


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
		return 7; 
	} 
	
	/** Cantidad de Filas */
	public int getRowCount() { 
		return dataList.size(); 
	} 
	
	
	/** Aca va el valor de devolución */
	public Object getValueAt(int column, int row) {

		ReciboPagoItem currentObject = (ReciboPagoItem) dataList.get(row);
		
		DecimalFormat moneda = new DecimalFormat("$###,##0.00");
		SimpleDateFormat fecha= new SimpleDateFormat("dd/MM/yyyy");
		
		//System.out.println("MONTO TOTAL ITEM: " + currentObject.getMonto());
		
		// Completo Datos		
		String recibo = "";
		String persona = "";
		String cuit = "";
		try {
			
			// Ahora puede haber un item que no este atado a un Contrato - Pgarello 06-01-2011			
			//direccion_inmueble = currentObject.getContratoNovedadPago().getContrato().getInmueble().getDireccion_completa();
			recibo = currentObject.getReciboPago().getNumero().toString() + " - " + fecha.format(currentObject.getReciboPago().getFechaEmision());
			
			persona = currentObject.getReciboPago().getPersona().getDescripcion();
			cuit = currentObject.getReciboPago().getPersona().getCUIT_DNI();
			
		} catch(Exception npe) {
			// npe.printStackTrace();
			// Aca no hago nothing ?????
		}
		
		// Proceso el tipo para ver si es un impuesto buscar cual
		String tipo = ItemTipo.getDescripcion(currentObject.getIdItemTipo());
//		if (currentObject.getIdItemTipo() == ItemTipo.ItemImpuestos) {
//			tipo += " " + ComboImpuesto.getImpuesto(currentObject.getContratoNovedadCobro().getImpuestoId());
//		}
		
        switch (column) {
        	case 0: return row+1;	
        	case 1: return recibo;
        	case 2: return persona;
        	case 3: return cuit;
        	case 4: return tipo;
        	case 5: return currentObject.getDescripcion();
        	case 6: return moneda.format(currentObject.getMonto()*-1);
        	default: return "error";
        }

		//return new Integer((column + 1) * (row + 1)); 
	}
	
	
	/** En este método seteo el encabezado de la Columna */
	public String getColumnName(int column) {
        switch (column) {
        	case 0: return new String("");
            case 1: return new String("Recibo");
            case 2: return new String("Persona");
            case 3: return new String("CUIT");
            case 4: return new String("Tipo");
            case 5: return new String("Leyenda");
            case 6: return new String("Total Item");
            default: return new String("ERROR");
        }
    }


}