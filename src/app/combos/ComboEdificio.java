package app.combos;

import java.util.List;

import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.layout.ColumnLayoutData;

import ccecho2.complex.ComboList.ComboList;
import ccecho2.complex.ComboList.ComboListItem;
import datos.edificio.Edificio;
import datos.edificio.EdificioFachada;

import echopointng.util.ColorKit;


/**
 *
 * @author pgarello
 */
@SuppressWarnings("serial")
public class ComboEdificio extends ComboList {
    
    /** Creates a new instance of TiposDocumento /
	public ComboTiposDocumento() {
        super("sesiones.tipo_documento.TipoDocumentoFacadeRemote",
                "descripcion",
                "id");
    }
    */
	
	ComboListItem[] lCombos = null;
	
	public ComboEdificio(int ancho, int alto, int index, boolean foco) {
        super();
        
        //this.setWidth(new Extent(ancho, Extent.PX));
        
        ColumnLayoutData cLabelLD = new ColumnLayoutData();
        cLabelLD.setAlignment(new Alignment(Alignment.LEFT,Alignment.CENTER));
        cLabelLD.setHeight(new Extent(alto, Extent.PX));
        this.setLayoutData(cLabelLD);
        
        //this.setFocusTraversalIndex(index);
        this.setFocusTraversalParticipant(foco);        
        
        
        
        if (lCombos == null) {
        
	        // Aca leo los datos
	    	try {
	    		List<Edificio> edificios = EdificioFachada.findAll();
	    		
	    		lCombos = new ComboListItem[edificios.size()+1];	    		
	    		
	    		lCombos[0] = new ComboListItem("--", "0"); 		
	        	for (int i = 1; i < edificios.size()+1; i++ ) {
	        		Edificio obj = edificios.get(i-1);
	        		String cadena = obj.getDescripcion();
	        		lCombos[i] = new ComboListItem(cadena, "" +  obj.getIdEdificio());
	        	}
	    		
	    	} catch(Exception e) {
	    		e.printStackTrace();
	    	}
        
        }
        
        LlenarCombo(lCombos);
        this.setEnabled(true);
        
    }
	
	public void ReiniciarCombo() {
		
		this.lCombos = null;
		
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
