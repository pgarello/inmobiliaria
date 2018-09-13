/*
 * Usuario.java
 *
 * Created on 17 de abril de 2007, 12:19
 *
 */

package framework.grales.seguridad;

import framework.grales.FWPersona;
import java.io.Serializable;
import java.util.Vector;

/**
 *
 * @author luigi
 */
public class FWUsuario implements Serializable{
    
	private static final long serialVersionUID = 1L;

	/** Identificador del usuario */
    private int id;
    
    /** Nombre del usuario */
    private String usuario;
    
    /** Persona asociada al usuario */
    private FWPersona persona;
    
    /** Vector de permisos del usuario - se usa para el menú */
    private Vector<FWTarea> permisos = new Vector<FWTarea>();
    
    private Boolean administrador;
    
    
    /** Creates a new instance of Usuario */
    public FWUsuario() {
    }

    
    public Vector<FWTarea> getPermisos() {
        return permisos;
    }

    public void setPermisos(Vector<FWTarea> permisos) {
        this.permisos = permisos;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public Boolean getAdministrador() {
		return administrador;
	}


	public void setAdministrador(Boolean administrador) {
		this.administrador = administrador;
	}
    
    
    
}
