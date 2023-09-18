package com.worldclock.steps.userinfofeature;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Sheet;
import org.junit.Assert;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.PageFactory;

import com.worldclock.config.BaseTestConfig;
import com.worldclock.pageobjects.HomePage;
import com.worldclock.utility.ExcelUtils;
import com.worldclock.utility.constants.ErrorConstants;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class DisplayUserInfoSteps extends BaseTestConfig {

	HomePage homePage;
	
	private static final Logger logger = LogManager.getLogger(DisplayUserInfoSteps.class);

	public DisplayUserInfoSteps() {
		homePage = PageFactory.initElements(driver, HomePage.class);
	}

	@When("user clicks on user profile icon")
	public void user_clicks_on_user_profile_icon() {
		try {
			try {
				homePage.expandHeader();
			} catch (NoSuchElementException e) {
				logger.warn("User profile section not hidden. Continuing test...");
			}
			homePage.openUserProfileSection();
		} catch (Exception e) {
			Assert.fail(ErrorConstants.USERPROFILE_CLICK_ERR);
		}
	}

	@Then("user info should be displayed")
	public void user_info_should_be_displayed() {
		String username = null;
		String userEmail = null;
		ExcelUtils excel;
		Sheet testSheet;

		try {
			username = homePage.getUsername();
			logger.info("Username: "+username);
			userEmail = homePage.getUserEmail();
			logger.info("User email: "+userEmail);
		} catch (Exception e) {
			Assert.fail(ErrorConstants.USERINFO_ERR);
		}

//		test passes if username and user email is not null
		Assert.assertNotNull(username);
		Assert.assertNotNull(userEmail);
			excel = new ExcelUtils();
		try {
			logger.info("Storing User info in excel sheet.");
			testSheet = excel.getExcelSheet("User Info Test Sheet");
			excel.writeData("Captured User Info:", testSheet, 0, 0);
			excel.writeData("User Name:", testSheet, 2, 0);
			excel.writeData(username, testSheet, 2, 1);
			excel.writeData("User Email Id:", testSheet, 3, 0);
			excel.writeData(userEmail, testSheet, 3, 1);
			logger.info("Data stored.");
		} catch(Exception e) {
			Assert.fail(ErrorConstants.EXCEL_READ_WRITE_ERR);
		}
	}

}
