/*
 * AcercaDeModel.java
 *
 * Created on 2 de mayo de 2007, 06:14
 *
 */

package app.abms.inmueble;

import java.util.List;

import datos.inmueble.Inmueble;
import nextapp.echo2.app.table.AbstractTableModel;


@SuppressWarnings("serial")
public class InmuebleListadoModel extends AbstractTableModel { 
	
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
		Inmueble currentObject = (Inmueble) dataList.get(row);
		
		// Evaluo el propietario por si viene nulo
		String propietario = "";
		try {
			propietario = currentObject.getPropietario().getDescripcion();
		} catch(NullPointerException npe) {
			/** NO TIENE PROPIETARIO ASIGNADO */
		}

		// Evaluo el EDIFICIO por si viene nulo
		String edificio = "";
		try {
			edificio = currentObject.getEdificio().getDescripcion();
		} catch(NullPointerException npe) {
			/** NO TIENE EDIFICIO ASIGNADO */
		}
		
		String direccion = currentObject.getDireccion_completa(); 
		//currentObject.getDireccionCalle() + " " + currentObject.getDireccionNro();		
		
        switch (column) {
        	case 0: return row+1;	
        	case 1: return direccion;
        	case 2: return edificio;
        	case 3: return currentObject.getLocalidad().getDescripcion();
        	case 4: return currentObject.getInmuebleTipo().getDescripcion();
        	case 5: return propietario;
        	default: return "error";
        }

		//return new Integer((column + 1) * (row + 1)); 
	} 
	
	/** En este método seteo el encabezado de la Columna */
	public String getColumnName(int column) {
        switch (column) {
        	case 0: return new String("");
            case 1: return new String("Dirección");
            case 2: return new String("Edificio");
            case 3: return new String("Localidad");
            case 4: return new String("Tipo");
            case 5: return new String("Propietario");
            default: return new String("ERROR");
        }
    }


}