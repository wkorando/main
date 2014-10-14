package com.dealership.search;

import java.util.List;

import com.dealership.model.Automobile;

/**
 * Simple implementation of a binary search for finding a automobile from a list
 * by its id.
 * 
 * @author Billy
 * 
 */
public final class SearchAutomobiles {

	private SearchAutomobiles() {
	}

	public static Automobile searchAutomobileList(Long id, List<Automobile> automobiles) {
		int high = automobiles.size();
		int median = high > 1 ? high / 2 : 0;
		Automobile automobile = automobiles.get(median);
		Long autoId = automobile.getId();
		int comparionsResult = id.compareTo(autoId);

		if (comparionsResult != 0) {
			if (automobiles.size() == 1) {// NOT FOUND
				automobile = null;
			} else if (comparionsResult > 0) {
				automobile = searchAutomobileList(id, automobiles.subList(median, high));
			} else if (comparionsResult < 0) {
				automobile = searchAutomobileList(id, automobiles.subList(0, median));
			}
		}

		return automobile;
	}

}
