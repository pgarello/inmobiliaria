package app.abms.localidad;

import java.util.List;

import ccecho2.complex.MessageWindowPane;

import nextapp.echo2.app.ApplicationInstance;
import nextapp.echo2.app.event.ActionEvent;

import datos.localidad.Localidad;
import datos.localidad.LocalidadFacade;

import framework.ui.generales.FWWindowPaneMensajes;
import framework.ui.generales.abms.ABMListadoView;
import framework.ui.principal.FWContentPanePrincipal;

@SuppressWarnings("serial")
public class LocalidadListadoView extends ABMListadoView {
	
	private List<Localidad> dataList;
	
    public LocalidadListadoView () {
    	
        super(app.abms.localidad.LocalidadListadoAddView.class);
        
        //System.out.println("app.abms.localidad.LocalidadListadoView CONSTRUCTOR");
        
        this.setTitle("ABM de Localidades - Listado");

        //this.getTable().addActionListener(this);
        
        ActualizarDatos();
                
        /** Dibujo los datos */
        //this.getBrowser().update();
    }
    
    
    public void ActualizarDatos() {
    	
        /** Cargo los datos en la grilla */
        LocalidadListadoModel oModel = new LocalidadListadoModel();
        dataList = LocalidadFacade.findAll();
        oModel.setDataList(dataList);
        this.update(oModel,0,0);
        
    }
    
    
    /* Si quiero usar sobrecarga del método */
    public void actionPerformed(ActionEvent e) {
    	
    	if (e.getActionCommand().equals("new")){            
        	
    		/** Inserción -------------------------------------------- */
        	((FWContentPanePrincipal) ApplicationInstance
        			.getActive().getDefaultWindow().getContent())
                    .abrirVentana(new LocalidadListadoAddView(this));
            
			
    	} else if (e.getActionCommand().equals("delete")){

    		/** Borrar ----------------------------------------------- */
    		if (this.getTable().getSelectionModel().getMinSelectedIndex() >= 0) {
    			new MessageWindowPane("¿Está seguro de eliminar permanentemente el Registro?", this, "doDelete");
    		} else {
            	((FWContentPanePrincipal) ApplicationInstance
            			.getActive().getDefaultWindow().getContent())
                        .abrirVentana(new FWWindowPaneMensajes("Error ABM LOCALIDAD","Debe seleccionar una fila para continuar.",(short)1));
            }
        
    	} else if (e.getActionCommand().equals("edit")){
    		
    		/** Edición ----------------------------------------------- */
    		int currentRow = this.getTable().getSelectionModel().getMinSelectedIndex();
    		if (currentRow >= 0) {
    			
    			Localidad oLocalidad = dataList.get(currentRow);
    	    	((FWContentPanePrincipal) ApplicationInstance
            			.getActive().getDefaultWindow().getContent())
                        .abrirVentana(new LocalidadListadoEditView(this, oLocalidad));
                
    		} else {
            	((FWContentPanePrincipal) ApplicationInstance
            			.getActive().getDefaultWindow().getContent())
                        .abrirVentana(new FWWindowPaneMensajes("Error ABM LOCALIDAD","Debe seleccionar una fila para continuar.",(short)1));
            }
			
    	} else if (e.getActionCommand().equals("exit")){
            
    		/** Salir ------------------------------------------------ */
            this.exit();
            
        }
    	
    }
    
    
    public void doDelete() {
        // elimino el registro actual        
    	int currentRow = this.getTable().getSelectionModel().getMinSelectedIndex();
        
        Localidad oLocalidad = dataList.get(currentRow);
        
        // Borrar
    	try {
    		LocalidadFacade.delete(oLocalidad);
    	} catch(Exception e) {
    		e.printStackTrace();
    		new MessageWindowPane(e.getMessage());
    	}
    	
    	// Actualizo la tabla
    	ActualizarDatos();
        
    }
    
    
        
}
