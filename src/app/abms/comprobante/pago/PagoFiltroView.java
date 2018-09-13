package app.abms.comprobante.pago;

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
import nextapp.echo2.app.ImageReference;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.ResourceImageReference;
import nextapp.echo2.app.event.ActionEvent;


import framework.nr.generales.busquedas.FWBusquedas;
import framework.ui.generales.FWWindowPaneMensajes;
import framework.ui.generales.abms.ABMListadoFilterView;
import framework.ui.principal.FWContentPanePrincipal;

@SuppressWarnings("serial")
public class PagoFiltroView extends ABMListadoFilterView implements FWBusquedas {
	
	
    public PagoFiltroView () {
        
    	super();
    	
    	// Seteo el título de la ventana
		this.setTitle("Filtro para el listado de Comprobantes de Pago - Liquidación Pago");
		this.setHeight(new Extent(550, Extent.PX));
		this.setWidth(new Extent(700, Extent.PX));
		
		CPPrincipal = ((FWContentPanePrincipal) ApplicationInstance.getActive().getDefaultWindow().getContent());
		
		oInmueble = null;
		oPropietario = null;
		
		// Agrego los componentes de la pantalla
		crearObjetos();
		renderObjetos();
    }
    
    private FWContentPanePrincipal CPPrincipal;
    
    private CCColumn _cPrincipal, cLabels, cTexts;
	private CCRow rPrincipal, rMensaje, rPropietario, rInmueble, rFechaDesde, rFechaHasta;
	
	private CCLabel lInmueble, lPropietario, lInquilino;
	private CCTextField tInmueble, tPropietario, tInquilino;
	
	private CCLabel lMensaje;
	private CCLabel lNroRecibo, lFechaDesde, lFechaHasta;
	private CCTextField tNroRecibo;
	
	CCDateField dfFecha_desde, dfFecha_hasta;
	CCCheckBox cbFecha_desde, cbFecha_hasta;
	
	private ImageReference iInmueble = new ResourceImageReference("/resources/crystalsvg22x22/actions/gohome.png");
    private CCButton btnInmueble;
    private Inmueble oInmueble;
    
    private ImageReference iPropietario = new ResourceImageReference("/resources/crystalsvg22x22/actions/kontact_contacts.png");
    private CCButton btnPropietario;
    private Persona oPropietario;
	
	
	// Tengo que cargar un combooooo el de localidades (dinámico) -- LISTO
	// Tengo otro combo - el de tipo de documento (estático) -- LISTO
	// Y la fecha de nacimiento ????? calendar -- LISTO
	// Falta el campo observaciones 
	
    private void crearObjetos() {
    	
        _cPrincipal = new CCColumn();
        _cPrincipal.setCellSpacing(new Extent(20));
        _cPrincipal.setInsets(new Insets(10));
        
        rPrincipal = new CCRow();
        rInmueble = new CCRow(22);
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
        
        /*******************************************************************/
        
        
        lNroRecibo = new CCLabel("Nro. de Recibo:",22);        
        tNroRecibo = new CCTextField(100,22,10,true);
        tNroRecibo.setRegex("^[0-9]{1,8}$");
        tNroRecibo.setText("0");
           
        lFechaDesde = new CCLabel("Fecha Desde:",22);
        rFechaDesde = new CCRow(22);
        dfFecha_desde = new CCDateField();
        dfFecha_desde.setEnabled(false);
        cbFecha_desde = new CCCheckBox(" ");
        cbFecha_desde.addActionListener(this);
        cbFecha_desde.setActionCommand("desde");
        
        
        lFechaHasta = new CCLabel("Fecha Hasta:",22);        
        rFechaHasta = new CCRow(22);
        dfFecha_hasta = new CCDateField();
        dfFecha_hasta.setEnabled(false);
        cbFecha_hasta = new CCCheckBox(" ");
        cbFecha_hasta.addActionListener(this);
        cbFecha_hasta.setActionCommand("hasta");
        
        
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
        
        // -------------------------------------------
        
        cLabels.add(lInmueble);
        cLabels.add(lPropietario);
        //cLabels.add(lInquilino);
        
        cTexts.add(rInmueble);
        //cTexts.add(tPropietario);
        cTexts.add(rPropietario);
        
        cLabels.add(lNroRecibo);
        cLabels.add(lFechaDesde);
        cLabels.add(lFechaHasta);
        
        // ------------------------------------------
        
        cTexts.add(tNroRecibo);
        
        rFechaDesde.add(dfFecha_desde);
        rFechaDesde.add(cbFecha_desde);
        cTexts.add(rFechaDesde);
        
        rFechaHasta.add(dfFecha_hasta);
        rFechaHasta.add(cbFecha_hasta);
        cTexts.add(rFechaHasta);    
                
        rMensaje.add(lMensaje);

        ApplicationInstance.getActive().setFocusedComponent(tInmueble);
        
    }
	
    public void actionPerformed(ActionEvent ae) {
    	
    	if (ae.getSource() instanceof CCCheckBox) {
    		
    		CCCheckBox obj = (CCCheckBox)ae.getSource();
    		
    		if (obj.getActionCommand().equals("desde")) {
    			if (obj.isSelected()) dfFecha_desde.setEnabled(true);
        		else dfFecha_desde.setEnabled(false);
    		} else {
    			if (obj.isSelected()) dfFecha_hasta.setEnabled(true);
        		else dfFecha_hasta.setEnabled(false);	
    		}
    		
    	
    	} else {
    		
    		// Es un BOTON el objeto que lanzó el evento
    	
	    	if (ae.getActionCommand().equals("propietario")){
	    		
	            CPPrincipal.abrirVentanaMensaje(new PersonaFindListadoView("Propietario", this, ContratoActor.ActorTipoPropietario));
	    		
	    	} else if (ae.getActionCommand().equals("inmueble")){
	    		
	    		CPPrincipal.abrirVentanaMensaje(new InmuebleFindListadoView(this, false));
	    		
	    	} 
	    	
	    	// Tiro el evento para arriba en la gerarquia de objetos
	    	super.actionPerformed(ae);
	    	
    	}
    	
    }
    
    public void find() {

    	/*
    	 La idea es buscar los datos y pasarselo a la pantalla LISTADO
    	 que tiene que ser una nueva ventana.
    	 La otra posibilidad es pasar los filtros y realizar la consulta
    	 de datos directamente en la otra pantalla 
    	 */
    	
    	/** LLamo a PagoListadoView */
    	int filtroIdPropietario = 0;
    	if (oPropietario != null) filtroIdPropietario =	oPropietario.getIdPersona();
    	
    	int filtroIdPropiedad = 0;
    	if (oInmueble != null) filtroIdPropiedad = oInmueble.getIdInmueble();
    	
    	int filtroNroRecibo = 0;
    	if (!tNroRecibo.getText().equals("")) filtroNroRecibo = Integer.parseInt(tNroRecibo.getText());
    	
    	Date filtroFechaDesde = null;
    	if (dfFecha_desde.isEnabled()) filtroFechaDesde = dfFecha_desde.getSelectedDate().getTime();
    	
    	Date filtroFechaHasta = null;
    	if (dfFecha_hasta.isEnabled()) filtroFechaHasta = dfFecha_hasta.getSelectedDate().getTime();
    	
    	PagoListadoView oPantallaListado = new PagoListadoView(filtroNroRecibo, filtroIdPropietario, filtroIdPropiedad, filtroFechaDesde, filtroFechaHasta);
    	((FWContentPanePrincipal) ApplicationInstance.getActive().getDefaultWindow().getContent()).abrirVentana(oPantallaListado);
    	
    }
    
    public void setResultado(Object object) {
		
		if (object instanceof Persona) {
			
			tPropietario.setText(((Persona)object).getDescripcion()); 
			oPropietario = (Persona) object;
			
			/* Puede ser propietario de muchas propiedades */
			
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
			
			// La propiedad puede tener muchos inquilinos en distintos contratos
			
		}
		
	}
    
	public void clear() {
		
		tInmueble.setText("");
		tInquilino.setText("");
		tPropietario.setText("");
		tNroRecibo.setText("0");
		
		oInmueble = null;
		oPropietario = null;
		
		dfFecha_desde.setEnabled(false);
		cbFecha_desde.setSelected(false);
		
		dfFecha_hasta.setEnabled(false);
		cbFecha_hasta.setSelected(false);		
		
	}
          
}