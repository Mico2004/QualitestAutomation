package com.automation.main.other;


import java.awt.AWTException;
import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.List;

import javax.swing.text.StyledEditorKit.StyledTextAction;

import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;import com.automation.main.page_helpers.Page;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.automation.main.page_helpers.ConfirmationMenu;
import com.automation.main.page_helpers.CopyMenu;
import com.automation.main.page_helpers.CoursesHelperPage;
import com.automation.main.page_helpers.LoginHelperPage;
import com.automation.main.page_helpers.RecordingHelperPage;

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
public class TestCaseCopyOneStudentRecording {

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
	String currentCourse;
	String targetCourse;
	String clickedRecording;
    DesiredCapabilities capability;
	@BeforeClass
	public void setup() {

		
		System.setProperty("webdriver.ie.driver", "src/test/resources/IEDriverServer.exe");
			capability=DesiredCapabilities.internetExplorer();
			capability.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING,false);
			
		driver=new InternetExplorerDriver(capability);
		ATUReports.add("selected browser type", LogAs.PASSED, new CaptureScreen( ScreenshotOf.DESKTOP));

		
		//ATUReports.setWebDriver(driver);
		//ATUReports.add("set driver", true);
		tegrity = PageFactory.initElements(driver, LoginHelperPage.class);

		record = PageFactory.initElements(driver, RecordingHelperPage.class);
		copy = PageFactory.initElements(driver, CopyMenu.class);
		
		confirm_menu = PageFactory.initElements(driver, ConfirmationMenu.class);
	}
	
	
//	@AfterTest
//	public void closeBroswer() {
//		this.driver.quit();
//	}


	// @Parameters({"web","title"}) in the future
	@Test
	public void loadPage() throws InterruptedException {
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);

	}
	
	// description = "get courses list"
	public void initializeCourseObject() throws InterruptedException {

		course = PageFactory.initElements(driver, CoursesHelperPage.class);
		course.courses = course.getCoursesListFromElement(course.course_list);
	}

	
	@Test(dependsOnMethods = "loadPage", description = "Login course page")
	public void loginCourses() throws InterruptedException
	{
		// 1. Login as INSTRUCTOR.
		tegrity.loginCourses("Instructor");// log in courses page
		initializeCourseObject();
		
		// Get list of all courses that user is instructor
		List<String> course_list_for_which_user_is_instructor = course.getAllCoursesForWhichTheUserIsInstructor(record);
		
		String target_course_name = course.getCourseInIndex(1);
		
		// 2. Select course.
		currentCourse = course.selectSecondCourse(record);
		System.out.println("Current course: " + currentCourse);
		//course.selectCourse(record);
		
		// 3. Click the 'Student Recording' tab.
		record.clickOnStudentRecordingsTab();
		
		// 4. Select source recording.
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
		String selected_recording_name = record.getFirstRecordingTitle();
		
		Thread.sleep(Page.TIMEOUT_TINY);
		
		// 5. Selected checkbox is checked.
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
		
		// 6. Select "Recording Tasks -> Copy" menu item.
		record.clickOnRecordingTaskThenCopy();
		
		// 7. Verify that only courses where this USER signed as INSTRUCTOR are displayed in "Course List".
		List<String> copy_window_course_list = copy.getCourseList();
		
		boolean is_all_courses_as_instructor = true;
		for(String course_name: copy_window_course_list) {
			if(!course_list_for_which_user_is_instructor.contains(course_name)) {
				is_all_courses_as_instructor = false;
			}
		}
		
		if(is_all_courses_as_instructor) {
			System.out.println("Verifed that only courses where this user signed as instructor displayed.");
			ATUReports.add("Verifed that only courses where this user signed as instructor displayed.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Not all courses where this user signed as instructor displayed.");
			ATUReports.add("Not all courses where this user signed as instructor displayed.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		
		// 8. Select destination course.
		copy.selectTargetCourseFromCourseList(target_course_name);
		
		// 9. Selected course marked with blue line.
		copy.isTargetCourseSelected(target_course_name);
		
		// 10. Click the "Copy Recording(s)" button.
		copy.clickOnCopyButton();
		
		// 11. Click "OK" button.
		confirm_menu.clickOnOkButtonAfterConfirmCopyRecording();
		
		// 12. Message is closed.
		boolean is_closed = confirm_menu.isConfirmationMenuClosed();
		
		if(is_closed) {
			System.out.println("Message window is closed.");
			ATUReports.add("Message window is closed.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Message window not closed.");
			ATUReports.add("Message window closed.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		
		// 13. "Copy" window is closed.
		is_closed = copy.isCopyMenuClosed();
		
		if(is_closed) {
			System.out.println("Copy window is closed.");
			ATUReports.add("Copy window is closed.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Copy window not closed.");
			ATUReports.add("Copy window closed.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		
		// 14. The source recording has status "Being copied from".
		record.checkRecordingInIndexIStatus(1, "Being copied from");
		
		// 15. Click on source recording's title.
		record.clickOnRecordingTitleInIndex(1);
		
		// ##########################################################################


	}
}
