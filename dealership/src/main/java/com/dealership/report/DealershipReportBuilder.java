package com.dealership.report;

import java.util.HashSet;
import java.util.Set;

import com.dealership.model.Automobile;

public class DealershipReportBuilder {
	public Set<Automobile> sortAutomobiles(String fieldToOrderBy, SortOrder sortOrder, Set<Automobile> automobiles) {
		Object[] array = sort(automobiles.toArray());
		Set<Automobile> automobiles2 = new HashSet<Automobile>();
		for (int i = 0; i < array.length; i++) {
			automobiles2.add((Automobile) array[i]);
		}
		return automobiles2;
	}

	private Object[] sort(Object[] automobiles) {
		boolean sortedItem = false;
		for (int i = 0; i < automobiles.length - 1; i++) {
			Automobile first = (Automobile) automobiles[i];
			Automobile second = (Automobile) automobiles[i + 1];

			if (first.getPrice().compareTo(second.getPrice()) < 0) {
				automobiles[i] = second;
				automobiles[i + 1] = first;
				sortedItem = true;
			}
		}
		if (sortedItem) {
			sort(automobiles);
		}
		return automobiles;
	}
}
