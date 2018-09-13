package app.abms.comprobante.pago;

import java.util.Date;

import app.busquedas.inmueble.InmuebleFindListadoView;
import app.busquedas.persona.PersonaFindListadoView;
import app.combos.ComboGANANCIAS;
import ccecho2.base.CCColumn;

import ccecho2.base.CCLabel;

import ccecho2.base.CCButton;
import ccecho2.base.CCCheckBox;
import ccecho2.base.CCDateField;

import ccecho2.base.CCRow;
import ccecho2.base.CCTextField;
import ccecho2.complex.ComboList.ComboList;
import ccecho2.complex.ComboList.NoItemSelectedException;
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


/**
 * Voy a filtrar las retenciones hechas a los PROPIETARIOS del Impuesto de Ganancias
 * Son los items de recibo de pago del tipo 10
 * 
 * Filtros:
 * 		PROPIETARIO
 * 		Fecha desde/hasta
 * 		Inscripción en GANANCIA (combo)
 * 		por número de retención --> el id_recibo_pago_item
 * 
 * @author pablo
 *
 */
@SuppressWarnings("serial")
public class GananciaFiltroView extends ABMListadoFilterView implements FWBusquedas {
	
	
    public GananciaFiltroView () {
        
    	super();
    	
    	// Seteo el título de la ventana
		this.setTitle("Filtro para el listado de Retenciones de GANANCIA");
		this.setHeight(new Extent(550, Extent.PX));
		this.setWidth(new Extent(700, Extent.PX));
		
		CPPrincipal = ((FWContentPanePrincipal) ApplicationInstance.getActive().getDefaultWindow().getContent());
		
		oPropietario = null;
		
		// Agrego los componentes de la pantalla
		crearObjetos();
		renderObjetos();
    }
    
    private FWContentPanePrincipal CPPrincipal;
    
    private CCColumn _cPrincipal, cLabels, cTexts;
	private CCRow rPrincipal, rMensaje, rPropietario, rFechaDesde, rFechaHasta;
	
	private CCLabel lPropietario;
	private CCTextField tPropietario;
	
	private CCLabel lMensaje;
	private CCLabel lNroRecibo, lFechaDesde, lFechaHasta, lGANANCIAS;
	private CCTextField tNroRecibo;
	
	CCDateField dfFecha_desde, dfFecha_hasta;
	CCCheckBox cbFecha_desde, cbFecha_hasta;
	
	ComboList cboGANANCIAS;
	
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
        rPropietario = new CCRow(22);
        
        cLabels = new CCColumn();
        cLabels.setCellSpacing(new Extent(10, Extent.PX));
        cLabels.setInsets(new Insets(10));
        
        cTexts = new CCColumn();
        cTexts.setCellSpacing(new Extent(10, Extent.PX));
        cTexts.setInsets(new Insets(10));
                
        /* Configuro el boton del propietario */
        btnPropietario = new CCButton(iPropietario);
        this.btnPropietario.setActionCommand("propietario");
        this.btnPropietario.setToolTipText("Asignar Propietario");
        
        this.btnPropietario.setStyleName(null);
        this.btnPropietario.setInsets(new Insets(10, 0));        
        this.btnPropietario.addActionListener(this);        
        
        /*******************************************************************/       
                
        lPropietario = new CCLabel("Propietario:",22);
        tPropietario = new CCTextField(300,false);
        tPropietario.setEnabled(false);
                
        /*******************************************************************/
        
        
        lNroRecibo = new CCLabel("Nro. Retención:",22);        
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
        
        lGANANCIAS = new CCLabel("GANANCIAS:",22);
        cboGANANCIAS = new ComboGANANCIAS(200,22,110,true,true);
        
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
        
        rPropietario.add(tPropietario);
        rPropietario.add(btnPropietario);
        
        // -------------------------------------------
        
        cLabels.add(lPropietario);
        
        cTexts.add(rPropietario);
        
        cLabels.add(lNroRecibo);
        cLabels.add(lFechaDesde);
        cLabels.add(lFechaHasta);
        cLabels.add(lGANANCIAS);
        
        // ------------------------------------------
        
        cTexts.add(tNroRecibo);
        
        rFechaDesde.add(dfFecha_desde);
        rFechaDesde.add(cbFecha_desde);
        cTexts.add(rFechaDesde);
        
        rFechaHasta.add(dfFecha_hasta);
        rFechaHasta.add(cbFecha_hasta);
        cTexts.add(rFechaHasta);
        
        cTexts.add(cboGANANCIAS);
                
        rMensaje.add(lMensaje);

        ApplicationInstance.getActive().setFocusedComponent(tPropietario);
        
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
    	
    	int filtroIdGanancia = 0;
		try {
			filtroIdGanancia = Integer.parseInt(cboGANANCIAS.getSelectedId());
		} catch (NumberFormatException e) {} 
		catch (NoItemSelectedException e) {}
    	
    	int filtroNroRecibo = 0;
    	if (!tNroRecibo.getText().equals("")) filtroNroRecibo = Integer.parseInt(tNroRecibo.getText());
    	
    	Date filtroFechaDesde = null;
    	if (dfFecha_desde.isEnabled()) filtroFechaDesde = dfFecha_desde.getSelectedDate().getTime();
    	
    	Date filtroFechaHasta = null;
    	if (dfFecha_hasta.isEnabled()) filtroFechaHasta = dfFecha_hasta.getSelectedDate().getTime();
    	
    	GananciaListadoView oPantallaListado = new GananciaListadoView(filtroNroRecibo, filtroIdPropietario, filtroIdGanancia, filtroFechaDesde, filtroFechaHasta);
    	((FWContentPanePrincipal) ApplicationInstance.getActive().getDefaultWindow().getContent()).abrirVentana(oPantallaListado);
    	
    }
    
    public void setResultado(Object object) {
		
		if (object instanceof Persona) {
			
			tPropietario.setText(((Persona)object).getDescripcion()); 
			oPropietario = (Persona) object;
			
			/* Puede ser propietario de muchas propiedades */
			
		}
		
	}
    
	public void clear() {
		
		tPropietario.setText("");
		tNroRecibo.setText("0");
		
		oPropietario = null;
		
		dfFecha_desde.setEnabled(false);
		cbFecha_desde.setSelected(false);
		
		dfFecha_hasta.setEnabled(false);
		cbFecha_hasta.setSelected(false);		
		
	}
          
}