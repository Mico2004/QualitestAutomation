package com.automation.main.delete_recordings;



import java.util.List;


import org.openqa.selenium.WebDriver;

import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.automation.main.page_helpers.ConfirmationMenu;
import com.automation.main.page_helpers.CopyMenu;
import com.automation.main.page_helpers.CoursesHelperPage;
import com.automation.main.page_helpers.DeleteMenu;
import com.automation.main.page_helpers.LoginHelperPage;
import com.automation.main.page_helpers.MoveWindow;
import com.automation.main.page_helpers.RecordingHelperPage;
import com.automation.main.utilities.DriverSelector;

import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;
import java.text.DateFormat;
import java.util.Date;

@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC15644CancelTheDelete {

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

		driver = DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());
		ATUReports.add("selected browser type", LogAs.PASSED, new CaptureScreen( ScreenshotOf.DESKTOP));

		
		tegrity = PageFactory.initElements(driver, LoginHelperPage.class);

		record = PageFactory.initElements(driver, RecordingHelperPage.class);
		copy = PageFactory.initElements(driver, CopyMenu.class);
		
		confirm_menu = PageFactory.initElements(driver, ConfirmationMenu.class);
		
		move_window = PageFactory.initElements(driver, MoveWindow.class);
		
		delete_menu = PageFactory.initElements(driver, DeleteMenu.class);
		
		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC15644CancelTheDelete at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC15644CancelTheDelete at " + DateToStr, "Starting the test: TC15644CancelTheDelete at " + DateToStr, LogAs.PASSED, null);	
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
		currentCourse = course.selectFirstCourse(record);
		System.out.println("Current course: " + currentCourse);
		//course.selectCourse(record);
		
		// 3. Select recording.
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
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
		
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);

	}
}
