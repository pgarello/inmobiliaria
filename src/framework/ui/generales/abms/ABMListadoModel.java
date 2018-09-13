/*
 * AcercaDeModel.java
 *
 * Created on 2 de mayo de 2007, 06:14
 *
 */

package framework.ui.generales.abms;

import java.util.Hashtable;
import java.util.Vector;

/**
 *  Modelo MVC de un ABM
 */
public class ABMListadoModel extends java.util.Observable {
    /** Vector que representa la página de datos */
    private Vector<Hashtable> dataPage = new Vector<Hashtable>();
    /** Número de página actual */
    private int currentPage = 0;
    /** Número de fila actual en la página */
    private int currentRow = 0;
    /** Modelo vacio para altas */
    private Hashtable emptyModel;
    /** Filtro actual usado para la consulta */
    private String[] filterValues = new String[0];
    
    /** Creates a new instance of AcercaDeModel */
    public ABMListadoModel() {
        super();
        this.setChanged();
    }

    public void setEmpty(Hashtable emptyModel) {
        this.emptyModel = emptyModel;
    }

    public Hashtable getEmpty() {
        return this.emptyModel;
    }
    
    public Vector<Hashtable> getDataPage() {
        return dataPage;
    }

    public void setDataPage(Vector<Hashtable> dataPage) {
        this.dataPage = dataPage;
        this.setChanged();
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
        this.setChanged();
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
        this.setChanged();
    }

    public String[] getFilterValues() {
        return filterValues;
    }

    public void setFilterValues(String[] filterValues) {
        this.filterValues = filterValues;
        this.setChanged();
    }
}
