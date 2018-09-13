package app.principal;
import ccecho2.base.*;
import ccecho2.complex.MessageWindowPane;

import echopointng.ExtentEx;
import framework.grales.seguridad.FWUsuario;
import framework.nr.seguridad.FWAutenticador;

import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.ApplicationInstance;
import nextapp.echo2.app.Color;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.FillImage;
import nextapp.echo2.app.ImageReference;
import nextapp.echo2.app.Insets;
import nextapp.echo2.app.ResourceImageReference;
import nextapp.echo2.app.event.ActionEvent;

public class WindowPaneAcercaDe extends CCWindowPane implements nextapp.echo2.app.event.ActionListener {

    private static final long serialVersionUID = 1L;

    /** Creates a new instance of NavWindow */
    protected CCSplitPane splitPane;
    protected CCContentPaneEx cPrincipal;
    
    private CCSplitPane spBotones;
    protected CCContentPane cpBotones;
    protected CCRow rBotones;
    protected CCContentPane cpBotonesExit;
    protected CCRow rBotonesExit;
    protected CCButton bExit;

    // Imagen del botón Ok
    private ImageReference LOGO = new ResourceImageReference("/resources/image/LogoNuevo.gif");
    // Imagen del botón Cancel
    private ImageReference CANCEL = new ResourceImageReference("/resources/crystalsvg22x22/actions/button_cancel.png");
    
    // Botón Ok
    private CCButton btnOK;
    // Botón Cancel
    private CCButton btnCancel;

    
    public WindowPaneAcercaDe() {
        super();
        
        this.setTitle("PANTALLA Acerca De ...");
                
        this.setStyleName("Default");
        this.setClosable(true);
        this.setWidth(new Extent(500, Extent.PX));
        this.setHeight(new Extent(500, Extent.PX));
        
        this.splitPane = new CCSplitPane(CCSplitPane.ORIENTATION_VERTICAL_BOTTOM_TOP, new ExtentEx("40px"));
        this.splitPane.setResizable(false);
        
        
        // Creo que esta es la pantalla principal ---------------------------------------
        this.cPrincipal = new CCContentPaneEx();
        
        CCLabel detalle1 = new CCLabel("By Chemsa");
        detalle1.setToolTipText("www.chemsa.com.ar");
        CCLabel detalle2 = new CCLabel("Pablo Garello");
        //CCLabel detalle3 = new CCLabel("www.chemsa.com.ar");
        
        
        this.cPrincipal.add(detalle1);
        this.cPrincipal.add(detalle2);
        
        
        // Fin Pantalla Principal --------------------------------------------------------
        
        
        this.spBotones = new CCSplitPane(CCSplitPane.ORIENTATION_HORIZONTAL_RIGHT_LEFT, new ExtentEx("40px"));
        
        this.cpBotones = new CCContentPane();
        this.cpBotones.setBackground(Color.DARKGRAY);
        this.cpBotonesExit = new CCContentPane();
        this.cpBotonesExit.setBackground(Color.DARKGRAY);

        this.rBotones = new CCRow();
        this.rBotones.setBackground(Color.DARKGRAY);
        this.rBotones.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));

        this.rBotonesExit = new CCRow();
        this.rBotonesExit.setBackground(Color.DARKGRAY);
        this.rBotonesExit.setAlignment(new Alignment(Alignment.CENTER, Alignment.CENTER));

        this.bExit = new CCButton(new ResourceImageReference("/resources/crystalsvg22x22/actions/exit.png"));
        this.bExit.setActionCommand("exit");
        this.bExit.addActionListener(this);
        this.bExit.setToolTipText("Salir del Formulario");

//        this.btnOK = new CCButton(this.OK);
//        this.btnOK.setActionCommand("ok");
//        this.btnOK.setToolTipText("Confirmar la Operación");
        
        this.btnCancel = new CCButton(this.CANCEL);
        this.btnCancel.setActionCommand("cancel");
        this.btnCancel.setToolTipText("Cancelar la Operación");

        //this.rBotonesExit.add(this.bExit);
        //this.rBotones.add(this.btnOK);
        //this.rBotones.add(this.btnCancel);

        this.cpBotones.add(this.rBotones);
        this.cpBotonesExit.add(this.rBotonesExit);
        
        this.spBotones.add(this.cpBotonesExit);
        this.spBotones.add(this.cpBotones);
        this.splitPane.add(this.spBotones);

        // Agrego el LOGO
        CCContentPane colLogo = new CCContentPane();
        FillImage oImage = new FillImage(LOGO, new Extent(100, Extent.PX), new Extent(20, Extent.PX), FillImage.NO_REPEAT);
        colLogo.setBackgroundImage(oImage);
        
        this.cPrincipal.add(colLogo);
        this.splitPane.add(this.cPrincipal);
        
        
        this.add(this.splitPane);
        this.splitPane.setSeparatorHeight(new Extent(2));
        
        //crearObjetos();
        //renderObjetos();
    }


    private void crearObjetos() {
    	/*
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

        lPassword = new CCLabel("Contraseña:");
        tPassword = new CCPasswordField();
        tPassword.setFocusTraversalIndex(200);
        tPassword.setFocusTraversalParticipant(true);
        

        rBotones = new CCRow();
        rBotones.setInsets(new Insets(10));
        rBotones.setAlignment(Alignment.ALIGN_CENTER);

        lMensaje = new CCLabel();
        lMensaje.setForeground(Color.RED);

        bAceptar = new CCButton("Aceptar");
        bAceptar.setFocusTraversalIndex(300);
        bAceptar.setFocusTraversalParticipant(true);
        bAceptar.setActionCommand("Aceptar");
        bAceptar.addActionListener(this);
        */
    }

    private void renderObjetos() {
        /*
    	add(cPrincipal);

        cPrincipal.add(rPrincipal);
        rPrincipal.add(cLabels);
        rPrincipal.add(cTexts);
        cPrincipal.add(rMensaje);
        cPrincipal.add(rBotones);

        cLabels.add(lUsuario);
        cLabels.add(lPassword);

        cTexts.add(tUsuario);
        cTexts.add(tPassword);

        rMensaje.add(lMensaje);

        rBotones.add(bAceptar);

        ApplicationInstance.getActive().setFocusedComponent(tUsuario);
        */
    }
    
    
    // Aca iteraccino a la proxima pantalla
    public void actionPerformed(ActionEvent e) {
    	
    	
    	//new MessageWindowPane("¿Esta seguro de realizar la actualización?",this, "doUpdate");
    	
        //if (e.getActionCommand().equals("Aceptar")){
        	
        	// Aca valido el usuario
        	// y cargo las tareas del usuario
        	// FWAutenticador autenticador = ((InmobiliariaApp) this.getApplicationInstance()).getAutenticador();
            //FWUsuario usuario = autenticador.autenticar(tUsuario.getText(), tPassword.getText());            
            
        	//FWUsuario usuario = new FWUsuario();
        	//usuario.setUsuario(tUsuario.getText());
        	/*
            if (usuario != null) {
            	
            	// Aca redirecciono por OK
                ((ContentPaneLogin) getParent()).cargarPrincipal(usuario);
            	
            } else {
                lMensaje.setText("El USUARIO no existe o la CLAVE es incorrecta.");
                ApplicationInstance.getActive().setFocusedComponent(tUsuario);
            }
            */
        //} 
    }
}