package org.softlang.utils;

import org.softlang.company.Employee;

public class HistoryEntry {

	private Employee employee;

	private String name;

	private String address;

	private double salary;

	public HistoryEntry(Employee toHistorize) {
		this.employee = toHistorize;
		this.name = new String(toHistorize.getName());
		this.address = new String(toHistorize.getAddress());
		this.salary = toHistorize.getSalary();
	}

	public Employee getEmployee() {
		return employee;
	}

	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}

	public double getSalary() {
		return salary;
	}

}
