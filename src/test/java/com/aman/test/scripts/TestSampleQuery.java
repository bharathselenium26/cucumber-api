package com.aman.test.scripts;

import org.json.JSONObject;
import org.testng.annotations.Test;

import com.aman.test.core.APIConstants;
import com.aman.test.core.ApiCore;

import io.restassured.response.Response;

public class TestSampleQuery {

	@Test
	public void verifySampleQuery() {
		String payload = "query lifts{\r\n" + 
				" 	allLifts{\r\n" + 
				"     name,\r\n" + 
				"    status\r\n" + 
				"  } \r\n" + 
				"}";
		JSONObject json = new JSONObject();
        json.put("query",payload);
		
		Response response = ApiCore.query(APIConstants.URL, json.toString());
		System.out.println(response.getBody().asString());
	}
	
	@Test
	public void verifySampleMutation() {
		String payload = "mutation {\r\n" + 
				"  setLiftStatus(id: \"panorama\" status: CLOSED) {\r\n" + 
				"    name\r\n" + 
				"    status\r\n" + 
				"  }\r\n" + 
				"}";
		JSONObject json = new JSONObject();
        json.put("query",payload);
		
		Response response = ApiCore.mutation(APIConstants.URL, json.toString());
		System.out.println(response.getBody().asString());
	}
	
	@Test
	public void verifySubscription() {
		String payload = "subscription {\r\n" + 
				"  liftStatusChange {\r\n" + 
				"    name\r\n" + 
				"    capacity\r\n" + 
				"    status\r\n" + 
				"  }\r\n" + 
				"}";
		JSONObject json = new JSONObject();
        json.put("query",payload);
		
		Response response = ApiCore.mutation(APIConstants.URL, json.toString());
		System.out.println(response.getBody().asString());
	}
}
