package app.abms.usuario;

import ccecho2.base.CCColumn;

import ccecho2.base.CCLabel;
import ccecho2.base.CCRow;
import ccecho2.base.CCTextField;

import framework.grales.seguridad.FWUsuario;
import framework.ui.generales.abms.ABMListadoAddView;
import framework.ui.principal.FWApplicationInstancePrincipal;


import datos.usuario.Usuario;
import datos.usuario.UsuarioFacade;


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
public class UsuarioClaveEdit extends ABMListadoAddView {
	
	private Usuario oUsuario;
	
	public UsuarioClaveEdit() {
		
		super();
		
		// Seteo el título de la ventana
		this.setTitle("Usuarios - Modificación de CLAVE");
		this.setHeight(new Extent(320, Extent.PX));
		this.setWidth(new Extent(450, Extent.PX));
		
		// Tomo el usuario logueado
		FWUsuario oFWUsuario = ((FWApplicationInstancePrincipal) ApplicationInstance.getActive()).getUsuario();
		oUsuario = UsuarioFacade.findById(oFWUsuario.getId());
		
		// Agrego los componentes de la pantalla
		crearObjetos();
		renderObjetos();
		
	}
	
	
	private CCColumn cPrincipal, cLabels, cTexts;
	private CCRow rPrincipal, rMensaje;
	
	private CCLabel lUsuario, lClave, lDescripcion, lMensaje;
	private CCTextField tUsuario, tClave, tDescripcion;

	
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
        lUsuario = new CCLabel("Usuario:", 22);
        tUsuario = new CCTextField(100,22,10,true);
        tUsuario.setEnabled(false);
        tUsuario.setText(oUsuario.getUsuario());
        
        //tLocalidad.setMaximumLength(100);
        
        lDescripcion = new CCLabel("Descripción:", 22);
        tDescripcion = new CCTextField(100,22,30,true);
        tDescripcion.setEnabled(false);
        tDescripcion.setText(oUsuario.getDescripcion());
        
        lClave = new CCLabel("Clave:", 22);
        tClave = new CCTextField(100,22,30,true);
        tClave.setMaximumLength(10);
        tClave.setText(oUsuario.getClave());
        
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
        
        cLabels.add(lDescripcion);
        cLabels.add(lUsuario);
        cLabels.add(lClave);

        cTexts.add(tDescripcion);
        cTexts.add(tUsuario);
        cTexts.add(tClave);

        rMensaje.add(lMensaje);


        ApplicationInstance.getActive().setFocusedComponent(tClave);
        
    }

	
	
    public boolean insert() {

    	// HAGO UPDATE
    	
    	System.out.println("UsuarioClaveEdit.insert");
    	
    	boolean salida = true;
    	
    	// Validaciones
    	// que la clave tenga por lo menos 6 caracteres ...
    	
    	// Levanto los valores ingresados
    	oUsuario.setClave(tClave.getText());
    	UsuarioFacade.save(oUsuario);
    	
    	return salida;

    }
    
}