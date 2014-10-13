package com.dealership.model.loaders;

import org.json.simple.JSONObject;

import com.dealership.model.Automobile;
import com.dealership.model.Sedan;

public class JSONSedanLoader implements AutomobileLoader {

	public Automobile loadAutomobile(Object object) {
		JSONObject jsonObject = (JSONObject) object;
		Sedan sedan = (Sedan) JSONAutomobileLoaderUtils.loadAutomobile(new Sedan(), (JSONObject) jsonObject);
		sedan.setHorsePower(Integer.valueOf((String) jsonObject.get("horsePower")));
		sedan.setMpg(Double.valueOf((String) jsonObject.get("mpg")));
		return sedan;
	}
}
