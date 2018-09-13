package datos.factura_item;

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
 * Data access object (DAO) for domain model class FacturaItem.
 * 
 * @see datos.factura_item.FacturaItem
 * @author MyEclipse Persistence Tools
 */

public class FacturaItemDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(FacturaItemDAO.class);

	// property constants
	public static final String ID_FACTURA = "idFactura";

	public static final String ID_NOVEDAD_FACTURA = "idNovedadFactura";

	public static final String MONTO = "monto";

	public static final String DESCRIPCION = "descripcion";

	public static final String ID_ITEM_TIPO = "idItemTipo";

	public void save(FacturaItem transientInstance) {
		log.debug("saving FacturaItem instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(FacturaItem persistentInstance) {
		log.debug("deleting FacturaItem instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public FacturaItem findById(java.lang.Integer id) {
		log.debug("getting FacturaItem instance with id: " + id);
		try {
			FacturaItem instance = (FacturaItem) getSession().get(
					"datos.factura_item.FacturaItem", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(FacturaItem instance) {
		log.debug("finding FacturaItem instance by example");
		try {
			List results = getSession().createCriteria(
					"datos.factura_item.FacturaItem").add(
					Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public static List findByProperty(String propertyName, Object value) {
		log.debug("finding FacturaItem instance with property: " + propertyName + ", value: " + value);
		Session oSessionH = SessionFactory.currentSession();
		try {
			String queryString = "from FacturaItem as model where model." + propertyName + "= ?";
			
			Query queryObject = oSessionH.createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByIdFactura(Object idFactura) {
		return findByProperty(ID_FACTURA, idFactura);
	}

	public List findByIdNovedadFactura(Object idNovedadFactura) {
		return findByProperty(ID_NOVEDAD_FACTURA, idNovedadFactura);
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
		log.debug("finding all FacturaItem instances");
		try {
			String queryString = "from FacturaItem";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public FacturaItem merge(FacturaItem detachedInstance) {
		log.debug("merging FacturaItem instance");
		try {
			FacturaItem result = (FacturaItem) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(FacturaItem instance) {
		log.debug("attaching dirty FacturaItem instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(FacturaItem instance) {
		log.debug("attaching clean FacturaItem instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}