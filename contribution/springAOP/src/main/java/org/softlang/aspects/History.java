package org.softlang.aspects;

import java.util.List;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.softlang.company.Employee;
import org.softlang.utils.HistoryEntry;
import org.softlang.utils.HistoryMaintainer;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class History {
	
	private HistoryMaintainer history = HistoryMaintainer.getInstance();
	
	@Pointcut("execution(public * org.softlang.services.EmployeeService.setEmployeeSalary(..))")
	public void setEmployeeSalary(){}
	
	@Pointcut("execution(public * org.softlang.services.EmployeeService.setEmployeeName(..))")
	public void setEmployeeName(){}
	
	@Pointcut("execution(public * org.softlang.services.EmployeeService.setEmployeeAddress(..))")
	public void setEmployeeAddress(){}
	
	@Pointcut("execution(public * org.softlang.services.EmployeeService.cut(..))")
	public void cutEmployeeSalary(){}
	
	@Pointcut("setEmployeeSalary() || setEmployeeName() || setEmployeeAddress() || cutEmployeeSalary()")
	public void modifyEmployee(){}
	
	@Before("@target(org.softlang.utils.annotations.Historizing) && modifyEmployee() "
			+ "&& !execution(public * org.softlang.services.EmployeeService.createEmployee(..)) && args(emp,..)")
	public void historizeEmployee(Employee emp) {		
		history.historize(emp);
	}
	
	public List<HistoryEntry> getHistory(Employee e) {
		return history.getHistoryForEmployee(e);
	}
	

}
