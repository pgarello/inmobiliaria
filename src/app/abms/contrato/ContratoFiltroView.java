package app.abms.contrato;

import java.util.Date;

import app.busquedas.inmueble.InmuebleFindListadoView;
import app.busquedas.persona.PersonaFindListadoView;
import ccecho2.base.CCColumn;

import ccecho2.base.CCLabel;

import ccecho2.base.CCButton;
import ccecho2.base.CCCheckBox;
import ccecho2.base.CCDateField;
import ccecho2.base.CCRow;
import ccecho2.base.CCTextField;
import datos.contrato_actor.ContratoActor;
import datos.inmueble.Inmueble;
import datos.persona.Persona;

import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.ApplicationInstance;

import nextapp.echo2.app.Color;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Font;
import nextapp.echo2.app.ImageReference;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.ResourceImageReference;
import nextapp.echo2.app.event.ActionEvent;


import framework.nr.generales.busquedas.FWBusquedas;
import framework.nr.generales.filtros.FWFiltros;
import framework.ui.generales.FWWindowPaneMensajes;
import framework.ui.generales.abms.ABMListadoFilterView;
import framework.ui.principal.FWContentPanePrincipal;

@SuppressWarnings("serial")
public class ContratoFiltroView extends ABMListadoFilterView implements FWBusquedas, FWFiltros {
	    
    private CCColumn _cPrincipal, cLabels, cTexts;
	
    private CCRow 	rPrincipal, rMensaje, rInquilino, rInmueble, rPropietario, rFechaDesde, rFechaHasta, 
					rFechaDesdeVencimiento, rFechaHastaVencimiento;
	
	private CCLabel lMensaje, lVencimientoCuota, lVencimientoCuota2, lFechaDesdeVencimiento, lFechaHastaVencimiento,
					lVencimientoContrato, lVencimientoContrato2;	
	private CCLabel lInmueble, lPropietario, lInquilino, lVigentes, lFechaDesde, lFechaHasta, lVacio, lComercial, lCuotas;
	
	private CCTextField tInmueble, tPropietario, tInquilino, tCuotas;
	
	private ImageReference iInmueble = new ResourceImageReference("/resources/crystalsvg22x22/actions/gohome.png");
    private CCButton btnInmueble;
    private Inmueble oInmueble;
    
    private ImageReference iInquilino = new ResourceImageReference("/resources/crystalsvg22x22/actions/kontact_contacts.png");
    private CCButton btnInquilino;
    private Persona oInquilino;
    
    private ImageReference iPropietario = new ResourceImageReference("/resources/crystalsvg22x22/actions/kontact_contacts.png");
    private CCButton btnPropietario;
    private Persona oPropietario;
    
    private CCCheckBox chVigentes, chRescindidos, chComercial;
    
    CCDateField dfFecha_desde, dfFecha_hasta, dfFecha_desdeVencimiento, dfFecha_hastaVencimiento;
	CCCheckBox cbFecha_desde, cbFecha_hasta, cbFecha_desdeVencimiento, cbFecha_hastaVencimiento;
	
    private FWContentPanePrincipal CPPrincipal;
	
	/*
	 * Filtros q tiene la pantalla
	 * 1 - Inmueble (me muestra todos los contratos de un inmueble)
	 * 2 - Propietario
	 * 3 - Inquilino
	 */	

	
    public ContratoFiltroView () {
        
    	super();
    	
    	// Seteo el título de la ventana
		this.setTitle("Filtro para el listado de Contratos - I");
		this.setHeight(new Extent(500, Extent.PX));
		this.setWidth(new Extent(800, Extent.PX));
		
		CPPrincipal = ((FWContentPanePrincipal) ApplicationInstance.getActive().getDefaultWindow().getContent());
		
		// Agrego los componentes de la pantalla
		crearObjetos();
		renderObjetos();
		
		//System.out.println("PROBANDO ********************* " + cLabels.get );
		
    }
    
    private void crearObjetos() {
    	
        _cPrincipal = new CCColumn();
        _cPrincipal.setCellSpacing(new Extent(20));
        _cPrincipal.setInsets(new Insets(10));
        
        rPrincipal = new CCRow();
        rInmueble = new CCRow(22);
        rInquilino = new CCRow(22);
        rPropietario = new CCRow(22);
        
        cLabels = new CCColumn();
        cLabels.setCellSpacing(new Extent(10, Extent.PX));
        cLabels.setInsets(new Insets(10));
        
        cTexts = new CCColumn();
        cTexts.setCellSpacing(new Extent(10, Extent.PX));
        cTexts.setInsets(new Insets(10));
        
        
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
        
        /* Configuro el boton del propietario */
        btnPropietario = new CCButton(iPropietario);
        this.btnPropietario.setActionCommand("propietario");
        this.btnPropietario.setToolTipText("Asignar Propietario");
        
        this.btnPropietario.setStyleName(null);
        this.btnPropietario.setInsets(new Insets(10, 0));        
        this.btnPropietario.addActionListener(this);
        
        /*******************************************************************/       
        lInmueble = new CCLabel("Inmueble:",22);
        tInmueble = new CCTextField(300, false);
        tInmueble.setEnabled(false);
                
        lPropietario = new CCLabel("Propietario:",22);
        tPropietario = new CCTextField(300,false);
        tPropietario.setEnabled(false);
        
        lInquilino = new CCLabel("Inquilino:",22);
        tInquilino = new CCTextField(300,false);
        tInquilino.setEnabled(false);
        
        lVigentes = new CCLabel("Filtro:",22);
        chVigentes = new CCCheckBox("Contratos Vigentes", 22);
        lVacio = new CCLabel("",22);
        chRescindidos = new CCCheckBox("Contratos Rescindidos", 22);
        lComercial = new CCLabel("",22);
        chComercial = new CCCheckBox("Contratos Comerciales", 22);

    // BLOQUE VENCIMIENTO CONTRATO
        lVencimientoContrato = new CCLabel("Vencimiento",30);
        lVencimientoContrato.setFont(new Font(Font.VERDANA, Font.BOLD, new Extent(12, Extent.PX)));
        lVencimientoContrato2 = new CCLabel("CONTRATO",30);
        lVencimientoContrato2.setFont(new Font(Font.VERDANA, Font.BOLD, new Extent(12, Extent.PX)));
        
        lFechaDesde = new CCLabel("Vencimiento Desde:",22);
        rFechaDesde = new CCRow(22);
        dfFecha_desde = new CCDateField();
        dfFecha_desde.setEnabled(false);
        cbFecha_desde = new CCCheckBox(" ");
        cbFecha_desde.addActionListener(this);
        cbFecha_desde.setActionCommand("desde");
        
        lFechaHasta = new CCLabel("Vencimiento Hasta:",22);        
        rFechaHasta = new CCRow(22);
        dfFecha_hasta = new CCDateField();
        dfFecha_hasta.setEnabled(false);
        cbFecha_hasta = new CCCheckBox(" ");
        cbFecha_hasta.addActionListener(this);
        cbFecha_hasta.setActionCommand("hasta");
    
        
    // BLOQUE VENCIMIENTO DE CUOTA
        lVencimientoCuota = new CCLabel("Vencimiento",22);
        lVencimientoCuota.setFont(new Font(Font.VERDANA, Font.BOLD, new Extent(12, Extent.PX)));        
        lVencimientoCuota2 = new CCLabel("CUOTAS",22);
        lVencimientoCuota2.setFont(new Font(Font.VERDANA, Font.BOLD, new Extent(12, Extent.PX)));        
        
        lFechaDesdeVencimiento = new CCLabel("Desde:",22);
        lFechaDesdeVencimiento.setTextAlignment(Alignment.ALIGN_CENTER);
        rFechaDesdeVencimiento = new CCRow(22);
        dfFecha_desdeVencimiento = new CCDateField();
        dfFecha_desdeVencimiento.setEnabled(false);
        cbFecha_desdeVencimiento = new CCCheckBox(" ");
        cbFecha_desdeVencimiento.addActionListener(this);
        cbFecha_desdeVencimiento.setActionCommand("desdeV");        
              
        lFechaHastaVencimiento = new CCLabel("Hasta:",22);
        lFechaHastaVencimiento.setTextAlignment(Alignment.ALIGN_CENTER);
        rFechaHastaVencimiento = new CCRow(22);
        dfFecha_hastaVencimiento = new CCDateField();
        dfFecha_hastaVencimiento.setEnabled(false);
        cbFecha_hastaVencimiento = new CCCheckBox(" ");
        cbFecha_hastaVencimiento.addActionListener(this);
        cbFecha_hastaVencimiento.setActionCommand("hastaV");
        
        lCuotas = new CCLabel("Cuota:",22);
        tCuotas = new CCTextField(50,22,1,true);
        tCuotas.setText("0");
        tCuotas.setRegex("^[0-9]{1,2}$");
        
        
        /*******************************************************************/
        rBotones = new CCRow();
        rBotones.setInsets(new Insets(10));
        rBotones.setAlignment(Alignment.ALIGN_CENTER);

        
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
        
        //rPrincipal.setBorder(new Border(1, Color.BLUE,Border.STYLE_DASHED));
        
        _cPrincipal.add(rMensaje);
        _cPrincipal.add(rBotones);        
        
        rInmueble.add(tInmueble);
        rInmueble.add(btnInmueble);

        rPropietario.add(tPropietario);
        rPropietario.add(btnPropietario);
        
        rInquilino.add(tInquilino);
        rInquilino.add(btnInquilino);
        
        cLabels.add(lInmueble);
        cLabels.add(lPropietario);
        cLabels.add(lInquilino);
        cLabels.add(lVigentes);
        cLabels.add(lVacio);
        cLabels.add(lComercial);
        
        cLabels.add(lVencimientoContrato);
        cLabels.add(lFechaDesde);
        cLabels.add(lFechaHasta);
        
        cLabels.add(new CCLabel());
        cLabels.add(lVencimientoCuota);
        cLabels.add(lFechaDesdeVencimiento);
        cLabels.add(lFechaHastaVencimiento);
        cLabels.add(lCuotas);
        
        // -----------------------------------------------------------
        
        cTexts.add(rInmueble);
        cTexts.add(rPropietario);
        cTexts.add(rInquilino);
        cTexts.add(chVigentes);
        cTexts.add(chRescindidos);
        cTexts.add(chComercial);
        
        cTexts.add(lVencimientoContrato2);
        rFechaDesde.add(dfFecha_desde);
        rFechaDesde.add(cbFecha_desde);
        cTexts.add(rFechaDesde);
        
        rFechaHasta.add(dfFecha_hasta);
        rFechaHasta.add(cbFecha_hasta);
        cTexts.add(rFechaHasta);    
        
        cTexts.add(new CCLabel());
        cTexts.add(lVencimientoCuota2);        
        rFechaDesdeVencimiento.add(dfFecha_desdeVencimiento);
        rFechaDesdeVencimiento.add(cbFecha_desdeVencimiento);
        cTexts.add(rFechaDesdeVencimiento);
        
        rFechaHastaVencimiento.add(dfFecha_hastaVencimiento);
        rFechaHastaVencimiento.add(cbFecha_hastaVencimiento);
        cTexts.add(rFechaHastaVencimiento);    
        cTexts.add(tCuotas);
        
        rMensaje.add(lMensaje);

        ApplicationInstance.getActive().setFocusedComponent(tInmueble);
        
    }
	
	
    public void find() {

    	/*
    	 La idea es buscar los datos y pasarselo a la pantalla LISTADO
    	 que tiene que ser una nueva ventana.
    	 La otra posibilidad es pasar los filtros y realizar la consulta
    	 de datos directamente en la otra pantalla 
    	 */    	    	
    	
    	/* Valido los filtros */
    	if ( (dfFecha_desdeVencimiento.isEnabled() && !dfFecha_hastaVencimiento.isEnabled()) || 
    		 (!dfFecha_desdeVencimiento.isEnabled() && dfFecha_hastaVencimiento.isEnabled()) || 
    		 (dfFecha_desdeVencimiento.isEnabled() && dfFecha_hastaVencimiento.isEnabled()) && tCuotas.getText().equals("0") ||
    		 (!dfFecha_desdeVencimiento.isEnabled() && !dfFecha_hastaVencimiento.isEnabled()) && !tCuotas.getText().equals("0") ) {
    		
			// salgo con ERROR
			new FWWindowPaneMensajes("Los filtros de vencimiento de cuotas ingresados son erróneos, modifíquelos", "ERROR");
			return;
    		
    	}
    	
    	
    	/** LLamo a ContratoListadoView */
    	
    	int filtro_inmueble = 0;
    	if (oInmueble != null) filtro_inmueble = oInmueble.getIdInmueble();
    	
    	int filtro_propietario = 0;
    	if (oPropietario != null) filtro_propietario = this.oPropietario.getIdPersona();
    	
    	int filtro_inquilino = 0;
    	if (oInquilino != null) filtro_inquilino = this.oInquilino.getIdPersona();
    	
    	boolean filtro_vigente = false;
    	if (chVigentes.isSelected()) filtro_vigente = true;
    	
    	boolean filtro_rescindido = false;
    	if (chRescindidos.isSelected()) filtro_rescindido = true;
    	
    	boolean filtro_comercial = false;
    	if (chComercial.isSelected()) filtro_comercial = true;
    	
    	Date filtroFechaDesde = null;
    	if (dfFecha_desde.isEnabled()) filtroFechaDesde = dfFecha_desde.getSelectedDate().getTime();
    	
    	Date filtroFechaHasta = null;
    	if (dfFecha_hasta.isEnabled()) filtroFechaHasta = dfFecha_hasta.getSelectedDate().getTime();
    	
    	Date filtroFechaDesdeV = null;
    	if (dfFecha_desdeVencimiento.isEnabled()) filtroFechaDesdeV = dfFecha_desdeVencimiento.getSelectedDate().getTime();
    	
    	Date filtroFechaHastaV = null;
    	if (dfFecha_hastaVencimiento.isEnabled()) filtroFechaHastaV = dfFecha_hastaVencimiento.getSelectedDate().getTime();
    	
    	int filtro_cuotaV = 0;
    	if (!tCuotas.getText().equals("0")) filtro_cuotaV = Integer.parseInt( tCuotas.getText() );
    	
    	
    	ContratoListadoView oPantallaListado = new ContratoListadoView(	filtro_inmueble, filtro_propietario, filtro_inquilino, 
    																	filtro_vigente, this, filtroFechaDesde, filtroFechaHasta, 
    																	filtro_rescindido, filtro_comercial, 
    																	filtroFechaDesdeV, filtroFechaHastaV, filtro_cuotaV);
    	
    	((FWContentPanePrincipal) ApplicationInstance.getActive().getDefaultWindow().getContent()).abrirVentana(oPantallaListado);
    	
    }
    
    private boolean buscaPropietario = false;
    public void actionPerformed(ActionEvent ae) {
    	
    	if (ae.getSource() instanceof CCCheckBox) {
    		
    		CCCheckBox obj = (CCCheckBox)ae.getSource();
    		
    		if (obj.getActionCommand().equals("desde")) {
    			if (obj.isSelected()) dfFecha_desde.setEnabled(true);
        		else dfFecha_desde.setEnabled(false);
    		
    		} else if (obj.getActionCommand().equals("hasta")) {
    			if (obj.isSelected()) dfFecha_hasta.setEnabled(true);
        		else dfFecha_hasta.setEnabled(false);
    		
    		} else if (obj.getActionCommand().equals("desdeV")) {
    			if (obj.isSelected()) dfFecha_desdeVencimiento.setEnabled(true);
        		else dfFecha_desdeVencimiento.setEnabled(false);
    		
    		} else if (obj.getActionCommand().equals("hastaV")) {
    			if (obj.isSelected()) dfFecha_hastaVencimiento.setEnabled(true);
        		else dfFecha_hastaVencimiento.setEnabled(false);    			    		
    		}    		
    		
    	} else  if (ae.getActionCommand().equals("inquilino")){
    		buscaPropietario = false;
            CPPrincipal.abrirVentanaMensaje(new PersonaFindListadoView("Inquilino", this, (short)0));
    		
    	} else if (ae.getActionCommand().equals("propietario")){
        	buscaPropietario = true;
            CPPrincipal.abrirVentanaMensaje(new PersonaFindListadoView("Propietario", this, ContratoActor.ActorTipoPropietario));
    	
    	} else if (ae.getActionCommand().equals("inmueble")){
    		
    		CPPrincipal.abrirVentanaMensaje(new InmuebleFindListadoView(this, false));
    		
    	}
    	
    	// Tiro el evento para arriba en la jerarquía de objetos
    	super.actionPerformed(ae);
    	
    }

	public void setResultado(Object object) {
		
		if (object instanceof Persona) {
			
			if (buscaPropietario) {
				tPropietario.setText(((Persona)object).getDescripcion()); 
				oPropietario = (Persona) object;
				
				tInquilino.setText(""); 
				oInquilino = null;
				
			} else {
				tInquilino.setText(((Persona)object).getDescripcion()); 
				oInquilino = (Persona) object;
				
				tPropietario.setText(""); 
				oPropietario = null;
			}
			
		} else if (object instanceof Inmueble) {
			
			oInmueble = (Inmueble)object;
			
			// Evaluo si el inmueble seleccionado tiene propietario
			try {
				if (oInmueble.getPropietario().getDescripcion().equals("")) {}
			} catch(NullPointerException npe) {
				
				// salgo
				new FWWindowPaneMensajes("El inmueble seleccionado debe tener asignado un PROPIETARIO", "ERROR");
				return;
			}
			
			tInmueble.setText(oInmueble.getDireccion_completa());
			tPropietario.setText(oInmueble.getPropietario().getDescripcion());
			
		}
		
	}
    
	public void clear() {
		
		this.oInmueble = null;
		this.oInquilino = null;
		this.oPropietario = null;
		
		this.chComercial.setSelected(false);
		this.lMensaje.setText("");
		
		tInmueble.setText("");
		tInquilino.setText("");
		tPropietario.setText("");
		
	}

	public void setMensaje(String mensaje) {
		lMensaje.setText(mensaje);	
	}
	      
}