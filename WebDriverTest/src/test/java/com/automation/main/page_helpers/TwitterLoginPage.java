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

public class TwitterLoginPage extends Page {

	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}

	public TwitterLoginPage(WebDriver browser) {
		super(browser);
		// TODO Auto-generated constructor stub
	}
	
	@FindBy(id="username_or_email")
	public WebElement twitter_email;
	@FindBy(id="password")
	public WebElement twitter_pass;
	@FindBy(css=".button.selected.submit")
	public WebElement twitter_login_button;
	@FindBy(xpath=".//*[@id='update-form']/div[3]/fieldset[2]/input[2]")
	public WebElement twitter_post_button;
	@FindBy(css=".TweetTextSize.js-tweet-text.tweet-text")
	public List<WebElement> twitter_cards_warpper;
	@FindBy(css="#status")
	public WebElement status_of_uploading;
	@FindBy(css=".twitter-timeline-link")
	public List<WebElement> twitter_links;
	
	public String mail = "techinstructor1";
	public String password = "dushi19920";
	
	public void verifyRecordingAndUniversityNameAreDisplay(String Status,String recording_name ,LoginHelperPage tegrity ) {
		
		String first_status = twitter_cards_warpper.get(0).getText();
		if(first_status.contains(recording_name)) {
			System.out.println("The recording name is: " + recording_name + " and is display on the status.");
			ATUReports.add(time+ " The recording name is: " + recording_name + "is display on the status.", LogAs.PASSED, null);
		} else {
			System.out.println("The recording name is: " + recording_name + "and is not display on the status.");
			ATUReports.add(time + " The recording name is: " + recording_name + "and is not display on the status.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
		String url = twitter_links.get(0).getAttribute("title");
		String[] url_to_compare = Status.split(" ");
		if(url_to_compare[4].equals(url)){
			System.out.println("The url name is: " + url + " and is display on the status.");
			ATUReports.add(time+ " The url name is: " + url + "is display on the status.", LogAs.PASSED, null);
		} else {
			System.out.println("The url name is: " + url + "and is not display on the status.");
			ATUReports.add(time + " The url name is: " + url + "and is not display on the status.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
	 
	}
	
	public String getStatus() {
		waitForVisibility(status_of_uploading);
		return status_of_uploading.getText();
	}
		
}
