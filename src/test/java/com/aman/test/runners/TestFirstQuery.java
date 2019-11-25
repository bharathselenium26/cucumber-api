package com.aman.test.runners;

import org.testng.annotations.DataProvider;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = { "src/test/resources/features/query_example.feature" }, plugin = { "pretty",
		"html:target/cucumber-report/", "json:target/cucumber-report/cucumber.json" }, glue = { "com.aman.test" })
public class TestFirstQuery extends AbstractTestNGCucumberTests {

	@DataProvider(parallel = true)
	public Object[][] scenarios() {
		return super.scenarios();
	}
}
