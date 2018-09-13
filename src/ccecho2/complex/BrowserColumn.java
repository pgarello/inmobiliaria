/*
 * BasicTableColumn.java
 *
 * Created on 14 de mayo de 2007, 09:34
 *
 */

package ccecho2.complex;

import ccecho2.base.CCTextField;
import java.io.Serializable;

/**
 *
 * @author luigi
 */
public class BrowserColumn implements Serializable {
    private int size;
    private String title;
    private String field;
    
    
    /** Creates a new instance of BasicTableColumn */
    public BrowserColumn() {
    }

    public BrowserColumn(int size, String title, String field) {
        this.setSize(size);
        this.setTitle(title);
        this.setField(field);
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getField() {
        return this.field;
    }

    public void setField(String field) {
        this.field = field;
    }
}
