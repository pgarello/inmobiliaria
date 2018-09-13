/*
 * BasicTable.java
 *
 * Created on 9 de mayo de 2007, 08:40
 *
 */

package ccecho2.base;

import echopointng.TableEx;
import echopointng.table.DefaultTableCellRendererEx;
import echopointng.table.TableCellRendererEx;
import echopointng.util.ColorKit;
import echopointng.xhtml.XhtmlFragment;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Table;
import nextapp.echo2.app.layout.TableLayoutData;

/**
 *
 * @author luigi
 */
public class CCTable extends TableEx {

	private static final long serialVersionUID = 1L;

	/** Creates a new instance of BasicTable */
    public CCTable() {
        this.setStyleName("Default");

        this.setHeightStretched(true);
        this.setWidth(new Extent(90, Extent.PERCENT));
        this.setSelectionEnabled(true);

        this.setDefaultRenderer(Object.class, new CCTableRender());
    }
    
    public CCTable(String nombre) {
    	
        this.setStyleName("Default");

        this.setHeightStretched(true);
        this.setWidth(new Extent(90, Extent.PERCENT));
        this.setSelectionEnabled(true);
    	
    	final TableLayoutData layoutDataGrey = new TableLayoutData();
		layoutDataGrey.setBackground(ColorKit.clr("#C0C0C0"));
		
		TableCellRendererEx cellRendererEx = new DefaultTableCellRendererEx() {
			/**
			 * @see echopointng.table.DefaultTableCellRendererEx#getTableCellRendererContent(nextapp.echo2.app.Table, java.lang.Object, int, int)
			 */
			public XhtmlFragment getTableCellRendererContent(Table table, Object value, int column, int row) {
				XhtmlFragment fragment = super.getTableCellRendererContent(table, value, column, row);
				if (column == 0 && row >= 0) {
					fragment.setLayoutData(layoutDataGrey);
				}
				if (row == -1) {
					fragment.setLayoutData(layoutDataGrey);
				}
				return fragment;
			}
			/**
			 * @see echopointng.table.DefaultTableCellRendererEx#isSelectionCausingCell(nextapp.echo2.app.Table, int, int)
			 */
			public boolean isSelectionCausingCell(Table table, int column, int row) {
				//return column == 0; // puedo seleccionar la fila haciendo click en cualquier columna ... si salgo solo con 0, permite el la 1ra col.
//				 Cualquiera sea la fila o la columna devuelvo VERDADERO -- lo uso para seleccionar una fila
				return true;
			}
		};
		this.setDefaultRenderer(cellRendererEx);
    }
    
}
