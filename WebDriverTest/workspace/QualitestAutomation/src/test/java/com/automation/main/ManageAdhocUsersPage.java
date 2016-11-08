package com.automation.main;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;import com.automation.main.page_helpers.Page;import com.automation.main.page_helpers.Page;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;

@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class ManageAdhocUsersPage extends Page {
	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}

	public ManageAdhocUsersPage(WebDriver browser) {
		super(browser);
		// TODO Auto-generated constructor stub
	}

	@FindBy(id = "ctl00_ContentPlaceHolder1_lbNewUser")
	public WebElement new_user_button;
	@FindBy(linkText = "Admin Dashboard")
	WebElement to_admin_dashboard;
	@FindBy(id = "ctl00_ContentPlaceHolder1_txtSearch") WebElement filter_search_user_input;
	@FindBy(id = "ctl00_ContentPlaceHolder1_btnSearch") WebElement filter_search_button;

	public void clickOnNewUser() throws InterruptedException {
		for (int i = 0; i < 10; i++) {
			try {
				new_user_button.click();
				System.out.println("Clicked on new user button.");
				break;
			} catch (Exception msg) {
				System.out.println("Not clicked on new user button.");
				Thread.sleep(Page.TIMEOUT_TINY);
			}
		}

	}

	// back to admin dash board
	public void toAdminDashBoard() throws InterruptedException {
		to_admin_dashboard.click();
		Thread.sleep(Page.TIMEOUT_TINY);
	}

	/// create new user
	public void createNewUser(String name, CreateNewUserWindow create_new_user_window) throws InterruptedException {

		// 3. Click on create course href link
		driver.switchTo().frame(0);
		Thread.sleep(Page.TIMEOUT_TINY);
		clickOnNewUser();
		Thread.sleep(Page.TIMEOUT_TINY);

		create_new_user_window.createNewUser(name, name, "abc@com.com", "111", "111");
		Thread.sleep(Page.TIMEOUT_TINY);

		try {

			driver.switchTo().alert().accept();
		} catch (Exception msg) {

		}
	}
	
	// This function get username and search for it
	public void searchForTargetUser(String username_to_search) throws InterruptedException {
		getIntoFrame(0);
		waitForVisibility(filter_search_user_input);
		sendKeysToWebElementInput(filter_search_user_input, username_to_search);
		
		try{
			filter_search_button.click();
			System.out.println("Searched for target username: " + username_to_search);
			ATUReports.add("Searched for target username: " + username_to_search, "True.", "True.", LogAs.PASSED, null);
			Assert.assertTrue(true);
			exitInnerFrame();
		} catch(Exception msg) {
			System.out.println("Fail to search for target username: " + username_to_search);
			ATUReports.add("Searched for target username: " + username_to_search, "True.", "False.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		
	}

}