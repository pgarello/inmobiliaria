/*
 * FWAutenticador.java
 *
 * Created on 17 de abril de 2007, 08:22
 *
 */

package app.seguridad;

import datos.usuario.Usuario;
import datos.usuario.UsuarioFacade;
import framework.grales.seguridad.FWTarea;
import framework.grales.seguridad.FWUsuario;
import framework.nr.seguridad.FWAutenticador;

/**
 *
 * Autenticador del FrameWork
 */
@SuppressWarnings("serial")
public class AutenticadorInmobiliaria extends FWAutenticador {

	private Usuario oUsuarioAplicacion;
	
    protected void createPerfil() {
    	
    	/**
    	 * Por ahora cargo aca las funciones del menú en forma genérica
    	 * para un futuro se pueden levantar en forma dinámica de una tabla
    	 * Pgarello 7-8-2007
    	 */
    	
        FWTarea tarea = new FWTarea();
        tarea.setDescripcion("Acerca de ...");
        tarea.setComando("app.principal.WindowPaneAcercaDe");
        tarea.setId(1); // Nivel dentro del menu Principal
        this.usuario.getPermisos().add(tarea);

        tarea = new FWTarea();
        tarea.setDescripcion("ABM Usuarios");
        tarea.setComando("app.abms.usuario.UsuarioListadoView");
        this.usuario.getPermisos().add(tarea);

        tarea = new FWTarea();
        tarea.setDescripcion("ABM Zonas");
        tarea.setComando("app.abms.zonas.ZonasListadoController");
        this.usuario.getPermisos().add(tarea);

        tarea = new FWTarea();
        tarea.setDescripcion("ABM de Personas FÃ­sicas");
        tarea.setComando("app.abms.personas_fisicas.PersonaFisicaController");
        this.usuario.getPermisos().add(tarea);

        tarea = new FWTarea();
        tarea.setDescripcion("ABM de Personas JurÃ­dicas");
        tarea.setComando("app.abms.personas_juridicas.PersonaJuridicaController");
        this.usuario.getPermisos().add(tarea);

        // No andubo
        tarea = new FWTarea();
        tarea.setDescripcion("Login");
        tarea.setComando("app.principal.InmobiliariaApp");
        this.usuario.getPermisos().add(tarea);

        tarea = new FWTarea();
        tarea.setDescripcion("Tarea erronea.");
        tarea.setComando("framework.ui.FWWindoadfawPaneLogin");
        this.usuario.getPermisos().add(tarea);
    }
    
    
    /** Crea una instancia en base a un usuario y pass -- uso sobrecarga del método*/
    public FWUsuario autenticar(String sUsuario, String sPassword) {
    	
    	// Limpio el usuario logueado
    	this.usuario = null;
    	
    	// Aca va la rutina de validación de usuarios .....
    	try {
    		
    		Usuario oUsuario = UsuarioFacade.findByLogin(sUsuario, sPassword);
    		
    		if (oUsuario != null) {    		
    			this.usuario = new FWUsuario();
    			
    			this.usuario.setUsuario(oUsuario.getDescripcion());
    			this.usuario.setId(oUsuario.getIdUsuario());
    			this.usuario.setAdministrador(oUsuario.getAdministrador());
    			
    			this.createPerfil();
    			this.oUsuarioAplicacion = oUsuario;
    		}
    		
    	} catch(Exception e) {
    		e.printStackTrace();
    		
    		//new MessageWindowPane(e.getMessage());
    	}

    	/*
        if (sUsuario.equals("usuario") && sPassword.equals("clave")) {
            this.usuario = new FWUsuario();
            this.usuario.setUsuario(sUsuario);
            this.createPerfil();
        }
        */
    	
        return this.usuario;
    }
    
    
    
    
    public Usuario getUsuarioAplicacion() {
    	return this.oUsuarioAplicacion;
    }
    
    public void setUsuarioAplicacion(Usuario oUsuario) {
    	this.oUsuarioAplicacion = oUsuario;
    }
    
}
