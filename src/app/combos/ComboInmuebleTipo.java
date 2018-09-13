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
 */
@SuppressWarnings("serial")
public class ComboInmuebleTipo extends ComboList {
    
	/**
	 * @param ancho Esta <b>fijo</b> en el constructor: ancho=150
	 * @param alto
	 * @param index
	 * @param foco
	 * @param valor_vacio
	 */
	public ComboInmuebleTipo(int ancho, int alto, int index, boolean foco, boolean valor_vacio) {
        super();
        
        //this.setWidth(new Extent(ancho, Extent.PX));
        this.getTextField().setWidth(new Extent(150, Extent.PX));
        
        short i = 0;
        ComboListItem[] lCombos = new ComboListItem[8];        
        if (valor_vacio) {
        	lCombos = new ComboListItem[9];
        	lCombos[i++] = new ComboListItem("", "0", "--");
        }
        
        lCombos[i++] = new ComboListItem("CASA", "1", "Casa");
        lCombos[i++] = new ComboListItem("COCHERA", "9", "Coch");
        lCombos[i++] = new ComboListItem("DEPARTAMENTO", "2", "Dpto");
        lCombos[i++] = new ComboListItem("GALPON", "8", "Galpón");
        lCombos[i++] = new ComboListItem("LOCAL COMERCIAL", "5", "Local");        
        lCombos[i++] = new ComboListItem("OFICINA", "6", "Ofic");        
        lCombos[i++] = new ComboListItem("QUINTA", "10", "Quinta");
        lCombos[i++] = new ComboListItem("TERRENO", "4", "Tno");        
        
        LlenarCombo(lCombos);
        
        if (!valor_vacio) this.setSelectedText(1);
        
        ColumnLayoutData cLabelLD = new ColumnLayoutData();
        cLabelLD.setAlignment(new Alignment(Alignment.LEFT,Alignment.CENTER));
        cLabelLD.setHeight(new Extent(alto, Extent.PX));
        this.setLayoutData(cLabelLD);
        
        //this.setFocusTraversalIndex(index);
        this.setFocusTraversalParticipant(foco);
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
