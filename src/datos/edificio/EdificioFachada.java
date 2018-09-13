package datos.edificio;

import datos.BaseHibernateDAO;
import datos.SessionFactory;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;

/**
 * Data access object (DAO) for domain model class Edificio.
 * 
 * @see datos.edificio.Edificio
 * @author MyEclipse Persistence Tools
 */

public class EdificioFachada extends BaseHibernateDAO {
	
	private static final Log log = LogFactory.getLog(EdificioFachada.class);

	// property constants
	public static final String DESCRIPCION = "descripcion";

	public static final String OBSERVACIONES = "observaciones";

	public static final String DPTO_CANTIDAD = "dptoCantidad";

	public static void save(Edificio transientInstance) {
		
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

	public void delete(Edificio persistentInstance) {
		log.debug("deleting Edificio instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Edificio findById(java.lang.Integer id) {
		log.debug("getting Edificio instance with id: " + id);
		try {
			Edificio instance = (Edificio) getSession().get(
					"datos.prueba.Edificio", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Edificio instance) {
		log.debug("finding Edificio instance by example");
		try {
			List results = getSession().createCriteria("datos.prueba.Edificio")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Edificio instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Edificio as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByDescripcion(Object descripcion) {
		return findByProperty(DESCRIPCION, descripcion);
	}

	public List findByObservaciones(Object observaciones) {
		return findByProperty(OBSERVACIONES, observaciones);
	}

	public List findByDptoCantidad(Object dptoCantidad) {
		return findByProperty(DPTO_CANTIDAD, dptoCantidad);
	}

	@SuppressWarnings("unchecked")
	public static List<Edificio> findAll() {
		log.debug("finding all Edificio instances");
		Session oSessionH = SessionFactory.currentSession();
		List<Edificio> edificios;	
		try {
			String queryString = "from Edificio order by descripcion";
			Query queryObject = oSessionH.createQuery(queryString);
			edificios = queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
		
		return edificios;
	}

	public static Edificio merge(Edificio detachedInstance) {
		log.debug("merging Edificio instance");
		try {
			Edificio result = (Edificio) SessionFactory.currentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Edificio instance) {
		log.debug("attaching dirty Edificio instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Edificio instance) {
		log.debug("attaching clean Edificio instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}