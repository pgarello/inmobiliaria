package ccecho2.base;

import java.util.Calendar;

import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.layout.ColumnLayoutData;

import echopointng.AbleProperties;
import echopointng.BorderEx;
import echopointng.DateField;
import echopointng.util.ColorKit;

public class CCDateField extends DateField {

	private static final long serialVersionUID = 1L;

	public CCDateField() {
		super();
		this.initComponent();
	}

	public CCDateField(Calendar arg0) {
		super(arg0);
		this.initComponent();
	}
	
	// ColumnLayoutData
	public CCDateField(int altura) {
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
        
        AbleProperties failureProperties = new AbleProperties();
        failureProperties.setBackground(ColorKit.clr("#F1C9C9"));
        failureProperties.setBorder(new BorderEx(Color.RED));

        
        this.getDateChooser().setYearSelectable(true);
        //System.out.println("Fecha: " + this.getDateChooser().getMaximumDate() + " - " + this.getDateChooser().getMinimumDate());
        
        this.setEnabled(true);
        //this.getTextField().setEnabled(false);
        
    }
    
    public void setEnabled(boolean enable) {
    	
    	if (enable) {    		
    		this.getTextField().setBackground(ColorKit.clr("#ffffdf"));
    	} else {
    		this.getTextField().setBackground(Color.LIGHTGRAY);
    	}
    		
    	super.setEnabled(enable);
    	
    }
	
}
