package app.abms.contrato;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import app.beans.Cuota;

import ccecho2.base.CCButton;
import ccecho2.base.CCTextField;

import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.ApplicationInstance;
import nextapp.echo2.app.Component;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.ImageReference;
import nextapp.echo2.app.Label;
import nextapp.echo2.app.ResourceImageReference;
import nextapp.echo2.app.Table;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.TableModelEvent;
import nextapp.echo2.app.event.TableModelListener;
import nextapp.echo2.app.layout.ColumnLayoutData;
import nextapp.echo2.app.list.ListSelectionModel;

import nextapp.echo2.app.table.TableCellRenderer;

import echopointng.table.AbleTableSelectionModel;
import echopointng.table.DefaultPageableSortableTableModel;

import framework.ui.generales.FWWindowPaneMensajes;
import framework.ui.generales.abms.ABMListadoView;
import framework.ui.principal.FWContentPanePrincipal;


@SuppressWarnings("serial")
public class ContratoAddCuotasListadoView extends ABMListadoView implements TableModelListener {
	
	private List<Cuota> dataList;
	
	private short cant_cuotas;
	private double monto_total;
	private Date fecha_desde;
	
	// Imagen del botón Ok
    private ImageReference OK = new ResourceImageReference("/resources/crystalsvg22x22/actions/button_ok.png");
    private CCButton btnOK;
    
    // Imagen Edicion
    private ImageReference iEdit = new ResourceImageReference("/resources/crystalsvg22x22/actions/edit.png");
    private CCButton btnEditItem;
    
    private ContratoAddView refClase;
    
    // Dejo accesible el atributo desde el paquete
    ContratoCuotaModel oModel = new ContratoCuotaModel();
    private FWContentPanePrincipal CPPrincipal;
	
    public ContratoAddCuotasListadoView (short cant_cuotas, double monto_total, Date fecha_desde, List<Cuota> dataListAUX, ContratoAddView refClase) {
    	
        super(null, true);
        
        //System.out.println("app.abms.localidad.LocalidadListadoView CONSTRUCTOR");
        
        this.setTitle("ABM de Contratos - Edición de cuotas");
        
        this.setHeight(new Extent(450, Extent.PX));
        
        CPPrincipal = ((FWContentPanePrincipal) ApplicationInstance.getActive().getDefaultWindow().getContent());

        //this.getTable().addActionListener(this);
        this.cant_cuotas = cant_cuotas;
        this.monto_total = monto_total;
        this.fecha_desde = fecha_desde;
        this.refClase = refClase;
        
        this.dataList = dataListAUX;
        
        this.sacarBotonesNavegacion();
        this.sacarBotonesABM();
        
        // Agrego el boton de confirmación
        this.btnOK = new CCButton(this.OK);
        this.btnOK.setActionCommand("ok");
        this.btnOK.setToolTipText("Confirmar la Operación");
        this.btnOK.addActionListener(this);
        
        this.rBotones.add(this.btnOK);
        
        // Agrego el boton para modificar
        this.btnEditItem = new CCButton(this.iEdit);
        this.btnEditItem.setActionCommand("edit");
        this.btnEditItem.setToolTipText("Editar la cuota");
        this.btnEditItem.addActionListener(this);
        
        this.rBotones.add(this.btnEditItem);
        
        
        // La tabla va a tener selección multiple !!! entonces la modifico
        this.ArmarTabla();
        
        ActualizarDatos();
                
        /** Dibujo los datos */
        //this.getBrowser().update();
    }
    
    private void ArmarTabla() {
    	
    	ColumnLayoutData cTableLD = new ColumnLayoutData();
    	cTableLD.setAlignment(new Alignment(Alignment.LEFT,Alignment.BOTTOM));
    	this.oTable.setLayoutData(cTableLD);
    	
    	final DefaultPageableSortableTableModel pageableSortableTableModel = new DefaultPageableSortableTableModel(this.oModel);
    	final AbleTableSelectionModel ableTableSelectionModel = new AbleTableSelectionModel(pageableSortableTableModel);
		ableTableSelectionModel.setSelectionMode(ListSelectionModel.MULTIPLE_SELECTION);
		this.oTable.setSelectionModel(ableTableSelectionModel);
    	
    }
    
    
    @SuppressWarnings("deprecation")
	public void ActualizarDatos() {
    	
        /** Cargo los datos en la grilla */
        //this.oModel = new ContratoCuotaModel();
        
        //this.oTable.setDefaultRenderer(Object.class, tableCellRenderer);
        //this.oModel.addTableModelListener(this);
        
/* TENGO QUE ARMAR LA LISTA DE OBJETO EN FORMA DINAMICA 
 * necesito: 	monto total del contrato
 * 				cantidad de cuotas del mismo
 * 
 * Evaluo si ya no me pasan la lista completa
 */
        if (this.dataList.isEmpty()) {
        
	        double monto_cuota = monto_total / cant_cuotas;
	        
	//      Calculo la fecha de vencimiento - arranco con la fecha_desde
			//Date fecha_vencimiento = this.fecha_desde;
			Calendar oFecha = Calendar.getInstance();
			
			/** Tengo que evaluar si el día de vencimiento no es menor al día del mes en la fecha desde */
			oFecha.setTime(this.fecha_desde);
			boolean diaModificado = false;
			int dia_fecha_desde = oFecha.get(Calendar.DAY_OF_MONTH);
			if (dia_fecha_desde > Cuota.dia_de_vencimiento) {
				
				// Es el mismo día del fecha desde
				diaModificado = true;
			} else {
				// Es el día prefijado
				oFecha.set(Calendar.DAY_OF_MONTH, Cuota.dia_de_vencimiento);			
			}
			
	
			// Calculo el periodo
			short periodo_mes = 0;
			short periodo_anio = 0;
	
			
			for (short i = 1; i <= cant_cuotas; i++) {
				
				periodo_mes = (short) (oFecha.get(Calendar.MONTH) + 1);
				periodo_anio = (short) oFecha.get(Calendar.YEAR);
				
				// Calculo la fecha de vencimiento, le sumo un mes
				// Evaluo si no preserve el día de la fecha desde
				if (diaModificado && i == 1){
					oFecha.set(Calendar.DAY_OF_MONTH, dia_fecha_desde);
				} else {
					oFecha.set(Calendar.DAY_OF_MONTH, Cuota.dia_de_vencimiento);
				}
				
				Cuota oCuota = new Cuota(i, monto_cuota, periodo_mes, periodo_anio, oFecha.getTime());
				
				dataList.add(oCuota);
				
				oFecha.add(Calendar.MONTH, 1);				
				
			}
        } // Fin if (this.dataList.size() == 0)
        //System.out.println("Datos: " + dataList.size());
        oModel.setDataList(dataList);
        this.update(oModel, 0, 0);
        
    }
    
    
    /* Si quiero usar sobrecarga del método */
    @SuppressWarnings("unchecked")
	public void actionPerformed(ActionEvent e) {
    	
    	if (e.getActionCommand().equals("ok")){            
        	
    		/** Devuelvo los datos -------------------------------------------- */
    		
    		// tengo que levantar todos los datos de la tabla y pasarlos a la pantalla que nos llamo
    		
        	// Actualizo todos los datos antes de procesarlos
        	// this.oModel.fireTableDataChanged();
    		
    		List<Cuota> lDatos = this.oModel.getDataList();
    		
    		this.refClase.procesarCuotas(lDatos);
    		
    		this.exit();
    	
    	} else if (e.getActionCommand().equals("edit")){            
            	
        	/** Edición de datos -------------------------------------------- */
    		
    		AbleTableSelectionModel ableTableSelectionModel = (AbleTableSelectionModel) this.oTable.getSelectionModel();
    		int selectedIndices[] = ableTableSelectionModel.getSelectedIndices();
    		
    		//System.out.println("Selecciono " + selectedIndices.length + " - " + ableTableSelectionModel.getMinSelectedIndex());
    		
    		if (selectedIndices.length == 0) { 
    			
    			CPPrincipal.abrirVentana(new FWWindowPaneMensajes("Error Edición Items","Debe seleccionar una fila para continuar.",(short)1));
    		
    		} else {
    			
    			// Paso la primera para editar
    			int currentRow = this.oTable.getSelectionModel().getMinSelectedIndex();
    			Cuota oCuota = (Cuota)oModel.getDataList().get(currentRow);
    			
    			CPPrincipal.abrirVentanaMensaje(new CuotaEditView(this, oCuota, currentRow));
    		
    		}

			
    	} else if (e.getActionCommand().equals("exit")){
            
    		/** Salir ------------------------------------------------ */
            this.exit();
            
            // Ver cuando LIMPIO NO siempre !!!
            // this.refClase.doLimpiar();
            
        }
    	
    }
    
    // Dibuja los componentes de la tabla
    @SuppressWarnings("unused")
	private final TableCellRenderer tableCellRenderer = new TableCellRenderer() {

		private static final long serialVersionUID = 1L;

		public Component getTableCellRendererComponent(Table table, Object value, int column, int row) {
	        if (value instanceof CCTextField) {
	        	//System.out.println("pasa por acaaaaaaaaaaaaaaaa");
	            return ((CCTextField) value);
	        }
	            
	        else return new Label(value.toString());
	    }
	};
	
	public void tableChanged(TableModelEvent e) {
		//System.out.println("HABERR ");
		
		@SuppressWarnings("unused")
		int row = e.getFirstRow(); // 0 ¿?
        int column = e.getColumn(); // -2 ¿?
        this.oModel = (ContratoCuotaModel)e.getSource();
        @SuppressWarnings("unused")
		String columnName = this.oModel.getColumnName(column);
        //Object data = this.oModel.getValueAt(row, column);

        //System.out.println("HABERR " + row + " - " + column + " - " + columnName);
        
        // Do something with the data...
    }
	
	@SuppressWarnings("unchecked")
	protected void actualizarCuotas(double total_cuota) {
		
		// Tengo que actualizar todas las cuotas seleccionadas
		AbleTableSelectionModel ableTableSelectionModel = (AbleTableSelectionModel) this.oTable.getSelectionModel();
		int selectedIndices[] = ableTableSelectionModel.getSelectedIndices();
		
		for (int i = 0; i < selectedIndices.length; i++) {
			int row = selectedIndices[i];
			
			Cuota oCuotaAux = (Cuota)oModel.getDataList().get(row);
			oCuotaAux.setValor(total_cuota);
			this.oModel.getDataList().set(row, oCuotaAux);
			
		}
		
		this.update(oModel, 0, 0);
		
	}
	
}