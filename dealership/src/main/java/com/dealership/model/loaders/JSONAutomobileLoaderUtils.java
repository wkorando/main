package com.dealership.model.loaders;

import java.math.BigDecimal;

import org.json.simple.JSONObject;

import com.dealership.model.Automobile;

public final class JSONAutomobileLoaderUtils {
	private JSONAutomobileLoaderUtils() {
	}

	public static Automobile loadAutomobile(Automobile automobile, JSONObject object) {
		automobile.setId(Long.valueOf((String) object.get("id")));
		automobile.setMake((String) object.get("make"));
		automobile.setModel((String) object.get("model"));
		automobile.setYear(Integer.valueOf((String) object.get("year")));
		automobile.setPrice(new BigDecimal(Double.valueOf((String) object.get("price"))));
		automobile.setNumberInStock(Integer.valueOf((String) object.get("numberInStock")));
		return automobile;
	}

}
