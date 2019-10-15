package datos.inmueble;

// Generated by MyEclipse Persistence Tools

import java.util.Set;

/**
 * InmuebleTipo generated by MyEclipse Persistence Tools
 */
public class InmuebleTipo extends AbstractInmuebleTipo implements java.io.Serializable {
	
	// Constructors

	private static final long serialVersionUID = -5342689845671705647L;

	/** default constructor */
	public InmuebleTipo() {
	}

	public InmuebleTipo(Short idInmuebleTipo) {
		this.setIdInmuebleTipo(idInmuebleTipo);
	}
	
	/** minimal constructor */
	public InmuebleTipo(Short idInmuebleTipo, String descripcion) {
		super(idInmuebleTipo, descripcion);
	}

	/** full constructor */
	public InmuebleTipo(Short idInmuebleTipo, String descripcion,
			String descripcionCorta, Set inmuebles) {
		super(idInmuebleTipo, descripcion, descripcionCorta, inmuebles);
	}

}