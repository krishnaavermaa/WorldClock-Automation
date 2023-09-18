package com.worldclock.utility.constants;

public class ErrorConstants {
	
	private ErrorConstants() {
	}
	
//	Error constants
	public static final String GENERAL_ERR = "ERROR: Unexpected Error occured while executing test.";
	public static final String PROPERTY_FILE_ERR = "ERROR: Unexpected error while locating properties file or loading it's data.";
	public static final String EXCEL_FILE_IO_ERR = "ERROR: Unexpected error while creating new excel file or accessing a pre-existing file.";
	public static final String INVALID_BROWSER_ERR = "ERROR: Invalid Browser name provided. Please re-check.";
	public static final String DISABLED_ALPHAS_ABSENT_ERR = "ERROR: No Disabled Alphabet present.";
	public static final String RANDOM_ALPHA_GEN_ERR = "ERROR: Unable to generate random alphabet.";
	public static final String RANDOM_CLICK_ERR = "ERROR: Unable to click on the random generated alphabet.";
	public static final String RETRIEVE_APPS_ERR = "ERROR: Unexpected error while retrieving displayed apps names.";
	public static final String RETRIEVE_ALPHAS_ERR = "ERROR: Unexpected error while retrieving Alphabets from Hot Apps section.";
	public static final String RETRIEVE_DISABLED_ALPHAS_ERR = "ERROR: Unexpected error while retrieving Disabled Alphabets list";
	public static final String HOMEPAGE_ACCESS_ERR = "ERROR: Unable to navigate to Be.Cognizant homepage.";
	public static final String WORLDCLOCK_SCROLL_ERR = "ERROR: Unable to scroll to world clock section.";
	public static final String ONEOCGNIZANT_SCROLL_ERR = "ERROR: Unable to scroll to One Cognizant Application.";
	public static final String ONECOGNIZANT_ACCESS_ERR = "ERROR: Unable to open and access to onecognizant page.";
	public static final String HOTAPPS_SCROLL_ERR = "ERROR: Unable to scroll to View All Hot Apps section.";
	public static final String APPSTORE_ACCESS_ERR = "ERROR: Unable to open and access App Store page.";
	public static final String DISABLED_ALPHAS_CLICK_ERR = "ERROR: Unable to test 'click' event on displayed disabled alphabets.";
	public static final String USERPROFILE_CLICK_ERR = "ERROR: Unexpected error occured while trying to open User Profile section.";
	public static final String USERINFO_ERR = "ERROR: Unexpected Error occured while trying to retrieve user info.";
	public static final String WORLDCLOCK_DISPLAY_ERR = "ERROR: Error while displaying World Clock.";
	public static final String LOCATION_DATE_ERR = "ERROR: Unexpected error occured while parsing date as per format or retrieving displayed date for location.";
	public static final String LOCATION_TIME_ERR = "ERROR: Unexpected error occured while parsing time as per format or retrieving displayed time for location.";
	public static final String SYS_DATE_ERR = "ERROR: Unexpected error occured while parsing system date as per format.";
	public static final String RETRIEVE_LOCATIONS_ERR = "ERROR: Unable to retrieve displayed locations from world clock.";
	public static final String TIME_DIFF_ERR = "ERROR: Unexpected error occured while verifying displayed time difference between Bangalore and other locations.";
	public static final String TIME_GOOGLE_COMPARE_ERR = "ERROR: Unexpected error occured while verifying time with google data.";
	public static final String EXCEL_READ_WRITE_ERR = "ERROR: Unexpected error while performing data read write operations in excel workbook.";
	public static final String LOGIN_ERR = "ERROR: Unexpected error while login or login timeout.";
	
//	Warning Constants
	public static final String QUIT_DRIVER_WARN = "Unexpected error occured while trying to close driver instance and quitting browser.";
	public static final String SCREENSHOT_WARN = "Unexpected error occured while capturing and storing screenshot.";
	public static final String TABS_CLOSE_WARN = "Unexpected error occured while closing excessive Tabs.";



















}
