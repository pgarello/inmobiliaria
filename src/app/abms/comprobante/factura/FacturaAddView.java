package app.abms.comprobante.factura;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Vector;

import app.beans.ItemTipo;
import app.busquedas.persona.PersonaFindListadoView;

import ccecho2.base.CCButton;
import ccecho2.base.CCCheckBox;
import ccecho2.base.CCColumn;
import ccecho2.base.CCLabel;
import ccecho2.base.CCTable;

import ccecho2.base.CCRow;
import ccecho2.base.CCTextArea;
import ccecho2.base.CCTextField;
import ccecho2.complex.MessageWindowPane;

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

import datos.persona.Persona;

import datos.contrato_novedad_factura.ContratoNovedadFacturaProcesos;
import datos.factura.Factura;
import datos.factura.FacturaDAO;
import datos.factura.FacturaProcesos;
import datos.factura_item.FacturaItem;
import datos.factura_item.FacturaItemProcesos;


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


/**
 * Pantalla para el alta de una FACTURA (puede ser A o B) según el cliente
 * Un cliente puede ser cualquier persona que registrada en el SISTEMA (INQUILINO, PROPIETARIO, OTRO)
 * Para determinar el tipo (A/B) lo puedo dejar en forma automática en base a los datos cargados en PERSONA
 * 
 * 1º	Debo seleccionar la PERSONA a la que le voy a GENERAR el COMPROBANTE
 * 		Si es un INQUILINO => busco los registros que tenga para facturar
 * 		Puede ser un PROPIETARIO también
 * 
 * 		Ademas les puedo agregar algún item manual.
 * 		POR AHORA NO VOY A DEJAR MODIFICAR LOS ITEMS O AGREGAR UNO NUEVO, SOLO ELIMINAR !!!
 * 
 * 		Voy a dejar modificar los datos de la PERSONA (domicilio - razón social) solo para los efectos de la impresión ¿?
 * 
 * 2º	Selecciono si se trata de una FACTURA A o B
 * 		Valido la selección del tipo de comprobante con el de la persona
 * 
 *
 */


@SuppressWarnings("serial")
public class FacturaAddView extends ABMAddView implements FWBusquedas {
	
// BOTONES
    private ImageReference iPersona = new ResourceImageReference("/resources/crystalsvg22x22/actions/kontact_contacts.png");
    private CCButton btnPersona;
    private Persona oPersona;

    
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
	private CCRow rPrincipal, rMensaje, rPersona, rItems, rTotales, rNumero, rTotalIVA, rTotalBruto;
	
	private CCLabel lMensaje;
	private CCLabel lPersona, lLeyenda, lTotal_recibo, lFiltro, lReciboNumero, lVacio, lVacio1, lVacio2, lFacturaTipo, lTotalIVA, lTotalBruto;
	
	private CCTextField tPersona, tTotal_recibo, tFacturaNumero, tIVA, tCUIT, tDomicilio, tTotalIVA, tTotalBruto;
	private CCTextArea taLeyenda;
	
	private CCCheckBox chConSaldo;
	private Factura oFactura;
	
	private float total_recibo;
	private boolean grabo_datos;
	
    private FWContentPanePrincipal CPPrincipal;
    
    // Tabla de objetos items
    public CCTable oTable;
    
    private List<FacturaItem> dataList = new Vector<FacturaItem>();
    FacturaItemAddModel oModel = new FacturaItemAddModel();

    public FacturaAddView () {
        
    	super();
    	
    	// Seteo el título de la ventana
		this.setTitle("Alta de Comprobantes - Factura");
		this.setHeight(new Extent(550, Extent.PX));
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
			 rTotalBruto.getComponent(i).setLayoutData(rowLayoutData);
			 rTotalIVA.getComponent(i).setLayoutData(rowLayoutData);
			 rTotales.getComponent(i).setLayoutData(rowLayoutData);
		}
		
    }
        
	

	
    
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
        rPersona = new CCRow(22);
        rItems = new CCRow();
        rTotales = new CCRow(22);
        rNumero = new CCRow(22);
        
        rTotalBruto = new CCRow(22);
        rTotalIVA = new CCRow(22);
                
        cLabels = new CCColumn();
        cLabels.setCellSpacing(new Extent(10, Extent.PX));
        cLabels.setInsets(new Insets(10));
        
        cTexts = new CCColumn();
        cTexts.setCellSpacing(new Extent(10, Extent.PX));
        cTexts.setInsets(new Insets(10));

        rTotales.setCellSpacing(new Extent(1, Extent.PX));
        rTotales.setInsets(new Insets(200,0,0,0));
//        RowLayoutData cRowLD = new RowLayoutData();
//        cRowLD.setAlignment(new Alignment(Alignment.RIGHT,Alignment.CENTER));
//        rTotales.setLayoutData(cRowLD);

        rTotalBruto.setCellSpacing(new Extent(1, Extent.PX));
        rTotalBruto.setInsets(new Insets(200,0,0,0));
//        rTotalBruto.setLayoutData(cRowLD);
        
        rTotalIVA.setCellSpacing(new Extent(1, Extent.PX));
        rTotalIVA.setInsets(new Insets(200,0,0,0));
//        rTotalIVA.setLayoutData(cRowLD);

        
        
        /* Configuro el boton del Persona */
        btnPersona = new CCButton(iPersona);
        this.btnPersona.setActionCommand("persona");
        this.btnPersona.setToolTipText("Buscar Persona");        
        this.btnPersona.setStyleName(null);
        this.btnPersona.setInsets(new Insets(10, 0));        
        this.btnPersona.addActionListener(this);
        
        
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
        tFacturaNumero = new CCTextField(100, false);
        // solamente puede ingresar números positivos y enteros
        tFacturaNumero.setRegex("^[0-9]{1,8}$");
        tFacturaNumero.setText("00000000");
        //tReciboNumero.setEnabled(false);

        lFacturaTipo = new CCLabel("");
        lFacturaTipo.setFont(new Font(Font.VERDANA, Font.BOLD, new Extent(14, Extent.PX)));
        lFacturaTipo.setForeground(Color.RED);
        
        /*******************************************************************/
                
        lPersona = new CCLabel("Persona:",22);
        tPersona = new CCTextField(300,false);
        tPersona.setEnabled(false);

        lVacio = new CCLabel("",22);
        lVacio1 = new CCLabel("",22);
        lVacio2 = new CCLabel("",22);
        
        tIVA = new CCTextField(300, 22, 100, false);
        tCUIT = new CCTextField(300, 22, 100, false);
        tDomicilio = new CCTextField(300, 22, 100, false);
        
        tIVA.setEnabled(false);
        tCUIT.setEnabled(false);
        tDomicilio.setEnabled(false);
        
        lLeyenda = new CCLabel("Leyenda:",44);
        taLeyenda = new CCTextArea(400,44,2,true);
        
//        lFiltro = new CCLabel("Filtro:",22);
//        chConSaldo = new CCCheckBox("Cuotas vigentes", 22);
//        chConSaldo.setSelected(true);
        
        /*******************************************************************/
        /* Tabla con el resultado de la búsqueda */
    	this.oTable = new CCTable("");
    	this.oTable.setHeaderBackground(Color.LIGHTGRAY);
    	this.oTable.setFont(new Font(Font.ARIAL, Font.PLAIN, new Extent(11)));
    	this.oTable.setWidth(new Extent(640));
        
    	ColumnLayoutData cTableLD = new ColumnLayoutData();
    	cTableLD.setAlignment(new Alignment(Alignment.LEFT,Alignment.BOTTOM));
    	this.oTable.setLayoutData(cTableLD);
    	
        
        lTotal_recibo = new CCLabel("TOTAL:");
        tTotal_recibo = new CCTextField();
        tTotal_recibo.setEnabled(false);
        tTotal_recibo.setFont(new Font(Font.ARIAL, Font.PLAIN, new Extent(12, Extent.PX)));

        lTotalBruto = new CCLabel("TOTAL Grabado:");
        tTotalBruto = new CCTextField();
        tTotalBruto.setEnabled(false);
        tTotalBruto.setFont(new Font(Font.ARIAL, Font.PLAIN, new Extent(12, Extent.PX)));
        
        lTotalIVA = new CCLabel("TOTAL IVA:");
        tTotalIVA = new CCTextField();
        tTotalIVA.setEnabled(false);
        tTotalIVA.setFont(new Font(Font.ARIAL, Font.PLAIN, new Extent(12, Extent.PX)));
        
        
        
        /** Cargo los datos en la grilla - Inicialmente esta vacia */
        oModel.setDataList(dataList);
        this.update(oModel);
        
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

        rPersona.add(tPersona);
        rPersona.add(btnPersona);
        
        CCColumn c1 = new CCColumn();
        CCColumn c2 = new CCColumn(); 
        
        c1.add(this.oTable);
        
        c2.add(btnAltaItem);
        c2.add(btnBajaItem);
        c2.add(btnEditItem);
        
        rItems.add(c1);
        rItems.add(c2);
        
        rTotales.add(lTotal_recibo);
        rTotales.add(tTotal_recibo);
        
        rTotalBruto.add(lTotalBruto);
        rTotalBruto.add(tTotalBruto);
        
        rTotalIVA.add(lTotalIVA);
        rTotalIVA.add(tTotalIVA);

        
        rNumero.add(tFacturaNumero);
        rNumero.add(new Separator());
        rNumero.add(lFacturaTipo);

        
        /* ---------------------------------------------- */
        
        cLabels.add(lReciboNumero);
        cLabels.add(lPersona);
        cLabels.add(lVacio);
        cLabels.add(lVacio1);
        cLabels.add(lVacio2);
        cLabels.add(lLeyenda);
        
        /* ---------------------------------------------- */
        
        cTexts.add(rNumero);
        cTexts.add(rPersona);
        cTexts.add(tIVA);
        cTexts.add(tCUIT);
        cTexts.add(tDomicilio);
        cTexts.add(taLeyenda);
        
        /* ---------------------------------------------- */
        // La tabla la voy a colocar en una FILA para poder agregar los botones
        cpPrincipal.add(rItems);
        cpPrincipal.add(rTotalBruto);
        cpPrincipal.add(rTotalIVA);
        cpPrincipal.add(rTotales);
        
        rMensaje.add(lMensaje);
        
        /* ---------------------------------------------- */        
//        this.rBotones.add(new Separator());
//        this.rBotones.add(btnPrint);
        /* ---------------------------------------------- */
        
        ApplicationInstance.getActive().setFocusedComponent(btnPersona);
        
    }
	
	
    @SuppressWarnings({ "deprecation", "static-access", "unchecked" })
	public void doInsert() {
    	
    	/**
    	 * Valido los datos - INTEGRIDAD
    	 * --------------------------------
    	 * 1º Validación de datos numericos
    	 * 2º Se hayan seleccionado una PERSONA
    	 * 3º Que el número de recibo no este duplicado ... ojo que si hay mas de un facturero, no puedo validar
    	 * 	  solo con el número, sino que necesito también la responsabilidad social o factura !!!
    	 */ 
    	
    	// valido que el total del recibo sea positivo, > 0.
    	if (total_recibo <= 0) {
    		ApplicationInstance.getActive().setFocusedComponent(tTotal_recibo);
            //throw new ValidacionException("Debe seleccionar una PROPIEDAD o un INQUILINO para continuar.");
        	new FWWindowPaneMensajes("El TOTAL DEL RECIBO debe ser positivo, mayor a cero.", "ERROR");
        	return;
    	}
    	   	
        if (oPersona == null) {
        	ApplicationInstance.getActive().setFocusedComponent(btnPersona);
            //throw new ValidacionException("Debe seleccionar una PROPIEDAD o un INQUILINO para continuar.");
        	new FWWindowPaneMensajes("Debe seleccionar una PERSONA para continuar.", "ERROR");
        	return;
        }        
    	
    	// Levanto los valores ingresados
    	FWUsuario oFWUsuario = ((FWApplicationInstancePrincipal) this.getApplicationInstance().getActive()).getUsuario();
    	oFactura = new Factura();
    	
    	/**
    	 * Datos que necesito de la PERSONA
    	 * razón_social
    	 * domicilio
    	 * cuit
    	 * iva
    	 * 
    	 */
    	
    	oFactura.setPersona(oPersona);
    	
    	oFactura.setCliente(tPersona.getText());
    	oFactura.setCuitDni(tCUIT.getText());
    	oFactura.setDomicilio(tDomicilio.getText());
    	oFactura.setIdInscripcionIva(oPersona.getIdInscripcionIva());
    	
    	oFactura.setFacturaTipo(Factura.getFacturaTipoPorResponsabilidadIVA(oPersona.getIdInscripcionIva()));
    	    	
    	oFactura.setLeyenda(taLeyenda.getText());
    	
    	oFactura.setUsuario(new Usuario(oFWUsuario.getId(), oFWUsuario.getUsuario(), ""));
    	oFactura.setNumero(new Integer(tFacturaNumero.getText()));
    	
    	oFactura.setAnulada(new Boolean(false)); // Cuando doy de alta siempre esta ANULADA FALSO!!! 
    	
    	/* Tengo que armar los datos de los items */
    	List<FacturaItem> lDatos = this.oModel.getDataList();
    	
    	
    	// Inserto
    	try {
    		FacturaProcesos.save(oFactura, lDatos);   
    		grabo_datos = true;
    	} catch(Exception e) {
    		e.printStackTrace();
    		new FWWindowPaneMensajes("Error inesperado del proceso.", "ERROR");
    		return;
    	}
    	
    	new MessageWindowPane("El proceso de grabación se realizo con éxito");
    	

    	//this.btnPrint.setEnabled(true);
        this.rBotones.add(new Separator());
        this.rBotones.add(btnPrint);
    	
    	
    }
    
    
    public void actionPerformed(ActionEvent ae) {
    	
    	if (ae.getActionCommand().equals("persona")){
    		
            CPPrincipal.abrirVentanaMensaje(new PersonaFindListadoView("Persona", this, (short)3));		
    		
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
    			FacturaItem oItem = (FacturaItem)oModel.getDataList().get(currentRow);    			
    			
    			CPPrincipal.abrirVentanaMensaje(new ItemEditView(this, oItem, currentRow));
    		
    		}
    		
    	} else if (ae.getActionCommand().equals("alta_item")){
    		
    		// Dejo dar de alta un item de una factura en forma manual .... que pasa con el IVA ¿?
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
		
		System.out.println("Cantidad de filas seleccionadas " + selectedIndices.length);
		
		Vector<FacturaItem> vectorDeObjetosABorrar = new Vector<FacturaItem>();
    	for (int i = 0; i < selectedIndices.length; i++) {
			int row = selectedIndices[i];
			
			// Una vez que borra una fila se pierde la posición de la fila seleccionada por que ahora tengo una fila menos ...
			
			/*
			 * Una posibilidad es que guarde una colección con los objetos seleccionados para ser borrados y luego recorra
			 * la colección y uso el método remove con un objeto
			 */
			
			//this.oModel.getDataList().remove(row);
			
			
			FacturaItem oFacturaItem = (FacturaItem)oModel.getDataList().get(row);
			vectorDeObjetosABorrar.add(oFacturaItem);
			
			//this.oTable.remove(row);
			
			//System.out.println("Fila seleccionada: " + row);
    	}
    	
    	for (FacturaItem oFacturaItem: vectorDeObjetosABorrar) {
    		this.oModel.getDataList().remove(oFacturaItem);
    	}
    	
		//int currentRow = this.oTable.getSelectionModel().getMinSelectedIndex();
		//this.oModel.getDataList().remove(currentRow);
				
		update(this.oModel);
    }
    
    
    public void doLimpiar() {
    
     	tPersona.setText("");
     	
     	tIVA.setText("");
        tCUIT.setText("");
        tDomicilio.setText("");
        
        lFacturaTipo.setText("");
        tFacturaNumero.setText("00000000");
    	
		dataList = new Vector<FacturaItem>();
		oModel.setDataList(dataList);
		update(this.oModel);
		
    }
    

	@SuppressWarnings("unchecked")
	public void setResultado(Object object) {
		
		//boolean vigentes = chConSaldo.isSelected();
		
		// previo a llenar los datos limpio la grilla primero
		dataList = new Vector<FacturaItem>();
		oModel.setDataList(dataList);
		update(this.oModel);		
		
		// Viene de la busqueda de persona			
		oPersona = (Persona) object;
		
		tPersona.setText(oPersona.getDescripcion());
		tIVA.setText(oPersona.getResponsabilidadIVA());
		tDomicilio.setText(oPersona.getDireccion() + " - " + oPersona.getLocalidad().getDescripcion() );
		tCUIT.setText(oPersona.getCUIT_DNI());
		
        /* Tengo que buscar el próximo número de FACTURA - Según sea A o B */
		
		FacturaDAO oDAO = new FacturaDAO();
		tFacturaNumero.setText("" + (oDAO.findMaxNumeroFactura( Factura.getFacturaTipoPorResponsabilidadIVA(oPersona.getIdInscripcionIva()) ) + 1));
		
		
		if (Factura.getFacturaTipoPorResponsabilidadIVA(oPersona.getIdInscripcionIva()) == Factura.facturaTipoA ) {			
			lFacturaTipo.setText("Factura A");			
		} else {
			lFacturaTipo.setText("Factura B");
		}
		
		
		/* 
		 * levanto todos los movimientos de contrato_novedad_factura de esta persona
		 * No tengo que ver todos ... solo lo vencido y no liquidado
		 * No hay saldo para estas novedades ya que se facturan por el 100% del valor
		 * ya esta calculado el IVA de cada uno de estos movimientos, los muestro como movimientos independientes pero al
		 * momento de imprimirlo lo agrupo y sumo ... (El IVA es lo que ya le desconté al propietario en el RECIBO DE PAGO)
		 */
		/** MAS DE 50 DATOS NO FUNCIONA LA TABLA !!! */
			
		List lNovedades = ContratoNovedadFacturaProcesos.buscarNovedadesFacturaPorPersona(oPersona, (short)0, (short)0);
		oModel.getDataList().addAll(FacturaItemProcesos.convertirLista(lNovedades));
		
		this.update(oModel);
		
		
	}
	
	
    @SuppressWarnings("unchecked")
	public void update(FacturaItemAddModel oModel) {
            	
		// Recorro la tabla para sacar el total del recibo
    	DecimalFormat moneda = new DecimalFormat("$###,##0.00");
    	List<FacturaItem> lDatos = oModel.getDataList();
    	total_recibo = 0;
    	    	
    	float iva = 0;
    	float bruto = 0;
    	
    	for(FacturaItem obj: lDatos) {
    		total_recibo += obj.getMonto();
    		
    		if (obj.getIdItemTipo() == ItemTipo.ItemIVA) {
    			iva += obj.getMonto();
    		} else {
    			// mientras no sea un item de IVA suma en el BRUTO
    			bruto += obj.getMonto();
    		}
    		
    	}
    	
    	tTotal_recibo.setText(moneda.format(total_recibo));
    	tTotalBruto.setText(moneda.format(bruto));
    	tTotalIVA.setText(moneda.format(iva));
    	
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
    	
	        String sUri = "PDFPreview?reporte=factura&id_factura=" + this.oFactura.getIdFactura();
	
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