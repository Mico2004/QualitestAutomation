package com.automation.main.page_helpers;

import java.util.HashSet;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
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
public class ConfirmationMenu extends Page {
	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");
	}

	// @FindBy(partialLinkText="Ok") WebElement ok_button;
	@FindBy(xpath = "//*[@id='alertWindow']/div[2]/button")
	public WebElement ok_button;
	@FindBy(css = ".btn.btn-default")
	public WebElement ok_buttonCss;
	@FindBy(id = "ModalDialogHeader")
	WebElement header_title;
	@FindBy(id = "ModalDialogHeaderWrap")
	WebElement background_header_title;
	@FindBy(id = "ModalDialogHeader")
	List<WebElement> header_title_list;
	@FindBy(css = ".emphasis.ng-binding")
	List<WebElement> error_msg_body_list;
	@FindBy(css = ".modal-body>.emphasis.ng-binding")
	WebElement correct_error_msg_body;
	@FindBy(xpath = "//*[@id=\"alertWindow\"]/div[1]/p")
	public
	WebElement add_additional_content_confirm_note;
	@FindBy(id = "alertWindow")
	WebElement alertWindow;
	
	
	public ConfirmationMenu(WebDriver browser) {
		super(browser);
		// TODO Auto-generated constructor stub
	}

	// This function clicks on ok button of copy menu
	public void clickOnOkButton() throws InterruptedException {			
		Thread.sleep(1000);
		try {
			if(isAlertPresent()){
				Thread.sleep(1000);
				clickOkInAlertIfPresent();
			}
			waitForVisibility(ok_button);		
			ok_button.click();
			ATUReports.add("Clicked on OK button.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} catch (Exception e) {
			ATUReports.add("Fail click on OK button.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}  
		Thread.sleep(1000);	
	}
	
	// This function clicks on ok button of copy menu
	// It also checks that the window need to contain the following:
	// title = "Success"
	// description = "Recordings have been queued for copy"
	// Can be improved if can access the elements and not only check if the
	// title and description
	// appears in HTML source code.
	public void clickOnOkButtonAfterConfirmCopyRecordings() throws InterruptedException {
		try {
			// String souce_page = driver.getPageSource();
			if (!header_title_list.get(0).getText().contains("Success")) {
				ATUReports.add("Error window title is wrong.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Assert.assertEquals(false, true);
			}
			if (!error_msg_body_list.get(0).getText().contains("Recordings have been queued for copy")) {
				ATUReports.add("Error window description is wrong.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Assert.assertEquals(false, true);
			}
			ok_button.click();
			ATUReports.add("Clicked on OK button.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} catch (Exception e) {
			ATUReports.add("Fail click on OK button.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
		Thread.sleep(3000);
	}

	// This function clicks on ok button of copy menu
	// It also checks that the window need to contain the following:
	// title = "Success"
	// description = "Recording has been queued for copy"
	// Can be improved if can access the elements and not only check if the
	// title and description
	// appears in HTML source code.
	public void clickOnOkButtonAfterConfirmCopyRecording() throws InterruptedException {
		try {
			wait.until(ExpectedConditions.visibilityOf(header_title_list.get(0)));
			// String souce_page = driver.getPageSource();
			try{
				new WebDriverWait(driver,15 ).until(ExpectedConditions.textToBePresentInElement(header_title_list.get(0), "Success"));
		    }catch(org.openqa.selenium.TimeoutException e){
				System.out.println("Error window title is wrong");
				ATUReports.add("Error window title is wrong", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			}	
			try{
				new WebDriverWait(driver,15 ).until(ExpectedConditions.textToBePresentInElement(error_msg_body_list.get(0), "Error window title is wrong"));
		    }catch(org.openqa.selenium.TimeoutException e){
				System.out.println("Error window description is wrong");
				ATUReports.add("Error window description is wrong", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			}	
			waitForVisibility(ok_button);
			ok_button.click();
			System.out.println("Clicked on OK button");
			ATUReports.add("Clicked on OK button.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} catch (Exception e) {
			System.out.println("Fail click on OK button");
			ATUReports.add("Fail click on OK button", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
		Thread.sleep(1500);
	}

	// This function clicks on ok button of copy menu
	// It also checks that the window need to contain the following:
	// title = "Error"
	// description = "Error window description is wrong."
	// Can be improved if can access the elements and not only check if the
	// title and description
	// appears in HTML source code.
	public void clickOnOkButtonOnErrorWindow() throws InterruptedException {
		try {
			if (!header_title_list.get(1).getText().contains("Error")) {
				System.out.println("Error window title is wrong");
				ATUReports.add("Error window title is wrong.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Assert.assertEquals(false, true);
			}
			if (!error_msg_body_list.get(1).getText().contains("A course must be selected")) {
				System.out.println("Error window description is wrong");
				ATUReports.add("Error window description is wrong.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Assert.assertEquals(false, true);
			}
			ok_button.click();
			System.out.println("Clicked on OK button");
			ATUReports.add("Clicked on OK button.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} catch (Exception e) {
			System.out.println("Fail click on OK button");
			ATUReports.add("Fail click on OK button.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
		Thread.sleep(3000);
	}

	// This function clicks on ok button of copy menu
	// It also checks that the window need to contain the following:
	// title = "Success"
	// description = "Recording has been queued for move"
	// Can be improved if can access the elements and not only check if the
	// title and description
	// appears in HTML source code.
	public void clickOnOkButtonAfterConfirmMoveRecording() throws InterruptedException {
		try {
			if (!header_title_list.get(0).getText().contains("Success")) {
				System.out.println("Error window title is wrong");
				ATUReports.add("Error window title is wrong", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Assert.assertEquals(false, true);
			}
			if (!error_msg_body_list.get(0).getText().contains("Recording has been queued for move")) {
				System.out.println("Error window description is wrong");
				ATUReports.add("Error window description is wrong", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Assert.assertEquals(false, true);
			}
			ok_button.click();
			System.out.println("Clicked on OK button");
			ATUReports.add("Clicked on OK button.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} catch (Exception e) {
			System.out.println("Fail click on OK button");
			ATUReports.add("Fail click on OK button", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
		Thread.sleep(3000);
	}

	// This function return true if confirmation menu is closed,
	// and false if it open
	public boolean isConfirmationMenuClosed() {
		try {
			header_title.isDisplayed();
			return false;
		} catch (org.openqa.selenium.NoSuchElementException msg) {
			return true;
		}
	}

	// This function clicks on ok button of cannot copy in process or fail
	// recordings
	// It also checks that the window need to contain the following:
	// title = "Copy"
	// description = "Cannot copy in-process or fail recordings"
	// Can be improved if can access the elements and not only check if the
	// title and description
	// appears in HTML source code.
	public void clickOnOkButtonAfterCannotCopyInProcessOrFailRecordings() throws InterruptedException {
		try {
			if (!header_title_list.get(0).getText().contains("Copy")) {
				System.out.println("Error window title is wrong");
				ATUReports.add("Error window title is wrong", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Assert.assertEquals(false, true);
			}
			if (!error_msg_body_list.get(0).getText().contains("Cannot copy in-process or fail recordings")) {
				System.out.println("Error window description is wrong");
				ATUReports.add("Error window description is wrong", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Assert.assertEquals(false, true);
			}
			ok_button.click();
			System.out.println("Clicked on OK button");
			ATUReports.add("Clicked on OK button.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} catch (Exception e) {
			System.out.println("Fail click on OK button");
			ATUReports.add("Fail click on OK button", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
		Thread.sleep(3000);
	}

	public void verifyCopySourceRecordingStatus(String expected) {
		String status = "Being copied from";
		if (expected.equals(status)) {
			ATUReports.add("right status", LogAs.PASSED, null);
			System.out.println("right status");
			Assert.assertTrue(true);
		} else {
			ATUReports.add("wrong status...", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			System.out.println("wrong status...");
			Assert.assertTrue(false);
		}
	}

	/// verify recording status for moving
	public void verifyMoveSourceRecordingStatus(String expected) {
		String status = "Recording has been queued for move";
		if (expected.equals(status)) {
			ATUReports.add("right status", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			ATUReports.add("werong status...", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}

	// This function clicks on ok button of copy menu
	// It also checks that the window need to contain the following:
	// title = "Success"
	// description = "Recordings have been queued for move"
	// Can be improved if can access the elements and not only check if the
	// title and description
	// appears in HTML source code.
	public void clickOnOkButtonAfterConfirmMoveRecordings() throws InterruptedException {
		try {
			// String souce_page = driver.getPageSource();
			if (!header_title_list.get(0).getText().contains("Success")) {
				ATUReports.add("Error window title is wrong.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Assert.assertEquals(false, true);
			}
			if (!error_msg_body_list.get(0).getText().contains("Recordings have been queued for move")) {
				ATUReports.add("Error window description is wrong.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Assert.assertEquals(false, true);
			}
			ok_button.click();
			ATUReports.add("Clicked on OK button.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} catch (Exception e) {
			ATUReports.add("Fail click on OK button.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
		Thread.sleep(3000);
	}

	// This function clicks on ok button of cannot move in process or fail
	// recordings
	// It also checks that the window need to contain the following:
	// title = "Move"
	// description = "Cannot move in-process or fail recordings"
	// Can be improved if can access the elements and not only check if the
	// title and description
	// appears in HTML source code.
	public void clickOnOkButtonAfterCannotMoveInProcessOrFailRecordings() throws InterruptedException {
		try {
			String souce_page = driver.getPageSource();
			if (!souce_page.contains("Move")) {
				System.out.println("Error window title is wrong");
				ATUReports.add("Error window title is wrong", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Assert.assertEquals(false, true);
			}
			if (!souce_page.contains("Cannot move in-process or fail recordings")) {
				System.out.println("Error window description is wrong");
				ATUReports.add("Error window description is wrong", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Assert.assertEquals(false, true);
			}
			ok_button.click();
			System.out.println("Clicked on OK button");
			ATUReports.add("Clicked on OK button.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} catch (Exception e) {
			System.out.println("Fail click on OK button");
			ATUReports.add("Fail click on OK button", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
		Thread.sleep(3000);
	}

	// This function clicks on ok button of The following recording(s) could not
	// be deleted
	// It also checks that the window need to contain the following:
	// title = "Delete"
	// description = "The following recording(s) could not be deleted"
	// Can be improved if can access the elements and not only check if the
	// title and description
	// appears in HTML source code.
	public void clickOnOkButtonAfterCouldNotBeDeleted() throws InterruptedException {
		try {
			String souce_page = driver.getPageSource();
			if (!souce_page.contains("Delete")) {
				System.out.println("Error window title is wrong");
				ATUReports.add("Error window title is wrong", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Assert.assertEquals(false, true);
			}
			if (!souce_page.contains("The following recording(s) could not be deleted")) {
				System.out.println("Error window description is wrong");
				ATUReports.add("Error window description is wrong", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Assert.assertEquals(false, true);
			}
			ok_button.click();
			System.out.println("Clicked on OK button");
			ATUReports.add("Clicked on OK button.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} catch (Exception e) {
			System.out.println("Fail click on OK button");
			ATUReports.add("Fail click on OK button", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
		Thread.sleep(3000);
	}

	public void clickOnOkButtonAfterConfirmEditRecordingProperties() throws InterruptedException {
		for(int i=0; i<90; i++) {
			try {
				if(driver.findElement(By.cssSelector(".emphasis.ng-binding")).getText().contains("Recording properties have been queued for edit")) {
					break;
				} else {
					System.out.println(error_msg_body_list.get(0).getText());
					Thread.sleep(1000);
				}
			} catch (Exception msg) {
				Thread.sleep(1000);
			}
			
		}
		
		try {
			String source_page = driver.getPageSource();
			if (!source_page.contains("Edit Recording Properties")) {
				System.out.println("Error window title is wrong.");
				ATUReports.add("Error window title is wrong.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Assert.assertEquals(false, true);
			}
			if (!source_page.contains("Recording properties have been queued for edit")) {
				System.out.println("Error window description is wrong.");
				ATUReports.add("Error window description is wrong.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Assert.assertEquals(false, true);
			}
			ok_button.click();
			System.out.println("Clicked on OK button.");
			ATUReports.add("Clicked on OK button.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} catch (Exception e) {
			System.out.println("Fail click on OK button.");
			ATUReports.add("Fail click on OK button.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
		Thread.sleep(3000);
	}

	// This function check that there is message: "Please select an instructor",
	// and click on ok button
	public void clickOnOkButtonAfterErrorNoInstructorSelected() {
		String error_msg = correct_error_msg_body.getText();
		if (error_msg.equals("Please select an instructor")) {
			System.out.println("Correct error message: Please select an instructor");
			ATUReports.add("Correct error message.", "Please select an instructor", "Please select an instructor",
					LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Not correct error messsage instead: Please select an instructor. Appear: " + error_msg);
			ATUReports.add("Not correct error message.", "Please select an instructor", error_msg, LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}

		try {
			ok_button.click();
			System.out.println("Clicked on ok button.");
			ATUReports.add("Clicked on ok button.", "True.", "True.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} catch (Exception msg) {
			System.out.println("Not clicked on ok button.");
			ATUReports.add("Clicked on ok button.", "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}

	// This function get string of title and check and return if window with
	// that title open or closed
	public boolean checkIfWindowModalWithTargetNameIsClosed(String target) {
		try {
			for (int i = 0; i < header_title_list.size(); i++) {
				if (header_title_list.get(i).getText().equals(target)) {
					return false;
				}
			}
			return true;
		} catch (Exception msg) {
			return true;
		}

	}

	// This function verify that confirm window is close
	public void verifyConfirmWindowIsClosed() {
		boolean is_closed = isConfirmationMenuClosed();

		if (is_closed) {
			System.out.println("Confirm window is closed.");
			ATUReports.add("Confirm window.", "Closed.", "Closed.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Confirm window is open.");
			ATUReports.add("Confirm window.", "Closed.", "Open.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}

	// This function verify that confirm window is open
	public void verifyConfirmWindowIsOpen() {
		boolean is_closed = isConfirmationMenuClosed();

		if (is_closed) {
			System.out.println("Confirm window is closed.");
			ATUReports.add("Confirm window.", "Open.", "Closed.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		} else {
			System.out.println("Confirm window is open.");
			ATUReports.add("Confirm window.", "Open.", "Open.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		}
	}

	// This function clicks on ok button of copy menu
	// It also checks that the window need to contain the following:
	// title = "Success"
	// description = "Recordings have been queued for move"
	// Can be improved if can access the elements and not only check if the
	// title and description
	// appears in HTML source code.
	public void clickOnOkButtonAfterConfirmAddAdditionalContentFile(String file_name) throws InterruptedException {
		try {
			Thread.sleep(1000);
			if (!header_title_list.get(0).getText().contains("Success")) {
				ATUReports.add("Error window title is wrong.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Assert.assertEquals(false, true);
			}
			if (!error_msg_body_list.get(0).getText().contains(file_name + " was uploaded successfully")) {
				ATUReports.add("Error window description is wrong.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Assert.assertEquals(false, true);
			}
			waitForVisibility(ok_button);
			ok_button.click();
			ATUReports.add("Clicked on OK button.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} catch (Exception e) {
			ATUReports.add("Fail click on OK button.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
		Thread.sleep(3000);
	}
	
	
	public void clickOnOkButtonAfterErrorClickOnTheApply() throws InterruptedException {
		try {
			Thread.sleep(1000);
			if (!header_title_list.get(0).getText().contains("Error")) {
				ATUReports.add("Error window title is wrong.", LogAs.PASSED, null);
				
			}
			if (!error_msg_body_list.get(0).getText().contains("An error has occurred.")) {
				ATUReports.add("Error window description is wrong.", LogAs.PASSED, null);
			}
			waitForVisibility(ok_button);
			ok_button.click();
			ATUReports.add("Clicked on OK button.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} catch (Exception e) {
			ATUReports.add("Fail click on OK button.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
		Thread.sleep(3000);
	}

	public void clickOnOkButtonAfterConfirmAddAdditionalContentLink() throws InterruptedException {
		try {
			
			if (!header_title_list.get(0).getText().contains("Success")) {
				ATUReports.add("Error window title is wrong.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Assert.assertEquals(false, true);
			}
			if (!error_msg_body_list.get(0).getText().contains("Additional content link is successfully added.")) {
				ATUReports.add("Error window description is wrong.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Assert.assertEquals(false, true);
			}
			ok_button.click();
			ATUReports.add("Clicked on OK button.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} catch (Exception e) {
			ATUReports.add("Fail click on OK button.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
		Thread.sleep(3000);
	}

	public void clickOnOkButtonAfterConfirmEmailSetting() throws InterruptedException {
		try {

			if (!header_title_list.get(0).getText().contains("Success")) {
				ATUReports.add("Error window title is wrong.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Assert.assertEquals(false, true);
			}
			if (!error_msg_body_list.get(0).getText().contains("Settings have been updated")) {
				ATUReports.add("Error window description is wrong.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Assert.assertEquals(false, true);
			}
			ok_button.click();
			ATUReports.add("Clicked on OK button.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} catch (Exception e) {
			ATUReports.add("Fail click on OK button.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
		Thread.sleep(3000);
	}

	/// click ok after send support email
	public void clickOnOkButtonAfterConfirmEmailSentSuccessfully() throws InterruptedException {
		// TODO Auto-generated method stub
		try {

			if (!header_title_list.get(0).getText().contains("Success")) {
				ATUReports.add("Error window title is wrong.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Assert.assertEquals(false, true);
			}
			if (!error_msg_body_list.get(0).getText().contains("Email sent successfully")) {
				ATUReports.add("Error window description is wrong.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Assert.assertEquals(false, true);
			}
			ok_button.click();
			ATUReports.add("Clicked on OK button.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} catch (Exception e) {
			ATUReports.add("Fail click on OK button.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
		Thread.sleep(3000);
	}

	/// confirmation after changing setting
	public void clickonokbuttonafterEulaChangeSetting() throws InterruptedException {

		try {

			if (!header_title_list.get(0).getText().contains("Success")) {
				ATUReports.add("Error window title is wrong.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Assert.assertEquals(false, true);
			}
			if (!error_msg_body_list.get(0).getText().contains("Settings have been updated")) {
				ATUReports.add("Error window description is wrong.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Assert.assertEquals(false, true);
			}
			ok_button.click();
			ATUReports.add("Clicked on OK button.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} catch (Exception e) {
			ATUReports.add("Fail click on OK button.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
		Thread.sleep(3000);
	}
	
	
	// This function verify that confirm window is close
	public void verifyConfirmationMenuWithTargetHeaderClosed(String target_header) {
		boolean is_closed = true;

		for (int i = 0; i < header_title_list.size(); i++) {
			if (header_title_list.get(i).equals(target_header)) {
				is_closed = false;
				break;
			}
		}

		if (is_closed) {
			System.out.println("Confirm window is closed.");
			ATUReports.add("Confirm window.", "Closed.", "Closed.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Confirm window is open.");
			ATUReports.add("Confirm window.", "Closed.", "Open.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}

	//This function cick on ok afte adding srt file from the local computer
	public void clickOnOkButtonAfterAddCloseCaptioning() throws InterruptedException
	{
		try {		
			if (!header_title_list.get(0).getText().contains("Success")) {
				ATUReports.add("Error window title is wrong.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Assert.assertEquals(false, true);
			}
			if (!error_msg_body_list.get(0).getText().contains("Closed captions were added successfully")) {
				ATUReports.add("Error window description is wrong.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Assert.assertEquals(false, true);
			}
			Thread.sleep(5000);
			wait.until(ExpectedConditions.elementToBeClickable(ok_buttonCss));
			wait.until(ExpectedConditions.elementToBeClickable(ok_button));
			ok_buttonCss.click();
			ok_button.click();
			/*ATUReports.add("Clicked on OK button.", LogAs.PASSED, null);
			System.out.println("Clicked on OK button.");*/
			Assert.assertTrue(true);
		} catch (Exception e) {
			ATUReports.add("failed to click on ok button.", LogAs.WARNING, null);
		}
		Thread.sleep(3000);
	}
	
	
	public void clickOnOkButtonAfterEditRecord() throws InterruptedException
	{
		try {		
			if (!header_title_list.get(0).getText().contains("Success")) {
				ATUReports.add("Error window title is wrong.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Assert.assertEquals(false, true);
			}
			if (!error_msg_body_list.get(0).getText().contains("Add operation completed successfully!")) {
				ATUReports.add("Error window description is wrong.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Assert.assertEquals(false, true);
			}
			Thread.sleep(5000);
			wait.until(ExpectedConditions.elementToBeClickable(ok_buttonCss));
			wait.until(ExpectedConditions.elementToBeClickable(ok_button));
			ok_buttonCss.click();
			ok_button.click();
			ATUReports.add("Clicked on OK button.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} catch (Exception e) {
			ATUReports.add("Clicked on OK button", LogAs.WARNING, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
		Thread.sleep(3000);
	}
	
	public void verifyConfirmBackgroundColor(RecordingHelperPage rec) throws InterruptedException {
		Thread.sleep(2000);
		String background_rec = rec.getBackGroundColor(rec.background);
		String menu_background = getBackGroundColor(background_header_title);
		if (background_rec.equals(menu_background)) {
			ATUReports.add("Confirm menu background color is same as recording background color.", LogAs.PASSED, null);
			System.out.println("Confirm menu background color is same as recording background color.");
			Assert.assertTrue(true);
		} else {
			ATUReports.add("Confirm menu background color is not same as recording background color.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			System.out.println("Confirm menu background color is not same as recording background color.");
			Assert.assertTrue(false);
		}

	}
	
	public void verifyTheLocationOfTheOkButtonIsInTheButtomRight() throws InterruptedException {
	
		
		Point headerTitle = header_title.getLocation();
		Point okButton = ok_button.getLocation();
		
		if (headerTitle.getX() < okButton.getX() && headerTitle.getY() < okButton.getY() ) {
			ATUReports.add("The location of the ok button is on the right bottom.", LogAs.PASSED, null);
			System.out.println("The location of the ok button is on the right bottom.");			
		} else {
			ATUReports.add("The location of the ok button is not on the right bottom.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			System.out.println("The location of the ok button is not on the right bottom.");
		
		}

	}
}
