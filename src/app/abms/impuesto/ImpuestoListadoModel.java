package app.abms.impuesto;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import app.beans.Utiles;
import app.combos.ComboImpuesto;

import datos.contrato.ContratoNovedadDTO;

import nextapp.echo2.app.table.AbstractTableModel;


@SuppressWarnings("serial")
public class ImpuestoListadoModel extends AbstractTableModel { 
	
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
		return 8; 
	} 
	
	/** Cantidad de Filas */
	public int getRowCount() { 
		return dataList.size(); 
	} 
	
	/** Aca va el valor de devolución */
	public Object getValueAt(int column, int row) {
		
		ContratoNovedadDTO currentObject = (ContratoNovedadDTO) dataList.get(row);
		
		SimpleDateFormat fecha= new SimpleDateFormat("dd/MM/yyyy");
		DecimalFormat moneda = new DecimalFormat("$###,##0.00");
		
		String impuesto = ComboImpuesto.getImpuesto(currentObject.getImpuestoId());
		String cuota_contrato = Utiles.convertirMes(currentObject.getPeriodoMes()) + " - " + currentObject.getPeriodoAnio() + " (Cuota " + currentObject.getContratoCuota() + ")";
		
        switch (column) {
        	case 0: return row+1;	
        	case 1: return impuesto;
        	case 2: return cuota_contrato;
        	case 3: return fecha.format(currentObject.getFechaVencimiento());
        	case 4: return currentObject.getImpuestoCuota();
        	case 5: return currentObject.getImpuestoAnio();
        	case 6: return moneda.format(currentObject.getMonto_propietario());
        	case 7: return moneda.format(currentObject.getMonto_inquilino());
        	default: return "error";
        }

	} 
	
	
	/** En este método seteo el encabezado de la Columna */
	public String getColumnName(int column) {
        switch (column) {
        	case 0: return new String("");
            case 1: return new String("Impuesto");
            case 2: return new String("Cuota Contrato");
            case 3: return new String("F.Vencimiento");
            case 4: return new String("Cuota");
            case 5: return new String("Año");
            case 6: return new String("Monto Prop.");
            case 7: return new String("Monto Inq.");
            default: return new String("ERROR");
        }
    }


}