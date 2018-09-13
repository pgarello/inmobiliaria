package datos.recibo_pago_item;

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
 * Data access object (DAO) for domain model class ReciboPagoItem.
 * 
 * @see datos.recibo_pago_item.ReciboPagoItem
 * @author MyEclipse Persistence Tools
 */

public class ReciboPagoItemDAO extends BaseHibernateDAO {
	
	private static final Log log = LogFactory.getLog(ReciboPagoItemDAO.class);

	// property constants
	public static final String ID_RECIBO_PAGO = "idReciboPago";

	public static final String ID_NOVEDAD = "idNovedad";

	public static final String MONTO = "monto";

	public static final String DESCRIPCION = "descripcion";

	public static final String ID_ITEM_TIPO = "idItemTipo";

	public static void save(ReciboPagoItem transientInstance) {
		log.debug("saving ReciboPagoItem instance");
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

	public static void delete(ReciboPagoItem persistentInstance) {
		log.debug("deleting ReciboPagoItem instance");
		System.out.println("Borrar ReciboPagoItem");
		Session oSessionH = SessionFactory.currentSession();
		try {
			oSessionH.delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public ReciboPagoItem findById(java.lang.Integer id) {
		log.debug("getting ReciboPagoItem instance with id: " + id);
		try {
			ReciboPagoItem instance = (ReciboPagoItem) getSession().get(
					"datos.recibo_pago_item.ReciboPagoItem", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(ReciboPagoItem instance) {
		log.debug("finding ReciboPagoItem instance by example");
		try {
			List results = getSession().createCriteria(
					"datos.recibo_pago_item.ReciboPagoItem").add(
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
//		log.debug("finding ReciboPagoItem instance with property: "
//				+ propertyName + ", value: " + value);
//		try {
//			String queryString = "from ReciboPagoItem as model where model."
//					+ propertyName + "= ?";
//			Query queryObject = getSession().createQuery(queryString);
//			queryObject.setParameter(0, value);
//			return queryObject.list();
//		} catch (RuntimeException re) {
//			log.error("find by property name failed", re);
//			throw re;
//		}
//	}
	
	@SuppressWarnings("unchecked")
	public static List<ReciboPagoItem> findByProperty(String propertyName, Object value) {
		Session oSessionH = SessionFactory.currentSession();
		List<ReciboPagoItem> cReciboPagoItem;
		try {
			String queryString = "select model from ReciboPagoItem model where model." + propertyName + "= :propertyValue";
			cReciboPagoItem = oSessionH.createQuery(queryString).setParameter("propertyValue", value).list();
		} catch (RuntimeException re) {
			throw re;
		} finally {
			//oSessionH.close();
		}
		return cReciboPagoItem;
	}
	
	

	public List findByIdReciboPago(Object idReciboPago) {
		return findByProperty(ID_RECIBO_PAGO, idReciboPago);
	}

	public List findByIdNovedad(Object idNovedad) {
		return findByProperty(ID_NOVEDAD, idNovedad);
	}

	public List findByMonto(Object monto) {
		return findByProperty(MONTO, monto);
	}

	public List findByDescripcion(Object descripcion) {
		return findByProperty(DESCRIPCION, descripcion);
	}

	public List findByIdItemTipo(Object idItemTipo) {
		return findByProperty(ID_ITEM_TIPO, idItemTipo);
	}

	public List findAll() {
		log.debug("finding all ReciboPagoItem instances");
		try {
			String queryString = "from ReciboPagoItem";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public ReciboPagoItem merge(ReciboPagoItem detachedInstance) {
		log.debug("merging ReciboPagoItem instance");
		try {
			ReciboPagoItem result = (ReciboPagoItem) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(ReciboPagoItem instance) {
		log.debug("attaching dirty ReciboPagoItem instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(ReciboPagoItem instance) {
		log.debug("attaching clean ReciboPagoItem instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}