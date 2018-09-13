package app.abms.comprobante.pago;

import java.util.Date;
import java.util.List;

import nextapp.echo2.app.ApplicationInstance;
import nextapp.echo2.app.Command;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.webcontainer.command.BrowserOpenWindowCommand;
import datos.Page;

import datos.recibo_pago.ReciboPagoProcesos;
import datos.recibo_pago_item.ReciboPagoItem;

import framework.grales.seguridad.FWUsuario;
import framework.ui.generales.abms.ABMListadoPrintView;

import framework.ui.principal.FWApplicationInstancePrincipal;


@SuppressWarnings("serial")
public class GananciaListadoView extends ABMListadoPrintView {
	
	private List<ReciboPagoItem> dataList;
	private Page dataListPage;
	
	private int filtro_nro_recibo;
	private int filtro_id_propietario;
	private int filtro_id_ganancia;
	private Date filtro_fecha_desde;
	private Date filtro_fecha_hasta;
	
	private int page_size = 9;
	private int page_number = 0;
	

    public GananciaListadoView (int filtro_nro_recibo, int filtro_id_propietario, int filtro_id_ganancia, Date filtro_fecha_desde, Date filtro_fecha_hasta) {
    	
        super(null);
        
        this.setTitle("Listado de Retenciones de GANANCIA");
        this.setWidth(new Extent(850, Extent.PX));
        this.setHeight(new Extent(350, Extent.PX));
        
        //this.getTable().addActionListener(this);
        
        this.filtro_nro_recibo = filtro_nro_recibo;
        this.filtro_id_propietario = filtro_id_propietario;
        this.filtro_id_ganancia = filtro_id_ganancia;
        this.filtro_fecha_desde = filtro_fecha_desde;
        this.filtro_fecha_hasta = filtro_fecha_hasta;
        
        // Saco los botones que no uso
        // this.soloEdicion();
        
        ActualizarDatos();
        
        this.sacarBotonEditar();
        this.sacarBotonBorrar();
                
        /** Dibujo los datos */
        //this.getBrowser().update();
        
    }
    
    
    @SuppressWarnings("unchecked")
	public void ActualizarDatos() {
    	
        /** Cargo los datos en la grilla */
    	//System.out.println("Filtros " + filtro_id_propiedad + " - " + filtro_id_inquilino);
        PagoItemGananciaModel oModel = new PagoItemGananciaModel();
        
        // Tengo que buscar los movimientos de retenciones según los filtros recibidos
        // Ver en que objeto voy a devolver los datos .... si puedo crear un objeto que no represente una tabla ¿?
        
        dataListPage = ReciboPagoProcesos.findGananciaByFilter(page_number, page_size, filtro_id_ganancia, filtro_id_propietario, filtro_nro_recibo, filtro_fecha_desde, filtro_fecha_hasta);
        dataList = dataListPage.getList();
        
        try {
			dataList = ReciboPagoProcesos.completarItems(dataList);
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        oModel.setDataList(dataList);
        this.update(oModel, page_number+1, dataListPage.getLastPageNumber()+1);
        
    }
    
    
    /* Si quiero usar polimorfismo del método */
    public void actionPerformed(ActionEvent e) {
    	
    	if (e.getActionCommand().equals("next")){
        		
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
        
        String sUri = 	"PDFPreview?reporte=listadoGanancia&usuario=" + usuario + 
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
