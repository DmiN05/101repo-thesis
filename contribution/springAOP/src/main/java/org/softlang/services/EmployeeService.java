package org.softlang.services;

import org.softlang.company.Department;
import org.softlang.company.Employee;
import org.softlang.utils.annotations.Historizing;
import org.springframework.stereotype.Component;

@Component
@Historizing
public class EmployeeService {

	public Employee cut(Employee e) {
		e.setSalary(e.getSalary() / 2);
		
		return e;
	}
	
	public Employee setEmployeeSalary(Employee e, double salary) {
		e.setSalary(salary);
		return e;
	}
	
	public Employee setEmployeeName(Employee e, String name) {
		e.setName(name);
		return e;
	}
	
	public Employee setEmployeeAddress(Employee e, String address) {
		e.setAddress(address);
		return e;
	}
	
	public Employee createEmployee(String name, String address, double salary, Department dept) {
		Employee e = new Employee();
		e.setName(name);
		e.setAddress(address);
		e.setSalary(salary);
		e.setDepartment(dept);
		dept.getEmployees().add(e);
		return e;
	}

}
