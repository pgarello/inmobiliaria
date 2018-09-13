package datos.contrato_novedad_factura;

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
 * Data access object (DAO) for domain model class ContratoNovedadFactura.
 * 
 * @see datos.contrato_novedad_factura.ContratoNovedadFactura
 * @author MyEclipse Persistence Tools
 */

public class ContratoNovedadFacturaDAO extends BaseHibernateDAO {
	
	private static final Log log = LogFactory.getLog(ContratoNovedadFacturaDAO.class);

	// property constants
	public static final String PERIODO_MES = "periodoMes";

	public static final String PERIODO_ANIO = "periodoAnio";

	public static final String CONTRATO_CUOTA = "contratoCuota";

	public static final String MONTO = "monto";

	public static void save(ContratoNovedadFactura transientInstance) {
		log.debug("saving ContratoNovedadFactura instance");
		System.out.println("saving ContratoNovedadFactura instance");
		
		Session oSessionH = SessionFactory.currentSession();
		//Transaction tx = oSessionH.beginTransaction();
		
		try {
			// Completo la fecha de alta
			Date fecha_alta = new Date();
			transientInstance.setFechaAlta(fecha_alta);
			
			oSessionH.save(transientInstance);
			//tx.commit();
			
			log.debug("save successful");
			System.out.println("save ContratoNovedadFactura successful");
			
		} catch (RuntimeException re) {
			//tx.rollback();
			log.error("save failed", re);
			System.err.println("save ContratoNovedadFactura failed");
			throw re;
		} finally {
			//oSessionH.close();
		}
	}

	public static void delete(ContratoNovedadFactura persistentInstance) {
		log.debug("deleting ContratoNovedadFactura instance");
		System.out.println("Borrar ContratoNovedadFactura");
		Session oSessionH = SessionFactory.currentSession();
		try {
			oSessionH.delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public ContratoNovedadFactura findById(java.lang.Integer id) {
		log.debug("getting ContratoNovedadFactura instance with id: " + id);
		try {
			ContratoNovedadFactura instance = (ContratoNovedadFactura) getSession().get("datos.contrato_novedad_factura.ContratoNovedadFactura",id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(ContratoNovedadFactura instance) {
		log.debug("finding ContratoNovedadFactura instance by example");
		try {
			List results = getSession().createCriteria(
					"datos.contrato_novedad_factura.ContratoNovedadFactura")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public static List<ContratoNovedadFactura> findByProperty(String propertyName, Object value) {
		log.debug("finding ContratoNovedadFactura instance with property: "	+ propertyName + ", value: " + value);
		Session oSessionH = SessionFactory.currentSession();
		try {
			
			String queryString = "from ContratoNovedadFactura as model where model." + propertyName + "= ?";
			
			Query queryObject = oSessionH.createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
			
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByPeriodoMes(Object periodoMes) {
		return findByProperty(PERIODO_MES, periodoMes);
	}

	public List findByPeriodoAnio(Object periodoAnio) {
		return findByProperty(PERIODO_ANIO, periodoAnio);
	}

	public List findByContratoCuota(Object contratoCuota) {
		return findByProperty(CONTRATO_CUOTA, contratoCuota);
	}

	public List findByMonto(Object monto) {
		return findByProperty(MONTO, monto);
	}

	public List findAll() {
		log.debug("finding all ContratoNovedadFactura instances");
		try {
			String queryString = "from ContratoNovedadFactura";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public ContratoNovedadFactura merge(ContratoNovedadFactura detachedInstance) {
		log.debug("merging ContratoNovedadFactura instance");
		try {
			ContratoNovedadFactura result = (ContratoNovedadFactura) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(ContratoNovedadFactura instance) {
		log.debug("attaching dirty ContratoNovedadFactura instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(ContratoNovedadFactura instance) {
		log.debug("attaching clean ContratoNovedadFactura instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}