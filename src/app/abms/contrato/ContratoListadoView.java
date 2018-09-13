package app.abms.contrato;

import java.util.Date;
import java.util.List;

import ccecho2.complex.MessageWindowPane;

import nextapp.echo2.app.ApplicationInstance;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Command;
import nextapp.echo2.app.Component;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.Table;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.layout.TableLayoutData;
import nextapp.echo2.app.list.ListSelectionModel;
import nextapp.echo2.app.table.TableCellRenderer;
import nextapp.echo2.webcontainer.command.BrowserOpenWindowCommand;
import datos.Page;

import datos.contrato.Contrato;
import datos.contrato.ContratoProcesos;

import framework.grales.seguridad.FWUsuario;
import framework.nr.generales.filtros.FWFiltros;
import framework.ui.generales.FWWindowPaneMensajes;
import framework.ui.generales.abms.ABMListadoPrintView;

import framework.ui.principal.FWApplicationInstancePrincipal;
import framework.ui.principal.FWContentPanePrincipal;

@SuppressWarnings("serial")
public class ContratoListadoView extends ABMListadoPrintView {
	
	private List<Contrato> dataList;
	private Page dataListPage;

	private int page_size = 10;
	private int page_number = 0;
	
	private FWFiltros refClaseFiltros;
	
	public boolean sin_datos = true;
	
	private int filtro_inmueble;
	private int filtroPropietario;
	private int filtroInquilino;
	private boolean filtroVigentes;
	private boolean filtroRescindido;
	private boolean filtroComercial;
	private Date filtro_fecha_desde;
	private Date filtro_fecha_hasta;
	
    
    public ContratoListadoView (int filtro_inmueble,
    							int filtroPropietario,
    							int filtroInquilino,
    							boolean filtroVigentes,
    							FWFiltros refClaseFiltros,
    							Date filtro_fecha_desde, 
    							Date filtro_fecha_hasta,
    							boolean filtroRescindido,
    							boolean filtroComercial) {
    	
        super(null);
        
        this.refClaseFiltros = refClaseFiltros;
        
        this.filtro_inmueble = filtro_inmueble;
        this.filtroPropietario = filtroPropietario;
        this.filtroInquilino = filtroInquilino;
        this.filtroVigentes = filtroVigentes;
        this.filtro_fecha_desde = filtro_fecha_desde;
        this.filtro_fecha_hasta = filtro_fecha_hasta;
        this.filtroRescindido = filtroRescindido;
        this.filtroComercial = filtroComercial;
        
        this.setTitle("ABM de Contratos - Listado - II");
        this.setWidth(new Extent(800, Extent.PX));
        this.setHeight(new Extent(450, Extent.PX));
        
        //this.getTable().addActionListener(this);
        
        ActualizarDatos(false);
                
        /** Dibujo los datos */
        //this.getBrowser().update();
    }

    
    
    @SuppressWarnings("unchecked")
	public void ActualizarDatos(boolean viene_de_borrar) {
    	
        /** Cargo los datos en la grilla */
    	
    	// Evaluo los filtros en null ¿?
    	
    	//System.out.println("Filtro prop :" + filtroPropietario);
    	//System.out.println("Filtro inq :" + filtroInquilino);
    	
        ContratoListadoModel oModel = new ContratoListadoModel();
        dataListPage = ContratoProcesos.findByFilter(filtroVigentes, filtro_inmueble, filtroInquilino, filtroPropietario, page_number, page_size, filtro_fecha_desde, 
        											 filtro_fecha_hasta, filtroRescindido, filtroComercial);
        dataList = dataListPage.getList();
        
        try {
			dataList = ContratoProcesos.completar(dataList);
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        
        // Controlo que la consulta no de vacia
        if (dataListPage.getList().size() > 0 || viene_de_borrar) {
        
        	this.sin_datos = false;
        	refClaseFiltros.setMensaje("");
        	
        	dataList = dataListPage.getList();
        
        	oModel.setDataList(dataList);
        	this.update(oModel, page_number+1, dataListPage.getLastPageNumber()+1);
        	this.fila_color = -1;
        	
        	ListSelectionModel selectionModel = this.oTable.getSelectionModel();
        	selectionModel.clearSelection();
        	this.oTable.setWidth(null);
        	this.oTable.setDefaultRenderer(Object.class, randomizingCellRenderer);
        	
        } else {
        	        	
        	// salgo
        	// this.actionPerformed(new ActionEvent(this.bExit, "exit"));      	
        	
        	// Informo en pantalla que me llamo ¿?
        	refClaseFiltros.setMensaje("La consulta no devuelve ningún dato.");
        	
        	//((CCContentPane) getParent()).remove(this);        	        	
        	//((FWContentPanePrincipal) ApplicationInstance.getActive().getDefaultWindow().getContent()).cerrarVentana(this);
        	
        }
                
    }    
    
    private int fila_color = -1;
    private TableCellRenderer randomizingCellRenderer = new TableCellRenderer() {
    	  
        public Component getTableCellRendererComponent(Table table, Object value, int column, int row) {
        	//System.out.println("haber " + value.getClass().getName() + " - " + column + " - " + row);     	
        	Label label = null;        	
        	if (column == 1) {   
        		//label = new Label("");
        		if ((Boolean)value) {
        			if (fila_color != row) fila_color = row;
        		}   		
        	} else {
        		label = new Label(value == null ? null : value.toString());
        		if (fila_color == row) {
            		TableLayoutData layoutData = new TableLayoutData();
        			layoutData.setBackground(Color.ORANGE);            
        			label.setLayoutData(layoutData);
            	}
        	}        	
        	      	
            return label;
        }        
    };
    
    
    
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
                        .abrirVentana(new FWWindowPaneMensajes("Error ABM CONTRATOS","Debe seleccionar una fila para continuar.",(short)1));
            }
        
    	} else if (e.getActionCommand().equals("edit")){
    		
    		/** Edición ----------------------------------------------- */
    		int currentRow = this.getTable().getSelectionModel().getMinSelectedIndex();
    		if (currentRow >= 0) {
    			
    			Contrato oContrato = dataList.get(currentRow);
    			((FWContentPanePrincipal) ApplicationInstance
            			.getActive().getDefaultWindow().getContent())
                        .abrirVentana(new ContratoListadoEditView(this, oContrato));                
    			
    		} else {
            	((FWContentPanePrincipal) ApplicationInstance
            			.getActive().getDefaultWindow().getContent())
                        .abrirVentana(new FWWindowPaneMensajes("Error ABM CONTRATOS","Debe seleccionar una fila para continuar.",(short)1));
            }
			
    	} else if (e.getActionCommand().equals("next")){
    		
    		if(dataListPage.hasNextPage()) {
    			page_number++;
    			ActualizarDatos(false);
    		}
    		
    	} else if (e.getActionCommand().equals("previous")){

    		if(dataListPage.hasPreviousPage()) {
    			page_number--;
    			ActualizarDatos(false);
    		}
    		
    	} else if (e.getActionCommand().equals("last")){
    		
    		int ultima_pagina = dataListPage.getLastPageNumber();
    		if (ultima_pagina != page_number) {
    			page_number = ultima_pagina;
    			ActualizarDatos(false);
    		}
    		
    	} else if (e.getActionCommand().equals("first")){
    		
    		if (0 != page_number) {
    			page_number = 0;
    			ActualizarDatos(false);
    		}
    		
    	} else if (e.getActionCommand().equals("print")){
    		
    		this.doPrint();
    		
    	}
    	
    	// Tiro el evento para arriba en la gerarquia de objetos
    	super.actionPerformed(e);
    	
    }
    
    
    public void doDelete() {
    	
    	/**
    	 * Las reglas de negocio están en la capa de PERSISTENCIA
    	 */
    	
        // elimino el registro actual        
    	int currentRow = this.getTable().getSelectionModel().getMinSelectedIndex();
        
        Contrato oContrato = dataList.get(currentRow);
        
        // Borrar
    	try {
    		ContratoProcesos.delete(oContrato);
    		
    		// Actualizo la tabla
        	ActualizarDatos(true);
        	
        	new MessageWindowPane("Se ha borrado el CONTRATO en forma exitosa.");
        	
    	} catch(Exception e) {
    		e.printStackTrace();
    		new MessageWindowPane(e.getMessage());
    	}
        
    }
    
 
    /**
     * Invoca el servlet que imprime el reporte
     */
    @SuppressWarnings("static-access")
	public void doPrint() {        
        
        FWUsuario oFWUsuario = ((FWApplicationInstancePrincipal) this.getApplicationInstance().getActive()).getUsuario();
        String usuario = oFWUsuario.getUsuario();
        
        String sUri = "PDFPreview?reporte=listadoContratos&usuario=" + usuario;

        StringBuilder sb = new StringBuilder()
                .append("width=640")
                .append(",height=480")
                .append(",resizable=yes")
                .append(",scrollbars=yes");

        Command oComm = new BrowserOpenWindowCommand(sUri, "Prueba", sb.toString());
        ApplicationInstance.getActive().enqueueCommand(oComm);
    }
    
        
}