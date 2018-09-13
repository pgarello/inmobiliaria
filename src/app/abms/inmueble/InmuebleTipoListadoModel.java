package app.abms.inmueble;

import java.util.List;

import datos.inmueble.InmuebleTipo;
import nextapp.echo2.app.table.AbstractTableModel;


@SuppressWarnings("serial")
public class InmuebleTipoListadoModel extends AbstractTableModel { 
	
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
		
		//System.out.println("InmuebleTipoListadoModel.getValueAt(" + row + ")");
		
		InmuebleTipo currentObject = (InmuebleTipo) dataList.get(row);
		String descripcion = "";
		String descripcion_corta = "";		
		try {
			descripcion = currentObject.getDescripcion();
			descripcion_corta = currentObject.getDescripcionCorta();
		} catch(Exception e) {
			System.out.println("InmuebleTipoListadoModel.getValueAt(" + row + ") ERROR");
			e.printStackTrace();
		}
		
        switch (column)
        {
        	case 0: return row+1;	
        	case 1: return descripcion;
        	case 2: return descripcion_corta;
        	default: return "error";        
        }

		//return new Integer((column + 1) * (row + 1)); 
	} 
	
	/** En este método seteo el encabezado de la Columna */
	public String getColumnName(int column) {
        switch (column) {
        	case 0: return new String("");
            case 1: return new String("Tipo");
            case 2: return new String("Corta");
            default: return new String("ERROR");
        }
    }


}