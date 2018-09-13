package app.combos;

import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.layout.ColumnLayoutData;
import ccecho2.complex.ComboList.ComboList;
import ccecho2.complex.ComboList.ComboListItem;
import echopointng.util.ColorKit;

@SuppressWarnings("serial")
public class ComboImpuesto extends ComboList {
    
	
	public ComboImpuesto(int ancho, int alto) {
		
        super();
        
        ComboListItem[] lCombos = new ComboListItem[5];
        
        lCombos[0] = new ComboListItem("Tasa General de Inmueble", "1", "TGI");
        lCombos[1] = new ComboListItem("Impuesto Inmobiliario", "2", "API");
        lCombos[2] = new ComboListItem("Aguas Santafesinas", "3", "ASSA");
        lCombos[3] = new ComboListItem("Expensas", "4", "EXP");
        lCombos[4] = new ComboListItem("Varios", "5", "VARIO");
                
        LlenarCombo(lCombos);
        
        
        ColumnLayoutData cLabelLD = new ColumnLayoutData();
        cLabelLD.setAlignment(new Alignment(Alignment.LEFT,Alignment.CENTER));
        cLabelLD.setHeight(new Extent(alto, Extent.PX));
        this.setLayoutData(cLabelLD);
        
        this.getTextField().setWidth(new Extent(ancho, Extent.PX));
        
        this.setEnabled(true);
        
    }
	
	
	public void setEnabled(boolean enable) {
    	
    	if (enable) {    		
    		this.setBackground(ColorKit.clr("#ffffdf"));
    	} else {
    		this.setBackground(Color.LIGHTGRAY);
    	}
    	
    	super.setEnabled(enable);
    	
    }
	
	
	public static String getImpuesto(short id) {
		String impuesto = "ERROR";
		switch (id) {
		case 1: 
			impuesto = "TGI";
			break;

		case 2: 
			impuesto = "API";
			break;
			
		case 3: 
			impuesto = "ASSA";
			break;
		
		case 4: 
			impuesto = "EXP";
			break;
			
		case 5: 
			impuesto = "VARIO";
			break;
			
		default:
			break;
		}
		
		return impuesto;
	}
	
}