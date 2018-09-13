package app.beans;

import java.io.Serializable;
import java.util.Date;

/**
 * Representa una cuota mensual de alquiler de un contrato
 * @author pablo
 *
 */
public class Cuota implements Serializable {

	private static final long serialVersionUID = -5761173662792598138L;
	
	// Atributos	

	private short cuota;
	private double valor;
	
	private short periodo_mes;
	private short periodo_anio;
	
	private Date fecha_vencimiento;

	// Constantes
	/* 	Por ahora voy a trabajar con una constante, mas adelante vamos a levantar este dato de la
	 	tabla SISTEMA */
	public static short dia_de_vencimiento = 10;
		
	public Cuota() {}

	public Cuota(short cuota, float valor) {
		this.cuota = cuota;
		this.valor = valor;
	}
	
	public Cuota(short cuota, double valor, short periodo_mes, short periodo_anio, Date fecha_vencimiento) {
		this.cuota = cuota;
		this.valor = valor;
		this.periodo_mes = periodo_mes;
		this.periodo_anio = periodo_anio;
		this.fecha_vencimiento = fecha_vencimiento;
	}

	
	
	public short getCuota() {
		return cuota;
	}

	public void setCuota(short cuota) {
		this.cuota = cuota;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public Date getFecha_vencimiento() {
		return fecha_vencimiento;
	}

	public void setFecha_vencimiento(Date fecha_vencimiento) {
		this.fecha_vencimiento = fecha_vencimiento;
	}

	public short getPeriodo_anio() {
		return periodo_anio;
	}

	public void setPeriodo_anio(short periodo_anio) {
		this.periodo_anio = periodo_anio;
	}

	public short getPeriodo_mes() {
		return periodo_mes;
	}

	public void setPeriodo_mes(short periodo_mes) {
		this.periodo_mes = periodo_mes;
	}
	
}