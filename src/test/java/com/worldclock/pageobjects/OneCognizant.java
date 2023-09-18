package com.worldclock.pageobjects;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.worldclock.config.BaseTestConfig;

public class OneCognizant extends BaseTestConfig {

	@FindBy(className = "viewAllHotsAppsHolder")
	WebElement viewAllHotAppsBtn;

	@FindBy(id = "appstoreHeader")
	WebElement appStoreHeader;

	@FindBy(id = "WelcomeNoteRenderDiv")
	WebElement welcomeTextEle;

	public void scrollToHotAppsContainer() {
		try {
			wait.until(ExpectedConditions.visibilityOf(welcomeTextEle));
		} catch (TimeoutException e) {
			driver.navigate().refresh();
			wait.until(ExpectedConditions.visibilityOf(welcomeTextEle));
		}
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", viewAllHotAppsBtn);
	}

	public AppStore viewAllApps() {
		try {
			wait.until(ExpectedConditions.visibilityOf(viewAllHotAppsBtn));
		} catch (TimeoutException e) {
			driver.navigate().refresh();
			wait.until(ExpectedConditions.visibilityOf(viewAllHotAppsBtn));
		}
		viewAllHotAppsBtn.click();
//		waiting for appstore to be openend
		wait.until(ExpectedConditions.visibilityOf(appStoreHeader));
		return PageFactory.initElements(driver, AppStore.class);
	}

}
