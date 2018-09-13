/*
 * CCMenuBar.java
 *
 * Created on 26 de septiembre de 2006, 18:16
 *
 
 */

package ccecho2.base;

import nextapp.echo2.app.Extent;

/**
 *
 * @author SShadow
 */
public class CCMenuBar extends echopointng.MenuBar {
    
	private static final long serialVersionUID = 1L;

	/** Creates a new instance of CCMenuBar */
    public CCMenuBar() {
        super();
        this.initComponent();
    }

    public void setTop(int newValue) {
        super.setTop(new Extent(newValue));
    }

    public void setLeft(int newValue) {
        super.setLeft(new Extent(newValue));
    }
    
    // Inicializa el componente
    private void initComponent(){
         this.setStyleName("Default");
    }
}
