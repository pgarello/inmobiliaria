package app.abms.persona;

import ccecho2.base.CCColumn;

import ccecho2.base.CCLabel;

import ccecho2.base.CCRow;
import ccecho2.base.CCTextField;

import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.ApplicationInstance;

import nextapp.echo2.app.Color;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Insets;


import framework.ui.generales.abms.ABMListadoFilterView;
import framework.ui.principal.FWContentPanePrincipal;

@SuppressWarnings("serial")
public class PersonaFiltroView extends ABMListadoFilterView {
	
	
    public PersonaFiltroView () {
        
    	super();
    	
    	// Seteo el título de la ventana
		this.setTitle("Filtro para el listado de Personas");
		this.setHeight(new Extent(550, Extent.PX));
		this.setWidth(new Extent(600, Extent.PX));
		
		// Agrego los componentes de la pantalla
		crearObjetos();
		renderObjetos();
    }
    
    
    
    private CCColumn _cPrincipal, cLabels, cTexts;
	private CCRow rPrincipal, rMensaje;
	
	private CCLabel lMensaje;
	private CCLabel lApellido, lNombres, lTelefono, lCelular, lDocNum, lRazonSocial;
	private CCTextField tApellido, tNombres, tTelefono, tDoc, tCelular, tRazonSocial;
	
		
	// Tengo que cargar un combooooo el de localidades (dinámico) -- LISTO
	// Tengo otro combo - el de tipo de documento (estático) -- LISTO
	// Y la fecha de nacimiento ????? calendar -- LISTO
	// Falta el campo observaciones 
	
    private void crearObjetos() {
    	
        _cPrincipal = new CCColumn();
        _cPrincipal.setCellSpacing(new Extent(20));
        _cPrincipal.setInsets(new Insets(10));
        
        rPrincipal = new CCRow();
        
        cLabels = new CCColumn();
        cLabels.setCellSpacing(new Extent(10, Extent.PX));
        cLabels.setInsets(new Insets(10));
        
        cTexts = new CCColumn();
        cTexts.setCellSpacing(new Extent(10, Extent.PX));
        cTexts.setInsets(new Insets(10));
        
        
        lApellido = new CCLabel("Apellido/Razón Social:",22);        
        tApellido = new CCTextField(300,22,10,true);
        tApellido.addActionListener(this);
        tApellido.setActionCommand("find");
           
        lNombres = new CCLabel("Nombres:",22);        
        tNombres = new CCTextField(300,22,20,true);
        tNombres.addActionListener(this);
        tNombres.setActionCommand("find");
        
        lDocNum = new CCLabel("Número de Doc.:",22);
        tDoc = new CCTextField(100,22,40,true);
                
        lTelefono = new CCLabel("Teléfono:",22);
        tTelefono = new CCTextField(100,22,70,true);
        tTelefono.setMaximumLength(100);
        
        lCelular = new CCLabel("Celular:",22);
        tCelular = new CCTextField(100,22,80,true);
        tCelular.setMaximumLength(100);
                
        lRazonSocial = new CCLabel("Razón Social:",22);
        tRazonSocial = new CCTextField(300,22,100,true);        
        
        
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
        
        cLabels.add(lApellido);
        cLabels.add(lNombres);
        cLabels.add(lDocNum);
        cLabels.add(lTelefono);
        cLabels.add(lCelular);
        //cLabels.add(lRazonSocial);
        
        cTexts.add(tApellido);
        cTexts.add(tNombres);        
        cTexts.add(tDoc);
        cTexts.add(tTelefono);
        cTexts.add(tCelular);
        //cTexts.add(tRazonSocial);        
                
        rMensaje.add(lMensaje);

        ApplicationInstance.getActive().setFocusedComponent(tApellido);
        
    }
	
	
    public void find() {

    	/*
    	 La idea es buscar los datos y pasarselo a la pantalla LISTADO
    	 que tiene que ser una nueva ventana.
    	 La otra posibilidad es pasar los filtros y realizar la consulta
    	 de datos directamente en la otra pantalla 
    	 */
    	
    	/** LLamo a PersonaListadoView */
    	
    	//new PersonaListadoView(); // MODO 1
    	
    	//FWContentPanePrincipal cpPrincipal = (FWContentPanePrincipal) ApplicationInstance.getActive().getDefaultWindow().getContent();
    	//cpPrincipal.abrirVentana("app.abms.persona.PersonaListadoView"); // MODO 2
    	
    	
    	/*
    	try {PersonaListadoView.class.newInstance();} 
    	catch (Exception e) {e.printStackTrace();}
    	*/ // MODO 3
    	
    	String filtro_apellido = tApellido.getText();
    	String filtro_nombre = tNombres.getText();
    	
    	PersonaListadoView oPantallaListado = new PersonaListadoView(filtro_apellido, filtro_nombre);
    	((FWContentPanePrincipal) ApplicationInstance.getActive().getDefaultWindow().getContent()).abrirVentana(oPantallaListado);
    	
    }
    
    public void clear() {
    	tApellido.setText("");
    	tNombres.setText("");
    }
           
}