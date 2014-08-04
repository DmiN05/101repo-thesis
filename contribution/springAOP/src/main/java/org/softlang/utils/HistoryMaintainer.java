package org.softlang.utils;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.softlang.company.Employee;

public enum HistoryMaintainer {
	
	INSTANCE;
	
	private Map<Employee, List<HistoryEntry>> history = new ConcurrentHashMap<Employee, List<HistoryEntry>>();
	
	public static HistoryMaintainer getInstance() {
		return INSTANCE;
	}
	
	public synchronized List<HistoryEntry> getHistoryForEmployee(Employee e) {
		if(!history.containsKey(e)) {
			List<HistoryEntry> result = Collections.synchronizedList(new LinkedList<HistoryEntry>());
			history.put(e, result);
			return result;
		}
		return history.get(e);
	}
	
	public void historize(Employee e) {
		HistoryEntry entry = new HistoryEntry(e);
		getHistoryForEmployee(e).add(entry);
	}
}
