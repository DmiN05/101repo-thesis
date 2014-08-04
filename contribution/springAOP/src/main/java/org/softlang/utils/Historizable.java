package org.softlang.utils;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public abstract class Historizable<T> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * maintain history entries for an entity here
	 */
	private List<T> history;

	/*
	 * no public constructors for abstract classes
	 */
	protected Historizable() {
	}

	public List<T> getHistory() {
		if(history == null) history = new LinkedList<T>();
		return history;
	}

	public void setHistory(List<T> history) {
		this.history = history;
	}

}
