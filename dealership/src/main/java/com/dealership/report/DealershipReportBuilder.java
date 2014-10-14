package com.dealership.report;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;

import com.dealership.model.Automobile;

public class DealershipReportBuilder {
	private static final Logger LOG = Logger.getLogger(DealershipReportBuilder.class);
	private static DealershipReportBuilder dealershipReportBuilder;

	public static DealershipReportBuilder getInstance() {
		if (dealershipReportBuilder == null) {
			dealershipReportBuilder = new DealershipReportBuilder();
		}
		return dealershipReportBuilder;
	}

	public List<Automobile> sortAutomobiles(String fieldToOrderBy, SortOrder sortOrder, Collection<Automobile> automobiles) {
		Method method = findMethod(fieldToOrderBy, automobiles.toArray()[0]);
		Object[] array = sort(automobiles.toArray(), method, sortOrder);
		List<Automobile> automobiles2 = new ArrayList<Automobile>();

		for (int i = 0; i < array.length; i++) {
			automobiles2.add((Automobile) array[i]);
		}
		return automobiles2;
	}

	private Method findMethod(String fieldToOrderBy, Object object) {
		Method method = null;
		String methodName = buildMethodName(fieldToOrderBy);
		try {
			method = object.getClass().getMethod(methodName, new Class[0]);
		} catch (NoSuchMethodException e) {
			LOG.error("An error occurred while trying to find method: " + methodName, e);
			throw new RuntimeException("An error occurred while trying to find method: " + methodName, e);
		}

		return method;
	}

	private String buildMethodName(String fieldToOrderBy) {
		StringBuilder builder = new StringBuilder();
		builder.append("get");
		builder.append(fieldToOrderBy);
		return builder.toString();
	}

	private Object[] sort(Object[] automobiles, Method method, SortOrder sortOrder) {
		boolean sortedItem = false;
		for (int i = 0; i < automobiles.length - 1; i++) {

			Automobile first = (Automobile) automobiles[i];
			Automobile second = (Automobile) automobiles[i + 1];
			Comparable<Object> firstComparable = getVal(method, first);
			Comparable<Object> secondComparable = getVal(method, second);

			if (sortOrder == SortOrder.ASCENDING) {
				if (firstComparable.compareTo(secondComparable) > 0) {
					sortedItem = updateArray(automobiles, i, first, second);
				}
			} else {
				if (firstComparable.compareTo(secondComparable) < 0) {
					sortedItem = updateArray(automobiles, i, first, second);
				}
			}
		}
		if (sortedItem) {
			sort(automobiles, method, sortOrder);
		}
		return automobiles;
	}

	private boolean updateArray(Object[] automobiles, int i, Automobile first, Automobile second) {
		boolean sortedItem;
		automobiles[i] = second;
		automobiles[i + 1] = first;
		sortedItem = true;
		return sortedItem;
	}

	@SuppressWarnings("unchecked")
	private Comparable<Object> getVal(Method method, Automobile first) {
		Comparable<Object> firstComparable = null;
		try {
			firstComparable = (Comparable<Object>) method.invoke(first, new Object[0]);

		} catch (ReflectiveOperationException e) {
			LOG.error("An error occurred while attempting to invoke method: " + method.getName(), e);
			throw new RuntimeException("An error occurred while attempting to invoke method: " + method.getName(), e);
		}
		return firstComparable;
	}
}
