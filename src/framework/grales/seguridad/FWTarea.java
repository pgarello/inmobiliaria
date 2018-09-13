/*
 * Tarea.java
 *
 * Created on 17 de abril de 2007, 12:22
 *
 */

package framework.grales.seguridad;

import java.io.Serializable;

/**
 * Representa una tarea en el sistema
 *
 * @author luigi
 */
public class FWTarea implements Serializable {
    
	private static final long serialVersionUID = -6825083310504797016L;

	
	/** Representa el nivel dentro del menú */
	private int nivel;
	
	
	/** Identificador de la tarea */
    private int id;
    
    /** Descripción de la tarea */
    private String descripcion;
    
    /** Clase a implementar */
    private String comando;
    
    
    
    /** Constructores */
    public FWTarea() {
    }

    
    
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getComando() {
        return comando;
    }

    public void setComando(String comando) {
        this.comando = comando;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
    
    
    
}