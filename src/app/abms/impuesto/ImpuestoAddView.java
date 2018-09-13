package app.abms.impuesto;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;


import app.beans.Combo;
import app.beans.Cuota;
import app.beans.NovedadTipo;
import app.beans.Utiles;
import app.busquedas.inmueble.InmuebleFindListadoView;
import app.combos.ComboGenerico;
import app.combos.ComboImpuesto;

import ccecho2.base.CCButton;
import ccecho2.base.CCColumn;
import ccecho2.base.CCDateField;
import ccecho2.base.CCLabel;

import ccecho2.base.CCRow;
import ccecho2.base.CCTextArea;
import ccecho2.base.CCTextField;
import ccecho2.complex.MessageWindowPane;
import ccecho2.complex.ComboList.ComboListItem;
import ccecho2.complex.ComboList.NoItemSelectedException;

import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.ApplicationInstance;

import nextapp.echo2.app.Color;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Font;
import nextapp.echo2.app.ImageReference;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.ListBox;
import nextapp.echo2.app.ResourceImageReference;

import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.list.DefaultListModel;
import nextapp.echo2.app.list.ListSelectionModel;


import datos.SessionFactory;
import datos.contrato.Contrato;
import datos.contrato.ContratoFacade;
import datos.contrato.ContratoProcesos;

import datos.contrato_actor.ContratoActor;
import datos.contrato_novedad_cobro.ContratoNovedadCobroProcesos;
import datos.contrato_novedad_pago.ContratoNovedadPagoProcesos;

import datos.inmueble.Inmueble;

import datos.persona.Persona;

import framework.nr.generales.busquedas.FWBusquedas;
import framework.ui.generales.abms.ABMAddView;
import framework.ui.generales.exception.ReglasDeNegocioException;
import framework.ui.generales.exception.ValidacionException;
import framework.ui.principal.FWContentPanePrincipal;

@SuppressWarnings("serial")
public class ImpuestoAddView extends ABMAddView implements FWBusquedas {
	
/**
 * Para dar de alta un impuesto debo primero seleccionar el contrato sobre el que va a impactar
 * Puedo hacerlo sobre un inmueble, igual que esta en el alta de un recibo 
 * 
 * También lo tengo que asociar a un periodo que es sobre el que va a impactar ¿?
 * El período debe estar dentro de la vigencia del contrato !!!
 * Podría seleccionar de las cuotas que quedan por pagar (alquileres por vencer) a cual impacta el
 * impuesto (puede ser un impuesto anterior a este periodo) --> ver el alta de comprobantes de pago
 * 
 * 
 * Para hacerlo mas genérico vamos a dar de alta NOVEDADES:
 * 1 - IMPUESTOS (se debe especificar quien lo paga si el propietario o el inquilino)
 * 2 - EXPENSAS
 * 3 - VARIOS
 * 
 * Según el tipo de novedad es donde se va a guardar ... 
 * - contrato_novedad_pago --> PROPIETARIO
 * - contrato_novedad_cobro --> INQUILINO
 * - contrato_novedad_factura ¿?
 * 
 * Por ahora solo voy a dar de alta novedades del tipo IMPUESTOS, pero puede haber un AJUSTE o VARIAS
 * 
 * Datos a cargar: 
 * 1 - Contrato
 * 2 - Persona a la que va a ir asociado el comprobante de pago (P.Ej. Propietario)
 * 3 - Cuota del contrato en que impacta --> esta asociado al período ¿?
 * 4 - Periodo (año y mes)
 * 5 - Monto
 * 6 - Tipo de novedad (en este caso es fija: IMPUESTO)
 * 7 - Observaciones
 * 8 - Fecha Vencimiento (Creo que es para los impuestos ¿? es la fecha de vencimiento en que se pago el imp. ¿?)
 * 9 - Quien paga el impuesto (el inquilino, el propietario o ambos)
 * 
 * 
 * Agregar Campos a las tablas de NOVEDADES ¿?
 * Para identificar cualquier impuesto es necesario representar los datos cuota y año.
 * Por ejemplo: TGI cuota 3 año 2011
 * Por ahora esta información se puede incluir en la observaciones ...
 * Tambien sería importante que identifiquemos dentro de la categoría IMPUESTO, los distintos tipos: TGI API LUZ
 * 
 * 3 - 	Ahora por sugerencias de los usuarios, puedo dar de alta el mismo impuesto en varias cuotas (siempre con el mismo monto)
 * 		Tengo que manejar un componente gráfico para poder seleccionar mas de una cuota. (actualmente es un combo)
 * 
 */	
	
	
    private ImageReference iInmueble = new ResourceImageReference("/resources/crystalsvg22x22/actions/gohome.png");
    private CCButton btnInmueble, bntCalcularTotal;
    
    // Datos Temporales
    private Inmueble oInmueble;
    private Contrato oContrato;
    private Persona oInquilino;    

    private CCColumn _cPrincipal, cLabels, cTexts;
	private CCRow rPrincipal, rMensaje, rInmueble, rDetalle, rCalcularTotal;
	
	private ComboGenerico cboCuotas, cboAnios;
	private ComboImpuesto cboImpuesto;
	
	private ListBox cboCuotasNew;
	private ComboListItem[] lCombos;
	
	private CCLabel lDetalle;
	private CCLabel lMensaje;
	private CCLabel lInmueble, lPropietario, lInquilino, lObservaciones, lFecha_vencimiento, lMonto_Propietario, 
					lMonto_Inquilino, lMonto_total, lCboCuotas, lCboImpuestos, lImpuestoCuota, lImpuestoAnio, 
					lVacio1, lVacio2, lVacio4, lContrato, lImpuesto;
	
	private CCTextField tInmueble, tPropietario, tInquilino, tMonto_propietario, tMonto_inquilino,  
						tMonto_total, tImpuestoCuota;
	
	CCDateField dfFecha_vencimiento;
	
	private CCTextArea tObservaciones;
	
    private FWContentPanePrincipal CPPrincipal;

	
    public ImpuestoAddView () {
        
    	super();
    	
    	// Seteo el título de la ventana
		this.setTitle("Alta de Impuestos");
		this.setHeight(new Extent(550, Extent.PX));
		this.setWidth(new Extent(650, Extent.PX));
		
		CPPrincipal = ((FWContentPanePrincipal) ApplicationInstance.getActive().getDefaultWindow().getContent());
		
		// Agrego los componentes de la pantalla
		crearObjetos();
		renderObjetos();
    }
        
	
	/* FALTA PROGRAMACION
	 * 1 - 	Ver ActionListener sobre TextField para que al modificar uno de los montos (propietario o inquilino)
	 * 		automáticamente se actualice el valor del total del impuesto (19/01/2011)
	 */
	
    private void crearObjetos() {
    	
        _cPrincipal = new CCColumn();
        _cPrincipal.setCellSpacing(new Extent(20));
        _cPrincipal.setInsets(new Insets(10));
        
        rPrincipal = new CCRow();        
        rInmueble = new CCRow(22);
        rCalcularTotal = new CCRow(22);
        
        cLabels = new CCColumn();
        cLabels.setCellSpacing(new Extent(10, Extent.PX));
        cLabels.setInsets(new Insets(10));
        
        cTexts = new CCColumn();
        cTexts.setCellSpacing(new Extent(10, Extent.PX));
        cTexts.setInsets(new Insets(10));

        /* Títulos */
        lVacio1 = new CCLabel("",22);
        lVacio2 = new CCLabel("",22);
        
        lVacio4 = new CCLabel("",50);
        
        lContrato = new CCLabel("Datos del Contrato:",22);
        lContrato.setFont(new Font(Font.VERDANA, Font.BOLD, new Extent(12, Extent.PX)));

        lImpuesto = new CCLabel("Datos del Impuesto:",22);
        lImpuesto.setFont(new Font(Font.VERDANA, Font.BOLD, new Extent(12, Extent.PX)));

        
        /* Configuro el boton del Inmueble */
        btnInmueble = new CCButton(iInmueble);
        this.btnInmueble.setActionCommand("inmueble");
        this.btnInmueble.setToolTipText("Asignar Inmueble");
        
        this.btnInmueble.setStyleName(null);
        this.btnInmueble.setInsets(new Insets(10, 0));        
        this.btnInmueble.addActionListener(this);
        
        /* Armo el combo de cuotas */
        List<Combo> listaVacia = new ArrayList<Combo>();
        lCboCuotas = new CCLabel("Cuota a imputar:",22);
        cboCuotas = new ComboGenerico(200,22,60,false, listaVacia);
        cboCuotas.setSelectedText(1);
        cboCuotas.setEnabled(false);
        
cboCuotasNew = new ListBox();
cboCuotasNew.setEnabled(false);
cboCuotasNew.setSelectionMode(ListSelectionModel.MULTIPLE_SELECTION);

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
        
// Responde solamente ante un ENTER 
//        tMonto_propietario.setActionCommand("theCommand");
//        tMonto_propietario.addActionListener(new ActionListener() {
//           public void actionPerformed(ActionEvent e) {
//               System.out.println("And....Action! " + e.getActionCommand());
//           }
//        });
        
        
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
        tImpuestoCuota.setRegex("^(1|2|3|4|5|6|7|8|9|10|11|12)(\\,(1|2|3|4|5|6|7|8|9|10|11|12))*$");
        
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
        rBotones = new CCRow();
        rBotones.setInsets(new Insets(10));
        rBotones.setAlignment(Alignment.ALIGN_CENTER);
        
        /*******************************************************************/
        rMensaje = new CCRow();
        rMensaje.setAlignment(Alignment.ALIGN_CENTER);

        rDetalle = new CCRow();
        lDetalle = new CCLabel("En esta pantalla se dan de alta los impuestos asociados a una propiedad incluida en un Contrato Vigente.");
        lDetalle.setForeground(Color.DARKGRAY);
        
        lMensaje = new CCLabel();
        lMensaje.setForeground(Color.RED);

    }
    
    
    private void renderObjetos() {
        
    	// Agrego las filas al ContentPane Principal
    	cpPrincipal.add(_cPrincipal);

    	_cPrincipal.add(rDetalle);
        _cPrincipal.add(rPrincipal);        
        _cPrincipal.add(rMensaje);
        _cPrincipal.add(rBotones);        
        
        // Configuro la pantalla
        
        rPrincipal.add(cLabels);
        rPrincipal.add(cTexts);
        
        rInmueble.add(tInmueble);
        rInmueble.add(btnInmueble);
        
        rCalcularTotal.add(tMonto_total);
        rCalcularTotal.add(bntCalcularTotal);
        
        /* ---------------------------------------------- */
        
        cLabels.add(lContrato);
        cLabels.add(lInmueble);
        cLabels.add(lPropietario);
        cLabels.add(lInquilino);        
        cLabels.add(lCboCuotas);
        cLabels.add(lVacio4);
        
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
        //cTexts.add(cboCuotas);
        cTexts.add(cboCuotasNew);
        
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

        ApplicationInstance.getActive().setFocusedComponent(btnInmueble);
        
    }
	
	
    @SuppressWarnings("deprecation")
	public boolean insert() throws ValidacionException {

    	boolean salida = true;
    	
    	/**
    	 * Valido los datos - INTEGRIDAD
    	 * 1º Validación de datos numericos
    	 * 2º Se hayan cargados los datos INMUEBLE - INQUILINO
    	 * 3º Selección de un periodo
    	 * 4º carga de datos sobre el impuesto
    	 * 5º unicidad del impuesto
    	 */ 
    	
        if (oInmueble == null) {
        	ApplicationInstance.getActive().setFocusedComponent(btnInmueble);
            throw new ValidacionException("Debe seleccionar una PROPIEDAD para continuar.");        	
        }
                
    	double monto_propietario = 0;
        try {
        	monto_propietario = (double) Utiles.ParseFloat(tMonto_propietario.getText());
        } catch (NumberFormatException ex) {
        	//lMensaje.setText("Monto invalido");
        	ApplicationInstance.getActive().setFocusedComponent(tMonto_propietario);
            throw new ValidacionException("Monto invalido", ex);
        }        
        
        double monto_inquilino = 0;
        try {
        	monto_inquilino = (double) Utiles.ParseFloat(tMonto_inquilino.getText());
        } catch (NumberFormatException ex) {
        	//lMensaje.setText("Monto invalido");
        	ApplicationInstance.getActive().setFocusedComponent(tMonto_inquilino);
            throw new ValidacionException("Monto invalido", ex);
        }
        
        if (monto_propietario == 0 && monto_inquilino == 0) {
        	ApplicationInstance.getActive().setFocusedComponent(tMonto_propietario);
            throw new ValidacionException("El monto del impuesto no puede ser $ 0,00", null);
        } 

        // valido la selección de una cuota (periodo)
//      String valor = "";       
//      try {
//			valor = cboCuotas.getSelectedId();			
//		} catch (NoItemSelectedException e1) {
//			e1.printStackTrace();
//        	ApplicationInstance.getActive().setFocusedComponent(cboCuotas);
//            throw new ValidacionException("Debe seleccionar el período a imputar el impuesto.", null);			
//		}
		
        if (cboCuotasNew.getSelectedIndices().length == 0) {
        	ApplicationInstance.getActive().setFocusedComponent(cboCuotasNew);
        	throw new ValidacionException("Debe seleccionar el período a imputar el impuesto.", null);
        }		
		
        
		// valido la selección de un impuesto
        String impuesto = "";
        try {
			impuesto = cboImpuesto.getSelectedId();
		} catch (NoItemSelectedException e1) {
			e1.printStackTrace();
        	ApplicationInstance.getActive().setFocusedComponent(cboImpuesto);
            throw new ValidacionException("Debe seleccionar el impuesto para continuar.", null);			
		}
		
		// valido q la cantidad de cuotas coincida con la cantidad de periodos ingresados
		String[] cuotas = tImpuestoCuota.getText().split(","); 
        // ingresar los valores separdos por coma, uno por cada periodo seleccionado (VALIDARLO!!!)
        short[] impuesto_cuotas = new short[cuotas.length];
        for(int i_cuota = 0; i_cuota < cuotas.length; i_cuota++) {
        	impuesto_cuotas[i_cuota] = Short.parseShort(cuotas[i_cuota]);
        }
        
        if (cboCuotasNew.getSelectedIndices().length != cuotas.length) {
        	ApplicationInstance.getActive().setFocusedComponent(tImpuestoCuota);
            throw new ValidacionException("La cantidad de cuotas del impuesto debe ser igual a la cantidad de periodos seleccionados.", null);
        }
        
		
		String impuesto_anio = "";
        try {
			impuesto_anio = cboAnios.getSelectedId();
		} catch (NoItemSelectedException e1) {
			e1.printStackTrace();
        	ApplicationInstance.getActive().setFocusedComponent(cboAnios);
            throw new ValidacionException("Debe seleccionar el año del impuesto para continuar.", null);			
		}
		
		// Unicidad del Impuesto
		// Busco si el impuesto ingresado ya se cargo o ingreso
		// ImpuestoProcesos.buscarImpuesto(oContrato, );
		
		
        /********************************************************************/
    	/** Levanto los valores ingresados y grabo							*/
        /********************************************************************/
    	
		Session oSessionH = SessionFactory.currentSession();
		Transaction tx = null;
		
		try {
						
			/** Ahora tengo para grabar sobre varias cuotas - 01/07/2013 */
			int[] indices = cboCuotasNew.getSelectedIndices();
			int indice_cuotas = -1;
			for (int i : indices) {
				
				System.out.println("indices " + i + " - " + lCombos[i].getValue());
				String valor = (String)lCombos[i].getValue();
				indice_cuotas++;
			
				tx = oSessionH.beginTransaction();
			
		        // obtengo los datos del combo del periodo        
		        short periodo_mes = Short.parseShort(valor.substring(0, valor.indexOf("|")));
		        valor = valor.substring(valor.indexOf("|")+1);
		        
		        short periodo_anio = Short.parseShort(valor.substring(0, valor.indexOf("|")));
		        valor = valor.substring(valor.indexOf("|")+1);
		        
		        short cuota = Short.parseShort(valor);
		
		        short id_impuesto = Short.parseShort(impuesto);
		        
		              
		        
		        short impuesto_annio = Short.parseShort(impuesto_anio);		        
		        
		    	/* En función de quien paga determino en donde grabo */
		        
		        if (monto_propietario != 0) {
		        	
		        	// Grabo en LIQUIDACION PAGO --> CONTRATO NOVEDAD PAGO	        		        	
					
		        	ContratoNovedadPagoProcesos.grabarNovedad(	oContrato, 
																oInmueble.getPropietario(),
																cuota,
																dfFecha_vencimiento.getSelectedDate().getTime(), //fecha_vencimiento
																monto_propietario, //monto
																periodo_anio,
																periodo_mes,
																(short) NovedadTipo.Impuesto, //novedad_tipo
																tObservaciones.getText(),
																id_impuesto,
																impuesto_cuotas[indice_cuotas],
																impuesto_annio);		        	
		        }
		        
		        if (monto_inquilino != 0) {
		        	
		        	// Grabo en LIQUIDACION COBRO
		        	
		        	ContratoNovedadCobroProcesos.grabarNovedad(	oContrato, 
		        												oContrato.getInquilino(),
																cuota,
																dfFecha_vencimiento.getSelectedDate().getTime(), //fecha_vencimiento
																monto_inquilino, //monto
																periodo_anio,
																periodo_mes,
																(short) NovedadTipo.Impuesto, //novedad_tipo
																tObservaciones.getText(),
																id_impuesto,
																impuesto_cuotas[indice_cuotas],
																impuesto_annio);		        	
		        }
		    
		        // Fin grabación de novedades --------------------------------
				
				tx.commit();			
			
			} // Fin for
			
		} catch (Exception e) {
			
			e.printStackTrace();
			tx.rollback();			
			
			// Que hago aca ¿? informo en pantalla
    		if (e instanceof ReglasDeNegocioException) {
    			throw new ValidacionException(e.getMessage(), e);
    		}
    		
    		salida = false;
			
		} finally { 
			// dio error de que la session ya estaba cerrada
			try{oSessionH.close();} catch(Exception e) {}
		}	
	    
    	
    	return salida;

    }
    
    public void salir() {
    	// Si quiero que se quede en la pantalla para seguir dando de alta datos
    }
    
    public void doLimpiar() {    	
    	
    	cboCuotasNew.setModel(new DefaultListModel());
    	
    	oInmueble = null;
    	
		tInquilino.setText("");
		tPropietario.setText("");
		tInmueble.setText("");
		
		tMonto_inquilino.setText("0,00");
		tMonto_propietario.setText("0,00");
		tMonto_total.setText("0,00");
		
		tImpuestoCuota.setText("");
		cboAnios.setSelectedText("");
    }
    
    public void actionPerformed(ActionEvent ae) {
    	
    	// System.out.println("Entro al actionPerformed");
    	
    	if (ae.getActionCommand().equals("inmueble")){
    		
    		CPPrincipal.abrirVentanaMensaje(new InmuebleFindListadoView(this, true));
    		
    	}
    	
    	// Tiro el evento para arriba en la gerarquia de objetos
    	super.actionPerformed(ae);
    	
    }


	@SuppressWarnings("unchecked")
	public void setResultado(Object object) {
		
		if (object instanceof Inmueble) {
			
			oInmueble = (Inmueble)object;			
			
			// Tengo que buscar el contrato donde figura el inmueble que traigo de filtro
			List<Contrato> lContratos = ContratoFacade.findByInmueble(oInmueble, true);
			
			// Ve cuando da error por que la propiedad no tiene ningún contrato vigente
			if (lContratos.size() != 0) {
				
				this.oContrato = lContratos.get(0);

				// Una vez que tengo el CONTRATO levanto los actores
				for(ContratoActor obj: oContrato.getContratoActors()) {
					if (obj.getId().getIdActorTipo() == ContratoActor.ActorTipoInquilino) {
						oInquilino = obj.getId().getPersona();
					}
				}
				
				tInquilino.setText(oInquilino.getDescripcion());
				tPropietario.setText(oInmueble.getPropietario().getDescripcion());
				tInmueble.setText(oInmueble.getDireccion_completa());
		        
				
				/* Tengo que recorrer los periodos del contrato a partir de la fecha de hoy para poder
				   seleccionar el período para el impuesto 
				   Los períodos de un contrato ... (mes, año, cuota nro.) 
				   Cargo los datos en un combo */
				
				List<Cuota> lCuotas = ContratoProcesos.buscarPeriodosContrato(oContrato, true);				
				
				// convierto los datos en una colección de objetos Combo
				List<Combo> lCombo = new ArrayList<Combo>();
				for (Cuota oCuota:lCuotas) {
					String descripcion = Utiles.convertirMes(oCuota.getPeriodo_mes()) + " - " + oCuota.getPeriodo_anio() + " (Cuota " +oCuota.getCuota() + ")";
					String valor = oCuota.getPeriodo_mes() + "|" + oCuota.getPeriodo_anio() + "|" + oCuota.getCuota();
					lCombo.add(new Combo(valor, descripcion, false));
				}
				
				cboCuotas.asignarDatosCombo(lCombo);
				cboCuotas.setEnabled(true);
				
				
/** PRUEBA DE UN COMBO LIST CON SELECCION MULTIPLE */
				
				
				lCombos = new ComboListItem[lCombo.size()];
				for (int i = 0; i < lCombo.size(); i++ ) {
					Combo obj = lCombo.get(i);
					String cadena = obj.getSDescripcion();
					boolean seleccionado = obj.getBDefecto();
					
					lCombos[i] = new ComboListItem(cadena, obj.getSValor(), "", seleccionado);
				}
				
				cboCuotasNew.setModel(new DefaultListModel(lCombos));
				cboCuotasNew.setEnabled(true);
							
				System.out.println("alto " + cboCuotasNew.getHeight());
				
/** FIN PRUEBA */
				
				
			} else {
			
				new MessageWindowPane("El inmueble no posee ningún contrato vigente.");
				
			}
			
		}
		
		
	}
               
}