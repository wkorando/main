package com.dealership.dao.impl;

import java.util.Set;
import java.util.concurrent.ConcurrentMap;

import com.dealership.dao.DealershipDAO;
import com.dealership.dao.SearchParameter;
import com.dealership.model.Automobile;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.query.SqlPredicate;

public class DealershipDAOHazelImpl implements DealershipDAO {
	private HazelcastInstance hazelcast;
	private ConcurrentMap<Long, Automobile> automobilesMap;
	private String MAP_NAME = "automobile";

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
}
