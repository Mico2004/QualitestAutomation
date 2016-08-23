package com.automation.main;


import java.awt.AWTException;
import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
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
public class TestCase15644CancelTheDelete {

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

	@BeforeClass
	public void setup() {

        driver=DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());
		ATUReports.add("selected browser type", LogAs.PASSED, new CaptureScreen( ScreenshotOf.DESKTOP));

		
		ATUReports.setWebDriver(driver);
		//ATUReports.add("set driver", true);
		tegrity = PageFactory.initElements(driver, LoginHelperPage.class);

		record = PageFactory.initElements(driver, RecordingHelperPage.class);
		copy = PageFactory.initElements(driver, CopyMenu.class);
		
		confirm_menu = PageFactory.initElements(driver, ConfirmationMenu.class);
		
		move_window = PageFactory.initElements(driver, MoveWindow.class);
		
		delete_menu = PageFactory.initElements(driver, DeleteMenu.class);
	}
	
	
	@AfterClass
	public void closeBroswer() {
		this.driver.quit();
	}


	// @Parameters({"web","title"}) in the future
	@Test
	public void loadPage() throws InterruptedException {
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);

	}
	
	// description = "get courses list"
	public void initializeCourseObject() throws InterruptedException {

		course = PageFactory.initElements(driver, CoursesHelperPage.class);
		course.courses = course.getStringFromElement(course.course_list);
	}

	
	@Test(dependsOnMethods = "loadPage", description = "Login course page")
	public void loginCourses() throws InterruptedException
	{
		// 1. Login as INSTRUCTOR.
		tegrity.loginCourses("User1");// log in courses page
		initializeCourseObject();
		
		// 2. Select course.
		currentCourse = course.selectCourseThatStartingWith("Ab");
		System.out.println("Current course: " + currentCourse);
		//course.selectCourse(record);
		
		// 3. Select recording.
		record.selectFirstCheckbox();
		String selected_recording_title = record.getFirstRecordingTitle();
		List<String> initial_recording_list = record.getCourseRecordingList();
		
		// 4. Select "Recording Tasks -> Delete".
		record.clickOnRecordingTaskThenDelete();
		
		// 5. Click the "Cancel" button.
		delete_menu.clickOnCancelButton();
		
		// 6. Verify that selected recording isn't deleted.
		List<String> after_cancel_recording_list = record.getCourseRecordingList();
		
		after_cancel_recording_list.removeAll(initial_recording_list);
		
		if(after_cancel_recording_list.size() == 0) {
			System.out.println("Verify that selected recording isn't deleted.");
			ATUReports.add("Verify that selected recording isn't deleted.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Verify that selected recording is changed.");
			ATUReports.add("Verify that selected recording is changed.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		
		// 7. Select "Recording Tasks -> Delete" menu item.
		initial_recording_list = record.getCourseRecordingList();
		record.clickOnRecordingTaskThenDelete();
		
		
		// 8. Click outside of the "Delete" window.
		delete_menu.clickEscOnKeyBoardToCloseDeleteWindow();
		
		// 9. Verify that selected recording isn't deleted.
		after_cancel_recording_list = record.getCourseRecordingList();
		
		after_cancel_recording_list.removeAll(initial_recording_list);
		
		if(after_cancel_recording_list.size() == 0) {
			System.out.println("Verify that selected recording isn't deleted.");
			ATUReports.add("Verify that selected recording isn't deleted.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Verify that selected recording is changed.");
			ATUReports.add("Verify that selected recording is changed.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		
		
		
		

	}
}
