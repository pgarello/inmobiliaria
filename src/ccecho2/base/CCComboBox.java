/*
 * CCComboBox.java
 *
 * Created on 8 de junio de 2007, 12:51
 *
 */

package ccecho2.base;

import echopointng.ComboBox;

/**
 *
 * @author luigi
 */
public class CCComboBox extends ComboBox {
    
    /** Creates a new instance of CCComboBox */
    public CCComboBox() {
        super();
        this.initComponent();
    }
    
    // Inicializa el componente
    private void initComponent(){
        this.setPopUpAlwaysOnTop(true);
        this.setStyleName("Default");
    }
    
    
    
}
