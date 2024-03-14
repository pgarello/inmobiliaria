package app.informes;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.jfree.util.Log;

import app.combos.ComboAnios;
import app.combos.ComboMeses;
import ccecho2.base.CCColumn;

import ccecho2.base.CCLabel;
import ccecho2.base.CCButton;
import ccecho2.base.CCCheckBox;
import ccecho2.base.CCDateField;
import ccecho2.base.CCRow;
import ccecho2.base.CCTextField;
import ccecho2.complex.ComboList.NoItemSelectedException;

import echopointng.Separator;

import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.ApplicationInstance;

import nextapp.echo2.app.Color;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.ImageReference;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.ResourceImageReference;
import nextapp.echo2.app.WindowPane;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.layout.ColumnLayoutData;
import framework.nr.generales.busquedas.FWBusquedas;
import framework.nr.generales.filtros.FWFiltros;

import framework.ui.generales.abms.ABMListadoFilterView;
import framework.ui.principal.FWContentPanePrincipal;

@SuppressWarnings("serial")
public class EstadisticaFiltroView extends ABMListadoFilterView implements FWBusquedas, FWFiltros {
	    
    private CCColumn _cPrincipal, cLabels, cTexts, cBotonesProcesos;
	private CCRow rPrincipal, rMensaje, rMesAnio, rBotonesProcesos;
	
	private CCLabel lMensaje, lMensaje1;
	private CCLabel lMesAnio;
	private CCTextField tInmueble;
	
	private ComboMeses oComboMeses;
	private ComboAnios oComboAnios;
	           
    CCDateField dfFecha_desde, dfFecha_hasta;
	CCCheckBox cbFecha_desde, cbFecha_hasta;
	
	private CCButton btnPropietario, btnInquilino;
	private ImageReference iPropietario = new ResourceImageReference("/resources/crystalsvg22x22/actions/run.png");
	
    private FWContentPanePrincipal CPPrincipal;
	
	/*
	 * Filtros q tiene la pantalla
	 * 1 - Inmueble (me muestra todos los contratos de un inmueble)
	 * 2 - Propietario
	 * 3 - Inquilino
	 */	

	
    public EstadisticaFiltroView () {
        
    	super();
    	
    	// Seteo el título de la ventana
		this.setTitle("Estadística de Contratos - I");
		this.setHeight(new Extent(450, Extent.PX));
		this.setWidth(new Extent(700, Extent.PX));
		
		CPPrincipal = ((FWContentPanePrincipal) ApplicationInstance.getActive().getDefaultWindow().getContent());
		
		//ApplicationInstance.getActive()
		//CPPrincipal.getParent().get
		//WindowPane.PROPERTY_MAXIMUM_WIDTH						
		
		// Agrego los componentes de la pantalla
		crearObjetos();
		renderObjetos();
				
		//this.setMensaje("1º Los INQUILINOS que no pagaron en el período" + //);
		//this.setMensaje("2º Los PROPIETARIOS a los que no se les pagó en el período.");
		
		Logger.getLogger("Inmobiliaria").log(Level.INFO, "Datos de AUDITORIA: " + this.getMaximumWidth());
		
    }
    
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
        
          
        /*******************************************************************/       
        rMesAnio = new CCRow(22);
        
        lMesAnio = new CCLabel("Período:",22);
        
        oComboAnios = new ComboAnios(100,22, true,(short)2);
        oComboMeses = new ComboMeses(100,22,11,true,(short)2);           

        /*******************************************************************/       
        
        cBotonesProcesos = new CCColumn();
        cBotonesProcesos.setCellSpacing(new Extent(10, Extent.PX));
        cBotonesProcesos.setInsets(new Insets(10));
        
        /* Configuro el boton que busca */
        btnPropietario = new CCButton("2º Los PROPIETARIOS a los que no se les pagó en el período.", iPropietario);
        this.btnPropietario.setActionCommand("propietarios");    
        this.btnPropietario.addActionListener(this);        
        ColumnLayoutData cLabelLD = new ColumnLayoutData();
        this.btnPropietario.setLayoutData(cLabelLD);

        //
        btnInquilino = new CCButton("1º Los INQUILINOS que no pagaron en el período.", iPropietario);
        this.btnInquilino.setActionCommand("inquilinos");        
        this.btnInquilino.addActionListener(this);
        ColumnLayoutData cLabelLD1 = new ColumnLayoutData();
        this.btnInquilino.setLayoutData(cLabelLD1);
                
        
        /*******************************************************************/
        rBotonesProcesos = new CCRow();
        rBotonesProcesos.setInsets(new Insets(10));
        //rBotones.setAlignment(Alignment.ALIGN_CENTER);
        
        /*******************************************************************/
        rMensaje = new CCRow();
        rMensaje.setAlignment(Alignment.ALIGN_CENTER);

        lMensaje = new CCLabel();
        //lMensaje.setForeground(Color.RED);
        
        lMensaje1 = new CCLabel("Se puede visualizar 2 tipos de reportes:", 30);
        lMensaje1.setForeground(Color.RED);

    }
    
    
    private void renderObjetos() {
        
    	// Agrego la columna al ContentPane Principal
    	cpPrincipal.add(_cPrincipal);

        _cPrincipal.add(rPrincipal);
        rPrincipal.add(cLabels);
        rPrincipal.add(cTexts);
        
        //rPrincipal.setBorder(new Border(1, Color.BLUE,Border.STYLE_DASHED));
                
        _cPrincipal.add(rBotonesProcesos);
        _cPrincipal.add(rMensaje);
                       
        
        cLabels.add(lMesAnio); // Label fila 1        
        //cLabels.add(lMensaje1);
        //cLabels.add(btnPropietario);
        
        // -----------------------------------------------------------
        
        rMesAnio.add(oComboMeses);
        rMesAnio.add(new Separator());
        rMesAnio.add(oComboAnios);
        
        cTexts.add(rMesAnio); // Texto fila 1
        //cTexts.add(btnPropietario);
        
        // -----------------------------------------------------------        
        rBotonesProcesos.add(cBotonesProcesos);
        
        cBotonesProcesos.add(lMensaje1);        
        cBotonesProcesos.add(btnInquilino);
        cBotonesProcesos.add(btnPropietario);
        // -----------------------------------------------------------
        
        rMensaje.add(lMensaje);
        
        ApplicationInstance.getActive().setFocusedComponent(tInmueble);
        
    }
	
    
    public void propietarios() {
    	Logger.getLogger("Inmobiliaria").log(Level.INFO, "EstadisticaFiltroView.propietarios ");
    	
    	short filtro_mes = 0;
		try {
			filtro_mes = Short.parseShort(oComboMeses.getSelectedId());
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (NoItemSelectedException e) {
			e.printStackTrace();
		}
    	short filtro_anio = 0;
		try {
			filtro_anio = Short.parseShort(oComboAnios.getSelectedId());
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (NoItemSelectedException e) {
			e.printStackTrace();
		}
    	
    	DeudasListadoView oPantallaListado = new DeudasListadoView(	filtro_mes, filtro_anio, this, false);
    	((FWContentPanePrincipal) ApplicationInstance.getActive().getDefaultWindow().getContent()).abrirVentana(oPantallaListado);
    
    	
    }
    
    public void inquilinos() {
    	Logger.getLogger("Inmobiliaria").log(Level.INFO, "EstadisticaFiltroView.inquilinos ");
    	
    	this.find();
    	
    }
    
    
	
    public void find() {

    	Logger.getLogger("Inmobiliaria").log(Level.INFO, "EstadisticaFiltroView.find");
    	
    	/*
    	 La idea es buscar los datos y pasarselo a la pantalla LISTADO
    	 que tiene que ser una nueva ventana.
    	 La otra posibilidad es pasar los filtros y realizar la consulta
    	 de datos directamente en la otra pantalla 
    	 */
    	
    	/** LLamo a DeudasListadoView 
    	 * 
    	 * Tengo que mostrar los datos del inquilino y el monto que adeuda, como la fecha de vencimiento ¿?: 
    	 * PROPIEDAD - PERIODO - CUOTA - MONTO - INQUILINO
    	 * 
    	 * AGREGO un nuevo listado
    	 * De los inquilinos que no me pagaron, a quien le pagué al PROPIETARIO
    	 * "Es la plata que pone la inmobiliaria"
    	 * 
    	 * */
    	    	
    	short filtro_mes = 0;
		try {
			filtro_mes = Short.parseShort(oComboMeses.getSelectedId());
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (NoItemSelectedException e) {
			e.printStackTrace();
		}
    	short filtro_anio = 0;
		try {
			filtro_anio = Short.parseShort(oComboAnios.getSelectedId());
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (NoItemSelectedException e) {
			e.printStackTrace();
		}
    	
    	DeudasListadoView oPantallaListado = new DeudasListadoView(	filtro_mes, filtro_anio, this, true);
    	((FWContentPanePrincipal) ApplicationInstance.getActive().getDefaultWindow().getContent()).abrirVentana(oPantallaListado);
    	
    }
    
    public void actionPerformed(ActionEvent ae) {
    	
    	Logger.getLogger("Inmobiliaria").log(Level.INFO, "EstadisticaFiltroView.actionPerformed command: " + ae.getActionCommand());
    	    	
        if (ae.getActionCommand().equals("propietarios")){
        	
            // Limpio los datos de pantalla
        	this.propietarios();
            
        } if (ae.getActionCommand().equals("inquilinos")) {
        	
        	// Antes ejecutaba el comando "find"
        	this.inquilinos();
        	
        }
        
    	// Tiro el evento para arriba en la gerarquia de objetos
    	super.actionPerformed(ae);
        	        
    }
    
    
	public void setResultado(Object object) {}
    
	public void clear() {
				
		//this.chComercial.setSelected(false);
		this.lMensaje.setText("");
		
	}

	public void setMensaje(String mensaje) {
		lMensaje.setText(mensaje);	
	}
	      
}