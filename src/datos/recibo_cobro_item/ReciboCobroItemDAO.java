package datos.recibo_cobro_item;

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
 * Data access object (DAO) for domain model class ReciboCobroItem.
 * 
 * @see datos.recibo_cobro_item.ReciboCobroItem
 * @author MyEclipse Persistence Tools
 */

public class ReciboCobroItemDAO extends BaseHibernateDAO {
	
	private static final Log log = LogFactory.getLog(ReciboCobroItemDAO.class);

	public static void save(ReciboCobroItem transientInstance) {
		
		log.debug("saving ReciboCobroItem instance");
		
		Session oSessionH = SessionFactory.currentSession();
		//Transaction tx = oSessionH.beginTransaction();
		
		try {
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

	public void delete(ReciboCobroItem persistentInstance) {
		log.debug("deleting ReciboCobroItem instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public ReciboCobroItem findById(java.lang.Integer id) {
		log.debug("getting ReciboCobroItem instance with id: " + id);
		try {
			ReciboCobroItem instance = (ReciboCobroItem) getSession().get(
					"datos.recibo_cobro_item.ReciboCobroItem", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(ReciboCobroItem instance) {
		log.debug("finding ReciboCobroItem instance by example");
		try {
			List results = getSession().createCriteria(
					"datos.recibo_cobro_item.ReciboCobroItem").add(
					Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

//	public List findByProperty(String propertyName, Object value) {
//		log.debug("finding ReciboCobroItem instance with property: " + propertyName + ", value: " + value);
//		try {
//			String queryString = "from ReciboCobroItem as model where model." + propertyName + " = ?";
//			Query queryObject = getSession().createQuery(queryString);
//			queryObject.setParameter(0, value);
//			return queryObject.list();
//		} catch (RuntimeException re) {
//			log.error("find by property name failed", re);
//			throw re;
//		}
//	}
	
	@SuppressWarnings("unchecked")
	public static List<ReciboCobroItem> findByProperty(String propertyName, Object value) {
		Session oSessionH = SessionFactory.currentSession();
		List<ReciboCobroItem> cReciboCobroItem;
		try {
			String queryString = "select model from ReciboCobroItem model where model." + propertyName + "= :propertyValue";
			cReciboCobroItem = oSessionH.createQuery(queryString).setParameter("propertyValue", value).list();
		} catch (RuntimeException re) {
			throw re;
		} finally {
			//oSessionH.close();
		}
		return cReciboCobroItem;
	}
	

	public List findAll() {
		log.debug("finding all ReciboCobroItem instances");
		try {
			String queryString = "from ReciboCobroItem";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public ReciboCobroItem merge(ReciboCobroItem detachedInstance) {
		log.debug("merging ReciboCobroItem instance");
		try {
			ReciboCobroItem result = (ReciboCobroItem) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(ReciboCobroItem instance) {
		log.debug("attaching dirty ReciboCobroItem instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(ReciboCobroItem instance) {
		log.debug("attaching clean ReciboCobroItem instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}