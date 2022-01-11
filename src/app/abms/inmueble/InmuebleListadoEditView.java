package app.abms.inmueble;

import java.util.List;

import app.busquedas.persona.PersonaFindListadoView;
import app.combos.ComboEdificio;
import app.combos.ComboInmuebleTipo;
import app.combos.ComboLocalidades;

import ccecho2.base.CCButton;
import ccecho2.base.CCColumn;
import ccecho2.base.CCLabel;
import ccecho2.base.CCRow;
import ccecho2.base.CCTextArea;
import ccecho2.base.CCTextField;

import ccecho2.complex.MessageWindowPane;
import ccecho2.complex.ComboList.ComboList;

import framework.nr.generales.busquedas.FWBusquedas;
import framework.ui.generales.FWWindowPaneMensajes;
import framework.ui.generales.abms.ABMListadoEditView;
import framework.ui.generales.exception.ReglasDeNegocioException;
import framework.ui.principal.FWContentPanePrincipal;

//import datos.contrato_actor.ContratoActor;
import datos.Page;
import datos.contrato.Contrato;
import datos.contrato.ContratoProcesos;
import datos.edificio.Edificio;
import datos.inmueble.Inmueble;
import datos.inmueble.InmuebleFacade;

import datos.inmueble.InmuebleTipo;
import datos.localidad.Localidad;
import datos.persona.Persona;

import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.ApplicationInstance;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.ImageReference;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.ResourceImageReference;

import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.layout.RowLayoutData;

@SuppressWarnings("serial")
public class InmuebleListadoEditView extends ABMListadoEditView implements FWBusquedas {
    
	
	private InmuebleListadoView listado;
	private Inmueble oInmueble;

	public InmuebleListadoEditView(InmuebleListadoView listado, Inmueble oInmueble) {
		
		super();
		
		// Es la clase que me llamo
		this.listado = listado;
		
		this.oInmueble = oInmueble;
		
		// Seteo el título de la ventana
		this.setTitle("Modificación de Propiedades - Desde Listado");
		this.setHeight(new Extent(500, Extent.PX));
		this.setWidth(new Extent(650, Extent.PX));
		
		CPPrincipal = ((FWContentPanePrincipal) ApplicationInstance.getActive().getDefaultWindow().getContent());
		
		// Agrego los componentes de la pantalla
		crearObjetos();
		asignarDatos();
		renderObjetos();
		
	}
	
	
	// Imagen del botón Propietario
    private ImageReference iPropietario = new ResourceImageReference("/resources/crystalsvg22x22/actions/kontact_contacts.png");
    private CCButton btnPropietario;
    private Persona oPropietario;
	
	private CCColumn cPrincipal, cLabels, cTexts;
	private CCRow rPrincipal, rMensaje, rDireccion, rPropietario;
	
	private CCLabel lMensaje;
	private CCLabel lDir_calle, lDir_nro, lDir_edificio, lObservaciones, lInmuebleTipo, lLocalidad, lPropietario;
	private CCTextField tDir_calle, tDir_nro, tDir_piso, tDir_dpto, tDir_edificio, tPropietario;
	
	ComboList cboInmuebleTipo, cboLocalidades, cboEdificios;
	
	private CCTextArea tObservaciones;
	
	private FWContentPanePrincipal CPPrincipal;
	
    private void crearObjetos() {
    	
        cPrincipal = new CCColumn();
        cPrincipal.setCellSpacing(new Extent(20));
        cPrincipal.setInsets(new Insets(10));
        
        rPrincipal = new CCRow();
        rDireccion = new CCRow(22);
        rPropietario = new CCRow(22);

        rMensaje = new CCRow();
        rMensaje.setAlignment(Alignment.ALIGN_CENTER);
        
        cLabels = new CCColumn();
        cLabels.setCellSpacing(new Extent(10));
        cLabels.setInsets(new Insets(10));
        
        cTexts = new CCColumn();
        cTexts.setCellSpacing(new Extent(10));
        cTexts.setInsets(new Insets(10));
        
        /* Configuro el boton del Propietario */
        btnPropietario = new CCButton(iPropietario);
        this.btnPropietario.setActionCommand("propietario");
        this.btnPropietario.setToolTipText("Asignar Propietario");
        this.btnPropietario.setStyleName(null);
        this.btnPropietario.setInsets(new Insets(10, 0));
        this.btnPropietario.addActionListener(this);
        
        /*
         Ver que datos permito modificar -
         Tener en cuenta si ya forma parte de un contrato vigente/vencido !!!
         */

        /*******************************************************************/
        lDir_calle = new CCLabel("Calle:",22);
        tDir_calle = new CCTextField(300,22,50, true);
        
        
        lDir_nro = new CCLabel("Num-Piso-Dpto:",22);
        tDir_nro = new CCTextField(80,true);
        tDir_nro.setMaximumLength(10);
        
        tDir_piso = new CCTextField(80,true);
        tDir_dpto = new CCTextField(80,true);
        
        
        lDir_edificio = new CCLabel("Edificio:",22);
        tDir_edificio = new CCTextField(300,22,90,true);
        
        cboEdificios = new ComboEdificio(100,22,60,true);
        cboEdificios.setSelectedText(0);
        
        lLocalidad = new CCLabel("Localidad:",22);
        cboLocalidades = new ComboLocalidades(100,22,60,true);
        cboLocalidades.setSelectedText(1);
        
        lInmuebleTipo = new CCLabel("Tipo:",22);
        cboInmuebleTipo = new ComboInmuebleTipo(220,22,60,true, true);
        cboInmuebleTipo.setSelectedText(1);
        
        lPropietario = new CCLabel("Propietario:",22);
        tPropietario = new CCTextField(300,true);
        tPropietario.setEnabled(false);
        
        lObservaciones = new CCLabel("Observaciones:",22*7);
        tObservaciones = new CCTextArea(400,22,7,true);
        
        /*******************************************************************/
        
        rBotones = new CCRow();
        rBotones.setInsets(new Insets(10));
        rBotones.setAlignment(Alignment.ALIGN_CENTER);

        lMensaje = new CCLabel();
        lMensaje.setForeground(Color.RED);

    }
    
    private void renderObjetos() {
        
    	// Agrego la columna al ContentPane Principal
    	cpPrincipal.add(cPrincipal);
    	
        cPrincipal.add(rPrincipal);
        rPrincipal.add(cLabels);
        rPrincipal.add(cTexts);
        cPrincipal.add(rMensaje);
        cPrincipal.add(rBotones);

        RowLayoutData cLabelLayoutData = new RowLayoutData();
        cLabelLayoutData.setAlignment(new Alignment(Alignment.LEFT,Alignment.CENTER));
        cLabels.setLayoutData(cLabelLayoutData);
        
        rDireccion.add(tDir_nro);
        rDireccion.add(new CCLabel("-"));
        rDireccion.add(tDir_piso);
        rDireccion.add(new CCLabel("-"));
        rDireccion.add(tDir_dpto);
        
        rPropietario.add(tPropietario);
        rPropietario.add(btnPropietario);
          
                
        cLabels.add(lDir_calle);
        cLabels.add(lDir_nro);
        cLabels.add(lDir_edificio);
        cLabels.add(lLocalidad);
        cLabels.add(lInmuebleTipo);
        cLabels.add(lPropietario);
        cLabels.add(lObservaciones);
        
        cTexts.add(tDir_calle);
        cTexts.add(rDireccion);
        //cTexts.add(tDir_edificio);
        cTexts.add(cboEdificios);
        cTexts.add(cboLocalidades);
        cTexts.add(cboInmuebleTipo);
        cTexts.add(rPropietario);
        cTexts.add(tObservaciones);

        ApplicationInstance.getActive().setFocusedComponent(tDir_calle);
        
    }
    
    
    private void asignarDatos() {
       	
    	tDir_calle.setText(oInmueble.getDireccionCalle());
    	
    	tDir_nro.setText(oInmueble.getDireccionNro());
    	tDir_piso.setText(oInmueble.getDireccionPiso());
    	tDir_dpto.setText(oInmueble.getDireccionDpto());
    	
    	//tDir_edificio.setText(oInmueble.getDireccionEdificio());
    	try {
    		cboEdificios.setSelectedText(oInmueble.getEdificio().getIdEdificio());
    	} catch(NullPointerException npe){
        	// Es cuando la propiedad no tiene asignado un edificio AUN!!!
        }
        
    	cboLocalidades.setSelectedText(oInmueble.getLocalidad().getIdLocalidad());
        cboInmuebleTipo.setSelectedText(Integer.valueOf("" +oInmueble.getInmuebleTipo().getIdInmuebleTipo()));
        
        try {
        	tPropietario.setText(oInmueble.getPropietario().getDescripcion());
        	this.oPropietario = oInmueble.getPropietario();
        } catch(NullPointerException npe){
        	// Es cuando la propiedad no tiene asignado un propietario AUN!!!
        }
        
        tObservaciones.setText(oInmueble.getObservaciones());
    	
    }
    
    
    @SuppressWarnings("unchecked")
	public boolean update() {
        
    	boolean salida = true;
    	
    	// Valido la integridad de los datos

    	
    	/**
    	 * Tener cuidado que cuando el Inmueble tiene un CONTRATO y se modifica el PROPIETARIO
    	 * hay que resolver que se hace con la tabla CONTRATO_ACTOR que referencia a la persona modificada (anterior propietario)
    	 * 16/01/2013
    	 * 
    	 * NUEVA REGLA DE NEGOCIO ....
    	 */
    
    	
    	int id_persona_old = oInmueble.getPropietario().getIdPersona();
    	int id_persona_new = oPropietario.getIdPersona();
    	
    	if (id_persona_old != id_persona_new) {
    		    		
    		/**
    		 * Se modifica ahora esta REGLA
    		 * Modifico el propietario y lo hago sobre el contrato que esté vigente sobre la misma
    		 * OJO modificar el propieario de un contrato no es tan sencillo !!!!
    		 * 21/02/2015
    		 */
    		
    		// valido q no tenga un contrato asociado
    		Page pagina = ContratoProcesos.findByFilter(true, oInmueble.getIdInmueble(), 0, 0, 0, 1, null, null, false, false, null, null, 0);
    		List<Contrato> lista = pagina.getList();
    		for(Contrato oContrato : lista) {
    			
    			// LLamo al proceso en la fachada de CONTRATOS
    			//new FWWindowPaneMensajes("No se puede modificar el PROPIETARIO del inmueble si hay algún contrato asociado al mismo.", "Regla de Negocio");
    			//return false;
    			
    			// Prueba Beruti 6020 Beatini, Estela -> Martínez, Alberto
    			
    			// Puede haber 1 solo contrato vigente, así que lo busco para modificarlo ... 
    			//Contrato oContrato = lista.get(0);    			
    			if (oContrato.getFechaRescision() == null) {
	    			try {
						ContratoProcesos.modificarContrato(oContrato, oPropietario);
					} catch (ReglasDeNegocioException e) {					
						e.printStackTrace();
						new FWWindowPaneMensajes("No se puede modificar el PROPIETARIO del inmueble por problemas en la actualización del CONTRATO asociado.", "Regla de Negocio");
		    			return false;
					}
    			}
    		}
    		
    	}
    	
    		    	
	    // Levanto los valores ingresados y se lo seteo al objeto	
   		try {
				
			oInmueble.setDireccionCalle(tDir_calle.getText());
			oInmueble.setDireccionDpto(tDir_dpto.getText());
			oInmueble.setDireccionNro(tDir_nro.getText());        		
			oInmueble.setDireccionPiso(tDir_piso.getText());			
			
			//oInmueble.setDireccionEdificio(tDir_edificio.getText());
			/** Si no selecciono edificio => tengo que limpiar el dato */
			Edificio oEdificio = null;
			int id_edificio = Integer.parseInt(cboEdificios.getSelectedId());
			if (id_edificio != 0) { 
				oEdificio = new Edificio(Integer.parseInt(cboEdificios.getSelectedId()));
			}
			oInmueble.setEdificio(oEdificio);
			
			Localidad oLocalidad = new Localidad(Integer.parseInt(cboLocalidades.getSelectedId()));
			oInmueble.setLocalidad(oLocalidad);
			
			InmuebleTipo oInmuebleTipo = new InmuebleTipo(Short.parseShort(cboInmuebleTipo.getSelectedId()));
			oInmueble.setInmuebleTipo(oInmuebleTipo);
			
			oInmueble.setPropietario(oPropietario);
			
			oInmueble.setObservaciones(tObservaciones.getText());
    			
				
		} catch (Exception e) {
			// Aca hay un error en la carga de datos
			e.printStackTrace();
			salida = false;				
		}    	


	    	
    	// Actualizo
		if (salida) {
	    	try {
	    		InmuebleFacade.update(oInmueble);
		    		
		    	// Actualizo la tabla
		        listado.ActualizarDatos();
		    		
		    } catch(Exception e) {
		    	e.printStackTrace();
		    	salida = false;
		    	new MessageWindowPane(e.getMessage());
		    	//new MessageWindowPane("Se produjo un error en la actualización");
		    }
		}
	    
    	return salida;
        
    }
 
    
    public void actionPerformed(ActionEvent ae) {
    	
    	if (ae.getActionCommand().equals("propietario")){
    		
    		//new MessageWindowPane("Aca llamo a la pantalla de selección del Propietario");
            CPPrincipal.abrirVentanaMensaje(new PersonaFindListadoView("Propietario", this, (short)0));
    		
    	}
    	
    	// Tiro el evento para arriba en la gerarquia de objetos
    	super.actionPerformed(ae);
    	
    }
    
	public void setResultado(Object object) {
		
		this.oPropietario = (Persona) object;
    	//System.out.println("PROPIETARIO " + oPropietario.getApellido() + ", " + oPropietario.getNombres());
    	this.tPropietario.setText(oPropietario.getDescripcion());  
		
	}

}