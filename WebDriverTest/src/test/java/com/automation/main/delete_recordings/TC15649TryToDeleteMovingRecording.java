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

import java.text.DateFormat;
import java.util.Date;
import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;


@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC15649TryToDeleteMovingRecording {

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
	String current_course;
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
		 System.out.println("Starting the test: TC15649TryToDeleteMovingRecording at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC15649TryToDeleteMovingRecording at " + DateToStr, "Starting the test: TC15649TryToDeleteMovingRecording at " + DateToStr, LogAs.PASSED, null);	
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

	
	@Test(description = "TC 15649 Try To Delete Moving Recording")
	public void test15649() throws InterruptedException
	{
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);

		// 1. Login as INSTRUCTOR.
		tegrity.loginCourses("User1");// log in courses page
		initializeCourseObject();
		
		// save destination course name
		String destination_course_name = course.getCourseInIndex(2);				
		System.out.println("Destination course: " + destination_course_name);
		
		// 2. Select course.
		current_course = course.selectFirstCourse(record);
		System.out.println("Source course: " + current_course);
		
		// 3. Select recording.
		String selected_recording_name = record.getFirstRecordingTitle();
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
		System.out.println("Selected recording name: " + selected_recording_name);
		
		// 4. Select "Recording Tasks -> Move" menu item.
		record.clickOnRecordingTaskThenMove();
		
		// 5. Select destination course.
		copy.selectTargetCourseFromCourseList(destination_course_name);

		// 6. Click "Move Recording(s)" button.
		move_window.clickOnMoveRecordings();
		
		// 7. Message box "Recording has been queued for move" is displayed.
		// 8. Click "OK" button.
		confirm_menu.clickOnOkButtonAfterConfirmMoveRecording();
		

		// 9. Message box is closed.
		boolean is_closed = confirm_menu.isConfirmationMenuClosed();
		
		if (is_closed) {
			System.out.println("Message box is closed");
			ATUReports.add("Message box is closed", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Message box is not closed");
			ATUReports.add("Message box is not closed", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		
		// 10. "Move" window is closed.
		is_closed = move_window.isMoveMenuClosed();
		
		if (is_closed) {
			System.out.println("Move window is closed");
			ATUReports.add("Move window is closed", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Move window is not closed");
			ATUReports.add("Move window is not closed", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		
		// 11. The source recording has status "Being copying from".
		record.checkRecordingInIndexIStatus(1, "Being moved from");
		
		// 12. While recording is being moved, select "Recording Tasks -> Delete" menu item.
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
		record.clickOnRecordingTaskThenDelete();
			
		// 13. Message box "Cannot copy in-process or failed recordings" is displayed.
		// 14. Click "OK" button.
		confirm_menu.clickOnOkButtonAfterCouldNotBeDeleted();
		
		
		// 15. Message box is closed.
		confirm_menu.isConfirmationMenuClosed();
		
		record.checkStatusExistenceForMaxTTime(360);
		
		// 16. Click "Courses" link in the breadcrumbs.
		record.returnToCourseListPage();
		
		// 17. Select destination course.
		course.clickOnTargetCourseName(destination_course_name);
		
		// 18. Verify that recording is copied successfully.
		List<String> target_course_recording_list = record.getCourseRecordingList();
		
		int i = 0;
		boolean is_recording_found = false;
		for(String recording : target_course_recording_list) {
			i++;
			if(recording.equals(selected_recording_name)) {
				is_recording_found = true;
				break;
			}
		}
		
		// 18.1. Recording is displayed in "Recordings" tab
		if(is_recording_found) {
			System.out.println("Recording is displayed in recordings tab");
			ATUReports.add("Recording is displayed in recordings tab", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Recording is not displayed in recordings tab");
			ATUReports.add("Recording is not displayed in recordings tab", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		
		// 18.2. Recording doesn't have a "Failed" status
		record.checkThatRecordingStatusTargetIndexIsNotXWithTimeout(i, "Failed", 1);
		
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
	}
}
