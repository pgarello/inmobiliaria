/*
 * CCRow.java
 *
 * Created on 17 de abril de 2007, 08:39
 *
 */

package ccecho2.base;

import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Row;
import nextapp.echo2.app.layout.ColumnLayoutData;

/**
 *
 * @author luigi
 */
@SuppressWarnings("serial")
public class CCRow extends Row {
    
    /** Creates a new instance of CCRow */
    public CCRow() {
        super();
        this.initComponent();
    }

    /** 
     * Constructor usado para insertar la fila dentro de una columna
     */
    public CCRow(int altura) {
        super();
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
