package app.abms.comprobante.pago;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import app.beans.Utiles;

import datos.recibo_pago.ReciboPago;

import nextapp.echo2.app.table.AbstractTableModel;


@SuppressWarnings("serial")
public class PagoListadoModel extends AbstractTableModel { 
	
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
		return 5; 
	} 
	
	/** Cantidad de Filas */
	public int getRowCount() { 
		return dataList.size(); 
	} 
	
	/** Aca va el valor de devolución */
	public Object getValueAt(int column, int row) {
		ReciboPago currentObject = (ReciboPago) dataList.get(row);
		
		SimpleDateFormat fecha= new SimpleDateFormat("dd/MM/yyyy");
		DecimalFormat moneda = new DecimalFormat("$###,##0.00");
			
        switch (column) {
        	case 0: return row+1;	
        	case 1: return fecha.format(currentObject.getFechaEmision());
        	case 2: return currentObject.getPersona().getDescripcion();
        	case 3: return moneda.format(currentObject.getTotal());
        	case 4: return Utiles.complete(currentObject.getNumero().toString(), 8);
        	default: return "error";
        }

	} 
	
	
	/** En este método seteo el encabezado de la Columna */
	public String getColumnName(int column) {
        switch (column) {
        	case 0: return new String("");
            case 1: return new String("Fecha Emisión");
            case 2: return new String("Razón");
            case 3: return new String("Total");
            case 4: return new String("Número");
            default: return new String("ERROR");
        }
    }


}