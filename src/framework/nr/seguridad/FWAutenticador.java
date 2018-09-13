/*
 * FWAutenticador.java
 *
 * Created on 17 de abril de 2007, 08:22
 *
 */

package framework.nr.seguridad;

import framework.grales.seguridad.FWUsuario;
import java.io.Serializable;


/**
 *
 * Autenticador del FrameWork
 */
public class FWAutenticador implements Serializable {
	
	private static final long serialVersionUID = -5873846175380069470L;
	
	//Variables de coniguracion para el acceso al WebService por ssl; 
    //cambiar en cada caso segun corresponda!!!!!!!!!!
    static String host = "batata.rectorado.unl.edu.ar";
    static String portType = "AutenticadorPort";
    static String serviceName = "AutenticadorService/AutenticadorWS";
    static String serviceEndpointAddress = "https://" + host + ":8181/" + serviceName;
    static String nameSpace = "http://autenticacion.rectorado.unl.edu.ar/";
    static String serviceUrl = serviceEndpointAddress + "?wsdl";
    
    
    protected FWUsuario usuario = null;
    /** Creates a new instance of FWAutenticador */
    
    public FWAutenticador() {}
    
    /** Crea una instancia en base a un usuario y pass */
    public FWUsuario autenticar(String sUsuario, String sPassword) {
    	
    	// Limpio el usuario logueado
    	this.usuario = null;
    	
        return this.usuario;
    }
    
    
    /*
    private static boolean doAuthentication(String sUsuario, String sPassword) {
        URL wsdlLocation;
        try {
            wsdlLocation = new URL(serviceUrl);
            QName serviceNameQ = new QName(nameSpace, "AutenticadorService");
            Service service = Service.create(wsdlLocation, serviceNameQ);
            AutenticadorService servicio = new AutenticadorService();
    //        AutenticadorWS autenticador = service.getPort(AutenticadorWS.class);
    //        AutenticadorWS autenticador = servicio.getAutenticadorPort();
            return autenticador.autenticar(sUsuario, sPassword);
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
            return false;
        }
    }*/

    protected void createPerfil() {
/*        // Se instancia un usuario con sus tareas
        FWTarea tarea = new FWTarea();
        tarea.setDescripcion("Acerca de ...");
        tarea.setComando("framework.ui.generales.acercaDe.AcercaDeController");
        this.usuario.getPermisos().add(tarea);

        tarea = new FWTarea();
        tarea.setDescripcion("ABM Bancos");
        tarea.setComando("app.abms.bancos.BancosListadoController");
        this.usuario.getPermisos().add(tarea);

        tarea = new FWTarea();
        tarea.setDescripcion("ABM Zonas");
        tarea.setComando("app.abms.zonas.ZonasListadoController");
        this.usuario.getPermisos().add(tarea);

        tarea = new FWTarea();
        tarea.setDescripcion("Alta / Modificaci�n de Socios");
        tarea.setComando("app.abms.asociados.AMAsociadosController");
        this.usuario.getPermisos().add(tarea);

        tarea = new FWTarea();
        tarea.setDescripcion("Tarea erronea.");
        tarea.setComando("framework.ui.FWWindoadfawPaneLogin");
        this.usuario.getPermisos().add(tarea);*/
    }
}