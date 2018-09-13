package app.abms.edificio;

import app.combos.ComboProvincia;
import ccecho2.base.CCColumn;

import ccecho2.base.CCLabel;
import ccecho2.base.CCRow;
import ccecho2.base.CCTextArea;
import ccecho2.base.CCTextField;

import ccecho2.complex.ComboList.ComboList;

import framework.ui.generales.abms.ABMListadoAddView;



import datos.edificio.Edificio;
import datos.edificio.EdificioFachada;
import datos.localidad.Localidad;
import datos.localidad.LocalidadFacade;


import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.ApplicationInstance;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.layout.RowLayoutData;

/**
 * Pantalla alta de usuarios
 */
@SuppressWarnings("serial")
public class EdificioListadoAddView extends ABMListadoAddView {
	
	private EdificioListadoView listado;
	
	public EdificioListadoAddView(EdificioListadoView listado) {
		
		super();
		
		// Es la clase que me llamo
		this.listado = listado;
		
		// Seteo el título de la ventana
		this.setTitle("Alta de Edificios");
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
        
        /* DATOS */
        
        lEdificio = new CCLabel("Edificio:", 22);
        tEdificio = new CCTextField(300,22,10,true);        
        
        lCantidad = new CCLabel("Cant.Dpto.:", 22);
        tCantidad = new CCTextField(100,22,30,true);
        
        lObservaciones = new CCLabel("Observaciones:", 88);
        tObservaciones = new CCTextArea(300,88,4,true); // 22 x 4
        
        /* fin datos */ 
        
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

	
	
    public boolean insert() {

    	System.out.println("EdificioListadoAddView.insert");
    	
    	boolean salida = true;
    	
    	// Validaciones
    	
    	// Levanto los valores ingresados
    	Edificio oEdificio = new Edificio();
    	
    	oEdificio.setDescripcion(tEdificio.getText());
    	oEdificio.setDptoCantidad( Short.parseShort(tCantidad.getText()));
    	oEdificio.setObservaciones(tObservaciones.getText());
    	
    	// Inserto
    	try {
    		EdificioFachada.save(oEdificio);
    		
        	// Actualizo la tabla
        	listado.ActualizarDatos();
    		
    	} catch(Exception e) {
    		//String mensaje_error = "Se produjo un error en la actualización";
    		e.printStackTrace();
    		salida = false;
    		//new MessageWindowPane(e.getMessage());
    		//new FWWindowPaneMensajes(mensaje_error, "ERROR");
    	}
    	
    	return salida;

    }
    
}