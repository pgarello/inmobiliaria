package app.abms.comprobante.cobro;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import app.beans.NovedadTipo;
import app.beans.Utiles;
import app.busquedas.inmueble.InmuebleFindListadoView;
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
import nextapp.echo2.app.layout.ColumnLayoutData;
import nextapp.echo2.app.layout.RowLayoutData;

import nextapp.echo2.app.list.ListSelectionModel;

import nextapp.echo2.webcontainer.command.BrowserOpenWindowCommand;


import datos.contrato.Contrato;
import datos.contrato.ContratoFacade;
import datos.contrato_actor.ContratoActor;
import datos.contrato_novedad_cobro.ContratoNovedadCobro;
import datos.contrato_novedad_cobro.ContratoNovedadCobroProcesos;
import datos.inmueble.Inmueble;

import datos.persona.Persona;

import datos.recibo_cobro.*;
import datos.recibo_cobro_item.*;
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
public class CobroAddView extends ABMAddView implements FWBusquedas {
	
// BOTONES
    private ImageReference iInmueble = new ResourceImageReference("/resources/crystalsvg22x22/actions/gohome.png");
    private CCButton btnInmueble;
    private Inmueble oInmueble;
    
    private ImageReference iInquilino = new ResourceImageReference("/resources/crystalsvg22x22/actions/kontact_contacts.png");
    private CCButton btnInquilino;
    private Persona oInquilino;

    
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
	private CCRow rPrincipal, rMensaje, rInquilino, rInmueble, rItems, rTotales;
	
	private CCLabel lMensaje;
	private CCLabel lInmueble, lPropietario, lInquilino, lLeyenda, lTotal_recibo, lFiltro, lReciboNumero;
	
	private CCTextField tInmueble, tPropietario, tInquilino, tTotal_recibo, tReciboNumero;
	private CCTextArea taLeyenda;
	
	private CCCheckBox chConSaldo;
	private ReciboCobro oReciboCobro;
	
	
    private FWContentPanePrincipal CPPrincipal;
    
    // Tabla de objetos items
    public CCTable oTable;
    
    private List<ReciboCobroItem> dataList = new Vector<ReciboCobroItem>();
    CobroItemAddModel oModel = new CobroItemAddModel();

    boolean grabo_datos;
    
    
	
    public CobroAddView () {
        
    	super();
    	
    	// Seteo el título de la ventana
		this.setTitle("Alta de Comprobantes - Liquidación Cobranza");
		this.setHeight(new Extent(550, Extent.PX));
		this.setWidth(new Extent(700, Extent.PX));
		
		this.grabo_datos = false;
		
		CPPrincipal = ((FWContentPanePrincipal) ApplicationInstance.getActive().getDefaultWindow().getContent());
		
		// Agrego los componentes de la pantalla
		crearObjetos();
		renderObjetos();
    }
        
	
	/* FALTA
	 * 1 Selecciono el inquilino (puede tener mas de un contrato)										LISTO 
	 * 2 Busco todos los movimientos pendientes de todos los contratos vigentes contra el inquilino		LISTO
	 * 3 Armo la grilla de items																		LISTO
	 * 4 Selección desde el inmueble																	LISTO
	 * 5 Totales de IVA ¿?
	 * 6 Leyenda del comprobante																		LISTO
	 * 7 Número del comprobante - Se muestra una vez que se (previa confirmación de impresión) graba
	 * 	 Como se si coincide con el impreso el el COMPROBANTE ¿? --> Es editable, como sugerencia
	 * 	 traigo el máximo + 1.																			LISTO
	 * 8 ABM Grilla																						LISTO
	 * 9 Resolver como hago para pagar un alquiler que todavía no vencio ??? 							LISTO
	 * 10 Selección multiple en la tabla																LISTO
	 */
	
    
    //	 Se activa cuando alguien selecciona una fila de la Tabla
//	public ActionListener tablaActionListener = new ActionListener() {
//		public void actionPerformed(ActionEvent e) {
//			
//			//System.out.println("CLICK");
//			
//		}
//	};

    
    
    private void crearObjetos() {
    	
        _cPrincipal = new CCColumn();
        _cPrincipal.setCellSpacing(new Extent(20));
        _cPrincipal.setInsets(new Insets(10));
        
        rPrincipal = new CCRow();        
        rInmueble = new CCRow(22);
        rInquilino = new CCRow(22);
        rItems = new CCRow();
        rTotales = new CCRow(22);
        //rMesAnio = new CCRow(22);
                
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
        
        /* Configuro el boton del Inmueble */
        btnInmueble = new CCButton(iInmueble);
        this.btnInmueble.setActionCommand("inmueble");
        this.btnInmueble.setToolTipText("Asignar Inmueble");        
        this.btnInmueble.setStyleName(null);
        this.btnInmueble.setInsets(new Insets(10, 0));        
        this.btnInmueble.addActionListener(this);
        
        
        /* Configuro el boton del Inquilino */
        btnInquilino = new CCButton(iInquilino);
        this.btnInquilino.setActionCommand("inquilino");
        this.btnInquilino.setToolTipText("Asignar Inquilino");        
        this.btnInquilino.setStyleName(null);
        this.btnInquilino.setInsets(new Insets(10, 0));        
        this.btnInquilino.addActionListener(this);
        

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
        ReciboCobroDAO oDAO = new ReciboCobroDAO();
        tReciboNumero.setText("" + (oDAO.findMaxNumeroRecibo() + 1));
        
        
        /*******************************************************************/
        
        lInmueble = new CCLabel("Inmueble:",22);
        tInmueble = new CCTextField(300, false);
        tInmueble.setEnabled(false);
                
        lPropietario = new CCLabel("Propietario:",22);
        tPropietario = new CCTextField(300,22,20,false);
        tPropietario.setEnabled(false);
        
        lInquilino = new CCLabel("Inquilino:",22);
        tInquilino = new CCTextField(300,false);
        tInquilino.setEnabled(false);

        lLeyenda = new CCLabel("Leyenda:",44);
        taLeyenda = new CCTextArea(400,44,2,true);
        
        lFiltro = new CCLabel("Filtro:",22);
        chConSaldo = new CCCheckBox("Cuotas vigentes", 22);
        chConSaldo.setSelected(true);
        
        /*******************************************************************/
        /* Tabla con el resultado de la búsqueda */
    	this.oTable = new CCTable("");
    	this.oTable.setHeaderBackground(Color.LIGHTGRAY);
    	this.oTable.setFont(new Font(Font.ARIAL, Font.PLAIN, new Extent(11)));
    	this.oTable.setWidth(new Extent(580));
        
    	ColumnLayoutData cTableLD = new ColumnLayoutData();
    	cTableLD.setAlignment(new Alignment(Alignment.LEFT,Alignment.BOTTOM));
    	this.oTable.setLayoutData(cTableLD);
        
    	/* andubo ... pero no pude levantar las filas seleccionadas 05/01/2012
    	ListSelectionModel oSelectionModel = new DefaultListSelectionModel();
    	oSelectionModel.setSelectionMode(ListSelectionModel.MULTIPLE_SELECTION);
    	this.oTable.setSelectionModel(oSelectionModel);
    	*/
		
		// le doy el color a la 1º column
		// this.oTable.setColorColumna1();
    	
    	//this.oTable.addActionListener(tablaActionListener);
        
        lTotal_recibo = new CCLabel("TOTAL:");
        tTotal_recibo = new CCTextField();

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
        
        rInmueble.add(tInmueble);
        rInmueble.add(btnInmueble);

        rInquilino.add(tInquilino);
        rInquilino.add(btnInquilino);

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
        
        /* ---------------------------------------------- */
        
        cTexts.add(tReciboNumero);
        cTexts.add(rInmueble);
        cTexts.add(tPropietario);
        cTexts.add(rInquilino);
        cTexts.add(chConSaldo);
        cTexts.add(taLeyenda);
        
        rTotales.add(lTotal_recibo);
        rTotales.add(tTotal_recibo);
        
        /* ---------------------------------------------- */
        // La tabla la voy a colocar en una FILA para poder agregar los botones
        cpPrincipal.add(rItems);
        cpPrincipal.add(rTotales);
        
        rMensaje.add(lMensaje);        
        
        ApplicationInstance.getActive().setFocusedComponent(btnInmueble);
        
    }
	
	
    @SuppressWarnings({ "deprecation", "static-access", "unchecked" })
	public void doInsert() {
    	
    	if (!this.grabo_datos) {
    	
	    	/**
	    	 * Valido los datos - INTEGRIDAD
	    	 * --------------------------------
	    	 * 1º Validación de datos numericos
	    	 * 2º Se hayan cargados los datos INMUEBLE o INQUILINO
	    	 * 3º Que el número de recibo no este duplicado ... ojo que si hay mas de un facturero, no puedo validar
	    	 * 	  solo con el número, sino que necesito también la responsabilidad social o factura !!!
	    	 */ 
	    	
	        if (oInmueble == null && oInquilino == null) {
	        	ApplicationInstance.getActive().setFocusedComponent(btnInmueble);
	            //throw new ValidacionException("Debe seleccionar una PROPIEDAD o un INQUILINO para continuar.");
	        	new FWWindowPaneMensajes("Debe seleccionar una PROPIEDAD o un INQUILINO para continuar.", "ERROR");
	        	return;
	        }        
	    	
	        /** Completo los ITEMS con el IVA */
	        
	        /*
			 * Agrego el IVA. Tengo que consultar el IVA actual del SISTEMA y aplicarselo al movimiento del tipo COMISION CONTRATO
			 * Comisión Contrato = $ 100 => IVA = $ 21.
			 */
	        
	        List<ReciboCobroItem> lDatos = this.oModel.getDataList();
	        
	        // Ya AGREGUE los items de IVA previamente ???? 11/05/2014
	        // List<ReciboCobroItem> lDatosMasIVA = ReciboCobroItemProcesos.completarItemsCobroConIVA(lDatos);
	        
	        
	    	// Levanto los valores ingresados
	    	FWUsuario oFWUsuario = ((FWApplicationInstancePrincipal) this.getApplicationInstance().getActive()).getUsuario();
	    	oReciboCobro = new ReciboCobro();
	    	
	    	oReciboCobro.setPersona(oInquilino);
	    	oReciboCobro.setLeyenda(taLeyenda.getText());    	
	    	oReciboCobro.setUsuario(new Usuario(oFWUsuario.getId(), oFWUsuario.getUsuario(), ""));
	    	oReciboCobro.setNumero(new Integer(tReciboNumero.getText()));
	    	
	    	/* Tengo que armar los datos de los items */    	
	    	
	    	// Inserto
	    	try { 
	    		ReciboCobroProcesos.save(oReciboCobro, lDatos);
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
	    	
	    	/* ---------------------------------------------- */     
	        this.rBotones.add(new Separator());
	        this.rBotones.add(btnPrint);
	        
	        btnBajaItem.setActionCommand("");
	        btnBajaItem.setToolTipText("Boton deshabilitado");
	        
	        btnAltaItem.setActionCommand("");
	        btnAltaItem.setToolTipText("Boton deshabilitado");
	        
	        btnEditItem.setActionCommand("");
	        btnEditItem.setToolTipText("Boton deshabilitado");
	        
	        btnInquilino.setEnabled(false);
	        btnInmueble.setEnabled(false);
	        
	        /* ---------------------------------------------- */
	    	
	    
	        // Actualizo la pantalla con los datos grabados
//	        oModel.setDataList(new Vector<ReciboCobroItem>());
//	        oModel.getDataList().addAll(lDatosMasIVA);
	        this.update(oModel);
	        
	        this.grabo_datos = true;
    	}
        
    }
    
    
    public void actionPerformed(ActionEvent ae) {
    	
    	if (ae.getActionCommand().equals("inquilino")){
    		
            CPPrincipal.abrirVentanaMensaje(new PersonaFindListadoView("Inquilino", this, ContratoActor.ActorTipoInquilino));
    		
            
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
    			
    		} else {    			    		
    			
    			int currentRow = this.oTable.getSelectionModel().getMinSelectedIndex();
    			ReciboCobroItem oReciboCobroItem = (ReciboCobroItem)oModel.getDataList().get(currentRow);
    			
    			// Valido que el ITEM sea modificable
    			if (oReciboCobroItem.getIdItemTipo() == NovedadTipo.IVA) {
    				CPPrincipal.abrirVentana(new FWWindowPaneMensajes("Error Edición Items","No se puede modificar la fila seleccionada.",(short)1));
    				return;
    			}
    			
    			// Edito
    			CPPrincipal.abrirVentanaMensaje(new ItemEditView(this, oReciboCobroItem, currentRow));
    		
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
		
		System.out.println("Cantidad de filas seleccionadas " + selectedIndices.length);
		
		Vector<ReciboCobroItem> vectorDeObjetosABorrar = new Vector<ReciboCobroItem>();
    	for (int i = 0; i < selectedIndices.length; i++) {
			int row = selectedIndices[i];
			
			// Una vez que borra una fila se pierde la posición de la fila seleccionada por que ahora tengo una fila menos ...
			
			/*
			 * Una posibilidad es que guarde una colección con los objetos seleccionados para ser borrados y luego recorra
			 * la colección y uso el método remove con un objeto
			 */
			
			//this.oModel.getDataList().remove(row);
			
			
			ReciboCobroItem oReciboCobroItem = (ReciboCobroItem)oModel.getDataList().get(row);
			vectorDeObjetosABorrar.add(oReciboCobroItem);
			
			//this.oTable.remove(row);
			
			//System.out.println("Fila seleccionada: " + row);
    	}
    	
    	for (ReciboCobroItem oReciboCobroItem: vectorDeObjetosABorrar) {
    		this.oModel.getDataList().remove(oReciboCobroItem);
    	}
    	
		//int currentRow = this.oTable.getSelectionModel().getMinSelectedIndex();
		//this.oModel.getDataList().remove(currentRow);
				
		update(this.oModel);
    }
    
    
    public void doLimpiar() {
    
    	if (!this.grabo_datos) {
    	
	    	tInmueble.setText("");
	    	tPropietario.setText("");
	    	tInquilino.setText("");
	    	
			dataList = new Vector<ReciboCobroItem>();
			oModel.setDataList(dataList);
			update(this.oModel);
			
    	}
    	
    }
    

	@SuppressWarnings("unchecked")
	public void setResultado(Object object) {
		
		boolean vigentes = chConSaldo.isSelected();
		
		if (object instanceof Persona) {
			
			// previo a llenar los datos limpio la grilla primero
			dataList = new Vector<ReciboCobroItem>();
			oModel.setDataList(dataList);
			update(this.oModel);
			
			
			// Viene de la busqueda de persona			
			tInquilino.setText(((Persona)object).getDescripcion());
			oInquilino = (Persona) object;
			
			// levanto todos los movimientos de contrato_novedad_cobro de esta persona
			List<ContratoNovedadCobro> lNovedades_todos = ContratoNovedadCobroProcesos.buscarNovedadesCobroPorInquilino(oInquilino, false, vigentes);
			
			/** Si no tiene NADA PENDIENTE (con saldo) está reventando - 03/03/2018 => busco sin tener en cuenta el saldo */
			if (lNovedades_todos.size() > 0) {
				Iterator iNovedades = lNovedades_todos.iterator();
				ContratoNovedadCobro oNovedad = (ContratoNovedadCobro)iNovedades.next();
				tInmueble.setText(oNovedad.getContrato().getInmueble().getDireccion_completa());
				tPropietario.setText(oNovedad.getContrato().getInmueble().getPropietario().getDescripcion());
			}
			
			/*
			 * Agrego el IVA. Tengo que consultar el IVA actual del SISTEMA y aplicarselo al movimiento del tipo COMISION CONTRATO
			 * Comisión Contrato = $ 100 => IVA = $ 21.
			 */
			List<ContratoNovedadCobro> lNovedades = ContratoNovedadCobroProcesos.buscarNovedadesCobroPorInquilino(oInquilino, true, vigentes);
			List<ReciboCobroItem> lItems = ReciboCobroItemProcesos.convertirLista(lNovedades);
			
			// Ordeno la lista
			lItems = (List<ReciboCobroItem>) Utiles.OrdenarCollection( lItems, new ReciboCobroItemComparator(""));
			
	        oModel.getDataList().addAll(lItems);
	        this.update(oModel);
	        
		} else if (object instanceof Inmueble) {
			
			// previo a llenar los datos limpio la grilla primero
			dataList = new Vector<ReciboCobroItem>();
			oModel.setDataList(dataList);
			update(this.oModel);
			
			
			oInmueble = (Inmueble)object; 
						
			// Tengo que buscar el contrato donde figura el inmueble que traigo de filtro
			List<Contrato> lContratos = ContratoFacade.findByInmueble(oInmueble, true);
			
			// Ve cuando da error por que la propiedad no tiene ningún contrato vigente
			if (lContratos.size() != 0) {
				Contrato oContrato = lContratos.get(0);

				// Una vez que tengo el inmueble levanto los actores
				@SuppressWarnings("unused")
				Persona oPropietario = null;
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
				List lNovedades = ContratoNovedadCobroProcesos.buscarNovedadesCobroPorContrato(oContrato, true, vigentes);
				
				
				List<ReciboCobroItem> lItems = ReciboCobroItemProcesos.convertirLista(lNovedades);
			
				// Ordeno la lista
				lItems = (List<ReciboCobroItem>) Utiles.OrdenarCollection( lItems, new ReciboCobroItemComparator(""));
								
				// System.out.println("DATOSSSSSSSSSSSSSSSSSSSSSS " + lNovedades.size());
		        oModel.getDataList().addAll(lItems);
		        this.update(oModel);
		        
			} else {
			
				new MessageWindowPane("El inmueble no posee ningún contrato vigente.");
				
			}
			
		}
		
	}
	
	
    @SuppressWarnings("unchecked")
	public void update(CobroItemAddModel oModel) {
    	
		// Recorro la tabla para sacar el total de la
    	DecimalFormat moneda = new DecimalFormat("$###,##0.00");
    	List<ReciboCobroItem> lDatos = oModel.getDataList();
    	
    	// Completo la lista con los datos del IVA
    	List<ReciboCobroItem> lDatosMasIVA = ReciboCobroItemProcesos.completarItemsCobroConIVA(lDatos);
    	
    	oModel.setDataList(new Vector<ReciboCobroItem>());
        oModel.getDataList().addAll(lDatosMasIVA);
    	
        BigDecimal total = new BigDecimal(0.0);
    	for(ReciboCobroItem obj: lDatosMasIVA) {
    		total = total.add(new BigDecimal(obj.getMonto()));
    	}
    	total = total.setScale(2, RoundingMode.HALF_UP);
    	tTotal_recibo.setText(moneda.format(total));
    	
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
    	
        String sUri = "PDFPreview?reporte=comprobanteReciboCobro&id_recibo_cobro=" + this.oReciboCobro.getIdReciboCobro();

        StringBuilder sb = new StringBuilder()
                .append("width=640")
                .append(",height=480")
                .append(",resizable=yes")
                .append(",scrollbars=yes");

        Command oComm = new BrowserOpenWindowCommand(sUri, "Prueba", sb.toString());
        ApplicationInstance.getActive().enqueueCommand(oComm);
    }

       
}