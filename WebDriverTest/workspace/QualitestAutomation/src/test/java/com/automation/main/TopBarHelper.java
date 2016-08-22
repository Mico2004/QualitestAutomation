package com.automation.main;

import java.security.PublicKey;
import java.util.List;

import org.omg.Messaging.SyncScopeHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Listeners;

import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import junitx.util.PrivateAccessor;

@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TopBarHelper extends Page {
	public TopBarHelper(WebDriver browser) {
		super(browser);
		// TODO Auto-generated constructor stub
	}
	
	@FindBy(id = "SignOutLink")WebElement sign_out_button;
	@FindBy(id = "UserName") WebElement username;
	@FindBy(id = "tegritySearchBox") WebElement search_box_field;
	@FindBy(id = "InstituteLogotype") WebElement institute_logo;
	@FindBy(css = ".university-logo-container.ng-scope.ng-isolate-scope") WebElement header;

	// This function clicks on sign out
	public void clickOnSignOut() {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(sign_out));
			
			sign_out.click();
			System.out.println("Clicked on Sign Out.");
			ATUReports.add("Sign Out.", "Clicked.", "Clicked.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} catch(Exception msg) {
			System.out.println("Not clicked on Sign Out.");
			ATUReports.add("Sign Out.", "Clicked.", "Not clicked.", LogAs.FAILED, null);
			Assert.assertFalse(false);
		}
	}
	
	// This function return username of logged user
	public String getUsernameOfLoggedUser() {
		try {
			String username_of_logged_user = username.getText();
			return username_of_logged_user;
		} catch (Exception msg) {
			return null;
		}
	}
	
	//  This function verify the search field is display at the top right of the UI page below the top navigation bar.
	public void verifySearchFieldDisplayedAtTopRight() {
		WebElement tegrity_logo_button = driver.findElement(By.id("TegrityLogo"));
		if((institute_logo.getLocation().x < search_box_field.getLocation().x) &&
				(username.getLocation().y < search_box_field.getLocation().y) &&
				(search_box_field.getLocation().y < tegrity_logo_button.getLocation().y)) {
			System.out.println("Verfied search field is displayed at top right of the page.");
			ATUReports.add("Verfied search field is displayed at top right of the page.", "True.", "True.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Not verfied that search field is displayed at top right of the page.");
			ATUReports.add("Verfied search field is displayed at top right of the page.", "True.", "False.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
	}
	
	// Verify that target text appear in search field
	public void verifyTargetTextInSearchField(String target_text) {
		if(search_box_field.getAttribute("placeholder").equals(target_text)) {
			System.out.println("Verfied that target text appears in search field: " + target_text);
			ATUReports.add("Verfied that target text appears in search field.", target_text, target_text, LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("No verfied that target text appears in search field: " + target_text);
			ATUReports.add("Verfied that target text appears in search field.", target_text, search_box_field.getAttribute("placeholder"), LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
	}
	
	// Search for target text in search field
	public void searchForTargetText(String target_text) {
		try {
			search_box_field.sendKeys(target_text + Keys.ENTER);
			System.out.println("Search for target text: " + target_text);
			ATUReports.add("Searched for target text.", target_text, target_text, LogAs.PASSED, null);
			Assert.assertTrue(true);
		} catch(Exception msg) { 
			System.out.println("Fail to search for target text: " + target_text);
			ATUReports.add("Searched for target text.", "", "Fail to search.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
	}
	
	// Verify that the logo at the top left corner of the UI
	public void verfiyThatTheLogoAtTheTopLeft() {
		WebElement tegrity_logo = driver.findElement(By.id("TegrityLogo"));
		if((sign_out_button.getLocation().y < institute_logo.getLocation().y) &&
				(institute_logo.getLocation().x < search_box_field.getLocation().x) &&
				(search_box_field.getLocation().y < tegrity_logo.getLocation().y)) {
			System.out.println("Verfied that the logo at the top left corner.");
			ATUReports.add("Verfied that the logo at the top left corner.", "True.", "True.", LogAs.PASSED, null);
		} else {
			System.out.println("Not verfied that the logo at the top left corner.");
			ATUReports.add("Verfied that the logo at the top left corner.", "True.", "False.", LogAs.FAILED, null);
		}
				
		
	}
}
