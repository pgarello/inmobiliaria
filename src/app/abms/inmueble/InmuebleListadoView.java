package app.abms.inmueble;

import java.util.List;

import ccecho2.complex.MessageWindowPane;

import nextapp.echo2.app.ApplicationInstance;
import nextapp.echo2.app.Command;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.webcontainer.command.BrowserOpenWindowCommand;
import datos.Page;
import datos.inmueble.Inmueble;
import datos.inmueble.InmuebleFacade;
import datos.inmueble.InmuebleProcesos;

import framework.grales.seguridad.FWUsuario;
import framework.nr.generales.filtros.FWFiltros;
import framework.ui.generales.FWWindowPaneMensajes;
import framework.ui.generales.abms.ABMListadoPrintView;
import framework.ui.principal.FWApplicationInstancePrincipal;
import framework.ui.principal.FWContentPanePrincipal;

@SuppressWarnings("serial")
public class InmuebleListadoView extends ABMListadoPrintView {
	
	private List<Inmueble> dataList;
	private Page dataListPage;
	
	private int page_size = 12;
	private int page_number = 0;
	
	private String filtro_calle = "";
	private int filtro_edificio = 0;
	private short filtro_tipo = 0;
	private int filtro_propietario;
	
	private FWFiltros refClaseFiltros;
	
	public boolean sin_datos = true;
	
	
    public InmuebleListadoView () {
    	
        super(null);        
        
        this.setTitle("ABM de Propiedades - Listado");
        this.setWidth(new Extent(700, Extent.PX));
        
        //this.getTable().addActionListener(this);
        
        ActualizarDatos();
                
        /** Dibujo los datos */
        //this.getBrowser().update();
    }

    
    public InmuebleListadoView (	String filtro_calle, 
    								int filtro_edificio, 
    								short filtro_tipo,
    								int filtro_propietario,
    								FWFiltros refClaseFiltros) {
    	
        super(null);
        
        this.refClaseFiltros = refClaseFiltros;
        this.setTitle("ABM de Propiedades - Listado");
        this.setWidth(new Extent(700, Extent.PX));
        this.setHeight(new Extent(400, Extent.PX));
        
        //this.getTable().addActionListener(this);
        this.filtro_calle = filtro_calle;
        this.filtro_edificio = filtro_edificio;
        this.filtro_tipo = filtro_tipo;
        this.filtro_propietario = filtro_propietario;
        
        ActualizarDatos();
                
        /** Dibujo los datos */
        //this.getBrowser().update();
    }

    
    
    @SuppressWarnings("unchecked")
	public void ActualizarDatos() {
    	
        /** Cargo los datos en la grilla */
        InmuebleListadoModel oModel = new InmuebleListadoModel();
        
        dataListPage = InmuebleProcesos.findByFilter(	filtro_calle, 
        												filtro_edificio, 
        												filtro_tipo,
        												filtro_propietario,
        												page_number, page_size);
        
        // Controlo que la consulta no de vacia
        if (dataListPage.getList().size() > 0) {
        
        	this.sin_datos = false;
        	refClaseFiltros.setMensaje("");
        	
        	dataList = dataListPage.getList();
        
        	oModel.setDataList(dataList);
        	this.update(oModel, page_number+1, dataListPage.getLastPageNumber()+1);
        	
        } else {
        	        	
        	// salgo
        	// this.actionPerformed(new ActionEvent(this.bExit, "exit"));      	
        	
        	// Informo en pantalla que me llamo ¿?
        	refClaseFiltros.setMensaje("La consulta no devuelve ningún dato.");
        	
        	//((CCContentPane) getParent()).remove(this);        	        	
        	//((FWContentPanePrincipal) ApplicationInstance.getActive().getDefaultWindow().getContent()).cerrarVentana(this);
        	
        }
        
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
                        .abrirVentana(new FWWindowPaneMensajes("Error ABM PROPIEDADES","Debe seleccionar una fila para continuar.",(short)1));
            }
        
    	} else if (e.getActionCommand().equals("edit")){
    		
    		/** Edición ----------------------------------------------- */
    		int currentRow = this.getTable().getSelectionModel().getMinSelectedIndex();
    		if (currentRow >= 0) {
    			
    			Inmueble oInmueble = dataList.get(currentRow);
    			((FWContentPanePrincipal) ApplicationInstance
            			.getActive().getDefaultWindow().getContent())
                        .abrirVentana(new InmuebleListadoEditView(this, oInmueble));
                
    			
    		} else {
            	((FWContentPanePrincipal) ApplicationInstance
            			.getActive().getDefaultWindow().getContent())
                        .abrirVentana(new FWWindowPaneMensajes("Error ABM PROPIEDADES","Debe seleccionar una fila para continuar.",(short)1));
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
        
        Inmueble oInmueble = dataList.get(currentRow);
        
        // Borrar
    	try {
    		InmuebleFacade.delete(oInmueble);
    		
    		// Actualizo la tabla
        	ActualizarDatos();
    	} catch(Exception e) {
    		e.printStackTrace();
    		new MessageWindowPane(e.getMessage());
    	}
        
    }
    
    /**
     * Invoca el servlet que imprime el reporte
     */
    public void doPrint() {        
        
        FWUsuario oFWUsuario = ((FWApplicationInstancePrincipal) this.getApplicationInstance().getActive()).getUsuario();
        String usuario = oFWUsuario.getUsuario();
        
        String sUri = "PDFPreview?reporte=listadoInmueble&usuario=" + usuario;

        StringBuilder sb = new StringBuilder()
                .append("width=640")
                .append(",height=480")
                .append(",resizable=yes")
                .append(",scrollbars=yes");

        Command oComm = new BrowserOpenWindowCommand(sUri, "Prueba", sb.toString());
        ApplicationInstance.getActive().enqueueCommand(oComm);
    }
    
    
        
}
