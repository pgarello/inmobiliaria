package datos.factura;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import datos.persona.Persona;
import datos.usuario.Usuario;

/**
 * AbstractFactura generated by MyEclipse Persistence Tools
 */

public abstract class AbstractFactura implements java.io.Serializable {

	// Fields

	private Integer idFactura;

	private Integer numero;

	// A o B
	private Short facturaTipo;

	private Date fechaEmision;

	private Persona persona;

	// MONOTRIBUTO / R.I. / EXENTO / CONSUMIDOR FINAL
	private Short idInscripcionIva;

	private String cliente;

	// Concateno la responsabilidad con el CUIT �? MONOTRIBUTO 30-23726150-0
	private String cuitDni;

	private String domicilio;

	private Usuario usuario;

	//private Date fechaAlta;

	private Boolean anulada;
	
	private Set facturaItems = new HashSet(0);
	
	private String leyenda;
	
	// Constructors

	/** default constructor */
	public AbstractFactura() {}

	/** full constructor */
	public AbstractFactura(Integer numero, Short facturaTipo,
			Date fechaEmision, Persona oPersona, Short idInscripcionIva,
			String cliente, String cuitDni, String domicilio, Usuario usuario,
			/*Date fechaAlta,*/ Boolean anulada, Set facturaItems, String leyenda) {
		this.numero = numero;
		this.facturaTipo = facturaTipo;
		this.fechaEmision = fechaEmision;
		this.persona = oPersona;
		this.idInscripcionIva = idInscripcionIva;
		this.cliente = cliente;
		this.cuitDni = cuitDni;
		this.domicilio = domicilio;
		this.usuario = usuario;
		//this.fechaAlta = fechaAlta;
		this.anulada = anulada;
		this.facturaItems = facturaItems;
		this.leyenda = leyenda;
	}

	// Property accessors

	public Integer getIdFactura() {
		return this.idFactura;
	}

	public void setIdFactura(Integer idFactura) {
		this.idFactura = idFactura;
	}

	public Integer getNumero() {
		return this.numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public Short getFacturaTipo() {
		return this.facturaTipo;
	}

	public void setFacturaTipo(Short facturaTipo) {
		this.facturaTipo = facturaTipo;
	}

	public Date getFechaEmision() {
		return this.fechaEmision;
	}

	public void setFechaEmision(Date fechaEmision) {
		this.fechaEmision = fechaEmision;
	}

	public Persona getPersona() {
		return this.persona;
	}

	public void setPersona(Persona oPersona) {
		this.persona = oPersona;
	}

	public Short getIdInscripcionIva() {
		return this.idInscripcionIva;
	}

	public void setIdInscripcionIva(Short idInscripcionIva) {
		this.idInscripcionIva = idInscripcionIva;
	}

	public String getCliente() {
		return this.cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public String getCuitDni() {
		return this.cuitDni;
	}

	public void setCuitDni(String cuitDni) {
		this.cuitDni = cuitDni;
	}

	public String getDomicilio() {
		return this.domicilio;
	}

	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

//	public Date getFechaAlta() {
//		return this.fechaAlta;
//	}
//
//	public void setFechaAlta(Date fechaAlta) {
//		this.fechaAlta = fechaAlta;
//	}

	public Boolean getAnulada() {
		return anulada;
	}

	public void setAnulada(Boolean anulada) {
		this.anulada = anulada;
	}
	
	public Set getFacturaItems() {
		return this.facturaItems;
	}

	public void setFacturaItems(Set facturaItems) {
		this.facturaItems = facturaItems;
	}
	
	public String getLeyenda() {
		return this.leyenda;
	}

	public void setLeyenda(String leyenda) {
		this.leyenda = leyenda;
	}


}