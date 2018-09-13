package app.combos;

//import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.layout.ColumnLayoutData;
import ccecho2.complex.ComboList.ComboList;
import ccecho2.complex.ComboList.ComboListItem;

@SuppressWarnings("serial")
public class ComboNovedadTipo extends ComboList {
    
	
	public ComboNovedadTipo(int ancho, int alto, int index, boolean foco) {
        super();
        
        this.setWidth(new Extent(ancho, Extent.PX));
        
        ComboListItem[] lCombos = new ComboListItem[5];
        
        lCombos[0] = new ComboListItem("Alquiler", "1");
        lCombos[1] = new ComboListItem("Comisión Alquiler", "2");
        lCombos[2] = new ComboListItem("Comisión Contrato", "3");
        lCombos[3] = new ComboListItem("Comisión Venta", "4");
        lCombos[4] = new ComboListItem("Impuestos", "5");
        
        LlenarCombo(lCombos);
        
        ColumnLayoutData cLabelLD = new ColumnLayoutData();
        cLabelLD.setAlignment(new Alignment(Alignment.LEFT,Alignment.CENTER));
        cLabelLD.setHeight(new Extent(alto, Extent.PX));
        this.setLayoutData(cLabelLD);
        
        //this.setFocusTraversalIndex(index);
        this.setFocusTraversalParticipant(foco);
        
    }
		
}