package datos.recibo_cobro;

import datos.BaseHibernateDAO;
import datos.SessionFactory;

import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;

import org.hibernate.criterion.Example;

/**
 * Data access object (DAO) for domain model class ReciboCobro.
 * 
 * @see datos.recibo_cobro.ReciboCobro
 * @author MyEclipse Persistence Tools
 */

public class ReciboCobroDAO extends BaseHibernateDAO {
	
	private static final Log log = LogFactory.getLog(ReciboCobroDAO.class);

	public static void save(ReciboCobro transientInstance) {
		log.debug("saving ReciboCobro instance");
		
		Session oSessionH = SessionFactory.currentSession();
		//Transaction tx = oSessionH.getTransaction();
		
		try {
			
			// Completo la fecha de alta
			Date fecha_emision = new Date();
			transientInstance.setFechaEmision(fecha_emision);
			
			
			oSessionH.save(transientInstance);
			//tx.commit();
			
			log.debug("save successful");
		} catch (RuntimeException re) {
			//tx.rollback();
			log.error("save failed", re);
			throw re;
		} finally {
			//oSessionH.close();
		}
	}

	public void delete(ReciboCobro persistentInstance) {
		log.debug("deleting ReciboCobro instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public ReciboCobro findById(java.lang.Integer id) {
		log.debug("getting ReciboCobro instance with id: " + id);
		try {
			ReciboCobro instance = (ReciboCobro) getSession().get("datos.recibo_cobro.ReciboCobro", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(ReciboCobro instance) {
		log.debug("finding ReciboCobro instance by example");
		try {
			List results = getSession().createCriteria("datos.recibo_cobro.ReciboCobro").add(Example.create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding ReciboCobro instance with property: " + propertyName + ", value: " + value);
		try {
			String queryString = "from ReciboCobro as model where model." + propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public int findMaxNumeroRecibo() {
		log.debug("búsqueda ReciboCobro máximo número de recibo: ");
		try {
			
			String queryString = "SELECT MAX(model.numero) FROM ReciboCobro AS model";
			
			Query queryObject = getSession().createQuery(queryString);

			return ((Number) queryObject.uniqueResult()).intValue();
			
		} catch (RuntimeException re) {
			log.error("búsqueda ReciboCobro máximo número de recibo ERROR", re);
			throw re;
		}
	}
	
	public List findAll() {
		log.debug("finding all ReciboCobro instances");
		try {
			String queryString = "from ReciboCobro";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public ReciboCobro merge(ReciboCobro detachedInstance) {
		log.debug("merging ReciboCobro instance");
		try {
			ReciboCobro result = (ReciboCobro) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(ReciboCobro instance) {
		log.debug("attaching dirty ReciboCobro instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(ReciboCobro instance) {
		log.debug("attaching clean ReciboCobro instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}