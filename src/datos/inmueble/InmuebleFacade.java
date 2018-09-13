package datos.inmueble;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import datos.SessionFactory;



public class InmuebleFacade {
	
	// property constants para las busquedas
	public static final String USUARIO = "usuario";
	public static final String CLAVE = "clave";
	public static final String DESCRIPCION = "descripcion";

	
	public static void save(Inmueble transientInstance) throws RuntimeException {
		Session oSessionH = SessionFactory.currentSession();
		Transaction tx = oSessionH.beginTransaction();
		try {			
			oSessionH.saveOrUpdate(transientInstance);
			tx.commit();
		} catch (RuntimeException re) {
			tx.rollback();
			throw re;			
		} finally {
			oSessionH.close();
		}
		
	}

	public static void delete(Inmueble persistentInstance) {
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

	public static Inmueble update(Inmueble detachedInstance) {
		Session oSessionH = SessionFactory.currentSession();
		Transaction tx = oSessionH.beginTransaction();
		Inmueble result = null;
		try {
			result = (Inmueble)oSessionH.merge(detachedInstance);
			tx.commit();
		} catch (RuntimeException re) {
			tx.rollback();
			throw re;
		} finally {
			oSessionH.close();
		}
		return result;
	}

	public static Inmueble findById(Integer id) {
		Session oSessionH = SessionFactory.currentSession();
		Inmueble result = null;
		try {
			result = (Inmueble) oSessionH.get(Inmueble.class, id);		
		} catch (RuntimeException re) {			
			throw re;
		} finally {
			oSessionH.close();
		}
		return result;
	}

	
	@SuppressWarnings("unchecked")
	public static List<Inmueble> findByProperty(String propertyName, Object value) {
		Session oSessionH = SessionFactory.currentSession();
		List<Inmueble> Inmuebles;
		try {
			String queryString = "select model from Inmueble model where model." + propertyName + "= :propertyValue";
			Inmuebles = oSessionH.createQuery(queryString).setParameter("propertyValue", value).list();
		} catch (RuntimeException re) {
			throw re;
		} finally {
			oSessionH.close();
		}
		return Inmuebles;
	}

	public List<Inmueble> findByInmueble(Object Inmueble) {
		return findByProperty(USUARIO, Inmueble);
	}

	public List<Inmueble> findByClave(Object clave) {
		return findByProperty(CLAVE, clave);
	}

	public List<Inmueble> findByDescripcion(Object descripcion) {
		return findByProperty(DESCRIPCION, descripcion);
	}

	@SuppressWarnings("unchecked")
	public static List<Inmueble> findAll() {
		Session oSessionH = SessionFactory.currentSession();
		List<Inmueble> Inmuebles;		
		try {
			String queryString = "select model from Inmueble model";
			Inmuebles = oSessionH.createQuery(queryString).list();
		} catch (RuntimeException re) {
			throw re;
		} finally {
			oSessionH.close();
		}
		
		System.out.println("InmuebleFacade.findAll: " + Inmuebles.size());
		
		return Inmuebles;
	}

	
	@SuppressWarnings("unchecked")
	public static List<InmuebleTipo> findAllInmuebleTipo() {
		Session oSessionH = SessionFactory.currentSession();
		List<InmuebleTipo> InmueblesTipo;		
		try {
			String queryString = "select model from InmuebleTipo model";
			InmueblesTipo = oSessionH.createQuery(queryString).list();
		} catch (RuntimeException re) {
			throw re;
		} finally {
			oSessionH.close();
		}
		
		System.out.println("InmuebleFacade.findAllInmuebleTipo: " + InmueblesTipo.size());
		
		return InmueblesTipo;
	}
	
	
}