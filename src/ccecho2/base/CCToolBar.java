/*
 * CCToolBar.java
 *
 * Created on 26 de septiembre de 2006, 19:25
 * 
 * Crear un ToolBar en el cual se pueden agregar los botones que se necesiten. Este Toolbar estará fijado a un borde de la pantalla. 
 * Como está basado en un SplitPane debería ser lo primero que se agregue a una ventana en la que se quiera agregar una barra de botones.
 *
 */

package ccecho2.base;

import nextapp.echo2.app.SplitPane;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Color;

/**
 *
 * @author SShadow
 *
 * Clase Toolbar genérica que provee los m�todos necesarios para agregar y sacar botones
 *
 */
public class CCToolBar extends SplitPane {
    
	private static final long serialVersionUID = -847482779271768440L;

	/** Creates a new instance of CCToolBar */
    //ArrayList botones = new ArrayList();
    
    int defaultHeight = 34;
    int defaultOrientation = SplitPane.ORIENTATION_VERTICAL_TOP_BOTTOM; //por defecto en la parte superior de la pantalla
    
    public CCToolBar() {
        super();
        this.setOrientation(this.defaultOrientation);
        this.setSeparatorPosition(new Extent(this.defaultHeight));
        this.setResizable(false);
        this.setSeparatorHeight(new Extent(1));
        this.setBackground(Color.LIGHTGRAY);
    }
    
    
}
