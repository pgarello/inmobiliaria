package app.abms.contrato;

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import nextapp.echo2.app.ApplicationInstance;
import nextapp.echo2.app.Color;

import nextapp.echo2.app.Component;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.Table;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.layout.TableLayoutData;
import nextapp.echo2.app.list.ListSelectionModel;
import nextapp.echo2.app.table.TableCellRenderer;


import datos.contrato.Contrato;
import datos.contrato.ContratoProcesos;


import framework.ui.generales.abms.ABMListadoPrintView;
import framework.ui.principal.FWContentPanePrincipal;


@SuppressWarnings("serial")
public class ContratoListadoRevisarView extends ABMListadoPrintView {
	
	private List<Contrato> dataList;
	public boolean sin_datos = true;	
    
	public ContratoListadoRevisarView () {
    	
        super(null);
        
        this.setTitle("Contratos para REVISAR por indexación mensual");               		    		    
		this.setWidth(new Extent(800, Extent.PX));        
		this.setHeight(new Extent(600, Extent.PX));
              
        // Solamente es para que visualice
        this.setModal(false);
        
        this.limpiarBotoneraEdicion();
        
        ActualizarDatos();
                
        /** Dibujo los datos */
        //this.getBrowser().update();
    }

    
    
    @SuppressWarnings("unchecked")
	public void ActualizarDatos() {
    	
        /** Cargo los datos en la grilla */
    	
        ContratoListadoVencidosModel oModel = new ContratoListadoVencidosModel();
        List<Contrato> dataList_aux = ContratoProcesos.findRevisar();
        
        try {
			dataList_aux = ContratoProcesos.completar(dataList_aux);
		} catch (Exception e) {
			e.printStackTrace();
		}
                        
        /*
         * 1º tengo que buscar las cuotas de este mes
         * 2º después tengo que ver si esa cuota corresponde a un mes a revisar 
         */        
        dataList = new ArrayList<Contrato>();
        for (Contrato oContrato : dataList_aux) {
        	
        	if (oContrato.seDebeRevisarMontoCuota())
        		dataList.add(oContrato);
        	
        }
        System.out.println("Cantidad de CONTRATOS a REVISAR:" + dataList.size() + " // " + dataList_aux.size());
        
    	//oModel.setDataList(dataList);
    	//this.update(oModel, 0, 0);
        	
        if (dataList.size() > 0) {
        	            
        	oModel.setDataList(dataList);
        	this.update(oModel, 0, 0);
        	this.fila_color = -1;
        	
        	ListSelectionModel selectionModel = this.oTable.getSelectionModel();
        	selectionModel.clearSelection();
        	this.oTable.setWidth(null);
        	this.oTable.setDefaultRenderer(Object.class, randomizingCellRenderer);
        	
        }
        
        
    }
    
    private int fila_color = -1;
//    private TableCellRenderer randomizingCellRenderer = new TableCellRenderer() {
//    	  
//        public Component getTableCellRendererComponent(Table table, Object value, int column, int row) {
//        	//System.out.println("haber " + value.getClass().getName() + " - " + column + " - " + row);     	
//        	Label label = null;        	
//        	if (column == 1) {   
//        		//label = new Label("");
//        		if ((Boolean)value) {
//        			if (fila_color != row) fila_color = row;
//        		}   		
//        	} else {
//        		label = new Label(value == null ? null : value.toString());
//        		if (fila_color == row) {
//            		TableLayoutData layoutData = new TableLayoutData();
//        			layoutData.setBackground(Color.GREEN);            
//        			label.setLayoutData(layoutData);
//            	}
//        	}        	
//        	      	
//            return label;
//        }        
//    };
    
    private TableCellRenderer randomizingCellRenderer = new TableCellRenderer() {        
        public Component getTableCellRendererComponent(Table table, Object value, int column, int row) {
        	//System.out.println("haber " + value + " - " + column + " - " + row + " " + fila_color);
        	Label label = null;
        	if (column == 1) {
        		if (value.toString().equals("*"))
        			if (fila_color != row) fila_color = row;        			
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
        
    	} else if (e.getActionCommand().equals("edit")){
    		
    		/** Edición ----------------------------------------------- */

			
    	} else if (e.getActionCommand().equals("next")){
    		
    		
    	} else if (e.getActionCommand().equals("previous")){

    		
    	} else if (e.getActionCommand().equals("last")){
    		
    		
    	} else if (e.getActionCommand().equals("first")){
    		
    		
    	} else if (e.getActionCommand().equals("print")){
    		
    		this.doPrint();
    		
    	} else if (e.getActionCommand().equals("exit")){
        	
//    		System.out.println("pantalla 2 " + GraphicsEnvironment.isHeadless());
//    		
//    		((FWContentPanePrincipal) ApplicationInstance.getActive().getDefaultWindow().getContent())
//    		
//    		//if (!GraphicsEnvironment.isHeadless()) {
//                // Obtener el tamaño de la pantalla
//                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//
//                System.out.println("pantalla 2 " + screenSize.width + " x " + screenSize.height);
//                
//                // Establecer el tamaño de la ventana en un porcentaje del tamaño de la pantalla
//                int porcentajeAncho = 80; // Porcentaje del ancho de la pantalla
//                int porcentajeAlto = 80; // Porcentaje del alto de la pantalla
//                int ancho = (int) (screenSize.width * porcentajeAncho / 100);
//                int alto = (int) (screenSize.height * porcentajeAlto / 100);
//                this.setWidth(new Extent(ancho));
//                this.setHeight(new Extent(alto));
//            //} 
//    		//((FWContentPanePrincipal) ApplicationInstance.getActive().getDefaultWindow().getContent()).cerrarVentana(this);
//            //((CCContentPane) getParent()).remove(this);
        } 
    	
        // Tiro el evento para arriba en la gerarquia de objetos
        super.actionPerformed(e);
        
    	
    }
    
    
    public void doDelete() {    	
        
    }
     
 
    /**
     * Invoca el servlet que imprime el reporte
     */
	public void doPrint() {        
               
    }
    
        
}