/*
 * CCTextField.java
 *
 * Created on 2 de marzo de 2007, 09:53
 *
 */

package ccecho2.base;

import ccecho2.base.interfaces.DataSourceLinkeable;
import echopointng.AbleProperties;
import echopointng.BorderEx;
import echopointng.TextFieldEx;
import echopointng.util.ColorKit;
import java.util.Hashtable;

import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.Border;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.layout.ColumnLayoutData;
import nextapp.echo2.app.layout.RowLayoutData;

public class CCTextField extends TextFieldEx implements DataSourceLinkeable {
	
	private static final long serialVersionUID = 1L;
	
	private String field = "";
    private Hashtable dataSource = null;
    
    /** Creates a new instance of CCTextField */
    public CCTextField() {
        super();
        this.initComponent();
    }

    /**
     * Este contructor se utiliza cuando el objeto contenedor es un COLUMN
     * @param ancho PX
     * @param alto PX
     * @param index índice para el foco
     * @param foco booleana q especifica si el objeto recibe el foco
     */
    public CCTextField(int ancho, int alto, int index, boolean foco) {
        super();
        this.initComponent();
        
        this.setWidth(new Extent(ancho, Extent.PX));
        
        ColumnLayoutData cLabelLD = new ColumnLayoutData();
        cLabelLD.setAlignment(new Alignment(Alignment.LEFT,Alignment.CENTER));
        cLabelLD.setHeight(new Extent(alto, Extent.PX));
        this.setLayoutData(cLabelLD);
                
        //this.setFocusTraversalIndex(index);
        this.setFocusTraversalParticipant(foco);
        
    }    
    
    
    /**
     * Este contructor se utiliza cuando el objeto contenedor es un ROW
     * @param ancho PX
     * @param foco booleana q especifica si el objeto recibe el foco
     */
    public CCTextField(int ancho, boolean foco) {
        super();
        this.initComponent();
        
        this.setWidth(new Extent(ancho, Extent.PX));
        
        RowLayoutData cLabelLD = new RowLayoutData();
        cLabelLD.setAlignment(new Alignment(Alignment.LEFT,Alignment.CENTER));
        this.setLayoutData(cLabelLD);
        
        this.setFocusTraversalParticipant(foco);
        
    }
    
    
    public String getField() {
        return this.field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Hashtable getDataSource() {
        return this.dataSource;
    }

    public void setDataSource(Hashtable dataSource) {
        this.dataSource = dataSource;
    }
    
    public boolean updateDataSource() {
        
        return true;
    }
    
    // Inicializa el componente
    private void initComponent(){
        this.setStyleName("Default");
        
        AbleProperties failureProperties = new AbleProperties();
        failureProperties.setBackground(ColorKit.clr("#F1C9C9"));
        failureProperties.setBorder(new BorderEx(Color.RED));

        this.setRegexFailureProperties(failureProperties);
        
        this.setDisabledBackground(Color.LIGHTGRAY);
        
        // Seteo los bordes
        Border oBorde = new Border(new Extent(1, Extent.PX), Color.DARKGRAY, Border.STYLE_INSET);
        this.setBorder(oBorde);
        
    }
    
    
    
    public void setEnabled(boolean enable) {
    	
    	if (enable) {    		
    		this.setBackground(ColorKit.clr("#ffffdf"));
    	} else {
    		this.setBackground(Color.LIGHTGRAY);
    	}
    	
    	super.setEnabled(enable);
    	
    }
    

}
