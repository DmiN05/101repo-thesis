package org.softlang.company;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 * A department has a name, a manager, employees, and subdepartments.
 */
@Entity
public class Department implements Serializable {

	private static final long serialVersionUID = -2008895922177165250L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String name;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Employee manager;
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<Department> subdepts = new LinkedList<Department>();
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<Employee> employees = new LinkedList<Employee>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Employee getManager() {
		return manager;
	}

	public void setManager(Employee manager) {
		this.manager = manager;
	}

	public List<Department> getSubdepts() {
		return subdepts;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
