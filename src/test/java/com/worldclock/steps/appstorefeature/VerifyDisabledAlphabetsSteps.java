package com.worldclock.steps.appstorefeature;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.support.PageFactory;

import com.worldclock.config.BaseTestConfig;
import com.worldclock.pageobjects.AppStore;
import com.worldclock.utility.constants.ErrorConstants;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

public class VerifyDisabledAlphabetsSteps extends BaseTestConfig {

	private AppStore appStore;
	private List<Character> disabledAlphas;
	
	private static final Logger logger = LogManager.getLogger(VerifyDisabledAlphabetsSteps.class);

	public VerifyDisabledAlphabetsSteps() {
		appStore = PageFactory.initElements(driver, AppStore.class);
	}

	@And("some alphabets are disabled")
	public void some_alphabets_should_be_disabled() {
		int countDisabled = 0;
		try {
//			retrieving all disabled alphabets list as List of characters
			disabledAlphas = appStore.retrieveDisabledAlphabets();
			logger.info("Disabled alphabets are: "+disabledAlphas.toString());
			countDisabled = disabledAlphas.size();
			logger.info("Count of disabled alphabets present: "+countDisabled);
		} catch (Exception e) {
			Assert.fail(ErrorConstants.RETRIEVE_DISABLED_ALPHAS_ERR);
		}
		if (countDisabled <= 0) {
//			if there is no disabled alphabet, then fail the test showing appropriate message
			Assert.fail(ErrorConstants.DISABLED_ALPHAS_ABSENT_ERR);
		}
	}

	@Then("disabled alphabets should not be clickable")
	public void disabled_alphabets_should_not_be_clickable() {
		try {
			disabledAlphas.forEach(alpha -> {
				// As long as alphabet is disabled to click event, the test passes
				Assert.assertFalse(appStore.clickOnAlphabet(alpha));
			});
		} catch (Exception e) {
			Assert.fail(ErrorConstants.DISABLED_ALPHAS_CLICK_ERR);
		}
	}

}
