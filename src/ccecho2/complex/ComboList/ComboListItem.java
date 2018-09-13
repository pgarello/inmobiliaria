/*
 * ComboListItems.java
 *
 * Created on 20 de junio de 2007, 08:21
 *
 */

package ccecho2.complex.ComboList;

import java.io.Serializable;

/**
 *
 * @author luigi
 */
@SuppressWarnings("serial")
public class ComboListItem implements Serializable {
	
    private Object value = null;
    private String detail = "";
    private boolean seleccionado = false;
    
    @SuppressWarnings("unused")
	private String descripcion_corta = "";
    
    /** Creates a new instance of ComboListItems */
    public ComboListItem(String detail, Object value) {
        this.detail = detail;
        this.value = value;
    }

    public ComboListItem(String detail, Object value, String desc_corta) {
        this.detail = detail;
        this.value = value;
        this.descripcion_corta = desc_corta;
    }

    public ComboListItem(String detail, Object value, String desc_corta, boolean seleccionado) {
        this.detail = detail;
        this.value = value;
        this.descripcion_corta = desc_corta;
        this.seleccionado = seleccionado;
    }
    
    
    public String toString() {
        return this.detail;
    }
    
    public Object getValue() {
        return this.value;
    }
    
    public boolean getSeleccionado() {
        return this.seleccionado;
    }
    
}
