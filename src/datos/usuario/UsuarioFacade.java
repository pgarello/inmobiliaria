package datos.usuario;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import datos.SessionFactory;



public class UsuarioFacade {
	
	// property constants
	public static final String USUARIO = "usuario";

	public static final String CLAVE = "clave";

	public static final String DESCRIPCION = "descripcion";

	public static void save(Usuario transientInstance) throws RuntimeException {
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

	public static void delete(Usuario persistentInstance) {
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

	public static Usuario update(Usuario detachedInstance) {
		Session oSessionH = SessionFactory.currentSession();
		Transaction tx = oSessionH.beginTransaction();
		Usuario result = null;
		try {
			result = (Usuario)oSessionH.merge(detachedInstance);
			tx.commit();
		} catch (RuntimeException re) {
			tx.rollback();
			throw re;
		} finally {
			oSessionH.close();
		}
		return result;
	}

	public static Usuario findById(Integer id) {
		Session oSessionH = SessionFactory.currentSession();
		Usuario result = null;
		try {
			result = (Usuario) oSessionH.get(Usuario.class, id);		
		} catch (RuntimeException re) {			
			throw re;
		} finally {
			oSessionH.close();
		}
		return result;
	}

	
	@SuppressWarnings("unchecked")
	public static List<Usuario> findByProperty(String propertyName, Object value) {
		Session oSessionH = SessionFactory.currentSession();
		List<Usuario> usuarios;
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

	public List<Usuario> findByUsuario(Object usuario) {
		return findByProperty(USUARIO, usuario);
	}

	public List<Usuario> findByClave(Object clave) {
		return findByProperty(CLAVE, clave);
	}

	public List<Usuario> findByDescripcion(Object descripcion) {
		return findByProperty(DESCRIPCION, descripcion);
	}

	@SuppressWarnings("unchecked")
	public static List<Usuario> findAll() {
		Session oSessionH = SessionFactory.currentSession();
		List<Usuario> usuarios;		
		try {
			String queryString = "select model from Usuario model";
			usuarios = oSessionH.createQuery(queryString).list();
		} catch (RuntimeException re) {
			throw re;
		} finally {
			oSessionH.close();
		}
		return usuarios;
	}
	
	
	@SuppressWarnings("unchecked")
	public static Usuario findByLogin(Object valueUsuario, Object valueClave) {
		Session oSessionH = SessionFactory.currentSession();
		Transaction tx = oSessionH.beginTransaction();
		Usuario oUsuario;
		try {
			String queryString = 	"SELECT model " +
									"FROM Usuario model " +
									"WHERE " +
									"model.usuario = :propertyValueUsuario AND " +
									"model.clave = :propertyValueClave";
			
			//oUsuarios = oSessionH.createQuery(queryString).setParameter("propertyValue", value).list();
			
			Query consulta = oSessionH.createQuery(queryString);
			consulta.setParameter("propertyValueUsuario", valueUsuario);
			consulta.setParameter("propertyValueClave", valueClave);
			oUsuario = (Usuario) consulta.uniqueResult();
			
			tx.commit();
			
		} catch (RuntimeException re) {
			tx.rollback();
			throw re;
		} finally {
			oSessionH.close();
		}
		return oUsuario;
	}

	
}