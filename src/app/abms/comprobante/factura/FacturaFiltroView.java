package app.abms.comprobante.factura;

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

/**
 * Pantalla con los filtros para la búsqueda de FACTURAS
 * 
 * Filtros:
 * - Facturas A o B
 * - Persona
 * - Número
 * 
 * 
 * @author pablo
 *
 */

@SuppressWarnings("serial")
public class FacturaFiltroView extends ABMListadoFilterView implements FWBusquedas {
	
	
    public FacturaFiltroView () {
        
    	super();
    	
    	// Seteo el título de la ventana
		this.setTitle("Filtro para el listado de Facturas");
		this.setHeight(new Extent(550, Extent.PX));
		this.setWidth(new Extent(700, Extent.PX));
		
		CPPrincipal = ((FWContentPanePrincipal) ApplicationInstance.getActive().getDefaultWindow().getContent());
		
		oPersona = null;
		
		// Agrego los componentes de la pantalla
		crearObjetos();
		renderObjetos();
    }
    
    private FWContentPanePrincipal CPPrincipal;
    
    private CCColumn _cPrincipal, cLabels, cTexts;
	private CCRow rPrincipal, rMensaje, rPersona, rInmueble, rFechaEmision;
	
	private CCLabel lInmueble, lPersona;
	private CCTextField tInmueble, tPersona;
	
	private CCLabel lMensaje;
	private CCLabel lNroFactura, lFechaEmision;
	private CCTextField tNroFactura;
	
	CCDateField dfFecha_emision;
	CCCheckBox cbFecha_emision;
	
	private ImageReference iInmueble = new ResourceImageReference("/resources/crystalsvg22x22/actions/gohome.png");
    private CCButton btnInmueble;
    private Inmueble oInmueble;
    
    private ImageReference iPersona = new ResourceImageReference("/resources/crystalsvg22x22/actions/kontact_contacts.png");
    private CCButton btnPersona;
    private Persona oPersona;
	

	
    private void crearObjetos() {
    	
        _cPrincipal = new CCColumn();
        _cPrincipal.setCellSpacing(new Extent(20));
        _cPrincipal.setInsets(new Insets(10));
        
        rPrincipal = new CCRow();
        rInmueble = new CCRow(22);
        rPersona = new CCRow(22);
        
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
        btnPersona = new CCButton(iPersona);
        this.btnPersona.setActionCommand("persona");
        this.btnPersona.setToolTipText("Asignar persona como filtro");
        
        this.btnPersona.setStyleName(null);
        this.btnPersona.setInsets(new Insets(10, 0));        
        this.btnPersona.addActionListener(this);        
        
        /*******************************************************************/       
        lInmueble = new CCLabel("Inmueble:",22);
        tInmueble = new CCTextField(300, false);
        tInmueble.setEnabled(false);
                
        lPersona = new CCLabel("Persona:",22);
        tPersona = new CCTextField(300, false);
        tPersona.setEnabled(false);
        
        /*******************************************************************/        
        
        lNroFactura = new CCLabel("Nro. de Factura:",22);        
        tNroFactura = new CCTextField(100,22,10,true);
        tNroFactura.setRegex("^[0-9]{1,8}$");
        tNroFactura.setText("0");
           
        lFechaEmision = new CCLabel("Fecha Emisión:",22);
        rFechaEmision = new CCRow(22);
        dfFecha_emision = new CCDateField();
        dfFecha_emision.setEnabled(false);
        cbFecha_emision = new CCCheckBox(" ");
        cbFecha_emision.addActionListener(this);
        cbFecha_emision.setActionCommand("desde");        
        
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

        rPersona.add(tPersona);
        rPersona.add(btnPersona);
        
        // -------------------------------------------
        
        //cLabels.add(lInmueble);
        cLabels.add(lPersona);
        
        //cTexts.add(rInmueble);
        cTexts.add(rPersona);
        
        cLabels.add(lNroFactura);
        cLabels.add(lFechaEmision);
        
        // ------------------------------------------
        
        cTexts.add(tNroFactura);
        
        rFechaEmision.add(dfFecha_emision);
        rFechaEmision.add(cbFecha_emision);
        cTexts.add(rFechaEmision);
                
        rMensaje.add(lMensaje);

        ApplicationInstance.getActive().setFocusedComponent(tInmueble);
        
    }
	
    public void actionPerformed(ActionEvent ae) {
    	
    	if (ae.getSource() instanceof CCCheckBox) {
    		
    		CCCheckBox obj = (CCCheckBox)ae.getSource();
    		
    		if (obj.isSelected()) dfFecha_emision.setEnabled(true);
        	else dfFecha_emision.setEnabled(false);
    		    	
    	} else {
    		
    		// Es un BOTON el objeto que lanzó el evento
    	
	    	if (ae.getActionCommand().equals("persona")){
	    		
	            CPPrincipal.abrirVentanaMensaje(new PersonaFindListadoView("Persona", this, (short)3));
	    		
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
    	
    	/** LLamo a ContratoListadoView */
    	int filtroIdPersona = 0;
    	if (oPersona != null) filtroIdPersona =	oPersona.getIdPersona();
    	
    	int filtroIdPropiedad = 0;
    	if (oInmueble != null) filtroIdPropiedad = oInmueble.getIdInmueble();
    	
    	int filtroNroFactura = 0;
    	if (!tNroFactura.getText().equals("")) filtroNroFactura = Integer.parseInt(tNroFactura.getText());
    	
    	Date filtroFechaEmision = null;
    	if (dfFecha_emision.isEnabled()) filtroFechaEmision = dfFecha_emision.getSelectedDate().getTime();
    	    	
    	FacturaListadoView oPantallaListado = new FacturaListadoView(filtroNroFactura, filtroIdPersona, filtroIdPropiedad, filtroFechaEmision);
    	((FWContentPanePrincipal) ApplicationInstance.getActive().getDefaultWindow().getContent()).abrirVentana(oPantallaListado);
    	
    }
    
    public void setResultado(Object object) {
		
		if (object instanceof Persona) {
			
			tPersona.setText(((Persona)object).getDescripcion()); 
			oPersona = (Persona) object;
			
			/* Puede ser inquilino de muchas propiedades */
			
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
			//tPropietario.setText(oInmueble.getPropietario().getDescripcion());
			
			// La propiedad puede tener muchos inquilinos en distintos contratos
			
		}
		
	}
    
	public void clear() {
		
		tInmueble.setText("");
		tPersona.setText("");
		tNroFactura.setText("0");
		
		oPersona = null;
		
		dfFecha_emision.setEnabled(false);
		cbFecha_emision.setSelected(false);
		
	}
          
}