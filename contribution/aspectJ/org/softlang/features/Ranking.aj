package org.softlang.features;

import org.softlang.company.Department;
import org.softlang.company.Employee;
import org.softlang.utils.RankingConstraint;
import org.softlang.utils.RankingConstraintException;
import org.softlang.utils.SimpleRankingConstraint;

public privileged aspect Ranking {

	/*
	 * Add bijection to gain ability to validate through department employee is
	 * belonging to
	 */
	private Department Employee.department;

	/*
	 * Use bijection here, too
	 */
	private Department Department.parent;

	// getter and setter
	public Department Employee.getDepartment() {
		return department;
	}

	public void Employee.setDepartment(Department d) {
		this.department = d;
	}

	public Department Department.getParent() {
		return parent;
	}

	public void Department.setParent(Department p) {
		this.parent = p;
	}

	/*
	 * Pointcut for Employee instantiation
	 */
	pointcut createNewEmployee(Employee e) : execution(Employee.new(*)) && target(e);

	/*
	 * Pointcut where employee's salary gets cut
	 */
	pointcut cutEmployee(Employee e) : call(* Employee.cut()) && target(e);

	/*
	 * Modification of employee's salary through setter call
	 */
	pointcut setEmployeeSalary(Employee e) : call(* Employee.setSalary(*)) && target(e);

	/*
	 * Advice above pointcuts through employee validation
	 */
	after(Employee e) : createNewEmployee(e) || cutEmployee(e) || setEmployeeSalary(e) {
		// possibility to use factory method
		RankingConstraint constraint = new SimpleRankingConstraint();
		if (!constraint.align(e))
			throw new RankingConstraintException(constraint);
	}
}
