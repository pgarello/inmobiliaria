package framework.ui.principal;

import ccecho2.base.CCWindow;

import echopointng.command.JavaScriptInclude;

import framework.grales.seguridad.FWUsuario;
import framework.nr.seguridad.FWAutenticador;
import framework.ui.seguridad.FWContentPaneLogin;
import nextapp.echo2.app.ApplicationInstance;
import nextapp.echo2.app.StyleSheet;
import nextapp.echo2.app.Window;
import nextapp.echo2.app.componentxml.ComponentXmlException;
import nextapp.echo2.app.componentxml.StyleSheetLoader;


public class FWApplicationInstancePrincipal extends ApplicationInstance {
    
	private static final long serialVersionUID = 1L;
    
	protected FWAutenticador autenticador;
    private CCWindow window;
    private FWUsuario usuario;

    public static final StyleSheet DEFAULT_STYLE_SHEET;
    static {
        try {
            DEFAULT_STYLE_SHEET = StyleSheetLoader.load("resources/Default.stylesheet.xml", Thread.currentThread().getContextClassLoader());
        } catch (ComponentXmlException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }
    
    // Inicia todo
    public Window init() {
        ApplicationInstance.getActive().enqueueCommand(new JavaScriptInclude("resources/javaScript/global.js"));
        
        setStyleSheet(DEFAULT_STYLE_SHEET);
        window = new CCWindow();
        window.setContent(new FWContentPaneLogin());
        return window;
        
    }
    
    
    public void exit() {
    	System.out.println("FWApplicationInstancePrincipal.exit");
    }
    

    /**
     * constructor
     */
    public static FWApplicationInstancePrincipal getApp() {
        return (FWApplicationInstancePrincipal) getActive();
    }
    
    /**
     * Carga el panel principal
     *
     * @param usuario usuario logueado en el sistema
     */
    public void cargarPrincipal(FWUsuario usuario) {
        this.setUsuario(usuario);
        window.setContent(new FWContentPanePrincipal());
    }

    /**
     * retorna el usuario logueado en el sistema
     *
     * @return usuario logueado en el sistema
     */
    public FWUsuario getUsuario() {
        return usuario;
    }
    
    
    

    /**
     * registra el usuario logueado en el sistema
     *
     * @param usuario a registrar como logueado
     */
    public void setUsuario(FWUsuario usuario) {
        this.usuario = usuario;
    }

    public FWAutenticador getAutenticador() {
        return this.autenticador;
    }
}