/*
 * WindowPaneExitable.java
 *
 * Created on 25 de abril de 2007, 11:13
 *
 */

package ccecho2.complex;

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
import nextapp.echo2.app.event.ActionEvent;
//import nextapp.echo2.app.layout.SplitPaneLayoutData;

/**
 *
 * @author luigi
 */
@SuppressWarnings("serial")
public class BasicSplitWindow extends CCWindowPane
        implements nextapp.echo2.app.event.ActionListener {
    
    /** Creates a new instance of NavWindow */
    private     CCSplitPane         splitPane;
    protected   CCContentPaneEx     cpPrincipal;
    protected   CCContentPaneEx     cpBotones;
    protected   CCRow               rBotones;

    
    
    public BasicSplitWindow() {
        this.setStyleName("Default");
        this.setClosable(true);
        this.setWidth(new Extent(500, Extent.PX));
        this.setHeight(new Extent(500, Extent.PX));
        
        this.splitPane = new CCSplitPane(CCSplitPane.ORIENTATION_VERTICAL_BOTTOM_TOP, new ExtentEx("40px"));
        
        this.cpPrincipal = new CCContentPaneEx();
        //SplitPaneLayoutData cpPrincipalLayoutData = new SplitPaneLayoutData();
        //cpPrincipalLayoutData.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
        //cpPrincipal.setLayoutData(cpPrincipalLayoutData);
        
        
        this.cpBotones = new CCContentPaneEx();
        this.cpBotones.setBackground(Color.DARKGRAY);

        this.rBotones = new CCRow();
        this.rBotones.setBackground(Color.DARKGRAY);
        this.rBotones.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));
        
        this.splitPane.setResizable(false); 
        this.cpBotones.add(this.rBotones);
        
        this.splitPane.add(this.cpBotones);
        this.splitPane.add(this.cpPrincipal);
        this.add(this.splitPane);
        this.splitPane.setSeparatorHeight(new Extent(2));
    }
    
    
    public void actionPerformed(ActionEvent e) {
    	
    	if (e.getActionCommand().equals("cancel")){
    		((FWContentPanePrincipal) ApplicationInstance.getActive().getDefaultWindow().getContent()).cerrarVentana(this);
        }
    	
    }
}
