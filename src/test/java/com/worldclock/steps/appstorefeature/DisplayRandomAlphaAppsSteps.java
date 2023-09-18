package com.worldclock.steps.appstorefeature;

import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Sheet;
import org.junit.Assert;
import org.openqa.selenium.support.PageFactory;

import com.worldclock.config.BaseTestConfig;
import com.worldclock.pageobjects.AppStore;
import com.worldclock.utility.ExcelUtils;
import com.worldclock.utility.constants.ErrorConstants;

import io.cucumber.java.en.Then;

public class DisplayRandomAlphaAppsSteps extends BaseTestConfig {

	private AppStore appStore;
	
	private static final Logger logger = LogManager.getLogger(DisplayRandomAlphaAppsSteps.class);

	public DisplayRandomAlphaAppsSteps() {
		appStore = PageFactory.initElements(driver, AppStore.class);
	}

	@Then("user randomly clicks on any one active alphabet")
	public void user_randomly_clicks_on_any_one_active_alphabet() {
		char randomAlpha = '\0';

//		loop to generate a random upper case alphabet which is not disabled
		while (true) {
//			generating a random alphabet in upper case using RandomStringUtils class
			try {
				logger.info("Generating random alphabet.");
				randomAlpha = RandomStringUtils.randomAlphabetic(1).toUpperCase().charAt(0);
			}
			catch(Exception e) {
				Assert.fail(ErrorConstants.RANDOM_ALPHA_GEN_ERR);
			}
//			if the generated alphabet is not disabled then break out of loop
			appStore.retrieveDisabledAlphabets();
			if (appStore.isNotDisabledAlphabet(randomAlpha))
				break;
		}
		logger.info("Random alphabet generated: "+randomAlpha);
		try {
			appStore.clickOnAlphabet(randomAlpha);
		}
		catch(Exception e) {
			Assert.fail(ErrorConstants.RANDOM_CLICK_ERR);
		}
	}

	@Then("all app names starting with that alphabet should be displayed")
	public void all_app_names_starting_with_that_alphabet_should_be_displayed() {
		List<String> appNames = null;
		String appName;
		ExcelUtils excel;
		Sheet testSheet;
		
		try {
			appNames = appStore.retrieveAllDisplayedApps();
		} catch(Exception e) {
			Assert.fail(ErrorConstants.RETRIEVE_APPS_ERR);
		}
//		test passes if app names are present
		Assert.assertNotEquals(0,appNames.size());
//		Accessing App Names starting with random alphabet
		excel=new ExcelUtils();
		try {
			logger.info("Storing App names in excel sheet.");
			testSheet=excel.getExcelSheet("App names Test Sheet");
			excel.writeData("Captured App names:", testSheet	, 0, 0);
			for(int i=0;i<appNames.size();i++)
			{
				appName=appNames.get(i);
				excel.writeData(appName, testSheet, i+2, 0);
			}
			logger.info("Data stored.");
		}
		catch (Exception e) {
			Assert.fail(ErrorConstants.EXCEL_READ_WRITE_ERR);
		}
	}

}
