package com.automation.main.page_helpers;

import org.openqa.selenium.JavascriptExecutor;
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
	@FindBy(id = "email-field")
	public WebElement from_email_field;
	@FindBy(id = "name-field")
	public WebElement from_name_field;
	@FindBy(id = "email-field2") 
	public WebElement to_email_field;
	@FindBy(id = "name-field2") 
	public WebElement to_name_field;
	@FindBy(id = "subject-field")
	public WebElement subject_field;
	@FindBy(id = "textarea-field")
	public WebElement comments_field;
	@FindBy(xpath = "//*[@id=\"supportWindow\"]/div[2]/form/span[1]")
	public WebElement support_window_info;
	@FindBy(xpath = ".//*[@id='supportWindow']/div[2]/form/div[5]/button[2]")
	public WebElement cancel_button;
	@FindBy(xpath=".//*[@id='supportWindow']/div[2]/form/div[5]/button[1]" )
	public WebElement send_button;
	
	

	///verify support window is not displayed
	public void verifyNoSupportWindow()
	{
		if(support_window_title.isDisplayed())
		{
			ATUReports.add(time +" support window is visible", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			System.out.println("support window is visible");
			Assert.assertTrue(false);
		}
		else {
			
			ATUReports.add(time +" support window is not visible", LogAs.PASSED, null);
			System.out.println("support window is not visible");
			Assert.assertTrue(true);
		}
	}
	
	///verify support window is displayed
	public void verifySupportWindow()
	{
		waitForVisibility(support_window_title);
		if(support_window_title.isDisplayed())
		{
			ATUReports.add(time +" support window is visible", LogAs.PASSED, null);
			System.out.println("support window is visible");
			Assert.assertTrue(true);
		}
		else {
			
			ATUReports.add(time +" support window is not visible", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
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
	    	ATUReports.add(time +" email sent", LogAs.PASSED, null);
			System.out.println("email sent");
			Assert.assertTrue(true);
	}
		catch(Exception e)
	    {
			System.out.println("failed clicking send");
			ATUReports.add(time +" email  failed", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			System.out.println("email  failed");
			Assert.assertTrue(false);
	    }
	}

	public void verifyThatTheEmailAdressFieldHelpDeskOrPlaceHolder() {
		
		try {
			String id = to_email_field.getAttribute("id");
			String mail = (String)((JavascriptExecutor) driver).executeScript("return document.getElementById(\""+id+"\").value;");
			if(mail.equals("helpdesk@mheducation.com")) {
				System.out.println("Verify that the email address is: " + mail);
				ATUReports.add(time + " Verify that the email address is: " + mail,"Success.","Success.",LogAs.PASSED,null);		
			} else{
				verifyPlaceHolderIsDisplay(to_email_field, "E-mail address");
			}
		}catch(Exception e) {
			e.printStackTrace();
			ATUReports.add(time +e.getMessage(),"Success.","Failed.",LogAs.FAILED,new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
	    }
	}
	
public void verifyThatTheSupportEmailHasDefultValue() {
		
		try {
			String id = to_name_field.getAttribute("id");
			String supportEmail = (String)((JavascriptExecutor) driver).executeScript("return document.getElementById(\""+id+"\").value;");
			if(supportEmail.equals("Institution Help Desk")) {
				System.out.println("Verify that the support email has defult value of institution Help Desk.");
				ATUReports.add(time + " Verify that the support email has defult value of institution Help Desk.","Success.","Success.",LogAs.PASSED,null);		
			} else{
				System.out.println("not Verify that the support email has defult value of institution Help Desk.");
				ATUReports.add(time + " Verify that the support email has defult value of institution Help Desk.","Success.","Failed.",LogAs.FAILED,new CaptureScreen(ScreenshotOf.BROWSER_PAGE));		
			}
		}catch(Exception e) {
			e.printStackTrace();
			ATUReports.add(time +e.getMessage(),"Success.","Failed.",LogAs.FAILED,new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
	    }
	}
	
}
