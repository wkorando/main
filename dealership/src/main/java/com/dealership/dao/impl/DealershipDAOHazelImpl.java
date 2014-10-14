package com.dealership.dao.impl;

import java.io.File;
import java.io.FileReader;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.dealership.dao.DealershipDAO;
import com.dealership.dao.SearchParameter;
import com.dealership.model.Automobile;
import com.dealership.model.loaders.AutomobileLoader;
import com.dealership.model.loaders.AutomobileLoaderFactory;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.query.SqlPredicate;

/**
 * Implementation of {@link DealershipDAO} for interacting with a Hazelcast
 * database.
 * 
 * @author Billy
 * 
 */
public class DealershipDAOHazelImpl implements DealershipDAO {
	private Logger LOG = Logger.getLogger(DealershipDAOHazelImpl.class);

	private HazelcastInstance hazelcast;
	private ConcurrentMap<Long, Automobile> automobilesMap;
	private String MAP_NAME = "automobile";
	private AutomobileLoaderFactory loaderFactory = new AutomobileLoaderFactory();
	private JSONParser parser = new JSONParser();

	public DealershipDAOHazelImpl() {
		hazelcast = Hazelcast.newHazelcastInstance();
		automobilesMap = hazelcast.getMap(MAP_NAME);
	}

	public Set<Automobile> search(SearchParameter... searchParameters) {
		IMap<Long, Automobile> map = hazelcast.getMap(MAP_NAME);
		String query = buildQuery(searchParameters);
		return (Set<Automobile>) map.values(new SqlPredicate(query));
	}

	private String buildQuery(SearchParameter[] searchParameters) {
		StringBuilder builder = new StringBuilder();
		for (SearchParameter searchItem : searchParameters) {
			if (builder.length() > 0) {
				builder.append(searchItem.getOperator().getName());
				builder.append(" ");
			}
			builder.append(searchItem.getFieldName());
			builder.append(" ");
			builder.append(searchItem.getValueOperator().getName());
			builder.append(" ");
			builder.append(searchItem.getValue());
			builder.append(" ");
		}
		return builder.toString();
	}

	public void addNew(Automobile automobile) {
		automobilesMap.put(automobile.getId(), automobile);
	}

	public void update(Automobile automobile) {
		automobilesMap.replace(automobile.getId(), automobile);
	}

	public void delete(Automobile automobile) {
		automobilesMap.remove(automobile.getId());
	}

	public Automobile lookupById(long id) {
		return automobilesMap.get(Long.valueOf(id));
	}

	public void loadAutomobilesFromFile(File file) {
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
			addNew(loader.loadAutomobile(jsonObj));
		}
	}
}
