package datos.persona;

import org.hibernate.Query;
import org.hibernate.Session;

import datos.Page;
import datos.SessionFactory;
import datos.contrato_actor.ContratoActor;

public class PersonaProcesos {

	@SuppressWarnings("unchecked")
	public static Page findByFilter(	String filtro_apellido, 
										String filtro_nombre,
										short filtro_actor,
										int page_number, 
										int page_size) {
		
		//List<Persona> Personas = PersonaFacade.findAll();
		
		Session oSessionH = SessionFactory.currentSession();
		
		
		//List<Persona> Personas;
		Page pagina;
		try {
			String queryString = 	"SELECT DISTINCT model " +
									"FROM Persona model ";
									//"JOIN model.localidad loc ";
									
			if (filtro_actor == ContratoActor.ActorTipoPropietario)	{
				queryString += 		", Inmueble i ";
				
			} else if (filtro_actor == ContratoActor.ActorTipoInquilino) {
				queryString += 		", ContratoActor ca, Contrato c ";
			}
			
			queryString += 			"WHERE ";
			
			if (filtro_actor == ContratoActor.ActorTipoPropietario){
				queryString += 		" model.idPersona = i.propietario.idPersona AND ";
				
			} else if (filtro_actor == ContratoActor.ActorTipoInquilino) {
				queryString += 		" model.idPersona = ca.id.persona.idPersona AND " +
									" ca.id.contrato = c AND " +
									" ca.id.idActorTipo = " + ContratoActor.ActorTipoInquilino + " AND ";
			}
			
			queryString +=			" ( UPPER(model.apellido) LIKE :apellidoValue OR UPPER(model.razonSocial) LIKE :razonSocialValue ";
			if (filtro_apellido.equals("")) {queryString += " OR model.apellido IS NULL";}
			
			queryString += ") AND (UPPER(model.nombres) LIKE :nombresValue";
			if (filtro_nombre.equals("")) {queryString += " OR model.nombres IS NULL";}
			
			queryString += ") ORDER BY model.apellido, model.nombres";
			
			filtro_apellido += "%";
			filtro_nombre += "%";
			
			
			Query consulta = oSessionH.createQuery(queryString);
			consulta.setParameter("apellidoValue", filtro_apellido.toUpperCase());
			consulta.setParameter("nombresValue", filtro_nombre.toUpperCase());
			consulta.setParameter("razonSocialValue", filtro_apellido.toUpperCase());
			
			pagina = new Page(consulta, page_number, page_size);
			
			//int offset = (page_number-1)*page_size; 
			//consulta.setFirstResult(offset); // en que posición arranco
			//consulta.setMaxResults(page_size);
			
			//Personas = consulta.list();
			
			/*
			Personas = pagina.getList();
			for(Persona obj: Personas) {
				oSessionH.refresh(obj);				 
				obj.getLocalidad().getDescripcion();
			}
			*/
			
		} catch (RuntimeException re) {
			throw re;
		} finally {
			oSessionH.close();
		}
		
		/*
		try {
			
			//Transaction tx = oSessionH.beginTransaction();
			
			// Recorro la colección y voy completando la localidad - dentro de la sesion
			// System.out.println("DATOS a .... " + Personas.size());
			Iterator iPersonas = Personas.iterator(); 
			while(iPersonas.hasNext()) {				
				
				Persona obj = (Persona)iPersonas.next();
				oSessionH.refresh(obj);
 
				obj.getLocalidad().getDescripcion();
				// System.out.println("DATOS b .... " + obj.getLocalidad().getDescripcion());
				
			}
			
			//tx.commit();
			
		} catch (RuntimeException re) {
			re.printStackTrace();
			throw re;
		} finally {
			oSessionH.close();
		}
		*/
		
		return pagina;
	} 
	
}
