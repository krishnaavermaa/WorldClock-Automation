package com.worldclock.steps.shared;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.support.PageFactory;

import com.worldclock.config.BaseTestConfig;
import com.worldclock.config.PropertyConfig;
import com.worldclock.pageobjects.HomePage;
import com.worldclock.pageobjects.LoginPage;
import com.worldclock.pageobjects.OneCognizant;
import com.worldclock.utility.ExcelUtils;
import com.worldclock.utility.constants.ErrorConstants;

import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

public class SharedSteps extends BaseTestConfig {

	private LoginPage loginPage;
	private HomePage homePage;
	private OneCognizant oneCognizant;
	private String mainWindow;
	
	private static final Logger logger = LogManager.getLogger(SharedSteps.class);

	@BeforeAll
	public static void openBrowser() {
		instantiateDriver();
	}

	@Before
	public void setup() {
		logger.info("***Executing Scenario***");
		try {
//			retrieving test url
			String testUrl = PropertyConfig.getTestUrl();
//			retrieving current window name
			mainWindow = driver.getWindowHandle();
//			opening url
			super.openUrl(testUrl);
		} catch (Exception e) {
			Assert.fail(ErrorConstants.GENERAL_ERR);
		}
	}

	@Given("user is on becognizant homepage")
	public void user_is_on_becognizant_homepage() {
		try {
//			when homepage is opened
			if (super.getPageTitle().equals(PropertyConfig.getHomepageTitle())) {
				logger.info("User on homepage.");
				homePage = PageFactory.initElements(driver, HomePage.class);
			}
//			if login page is opening then login the user
			else if (super.getPageTitle().equals(PropertyConfig.getLoginpageTitle())) {
				logger.info("User on login page.");
				loginPage = PageFactory.initElements(driver, LoginPage.class);
				try {
					loginPage.loginUser();
				} catch (Exception e) {
					Assert.fail(ErrorConstants.LOGIN_ERR);
				}
				homePage = loginPage.getHomepage();
			}
//			neither homepage nor loginpage opened
			else
				Assert.fail(ErrorConstants.HOMEPAGE_ACCESS_ERR);
		} catch (Exception e) {
			Assert.fail(ErrorConstants.HOMEPAGE_ACCESS_ERR);
		}
	}

	@When("user scrolls down to world clock section")
	public void user_scrolls_down_to_world_clock_section() {
		try {
//			scroll to world clock section
			homePage.scrollToWorldClock();
		} catch (Exception e) {
			Assert.fail(ErrorConstants.WORLDCLOCK_SCROLL_ERR);
		}
	}

	@When("user navigates to onecognizant app using quicklink")
	public void user_navigates_to_onecognizant_app_using_quicklink() {
		try {
//			scroll to one cognizant app
			homePage.scrollToOnecognizantApp();
		} catch (Exception e) {
			Assert.fail(ErrorConstants.ONEOCGNIZANT_SCROLL_ERR);
		}
		try {
//			open one cognizant app
			oneCognizant = homePage.openOneCognizantQuickapp();
		} catch (Exception e) {
			Assert.fail(ErrorConstants.ONECOGNIZANT_ACCESS_ERR);
		}
	}

	@And("user click on View All Apps button under Hot Apps")
	public void user_click_on_view_all_apps_button_under_hot_apps() {
		try {
//			scroll to view all hot apps button in one cognizant app
			oneCognizant.scrollToHotAppsContainer();
		} catch (Exception e) {
			Assert.fail(ErrorConstants.HOTAPPS_SCROLL_ERR);
		}
		try {
//			clicking on view all hot apps button
			oneCognizant.viewAllApps();
		} catch (Exception e) {
			Assert.fail(ErrorConstants.APPSTORE_ACCESS_ERR);
		}
	}

	@After
	public void closeExcessiveTabs() {
		try {
//			get current opened tab
			String currWindow = driver.getWindowHandle();
//			close the tab if it is not the first tab opened
			if (!currWindow.equals(mainWindow)) {
				driver.close();
				driver.switchTo().window(mainWindow);
			}
		} catch (Exception e) {
			logger.warn(ErrorConstants.TABS_CLOSE_WARN);
		}
	}

	@AfterAll
	public static void quitBrowser() throws InterruptedException {
//		quit the driver and browser window
		ExcelUtils.saveData();
		logger.info("*****Ending Tests*****");
		closeDriver();
	}
}
