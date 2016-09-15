package com.automation.main.page_helpers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;

public class GetSupprtWindow extends Page {

	public GetSupprtWindow(WebDriver browser) {
		super(browser);
		// TODO Auto-generated constructor stub
	}
	@FindBy(id = "supportWindow") WebElement support_window_title;
	@FindBy(id = "email-field") WebElement from_email_field;
	@FindBy(id = "name-field") WebElement from_name_field;
	@FindBy(id = "email-field2") WebElement to_email_field;
	@FindBy(id = "name-field2") WebElement to_name_field;
	@FindBy(id = "subject-field") WebElement subject_field;
	@FindBy(id = "textarea-field") WebElement comments_field;
	@FindBy(xpath = "//*[@id=\"supportWindow\"]/div[2]/form/span[1]") WebElement support_window_info;
	@FindBy(tagName = "Cancel") WebElement cancel_button;
	@FindBy(css=".btn.btn-primary.pull-right" ) WebElement send_button;
	
	

	///verify support window is not displayed
	public void verifyNoSupportWindow()
	{
		if(support_window_title.isDisplayed())
		{
			ATUReports.add("support window is visible", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			System.out.println("support window is visible");
			Assert.assertTrue(false);
		}
		else {
			
			ATUReports.add("support window is not visible", LogAs.PASSED, null);
			System.out.println("support window is not visible");
			Assert.assertTrue(true);
		}
	}
	
	///verify support window is displayed
	public void verifySupportWindow()
	{
		if(support_window_title.isDisplayed())
		{
			ATUReports.add("support window is visible", LogAs.PASSED, null);
			System.out.println("support window is visible");
			Assert.assertTrue(true);
		}
		else {
			
			ATUReports.add("support window is not visible", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			System.out.println("get started block is  not visible");
			Assert.assertTrue(false);
		}
	}
	///fill support window blank fields and send it to your email
	public void fillSupportWindowAndSend(String from_email,String from_name,String subject,String comments,ConfirmationMenu confirm ,WebDriver driver)
	{ try {
		waitForVisibility(support_window_title);
		from_email_field.clear();
		from_name_field.clear();
		subject_field.clear();
		comments_field.clear();
		from_email_field.sendKeys(from_email);
	    from_name_field.sendKeys(from_name);
	    subject_field.sendKeys(subject);
	    comments_field.sendKeys(comments);
	    for (String window : driver.getWindowHandles()) {
			driver.switchTo().window(window);
			break;
		}  
	   waitForVisibility(send_button);
	    send_button.click();
	        waitForVisibility(confirm.ok_button);
	    	confirm.clickOnOkButtonAfterConfirmEmailSentSuccessfully();
	    	ATUReports.add("email sent", LogAs.PASSED, null);
			System.out.println("email sent");
			Assert.assertTrue(true);
	}
		catch(Exception e)
	    {
			System.out.println("failed clicking send");
			ATUReports.add("email  failed", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			System.out.println("email  failed");
			Assert.assertTrue(false);
	    }
	}
}
