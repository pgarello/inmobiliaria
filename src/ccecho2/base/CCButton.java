/*
 * CCButton.java
 *
 * Created on 26 de septiembre de 2006, 19:05
 *
 */

package ccecho2.base;

/**
 *
 * @author SShadow
 */
@SuppressWarnings("serial")
public class CCButton extends nextapp.echo2.app.Button {
    
    /** Creates a new instance of CCButton */
    public CCButton() {
        super();
        this.initComponent();
    }
    
    public CCButton(String text){
        super(text);
        this.initComponent();
    }
    
    public CCButton(String text, nextapp.echo2.app.ImageReference icon){
        super(text,icon);
        this.initComponent();
    }
    
    public CCButton(nextapp.echo2.app.ImageReference icon){
        super(icon);
        this.initComponent();
    }
    
    // Inicializa el componente
    private void initComponent(){
         this.setStyleName("Default");
    }
}
