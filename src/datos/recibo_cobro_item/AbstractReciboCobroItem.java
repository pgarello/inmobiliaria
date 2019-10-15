package datos.recibo_cobro_item;

import datos.contrato_novedad_cobro.ContratoNovedadCobro;
import datos.recibo_cobro.ReciboCobro;

/**
 * AbstractReciboCobroItem generated by MyEclipse Persistence Tools
 */

public abstract class AbstractReciboCobroItem implements java.io.Serializable {

	// Fields

	private Integer idReciboCobroItem;

	private ReciboCobro reciboCobro;

	private ContratoNovedadCobro contratoNovedadCobro;

	private Double monto;

	private String descripcion;

	private Short idItemTipo;

	// Constructors

	/** default constructor */
	public AbstractReciboCobroItem() {
	}

	/** minimal constructor */
	public AbstractReciboCobroItem(Integer idReciboCobroItem,
			ReciboCobro reciboCobro, Double monto, Short idItemTipo) {
		this.idReciboCobroItem = idReciboCobroItem;
		this.reciboCobro = reciboCobro;
		this.monto = monto;
		this.idItemTipo = idItemTipo;
	}

	/** full constructor */
	public AbstractReciboCobroItem(Integer idReciboCobroItem,
			ReciboCobro reciboCobro, ContratoNovedadCobro contratoNovedadCobro,
			Double monto, String descripcion, Short idItemTipo) {
		this.idReciboCobroItem = idReciboCobroItem;
		this.reciboCobro = reciboCobro;
		this.contratoNovedadCobro = contratoNovedadCobro;
		this.monto = monto;
		this.descripcion = descripcion;
		this.idItemTipo = idItemTipo;
	}

	// Property accessors

	public Integer getIdReciboCobroItem() {
		return this.idReciboCobroItem;
	}

	public void setIdReciboCobroItem(Integer idReciboCobroItem) {
		this.idReciboCobroItem = idReciboCobroItem;
	}

	public ReciboCobro getReciboCobro() {
		return this.reciboCobro;
	}

	public void setReciboCobro(ReciboCobro reciboCobro) {
		this.reciboCobro = reciboCobro;
	}

	public ContratoNovedadCobro getContratoNovedadCobro() {
		return this.contratoNovedadCobro;
	}

	public void setContratoNovedadCobro(ContratoNovedadCobro contratoNovedadCobro) {
		this.contratoNovedadCobro = contratoNovedadCobro;
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

}