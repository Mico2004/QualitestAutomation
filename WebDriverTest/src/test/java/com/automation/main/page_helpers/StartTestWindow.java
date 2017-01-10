package com.automation.main.page_helpers;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
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
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;




@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class StartTestWindow extends Page {
	public StartTestWindow(WebDriver browser) {
		super(browser);
		// TODO Auto-generated constructor stub
	}
	
	@FindBy(id = "ModalDialogHeader")
	public WebElement start_test_window_title;
	@FindBy(id = "ModalDialogHeaderWrap")
	public WebElement start_test_window_background;
	@FindBy(id = "AcceptButton")
	public WebElement accept_button;
	@FindBy(id = "CancelButton")
	public WebElement cancel_button;  
	@FindBy(xpath = ".//*[@id='InfoText']/p")
	WebElement test_message;  
	
	
	// This function return true if publish window is closed, and false if it is open
			public void verifyThatTheTextInTheTextboxIsEqualsToTheTextFromAdmin(String message) {
				try {
					if(test_message.getText().equals(message)) {
						System.out.println("The text " + message +" from the admin is equals to the text box in the start test window: " +test_message.getText());
						ATUReports.add(time +"The text " + message +" from the admin is equals to the text box in the start test window: " +test_message.getText(), "Success.", "Success.", LogAs.PASSED, null);		
					} else {
						System.out.println("The text " + message +" from the admin is not equals to the text box in the start test window: " +test_message.getText());
						ATUReports.add(time +"The text" + message +" from the admin is not equals to the text box in the start test window: " +test_message.getText(), "Success.", "Success.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
					}
					
				} catch (Exception msg) {
					msg.getMessage();
					ATUReports.add(msg.getMessage(), "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				}
			}
	
	// This function return true if publish window is closed,
		// and false if it is open
		public boolean isStartATestWindowClosed() {
			try {
				if(start_test_window_title.getText().equals("Start a Test")) {
					return false;
				} else {
					return true;
				}
				
			} catch (org.openqa.selenium.NoSuchElementException msg) {
				return true;
			}
		}
	
	public void clickOnCancelButton() {
		try {
			wait.until(ExpectedConditions.visibilityOf(cancel_button));	
			
			while(isStartATestWindowClosed() == false) {
				((JavascriptExecutor) driver).executeScript("document.getElementById(\""+cancel_button.getAttribute("id")+"\").click();");
				Thread.sleep(1000);
			}
			
			System.out.println("Clicked on cancel button.");
			ATUReports.add(time +" Clicked on cancel button.", "Success.", "Success.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} catch (Exception msg) {
			System.out.println("Fail to click on cancel button.");
			ATUReports.add(time +" Clicked on cancel button.", "Success.", "Fail."+msg.getMessage(), LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}
	
	// This function clicks on save button
	public void clickOnAcceptButton()
	{
		try {
			while(isStartATestWindowClosed() == false) {
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", accept_button);	
				Thread.sleep(2000);
			}
			
			System.out.println("Clicked on accept button.");
			ATUReports.add(time +" Clicked on accept button.", "True.", "True.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} catch(Exception msg) {
			System.out.println("Not clicked on accept button.");
			ATUReports.add(time +" Clicked on accept button.", "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}
	
	// verify start a test properties background color is same as recording background color
	public void verifyBackgroundColor(RecordingHelperPage rec) throws InterruptedException {
		
		try{
		Thread.sleep(1000);	
		String background_rec = rec.getBackGroundColor(rec.background);
		String menu_background = getBackGroundColor(start_test_window_background);
		if (background_rec.equals(menu_background)) {
			ATUReports.add(time +" start a test menu background color is same as recording background color","Success.", "Success.", LogAs.PASSED, null);
			System.out.println("start a test menu background color is same as recording background color");
			Assert.assertTrue(true);
		} else {
			ATUReports.add(time +" start a test menu background color is same as recording background color","Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			System.out.println("start a test menu background color is same as recording background color");
			Assert.assertTrue(false);
		}
		}catch(Exception e){
			e.getMessage();
			ATUReports.add(e.getMessage(), "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));

			}
	}
	
	// This function verify that start a test window is open
	public void verifyStartATestWindowOpen() throws InterruptedException {
		
		Thread.sleep(500);
		boolean is_closed = isStartATestWindowClosed();
		if(!is_closed) {
			System.out.println("Start a test window is open.");
			ATUReports.add(time +" Start a test window.", "Open.", "Open.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Start a test window is close.");
			ATUReports.add(time +" Start a test window.", "Open.", "Close.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}
	
	/**
 * this function verify Start a Test Title
	 */
public void verifyStartTestTitle() throws InterruptedException {
		try{
		waitForVisibility(start_test_window_title);
		String val = start_test_window_title.getText();
		if (val.equals("Start a Test")) {
			System.out.println("Start a Test menu title verified.");
			ATUReports.add(time +" Start a Test menu title verified.", LogAs.PASSED, null);
		} else {
			System.out.println("Start a Test menu title not verified.");
			ATUReports.add(time +" Start a Test title not verified.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
		Assert.assertEquals("Start a Test", val);
		
		}catch(Exception e){
			e.getMessage();
			ATUReports.add(e.getMessage(), "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));

			}
	}
	

public void VerifyTheLocationOfTheSaveAndCancel() {
	
	try{
		
	Point cancel=cancel_button.getLocation();
	Point save= accept_button.getLocation();
	Point title = start_test_window_title.getLocation();
	
	if((save.getY() > title.getY()) && (save.getX() > cancel.getX()))
	{
	 System.out.println("The Save button is displayed on the bottom right corner of the model window.");
	 ATUReports.add(time +" The Save button is displayed on the bottom right corner of the model window.","Success.", "Success.", LogAs.PASSED, null);
	}else {
	 System.out.println("The Save button isn't displayed on the bottom right corner of the model window.");
	 ATUReports.add(time +" The Save button isn't displayed on the bottom right corner of the model window.", "Success.", "Fail.", LogAs.FAILED,new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
	}
	if((cancel.getY() > title.getY()) && (cancel.getX() < save.getX())) {
		 System.out.println("The Cancel button is displayed on the left of the Save button.");
		 ATUReports.add(time +" The Cancel button is displayed on the left of the Save button.","Success.", "Success.", LogAs.PASSED, null);
		}else {
		 System.out.println("The Cancel button is not displayed on the left of the Save button.");
		 ATUReports.add(time +" The Cancel button is not displayed on the left of the Save button.", "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
	}catch(Exception e){
		e.getMessage();
		ATUReports.add(e.getMessage(), "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));

		}
	}
	
}