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
public class ComboGANANCIAS extends ComboList {
    
	public static short NoInscripto = 1; // valor por defecto de la BD
	public static short Inscripto = 2;
	public static short Exento = 3;
	public static short Monotributo = 4;
	
	
	public ComboGANANCIAS(int ancho, int alto, int index, boolean foco, boolean valor_vacio) {
        super();
        
        this.setWidth(new Extent(ancho, Extent.PX));
        
        short i = 0;
        ComboListItem[] lCombos = new ComboListItem[4];
        if (valor_vacio) {
        	lCombos = new ComboListItem[5];
        	lCombos[i++] = new ComboListItem("", "0", "");
        }
        
        
        lCombos[i++] = new ComboListItem("NO INSCRIPTO", "1");
        lCombos[i++] = new ComboListItem("INSCRIPTO", "2");
        lCombos[i++] = new ComboListItem("EXENTO", "3");
        lCombos[i++] = new ComboListItem("MONOTRIBUTO", "4");
        
        LlenarCombo(lCombos);
        
        ColumnLayoutData cLabelLD = new ColumnLayoutData();
        cLabelLD.setAlignment(new Alignment(Alignment.LEFT,Alignment.CENTER));
        cLabelLD.setHeight(new Extent(alto, Extent.PX));
        this.setLayoutData(cLabelLD);
        
        // Seteo un valor por defecto
        if (!valor_vacio) this.setSelectedText(1);
        else this.setSelectedText(0);
        
        //this.setFocusTraversalIndex(index);
        this.setFocusTraversalParticipant(foco);
        
    }
	
	public static String getInscripcionIVAxID(short id) {
		
		String respuesta = "";
		switch (id) {
		case 1: 
			respuesta = "NO INSCRIPTO";
			break;
		case 2: 
			respuesta = "INSCRIPTO";
			break;
		case 3: 
			respuesta = "EXENTO";
			break;
		case 4: 
			respuesta = "MONOTRIBUTO";
			break;
			
		default:
			break;
		}
		
		return respuesta;
		
	}
	
			
}