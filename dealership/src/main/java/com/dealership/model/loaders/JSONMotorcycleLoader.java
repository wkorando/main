package com.dealership.model.loaders;

import org.json.simple.JSONObject;

import com.dealership.model.Automobile;
import com.dealership.model.Motorcycle;

public class JSONMotorcycleLoader implements AutomobileLoader {

	public Automobile loadAutomobile(Object object) {
		JSONObject jsonObject = (JSONObject) object;
		Motorcycle motorcycle = (Motorcycle) JSONAutomobileLoaderUtils.loadAutomobile(new Motorcycle(), (JSONObject) jsonObject);
		motorcycle.setEngineSize(Integer.valueOf((String) jsonObject.get("engineSize")));
		motorcycle.setHasSideCar(Boolean.valueOf((String) jsonObject.get("hasSidecar")));
		return motorcycle;
	}

}
