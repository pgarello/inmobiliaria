package app.abms.usuario;

import ccecho2.base.CCColumn;
import ccecho2.base.CCLabel;
import ccecho2.base.CCRow;
import ccecho2.base.CCTextField;
import ccecho2.complex.MessageWindowPane;

import framework.ui.generales.FWWindowPaneMensajes;
import framework.ui.generales.abms.ABMListadoEditView;

import framework.ui.principal.FWContentPanePrincipal;

import datos.usuario.UsuarioFacade;
import datos.usuario.Usuario;

import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.ApplicationInstance;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Insets;

import nextapp.echo2.app.layout.RowLayoutData;

/**
 * PresentaciÃ³n de la ventana "acerca de..."
 */
@SuppressWarnings("serial")
public class UsuarioListadoEditView extends ABMListadoEditView {
    
	
	private UsuarioListadoView listado;
	private Usuario oUsuario;

	public UsuarioListadoEditView(UsuarioListadoView listado, Usuario oUsuario) {
		
		super();
		
		// Es la clase que me llamo
		this.listado = listado;
		
		this.oUsuario = oUsuario;
		
		// Seteo el título de la ventana
		this.setTitle("Modificación de Usuarios");
		this.setHeight(new Extent(320, Extent.PX));
		this.setWidth(new Extent(450, Extent.PX));
		
		// Agrego los componentes de la pantalla
		crearObjetos();
		renderObjetos();
		
	}
	
	
	private CCColumn cPrincipal, cLabels, cTexts;
	private CCRow rPrincipal, rMensaje;
	
	private CCLabel lUsuario, lPassword1, lPassword2, lDescripcion, lMensaje;
	private CCTextField tUsuario, tPassword1, tPassword2, tDescripcion;
	
	
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
        
        lUsuario = new CCLabel("Usuario:");
        tUsuario = new CCTextField();        
        tUsuario.setWidth(new Extent(200, Extent.PX));
        tUsuario.setText(oUsuario.getUsuario());
        tUsuario.setEnabled(false);
        tUsuario.setBackground(Color.LIGHTGRAY);
        

        lPassword1 = new CCLabel("Contraseña:");        
        tPassword1 = new CCTextField();
        tPassword1.setFocusTraversalIndex(200);
        tPassword1.setFocusTraversalParticipant(true);
        tPassword1.setWidth(new Extent(200, Extent.PX));
        tPassword1.setText(oUsuario.getClave());
        
        lPassword2 = new CCLabel("Repita la contraseña:");
        tPassword2 = new CCTextField();
        tPassword2.setFocusTraversalIndex(300);
        tPassword2.setFocusTraversalParticipant(true);
        tPassword2.setWidth(new Extent(200, Extent.PX));
        tPassword2.setText(oUsuario.getClave());
        
        lDescripcion = new CCLabel("Descripción:");
        tDescripcion = new CCTextField();
        tDescripcion.setFocusTraversalIndex(400);
        tDescripcion.setFocusTraversalParticipant(true);
        tDescripcion.setWidth(new Extent(200, Extent.PX));
        tDescripcion.setMaximumLength(100);
        tDescripcion.setText(oUsuario.getDescripcion());
        
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
        
        cLabels.add(lUsuario);
        cLabels.add(lPassword1);
        cLabels.add(lPassword2);
        cLabels.add(lDescripcion);

        cTexts.add(tUsuario);
        cTexts.add(tPassword1);
        cTexts.add(tPassword2);
        cTexts.add(tDescripcion);

        rMensaje.add(lMensaje);


        ApplicationInstance.getActive().setFocusedComponent(tPassword1);
        
    }
    
    
    public boolean update() {
        
    	boolean salida = true;
    	
    	// Valido la integridad de los datos
    	String clave1 = tPassword1.getText().trim();
    	String clave2 = tPassword2.getText().trim();
    	
    	if (!clave1.equals(clave2)) {
    		
    		//new MessageWindowPane("Los campos CLAVE deben ser iguales.");
    		
    		((FWContentPanePrincipal) ApplicationInstance
                    .getActive().getDefaultWindow().getContent())
                    .abrirVentana(new FWWindowPaneMensajes("Error Moficicación de USUARIOS","Los campos CLAVE deben ser iguales.",(short)1));
    		
    		salida = false;
    		
    	} else {
    	
	    	// Levanto los valores ingresados y se lo seteo al objeto    	
	    	oUsuario.setDescripcion(tDescripcion.getText());
	    	oUsuario.setUsuario(tUsuario.getText());
	    	oUsuario.setClave(tPassword1.getText());
	    	
	    	// Actualizo
	    	try {
	    		UsuarioFacade.update(oUsuario);
	    		
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


