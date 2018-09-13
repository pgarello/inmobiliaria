package datos.contrato_novedad_cobro;

import datos.BaseHibernateDAO;
import datos.SessionFactory;
import datos.contrato.Contrato;

import datos.persona.Persona;

import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;

import org.hibernate.criterion.Example;

import app.beans.NovedadTipo;

/**
 * Data access object (DAO) for domain model class ContratoNovedadCobro.
 * 
 * @see datos.contrato_novedad_cobro.ContratoNovedadCobro
 * @author MyEclipse Persistence Tools
 */

public class ContratoNovedadCobroDAO extends BaseHibernateDAO {
	
	private static final Log log = LogFactory.getLog(ContratoNovedadCobroDAO.class);

	public static void save(ContratoNovedadCobro transientInstance) {
		log.debug("saving ContratoNovedadCobro instance");
		System.out.println("saving ContratoNovedadCobro instance");
		
		Session oSessionH = SessionFactory.currentSession();
		//Transaction tx = oSessionH.beginTransaction();
		
		try {
			// Completo la fecha de alta
			Date fecha_alta = new Date();
			transientInstance.setFechaAlta(fecha_alta);
			
			oSessionH.save(transientInstance);
			//tx.commit();
			log.debug("save successful");
			System.out.println("save ContratoNovedadCobro successful");
		} catch (RuntimeException re) {
			//tx.rollback();
			log.error("save failed", re);
			System.out.println("save ContratoNovedadCobro failed");
			throw re;
		} finally {
			//oSessionH.close();
		}
	}

	public static void delete(ContratoNovedadCobro persistentInstance) {
		log.debug("deleting ContratoNovedadCobro instance");
		System.out.println("Borrar ContratoNovedadCobro");
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

	public ContratoNovedadCobro findById(java.lang.Integer id) {
		log.debug("getting ContratoNovedadCobro instance with id: " + id);
		try {
			ContratoNovedadCobro instance = (ContratoNovedadCobro) getSession()
					.get("datos.contrato_novedad_cobro.ContratoNovedadCobro",
							id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(ContratoNovedadCobro instance) {
		log.debug("finding ContratoNovedadCobro instance by example");
		try {
			List results = getSession().createCriteria(
					"datos.contrato_novedad_cobro.ContratoNovedadCobro").add(
					Example.create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding ContratoNovedadCobro instance with property: " + propertyName + ", value: " + value);
		try {
			String queryString = "from ContratoNovedadCobro as model where model." + propertyName + "= ? order by contratoCuota";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findAll() {
		log.debug("finding all ContratoNovedadCobro instances");
		try {
			String queryString = "from ContratoNovedadCobro";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<ContratoNovedadCobro> findImpuestosPorContrato(Contrato value) {
		log.debug("ContratoNovedadCobro.findImpuestosPorContrato value: " + value.getIdContrato());
		try {
			String queryString = 	"FROM ContratoNovedadCobro as model " +
									"WHERE model.idNovedadTipo = " + NovedadTipo.Impuesto +
									"AND model.contrato = ? ";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
	
	

	public ContratoNovedadCobro merge(ContratoNovedadCobro detachedInstance) {
		log.debug("merging ContratoNovedadCobro instance");
		try {
			ContratoNovedadCobro result = (ContratoNovedadCobro) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(ContratoNovedadCobro instance) {
		log.debug("attaching dirty ContratoNovedadCobro instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(ContratoNovedadCobro instance) {
		log.debug("attaching clean ContratoNovedadCobro instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	
// METODOS PERSONALIZADOS ----------------------------------------------------------------
	
	@SuppressWarnings("unchecked")
	public static List<ContratoNovedadCobro> findByPersona(Persona oPersona) {
		log.debug("ContratoNovedadCobroDAO.findByPersona");
		
		Session oSessionH = SessionFactory.currentSession();

		List<ContratoNovedadCobro> lNovedades = null;
		//Date fecha_alta = new Date();
		
		try {
			String queryString = 	"FROM ContratoNovedadCobro AS model WHERE model.persona = ? ";
									//"AND model.fechaVencimiento < ? ";
			Query queryObject = oSessionH.createQuery(queryString);
			
			queryObject.setParameter(0, oPersona);
			//queryObject.setParameter(1, fecha_alta);
			
			lNovedades = queryObject.list();
			
			// recorro las relaciones peresozas
			for(ContratoNovedadCobro obj: lNovedades) {
				obj.getContrato().getInmueble().getLocalidad().getDescripcion();
			}
			
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		} finally {
			oSessionH.close();
		}		
		
		return lNovedades;
	}

	
	@SuppressWarnings("unchecked")
	public static List<ContratoNovedadCobro> findByContrato(Contrato oContrato) {
		
		log.debug("ContratoNovedadCobroDAO.findByContrato");
		
		Session oSessionH = SessionFactory.currentSession();

		List<ContratoNovedadCobro> lNovedades = null;
		Date fecha_alta = new Date();
		
		try {
			String queryString = 	"FROM ContratoNovedadCobro AS model WHERE model.contrato = ? " +
									"AND model.fechaVencimiento < ? ";
			Query queryObject = oSessionH.createQuery(queryString);
			
			queryObject.setParameter(0, oContrato);
			queryObject.setParameter(1, fecha_alta);
			
			lNovedades = queryObject.list();
			
			// recorro las relaciones peresozas
			for(ContratoNovedadCobro obj: lNovedades) {
				obj.getContrato().getInmueble().getLocalidad().getDescripcion();
			}
			
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		} finally {
			oSessionH.close();
		}		
		
		return lNovedades;
	}

	
	@SuppressWarnings("unchecked")
	public static List<ContratoNovedadCobro> findByPeriodo(short mes, short anio) {
		
		log.debug("ContratoNovedadCobroDAO.findByPeriodo");
		
		Session oSessionH = SessionFactory.currentSession();

		List<ContratoNovedadCobro> lNovedades = null;
		//Date fecha_alta = new Date();

		try {
			String queryString = 	"FROM ContratoNovedadCobro AS model " +
									"WHERE month(model.fechaVencimiento) = " + mes + " " +
									"AND year(model.fechaVencimiento) = " + anio + " " +
									"ORDER BY model.contrato.inmueble ";
			Query queryObject = oSessionH.createQuery(queryString);
			
//			queryObject.setParameter(0, mes);
//			queryObject.setParameter(1, anio);
			
			lNovedades = queryObject.list();
			
			// recorro las relaciones peresozas
			for(ContratoNovedadCobro obj: lNovedades) {
				obj.getContrato().getInmueble().getLocalidad().getDescripcion();
				obj.getContrato().getInquilino().getDescripcion();
			}
			
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		} finally {
			oSessionH.close();
		}		
		
		return lNovedades;
	}

	
}