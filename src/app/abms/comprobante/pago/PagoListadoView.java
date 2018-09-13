package app.abms.comprobante.pago;

import java.util.Date;
import java.util.List;

import ccecho2.complex.MessageWindowPane;

import nextapp.echo2.app.ApplicationInstance;
import nextapp.echo2.app.Command;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.webcontainer.command.BrowserOpenWindowCommand;
import datos.Page;

import datos.recibo_pago.ReciboPago;
import datos.recibo_pago.ReciboPagoProcesos;

import framework.grales.seguridad.FWUsuario;
import framework.ui.generales.FWWindowPaneMensajes;
import framework.ui.generales.abms.ABMListadoPrintView;

import framework.ui.principal.FWApplicationInstancePrincipal;
import framework.ui.principal.FWContentPanePrincipal;

@SuppressWarnings("serial")
public class PagoListadoView extends ABMListadoPrintView {
	
	private List<ReciboPago> dataList;
	private Page dataListPage;
	
	private int filtro_nro_recibo;
	private int filtro_id_propietario;
	private int filtro_id_propiedad;
	private Date filtro_fecha_desde;
	private Date filtro_fecha_hasta;
	
	private int page_size = 9;
	private int page_number = 0;
	

    public PagoListadoView (int filtro_nro_recibo, int filtro_id_propietario, int filtro_id_propiedad, Date filtro_fecha_desde, Date filtro_fecha_hasta) {
    	
        super(null);
        
        this.setTitle("Listado de Comprobantes - Liquidación Pago");
        this.setWidth(new Extent(700, Extent.PX));
        this.setHeight(new Extent(350, Extent.PX));
        
        //this.getTable().addActionListener(this);
        
        this.filtro_nro_recibo = filtro_nro_recibo;
        this.filtro_id_propietario = filtro_id_propietario;
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
        PagoListadoModel oModel = new PagoListadoModel();
        dataListPage = ReciboPagoProcesos.findByFilter(page_number, page_size, filtro_id_propiedad, filtro_id_propietario, filtro_nro_recibo, filtro_fecha_desde, filtro_fecha_hasta);
        dataList = dataListPage.getList();
        
        try {
			dataList = ReciboPagoProcesos.completar(dataList);
		} catch (Exception e) {
			e.printStackTrace();
		}
        
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
                        .abrirVentana(new FWWindowPaneMensajes("Error Listado de Comprobantes","Debe seleccionar una fila para continuar.",(short)1));
            }
        
    	} else if (e.getActionCommand().equals("edit")){
    		
    		/** Edición ----------------------------------------------- */
    		int currentRow = this.getTable().getSelectionModel().getMinSelectedIndex();
    		if (currentRow >= 0) {
    			
    			ReciboPago oRecibo = dataList.get(currentRow);
//    			try {
//					oRecibo = ReciboCobroProcesos.completarConSaldo(oRecibo);
//				} catch (Exception e1) {
//					e1.printStackTrace();
//					// reviento !!!!!!!!!!!!
//				}
				
    			((FWContentPanePrincipal) ApplicationInstance
            			.getActive().getDefaultWindow().getContent())
                        .abrirVentana(new PagoListadoEditView(this, oRecibo));                
    			
    		} else {
            	((FWContentPanePrincipal) ApplicationInstance
            			.getActive().getDefaultWindow().getContent())
                        .abrirVentana(new FWWindowPaneMensajes("Error ABM CONTRATOS","Debe seleccionar una fila para continuar.",(short)1));
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
    	
    	System.out.println("Estoy en PagoListadoView.doDelete");
    	
        // elimino el registro actual        
    	int currentRow = this.getTable().getSelectionModel().getMinSelectedIndex();
        
    	ReciboPago oRecibo = dataList.get(currentRow);
        
        // Borrar
    	try {
    		ReciboPagoProcesos.delete(oRecibo);
    		
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
    @SuppressWarnings("static-access")
	public void doPrint() {        
        
        FWUsuario oFWUsuario = ((FWApplicationInstancePrincipal) this.getApplicationInstance().getActive()).getUsuario();
        String usuario = oFWUsuario.getUsuario();
        
        // Necesito pasarle los filtros !!!!!!!!!!!!!!
        // Si les paso los datos consultados, están paginados ....        
        long fecha_desde = 0;
        if (this.filtro_fecha_desde != null) fecha_desde = this.filtro_fecha_desde.getTime();
        
        long fecha_hasta = 0;
        if (this.filtro_fecha_hasta != null) fecha_hasta = this.filtro_fecha_hasta.getTime();
        
        String sUri = 	"PDFPreview?reporte=listadoPagos&usuario=" + usuario + 
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
