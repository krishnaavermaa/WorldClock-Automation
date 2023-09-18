package com.worldclock.config;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;

import com.worldclock.utility.constants.ErrorConstants;
import com.worldclock.utility.constants.TestConstants;

public class PropertyConfig extends BaseTestConfig{

	private static File testDataPropertiesFile;
	private static Properties testdataProp;
	
	private static final Logger logger = LogManager.getLogger(PropertyConfig.class);

	private PropertyConfig() {
	}

	static {
		testdataProp = new Properties();
		try {
			testDataPropertiesFile = new File(TestConstants.TESTDATA_FILE_PATH);
//		loading properties file data
			testdataProp.load(new FileInputStream(testDataPropertiesFile));
			logger.info("Properties file loaded.");
		} catch (Exception e) {
			Assert.fail(ErrorConstants.PROPERTY_FILE_ERR);
		}
	}

	public static String getTestBrowser() {
		return testdataProp.getProperty("test.browser","CHROME");
	}

	public static String getTestUrl() {
		return testdataProp.getProperty("test.url");
	}

	public static String getTestUserEmail() {
		return testdataProp.getProperty("test.user.email");
	}
	
	public static String getHomepageTitle() {
		return testdataProp.getProperty("becognizant.homepage.title");
	}
	
	public static String getLoginpageTitle() {
		return testdataProp.getProperty("becognizant.loginpage.title");
	}
	
	public static String getOnecognizantTitle() {
		return testdataProp.getProperty("onecognizant.page.title");
	}
	
	public static String getGoogleUrl() {
		return testdataProp.getProperty("google.url");
	}

	public static String getExcelFilePath() {
		 return testdataProp.getProperty("test.output.xlsx.path");
	}
	
	public static String getScreenshotDir() {
		return testdataProp.getProperty("test.output.screenshot.path");
	}
	
	public static boolean getScreenshotPreference() {
		String screenshotPreference=testdataProp.getProperty("test.screenshots.preference","ON").toUpperCase();
		if(screenshotPreference.equals("ON")) return true;
		else if(screenshotPreference.equals("OFF"))return false;
		return true;
		
	}
	

}
