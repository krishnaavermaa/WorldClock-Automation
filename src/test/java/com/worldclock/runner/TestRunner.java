package com.worldclock.runner;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

//This class uses junit runner to run Cucumber class which in turn runs all the tests
@RunWith(Cucumber.class)
@CucumberOptions(features = {
				"src/test/resources/features/becognizant-userinfo.feature",
				"src/test/resources/features/becognizant-worldclock.feature",
				"src/test/resources/features/onecognizant-appstore.feature" },
				glue = {"com.worldclock.steps" },
				dryRun = false,
				plugin = { "html:target/cucumber-reports/testReport.html" },
				monochrome = true)

public class TestRunner {

	public static void main(String[] args) {
	}

}
