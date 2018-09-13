/*
 * AcercaDeModel.java
 *
 * Created on 2 de mayo de 2007, 06:14
 *
 */

package app.abms.usuario;

import java.util.List;

import datos.usuario.Usuario;
import nextapp.echo2.app.table.AbstractTableModel;


@SuppressWarnings("serial")
public class UsuarioListadoModel extends AbstractTableModel { 
	
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
		return 4; 
	} 
	
	/** Cantidad de Filas */
	public int getRowCount() { 
		return dataList.size(); 
	} 
	
	/** Aca va el valor de devolución */
	public Object getValueAt(int column, int row) {
		Usuario currentObject = (Usuario) dataList.get(row);
        switch (column)
        {
        /**
            case 0: return new TextField();
            case 1: return new Integer(2);
            case 2: return currentObject;
            case 3: return new Integer(4);
            default: return new Integer((column + 1) * (row + 1));
        */
        	case 0: return row+1;	
        	case 1: return currentObject.getDescripcion();
        	case 2: return currentObject.getUsuario();
        	case 3: return currentObject.getClave();
        	default: return "error";        
        }

		//return new Integer((column + 1) * (row + 1)); 
	} 
	
	/** En este método seteo el encabezado de la Columna */
	public String getColumnName(int column) {
        switch (column) {
        	case 0: return new String("");
            case 1: return new String("Descripción");
            case 2: return new String("Usuario");
            case 3: return new String("Clave");
            default: return new String("ERROR");
        }
    }


}