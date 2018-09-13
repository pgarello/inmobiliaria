package app.combos;

import java.util.List;

import app.beans.Combo;

import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.layout.ColumnLayoutData;

import ccecho2.complex.ComboList.ComboList;
import ccecho2.complex.ComboList.ComboListItem;

import echopointng.util.ColorKit;


/**
 *
 * @author pgarello
 */
@SuppressWarnings("serial")
public class ComboGenerico extends ComboList {
    
	ComboListItem[] lCombos = null;
	
	public ComboGenerico(int ancho, int alto, int index, boolean foco, List<Combo> datos) {
        
		super();
        
        //this.setWidth(new Extent(ancho, Extent.PX));
		this.getTextField().setWidth(new Extent(ancho, Extent.PX));
        
        ColumnLayoutData cLabelLD = new ColumnLayoutData();
        cLabelLD.setAlignment(new Alignment(Alignment.LEFT,Alignment.CENTER));
        cLabelLD.setHeight(new Extent(alto, Extent.PX));
        this.setLayoutData(cLabelLD);
        
        //this.setFocusTraversalIndex(index);
        this.setFocusTraversalParticipant(foco);        
        
               
	   // Asigno los datos
	   try {
	    		
		   lCombos = new ComboListItem[datos.size()];
	       for (int i = 0; i < datos.size(); i++ ) {
	    	   Combo obj = datos.get(i);
	    	   String cadena = obj.getSDescripcion();	    	   
	    	   lCombos[i] = new ComboListItem(cadena, obj.getSValor());
	       }
	    		
	   } catch(Exception e) {
		   e.printStackTrace();
	   }
         
       LlenarCombo(lCombos);
       this.setEnabled(true);
        
    }
	
	public void ReiniciarCombo() {
		
		this.lCombos = null;
		
	}
	
	
	public void asignarDatosCombo(List<Combo> datos) {
		
		lCombos = new ComboListItem[datos.size()];
		for (int i = 0; i < datos.size(); i++ ) {
			Combo obj = datos.get(i);
			String cadena = obj.getSDescripcion();
			boolean seleccionado = obj.getBDefecto();
			
			lCombos[i] = new ComboListItem(cadena, obj.getSValor(), "", seleccionado);
		}
		LlenarCombo(lCombos);
		
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