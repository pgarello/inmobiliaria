package app.abms.usuario;

import java.util.List;

import ccecho2.complex.MessageWindowPane;

import nextapp.echo2.app.ApplicationInstance;
import nextapp.echo2.app.event.ActionEvent;
import datos.usuario.UsuarioFacade;
import datos.usuario.Usuario;


import framework.ui.generales.FWWindowPaneMensajes;
import framework.ui.generales.abms.ABMListadoView;

import framework.ui.principal.FWContentPanePrincipal;

@SuppressWarnings("serial")
public class UsuarioListadoView extends ABMListadoView {
	
	private List<Usuario> dataList;
	
    public UsuarioListadoView () {
    	
        super(app.abms.usuario.UsuarioListadoAddView.class);
        
        this.setTitle("ABM de Usuarios - Listado");

        //this.getTable().addActionListener(this);
        
        ActualizarDatos();
                
        /** Dibujo los datos */
        //this.getBrowser().update();
    }
    
    
    public void ActualizarDatos() {
    	
        /** Cargo los datos en la grilla */
        UsuarioListadoModel oModel = new UsuarioListadoModel();
        dataList = UsuarioFacade.findAll();
        oModel.setDataList(dataList);
        this.update(oModel,0,0);
        
    }
    
    
    /* Si quiero usar sobrecarga del método */
    public void actionPerformed(ActionEvent e) {
    	
    	if (e.getActionCommand().equals("new")){            
        	
    		/** Inserción -------------------------------------------- */
        	((FWContentPanePrincipal) ApplicationInstance
        			.getActive().getDefaultWindow().getContent())
                    .abrirVentana(new UsuarioListadoAddView(this));
			
    	} else if (e.getActionCommand().equals("delete")){

    		/** Borrar ----------------------------------------------- */
    		if (this.getTable().getSelectionModel().getMinSelectedIndex() >= 0) {
    			new MessageWindowPane("¿Está seguro de eliminar permanentemente el Registro?", this, "doDelete");
    		} else {
            	((FWContentPanePrincipal) ApplicationInstance
            			.getActive().getDefaultWindow().getContent())
                        .abrirVentana(new FWWindowPaneMensajes("Error ABM USUARIOS","Debe seleccionar una fila para continuar.",(short)1));
            }
        
    	} else if (e.getActionCommand().equals("edit")){
    		
    		/** Edición ----------------------------------------------- */
    		int currentRow = this.getTable().getSelectionModel().getMinSelectedIndex();
    		if (currentRow >= 0) {
    			Usuario oUsuario = dataList.get(currentRow);
    	    	((FWContentPanePrincipal) ApplicationInstance
            			.getActive().getDefaultWindow().getContent())
                        .abrirVentana(new UsuarioListadoEditView(this, oUsuario));
    		} else {
            	((FWContentPanePrincipal) ApplicationInstance
            			.getActive().getDefaultWindow().getContent())
                        .abrirVentana(new FWWindowPaneMensajes("Error ABM USUARIOS","Debe seleccionar una fila para continuar.",(short)1));
            }
			
    	} else if (e.getActionCommand().equals("exit")){
            
    		/** Salir ------------------------------------------------ */
            this.exit();
            
        }
    	
    }
    
    
    public void doDelete() {
        // elimino el registro actual        
    	int currentRow = this.getTable().getSelectionModel().getMinSelectedIndex();
        
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
        
    }
    
    
        
}
