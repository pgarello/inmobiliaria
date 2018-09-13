package datos.recibo_cobro_item;

import java.util.Comparator;

public class ReciboCobroItemComparator implements Comparator<ReciboCobroItem> {

		private String comparar_columna;
		
		public ReciboCobroItemComparator(String orden) {

			// Tengo que validar que la columna pasada sea válida ¿?
			comparar_columna = orden;
			
		}
		
		
		public int compare(ReciboCobroItem o1, ReciboCobroItem o2) {
			
			// Primero evaluo el año
	       	int resultado = o1.getContratoNovedadCobro().getPeriodoAnio().compareTo(o2.getContratoNovedadCobro().getPeriodoAnio());	        
	        
	       	// Si son iguales evaluo el mes
	       	if (resultado == 0) {
	       		resultado = o1.getContratoNovedadCobro().getPeriodoMes().compareTo(o2.getContratoNovedadCobro().getPeriodoMes());
	       	}
	       	
	        return resultado;
					
		}

//		 EN CASO DE QUEDER MODIFICAR EL EQUALS DE LOS OBJETOS
//		 public boolean equals(Object o1, Object o2) {
//			 return compare(o1, o2) == 0;
//		 }
		
}