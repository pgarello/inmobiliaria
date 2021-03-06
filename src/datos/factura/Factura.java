package datos.factura;

// Generated by MyEclipse Persistence Tools

import java.util.Date;
import java.util.Set;

import datos.persona.Persona;
import datos.usuario.Usuario;

import app.combos.ComboInscripcionIVA;

/**
 * Factura generated by MyEclipse Persistence Tools
 */
public class Factura extends AbstractFactura implements java.io.Serializable {

	private static final long serialVersionUID = 8220730849572761438L;

	// Constantes
	public static short facturaTipoA = 1;
	public static short facturaTipoB = 2;
	
	// Atributos Gestionados por la entidad
	private double total;
	
	public double getTotal() {
		return total;
	}
	
	public void setTotal(double total) {
		this.total = total;
	}
	
	// Constructors

	/** default constructor */
	public Factura() {
	}

	/** full constructor */
	public Factura(Integer numero, Short facturaTipo, Date fechaEmision,
			Persona oPersona, Short idInscripcionIva, String cliente,
			String cuitDni, String domicilio, Usuario usuario, /*Date fechaAlta,*/ 
			Boolean anulada, Set facturaItems, String leyenda) {
		
		super(numero, facturaTipo, fechaEmision, oPersona, idInscripcionIva,
				cliente, cuitDni, domicilio, usuario, /*fechaAlta,*/ anulada, facturaItems, leyenda);
		
	}
	
	
	public String getFacturaTipoLetras() {
		String respuesta = "B";
		if (this.getFacturaTipo() == Factura.facturaTipoA) {
			respuesta = "A";
		}
		return respuesta;
	}
	
	/**
	 * Devuelve FACTURA A (valor 1) o B (valor 2)
	 * @param idResponsabilidadIva
	 * @return
	 */
	public static short getFacturaTipoPorResponsabilidadIVA(short idResponsabilidadIva) {
		
		short respuesta = 1;
		
		if (idResponsabilidadIva == ComboInscripcionIVA.ConsumidorFinal ||
			idResponsabilidadIva == ComboInscripcionIVA.Exento ) {
			
			respuesta = 2;
			
		}
		return respuesta;
	}
	
	

}
