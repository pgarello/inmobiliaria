package app.busquedas.inmueble;

import java.util.ArrayList;
import java.util.List;

import app.combos.ComboEdificio;

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
import datos.contrato.ContratoProcesos;
import datos.inmueble.Inmueble;
import datos.inmueble.InmuebleProcesos;

import framework.nr.generales.busquedas.FWBusquedas;
import framework.ui.generales.FWWindowPaneMensajes;
import framework.ui.generales.abms.ListadoFindView;
import framework.ui.principal.FWContentPanePrincipal;

@SuppressWarnings("serial")
public class InmuebleFindListadoView extends ListadoFindView {
	
	private List<Inmueble> dataList;
	private Page dataListPage;
	
    private CCColumn _cPrincipal, cLabels, cTexts, cBusqueda;
	private CCRow rPrincipal;
	
	private CCLabel lDir_calle, lDir_edificio;
	private CCTextField tDir_calle;
	//private CCCheckBox chConSaldo;
	//ComboList cboEdificio;
	private ComboEdificio cboEdificio;
	
    private ImageReference FILTRO = new ResourceImageReference("/resources/crystalsvg22x22/actions/find.png");    
    private CCButton btnFiltro;

    private FWContentPanePrincipal CPPrincipal;
    
    private Inmueble oInmueble_devuelta;
    private FWBusquedas refClaseLlama;
    
    private int page_size = 9;
	private int page_number = 0;
	private boolean vigente;
	
	private CCLabel lPagina;
	
    public InmuebleFindListadoView () {
    	
        super(null);
        
        this.setTitle("Busqueda de Propiedades");
        this.setHeight(new Extent(400, Extent.PX));
        this.setWidth(new Extent(700, Extent.PX));
        
        /** Dibujo los datos */
        //ActualizarDatos();

    }

    /**
     * Le agrego al constructor una bandera booleana para realizar la búsqueda de los inmuebles
     * con contratos vigentes o todos
     * 
     * vigentes		true: busca los inmuebles con contrato vigente a la fecha
     * 				false: busca todos 
     * 
     * Pgarello 17-01-2011
     */
    public InmuebleFindListadoView (FWBusquedas refClaseLlama, boolean vigentes) {
    	
        super(null);
        
        CPPrincipal = ((FWContentPanePrincipal) ApplicationInstance.getActive().getDefaultWindow().getContent());
        
        this.refClaseLlama = refClaseLlama;
        this.vigente = vigentes;
        
        this.setTitle("Busqueda de Propiedades");
        this.setHeight(new Extent(400, Extent.PX));
        this.setWidth(new Extent(700, Extent.PX));
        
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
   
        
        lDir_calle = new CCLabel("Calle:",22);        
        tDir_calle = new CCTextField(300,22,10,true);
        tDir_calle.addActionListener(this);
        tDir_calle.setActionCommand("filtro");
           
        lDir_edificio = new CCLabel("Edificio:",22);        
        //tDir_edificio = new CCTextField(300,22,20,true);
        cboEdificio = new ComboEdificio(100,22,60,true);
        cboEdificio.setSelectedText(0);

//        lFiltro = new CCLabel("Filtro:",22);
//        chConSaldo = new CCCheckBox("Cuotas con saldo", 22);
//        chConSaldo.setSelected(true);
        
        /* Tabla con el resultado de la búsqueda */
    	this.oTable = new CCTable();
    	this.oTable.setHeaderBackground(Color.LIGHTGRAY);
    	this.oTable.setFont(new Font(Font.ARIAL, Font.PLAIN, new Extent(11)));    	
    	
        /* Configuro el boton de la Busqueda */
        btnFiltro = new CCButton(FILTRO);
        btnFiltro.setActionCommand("filtro");
        btnFiltro.setToolTipText("Aplica los filtros a la búsqueda");        
        btnFiltro.setStyleName(null);
        btnFiltro.addActionListener(this);
    	
        
    }
    
    
    private void renderObjetos() {
        
    	// Agrego la columna al ContentPane Principal
    	cpPrincipal.add(_cPrincipal);

        _cPrincipal.add(rPrincipal);
        rPrincipal.add(cLabels);
        rPrincipal.add(cTexts);
        rPrincipal.add(cBusqueda);
        
        cBusqueda.add(btnFiltro);
        
        cLabels.add(lDir_calle);
        cLabels.add(lDir_edificio);
        //cLabels.add(lFiltro);

        cTexts.add(tDir_calle);
        //cTexts.add(tDir_edificio);
        cTexts.add(cboEdificio);
        //cTexts.add(chConSaldo);

        this.cpPrincipal.add(this.oTable);      
        this.cpPrincipal.add(this.lPagina);
        
        ApplicationInstance.getActive().setFocusedComponent(tDir_calle);
        
    }
    
    
    
    @SuppressWarnings("unchecked")
	public void ActualizarDatos() {
    	
    	/** Levanto los filtros de búsqueda */
    	String filtro_calle = tDir_calle.getText();
    	
    	int filtro_edificio = 0;
    	try {filtro_edificio = Integer.parseInt(cboEdificio.getSelectedId());} catch(Exception e) {}
    	
    	
        /** Cargo los datos en la grilla - realizo la busqueda */
        InmuebleFindListadoModel oModel = new InmuebleFindListadoModel();
        dataListPage = InmuebleProcesos.findByFilter(filtro_calle, filtro_edificio, (short)0, 0, page_number, page_size);
        dataList = dataListPage.getList();
        
        /** Filtro los datos segun si tienen contrato vigente 
         * 
         * Para que este mejor implemetado debo hacer los filtros en la consulta directamente
         * 02/04/2012
         * */
        //System.out.println("Datos de la consulta: " + dataList.size() + " pag " + dataListPage.getLastPageNumber());
        List<Inmueble> dataList_filtrada = new ArrayList<Inmueble>();
        for (Inmueble oInmueble: dataList) {
        	
        	// llamo al proceso que valida la existencia de un contrato vigente
        	if (this.vigente) {
        		int contratos = (ContratoProcesos.findByFilter(this.vigente, oInmueble.getIdInmueble(), 0, 0, 0, 1, null, null, false, false, null, null, 0)).getList().size();
        		//System.out.println("CONTRATO: " + contratos);
        		if (contratos > 0)
        			dataList_filtrada.add(oInmueble);
        	} else {
        		dataList_filtrada.add(oInmueble);
        	}
        	
        }        
        
        dataList = dataList_filtrada;
        oModel.setDataList(dataList_filtrada);
        this.update(oModel, 0, 0);
        
        // Refresco la página
        this.lPagina.setText("Página " + (page_number+1) + " de " + (dataListPage.getLastPageNumber()+1));
        
    }
    
    
    /* Si quiero usar polimorfismo del método */
    public void actionPerformed(ActionEvent e) {
    	
    	if (e.getActionCommand().equals("asociar")){       
        	
    		if (this.getTable().getSelectionModel().getMinSelectedIndex() >= 0) {    			
    		
    			int currentRow = this.getTable().getSelectionModel().getMinSelectedIndex();
    			
    			oInmueble_devuelta = dataList.get(currentRow);
    			
   				refClaseLlama.setResultado(oInmueble_devuelta);
    			
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