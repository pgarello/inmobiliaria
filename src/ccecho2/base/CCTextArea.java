package ccecho2.base;

import echopointng.util.ColorKit;

import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.Border;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Font;
import nextapp.echo2.app.TextArea;
import nextapp.echo2.app.layout.ColumnLayoutData;

public class CCTextArea extends TextArea {
	
	private static final long serialVersionUID = 1L;
	    
    /** Creates a new instance of CCTextField */
    public CCTextArea() {
        super();
        this.initComponent();
    }

    /**
     * @param ancho PX
     * @param alto PX
     * @param cant_lineas 
     * @param foco booleana q especifica si el objeto recibe el foco
     */
    public CCTextArea(int ancho, int alto, int cant_lineas, boolean foco) {
        super();
        this.initComponent();
        
        this.setWidth(new Extent(ancho, Extent.PX));
        
        ColumnLayoutData cLabelLD = new ColumnLayoutData();
        cLabelLD.setAlignment(new Alignment(Alignment.LEFT,Alignment.CENTER));
        cLabelLD.setHeight(new Extent(cant_lineas * 22, Extent.PX));
        //cLabelLD.setHeight(new Extent(alto, Extent.PX));
        
        this.setLayoutData(cLabelLD);
        
        this.setHeight(new Extent(cant_lineas * 20, Extent.PX));
        
        this.setFont(new Font(Font.VERDANA, Font.PLAIN, new Extent(12, Extent.PX)));
        
        //this.setFocusTraversalIndex(index);
        this.setFocusTraversalParticipant(foco);
        
    }    
    
        
    // Inicializa el componente
    private void initComponent(){
        this.setStyleName("Default");
                
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