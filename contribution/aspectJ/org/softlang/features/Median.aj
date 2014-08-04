package org.softlang.features;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.softlang.company.Company;
import org.softlang.company.Department;
import org.softlang.company.Employee;

public aspect Median {

	public List<Double> Company.getAllSalaries() {
		List<Double> companySalaries = new ArrayList<>(getDepts().size());
		for (Department d : getDepts()) {
			companySalaries.addAll(d.getAllSalaries());
		}
		Collections.sort(companySalaries);
		return companySalaries;
	}

	public List<Double> Department.getAllSalaries() {
		List<Double> departmentSalaries = new ArrayList<>();
		departmentSalaries.add(getManager().getSalary());
		for (Employee e : getEmployees()) {
			departmentSalaries.add(e.getSalary());
		}
		for (Department subDep : getSubdepts()) {
			departmentSalaries.addAll(subDep.getAllSalaries());
		}
		return departmentSalaries;
	}

	public Double Company.getMedian() {
		List<Double> allCompanySalaries = getAllSalaries();
		return allCompanySalaries.isEmpty() ? null : allCompanySalaries
				.get(allCompanySalaries.size() / 2);
	}

}
