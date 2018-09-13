package app.abms.comprobante.cobro;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import java.util.Vector;

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

import datos.recibo_cobro.ReciboCobro;
import datos.recibo_cobro.ReciboCobroProcesos;
import datos.recibo_cobro_item.ReciboCobroItem;

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
public class CobroListadoEditView extends ABMListadoEditViewExit {
    
    // Imagen del botón Impresión
    private ImageReference iPrint = new ResourceImageReference("/resources/crystalsvg22x22/actions/fileprint.png");
    private CCButton btnPrint;
	
	private CobroListadoView listado;
	private ReciboCobro oRecibo;
	
	private FWContentPanePrincipal CPPrincipal;

	public CobroListadoEditView(CobroListadoView listado, ReciboCobro oRecibo) {
		
		super();
		
		// Es la clase que me llamo
		this.listado = listado;
		
		this.oRecibo = oRecibo;
		
		// Seteo el título de la ventana
		this.setTitle("Visualización de Recibos de Liquidación Cobranza");
		this.setHeight(new Extent(500, Extent.PX));
		this.setWidth(new Extent(700, Extent.PX));
		
		CPPrincipal = ((FWContentPanePrincipal) ApplicationInstance.getActive().getDefaultWindow().getContent());
		
		// Agrego los componentes de la pantalla
		crearObjetos();
		renderObjetos();
		
		asignarDatos();
		
		
	}
	
	
	private CCColumn _cPrincipal, cLabels, cTexts;
	private CCRow rPrincipal, rMensaje, rItems, rTotales;
	
	private CCLabel lMensaje;
	private CCLabel lInquilinoNombre, lInquilinoDomicilio, lInquilinoIVA, lNumero, lFecha, lLeyenda, lTotal_recibo;
	
	private CCTextField tInquilinoNombre, tInquilinoDomicilio, tInquilinoIVA, tNumero, tFecha, tTotal_recibo;
	private CCTextArea taLeyenda;
	
	private List<ReciboCobroItem> dataList = new Vector<ReciboCobroItem>();
    CobroItemEditModel oModel = new CobroItemEditModel();
    
    // Tabla de objetos items
    public CCTable oTable;
    
	
    private void crearObjetos() {
    	
    	//System.out.println("CobroListadoEditView.crearObjetos");
    	
    	_cPrincipal = new CCColumn();
        _cPrincipal.setCellSpacing(new Extent(20));
        _cPrincipal.setInsets(new Insets(10));
        
        rPrincipal = new CCRow();        
        rItems = new CCRow();
        rTotales = new CCRow(22);
                
        cLabels = new CCColumn();
        cLabels.setCellSpacing(new Extent(10, Extent.PX));
        cLabels.setInsets(new Insets(10));
        
        cTexts = new CCColumn();
        cTexts.setCellSpacing(new Extent(10, Extent.PX));
        cTexts.setInsets(new Insets(10));

        rTotales.setCellSpacing(new Extent(10, Extent.PX));
        rTotales.setInsets(new Insets(300,20));
        RowLayoutData cRowLD = new RowLayoutData();
        cRowLD.setAlignment(new Alignment(Alignment.RIGHT,Alignment.CENTER));
        rTotales.setLayoutData(cRowLD);
         
        /* Boton impresión */
        btnPrint = new CCButton(iPrint);
        btnPrint.setActionCommand("imprimir");
        btnPrint.setToolTipText("Imprimir comprobante");
        btnPrint.addActionListener(this);
        
        /*******************************************************************/       
        lInquilinoNombre = new CCLabel("Inquilino:",22);
        tInquilinoNombre = new CCTextField(400,22,20,false);
        tInquilinoNombre.setEnabled(false);
        
        lInquilinoDomicilio = new CCLabel("Domicilio:",22);
        tInquilinoDomicilio = new CCTextField(400,22,20,false);
        tInquilinoDomicilio.setEnabled(false);

        lInquilinoIVA = new CCLabel("IVA:",22);
        tInquilinoIVA = new CCTextField(400,22,20,false);
        tInquilinoIVA.setEnabled(false);
        
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
        
    	        
        lTotal_recibo = new CCLabel("TOTAL:");
        tTotal_recibo = new CCTextField();
        tTotal_recibo.setEnabled(false);
        
        /*******************************************************************/
        rMensaje = new CCRow();
        rMensaje.setAlignment(Alignment.ALIGN_CENTER);

        lMensaje = new CCLabel();
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
        cLabels.add(lInquilinoNombre);
        cLabels.add(lInquilinoDomicilio);
        cLabels.add(lInquilinoIVA);
        cLabels.add(lLeyenda);
        
        /* ---------------------------------------------- */
        
        cTexts.add(tNumero);
        cTexts.add(tFecha);
        cTexts.add(tInquilinoNombre);
        cTexts.add(tInquilinoDomicilio);
        cTexts.add(tInquilinoIVA);
        cTexts.add(taLeyenda);
        
        rTotales.add(lTotal_recibo);
        rTotales.add(tTotal_recibo);
        
        /* ---------------------------------------------- */
        // La tabla la voy a colocar en una FILA para poder agregar los botones
        cpPrincipal.add(rItems);
        cpPrincipal.add(rTotales);
        
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
    
    	System.out.println("CobroListadoEditView.asignarDatos");
    	
    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    	//DecimalFormat moneda = new DecimalFormat("###,##0.00");
    	
//    	Set<ReciboCobroItem> cItems = this.oRecibo.getReciboCobroItems();
//    	for (ReciboCobroItem oItem: cItems) {
//    		oItem.getIdItemTipo();
//    	}
    	
    	//tInmueble.setText("");
    	
		try {
			this.oRecibo = ReciboCobroProcesos.completarConSaldo(this.oRecibo);
		} catch (Exception e1) {
			e1.printStackTrace();
			// reviento !!!!!!!!!!!!
			
			// lleno el mensaje de error ¿?
			
		}
    	
    	
    	tNumero.setText(Utiles.complete(this.oRecibo.getNumero().toString(), 8));
    	tFecha.setText(sdf.format(this.oRecibo.getFechaEmision()));
    	tInquilinoNombre.setText(this.oRecibo.getPersona().getDescripcion());
    	tInquilinoDomicilio.setText(this.oRecibo.getPersona().getDireccion() + " - " + this.oRecibo.getPersona().getLocalidad().getDescripcion());
    	tInquilinoIVA.setText(this.oRecibo.getPersona().getResponsabilidadIVA() + "  " + this.oRecibo.getPersona().getCuit());
    	taLeyenda.setText(this.oRecibo.getLeyenda());
    	
        /** Cargo los datos en la grilla - Inicialmente esta vacia */
        dataList = new Vector(this.oRecibo.getReciboCobroItems()); 
        oModel.setDataList(dataList);
        this.update(oModel);

    	
    }
    
    /**
     * Este es el método que actualiza los datos de la pantalla que esta abajo ¿?
     * @param oModel
     */
    @SuppressWarnings("unchecked")
	public void update(CobroItemEditModel oModel) {
            	
		// Recorro la tabla para sacar el total de la
    	DecimalFormat moneda = new DecimalFormat("$###,##0.00");
    	List<ReciboCobroItem> lDatos = oModel.getDataList();
    	float total = 0;
    	for(ReciboCobroItem obj: lDatos) {
    		total += obj.getMonto();
    	}
    	tTotal_recibo.setText(moneda.format(total));
    	
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
    	
        String sUri = "PDFPreview?reporte=comprobanteReciboCobro&id_recibo_cobro=" + this.oRecibo.getIdReciboCobro();

        StringBuilder sb = new StringBuilder()
                .append("width=640")
                .append(",height=480")
                .append(",resizable=yes")
                .append(",scrollbars=yes");

        Command oComm = new BrowserOpenWindowCommand(sUri, "Prueba", sb.toString());
        ApplicationInstance.getActive().enqueueCommand(oComm);
    }
    

}