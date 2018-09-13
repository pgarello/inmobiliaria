/*
 * AcercaDeModel.java
 *
 * Created on 2 de mayo de 2007, 06:14
 *
 */

package app.busquedas.persona;

import java.util.List;

import datos.persona.Persona;
import nextapp.echo2.app.table.AbstractTableModel;


@SuppressWarnings("serial")
public class PersonaFindListadoModel extends AbstractTableModel { 
	
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
		Persona currentObject = (Persona) dataList.get(row);
		
		// Evaluo el tipo de persona
		String persona_razon = "";
		short persona_tipo = currentObject.getIdPersonaTipo();
		if (persona_tipo == Persona.PersonaFisica) {
			persona_razon = currentObject.getApellido() + ", " + currentObject.getNombres();
		} else if (persona_tipo == Persona.PersonaJuridica) {
			persona_razon = currentObject.getRazonSocial();
		}
		
		String localidad = "";
		try {localidad = currentObject.getLocalidad().getDescripcion();}
		catch(NullPointerException npe) {}
		
        switch (column) {
        	case 0: return row+1;	
        	case 1: return persona_razon;
        	case 2: return currentObject.getDireccion();
        	case 3: return currentObject.getTelefono();
        	case 4: return localidad;
        	case 5: return Persona.getPersonaTipo(currentObject.getIdPersonaTipo());
        	default: return "error";
        }

		//return new Integer((column + 1) * (row + 1)); 
	} 
	
	/** En este método seteo el encabezado de la Columna */
	public String getColumnName(int column) {
        switch (column) {
        	case 0: return new String("");
            case 1: return new String("Razón");
            case 2: return new String("Domicilio");
            case 3: return new String("Teléfono");
            case 4: return new String("Localidad");
            case 5: return new String("Personeria");
            default: return new String("ERROR");
        }
    }


}