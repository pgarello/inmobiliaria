/*
 * WindowPaneExitable.java
 *
 * Created on 25 de abril de 2007, 11:13
 *
 */

package ccecho2.complex;

import ccecho2.base.CCButton;
import ccecho2.base.CCContentPane;
import ccecho2.base.CCContentPaneEx;
import ccecho2.base.CCSplitPane;
import ccecho2.base.CCRow;
import ccecho2.base.CCWindowPane;
import echopointng.ExtentEx;
import framework.ui.principal.FWContentPanePrincipal;

import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.ApplicationInstance;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.ResourceImageReference;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.WindowPaneEvent;
import nextapp.echo2.app.event.WindowPaneListener;


/**
 *
 * @author luigi
 * modifico pgarello
 */
@SuppressWarnings("serial")
public class WindowPaneExitable extends CCWindowPane
        implements nextapp.echo2.app.event.ActionListener, WindowPaneListener {
    
    /** Creates a new instance of NavWindow */
    protected CCSplitPane splitPane;
    protected CCContentPaneEx cpPrincipal;
    
    private CCSplitPane spBotones;
    protected CCContentPane cpBotones;
    protected CCRow rBotones;
    protected CCContentPane cpBotonesExit;
    protected CCRow rBotonesExit;
    protected CCButton bExit;

    
    
    public WindowPaneExitable() {
        this.setStyleName("Default");
        this.setClosable(true);
        this.setWidth(new Extent(400, Extent.PX));
        this.setHeight(new Extent(300, Extent.PX));
        
        this.splitPane = new CCSplitPane(CCSplitPane.ORIENTATION_VERTICAL_BOTTOM_TOP, new ExtentEx("40px"));

        
        this.cpPrincipal = new CCContentPaneEx();
        this.spBotones = new CCSplitPane(CCSplitPane.ORIENTATION_HORIZONTAL_RIGHT_LEFT, new ExtentEx("80px"));
        
        this.cpBotones = new CCContentPane();
        this.cpBotones.setBackground(Color.DARKGRAY);
        this.cpBotonesExit = new CCContentPane();
        this.cpBotonesExit.setBackground(Color.DARKGRAY);

        this.rBotones = new CCRow();
        this.rBotones.setBackground(Color.DARKGRAY);
        this.rBotones.setAlignment(new Alignment(Alignment.CENTER,Alignment.CENTER));
        this.rBotones.setInsets(new Insets(5));

        this.rBotonesExit = new CCRow();
        this.rBotonesExit.setBackground(Color.DARKGRAY);
        this.rBotonesExit.setAlignment(new Alignment(Alignment.CENTER,Alignment.CENTER));
        this.rBotonesExit.setInsets(new Insets(5));

        this.bExit = new CCButton(new ResourceImageReference("/resources/crystalsvg22x22/actions/exit.png"));
        this.rBotonesExit.add(this.bExit);
        
        this.splitPane.setResizable(false); 

        this.bExit.setActionCommand("exit");
        this.bExit.addActionListener(this);
        this.bExit.setToolTipText("Salir del Formulario");


        this.cpBotones.add(this.rBotones);
        this.cpBotonesExit.add(this.rBotonesExit);
        
        this.spBotones.add(this.cpBotonesExit);
        this.spBotones.add(this.cpBotones);
        this.splitPane.add(this.spBotones);

        this.splitPane.add(this.cpPrincipal);
        
        this.add(this.splitPane);
        this.splitPane.setSeparatorHeight(new Extent(2));
        
        this.addWindowPaneListener(this);
    }
    
    
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("exit")){
        	((FWContentPanePrincipal) ApplicationInstance.getActive().getDefaultWindow().getContent()).cerrarVentana(this);
            //((CCContentPane) getParent()).remove(this);
        } 
    }


	public void windowPaneClosing(WindowPaneEvent arg0) {
		//System.out.println("Cerrando Ventana");
		//((CCContentPane) getParent()).remove(this);
		((FWContentPanePrincipal) ApplicationInstance.getActive().getDefaultWindow().getContent()).cerrarVentana();		
	}
}
