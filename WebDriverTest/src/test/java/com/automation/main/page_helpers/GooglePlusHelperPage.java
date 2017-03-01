package com.automation.main.page_helpers;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;

public class GooglePlusHelperPage extends Page {

	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}

	public GooglePlusHelperPage(WebDriver browser) {
		super(browser);
		// TODO Auto-generated constructor stub
	}
	
	@FindBy(id="Email")
	public WebElement google_email;
	@FindBy(id="next")
	public WebElement google_next;
	@FindBy(id="Passwd")
	public WebElement google_pass;
	@FindBy(id="signIn")
	public WebElement google_login_button;
	@FindBy(css=".RveJvd.snByac")
	public List<WebElement> google_post_button;
	@FindBy(css=".Ihwked.UB0dDd.GcESAf.XkfHGe.hE2QI")
	public List<WebElement> google_cards_warpper;
	@FindBy(css=".Gu1oT")
	public List<WebElement> tegrity_label;
	@FindBy(css=".f6umD")
	public List<WebElement> tegrity_url;
	public String mail = "qategrity@gmail.com";
	public String password = "Teg123qa";
	
	
	public void verifyRecordingAndUniversityNameAreDisplay(String pageUrl) {
		
		String url = pageUrl.substring(8);
		if(tegrity_label.get(2).getText().equals("Tegrity")) {
			System.out.println("The label name is: Tegrity and is display on the status.");
			ATUReports.add(time+ " The label name is: Tegrity and is display on the status.", LogAs.PASSED, null);
		} else {
			System.out.println("The label name is: Tegrity and is not display on the status.");
			ATUReports.add(time + " The label name is: Tegrity and is not display on the status.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
		
		if(tegrity_url.get(2).getText().equals(url)) {
			System.out.println("The url name is: " + url + " and is display on the status.");
			ATUReports.add(time+ " The url name is: " + url + "is display on the status.", LogAs.PASSED, null);
		} else {
			System.out.println("The url name is: " + url + "and is not display on the status.");
			ATUReports.add(time + " The url name is: " + url + "and is not display on the status.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
	}
		
}
