package datos.contrato;

import java.util.Comparator;

public class ContratoComparator implements Comparator<Contrato> {

		private String comparar_columna;
		
		public ContratoComparator(String orden) {

			// Tengo que validar que la columna pasada sea válida ¿?
			comparar_columna = orden;
			
		}
		
		
		public int compare(Contrato o1, Contrato o2) {
			
			int resultado = 0;
	        
	        if (comparar_columna.equals("materia")){
	        	//resultado = o1.getOMateriaDTO().getMateria().compareToIgnoreCase(o2.getOMateriaDTO().getMateria());
	        }else if (comparar_columna.equals("plan")){
	        	//resultado = o1.getOMateriaDTO().getPropuestaDTO().getIdPropuestaPlanGuarani().compareToIgnoreCase(o2.getOMateriaDTO().getPropuestaDTO().getIdPropuestaPlanGuarani());
	        }else if (comparar_columna.equals("propuesta")){
	        	//resultado = o1.getOMateriaDTO().getPropuestaDTO().getPropuesta().compareToIgnoreCase(o2.getOMateriaDTO().getPropuestaDTO().getPropuesta());
	        }else{
	           	// Valor por defecto de la comparación
	        	resultado = o1.getFechaDesde().toString().compareToIgnoreCase(o2.getFechaDesde().toString());
	        }
	        
	        return resultado;
					
		}

//		 EN CASO DE QUEDER MODIFICAR EL EQUALS DE LOS OBJETOS
//		 public boolean equals(Object o1, Object o2) {
//			 return compare(o1, o2) == 0;
//		 }
		
}