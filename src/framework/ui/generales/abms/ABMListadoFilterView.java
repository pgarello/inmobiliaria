/*
 * AcercaDeModel.java
 *
 * Created on 2 de mayo de 2007, 06:14
 *
 */

package framework.ui.generales.abms;

import ccecho2.base.CCButton;
import ccecho2.complex.WindowPaneExitable;

import java.util.Observable;
import nextapp.echo2.app.Alignment;

import nextapp.echo2.app.ImageReference;
import nextapp.echo2.app.ResourceImageReference;
import nextapp.echo2.app.event.ActionEvent;


/**
 * @author pgarello
 */
public class ABMListadoFilterView extends WindowPaneExitable {

	private static final long serialVersionUID = 1L;
            
    /* imágenes de botones */    
    private ImageReference FIND = new ResourceImageReference("/resources/crystalsvg22x22/actions/find.png");
    private ImageReference CANCEL = new ResourceImageReference("/resources/crystalsvg22x22/actions/button_cancel.png");
    

    // Navegación    
    /** Botón de búsqueda */
    private CCButton btnFind = new CCButton(FIND);
    private CCButton btnCancel = new CCButton(CANCEL);
    

    /** Crea una instancia de ABMListadoFilterView */
    public ABMListadoFilterView() {
        super();
        
        this.crearObjetos();
        this.renderObjetos();
        
        //((FWContentPanePrincipal) ApplicationInstance.getActive().getDefaultWindow().getContent()).abrirVentana(this);

    }
    
    public void update(Observable o, Object arg) {}

    public void setController(ABMListadoController controller) {}

    private void crearObjetos() {
        
        this.btnCancel.setActionCommand("cancel");
        this.btnCancel.setToolTipText("Limpiar filtros");

        this.btnFind.setActionCommand("find");
        this.btnFind.setToolTipText("Consultar datos");
        
        // Agregar los eventos a los botones
        this.btnCancel.addActionListener(this);
        this.btnFind.addActionListener(this);

    }

    /**
     *Crea la representacion de los objetos en pantalla
     */
    private void renderObjetos() {

    	// Ver el boton de borrar
        this.rBotones.add(this.btnCancel);
        this.rBotones.add(this.btnFind);
        
        this.rBotones.setAlignment(Alignment.ALIGN_RIGHT);

    }
    
    public void actionPerformed(ActionEvent e) {
    	
        if (e.getActionCommand().equals("cancel")){
        	
            // Limpio los datos de pantalla
        	this.clear();
            
        } else if (e.getActionCommand().equals("find")) {
        	
            // Consulto los datos
        	this.find();
            
        } 
        
        // Tiro el evento para arriba en la gerarquia de objetos
    	super.actionPerformed(e);
        
    }

    // Estos métodos se usan con sobrecarga
    public void find() {}
    public void clear() {}

}