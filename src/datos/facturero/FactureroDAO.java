package datos.facturero;

import datos.BaseHibernateDAO;
import java.util.Date;
import java.util.List;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * Data access object (DAO) for domain model class Facturero.
 * 
 * @see datos.facturero.Facturero
 * @author MyEclipse Persistence Tools
 */

public class FactureroDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(FactureroDAO.class);

	// property constants
	public static final String NUMERO_DESDE = "numeroDesde";

	public static final String NUMERO_HASTA = "numeroHasta";

	public static final String PUNTO_DE_VENTA = "puntoDeVenta";

	public static final String INDICE_ACTUAL = "indiceActual";

	public static final String RAZON_SOCIAL = "razonSocial";

	public static final String OBSERVACIONES = "observaciones";

	public void save(Facturero transientInstance) {
		log.debug("saving Facturero instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Facturero persistentInstance) {
		log.debug("deleting Facturero instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Facturero findById(java.lang.Integer id) {
		log.debug("getting Facturero instance with id: " + id);
		try {
			Facturero instance = (Facturero) getSession().get(
					"datos.prueba.Facturero", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Facturero instance) {
		log.debug("finding Facturero instance by example");
		try {
			List results = getSession()
					.createCriteria("datos.prueba.Facturero").add(
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
		log.debug("finding Facturero instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Facturero as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByNumeroDesde(Object numeroDesde) {
		return findByProperty(NUMERO_DESDE, numeroDesde);
	}

	public List findByNumeroHasta(Object numeroHasta) {
		return findByProperty(NUMERO_HASTA, numeroHasta);
	}

	public List findByPuntoDeVenta(Object puntoDeVenta) {
		return findByProperty(PUNTO_DE_VENTA, puntoDeVenta);
	}

	public List findByIndiceActual(Object indiceActual) {
		return findByProperty(INDICE_ACTUAL, indiceActual);
	}

	public List findByRazonSocial(Object razonSocial) {
		return findByProperty(RAZON_SOCIAL, razonSocial);
	}

	public List findByObservaciones(Object observaciones) {
		return findByProperty(OBSERVACIONES, observaciones);
	}

	public List findAll() {
		log.debug("finding all Facturero instances");
		try {
			String queryString = "from Facturero";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Facturero merge(Facturero detachedInstance) {
		log.debug("merging Facturero instance");
		try {
			Facturero result = (Facturero) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Facturero instance) {
		log.debug("attaching dirty Facturero instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Facturero instance) {
		log.debug("attaching clean Facturero instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}