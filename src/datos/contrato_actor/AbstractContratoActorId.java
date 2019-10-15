package datos.contrato_actor;

import datos.contrato.Contrato;
import datos.persona.Persona;

/**
 * AbstractContratoActorId generated by MyEclipse Persistence Tools
 */

public abstract class AbstractContratoActorId implements java.io.Serializable {

	// Fields

	private Contrato contrato;

	private Short idActorTipo;

	private Persona persona;

	// Constructors

	/** default constructor */
	public AbstractContratoActorId() {
	}

	/** full constructor */
	public AbstractContratoActorId(Contrato contrato, Short idActorTipo,
			Persona persona) {
		this.contrato = contrato;
		this.idActorTipo = idActorTipo;
		this.persona = persona;
	}

	// Property accessors

	public Contrato getContrato() {
		return this.contrato;
	}

	public void setContrato(Contrato contrato) {
		this.contrato = contrato;
	}

	public Short getIdActorTipo() {
		return this.idActorTipo;
	}

	public void setIdActorTipo(Short idActorTipo) {
		this.idActorTipo = idActorTipo;
	}

	public Persona getPersona() {
		return this.persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof AbstractContratoActorId))
			return false;
		AbstractContratoActorId castOther = (AbstractContratoActorId) other;

		return ((this.getContrato() == castOther.getContrato()) || (this
				.getContrato() != null
				&& castOther.getContrato() != null && this.getContrato()
				.equals(castOther.getContrato())))
				&& ((this.getIdActorTipo() == castOther.getIdActorTipo()) || (this
						.getIdActorTipo() != null
						&& castOther.getIdActorTipo() != null && this
						.getIdActorTipo().equals(castOther.getIdActorTipo())))
				&& ((this.getPersona() == castOther.getPersona()) || (this
						.getPersona() != null
						&& castOther.getPersona() != null && this.getPersona()
						.equals(castOther.getPersona())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getContrato() == null ? 0 : this.getContrato().hashCode());
		result = 37
				* result
				+ (getIdActorTipo() == null ? 0 : this.getIdActorTipo()
						.hashCode());
		result = 37 * result
				+ (getPersona() == null ? 0 : this.getPersona().hashCode());
		return result;
	}

}