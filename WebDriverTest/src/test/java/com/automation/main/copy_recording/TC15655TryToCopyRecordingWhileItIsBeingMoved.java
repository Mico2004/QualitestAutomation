package com.automation.main.copy_recording;



import java.util.List;


import org.openqa.selenium.WebDriver;

import org.openqa.selenium.remote.DesiredCapabilities;

import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
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
import com.automation.main.page_helpers.TopBarHelper;
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
public class TC15655TryToCopyRecordingWhileItIsBeingMoved {

	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}

	public TopBarHelper top_bar_helper;
	public LoginHelperPage tegrity;
	public CoursesHelperPage course;
	public RecordingHelperPage record;
	public ConfirmationMenu confirm_menu;
	public DeleteMenu delete_menu;
	public MoveWindow move_window;
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

		
//		System.setProperty("webdriver.ie.driver", "src/test/resources/IEDriverServer.exe");
//			capability=DesiredCapabilities.internetExplorer();
//			capability.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING,true);
//			
//		driver=new InternetExplorerDriver(capability);
//		ATUReports.add("selected browser type", LogAs.PASSED, new CaptureScreen( ScreenshotOf.DESKTOP));
		driver = DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());
		ATUReports.add("selected browser type", LogAs.PASSED, new CaptureScreen( ScreenshotOf.DESKTOP));

		
		//ATUReports.setWebDriver(driver);
		//ATUReports.add("set driver", true);
		tegrity = PageFactory.initElements(driver, LoginHelperPage.class);

		record = PageFactory.initElements(driver, RecordingHelperPage.class);
		copy = PageFactory.initElements(driver, CopyMenu.class);
		
		confirm_menu = PageFactory.initElements(driver, ConfirmationMenu.class);
		move_window = PageFactory.initElements(driver, MoveWindow.class);
		delete_menu = PageFactory.initElements(driver, DeleteMenu.class);
		top_bar_helper = PageFactory.initElements(driver, TopBarHelper.class);
		
		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC15655TryToCopyRecordingWhileItIsBeingMoved at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC15655TryToCopyRecordingWhileItIsBeingMoved at " + DateToStr, "Starting the test: TC15655TryToCopyRecordingWhileItIsBeingMoved at " + DateToStr, LogAs.PASSED, null);	
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

	
	@Test(description = "TC 15655 Try To Copy Recording While It Is Being Moved")
	public void test15655() throws InterruptedException
	{
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);

		// 1. Login as INSTRUCTOR.
		tegrity.loginCourses("User1");// log in courses page
		initializeCourseObject();
		
		// Preset login and delete all recording in Ba, Then copy one recording from Ab to Ba.
		course.deleteAllRecordingsInCourseStartWith("Ba", 0, record, delete_menu);
		Thread.sleep(3000);
		course.copyOneRecordingFromCourseStartWithToCourseStartWithOfType("Ab", "Ba", 0, record, copy, confirm_menu);
		course.verifyRecordingsStatusIsClear("Ab", 0,record);
		
		
		// Pretest go to first and second target and delete all recordings
		String destination_course_name = course.selectCourseThatStartingWith("abc");
		record.deleteAllRecordings(delete_menu);
		record.returnToCourseListPage();
		
		
		// 2. Select course.
		current_course = course.selectCourseThatStartingWith("Ba");
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
		confirm_menu.verifyConfirmWindowIsClosed();

		// 10. "Move" window is closed.
		boolean is_closed = move_window.isMoveMenuClosed();
		
		if (is_closed) {
			System.out.println("Move window is closed");
			ATUReports.add("Move window is closed", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Move window is not closed");
			ATUReports.add("Move window is not closed", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		
		// 11. The source recording has status "Being moved from".
		record.verifyTargetRecordingHaveTargetStatus(selected_recording_name, "Being moved from");
		
		// 12. While recording has a "Being moved from", select "Recording Tasks -> Copy" menu item.
		record.selectTargetRecordingCheckbox(selected_recording_name);
		record.clickOnRecordingTaskThenCopy();
	
		// 13. Message box "Cannot copy in-process or failed recordings" is displayed.
		// 14. Click "OK" button.
		confirm_menu.clickOnOkButtonAfterCannotCopyInProcessOrFailRecordings();
		
		// 15. Message box is closed.
		confirm_menu.isConfirmationMenuClosed();
		
		record.waitUntilFirstRecordingBeingCopiedFromStatusDissaper();
		
		// 16. Click "Courses" link in the breadcrumbs.
		record.returnToCourseListPage();
		
		// 17. Select destination course.
		course.selectCourseThatStartingWith(destination_course_name);
		
		// 18. Verify that recording is copied successfully.
		
		record.verifyRecordingDisplayedCorrectly(selected_recording_name);
	
		// 18.2. The recording finished moving - has this status "IE, FF, Safari Ready" or none at all
		record.verifyNoStatusInTheIndex(1);
	
		
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
		
	}
}
