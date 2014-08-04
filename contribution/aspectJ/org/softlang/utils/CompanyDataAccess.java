package org.softlang.utils;

import javax.persistence.EntityManager;

import org.softlang.company.Company;

public class CompanyDataAccess {

	private EntityManager em;

	public Company findCompany() {
		Company result = em.createQuery("FROM Company c WHERE c.name = :name", Company.class)
				.setParameter("name", "meganalysis").getResultList().get(0);
		
		return result;
	}
	
	public void insertCompany(Company c) throws Exception {		
		if(em.contains(c)) {
			em.merge(c);
		} else {
			em.persist(c);
		}
		em.flush();
	}

}
