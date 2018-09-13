/*
 * BasicTable.java
 *
 * Created on 9 de mayo de 2007, 08:40
 *
 */

package ccecho2.complex;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Vector;
import nextapp.echo2.app.table.AbstractTableModel;

/**
 *
 * @author luigi
 */
public class BrowserModel extends AbstractTableModel {
    private Vector<Hashtable> lista = null;
    private Vector<BrowserColumn> tableColumns = null;

    public BrowserModel () {
         this.tableColumns = new Vector<BrowserColumn>();
         this.lista = new Vector<Hashtable>();
    }

    public BrowserModel (Vector<Hashtable> lista, Vector<BrowserColumn> tableColumns) {
        super();
        this.setLista(lista);
        this.setTableColumns(tableColumns);
    }
    
    public int getColumnCount() {
        int tableCount = 0;
        if (this.tableColumns != null) {    // a ser manejado por una excepcion
            tableCount = this.tableColumns.size();
        }
        return tableCount;
    }

    public int getRowCount() {
        int rowCount = 0;
        if (this.getLista() != null) {    // a ser manejado por una excepcion
            rowCount = getLista().size();
        }
        
        return rowCount;
    }

    public Object getValueAt(int column, int row) {
        String resu = "";
        Hashtable table = (Hashtable) this.getLista().get(row).get("values");
        BrowserColumn basicColumn = this.getTableColumns().get(column);

        if (table.containsKey(basicColumn.getField())) {
            resu = String.valueOf(table.get(basicColumn.getField()));
        }

        return resu;
    }

    
    
    public Vector<Hashtable> getLista() {
        return lista;
    }

    public void setLista(Vector<Hashtable> lista) {
        this.lista = lista;
    }

    public Vector<BrowserColumn> getTableColumns() {
        return this.tableColumns;
    }

    public void setTableColumns(Vector<BrowserColumn> tableColumns) {
        this.tableColumns = tableColumns;
    }

    public String getColumnName(int column) {
        return this.tableColumns.get(column).getTitle();
    }
}


