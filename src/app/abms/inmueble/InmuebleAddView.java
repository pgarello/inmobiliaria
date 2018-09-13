package app.abms.inmueble;

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

import ccecho2.complex.ComboList.ComboList;
import ccecho2.complex.ComboList.NoItemSelectedException;

import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.ApplicationInstance;

import nextapp.echo2.app.Color;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.ImageReference;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.ResourceImageReference;

import nextapp.echo2.app.event.ActionEvent;


import datos.edificio.Edificio;
import datos.inmueble.Inmueble;
import datos.inmueble.InmuebleFacade;
import datos.inmueble.InmuebleTipo;
import datos.localidad.Localidad;
import datos.persona.Persona;


import framework.nr.generales.busquedas.FWBusquedas;
import framework.ui.generales.abms.ABMAddView;
import framework.ui.principal.FWContentPanePrincipal;

@SuppressWarnings("serial")
public class InmuebleAddView extends ABMAddView implements FWBusquedas {
	
	
	// Imagen del botón Propietario
    private ImageReference iPropietario = new ResourceImageReference("/resources/crystalsvg22x22/actions/kontact_contacts.png");
    private CCButton btnPropietario;
    private Persona oPropietario;

    private CCColumn _cPrincipal, cLabels, cTexts;
	private CCRow rPrincipal, rMensaje, rDireccion, rPropietario;
	
	private CCLabel lMensaje;
	private CCLabel lDir_calle, lDir_nro, lDir_edificio, lObservaciones, lInmuebleTipo, lLocalidad, lPropietario;
	private CCTextField tDir_calle, tDir_nro, tDir_piso, tDir_dpto, tDir_edificio, tPropietario;
	
	ComboList cboInmuebleTipo, cboLocalidades, cboEdificios;
	
	private CCTextArea tObservaciones;
	
    private FWContentPanePrincipal CPPrincipal;

	
    public InmuebleAddView () {
        
    	super();
    	
    	// Seteo el título de la ventana
		this.setTitle("Alta de Propiedades");
		this.setHeight(new Extent(550, Extent.PX));
		this.setWidth(new Extent(600, Extent.PX));
		
		CPPrincipal = ((FWContentPanePrincipal) ApplicationInstance.getActive().getDefaultWindow().getContent());
		
		// Agrego los componentes de la pantalla
		crearObjetos();
		renderObjetos();
    }
        
	
	/* FALTA
	 * 1 Combo inmueble_tipo - LISTO
	 * 2 Combo propietario -
	 * 3 Falta aplicar el estilo al objeto TextArea (ver archivo de estilos)
	 */
	
    private void crearObjetos() {
    	
        _cPrincipal = new CCColumn();
        _cPrincipal.setCellSpacing(new Extent(20));
        _cPrincipal.setInsets(new Insets(10));
        
        rPrincipal = new CCRow();
        rDireccion = new CCRow(22);
        rPropietario = new CCRow(22);
        
        cLabels = new CCColumn();
        cLabels.setCellSpacing(new Extent(10, Extent.PX));
        cLabels.setInsets(new Insets(10));
        
        cTexts = new CCColumn();
        cTexts.setCellSpacing(new Extent(10, Extent.PX));
        cTexts.setInsets(new Insets(10));
        
        /* Configuro el boton del Propietario */
        btnPropietario = new CCButton(iPropietario);
        this.btnPropietario.setActionCommand("propietario");
        this.btnPropietario.setToolTipText("Asignar Propietario");
        
        this.btnPropietario.setStyleName(null);
        //this.btnPropietario.setBackground(Color.LIGHTGRAY);
        this.btnPropietario.setInsets(new Insets(10, 0));
        
        this.btnPropietario.addActionListener(this);
        
        
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
        cboEdificios.setSelectedText(0); // para que no tenga ninguno seleccionado
        
        lLocalidad = new CCLabel("Localidad:",22);
        cboLocalidades = new ComboLocalidades(100,22,60,true);
        cboLocalidades.setSelectedText(1);
        
        lInmuebleTipo = new CCLabel("Tipo:",22);
        cboInmuebleTipo = new ComboInmuebleTipo(200,22,60,true, false);
        cboInmuebleTipo.setSelectedText(1);
        
        lPropietario = new CCLabel("Propietario:",22);
        tPropietario = new CCTextField(300,true);
        tPropietario.setEnabled(false);
        //tPropietario.set
        
        
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
        
        //rPrincipal.setBorder(new Border(1, Color.BLUE,Border.STYLE_DASHED));
        
        rDireccion.add(tDir_nro);
        rDireccion.add(new CCLabel("-"));
        rDireccion.add(tDir_piso);
        rDireccion.add(new CCLabel("-"));
        rDireccion.add(tDir_dpto);
        
        rPropietario.add(tPropietario);
        rPropietario.add(btnPropietario);
        
        _cPrincipal.add(rMensaje);
        _cPrincipal.add(rBotones);        
                
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
                        
        rMensaje.add(lMensaje);

        ApplicationInstance.getActive().setFocusedComponent(tDir_calle);
        
    }
	
	
    public boolean insert() {

    	boolean salida = true;
    	
    	// Levanto los valores ingresados
    	Inmueble oInmueble = new Inmueble();
    	
    	oInmueble.setDireccionCalle(tDir_calle.getText());
    	oInmueble.setDireccionNro(tDir_nro.getText());
    	oInmueble.setDireccionDpto(tDir_dpto.getText());
    	oInmueble.setDireccionPiso(tDir_piso.getText());
    	//oInmueble.setDireccionEdificio(tDir_edificio.getText());
    	oInmueble.setObservaciones(tObservaciones.getText());
    	
    	try {
    		System.out.println("Id PROPIETARIO " + oPropietario.getIdPersona());
    		oInmueble.setPropietario(oPropietario);
    	} catch(NullPointerException npe) {
    		// Puedo no seleccionar el propietario en el alta
    	}
    	
    	try {			
    		Localidad oLocalidad = new Localidad(Integer.parseInt(cboLocalidades.getSelectedId()));
			oInmueble.setLocalidad(oLocalidad);
		} catch (NumberFormatException e1) {
			e1.printStackTrace();
		} catch (NoItemSelectedException e1) {
			e1.printStackTrace();
		}
			
		try {
			InmuebleTipo oInmuebleTipo = new InmuebleTipo(Short.parseShort(cboInmuebleTipo.getSelectedId()));
			oInmueble.setInmuebleTipo(oInmuebleTipo);
		} catch (NumberFormatException e1) {
			e1.printStackTrace();
		} catch (NoItemSelectedException e1) {
			e1.printStackTrace();
		}
		
		try {
			if (!cboEdificios.getSelectedId().equals("0")) {
				Edificio oEdificio = new Edificio(Integer.parseInt(cboEdificios.getSelectedId()));
				oInmueble.setEdificio(oEdificio);
			}
		} catch (NumberFormatException e1) {
			e1.printStackTrace();
		} catch (NoItemSelectedException e1) {
			e1.printStackTrace();
		}
    	
		    	
    	// Inserto
    	try {
    		InmuebleFacade.save(oInmueble);    		    		
    	} catch(Exception e) {
    		e.printStackTrace();
    		salida = false;
    		//new MessageWindowPane(e.getMessage());
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
		if (this.oPropietario.getIdPersonaTipo() == Persona.PersonaFisica) {
			this.tPropietario.setText(oPropietario.getApellido() + ", " + oPropietario.getNombres());
		} else {
			this.tPropietario.setText(oPropietario.getRazonSocial());
		}
		
	}
	
	public void doLimpiar() {
		tDir_calle.setText("");
		tDir_nro.setText("");
		tObservaciones.setText("");
		tPropietario.setText("");
		cboEdificios.setSelectedText(0);
		cboInmuebleTipo.setSelectedText(1);
		cboLocalidades.setSelectedText(1);
	}
	
           
}