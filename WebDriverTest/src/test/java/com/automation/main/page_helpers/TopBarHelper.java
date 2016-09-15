package com.automation.main.page_helpers;

import java.security.PublicKey;
import java.util.List;

import org.apache.regexp.recompile;
import org.omg.Messaging.SyncScopeHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Listeners;

import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;
import junitx.util.PrivateAccessor;

@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TopBarHelper extends Page {
	public TopBarHelper(WebDriver browser) {
		super(browser);
		// TODO Auto-generated constructor stub
	}

	@FindBy(id = "SignOutLink")
	public WebElement sign_out_button;
	@FindBy(id = "UserName")
	public WebElement username;
	@FindBy(id = "tegritySearchBox")
	public WebElement search_box_field;
	@FindBy(id = "InstituteLogotype")
	public WebElement institute_logo;
	@FindBy(css = ".university-logo-container.ng-scope.ng-isolate-scope")
	public WebElement header;
	@FindBy(id = "MyAccountLink")
	public WebElement my_account_link;
	@FindBy(id = "ReportsLink")
	public WebElement report_menu;
	@FindBy(id = "WeeklySummary")
	public WebElement weekly_summary;
	@FindBy(id = "DetailedReports")
	public WebElement detailed_reports;
	@FindBy(id = "CustomAnalysis")
	public WebElement custom_analysis;

	// This function clicks on my account link
	public void clickOnMyAccountLink() {
		clickElement(my_account_link);
	}

	// This function clicks on sign out
	public void clickOnSignOut() {
			
			System.out.println("signOut1");
			Actions builder = new Actions(driver); // new line
			builder.sendKeys(Keys.PAGE_UP); // new line
			builder.moveToElement(sign_out).build().perform();
			waitForVisibility(sign_out);
			System.out.println("signOut2");
			sign_out.click();
			System.out.println("signOut3");
			// if(driver instanceof InternetExplorerDriver) {
			// WebElement iw =
			// driver.findElements(By.cssSelector(".ng-scope>.ng-scope.ng-binding")).get(1);
			// iw.sendKeys(Keys.ENTER);
			// }

			for (int second = 0;second<60; second++) {
				try {
				if (second >= 60) {
					System.out.println("LogOut from user not succeeded.");
					ATUReports.add(" Login page timeout", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
					Assert.assertTrue(false);
				}
			
					if (driver.getTitle().equals("Tegrity Lecture Capture"))// check// if// element// is// present
					{
						System.out.println("LogOut from user succeeded.");
						ATUReports.add(" Login page correctly displaied", LogAs.PASSED, null);
						Assert.assertTrue(true);
						break;
					} else {
						Thread.sleep(3000);
						System.out.println("Sign_out.Click");
						sign_out.click();
					}
				} catch (Exception e) {
					
				}

				
			}
			System.out.println("signOut5");	
			
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

	// This function verify the search field is display at the top right of the
	// UI page below the top navigation bar.
	public void verifySearchFieldDisplayedAtTopRight() {
		WebElement tegrity_logo_button = driver.findElement(By.id("TegrityLogo"));
		if ((institute_logo.getLocation().x < search_box_field.getLocation().x)
				&& (username.getLocation().y < search_box_field.getLocation().y)
				&& (search_box_field.getLocation().y < tegrity_logo_button.getLocation().y)) {
			System.out.println("Verfied search field is displayed at top right of the page.");
			ATUReports.add("Verfied search field is displayed at top right of the page.", "True.", "True.",
					LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Not verfied that search field is displayed at top right of the page.");
			ATUReports.add("Verfied search field is displayed at top right of the page.", "True.", "False.",
					LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}

	// Verify that target text appear in search field
	public void verifyTargetTextInSearchField(String target_text) {
		if (search_box_field.getAttribute("placeholder").equals(target_text)) {
			System.out.println("Verfied that target text appears in search field: " + target_text);
			ATUReports.add("Verfied that target text appears in search field.", target_text, target_text, LogAs.PASSED,
					null);
			Assert.assertTrue(true);
		} else {
			System.out.println("No verfied that target text appears in search field: " + target_text);
			ATUReports.add("Verfied that target text appears in search field.", target_text,
					search_box_field.getAttribute("placeholder"), LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
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
		} catch (Exception msg) {
			System.out.println("Fail to search for target text: " + target_text);
			ATUReports.add("Searched for target text.", "", "Fail to search.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}

	// Verify that the logo at the top left corner of the UI
	public void verfiyThatTheLogoAtTheTopLeft() {
		WebElement tegrity_logo = driver.findElement(By.id("TegrityLogo"));
		if ((sign_out_button.getLocation().y < institute_logo.getLocation().y)
				&& (institute_logo.getLocation().x < search_box_field.getLocation().x)
				&& (search_box_field.getLocation().y < tegrity_logo.getLocation().y)) {
			System.out.println("Verfied that the logo at the top left corner.");
			ATUReports.add("Verfied that the logo at the top left corner.", "True.", "True.", LogAs.PASSED, null);
		} else {
			System.out.println("Not verfied that the logo at the top left corner.");
			ATUReports.add("Verfied that the logo at the top left corner.", "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}

	}

	// This function click on Content Task then on copy in the sub menu
	public void clickOnReportThenWeekly() throws InterruptedException {

		waitForVisibility(report_menu);
		moveToElementAndClick(report_menu, driver);
		report_menu.sendKeys(Keys.TAB);

		for (int i = 0; i < 6; i++) {
			report_menu.sendKeys(Keys.TAB);// solution
			try {
				weekly_summary.click();

				ATUReports.add("Clicked on weekly summary.", LogAs.PASSED, null);
				System.out.println("Clicked on weekly summary.");
				Assert.assertTrue(true);
				return;

			} catch (Exception e) {
			}
			try {
				if (driver.getCurrentUrl().contains("weeklySummary"))// check if
																		// list
																		// of
																		// courses
																		// are
																		// present
				{
					ATUReports.add("copy menu verified ", LogAs.PASSED, null);
					Assert.assertTrue(true);
					break;

				}
			} catch (Exception e) {
			}

			Thread.sleep(1000);
			try {

				weekly_summary.click();
				ATUReports.add("click succeeded", LogAs.PASSED, null);
			} catch (Exception e) {
				ATUReports.add("click failed ", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			}
		}
	}

	// This function click on Report then on CustomAnalysis in the sub menu
	public void clickOnReportThenDetailedReports() throws InterruptedException {

		waitForVisibility(report_menu);
		moveToElementAndClick(report_menu, driver);
		report_menu.sendKeys(Keys.TAB);

		for (int i = 0; i < 6; i++) {
			report_menu.sendKeys(Keys.TAB);// solution
			try {
				detailed_reports.click();

				ATUReports.add("Clicked on detailed_reports.", LogAs.PASSED, null);
				System.out.println("Clicked on detailed reports.");
				Assert.assertTrue(true);
				return;

			} catch (Exception e) {
			}
			try {
				if (driver.getCurrentUrl().contains("detailedReports"))// check
																		// if
																		// list
																		// of
																		// courses
																		// are
																		// present
				{
					ATUReports.add("detailed_reports verified ", LogAs.PASSED, null);
					Assert.assertTrue(true);
					break;

				}
			} catch (Exception e) {
			}

			Thread.sleep(1000);
			try {

				detailed_reports.click();
				ATUReports.add("click succeeded", LogAs.PASSED, null);
			} catch (Exception e) {
				ATUReports.add("click failed ", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			}
		}
	}

	// This function click on Report then on DetailedReports in the sub menu
	public void clickOnReportThenCustomAnalysis() throws InterruptedException {

		waitForVisibility(report_menu);
		moveToElementAndClick(report_menu, driver);
		report_menu.sendKeys(Keys.TAB);

		for (int i = 0; i < 6; i++) {
			report_menu.sendKeys(Keys.TAB);// solution
			try {
				custom_analysis.click();

				ATUReports.add("Clicked on custom analysis.", LogAs.PASSED, null);
				System.out.println("Clicked on custom analysis.");
				Assert.assertTrue(true);
				return;

			} catch (Exception e) {
			}
			try {
				if (driver.getCurrentUrl().contains("customAnalysis"))// check
																		// if
																		// list
																		// of
																		// courses
																		// are
																		// present
				{
					ATUReports.add("custom analysis verified ", LogAs.PASSED, null);
					Assert.assertTrue(true);
					break;

				}
			} catch (Exception e) {
			}

			Thread.sleep(1000);
			try {

				custom_analysis.click();
				ATUReports.add("click succeeded", LogAs.PASSED, null);
			} catch (Exception e) {
				ATUReports.add("click failed ", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			}
		}
	}
}
