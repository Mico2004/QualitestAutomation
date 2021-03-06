package com.automation.main.delete_recordings;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.automation.main.page_helpers.CopyMenu;
import com.automation.main.page_helpers.CoursesHelperPage;
import com.automation.main.page_helpers.DeleteMenu;
import com.automation.main.page_helpers.LoginHelperPage;
import com.automation.main.page_helpers.MoveWindow;
import com.automation.main.page_helpers.RecordingHelperPage;
import com.automation.main.utilities.DriverSelector;

import java.text.DateFormat;
import java.util.Date;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;
import junit.framework.Assert;

public class TC15653DeleteFailedRecording {
	///

	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}

	public DeleteMenu delete;
	public MoveWindow move_Window;
	public LoginHelperPage tegrity;
	public CoursesHelperPage course;
	public RecordingHelperPage record;
	WebDriver driver;
	WebDriverWait wait;
	public static WebDriver thread_driver;
	CopyMenu copy;
	DesiredCapabilities capability;
	String os;

	public List<String> recording_for_delete;

	@AfterClass
	public void closeBroswer() {
		this.driver.quit();
	}

	@BeforeClass
	public void setup() {
		

			driver = DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());
			
			recording_for_delete = new ArrayList<String>();
			tegrity = PageFactory.initElements(driver, LoginHelperPage.class);
			delete = PageFactory.initElements(driver, DeleteMenu.class);
			course = PageFactory.initElements(driver, CoursesHelperPage.class);
			record = PageFactory.initElements(driver, RecordingHelperPage.class);
			copy = PageFactory.initElements(driver, CopyMenu.class);
			move_Window = PageFactory.initElements(driver, MoveWindow.class);
		
		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC15653DeleteFailedRecording at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC15653DeleteFailedRecording at " + DateToStr, "Starting the test: TC15653DeleteFailedRecording at " + DateToStr, LogAs.PASSED, null);
	}

	@Test (description= "TC 15653 Delete Failed Recording")
	public void test15653() throws InterruptedException {
		// 1.load page
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
		// 2.login as instructor
		tegrity.loginCourses("User1");// log in courses page
		// 3.select invalid courses
		course.selectCourseThatStartingWith("BankInvalid");
		// 4.select first recording
	
		record.getCheckbox().click();// first recording
		// 5.check its status
		record.verifyErrorStatus(record.first_recording_status);

		// list all recording names to check which one is selected and should
		// appear in delete menu
		record.convertRecordingsListToNames();
		int counter = 0;
		for (WebElement element : record.checkboxlist) {
			if (element.isSelected()) {
				recording_for_delete.add(record.recording_list_names.get(counter));
			}
			counter++;
		}

		// 6.to delete menu
		record.toDeleteMenu();
		// 7.click on delete
		delete.clickOnDeleteButton();
		// 8.click on ok

		record.recording_list_names = null;
		record.convertRecordingsListToNames();
		/// 9.check recordings were deleted
		for (String recorded : recording_for_delete) {
			if (record.recording_list_names.contains(recorded)) {
				System.out.println("recording_for_delete was not deleted ");
				ATUReports.add("check if selected recording was deleted", "select checkboxes", "recording dont appear",
						"recording appears", LogAs.FAILED, null);
				Assert.assertTrue(false);
				return;
			}

		}
		System.out.println("recording for delete were deleted successfully");
		ATUReports.add("check if selected recording was deleted", "select checkboxes", "recording dont appear",
				"recording dont appear", LogAs.PASSED, null);
		Assert.assertTrue(true);
		
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
	}
	
	

}
