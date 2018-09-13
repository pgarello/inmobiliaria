/*
 * BasicTableBrowser.java
 *
 * Created on 15 de mayo de 2007, 05:24
 *
 */

package ccecho2.complex;

import ccecho2.base.CCTable;
import ccecho2.base.CCTableRender;
import java.io.Serializable;
import java.util.Iterator;
import nextapp.echo2.app.Extent;

/**
 *
 * @author luigi
 */
public class Browser implements Serializable {
    private CCTable table;
    private BrowserModel model;
    private CCTableRender render;
    private int currentRow = 0;
    
    /** Creates a new instance of BasicTableBrowser */
    public Browser() {
        this.table = new CCTable();
        this.model = new BrowserModel();
        this.render = new CCTableRender();
        this.table.setDefaultRenderer(Object.class, this.render);
        this.update();
    }

    public CCTable getTable() {
        return this.table;
    }

    public BrowserModel getModel() {
        return this.model;
    }

    public CCTableRender getRender() {
        return this.render;
    }

    public void update() {
        this.table.setModel(this.model);

        int nroColumna = 0;
        Iterator i = this.getModel().getTableColumns().iterator();
        while (i.hasNext()) {
            BrowserColumn column = (BrowserColumn) i.next();
            this.getTable().getColumnModel().getColumn(nroColumna).setWidth(
                    new Extent(column.getSize(), Extent.PERCENT));
            nroColumna++;
        }
    }

    public void addTableColumns(BrowserColumn column) {
        this.getModel().getTableColumns().add(column);
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }
}

