package com.worldclock.steps.worldclockfeature;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.WeekFields;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.support.PageFactory;

import com.worldclock.config.BaseTestConfig;
import com.worldclock.pageobjects.HomePage;
import com.worldclock.pageobjects.WorldClock;
import com.worldclock.utility.constants.ErrorConstants;

import io.cucumber.java.en.Then;

public class VerifyBangaloreDateSteps extends BaseTestConfig {

	HomePage homePage;
	WorldClock worldClock;
	LocalDate bangaloreDisplayedDate;
	LocalDate systemDate;
	
	private static final Logger logger = LogManager.getLogger(VerifyBangaloreDateSteps.class);

	public VerifyBangaloreDateSteps() {
		homePage = PageFactory.initElements(driver, HomePage.class);
	}

	@Then("the Bangalore date should match the System Date")
	public void bangalore_date_should_match_system_date() {
		String bangaloreDateStr = null;
		String systemDateStr = null;

		worldClock = homePage.retrieveWorldClock();
//		retrieving displayed date for Bangalore location
		try {
			bangaloreDisplayedDate = worldClock.retrieveDateForLocation("Bangalore");
			bangaloreDateStr = bangaloreDisplayedDate.format(dateFormatter);
			logger.info("Displayed Bangalore date: "+bangaloreDateStr);
		} catch (Exception e) {
			Assert.fail(ErrorConstants.LOCATION_DATE_ERR);
		}
//		retrieving system date
		try {
			logger.info("Retrieving system date.");
			systemDate = LocalDate.now();
//			.format(dateFormatter);
			logger.info("Formatting system date.");
			systemDateStr = systemDate.format(dateFormatter);
			logger.info("System date: "+systemDateStr);
		} catch (Exception e) {
			Assert.fail(ErrorConstants.SYS_DATE_ERR);
		}

//		test passes if Bangalore Date is exactly equal to System Date
		Assert.assertEquals(systemDateStr, bangaloreDateStr);
	}

	@Then("the Bangalore Day should match the System Day")
	public void bangalore_day_should_match_system_day() {
		DayOfWeek currDisplayedDay;
		DayOfWeek systemDateDay;

		logger.info("Retrieving the 'Day' from displayed Bangalore date");
		currDisplayedDay = bangaloreDisplayedDate.getDayOfWeek();
		logger.info("Displayed Bangalore Date's Day: "+currDisplayedDay);
		logger.info("Retrieving the 'Day' from system date");
		systemDateDay = systemDate.getDayOfWeek();
		logger.info("System date's Day: "+systemDateDay);
//		test passes is displayed Bangalore date's day equals system date's day
		Assert.assertEquals(currDisplayedDay, systemDateDay);
	}

	@Then("the Bangalore week should match the System week")
	public void bangalore_week_should_match_system_week() {
		int bangaloreDisplayedDateWeek;
		int systemDateWeek;

		logger.info("Retrieiving Bangalore's Date week number.");
		bangaloreDisplayedDateWeek = bangaloreDisplayedDate.get(WeekFields.ISO.weekOfYear());
		logger.info("Retrieving System Date's week number.");
		systemDateWeek = systemDate.get(WeekFields.ISO.weekOfYear());
//		test passes is displayed Bangalore date's week equals system date's week
		Assert.assertEquals(bangaloreDisplayedDateWeek, systemDateWeek);
	}

}
