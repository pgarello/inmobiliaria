package app.beans;

/**
 * Estos tipos surgen en los movimientos de un CONTRATO y son los tipos de NOVEDADES que se
 * generan
 * 
 * @author pablo
 *
 */
public class NovedadTipo {

	public static short Alquiler = 1;
	public static short ComisionAlquiler = 2; // se cobra al propietario
	public static short ComisionContrato = 3; // se le cobra al inquilino
	public static short ComisionVenta = 4; // se les cobra ¿?
	public static short Impuesto = 5; // se les cobra a ambos
	public static short IVA = 6;
	public static short ComisionRescision = 7;

	public static String getDescripcion(short id_novedad_tipo) {
		
		String descripcion = "";
		
		switch (id_novedad_tipo) {
			case 1: descripcion = "Alquiler"; break;
			case 2: descripcion = "Comisión Alquiler"; break;
			case 3: descripcion = "Comisión Contrato"; break;
			case 4: descripcion = "Comisión Venta"; break;
			case 5: descripcion = "Impuesto"; break;
			case 6: descripcion = "IVA"; break;
			case 7: descripcion = "Comisión Rescisión"; break;
		}
				
		return descripcion;		
	}
	
	public static String getDescripcionCorta(short id_novedad_tipo) {
		
		String descripcion = "";
		
		switch (id_novedad_tipo) {
			case 1: descripcion = "Alquiler"; break;
			case 2: descripcion = "Comisión"; break;
			case 3: descripcion = "Comisión"; break;
			case 4: descripcion = "Comisión"; break;
			case 5: descripcion = "Impuesto"; break;
			case 6: descripcion = "IVA"; break;
			case 7: descripcion = "Comisión"; break;
		}		
		return descripcion;
	}
	
}
