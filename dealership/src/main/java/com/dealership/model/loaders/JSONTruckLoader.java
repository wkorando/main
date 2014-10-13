package com.dealership.model.loaders;

import org.json.simple.JSONObject;

import com.dealership.model.Automobile;
import com.dealership.model.Truck;

public class JSONTruckLoader implements AutomobileLoader {

	public Automobile loadAutomobile(Object object) {
		JSONObject jsonObject = (JSONObject) object;
		Truck truck = (Truck) JSONAutomobileLoaderUtils.loadAutomobile(new Truck(), (JSONObject) jsonObject);
		truck.setHaulingCapacity(Integer.valueOf((String) jsonObject.get("haulingCapacity")));
		truck.setTowingCapacity(Integer.valueOf((String) jsonObject.get("towingCapacity")));
		truck.setTruckBedSize(Double.valueOf((String) jsonObject.get("truckBedSize")));
		return truck;
	}

}
