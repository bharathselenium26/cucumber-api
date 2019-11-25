package com.aman.test.core;

import static io.restassured.RestAssured.given;

import io.restassured.response.Response;

public class ApiCore {

	public static Response query(String apiPath, String json) {
		Response response = given().contentType("application/json;charset=UTF-8").body(json).when().log().all()
				.post(apiPath.trim());

		return response;
	}

	public static Response mutation(String apiPath, String json) {
		Response response = given().contentType("application/json;charset=UTF-8").body(json).when().log().all()
				.post(apiPath.trim());

		return response;
	}

	public static Response subscription(String apiPath,String json) {
		Response response = given().contentType("application/json;charset=UTF-8").body(json).when().log().all()
				.post(apiPath.trim());

		return response;
	}

	
	public void test1() {
		
	}
}
