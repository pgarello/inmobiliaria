package datos.localidad;

//import java.util.HashSet;
//import java.util.Set;

/**
 * Localidad generated by MyEclipse Persistence Tools
 * Puedo agregar el atributo caracteristica telef�nica
 * P.Ej. Paran� - Entre R�os - 0343
 * Pgarello 25/02/2009
 */

public class Localidad implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	// Fields

	private Integer idLocalidad;

	private String descripcion;

	private String provincia;

	private String cp;

	// Constructors

	/** default constructor */
	public Localidad() {
	}

	public Localidad(Integer idLocalidad) {
		this.idLocalidad = idLocalidad;
	}
	
	/** minimal constructor */
	public Localidad(Integer idLocalidad, String descripcion, String provincia) {
		this.idLocalidad = idLocalidad;
		this.descripcion = descripcion;
		this.provincia = provincia;
	}

	/** full constructor */
	public Localidad(Integer idLocalidad, String descripcion, String provincia,
			String cp) {
		this.idLocalidad = idLocalidad;
		this.descripcion = descripcion;
		this.provincia = provincia;
		this.cp = cp;
	}

	// Property accessors

	public Integer getIdLocalidad() {
		return this.idLocalidad;
	}

	public void setIdLocalidad(Integer idLocalidad) {
		this.idLocalidad = idLocalidad;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getProvincia() {
		return this.provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getCp() {
		return this.cp;
	}

	public void setCp(String cp) {
		this.cp = cp;
	}


}