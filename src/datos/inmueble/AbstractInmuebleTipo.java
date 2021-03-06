package datos.inmueble;

import java.util.HashSet;
import java.util.Set;

/**
 * AbstractInmuebleTipo generated by MyEclipse Persistence Tools
 */

public abstract class AbstractInmuebleTipo implements java.io.Serializable {

	// Fields

	private Short idInmuebleTipo;
	public static Short InmuebleTipoCochera = 9;

	private String descripcion;

	private String descripcionCorta;

	private Set inmuebles = new HashSet(0);

	// Constructors

	/** default constructor */
	public AbstractInmuebleTipo() {
	}

	/** minimal constructor */
	public AbstractInmuebleTipo(Short idInmuebleTipo, String descripcion) {
		this.idInmuebleTipo = idInmuebleTipo;
		this.descripcion = descripcion;
	}

	/** full constructor */
	public AbstractInmuebleTipo(Short idInmuebleTipo, String descripcion,
			String descripcionCorta, Set inmuebles) {
		this.idInmuebleTipo = idInmuebleTipo;
		this.descripcion = descripcion;
		this.descripcionCorta = descripcionCorta;
		this.inmuebles = inmuebles;
	}

	// Property accessors

	public Short getIdInmuebleTipo() {
		return this.idInmuebleTipo;
	}

	public void setIdInmuebleTipo(Short idInmuebleTipo) {
		this.idInmuebleTipo = idInmuebleTipo;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDescripcionCorta() {
		return this.descripcionCorta;
	}

	public void setDescripcionCorta(String descripcionCorta) {
		this.descripcionCorta = descripcionCorta;
	}

	public Set getInmuebles() {
		return this.inmuebles;
	}

	public void setInmuebles(Set inmuebles) {
		this.inmuebles = inmuebles;
	}

}