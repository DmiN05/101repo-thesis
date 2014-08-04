package org.softlang.features;

import java.util.List;

import org.softlang.company.Company;
import org.softlang.company.Employee;
import org.softlang.utils.CompanyLogger;
import org.softlang.utils.CompanyUtils;

/**
 * Logging for cut
 */
public aspect Logging {

	/*
	 * Logger instance
	 */
	private CompanyLogger logger = CompanyLogger.getInstance();

	/*
	 * Company's mean gets updated after each company cut
	 */
	public double Company.deltaMean;

	/*
	 * See Company.mean
	 */
	public double Company.deltaMedian;

	/*
	 * Introduce a pretty toString() method
	 */
	public String Employee.toString() {
		return "Employee " + getName() + " - Address " + getAddress()
				+ " - Salary " + getSalary();
	}

	/*
	 * Define a pointcut where the cut method gets called on an employee
	 */
	pointcut cutEmployee(Employee e):
		target(e) && call(public void Employee.cut());

	/*
	 * Define a pointcut where the cut method gets called on a company
	 */
	pointcut cutCompany(Company c):
		target(c) && call(public void Company.cut());

	/*
	 * Around advice - store old salary temporarily, proceed in context, then
	 * create new log entry
	 */
	void around(Employee e) : cutEmployee(e) {
		double oldSalary = e.getSalary();
		proceed(e);
		logger.logCut(e.getName(), oldSalary, e.getSalary());
	}

	/*
	 * After advice - after cut on company completed, store company's overall
	 * salary development median and mean
	 */
	after(Company c) : cutCompany(c) {
		List<Double> deltas = logger.getDeltas();

		c.deltaMedian = CompanyUtils.calculateMedian(deltas);
		c.deltaMean = CompanyUtils.calculateMean(deltas);
	}

}
