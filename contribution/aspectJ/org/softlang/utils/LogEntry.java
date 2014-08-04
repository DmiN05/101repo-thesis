package org.softlang.utils;


public class LogEntry {

	private String name;

	private Double oldSalary;

	private Double newSalary;
	
	public LogEntry(String name, Double oldSalary, Double newSalary) {
		this.name = name;
		this.oldSalary = oldSalary;
		this.newSalary = newSalary;
	}

	public String getName() {
		return name;
	}

	public Double getOldSalary() {
		return oldSalary;
	}

	public Double getNewSalary() {
		return newSalary;
	}

	@Override
	public String toString() {
		return "LogEntry {name=\"" + name + "\", oldSalary=\"" + oldSalary
				+ "\", newSalary=\"" + newSalary + "\"}";
	}
}
