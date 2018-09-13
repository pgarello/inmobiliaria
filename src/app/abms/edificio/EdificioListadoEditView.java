package app.abms.edificio;

import ccecho2.base.CCColumn;
import ccecho2.base.CCLabel;
import ccecho2.base.CCRow;
import ccecho2.base.CCTextArea;
import ccecho2.base.CCTextField;
import ccecho2.complex.MessageWindowPane;

import framework.ui.generales.FWWindowPaneMensajes;
import framework.ui.generales.abms.ABMListadoEditView;

import framework.ui.principal.FWContentPanePrincipal;

import datos.edificio.Edificio;
import datos.edificio.EdificioFachada;

import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.ApplicationInstance;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Insets;

import nextapp.echo2.app.layout.RowLayoutData;

@SuppressWarnings("serial")
public class EdificioListadoEditView extends ABMListadoEditView {
    
	
	private EdificioListadoView listado;
	private Edificio oEdificio;

	public EdificioListadoEditView(EdificioListadoView listado, Edificio oEdificio) {
		
		super();
		
		// Es la clase que me llamo
		this.listado = listado;
		
		this.oEdificio = oEdificio;
		
		// Seteo el título de la ventana
		this.setTitle("Modificación de Edificios");
		this.setHeight(new Extent(360, Extent.PX));
		this.setWidth(new Extent(500, Extent.PX));
		
		// Agrego los componentes de la pantalla
		crearObjetos();
		renderObjetos();
		
	}
	
	
	private CCColumn cPrincipal, cLabels, cTexts;
	private CCRow rPrincipal, rMensaje;
	
	private CCLabel lEdificio, lCantidad, lObservaciones, lMensaje;
	private CCTextField tEdificio, tCantidad;
	private CCTextArea tObservaciones;
		
    private void crearObjetos() {
    	
        cPrincipal = new CCColumn();
        cPrincipal.setCellSpacing(new Extent(20));
        cPrincipal.setInsets(new Insets(10));
        rPrincipal = new CCRow();

        rMensaje = new CCRow();
        rMensaje.setAlignment(Alignment.ALIGN_CENTER);
        
        cLabels = new CCColumn();
        cLabels.setCellSpacing(new Extent(10));
        cLabels.setInsets(new Insets(10));
        
        cTexts = new CCColumn();
        cTexts.setCellSpacing(new Extent(10));
        cTexts.setInsets(new Insets(10));
        
        /* Datos */
        
        lEdificio = new CCLabel("Edificio:", 22);
        tEdificio = new CCTextField(300,22,10,true);        
        tEdificio.setText(oEdificio.getDescripcion());
        
        lCantidad = new CCLabel("Cant.Dpto.:", 22);
        tCantidad = new CCTextField(100,22,30,true);
        tCantidad.setText(""+oEdificio.getDptoCantidad());
        
        lObservaciones = new CCLabel("Observaciones:", 88);
        tObservaciones = new CCTextArea(300,88,4,true); // 22 x 4
        tObservaciones.setText(oEdificio.getObservaciones());
        
        /* FIN DATOS */
        
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
        
        cLabels.add(lEdificio);
        cLabels.add(lCantidad);
        cLabels.add(lObservaciones);
        
        cTexts.add(tEdificio);
        cTexts.add(tCantidad);
        cTexts.add(tObservaciones);
        
        rMensaje.add(lMensaje);

        ApplicationInstance.getActive().setFocusedComponent(tEdificio);
        
    }
    
    
    public boolean update() {
        
    	boolean salida = true;
    	
    	
    	// Valido la integridad de los datos
    	
    	String edificio = tEdificio.getText().trim();
    	
    	if (edificio.equals("")) {
    		
    		//new MessageWindowPane("Los campos CLAVE deben ser iguales.");
    		
    		((FWContentPanePrincipal) ApplicationInstance
                    .getActive().getDefaultWindow().getContent())
                    .abrirVentana(new FWWindowPaneMensajes("Error Modificación de EDIFICIOS","El campo descripción es obligatorio.",(short)1));
    		
    		salida = false;
    		
    	} else {
    	
	    	// Levanto los valores ingresados y se lo seteo al objeto    	
    		oEdificio.setDescripcion(edificio);
    		oEdificio.setDptoCantidad(Short.parseShort(tCantidad.getText()));
	    	oEdificio.setObservaciones(tObservaciones.getText());
	    	
	    	// Actualizo
	    	try {
	    		EdificioFachada.merge(oEdificio);
	    		
	    		// Actualizo la tabla
	        	listado.ActualizarDatos();
	    		
	    	} catch(Exception e) {
	    		e.printStackTrace();
	    		salida = false;
	    		//new MessageWindowPane(e.getMessage());
	    		new MessageWindowPane("Se produjo un error en la actualización");
	    	}
	    	
    	}
    	
    	return salida;
        
    }
    
}


