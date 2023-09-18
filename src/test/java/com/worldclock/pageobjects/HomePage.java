package com.worldclock.pageobjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.worldclock.config.BaseTestConfig;
import com.worldclock.config.PropertyConfig;

public class HomePage extends BaseTestConfig {

	@FindBy(id = "O365_ShyHeader")
	WebElement headerElement;

	@FindBy(id = "O365_HeaderRightRegion")
	WebElement userProfile;

	@FindBy(id = "mectrl_currentAccount_primary")
	WebElement username;

	@FindBy(id = "mectrl_currentAccount_secondary")
	WebElement userEmail;

	@FindBy(id = "60655e4a-73c8-49d0-9571-c762791557af")
	WebElement worldClockContainer;

	@FindBy(xpath = "//span[text()='World Clock']")
	WebElement worldClock;
	
	@FindBy(xpath = "//div[@data-automation-id='contentScrollRegion']")
	WebElement scrollableRegion;

	@FindBy(xpath = "//div[@title='OneCognizant']")
	WebElement onecognizantApp;
	
	private static final Logger logger = LogManager.getLogger(HomePage.class);

	public HomePage() {
	}

	public void expandHeader() {
		logger.info("Expanding header element in webpage.");
		headerElement.click();
	}

	public void openUserProfileSection() {
		wait.until(ExpectedConditions.elementToBeClickable(userProfile));
		logger.info("Opening Userprofile section.");
		userProfile.click();
	}

	public OneCognizant openOneCognizantQuickapp() {
		wait.until(ExpectedConditions.visibilityOf(onecognizantApp));
		onecognizantApp.click();
		driver.switchTo().window(driver.getWindowHandles().toArray()[1].toString());
//		waiting for OneCognizant app to be opened
		try {
		wait.until(ExpectedConditions.titleIs(PropertyConfig.getOnecognizantTitle()));
		}
		catch(TimeoutException e) {
			driver.navigate().refresh();
			wait.until(ExpectedConditions.titleIs(PropertyConfig.getOnecognizantTitle()));
		}
		logger.info("One Cognizant app opened.");
		return PageFactory.initElements(driver, OneCognizant.class);
	}

	public String getUsername() {
		wait.until(ExpectedConditions.visibilityOf(username));
		logger.info("Capturing username.");
		return username.getText();
	}

	public String getUserEmail() {
		logger.info("Capturing user email id.");
		return userEmail.getText();
	}

	public void scrollToWorldClock() {
		((JavascriptExecutor)driver).executeScript("arguments[0].scrollTop=arguments[0].scrollHeight",scrollableRegion);
		logger.info("Scrolling to 'world clock' section...");
	}

	public void scrollToOnecognizantApp() {
		((JavascriptExecutor)driver).executeScript("arguments[0].scrollTo(0, 200);",scrollableRegion);
		logger.info("Scrolling to 'one cognizant' -- hotapps section...");
	}

	public boolean hasWorldClockInView() {
//			return true if worldClock element is not null and is visible
		try {
			wait.until(ExpectedConditions.visibilityOf(worldClock));
			return worldClock != null;
		} catch (Exception e) {
			return false;
		}
	}

	public WorldClock retrieveWorldClock() {
		return PageFactory.initElements(driver, WorldClock.class);
	}
	
//	public LoginPage getLoginPage() {
//		return PageFactory.initElements(driver, LoginPage.class);
//	}

//	public boolean isLoginPage() {
//		String title=driver.getTitle();
//		return title.equals(PropertyConfig.getLoginpageTitle());
//	}

//	public boolean isHomePage() {
//		String title=driver.getTitle();
//		return title.equals(PropertyConfig.getHomepageTitle());
//	}

}
