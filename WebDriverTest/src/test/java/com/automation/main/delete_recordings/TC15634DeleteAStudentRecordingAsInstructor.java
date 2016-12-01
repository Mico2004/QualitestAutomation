package com.automation.main.delete_recordings;



import java.util.List;

import org.openqa.selenium.WebDriver;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
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

import java.text.DateFormat;
import java.util.Date;
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
public class TC15634DeleteAStudentRecordingAsInstructor {

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
		 System.out.println("Starting the test: TC15634DeleteAStudentRecordingAsInstructor at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC15634DeleteAStudentRecordingAsInstructor at " + DateToStr, "Starting the test: TC15634DeleteAStudentRecordingAsInstructor at " + DateToStr, LogAs.PASSED, null);	
	}
	
	
	@AfterClass
	public void closeBroswer() {
		this.driver.quit();
	}

	
	// description = "get courses list"
	public void initializeCourseObject() throws InterruptedException {

		course = PageFactory.initElements(driver, CoursesHelperPage.class);
		course.courses = course.getCoursesListFromElement(course.course_list);
	}

	
	@Test(description = "TC 15634 Delete A Student Recording As Instructor")
	public void test15634() throws InterruptedException
	{
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);

		// 1. Login as INSTRUCTOR.
		tegrity.loginCourses("User1");// log in courses page
		initializeCourseObject();
		
		// 2. Select course.
		currentCourse = course.selectFirstCourse(record);
		System.out.println("Current course: " + currentCourse);
		
		// 3. Click on "Student Recordings" tab.
		record.clickOnStudentRecordingsTab();
				
		// 4. Select recording to delete.
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
		String selected_recording_title = record.getFirstRecordingTitle();
		//List<String> initial_recording_list = record.getCourseRecordingList();
		
		// 5. Select "Recording Tasks -> Delete".
		record.clickOnRecordingTaskThenDelete();
		
	
		// 6. Verify that only selected recording displayed in "List of Recordings".
		List<String> delete_recording_list = delete_menu.getRecordingList();
		
		if(delete_recording_list.size() == 1) {
			if(delete_recording_list.contains(selected_recording_title)) {
				System.out.println("Verified that only selected recording displayed in list of recordings.");
				ATUReports.add("Verified that only selected recording displayed in list of recordings.", LogAs.PASSED, null);
				Assert.assertTrue(true);
			} else {
				System.out.println("Verified that not selected recording displayed in list of recordings.");
				ATUReports.add("Verified that not selected recording displayed in list of recordings.",selected_recording_title ,LogAs.WARNING, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				//Assert.assertTrue(false);
			}
  		} else {
			System.out.println("Delete recording list not contain just one recording. It contains: " + delete_recording_list.size());
			ATUReports.add("Delete recording list not contain just one recording. It contains: " + delete_recording_list.size(), LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
		
		// 7. Click "Delete" button
		delete_menu.clickOnDeleteButton();
		
		// 8. "Delete" window is closed.
		delete_menu.verifyDeleteWindowClosed();
		
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
		
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);

	}
}
