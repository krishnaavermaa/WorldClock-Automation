package com.worldclock.pageobjects;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.worldclock.config.BaseTestConfig;

public class AppStore extends BaseTestConfig {

	List<Character> displayedAlphaList;
	List<Character> disabledAlphaList;
	
	@FindBy(css = ".aZHolder>div")
	List<WebElement> alphabetsElements;

	@FindBy(css = "div.charAZBtnNoResult")
	List<WebElement> disabledAlphasElements;
	
	@FindBy(css="div.appStoreAppName")
	List<WebElement> appNameElements;
	
	private static final Logger logger = LogManager.getLogger(AppStore.class);
	
	public AppStore() {
	}

	public List<Character> getAlphabetsDisplayed() {
//		retrieving alphabets and storing as List of Characters
		logger.info("Retrieving Displayed characters as List of characters...");
		displayedAlphaList= alphabetsElements.stream()
				.filter(alpha->{
					String alphaText=alpha.getText();
					return alphaText.length()==1 && alphaText.matches("[A-Za-z]");
				})
				.map(alpha ->alpha.getText().charAt(0))
				.collect(Collectors.toList());
		return displayedAlphaList;
	}

	public List<Character> retrieveDisabledAlphabets() {
//		retrieving list of disabled alphabets and storing as List of Characters
		logger.info("Retrieving Disabled characters as List of characters...");
		disabledAlphaList = disabledAlphasElements.stream()
				.filter(alpha->{
					String alphaText=alpha.getText();
					return alphaText.length()==1 && alphaText.matches("[A-Za-z]");
				})
				.map(alpha ->alpha.getText().charAt(0))
				.collect(Collectors.toList());
		return disabledAlphaList;
	}

	public boolean clickOnAlphabet(char alpha) {
		WebElement alphaToBeClicked;

		alphaToBeClicked = driver.findElement(By.xpath("//div[@class='aZHolder']/div[normalize-space(text())='" + alpha + "']"));
		try {
//		trying to click a disabled alphabet
			logger.info("Performing 'click' on the alphabet: "+alpha);
			alphaToBeClicked.click();
			wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfAllElements(appNameElements)));
		}
//		if click event is not intercepted by the alphabet
		catch(ElementClickInterceptedException e) {
			logger.warn("Click failed on the alphabet: "+alpha);
			return false;
		}
		return true;
	}

	public boolean isNotDisabledAlphabet(char randomAlpha) {
		if(!disabledAlphaList.contains(randomAlpha))
			return true;
		logger.info(randomAlpha+" is disabled.");
		return false;
	}

	public List<String> retrieveAllDisplayedApps() {
//		retrieve all displayed alphabets list as List of Characters
		logger.info("Retrieving all displayed app names as List.");
		List<String> appNamesList= appNameElements.stream().map(appName->{
			return appName.getText();
		}).collect(Collectors.toList());
		return appNamesList;
	}

}
