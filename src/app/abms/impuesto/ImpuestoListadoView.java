package app.abms.impuesto;

import java.util.List;

import ccecho2.complex.MessageWindowPane;

import nextapp.echo2.app.ApplicationInstance;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.event.ActionEvent;
//import datos.Page;

import datos.contrato.Contrato;
import datos.contrato.ContratoNovedadDTO;
import datos.contrato.ImpuestoProcesos;

import framework.ui.generales.FWWindowPaneMensajes;
import framework.ui.generales.abms.ABMListadoView;
import framework.ui.generales.exception.ReglasDeNegocioException;
import framework.ui.principal.FWContentPanePrincipal;

@SuppressWarnings("serial")
public class ImpuestoListadoView extends ABMListadoView {
	
	private List<ContratoNovedadDTO> dataList;
	//private Page dataListPage;
	private Contrato oContrato;

    
    public ImpuestoListadoView (Contrato oContrato) {
    	
        super(null);
        
        this.setTitle("Listado de Impuestos");
        this.setWidth(new Extent(700, Extent.PX));
        
        //this.getTable().addActionListener(this);
        this.oContrato = oContrato;
        
        ActualizarDatos();
                
        /** Dibujo los datos */
        //this.getBrowser().update();
    }

    
    
    @SuppressWarnings("unchecked")
	public void ActualizarDatos() {
    	
    	/* 
    	 * Puedo tener Novedades que paga el propietario o que paga el inquilino
    	 * 
    	 * Puedo crear una clase ImpuestoProcesos que busque todos los datos y me los devuelva ¿?
    	 * No son Impuestos sino NOVEDADES ¿?
    	 */ 
    	
        /** Cargo los datos en la grilla */
        ImpuestoListadoModel oModel = new ImpuestoListadoModel();
        //dataListPage = ContratoProcesos.findByFilter(0,9);
        //dataList = dataListPage.getList();
        
        /** Valido que haya seleccionado un Contrato */        
        
        
        dataList = ImpuestoProcesos.buscarNovedades(this.oContrato);
        
        try {
			//dataList = ReciboCobroProcesos.completar(dataList);
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        oModel.setDataList(dataList);
        this.update(oModel,0,0);
        
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
                        .abrirVentana(new FWWindowPaneMensajes("Error ABM IMPUESTOS","Debe seleccionar una fila para continuar.",(short)1));
            }
        
    	} else if (e.getActionCommand().equals("edit")){
    		
    		/** Edición ----------------------------------------------- */
    		int currentRow = this.getTable().getSelectionModel().getMinSelectedIndex();
    		if (currentRow >= 0) {
    			
    			ContratoNovedadDTO oContratoNovedadDTO = dataList.get(currentRow);
    			 
    			((FWContentPanePrincipal) ApplicationInstance
            			.getActive().getDefaultWindow().getContent())
                        .abrirVentana(new ImpuestoListadoEditView(this, oContratoNovedadDTO));                
    			
    		} else {
            	((FWContentPanePrincipal) ApplicationInstance
            			.getActive().getDefaultWindow().getContent())
                        .abrirVentana(new FWWindowPaneMensajes("Error ABM IMPUESTOS","Debe seleccionar una fila para continuar.",(short)1));
            }
			
    	} 
    	
    	// Tiro el evento para arriba en la gerarquia de objetos
    	super.actionPerformed(e);
    	
    }
    
    
    public void doDelete() {
        // elimino el registro actual        
    	@SuppressWarnings("unused")
		int currentRow = this.getTable().getSelectionModel().getMinSelectedIndex();
        
    	/**
    	 * Manejo la colección con abstracción ya que no se si el impuesto a borrar lo paga el inquilino o el propietario.
    	 * OJO pueden ser ambos, en ese caso ¿?¿?¿?
    	 * Tengo que evaluar los montos: inquilino y propietario. 
    	 * 		INQUILINO --> NOVEDAD COBRO
    	 * 		PROPIETARIO --> NOVEDAD PAGO
    	 */
    	
        ContratoNovedadDTO oContratoNovedadDTO = dataList.get(currentRow);
        
        // Reglas de negocio de la eliminación ¿Van aca o en la fachada de procesos?
        
       try {
    	   ImpuestoProcesos.borrarNovedad(oContratoNovedadDTO);
       } catch (ReglasDeNegocioException e) {
    	   e.printStackTrace();
    	   ((FWContentPanePrincipal) ApplicationInstance
       			.getActive().getDefaultWindow().getContent())
                .abrirVentana(new FWWindowPaneMensajes("Error ABM IMPUESTOS","No se puede eliminar el IMPUESTO, chequee que no existan datos asociados.",(short)1));
       }
    	
        
    }
    
    
        
}
