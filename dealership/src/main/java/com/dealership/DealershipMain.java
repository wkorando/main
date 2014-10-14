package com.dealership;

import java.io.File;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Set;

import com.dealership.dao.DealershipDAO;
import com.dealership.dao.SearchParameter;
import com.dealership.dao.SearchParameter.Operator;
import com.dealership.dao.SearchParameter.ValueOperator;
import com.dealership.dao.impl.DealershipDAOHazelImpl;
import com.dealership.model.Automobile;
import com.dealership.report.DealershipReportBuilder;
import com.dealership.report.SortOrder;

public class DealershipMain {
	private DealershipDAO dao = new DealershipDAOHazelImpl();

	public Set<Automobile> findAllAutosInPriceRange(BigDecimal low, BigDecimal high) {
		SearchParameter lowValue = new SearchParameter("price", low.toString(), Operator.AND, ValueOperator.GREATER_THAN_OR_EQUAL_TO);
		SearchParameter highValue = new SearchParameter("price", high.toString(), Operator.AND, ValueOperator.LESSER_THAN_OR_EQUAL_TO);

		return dao.search(lowValue, highValue);
	}

	public Set<Automobile> runCustomSearch(SearchParameter... searchParameters) {
		return dao.search(searchParameters);
	}

	public void loadAutomobiles(File file) {
		dao.loadAutomobilesFromFile(file);
	}

	public Collection<Automobile> runReport(String fieldToOrderBy, SortOrder sortOrder, SearchParameter... searchParameters) {
		Set<Automobile> automobiles = dao.search(searchParameters);

		return DealershipReportBuilder.getInstance().sortAutomobiles(fieldToOrderBy, sortOrder, automobiles);
	}

	public BigDecimal totalValueOfStock() {
		Set<Automobile> automobiles = dao.search(new SearchParameter("price", "0", Operator.AND, ValueOperator.GREATER_THAN_OR_EQUAL_TO));
		BigDecimal grandTotal = new BigDecimal(0);
		for (Automobile automobile : automobiles) {
			grandTotal = grandTotal.add(automobile.getPrice().multiply(BigDecimal.valueOf(automobile.getNumberInStock())));
		}
		return grandTotal;
	}

	public void runSale(BigDecimal percentageOff, Set<Automobile> automobilesOnSale) {
		for (Automobile automobile : automobilesOnSale) {
			BigDecimal salePrice = automobile.getPrice();
			salePrice = salePrice.subtract(salePrice.multiply(percentageOff));
			automobile.setPrice(salePrice);
			dao.update(automobile);
		}
	}
}
