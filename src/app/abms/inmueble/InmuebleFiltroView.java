package app.abms.inmueble;

import app.busquedas.persona.PersonaFindListadoView;
import app.combos.ComboEdificio;
import app.combos.ComboInmuebleTipo;
import ccecho2.base.CCColumn;

import ccecho2.base.CCLabel;

import ccecho2.base.CCButton;
import ccecho2.base.CCRow;
import ccecho2.base.CCTextField;
import ccecho2.complex.ComboList.ComboList;

import datos.contrato_actor.ContratoActor;
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
import framework.nr.generales.filtros.FWFiltros;
import framework.ui.generales.abms.ABMListadoFilterView;
import framework.ui.principal.FWContentPanePrincipal;

@SuppressWarnings("serial")
public class InmuebleFiltroView extends ABMListadoFilterView implements FWBusquedas, FWFiltros {
		
    private ImageReference iPropietario = new ResourceImageReference("/resources/crystalsvg22x22/actions/kontact_contacts.png");
    private CCButton btnPropietario;
    private Persona oPropietario;

	
    private CCColumn _cPrincipal, cLabels, cTexts;
	private CCRow rPrincipal, rMensaje, rPropietario;
	
	private CCLabel lMensaje;
	private CCLabel lDir_calle, lDir_edificio, lInmuebleTipo, lPropietario;
	private CCTextField tDir_calle, tDir_edificio, tPropietario;
	
	ComboList cboInmuebleTipo, cboEdificio;
	
	private FWContentPanePrincipal CPPrincipal;
	
	
    public InmuebleFiltroView () {
        
    	super();
    	
    	// Seteo el título de la ventana
		this.setTitle("Filtro para el listado de Propiedades");
		this.setHeight(new Extent(550, Extent.PX));
		this.setWidth(new Extent(600, Extent.PX));
		
		CPPrincipal = ((FWContentPanePrincipal) ApplicationInstance.getActive().getDefaultWindow().getContent());
		
		// Agrego los componentes de la pantalla
		crearObjetos();
		renderObjetos();
    }
	
	
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
           
        lDir_edificio = new CCLabel("Edificio:",22);
        tDir_edificio = new CCTextField(300,22,90,true);
        cboEdificio = new ComboEdificio(100,22,60,true);
        cboEdificio.setSelectedText(0);
        
        lInmuebleTipo = new CCLabel("Tipo:",22);
        cboInmuebleTipo = new ComboInmuebleTipo(200,22,60,true,true);
        //cboInmuebleTipo.setSelectedText(0);
        
        lPropietario = new CCLabel("Propietario:",22);
        tPropietario = new CCTextField(300,false);
        tPropietario.setEnabled(false);
        
        
        
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
        
        
        cLabels.add(lDir_calle);
        cLabels.add(lDir_edificio);
        cLabels.add(lInmuebleTipo);
        cLabels.add(lPropietario);
        
        cTexts.add(tDir_calle);
        //cTexts.add(tDir_edificio);
        cTexts.add(cboEdificio);
        cTexts.add(cboInmuebleTipo);
        cTexts.add(rPropietario);
                
        rMensaje.add(lMensaje);

        ApplicationInstance.getActive().setFocusedComponent(tDir_calle);
        
    }
	
    public void clear() {
    	// Limpio los datos de pantalla
    	
    	// cboInmuebleTipo = new ComboInmuebleTipo(200,22,60,true,true);
    	cboInmuebleTipo.setSelectedText(0);
    	
    	tDir_calle.setText("");
    	//tDir_edificio.setText("");
    	cboEdificio.setSelectedText(0);
    	this.oPropietario = null;
    	this.tPropietario.setText("");
    	
    	lMensaje.setText("");
    	
    }
    
    
    public void find() {

    	/*
    	 La idea es buscar los datos y pasarselo a la pantalla LISTADO
    	 que tiene que ser una nueva ventana.
    	 La otra posibilidad es pasar los filtros y realizar la consulta
    	 de datos directamente en la otra pantalla 
    	 */
    	
    	/** LLamo a InmuebleListadoView */
    	String filtro_calle = tDir_calle.getText();
    	
    	int filtro_edificio = 0;
    	try {filtro_edificio = Integer.parseInt(cboEdificio.getSelectedId());} catch(Exception e) {}
    	//String filtro_edificio = tDir_edificio.getText();
    	
    	short filtro_tipo = 0;
		try {filtro_tipo = Short.parseShort(cboInmuebleTipo.getSelectedId());} catch (Exception e) {}
		
		
    	int filtro_propietario = 0;
    	try {filtro_propietario = this.oPropietario.getIdPersona();} catch(Exception e) {}
    	
    	InmuebleListadoView oPantallaListado = new InmuebleListadoView(	filtro_calle,
    																	filtro_edificio,
    																	filtro_tipo,
    																	filtro_propietario,
    																	this);
    	
    	if (!oPantallaListado.sin_datos) {    	
    		// Esto es para ver la pantalla
    		((FWContentPanePrincipal) ApplicationInstance.getActive().getDefaultWindow().getContent()).abrirVentana(oPantallaListado);
    	}
    	
    }
    
    
    public void actionPerformed(ActionEvent ae) {
    	
    	if (ae.getActionCommand().equals("propietario")){
    		
    		//new MessageWindowPane("Aca llamo a la pantalla de selección del Propietario");
            CPPrincipal.abrirVentanaMensaje(new PersonaFindListadoView("Propietario", this, ContratoActor.ActorTipoPropietario));
    		
    	}
    	
    	// Tiro el evento para arriba en la gerarquia de objetos
    	super.actionPerformed(ae);
    	
    }


	public void setResultado(Object object) {
		this.oPropietario = (Persona) object;
    	//System.out.println("PROPIETARIO " + oPropietario.getApellido() + ", " + oPropietario.getNombres());
    	//this.tPropietario.setText(oPropietario.getApellido() + ", " + oPropietario.getNombres());  
		this.tPropietario.setText(oPropietario.getDescripcion());
	}


	public void setMensaje(String mensaje) {
		
		lMensaje.setText(mensaje);
		
	}
              
}