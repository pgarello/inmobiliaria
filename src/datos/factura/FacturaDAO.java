package datos.factura;

import datos.BaseHibernateDAO;

import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * Data access object (DAO) for domain model class Factura.
 * 
 * @see datos.factura.Factura
 * @author MyEclipse Persistence Tools
 */

public class FacturaDAO extends BaseHibernateDAO {
	private static final Log log = LogFactory.getLog(FacturaDAO.class);

	// property constants
	public static final String NUMERO = "numero";

	public static final String FACTURA_TIPO = "facturaTipo";

	public static final String ID_PERSONA = "idPersona";

	public static final String ID_INSCRIPCION_IVA = "idInscripcionIva";

	public static final String CLIENTE = "cliente";

	public static final String CUIT_DNI = "cuitDni";

	public static final String DOMICILIO = "domicilio";

	public static final String ID_USUARIO = "idUsuario";

	public void save(Factura transientInstance) {
		log.debug("saving Factura instance");
		try {
			
			Date fecha_emision = new Date();
			transientInstance.setFechaEmision(fecha_emision);
			
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}
	
	public void update(Factura transientInstance) {
		log.debug("update Factura instance");
		try {
			
			Factura oFacturaGrabar = this.findById(transientInstance.getIdFactura());
			
//			 como controlo que solamente se puedan modificar 2 columnas
			oFacturaGrabar.setAnulada(transientInstance.getAnulada());
			oFacturaGrabar.setNumero(transientInstance.getNumero());			
						
			getSession().saveOrUpdate(transientInstance);
			
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Factura persistentInstance) {
		log.debug("deleting Factura instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Factura findById(java.lang.Integer id) {
		log.debug("getting Factura instance with id: " + id);
		try {
			Factura instance = (Factura) getSession().get(
					"datos.factura.Factura", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Factura instance) {
		log.debug("finding Factura instance by example");
		try {
			List results = getSession().createCriteria("datos.factura.Factura")
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
		log.debug("finding Factura instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Factura as model where model." + propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByNumero(Object numero) {
		return findByProperty(NUMERO, numero);
	}

	public List findByFacturaTipo(Object facturaTipo) {
		return findByProperty(FACTURA_TIPO, facturaTipo);
	}

	public List findByIdPersona(Object idPersona) {
		return findByProperty(ID_PERSONA, idPersona);
	}

	public List findByIdInscripcionIva(Object idInscripcionIva) {
		return findByProperty(ID_INSCRIPCION_IVA, idInscripcionIva);
	}

	public List findByCliente(Object cliente) {
		return findByProperty(CLIENTE, cliente);
	}

	public List findByCuitDni(Object cuitDni) {
		return findByProperty(CUIT_DNI, cuitDni);
	}

	public List findByDomicilio(Object domicilio) {
		return findByProperty(DOMICILIO, domicilio);
	}

	public List findByIdUsuario(Object idUsuario) {
		return findByProperty(ID_USUARIO, idUsuario);
	}

	public List findAll() {
		log.debug("finding all Factura instances");
		try {
			String queryString = "from Factura";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Factura merge(Factura detachedInstance) {
		log.debug("merging Factura instance");
		try {
			Factura result = (Factura) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Factura instance) {
		log.debug("attaching dirty Factura instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Factura instance) {
		log.debug("attaching clean Factura instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	
	/**
	 * Devuelve el último número de factura del talonario correspondiente (A o B)
	 * @param tipo_factura
	 * @return
	 */
	public int findMaxNumeroFactura(short tipo_factura) {
		log.debug("búsqueda ReciboPago máximo número de recibo: ");
		try {
			
			String queryString = "SELECT MAX(model.numero) FROM Factura AS model WHERE model.facturaTipo= ?";
			
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, tipo_factura);
			
			// Controlo si es el 1º registro de la tabla
			int ultimo = 0;
			try {
				ultimo = ((Number) queryObject.uniqueResult()).intValue();
			} catch(NullPointerException npe) {}

			return ultimo;
			
		} catch (RuntimeException re) {
			log.error("búsqueda ReciboPago máximo número de recibo ERROR", re);
			throw re;
		}
	}
	
}