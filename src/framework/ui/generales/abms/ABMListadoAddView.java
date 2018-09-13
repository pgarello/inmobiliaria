/*
 * NavWindow.java
 *
 * Created on 9 de febrero de 2007, 15:05
 *
 */

package framework.ui.generales.abms;

import ccecho2.base.CCButton;
import ccecho2.base.CCContentPane;

import ccecho2.base.interfaces.DataSourceLinkeable;
import ccecho2.complex.BasicSplitWindow;
import ccecho2.complex.MessageWindowPane;

import framework.ui.principal.FWContentPanePrincipal;
import java.util.Hashtable;

import java.util.Vector;
import nextapp.echo2.app.ApplicationInstance;
import nextapp.echo2.app.ImageReference;
import nextapp.echo2.app.ResourceImageReference;
import nextapp.echo2.app.event.ActionEvent;

/**
 *
 * @author SShadow
 */
public class ABMListadoAddView extends BasicSplitWindow {
    
	private static final long serialVersionUID = 1L;
	
	// Imagen del bot�n Ok
    private ImageReference OK = new ResourceImageReference("/resources/crystalsvg22x22/actions/button_ok.png");
    // Imagen del bot�n Cancel
    private ImageReference CANCEL = new ResourceImageReference("/resources/crystalsvg22x22/actions/button_cancel.png");
    
    // Bot�n Ok
    private CCButton btnOK;
    // Bot�n Cancel
    private CCButton btnCancel;
    
    // Controlador de la vista
    private ABMListadoController controller;
    
    // Hashtable del objeto que se está editando
    protected Hashtable editHashTable;
    
    // Lista de Componentes vinculados al objeto editado
    protected Vector<DataSourceLinkeable> updatableComponents = new Vector<DataSourceLinkeable>();
    
    public ABMListadoAddView() {
        super();
        
        this.setModal(true);
        
        /* TODO ver como manejar el z-index en forma automática para no hacerlo */
        this.setZIndex(999);
        this.setClosable(false);
        
        this.crearObjetos();
        this.renderObjetos();
        
        //((FWContentPanePrincipal) ApplicationInstance.getActive().getDefaultWindow().getContent()).abrirVentanaMensaje(this);

    }
    
    /**
     *Se crean todos los objetos a mostrar
     */
    private void crearObjetos() {
        // crea los botones
        this.btnOK = new CCButton(this.OK);
        this.btnOK.setActionCommand("ok");
        this.btnOK.setToolTipText("Confirmar la Operaci�n");
        this.btnCancel = new CCButton(this.CANCEL);
        this.btnCancel.setActionCommand("cancel");
        this.btnCancel.setToolTipText("Cancelar la Operaci�n");
        
        // Agregar los eventos a los botones
        this.btnOK.addActionListener(this);
        this.btnCancel.addActionListener(this);
    }
    
    /**
     *Se muestran los objetos
     */
    private void renderObjetos() {
        // Agrego los botones a la barra
        this.rBotones.add(this.btnOK);
        this.rBotones.add(this.btnCancel);
    }

    
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("ok")){
            MessageWindowPane mwp = new MessageWindowPane(
                    "�Esta seguro de realizar la actualizaci�n?", this, "doUpdate");
        } 
        /*
        else if (e.getActionCommand().equals("cancel")){
            this.exit();
        }
        */
        super.actionPerformed(e);
    }
    
    public void doUpdate() {
        
        // Disparo el insert en la clase que hereda
    	boolean actualizo = this.insert();
        
        if (actualizo) {
            this.exit();
        } else {
            new MessageWindowPane("Se produjo un error en la actualizaci�n");
        }
    }

    
    public void exit(){
    	((FWContentPanePrincipal) ApplicationInstance.getActive().getDefaultWindow().getContent()).cerrarVentana(this);
        //((CCContentPane) getParent()).remove(this);
    }
    
    /**
     * M�todo a realizar sobrecarga 
     */
    public boolean insert() {
    	return true;
    }
    
   

}

