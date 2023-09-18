package com.worldclock.pageobjects;

import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.worldclock.config.BaseTestConfig;
import com.worldclock.config.PropertyConfig;

public class LoginPage extends BaseTestConfig{

	WebDriverWait loginWait;
	
	@FindBy(xpath = "//input[@type='email']")
	WebElement emailInput;
	
	@FindBy(xpath="//input[@type='password']")
	WebElement passwordInput;
	
	@FindBy(xpath="//input[@type='submit' and @value='Yes']")
	WebElement nextBtn;
	
	private static final Logger logger = LogManager.getLogger(LoginPage.class);
	
	
	public LoginPage() {
//		an explicit wait of 40 seconds for user to enter password manually and pass code verification
		loginWait=new WebDriverWait(driver, Duration.ofSeconds(40));
	}
	
	public void loginUser() {
		logger.info("User signing in...");
		enterUserDetails();
//		wait until Be.cognizant homepage is loaded
		waitUntilRedirected();
	}
	
	private void enterUserDetails() {
		String emailId;
		
		emailId=PropertyConfig.getTestUserEmail();
		wait.until(ExpectedConditions.visibilityOf(emailInput));
		emailInput.sendKeys(emailId);
//		taking screenshot
		captureScreenShot(this.getClass().getSimpleName());
		emailInput.sendKeys(Keys.ENTER);
//		The password and verification step must be cleared manually
		logger.info("waiting for user to enter password & clear code verification manually...");
//		waiting for manual steps to be over
		loginWait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(nextBtn)));
		logger.info("Login manual procedures cleared...");
//		taking screenshot
		captureScreenShot(this.getClass().getSimpleName());
		nextBtn.click();
		
	}
	public void waitUntilRedirected() {
		loginWait.until(ExpectedConditions.titleIs(PropertyConfig.getHomepageTitle()));
		logger.info("User signed in & redirected to homepage.");
	}

	public HomePage getHomepage() {
		return PageFactory.initElements(driver, HomePage.class);
	}

}
