/*
 * AcercaDeModel.java
 *
 * Created on 2 de mayo de 2007, 06:14
 *
 */

package app.informes;

import java.text.DecimalFormat;
import java.util.List;

import app.beans.NovedadTipo;

import datos.contrato_novedad_cobro.ContratoNovedadCobro;

import nextapp.echo2.app.table.AbstractTableModel;


@SuppressWarnings("serial")
public class DeudaListadoModel extends AbstractTableModel { 
	
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
		
		ContratoNovedadCobro currentObject = (ContratoNovedadCobro) dataList.get(row);
		
		//SimpleDateFormat fecha = new SimpleDateFormat("dd/MM/yyyy");
		
		// Evaluo el propietario por si viene nulo
		String direccion_inmueble = "", periodo_cuota = "", cliente = "", movimiento = "", rescindido="";
		try {
			direccion_inmueble = currentObject.getContrato().getInmueble().getDireccion_completa();
			periodo_cuota = currentObject.getPeriodoCuota();
			cliente = currentObject.getContrato().getInquilino().getDescripcion();
			movimiento = NovedadTipo.getDescripcion(currentObject.getIdNovedadTipo());
			if (currentObject.getContrato().getFechaRescision() != null)
				//rescindido = fecha.format(currentObject.getContrato().getFechaRescision());
				rescindido = "*";
		} catch(NullPointerException npe) {
			/** NO TIENE PROPIETARIO ASIGNADO */
		}
		
		DecimalFormat moneda = new DecimalFormat("$###,##0.00");
		
        switch (column) {
        	case 0: return row+1;
        	case 1: return rescindido;
        	case 2: return direccion_inmueble;
        	case 3: return periodo_cuota;
        	case 4: return movimiento;
        	case 5: return cliente;
        	case 6: return moneda.format(currentObject.getMonto());
        	case 7: return moneda.format(currentObject.getSaldo());
        	default: return "error";
        }

		//return new Integer((column + 1) * (row + 1)); 
	} 
	
	/** En este método seteo el encabezado de la Columna */
	public String getColumnName(int column) {
        switch (column) {
        	case 0: return new String("");
        	case 1: return null;
            case 2: return new String("Dirección");
            case 3: return new String("Periodo");
            case 4: return new String("Movimiento");
            case 5: return new String("Inquilino");
            case 6: return new String("Monto");
            case 7: return new String("Saldo");
            default: return new String("ERROR");
        }
    }


}