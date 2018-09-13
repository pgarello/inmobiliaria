package app.abms.usuario;

import ccecho2.base.CCColumn;

import ccecho2.base.CCLabel;
import ccecho2.base.CCRow;
import ccecho2.base.CCTextField;

import framework.ui.generales.abms.ABMListadoAddView;



import datos.usuario.UsuarioFacade;
import datos.usuario.Usuario;

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
public class UsuarioListadoAddView extends ABMListadoAddView {
	
	private UsuarioListadoView listado;
	
	public UsuarioListadoAddView(UsuarioListadoView listado) {
		
		super();
		
		// Es la clase que me llamo
		this.listado = listado;
		
		// Seteo el título de la ventana
		this.setTitle("Alta de Usuarios");
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
        tUsuario.setFocusTraversalIndex(100);
        tUsuario.setFocusTraversalParticipant(true);
        tUsuario.setWidth(new Extent(200, Extent.PX));

        lPassword1 = new CCLabel("Contraseña:");        
        tPassword1 = new CCTextField();
        tPassword1.setFocusTraversalIndex(200);
        tPassword1.setFocusTraversalParticipant(true);
        tPassword1.setWidth(new Extent(200, Extent.PX));
        
        lPassword2 = new CCLabel("Repita la contraseña:");
        tPassword2 = new CCTextField();
        tPassword2.setFocusTraversalIndex(300);
        tPassword2.setFocusTraversalParticipant(true);
        tPassword2.setWidth(new Extent(200, Extent.PX));
        
        lDescripcion = new CCLabel("Descripción:");
        tDescripcion = new CCTextField();
        tDescripcion.setFocusTraversalIndex(400);
        tDescripcion.setFocusTraversalParticipant(true);
        tDescripcion.setWidth(new Extent(200, Extent.PX));
        tDescripcion.setMaximumLength(100);
        
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


        ApplicationInstance.getActive().setFocusedComponent(tUsuario);
        
    }

	
	
    public boolean insert() {

    	boolean salida = true;
    	
    	// Levanto los valores ingresados
    	Usuario oUsuario = new Usuario();
    	
    	oUsuario.setDescripcion(tDescripcion.getText());
    	oUsuario.setUsuario(tUsuario.getText());
    	oUsuario.setClave(tPassword1.getText());
    	
    	// Inserto
    	try {
    		UsuarioFacade.save(oUsuario);
    		
        	// Actualizo la tabla
        	listado.ActualizarDatos();
    		
    	} catch(Exception e) {
    		e.printStackTrace();
    		salida = false;
    		//new MessageWindowPane(e.getMessage());
    	}
    	
    	return salida;

    }
    
}