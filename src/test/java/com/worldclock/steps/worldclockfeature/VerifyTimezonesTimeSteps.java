package com.worldclock.steps.worldclockfeature;

import java.time.LocalTime;
import java.util.Iterator;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.support.PageFactory;

import com.worldclock.config.BaseTestConfig;
import com.worldclock.config.PropertyConfig;
import com.worldclock.pageobjects.GoogleSearchPage;
import com.worldclock.pageobjects.HomePage;
import com.worldclock.pageobjects.WorldClock;
import com.worldclock.utility.constants.ErrorConstants;

import io.cucumber.java.en.Then;

public class VerifyTimezonesTimeSteps extends BaseTestConfig {

	HomePage homePage;
	WorldClock worldClock;
	Set<String> locations;
	
	private static final Logger logger = LogManager.getLogger(VerifyTimezonesTimeSteps.class);

	public VerifyTimezonesTimeSteps() {
		homePage = PageFactory.initElements(driver, HomePage.class);
	}

	@Then("the Bangalore Time should match the System Time")
	public void bangalore_time_match_system_time() {
		LocalTime sysTime = null;
		LocalTime bangaloreDisplayedTime = null;

//		retrieving Bangalore time displayed
		try {
			worldClock = homePage.retrieveWorldClock();
			bangaloreDisplayedTime = worldClock.retrieveTimeForLocation("Bangalore");
			logger.info("Bangalore's displayed time: "+bangaloreDisplayedTime);
//		retrieving system time
			logger.info("Retrieving System time.");
			sysTime = LocalTime.now();
			logger.info("Formatting System time.");
			sysTime = LocalTime.parse(sysTime.format(timeFormatter), timeFormatter);
			logger.info("System time: "+sysTime);
		} catch (Exception e) {
			Assert.fail(ErrorConstants.LOCATION_TIME_ERR);
		}
//		test passes if bangalore time displayed matches system time;
		Assert.assertEquals(0, sysTime.compareTo(bangaloreDisplayedTime));
	}

	@Then("every other displayed timezone time should match the time as per google data")
	public void other_timezone_time_match_google_time() {
		String location;
		Iterator<String> locationsIterator;
		GoogleSearchPage googleSearch;
		LocalTime googleTimeData;
		LocalTime displayedTimeData;

		locations = worldClock.retrieveClockLocations();
		locationsIterator = locations.iterator();
//		looping through every world clock displayed besides bangalore
		while (locationsIterator.hasNext()) {
			location = locationsIterator.next();
			try {
				if (!location.toLowerCase().contains("bangalore")) {
					location = location.replaceAll("\\(.*\\)", "");
//				opening google search page
					openGoogleSearchPage();
					googleSearch = PageFactory.initElements(driver, GoogleSearchPage.class);
					googleSearch.clickSearchBox();
//				searching location's date time
					googleSearch.searchDateTimeAtLocation(location);
//				retrieving date time data by google for the location
					googleTimeData = googleSearch.getGoogleTime();
					logger.info("Expected Time for "+location+" as per google data: "+googleTimeData);
//				switching back to test page and retrieving actual time displayed for location
					switchToTestPage();
					displayedTimeData = worldClock.retrieveTimeForLocation(location);
					logger.info("Displayed time for "+location+": "+displayedTimeData);
//				test passes if displayed time matches google time
					Assert.assertEquals(0, displayedTimeData.compareTo(googleTimeData));
				}
			} catch (Exception e) {
				Assert.fail(ErrorConstants.TIME_GOOGLE_COMPARE_ERR);
			}
		}
	}

	private void openGoogleSearchPage() {
		super.openNewTab();
		logger.info("Switching to new tab.");
		driver.switchTo().window(driver.getWindowHandles().toArray()[1].toString());
		super.openUrl(PropertyConfig.getGoogleUrl());
	}

	private void switchToTestPage() {
		driver.close();
		logger.info("Switching to Default window.");
		driver.switchTo().window(driver.getWindowHandles().toArray()[0].toString());
	}

}
