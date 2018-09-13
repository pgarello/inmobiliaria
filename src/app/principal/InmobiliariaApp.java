package app.principal;

import app.seguridad.AutenticadorInmobiliaria;
import framework.ui.principal.FWApplicationInstancePrincipal;

@SuppressWarnings("serial")
public class InmobiliariaApp extends FWApplicationInstancePrincipal {
    public InmobiliariaApp() {
        super();
        this.autenticador = new AutenticadorInmobiliaria();
    }
}