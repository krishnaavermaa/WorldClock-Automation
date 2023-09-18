package com.worldclock.steps.appstorefeature;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.support.PageFactory;

import com.worldclock.config.BaseTestConfig;
import com.worldclock.pageobjects.AppStore;
import com.worldclock.utility.constants.ErrorConstants;

import io.cucumber.java.en.Then;

public class VerifyA2ZalphabetsDisplayedSteps extends BaseTestConfig {

	private AppStore appStore;
	private static final Logger logger = LogManager.getLogger(VerifyA2ZalphabetsDisplayedSteps.class);

	public VerifyA2ZalphabetsDisplayedSteps() {
		appStore = PageFactory.initElements(driver, AppStore.class);
	}

	@Then("show A to Z alphabets")
	public void show_a_to_z_alphabets() {
		List<Character> alphabetsList = null;
		Set<Character> alphaSet;

//		retrieving alphabets List Displayed
		try {
			alphabetsList = appStore.getAlphabetsDisplayed();
			logger.info("Displayed alphabets are: "+alphabetsList.toString());
		}
		catch(Exception e) {
			Assert.fail(ErrorConstants.RETRIEVE_ALPHAS_ERR);
		}

//		storing the alphabets list as Set of characters
		alphaSet = alphabetsList.stream().collect(Collectors.toSet());
		logger.info("Retrieved characters count: "+alphaSet.size());

//		If set is of size 26 then logically all alphabets are hence displayed
		Assert.assertEquals(26, alphaSet.size());
	}

}
