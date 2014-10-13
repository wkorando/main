package com.dealership.model.loaders;

import org.json.simple.JSONObject;

import com.dealership.model.Automobile;
import com.dealership.model.Van;

public class JSONVanLoader implements AutomobileLoader {

	public Automobile loadAutomobile(Object object) {
		JSONObject jsonObject = (JSONObject) object;
		Van van = (Van) JSONAutomobileLoaderUtils.loadAutomobile(new Van(), (JSONObject) jsonObject);
		van.setPassangerCapacity(Integer.valueOf((String) jsonObject.get("passangerCapacity")));
		return van;
	}

}
