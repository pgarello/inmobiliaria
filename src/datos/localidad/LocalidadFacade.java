package datos.localidad;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import datos.SessionFactory;



public class LocalidadFacade {
	
	// property constants
	
	/*
	public static final String USUARIO = "usuario";

	public static final String CLAVE = "clave";

	public static final String DESCRIPCION = "descripcion";
	*/

	
	public static void save(Localidad transientInstance) throws RuntimeException {
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
	

	
	public static void delete(Localidad persistentInstance) {
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
	

	
	public static Localidad update(Localidad detachedInstance) {
		Session oSessionH = SessionFactory.currentSession();
		Transaction tx = oSessionH.beginTransaction();
		Localidad result = null;
		try {
			result = (Localidad)oSessionH.merge(detachedInstance);
			tx.commit();
		} catch (RuntimeException re) {
			tx.rollback();
			throw re;
		} finally {
			oSessionH.close();
		}
		return result;
	}
	

	/*
	public static usuario findById(Integer id) {
		Session oSessionH = SessionFactory.currentSession();
		usuario result = null;
		try {
			result = (usuario) oSessionH.get(usuario.class, id);		
		} catch (RuntimeException re) {			
			throw re;
		} finally {
			oSessionH.close();
		}
		return result;
	}
	 */
	
	/*
	@SuppressWarnings("unchecked")
	public static List<localidad> findByProperty(String propertyName, Object value) {
		Session oSessionH = SessionFactory.currentSession();
		List<usuario> usuarios;
		try {
			String queryString = "select model from Usuario model where model." + propertyName + "= :propertyValue";
			usuarios = oSessionH.createQuery(queryString).setParameter("propertyValue", value).list();
		} catch (RuntimeException re) {
			throw re;
		} finally {
			oSessionH.close();
		}
		return usuarios;
	}

	public List<usuario> findByUsuario(Object usuario) {
		return findByProperty(USUARIO, usuario);
	}

	public List<usuario> findByClave(Object clave) {
		return findByProperty(CLAVE, clave);
	}

	public List<usuario> findByDescripcion(Object descripcion) {
		return findByProperty(DESCRIPCION, descripcion);
	}
	*/

	@SuppressWarnings("unchecked")
	public static List<Localidad> findAll() {
		
		System.out.println("LocalidadFacade.findAll - entrada");
		
		Session oSessionH = SessionFactory.currentSession();
		List<Localidad> localidades;		
		try {
			String queryString = "SELECT model FROM Localidad model ORDER BY descripcion ";
			localidades = oSessionH.createQuery(queryString).list();
		} catch (RuntimeException re) {
			throw re;
		} finally {
			oSessionH.close();
		}
		
		System.out.println("LocalidadFacade.findAll - salida:" + localidades.size());
		
		return localidades;
	}
	
}