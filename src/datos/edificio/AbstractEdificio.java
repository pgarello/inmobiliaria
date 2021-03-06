package datos.edificio;

import java.util.HashSet;
import java.util.Set;

/**
 * AbstractEdificio generated by MyEclipse Persistence Tools
 */

public abstract class AbstractEdificio implements java.io.Serializable {

	// Fields

	private Integer idEdificio;

	private String descripcion;

	private String observaciones;

	private Short dptoCantidad;

	private Set inmuebles = new HashSet(0);

	// Constructors

	/** default constructor */
	public AbstractEdificio() {
	}

	/** minimal constructor */
	public AbstractEdificio(Integer idEdificio, String descripcion) {
		this.idEdificio = idEdificio;
		this.descripcion = descripcion;
	}

	/** full constructor */
	public AbstractEdificio(Integer idEdificio, String descripcion,
			String observaciones, Short dptoCantidad, Set inmuebles) {
		this.idEdificio = idEdificio;
		this.descripcion = descripcion;
		this.observaciones = observaciones;
		this.dptoCantidad = dptoCantidad;
		this.inmuebles = inmuebles;
	}

	// Property accessors

	public Integer getIdEdificio() {
		return this.idEdificio;
	}

	public void setIdEdificio(Integer idEdificio) {
		this.idEdificio = idEdificio;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getObservaciones() {
		return this.observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public Short getDptoCantidad() {
		return this.dptoCantidad;
	}

	public void setDptoCantidad(Short dptoCantidad) {
		this.dptoCantidad = dptoCantidad;
	}

	public Set getInmuebles() {
		return this.inmuebles;
	}

	public void setInmuebles(Set inmuebles) {
		this.inmuebles = inmuebles;
	}

}