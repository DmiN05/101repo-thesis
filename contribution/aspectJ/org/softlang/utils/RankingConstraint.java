package org.softlang.utils;

import org.softlang.company.Employee;

public interface RankingConstraint {
	
	public String getConstraintQualifier();
	
	public boolean align(Employee e);

}
