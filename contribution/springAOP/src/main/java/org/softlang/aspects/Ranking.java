package org.softlang.aspects;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.softlang.company.Employee;
import org.softlang.utils.RankingConstraintException;
import org.softlang.utils.SimpleRankingConstraint;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class Ranking {

	@Pointcut("execution(public * org.softlang.services.EmployeeService.createEmployee(..))")
	public void createNewEmployee() {
	}

	@Pointcut("execution(public * org.softlang.services.EmployeeService.setEmployeeSalary(..))")
	public void setEmployeeSalary() {
	}
	
	@Pointcut("execution(public * org.softlang.services.EmployeeService.cut(..))")
	public void cutEmployee() {
	}

	@AfterReturning(pointcut = "createNewEmployee() || setEmployeeSalary()", returning = "e")
	public void checkForRanking(Employee e) {
		SimpleRankingConstraint constraint = new SimpleRankingConstraint();
		if (!constraint.align(e))
			throw new RankingConstraintException(constraint);
	}

}
