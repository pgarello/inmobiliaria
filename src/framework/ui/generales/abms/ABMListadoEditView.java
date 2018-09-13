package framework.ui.generales.abms;

import ccecho2.base.CCButton;
//import ccecho2.base.CCContentPane;
import ccecho2.complex.BasicSplitWindow;

import ccecho2.complex.MessageWindowPane;


import framework.ui.principal.FWContentPanePrincipal;

import nextapp.echo2.app.ApplicationInstance;
import nextapp.echo2.app.ImageReference;
import nextapp.echo2.app.ResourceImageReference;
import nextapp.echo2.app.event.ActionEvent;

/**
 *
 * @author SShadow
 */
@SuppressWarnings("serial")
public class ABMListadoEditView extends BasicSplitWindow {
    
    // Imagen del bot�n Ok
    private ImageReference OK = new ResourceImageReference("/resources/crystalsvg22x22/actions/button_ok.png");
    // Imagen del bot�n Cancel
    private ImageReference CANCEL = new ResourceImageReference("/resources/crystalsvg22x22/actions/button_cancel.png");
    
    // Bot�n Ok
    private CCButton btnOK;
    // Bot�n Cancel
    private CCButton btnCancel;

    
    public ABMListadoEditView() {
        super();
        
        this.setModal(true);
        /* ver como manejar el z-index en forma autom�tica para no hacerlo */
        this.setZIndex(999);
        this.setClosable(false);
        
        this.crearObjetos();
        this.renderObjetos();
        
        //((FWContentPanePrincipal) ApplicationInstance.getActive().getDefaultWindow().getContent()).abrirVentana(this);
        
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
            new MessageWindowPane(
                    "�Esta seguro de realizar la actualizaci�n?",
                    this, "doUpdate");
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
    	boolean actualizo = this.update();        
    	
        if (actualizo) {
            // this.exit();
            
            // Ver aca si direcciono a la edici�n ... lo hace la clase que
        	this.redireccion();
            
        } else {
            //new MessageWindowPane("Se produjo un error en la actualizaci�n");
        }
        
        
    }

    /**
     * M�todo a realizar sobrecarga 
     */
    public boolean update() {
    	return true;
    }
    
    public void redireccion() {
    	this.exit();
    }
    
    public void exit(){
    	((FWContentPanePrincipal) ApplicationInstance.getActive().getDefaultWindow().getContent()).cerrarVentana(this);
        //((CCContentPane) getParent()).remove(this);
    }

}