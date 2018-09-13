package app.busquedas.persona;

import java.util.ArrayList;
import java.util.List;

import ccecho2.base.CCButton;
import ccecho2.base.CCColumn;
import ccecho2.base.CCLabel;
import ccecho2.base.CCRow;
import ccecho2.base.CCTable;
import ccecho2.base.CCTextField;

import nextapp.echo2.app.ApplicationInstance;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Font;
import nextapp.echo2.app.ImageReference;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.ResourceImageReference;
import nextapp.echo2.app.event.ActionEvent;

import datos.Page;
import datos.persona.Persona;
import datos.persona.PersonaProcesos;

import framework.nr.generales.busquedas.FWBusquedas;
import framework.ui.generales.FWWindowPaneMensajes;
import framework.ui.generales.abms.ListadoFindView;
import framework.ui.principal.FWContentPanePrincipal;

@SuppressWarnings("serial")
public class PersonaFindListadoView extends ListadoFindView {
	
	private List<Persona> dataList;
	private Page dataListPage;
	
    private CCColumn _cPrincipal, cLabels, cTexts, cBusqueda;
	private CCRow rPrincipal;
	
	private CCLabel lApellido, lNombres;
	private CCTextField tApellido, tNombres;
	
    private ImageReference FILTRO = new ResourceImageReference("/resources/crystalsvg22x22/actions/find.png");    
    private CCButton btnFiltro;

    private FWContentPanePrincipal CPPrincipal;
    
    //private CCTextField tDescripcion; 
    private Persona oPersona_devuelta;
    private FWBusquedas refClaseLlama;
    private short filtro_actor;
	
    private int page_size = 9;
	private int page_number = 0;
	private CCLabel lPagina;
    
    
    public PersonaFindListadoView () {
    	
        super(null);
        
        this.setTitle("Busqueda Entidad");
        this.setWidth(new Extent(700, Extent.PX));
        
        //this.getTable().addActionListener(this);

        /** Dibujo los datos */
        //ActualizarDatos();

    }

    /**
     * Ver si le agrego un parámetro para filtrar por actor (filtro_actor):
     * Constantes en el objeto <b>ContratoActor</b>
     * PROPIETARIO	2
     * INQUILINO	1
     * TODOS		3
     */    
    public PersonaFindListadoView (String titulo, FWBusquedas refClaseLlama, short filtro_actor) {
    	
        super(null);
        
        CPPrincipal = ((FWContentPanePrincipal) ApplicationInstance.getActive().getDefaultWindow().getContent());
        
        this.refClaseLlama = refClaseLlama;
        this.filtro_actor = filtro_actor;
        
        this.setTitle("Busqueda Entidad - " + titulo );
        this.setWidth(new Extent(700, Extent.PX));
        this.setHeight(new Extent(500, Extent.PX));
        
        // this.getTable().addActionListener(this);
        
		// Agrego los componentes de la pantalla
		crearObjetos();
		renderObjetos();
        
        /** Dibujo los datos */
        //ActualizarDatos();

    }

    private void crearObjetos() {
    	
        _cPrincipal = new CCColumn();
        _cPrincipal.setCellSpacing(new Extent(20));
        _cPrincipal.setInsets(new Insets(10));
        
        rPrincipal = new CCRow();
        
        // Información de la página que se esta mostrando
        this.lPagina = new CCLabel();
        
        cLabels = new CCColumn();
        cLabels.setCellSpacing(new Extent(5, Extent.PX));
        cLabels.setInsets(new Insets(5));
        
        cTexts = new CCColumn();
        cTexts.setCellSpacing(new Extent(5, Extent.PX));
        cTexts.setInsets(new Insets(5));
        
        cBusqueda = new CCColumn();
        cBusqueda.setCellSpacing(new Extent(20, Extent.PX));
        cBusqueda.setInsets(new Insets(20));
   
        
        lApellido = new CCLabel("Apellido:",22);        
        tApellido = new CCTextField(300,22,10,true);
        tApellido.addActionListener(this);
        tApellido.setActionCommand("filtro");
           
        lNombres = new CCLabel("Nombres:",22);        
        tNombres = new CCTextField(300,22,20,true);
        tNombres.addActionListener(this);
        tNombres.setActionCommand("filtro");

        /* Tabla con el resultado de la búsqueda */
    	this.oTable = new CCTable();
    	this.oTable.setHeaderBackground(Color.LIGHTGRAY);
    	this.oTable.setFont(new Font(Font.ARIAL, Font.PLAIN, new Extent(11)));
    	
    	
        /* Configuro el boton de la Busqueda */
        btnFiltro = new CCButton(FILTRO);
        btnFiltro.addActionListener(this);
        this.btnFiltro.setActionCommand("filtro");
        this.btnFiltro.setToolTipText("Aplica los filtros a la búsqueda");        
        this.btnFiltro.setStyleName(null);

    	
        
    }
    
    
    private void renderObjetos() {
        
    	// Agrego la columna al ContentPane Principal
    	cpPrincipal.add(_cPrincipal);

        _cPrincipal.add(rPrincipal);
        rPrincipal.add(cLabels);
        rPrincipal.add(cTexts);
        rPrincipal.add(cBusqueda);
        
        cBusqueda.add(btnFiltro);
        
        cLabels.add(lApellido);
        cLabels.add(lNombres);

        cTexts.add(tApellido);
        cTexts.add(tNombres);        

        this.cpPrincipal.add(this.oTable);
        this.cpPrincipal.add(this.lPagina);
        
        ApplicationInstance.getActive().setFocusedComponent(tApellido);
        
    }
    
    
    
    @SuppressWarnings("unchecked")
	public void ActualizarDatos() {
    	
    	/** Levanto los filtros de búsqueda */
    	String filtro_apellido = tApellido.getText();
    	String filtro_nombre = tNombres.getText();
    	
    	
        /** Cargo los datos en la grilla */
        PersonaFindListadoModel oModel = new PersonaFindListadoModel();
        dataListPage = PersonaProcesos.findByFilter(filtro_apellido, filtro_nombre, filtro_actor, page_number, page_size);
        
        if (dataListPage.getTotalResults() != -1) {
        
	        dataList = dataListPage.getList();
	        oModel.setDataList(dataList);
	        
	        this.update(oModel, page_number+1, dataListPage.getLastPageNumber()+1);
	        
	        // Refresco la página
	        this.lPagina.setText("Página " + (page_number+1) + " de " + (dataListPage.getLastPageNumber()+1));
	        
        } else {
        	oModel.setDataList(new ArrayList<Persona>());
        	this.update(oModel, 0, 0);
        	this.lPagina.setText("La consulta de datos no devuelve ningún resultado !!!");        	
        }
        
    }
    
    
    /* Si quiero usar polimorfismo del método */
    public void actionPerformed(ActionEvent e) {
    	
    	//System.out.println("AP " + e.getActionCommand());
    	
    	if (e.getActionCommand().equals("asociar")){            
        	
    		if (this.getTable().getSelectionModel().getMinSelectedIndex() >= 0) {    			
    			
    			int currentRow = this.getTable().getSelectionModel().getMinSelectedIndex();
    			
    			oPersona_devuelta = dataList.get(currentRow);
    			//this.tDescripcion.setText(oPersona_devuelta.getApellido() + ", " + oPersona_devuelta.getNombres());
    			
    			// en base al tipo de ACTOR es el objeto que devuelvo    			
   				refClaseLlama.setResultado(oPersona_devuelta);
    			
    			this.exit();
    			
    		} else {
            	CPPrincipal.abrirVentana(new FWWindowPaneMensajes("Error BUSQUEDA ENTIDAD","Debe seleccionar una fila para continuar.",(short)1));
            }
			
    	} else if (e.getActionCommand().equals("filtro")){    		
    		    		
    		ActualizarDatos();
        
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
    	}
    	
    	// Tiro el evento para arriba en la gerarquia de objetos
    	super.actionPerformed(e);
    	
    }
            
} // Fin clase