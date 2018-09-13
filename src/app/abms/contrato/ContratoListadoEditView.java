package app.abms.contrato;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Set;

import ccecho2.base.CCButton;
import ccecho2.base.CCCheckBox;
import ccecho2.base.CCColumn;
import ccecho2.base.CCDateField;
import ccecho2.base.CCLabel;

import ccecho2.base.CCRow;
import ccecho2.base.CCTextArea;
import ccecho2.base.CCTextField;
import ccecho2.complex.MessageWindowPane;
//import ccecho2.complex.MessageWindowPane;

import echopointng.Separator;

import framework.ui.generales.abms.ABMListadoEditViewExit;
import framework.ui.principal.FWContentPanePrincipal;

import datos.contrato.Contrato;
import datos.contrato.ContratoFacade;
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
public class ContratoListadoEditView extends ABMListadoEditViewExit {
    
    // Imagen del botón para ver la Cuenta Corriente
    private ImageReference CtaCte = new ResourceImageReference("/resources/crystalsvg22x22/actions/view_text.png");
    private ImageReference CtaCte2 = new ResourceImageReference("/resources/crystalsvg22x22/actions/view_text.png");
    
    private ImageReference iPropietario = new ResourceImageReference("/resources/crystalsvg22x22/actions/kontact_contacts.png");
    private CCButton btnPropietario;
    
    // Botón Cuenta Corriente
    private CCButton btnCtaCte;
    private CCButton btnCtaCte2;
    
	private ContratoListadoView listado;
	private Contrato oContrato;

	/**
	 * Falta la MODIFICACION -- por ahora solo modificamos las observaciones del contrato
	 * Falta el BORRADO 
	 */
	
	public ContratoListadoEditView(ContratoListadoView listado, Contrato oContrato) {
		
		super();
		
		// Es la clase que me llamo
		this.listado = listado;
		
		this.oContrato = oContrato;
		
		// Seteo el título de la ventana
		this.setTitle("Modificación de Contratos - Desde Listado - III");
		this.setHeight(new Extent(500, Extent.PX));
		this.setWidth(new Extent(700, Extent.PX));
		
		//CPPrincipal = ((FWContentPanePrincipal) ApplicationInstance.getActive().getDefaultWindow().getContent());
		
		// Agrego los componentes de la pantalla
		crearObjetos();
		asignarDatos();
		renderObjetos();
		
	}
	
	
	private CCColumn c1Principal, cLabels, cTexts;
	private CCRow rPrincipal, rMensaje;
	
	private CCLabel lMensaje;
	private CCLabel lInmueble, lPropietario, lInquilino, lObservaciones, lFecha_desde, lFecha_hasta, lMonto, lFecha_rescision,
					lCuotas, lComision_prop_fija, lComision_prop_porc, lComision_inquilino, lVacio, lComisiones, lComercial;
	
	private CCTextField tInmueble, tPropietario, tInquilino, tMonto, tCuotas, tComision_prop_fija, 
						tComision_prop_porc, tComision_inquilino;
	
	CCDateField dfFecha_desde, dfFecha_hasta, dfFecha_rescision;
	
	private CCCheckBox chComercial;
	
	private CCTextArea tObservaciones;
	private CCRow rPropietario;
	
    private void crearObjetos() {
    	
    	//System.out.println("ContratoListadoEditView.crearObjetos");
    	
        c1Principal = new CCColumn();
        c1Principal.setCellSpacing(new Extent(20));
        c1Principal.setInsets(new Insets(10));
        rPrincipal = new CCRow();

        rMensaje = new CCRow();
        rMensaje.setAlignment(Alignment.ALIGN_CENTER);
        
        cLabels = new CCColumn();
        cLabels.setCellSpacing(new Extent(10));
        cLabels.setInsets(new Insets(10));
        
        cTexts = new CCColumn();
        cTexts.setCellSpacing(new Extent(10));
        cTexts.setInsets(new Insets(10));
        
        //tUsuario.setText(oUsuario.getUsuario());
        //tUsuario.setEnabled(false);
        //tUsuario.setBackground(Color.LIGHTGRAY);
        
        /*
         Ver que datos permito modificar -
         Tener en cuenta si ya forma parte de un contrato vigente/vencido !!!
         */
        
        /* Configuro el boton que muestra los propietarios */
        rPropietario = new CCRow(22);
        
        btnPropietario = new CCButton(iPropietario);
        this.btnPropietario.setActionCommand("propietario");
        this.btnPropietario.setToolTipText("Ver los Propietarios");
        
        this.btnPropietario.setStyleName(null);
        this.btnPropietario.setInsets(new Insets(10, 0));        
        this.btnPropietario.addActionListener(this);        

        /*******************************************************************/
        lInmueble = new CCLabel("Inmueble:",22);
        tInmueble = new CCTextField(300,22,20,false);
        tInmueble.setEnabled(false);
                
        lPropietario = new CCLabel("Propietario:",22);
        tPropietario = new CCTextField(300,false);
        tPropietario.setEnabled(false);
        
        lInquilino = new CCLabel("Inquilino:",22);
        tInquilino = new CCTextField(300,22,20,false);
        tInquilino.setEnabled(false);
        
        lFecha_desde = new CCLabel("Desde:",22);
        dfFecha_desde = new CCDateField(22);
        dfFecha_desde.setEnabled(false);

        lFecha_hasta = new CCLabel("Hasta:",22);
        dfFecha_hasta = new CCDateField(22);
        dfFecha_hasta.setEnabled(false);

        lFecha_rescision = new CCLabel("Rescisión:",22);
        dfFecha_rescision = new CCDateField(22);
        dfFecha_rescision.setEnabled(false);
        
        lMonto = new CCLabel("Monto Total:",22);
        tMonto = new CCTextField(100,22,20,true);
        tMonto.setText("0,00");
        tMonto.setRegex("^(-)?(\\d){1,3}(\\.(\\d){3})*(,\\d{1,2})?$");
        tMonto.setEnabled(false);
        
        lCuotas = new CCLabel("Cant. de Cuotas:",22);
        tCuotas = new CCTextField(50,22,20,true);
        tCuotas.setText("0");
        tCuotas.setRegex("^[0-9]{1,2}$");
        //tCuotas.setRegex("^[0-9]*$"); // Cantidad ilimitada de digitos
        tCuotas.setEnabled(false);
        
        lComercial = new CCLabel("Contrato COMERCIAL",22);
        chComercial = new CCCheckBox("", 22);

   /* ---------------------------------- */
        
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
        
        lObservaciones = new CCLabel("Observaciones:",22*7);
        tObservaciones = new CCTextArea(400,22,7,true);
        
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
        
    	//System.out.println("ContratoListadoEditView.renderObjetos");
    	
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
        
        rPropietario.add(tPropietario);
        rPropietario.add(btnPropietario);
        
        /*
        RowLayoutData cLabelLayoutData = new RowLayoutData();
        cLabelLayoutData.setAlignment(new Alignment(Alignment.LEFT,Alignment.CENTER));
        cLabels.setLayoutData(cLabelLayoutData);
        */
       
        cLabels.add(lInmueble);       
        cLabels.add(lPropietario);
        cLabels.add(lInquilino);
        cLabels.add(lFecha_desde);
        cLabels.add(lFecha_hasta);
        cLabels.add(lFecha_rescision);
        cLabels.add(lMonto);
        cLabels.add(lCuotas);
        cLabels.add(lComercial);
        
        cLabels.add(lComisiones);
        cLabels.add(lComision_prop_fija);
        cLabels.add(lComision_prop_porc);
        cLabels.add(lComision_inquilino);
        
        cLabels.add(lObservaciones);
        
        /* ---------------------------------------------- */
        
       
        cTexts.add(tInmueble);       
        cTexts.add(rPropietario);
        cTexts.add(tInquilino);
        cTexts.add(dfFecha_desde);
        cTexts.add(dfFecha_hasta);
        cTexts.add(dfFecha_rescision);
        cTexts.add(tMonto);
        cTexts.add(tCuotas);
        cTexts.add(chComercial);
        
        cTexts.add(lVacio);
        cTexts.add(tComision_prop_fija);
        cTexts.add(tComision_prop_porc);
        cTexts.add(tComision_inquilino);
        
        cTexts.add(tObservaciones);

        //ApplicationInstance.getActive().setFocusedComponent(tInmueble);
           
    }
    
    
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
    	    	
		dfFecha_desde.getTextField().setText(sdf.format(oContrato.getFechaDesde()));
		dfFecha_hasta.getTextField().setText(sdf.format(oContrato.getFechaHasta()));
		
		try {
			System.out.println("viene la fecha: " + oContrato.getFechaRescision() + "-" + oContrato.getIdContrato());
			dfFecha_rescision.getTextField().setText(sdf.format(oContrato.getFechaRescision()));
		} catch(NullPointerException npe) {
			dfFecha_rescision.getTextField().setText("");
		}
		
		tMonto.setText(moneda.format(oContrato.getMonto()));
		tCuotas.setText(""+oContrato.getCantidadCuota());
		chComercial.setSelected(oContrato.getComercial());
		
		tComision_prop_fija.setText(moneda.format(oContrato.getComisionPropFija()));
		tComision_prop_porc.setText(moneda.format(oContrato.getComisionPropPorc()));
		tComision_inquilino.setText(moneda.format(oContrato.getComisionInquilino()));
		
		tObservaciones.setText(oContrato.getObservaciones());
    	
    }
    
    
    public boolean update() {
        
    	boolean salida = true;
    	
    	// No hay ninguna modificación ¿?
    	// Por ahora podemos modificar los comentarios solamente ¿? y si es un CONTRATO COMERCIAL
    	
    	oContrato.setObservaciones(tObservaciones.getText());
    	oContrato.setComercial(chComercial.isSelected());
    	
    	try {
    		ContratoFacade.update(oContrato);
    		
	    	// Actualizo la tabla
	        listado.ActualizarDatos(false);
	    		
	    } catch(Exception e) {
	    	e.printStackTrace();
	    	salida = false;
	    	new MessageWindowPane(e.getMessage());
	    	//new MessageWindowPane("Se produjo un error en la actualización");
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
        
        } else if (ae.getActionCommand().equals("propietario")) {
    		
    		// Ojo que es la ventana 4 (¿Cual es el máximo de ventanas abiertas simultaneamente?)
    		((FWContentPanePrincipal) ApplicationInstance
        			.getActive().getDefaultWindow().getContent())
        			.abrirVentana(new ContratoPropietarios(oContrato));
        }
    	
    	// Tiro el evento para arriba en la gerarquia de objetos
    	super.actionPerformed(ae);
    	
    }

}