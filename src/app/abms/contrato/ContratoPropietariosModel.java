package app.abms.contrato;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import app.beans.NovedadTipo;
import app.beans.Utiles;

import datos.contrato_actor.ContratoActor;
import datos.contrato_novedad_cobro.ContratoNovedadCobro;
//import datos.contrato_novedad_pago.ContratoNovedadPago;

import nextapp.echo2.app.table.AbstractTableModel;


@SuppressWarnings("serial")
public class ContratoPropietariosModel extends AbstractTableModel { 
	
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
		return 3; 
	} 
	
	/** Cantidad de Filas */
	public int getRowCount() { 
		return dataList.size(); 
	} 
	
	/** Aca va el valor de devolución */
	public Object getValueAt(int column, int row) {
		ContratoActor currentObject = (ContratoActor) dataList.get(row);
		
		SimpleDateFormat fecha = new SimpleDateFormat("dd/MM/yyyy");
		DecimalFormat moneda = new DecimalFormat("$ ###,##0.00");
		
		String fecha_baja = "";
		try { 
			fecha_baja = fecha.format(currentObject.getFechaBaja());
		} catch(NullPointerException npe) {}
		
        switch (column) {
        	case 0: return currentObject.getId().getPersona().getDescripcion();
        	case 1: return fecha_baja ;
        	case 2: return currentObject.getPorcentaje();
        	default: return "error";
        }

	} 
	
	
	/** En este método seteo el encabezado de la Columna */
	public String getColumnName(int column) {
        switch (column) {
        	case 0: return new String("Propietario");
            case 1: return new String("Fecha Baja");
            case 2: return new String("Porcentaje");
            default: return new String("ERROR");
        }
    }


}