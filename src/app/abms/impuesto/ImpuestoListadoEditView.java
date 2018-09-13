package app.abms.impuesto;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import app.beans.Combo;
import app.beans.Cuota;
import app.beans.Utiles;
import app.combos.ComboGenerico;
import app.combos.ComboImpuesto;
import ccecho2.base.CCButton;
import ccecho2.base.CCColumn;
import ccecho2.base.CCDateField;
import ccecho2.base.CCLabel;

import ccecho2.base.CCRow;
import ccecho2.base.CCTextArea;
import ccecho2.base.CCTextField;

import echopointng.Separator;
import framework.ui.generales.abms.ABMListadoEditViewExit;
import framework.ui.principal.FWContentPanePrincipal;

import datos.contrato.Contrato;
import datos.contrato.ContratoNovedadDTO;
import datos.contrato.ContratoProcesos;

import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.ApplicationInstance;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Font;
import nextapp.echo2.app.ImageReference;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.ResourceImageReference;

import nextapp.echo2.app.event.ActionEvent;

/**
 * Esta pantalla edita todos los datos que hay de un impuesto seleccionado en la pantalla anterior.
 * Alguno de los datos se pueden modificar (siempre y cuando se cumplan ciertas reglas de negocio)
 * Aparte de la información plana del impuesto también se va a poder visualizar todos los recibos 
 * en donde esten imputados pagos sobre el impuesto
 * 
 * @author pablo 13/01/2012
 *
 */
@SuppressWarnings("serial")
public class ImpuestoListadoEditView extends ABMListadoEditViewExit {
    
	
	@SuppressWarnings("unused")
	private ImpuestoListadoView listado;
	private ContratoNovedadDTO oContratoNovedadDTO;

	public ImpuestoListadoEditView(ImpuestoListadoView listado, ContratoNovedadDTO oContratoNovedadDTO) {
		
		super();
		
		// Es la clase que me llamo
		this.listado = listado;
		
		this.oContratoNovedadDTO = oContratoNovedadDTO;
		
		// Seteo el título de la ventana
		this.setTitle("Modificación de Impuestos - Desde Listado - III");
		this.setHeight(new Extent(500, Extent.PX));
		this.setWidth(new Extent(700, Extent.PX));
		
		// Agrego los componentes de la pantalla
		crearObjetos();
		asignarDatos();
		renderObjetos();
		
	}
	
	private ImageReference Pagos = new ResourceImageReference("/resources/crystalsvg22x22/actions/view_text.png");
	private CCButton btnPagos;
	
	private CCColumn cPrincipal, cLabels, cTexts;
	private CCRow rPrincipal, rMensaje, rInmueble, rDetalle, rCalcularTotal;
	
	private ComboGenerico cboCuotas, cboAnios;
	private ComboImpuesto cboImpuesto;
	
	private CCLabel lDetalle;
	private CCLabel lMensaje;
	private CCLabel lInmueble, lPropietario, lInquilino, lObservaciones, lFecha_vencimiento, lMonto_Propietario, 
					lMonto_Inquilino, lMonto_total, lCboCuotas, lCboImpuestos, lImpuestoCuota, lImpuestoAnio, 
					lVacio1, lVacio2, lContrato, lImpuesto;
	
	private CCTextField tInmueble, tPropietario, tInquilino, tMonto_propietario, tMonto_inquilino,  
						tMonto_total, tImpuestoCuota;
	
	CCDateField dfFecha_vencimiento;
	
	private CCTextArea tObservaciones;
	
	private CCButton bntCalcularTotal;
	
	
	
    private void crearObjetos() {
    	
        cPrincipal = new CCColumn();
        cPrincipal.setCellSpacing(new Extent(20));
        cPrincipal.setInsets(new Insets(10));
        
        rPrincipal = new CCRow();
        rInmueble = new CCRow(22);
        rCalcularTotal = new CCRow(22);

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

        /*******************************************************************/
        /* Títulos */
        lVacio1 = new CCLabel("",22);
        lVacio2 = new CCLabel("",22);
        lContrato = new CCLabel("Datos del Contrato:",22);
        lContrato.setFont(new Font(Font.VERDANA, Font.BOLD, new Extent(12, Extent.PX)));

        lImpuesto = new CCLabel("Datos del Impuesto:",22);
        lImpuesto.setFont(new Font(Font.VERDANA, Font.BOLD, new Extent(12, Extent.PX)));

        
        /* Armo el combo de cuotas */
        List<Combo> listaVacia = new ArrayList<Combo>();
        lCboCuotas = new CCLabel("Cuota a imputar:",22);
        cboCuotas = new ComboGenerico(200,22,60,false, listaVacia);
        cboCuotas.setSelectedText(1);
        cboCuotas.setEnabled(false);

        /* Armo el combo de impuestos */
        lCboImpuestos = new CCLabel("Impuesto:",22);
        cboImpuesto = new ComboImpuesto(200,22);           
        
        
        /*******************************************************************/       
        lInmueble = new CCLabel("Inmueble:",22);
        tInmueble = new CCTextField(300, false);
        tInmueble.setEnabled(false);
                
        lPropietario = new CCLabel("Propietario:",22);
        tPropietario = new CCTextField(300,22,20,false);
        tPropietario.setEnabled(false);
        
        lInquilino = new CCLabel("Inquilino:",22);
        tInquilino = new CCTextField(300,22,20,false);
        tInquilino.setEnabled(false);
        
        lFecha_vencimiento = new CCLabel("Vencimiento:",22);
        dfFecha_vencimiento = new CCDateField();
                
        lMonto_Propietario = new CCLabel("Monto Propietario:",22);
        tMonto_propietario = new CCTextField(100,22,20,true);
        tMonto_propietario.setText("0,00");
        tMonto_propietario.setRegex("^(-)?(\\d){1,3}(\\.(\\d){3})*(,\\d{1,2})?$");        
        
        lMonto_Inquilino = new CCLabel("Monto Inquilino:",22);
        tMonto_inquilino = new CCTextField(100,22,20,true);
        tMonto_inquilino.setText("0,00");
        tMonto_inquilino.setRegex("^(-)?(\\d){1,3}(\\.(\\d){3})*(,\\d{1,2})?$");
        
        
        /* Configuro el boton para calcular el total */
        bntCalcularTotal = new CCButton("Calcular");
        this.bntCalcularTotal.setActionCommand("calcular");
        this.bntCalcularTotal.setToolTipText("Calcula el total del Impuesto");
        
        this.bntCalcularTotal.setStyleName(null);
        this.bntCalcularTotal.setInsets(new Insets(10, 0));
        this.bntCalcularTotal.addActionListener(this);        
        
        
        lMonto_total = new CCLabel("Total:",22);
        tMonto_total = new CCTextField(100,true);
        tMonto_total.setText("0,00");
        tMonto_total.setEnabled(false);
        
        lImpuestoCuota = new CCLabel("Impuesto Cuota:",22);
        tImpuestoCuota = new CCTextField(100,22,20,true);
        tImpuestoCuota.setRegex("^(\\d){1,2}$");
        
        lImpuestoAnio = new CCLabel("Impuesto Año:",22);
        //tImpuestoAnio = new CCTextField(100,22,20,true);
        
        // El año en curso, -1, +1
        List<Combo> listaAnios = new ArrayList<Combo>();
        
        Calendar c1 = Calendar.getInstance();
        int anio = c1.get(Calendar.YEAR);
        
        Combo oCombo = new Combo(""+(anio-1), ""+(anio-1), false);
        listaAnios.add(oCombo);
        oCombo = new Combo(""+(anio), ""+(anio), true);
        listaAnios.add(oCombo);
        oCombo = new Combo(""+(anio+1), ""+(anio+1), false);
        listaAnios.add(oCombo);
        
        cboAnios = new ComboGenerico(100,22,60,false, listaAnios);
        
        
        lObservaciones = new CCLabel("Observaciones:",22*7);
        tObservaciones = new CCTextArea(400,22,7,true);
        
        /*******************************************************************/
        
//        rBotones = new CCRow();
//        rBotones.setInsets(new Insets(10));
//        rBotones.setAlignment(Alignment.ALIGN_CENTER);

        this.btnPagos = new CCButton(this.Pagos);
        this.btnPagos.setActionCommand("pagos");
        this.btnPagos.setToolTipText("Ver la Cuenta Corriente - (Propietarios)");
        this.btnPagos.addActionListener(this);
        
        rDetalle = new CCRow();
        lDetalle = new CCLabel("En esta pantalla se visualizan todos los datos asociados al impuesto seleccionado.");
        lDetalle.setForeground(Color.DARKGRAY);
        
        lMensaje = new CCLabel();
        lMensaje.setForeground(Color.RED);

    }
    
    private void renderObjetos() {
        
    	// Agrego la columna al ContentPane Principal
    	cpPrincipal.add(cPrincipal);
    	
    	cPrincipal.add(rDetalle);
        cPrincipal.add(rPrincipal);
        cPrincipal.add(rMensaje);
        //cPrincipal.add(rBotones);
        
    	this.rBotones.add(new Separator());
        this.rBotones.add(this.btnPagos);
        
        rPrincipal.add(cLabels);
        rPrincipal.add(cTexts);
        
        rInmueble.add(tInmueble);
        //rInmueble.add(btnInmueble);
        
        rCalcularTotal.add(tMonto_total);
        //rCalcularTotal.add(bntCalcularTotal);
        
        /* ---------------------------------------------- */
        
        cLabels.add(lContrato);
        cLabels.add(lInmueble);
        cLabels.add(lPropietario);
        cLabels.add(lInquilino);        
        cLabels.add(lCboCuotas);
        
        cLabels.add(lImpuesto);
        cLabels.add(lMonto_Propietario);
        cLabels.add(lMonto_Inquilino);
        cLabels.add(lMonto_total);
        cLabels.add(lFecha_vencimiento);
        
        cLabels.add(lCboImpuestos);
        cLabels.add(lImpuestoCuota);
        cLabels.add(lImpuestoAnio);
        
        cLabels.add(lObservaciones);
        
        /* ---------------------------------------------- */
        
        cTexts.add(lVacio1);
        cTexts.add(rInmueble);
        cTexts.add(tPropietario);
        cTexts.add(tInquilino);        
        cTexts.add(cboCuotas);
        
        cTexts.add(lVacio2);
        cTexts.add(tMonto_propietario);
        cTexts.add(tMonto_inquilino);
        cTexts.add(rCalcularTotal);
        cTexts.add(dfFecha_vencimiento);
        
        cTexts.add(cboImpuesto);
        cTexts.add(tImpuestoCuota);
        //cTexts.add(tImpuestoAnio);
        cTexts.add(cboAnios);
        
        cTexts.add(tObservaciones);
        
        /* ---------------------------------------------- */
        
        rMensaje.add(lMensaje);
        rDetalle.add(lDetalle);

        ApplicationInstance.getActive().setFocusedComponent(tMonto_propietario);
        
    }
    
    
    private void asignarDatos() {
    
    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    	DecimalFormat moneda = new DecimalFormat("###,##0.00");
    	
        tInmueble.setText(	oContratoNovedadDTO.getContrato().getInmueble().getDireccion_completa() + 
        					" (" + 
        					oContratoNovedadDTO.getContrato().getInmueble().getLocalidad().getDescripcion() + 
        					")");
        
        tPropietario.setText(oContratoNovedadDTO.getContrato().getInmueble().getPropietario().getDescripcion());
        tInquilino.setText(oContratoNovedadDTO.getContrato().getInquilino().getDescripcion());
        
        tMonto_propietario.setText(moneda.format(oContratoNovedadDTO.getMonto_propietario() * -1));
        tMonto_inquilino.setText(moneda.format(oContratoNovedadDTO.getMonto_inquilino()));
        tMonto_total.setText(moneda.format(oContratoNovedadDTO.getMonto_inquilino() + (oContratoNovedadDTO.getMonto_propietario() * -1) ));
        
        tObservaciones.setText(oContratoNovedadDTO.getObservaciones());
        
        tImpuestoCuota.setText(""+oContratoNovedadDTO.getImpuestoCuota());
        cboAnios.setSelectedText(""+oContratoNovedadDTO.getImpuestoAnio());
        dfFecha_vencimiento.getTextField().setText(sdf.format(oContratoNovedadDTO.getFechaVencimiento()));
        
        /* Tengo que recorrer los periodos del contrato a partir de la fecha de hoy para poder
		   seleccionar el período para el impuesto 
		   Los períodos de un contrato ... (mes, año, cuota nro.) 
		   Cargo los datos en un combo */
		
        Contrato oContrato = oContratoNovedadDTO.getContrato();
		List<Cuota> lCuotas = ContratoProcesos.buscarPeriodosContrato(oContrato, true);				
		
		// convierto los datos en una colección de objetos Combo
		List<Combo> lCombo = new ArrayList<Combo>();
		String clave_combo = "";
		for (Cuota oCuota:lCuotas) {
			String descripcion = Utiles.convertirMes(oCuota.getPeriodo_mes()) + " - " + oCuota.getPeriodo_anio() + " (Cuota " +oCuota.getCuota() + ")";
			String valor = oCuota.getPeriodo_mes() + "|" + oCuota.getPeriodo_anio() + "|" + oCuota.getCuota();
			
			boolean valor_por_defecto = false;
			if (oContratoNovedadDTO.getContratoCuota() == oCuota.getCuota() &&
				oContratoNovedadDTO.getPeriodoAnio() == oCuota.getPeriodo_anio() &&
				oContratoNovedadDTO.getPeriodoMes() == oCuota.getPeriodo_mes()) {
				//System.out.println("VALOR POR DEFECTO");
				clave_combo = valor;
				valor_por_defecto = true;
			}
			
			lCombo.add(new Combo(valor, descripcion, valor_por_defecto));
		}
		
		cboCuotas.asignarDatosCombo(lCombo);
		cboCuotas.setSelectedText(clave_combo); // Con esto andubo
		cboCuotas.setEnabled(true);
		
		
		// combo de tipo de impuesto
		cboImpuesto.setSelectedText((int) oContratoNovedadDTO.getImpuestoId());
    	
    }
    
    
    public boolean update() {
        
    	boolean salida = true;
    	
    	// Valido la integridad de los datos

    	
//    	
//
//
//	    	
//    	// Actualizo
//		if (salida) {
//	    	try {
//	    		PersonaFacade.update(oPersona);
//		    		
//		    	// Actualizo la tabla
//		        listado.ActualizarDatos();
//		    		
//		    } catch(Exception e) {
//		    	e.printStackTrace();
//		    	salida = false;
//		    	new MessageWindowPane(e.getMessage());
//		    	//new MessageWindowPane("Se produjo un error en la actualización");
//		    }
//		}
	    	
    	return salida;
        
    }
 
    
    public void actionPerformed(ActionEvent ae) {
    	
    	// Acciones gestionadas localmente
    	if (ae.getActionCommand().equals("exit")){
        	
    		// Debería cerrar todas las ventanas abiertas !!!
    		// este es el código que cierra esta ventana .. lo puedo llamar por la herencia como esta ahora
    		
    		//((FWContentPanePrincipal) ApplicationInstance.getActive().getDefaultWindow().getContent()).cerrarVentana(this);
    		((FWContentPanePrincipal) ApplicationInstance.getActive().getDefaultWindow().getContent()).cerrarTodasLasVentana();
            
        } else if (ae.getActionCommand().equals("cancel")){
    		((FWContentPanePrincipal) ApplicationInstance.getActive().getDefaultWindow().getContent()).cerrarVentana(this);
    		return;
        }
    	
    	// Tiro el evento para arriba en la gerarquia de objetos
    	super.actionPerformed(ae);
    	
    }

}