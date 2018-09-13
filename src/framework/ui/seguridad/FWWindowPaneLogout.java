package framework.ui.seguridad;

import ccecho2.base.CCButton;
import ccecho2.base.CCColumn;
import ccecho2.base.CCPasswordField;
import ccecho2.base.CCRow;
import ccecho2.base.CCLabel;
import ccecho2.base.CCTextField;
import ccecho2.base.CCWindowPane;
import framework.grales.seguridad.FWUsuario;
import framework.nr.seguridad.FWAutenticador;
import framework.ui.principal.FWApplicationInstancePrincipal;
import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.ApplicationInstance;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.event.ActionEvent;

public class FWWindowPaneLogout 
		extends CCWindowPane 
        implements nextapp.echo2.app.event.ActionListener {

    private static final long serialVersionUID = 1L;

    public FWWindowPaneLogout() {
        super();
        this.setTitle("Sistema Inmobiliaria ACUARIO PROPIEDADES");
        this.setStyleName("Default");
        this.setModal(true);
        this.setClosable(false);
        this.setWidth(new Extent(450, Extent.PX));
        this.setHeight(new Extent(300, Extent.PX));
        this.setResizable(false);
        crearObjetos();
        renderObjetos();
    }

    private CCColumn cPrincipal;
    private CCRow rPrincipal;

    private CCRow rMensaje;
    private CCLabel lMensaje;

    private CCColumn cLabels;
    private CCColumn cTexts;

    private CCLabel lUsuario;
    private CCLabel lPassword;

    private CCTextField tUsuario;
    private CCPasswordField tPassword;

    private CCButton bAceptar;
    private CCRow rBotones;

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
        tUsuario.addActionListener(this);

        lPassword = new CCLabel("Contraseña:");
        tPassword = new CCPasswordField();
        tPassword.setFocusTraversalIndex(200);
        tPassword.setFocusTraversalParticipant(true);
        tPassword.addActionListener(this);

        rBotones = new CCRow();
        rBotones.setInsets(new Insets(10));
        rBotones.setAlignment(Alignment.ALIGN_CENTER);

        lMensaje = new CCLabel("Ahora puede cerrar la ventana del navegador.");
        lMensaje.setForeground(Color.RED);
        

        bAceptar = new CCButton("Aceptar");
        bAceptar.setFocusTraversalIndex(300);
        bAceptar.setFocusTraversalParticipant(true);
        bAceptar.setActionCommand("Aceptar");
        bAceptar.addActionListener(this);
    }

    private void renderObjetos() {
        add(cPrincipal);

        cPrincipal.add(rPrincipal);
        rPrincipal.add(cLabels);
        rPrincipal.add(cTexts);
        cPrincipal.add(rMensaje);
        cPrincipal.add(rBotones);

//        cLabels.add(lUsuario);
//        cLabels.add(lPassword);
//
//        cTexts.add(tUsuario);
//        cTexts.add(tPassword);

        rMensaje.add(lMensaje);

        //rBotones.add(bAceptar);

        //ApplicationInstance.getActive().setFocusedComponent(tUsuario);
    }
    
    public void actionPerformed(ActionEvent e) {
    	
    	// valido los datos
    	if (tUsuario.getText().equals("") || tPassword.getText().equals("")) {
    		lMensaje.setText("El USUARIO o la CLAVE no fueron ingresadas.");
            ApplicationInstance.getActive().setFocusedComponent(tUsuario);
            return;
    	}
    	
        //if (e.getActionCommand().equals("Aceptar")){
            FWAutenticador autenticador = ((FWApplicationInstancePrincipal) this.getApplicationInstance()).getAutenticador();
            FWUsuario usuario = autenticador.autenticar(tUsuario.getText(),
                                                        tPassword.getText());
            if (usuario != null) {
                ((FWContentPaneLogin) getParent()).cargarPrincipal(usuario);
            } else {
                lMensaje.setText("El USUARIO no existe o la CLAVE es incorrecta.");
                ApplicationInstance.getActive().setFocusedComponent(tUsuario);
            }
            return;
        //} 
    }
}


