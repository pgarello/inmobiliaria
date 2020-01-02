package app.abms.contrato;

import java.util.Calendar;
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

import datos.contrato.Contrato;
import datos.contrato.ContratoProcesos;

import framework.grales.seguridad.FWUsuario;

import framework.ui.generales.abms.ABMListadoPrintView;

import framework.ui.principal.FWApplicationInstancePrincipal;

@SuppressWarnings("serial")
public class ContratoListadoVencidosView extends ABMListadoPrintView {
	
	private List<Contrato> dataList;
	public boolean sin_datos = true;
	
    
    //public ContratoListadoVencidosView (Boolean selectorFecha) {
	public ContratoListadoVencidosView () {
    	
        super(null);
        
        this.setTitle("Contratos Por Vencer");
        this.setWidth(new Extent(800, Extent.PX));
        this.setHeight(new Extent(450, Extent.PX));
               
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
        dataList = ContratoProcesos.findPorVencer();
        
        try {
			dataList = ContratoProcesos.completar(dataList);
		} catch (Exception e) {
			e.printStackTrace();
		}
        
		// Evaluo si hay algún contrato que ya no tenga cargada una renovación, para que sea una renovación tiene que ser
		// la misma propiedad y el mismo inquilino ademas de diferir en la fecha, debe ser posterior al de la lista.
        for (Contrato oContrato : dataList) {
        	int filtro_inmueble = oContrato.getInmueble().getIdInmueble();
        	int filtro_inquilino = oContrato.getInquilino().getIdPersona();
        	boolean filtro_vigente = false;
        	int filtro_propietario = 0;
        	int page_number = 0;
        	int page_size = 1;
        	
        	Calendar fechaDesde1 = Calendar.getInstance();
        	fechaDesde1.setTime(oContrato.getFechaHasta());
        	fechaDesde1.add(Calendar.DATE, 1);
        	Date filtro_fecha_desde = fechaDesde1.getTime();
        	
        	Date filtro_fecha_hasta = null;
        	
        	List<Contrato> lContratos = (ContratoProcesos.findByFilter( filtro_vigente, filtro_inmueble, filtro_inquilino, 
        																filtro_propietario, page_number, page_size, filtro_fecha_desde, 
        																filtro_fecha_hasta, false, false)).getList();
        	
        	if (lContratos.size() > 0) {
        		//System.out.println("YA RENOVADOOOOOOOO ");
        		oContrato.setYaRenovado(true);
        	}        	
        	
        }
           
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
        	ActualizarDatos();
        	
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