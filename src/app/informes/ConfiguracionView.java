package app.informes;

import java.util.List;

import nextapp.echo2.app.ApplicationInstance;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Command;
import nextapp.echo2.app.Component;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.Table;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.layout.TableLayoutData;
import nextapp.echo2.app.table.TableCellRenderer;
import nextapp.echo2.webcontainer.command.BrowserOpenWindowCommand;

import datos.contrato_novedad_cobro.ContratoNovedadCobro;
import datos.contrato_novedad_cobro.ContratoNovedadCobroProcesos;

import framework.grales.seguridad.FWUsuario;
import framework.nr.generales.filtros.FWFiltros;

import framework.ui.generales.abms.ABMListadoPrintView;
import framework.ui.principal.FWApplicationInstancePrincipal;


@SuppressWarnings("serial")
public class ConfiguracionView extends ABMListadoPrintView {
	
	private List<ContratoNovedadCobro> dataList;
	
	public boolean sin_datos = true;

    
    public ConfiguracionView () {
    	
        super(null);
        
        this.setTitle("Datos de Configuración");
        this.setWidth(new Extent(800, Extent.PX));
        this.setHeight(new Extent(500, Extent.PX));
        
        //this.getTable().addActionListener(this);        
        this.limpiarBotoneraEdicion();
        //this.agregarBotonImprimir();
        //this.agregarBotonExcel();
        
        ActualizarDatos();
                
        /** Dibujo los datos */
        //this.getBrowser().update();
    }

    
    
    @SuppressWarnings("unchecked")
	public void ActualizarDatos() {
    	
        /** Cargo los datos en la grilla */
//        DeudaListadoModel oModel = new DeudaListadoModel();
//        
//        List<ContratoNovedadCobro> lNovedades = ContratoNovedadCobroProcesos.buscarNovedadesCobroDeuda(filtro_mes, filtro_anio); //, page_number, page_size);
//        
//        // Controlo que la consulta no de vacia
//        if (lNovedades.size() > 0) {
//        
//        	this.sin_datos = false;
//        	//refClaseFiltros.setMensaje("");
//        	
//        	dataList = lNovedades;
//        
//        	oModel.setDataList(dataList);
//        	this.update(oModel);
//        	
//        	this.oTable.setDefaultRenderer(Object.class, randomizingCellRenderer);
//        	
//        } else {
//        	        	
//        	// salgo
//        	// this.actionPerformed(new ActionEvent(this.bExit, "exit"));      	
//        	
//        	// Informo en pantalla que me llamo ¿?
//        	refClaseFiltros.setMensaje("La consulta no devuelve ningún dato.");
//        	
//        	//((CCContentPane) getParent()).remove(this);        	        	
//        	//((FWContentPanePrincipal) ApplicationInstance.getActive().getDefaultWindow().getContent()).cerrarVentana(this);
//        	
//        }
        
    }
    
    private int fila_color = -1;    
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
        	
    	if (e.getActionCommand().equals("print")){
    		
    		this.doPrint();
    		
    	}
    	
    	// Tiro el evento para arriba en la gerarquia de objetos
    	super.actionPerformed(e);
    	
    }
    
    
    public void doDelete() {}
    
    /**
     * Invoca el servlet que imprime el reporte
     */
    public void doPrint() {        
    
    }
    
    
        
}
