package app.abms.localidad;

import java.util.List;

import datos.localidad.Localidad;
import nextapp.echo2.app.table.AbstractTableModel;


@SuppressWarnings("serial")
public class LocalidadListadoModel extends AbstractTableModel { 
	
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
		Localidad currentObject = (Localidad) dataList.get(row);
		
		//System.out.println("Control LocalidadListadoModel fila:" + row + " columna:" + column);
		
		String localidad = "";
		String provincia = "";
		
		String cp = "";
		if(currentObject.getCp() != null)cp = currentObject.getCp();
		
		try{localidad = currentObject.getDescripcion();} catch(Exception e){e.printStackTrace();}
		try{provincia = currentObject.getProvincia();} catch(Exception e){e.printStackTrace();}
		
        switch (column)
        {
        	case 0: return row+1;	
        	case 1: return localidad;
        	case 2: return provincia;
        	case 3: return cp;
        	default: return "error";        
        }

	} 
	
	/** En este método seteo el encabezado de la Columna */
	public String getColumnName(int column) {
        switch (column) {
        	case 0: return new String("");
            case 1: return new String("Localidad");
            case 2: return new String("Provincia");
            case 3: return new String("C.P.");
            default: return new String("ERROR");
        }
    }

}