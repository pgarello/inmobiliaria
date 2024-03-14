package datos.contrato_novedad_pago;

import datos.BaseHibernateDAO;
import datos.SessionFactory;
import datos.contrato.Contrato;
import datos.contrato_novedad_cobro.ContratoNovedadCobro;
import datos.persona.Persona;

import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
//import org.hibernate.Transaction;
import org.hibernate.criterion.Example;

import app.beans.NovedadTipo;

/**
 * Data access object (DAO) for domain model class ContratoNovedadPago.
 * 
 * @see datos.contrato_novedad_pago.ContratoNovedadPago
 * @author MyEclipse Persistence Tools
 */

public class ContratoNovedadPagoDAO extends BaseHibernateDAO {
	
	private static final Log log = LogFactory.getLog(ContratoNovedadPagoDAO.class);

	public static void save(ContratoNovedadPago transientInstance) {
		log.debug("saving ContratoNovedadPago instance");
		System.out.println("saving ContratoNovedadPago instance");
		
		Session oSessionH = SessionFactory.currentSession();
		//Transaction tx = oSessionH.beginTransaction();
		
		try {
			// Completo la fecha de alta
			Date fecha_alta = new Date();
			transientInstance.setFechaAlta(fecha_alta);
			
			oSessionH.save(transientInstance);
			//tx.commit();
			
			log.debug("save successful");
			System.out.println("save ContratoNovedadPago successful");
		} catch (RuntimeException re) {
			//tx.rollback();
			log.error("save failed", re);
			System.err.println("save ContratoNovedadPago failed");
			throw re;
		} finally {
			//oSessionH.close();
		}
	}

	public static void delete(ContratoNovedadPago persistentInstance) {
		log.debug("deleting ContratoNovedadPago instance");
		System.out.println("Borrar ContratoNovedadPago");
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

	
	
	
	
	
/*********************************************************************
 * METODOS DE BUSQUEDA 
 ******************************************************************* */
	
	
	public ContratoNovedadPago findById(java.lang.Integer id) {
		log.debug("getting ContratoNovedadPago instance with id: " + id);
		try {
			ContratoNovedadPago instance = (ContratoNovedadPago) getSession()
					.get("datos.contrato_novedad_pago.ContratoNovedadPago", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	
	
	public List findByExample(ContratoNovedadPago instance) {
		log.debug("finding ContratoNovedadPago instance by example");
		try {
			List results = getSession().createCriteria(
					"datos.contrato_novedad_pago.ContratoNovedadPago").add(
					Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<ContratoNovedadPago> findByProperty(String propertyName, Object value) {
		log.debug("finding ContratoNovedadPago instance with property: " + propertyName + ", value: " + value);
		
		List<ContratoNovedadPago> lNovedades = null;
		
		try {
			String queryString = "FROM ContratoNovedadPago AS model " +
								 "WHERE model." + propertyName + "= ? " +
								 "ORDER BY model.contratoCuota, model.idNovedadTipo";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			
			lNovedades = queryObject.list();
			
			// recorro las relaciones peresozas
			for(ContratoNovedadPago obj: lNovedades) {
				obj.getContrato().getInmueble().getLocalidad().getDescripcion();				
			}
			
			//return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
		return lNovedades;
	}

	public List findAll() {
		log.debug("finding all ContratoNovedadPago instances");
		try {
			String queryString = "from ContratoNovedadPago";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<ContratoNovedadPago> findImpuestosPorContrato(Contrato value) {
		log.debug("ContratoNovedadPago.findImpuestosPorContrato value: " + value.getIdContrato());
		try {
			String queryString = 	"FROM ContratoNovedadPago as model " +
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
	
	public static void update(ContratoNovedadPago detachedInstance) {
		log.debug("merging ContratoNovedadPago instance");
		Session oSessionH = SessionFactory.currentSession();
		Transaction tx = oSessionH.beginTransaction();		
		try {
			oSessionH.update(detachedInstance);
			tx.commit();
			log.debug("merge successful");
		} catch (RuntimeException re) {
			tx.rollback();
			log.error("merge failed", re);
			throw re;
		} finally {
			oSessionH.close();
		}
	}

	public void attachDirty(ContratoNovedadPago instance) {
		log.debug("attaching dirty ContratoNovedadPago instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(ContratoNovedadPago instance) {
		log.debug("attaching clean ContratoNovedadPago instance");
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
	public static List<ContratoNovedadPago> findByPersona(Persona oPersona) {
		log.debug("ContratoNovedadPagoDAO.findByPersona");
		
		Session oSessionH = SessionFactory.currentSession();

		List<ContratoNovedadPago> lNovedades = null;
		//Date fecha_alta = new Date();
		
		try {
			String queryString = 	"FROM ContratoNovedadPago AS model " +
									"WHERE model.persona = ? " +
									"ORDER BY model.contrato.idContrato, model.periodoAnio, model.periodoMes, model.idNovedadTipo ";
									//"AND model.fechaVencimiento < ? ";
			Query queryObject = oSessionH.createQuery(queryString);
			
			queryObject.setParameter(0, oPersona);
			//queryObject.setParameter(1, fecha_alta);
			
			lNovedades = queryObject.list();
			
			// recorro las relaciones peresozas
			for(ContratoNovedadPago obj: lNovedades) {
				obj.getContrato().getInmueble().getLocalidad().getDescripcion();
			}
			
		} catch (RuntimeException re) {
			log.error("ContratoNovedadPagoDAO.findByPersona failed", re);
			throw re;
		} finally {
			oSessionH.close();
		}		
		
		return lNovedades;
	}

	
	@SuppressWarnings("unchecked")
	public static List<ContratoNovedadPago> findByContratoCuota(Contrato oContrato, short cuota_numero) {
		log.debug("ContratoNovedadPagoDAO.findByContratoCuota " + oContrato + " / " + cuota_numero);
		
		Session oSessionH = SessionFactory.currentSession();

		List<ContratoNovedadPago> lNovedades = null;
		//Date fecha_alta = new Date();
		
		try {
			String queryString = 	"FROM ContratoNovedadPago AS model " +
									"WHERE model.contrato = ? " +
									"AND model.contratoCuota = ? ";

			Query queryObject = oSessionH.createQuery(queryString);
			
			queryObject.setParameter(0, oContrato);
			queryObject.setParameter(1, cuota_numero);
			
			lNovedades = queryObject.list();
			
		} catch (RuntimeException re) {
			log.error("ContratoNovedadPagoDAO.findByContratoCuota failed", re);
			throw re;
		} finally {
			oSessionH.close();
		}		
		
		return lNovedades;
	}

	
	public static List<ContratoNovedadPago> findByPeriodo(short mes, short anio) {
		
		log.debug("ContratoNovedadPagoDAO.findByPeriodo");
		
		Session oSessionH = SessionFactory.currentSession();

		List<ContratoNovedadPago> lNovedades = null;
		//Date fecha_alta = new Date();

		try {
			String queryString = 	"FROM ContratoNovedadPago AS model " +
									"WHERE month(model.fechaLiquidacion) = " + mes + " " +
									"AND year(model.fechaLiquidacion) = " + anio + " " +
									"ORDER BY model.contrato.inmueble ";
			Query queryObject = oSessionH.createQuery(queryString);
			
//			queryObject.setParameter(0, mes);
//			queryObject.setParameter(1, anio);
			
			lNovedades = queryObject.list();
			
			// recorro las relaciones peresozas
			for(ContratoNovedadPago obj: lNovedades) {
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