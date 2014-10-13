package com.dealership;

import java.io.File;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.Set;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.dealership.dao.DealershipDAO;
import com.dealership.dao.SearchParameter;
import com.dealership.dao.SearchParameter.Operator;
import com.dealership.dao.SearchParameter.ValueOperator;
import com.dealership.dao.impl.DealershipDAOHazelImpl;
import com.dealership.model.Automobile;
import com.dealership.model.loaders.AutomobileLoader;
import com.dealership.model.loaders.AutomobileLoaderFactory;
import com.dealership.report.SortOrder;

public class DealershipMain {
	private static final Logger LOG = Logger.getLogger(DealershipMain.class);

	private DealershipDAO dao = new DealershipDAOHazelImpl();
	private JSONParser parser = new JSONParser();
	private AutomobileLoaderFactory loaderFactory = new AutomobileLoaderFactory();

	public Set<Automobile> findAllAutosInPriceRange(BigDecimal low, BigDecimal high) {
		SearchParameter lowValue = new SearchParameter("price", low.toString(), Operator.AND, ValueOperator.GREATER_THAN_OR_EQUAL_TO);
		SearchParameter highValue = new SearchParameter("price", high.toString(), Operator.AND, ValueOperator.LESSER_THAN_OR_EQUAL_TO);
		
		return dao.search(lowValue, highValue);
	}
	
	public Set<Automobile> runCustomSearch(SearchParameter... searchParameters){
		return dao.search(searchParameters);
	}
	
	public void loadAutomobiles(File file) {
		JSONArray jsonArray;
		try {
			jsonArray = (JSONArray) parser.parse(new FileReader(file));
		} catch (Exception e) {
			LOG.error("An error occurred while attempting to read passed in JSON file", e);
			throw new RuntimeException("An error occurred while attempting to read passed in JSON file", e);
		}

		for (Object obj : jsonArray) {
			JSONObject jsonObj = (JSONObject) obj;
			AutomobileLoader loader = loaderFactory.findLoaderForJSONObject(jsonObj);
			dao.addNew(loader.loadAutomobile(jsonObj));
		}
	}
	
	public Set<Automobile> runReport(String fieldToOrderBy, SortOrder sortOrder, SearchParameter... searchParameters){
		Set<Automobile> automobiles = dao.search(searchParameters);
		return null;
	}
}
