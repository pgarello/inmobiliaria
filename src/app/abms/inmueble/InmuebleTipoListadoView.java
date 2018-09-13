package app.abms.inmueble;

import java.util.List;

import ccecho2.complex.MessageWindowPane;

import nextapp.echo2.app.ApplicationInstance;
import nextapp.echo2.app.event.ActionEvent;

import datos.inmueble.InmuebleFacade;
import datos.inmueble.InmuebleTipo;

import framework.ui.generales.FWWindowPaneMensajes;
import framework.ui.generales.abms.ABMListadoView;
import framework.ui.principal.FWContentPanePrincipal;

@SuppressWarnings("serial")
public class InmuebleTipoListadoView extends ABMListadoView {
	
	private List<InmuebleTipo> dataList;
	
    public InmuebleTipoListadoView () {
    	
        super(app.abms.usuario.UsuarioListadoAddView.class);
        
        this.setTitle("ABM de Inmuebles Tipo - Listado");

        //this.getTable().addActionListener(this);
        
        ActualizarDatos();
                
        /** Dibujo los datos */
        //this.getBrowser().update();
    }
    
    
    public void ActualizarDatos() {
    	
        /** Cargo los datos en la grilla */
        InmuebleTipoListadoModel oModel = new InmuebleTipoListadoModel();
        dataList = InmuebleFacade.findAllInmuebleTipo();
        oModel.setDataList(dataList);
        this.update(oModel,0,0);
        
    }
    
    
    /* Si quiero usar sobrecarga del método */
    public void actionPerformed(ActionEvent e) {
    	
    	if (e.getActionCommand().equals("new")){            
        	
    		/** Inserción -------------------------------------------- /
        	((FWContentPanePrincipal) ApplicationInstance
        			.getActive().getDefaultWindow().getContent())
                    .abrirVentana(new UsuarioListadoAddView(this));
            */
			
    	} else if (e.getActionCommand().equals("delete")){

    		/** Borrar ----------------------------------------------- */
    		if (this.getTable().getSelectionModel().getMinSelectedIndex() >= 0) {
    			new MessageWindowPane("¿Está seguro de eliminar permanentemente el Registro?", this, "doDelete");
    		} else {
            	((FWContentPanePrincipal) ApplicationInstance
            			.getActive().getDefaultWindow().getContent())
                        .abrirVentana(new FWWindowPaneMensajes("Error ABM INMUEBLE TIPO","Debe seleccionar una fila para continuar.",(short)1));
            }
        
    	} else if (e.getActionCommand().equals("edit")){
    		
    		/** Edición ----------------------------------------------- */
    		int currentRow = this.getTable().getSelectionModel().getMinSelectedIndex();
    		if (currentRow >= 0) {
    			/*
    			Edificio oEdificio = dataList.get(currentRow);
    	    	((FWContentPanePrincipal) ApplicationInstance
            			.getActive().getDefaultWindow().getContent())
                        .abrirVentana(new EdificioListadoEditView(this, oEdificio));
                */
    		} else {
            	((FWContentPanePrincipal) ApplicationInstance
            			.getActive().getDefaultWindow().getContent())
                        .abrirVentana(new FWWindowPaneMensajes("Error ABM INMUEBLE TIPO","Debe seleccionar una fila para continuar.",(short)1));
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
