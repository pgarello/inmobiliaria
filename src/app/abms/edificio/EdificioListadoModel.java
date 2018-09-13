package app.abms.edificio;

import java.util.List;

import datos.edificio.Edificio;
import nextapp.echo2.app.table.AbstractTableModel;


@SuppressWarnings("serial")
public class EdificioListadoModel extends AbstractTableModel { 
	
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
		Edificio currentObject = (Edificio) dataList.get(row);
        switch (column)
        {
        	case 0: return row+1;	
        	case 1: return currentObject.getDescripcion();
        	case 2: return currentObject.getDptoCantidad();
        	case 3: return currentObject.getObservaciones();
        	default: return "error";        
        }

		//return new Integer((column + 1) * (row + 1)); 
	} 
	
	/** En este método seteo el encabezado de la Columna */
	public String getColumnName(int column) {
        switch (column) {
        	case 0: return new String("");
            case 1: return new String("Edificio");
            case 2: return new String("Cant.Dpto.");
            case 3: return new String("Observaciones");
            default: return new String("ERROR");
        }
    }


}