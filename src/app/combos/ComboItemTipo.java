/*
 * TiposDocumento.java
 *
 * Created on 26 de junio de 2007, 10:45
 *
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
 * Tener en cuenta que para una nueva versión tendriamos que levantar los datos de una tabla
 * pgarello 11-01-2008
 * 
 * Son solamente los items que deja agregar a un RECIBO COBRO/PAGO
 * 
 */
@SuppressWarnings("serial")
public class ComboItemTipo extends ComboList {
    	
	public ComboItemTipo(int ancho, int alto) {
        super();
        
        //this.setWidth(new Extent(ancho, Extent.PX));
        this.getTextField().setWidth(new Extent(ancho, Extent.PX));
        
        ComboListItem[] lCombos = new ComboListItem[6];        
        
        lCombos[0] = new ComboListItem("COMISION ALQ.", "2", "CAlq");
        lCombos[1] = new ComboListItem("COMISION CONT.", "3", "CCon");
        lCombos[2] = new ComboListItem("IMPUESTOS", "5", "Imp");
        lCombos[3] = new ComboListItem("INTERESES", "7", "Int");        
        lCombos[4] = new ComboListItem("IVA", "6", "IVA");
        lCombos[5] = new ComboListItem("VARIOS", "8", "Var");
        
        LlenarCombo(lCombos);
        
        this.setSelectedText(1);
        
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