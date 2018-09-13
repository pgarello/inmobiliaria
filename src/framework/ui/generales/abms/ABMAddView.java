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
import framework.ui.generales.FWWindowPaneMensajes;
import framework.ui.generales.exception.ValidacionException;

import nextapp.echo2.app.ImageReference;
import nextapp.echo2.app.ResourceImageReference;
import nextapp.echo2.app.event.ActionEvent;

/**
 * @author Pgarello
 */
public class ABMAddView extends WindowPaneExitable {
    
	private static final long serialVersionUID = -7993598306912105564L;
	
	// Imagen del bot�n Ok
    private ImageReference OK = new ResourceImageReference("/resources/crystalsvg22x22/actions/button_ok.png");
    // Imagen del bot�n Cancel
    private ImageReference CANCEL = new ResourceImageReference("/resources/crystalsvg22x22/actions/button_cancel.png");
    
    // Bot�n Ok
    private CCButton btnOK;
    // Bot�n Cancel
    private CCButton btnCancel;
            
    public ABMAddView() {
        
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
        
       		new MessageWindowPane("�Esta seguro de realizar la actualizaci�n?", this, "doInsert");
        
        } else if (e.getActionCommand().equals("cancel")){
            
        	// Aca tendria q limpiar todos los datos
        	new MessageWindowPane("�Esta seguro de borrar todos los datos ingresados?", this, "doLimpiar");
            
        } else if (e.getActionCommand().equals("exit")){
            
    		/** Salir ------------------------------------------------ */
            this.exit();
            
        }
    }
  
    
    public void doInsert() {
        
        // Disparo el insert en la clase que hereda    	
    	String mensaje_error = "Se produjo un error en la actualizaci�n";
    	boolean actualizo = false; 
    	try {
    		actualizo = this.insert();
    	} catch(Exception ve) {
    		mensaje_error = ve.getMessage();
    	}
        
        if (actualizo) {
        	
        	// Tendria que salir una ventana de confirmaci�n de la operacion �?
        	new MessageWindowPane("El proceso de grabaci�n se realizo con �xito");
        	
        	// Aca salgo o limpio la pantalla para cargar un nuevo registro �?
            // this.exit();
        	
        	this.salir();
        	
        } else {
        	new FWWindowPaneMensajes(mensaje_error, "ERROR");
            //new MessageWindowPane(mensaje_error);
        }
    }

    
    public void exit(){
        ((CCContentPane) getParent()).remove(this);
    }
    
    /**
     * M�todos a realizar sobrecarga 
     */
    public boolean insert() throws ValidacionException {
    	return true;
    }
    
    public void doLimpiar() {
    	// Este m�todo se sobrecarga en cada implementaci�n por herencia
    }
    
    public void salir() {
    	this.exit();
    }
    
}