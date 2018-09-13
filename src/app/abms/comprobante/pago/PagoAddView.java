package app.abms.comprobante.pago;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import app.beans.ItemTipo;
import app.busquedas.inmueble.InmuebleFindListadoView;
import app.busquedas.persona.PersonaFindListadoView;
import app.combos.ComboAnios;
import app.combos.ComboMeses;

import ccecho2.base.CCButton;
import ccecho2.base.CCCheckBox;
import ccecho2.base.CCColumn;
import ccecho2.base.CCLabel;
import ccecho2.base.CCTable;

import ccecho2.base.CCRow;
import ccecho2.base.CCTextArea;
import ccecho2.base.CCTextField;
import ccecho2.complex.MessageWindowPane;
import ccecho2.complex.ComboList.NoItemSelectedException;

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
import nextapp.echo2.app.event.ActionListener;
import nextapp.echo2.app.layout.ColumnLayoutData;
import nextapp.echo2.app.layout.RowLayoutData;

//import nextapp.echo2.app.list.DefaultListSelectionModel;
import nextapp.echo2.app.list.ListSelectionModel;

import nextapp.echo2.webcontainer.command.BrowserOpenWindowCommand;


import datos.Page;
import datos.contrato.Contrato;
import datos.contrato.ContratoFacade;
import datos.contrato_actor.ContratoActor;

import datos.contrato_novedad_pago.ContratoNovedadPagoProcesos;
import datos.inmueble.Inmueble;

import datos.persona.Persona;

import datos.recibo_pago.ReciboPago;
import datos.recibo_pago.ReciboPagoDAO;
import datos.recibo_pago.ReciboPagoProcesos;
import datos.recibo_pago_item.ReciboPagoItem;
import datos.recibo_pago_item.ReciboPagoItemProcesos;
import datos.usuario.Usuario;

import echopointng.Separator;
import echopointng.table.AbleTableSelectionModel;
import echopointng.table.DefaultPageableSortableTableModel;

import framework.grales.seguridad.FWUsuario;
import framework.nr.generales.busquedas.FWBusquedas;
import framework.ui.generales.FWWindowPaneMensajes;
import framework.ui.generales.abms.ABMAddView;
import framework.ui.principal.FWApplicationInstancePrincipal;
import framework.ui.principal.FWContentPanePrincipal;

@SuppressWarnings("serial")
public class PagoAddView extends ABMAddView implements FWBusquedas {
	
// BOTONES
    private ImageReference iInmueble = new ResourceImageReference("/resources/crystalsvg22x22/actions/gohome.png");
    private CCButton btnInmueble;
    private Inmueble oInmueble;
    
    private ImageReference iPropietario = new ResourceImageReference("/resources/crystalsvg22x22/actions/kontact_contacts.png");
    private CCButton btnPropietario;
    private Persona oPropietario;

    
    private ImageReference iAlta = new ResourceImageReference("/resources/crystalsvg22x22/actions/add.png");
    private CCButton btnAltaItem;

    private ImageReference iBaja = new ResourceImageReference("/resources/crystalsvg22x22/actions/remove.png");
    private CCButton btnBajaItem;

    private ImageReference iEdit = new ResourceImageReference("/resources/crystalsvg22x22/actions/edit.png");
    private CCButton btnEditItem;

    private ImageReference iPrint = new ResourceImageReference("/resources/crystalsvg22x22/actions/fileprint.png");
    private CCButton btnPrint;
    protected CCRow rBotones_barra;

    
    
    
    private CCColumn _cPrincipal, cLabels, cTexts;
	private CCRow rPrincipal, rMensaje, rPropietario, rInmueble, rItems, rTotalRecibo, rTotalIVA, rTotalComision, rMesAnio, rTotalGANANCIA, rVacio;
	
	private CCLabel lMensaje;
	private CCLabel lInmueble, lPropietario, lInquilino, lLeyenda, lTotal_recibo, lFiltro, lReciboNumero, lTotalIVA, lTotalComisiones, lTotalGANANCIAS;
	
	private CCTextField tInmueble, tPropietario, tInquilino, tTotal_recibo, tReciboNumero, tTotalIVA, tTotalComisiones, tTotalGANANCIAS;
	private CCTextArea taLeyenda;
	
	private ComboMeses oComboMeses;
	private ComboAnios oComboAnios;
	
	private CCCheckBox chConSaldo;
	private ReciboPago oReciboPago;
	
	private float total_recibo;
	private boolean grabo_datos;
	
    private FWContentPanePrincipal CPPrincipal;
    
    // Tabla de objetos items
    public CCTable oTable;
    //private PageableSortableTable table;
    
    private List<ReciboPagoItem> dataList = new Vector<ReciboPagoItem>();
    PagoItemAddModel oModel = new PagoItemAddModel();
    //AbleTableSelectionModel ableTableSelectionModel;
    //ListSelectionModel oSelectionModel;

	
    public PagoAddView () {
        
    	super();
    	
    	// Seteo el título de la ventana
		this.setTitle("Alta de Comprobantes - Liquidación Pago");
		this.setHeight(new Extent(600, Extent.PX));
		this.setWidth(new Extent(730, Extent.PX));
		
		CPPrincipal = ((FWContentPanePrincipal) ApplicationInstance.getActive().getDefaultWindow().getContent());
		
		grabo_datos = false;
		
		// Agrego los componentes de la pantalla
		crearObjetos();
		renderObjetos();
		
		// Pruebo asignar el layout una vez dibujada las filas son para las FILAS DE TOTALES
		int componentCount = 2;
		for (int i = 0; i < componentCount; ++i) {
			 RowLayoutData rowLayoutData = new RowLayoutData();
			 rowLayoutData.setWidth(new Extent(100 / componentCount, Extent.PERCENT));
			 rTotalRecibo.getComponent(i).setLayoutData(rowLayoutData);
			 rTotalIVA.getComponent(i).setLayoutData(rowLayoutData);
			 rTotalComision.getComponent(i).setLayoutData(rowLayoutData);
			 rTotalGANANCIA.getComponent(i).setLayoutData(rowLayoutData);
		}
		
		//oPropietario = new Persona();
    }
        
	
	/* FALTA
	 * 1 	Selecciono el propietario (puede tener mas de un propiedad)									 
	 * 		Liquido todas las propiedades en el mismo comprobante
	 * 		En el detalle va a salir el listado de propiedades ... una lista en pantalla
	 * 
	 * 		chequear los datos de la tabla ContratoNovedadPago. Fecha de Vencimiento esta en null ???
	 * 		a. La fecha esta en null siempre que el tipo de novedad sea "Comision Alquiler", ademas
	 * 		de ser negativo el monto
	 * 		b. La fecha no esta nula cuando el tipo de novedad es "Impuesto" y el monto es positivo
	 * 
	 * 2	Puedo seleccionar una propiedad, en este caso solo liquido la propiedad seleccionada
	 * 
	 */
	
    
    //	 Se activa cuando alguien selecciona una fila de la Tabla
	public ActionListener tablaActionListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			
			//System.out.println("CLICK");
			
		}
	};

    
    
    private void crearObjetos() {
    	
        _cPrincipal = new CCColumn();
        _cPrincipal.setCellSpacing(new Extent(20));
        _cPrincipal.setInsets(new Insets(10));
        
        rPrincipal = new CCRow();        
        rInmueble = new CCRow(22);
        rPropietario = new CCRow(22);
        rItems = new CCRow();
        
        rTotalRecibo = new CCRow(22);
        rTotalComision = new CCRow(22);
        rTotalIVA = new CCRow(22);
        rTotalGANANCIA = new CCRow(22);
        
        rMesAnio = new CCRow(22);
        rVacio = new CCRow(22);
        
                
        cLabels = new CCColumn();
        cLabels.setCellSpacing(new Extent(10, Extent.PX));
        cLabels.setInsets(new Insets(10));
        
        cTexts = new CCColumn();
        cTexts.setCellSpacing(new Extent(10, Extent.PX));
        cTexts.setInsets(new Insets(10));

        RowLayoutData cRowLD = new RowLayoutData();
        cRowLD.setAlignment(new Alignment(Alignment.RIGHT,Alignment.CENTER));
        cRowLD.setWidth(new Extent(50, Extent.PERCENT));
        
        rTotalRecibo.setCellSpacing(new Extent(10, Extent.PX));
        rTotalRecibo.setInsets(new Insets(200,0,0,0));        
        rTotalRecibo.setLayoutData(cRowLD);
        
        rTotalComision.setCellSpacing(new Extent(1, Extent.PX));
        rTotalComision.setInsets(new Insets(200,0,0,0));
        rTotalComision.setLayoutData(cRowLD);
        
        rTotalIVA.setCellSpacing(new Extent(10, Extent.PX));
        rTotalIVA.setInsets(new Insets(200,0,0,0));
        rTotalIVA.setLayoutData(cRowLD);
        
        rTotalGANANCIA.setCellSpacing(new Extent(10, Extent.PX));
        rTotalGANANCIA.setInsets(new Insets(200,0,0,0));
        rTotalGANANCIA.setLayoutData(cRowLD);
        
        
        /* Configuro el boton del Inmueble */
        btnInmueble = new CCButton(iInmueble);
        this.btnInmueble.setActionCommand("inmueble");
        this.btnInmueble.setToolTipText("Asignar Inmueble");        
        this.btnInmueble.setStyleName(null);
        this.btnInmueble.setInsets(new Insets(10, 0));        
        this.btnInmueble.addActionListener(this);
        
        
        /* Configuro el boton del Inquilino */
        btnPropietario = new CCButton(iPropietario);
        this.btnPropietario.setActionCommand("propietario");
        this.btnPropietario.setToolTipText("Asignar Propietario");        
        this.btnPropietario.setStyleName(null);
        this.btnPropietario.setInsets(new Insets(10, 0));        
        this.btnPropietario.addActionListener(this);
        

        /* Configuro los botones AB Items */
        btnAltaItem = new CCButton(iAlta);
        btnAltaItem.setActionCommand("alta_item");
        btnAltaItem.setToolTipText("Alta de un nuevo item");        
        btnAltaItem.setStyleName(null);
        btnAltaItem.setInsets(new Insets(10, 10));        
        btnAltaItem.addActionListener(this);

        btnBajaItem = new CCButton(iBaja);
        btnBajaItem.setActionCommand("baja_item");
        btnBajaItem.setToolTipText("Baja del item seleccionado");        
        btnBajaItem.setStyleName(null);
        btnBajaItem.setInsets(new Insets(10, 10));        
        btnBajaItem.addActionListener(this);
        
        btnEditItem = new CCButton(iEdit);
        btnEditItem.setActionCommand("edit_item");
        btnEditItem.setToolTipText("Edición del item seleccionado");        
        btnEditItem.setStyleName(null);
        btnEditItem.setInsets(new Insets(10, 10));        
        btnEditItem.addActionListener(this);
        
        /* Boton impresión */
        btnPrint = new CCButton(iPrint);
        btnPrint.setActionCommand("imprimir");
        btnPrint.setToolTipText("Imprimir comprobante");
        btnPrint.addActionListener(this);
        //btnPrint.setEnabled(false);
        
        /*******************************************************************/
        lReciboNumero = new CCLabel("Número:",22);
        tReciboNumero = new CCTextField(100, 22, 100, false);
        // solamente puede ingresar números positivos y enteros
        tReciboNumero.setRegex("^[0-9]{1,8}$");
        //tReciboNumero.setText("00000000");
        //tReciboNumero.setEnabled(false);

        /* Tengo que buscar el próximo número de Recibo */
        ReciboPagoDAO oDAO = new ReciboPagoDAO();
        tReciboNumero.setText("" + (oDAO.findMaxNumeroRecibo() + 1));
        
        
        /*******************************************************************/
        
        lInmueble = new CCLabel("Inmueble:",22);
        tInmueble = new CCTextField(300, false);
        tInmueble.setEnabled(false);
                
        lPropietario = new CCLabel("Propietario:",22);
        tPropietario = new CCTextField(300,false);
        tPropietario.setEnabled(false);
        
        lInquilino = new CCLabel("Inquilino:",22);
        tInquilino = new CCTextField(300,22,20,false);
        tInquilino.setEnabled(false);

        lLeyenda = new CCLabel("Leyenda:",44);
        taLeyenda = new CCTextArea(400,44,2,true);
        
        lFiltro = new CCLabel("Filtro:",22);
        //chConSaldo = new CCCheckBox("Cuotas vigentes", 22);
        chConSaldo = new CCCheckBox("Cuotas vigentes");
        chConSaldo.setSelected(true);
        
        /*******************************************************************/
        
        //lMesAnio = new CCLabel("Mes y Año:");
        oComboAnios = new ComboAnios(100,22,10,true,(short)2);
        oComboMeses = new ComboMeses(100,22,11,true,(short)2);
        
        /*******************************************************************/
        /* Tabla con el resultado de la búsqueda */
    	this.oTable = new CCTable("");
    	this.oTable.setHeaderBackground(Color.LIGHTGRAY);
    	this.oTable.setFont(new Font(Font.ARIAL, Font.PLAIN, new Extent(11)));
    	this.oTable.setWidth(new Extent(640));
        
    	ColumnLayoutData cTableLD = new ColumnLayoutData();
    	cTableLD.setAlignment(new Alignment(Alignment.LEFT,Alignment.BOTTOM));
    	this.oTable.setLayoutData(cTableLD);
        
    	
        lTotal_recibo = new CCLabel("TOTAL Recibo:");
        tTotal_recibo = new CCTextField();
        tTotal_recibo.setEnabled(false);
        
        lTotalComisiones = new CCLabel("TOTAL Comisiones:");
        tTotalComisiones = new CCTextField();
        tTotalComisiones.setEnabled(false);
        
        lTotalIVA = new CCLabel("TOTAL IVA:");
        tTotalIVA = new CCTextField();
        tTotalIVA.setEnabled(false);
        
        lTotalGANANCIAS = new CCLabel("TOTAL Retención GAN:");
        tTotalGANANCIAS = new CCTextField();
        tTotalGANANCIAS.setEnabled(false);

        /** Cargo los datos en la grilla - Inicialmente esta vacia */
        oModel.setDataList(dataList);
        this.update(oModel, true);
        
        /*******************************************************************/
        rMensaje = new CCRow();
        rMensaje.setAlignment(Alignment.ALIGN_CENTER);

        lMensaje = new CCLabel();
        lMensaje.setForeground(Color.RED);

    }
    
    
    private void renderObjetos() {
        
    	// Agrego la columna al ContentPane Principal
    	cpPrincipal.add(_cPrincipal);

        _cPrincipal.add(rPrincipal);
        rPrincipal.add(cLabels);
        rPrincipal.add(cTexts);
        
        rInmueble.add(tInmueble);
        rInmueble.add(btnInmueble);

        rPropietario.add(tPropietario);
        rPropietario.add(btnPropietario);

        rMesAnio.add(chConSaldo);
        rMesAnio.add(new Separator());
        rMesAnio.add(oComboMeses);
        rMesAnio.add(new Separator());
        rMesAnio.add(oComboAnios);
        
        rVacio.add(new CCLabel(" "));
        
        CCColumn c1 = new CCColumn();
        CCColumn c2 = new CCColumn(); 

        c1.add(this.oTable);
        //c1.add(this.table);
        
        c2.add(btnAltaItem);
        c2.add(btnBajaItem);
        c2.add(btnEditItem);        
        
        rItems.add(c1);
        rItems.add(c2);
        
        
        cLabels.add(lReciboNumero);
        cLabels.add(lInmueble);
        cLabels.add(lPropietario);
        cLabels.add(lInquilino);
        cLabels.add(lFiltro);
        cLabels.add(lLeyenda);
        //cLabels.add(lMesAnio);
        
        /* ---------------------------------------------- */
        
        cTexts.add(tReciboNumero);
        cTexts.add(rInmueble);
        cTexts.add(rPropietario);
        cTexts.add(tInquilino);
        //cTexts.add(chConSaldo);
        cTexts.add(rMesAnio);
        cTexts.add(taLeyenda);
        //cTexts.add(rMesAnio);
        
        rTotalRecibo.add(lTotal_recibo);
        rTotalRecibo.add(tTotal_recibo);
        
        rTotalComision.add(lTotalComisiones);
        rTotalComision.add(tTotalComisiones);
        
        rTotalIVA.add(lTotalIVA);
        rTotalIVA.add(tTotalIVA);
        
        rTotalGANANCIA.add(lTotalGANANCIAS);
        rTotalGANANCIA.add(tTotalGANANCIAS);
        
        /* ---------------------------------------------- */
        // La tabla la voy a colocar en una FILA para poder agregar los botones
        cpPrincipal.add(rItems);
        cpPrincipal.add(rTotalComision);
        cpPrincipal.add(rTotalIVA);
        cpPrincipal.add(rTotalGANANCIA);
        cpPrincipal.add(rTotalRecibo);
        cpPrincipal.add(rVacio);
        
        rMensaje.add(lMensaje);
        
        /* ---------------------------------------------- */        
        //this.rBotones.add(new Separator());
        //this.rBotones.add(btnPrint);
        /* ---------------------------------------------- */
        
        ApplicationInstance.getActive().setFocusedComponent(btnInmueble);
        
    }
	
	
    @SuppressWarnings({ "deprecation", "static-access", "unchecked" })
	public void doInsert() {
    	
    	/**
    	 * Valido los datos - INTEGRIDAD
    	 * --------------------------------
    	 * 1º Validación de datos numericos
    	 * 2º Se hayan cargados los datos INMUEBLE o PROPIETARIO
    	 * 3º Que el número de recibo no este duplicado ... ojo que si hay mas de un facturero, no puedo validar
    	 * 	  solo con el número, sino que necesito también la responsabilidad social o factura !!!
    	 */ 
    	
    	// valido que el total del recibo sea positivo, > 0.
    	if (total_recibo <= 0) {
    		ApplicationInstance.getActive().setFocusedComponent(tTotal_recibo);
            //throw new ValidacionException("Debe seleccionar una PROPIEDAD o un INQUILINO para continuar.");
    		//System.out.println("Total del Recibo PAGO: " + total_recibo);
        	new FWWindowPaneMensajes("El TOTAL DEL RECIBO debe ser positivo, mayor a cero.", "ERROR");
        	return;
    	}
    	    	
        if (oInmueble == null && oPropietario == null) {
        	ApplicationInstance.getActive().setFocusedComponent(btnInmueble);
            //throw new ValidacionException("Debe seleccionar una PROPIEDAD o un INQUILINO para continuar.");
        	new FWWindowPaneMensajes("Debe seleccionar una PROPIEDAD o un PROPIETARIO para continuar.", "ERROR");
        	return;
        }
        
        int filtro_nro_recibo = Integer.parseInt(tReciboNumero.getText());
        int page_number = 0, page_size = 10, filtro_inmueble = 0, filtro_propietario = 0;
        Date filtro_fecha_desde = null, filtro_fecha_hasta = null;       
        Page datos = ReciboPagoProcesos.findByFilter(page_number, page_size, filtro_inmueble, filtro_propietario, filtro_nro_recibo, filtro_fecha_desde, filtro_fecha_hasta);
        if (datos.getList().size() != 0) {
        	ApplicationInstance.getActive().setFocusedComponent(tReciboNumero);
            //throw new ValidacionException("Debe seleccionar una PROPIEDAD o un INQUILINO para continuar.");
        	new FWWindowPaneMensajes("El Número de Recibo ingresado ya existe, modifíquelo.", "ERROR");
        	return;
        }
        
        
        /** Fin validaciones */
    	
    	// Levanto los valores ingresados
    	FWUsuario oFWUsuario = ((FWApplicationInstancePrincipal) this.getApplicationInstance().getActive()).getUsuario();
    	oReciboPago = new ReciboPago();
    	
    	oReciboPago.setPersona(oPropietario);
    	oReciboPago.setLeyenda(taLeyenda.getText());    	
    	oReciboPago.setUsuario(new Usuario(oFWUsuario.getId(), oFWUsuario.getUsuario(), ""));
    	oReciboPago.setNumero(new Integer(tReciboNumero.getText()));
    	
    	/* Tengo que armar los datos de los items */
    	List<ReciboPagoItem> lDatos = this.oModel.getDataList();
    	
    	
    	// Inserto
    	try {
    		ReciboPagoProcesos.save(oReciboPago, lDatos);   
    		grabo_datos = true;
    	} catch(Exception e) {
    		e.printStackTrace();
    		new FWWindowPaneMensajes("Error inesperado del proceso.", "ERROR");
    		return;
    	}
    	
    	new MessageWindowPane("El proceso de grabación se realizo con éxito");
    	
/* 
 * Aca tendría que informar el número de recibo asignado
 * Habilitar el boton de impresión del RECIBO
 * Que pasa si quiero borrarlo
 */

    	//this.btnPrint.setEnabled(true);
        this.rBotones.add(new Separator());
        this.rBotones.add(btnPrint);
    	
    	
    }
    
    
    public void actionPerformed(ActionEvent ae) {
    	
    	if (ae.getActionCommand().equals("propietario")){
    		
            CPPrincipal.abrirVentanaMensaje(new PersonaFindListadoView("Propietario", this, ContratoActor.ActorTipoPropietario));
    		
            
    	} else if (ae.getActionCommand().equals("inmueble")){
    		
    		CPPrincipal.abrirVentanaMensaje(new InmuebleFindListadoView(this, false));
    		
    		
    	} else if (ae.getActionCommand().equals("edit_item")){
    		
    		// Tengo que validar que haya solo un item seleccionado
    		AbleTableSelectionModel ableTableSelectionModel = (AbleTableSelectionModel) this.oTable.getSelectionModel();
    		int selectedIndices[] = ableTableSelectionModel.getSelectedIndices();
    		if (selectedIndices.length > 1) {
    		
    			CPPrincipal.abrirVentana(new FWWindowPaneMensajes("Error Edición Items","Debe seleccionar una sola fila para continuar.",(short)1));
    			
    		} else if (selectedIndices.length == 0) {
    		
    			CPPrincipal.abrirVentana(new FWWindowPaneMensajes("Error Edición Items","Debe seleccionar una fila para continuar.",(short)1));
    			
    		// Levanto el item seleccionado
    		// if (this.oTable.getSelectionModel().getMinSelectedIndex() >= 0) {
    		} else {
    			
    			int currentRow = this.oTable.getSelectionModel().getMinSelectedIndex();
    			ReciboPagoItem oReciboPagoItem = (ReciboPagoItem)oModel.getDataList().get(currentRow);    			
    			
    			CPPrincipal.abrirVentanaMensaje(new ItemEditView(this, oReciboPagoItem, currentRow));
    		
    		}
    		
    	} else if (ae.getActionCommand().equals("alta_item")){
    		
    		CPPrincipal.abrirVentanaMensaje(new ItemEditView(this, null, 0));
    		
    		
    	} else if (ae.getActionCommand().equals("baja_item")){
    		
    		// Levanto todos los items seleccionados y los saco de la tabla
    		AbleTableSelectionModel ableTableSelectionModel = (AbleTableSelectionModel) this.oTable.getSelectionModel();
    		int selectedIndices[] = ableTableSelectionModel.getSelectedIndices();
    		
    		if (selectedIndices.length == 0) {
    			CPPrincipal.abrirVentana(new FWWindowPaneMensajes("Error Baja Items","Debe seleccionar una fila para continuar.",(short)1));
    		} else {
    		
    			// if (this.oTable.getSelectionModel().getMinSelectedIndex() >= 0) {
        		new MessageWindowPane("¿Esta seguro de eliminar el Item seleccionado?", this, "doDelete");
    			    		
    		}
    		
    	} else if (ae.getActionCommand().equals("cancel")){
    		    		
    		// Se ejecuta el método por sobrecarga
    		
    	} else if (ae.getActionCommand().equals("imprimir")){
    		
    		// Aca lanzo la impresión
    		doPrint();
    		
    	}

    	
    	
    	
    	// Tiro el evento para arriba en la gerarquia de objetos
    	super.actionPerformed(ae);
    	
    }

    
    @SuppressWarnings("unused")
	public void doDelete() {
    	
    	// Ahora puedo tener una colección de items seleccionados
    	AbleTableSelectionModel ableTableSelectionModel = (AbleTableSelectionModel) this.oTable.getSelectionModel();
		int selectedIndices[] = ableTableSelectionModel.getSelectedIndices();
		
		//System.out.println("Cantidad de filas seleccionadas " + selectedIndices.length);
		boolean retencionGanancias = false;
		Vector<ReciboPagoItem> vectorDeObjetosABorrar = new Vector<ReciboPagoItem>();
    	for (int i = 0; i < selectedIndices.length; i++) {
			int row = selectedIndices[i];
			
			// Una vez que borra una fila se pierde la posición de la fila seleccionada por que ahora tengo una fila menos ...
			
			/*
			 * Una posibilidad es que guarde una colección con los objetos seleccionados para ser borrados y luego recorra
			 * la colección y uso el método remove con un objeto
			 */
			
			//this.oModel.getDataList().remove(row);
			
			
			ReciboPagoItem oReciboPagoItem = (ReciboPagoItem)oModel.getDataList().get(row);
			vectorDeObjetosABorrar.add(oReciboPagoItem);
			
			//this.oTable.remove(row);			
			//System.out.println("Fila seleccionada: " + row);
			
			// Evaluo si dentro de las filas borradas esta la RETENCION DE GANANCIAS - pgarello 25/03/2015
			if (oReciboPagoItem.getIdItemTipo() == ItemTipo.ItemRetencionGanancia ) {
				retencionGanancias = true;
			}
			
    	}
    	
    	for (ReciboPagoItem oReciboPagoItem: vectorDeObjetosABorrar) {
    		this.oModel.getDataList().remove(oReciboPagoItem);
    	}
    	
		//int currentRow = this.oTable.getSelectionModel().getMinSelectedIndex();
		//this.oModel.getDataList().remove(currentRow);
				
		update(this.oModel, !retencionGanancias);
    }
    
    
    public void doLimpiar() {
    
    	tInmueble.setText("");
    	tPropietario.setText("");
    	tInquilino.setText("");
    	
		dataList = new Vector<ReciboPagoItem>();
		oModel.setDataList(dataList);
		update(this.oModel, false);
		
		oComboAnios.setSelectedText(0);
		oComboMeses.setSelectedText(0);
    	
    }
    

	@SuppressWarnings("unchecked")
	public void setResultado(Object object) {
		
		boolean vigentes = chConSaldo.isSelected();
		short mes = 0;
		short anio = 0;
		
		try {
			mes = Short.parseShort(oComboMeses.getSelectedId());
			anio = Short.parseShort(oComboAnios.getSelectedId());
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (NoItemSelectedException e) {
			e.printStackTrace();
		}
		
		//System.out.println("Datos: " + mes + " - " + anio);
		
		if (object instanceof Persona) {
			
			// previo a llenar los datos limpio la grilla primero
			dataList = new Vector<ReciboPagoItem>();
			oModel.setDataList(dataList);
			update(this.oModel, false);
			
			// Viene de la busqueda de persona			
			tPropietario.setText(((Persona)object).getDescripcion());
			oPropietario = (Persona) object;
			
			/* 
			 * levanto todos los movimientos de contrato_novedad_pago de esta persona
			 * No tengo que ver todos ... solo lo vencido y no liquidado 
			 */
			/** MAS DE 50 DATOS NO FUNCIONA LA TABLA !!! */
			List lNovedades = ContratoNovedadPagoProcesos.buscarNovedadesPagoPorPropietario(oPropietario, true, vigentes, mes, anio);			
			//System.out.println("DATOSSSSSSSSSSSSSSSSSSSSSS " + lNovedades.size());
			
			/*
			 * Agrego el IVA. Tengo que consultar el IVA actual del SISTEMA y aplicarselo al movimiento del tipo COMISION ALQUILER
			 * ver si hay que tener en cuenta algún otro tipo de NOVEDAD
			 * Comisión Alquiler = $ 100 => IVA = $ 21.
			 * 
			 * Ahora lo agrego en el método update
			 */			
			// List<ReciboPagoItem> lItems = ReciboPagoItemProcesos.completarItemsPagoConIVA(ReciboPagoItemProcesos.convertirLista(lNovedades));
			List<ReciboPagoItem> lItems = ReciboPagoItemProcesos.convertirLista(lNovedades);
						
	        oModel.getDataList().addAll(lItems);
	        this.update(oModel, true);
	        
		} else if (object instanceof Inmueble) {
			
			// previo a llenar los datos limpio la grilla primero
			dataList = new Vector<ReciboPagoItem>();
			oModel.setDataList(dataList);
			update(this.oModel, false);
			
			
			oInmueble = (Inmueble)object; 
						
			// Tengo que buscar el contrato donde figura el inmueble que traigo de filtro
			List<Contrato> lContratos = ContratoFacade.findByInmueble(oInmueble, true);
			
			// Ve cuando da error por que la propiedad no tiene ningún contrato vigente
			if (lContratos.size() != 0) {
				Contrato oContrato = lContratos.get(0);

				// Una vez que tengo el inmueble levanto los actores
				Persona oInquilino = null;
				for(ContratoActor obj: oContrato.getContratoActors()) {
					if (obj.getId().getIdActorTipo() == ContratoActor.ActorTipoInquilino) {
						oInquilino = obj.getId().getPersona();
					} else if (obj.getId().getIdActorTipo() == ContratoActor.ActorTipoPropietario) {
						oPropietario = obj.getId().getPersona();
					}
				}
				
				tInquilino.setText(oInquilino.getDescripcion());
				tPropietario.setText(oInmueble.getPropietario().getDescripcion());
				tInmueble.setText(oInmueble.getDireccion_completa());
				
				// levanto todos los movimientos de contrato_novedad_cobro del CONTRATO
				//List lNovedades = ContratoNovedadCobroDAO.findByContrato(oContrato);
				List lNovedades = ContratoNovedadPagoProcesos.buscarNovedadesPagoPorContrato(oContrato, true, vigentes, mes, anio);				
				System.out.println("DATOSSSSSSSSSSSSSSSSSSSSSS " + lNovedades.size());
				
				/*
				 * Agrego el IVA. Tengo que consultar el IVA actual del SISTEMA y aplicarselo al movimiento del tipo COMISION ALQUILER
				 * ver si hay que tener en cuenta algún otro tipo de NOVEDAD
				 * Comisión Alquiler = $ 100 => IVA = $ 21.
				 * 
				 * Ahora lo agrego en el método update
				 */
				//List<ReciboPagoItem> lItems = ReciboPagoItemProcesos.completarItemsPagoConIVA(ReciboPagoItemProcesos.convertirLista(lNovedades));
				List<ReciboPagoItem> lItems = ReciboPagoItemProcesos.convertirLista(lNovedades);
							
		        oModel.getDataList().addAll(lItems);
		        this.update(oModel, true);
		        
			} else {
			
				new MessageWindowPane("El inmueble no posee ningún contrato vigente.");
				
			}
			
		}
		
	}
	
	
    @SuppressWarnings("unchecked")
	public void update(PagoItemAddModel oModel, boolean calcularGanancia) {
            	
		// Recorro la tabla para sacar el total del recibo
    	DecimalFormat moneda = new DecimalFormat("$###,##0.00");
    	List<ReciboPagoItem> lDatos = oModel.getDataList();
    	
    	// Completo la lista con los datos del IVA
    	List<ReciboPagoItem> lDatosMasIVA = ReciboPagoItemProcesos.completarItemsPagoConIVA(lDatos);
    	
    	// Completo la lista con los datos del GANANCIA
    	// Da ERROR por que al principio el PROPIETARIO no está inicializado !!!
    	/**
    	 * Si quiero borrar el item de ganancia !!! como hago
    	 */
    	oModel.setDataList(new Vector<ReciboPagoItem>());
    	if (calcularGanancia) {
    		List<ReciboPagoItem> lDatosMasGANANCIA = ReciboPagoItemProcesos.completarItemsPagoConGANANCIA(lDatosMasIVA, oPropietario);
    		oModel.getDataList().addAll(lDatosMasGANANCIA);
    	} else {
    		oModel.getDataList().addAll(lDatosMasIVA);
    	}
    	
        BigDecimal total = new BigDecimal(0.0);
    	float total_iva = 0, total_comisiones = 0, total_retecion_gan = 0;
    	for(ReciboPagoItem obj: (List<ReciboPagoItem>)oModel.getDataList()) {
    		total = total.add(new BigDecimal(obj.getMonto()));
    		
    		if (obj.getIdItemTipo() == ItemTipo.ItemIVA) {
    			total_iva += obj.getMonto();
    		}
    		
    		if (obj.getIdItemTipo() == ItemTipo.ItemComisionAlquiler) {
    			total_comisiones += obj.getMonto();
    		}
    		
    		if (obj.getIdItemTipo() == ItemTipo.ItemRetencionGanancia) {
    			total_retecion_gan += obj.getMonto();
    		}
    	}
    	
    	this.total_recibo = total.floatValue();
    	total = total.setScale(2, RoundingMode.HALF_UP);
    	tTotal_recibo.setText(moneda.format(total));
    	tTotalComisiones.setText(moneda.format(total_comisiones));
    	tTotalIVA.setText(moneda.format(total_iva));
    	tTotalGANANCIAS.setText(moneda.format(total_retecion_gan));
    	
        // Refresco la lista
        this.oTable.setModel(oModel);
        
        // Actualizo la lista de selección .. limpia
        final DefaultPageableSortableTableModel pageableSortableTableModel = new DefaultPageableSortableTableModel(this.oModel);
    	final AbleTableSelectionModel ableTableSelectionModel = new AbleTableSelectionModel(pageableSortableTableModel);
		ableTableSelectionModel.setSelectionMode(ListSelectionModel.MULTIPLE_SELECTION);
		this.oTable.setSelectionModel(ableTableSelectionModel);
        
    }
    
    
    /**
     * Invoca el servlet que imprime el reporte
     */
    public void doPrint() {
    	
    	// Hasta que no se grabó el comprobante no puedo imprimirlo
    	if (grabo_datos) {
    	
	        String sUri = "PDFPreview?reporte=comprobanteReciboPago&id_recibo_pago=" + this.oReciboPago.getIdReciboPago();
	
	        StringBuilder sb = new StringBuilder()
	                .append("width=640")
	                .append(",height=480")
	                .append(",resizable=yes")
	                .append(",scrollbars=yes");
	
	        Command oComm = new BrowserOpenWindowCommand(sUri, "Prueba", sb.toString());
	        ApplicationInstance.getActive().enqueueCommand(oComm);
	        
    	} else {
    		
    		//ApplicationInstance.getActive().setFocusedComponent();
        	new FWWindowPaneMensajes("Debe grabar el COMPROBANTE antes de imprimirlo.", "ERROR");
        	return;
    		
    	}
    	
    }
               
}