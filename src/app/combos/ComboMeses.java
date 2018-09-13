/*
 * TiposDocumento.java
 *
 * Created on 26 de junio de 2007, 10:45
 *
 */

package app.combos;

//import nextapp.echo2.app.Extent;
import java.util.Calendar;
import java.util.GregorianCalendar;

import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.layout.ColumnLayoutData;
import nextapp.echo2.app.layout.RowLayoutData;
import ccecho2.complex.ComboList.ComboList;
import ccecho2.complex.ComboList.ComboListItem;
import echopointng.util.ColorKit;

/**
 *
 * @author luigi
 */
@SuppressWarnings("serial")
public class ComboMeses extends ComboList {
    
	/**
	 * Combo con los meses del año
	 * @param ancho
	 * @param alto
	 * @param index
	 * @param foco
	 * @param layout 1: COLUMN -- 2: ROW
	 */
	public ComboMeses(int ancho, int alto, int index, boolean foco, short layout) {
        super();
        
        //this.setWidth(new Extent(ancho, Extent.PX));
        this.getTextField().setWidth(new Extent(80, Extent.PX));
        
        ComboListItem[] lCombos = new ComboListItem[13];
        
        lCombos[0] = new ComboListItem("--", "0");
        
        lCombos[1] = new ComboListItem("Enero", "1");
        lCombos[2] = new ComboListItem("Febrero", "2");
        lCombos[3] = new ComboListItem("Marzo", "3");
        lCombos[4] = new ComboListItem("Abril", "4");
        lCombos[5] = new ComboListItem("Mayo", "5");
        lCombos[6] = new ComboListItem("Junio", "6");
        lCombos[7] = new ComboListItem("Julio", "7");
        lCombos[8] = new ComboListItem("Agosto", "8");
        lCombos[9] = new ComboListItem("Septiembre", "9");
        lCombos[10] = new ComboListItem("Octubre", "10");
        lCombos[11] = new ComboListItem("Noviembre", "11");
        lCombos[12] = new ComboListItem("Diciembre", "12");        
        
        LlenarCombo(lCombos);
        
        // Mes actual
        Calendar oHoy = new GregorianCalendar();        
        int mes_actual = oHoy.get(Calendar.MONTH);

        this.setSelectedText(mes_actual+1);
        
        if (layout == 1) {
        	ColumnLayoutData cColumnLD = new ColumnLayoutData();
        	cColumnLD.setAlignment(new Alignment(Alignment.LEFT,Alignment.CENTER));
        	cColumnLD.setHeight(new Extent(alto, Extent.PX));
        	this.setLayoutData(cColumnLD);
        } else if(layout == 2) {
        	RowLayoutData cRowLD = new RowLayoutData();
        	cRowLD.setAlignment(new Alignment(Alignment.RIGHT,Alignment.CENTER));
        	this.setLayoutData(cRowLD);
        } 
        
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
