package com.automation.main.page_helpers;

import java.util.List;

import org.omg.Messaging.SyncScopeHelper;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
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
public class PublishWindow extends Page {
	public PublishWindow(WebDriver browser) {
		super(browser);
		// TODO Auto-generated constructor stub
	}
	
	@FindBy(id = "ModalDialogHeader")WebElement publish_window_title;
	@FindBy(id = "NeverOption") WebElement never_option;
	@FindBy(id = "SubmitButton") WebElement save_button;
	@FindBy(id = "AlwaysOption")
	WebElement always_select_button;
	@FindBy(id = "DateRangeOption")
	WebElement date_range_select_button;
	@FindBy(id = "NeverOption")
	WebElement never_select_button;
	@FindBy(id = "CancelButton")
	WebElement cancel_button;
	

	// This function return true if publish window is closed,
	// and false if it is open
	public boolean isPublishWindowClosed() {
		try {
			if(publish_window_title.getText().equals("Publish")) {
				return false;
			} else {
				return true;
			}
			
		} catch (org.openqa.selenium.NoSuchElementException msg) {
			return true;
		}
	}
	
	
	
	// This function verify that publish window is open
	public void verifyPublishWindowOpen() {
		boolean is_closed = isPublishWindowClosed();
		
		if(!is_closed) {
			System.out.println("Publish window is open.");
			ATUReports.add("Publish window.", "Open.", "Open.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Publish window is close.");
			ATUReports.add("Publish window.", "Open.", "Close.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}
	
	
	// This function verify that publish window is open
	public void verifyPublishWindowClosed() {
		boolean is_closed = isPublishWindowClosed();
		
		if(is_closed) {
			System.out.println("Publish window is close.");
			ATUReports.add("Publish window.", "Close.", "Close.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Publish window is open.");
			ATUReports.add("Publish window.", "Close.", "Open.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}
	
	// This function verify that never option is: 0 - not selected, 1 - selected
	public void verifyThatNeverOptionSelectedOrNotSelected(int selected) {
		boolean is_selected = never_option.isSelected();
		
		if(selected == 0) {
			if(is_selected) {
				System.out.println("Never option is selected.");
				ATUReports.add("Never option.", "Not selected.", "Selected.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Assert.assertTrue(false);
			} else {
				System.out.println("Never option is not selected.");
				ATUReports.add("Never option.", "Not selected.", "Not selected.", LogAs.PASSED, null);
				Assert.assertTrue(true);
			}
		} else {
			if(is_selected) {
				System.out.println("Never option is selected.");
				ATUReports.add("Never option.", "Selected.", "Selected.", LogAs.PASSED, null);
				Assert.assertTrue(true);
			} else {
				System.out.println("Never option is not selected.");
				ATUReports.add("Never option.", "Selected.", "Not selected.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Assert.assertTrue(false);
			}
		}
	}
	
	// This function clicks on save button
	public void clickOnSaveButton()
	{
		try {
			while(isPublishWindowClosed() == false) {
				save_button.click();
				Thread.sleep(3000);
			}
			
			System.out.println("Clicked on save button.");
			ATUReports.add("Clicked on save button.", "True.", "True.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} catch(Exception msg) {
			System.out.println("Not clicked on save button.");
			ATUReports.add("Clicked on save button.", "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}

}
