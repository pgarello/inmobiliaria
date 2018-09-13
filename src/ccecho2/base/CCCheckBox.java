package ccecho2.base;


/**
 * @author pablo
 */
import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.CheckBox;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.layout.ColumnLayoutData;
import nextapp.echo2.app.layout.RowLayoutData;

@SuppressWarnings("serial")
public class CCCheckBox extends CheckBox {

	public CCCheckBox() {
		super();
		this.initComponent();
	}
	
	public CCCheckBox(String s) {
		super(s);
		this.initComponent();
		
		RowLayoutData cRowLD = new RowLayoutData();
    	cRowLD.setAlignment(new Alignment(Alignment.RIGHT,Alignment.CENTER));
    	this.setLayoutData(cRowLD);
		
	}

	/*
	public CCCheckBox(String s, int value) {
		super(s);
		this.initComponent();
		
		this.setActionCommand("" + value);
		
	}
	*/
	
	public CCCheckBox(String s, int altura) {
		super(s);
		this.initComponent();		
		
        ColumnLayoutData cLabelLD = new ColumnLayoutData();
        cLabelLD.setAlignment(new Alignment(Alignment.LEFT,Alignment.CENTER));
        cLabelLD.setHeight(new Extent(altura, Extent.PX));
        this.setLayoutData(cLabelLD);
		
	}
	
	
	// Inicializa el componente
    private void initComponent(){
        //this.setStyleName("Default");
    	this.setStyleName(null);
         
        // Seteo los bordes
        // Border oBorde = new Border(new Extent(0, Extent.PX), Color.DARKGRAY, Border.STYLE_INSET);
        // this.setBorder(oBorde);
         
    }
	
}
