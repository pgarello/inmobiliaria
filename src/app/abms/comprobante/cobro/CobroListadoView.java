package app.abms.comprobante.cobro;

import java.util.Date;
import java.util.List;

import ccecho2.complex.MessageWindowPane;

import nextapp.echo2.app.ApplicationInstance;
import nextapp.echo2.app.Command;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.webcontainer.command.BrowserOpenWindowCommand;
import datos.Page;

import datos.recibo_cobro.ReciboCobro;
import datos.recibo_cobro.ReciboCobroProcesos;

import framework.grales.seguridad.FWUsuario;
import framework.ui.generales.FWWindowPaneMensajes;
import framework.ui.generales.abms.ABMListadoPrintView;

import framework.ui.principal.FWApplicationInstancePrincipal;
import framework.ui.principal.FWContentPanePrincipal;

@SuppressWarnings("serial")
public class CobroListadoView extends ABMListadoPrintView {
	
	private List<ReciboCobro> dataList;
	private Page dataListPage;
	
	private int filtro_nro_recibo;
	private int filtro_id_inquilino;
	private int filtro_id_propiedad;
	private Date filtro_fecha_desde;
	private Date filtro_fecha_hasta;
	
	private int page_size = 9;
	private int page_number = 0;
	

    public CobroListadoView (int filtro_nro_recibo, int filtro_id_inquilino, int filtro_id_propiedad, Date filtro_fecha_desde, Date filtro_fecha_hasta) {
    	
        super(null);
        
        this.setTitle("Listado de Comprobantes - Liquidaci�n Cobranza");
        this.setWidth(new Extent(700, Extent.PX));
        this.setHeight(new Extent(350, Extent.PX));
        
        //this.getTable().addActionListener(this);
        
        this.filtro_nro_recibo = filtro_nro_recibo;
        this.filtro_id_inquilino = filtro_id_inquilino;
        this.filtro_id_propiedad = filtro_id_propiedad;
        this.filtro_fecha_desde = filtro_fecha_desde;
        this.filtro_fecha_hasta = filtro_fecha_hasta;
        
        // Saco los botones que no uso
        // this.soloEdicion();
        
        ActualizarDatos();
                
        /** Dibujo los datos */
        //this.getBrowser().update();
    }

    
    
    @SuppressWarnings("unchecked")
	public void ActualizarDatos() {
    	
        /** Cargo los datos en la grilla */
    	//System.out.println("Filtros " + filtro_id_propiedad + " - " + filtro_id_inquilino);
        CobroListadoModel oModel = new CobroListadoModel();
        dataListPage = ReciboCobroProcesos.findByFilter(page_number, page_size, filtro_id_propiedad, filtro_id_inquilino, filtro_nro_recibo, filtro_fecha_desde, filtro_fecha_hasta);
        dataList = dataListPage.getList();
        
        try {
			dataList = ReciboCobroProcesos.completar(dataList);
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        oModel.setDataList(dataList);
        this.update(oModel, page_number+1, dataListPage.getLastPageNumber()+1);
        
    }
    
    
    /* Si quiero usar polimorfismo del m�todo */
    public void actionPerformed(ActionEvent e) {
    	
    	if (e.getActionCommand().equals("new")){            
        	
    		/** Inserci�n -------------------------------------------- */
        	// new PersonaAddView(this);
			
    	} else if (e.getActionCommand().equals("delete")){

    		/** Borrar ----------------------------------------------- */
    		    		
    		if (this.getTable().getSelectionModel().getMinSelectedIndex() >= 0) {    			
    			new MessageWindowPane("�Est� seguro de eliminar permanentemente el Registro?", this, "doDelete");
    		} else {
            	((FWContentPanePrincipal) ApplicationInstance
            			.getActive().getDefaultWindow().getContent())
                        .abrirVentana(new FWWindowPaneMensajes("Error Listado de Recibos COBRANZA","Debe seleccionar una fila para continuar.",(short)1));
            }
        
    	} else if (e.getActionCommand().equals("edit")){
    		
    		/** Edici�n ----------------------------------------------- */
    		int currentRow = this.getTable().getSelectionModel().getMinSelectedIndex();
    		if (currentRow >= 0) {
    			
    			ReciboCobro oRecibo = dataList.get(currentRow);
//    			try {
//					oRecibo = ReciboCobroProcesos.completarConSaldo(oRecibo);
//				} catch (Exception e1) {
//					e1.printStackTrace();
//					// reviento !!!!!!!!!!!!
//				}
				
    			((FWContentPanePrincipal) ApplicationInstance
            			.getActive().getDefaultWindow().getContent())
                        .abrirVentana(new CobroListadoEditView(this, oRecibo));                
    			
    		} else {
            	((FWContentPanePrincipal) ApplicationInstance
            			.getActive().getDefaultWindow().getContent())
                        .abrirVentana(new FWWindowPaneMensajes("Error Listado de Recibos COBRANZA","Debe seleccionar una fila para continuar.",(short)1));
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
    	// int currentRow = this.getTable().getSelectionModel().getMinSelectedIndex();
        /*
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
    	*/
        
    }
    
    /**
     * Invoca el servlet que imprime el reporte
     */
    @SuppressWarnings("static-access")
	public void doPrint() {        
        
        FWUsuario oFWUsuario = ((FWApplicationInstancePrincipal) this.getApplicationInstance().getActive()).getUsuario();
        String usuario = oFWUsuario.getUsuario();
        
        // Necesito pasarle los filtros !!!!!!!!!!!!!!
        // Si les paso los datos consultados, est�n paginados ....        
        long fecha_desde = 0;
        if (this.filtro_fecha_desde != null) fecha_desde = this.filtro_fecha_desde.getTime();
        
        long fecha_hasta = 0;
        if (this.filtro_fecha_hasta != null) fecha_hasta = this.filtro_fecha_hasta.getTime();
        
        String sUri = 	"PDFPreview?reporte=listadoCobros&usuario=" + usuario + 
        				"&filtro_nro=" + this.filtro_nro_recibo +
        				"&filtro_desde=" + fecha_desde +
        				"&filtro_hasta=" + fecha_hasta;

        StringBuilder sb = new StringBuilder()
                .append("width=640")
                .append(",height=480")
                .append(",resizable=yes")
                .append(",scrollbars=yes");

        Command oComm = new BrowserOpenWindowCommand(sUri, "Prueba", sb.toString());
        ApplicationInstance.getActive().enqueueCommand(oComm);
    }
        
}
