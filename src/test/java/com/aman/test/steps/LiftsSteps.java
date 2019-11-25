package com.aman.test.steps;

import com.aman.test.commons.pojo.Lifts;
import com.aman.test.core.ApiCore;
import com.aman.test.core.ApiUtils;
import com.aman.test.core.CustomAssert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;

public class LiftsSteps {

	String url = "";
	Lifts lifts = null;

	@Given("Application {string} is initialized")
	public void application_is_initialized(String url) {
		this.url = url;
	}

	@Then("fetch the details of all lifts")
	public void fetch_the_details_of_all_lifts() {
		String payload = "query lifts{\r\n" + " 	allLifts{\r\n" + "     name,\r\n" + "    status\r\n" + "  } \r\n"
				+ "}";
		String json = ApiUtils.getJsonAsString(payload);
		Response response = ApiCore.query(url, json);
		CustomAssert.assertEquals(response.getStatusCode(), 200);
		lifts = response.as(Lifts.class);
	}

	@Then("verify count {string}")
	public void verify_count(String liftsExpectedCount) {
		int liftsExpectedCountValue = Integer.parseInt(liftsExpectedCount);
		int totalActualLifts = lifts.getData().getAllLifts().length;
		CustomAssert.assertEquals(totalActualLifts, liftsExpectedCountValue);
	}

	@Then("query for all lifts")
	public void query_for_all_lifts(String payload) {
		String json = ApiUtils.getJsonAsString(payload);
		Response response = ApiCore.query(url, json);
		CustomAssert.assertEquals(response.getStatusCode(), 200);
		lifts = response.as(Lifts.class);
	}
}
