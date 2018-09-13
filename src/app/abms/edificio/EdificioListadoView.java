package app.abms.edificio;

import java.util.List;

import ccecho2.complex.MessageWindowPane;

import nextapp.echo2.app.ApplicationInstance;
import nextapp.echo2.app.event.ActionEvent;
import datos.edificio.Edificio;
import datos.edificio.EdificioFachada;

import framework.ui.generales.FWWindowPaneMensajes;
import framework.ui.generales.abms.ABMListadoView;
import framework.ui.principal.FWContentPanePrincipal;

@SuppressWarnings("serial")
public class EdificioListadoView extends ABMListadoView {
	
	private List<Edificio> dataList;
	
    public EdificioListadoView () {
    	
        super(app.abms.usuario.UsuarioListadoAddView.class);
        
        this.setTitle("ABM de Edificios - Listado");

        //this.getTable().addActionListener(this);
        
        ActualizarDatos();
                
        /** Dibujo los datos */
        //this.getBrowser().update();
    }
    
    
    public void ActualizarDatos() {
    	
        /** Cargo los datos en la grilla */
        EdificioListadoModel oModel = new EdificioListadoModel();
        dataList = EdificioFachada.findAll();
        oModel.setDataList(dataList);
        this.update(oModel,0,0);
        
    }
    
    
    /* Si quiero usar sobrecarga del método */
    public void actionPerformed(ActionEvent e) {
    	
    	if (e.getActionCommand().equals("new")){            
        	
    		/** Inserción -------------------------------------------- */
        	((FWContentPanePrincipal) ApplicationInstance
        			.getActive().getDefaultWindow().getContent())
                    .abrirVentana(new EdificioListadoAddView(this));
            
			
    	} else if (e.getActionCommand().equals("delete")){

    		/** Borrar ----------------------------------------------- */
    		if (this.getTable().getSelectionModel().getMinSelectedIndex() >= 0) {
    			new MessageWindowPane("¿Está seguro de eliminar permanentemente el Registro?", this, "doDelete");
    		} else {
            	((FWContentPanePrincipal) ApplicationInstance
            			.getActive().getDefaultWindow().getContent())
                        .abrirVentana(new FWWindowPaneMensajes("Error ABM EDIFICIOS","Debe seleccionar una fila para continuar.",(short)1));
            }
        
    	} else if (e.getActionCommand().equals("edit")){
    		
    		/** Edición ----------------------------------------------- */
    		int currentRow = this.getTable().getSelectionModel().getMinSelectedIndex();
    		if (currentRow >= 0) {
    			
    			Edificio oEdificio = dataList.get(currentRow);
    	    	((FWContentPanePrincipal) ApplicationInstance
            			.getActive().getDefaultWindow().getContent())
                        .abrirVentana(new EdificioListadoEditView(this, oEdificio));
                
    		} else {
            	((FWContentPanePrincipal) ApplicationInstance
            			.getActive().getDefaultWindow().getContent())
                        .abrirVentana(new FWWindowPaneMensajes("Error ABM EDIFICIOS","Debe seleccionar una fila para continuar.",(short)1));
            }
			
    	} else if (e.getActionCommand().equals("exit")){
            
    		/** Salir ------------------------------------------------ */
            this.exit();
            
        }
    	
    }
    
    
    public void doDelete() {
        // elimino el registro actual        
    	// int currentRow = this.getTable().getSelectionModel().getMinSelectedIndex();
        /*
        Usuario oUsuario = dataList.get(currentRow);
        
        // Borrar
    	try {
    		UsuarioFacade.delete(oUsuario);
    	} catch(Exception e) {
    		e.printStackTrace();
    		new MessageWindowPane(e.getMessage());
    	}
    	
    	// Actualizo la tabla
    	ActualizarDatos();
        */
    }
    
    
        
}
