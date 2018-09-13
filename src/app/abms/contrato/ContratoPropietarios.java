package app.abms.contrato;

//import java.text.DecimalFormat;
import java.util.List;
import java.util.Vector;

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

import datos.contrato_actor.ContratoActor;
import datos.contrato_novedad_pago.ContratoNovedadPago;
import datos.contrato_novedad_pago.ContratoNovedadPagoProcesos;

import framework.ui.generales.FWWindowPaneMensajes;
import framework.ui.generales.abms.ABMListadoView;
import framework.ui.principal.FWContentPanePrincipal;



@SuppressWarnings("serial")
public class ContratoPropietarios extends ABMListadoView {
	
	private List<ContratoActor> dataList;
	private Contrato oContrato;
	ContratoPropietariosModel oModel = new ContratoPropietariosModel();
	
    public ContratoPropietarios (Contrato oContrato) {
        
    	super(null, false);
    	
    	// Seteo el título de la ventana
		this.setTitle("Contrato - Listado de PROPIETARIOS - IV");
		
		this.setHeight(new Extent(300, Extent.PX));
		this.setWidth(new Extent(400, Extent.PX));
		
		// Es una pantalla de visualización
		// Probar posicionarla en forma especial
		// this.setModal(false); // no me deja seleccionarla, como que el foco 
		this.oContrato = oContrato;
		
		// Limpio los botones que no uso
		this.rBotones.removeAll();
		
        //cPrincipal.add(tMonto);
		
		ActualizarDatos();
		
    }
        
	
    public void ActualizarDatos() {
    	
        /** 
         * Cargo los datos en la grilla 
         */
        dataList = new Vector<ContratoActor>();
        for (ContratoActor obj: this.oContrato.getContratoActors()) {
        	if (obj.getId().getIdActorTipo() == ContratoActor.ActorTipoPropietario) {
        		dataList.add(obj);
        	}
        }
        
        //System.out.println("DATOS de novedades de PAGO: " + dataListPago.size());
        
        oModel.setDataList(dataList);
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
               
}