package org.softlang.utils;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public abstract class Historizable<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<T> history;

	protected Historizable() {
	}

	public abstract T getCopy();

	public List<T> getHistory() {
		if (history == null)
			history = new LinkedList<>();
		return history;
	}

}
