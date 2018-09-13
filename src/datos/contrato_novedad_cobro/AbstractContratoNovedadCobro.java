package datos.contrato_novedad_cobro;

import java.util.Date;

import datos.contrato.Contrato;
import datos.persona.Persona;

/**
 * AbstractContratoNovedadCobro generated by MyEclipse Persistence Tools
 */

public abstract class AbstractContratoNovedadCobro implements
		java.io.Serializable {

	// Fields

	private Integer idContratoNovedadCobro;

	private Contrato contrato;

	private Persona persona;

	private Short idNovedadTipo;

	private Short periodoMes;

	private Short periodoAnio;

	private Short contratoCuota;

	private Double monto;

	private Date fechaVencimiento;

	private Date fechaAlta;
	
	private String observaciones;

	
	private Short impuestoId;
	private Short impuestoCuota;
	private Short impuestoAnio;

	// Constructors

	/** default constructor */
	public AbstractContratoNovedadCobro() {}

	/** minimal constructor */
	public AbstractContratoNovedadCobro(Integer idContratoNovedadCobro,
			Contrato contrato, Persona persona, Short idNovedadTipo,
			Date fechaAlta) {
		this.idContratoNovedadCobro = idContratoNovedadCobro;
		this.contrato = contrato;
		this.persona = persona;
		this.idNovedadTipo = idNovedadTipo;
		this.fechaAlta = fechaAlta;
	}

	/** full constructor */
	public AbstractContratoNovedadCobro(Integer idContratoNovedadCobro,
			Contrato contrato, Persona persona, Short idNovedadTipo,
			Short periodoMes, Short periodoAnio, Short contratoCuota,
			Double monto, Date fechaVencimiento, Date fechaAlta, String observaciones,
			Short impuestoId, Short impuestoCuota, Short impuestoAnio) {
		
		this.idContratoNovedadCobro = idContratoNovedadCobro;
		this.contrato = contrato;
		this.persona = persona;
		this.idNovedadTipo = idNovedadTipo;
		this.periodoMes = periodoMes;
		this.periodoAnio = periodoAnio;
		this.contratoCuota = contratoCuota;
		this.monto = monto;
		this.fechaVencimiento = fechaVencimiento;
		this.fechaAlta = fechaAlta;
		this.observaciones = observaciones;
		this.impuestoId = impuestoId;
		this.impuestoCuota = impuestoCuota;
		this.impuestoAnio = impuestoAnio;
	}

	// Property accessors

	public Integer getIdContratoNovedadCobro() {
		return this.idContratoNovedadCobro;
	}

	public void setIdContratoNovedadCobro(Integer idContratoNovedadCobro) {
		this.idContratoNovedadCobro = idContratoNovedadCobro;
	}

	public Contrato getContrato() {
		return this.contrato;
	}

	public void setContrato(Contrato contrato) {
		this.contrato = contrato;
	}

	public Persona getPersona() {
		return this.persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public Short getIdNovedadTipo() {
		return this.idNovedadTipo;
	}

	public void setIdNovedadTipo(Short idNovedadTipo) {
		this.idNovedadTipo = idNovedadTipo;
	}

	public Short getPeriodoMes() {
		return this.periodoMes;
	}

	public void setPeriodoMes(Short periodoMes) {
		this.periodoMes = periodoMes;
	}

	public Short getPeriodoAnio() {
		return this.periodoAnio;
	}

	public void setPeriodoAnio(Short periodoAnio) {
		this.periodoAnio = periodoAnio;
	}

	public Short getContratoCuota() {
		return this.contratoCuota;
	}

	public void setContratoCuota(Short contratoCuota) {
		this.contratoCuota = contratoCuota;
	}

	public Double getMonto() {
		return this.monto;
	}

	public void setMonto(Double monto) {
		this.monto = monto;
	}

	public Date getFechaVencimiento() {
		return this.fechaVencimiento;
	}

	public void setFechaVencimiento(Date fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}

	public Date getFechaAlta() {
		return this.fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public String getObservaciones() {
		return this.observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public Short getImpuestoAnio() {
		return impuestoAnio;
	}

	public void setImpuestoAnio(Short impuestoAnio) {
		this.impuestoAnio = impuestoAnio;
	}

	public Short getImpuestoCuota() {
		return impuestoCuota;
	}

	public void setImpuestoCuota(Short impuestoCuota) {
		this.impuestoCuota = impuestoCuota;
	}

	public Short getImpuestoId() {
		return impuestoId;
	}

	public void setImpuestoId(Short impuestoId) {
		this.impuestoId = impuestoId;
	}
	
}