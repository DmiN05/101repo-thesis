package org.softlang.utils;

import java.util.Collections;
import java.util.List;

public class CompanyUtils {
	
	/**
	 * Performs a median selection based on a log list of double values
	 * @param logs - a list of double values
	 * @return the median of the logs
	 */
	public static double calculateMedian(List<Double> logs) {
		Collections.sort(logs);
		int middle = logs.size() / 2;
		return logs.get(middle);
	}

	/**
	 * Performs a median calculation on a log list
	 * @param logs - a list double values
	 * @return the mean of the logs
	 */
	public static double calculateMean(List<Double> logs) {
		double mean = 0;
		for (Double log : logs) {
			mean += log;
		}
		return mean / logs.size();
	}

}
