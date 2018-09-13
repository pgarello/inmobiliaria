/*
 * CCPasswordField.java
 *
 * Created on 20 de abril de 2007, 11:22
 *
 */

package ccecho2.base;

import nextapp.echo2.app.PasswordField;

/**
 *
 * @author luigi
 */
public class CCPasswordField extends PasswordField {
    
    /** Creates a new instance of CCPasswordField */
    public CCPasswordField() {
        super();
        this.initComponent();
    }
    
    // Inicializa el componente
    private void initComponent(){
         this.setStyleName("Default");
    }

}
