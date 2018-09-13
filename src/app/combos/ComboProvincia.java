/*
 * ComboProvincia.java
 */

package app.combos;

//import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.layout.ColumnLayoutData;
import ccecho2.complex.ComboList.ComboList;
import ccecho2.complex.ComboList.ComboListItem;
import echopointng.util.ColorKit;

/**
 * pgarello 22-02-2009
 */
@SuppressWarnings("serial")
public class ComboProvincia extends ComboList {
    	
	public ComboProvincia(int ancho, int alto) {
        super();
        
        //this.setWidth(new Extent(ancho, Extent.PX));
        this.getTextField().setWidth(new Extent(ancho, Extent.PX));
        
        ComboListItem[] lCombos = new ComboListItem[23];
                
        lCombos[0] = 	new ComboListItem("Buenos Aires", "4", "BA");
        lCombos[1] = 	new ComboListItem("Catamarca", "21", "");
        lCombos[2] = 	new ComboListItem("Chaco", "5", "");
        lCombos[3] = 	new ComboListItem("Chubut", "16", "");
        lCombos[4] = 	new ComboListItem("Corrientes", "6", "Corr");
        lCombos[5] = 	new ComboListItem("Córdoba", "1", "Cba");
        lCombos[6] = 	new ComboListItem("Entre Ríos", "2", "ER");   
        lCombos[7] = 	new ComboListItem("Formosa", "8", "");    
        lCombos[8] = 	new ComboListItem("Jujuy", "18", "");
        lCombos[9] = 	new ComboListItem("La Pampa", "10", "LPp");
        lCombos[10] = 	new ComboListItem("La Rioja", "22", "LRj");
        lCombos[11] = 	new ComboListItem("Mendoza", "11", "Mdza");
        lCombos[12] = 	new ComboListItem("Misiones", "7", "Mnes");
        lCombos[13] = 	new ComboListItem("Neuquén", "12", "");
        lCombos[14] = 	new ComboListItem("Rio Negro", "15", "");
        lCombos[15] = 	new ComboListItem("Salta", "19", "");
        lCombos[16] = 	new ComboListItem("San Luis", "13", "");
        lCombos[17] = 	new ComboListItem("San Juan", "14", "");
        lCombos[18] = 	new ComboListItem("Santa Cruz", "17", "StaCr");
        lCombos[19] = 	new ComboListItem("Santa Fe", "3", "StaFe");
        lCombos[20] = 	new ComboListItem("Sgo. del Estero", "9", "");
        lCombos[21] = 	new ComboListItem("Tierra del Fuego", "23", "");
        lCombos[22] = 	new ComboListItem("Tucumán", "20", "");
                
        LlenarCombo(lCombos);
        
        this.setSelectedText(3);
        
        ColumnLayoutData cLabelLD = new ColumnLayoutData();
        cLabelLD.setAlignment(new Alignment(Alignment.LEFT,Alignment.CENTER));
        cLabelLD.setHeight(new Extent(alto, Extent.PX));
        this.setLayoutData(cLabelLD);
        
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
	
}
