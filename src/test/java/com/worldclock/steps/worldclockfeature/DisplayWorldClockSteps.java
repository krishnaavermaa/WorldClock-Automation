package com.worldclock.steps.worldclockfeature;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.support.PageFactory;

import com.worldclock.config.BaseTestConfig;
import com.worldclock.pageobjects.HomePage;
import com.worldclock.utility.constants.ErrorConstants;

import io.cucumber.java.en.Then;

public class DisplayWorldClockSteps extends BaseTestConfig {

	HomePage homePage;
	
	private static final Logger logger = LogManager.getLogger(DisplayWorldClockSteps.class);
	
	public DisplayWorldClockSteps() {
		homePage = PageFactory.initElements(driver, HomePage.class);
	}

	@Then("world clock should be displayed")
	public void world_clock_should_be_displayed() {
//		test passes if world clock is visible i.e present and has a non-zero height & width
		boolean clockInView=false;
		try {
			 clockInView= homePage.hasWorldClockInView();
		} catch (Exception e) {
			Assert.fail(ErrorConstants.WORLDCLOCK_DISPLAY_ERR);
		}
		logger.info("World clock is in view.");
		Assert.assertTrue(clockInView);
	}

}
