
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

public class TumblrLoginHelper extends Page {

	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");
	}

	public TumblrLoginHelper(WebDriver browser) {
		super(browser);
		// TODO Auto-generated constructor stub
	}
	
	@FindBy(id="signup_determine_email")
	public WebElement tumblr_email;
	@FindBy(id="signup_forms_submit")
	public WebElement tumblr_next;
	@FindBy(id="login-signin")
	public WebElement tumblr_next2;
	@FindBy(id="login-passwd")
	public WebElement tumblr_pass;
	@FindBy(id="login-signin")
	public WebElement tumblr_login_button;
	@FindBy(id="u_0_k")
	public WebElement facebook_post_button;
	@FindBy(css=".userContentWrapper._5pcr")
	public List<WebElement> facebook_cards_warpper;
	@FindBy(css="._52c6")
	public List<WebElement> facebook_cards_links;
	public String mail = "mickael.elimelech@qualitest.co.il";
	public String password = "Teg123qa";
	
	
	public void verifyRecordingAndUniversityNameAreDisplay(String recording_name ,LoginHelperPage tegrity ) {
		String url = tegrity.pageUrl.substring(8);
		try{
			new WebDriverWait(driver, 3).until(ExpectedConditions.textToBePresentInElement(facebook_cards_warpper.get(0), recording_name));	
			new WebDriverWait(driver, 3).until(ExpectedConditions.textToBePresentInElement(facebook_cards_warpper.get(0),url.toUpperCase()));
			
			System.out.println("The University Name: " + tegrity.pageUrl + " is display and the recording name is: " + recording_name + " display also.");
			ATUReports.add(time +"The University Name: " + tegrity.pageUrl + " is display and the recording name is: " + recording_name + " display also.", LogAs.PASSED, null);
		}
		catch(org.openqa.selenium.TimeoutException msg){		
			System.out.println("The University Name: " + tegrity.pageUrl + " is not display and the recording name is: " + recording_name + " display also.");
			ATUReports.add(time +"The University Name: " + tegrity.pageUrl + " is not display and the recording name is: " + recording_name + " display also.", LogAs.PASSED, null);
		}
	}
		
}
