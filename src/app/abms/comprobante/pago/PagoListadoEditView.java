package app.abms.comprobante.pago;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import java.util.Vector;

import app.beans.ItemTipo;
import app.beans.Utiles;

import ccecho2.base.CCButton;
import ccecho2.base.CCColumn;
import ccecho2.base.CCLabel;
import ccecho2.base.CCTable;

import ccecho2.base.CCRow;
import ccecho2.base.CCTextArea;
import ccecho2.base.CCTextField;
//import ccecho2.complex.MessageWindowPane;

import echopointng.Separator;
import framework.ui.generales.abms.ABMListadoEditViewExit;
import framework.ui.principal.FWContentPanePrincipal;

import datos.recibo_pago.ReciboPago;
import datos.recibo_pago.ReciboPagoProcesos;
import datos.recibo_pago_item.ReciboPagoItem;

import nextapp.echo2.app.Alignment;

import nextapp.echo2.app.ApplicationInstance;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Command;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Font;
import nextapp.echo2.app.ImageReference;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.ResourceImageReference;

import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.layout.ColumnLayoutData;
import nextapp.echo2.app.layout.RowLayoutData;
import nextapp.echo2.app.list.DefaultListSelectionModel;
import nextapp.echo2.webcontainer.command.BrowserOpenWindowCommand;

@SuppressWarnings("serial")
public class PagoListadoEditView extends ABMListadoEditViewExit {
    
    // Imagen del botón Impresión
    private ImageReference iPrint = new ResourceImageReference("/resources/crystalsvg22x22/actions/fileprint.png");
    private CCButton btnPrint;
	
	private PagoListadoView listado;
	private ReciboPago oRecibo;
	
	private FWContentPanePrincipal CPPrincipal;

	public PagoListadoEditView(PagoListadoView listado, ReciboPago oRecibo) {
		
		super();
		
		// Es la clase que me llamo
		this.listado = listado;
		
		this.oRecibo = oRecibo;
		
		// Seteo el título de la ventana
		this.setTitle("Visualización de Recibos de Liquidación Pago");
		this.setHeight(new Extent(550, Extent.PX));
		this.setWidth(new Extent(700, Extent.PX));
		
		CPPrincipal = ((FWContentPanePrincipal) ApplicationInstance.getActive().getDefaultWindow().getContent());
		
		// Agrego los componentes de la pantalla
		crearObjetos();
		renderObjetos();
		
		int componentCount = 2;
		for (int i = 0; i < componentCount; ++i) {

			RowLayoutData rowLayoutData = new RowLayoutData();
			rowLayoutData.setWidth(new Extent(100 / componentCount, Extent.PERCENT));
			 
			//rItems.getComponent(i).setLayoutData(rowLayoutData);
			 
			rTotalRecibo.getComponent(i).setLayoutData(rowLayoutData);
			rTotalIVA.getComponent(i).setLayoutData(rowLayoutData);
			rTotalComision.getComponent(i).setLayoutData(rowLayoutData);
			rTotalGANANCIAS.getComponent(i).setLayoutData(rowLayoutData);
		}
		
		asignarDatos();
		
		
	}
	
	
	private CCColumn _cPrincipal, cLabels, cTexts;
	private CCRow rPrincipal, rMensaje, rItems, rTotalRecibo, rTotalIVA, rTotalComision, rTotalGANANCIAS;
	
	private CCLabel lMensaje;
	private CCLabel lPropietarioNombre, lInquilinoDomicilio, lPropietarioIVA, lNumero, lFecha, lLeyenda, lTotal_recibo, lTotalIVA, lTotalComisiones, lTotalGANANCIAS;
	
	private CCTextField tPropietarioNombre, tInquilinoDomicilio, tPropietarioIVA, tNumero, tFecha, tTotal_recibo, tTotalIVA, tTotalComisiones, tTotalGANANCIAS;
	private CCTextArea taLeyenda;
	
	private List<ReciboPagoItem> dataList = new Vector<ReciboPagoItem>();
    PagoItemEditModel oModel = new PagoItemEditModel();
    
    // Tabla de objetos items
    public CCTable oTable;
    
	
    private void crearObjetos() {
    	
    	//System.out.println("CobroListadoEditView.crearObjetos");
    	
    	_cPrincipal = new CCColumn();
        _cPrincipal.setCellSpacing(new Extent(20));
        _cPrincipal.setInsets(new Insets(10));
        
        rPrincipal = new CCRow();     
        rItems = new CCRow();
        rTotalRecibo = new CCRow(22);
        rTotalComision = new CCRow(22);
        rTotalIVA = new CCRow(22);
        rTotalGANANCIAS = new CCRow(22);
                
        cLabels = new CCColumn();
        cLabels.setCellSpacing(new Extent(10, Extent.PX));
        cLabels.setInsets(new Insets(10));
        
        cTexts = new CCColumn();
        cTexts.setCellSpacing(new Extent(10, Extent.PX));
        cTexts.setInsets(new Insets(10));

        RowLayoutData cRowLD = new RowLayoutData();
        cRowLD.setAlignment(new Alignment(Alignment.RIGHT,Alignment.CENTER));
        
        rTotalRecibo.setCellSpacing(new Extent(10, Extent.PX));
        rTotalRecibo.setInsets(new Insets(200,0,0,0));        
        rTotalRecibo.setLayoutData(cRowLD);
        
        rTotalComision.setCellSpacing(new Extent(1, Extent.PX));
        rTotalComision.setInsets(new Insets(200,10,0,0));
        rTotalComision.setLayoutData(cRowLD);
        
        rTotalIVA.setCellSpacing(new Extent(10, Extent.PX));
        rTotalIVA.setInsets(new Insets(200,0,0,0));
        rTotalIVA.setLayoutData(cRowLD);
        
        rTotalGANANCIAS.setCellSpacing(new Extent(10, Extent.PX));
        rTotalGANANCIAS.setInsets(new Insets(200,0,0,0));
        rTotalGANANCIAS.setLayoutData(cRowLD);

         
        /* Boton impresión */
        btnPrint = new CCButton(iPrint);
        btnPrint.setActionCommand("imprimir");
        btnPrint.setToolTipText("Imprimir comprobante");
        btnPrint.addActionListener(this);
        
        /*******************************************************************/       
        lPropietarioNombre = new CCLabel("Propietario:",22);
        tPropietarioNombre = new CCTextField(400,22,20,false);
        tPropietarioNombre.setEnabled(false);
        
        lInquilinoDomicilio = new CCLabel("Domicilio:",22);
        tInquilinoDomicilio = new CCTextField(400,22,20,false);
        tInquilinoDomicilio.setEnabled(false);

        lPropietarioIVA = new CCLabel("IVA:",22);
        tPropietarioIVA = new CCTextField(400,22,20,false);
        tPropietarioIVA.setEnabled(false);
        
        lNumero = new CCLabel("Número:",22);
        tNumero = new CCTextField(200,22,20,false);
        tNumero.setEnabled(false);
        
        lFecha = new CCLabel("Fecha emisión:",22);
        tFecha = new CCTextField(200,22,20,false);
        tFecha.setEnabled(false);
        
        lLeyenda = new CCLabel("Leyenda:",44);
        taLeyenda = new CCTextArea(400,44,2,true);
        taLeyenda.setEnabled(false);
                
        /* Tabla con el resultado de la búsqueda */
    	this.oTable = new CCTable();
    	this.oTable.setHeaderBackground(Color.LIGHTGRAY);
    	this.oTable.setFont(new Font(Font.ARIAL, Font.PLAIN, new Extent(11)));
    	this.oTable.setWidth(new Extent(580));
        
    	ColumnLayoutData cTableLD = new ColumnLayoutData();
    	cTableLD.setAlignment(new Alignment(Alignment.LEFT,Alignment.BOTTOM));
    	this.oTable.setLayoutData(cTableLD);
        
    	        
    	lTotal_recibo = new CCLabel("TOTAL Recibo:");
        tTotal_recibo = new CCTextField();
        tTotal_recibo.setEnabled(false);
        tTotal_recibo.setFont(new Font(Font.ARIAL, Font.BOLD, new Extent(12, Extent.PX)));
        
        lTotalComisiones = new CCLabel("TOTAL Comisiones:");
        tTotalComisiones = new CCTextField();
        tTotalComisiones.setEnabled(false);
        tTotalComisiones.setFont(new Font(Font.ARIAL, Font.PLAIN, new Extent(12, Extent.PX)));
        
        lTotalIVA = new CCLabel("TOTAL IVA:");
        tTotalIVA = new CCTextField();
        tTotalIVA.setEnabled(false);
        tTotalIVA.setFont(new Font(Font.ARIAL, Font.PLAIN, new Extent(12, Extent.PX)));

        lTotalGANANCIAS = new CCLabel("TOTAL RETENCION GANANCIAS:");
        tTotalGANANCIAS = new CCTextField();
        tTotalGANANCIAS.setEnabled(false);
        tTotalGANANCIAS.setFont(new Font(Font.ARIAL, Font.PLAIN, new Extent(12, Extent.PX)));

        
        /*******************************************************************/
        rMensaje = new CCRow();
        rMensaje.setAlignment(Alignment.ALIGN_CENTER);

        lMensaje = new CCLabel(" ");
        lMensaje.setForeground(Color.RED);


    }
    
    private void renderObjetos() {
        
    	//System.out.println("CobroListadoEditView.renderObjetos");
    	
//    	 Agrego la columna al ContentPane Principal
    	cpPrincipal.add(_cPrincipal);

        _cPrincipal.add(rPrincipal);
        rPrincipal.add(cLabels);
        rPrincipal.add(cTexts);
        
        CCColumn c1 = new CCColumn();
        CCColumn c2 = new CCColumn(); 
        
        c1.add(this.oTable);
        //c1.add(this.table);
        
        rItems.add(c2);
        rItems.add(c1);
        
        cLabels.add(lNumero);
        cLabels.add(lFecha);
        cLabels.add(lPropietarioNombre);
        cLabels.add(lInquilinoDomicilio);
        cLabels.add(lPropietarioIVA);
        cLabels.add(lLeyenda);
        
        /* ---------------------------------------------- */
        
        cTexts.add(tNumero);
        cTexts.add(tFecha);
        cTexts.add(tPropietarioNombre);
        cTexts.add(tInquilinoDomicilio);
        cTexts.add(tPropietarioIVA);
        cTexts.add(taLeyenda);
        
        rTotalRecibo.add(lTotal_recibo);
        rTotalRecibo.add(tTotal_recibo);
        
        rTotalComision.add(lTotalComisiones);
        rTotalComision.add(tTotalComisiones);
        
        rTotalIVA.add(lTotalIVA);
        rTotalIVA.add(tTotalIVA);

        rTotalGANANCIAS.add(lTotalGANANCIAS);
        rTotalGANANCIAS.add(tTotalGANANCIAS);

        
        /* ---------------------------------------------- */
        // La tabla la voy a colocar en una FILA para poder agregar los botones
        cpPrincipal.add(rItems);
        cpPrincipal.add(rTotalComision);
        cpPrincipal.add(rTotalIVA);
        cpPrincipal.add(rTotalGANANCIAS);
        cpPrincipal.add(rTotalRecibo);
        
        rMensaje.add(lMensaje);
        
        /* ---------------------------------------------- */
        this.rBotones.remove(1);
        this.rBotones.add(new Separator());
        this.rBotones.add(btnPrint);
        /* ---------------------------------------------- */
        
        ApplicationInstance.getActive().setFocusedComponent(tNumero);
           
    }
    
    
    @SuppressWarnings("unchecked")
	private void asignarDatos() {
    
    	System.out.println("PagoListadoEditView.asignarDatos");
    	
    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    	//DecimalFormat moneda = new DecimalFormat("###,##0.00");
    	
//    	Set<ReciboPagoItem> cItems = this.oRecibo.getReciboPagoItems();
//    	for (ReciboPagoItem oItem: cItems) {
//    		oItem.getIdItemTipo();
//    	}
    	
    	//tInmueble.setText("");
    	
		try {
			this.oRecibo = ReciboPagoProcesos.completarConSaldo(this.oRecibo);
		} catch (Exception e1) {
			e1.printStackTrace();
			// reviento !!!!!!!!!!!!
			
			// lleno el mensaje de error ¿?
			
		}
    	
    	
    	tNumero.setText(Utiles.complete(this.oRecibo.getNumero().toString(), 8));
    	tFecha.setText(sdf.format(this.oRecibo.getFechaEmision()));
    	tPropietarioNombre.setText(this.oRecibo.getPersona().getDescripcion());
    	
    	// Si no tiene asignada la dirección la PERSONA da error - 13/04/2013
    	try {
    		tInquilinoDomicilio.setText(this.oRecibo.getPersona().getDireccion() + " - " + this.oRecibo.getPersona().getLocalidad().getDescripcion());
    	} catch (NullPointerException npe) {}
    	
    	tPropietarioIVA.setText(this.oRecibo.getPersona().getResponsabilidadIVA());
    	taLeyenda.setText(this.oRecibo.getLeyenda());
    	
        /** Cargo los datos en la grilla - Inicialmente esta vacia */
        dataList = new Vector(this.oRecibo.getReciboPagoItems()); 
        oModel.setDataList(dataList);
        this.update(oModel);

    	
    }
    
    /**
     * Este es el método que actualiza los datos de la pantalla que esta abajo ¿?
     * @param oModel
     */
    @SuppressWarnings("unchecked")
	public void update(PagoItemEditModel oModel) {
            	
		// Recorro la tabla para sacar el total de la
    	DecimalFormat moneda = new DecimalFormat("$###,##0.00");
    	List<ReciboPagoItem> lDatos = oModel.getDataList();
    	float total = 0;    	
    	float total_iva = 0, total_comisiones = 0, total_ganancias = 0;
    	for(ReciboPagoItem obj: lDatos) {
    		total += obj.getMonto();
    		
    		if (obj.getIdItemTipo() == ItemTipo.ItemIVA) {
    			total_iva += obj.getMonto();
    		}
    		
    		if (obj.getIdItemTipo() == ItemTipo.ItemComisionAlquiler) {
    			total_comisiones += obj.getMonto();
    		}
    		
    		if (obj.getIdItemTipo() == ItemTipo.ItemRetencionGanancia) {
    			total_ganancias += obj.getMonto();
    		}
    	}
    	
    	tTotal_recibo.setText(moneda.format(total));
    	tTotalComisiones.setText(moneda.format(total_comisiones));
    	tTotalIVA.setText(moneda.format(total_iva));
    	tTotalGANANCIAS.setText(moneda.format(total_ganancias));
    	
        // Refresco la lista
        this.oTable.setModel(oModel);
        this.oTable.setSelectionModel(new DefaultListSelectionModel());
        
    }
 
    
    public void actionPerformed(ActionEvent ae) {
    	
    	if (ae.getActionCommand().equals("ok")) {
    	
	    } else if (ae.getActionCommand().equals("cancel")){
			
			//update(this.oModel);
	
			
		} else if (ae.getActionCommand().equals("imprimir")){
			
			// Aca lanzo la impresión
			doPrint();
			
		}

    	// Tiro el evento para arriba en la gerarquia de objetos
    	super.actionPerformed(ae);
    	
    }
    
    
    /**
     * Invoca el servlet que imprime el reporte
     */
    public void doPrint() {
    	
        String sUri = "PDFPreview?reporte=comprobanteReciboPago&id_recibo_pago=" + this.oRecibo.getIdReciboPago();

        StringBuilder sb = new StringBuilder()
                .append("width=640")
                .append(",height=480")
                .append(",resizable=yes")
                .append(",scrollbars=yes");

        Command oComm = new BrowserOpenWindowCommand(sUri, "Prueba", sb.toString());
        ApplicationInstance.getActive().enqueueCommand(oComm);
    }
    

}