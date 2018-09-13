package datos.contrato;

import java.util.Date;

import datos.persona.Persona;

/**
 * Representa tanto los datos de ContratoNovedadPago como los de ContratoNovedadCobro
 * Se utiliza para poder listar ambas colecciones tratándolas como un único objeto
 * @author pablo
 *
 * Como se a quien impacta, ¿al propietario o al inquilino?
 *
 */

public class ContratoNovedadDTO {

	private Contrato contrato;
	private Persona persona;
	
	// A que movimiento (cuota) del contrato esta asociada la NOVEDAD (impuesto)
	private Short periodoMes;
	private Short periodoAnio;
	private Short contratoCuota;
	
	private Double monto_propietario;
	private Double monto_inquilino;
	
	private Date fechaVencimiento;
	
	private String observaciones;
	
	// Datos de impuesto en caso de que sea una NOVEDAD de este tipo
	private Short impuestoId;
	private Short impuestoCuota;
	private Short impuestoAnio;
	
	
	// CONSTRUCTOR
	
	public ContratoNovedadDTO(	Contrato contrato,
								Short periodoMes, 
								Short periodoAnio, 
								Short contratoCuota, 
								double monto_propietario,
								double monto_inquilino,
								Date fechaVencimiento, 
								String observaciones, 
								short impuestoId, 
								short impuestoCuota, 
								short impuestoAnio) {
		
		this.contrato = contrato;
		this.periodoMes = periodoMes;
		this.periodoAnio = periodoAnio;
		this.contratoCuota = contratoCuota;
		this.monto_propietario = monto_propietario;
		this.monto_inquilino = monto_inquilino;
		this.fechaVencimiento = fechaVencimiento;
		this.observaciones = observaciones;
		this.impuestoId = impuestoId;
		this.impuestoCuota = impuestoCuota;
		this.impuestoAnio = impuestoAnio;
	}

	// METODOS GET
	
	public Contrato getContrato() {
		return contrato;
	}

	public Short getContratoCuota() {
		return contratoCuota;
	}

	public Date getFechaVencimiento() {
		return fechaVencimiento;
	}

	public Short getImpuestoAnio() {
		return impuestoAnio;
	}

	public Short getImpuestoCuota() {
		return impuestoCuota;
	}

	public Short getImpuestoId() {
		return impuestoId;
	}

	public Double getMonto_propietario() {
		return monto_propietario;
	}
	
	public Double getMonto_inquilino() {
		return monto_inquilino;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public Short getPeriodoAnio() {
		return periodoAnio;
	}

	public Short getPeriodoMes() {
		return periodoMes;
	}

	public Persona getPersona() {
		return persona;
	}
	
	
	
	
}