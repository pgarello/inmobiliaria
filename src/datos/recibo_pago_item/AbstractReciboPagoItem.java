package datos.recibo_pago_item;

import datos.contrato_novedad_pago.ContratoNovedadPago;

import datos.recibo_pago.ReciboPago;

/**
 * AbstractReciboPagoItem generated by MyEclipse Persistence Tools
 */

public abstract class AbstractReciboPagoItem implements java.io.Serializable {

	// Fields

	private Integer idReciboPagoItem;

	private ReciboPago reciboPago;
	
	private ContratoNovedadPago contratoNovedadPago;

	private Integer idNovedad;

	private Double monto;

	private String descripcion;

	private Short idItemTipo;

	// Constructors

	/** default constructor */
	public AbstractReciboPagoItem() {
	}

	/** minimal constructor */
	public AbstractReciboPagoItem(	ReciboPago reciboPago, 
									Double monto,
									Short idItemTipo) {
		this.reciboPago = reciboPago;
		this.monto = monto;
		this.idItemTipo = idItemTipo;
	}

	/** full constructor */
	public AbstractReciboPagoItem(	ReciboPago reciboPago, 
									Integer idNovedad,
									ContratoNovedadPago contratoNovedadPago,
									Double monto, String descripcion, Short idItemTipo) {
		
		this.reciboPago = reciboPago;
		this.idNovedad = idNovedad;
		this.contratoNovedadPago = contratoNovedadPago;
		this.monto = monto;
		this.descripcion = descripcion;
		this.idItemTipo = idItemTipo;
	}

	// Property accessors

	public Integer getIdReciboPagoItem() {
		return this.idReciboPagoItem;
	}

	public void setIdReciboPagoItem(Integer idReciboPagoItem) {
		this.idReciboPagoItem = idReciboPagoItem;
	}


	public ReciboPago getReciboPago() {
		return this.reciboPago;
	}

	public void setReciboPago(ReciboPago reciboPago) {
		this.reciboPago = reciboPago;
	}
	

	public Integer getIdNovedad() {
		return this.idNovedad;
	}

	public void setIdNovedad(Integer idNovedad) {
		this.idNovedad = idNovedad;
	}

	public Double getMonto() {
		return this.monto;
	}

	public void setMonto(Double monto) {
		this.monto = monto;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Short getIdItemTipo() {
		return this.idItemTipo;
	}

	public void setIdItemTipo(Short idItemTipo) {
		this.idItemTipo = idItemTipo;
	}

	public ContratoNovedadPago getContratoNovedadPago() {
		return this.contratoNovedadPago;
	}

	public void setContratoNovedadPago(ContratoNovedadPago contratoNovedadPago) {
		this.contratoNovedadPago = contratoNovedadPago;
	}
	
}