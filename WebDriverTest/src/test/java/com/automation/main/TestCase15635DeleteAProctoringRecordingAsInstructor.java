package com.automation.main;


import java.awt.AWTException;
import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.collections.set.PredicatedSet;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;



import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import atu.testng.reports.utils.Utils;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;
import junitx.util.PropertyManager;

@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TestCase15635DeleteAProctoringRecordingAsInstructor {

	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}

	public LoginHelperPage tegrity;
	public CoursesHelperPage course;
	public RecordingHelperPage record;
	public ConfirmationMenu confirm_menu;
	public MoveWindow move_window;
	public DeleteMenu delete_menu;
	WebDriver driver;
	WebDriverWait wait;
	public static WebDriver thread_driver;
	CopyMenu copy;
	String currentCourse;
	String targetCourse;
	String clickedRecording;
    DesiredCapabilities capability;
	
    
    @BeforeClass
	public void setup() throws InterruptedException {

		System.setProperty("webdriver.ie.driver", "src/test/resources/IEDriverServer.exe");
		capability=DesiredCapabilities.internetExplorer();
		capability.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING,false);
		
        driver=DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());
		 ATUReports.add("selected browser type", LogAs.PASSED, new CaptureScreen( ScreenshotOf.DESKTOP));

		
		//ATUReports.setWebDriver(driver);
		//ATUReports.add("set driver", true);
		tegrity = PageFactory.initElements(driver, LoginHelperPage.class);

		record = PageFactory.initElements(driver, RecordingHelperPage.class);
		copy = PageFactory.initElements(driver, CopyMenu.class);
		course=PageFactory.initElements(driver, CoursesHelperPage.class);
		confirm_menu = PageFactory.initElements(driver, ConfirmationMenu.class);
		
		move_window = PageFactory.initElements(driver, MoveWindow.class);
		
		delete_menu = PageFactory.initElements(driver, DeleteMenu.class);

		

	
    
    }
	

	
	



	
	// description = "get courses list"
	public void initializeCourseObject() throws InterruptedException {

		course = PageFactory.initElements(driver, CoursesHelperPage.class);
		course.courses = course.getStringFromElement(course.course_list);
	}

	
	@Test( description = "Login course page")
	public void loginCourses() throws InterruptedException
	{
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
		// 1. Login as INSTRUCTOR.
		tegrity.loginCourses("SuperUser");// log in courses page
		Thread.sleep(2000);
		course.selectCourseThatStartingWith("BankValidRecordings");
		// 3. Click on "Tests" tab.
				record.clickOnTestsTab();
				Thread.sleep(1000);
				record.getCheckbox().click();;
				record.clickOnRecordingTaskThenCopy();
				copy.selectTargetCourseFromCourseListThatStartWith("Ab");
				copy.clickOnCopyButton();
				Thread.sleep(2000);
				confirm_menu.clickOnOkButtonAfterConfirmCopyRecording();

				record.checkStatusExistenceForMaxTTime(180);
				
				record.signOut();

		// 1. Login as INSTRUCTOR.
		tegrity.loginCourses("User1");// log in courses page
		initializeCourseObject();
		
		// 2. Select course.
		currentCourse = course.selectCourseThatStartingWith("Ab");
		System.out.println("Current course: " + currentCourse);
		
		// 3. Click on "Tests" tab.
		record.clickOnTestsTab();
		
		Thread.sleep(1000);
		
		// 4. Select recording to delete.
	   record.convertRecordingsListToNames();
	   record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
		String selected_recording_title=record.recording_list_names.get(0);
		List<String> initial_recording_list = record.getCourseRecordingList();
		
		
		// 5. Select "Recording Tasks -> Delete".
		record.clickOnRecordingTaskThenDelete();
		
	/*
		// 6. Verify that only selected recording displayed in "List of Recordings".
		List<String> delete_recording_list = delete_menu.getRecordingList();
		
		if(delete_recording_list.size() == 1) {
			if(delete_recording_list.get(0).startsWith(selected_recording_title)) {
				System.out.println("Verified that only selected recording displayed in list of recordings.");
				ATUReports.add("Verified that only selected recording displayed in list of recordings.", LogAs.PASSED, null);
				Assert.assertTrue(true);
			} else {
				System.out.println("Verified that not selected recording displayed in list of recordings.");
				ATUReports.add("Verified that not selected recording displayed in list of recordings.", LogAs.FAILED, null);
				Assert.assertTrue(false);
			}
  		} else {
			System.out.println("Delete recording list not contain just one recording. It contains: " + delete_recording_list.size());
			ATUReports.add("Delete recording list not contain just one recording. It contains: " + delete_recording_list.size(), LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
	*/	
		// 7. Click "Delete" button
		delete_menu.clickOnDeleteButton();
			
		Thread.sleep(1000);
		
		// 8. "Delete" window is closed.
		boolean is_delete_window_closed = delete_menu.isDeleteMenuClose();
	
		if(is_delete_window_closed) {
			System.out.println("Delete window is closed.");
			ATUReports.add("Delete window is closed.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Delete window not closed.");
			ATUReports.add("Delete window not closed.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		
		// 9. Verify that selected recording is deleted.
		List <String> current_recording_list = record.getCourseRecordingList();
		
		if(current_recording_list.contains(selected_recording_title)) {
			System.out.println("Verified that selected recording is not deleted.");
			ATUReports.add("Verified that selected recording is not deleted.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		} else {
			System.out.println("Verified that selected recording is deleted.");
			ATUReports.add("Verified that selected recording is deleted.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		}
		
		this.driver.quit();
	}
}
