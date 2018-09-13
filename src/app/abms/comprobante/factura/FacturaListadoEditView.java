package app.abms.comprobante.factura;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import java.util.Vector;

import app.beans.ItemTipo;
import app.beans.Utiles;
import app.combos.ComboInscripcionIVA;

import ccecho2.base.CCButton;
import ccecho2.base.CCCheckBox;
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

import datos.factura.Factura;
import datos.factura.FacturaDAO;
import datos.factura.FacturaProcesos;
import datos.factura_item.FacturaItem;

import datos.recibo_cobro_item.ReciboCobroItem;
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

/**
 * Edita los datos de una factura
 * Permite:
 * 1 - Imprimir nuevamente la factura
 * 2 - Modificar el estado de la factura ... pasarla a ANULADA 
 * 3 - Modificar el número de factura
 * 
 * @author pablo
 *
 */


@SuppressWarnings("serial")
public class FacturaListadoEditView extends ABMListadoEditViewExit {
    
    // Imagen del botón Impresión
    private ImageReference iPrint = new ResourceImageReference("/resources/crystalsvg22x22/actions/fileprint.png");
    private CCButton btnPrint;
	
	private FacturaListadoView listado;
	private Factura oFactura;
	
	private FWContentPanePrincipal CPPrincipal;

	public FacturaListadoEditView(FacturaListadoView listado, Factura oFactura) {
		
		super();
		
		// Es la clase que me llamo
		this.listado = listado;
		
		this.oFactura = oFactura;
		
		// Seteo el título de la ventana
		this.setTitle("Visualización de Facturas");
		this.setHeight(new Extent(500, Extent.PX));
		this.setWidth(new Extent(700, Extent.PX));
		
		CPPrincipal = ((FWContentPanePrincipal) ApplicationInstance.getActive().getDefaultWindow().getContent());
		
		// Agrego los componentes de la pantalla
		crearObjetos();
		renderObjetos();
		
		int componentCount = 2;
		for (int i = 0; i < componentCount; ++i) {
			 RowLayoutData rowLayoutData = new RowLayoutData();
			 rowLayoutData.setWidth(new Extent(100 / componentCount, Extent.PERCENT));
			 rTotalRecibo.getComponent(i).setLayoutData(rowLayoutData);
			 rTotalIVA.getComponent(i).setLayoutData(rowLayoutData);
			 rTotalComision.getComponent(i).setLayoutData(rowLayoutData);
		}
		
		asignarDatos();
		
		
	}
	
	
	private CCColumn _cPrincipal, cLabels, cTexts;
	private CCRow rPrincipal, rMensaje, rItems, rTotalRecibo, rTotalIVA, rTotalComision, rNumero;
	
	private CCLabel lMensaje;
	private CCLabel lPersonaNombre, lPersonaDomicilio, lPersonaIVA, lNumero, lFecha, lLeyenda, lTotal_recibo, lTotalIVA, lTotalComisiones, lFacturaTipo;
	
	private CCTextField tPersonaNombre, tPersonaDomicilio, tPersonaIVA, tNumero, tFecha, tTotal_recibo, tTotalIVA, tTotalComisiones;
	private CCTextArea taLeyenda;
	
	private CCCheckBox cbAnulada;
	
	private List<FacturaItem> dataList = new Vector<FacturaItem>();
    FacturaItemEditModel oModel = new FacturaItemEditModel();
    
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
        rNumero = new CCRow(22);
                
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
         
        /* Boton impresión */
        btnPrint = new CCButton(iPrint);
        btnPrint.setActionCommand("imprimir");
        btnPrint.setToolTipText("Imprimir comprobante");
        btnPrint.addActionListener(this);
        
        /*******************************************************************/       
        lPersonaNombre = new CCLabel("Persona:",22);
        tPersonaNombre = new CCTextField(400,22,20,false);
        tPersonaNombre.setEnabled(false);
        
        lPersonaDomicilio = new CCLabel("Domicilio:",22);
        tPersonaDomicilio = new CCTextField(400,22,20,false);
        tPersonaDomicilio.setEnabled(false);

        lPersonaIVA = new CCLabel("IVA:",22);
        tPersonaIVA = new CCTextField(400,22,20,false);
        tPersonaIVA.setEnabled(false);        
        
        /**/
        
        lNumero = new CCLabel("Número:",22);
        tNumero = new CCTextField(100,false);
        tNumero.setEnabled(true);
        tNumero.setRegex("^[0-9]{1,8}$");
        
        lFacturaTipo = new CCLabel("");
        lFacturaTipo.setFont(new Font(Font.VERDANA, Font.BOLD, new Extent(14, Extent.PX)));
        lFacturaTipo.setForeground(Color.RED);
        
        cbAnulada = new CCCheckBox("Anulada");
        cbAnulada.addActionListener(this);
        cbAnulada.setActionCommand("anular");
        cbAnulada.setFont(new Font(Font.VERDANA, Font.BOLD, new Extent(14, Extent.PX)));
        cbAnulada.setForeground(Color.GREEN);
        
        /**/
        
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
        
    	        
    	lTotal_recibo = new CCLabel("TOTAL Factura:");
        tTotal_recibo = new CCTextField();
        tTotal_recibo.setEnabled(false);
        tTotal_recibo.setFont(new Font(Font.ARIAL, Font.BOLD, new Extent(12, Extent.PX)));
        
        lTotalComisiones = new CCLabel("TOTAL Grabado:");
        tTotalComisiones = new CCTextField();
        tTotalComisiones.setEnabled(false);
        tTotalComisiones.setFont(new Font(Font.ARIAL, Font.PLAIN, new Extent(12, Extent.PX)));
        
        lTotalIVA = new CCLabel("TOTAL IVA:");
        tTotalIVA = new CCTextField();
        tTotalIVA.setEnabled(false);
        tTotalIVA.setFont(new Font(Font.ARIAL, Font.PLAIN, new Extent(12, Extent.PX)));
        
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
        
        rNumero.add(tNumero);
        rNumero.add(new Separator());
        rNumero.add(lFacturaTipo);
        rNumero.add(new Separator());
        rNumero.add(cbAnulada);
        
        /* ---------------------------------------------- */
        
        cLabels.add(lNumero);
        cLabels.add(lFecha);
        cLabels.add(lPersonaNombre);
        cLabels.add(lPersonaDomicilio);
        cLabels.add(lPersonaIVA);
        cLabels.add(lLeyenda);        
        
        cTexts.add(rNumero);
        cTexts.add(tFecha);
        cTexts.add(tPersonaNombre);
        cTexts.add(tPersonaDomicilio);
        cTexts.add(tPersonaIVA);
        cTexts.add(taLeyenda);
        
        /* ---------------------------------------------- */
        
        rTotalRecibo.add(lTotal_recibo);
        rTotalRecibo.add(tTotal_recibo);
        
        rTotalComision.add(lTotalComisiones);
        rTotalComision.add(tTotalComisiones);
        
        rTotalIVA.add(lTotalIVA);
        rTotalIVA.add(tTotalIVA);
        
        /* ---------------------------------------------- */
        // La tabla la voy a colocar en una FILA para poder agregar los botones
        cpPrincipal.add(rItems);
        cpPrincipal.add(rTotalComision);
        cpPrincipal.add(rTotalIVA);
        cpPrincipal.add(rTotalRecibo);
                
        rMensaje.add(lMensaje);
        cpPrincipal.add(rMensaje);
        
        /* ---------------------------------------------- */
        this.rBotones.remove(1);
        this.rBotones.add(new Separator());
        this.rBotones.add(btnPrint);
        /* ---------------------------------------------- */
        
        ApplicationInstance.getActive().setFocusedComponent(tNumero);
           
    }
    
    
    @SuppressWarnings("unchecked")
	private void asignarDatos() {
    
    	System.out.println("FacturaListadoEditView.asignarDatos");
    	
    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    	//DecimalFormat moneda = new DecimalFormat("###,##0.00");
    	
		try {
			List<Factura> lista = new ArrayList<Factura>();
			lista.add(this.oFactura);
			FacturaProcesos.completar(lista);
		} catch (Exception e1) {
			e1.printStackTrace();
			// reviento !!!!!!!!!!!!
			
			// lleno el mensaje de error ¿?
			
		}
		
		lFacturaTipo.setText(this.oFactura.getFacturaTipoLetras());
		cbAnulada.setSelected(this.oFactura.getAnulada());
		if (this.oFactura.getAnulada()) {
			cbAnulada.setForeground(Color.RED);
		} else {
			cbAnulada.setForeground(Color.GREEN);
		}
    	 	
    	tNumero.setText(Utiles.complete(this.oFactura.getNumero().toString(), 8));
    	tFecha.setText(sdf.format(this.oFactura.getFechaEmision()));
    	tPersonaNombre.setText(this.oFactura.getCliente());
    	tPersonaDomicilio.setText(this.oFactura.getDomicilio());
    	tPersonaIVA.setText( ComboInscripcionIVA.getInscripcionIVAxID(this.oFactura.getIdInscripcionIva()) + " - "  + this.oFactura.getCuitDni());
    	taLeyenda.setText(this.oFactura.getLeyenda());
    	
        /** Cargo los datos en la grilla - Inicialmente esta vacia */
        dataList = new Vector(this.oFactura.getFacturaItems()); 
        oModel.setDataList(dataList);
        this.update(oModel);

    	
    }
    
    /**
     * Este es el método que actualiza los datos de la pantalla que esta abajo ¿?
     * @param oModel
     */
    @SuppressWarnings("unchecked")
	public void update(FacturaItemEditModel oModel) {
    	
    	// Recorro los datos de la tabla para sacar el total de la FACTURA
    	DecimalFormat moneda = new DecimalFormat("$###,##0.00");
    	List<FacturaItem> lDatos = oModel.getDataList();
    	
    	float total = 0;
    	float iva = 0;
    	float bruto = 0;
    	
    	for(FacturaItem obj: lDatos) {
    		
    		total += obj.getMonto();
    		
    		if (obj.getIdItemTipo() == ItemTipo.ItemIVA) {
    			iva += obj.getMonto();
    		} else {
    			// mientras no sea un item de IVA suma en el BRUTO
    			bruto += obj.getMonto();
    		}
    		
    	}
    	
    	tTotal_recibo.setText(moneda.format(total));
    	tTotalComisiones.setText(moneda.format(bruto));
    	tTotalIVA.setText(moneda.format(iva));
    	
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
			
		} else if (ae.getActionCommand().equals("anular")){
			
			// cambiar el color según el valor del checkbox
			CCCheckBox obj = (CCCheckBox)ae.getSource();
			if (obj.isSelected()) {			
				cbAnulada.setForeground(Color.RED);
			} else {
				cbAnulada.setForeground(Color.GREEN);
			}
			
		}

    	// Tiro el evento para arriba en la gerarquia de objetos
    	super.actionPerformed(ae);
    	
    }
    
    
    public boolean update() {
    	
    	this.oFactura.setAnulada(this.cbAnulada.isSelected());
    	this.oFactura.setNumero(Integer.parseInt(this.tNumero.getText()));
    	
    	// realiza la actualización del estado de una factura
    	FacturaDAO oDAO = new FacturaDAO();
    	oDAO.update(oFactura);
    	
    	return true;
    	
    }
    
    
    /**
     * Invoca el servlet que imprime el reporte
     */
    public void doPrint() {
    	
        String sUri = "PDFPreview?reporte=factura&id_factura=" + this.oFactura.getIdFactura();

        StringBuilder sb = new StringBuilder()
                .append("width=640")
                .append(",height=480")
                .append(",resizable=yes")
                .append(",scrollbars=yes");

        Command oComm = new BrowserOpenWindowCommand(sUri, "Prueba", sb.toString());
        ApplicationInstance.getActive().enqueueCommand(oComm);
    }
    

}