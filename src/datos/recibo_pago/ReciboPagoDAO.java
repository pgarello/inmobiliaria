package datos.recibo_pago;

import datos.BaseHibernateDAO;
import datos.SessionFactory;

import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;

import org.hibernate.criterion.Example;
import org.hibernate.criterion.Projections;

/**
 * Data access object (DAO) for domain model class ReciboPago.
 * 
 * @see datos.recibo_pago.ReciboPago
 * @author MyEclipse Persistence Tools
 */

public class ReciboPagoDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(ReciboPagoDAO.class);

	// property constants
	public static final String ID_PERSONA = "idPersona";

	public static final String NUMERO = "numero";

	public static final String LEYENDA = "leyenda";

	public static final String ID_USUARIO = "idUsuario";

	public void save(ReciboPago transientInstance) {
		log.debug("saving ReciboPago instance");
		Session oSessionH = SessionFactory.currentSession();
		
		try {
			
			// Completo la fecha de alta
			Date fecha_emision = new Date();
			transientInstance.setFechaEmision(fecha_emision);
	
			oSessionH.save(transientInstance);
			
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		} finally {
			//oSessionH.close();
		}
	}

	public static void delete(ReciboPago persistentInstance) {
		log.debug("deleting ReciboPago instance");
		System.out.println("Borrar ReciboPago");
		Session oSessionH = SessionFactory.currentSession();
		try {
			oSessionH.delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		} //finally {
//			oSessionH.close();
//		}
	}

	public ReciboPago findById(java.lang.Integer id) {
		log.debug("getting ReciboPago instance with id: " + id);
		Session oSessionH = SessionFactory.currentSession();
		try {
			ReciboPago instance = (ReciboPago) oSessionH.get("datos.recibo_pago.ReciboPago", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		} finally {
			oSessionH.close();
		}
	}

	public List findByExample(ReciboPago instance) {
		log.debug("finding ReciboPago instance by example");
		Session oSessionH = SessionFactory.currentSession();
		try {
			List results = oSessionH.createCriteria(
					"datos.recibo_pago.ReciboPago").add(
					Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		} finally {
			oSessionH.close();
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding ReciboPago instance with property: " + propertyName + ", value: " + value);
		Session oSessionH = SessionFactory.currentSession();
		try {
			String queryString = "from ReciboPago as model where model." + propertyName + "= ?";
			Query queryObject = oSessionH.createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		} finally {
			oSessionH.close();
		}
	}

	public List findByIdPersona(Object idPersona) {
		return findByProperty(ID_PERSONA, idPersona);
	}

	public List findByNumero(Object numero) {
		return findByProperty(NUMERO, numero);
	}

	public List findByLeyenda(Object leyenda) {
		return findByProperty(LEYENDA, leyenda);
	}

	public List findByIdUsuario(Object idUsuario) {
		return findByProperty(ID_USUARIO, idUsuario);
	}

	public List findAll() {
		log.debug("finding all ReciboPago instances");
		try {
			String queryString = "from ReciboPago";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public ReciboPago merge(ReciboPago detachedInstance) {
		log.debug("merging ReciboPago instance");
		try {
			ReciboPago result = (ReciboPago) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(ReciboPago instance) {
		log.debug("attaching dirty ReciboPago instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(ReciboPago instance) {
		log.debug("attaching clean ReciboPago instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	
	public int findMaxNumeroRecibo() {
		log.debug("búsqueda ReciboPago máximo número de recibo: ");
		Session oSessionH = SessionFactory.currentSession();
		try {
			
//			String queryString = "SELECT MAX(model.numero) FROM ReciboPago AS model";
//			
//			Query queryObject = getSession().createQuery(queryString);
//			
//			// Controlo si es el 1º registro de la tabla
			int ultimo = 0;
//			try {								
//				ultimo = ((Number) queryObject.uniqueResult()).intValue();
//			} catch(NullPointerException npe) {}

			
			Criteria criteria = oSessionH.createCriteria(ReciboPago.class).setProjection(Projections.max("numero"));
			try {
				ultimo = (Integer)criteria.uniqueResult();
			} catch(NullPointerException npe) {}
			
			return ultimo;
			
		} catch (RuntimeException re) {
			log.error("búsqueda ReciboPago máximo número de recibo ERROR", re);
			throw re;
		} finally {
			oSessionH.close();
		}
	}
	
}