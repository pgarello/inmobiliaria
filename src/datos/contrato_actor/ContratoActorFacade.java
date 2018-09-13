package datos.contrato_actor;

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
 * Data access object (DAO) for domain model class ContratoActor.
 * 
 * @see datos.contrato_actor.ContratoActor
 * @author MyEclipse Persistence Tools
 */

public class ContratoActorFacade extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(ContratoActorFacade.class);

	// property constants

	public static void save(ContratoActor transientInstance) {
		log.debug("saving ContratoActor instance");
		System.out.println("saving ContratoActor instance");
		
		Session oSessionH = SessionFactory.currentSession();
		//Transaction tx = oSessionH.beginTransaction();
		
		try {
			oSessionH.save(transientInstance);
			//tx.commit();
			log.debug("save successful");
			System.out.println("save ContratoActor successful");
		} catch (RuntimeException re) {
			re.printStackTrace();
			//tx.rollback();
			log.error("save failed", re);
			System.err.println("save ContratoActor failed");
			throw re;
		} finally {
			//oSessionH.close();
		}
	}

	public static void delete(ContratoActor persistentInstance) {
		log.debug("deleting ContratoActor instance");
		System.out.println("Borrar ContratoActor");
		Session oSessionH = SessionFactory.currentSession();
		try {
			oSessionH.delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		} finally {
			//oSessionH.close();
		}
	}

	public ContratoActor findById(datos.contrato_actor.ContratoActorId id) {
		log.debug("getting ContratoActor instance with id: " + id);
		try {
			ContratoActor instance = (ContratoActor) getSession().get(
					"datos.contrato_actor.ContratoActor", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(ContratoActor instance) {
		log.debug("finding ContratoActor instance by example");
		try {
			List results = getSession().createCriteria(
					"datos.contrato_actor.ContratoActor").add(
					Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding ContratoActor instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from ContratoActor as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findAll() {
		log.debug("finding all ContratoActor instances");
		try {
			String queryString = "from ContratoActor";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public ContratoActor merge(ContratoActor detachedInstance) {
		log.debug("merging ContratoActor instance");
		try {
			ContratoActor result = (ContratoActor) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(ContratoActor instance) {
		log.debug("attaching dirty ContratoActor instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(ContratoActor instance) {
		log.debug("attaching clean ContratoActor instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}