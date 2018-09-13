package servlets;

import app.principal.InmobiliariaApp;

import nextapp.echo2.app.ApplicationInstance;
import nextapp.echo2.webcontainer.WebContainerServlet;

public class PrincipalServlet extends WebContainerServlet {

	private static final long serialVersionUID = 1L;

	@Override
    public ApplicationInstance newApplicationInstance() {
    	
		return new InmobiliariaApp();
    
    }
}