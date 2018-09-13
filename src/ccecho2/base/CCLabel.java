/*
 * CCLabel.java
 *
 * Created on 2 de marzo de 2007, 09:53
 *
 */

package ccecho2.base;

import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.ImageReference;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.layout.ColumnLayoutData;

/**
 *
 * @author SShadow
 */
@SuppressWarnings("serial")
public class CCLabel extends Label {
    
    /** Creates a new instance of CCLabel */
    public CCLabel() {
        super();
        this.initComponent();
    }

    
    public CCLabel(String s){
        super(s);
        this.initComponent();
    }
    
    public CCLabel(ImageReference ir){
        super(ir);
        this.initComponent();
    }
    
    public CCLabel(String s, int altura){
        super(s);
        this.initComponent();
        
        ColumnLayoutData cLabelLD = new ColumnLayoutData();
        cLabelLD.setAlignment(new Alignment(Alignment.LEFT,Alignment.CENTER));
        cLabelLD.setHeight(new Extent(altura, Extent.PX));
        this.setLayoutData(cLabelLD);
    }
    
    // Inicializa el componente
    private void initComponent(){
         this.setStyleName("Default");
    }

}
