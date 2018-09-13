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
 *
 * @author luigi
 */
@SuppressWarnings("serial")
public class ComboTiposDocumento extends ComboList {
    
    /** Creates a new instance of TiposDocumento /
	public ComboTiposDocumento() {
        super("sesiones.tipo_documento.TipoDocumentoFacadeRemote",
                "descripcion",
                "id");
    }
    */
	
	public ComboTiposDocumento(int ancho, int alto, int index, boolean foco) {
        super();
        
        //this.setWidth(new Extent(ancho, Extent.PX));
        this.getTextField().setWidth(new Extent(50, Extent.PX));
        
        ComboListItem[] lCombos = new ComboListItem[4];
        lCombos[0] = new ComboListItem("DNI", "1");
        lCombos[1] = new ComboListItem("LC", "2");
        lCombos[2] = new ComboListItem("LE", "3");
        lCombos[3] = new ComboListItem("PASS", "4");
        
        LlenarCombo(lCombos);
        
        this.setSelectedText(1);
        
        ColumnLayoutData cLabelLD = new ColumnLayoutData();
        cLabelLD.setAlignment(new Alignment(Alignment.LEFT,Alignment.CENTER));
        cLabelLD.setHeight(new Extent(alto, Extent.PX));
        this.setLayoutData(cLabelLD);
        
        //this.setFocusTraversalIndex(index);
        this.setFocusTraversalParticipant(foco);
        
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
