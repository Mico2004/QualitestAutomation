package com.automation.main.delete_recordings;



import java.util.ArrayList;
import java.util.HashSet;
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
public class TC15642DeleteSeveralRecordings {

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

		
		//ATUReports.add("set driver", true);
		tegrity = PageFactory.initElements(driver, LoginHelperPage.class);

		record = PageFactory.initElements(driver, RecordingHelperPage.class);
		copy = PageFactory.initElements(driver, CopyMenu.class);
		
		confirm_menu = PageFactory.initElements(driver, ConfirmationMenu.class);
		
		move_window = PageFactory.initElements(driver, MoveWindow.class);
		
		delete_menu = PageFactory.initElements(driver, DeleteMenu.class);
		
		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC15642DeleteSeveralRecordings at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC15642DeleteSeveralRecordings at " + DateToStr, "Starting the test: TC15642DeleteSeveralRecordings at " + DateToStr, LogAs.PASSED, null);	
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

	
	@Test(description = "TC 15642 Delete Several Recordings")
	public void test15642() throws InterruptedException
	{
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);

		// 1. Login as INSTRUCTOR.
		tegrity.loginCourses("User1");// log in courses page
		initializeCourseObject();
		
		// 2. Select course.
		currentCourse = course.selectFirstCourse(record);
		System.out.println("Current course: " + currentCourse);
		//course.selectCourse(record);
		
		// 3. Select several recordings.
		List<String> initial_recording_list = record.getCourseRecordingList();
		
		//4 verify that we have some record before we start the test
		if(initial_recording_list.isEmpty() || initial_recording_list.size() ==1 ) {
			
			
			// 4.1 signOut from the user and enter to the superuser and move and move to the band
			tegrity.signOut();
			tegrity.loginCourses("SuperUser");
			course.selectCourseThatStartingWith("BankValid");
			
			//  4.2 select the two first records
			record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
			record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox2);
			
			
			// 4.3 copy record from the bank to the target course	
			record.copyRecordsToAnotherCourse(currentCourse);
			confirm_menu.clickOnOkButton();
			record.checkStatusExistenceForMaxTTime(120);
			
			// 4.4 return to the user
			tegrity.signOut();
			tegrity.loginCourses("User1");// log in courses page
			
			// 4.5  Select course.
			currentCourse = course.selectFirstCourse(record);
			System.out.println("Current course: " + currentCourse);
			initial_recording_list = record.getCourseRecordingList();
		}
		
		
		// if not we check all the course and than we delete them
		record.checkAllCheckBox();
		
		
		// 4. Select "Recording Tasks -> Delete".
		record.clickOnRecordingTaskThenDelete();
		
	
		// 5. Verify that only selected recording displayed in "List of Recordings".
		List<String> delete_recording_list = delete_menu.getRecordingList();
		
		List<String> compare_recording_list = new ArrayList<String>(); 
		
		for(String recording: delete_recording_list) {
			compare_recording_list.add(recording.replaceAll(" ", ""));
		}
		
		for(String recording: initial_recording_list) {
			try {
				compare_recording_list.remove(recording.replaceAll(" ", ""));
			} catch (Exception msg) {
				System.out.println("Verified that not selected same recordings displayed in list of recordings.");
				ATUReports.add("Verify that all selected recordings are displayed in List of Recordings", "All of selected recordings are dipslayed in List of Recordings", "Not all of selected recordings are dipslayed in List of Recordings", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Assert.assertTrue(false);
			}
			
		}
		
		if(compare_recording_list.size() == 0) {
			System.out.println("Verified that only selected recordings displayed in list of recordings.");
			ATUReports.add("Verify that all selected recordings are displayed in List of Recordings", "All of selected recordings are dipslayed in List of Recordings", " All of selected recordings are Not dipslayed in List of Recordings", LogAs.PASSED, null);
			Assert.assertTrue(true);
  		} else {
			System.out.println("Delete recordings list not same number of recordings as in course recording list.");
			ATUReports.add("Verify that all selected recordings are displayed in List of Recordings", "All of selected recordings are dipslayed in List of Recordings", "Delete recordings list not same number of recordings as in course recording list.", LogAs.FAILED,new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
		
		// 6. Click "Delete" button
		delete_menu.clickOnDeleteButton();
		
		// 7. "Delete" window is closed.
		boolean is_delete_window_closed = delete_menu.isDeleteMenuClose();
	
		if(is_delete_window_closed) {
			System.out.println("Delete window is closed.");
			ATUReports.add("Delete window is closed", "Success", "Success", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Delete window not closed.");
			ATUReports.add("Delete window is closed", "Success", "Fail", LogAs.FAILED,new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
		
		// 8. Verify that selected recording is deleted.
		List <String> current_recording_list = record.getCourseRecordingList();
		
		if(current_recording_list.size() != 0) {
			System.out.println("Verified that selected recordings is not deleted.");
			ATUReports.add("Verified that selected recordings is deleted", "Success", "Fail", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		} else {
			System.out.println("Verified that selected recordings is deleted.");
			ATUReports.add("Verified that selected recordings is deleted", "Success", "Success", LogAs.PASSED, null);
			Assert.assertTrue(true);
		}
		
		
		//after test to add records
		tegrity.signOut();
		tegrity.loginCourses("SuperUser");// log in courses page
		course.copyRecordingFromCourseStartWithToCourseStartWithOfType("BankValidRecording", "Ab", 0, record, copy,confirm_menu);
		course.selectCourseThatStartingWith("Ab");
		record.checkStatusExistenceForMaxTTime(540);
		
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
		
	}
}
