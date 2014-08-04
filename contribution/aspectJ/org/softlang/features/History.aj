package org.softlang.features;

import java.util.ArrayList;
import java.util.List;

import org.softlang.company.Company;
import org.softlang.company.Employee;
import org.softlang.utils.CompanyUtils;
import org.softlang.utils.Historizable;

public aspect History {

	/*
	 * Declare inheritance afterwards
	 */
	declare parents: Employee extends Historizable<Employee>;

	/*
	 * Create new getCopy Method on Employee class
	 */
	public Employee Employee.getCopy() {
		Employee copy = new Employee();
		copy.setName(getName());
		copy.setSalary(getSalary());
		copy.setAddress(getAddress());

		return copy;
	}

	/*
	 * Create new method on company to achieve median calculation for employee's salary history
	 */
	public double Company.getMedianForEmployee(Employee e) {
		List<Double> deltas = new ArrayList<>();
		for (Employee history : e.getHistory()) {
			deltas.add(history.getSalary());
		}
		return CompanyUtils.calculateMedian(deltas);
	}
	
	/*
	 * Pointcut where getCopy method gets called
	 */
	pointcut copyEntity() : call (* *.getCopy());
	
	/*
	 * Pointcut where initialization of entity's constructor is pending
	 */
	pointcut initializeEntity() : initialization(*.new(..));

	/*
	 * Pointcut where any field is set. Consider to avoid calling this pointcut when in the control flow of above pointcuts
	 */
	pointcut modification() : set(* *.*) && !cflow(copyEntity()) && !cflow(initializeEntity());

	/*
	 * Around advice to historize entity, here Employee
	 */
	void around(Employee e) : target(e) && modification() {
		Employee copy = e.getCopy();
		proceed(e);
		// create only history entries where changes were actually made
		if (copy != null && !copy.equals(e)) {
			e.getHistory().add(copy);
		}
	}
}
