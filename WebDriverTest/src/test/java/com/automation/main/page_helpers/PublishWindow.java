package com.automation.main.page_helpers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
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
public class PublishWindow extends Page {
	public PublishWindow(WebDriver browser) {
		super(browser);
		// TODO Auto-generated constructor stub
	}
	             
	@FindBy(id = "ModalDialogHeader")WebElement
	publish_window_title;
	@FindBy(id = "NeverOption")
	public WebElement never_option;
	@FindBy(id = "SubmitButton")
	WebElement save_button;
	@FindBy(id = "AlwaysOption")
	WebElement always_select_button;
	@FindBy(id = "DateRangeOption")
	WebElement date_range_select_button;
	@FindBy(id = "NeverOption")
	WebElement never_select_button;
	@FindBy(id = "CancelButton")
	WebElement cancel_button;
	@FindBy(id = "ModalDialogHeaderWrap")
	WebElement publish_window_title_background;
	@FindBy(id = "InfoText")
	WebElement publish_window_text;
	@FindBy(xpath = ".//*[@id='InfoText2']/span")
	WebElement publish_window_text2;
	@FindBy(xpath = ".//*[@id='publishRecordingWindow']/form/div[1]/div[1]/div[1]/label")
	WebElement always_label;
	@FindBy(xpath = ".//*[@id='publishRecordingWindow']/form/div[1]/div[1]/div[2]/label")
	WebElement date_range_label;
	@FindBy(xpath = ".//*[@id='publishRecordingWindow']/form/div[1]/div[1]/div[3]/label")
	WebElement never_label;
	@FindBy(xpath = ".//*[@id='publishRecordingWindow']/form/div[1]/div[1]/div[2]/div/span")
	WebElement and_label;
	@FindBy(xpath = ".//*[@id='dropdown1']/input")
	public WebElement start_date;
	@FindBy(id = "dropdown1")
	public WebElement start;
	@FindBy(id = "dropdown2")
	public WebElement end;
	@FindBy(xpath = ".//*[@id='dropdown2']/input")
	public WebElement end_date;
	@FindBy(xpath =".//*[@id='publishRecordingWindow']/form/div[1]/div[1]/div[2]/div/div[1]/div[2]")
	public WebElement calenderStart;
	@FindBy(xpath =".//*[@id='publishRecordingWindow']/form/div[1]/div[1]/div[2]/div/div[2]/div[2]")
	public WebElement calenderEnd;
	@FindBy(xpath =".//*[@id='publishRecordingWindow']/form/div[1]/div[1]/div[2]/div/div[1]/div[2]/div/table/thead/tr[1]/th[3]")
	public WebElement titleOfCalenderStart;
	@FindBy(xpath =".//*[@id='publishRecordingWindow']/form/div[1]/div[1]/div[2]/div/div[2]/div[2]/div/table/thead/tr[1]/th[3]")
	public WebElement titleOfCalenderEnd;
	@FindBy(xpath =".//*[@id='publishRecordingWindow']/form/div[1]/div[2]/span[2]")
	public WebElement errorTitleWrongDates;
	@FindBy(xpath =".//*[@id='publishRecordingWindow']/form/div[1]/div[2]/span[3]")
	public WebElement errorTitleMissingDates;
	@FindBy(xpath =".//*[@id='publishRecordingWindow']/form/div[1]/div[2]/span[1]")
	public WebElement errorTitleInvalidDates;
	
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
	
	public void clickOnCancelButton() {
			try {
				wait.until(ExpectedConditions.visibilityOf(cancel_button));	
				
				while(isPublishWindowClosed() == false) {
					((JavascriptExecutor) driver).executeScript("document.getElementById(\""+cancel_button.getAttribute("id")+"\").click();");
					Thread.sleep(1000);
				}
				
				System.out.println("Clicked on cancel button.");
				ATUReports.add("Clicked on cancel button.", "Success.", "Success.", LogAs.PASSED, null);
				Assert.assertTrue(true);
			} catch (Exception msg) {
				System.out.println("Fail to click on cancel button.");
				ATUReports.add("Clicked on cancel button.", "Success.", "Fail."+msg.getMessage(), LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
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
	
	// This function verify that never option is: 0 - not selected, 1 - selected
	public void verifyThatDateRangeOptionSelectedOrNotSelected(int selected) {
			boolean is_selected = date_range_select_button.isSelected();
			
			if(selected == 0) {
				if(is_selected) {
					System.out.println("date range option is selected.");
					ATUReports.add("date range option.", "Not selected.", "Selected.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
					Assert.assertTrue(false);
				} else {
					System.out.println("date range option is not selected.");
					ATUReports.add("date range option.", "Not selected.", "Not selected.", LogAs.PASSED, null);
					Assert.assertTrue(true);
				}
			} else {
				if(is_selected) {
					System.out.println("date range option is selected.");
					ATUReports.add("date range option.", "Selected.", "Selected.", LogAs.PASSED, null);
					Assert.assertTrue(true);
				} else {
					System.out.println("date range option is not selected.");
					ATUReports.add("date range option.", "Selected.", "Not selected.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
					Assert.assertTrue(false);
				}
			}
		}
		
	// This function verify that always option is: 0 - not selected, 1 - selected
	public void verifyThatAlwaysOptionSelectedOrNotSelected(int selected) {
			boolean is_selected = always_select_button.isSelected();
			
			if(selected == 0) {
				if(is_selected) {
					System.out.println("Always option is selected.");
					ATUReports.add("Always option.", "Not selected.", "Selected.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
					Assert.assertTrue(false);
				} else {
					System.out.println("Always option is not selected.");
					ATUReports.add("Always option.", "Not selected.", "Not selected.", LogAs.PASSED, null);
					Assert.assertTrue(true);
				}
			} else {
				if(is_selected) {
					System.out.println("Always option is selected.");
					ATUReports.add("Always option.", "Selected.", "Selected.", LogAs.PASSED, null);
					Assert.assertTrue(true);
				} else {
					System.out.println("Always option is not selected.");
					ATUReports.add("Always option.", "Selected.", "Not selected.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
					Assert.assertTrue(false);
				}
			}
		}
		
	// This function clicks on save button
	public void clickOnSaveButton()
	{
		try {
			while(isPublishWindowClosed() == false) {
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", save_button);	
				Thread.sleep(2000);
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

	
	public void clickOnSaveButtonWithOutCloseTheWindow()
	{
		try{
			waitForVisibility(save_button);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", save_button);		
			System.out.println("Clicked on save button.");
			ATUReports.add("Clicked on save button.", "True.", "True.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} catch(Exception msg) {
			System.out.println("Not clicked on save button.");
			ATUReports.add("Clicked on save button.", "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}
	
	
	public void waitForPageToLoad() {
		
		try{
			wait.until(ExpectedConditions.visibilityOf(publish_window_title));
			wait.until(ExpectedConditions.visibilityOf(publish_window_title_background));
			wait.until(ExpectedConditions.visibilityOf(publish_window_text));
			wait.until(ExpectedConditions.visibilityOf(always_select_button));
			wait.until(ExpectedConditions.visibilityOf(never_option));
			wait.until(ExpectedConditions.elementToBeClickable(By.id("CancelButton")));
			wait.until(ExpectedConditions.elementToBeClickable(By.id("SubmitButton")));
			
			}catch(Exception e){
				e.getMessage();
				ATUReports.add(e.getMessage(), "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			}
		
	}
	
	// verify Publish menu background color is same as recording background color
	public void verifyPublishColor(RecordingHelperPage rec) throws InterruptedException {
			
			try{
			String background_rec = rec.getBackGroundColor(rec.background);
			String menu_background = getBackGroundColor(publish_window_title_background);
			if (rec.getBackGroundColor(rec.background).equals(getBackGroundColor(publish_window_title_background))) {
				ATUReports.add("Publish menu background color is same as recording background color", LogAs.PASSED, null);
				System.out.println("Publish menu background color is same as recording background color");
				Assert.assertTrue(true);
			} else {
				ATUReports.add("Publish menu background color is not  same as recording background color", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				System.out.println("Publish menu background color is  not same as recording background color");
				Assert.assertTrue(false);
			}
			}catch(Exception e){
				e.getMessage();
				ATUReports.add(e.getMessage(), "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));

				}
		}
				
		/**
	 * this function verify edit recording properties Title
		 */
	public void verifyPublishWindowTitle() throws InterruptedException {
			try{
			waitForVisibility(publish_window_title);
			String val = publish_window_title.getText();
			if (val.equals("Publish")) {
				System.out.println("Publish menu title verified.");
				ATUReports.add("Publish menu title verified.", LogAs.PASSED, null);
			} else {
				System.out.println("Publish menu title not verified.");
				ATUReports.add("Publish title not verified.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			}
			Assert.assertEquals("Publish", val);
			
			}catch(Exception e){
				e.getMessage();
				ATUReports.add(e.getMessage(), "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));

				}
		}
			
	public void verifyInfomativeText() {
			
			try {
				String infoText  = publish_window_text.getText();
				
				if (infoText.equals("Publishing allows you to control when recordings are made available to students")) {
					System.out.println("Valid Infomative Text.");
					ATUReports.add("Valid Infomative Text.", LogAs.PASSED, null);
				} else {
					System.out.println("Not Valid Infomative Text.");
					ATUReports.add("Not Valid Infomative Text.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				}
			} catch (Exception e) {	
				e.printStackTrace();
				ATUReports.add(e.getMessage(), "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			}
			
		}

	public void verifyErrorTitleInvalidDatesDisplayedAndValid() {
		
		try {	
			if(errorTitleInvalidDates.isDisplayed()){		
				String errorTitleText  = errorTitleInvalidDates.getText();		
				if (errorTitleText.equals("Cannot publish the selected recordings. Please contact Tegrity support")) {
					System.out.println("The error message is displayed and valid.");
					ATUReports.add("The error message is displayed and valid.","True.","True.", LogAs.PASSED, null);
				} else{
					System.out.println("The error message is displayed and not valid.");
					ATUReports.add("The error message is displayed and not valid.","True.","False.",LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				}				
			} else {
				System.out.println("The error message is not displayed.");
				ATUReports.add("The error message is not displayed.","True.","False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			}
		} catch (Exception e) {	
			e.printStackTrace();
			ATUReports.add(e.getMessage(), "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
		
	}
	
public void verifyErrorTitleMissingDatesDisplayedAndValid() {
		
		try {	
			if(errorTitleMissingDates.isDisplayed()){		
				String errorTitleText  = errorTitleMissingDates.getText();		
				if (errorTitleText.equals("Please specify both the beginning and end dates.")) {
					System.out.println("The error message is displayed and valid.");
					ATUReports.add("The error message is displayed and valid.","True.","True.", LogAs.PASSED, null);
				} else{
					System.out.println("The error message is displayed and not valid.");
					ATUReports.add("The error message is displayed and not valid.","True.","False.",LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				}				
			} else {
				System.out.println("The error message is not displayed.");
				ATUReports.add("The error message is not displayed.","True.","False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			}
		} catch (Exception e) {	
			e.printStackTrace();
			ATUReports.add(e.getMessage(), "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
		
	}
	
	public void verifyErrorTitleWrongDatesDisplayedAndValid() {
		
		try {	
			if(errorTitleWrongDates.isDisplayed()){		
				String errorTitleText  = errorTitleWrongDates.getText();		
				if (errorTitleText.equals("Please enter an end-date that is after or the same as the beginning date.")) {
					System.out.println("The error message is displayed and valid.");
					ATUReports.add("The error message is displayed and valid.","True.","True.", LogAs.PASSED, null);
				} else{
					System.out.println("The error message is displayed and not valid.");
					ATUReports.add("The error message is displayed and not valid.","True.","False.",LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				}				
			} else {
				System.out.println("The error message is not displayed.");
				ATUReports.add("The error message is not displayed.","True.","False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			}
		} catch (Exception e) {	
			e.printStackTrace();
			ATUReports.add(e.getMessage(), "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
		
	}
	

	public void verifyInfomativeTextAndVerifyBelowTheOtherInfoText() {
			
			try {
				String infoText  = publish_window_text2.getText();			
				if (infoText.equals("When would you like the selected recordings to be available?")) {
					System.out.println("Valid Infomative Text2.");
					ATUReports.add("Valid Infomative Text2.", LogAs.PASSED, null);
				} else {
					System.out.println("Not Valid Infomative Text2.");
					ATUReports.add("Not Valid Infomative Text2.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				}
	
				Point LocText = publish_window_text.getLocation();
				Point LocText2 = publish_window_text2.getLocation();
				
				if(LocText.getY() < LocText2.getY()){
					System.out.println("The location of the line 'Publishing allows ...' is above the line 'When would you.'");
					ATUReports.add("The location of the line 'Publishing allows ...' is above the line 'When would you'.", LogAs.PASSED, null);
				} else {
					System.out.println("The location of the line 'Publishing allows ...' is below the line 'When would you'");
					ATUReports.add("The location of the line 'Publishing allows ...' is below the line 'When would you'.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				}
	
			} catch (Exception e) {	
				e.printStackTrace();
				ATUReports.add(e.getMessage(), "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			}
						
		}

	public void verifyThatTheRadioButtonsWillDisplayBelow() {
			try {
				
				Point LocText2 = publish_window_text2.getLocation();
				Point always = always_select_button.getLocation();
				Point dateRange = date_range_select_button.getLocation();
				Point never = never_select_button.getLocation();
				
				
				if( never.getY()>dateRange.getY()  &&  dateRange.getY() > always.getY() && always.getY() > LocText2.getY()){
					System.out.println("The location of the radio buttons is below the line 'When would you.'");
					ATUReports.add("The location of the radio buttons is below the line 'When would you.'.", LogAs.PASSED, null);
				} else {
					System.out.println("The location of the radio buttons is above the line 'When would you.'");
					ATUReports.add("The location of the radio buttons is above the line 'When would you.'", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				}
	
			} catch (Exception e) {	
				e.printStackTrace();
				ATUReports.add(e.getMessage(), "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			}
			
			
		}
			
	public void VerifyTheLocationOfTheSaveAndCancel() {
			
			try{
				
			Point cancel=cancel_button.getLocation();
			Point save= save_button.getLocation();
			Point title = publish_window_title_background.getLocation();
			
			if((save.getY() > title.getY()) && (save.getX() > cancel.getX()))
			{
			 System.out.println("The Save button is displayed on the bottom right corner of the model window.");
			 ATUReports.add("The Save button is displayed on the bottom right corner of the model window.", LogAs.PASSED, null);
			}else {
			 System.out.println("The Save button isn't displayed on the bottom right corner of the model window.");
			 ATUReports.add("The Save button isn't displayed on the bottom right corner of the model window.", LogAs.FAILED,new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			}
			if((cancel.getY() > title.getY()) && (cancel.getX() < save.getX())) {
				 System.out.println("The Cancel button is displayed on the left of the Save button.");
				 ATUReports.add("The Cancel button is displayed on the left of the Save button.", LogAs.PASSED, null);
				}else {
				 System.out.println("The Cancel button is not displayed on the left of the Save button.");
				 ATUReports.add("The Cancel button is not displayed on the left of the Save button.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				}
			}catch(Exception e){
				e.getMessage();
				ATUReports.add(e.getMessage(), "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));

				}
			}
	
	public void VerifyTheContentOfTheAvailablePublishingOptions() {
		
			try{
				
				String alwaysText = always_label.getText();
				String dateRangeText = date_range_label.getText();
				String neverText = never_label.getText();
				String andText = and_label.getText();
				String className = "form-control ng-pristine ng-valid ng-valid-valid-date";
				List<WebElement> Date = (List<WebElement>)((JavascriptExecutor) driver).executeScript("return document.getElementsByClassName(\""+className+"\");");
				String endDateText = Date.get(1).getAttribute("placeholder");
				String fromDateText =Date.get(0).getAttribute("placeholder");
				
				if(alwaysText.equals("Always"))
				{
					System.out.println("The context of the first label is: " +alwaysText +" equels to Always.");
					ATUReports.add("The context of the first label is: " +alwaysText +" equels to Always.", LogAs.PASSED, null);
				}else {
					System.out.println("The context of the first label is: " +alwaysText +" not equels to Always.");
					ATUReports.add("The context of the first label is: " +alwaysText +" not equels to Always.", LogAs.FAILED,new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				}
				if(dateRangeText.equals("Date Range"))
				{
					System.out.println("The context of the second label is: " +dateRangeText +" equels to Date Range.");
					ATUReports.add("The context of the second label is: " +dateRangeText +" equels to Date Range.", LogAs.PASSED, null);
				}else {
					System.out.println("The context of the second label is: " +dateRangeText +" not equels to Date Range.");
					ATUReports.add("The context of the second label is: " +dateRangeText +" not equels to Date Range.", LogAs.FAILED,new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				}
				if(neverText.equals("Never"))
				{
					System.out.println("The context of the third label is: " +neverText +" equels to Never.");
					ATUReports.add("The context of the third label is: " +neverText +" equels to Never.", LogAs.PASSED, null);
				}else {
					System.out.println("The context of the third label is: " +neverText +" not equels to Never.");
					ATUReports.add("The context of the third label is: " +neverText +" not equels to Never.", LogAs.FAILED,new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				}
				
				if(andText.equals("and"))
				{
					System.out.println("The context of the middle label is: " +andText +" equels to and.");
					ATUReports.add("The context of the middle label is: " +andText +" equels to and.", LogAs.PASSED, null);
				}else {
					System.out.println("The context of the middle label is: " +andText +" not equels to and.");
					ATUReports.add("The context of the middle label is: " +andText +" not equels to and.", LogAs.FAILED,new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				}
				
				if(endDateText.equals("Select Date"))
				{
					System.out.println("The context of the first date label is: " +fromDateText +" equels to Select Date.");
					ATUReports.add("The context of the first date label is: " +fromDateText +" equels to and.", LogAs.PASSED, null);
				}else {
					System.out.println("The context of the first date label is: " +fromDateText +" not equels to Select Date.");
					ATUReports.add("The context of the first date label is: " +fromDateText +" not equels to Select Date.", LogAs.FAILED,new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				}
				if(endDateText.equals("Select Date"))
				{
					System.out.println("The context of the end date label is: " +endDateText +" equels to Select Date.");
					ATUReports.add("The context of the end date label is: " +endDateText +" equels to Select Date.", LogAs.PASSED, null);
				}else {
					System.out.println("The context of the end date label is: " +endDateText +" not equels to Select Date.");
					ATUReports.add("The context of the end date label is: " +endDateText +" not equels to Select Date.", LogAs.FAILED,new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				}
						
				}catch(Exception e){
					e.printStackTrace();
					ATUReports.add(e.getMessage(), "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));

					}
				}

	public void verifyThatAfterClickingOnTheFromTheCalenderWidgetIsDisplayed(WebElement element,WebElement calender) {
		try{			
			clickElementJS(element);	
			if(calender.isDisplayed()){
				System.out.println("verify that After Clicking On The From The Calender Widget Is Displayed.");
				ATUReports.add("verify that After Clicking On The From The Calender Widget Is Displayed.", LogAs.PASSED, null);
			}else {
				System.out.println("not verify that After Clicking On The From The Calender Widget Is Displayed.");
				ATUReports.add("not verify that After Clicking On The From The Calender Widget Is Displayed.", LogAs.FAILED,new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			}
		}catch(Exception e){
			e.printStackTrace();
			ATUReports.add(e.getMessage(), "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));

			}
		}	
			
	public void chooseRadioButton(String choose) {
		
		try{
			switch(choose){	
				case "Always":
					clickElementJS(always_select_button);
					break;
				case "Date Range":
					clickElementJS(date_range_select_button);
					break;
				case "Never":
					clickElementJS(never_select_button);
					break;				
			}
		}catch(Exception e){
			e.printStackTrace();
			ATUReports.add(e.getMessage(), "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
	}

	public void veirfyDateFromThisDate(String date ,WebElement dateCalender) throws ParseException {
			
			try{			
				String dateToCompare = dateCalender.getAttribute("value");
				if(dateToCompare.equals(date)){
					System.out.println("verify that the right date is displayed in textbox");
					ATUReports.add("verify that the right date is displayed in textbox.", LogAs.PASSED, null);
				}else {
					System.out.println("not verify that the right date is displayed in textbox.");
					ATUReports.add("not verify that the right date is displayed in textbox.", LogAs.FAILED,new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				}
			}catch(Exception e){
				e.printStackTrace();
				ATUReports.add(e.getMessage(), "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));

				}	
		}

	// The date is in the following format: 'XX/XX/XXXX'.
	public void verifyThatTheCalendarInTheRightFormat(WebElement date_Field){
		
		String correctDate = date_Field.getAttribute("value");		
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		sdf.setLenient(false);
		try {

			//if not valid, it will throw ParseException
			Date date = sdf.parse(correctDate);
			System.out.println(date);
			System.out.println("The date is in the following format: 'XX/XX/XXXX'");
			ATUReports.add("The date is in the following format: 'XX/XX/XXXX'", "Success.", "Success.", LogAs.PASSED, null);
			Assert.assertTrue(true);
			
		} catch (ParseException e) {
			System.out.println("The date is not in the following format: 'XX/XX/XXXX'" );				
			ATUReports.add("The date is not in the following format: 'XX/XX/XXXX'", "Success.", "Fail", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}

	}

	
	public void clearTheDateOfTheWebElement(WebElement element) {
		
		try{			
			element.clear();
			System.out.println("Delete the textbox content.");
			ATUReports.add("Delete the textbox content.","Erase.","Erase.", LogAs.PASSED, null);
			
		}catch(Exception e){
			e.printStackTrace();
			ATUReports.add(e.getMessage(), "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));

			}	
	}

}
