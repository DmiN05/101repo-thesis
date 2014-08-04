package org.softlang.features;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.softlang.utils.CompanyDataAccess;
import org.softlang.utils.annotations.AutoCreate;
import org.softlang.utils.annotations.AutoTransaction;

/*
 * Mark aspect as priviledged to gain access to private members
 */
public privileged aspect Persistence {

	/*
	 * Declare DAOs entity manager as auto created, hence we demonstrate
	 * dependency injection, or inversion of control
	 */
	declare @field : * CompanyDataAccess.em : @AutoCreate;

	/*
	 * Declare auto transaction on the insertCompany method, hence transaction
	 * begins, commits and rolls back automatically, see below
	 */
	declare @method : * CompanyDataAccess.insertCompany(..): @AutoTransaction;

	/*
	 * Hold a single entity manager instance to inject
	 */
	private EntityManager localEm = javax.persistence.Persistence
			.createEntityManagerFactory("101PU").createEntityManager();

	/*
	 * Pointcut for accessing entity manager's instance
	 */
	pointcut accessEntityManager(CompanyDataAccess dao) : target(dao) && (get(@AutoCreate * *.*) || within(Persistence) && get(* *.em));

	/*
	 * Before-Advice to create entity manager automatically, hence inject it
	 */
	before(CompanyDataAccess dao) : accessEntityManager(dao) {
		dao.em = localEm;
	}

	/*
	 * Pointcut for calling methods with AutoTransaction annotation
	 */
	pointcut callAutoTransactionMethod(CompanyDataAccess dao) : target(dao)
																&& call(@AutoTransaction * CompanyDataAccess.*(..));

	/*
	 * Begin, commit and rollback transaction automatically, rethrow exceptions
	 * as runtimeexceptions to omit catching elsewhere
	 */
	Object around(CompanyDataAccess dao) : callAutoTransactionMethod(dao) {
		EntityManager em = dao.em;
		EntityTransaction tx = em.getTransaction();
		try {
			if (!tx.isActive()) {
				tx.begin();
			}
			Object result = proceed(dao);
			if (tx.isActive()) {
				tx.commit();
			}
			return result;
		} catch (Exception e) {
			if (tx.isActive()) {
				tx.rollback();
			}
			// form exception message
			final String exceptionMessage = "Exception thrown at " + thisJoinPoint.getSignature()+ ". Transaction rolling back.";
			throw new RuntimeException(exceptionMessage, e);
		}
	}
}
