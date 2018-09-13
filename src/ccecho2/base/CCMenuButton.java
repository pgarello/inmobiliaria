/*
 * CCMenuButton.java
 *
 * Created on 26 de septiembre de 2006, 18:18
 *
 */

package ccecho2.base;

/**
 *
 * @author SShadow
 */
public class CCMenuButton extends echopointng.MenuButton {
    
    /** Creates a new instance of CCMenuButton */
    public CCMenuButton() {
        super();
        this.initComponent();
    }
    
    public CCMenuButton(String text) {
        super(text);
        this.initComponent();
    }
    
    public CCMenuButton(String text, nextapp.echo2.app.ImageReference icon) {
        super(text, icon);
        this.initComponent();
    }

    public CCMenuButton(nextapp.echo2.app.ImageReference icon) {
        super(icon);
        this.initComponent();
    }
    
    // Inicializa el componente
    private void initComponent(){
         this.setStyleName("Default");
    }

}
