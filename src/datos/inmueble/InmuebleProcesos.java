package datos.inmueble;

import org.hibernate.Query;
import org.hibernate.Session;

import datos.Page;
import datos.SessionFactory;

public class InmuebleProcesos {

	@SuppressWarnings("unchecked")
	public static Page findByFilter(	String filtro_calle, 
										int filtro_edificio,
										short filtro_tipo,
										int filtro_propietario,
										int page_number, 
										int page_size) {
		
		//List<Inmueble> Inmuebles;
		
		Session oSessionH = SessionFactory.currentSession();
		
		Page pagina;
		try {
			String queryString = 	"SELECT model " +
									"FROM Inmueble model " +
									"WHERE UPPER(model.direccionCalle) LIKE :calleValue ";
									//"AND UPPER(model.direccionEdificio) LIKE :edificioValue ";
			
			if (filtro_tipo != 0) queryString += " AND model.inmuebleTipo.idInmuebleTipo = " + filtro_tipo;
			if (filtro_propietario != 0) queryString += " AND model.propietario.idPersona = " + filtro_propietario;
			if (filtro_edificio != 0) queryString += " AND model.edificio.idEdificio = " + filtro_edificio;
			
			queryString+= " ORDER BY model.direccionCalle, model.direccionNro, model.direccionPiso, model.direccionDpto ";
			
			filtro_calle += "%";			
			
			Query oQuery = oSessionH.createQuery(queryString);
			oQuery.setParameter("calleValue", "%" + filtro_calle.toUpperCase());
			//oQuery.setParameter("edificioValue", "%" + filtro_edificio.toUpperCase());
			
			pagina = new Page(oQuery, page_number, page_size);
			
			/*
			Inmuebles = oQuery.list();
			for(Inmueble obj: Inmuebles) {
				obj.getLocalidad().getDescripcion();				
				obj.getInmuebleTipo().getDescripcion();
				
				// Ojo que puede no venir -- NullPointerException
				try {
					obj.getPropietario().getApellido();
				} catch(NullPointerException npe) {}
			}
			*/
			
		} catch (RuntimeException re) {
			throw re;
		} finally {
			oSessionH.close();
		}		
		
		return pagina;
	} 
	
}
