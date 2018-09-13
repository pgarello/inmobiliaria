/*
 * CCSplitPane.java
 *
 * Created on 26 de septiembre de 2006, 17:59
 *
 * C[ustom]C[omponent]SplitPane
 * Esta clase extiende a la clase SplitPane del paquete Echo2 para poder agregarle funcionalidades
 *
 */

package ccecho2.base;

/**
 *
 * @author Marcelo D. Ré
 *
 */

public class CCSplitPane extends nextapp.echo2.app.SplitPane {
    
    /** Creates a new instance of CCSplitPane */
    public CCSplitPane() {
        super();
        this.initComponent();
    }
 
    public CCSplitPane(int orientation, nextapp.echo2.app.Extent separatorPosition) {
        super(orientation, separatorPosition);
        this.initComponent();
    }
    
    public CCSplitPane(int orientation) {
        super(orientation);
        this.initComponent();
    }
    
    // Inicializa el componente
    private void initComponent(){
        this.setStyleName("Default");
    }
}
