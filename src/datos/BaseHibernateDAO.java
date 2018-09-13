package datos;


import org.hibernate.Session;


/**
 * Data access object (DAO) for domain model
 * @author MyEclipse Persistence Tools
 */
public class BaseHibernateDAO implements IBaseHibernateDAO {
	
	public Session getSession() {
		return SessionFactory.currentSession();
		
		/**
		 * Para manejar siempre una misma SESION
		 * pgarello 6/02/2013
		 */
		//return HibernateSessionFactory.getSession();
	}
	
}