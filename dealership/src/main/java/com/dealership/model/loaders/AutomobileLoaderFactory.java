package com.dealership.model.loaders;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

public class AutomobileLoaderFactory {
	private Logger LOG = Logger.getLogger(AutomobileLoaderFactory.class);

	public AutomobileLoader findLoaderForJSONObject(JSONObject jsonObject) {
		AutomobileLoader loader = null;
		if (jsonObject.containsKey("engineSize")) {
			loader = loadJSONClass("Motorcycle");
		} else if (jsonObject.containsKey("haulingCapacity")) {
			loader = loadJSONClass("Truck");
		} else if (jsonObject.containsKey("mpg")) {
			loader = loadJSONClass("Sedan");
		} else if (jsonObject.containsKey("passangerCapacity")) {
			loader = loadJSONClass("Van");
		}
		return loader;
	}

	private AutomobileLoader loadJSONClass(String type) {
		try {
			return (AutomobileLoader) AutomobileLoaderFactory.class.getClassLoader().loadClass("com.dealership.model.loaders.JSON" + type + "Loader")
					.newInstance();
		} catch (ReflectiveOperationException e) {
			LOG.error("An error occured while attempting to load a AutomobileLoader for type: " + type, e);
			throw new RuntimeException("An error occured while attempting to load a AutomobileLoader for type: " + type, e);
		}
	}
}
