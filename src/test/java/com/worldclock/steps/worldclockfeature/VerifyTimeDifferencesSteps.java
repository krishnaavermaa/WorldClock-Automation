package com.worldclock.steps.worldclockfeature;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Iterator;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.support.PageFactory;

import com.worldclock.config.BaseTestConfig;
import com.worldclock.pageobjects.HomePage;
import com.worldclock.pageobjects.WorldClock;
import com.worldclock.utility.constants.ErrorConstants;

import io.cucumber.java.en.Then;

public class VerifyTimeDifferencesSteps extends BaseTestConfig {

	HomePage homePage;
	WorldClock worldClock;
	Set<String> locations;
	
	private static final Logger logger = LogManager.getLogger(VerifyTimeDifferencesSteps.class);

	public VerifyTimeDifferencesSteps() {
		homePage = PageFactory.initElements(driver, HomePage.class);
	}

	@Then("show correct time gap between Bangalore and every other timezone time displayed")
	public void correct_time_gap_between_bangalore_and_other_timezone_time() {
		String location;
		Iterator<String> locationsIterator;
		long expectedHourTimeOffset;
		long expectedMinutesTimeOffset;
		String expectedTimeOffset;
		String displayedTimeOffset;
		LocalDate bangaloreDisplayedDate;
		LocalTime bangaloreDisplayedTime;
		LocalDate otherLocationDate;
		LocalTime otherLocationTime;
		Duration duration;

		worldClock = homePage.retrieveWorldClock();
		try {
			locations = worldClock.retrieveClockLocations();
		} catch (Exception e) {
			Assert.fail(ErrorConstants.RETRIEVE_LOCATIONS_ERR);
		}
		locationsIterator = locations.iterator();

//		looping through all displayed locations besides Bangalore and verifying displayed time difference
		while (locationsIterator.hasNext()) {
			location = locationsIterator.next();
			try {
				if (!location.toLowerCase().contains("bangalore")) {
					bangaloreDisplayedDate = worldClock.retrieveDateForLocation("Bangalore");
					logger.info("Bangalore date displayed: "+bangaloreDisplayedDate);
					otherLocationDate = worldClock.retrieveDateForLocation(location);
					logger.info(location+" date displayed: "+otherLocationDate);
					bangaloreDisplayedTime = worldClock.retrieveTimeForLocation("Bangalore");
					logger.info("Bangalore time displayed: "+bangaloreDisplayedTime);
					otherLocationTime = worldClock.retrieveTimeForLocation(location);
					logger.info(location+" time displayed: "+otherLocationTime);
//					retrieving actual displayed time difference
					displayedTimeOffset = worldClock.retrieveTimeOffsetForLocation(location);
					logger.info("Displayed time difference: "+displayedTimeOffset);

//				calculating time difference
					logger.info("Date, Time retrieved. Calculating time difference.");
					duration = Duration.between(bangaloreDisplayedDate.atTime(bangaloreDisplayedTime),
							otherLocationDate.atTime(otherLocationTime));
//				calculating hourly time difference
					expectedHourTimeOffset = duration.toHours();
//				calculating minutes time difference
					expectedMinutesTimeOffset = duration.toMinutes() % 60;
					expectedTimeOffset = expectedHourTimeOffset + "h " + Math.abs(expectedMinutesTimeOffset % 60) + "m";
//				test passes if actual time difference message displayed matches expected time difference
					logger.info("Verifying time difference");
					if (expectedTimeOffset.equals(displayedTimeOffset))
					{
						logger.info("Expected time difference: "+expectedTimeOffset);
						Assert.assertTrue(true);
					}
					else {
						if (expectedHourTimeOffset < 0)
							expectedTimeOffset = Math.abs(expectedHourTimeOffset) + "h "
									+ Math.abs(expectedMinutesTimeOffset % 60) + "m behind";
						else
							expectedTimeOffset = Math.abs(expectedHourTimeOffset) + "h "
									+ Math.abs(expectedMinutesTimeOffset % 60) + "m ahead";
						logger.info("Expected time difference: "+expectedTimeOffset);
						Assert.assertEquals(expectedTimeOffset, displayedTimeOffset);
					}
				}
			} catch (Exception e) {
				Assert.fail(ErrorConstants.TIME_DIFF_ERR);
			}
		}
	}

}
