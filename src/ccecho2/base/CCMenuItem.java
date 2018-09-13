/*
 * CCMenuItem.java
 *
 * Created on 26 de septiembre de 2006, 18:19
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
public class CCMenuItem extends echopointng.MenuItem {
    
	private static final long serialVersionUID = 4512760617002325837L;
	
	/** Creates a new instance of CCMenuItem */
    private String command;
    
    private boolean seguridad;
    
    
    /**
     * CONSTRUCTORES
     */
    
    public CCMenuItem() {
        super();
        this.initComponent();
    }
    
    public CCMenuItem(String text) {
        super(text);
        seguridad = false;
        this.initComponent();
    }
    
    public CCMenuItem(String text, nextapp.echo2.app.ImageReference icon) {
        super(text, icon);
        this.initComponent();
    }

    public CCMenuItem(nextapp.echo2.app.ImageReference icon) {
        super(icon);
        this.initComponent();
    }

    
    /**
     * GET/SET
     * @return
     */
    
    
    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }
    
    public boolean isSeguridad() {
		return seguridad;
	}

	public void setSeguridad(boolean seguridad) {
		this.seguridad = seguridad;
	}

	
	/**
	 * LOGICA
	 */
	
	// Inicializa el componente
    private void initComponent(){
        
    	//this.setStyleName("Default");
    	this.setStyleName(null);
    	
        //this.setBackground(Color.BLUE);
    	this.setBorder(BorderEx.NONE);
     	
        //Font oFont = new Font(Font.VERDANA, Font.BOLD, new Extent(9, Extent.PX));
     	//this.setFont(oFont);
     	
		this.setFont(new Font(Font.VERDANA, Font.PLAIN, new Extent(8, Extent.PT)));
		this.setRolloverFont(new Font(Font.VERDANA, Font.PLAIN, new Extent(8, Extent.PT)));

     	
     	this.setHeight(new Extent(14, Extent.PX));
     	
     	//Border oBorde = new Border(new Extent(0, Extent.PX), Color.WHITE, Border.STYLE_NONE);
     	//this.setBorder(oBorde);
     	
         
    }

}
