package datos.setup;

// Generated by MyEclipse Persistence Tools

import java.util.Date;

/**
 * Setup generated by MyEclipse Persistence Tools
 */
public class Setup extends AbstractSetup implements java.io.Serializable {

	private static final long serialVersionUID = 7868945052581094903L;

	/** default constructor */
	public Setup() {
	}

	/** minimal constructor */
	public Setup(String tabla, Long indice) {
		super(tabla, indice);
	}

	/** full constructor */
	public Setup(Date fechaActualizacion, String tabla, Short idFacturero,
			Long indice, String observaciones) {
		super(fechaActualizacion, tabla, idFacturero, indice, observaciones);
	}

}
