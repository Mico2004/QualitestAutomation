package com.automation.main.page_helpers;

import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
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
	@FindBy(id="inboxfield")
	public WebElement mailinator_mail_edittext;	
	@FindBy(xpath="html/body/section[1]/div/div[3]/div[2]/div[2]/div[1]/span/button" )
	public WebElement mailinator_mail_go;	
	@FindBy(css=".innermail.ng-binding")
	public List<WebElement> mail_time_of_sending;
	@FindBy(xpath="html/body")
	public WebElement contant_of_mail ;
	
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
	
	// This function verify that copy menu open
	public void verifyThatGetSupportMenuOpen() {
				boolean is_closed = isGetSupportMenuClosed();
				
				if(!is_closed) {
					System.out.println("Get support menu is open.");
					ATUReports.add(time +" Get support menu.", "Open.", "Open.", LogAs.PASSED, null);
					Assert.assertTrue(true);
				} else {
					System.out.println("Get support menu is close.");
					ATUReports.add(time +" Get support menu.", "Open.", "Close.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
					Assert.assertTrue(false);
				}
			}
	
	// This function verify that copy menu close
	public void verifyThatGetSupportMenuClose() {
		boolean is_closed = isGetSupportMenuClosed();
		
		if(!is_closed) {
			System.out.println("Get support menu is open.");
			ATUReports.add(time +" Get support menu.", "Close.", "Open.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		} else {
			System.out.println("Copy Get support is close.");
			ATUReports.add(time +" Get support menu.", "Close.", "Close.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		}
	}
	
	// This function return true if copy menu is closed and false if it is open
	public boolean isGetSupportMenuClosed() {
			try {
				support_window_title.isDisplayed();
				return false;
			} catch (org.openqa.selenium.NoSuchElementException msg) {
				return true;
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

	public void verifyThatTheEmailAdressFieldHelpDeskOrPlaceHolder(String Email) {
		
		try {
			String id = to_email_field.getAttribute("id");
			String mail = (String)((JavascriptExecutor) driver).executeScript("return document.getElementById(\""+id+"\").value;");
			if(mail.equals(Email)) {
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

	
	public String getTheGgidFromTheUrl(String url) {
		
		String[] urlsplit = url.split("/");
	
 		return urlsplit[4];	
	}
	
	public String getTheUniverstyNameFromTheUrl(String url) {
		
		String[] urlsplit = url.split("/");
	
 		return urlsplit[1];	
	}

	public String getUserAgentByLogs() {
		 LogEntries logEntries = driver.manage().logs().get(LogType.BROWSER);
		 String user_agent = null;  
		 for (LogEntry entry : logEntries) {
	          //  System.out.println(new Date(entry.getTimestamp()) + " " + entry.getLevel() + " " + entry.getMessage());
	            if(entry.getMessage().contains("User-Agent")){
	            	user_agent = entry.getMessage();
	            	break;
	        }
	    }
	        return user_agent;
	}
	
}
