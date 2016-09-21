package com.automation.main.admin_course_view_recording_tasks;



import java.util.List;

import org.omg.Messaging.SyncScopeHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.automation.main.page_helpers.AdminDashboardPage;
import com.automation.main.page_helpers.AdminDashboardViewCourseList;
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
import atu.testng.reports.utils.Utils;
import java.text.DateFormat;
import java.util.Date;


@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC22006ValidateMoveRecordingCancelFunctionality {

	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}

	public LoginHelperPage tegrity;
	public CoursesHelperPage course;
	public RecordingHelperPage record;
	public DeleteMenu delete_menu;
	public MoveWindow move_window;
	public AdminDashboardPage admin_dashboard_page;
	public AdminDashboardViewCourseList admin_dashboard_view_course_list;
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
		//
		//ATUReports.setWebDriver(driver);
		//ATUReports.add("set driver", true);
		tegrity = PageFactory.initElements(driver, LoginHelperPage.class);

		record = PageFactory.initElements(driver, RecordingHelperPage.class);
		copy = PageFactory.initElements(driver, CopyMenu.class);
		delete_menu = PageFactory.initElements(driver, DeleteMenu.class);
		admin_dashboard_page = PageFactory.initElements(driver, AdminDashboardPage.class);
		admin_dashboard_view_course_list = PageFactory.initElements(driver, AdminDashboardViewCourseList.class);
		move_window = PageFactory.initElements(driver, MoveWindow.class);
		
		wait = new WebDriverWait(driver, 30);
		

		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC22006ValidateMoveRecordingCancelFunctionality at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC22006ValidateMoveRecordingCancelFunctionality at " + DateToStr,
		 "Starting the test: TC22006ValidateMoveRecordingCancelFunctionality at " + DateToStr, LogAs.PASSED, null);
		
	}
	
	@AfterClass
	public void closeBroswer() {
		driver.quit();
	}


	
	// description = "get courses list"
	public void initializeCourseObject() throws InterruptedException {

		course = PageFactory.initElements(driver, CoursesHelperPage.class);
		course.courses = course.getStringFromElement(course.course_list);
	}

	
	@Test(description = "TC 22006 Validate Move Recording Cancel Functionality")
	public void test22006() throws InterruptedException//
	{
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
		// 1. Login as User1.
		tegrity.loginCourses("User1");// log in courses page
		initializeCourseObject();
		
		// 2. Get the full name of the Ab course.
		String target_course_name = course.selectCourseThatStartingWith("Ab");
		String url =  course.getCurrentUrlCoursePage(); 
		System.out.println("Target course name for this test is: " + target_course_name);
		ATUReports.add("Target course name for this test is: "+ target_course_name, LogAs.PASSED, null);
		
		// 3. Logout.
		record.signOut();
		
			
		// 4. Login as Full Admin
		tegrity.loginAdmin("Admin");
		Thread.sleep(3000);
			
			
		// 5. Click on "view course list" under "courses" section.
		admin_dashboard_page.clickOnTargetSubmenuCourses("View Course List");
		Thread.sleep(3000);
			
		// 6. move to the course through url
		Thread.sleep(5000);
		admin_dashboard_view_course_list.moveToCoursesThroughGet(url);	
		Thread.sleep(1000);
		
		// 8. Click on a checkbox of one recording.
		record.getCheckbox().click();
		String checked_recording_title = record.getFirstRecordingTitle();
			
		// 9. Hover over "Recording tasks" menu.
		// 10. The menu items are displayed.
		// 11. Click on the menu item "Move".
		record.clickOnRecordingTaskThenMove();
		Thread.sleep(3000);
		
		// The move window displays.
		boolean is_move_window_closed = move_window.isMoveMenuClosed();
		
		if(!is_move_window_closed) {
			System.out.println("Move window is opened.");
			ATUReports.add("Move window.", "Open.", "Open.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Move window is closed.");
			ATUReports.add("Move window.", "Open.", "Closed.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		
		// 13. On the move window, click on "cancel" button.
		move_window.clickOnCancelButton();
		
		// 14. The move window is closed.
		is_move_window_closed = move_window.isMoveMenuClosed();
		
		if(is_move_window_closed) {
			System.out.println("Move window is closed.");
			ATUReports.add("Move window.", "Closed.", "Closed.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Move window is opened.");
			ATUReports.add("Move window.", "Closed.", "Open.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		
		// 15. The recording we have checked is at its placed (it's status didnt change and it didnt move).
		List<String> recording_list = record.getCourseRecordingList();
		
		boolean is_found = false;
		for(int i=0; i<recording_list.size(); i++) {
			if(recording_list.get(i).equals(checked_recording_title)) {
				is_found = true;
				record.checkThatRecordingStatusTargetIndexIsNotXWithTimeout(i+1, "Error", 5);
				break;
			}
		}
		
		if(is_found) {
			System.out.println("Recording is at its placed.");
			ATUReports.add("Recording is in its place.", "Correct.", "Correct.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Recording is not found in it place.");
			ATUReports.add("Recording is in its place.", "Correct.", "Uncorrect.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		
		
		// 16. Hover over "Recording tasks" menu.
		// 17. Click on the menu item "Move".
		record.clickOnRecordingTaskThenMove();
		Thread.sleep(3000);
		
		
		// 18. The move window displays.
		is_move_window_closed = move_window.isMoveMenuClosed();
		
		if(!is_move_window_closed) {
			System.out.println("Move window is opened.");
			ATUReports.add("Move window.", "Open.", "Open.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Move window is closed.");
			ATUReports.add("Move window.", "Open.", "Closed.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		
		// 19. When the move window displays, click on any ESC.
		driver.findElement(By.id("members_value")).sendKeys(Keys.ESCAPE);
		
		Thread.sleep(1000);
		
		// 20. The move window is closed.
		is_move_window_closed = move_window.isMoveMenuClosed();
		
		if(is_move_window_closed) {
			System.out.println("Move window is closed.");
			ATUReports.add("Move window.", "Closed.", "Closed.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Move window is opened.");
			ATUReports.add("Move window.", "Closed.", "Open.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		
		// 21. The recording we have checked remained in the course.
		// 22. The recording doesn't have an 'Error' status.
		// 23. The recording link is enabled (Not greyed out and clickable). ***
		recording_list = record.getCourseRecordingList();
		
		is_found = false;
		for(int i=0; i<recording_list.size(); i++) {
			if(recording_list.get(i).equals(checked_recording_title)) {
				is_found = true;
				record.checkThatRecordingStatusTargetIndexIsNotXWithTimeout(i+1, "Error", 5);
				record.clickOnRecordingTitleInIndex(i+1);
				break;
			}
		}
		
		if(is_found) {
			System.out.println("Recording is at its placed.");
			ATUReports.add("Recording is in its place.", "Correct.", "Correct.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Recording is not found in it place.");
			ATUReports.add("Recording is in its place.", "Correct.", "Uncorrect.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}

		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
		
		
	}

}
