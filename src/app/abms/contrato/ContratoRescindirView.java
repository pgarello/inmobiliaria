package app.abms.contrato;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.regex.Pattern;

import ccecho2.base.CCButton;
import ccecho2.base.CCColumn;
import ccecho2.base.CCDateField;
import ccecho2.base.CCLabel;

import ccecho2.base.CCRow;
import ccecho2.base.CCTextField;
import ccecho2.complex.MessageWindowPane;
//import ccecho2.complex.MessageWindowPane;

import echopointng.Separator;

import framework.ui.generales.FWWindowPaneMensajes;
import framework.ui.generales.abms.ABMListadoEditViewExit;
import framework.ui.generales.exception.ReglasDeNegocioException;
import framework.ui.generales.exception.ValidacionException;
import framework.ui.principal.FWContentPanePrincipal;

import datos.contrato.Contrato;
import datos.contrato.ContratoProcesos;
import datos.contrato_actor.ContratoActor;

import nextapp.echo2.app.Alignment;

import nextapp.echo2.app.ApplicationInstance;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Font;
import nextapp.echo2.app.ImageReference;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.ResourceImageReference;

import nextapp.echo2.app.event.ActionEvent;

@SuppressWarnings("serial")
public class ContratoRescindirView extends ABMListadoEditViewExit {
    
    // Imagen del botón para ver la Cuenta Corriente
    private ImageReference CtaCte = new ResourceImageReference("/resources/crystalsvg22x22/actions/view_text.png");
    private ImageReference CtaCte2 = new ResourceImageReference("/resources/crystalsvg22x22/actions/view_text.png");
    
    // Botón Cuenta Corriente
    private CCButton btnCtaCte;
    private CCButton btnCtaCte2;
    
	private Contrato oContrato;
	
	private FWContentPanePrincipal CPPrincipal;

	/**
	 * Para la RESCINSION voy a cargar la fecha de la misma y el monto que se aplica como INDEMNIZACION
	 * Voy a tener reglas de NEGOCIO. ¿cuales?
	 * ademas de grabar la fecha y la novedad de indemnización
	 * voy a tener que borrar las novedades a partir de la fecha, tanto las de pago como las de cobro.
	 * 
	 * Tengo que validar que ya no esté Rescindido el CONTRATO - Lo hago en esta Pantalla o en la anterior ???
	 */
	
	public ContratoRescindirView(Contrato oContrato) {
		
		super();
			
		this.oContrato = oContrato;
		
		// Seteo el título de la ventana
		this.setTitle("Rescinción de CONTRATOS - II");
		this.setHeight(new Extent(500, Extent.PX));
		this.setWidth(new Extent(700, Extent.PX));
		
		CPPrincipal = ((FWContentPanePrincipal) ApplicationInstance.getActive().getDefaultWindow().getContent());
		
		// Agrego los componentes de la pantalla
		crearObjetos();
		asignarDatos();
		renderObjetos();
		
	}
	
	
	private CCColumn c1Principal, cLabels, cTexts;
	private CCRow rPrincipal, rMensaje, rFechas;
	
	private CCLabel lMensaje;
	private CCLabel lInmueble, lPropietario, lInquilino, lFecha_desde, lMonto, lFecha_rescision, lMontoRescision, 
					lCuotas, lComision_prop_fija, lComision_prop_porc, lComision_inquilino, lVacio, lComisiones;
	
	private CCTextField tInmueble, tPropietario, tInquilino, tMonto, tCuotas, tComision_prop_fija, 
						tComision_prop_porc, tComision_inquilino, tFechaDesde, tFechaHasta, tMontoRescision;
	
	CCDateField dfFecha_rescision;
	
    private void crearObjetos() {
    	
    	System.out.println("ContratoListadoEditView.crearObjetos");
    	
        c1Principal = new CCColumn();
        c1Principal.setCellSpacing(new Extent(20));
        c1Principal.setInsets(new Insets(10));
        rPrincipal = new CCRow();

        rMensaje = new CCRow();
        rMensaje.setAlignment(Alignment.ALIGN_CENTER);
        
        rFechas = new CCRow(22);
        
        cLabels = new CCColumn();
        cLabels.setCellSpacing(new Extent(10));
        cLabels.setInsets(new Insets(10));
        
        cTexts = new CCColumn();
        cTexts.setCellSpacing(new Extent(10));
        cTexts.setInsets(new Insets(10));
        
        /*
         Ver que datos permito modificar -
         Tener en cuenta si ya forma parte de un contrato vigente/vencido !!!
         */

        /*******************************************************************/
        lInmueble = new CCLabel("Inmueble:",22);
        tInmueble = new CCTextField(300,22,20,false);
        tInmueble.setEnabled(false);
                
        lPropietario = new CCLabel("Propietario:",22);
        tPropietario = new CCTextField(300,22,20,false);
        tPropietario.setEnabled(false);
        
        lInquilino = new CCLabel("Inquilino:",22);
        tInquilino = new CCTextField(300,22,20,false);
        tInquilino.setEnabled(false);
        
        lFecha_desde = new CCLabel("Desde/Hasta:",22);
        tFechaDesde = new CCTextField(100,false);
        tFechaDesde.setEnabled(false);
        
        tFechaHasta = new CCTextField(100,false);
        tFechaHasta.setEnabled(false);
        
        rFechas.add(tFechaDesde);
        rFechas.add(new Separator());
        rFechas.add(tFechaHasta);

// Campos editables
        lFecha_rescision = new CCLabel("Rescisión:",22);
        dfFecha_rescision = new CCDateField(22);
        dfFecha_rescision.getTextField().setEnabled(false);
        
        lMontoRescision = new CCLabel("Monto Rescisión:",22);
        tMontoRescision = new CCTextField(100,22,20,true);
        tMontoRescision.setText("0");
        tMontoRescision.setRegex("^(-)?(\\d){1,3}(\\.(\\d){3})*(,\\d{1,2})?$");        
// Fin campos editables
        
        lMonto = new CCLabel("Monto Total:",22);
        tMonto = new CCTextField(100,22,20,true);
        tMonto.setRegex("^(-)?(\\d){1,3}(\\.(\\d){3})*(,\\d{1,2})?$");
        tMonto.setEnabled(false);
        
        lCuotas = new CCLabel("Cant. de Cuotas:",22);
        tCuotas = new CCTextField(50,22,20,true);
        tCuotas.setRegex("^[0-9]{1,2}$");
        tCuotas.setEnabled(false);

        lComisiones = new CCLabel("Comisiones:",22);
        lComisiones.setFont(new Font(Font.VERDANA, Font.BOLD, new Extent(12, Extent.PX)));
        lVacio = new CCLabel("",22);
        
        lComision_prop_fija = new CCLabel("Propietario (fija $):",22);
        tComision_prop_fija = new CCTextField(100,22,20,true);
        tComision_prop_fija.setEnabled(false);

        lComision_prop_porc = new CCLabel("Propietario (%):",22);
        tComision_prop_porc = new CCTextField(100,22,20,true);
        tComision_prop_porc.setText("0");
        tComision_prop_porc.setEnabled(false);
        
        lComision_inquilino = new CCLabel("Inquilino ($):",22);
        tComision_inquilino = new CCTextField(100,22,20,true);
        tComision_inquilino.setEnabled(false);
        
        /*******************************************************************/
        
//        rBotones = new CCRow();
//        rBotones.setInsets(new Insets(5));
//        rBotones.setAlignment(Alignment.ALIGN_CENTER);

        this.btnCtaCte = new CCButton(this.CtaCte);
        this.btnCtaCte.setActionCommand("ctacte");
        this.btnCtaCte.setToolTipText("Ver la Cuenta Corriente - (Inquilino)");
        this.btnCtaCte.addActionListener(this);
        
        this.btnCtaCte2 = new CCButton(this.CtaCte2);
        this.btnCtaCte2.setActionCommand("ctacte2");
        this.btnCtaCte2.setToolTipText("Ver la Cuenta Corriente - (Propietario)");
        this.btnCtaCte2.addActionListener(this);
        
        /*******************************************************************/
        
        lMensaje = new CCLabel();
        lMensaje.setForeground(Color.RED);

    }
    
    private void renderObjetos() {
        
    	// System.out.println("ContratoListadoEditView.renderObjetos");
    	
    	// Agrego la columna al ContentPane Principal
    	cpPrincipal.add(c1Principal);
    	
        c1Principal.add(rPrincipal);
        rPrincipal.add(cLabels);
        rPrincipal.add(cTexts);
        //cPrincipal.add(rMensaje);
        
        this.rBotones.add(new Separator());
        this.rBotones.add(this.btnCtaCte);
        this.rBotones.add(this.btnCtaCte2);
        //cPrincipal.add(rBotones);
       
        cLabels.add(lInmueble);       
        cLabels.add(lPropietario);
        cLabels.add(lInquilino);
        cLabels.add(lFecha_desde);
        
        cLabels.add(lFecha_rescision);        
        cLabels.add(lMontoRescision);
        
        cLabels.add(lMonto);        
        cLabels.add(lCuotas);
        
        cLabels.add(lComisiones);
        cLabels.add(lComision_prop_fija);
        cLabels.add(lComision_prop_porc);
        cLabels.add(lComision_inquilino);
        
        //cLabels.add(lObservaciones);
        
        /* ---------------------------------------------- */
        
       
        cTexts.add(tInmueble);       
        cTexts.add(tPropietario);
        cTexts.add(tInquilino);
        cTexts.add(rFechas);
        
        cTexts.add(dfFecha_rescision);
        cTexts.add(tMontoRescision);
        
        cTexts.add(tMonto);
        cTexts.add(tCuotas);
        
        cTexts.add(lVacio);
        cTexts.add(tComision_prop_fija);
        cTexts.add(tComision_prop_porc);
        cTexts.add(tComision_inquilino);

        //ApplicationInstance.getActive().setFocusedComponent(tInmueble);
           
    }
    
    private boolean ya_rescindido = false;
    private void asignarDatos() {
    
    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    	DecimalFormat moneda = new DecimalFormat("###,##0.00");
    	
    	tInmueble.setText(oContrato.getInmueble().getDireccion_completa());
    	tPropietario.setText(oContrato.getInmueble().getPropietario().getDescripcion());
    	
    	// recorro los actores
    	Set<ContratoActor> actores = oContrato.getContratoActors();
		for(ContratoActor oActor: actores) {
			if (oActor.getId().getIdActorTipo() == ContratoActor.ActorTipoInquilino) {
				tInquilino.setText(oActor.getId().getPersona().getDescripcion());
			}
		}
    	    	
		tFechaDesde.setText(sdf.format(oContrato.getFechaDesde()));
		tFechaHasta.setText(sdf.format(oContrato.getFechaHasta()));
		
		try {
			dfFecha_rescision.getTextField().setText(sdf.format(oContrato.getFechaRescision()));
			ya_rescindido = true;
		} catch(NullPointerException npe) {
			dfFecha_rescision.getTextField().setText("");
		}
		
		tMonto.setText(moneda.format(oContrato.getMonto()));
		tCuotas.setText(""+oContrato.getCantidadCuota());
		
		tComision_prop_fija.setText(moneda.format(oContrato.getComisionPropFija()));
		tComision_prop_porc.setText(moneda.format(oContrato.getComisionPropPorc()));
		tComision_inquilino.setText(moneda.format(oContrato.getComisionInquilino()));
		
		//tObservaciones.setText(oContrato.getObservaciones());
    	
    }
    
    
    public boolean update() {
        
    	boolean salida = false;
    	
    	// Tengo que grabar los datos: Fecha Rescisión y Monto de la misma
    	// Donde pongo las reglas de negocio ???
    	
    	// Validación de datos previo al procesamiento
    	/** 1º hayan ingresado un valor para la fecha */
    	//System.out.println("Fecha " + dfFecha_rescision.getText());
    	boolean validar = true;
    	if (dfFecha_rescision.getText().equals("")) {
    		ApplicationInstance.getActive().setFocusedComponent(dfFecha_rescision);
        	new FWWindowPaneMensajes("Debe ingresar la fecha de Rescisión.", "ERROR");
        	validar = false;
    	}
    	
       	if (ya_rescindido) {
    		ApplicationInstance.getActive().setFocusedComponent(dfFecha_rescision);
        	new FWWindowPaneMensajes("El CONTRATO ya fue rescindido!", "ERROR");
        	validar = false;
    	}
    	
    	/** 2º que la fecha de rescisión este entre fecha desde y hasta */
    	Date fecha_rescision = dfFecha_rescision.getSelectedDate().getTime();
    	Date fecha_desde = oContrato.getFechaDesde();
    	Date fecha_hasta = oContrato.getFechaHasta();
    	
    	if (validar &&
    		(fecha_rescision.before(fecha_desde) || fecha_rescision.after(fecha_hasta))) {
    		ApplicationInstance.getActive().setFocusedComponent(dfFecha_rescision);
        	new FWWindowPaneMensajes("La fecha de Rescisión debe estar entre las fechas DESDE y HASTA del Contrato.", "ERROR");
        	validar = false;
    	}
    	
    	/** Valido el formato del monto */
		boolean campo_valido = Pattern.matches(tMontoRescision.getRegex(), tMontoRescision.getText());
		if (validar && !campo_valido) {
			ApplicationInstance.getActive().setFocusedComponent(tMontoRescision);
        	new FWWindowPaneMensajes("El monto de rescisión no es valido.", "ERROR");
        	validar = false;
		}

		if (validar) {
			try {
				float monto = Float.parseFloat(tMontoRescision.getText());
	    		ContratoProcesos.rescindirContrato(oContrato, monto, fecha_rescision);
	    		salida = true;
	    	} catch(Exception e) {
	    		e.printStackTrace();    		
	    		new FWWindowPaneMensajes(e.getMessage(), "ERROR");	    		
	    	}
		}
    	
    	return salida;
        
    }
 
    
    public void actionPerformed(ActionEvent ae) {
    	
    	if (ae.getActionCommand().equals("ctacte")){
    		
    		// Ojo que es la ventana 4 (¿Cual es el máximo de ventanas abiertas simultaneamente?)
    		((FWContentPanePrincipal) ApplicationInstance
        			.getActive().getDefaultWindow().getContent())
        			.abrirVentana(new ContratoCtaCteView(oContrato));
    		
        } else if (ae.getActionCommand().equals("ctacte2")) {
    		
    		// Ojo que es la ventana 4 (¿Cual es el máximo de ventanas abiertas simultaneamente?)
    		((FWContentPanePrincipal) ApplicationInstance
        			.getActive().getDefaultWindow().getContent())
        			.abrirVentana(new ContratoCtaCteView2(oContrato));
        }
    	
    	// Tiro el evento para arriba en la gerarquia de objetos
    	super.actionPerformed(ae);
    	
    }

}