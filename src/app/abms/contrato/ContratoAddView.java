package app.abms.contrato;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import app.beans.Cuota;
import app.beans.Utiles;
import app.busquedas.inmueble.InmuebleFindListadoView;
import app.busquedas.persona.PersonaFindListadoView;

import ccecho2.base.CCButton;
import ccecho2.base.CCColumn;
import ccecho2.base.CCDateField;
import ccecho2.base.CCLabel;

import ccecho2.base.CCRow;
import ccecho2.base.CCTextArea;
import ccecho2.base.CCTextField;
//import ccecho2.complex.MessageWindowPane;

import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.ApplicationInstance;

import nextapp.echo2.app.Color;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Font;
import nextapp.echo2.app.ImageReference;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.ResourceImageReference;

import nextapp.echo2.app.event.ActionEvent;


import datos.contrato.Contrato;
import datos.contrato.ContratoProcesos;
import datos.facturero.Facturero;
//import datos.contrato_actor.ContratoActor;
import datos.inmueble.Inmueble;

import datos.persona.Persona;


import framework.nr.generales.busquedas.FWBusquedas;
import framework.ui.generales.FWWindowPaneMensajes;
import framework.ui.generales.abms.ABMAddView;
import framework.ui.generales.exception.ReglasDeNegocioException;
import framework.ui.generales.exception.ValidacionException;
import framework.ui.principal.FWContentPanePrincipal;

@SuppressWarnings("serial")
public class ContratoAddView extends ABMAddView implements FWBusquedas, iContratoCuotas {
	
	
    private ImageReference iInmueble = new ResourceImageReference("/resources/crystalsvg22x22/actions/gohome.png");
    private CCButton btnInmueble;
    private Inmueble oInmueble;
    
    private ImageReference iInquilino = new ResourceImageReference("/resources/crystalsvg22x22/actions/kontact_contacts.png");
    private CCButton btnInquilino;
    private Persona oInquilino;

    private ImageReference iCuotas = new ResourceImageReference("/resources/crystalsvg22x22/actions/math_matrix.png");
    private CCButton btnCuotas;
    private List<Cuota> vCuotas = new Vector<Cuota>();
    

    private CCColumn _cPrincipal, cLabels, cTexts;
	private CCRow rPrincipal, rMensaje, rInquilino, rInmueble, rCuotas, rComisionPropietario;
	
	private CCLabel lMensaje;
	private CCLabel lInmueble, lPropietario, lInquilino, lObservaciones, lFecha_desde, lFecha_hasta, lMonto, 
					lCuotas, lComision_prop_fija, lComision_prop_porc, lComision_inquilino, lVacio, lComisiones, lComisionPropietario;
	
	private CCTextField tInmueble, tPropietario, tInquilino, tMonto, tCuotas, tComision_prop_fija, 
						tComision_prop_porc, tComision_inquilino;
	
	CCDateField dfFecha_desde, dfFecha_hasta;
	
	private CCTextArea tObservaciones;
	
    private FWContentPanePrincipal CPPrincipal;

	
    public ContratoAddView () {
        
    	super();
    	
    	// Seteo el título de la ventana
		this.setTitle("Alta de Contratos de Alquiler");
		this.setHeight(new Extent(550, Extent.PX));
		this.setWidth(new Extent(650, Extent.PX));
		
		CPPrincipal = ((FWContentPanePrincipal) ApplicationInstance.getActive().getDefaultWindow().getContent());
		
		// Agrego los componentes de la pantalla
		crearObjetos();
		renderObjetos();
    }
        
	
	/* FALTA
	 * 1 Monto - Cantidad de cuotas - 								LISTO
	 * 2 Comisión Propietario (Fija o Porcentaje) 					LISTO
	 * 3 Comisión Inquilino (lo que se cobra por el contrato) 		LISTO
	 * 4 Faltas las pantallas hijas									LISTO
	 * 5 Metodo grabar 												LISTO
	 * 6 Validaciones de datos
	 * 7 Boton Cancelar - tiene que limpiar los datos ingresados
	 * 8 Ver los impuestos ....
	 * 
	 * Expresiones regulares!!!
	 */
	
    private void crearObjetos() {
    	
        _cPrincipal = new CCColumn();
        _cPrincipal.setCellSpacing(new Extent(20));
        _cPrincipal.setInsets(new Insets(10));
        
        rPrincipal = new CCRow();        
        rInmueble = new CCRow(22);
        rInquilino = new CCRow(22);
        rCuotas = new CCRow(22);
        rComisionPropietario = new CCRow(22);
        
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

        /* Configuro el boton de Cuotas */
        btnCuotas = new CCButton(iCuotas);
        this.btnCuotas.setActionCommand("cuotas");
        this.btnCuotas.setToolTipText("Personalizar cuotas mensuales");
        
        this.btnCuotas.setStyleName(null);
        this.btnCuotas.setInsets(new Insets(10, 0));        
        this.btnCuotas.addActionListener(this);

        
        
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
        
        lFecha_desde = new CCLabel("Desde:",22);
        dfFecha_desde = new CCDateField();
        dfFecha_desde.getTextField().setEnabled(false);

        lFecha_hasta = new CCLabel("Hasta:",22);
        dfFecha_hasta = new CCDateField();
        dfFecha_hasta.getTextField().setEnabled(false);
        
        lMonto = new CCLabel("Monto Total:",22);
        tMonto = new CCTextField(100,22,20,true);
        tMonto.setText("0,00");
        tMonto.setRegex("^(-)?(\\d){1,3}(\\.(\\d){3})*(,\\d{1,2})?$");
        
        lCuotas = new CCLabel("Cant. de Cuotas:",22);
        tCuotas = new CCTextField(50,true);
        tCuotas.setText("0");
        tCuotas.setRegex("^[0-9]{1,2}$");
        //tCuotas.setRegex("^[0-9]*$"); // Cantidad ilimitada de digitos

        lComisiones = new CCLabel("Comisiones:",22);
        lComisiones.setFont(new Font(Font.VERDANA, Font.BOLD, new Extent(12, Extent.PX)));
        lVacio = new CCLabel("",22);
        
        lComision_prop_fija = new CCLabel("Propietario (fija $):",22);
        tComision_prop_fija = new CCTextField(100,22,20,true);
        tComision_prop_fija.setRegex("^(-)?(\\d){1,3}(\\.(\\d){3})*(,\\d{1,2})?$");
        tComision_prop_fija.setText("0");

        lComision_prop_porc = new CCLabel("Propietario (%):",22);
        tComision_prop_porc = new CCTextField(100,true);
        tComision_prop_porc.setText("0");
        lComisionPropietario = new CCLabel(".  (10% Por defecto)");
        
        lComision_inquilino = new CCLabel("Inquilino ($):",22);
        tComision_inquilino = new CCTextField(100,22,20,true);
        tComision_inquilino.setRegex("^(-)?(\\d){1,3}(\\.(\\d){3})*(,\\d{1,2})?$");
        tComision_inquilino.setText("0");
        
        lObservaciones = new CCLabel("Observaciones:",22*7);
        tObservaciones = new CCTextArea(400,22,7,true);
        
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
        
        rInmueble.add(tInmueble);
        rInmueble.add(btnInmueble);

        rInquilino.add(tInquilino);
        rInquilino.add(btnInquilino);

        rCuotas.add(tCuotas);
        rCuotas.add(btnCuotas);
        
        rComisionPropietario.add(tComision_prop_porc);
        rComisionPropietario.add(lComisionPropietario);
        
        _cPrincipal.add(rMensaje);
        _cPrincipal.add(rBotones);        
                
        cLabels.add(lInmueble);
        cLabels.add(lPropietario);
        cLabels.add(lInquilino);
        cLabels.add(lFecha_desde);
        cLabels.add(lFecha_hasta);
        cLabels.add(lMonto);
        cLabels.add(lCuotas);
        
        cLabels.add(lComisiones);
        cLabels.add(lComision_prop_fija);
        cLabels.add(lComision_prop_porc);
        cLabels.add(lComision_inquilino);
        
        cLabels.add(lObservaciones);
        
        /* ---------------------------------------------- */
        
        cTexts.add(rInmueble);
        cTexts.add(tPropietario);
        cTexts.add(rInquilino);
        cTexts.add(dfFecha_desde);
        cTexts.add(dfFecha_hasta);
        cTexts.add(tMonto);
        cTexts.add(rCuotas);
        
        cTexts.add(lVacio);
        cTexts.add(tComision_prop_fija);
        //cTexts.add(tComision_prop_porc);
        cTexts.add(rComisionPropietario);
        cTexts.add(tComision_inquilino);
        
        cTexts.add(tObservaciones);
        
        /* ---------------------------------------------- */
        
        rMensaje.add(lMensaje);

        ApplicationInstance.getActive().setFocusedComponent(btnInmueble);
        
    }
	
	
    @SuppressWarnings("deprecation")
	public boolean insert() throws ValidacionException {

    	boolean salida = true;
    	
    	/**
    	 * Valido los datos - INTEGRIDAD
    	 * 1º Validación de datos numericos
    	 * 2º Se hayan cargados los datos INMUEBLE - INQUILINO
    	 * 2º las fecha desde tiene que ser inferior a la fecha hasta
    	 * 3º la cantidad de cuotas no puede ser superior a la diferencia en meses entre desde y hasta (unidad mínima -- meses)
    	 * 4º 
    	 */ 
    	
        if (oInmueble == null) {
        	ApplicationInstance.getActive().setFocusedComponent(btnInmueble);
            throw new ValidacionException("Debe seleccionar una PROPIEDAD para continuar.");        	
        }
        
        if (oInquilino == null) {
        	ApplicationInstance.getActive().setFocusedComponent(btnInquilino);
            throw new ValidacionException("Debe seleccionar el INQUILINO para dar de alta el contrato.");        	
        }
        
    	double monto = 0;
        try {
        	monto = (double) Utiles.ParseFloat(tMonto.getText());
        } catch (NumberFormatException ex) {
        	//lMensaje.setText("Monto invalido");
        	ApplicationInstance.getActive().setFocusedComponent(tMonto);
            throw new ValidacionException("Monto invalido", ex);
        }
        
        if(monto <= 0) {
    		ApplicationInstance.getActive().setFocusedComponent(tMonto);
            throw new ValidacionException("Monto invalido", null);
    	}
        
    	double comision_inquilino = 0;
        try {
        	comision_inquilino = (double) Utiles.ParseFloat(tComision_inquilino.getText());
        } catch (NumberFormatException ex) {
        	//lMensaje.setText("Monto invalido");
        	ApplicationInstance.getActive().setFocusedComponent(tComision_inquilino);
            throw new ValidacionException("Monto invalido", ex);
        }
        
        
        /********************************************************************/
    	/** Levanto los valores ingresados 									*/
        /********************************************************************/
    	Contrato oContrato = new Contrato();
    	
    	oContrato.setInmueble(oInmueble);
    	
    	/* Tengo que armar los datos */
    	
    	oContrato.setFechaDesde(dfFecha_desde.getSelectedDate().getTime());
    	oContrato.setFechaHasta(dfFecha_hasta.getSelectedDate().getTime());
    	oContrato.setMonto(monto);
    	oContrato.setCantidadCuota(Short.valueOf(tCuotas.getText()));
    	oContrato.setComisionPropFija(Double.valueOf(tComision_prop_fija.getText()));
    	oContrato.setComisionPropPorc(Double.valueOf(tComision_prop_porc.getText()));
    	oContrato.setComisionInquilino(comision_inquilino);
    	oContrato.setObservaciones(tObservaciones.getText());
    	oContrato.setComercial(false);
    	
    	// Por ahora no discrimino el FACTURERO - pgarello 13-01-2011
    	Facturero oFacturero = new Facturero();
    	oFacturero.setIdFacturero(1);
    	oContrato.setFacturero(oFacturero);
    	
    	
    	/* Tengo que armar los datos de las cuotas */
		if (vCuotas.isEmpty()) { 
			
			// Calculo el valor del alquiler
			short cant_cuotas = oContrato.getCantidadCuota();
			double monto_total = oContrato.getMonto();
			double monto_cuota = monto_total / cant_cuotas;
			
			// Calculo la fecha de vencimiento - arranco con la fecha_desde
			Date fecha_vencimiento = oContrato.getFechaDesde();
			Calendar oFecha = Calendar.getInstance();
			
			//oFecha.set(Calendar.DAY_OF_MONTH, Cuota.dia_de_vencimiento);
			
			fecha_vencimiento.setDate(Cuota.dia_de_vencimiento);
			oFecha.setTime(fecha_vencimiento);

			// Calculo el periodo
			short periodo_mes = 0;
			short periodo_anio = 0;

			
			for (short i = 1; i <= cant_cuotas; i++) {
				
				periodo_mes = (short) (oFecha.get(Calendar.MONTH) + 1);
				periodo_anio = (short) oFecha.get(Calendar.YEAR);
				
				Cuota oCuota = new Cuota(i, monto_cuota, periodo_mes, periodo_anio, oFecha.getTime());
				
				vCuotas.add(oCuota);
				
				// Calculo la fecha de vencimiento, le sumo un mes
				oFecha.add(Calendar.MONTH, 1);				
				
			}
			
		}
    	
    	// Inserto
    	try {
    		ContratoProcesos.save(oContrato, oInquilino, vCuotas);    		    		
    	} catch(Exception e) {
    		e.printStackTrace();
    		
    		if (e instanceof ReglasDeNegocioException) {
    			throw new ValidacionException(e.getMessage(), e);
    		}
    		
    		salida = false;
    		
    	}
    	
    	return salida;

    }
    
    public void actionPerformed(ActionEvent ae) {
    	
    	if (ae.getActionCommand().equals("inquilino")){
    		
            CPPrincipal.abrirVentanaMensaje(new PersonaFindListadoView("Inquilino", this, (short)0));
    		
    	} else if (ae.getActionCommand().equals("inmueble")){
    		
    		CPPrincipal.abrirVentanaMensaje(new InmuebleFindListadoView(this, false));
    		
    	} else if (ae.getActionCommand().equals("cuotas")) {
    		
    		// System.out.println("Boton Cuotas");
    		
    		// valido que se hayan cargados los campos: monto total del contrato, cantidad de meses, fecha desde y hasta
    		
    		// llamo a la pantalla de las cuotas
    		// Puede que ya haya ingresado las cuotas => tengo que pasarle la colección directamente 15/03/2012
    		
    		short cant_cuotas = Short.valueOf(tCuotas.getText());    		
    		double monto_total = (double) Utiles.ParseFloat(tMonto.getText());
    		Date fecha_desde = dfFecha_desde.getSelectedDate().getTime();
    		List<Cuota> dataList = this.vCuotas;

    		//System.out.println("CANTIDAD DE CUOTAS " + this.vCuotas.size());
    		
    		CPPrincipal.abrirVentanaMensaje(new ContratoAddCuotasListadoView(cant_cuotas, monto_total, fecha_desde, dataList, this));
    		
    	}
    	
    	// Tiro el evento para arriba en la gerarquia de objetos
    	super.actionPerformed(ae);
    	
    }


	@SuppressWarnings("unchecked")
	public void setResultado(Object object) {
		
		if (object instanceof Persona) {
			
			tInquilino.setText(((Persona)object).getDescripcion()); 
			oInquilino = (Persona) object;
			
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
			
			// Busco las comisiones que se le cobro al propietario por otros contratos
			int filtro_propietario = oInmueble.getPropietario().getIdPersona();
			List<Contrato> lContratos = (ContratoProcesos.findByFilter(false, 0, 0, filtro_propietario, 0, 0, null, null, false, false)).getList();			
			HashMap<Double,Double> hmPorcentajes = new HashMap<Double,Double>();
			for (Contrato oContrato : lContratos) {
				Double porcentaje = oContrato.getComisionPropPorc();
				if (!hmPorcentajes.containsKey(porcentaje)) {
					//System.out.println("PUT" + porcentaje);
					hmPorcentajes.put(porcentaje, porcentaje);
				}
			}
			String sPorcentajes = ". (";
			Iterator iPorcentajes = hmPorcentajes.entrySet().iterator();
			DecimalFormat moneda = new DecimalFormat("#.00");
			boolean primerElemento = true;
			while (iPorcentajes.hasNext()) {
				Map.Entry e = (Map.Entry)iPorcentajes.next();
				if (primerElemento) {
					sPorcentajes += moneda.format(e.getValue()) + "%";
					primerElemento = false;
				} else {
					sPorcentajes += " - " + moneda.format(e.getValue()) + "%";
				}
			}
			sPorcentajes += ")";
			
			lComisionPropietario.setText(sPorcentajes);
			
			tInmueble.setText(oInmueble.getDireccion_completa());
			tPropietario.setText(oInmueble.getPropietario().getDescripcion());
			
		}
	}
	
	public void procesarCuotas(List<Cuota> lCuotas) {
		
		this.vCuotas = lCuotas;
		
		// Obtengo el total de la suma de todas las cuotas
		double total = 0d; 
		for(Cuota oCuota: lCuotas) {
			//System.out.println("VALOR CUOTA "+ oCuota.getValor());
			total += oCuota.getValor();
		}
		
		DecimalFormat moneda = new DecimalFormat("###,##0.00");
		this.tMonto.setText(moneda.format(total));
		
	}

	// Limpio los datos ingresados
	public void doLimpiar() {
		this.vCuotas = new Vector<Cuota>();
	}
	
}