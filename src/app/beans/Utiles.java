package app.beans;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;

public class Utiles {

	public static float ParseFloat(String valor) {
		
		float respuesta = 0;
		
		if (valor.trim().length() != 0) {
		
			// Elimino todos los punto (el de los miles y el de los millones) si hubiese		
			valor = replace(valor,".", "");		
			valor = replace(valor,",", ".");
			
			respuesta = Float.valueOf(valor).floatValue();
		}
		
		return respuesta;
	}
	
	public static long ParseLong(String valor) {
		
		// Elimino todos los punto (el de los miles y el de los millones) si hubiere		
		valor = replace(valor,".", "");		
		valor = replace(valor,",", ".");
		
		return Long.valueOf(valor).longValue();
	}
	
	
	public static String replace(String _var, String _oldVal, String _newVal) {

		if (_var != null && !_var.equals("") && _oldVal != null && !_oldVal.equals("") && _newVal != null) {
			StringTokenizer st = new StringTokenizer(_var,_oldVal);
			String var = st.nextToken();
			while (st.hasMoreTokens()) {
				var = var + _newVal + st.nextToken() ;
			}

			return var;
		} else {
			return _var;
		}
	}
	
	/**
	 * Recibe una collection y la ordena devolviendo otra collection
	 * @param Collection
	 * @param Object
	 * @param int
	 * @return Collection
	 **/
	@SuppressWarnings("unchecked")
	public static Collection OrdenarCollection(Collection coleccion, Comparator oComparator){
		
		List listHT = new ArrayList(coleccion);
			
		Collections.sort(listHT, oComparator);
		
		Collection coleccion_ordenada = (Collection) listHT;
		
		return coleccion_ordenada;
	}
	
	
	public static String convertirMes(short mes_numero) {
		
		String mes = "";
		
		switch (mes_numero) {
			case 1: mes = "Enero";
					break;
			case 2: mes = "Febrero";
					break;
			case 3: mes = "Marzo";
					break;
			case 4: mes = "Abril";
					break;
			case 5: mes = "Mayo";
					break;
			case 6: mes = "Junio";
					break;
			case 7: mes = "Julio";
					break;
			case 8: mes = "Agosto";
					break;
			case 9: mes = "Septiembre";
					break;
			case 10: mes = "Octubre";
					break;
			case 11: mes = "Noviembre";
					break;
			case 12: mes = "Diciembre";
					break;					
		}
		
		return mes;
		
	}
	
	
	/**
	 *  Completa una String con el caracter "0" por la izquierda hasta una longitud size
	 */
	public static String complete(String _oldVal, int size) {
		String newVal = _oldVal.trim();
		int number = _oldVal.trim().length();
		if (number < size) {
			while (number < size){
				newVal = "0" +  newVal;
				number = newVal.length();
			}
			return newVal;
		} else {
			return _oldVal;
		}
	}
	
	
	/************************************************************************
	*  Devuelve el numero en letras
	*************************************************************************/
	public static String getNumberToString(double value) {

		int iCentavos = 0;
		String trab = "";
		if (value < 0){
			value = Math.abs(value);
			trab = trab + "(negativo) ";
		}
		iCentavos = (int)(value * 100 + 0.5) - (int)(value) * 100;
		value = (int) value;
		trab = trab + ALetras(value);
		String trab2 = trab.substring((trab.length() - 2),trab.length());
		if (trab2 == "un"){
			trab = trab + "o";
		}

		if (iCentavos > 0){
			trab = trab + " con " + ALetras(iCentavos) + " centavo";
			if (iCentavos > 1){
				trab = trab + "s";
			}
		}
		trab = trab + ".-";
		return trab;
	}

	/**
	*  Funcion privada es utilizada por GETNUMBERTOSTRING
	*/
	private static String ALetras(double _nNumber){
		int cant = 0;
		int resto = 0;
		int nNumber = (int) _nNumber;
		String trab = "";

		if (nNumber == 0) {
			trab="cero";

		} else if (nNumber < 20) {
			trab = Basicos(nNumber);

		} else if (nNumber < 100) {
			cant = (int) (nNumber / 10);
			resto = nNumber - cant * 10;
			if (resto == 0) {trab = DecenaSola(cant);}
			else {trab = Decena(cant) + ALetras(resto);}

		} else if (nNumber == 100) {
			trab = "cien";

		} else if (nNumber < 1000) {
			cant = (int)(nNumber / 100);
			resto = nNumber - cant * 100;
			trab = Centena(cant);
			if (resto > 0){trab = trab + " " + ALetras(resto);}

		} else if (nNumber == 1000){
			trab = "mil";

		} else if (nNumber < 2000){
			trab = "mil" + ALetras(nNumber - 1000);

		} else if (nNumber < 1000000){
			cant = (int)(nNumber / 1000);
			resto = nNumber - cant * 1000;
			trab = ALetras(cant) + " mil";
			if (resto > 0){ trab = trab + " " + ALetras(resto);}

		} else if (nNumber < 1000000000){
			cant = (int)(nNumber / 1000000);
			resto = nNumber - cant * 1000000;
			if (cant == 1) {trab = "un millón";}
			else{trab = ALetras(cant) + " millones";}
			if (resto > 0){trab = trab + " " + ALetras(resto);}

		} else {
			trab = "ERROR - Número muy grande -";
		}
		return trab;
	}


	/**
	*  Funcion privada es utilizada por GETNUMBERTOSTRING
	*/
	private static String Basicos(int i){
		String trab = "";
		String [] aBasicos = {"uno","dos","tres","cuatro","cinco","seis","siete","ocho","nueve","diez",
		"once","doce","trece","catorce","quince","dieciséis","diecisiete","dieciocho","diecinueve"};
		if ((i >= 1) && (i <= 19)){ trab = aBasicos[i - 1];}
		else {trab = "??";}
		return trab;
	}


	/**
	*  Funcion privada es utilizada por GETNUMBERTOSTRING
	*/
	private static String DecenaSola(int i){
		String trab = "";
		String [] aDecenas = {"veinte","treinta","cuarenta","cincuenta","sesenta","setenta","ochenta","noventa"};
		if ((i>=2) && (i<=9)){trab = aDecenas[i - 2];}
		else{trab = "??";}
		return trab;
	}


	/**
	*  Funcion privada es utilizada por GETNUMBERTOSTRING
	*/
	private static String Decena(int i){
		String trab = "";
		if (i == 2){trab = "veinti";}
		else if ((i>=3) && (i<=9)){trab = DecenaSola(i) + " y ";}
		else {trab = "??";}
		return trab;
	}


	/**
	*  Funcion privada es utilizada por GETNUMBERTOSTRING
	*/
	private static String Centena(int i){
		String trab = "";
		String [] aCentenas = {"ciento", "doscientos","trescientos","cuatrocientos","quinientos","seiscientos","setecientos","ochocientos","novecientos"};
		if ((i>=1) && (i<=9)){trab = aCentenas[i - 1];}
		else{trab = "??";}
		return trab;

	}
	
	public static short diferenciaEnMeses(Calendar gc1, Calendar gc2) {
		
		short diferencia = 1;
		
		// si el día desde es menor al de hasta, pasa en 1 la cuenta
		while ( gc1.before(gc2) ) {
			gc1.add(Calendar.MONTH, 1);
			diferencia++; 
		}
		
		
		int gc1_dia = gc1.get(Calendar.DAY_OF_MONTH);
		int gc2_dia = gc2.get(Calendar.DAY_OF_MONTH);
		if (gc1_dia < gc2_dia) {
			diferencia--;
		}
		
		return diferencia;
	}
	
	/**
	 * Calcula la diferencia en días entre la fecha actual y la fecha objetivo
	 * Si es negativo la respuesta es por que la fecha objetivo es inferior a la fecha en día 
	 * @param fechaObjetivo
	 * @return
	 */
	public static short diasQueFaltan(Calendar fechaObjetivo) {
		
		Calendar hoy = Calendar.getInstance();
		
		long fechaInicialMs = hoy.getTimeInMillis();
        long fechaFinalMs = fechaObjetivo.getTimeInMillis();
        long diferencia = fechaFinalMs - fechaInicialMs;
        double dias = Math.floor(diferencia / (1000 * 60 * 60 * 24));
        return ((short) dias);
		
	}
	
	
	
}
