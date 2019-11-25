package com.aman.test.core;

import org.json.JSONObject;

public class ApiUtils {

	public static String getJsonAsString(String payload) {
		JSONObject json = new JSONObject();
		json.put("query", payload);
		return json.toString();
	}
}
