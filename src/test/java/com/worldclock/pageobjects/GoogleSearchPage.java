package com.worldclock.pageobjects;

import java.time.LocalDate;
import java.time.LocalTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.worldclock.config.BaseTestConfig;

public class GoogleSearchPage extends BaseTestConfig{
	
	@FindBy(id="APjFqb")
	WebElement searchBox;
	
	@FindBy(xpath ="//div[@id='rso']/div[1]//*[@role='heading']")
	WebElement googleTime;
	
	@FindBy(xpath ="//div[@id='rso']/div[1]//*[@role='heading']/../div[2]")
	WebElement googleDate;
	
	private static final Logger logger = LogManager.getLogger(GoogleSearchPage.class);
	
	public GoogleSearchPage() {
	}
	
	public void clickSearchBox() {
		wait.until(ExpectedConditions.elementToBeClickable(searchBox));
		searchBox.click();
	}
	
	public void searchDateTimeAtLocation(String location) {
		String searchStr;
		
		searchStr=location+" date time";
		searchBox.sendKeys(searchStr);
		searchBox.sendKeys(Keys.ENTER);
		logger.info("Executing google search for: "+searchStr);
	}
	
	public LocalTime getGoogleTime() {
		wait.until(ExpectedConditions.visibilityOf(googleTime));
		logger.info("Extracting 'time' from google search results.");
		String googleTimeStr=googleTime.getText();
//		correcting the google time retrieved
		googleTimeStr= googleTimeStr.replaceAll("[^\\w:]+"," ");
		logger.info("Formatting time.");
		return LocalTime.parse(googleTimeStr, timeFormatter);
	}
	
	public LocalDate getGoogleDate() {
		wait.until(ExpectedConditions.visibilityOf(googleDate));
		logger.info("Extracting 'date' from google search results.");
		String googleDateStr=googleDate.getText();
		googleDateStr=googleDateStr.substring(0, googleDateStr.indexOf('('));
		logger.info("Formatting date.");
		return LocalDate.parse(googleDateStr, dateFormatter);
	}
	
	
	
}
