package app.combos;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.layout.ColumnLayoutData;
import nextapp.echo2.app.layout.RowLayoutData;
import ccecho2.complex.ComboList.ComboList;
import ccecho2.complex.ComboList.ComboListItem;
import datos.contrato.ContratoFacade;
import datos.localidad.Localidad;
import datos.localidad.LocalidadFacade;
import echopointng.util.ColorKit;

/**
 *
 * @author pablo
 */
@SuppressWarnings("serial")
public class ComboAnios extends ComboList {
    
	/**
	 * Combo con los años, el año del día. Mas, menos 2.
	 * @param ancho
	 * @param alto
	 * @param index
	 * @param foco
	 * @param layout 1: COLUMN -- 2: ROW
	 */
	public ComboAnios(int ancho, int alto, int index, boolean foco, short layout) {
        
		super();
        
        //this.setWidth(new Extent(ancho, Extent.PX));
        this.getTextField().setWidth(new Extent(50, Extent.PX));
        
        // Obtengo los datos según el año de la fecha de hoy +- 2.
        Calendar oHoy = new GregorianCalendar();
        
        int anio_actual = oHoy.get(Calendar.YEAR);
        
        ComboListItem[] lCombos = new ComboListItem[6];
        
        lCombos[0] = new ComboListItem("--", "0");
        lCombos[1] = new ComboListItem(Integer.toString(anio_actual-2), Integer.toString(anio_actual-2));
        lCombos[2] = new ComboListItem(Integer.toString(anio_actual-1), Integer.toString(anio_actual-1));
        lCombos[3] = new ComboListItem(Integer.toString(anio_actual), Integer.toString(anio_actual));
        lCombos[4] = new ComboListItem(Integer.toString(anio_actual+1), Integer.toString(anio_actual+1));
        lCombos[5] = new ComboListItem(Integer.toString(anio_actual+2), Integer.toString(anio_actual+2));
        
        LlenarCombo(lCombos);
        
        this.setSelectedText(0);
        
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
	
	ComboListItem[] lCombos = null;
	
	/**
	 * Desde la base de datos - tabla ¿? 
	 * @param ancho
	 * @param alto
	 * @param foco
	 * @param layout
	 */
	public ComboAnios(int ancho, int alto, boolean foco, short layout) {
        
		super();
        
        //this.setWidth(new Extent(ancho, Extent.PX));
        this.getTextField().setWidth(new Extent(50, Extent.PX));
        
        // Obtengo los datos según el año de la fecha de hoy +- 2.
        Calendar oHoy = new GregorianCalendar();        
        int anio_actual = oHoy.get(Calendar.YEAR);
        
        //      Aca leo los datos
    	try {
    		List<Short> anios = ContratoFacade.findPeriodoAnio();
    		
    		lCombos = new ComboListItem[anios.size()+1];
    		lCombos[0] = new ComboListItem("--", "0");
        	for (int i = 0; i < anios.size(); i++ ) {
        		Short obj = anios.get(i);
        		lCombos[i+1] = new ComboListItem(Short.toString(obj), Short.toString(obj));
        	}
    		
    	} catch(Exception e) {
    		e.printStackTrace();
    	}        
        
        LlenarCombo(lCombos);
        
        this.setSelectedText(Integer.toString(anio_actual));
        
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
