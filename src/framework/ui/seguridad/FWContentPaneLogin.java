package framework.ui.seguridad;

import ccecho2.base.CCContentPane;
import ccecho2.base.CCWindowPane;
import framework.grales.seguridad.FWUsuario;

import framework.ui.principal.FWApplicationInstancePrincipal;

public class FWContentPaneLogin extends CCContentPane {
    private static final long serialVersionUID = 1L;
    private FWWindowPaneLogin login;
    private FWUsuario usuario;
    
    public FWContentPaneLogin() {
        super();
        cargarLogin();
    }

    public FWContentPaneLogin(boolean salir) {
        super();
        cargarLogout();
    }
    
    
    
    public void cargarPrincipal(FWUsuario u) {
        usuario = u;
        ((FWApplicationInstancePrincipal) this.getApplicationInstance()).cargarPrincipal(usuario);
        remove(login);
    }

    private void cargarLogin() {    	
        login = new FWWindowPaneLogin();
        abrirVentana(login);
    }
    
    private void cargarLogout() {     	
        //login = new FWWindowPaneLogout();
        abrirVentana(new FWWindowPaneLogout());
    }

    public void abrirVentana(CCWindowPane ventana) {
        this.add(ventana);
    }
 }




