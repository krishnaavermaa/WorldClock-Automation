package com.worldclock.pageobjects;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.worldclock.config.BaseTestConfig;
import com.worldclock.utility.constants.TestConstants;

public class WorldClock extends BaseTestConfig {

	@FindBy(xpath = "//div[@data-automation-id='clock-card-location']")
	List<WebElement> locations;
	
	private static final Logger logger = LogManager.getLogger(WorldClock.class);

	public Set<String> retrieveClockLocations() {
		wait.until(ExpectedConditions.visibilityOfAllElements(locations));
		logger.info("Retrieving all world clock locations available.");
		return locations.stream().map(loc -> loc.getText()).collect(Collectors.toSet());
	}

	public LocalDate retrieveDateForLocation(String location) {
		WebElement dateField;
		String dateStr;
		LocalDate date = null;
		ZoneId zoneId;

		logger.info("Extracting date for "+location);
		zoneId = ZoneId.of(ZoneId.SHORT_IDS.get(extractTimeZone(location)));
		dateFormatter.withZone(zoneId);
		dateField = driver.findElement(By.xpath("//div[@data-automation-id='clock-card-location' and contains(text(),'"
				+ location + "')]/..//div[@data-automation-id='clock-card-time-offset']/../div[2]"));
		dateStr = dateField.getText();
		logger.info("formatting date.");
		try {
			date = LocalDate.parse(dateStr, dateFormatter);
		} catch (DateTimeParseException e) {
			date = LocalDate.parse(dateStr, dateFormatter2);
		}
		dateFormatter.withZone(ZoneId.systemDefault());
		return date;
	}

	public LocalTime retrieveTimeForLocation(String location) {
		WebElement timeField;
		String timeStr;
		LocalTime time = null;
		ZoneId zoneId;

		logger.info("Extracting time for "+location);
		zoneId = ZoneId.of(ZoneId.SHORT_IDS.get(extractTimeZone(location)));
		timeFormatter.withZone(zoneId);
		timeField = driver.findElement(By.xpath("//div[@data-automation-id='clock-card-location' and contains(text(),'"
				+ location + "')]/..//div[@data-automation-id='clock-card-AM-PM-time']"));
		timeStr = timeField.getText();
		timeStr = timeStr.replace("AM", " am");
		timeStr = timeStr.replace("PM", " pm");
		logger.info("formatting time.");
		time = LocalTime.parse(timeStr, timeFormatter);
		timeFormatter.withZone(ZoneId.systemDefault());
		return time;
	}

	public String retrieveTimeOffsetForLocation(String location) {
		WebElement timeOffsetField;
		logger.info("Retrieving time offset for location "+location);
		timeOffsetField = driver
				.findElement(By.xpath("//div[@data-automation-id='clock-card-location' and contains(text(),'" + location
						+ "')]/..//div[@data-automation-id='clock-card-time-offset']"));
		return timeOffsetField.getText();
	}

	private String extractTimeZone(String location) {
		logger.info("Extracting timezone for location "+location);
		if (location.contains("("))
			return location.substring(location.indexOf('(') + 1, location.indexOf(')'));
		else
			// default timezone[ Bangalore(India) 's timezone ]
			return TestConstants.DEFAULT_TIMEZONE_SHORT;
	}
}
