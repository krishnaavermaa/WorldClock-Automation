package com.worldclock.config;

import java.io.File;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import org.junit.Assert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.worldclock.utility.constants.ErrorConstants;
import com.worldclock.utility.constants.TestConstants;
import com.worldclock.utility.enums.BrowserEnums;

public class BaseTestConfig {

	protected static WebDriver driver;
	protected static WebDriverWait wait;
	protected static DateTimeFormatter dateFormatter;
	protected static DateTimeFormatter dateFormatter2;
	protected static DateTimeFormatter timeFormatter;
	private static int ssSequence;
	private static long startTime;

	private static final Logger logger = LogManager.getLogger(BaseTestConfig.class);
	
	public static void instantiateDriver() {
		String browser;
		BrowserEnums currBrowser;

//		initializing certain fields
		Configurator.initialize(null, "src/test/resources/properties/log4j2.properties");
		startTime = Long.valueOf(System.currentTimeMillis());
		dateFormatter = DateTimeFormatter.ofPattern(TestConstants.DATE_FORMAT);
		dateFormatter2 = DateTimeFormatter.ofPattern(TestConstants.DATE_FORMAT_2);
		timeFormatter = DateTimeFormatter.ofPattern(TestConstants.TIME_FORMAT);
		browser = PropertyConfig.getTestBrowser().toUpperCase();
		currBrowser = BrowserEnums.valueOf(browser);
		switch (currBrowser) {
//		instantiating Chrome Browser
		case CHROME:
			System.setProperty("webdriver.chrome.driver", "drivers/chromedriver116.exe");
			driver = new ChromeDriver();
			break;
//		instantiating Firefox Browser
		case FIREFOX:
			System.setProperty("webdriver.gecko.driver", "drivers/geckodriver64.exe");
			driver = new FirefoxDriver();
			break;
//		invalid browser name case
		default:
			Assert.fail(ErrorConstants.INVALID_BROWSER_ERR);
		}
		logger.info("Using browser: "+browser);
//		maximize browser window
		driver.manage().window().maximize();
		logger.debug("Browser window maximized.");
//		implicit wait of 8 seconds
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(8));
		logger.debug("Specified implicit wait of 8 seconds.");
//		Explicit wait of 20 seconds
		wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		logger.debug("webdriver wait of 20 seconds initialized for use.");
	}

//	gets title of openend page
	public String getPageTitle() {
		return driver.getTitle();
	}

	public void captureScreenShot(String ssName) {
//		if ssSequence is -1 stop execution of function -- this is to speed up test execution when screenshot preference is set to OFF
		if (ssSequence == -1)
			return;
		String ssPath;
		File screenshotTempFile;
		File screenshotFile;
		File screenshotDir;
		boolean screenshotPreference;
		long customTimeStamp;

		ssPath = PropertyConfig.getScreenshotDir();
		try {
//			clearing all previous screenshots (if any) in the desired screenshot directory
			if (ssSequence == 0) {
				screenshotPreference = PropertyConfig.getScreenshotPreference();
				screenshotDir = new File(ssPath);
//				delete the old screenshots if any
				if (screenshotDir.exists())
				{
					Stream.of(screenshotDir.listFiles()).forEach(file -> file.delete());
					logger.warn("Pre-Existing screenshots deleted, if any");
				}
//				if screenshot preference set to zero then stop execution and set ssSequence to -1
				if (!screenshotPreference) {
					logger.info(
							"Screenshots Functionality is OFF. Set your preference to ON if you desire to capture screenshots.");
					ssSequence--;
					return;
				}
//				if screenshot directory doesn't exist, create it
				if (!screenshotDir.exists())
				{
					screenshotDir.mkdirs();
					logger.info("Screenshot storage directory created.");
				}
			}
//			taking a screenshot
			screenshotTempFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			logger.info("Screenshot captured.");
//			preparing custom timestamp for screenshot
			customTimeStamp = Long.valueOf(System.currentTimeMillis()) - startTime;
//			storing the screenshot file as png in desired directory
			screenshotFile = new File(ssPath + (++ssSequence) + "_" + ssName + "_" + customTimeStamp + ".png");
			screenshotFile.createNewFile();
			FileHandler.copy(screenshotTempFile, screenshotFile);
			logger.info("Screenshot stored as: "+screenshotFile.getName());
//			200 ms thread sleep allowing the screenshot to be successfully captured within this time frame
			Thread.sleep(200);
			logger.debug("Continuing test...");
		} catch (Exception e) {
			logger.warn(ErrorConstants.SCREENSHOT_WARN);
		}
	}

	public void openUrl(String url) {
//		navigate to test url
		driver.navigate().to(url);
		logger.info("Navigating to "+url);
	}

	public void openNewTab() {
//		using javascript executor to open new browser tab
		((JavascriptExecutor) driver).executeScript("window.open();");
		logger.info("New Tab opened");
	}

//	code to close driver instance and quit browser
	public static void closeDriver() {
		logger.info("Closing driver and quitting browser window.");
		try {
			driver.quit();
		} catch (Exception e) {
			logger.warn(ErrorConstants.QUIT_DRIVER_WARN);
		}

	}

}
