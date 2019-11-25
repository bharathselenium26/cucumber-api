package com.aman.test.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = { "src/test/resources/features/query_example.feature" }, plugin = { "pretty",
"html:target/cucumber-report/" }, glue = { "com.aman.test" })
public class TestFirstQuery extends AbstractTestNGCucumberTests {

}
