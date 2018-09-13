package app.abms.localidad;

import app.combos.ComboProvincia;
import ccecho2.base.CCColumn;
import ccecho2.base.CCLabel;
import ccecho2.base.CCRow;
import ccecho2.base.CCTextField;
import ccecho2.complex.MessageWindowPane;
import ccecho2.complex.ComboList.ComboList;

import framework.ui.generales.FWWindowPaneMensajes;
import framework.ui.generales.abms.ABMListadoEditView;

import framework.ui.principal.FWContentPanePrincipal;

import datos.localidad.Localidad;
import datos.localidad.LocalidadFacade;

import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.ApplicationInstance;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Insets;

import nextapp.echo2.app.layout.RowLayoutData;

@SuppressWarnings("serial")
public class LocalidadListadoEditView extends ABMListadoEditView {
    
	
	private LocalidadListadoView listado;
	private Localidad oLocalidad;

	public LocalidadListadoEditView(LocalidadListadoView listado, Localidad oLocalidad) {
		
		super();
		
		// Es la clase que me llamo
		this.listado = listado;
		
		this.oLocalidad = oLocalidad;
		
		// Seteo el título de la ventana
		this.setTitle("Modificación de Localidades");
		this.setHeight(new Extent(320, Extent.PX));
		this.setWidth(new Extent(500, Extent.PX));
		
		// Agrego los componentes de la pantalla
		crearObjetos();
		renderObjetos();
		
	}
	
	
	private CCColumn cPrincipal, cLabels, cTexts;
	private CCRow rPrincipal, rMensaje;
	
	private CCLabel lLocalidad, lProvincia, lCP, lMensaje;
	private CCTextField tLocalidad, tCP;
	
	private ComboList cboProvincia;
	
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
        
        lLocalidad = new CCLabel("Localidad:", 22);
        tLocalidad = new CCTextField(300,22,10,true);        
        tLocalidad.setText(oLocalidad.getDescripcion());
        //tUsuario.setBackground(Color.LIGHTGRAY);
        
        lProvincia = new CCLabel("Provincia:", 22);
        cboProvincia = new ComboProvincia(100,22);
        cboProvincia.setText(oLocalidad.getProvincia());
        
        lCP = new CCLabel("Código Postal:", 22);
        tCP = new CCTextField(100,22,30,true);
        tCP.setText(oLocalidad.getCp());
        
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
        
        cLabels.add(lLocalidad);
        cLabels.add(lProvincia);
        cLabels.add(lCP);

        cTexts.add(tLocalidad);
        cTexts.add(cboProvincia);
        cTexts.add(tCP);

        rMensaje.add(lMensaje);


        ApplicationInstance.getActive().setFocusedComponent(tLocalidad);
        
    }
    
    
    public boolean update() {
        
    	boolean salida = true;
    	
    	
    	// Valido la integridad de los datos
    	String localidad = tLocalidad.getText().trim();
    	
    	if (localidad.equals("")) {
    		
    		//new MessageWindowPane("Los campos CLAVE deben ser iguales.");
    		
    		((FWContentPanePrincipal) ApplicationInstance
                    .getActive().getDefaultWindow().getContent())
                    .abrirVentana(new FWWindowPaneMensajes("Error Modificación de LOCALIDADES","El campo localidad es obligatorio.",(short)1));
    		
    		salida = false;
    		
    	} else {
    	
	    	// Levanto los valores ingresados y se lo seteo al objeto    	
    		oLocalidad.setDescripcion(localidad);
    		oLocalidad.setProvincia(cboProvincia.getText());
	    	oLocalidad.setCp(tCP.getText());
	    	
	    	// Actualizo
	    	try {
	    		LocalidadFacade.update(oLocalidad);
	    		
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
    
}


