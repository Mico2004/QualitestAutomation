package com.automation.main.move_recording;

//precondition student first course must have recordings in recordings tab as well as in student recordings tab



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
import com.automation.main.page_helpers.LoginHelperPage;
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
public class TC15602TryToMoveRecordingAsAStudent {

	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}

	public LoginHelperPage tegrity;
	public CoursesHelperPage course;
	public RecordingHelperPage record;
	public ConfirmationMenu confirm_menu;
	WebDriver driver;
	WebDriverWait wait;
	public static WebDriver thread_driver;
	CopyMenu copy;
	String current_course;
	String targetCourse;
	String clickedRecording;
	DesiredCapabilities capability;
	@BeforeClass
	public void setup() {
		
		driver = DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());
		
		ATUReports.add("selected browser type", LogAs.PASSED, new CaptureScreen( ScreenshotOf.DESKTOP));

		//ATUReports.setWebDriver(driver);
		//ATUReports.add("set driver", true);
		tegrity = PageFactory.initElements(driver, LoginHelperPage.class);

		record = PageFactory.initElements(driver, RecordingHelperPage.class);
		copy = PageFactory.initElements(driver, CopyMenu.class);
		
		confirm_menu = PageFactory.initElements(driver, ConfirmationMenu.class);
		
		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC15602TryToMoveRecordingAsAStudent at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC15602TryToMoveRecordingAsAStudent at " + DateToStr, "Starting the test: TC15602TryToMoveRecordingAsAStudent at " + DateToStr, LogAs.PASSED, null);	
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

	
	@Test(description = "TC 15602 Try To Move Recording As AStudent")
	public void test15602() throws InterruptedException
	{
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);

		// 1. Login as INSTRUCTOR.
		tegrity.loginCourses("User4");
		initializeCourseObject();
		
		// 2. Select course.
		current_course = course.selectCourseThatStartingWith("Ab");
		System.out.println("Current course: " + current_course);
		//course.selectCourse(record);
		
		// Verify that there recordings in the course
		int number_of_recordings_in_the_course = record.getCourseRecordingList().size();
				
		if(number_of_recordings_in_the_course > 0) {
			System.out.println("There is recordings in the course.");
			ATUReports.add("Recordings", "There is recordings in the course.", "There is recordings in the course.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("There is not recordings in the course.");
			ATUReports.add("Recordings", "There is recordings in the course.", "There is not recordings in the course.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		
		// 3. Select any recording.
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
		
		// 4. Click "Recording Tasks" button.
		record.recording_tasks_button.click();
		
		// 5. Verify that "Move" menu item is not displayed in "Recording Tasks" menu.
		boolean is_displayed = record.isMoveButtonDisplayedInRecordingTasks();
		
		if(!is_displayed) {
			System.out.println("Move button is not displayed in recording tasks menu");
			ATUReports.add("Verify that Move menu item is not displayed in Recording Tasks menu", "Move menu item is not displayed in Recording Tasks menu", "Move button is not displayed in recording tasks menu", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Move button is displayed in recording tasks menu");
			ATUReports.add("Verify that Move menu item is not displayed in Recording Tasks menu", "Move menu item is not displayed in Recording Tasks menu", "Move button is displayed in recording tasks menu", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		
		// Verify that Student recordings tab exist
		if(record.student_recordings_tab.isDisplayed()) {
			System.out.println("Student recordings tab is exist.");
			ATUReports.add("Student Recordings Tab.", "Exists.", "Exists.", LogAs.PASSED, null);
		} else {
			System.out.println("Student recordings tab is not exist.");
			ATUReports.add("Student Recordings Tab.", "Exists.", "Not exists.", LogAs.FAILED, null);
		}
		
		// 6. Click "Student Recordings" tab.
		record.clickOnStudentRecordingsTab();
		
		Thread.sleep(1000);
		
		// 7. Select any recording.
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
		
		// 8. Click "Recording Tasks" button.
		record.recording_tasks_button.click();
		
		// 9. Verify that "Move" menu item is not displayed in "Recording Tasks" menu
		record.isMoveButtonDisplayedInRecordingTasks();
		
		if(!is_displayed) {
			System.out.println("Move button is not displayed in recording tasks menu");
			ATUReports.add("Verify that Move menu item is not displayed in Recording Tasks menu", "Move menu item is not displayed in Recording Tasks menu", "Move button is not displayed in recording tasks menu", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Move button is displayed in recording tasks menu");
			ATUReports.add("Verify that Move menu item is not displayed in Recording Tasks menu", "Move menu item is not displayed in Recording Tasks menu", "Move button is displayed in recording tasks menu", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
	}
}