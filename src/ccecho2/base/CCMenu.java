/*
 * CCMenu.java
 *
 * Created on 26 de septiembre de 2006, 18:14
 *
 */

package ccecho2.base;

import echopointng.BorderEx;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Font;

/**
 *
 * @author SShadow
 */

public class CCMenu extends echopointng.Menu {
    
	private static final long serialVersionUID = 1L;

	/** Creates a new instance of CCMenu */
    public CCMenu() {
        super();
        this.initComponent();
    }
    
    public CCMenu(String text) {
        super(text);
        this.initComponent();
    }
    
    public CCMenu(String text, nextapp.echo2.app.ImageReference icon) {
        super(text, icon);
        this.initComponent();
    }

    public CCMenu(nextapp.echo2.app.ImageReference icon) {
        super(icon);
        this.initComponent();
    }

    // Inicializa el componente
    private void initComponent(){
    	
    	//this.setStyleName("Default");
    	this.setStyleName(null);
    	
    	//this.setBackground(Color.BLUE);
    	//Font oFont = new Font(Font.VERDANA, Font.BOLD, new Extent(9, Extent.PX));
    	//this.setFont(oFont);
    	
    	this.setBorder(BorderEx.NONE);
    	
		this.setFont(new Font(Font.VERDANA, Font.PLAIN, new Extent(8, Extent.PT)));
		this.setRolloverFont(new Font(Font.VERDANA, Font.PLAIN, new Extent(8, Extent.PT)));

    	
    	this.setHeight(new Extent(14, Extent.PX));
    	
        this.setMenuAlwaysOnTop(true);
    }
}
