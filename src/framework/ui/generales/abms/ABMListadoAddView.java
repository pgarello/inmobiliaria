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
	
	// Imagen del botón Ok
    private ImageReference OK = new ResourceImageReference("/resources/crystalsvg22x22/actions/button_ok.png");
    // Imagen del botón Cancel
    private ImageReference CANCEL = new ResourceImageReference("/resources/crystalsvg22x22/actions/button_cancel.png");
    
    // Botón Ok
    private CCButton btnOK;
    // Botón Cancel
    private CCButton btnCancel;
    
    // Controlador de la vista
    private ABMListadoController controller;
    
    // Hashtable del objeto que se estÃ¡ editando
    protected Hashtable editHashTable;
    
    // Lista de Componentes vinculados al objeto editado
    protected Vector<DataSourceLinkeable> updatableComponents = new Vector<DataSourceLinkeable>();
    
    public ABMListadoAddView() {
        super();
        
        this.setModal(true);
        
        /* TODO ver como manejar el z-index en forma automÃ¡tica para no hacerlo */
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
        this.btnOK.setToolTipText("Confirmar la Operación");
        this.btnCancel = new CCButton(this.CANCEL);
        this.btnCancel.setActionCommand("cancel");
        this.btnCancel.setToolTipText("Cancelar la Operación");
        
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
                    "¿Esta seguro de realizar la actualización?", this, "doUpdate");
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
            new MessageWindowPane("Se produjo un error en la actualización");
        }
    }

    
    public void exit(){
    	((FWContentPanePrincipal) ApplicationInstance.getActive().getDefaultWindow().getContent()).cerrarVentana(this);
        //((CCContentPane) getParent()).remove(this);
    }
    
    /**
     * Método a realizar sobrecarga 
     */
    public boolean insert() {
    	return true;
    }
    
   

}

