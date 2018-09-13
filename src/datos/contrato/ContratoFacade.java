package datos.contrato;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;


import datos.SessionFactory;
import datos.contrato_actor.ContratoActor;
import datos.inmueble.Inmueble;


/**
 * Data access object (DAO) for domain model class Contrato.
 * 
 * @see datos.contrato.Contrato
 * @author MyEclipse Persistence Tools
 */

public class ContratoFacade {
	
	private static final Log log = LogFactory.getLog(ContratoFacade.class);

	// property constants
	public static final String OBSERVACIONES = "observaciones";

	public static final String MONTO = "monto";

	public static final String CANTIDAD_CUOTA = "cantidadCuota";

	public static final String COMISION_PROP_PORC = "comisionPropPorc";

	public static final String COMISION_PROP_FIJA = "comisionPropFija";

	public static final String COMISION_INQUILINO = "comisionInquilino";

	public static void save(Contrato transientInstance) {
		log.debug("saving Contrato instance");
		System.out.println("saving Contrato instance");
		
		Session oSessionH = SessionFactory.currentSession();
		//Transaction tx = oSessionH.beginTransaction();
		
		try {
			
			// Completo la fecha de alta
			Date fecha_alta = new Date();
			transientInstance.setFechaAlta(fecha_alta);
			
			oSessionH.save(transientInstance);
			//tx.commit();
			log.debug("save successful");
			System.out.println("save Contrato successful");
		} catch (RuntimeException re) {
			//tx.rollback();
			log.error("save failed", re);
			System.err.println("save Contrato failed");
			throw re;
		} finally {
			//oSessionH.close();
		}
	}

	public static void delete(Contrato persistentInstance) {
		log.debug("deleting Contrato instance");
		System.out.println("Borrar CONTRATO DAO");
		Session oSessionH = SessionFactory.currentSession();
		//Transaction tx = oSessionH.beginTransaction();
		
		try {
			Contrato oContrato = (Contrato) oSessionH.merge(persistentInstance);
			oSessionH.delete(oContrato);
			//tx.commit();
			log.debug("delete successful");
		} catch (RuntimeException re) {
			//tx.rollback();
			log.error("delete failed", re);
			throw re;
		} finally {
			//oSessionH.close();
		}
	}
	
	public static Contrato update(Contrato detachedInstance) {
		Session oSessionH = SessionFactory.currentSession();
		/** La sesion la manejo en las clases que llaman - volví a habilitar la transacción para q grabe las modificaciones */
		Transaction tx = oSessionH.beginTransaction();
		Contrato result = null;
		try {
			result = (Contrato)oSessionH.merge(detachedInstance);
			tx.commit();
		} catch (RuntimeException re) {
			tx.rollback();
			throw re;
		} finally {
			oSessionH.close();
		}
		return result;
	}

	public static Contrato findById(java.lang.Integer id) {
		log.debug("getting Contrato instance with id: " + id);
		Session oSessionH = SessionFactory.currentSession();
		try {
			Contrato instance = (Contrato) oSessionH.get("datos.contrato.Contrato", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		} finally {
			oSessionH.close();
		}
	}

	/*
	public List findByExample(Contrato instance) {
		log.debug("finding Contrato instance by example");
		try {
			List results = getSession().createCriteria(
					"datos.contrato.Contrato").add(Example.create(instance))
					.list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
	*/

	@SuppressWarnings("unchecked")
	public static List findByProperty(String propertyName, Object value) {
		log.debug("finding Contrato instance with property: " + propertyName + ", value: " + value);
		
		Session oSessionH = SessionFactory.currentSession();
		List<Contrato> Contratos;
		try {
			String queryString = "from Contrato as model where model." + propertyName + "= ?";
			Query queryObject = oSessionH.createQuery(queryString);
			queryObject.setParameter(0, value);
			Contratos = queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		} finally {
			oSessionH.close();
		}
		
		return Contratos;
	}

	public List findByObservaciones(Object observaciones) {
		return findByProperty(OBSERVACIONES, observaciones);
	}

	public List findByMonto(Object monto) {
		return findByProperty(MONTO, monto);
	}

	public List findByCantidadCuota(Object cantidadCuota) {
		return findByProperty(CANTIDAD_CUOTA, cantidadCuota);
	}

	public List findByComisionPropPorc(Object comisionPropPorc) {
		return findByProperty(COMISION_PROP_PORC, comisionPropPorc);
	}

	public List findByComisionPropFija(Object comisionPropFija) {
		return findByProperty(COMISION_PROP_FIJA, comisionPropFija);
	}

	public List findByComisionInquilino(Object comisionInquilino) {
		return findByProperty(COMISION_INQUILINO, comisionInquilino);
	}

	@SuppressWarnings("unchecked")
	public static List<Contrato> findAll() {
		log.debug("finding all Contrato instances");
		
		Session oSessionH = SessionFactory.currentSession();
		List<Contrato> Contratos;
		
		try {
			String queryString = "from Contrato";
			Query queryObject = oSessionH.createQuery(queryString);
			Contratos = queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		} finally {
			oSessionH.close();
		}
		
		return Contratos;
	}

	@SuppressWarnings("unchecked")
	public static List findByInmueble(Inmueble oInmueble, boolean vigentes) {
		
		//log.debug("Buscar Contrato con id_inmueble: " + oInmueble.getIdInmueble());
		System.out.println("Buscar Contrato con id_inmueble: " + oInmueble.getIdInmueble());
		
		List<Contrato> Contratos;
		Session oSessionH = SessionFactory.currentSession();
		try {
			String queryString = "FROM Contrato AS model WHERE model.inmueble = ? ";
			
			if (vigentes) {
				queryString += " AND model.fechaDesde <= ? AND model.fechaHasta >= ? AND model.fechaRescision IS NULL"; 
			}
			
			Query queryObject = oSessionH.createQuery(queryString);
			queryObject.setParameter(0, oInmueble);
			if (vigentes) {
				Date fecha_hoy = new Date();
				queryObject.setParameter(1, fecha_hoy);
				queryObject.setParameter(2, fecha_hoy);
			}
			
			Contratos = queryObject.list();
			
			// levanto las relaciones lazy (peresozas)
			for(Contrato obj: Contratos) {
				obj.getInmueble().getIdInmueble();
				System.out.println("Actores:" +obj.getContratoActors().size());
				for(ContratoActor obj1: obj.getContratoActors()) {
					obj1.getId().getPersona().getDescripcion();
				}
			}
			
		} catch (RuntimeException re) {
			re.printStackTrace();
			log.error("get failed", re);
			throw re;
		} finally {
			oSessionH.close();
		}
		
		return Contratos;
		
	}

	
	public static void completarInmueble(Contrato oContrato) {
	
		Session oSessionH = SessionFactory.currentSession();
		try {
			
			oSessionH.refresh(oContrato);
			
			oContrato.getInmueble().getIdInmueble();
			oContrato.getInmueble().getDireccion_completa();
			//System.out.println("Actores Completar Actores:" +oContrato.getContratoActors().size() + " - " + oContrato.getInmueble().getDireccion_completa());
			for(ContratoActor obj1: oContrato.getContratoActors()) {
				obj1.getId().getPersona().getDescripcion();
			}			
			
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		} finally {
			oSessionH.close();
		}
		
	}

	/**
	 * Busca los años en los que tengo contratos
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Short> findPeriodoAnio() {
		
		List<Short> respuesta;
		Session oSessionH = SessionFactory.currentSession();
		try {
			String queryString = "SELECT periodo_anio " +
								 "FROM contrato as c " +
								 "JOIN contrato_novedad_cobro as cnc using(id_contrato) " +
								 "GROUP BY periodo_anio " +
								 "ORDER BY periodo_anio ";
			
			Query queryObject = oSessionH.createSQLQuery(queryString);
			
			respuesta = queryObject.list();
			
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		} finally {
			oSessionH.close();
		}	
		
		return respuesta;
		
	}
	
	
	/*
	public Contrato merge(Contrato detachedInstance) {
		log.debug("merging Contrato instance");
		try {
			Contrato result = (Contrato) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}
	*/

	/*
	public void attachDirty(Contrato instance) {
		log.debug("attaching dirty Contrato instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	*/

	/*
	public void attachClean(Contrato instance) {
		log.debug("attaching clean Contrato instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	*/
	
}