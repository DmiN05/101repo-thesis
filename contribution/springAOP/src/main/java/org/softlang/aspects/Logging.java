package org.softlang.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.softlang.company.Employee;
import org.softlang.utils.CompanyLogger;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class Logging {

	private CompanyLogger logger = CompanyLogger.getInstance();

	@Around("(execution(public * org.softlang.services.EmployeeService.cut(org.softlang.company.Employee)) "
			+ "|| execution(public * org.softlang.services.EmployeeService.setEmployeeSalary(..))) "
			+ "&& args(emp,..)")
	public void beforeCallAnyMethod(ProceedingJoinPoint pjp, Employee emp) throws Throwable {
		double oldSalary = emp.getSalary();
		pjp.proceed();
		logger.logCut(emp.getName(), oldSalary, emp.getSalary());
		
	}

	public CompanyLogger getLogger() {
		return logger;
	}

}
