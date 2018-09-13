package app.abms.comprobante.factura;

import java.util.Date;
import java.util.List;

import ccecho2.complex.MessageWindowPane;

import nextapp.echo2.app.ApplicationInstance;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.event.ActionEvent;
import datos.Page;

import datos.factura.Factura;
import datos.factura.FacturaProcesos;


import framework.ui.generales.FWWindowPaneMensajes;
import framework.ui.generales.abms.ABMListadoView;
import framework.ui.principal.FWContentPanePrincipal;

@SuppressWarnings("serial")
public class FacturaListadoView extends ABMListadoView {
	
	private List<Factura> dataList;
	private Page dataListPage;
	
	private int filtro_nro_factura;
	private int filtro_id_inquilino;
	private int filtro_id_propiedad;
	private Date filtro_fecha_emision;
	
	private int page_size = 9;
	private int page_number = 0;
	

    public FacturaListadoView (int filtro_nro_factura, int filtro_id_inquilino, int filtro_id_propiedad, Date filtro_fecha_emision) {
    	
        super(null);
        
        this.setTitle("Listado de Facturas");
        this.setWidth(new Extent(700, Extent.PX));
        this.setHeight(new Extent(350, Extent.PX));
        
        //this.getTable().addActionListener(this);
        
        this.filtro_nro_factura = filtro_nro_factura;
        this.filtro_id_inquilino = filtro_id_inquilino;
        this.filtro_id_propiedad = filtro_id_propiedad;
        this.filtro_fecha_emision = filtro_fecha_emision;
        
        // Saco los botones que no uso
        this.soloEdicion();
        
        ActualizarDatos();
                
        /** Dibujo los datos */
        //this.getBrowser().update();
    }

    
    
    @SuppressWarnings("unchecked")
	public void ActualizarDatos() {
    	
        /** Cargo los datos en la grilla */
    	//System.out.println("Filtros " + filtro_id_propiedad + " - " + filtro_id_inquilino);
        FacturaListadoModel oModel = new FacturaListadoModel();
        dataListPage = FacturaProcesos.findByFilter(page_number, page_size, filtro_id_propiedad, filtro_id_inquilino, filtro_nro_factura, filtro_fecha_emision);
               
        System.out.println("FacturaListadoView " + dataListPage.getTotalResults() + " .. " + dataListPage.getList().size());
        if (dataListPage.getList().size() == 0) {
        	// salgo con el mensaje de que la consulta de datos no devuelve ningún resultado ???
        	
        	this.lPagina.setText("Su consulta de datos no devuelve ningún resultado!");
        	
        } else {
        	        	
	        dataList = dataListPage.getList();        
	        
	        try {
				dataList = FacturaProcesos.completar(dataList);
			} catch (Exception e) {
				e.printStackTrace();
			}
	        
	        oModel.setDataList(dataList);
	        this.update(oModel, page_number+1, dataListPage.getLastPageNumber()+1);
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
                        .abrirVentana(new FWWindowPaneMensajes("Error ABM CONTRATOS","Debe seleccionar una fila para continuar.",(short)1));
            }
        
    	} else if (e.getActionCommand().equals("edit")){
    		
    		/** Edición ----------------------------------------------- */
    		int currentRow = this.getTable().getSelectionModel().getMinSelectedIndex();
    		if (currentRow >= 0) {
    			
    			Factura oFactura = dataList.get(currentRow);
//    			try {
//					oRecibo = ReciboCobroProcesos.completarConSaldo(oRecibo);
//				} catch (Exception e1) {
//					e1.printStackTrace();
//					// reviento !!!!!!!!!!!!
//				}
				
    			((FWContentPanePrincipal) ApplicationInstance
            			.getActive().getDefaultWindow().getContent())
                        .abrirVentana(new FacturaListadoEditView(this, oFactura));                
    			
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
        
}
