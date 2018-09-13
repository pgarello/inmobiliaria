/*
 * WindowPaneExitable.java
 *
 * Created on 25 de abril de 2007, 11:13
 *
 */

package ccecho2.complex;

import ccecho2.base.CCButton;
import ccecho2.base.CCContentPane;
import ccecho2.base.CCLabel;
import framework.ui.principal.FWContentPanePrincipal;
import java.lang.reflect.Method;
import nextapp.echo2.app.ApplicationInstance;
import nextapp.echo2.app.Extent;

import nextapp.echo2.app.ResourceImageReference;
import nextapp.echo2.app.event.ActionEvent;

/**
 *
 * @author luigi
 */
@SuppressWarnings("serial")
public class MessageWindowPane extends BasicSplitWindow
        implements nextapp.echo2.app.event.ActionListener {
    
    /** Creates a new instance of NavWindow */
    protected CCButton bAccept;
    protected CCButton bCancel;
    
    Object obj = null;
    String method = "";
    
    public MessageWindowPane(String message, Object obj, String method) {
        this.obj = obj;
        this.method = method;

        this.setModal(true);
        this.setZIndex(999);
        this.setClosable(false);
        
        int ancho = message.length() * 10;
        
        this.setWidth(new Extent(ancho, Extent.PX));
        this.setHeight(new Extent(200, Extent.PX));

        this.cpPrincipal.add(new CCLabel(message));

        this.bAccept = new CCButton(new ResourceImageReference("/resources/crystalsvg22x22/actions/button_ok.png"));
        this.bCancel = new CCButton(new ResourceImageReference("/resources/crystalsvg22x22/actions/button_cancel.png"));
        this.rBotones.add(this.bAccept);
        this.rBotones.add(this.bCancel);
        
        this.bAccept.setActionCommand("accept");
        this.bAccept.addActionListener(this);

        this.bCancel.setActionCommand("cancel");
        this.bCancel.addActionListener(this);
               
        ((FWContentPanePrincipal) ApplicationInstance
                .getActive().getDefaultWindow().getContent())
                .abrirVentanaMensaje(this);
        
        //ApplicationInstance.getActive().setFocusedComponent(bAccept);
    }

    public MessageWindowPane(String message) {
        this.setModal(true);
        this.setZIndex(999);
        this.setClosable(false);
        
        int ancho = message.length() * 10;
        
        this.setWidth(new Extent(ancho, Extent.PX));
        this.setHeight(new Extent(200, Extent.PX));

        this.cpPrincipal.add(new CCLabel(message));

        this.bAccept = new CCButton(new ResourceImageReference("/resources/crystalsvg22x22/actions/button_ok.png"));
        this.rBotones.add(this.bAccept);

        this.bAccept.setActionCommand("cancel");
        this.bAccept.addActionListener(this);

        ((FWContentPanePrincipal) ApplicationInstance
                .getActive().getDefaultWindow().getContent())
                .abrirVentanaMensaje(this);
    }

    @SuppressWarnings("unchecked")
	public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("cancel")) {
            ((CCContentPane) getParent()).remove(this);
        } else if (e.getActionCommand().equals("accept")) {
            try {
                Class myclass = obj.getClass();
                Method method = myclass.getMethod(this.method);
                method.invoke(this.obj);
            } catch(Exception ex) {
                ex.printStackTrace();
            }
            ((CCContentPane) getParent()).remove(this);
        }
    }
}


