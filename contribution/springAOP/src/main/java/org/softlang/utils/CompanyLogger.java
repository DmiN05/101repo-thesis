package org.softlang.utils;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public enum CompanyLogger {
	
	INSTANCE;
	
	private List<LogEntry> companyLogs = Collections.synchronizedList(new LinkedList<LogEntry>());

	public static CompanyLogger getInstance() {
		return INSTANCE;
	}

	public void logCut(String employeeName, Double employeeOldSalary,
			Double employeeNewSalary) {
		LogEntry log = new LogEntry(employeeName, employeeOldSalary,
				employeeNewSalary);
		companyLogs.add(log);

		System.out.println(log);
	}

	public List<Double> getDeltas() {
		List<Double> deltas = new LinkedList<Double>();
		for (LogEntry entry : companyLogs) {
			deltas.add(entry.getNewSalary() - entry.getOldSalary());
		}
		return deltas;
	}
}
