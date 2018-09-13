import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;

/**
 * Exportar "Duplicados Electrónicos"
 * Levanta el archivo CABECERA.txt 
 * graba el archivo cabecera.csv con los datos de las facturas
 * Falta agregarle el control con la última fila del archivo y los datos levantados
 * @author pablo
 *
 */
public class FacturaElectronica {

	/**
	  	@param args
	  	url www.afip.gov.ar
		27061396778
		alicia2021
		--> entrar a "comprobantes en línea"
		
		El archivo que lee es: CABECERA.txt
		
		Modificar las Ñ en el archivo descargado .txt (HEREÑU-MIÑO-PIÑON-CARREÑO) antes de procesarlo --> YA NO ES NECESARIO
	 */
	
    /**
     * Tengo que modificar el archivo VENTAS.txt
     * La columna a modificar es la 243-244 y hay que pisar el valor por una E cuando el campo fExento sea 0
     */

	
	public static void main(String[] args) {
		 
		FileWriter fichero = null, fichero2 = null;
		DataInputStream entrada = null, entrada2 = null;
		
		System.out.println("Prueba 1 -" + FacturaElectronica.completarConCerosIzq(1, 8) + "-");
        System.out.println("Prueba 2 -" + FacturaElectronica.completarConEspacioDer("Prueba", 20) + "-");
		
        String strLinea = "";
        String strLinea2 = "";
        
		try {
			
			fichero = new FileWriter("C:/Pablo/desarrollos/Inmobiliaria/Acuario/FacturasE/cabecera.csv");
			fichero2 = new FileWriter("C:/Pablo/desarrollos/Inmobiliaria/Acuario/FacturasE/VENTAS_2.txt");
			
			PrintWriter pw = new PrintWriter(fichero);
			PrintWriter pw2 = new PrintWriter(fichero2);
			
            // Abrimos el archivo
            FileInputStream fstream = new FileInputStream("C:\\Pablo\\desarrollos\\Inmobiliaria\\Acuario\\FacturasE\\CABECERA.txt");
            FileInputStream fstream2 = new FileInputStream("C:\\Pablo\\desarrollos\\Inmobiliaria\\Acuario\\FacturasE\\VENTAS.txt");
            
            // Creamos el objeto de entrada
            entrada = new DataInputStream(fstream);
            entrada2 = new DataInputStream(fstream2);
            
            // Creamos el Buffer de Lectura
            BufferedReader buffer = new BufferedReader(new InputStreamReader(entrada));
            BufferedReader buffer2 = new BufferedReader(new InputStreamReader(entrada2));
            
            short fila = 1;
            // Leer el archivo linea por linea
            while ((strLinea = buffer.readLine()) != null)   {
            	            	
            	String fecha = strLinea.substring(1, 9);
            	Calendar cal = null;
            	try {  
            		DateFormat formatter = new SimpleDateFormat("yyyyMMdd"); 
            		Date date = (Date)formatter.parse(fecha); 
            		cal= Calendar.getInstance();
            		cal.setTime(date);
            	} catch (ParseException e) {
            		System.out.println("Exception :"+e);  
            	}
            	
            	String comprobante = strLinea.substring(9, 11); // Si el comprobante es una NOTA DE CREDITO el monto resta y no suma !!!
            	String pto_venta = strLinea.substring(12, 16);
            	String numero = strLinea.substring(16, 24);
            	
            	String cod_documento = strLinea.substring(35, 37);
            	String cuit_cuil = strLinea.substring(37, 48);
            	String comprador = strLinea.substring(48, 78).trim();
            	
            	// Cuando viene la Ñ tengo que ajustar los índices en +1
            	int ajuste = 0;
            	if (comprador.contains("Ã‘")) {
            		ajuste = 1;
            		comprador = strLinea.substring(48, 79).trim();
            		comprador = comprador.replace("Ã‘", "Ñ");	
            	}
            	
            	String total_entero = strLinea.substring(78+ajuste, 91+ajuste); // 13 caracteres
            	String total_decimal = strLinea.substring(91+ajuste, 93+ajuste); // 2 caracteres
            	String total = total_entero + "." + total_decimal;
            	Float fTotal = new Float(total);
            	
            	String no_grav_entero = strLinea.substring(93+ajuste, 106+ajuste);
            	String no_grav_decimal = strLinea.substring(106+ajuste, 108+ajuste);
            	String no_grav = no_grav_entero + "." + no_grav_decimal;
            	Float fNo_grav = new Float(no_grav);
            	
            	String grav_entero = strLinea.substring(108+ajuste, 121+ajuste);
            	String grav_decimal = strLinea.substring(121+ajuste, 123+ajuste);
            	String grav = grav_entero + "." + grav_decimal;
            	Float fGrav = new Float(grav);
            	
            	String exento_entero = strLinea.substring(153+ajuste, 166+ajuste);
            	String exento_decimal = strLinea.substring(166+ajuste, 168+ajuste);
            	String exento = exento_entero + "." + exento_decimal;
            	Float fExento = new Float(exento);
            	
            	String CAI = strLinea.substring(260+ajuste, 274+ajuste);
            	
                // Imprimimos la línea por pantalla
            	DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            	DecimalFormat moneyFormat = new DecimalFormat("0.00");
            	
            	String alicuota_iva = "0000";
                String impuesto_liquidado = "";
                Double aux = new Double(0.0);
                if (fGrav.doubleValue() > 0f ) {
                	alicuota_iva = "2100";
                	aux = fGrav * 0.21;
                	
                	// ajuste por redondeo
                	Double control = fTotal - fNo_grav - fGrav - aux - fExento;
                	aux = aux + control;
                	
                }
            	
                if (FacturaElectronica.getComprobanteNC(comprobante)) {
                	fTotal = fTotal * -1;
                	fNo_grav = fNo_grav * -1;
                	fGrav = fGrav * -1;
                	aux = aux * -1;
                	fExento = fExento * -1;
                }
                
                String salida = fila + ";" +
                				formatter.format(cal.getTime()) + ";" +
                				FacturaElectronica.getComprobante(comprobante) + ";" + 
                				pto_venta + "-" + numero + ";" +
                				FacturaElectronica.getDocumento(cod_documento) + ";" + 
                				cuit_cuil + ";" +
                				comprador + ";" +
                				moneyFormat.format(fTotal) + ";" +
                				moneyFormat.format(fNo_grav) + ";" +
                				moneyFormat.format(fGrav) + ";" +
                				moneyFormat.format(aux) + ";" +
                				moneyFormat.format(fExento) + ";" +                				
                				CAI;                
                                                                                    
                //System.out.println("Aux " + aux);                
                impuesto_liquidado = FacturaElectronica.completarConCerosIzq( Integer.parseInt(FacturaElectronica.separarEnteroDecimal(aux, 2)), 15);                                
                
                String salidaTXT = 	"1" +
                					fecha +
                					comprobante +
                					" " +
                					pto_venta + 
                					FacturaElectronica.completarConCerosIzq(Integer.parseInt(numero), 20) +
                					FacturaElectronica.completarConCerosIzq(Integer.parseInt(numero), 20) +
                					cod_documento +
                					cuit_cuil +
                					FacturaElectronica.completarConEspacioDer(comprador, 30) +
                					total_entero + total_decimal + // 15 (100 .. 114)
                					no_grav_entero + no_grav_decimal +
                					grav_entero + grav_decimal +
                					alicuota_iva + // 4 caracteres '2100' - Puedo calcularlo en base a lo grabado ¿?
                					impuesto_liquidado + // 15 caracteres
                					FacturaElectronica.completarConCerosIzq(0, 15) + // 15 caracteres Impuesto liquidado a RNI ¿? 
                					exento_entero + exento_decimal + // importe exento 15 caracteres
                					FacturaElectronica.completarConEspacioDer("", 75) + // 75 espacios vacíos DESDE ORDER 18 al 24
                					"1" + // cantidad de alicuotas de IVA
                					FacturaElectronica.completarConEspacioDer("", 106) + // 106 espacios vacíos DESDE ORDER 26 al 30
                					FacturaElectronica.completarConCerosIzq(0, 8) +  // fecha_pago_retenciones
                					FacturaElectronica.completarConCerosIzq(0, 15) + // importe retenciones
                					"";
                
                System.out.println(salida);
                //System.out.println("Longitud " + salidaTXT.length() );
                
                pw.println(salida);
                //pw2.println(salidaTXT);
                
// proceso el 2º archivo
                
                strLinea2 = buffer2.readLine();
                
                // puede ser 0 o E
                String salida2 = strLinea2;
                String exento_caracter = strLinea2.substring(242, 243);
                if (alicuota_iva.equals("0000") && !exento_caracter.equals("E") ) {
                	System.out.println("MODIFICAR " + exento_caracter + " // " + strLinea2 );
                	salida2 = strLinea2.substring(0,242) + "E" + strLinea2.substring(243,strLinea2.length());
                }
                pw2.println(salida2);
// Fin proceso 2 
                
                System.out.println("---------------------------------------------------------------------------------");
                
                fila++;
            }
            // Cerramos el archivo
            entrada.close();
            entrada2.close();
            
            fichero.close();
            fichero2.close();
            
        }catch (Exception e){ //Catch de excepciones
        	e.printStackTrace();
            System.out.println(strLinea);
        	System.err.println("Ocurrio un error: " + e.getMessage());
        } finally {
        	
        	try {
 	           if (entrada != null) entrada.close();
 	           if (entrada2 != null) entrada2.close();
 	           if (null != fichero) fichero.close();
 	          if (null != fichero2) fichero2.close();
 	        } catch (Exception e2) {
 	        	e2.printStackTrace();
 	        }
        	
        }
		
	}
	
	private static String getComprobante(String codigo) {
		String respuesta = "";
		int iCodigo = Integer.parseInt(codigo) ;
		switch (iCodigo) {
		case 1:
			respuesta = "FACT A"; break;
		case 2:
			respuesta = "ND A"; break;
		case 3:
			respuesta = "NC A"; break;
		case 4:
			respuesta = "REC A"; break;
		case 5:
			respuesta = "NV A"; break;
		case 6:
			respuesta = "FACT B"; break;
		case 7:
			respuesta = "ND B"; break;
		case 8:
			respuesta = "NC B"; break;
		case 9:
			respuesta = "REC B"; break;
		case 10:
			respuesta = "NV B"; break;
			
		default:
			respuesta = "Comprobante ERROR:" + codigo;
			break;
		}
		
		return respuesta;
	}

	
	private static boolean getComprobanteNC(String codigo) {
		boolean respuesta = false;
		int iCodigo = Integer.parseInt(codigo) ;
		switch (iCodigo) {
			case 3:
				//respuesta = "NC A"; break;
				respuesta = true; break;
			case 8:
				//respuesta = "NC B"; break;
				respuesta = true; break;			
		}
		
		return respuesta;
	}

	
	
	private static String getDocumento(String codigo) {
		String respuesta = "";
		int iCodigo = Integer.parseInt(codigo) ;
		switch (iCodigo) {
		case 80:
			respuesta = "CUIT"; break;
		case 86:
			respuesta = "CUIL"; break;
		case 87:
			respuesta = "CDI"; break;
		case 89:
			respuesta = "LE"; break;
		case 90:
			respuesta = "LC"; break;
		case 96:
			respuesta = "DNI"; break;
		case 94:
			respuesta = "PASP"; break;		
			
		default:
			respuesta = "Documento ERROR:" + codigo;
			break;
		}
		
		return respuesta;
	}
	
	private static String completarConEspacioDer(String palabra, int cantidad) {
		String salida = "";
		
		String formato = "%1$-" + cantidad + "s";
		salida = String.format(formato, palabra);
		
		return salida;
	}
	
	private static String completarConCerosIzq(int numero, int cantidad) {
		String salida = "";
		
		Formatter fmt = new Formatter();
		String formato = "%0" + cantidad + "d";
		salida = fmt.format(formato,numero).toString();
		
		return salida;
	}

	private static String separarEnteroDecimal(Double numero, int decimales) {
		String respuesta = "";

        BigDecimal totalAUX = new BigDecimal(numero);
        totalAUX = totalAUX.setScale(2, RoundingMode.HALF_UP);
		
		String aux = totalAUX.toString();
		String entero = aux.substring(0, aux.indexOf("."));
		String decimal = FacturaElectronica.completarConCerosIzq( Integer.parseInt(aux.substring(aux.indexOf(".")+1, aux.length())) , decimales);
		
		respuesta = entero + decimal;
		
		return respuesta;
		
	}
	
} // fin class