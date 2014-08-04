package org.softlang.utils;

import java.util.LinkedList;
import java.util.List;

public class CompanyLogger {

	private List<LogEntry> loggedEntries;

	private CompanyLogger() {
		loggedEntries = new LinkedList<>();
	}

	public static CompanyLogger getInstance() {
		return new CompanyLogger();

	}

	public void logCut(String employeeName, Double employeeOldSalary,
			Double employeeNewSalary) {
		LogEntry log = new LogEntry(employeeName, employeeOldSalary,
				employeeNewSalary);
		loggedEntries.add(log);

		System.out.println(log);
	}

	public List<Double> getDeltas() {
		List<Double> deltas = new LinkedList<>();
		for (LogEntry entry : loggedEntries) {
			deltas.add(entry.getNewSalary() - entry.getOldSalary());
		}
		return deltas;
	}

	public List<LogEntry> getLogs() {
		return loggedEntries;
	}

}
