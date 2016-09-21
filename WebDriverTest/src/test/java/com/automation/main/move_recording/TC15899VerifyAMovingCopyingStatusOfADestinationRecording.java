package com.automation.main.move_recording;

// Precondition: first recording in second course must be long duration


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

@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC15899VerifyAMovingCopyingStatusOfADestinationRecording {

	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}

	public LoginHelperPage tegrity;
	public DeleteMenu delete_menu;
	public CoursesHelperPage course;
	public RecordingHelperPage record;
	public ConfirmationMenu confirm_menu;
	public MoveWindow move_window;
	WebDriver driver;
	WebDriverWait wait;
	public static WebDriver thread_driver;
	CopyMenu copy;
	String current_course;
	String target_course;
	String clickedRecording;
    DesiredCapabilities capability;
	@BeforeClass
	public void setup() {

		driver = DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());
		
		//ATUReports.setWebDriver(driver);
		//ATUReports.add("set driver", true);
		tegrity = PageFactory.initElements(driver, LoginHelperPage.class);

		record = PageFactory.initElements(driver, RecordingHelperPage.class);
		copy = PageFactory.initElements(driver, CopyMenu.class);
		
		confirm_menu = PageFactory.initElements(driver, ConfirmationMenu.class);
		
		move_window = PageFactory.initElements(driver, MoveWindow.class);
		
		delete_menu = PageFactory.initElements(driver, DeleteMenu.class);
		
		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC15899VerifyAMovingCopyingStatusOfADestinationRecording at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC15899VerifyAMovingCopyingStatusOfADestinationRecording at " + DateToStr,
		 "Starting the test: TC15899VerifyAMovingCopyingStatusOfADestinationRecording at " + DateToStr, LogAs.PASSED, null);
	}
	
	
	@AfterClass
	public void closeBroswer() {
		this.driver.quit();
	}

	
	// description = "get courses list"
	public void initializeCourseObject() throws InterruptedException {

		course = PageFactory.initElements(driver, CoursesHelperPage.class);
		course.courses = course.getStringFromElement(course.course_list);
	}

	
	@Test(description = "TC 15899 Verify A Moving Copying Status Of A Destination Recording")
	public void test15899() throws Exception
	{
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);

		// 1. Login as INSTRUCTOR.
		tegrity.loginCourses("User1");// log in courses page
		initializeCourseObject();
		
		
		// Pretest
		target_course = course.selectCourseThatStartingWith("abc");
		record.deleteAllRecordings(delete_menu);
		record.returnToCourseListPage();
		
		// 2. Select course.
		current_course = course.selectCourseThatStartingWith("Ab");
		System.out.println("Current course: " + current_course);
		//course.selectCourse(record);
		
		// 3. Select recording.
		String selected_recording_name = record.getFirstRecordingTitle();
		System.out.println("Record to select: " + selected_recording_name);
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
		
		// 4. Select "Recording Tasks -> Move"
		record.clickOnRecordingTaskThenMove();
		
		Thread.sleep(2000);
		
		// 5. Select destination course.
		copy.selectTargetCourseFromCourseList(target_course);
		
		// 6. Click "Move Recording(s)".
		move_window.clickOnMoveRecordings();
		
		// 7. Click "OK" button.
		confirm_menu.clickOnOkButtonAfterConfirmMoveRecording();
		
		// 7.1. Message is closed.
		boolean is_closed = confirm_menu.isConfirmationMenuClosed();
		
		if(is_closed) {
			System.out.println("Message is closed.");
			ATUReports.add("Message is closed.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Message is not closed.");
			ATUReports.add("Message is not closed.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		
		// 7.2. "Copy" window is closed.
		is_closed = copy.isCopyMenuClosed();
		
		if(is_closed) {
			System.out.println("Copy window is closed.");
			ATUReports.add("Copy window is closed.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Copy window is not closed.");
			ATUReports.add("Copy window is not closed.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		
		// 7.3. The source recording has status "Being copying from".
		record.checkRecordingInIndexIStatus(1, "Being moved from");
		
		// 8. While recording is being copied, click the "Courses" link at the breadcrumbs.
		record.returnToCourseListPage();
		
		Thread.sleep(800);
		
		// 9. Select destination course.
		course.selectTargetCourse(target_course);
		
		// 10. Verify that destination recording has a "Moving/Copying" status.
		// 10.1. Recr`row is grayed out.
		// 10.2. Recording has a "Moving/Copying" status.	
		List<String> current_recording_list = record.getCourseRecordingList();
		boolean is_found = false;
		for(int i = 0; i < current_recording_list.size(); i++) {
			if(current_recording_list.get(i).equals(selected_recording_name)) {
				is_found = true;
				record.checkRecordingInIndexIStatus(i + 1, "Moving/Copying");
				if(!record.isIndexRecordingClickable(i + 1)) {
					System.out.println("Recording is grayed out");
					ATUReports.add("Recording is grayed out", LogAs.PASSED, null);
					Assert.assertTrue(true);
				} else {
					System.out.println("Recording is not grayed out");
					ATUReports.add("Recording is not grayed out", LogAs.FAILED, null);
					Assert.assertTrue(false);
				}
	
			}
		}
		
		if(is_found == false) {
			System.out.println("Recording is not found on destination course.");
			ATUReports.add("Recording is not found on destination course.", "Recording is found", "Recording is not found", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);

		
	}
}
