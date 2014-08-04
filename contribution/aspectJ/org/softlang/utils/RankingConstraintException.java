package org.softlang.utils;

public class RankingConstraintException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public RankingConstraintException(RankingConstraint constraint) {
		super(constraint.getConstraintQualifier());
	}

}
