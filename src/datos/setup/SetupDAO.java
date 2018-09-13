package datos.setup;

import datos.BaseHibernateDAO;
import datos.SessionFactory;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Example;

/**
 * Data access object (DAO) for domain model class Setup.
 * 
 * @see datos.setup.Setup
 * @author MyEclipse Persistence Tools
 */

public class SetupDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(SetupDAO.class);

	// property constants
	public static final String TABLA = "tabla";
	public static final String ID_FACTURERO = "idFacturero";
	public static final String INDICE = "indice";
	public static final String OBSERVACIONES = "observaciones";

	public void save(Setup transientInstance) {
		log.debug("saving Setup instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Setup persistentInstance) {
		log.debug("deleting Setup instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Setup findById(java.lang.Integer id) {
		log.debug("getting Setup instance with id: " + id);
		try {
			Setup instance = (Setup) getSession().get("datos.prueba.Setup", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Setup instance) {
		log.debug("finding Setup instance by example");
		try {
			List results = getSession().createCriteria("datos.prueba.Setup")
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
		log.debug("finding Setup instance with property: " + propertyName + ", value: " + value);
		try {
			String queryString = "FROM Setup as model WHERE model." + propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
	
	public static float findIVAActual() {
		Session oSessionH = SessionFactory.currentSession();
		float valor_IVA = 0;
		try {
			String queryString = "SELECT model " +
								 "FROM Setup model " +
								 "WHERE model.tabla = :propertyValue AND model.fechaActualizacion IS NULL";
			
			Query queryObject = oSessionH.createQuery(queryString);
			
			queryObject.setParameter("propertyValue", "IVA");
			
			Setup oSetup = (Setup) queryObject.uniqueResult();
			valor_IVA = oSetup.getIndice();
			
		} catch (RuntimeException re) {
			throw re;
		}
		
		return valor_IVA;
	}

	public static float findGANANCIA(String indice) {
		
		Session oSessionH = SessionFactory.currentSession();
		float valor = 0;
		try {
			String queryString = "SELECT model " +
								 "FROM Setup model " +
								 "WHERE model.tabla = :propertyValue AND model.fechaActualizacion IS NULL";
			
			Query queryObject = oSessionH.createQuery(queryString);
			
			queryObject.setParameter("propertyValue", indice);
			
			Setup oSetup = (Setup) queryObject.uniqueResult();
			valor = oSetup.getIndice();
			
		} catch (RuntimeException re) {
			throw re;
		}
		
		return valor;
	}

	
	
	public List findByTabla(Object tabla) {
		return findByProperty(TABLA, tabla);
	}

	public List findByIdFacturero(Object idFacturero) {
		return findByProperty(ID_FACTURERO, idFacturero);
	}

	public List findByIndice(Object indice) {
		return findByProperty(INDICE, indice);
	}

	public List findByObservaciones(Object observaciones) {
		return findByProperty(OBSERVACIONES, observaciones);
	}

	public List findAll() {
		log.debug("finding all Setup instances");
		try {
			String queryString = "from Setup";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Setup merge(Setup detachedInstance) {
		log.debug("merging Setup instance");
		try {
			Setup result = (Setup) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Setup instance) {
		log.debug("attaching dirty Setup instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Setup instance) {
		log.debug("attaching clean Setup instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}