/*
 * NavWindow.java
 *
 * Created on 9 de febrero de 2007, 15:05
 *
 */

package framework.ui.generales.abms;

import ccecho2.base.CCButton;
import ccecho2.base.CCContentPane;
import ccecho2.complex.MessageWindowPane;
import ccecho2.complex.WindowPaneExitable;

import framework.ui.principal.FWContentPanePrincipal;
import nextapp.echo2.app.ApplicationInstance;
import nextapp.echo2.app.ImageReference;
import nextapp.echo2.app.ResourceImageReference;
import nextapp.echo2.app.event.ActionEvent;

/**
 * @author Pgarello
 */
public class ABMFilterView extends WindowPaneExitable {
    
	private static final long serialVersionUID = -7993598306912105564L;
	
	// Imagen del botón Ok
    private ImageReference OK = new ResourceImageReference("/resources/crystalsvg22x22/actions/button_ok.png");
    // Imagen del botón Cancel
    private ImageReference CANCEL = new ResourceImageReference("/resources/crystalsvg22x22/actions/button_cancel.png");
    
    // Botón Ok
    private CCButton btnOK;
    // Botón Cancel
    private CCButton btnCancel;
            
    public ABMFilterView() {
        
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
        
        	new MessageWindowPane("¿Esta seguro de realizar la actualización?", this, "doInsert");
        
        } else if (e.getActionCommand().equals("cancel")){
            
        	// Aca tendria q limpiar todos los datos
        	// this.exit();
            
        } else if (e.getActionCommand().equals("exit")){
            
    		/** Salir ------------------------------------------------ */
            this.exit();
            
        }
    }
    
    public void doInsert() {
        
        // Disparo el insert en la clase que hereda
    	boolean actualizo = this.insert();
        
        if (actualizo) {
        	
        	// Tendria que salir una ventana de confirmación de la operacion ¿?
        	new MessageWindowPane("El proceso de grabación se realizo con éxito");
        	
        	// Aca salgo o limpio la pantalla para cargar un nuevo registro ¿?
            this.exit();
        } else {
            new MessageWindowPane("Se produjo un error en la actualización");
        }
    }

    
    public void exit(){
        ((CCContentPane) getParent()).remove(this);
    }
    
    /**
     * Método a realizar sobrecarga 
     */
    public boolean insert() {
    	return true;
    }
    
   
}