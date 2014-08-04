package org.softlang.utils;

import org.softlang.company.Department;
import org.softlang.company.Employee;

/*
 * This Constraint is used for testing purposes - validate if given employee's salary isn't higher than any employee above him, 
 * hence if his manager and any employee in a higher department earns more money.
 */
public class SimpleRankingConstraint implements RankingConstraint {
	
	private static final String QUALIFIER = "TESTING_CONSTRAINT";

	@Override
	public boolean align(Employee e) {
		Department topDep = e.getDepartment();
		while (topDep != null) {
			if (!align(topDep, e, topDep.getParent() != null)) {
				return false;
			}
			topDep = topDep.getParent();
		}
		return true;
	}

	private boolean align(Department d, Employee e,
			boolean ignoreEmployeeSalaries) {
		if (d == null) {
			return true;
		}
		if (d.getManager() != null
				&& d.getManager().getSalary() < e.getSalary()) {
			return false;
		}
		if (!ignoreEmployeeSalaries) {
			for (Employee empl : d.getEmployees()) {
				if (!align(empl, e))
					return false;
			}
		}
		return true;
	}

	private boolean align(Employee e1, Employee e2) {
		if (e1.getSalary() < e2.getSalary())
			return false;
		return true;
	}

	@Override
	public String getConstraintQualifier() {
		return QUALIFIER;
	}

}
