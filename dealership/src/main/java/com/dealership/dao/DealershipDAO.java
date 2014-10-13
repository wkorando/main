package com.dealership.dao;

import java.util.Set;

import com.dealership.model.Automobile;

public interface DealershipDAO {
	Set<Automobile> search(SearchParameter... searchParameters);

	void addNew(Automobile automobile);

	void update(Automobile automobile);

	void delete(Automobile automobile);

	Automobile lookupById(long id);
}
