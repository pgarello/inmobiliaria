/*
 * TiposDocumento.java
 *
 * Created on 26 de junio de 2007, 10:45
 *
 */

package app.combos;

//import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.layout.ColumnLayoutData;
import ccecho2.complex.ComboList.ComboList;
import ccecho2.complex.ComboList.ComboListItem;

/**
 *
 * @author pablo
 */
@SuppressWarnings("serial")
public class ComboInscripcionIVA extends ComboList {
    
	public static short ConsumidorFinal = 1;
	public static short Monotributo = 2;
	public static short ResponsableInscripto = 3;
	public static short Exento = 4;
	
	
	public ComboInscripcionIVA(int ancho, int alto, int index, boolean foco) {
        super();
        
        this.setWidth(new Extent(ancho, Extent.PX));
        
        ComboListItem[] lCombos = new ComboListItem[4];
        lCombos[0] = new ComboListItem("CONSUMIDOR FINAL", "1");
        lCombos[1] = new ComboListItem("MONOTRIBUTO", "2");
        lCombos[2] = new ComboListItem("RESPONSABLE INSCRIPTO", "3");
        lCombos[3] = new ComboListItem("EXENTO", "4");
        
        LlenarCombo(lCombos);
        
        ColumnLayoutData cLabelLD = new ColumnLayoutData();
        cLabelLD.setAlignment(new Alignment(Alignment.LEFT,Alignment.CENTER));
        cLabelLD.setHeight(new Extent(alto, Extent.PX));
        this.setLayoutData(cLabelLD);
        
        // Seteo un valor por defecto
        this.setSelectedText(1);
        
        //this.setFocusTraversalIndex(index);
        this.setFocusTraversalParticipant(foco);
        
    }
	
	public static String getInscripcionIVAxID(short id) {
		
		String respuesta = "";
		switch (id) {
		case 1: 
			respuesta = "CONSUMIDOR FINAL";
			break;
		case 2: 
			respuesta = "MONOTRIBUTO";
			break;
		case 3: 
			respuesta = "RESPONSABLE INSCRIPTO";
			break;
		case 4: 
			respuesta = "EXENTO";
			break;
			
		default:
			break;
		}
		
		return respuesta;
		
	}
	
		
}