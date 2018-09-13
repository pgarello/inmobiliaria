package app.abms.persona;

import java.util.List;

import ccecho2.complex.MessageWindowPane;

import nextapp.echo2.app.ApplicationInstance;
import nextapp.echo2.app.Command;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.webcontainer.command.BrowserOpenWindowCommand;

import datos.Page;
import datos.persona.Persona;
import datos.persona.PersonaFacade;
import datos.persona.PersonaProcesos;

import framework.grales.seguridad.FWUsuario;
import framework.ui.generales.FWWindowPaneMensajes;
import framework.ui.generales.abms.ABMListadoPrintView;

import framework.ui.principal.FWApplicationInstancePrincipal;
import framework.ui.principal.FWContentPanePrincipal;

@SuppressWarnings("serial")
public class PersonaListadoView extends ABMListadoPrintView {
	
	private List<Persona> dataList;
	private Page dataListPage;
	
	private String filtro_apellido = "";
	private String filtro_nombre = "";
	
	private int page_size = 12;
	private int page_number = 0;
	
    public PersonaListadoView () {
    	
        super(null);
        
        this.setTitle("ABM de Personas - Listado");
        this.setWidth(new Extent(700, Extent.PX));
        this.setHeight(new Extent(400, Extent.PX));
        
        //this.getTable().addActionListener(this);
        
        ActualizarDatos();
                
        /** Dibujo los datos */
        //this.getBrowser().update();
    }

    
    public PersonaListadoView (String filtro_apellido, String filtro_nombre) {
    	
        super(null);
        
        this.setTitle("ABM de Personas - Listado");
        this.setWidth(new Extent(700, Extent.PX));
        this.setHeight(new Extent(400, Extent.PX));
        
        this.filtro_apellido = filtro_apellido;
        this.filtro_nombre = filtro_nombre;
        
        //this.getTable().addActionListener(this);
        
        ActualizarDatos();
                
        /** Dibujo los datos */
        //this.getBrowser().update();
    }

    
    
    @SuppressWarnings("unchecked")
	public void ActualizarDatos() {
    	
        /** Cargo los datos en la grilla */
        PersonaListadoModel oModel = new PersonaListadoModel();
        
        // Levanto los campos que son filtro                
        dataListPage = PersonaProcesos.findByFilter(this.filtro_apellido, this.filtro_nombre, (short)0, page_number, page_size);
        dataList = dataListPage.getList();
        
        
        oModel.setDataList(dataList);
        this.update(oModel, page_number+1, dataListPage.getLastPageNumber()+1);
        
    }
    
    
    /* Si quiero usar polimorfismo del método */
    public void actionPerformed(ActionEvent e) {
    	
    	if (e.getActionCommand().equals("new")){            
        	
    		/** Inserción -------------------------------------------- */
        	// new PersonaAddView(this);
			
    	} else if (e.getActionCommand().equals("delete")){

    		/** Borrar ----------------------------------------------- */
    		    		
    		if (this.getTable().getSelectionModel().getMinSelectedIndex() >= 0) {    			
    			new MessageWindowPane("¿Está seguro de eliminar permanentemente el Registro?", this, "doDelete");
    		} else {
            	((FWContentPanePrincipal) ApplicationInstance
            			.getActive().getDefaultWindow().getContent())
                        .abrirVentana(new FWWindowPaneMensajes("Error ABM PERSONAS","Debe seleccionar una fila para continuar.",(short)1));
            }
        
    	} else if (e.getActionCommand().equals("edit")){
    		
    		/** Edición ----------------------------------------------- */
    		int currentRow = this.getTable().getSelectionModel().getMinSelectedIndex();
    		if (currentRow >= 0) {
    			Persona oPersona = dataList.get(currentRow);
    			((FWContentPanePrincipal) ApplicationInstance
            			.getActive().getDefaultWindow().getContent())
                        .abrirVentana(new PersonaListadoEditView(this, oPersona));
    			
    		} else {
            	((FWContentPanePrincipal) ApplicationInstance
            			.getActive().getDefaultWindow().getContent())
                        .abrirVentana(new FWWindowPaneMensajes("Error ABM PERSONAS","Debe seleccionar una fila para continuar.",(short)1));
            }
			
    	} else if (e.getActionCommand().equals("next")){
    		
    		if(dataListPage.hasNextPage()) {
    			page_number++;
    			ActualizarDatos();
    		}
    		
    	} else if (e.getActionCommand().equals("previous")){

    		if(dataListPage.hasPreviousPage()) {
    			page_number--;
    			ActualizarDatos();
    		}
    		
    	} else if (e.getActionCommand().equals("last")){
    		int ultima_pagina = dataListPage.getLastPageNumber();
    		if (ultima_pagina != page_number) {
    			page_number = ultima_pagina;
    			ActualizarDatos();
    		}
    		
    	} else if (e.getActionCommand().equals("first")){
    		
    		if (0 != page_number) {
    			page_number = 0;
    			ActualizarDatos();
    		}
    		
    	} else if (e.getActionCommand().equals("print")){
    		
    		this.doPrint();
    		
    	}
    	
    	// Tiro el evento para arriba en la gerarquia de objetos
    	super.actionPerformed(e);
    	
    }
    
    
    public void doDelete() {
        // elimino el registro actual        
    	int currentRow = this.getTable().getSelectionModel().getMinSelectedIndex();
        
        Persona oPersona = dataList.get(currentRow);
        
        // Borrar
    	try {
    		PersonaFacade.delete(oPersona);
    		
    		// Actualizo la tabla
        	ActualizarDatos();
    	} catch(Exception e) {
    		e.printStackTrace();
    		new MessageWindowPane("No se puede borrar la PERSONA por que existen datos asociados a la misma.");
    	}
        
    }
    
    
    /**
     * Invoca el servlet que imprime el reporte
     */
    public void doPrint() {        
        
        FWUsuario oFWUsuario = ((FWApplicationInstancePrincipal) this.getApplicationInstance().getActive()).getUsuario();
        String usuario = oFWUsuario.getUsuario();
        
        String sUri = "PDFPreview?reporte=listadoPersonas&usuario=" + usuario;

        StringBuilder sb = new StringBuilder()
                .append("width=640")
                .append(",height=480")
                .append(",resizable=yes")
                .append(",scrollbars=yes");

        Command oComm = new BrowserOpenWindowCommand(sUri, "Prueba", sb.toString());
        ApplicationInstance.getActive().enqueueCommand(oComm);
    }
    
    
        
}
