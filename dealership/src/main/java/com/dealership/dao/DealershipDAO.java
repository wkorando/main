package com.dealership.dao;

import java.io.File;
import java.util.Set;

import com.dealership.model.Automobile;

/**
 * DAO interface for interacting with the datastore.
 * 
 * @author Billy
 * 
 */
public interface DealershipDAO {
	Set<Automobile> search(SearchParameter... searchParameters);

	void addNew(Automobile automobile);

	void update(Automobile automobile);

	void delete(Automobile automobile);

	Automobile lookupById(long id);

	void loadAutomobilesFromFile(File file);
}
