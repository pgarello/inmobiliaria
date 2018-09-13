package app.beans;

/**
 * Difiere en las NovedadTipo en que puede tener tipos de items que surgen al momento del
 * alta de un recibo (COBRO / PAGO). Por ejemplo INTERESES
 * 
 * ¿ Podría extender la clase NovedadTipo y agregar los nuevos movimientos ?
 * 
 * @author pablo
 *
 */

public class ItemTipo {

	public static short ItemAlquiler = 1;
	
	public static short ItemComisionAlquiler = 2; // se cobra al propietario
	public static short ItemComisionContrato = 3; // se le cobra al inquilino
	public static short ItemComisionVenta = 4;
	
	public static short ItemImpuestos = 5;
	public static short ItemIVA = 6;
	public static short ItemIntereses = 7;
	public static short ItemVarios = 8;
	
	public static short ItemComisionRescision = 9; // se le cobra al inquilino
	
	public static short ItemRetencionGanancia = 10; // se le retiene al propietario

	public static String getDescripcion(short id_item_tipo) {
		
		String descripcion = "";
		
		switch (id_item_tipo) {
			case 1: descripcion = "Alquiler"; break;
			case 2: descripcion = "Comisión Alquiler"; break;
			case 3: descripcion = "Comisión Contrato"; break;
			case 4: descripcion = "Comisión Venta"; break;
			case 5: descripcion = "Impuestos"; break;
			case 6: descripcion = "IVA"; break;
			case 7: descripcion = "Intereses"; break;
			case 8: descripcion = "Varios"; break;
			case 9: descripcion = "Comisión Rescisión"; break;
			case 10: descripcion = "Retención GANANCIA"; break;
		}		
		
		return descripcion;
		
	}
	
}