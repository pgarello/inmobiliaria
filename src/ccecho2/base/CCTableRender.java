/*
 * BasicTable.java
 *
 * Created on 9 de mayo de 2007, 08:40
 *
 */

package ccecho2.base;

import nextapp.echo2.app.Component;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.Table;
import nextapp.echo2.app.layout.TableLayoutData;
import nextapp.echo2.app.table.TableCellRenderer;

/**
 *
 * @author luigi
 */
public class CCTableRender implements TableCellRenderer {
    public Component getTableCellRendererComponent(Table table, Object value, int column, int row) {
        Label label = new Label(value.toString());
        TableLayoutData layoutData = new TableLayoutData();
        label.setLayoutData(layoutData);
        return label;
    }
}
