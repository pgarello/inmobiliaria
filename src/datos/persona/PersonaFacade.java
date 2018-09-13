package datos.persona;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import datos.SessionFactory;



public class PersonaFacade {
	
	// property constants
	public static final String USUARIO = "usuario";

	public static final String CLAVE = "clave";

	public static final String DESCRIPCION = "descripcion";

	public static void save(Persona transientInstance) throws RuntimeException {
		Session oSessionH = SessionFactory.currentSession();
		Transaction tx = oSessionH.beginTransaction();
		try {
			
			// Antes de grabar le asigno la fecha de alta
			
			// Valida unicidad por numero de documento
			
			oSessionH.saveOrUpdate(transientInstance);
			tx.commit();
		} catch (RuntimeException re) {
			tx.rollback();
			throw re;			
		} finally {
			oSessionH.close();
		}
		
	}

	public static void delete(Persona persistentInstance) {
		Session oSessionH = SessionFactory.currentSession();
		Transaction tx = oSessionH.beginTransaction();
		try {
			oSessionH.delete(persistentInstance);
			tx.commit();
		} catch (RuntimeException re) {
			tx.rollback();
			throw re;
		} finally {
			oSessionH.close();
		}
	}

	public static Persona update(Persona detachedInstance) {
		Session oSessionH = SessionFactory.currentSession();
		Transaction tx = oSessionH.beginTransaction();
		Persona result = null;
		try {
			result = (Persona)oSessionH.merge(detachedInstance);
			tx.commit();
		} catch (RuntimeException re) {
			tx.rollback();
			throw re;
		} finally {
			oSessionH.close();
		}
		return result;
	}

	public static Persona findById(Integer id) {
		Session oSessionH = SessionFactory.currentSession();
		Persona result = null;
		try {
			result = (Persona) oSessionH.get(Persona.class, id);		
		} catch (RuntimeException re) {			
			throw re;
		} finally {
			oSessionH.close();
		}
		return result;
	}

	
	@SuppressWarnings("unchecked")
	public static List<Persona> findByProperty(String propertyName, Object value) {
		Session oSessionH = SessionFactory.currentSession();
		List<Persona> Personas;
		try {
			String queryString = "select model from Persona model where model." + propertyName + "= :propertyValue";
			Personas = oSessionH.createQuery(queryString).setParameter("propertyValue", value).list();
		} catch (RuntimeException re) {
			throw re;
		} finally {
			oSessionH.close();
		}
		return Personas;
	}

	public List<Persona> findByPersona(Object Persona) {
		return findByProperty(USUARIO, Persona);
	}

	public List<Persona> findByClave(Object clave) {
		return findByProperty(CLAVE, clave);
	}

	public List<Persona> findByDescripcion(Object descripcion) {
		return findByProperty(DESCRIPCION, descripcion);
	}

	@SuppressWarnings("unchecked")
	public static List<Persona> findAll() {
		Session oSessionH = SessionFactory.currentSession();
		List<Persona> Personas;		
		try {
			String queryString = "select model from Persona model";
			Personas = oSessionH.createQuery(queryString).list();
		} catch (RuntimeException re) {
			throw re;
		} finally {
			oSessionH.close();
		}
		return Personas;
	}

	
}