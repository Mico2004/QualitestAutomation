package com.automation.main;


import java.awt.AWTException;
import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.List;

import org.junit.AfterClass;
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
public class TC15552CopySeveralRecordings {

	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}

	public LoginHelperPage tegrity;
	public CoursesHelperPage course;
	public RecordingHelperPage record;
	public ConfirmationMenu confirm_menu;
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

		driver = DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());
		ATUReports.add("selected browser type", LogAs.PASSED, new CaptureScreen( ScreenshotOf.DESKTOP));
//		driver.manage().window().maximize();
		//ATUReports.setWebDriver(driver);
		//ATUReports.add("set driver", true);
		tegrity = PageFactory.initElements(driver, LoginHelperPage.class);

		record = PageFactory.initElements(driver, RecordingHelperPage.class);
		copy = PageFactory.initElements(driver, CopyMenu.class);
		delete_menu = PageFactory.initElements(driver, DeleteMenu.class);
		
		confirm_menu = PageFactory.initElements(driver, ConfirmationMenu.class);
	
	}
	
	
	@AfterTest
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
		
		Thread.sleep(3000);
		
		
		// 2. Select abc course.
		targetCourse = course.selectCourseThatStartingWith("abc");
		System.out.println("First course clicked: " + targetCourse);
		
		Thread.sleep(3000);
		
		// 3. Get abc course recording list.
		int number_of_recordings_in_target_course = record.getNumberOfRecordings();
		
		System.out.println(number_of_recordings_in_target_course);		
		
		
		if (number_of_recordings_in_target_course == 0) {
			System.out.println("There is no recordings in target course.");
			ATUReports.add("Is recordings in course", "There is no recordings in target course.", "There is no recordings in target course.", LogAs.PASSED, null);
		} else {
			System.out.println("There is recordings in target course, then it will delete them all.");
			ATUReports.add("Is recordings in course", "There is recordings in target course, then it will delete them all.", "There is recordings in target course, then it will delete them all.", LogAs.PASSED, null);
			
			record.checkAllCheckBox();
			record.clickOnRecordingTaskThenDelete();
			Thread.sleep(2000);
			delete_menu.clickOnDeleteButton();
			Thread.sleep(2000);
		}
		
		// 4. Go back to courses list.
		record.returnToCourseListPage();
		
		
		// 5. Select Ab course.
		currentCourse = course.selectCourseThatStartingWith("Ab");
		System.out.println("Current course: " + currentCourse);
				
				
		// 6. Select all recordings.
		List<String> initial_copied_course_recordings_list_before_coping = record.checkAllCheckBox();
		
		// Checking that there are at least two recordings in the course
		record.verifyThatThereAreAtLeastTwoRecordings();
		
		System.out.println("Initail - copying from course recording list selected:");
		for(int i = 0; i < initial_copied_course_recordings_list_before_coping.size(); i++)
		{
			System.out.println(i + ". - " + initial_copied_course_recordings_list_before_coping.get(i));
		}
				
		// 7. Select "Recording Tasks -> Copy" menu item.
		record.clickOnRecordingTaskThenCopy();
		
		
		Thread.sleep(3000);
		
		// 8. Select destination course.
		boolean is_target_course_selected = copy.selectTargetCourseFromCourseList(targetCourse);
		if (is_target_course_selected) {
			System.out.println("Target course selected: " + targetCourse);
		} else {
			System.out.println("Target course not selected: " + targetCourse);
		}
		
		Thread.sleep(2000);
				
		// 9. Click "Copy Recording(s)" button.
		copy.clickOnCopyButton();
		
		Thread.sleep(3000);
				
		// 10. Click "OK" button.
		confirm_menu.clickOnOkButtonAfterConfirmCopyRecordings();
		
		// 11. Message window is closed.
		boolean is_closed = confirm_menu.isConfirmationMenuClosed();
		
		if (is_closed) {
			System.out.println("Message window is closed.");
			ATUReports.add("Message window.", "Message window is closed.", "Message window is closed.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Message window is not closed.");
			ATUReports.add("Message window.", "Message window is closed.", "Message window is not closed.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		
		// 12. Copy window is closed.
		is_closed = copy.isCopyMenuClosed();
			
		if (is_closed) {
			System.out.println("Copy window is closed.");
			ATUReports.add("Copy window.", "Copy window is closed.", "Copy window is closed.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Copy window is not closed.");
			ATUReports.add("Copy window.", "Copy window is closed.", "Copy window is not closed.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		
		
		// 13. Recording's status change when the copying started
		List<String> source_recording_list = record.getCourseRecordingList(); 
		
		for(int i = 0; i < source_recording_list.size(); i++) {
			record.checkRecordingInIndexIStatus((i + 1), "Being copied from");
		}
		
		// 14. Recording's status change after the copying is done
		record.checkStatusExistenceForMaxTTime(180);
				
			
		// 15. Get first course recording list.
		List<String> copied_course_recordings_list_before_coping = record.getCourseRecordingList();
		
//		System.out.println(initial_copied_course_recordings_list_before_coping.size());
//		System.out.println(copied_course_recordings_list_before_coping.size());
		
		copied_course_recordings_list_before_coping.removeAll(initial_copied_course_recordings_list_before_coping);
		
		if (copied_course_recordings_list_before_coping.size() == 0) {
			System.out.println("Verified that all recordings exists after the copying.");
			ATUReports.add("Verify recordings", "Verified that all recordings exists after the copying.", "Verified that all recordings exists after the copying.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			
			ATUReports.add("Verify recordings", "Verified that all recordings exists after the copying.", "Verified that not all recordings exists after the copying.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		
		// 16. Go back to courses list.
		record.returnToCourseListPage();
				
		Thread.sleep(3000);
		
		// 17. Select abc course.
		targetCourse = course.selectCourseThatStartingWith("abc");
		System.out.println("target course clicked: " + targetCourse);
		
		Thread.sleep(3000);
		
		// 18. Get first course recording list after copying.
		List<String> after_copying_target_course_recordings_list = record.getCourseRecordingList();
				
		if (after_copying_target_course_recordings_list.containsAll(initial_copied_course_recordings_list_before_coping)) {
			System.out.println("Verified that all recordings exists after the copying.");
			ATUReports.add("Verify recordings.", "Verified that all recordings exists after the copying.", "Verified that all recordings exists after the copying.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Verified that not all recordings exists after the copying.");
			ATUReports.add("Verify recordings.", "Verified that all recordings exists after the copying.", "Verified that not all recordings exists after the copying.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		
		// 19. Check that all recordings is clickable
		record.checkIfAllRecordingsClickable();
		
//		this.driver.quit();
		
	}
}
