package app.abms.contrato;

//import java.text.DecimalFormat;
import java.util.List;

import ccecho2.base.CCTextField;
import ccecho2.complex.MessageWindowPane;

import nextapp.echo2.app.ApplicationInstance;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Component;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.Table;

import nextapp.echo2.app.Extent;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.layout.TableLayoutData;
import nextapp.echo2.app.table.TableCellRenderer;


import datos.contrato.Contrato;

import datos.contrato_novedad_cobro.ContratoNovedadCobro;
import datos.contrato_novedad_cobro.ContratoNovedadCobroProcesos;


import framework.ui.generales.FWWindowPaneMensajes;
import framework.ui.generales.abms.ABMListadoView;
import framework.ui.principal.FWContentPanePrincipal;



@SuppressWarnings("serial")
public class ContratoCtaCteView extends ABMListadoView {
	
	private List<ContratoNovedadCobro> dataListPago;
	private Contrato oContrato;
	private CCTextField tMonto;
	ContratoCtaCteModel oModel = new ContratoCtaCteModel();
	
    public ContratoCtaCteView (Contrato oContrato) {
        
    	super(null, false);
    	
    	// Seteo el título de la ventana
		this.setTitle("Cuenta Corriente del Contrato - Liquidación COBRANZA - IV");
		
		this.setHeight(new Extent(500, Extent.PX));
		this.setWidth(new Extent(600, Extent.PX));
		
		// Es una pantalla de visualización
		// Probar posicionarla en forma especial
		
		//this.setModal(false);
		
		this.oContrato = oContrato;
		
		// Limpio los botones que no uso
		this.rBotones.removeAll();
		
		// Declaro los componentes aparte de la grilla
        tMonto = new CCTextField(100,22,20,true);
        tMonto.setText("0,00");
        tMonto.setRegex("^(-)?(\\d){1,3}(\\.(\\d){3})*(,\\d{1,2})?$");
        //cPrincipal.add(tMonto);
		
		ActualizarDatos();
		
		// se realiza antes de mostrar los datos por lo que esta en 0 en contador aun
        //DecimalFormat moneda = new DecimalFormat("###,##0.00");
        //tMonto.setText(moneda.format(oModel.getTotal_cobro()));
        //System.out.println("PRUEBA " + moneda.format(oModel.getTotal_cobro()));
    }
        
	
    public void ActualizarDatos() {
    	
        /** 
         * Cargo los datos en la grilla 
         * a - 	Tengo que buscar primero los datos de las novedades de cobro
         * 		Liquidación COBRO - Inquilino
         */
    	    	    	
        //ContratoCtaCteModel oModel = new ContratoCtaCteModel();
        dataListPago = ContratoNovedadCobroProcesos.buscarNovedadesCobroPorContrato(this.oContrato, false, false);
        
        //System.out.println("DATOS de novedades de PAGO: " + dataListPago.size());
                
        oModel.setDataList(dataListPago);
        this.update(oModel,0,0);
        
        this.oTable.setDefaultRenderer(Object.class, randomizingCellRenderer);
        
    }
	    
    private int fila_color = 0;    
    private TableCellRenderer randomizingCellRenderer = new TableCellRenderer() {
        
        public Component getTableCellRendererComponent(Table table, Object value, int column, int row) {
        	//System.out.println("haber " + value.getClass().getName() + " - " + column + " - " + row);
        	/** Como hago para saber que la fila hay que pintarla de un color particular ¿? */        	
        	Label label = new Label(value == null ? null : value.toString());
        	
        	if ((column == 4 && value.toString().equals("$ 0,00")) || fila_color == row) {
        		if (fila_color != row) fila_color = row;
	            TableLayoutData layoutData = new TableLayoutData();
	            layoutData.setBackground(Color.GREEN);            
	            label.setLayoutData(layoutData);
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
    			
    			//Contrato oContrato = dataList.get(currentRow);
    			/*
    			((FWContentPanePrincipal) ApplicationInstance
            			.getActive().getDefaultWindow().getContent())
                        .abrirVentana(new ContratoListadoEditView(this, oContrato));
                */                
    			
    		} else {
            	((FWContentPanePrincipal) ApplicationInstance
            			.getActive().getDefaultWindow().getContent())
                        .abrirVentana(new FWWindowPaneMensajes("Error ABM CONTRATOS","Debe seleccionar una fila para continuar.",(short)1));
            }
			
    	} 
    	
    	// Tiro el evento para arriba en la gerarquia de objetos
    	super.actionPerformed(e);
    	
    }
               
}