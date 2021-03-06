package com.automation.main.page_helpers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;

public class HelpPage extends Page {

	public HelpPage(WebDriver browser) {
		super(browser);
		// TODO Auto-generated constructor stub
	}

	@FindBy(linkText = "Home")
	WebElement home_button;

	/// verify help page
	public void verifyHelpPage() {
		if (home_button.isDisplayed()) {
			verifyHelpPageUrl(driver);
			System.out.println("help page verified");
			ATUReports.add(time +" help page verified", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("help page not verified");
			ATUReports.add(time +" help page not verified", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}

	/// verify help page url
	public void verifyHelpPageUrl(WebDriver driver) {
		String url = driver.getCurrentUrl();
		if (url.contains("help.tegrity.com/knowledgebase_category/administrators")) {
			System.out.println("help page url verified");
			ATUReports.add(time +" help page url verified", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("help page url not verified");
			ATUReports.add(time +" help page url not verified", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}
	
	
	public void verifyHelpPageUrlGuest(WebDriver driver) {
		String url = driver.getCurrentUrl();
		if (url.contains("http://help.tegrity.com/knowledgebase_category/students")) {
			System.out.println("help page url verified");
			ATUReports.add(time +" help page url verified", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("help page url not verified");
			ATUReports.add(time +" help page url not verified", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}
}
