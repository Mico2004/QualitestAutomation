package com.automation.main;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;import com.automation.main.page_helpers.Page;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
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
public class TestSuitePreSetCopyRecordings11 {

	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}

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
	String currentCourse;
	String targetCourse;
	String clickedRecording;
	DesiredCapabilities capability;
	public AdminDashboardPage admin_dashboard_page;
	public ManageAdhocCoursesEnrollmentsPage mange_adhoc_course_enrollments;
	public CreateNewCourseWindow create_new_course_window;
	public ManageAdhocUsersPage mange_adhoc_users_page;
	public CreateNewUserWindow create_new_user_window;
	public ManageAdHocCoursesMembershipWindow mangage_adhoc_courses_membership_window;
	public EditRecordinPropertiesWindow erp_window;

	@BeforeClass
	public void setup() {

//		System.setProperty("webdriver.edge.driver", "src/test/resources/MicrosoftWebDriver.exe");
		// capability=DesiredCapabilities.internetExplorer();
		// capability.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING,false);
		//
		System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
		driver = new ChromeDriver();


	    driver.manage().window().maximize();
		
		//driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		ATUReports.setWebDriver(driver);
		tegrity = PageFactory.initElements(driver, LoginHelperPage.class);

		record = PageFactory.initElements(driver, RecordingHelperPage.class);
		copy = PageFactory.initElements(driver, CopyMenu.class);
		delete_menu = PageFactory.initElements(driver, DeleteMenu.class);

		confirm_menu = PageFactory.initElements(driver, ConfirmationMenu.class);

		move_window = PageFactory.initElements(driver, MoveWindow.class);

		mange_adhoc_course_enrollments = PageFactory.initElements(driver, ManageAdhocCoursesEnrollmentsPage.class);

		create_new_course_window = PageFactory.initElements(driver, CreateNewCourseWindow.class);

		mange_adhoc_users_page = PageFactory.initElements(driver, ManageAdhocUsersPage.class);

		create_new_user_window = PageFactory.initElements(driver, CreateNewUserWindow.class);
		admin_dashboard_page = PageFactory.initElements(driver, AdminDashboardPage.class);
		mangage_adhoc_courses_membership_window = PageFactory.initElements(driver,
				ManageAdHocCoursesMembershipWindow.class);

		wait = new WebDriverWait(driver, 30);
		move_window = PageFactory.initElements(driver, MoveWindow.class);
		erp_window = PageFactory.initElements(driver, EditRecordinPropertiesWindow.class);
		
		Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TestSuitePreSetCopyRecordings11 at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TestSuitePreSetCopyRecordings11 at " + DateToStr, "Starting the test: TestSuitePreSetCopyRecordings11 at " + DateToStr, LogAs.PASSED, null);
	
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
	public void loginCourses() throws InterruptedException {
		// 1. Login with SuperUser.
		
		tegrity.loginCourses("SuperUser");// log in courses page
		initializeCourseObject();

		// Copy all recording from Bank Valid Recording to course starting with
		// Ab
		// Copy all tests from Bank Valid Recording to course starting
		// with Ab
//		course.waitForVisibility(course.active_courses_tab_button);
		Thread.sleep(Page.TIMEOUT_TINY);		
		
		course.deleteAllRecordingsInCourseStartWith("Ab", 0, record, delete_menu);
		Thread.sleep(Page.TIMEOUT_TINY);		
		course.deleteAllRecordingsInCourseStartWith("Ab", 1, record, delete_menu);
		Thread.sleep(Page.TIMEOUT_TINY);		
		course.deleteAllRecordingsInCourseStartWith("Ab", 2, record, delete_menu);
		Thread.sleep(Page.TIMEOUT_TINY);		
		course.deleteAllRecordingsInCourseStartWith("Ab", 3, record, delete_menu);
		Thread.sleep(Page.TIMEOUT_TINY);		

		
		
		course.copyRecordingFromCourseStartWithToCourseStartWithOfType("BankValidRecording", "Ab", 0, record, copy,
				confirm_menu);
		Thread.sleep(Page.TIMEOUT_TINY);		
		course.copyRecordingFromCourseStartWithToCourseStartWithOfType("BankValidRecording", "Ab", 1, record, copy,
				confirm_menu);
		Thread.sleep(Page.TIMEOUT_TINY);			
		// Copy all additional content from Bank Valid Recording to course
		// starting with Ab
		course.copyRecordingFromCourseStartWithToCourseStartWithOfType("BankValidRecording", "Ab", 2, record, copy,
				confirm_menu);
		Thread.sleep(Page.TIMEOUT_TINY);			
		// Copy all student recordings from Bank Valid Recording to course
		// starting with Ab
		course.copyRecordingFromCourseStartWithToCourseStartWithOfType("BankValidRecording", "Ab", 3, record, copy,
				confirm_menu);		
		
		course.verifyRecordingsStatusIsClear("BankValidRecording",0,record);
		System.out.println("1");		
		course.verifyRecordingsStatusIsClear("BankValidRecording",2,record);
		System.out.println("3");
		course.verifyRecordingsStatusIsClear("BankValidRecording",3,record);
		System.out.println("4");

		
		

		
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
//		// 2.copy courses to pastcourses a
//
//		course.copyRecordingFromCourseStartWithToCourseStartWithOfType("BankValidRecording", "PastCourseA", 0, record,
//				copy, confirm_menu);
//		course.copyRecordingFromCourseStartWithToCourseStartWithOfType("BankValidRecording", "PastCourseA", 1, record,
//				copy, confirm_menu);
//		course.copyRecordingFromCourseStartWithToCourseStartWithOfType("BankValidRecording", "PastCourseA", 2, record,
//				copy, confirm_menu);
//		// 3.copy courses to pastcourses b
//
//		course.waitForVisibility(course.course_list.get(0));
//		initializeCourseObject();
//		course.copyRecordingFromCourseStartWithToCourseStartWithOfType("BankValidRecording", "PastCourseB", 0, record,
//				copy, confirm_menu);
//		course.copyRecordingFromCourseStartWithToCourseStartWithOfType("BankValidRecording", "PastCourseB", 1, record,
//				copy, confirm_menu);
//		course.copyRecordingFromCourseStartWithToCourseStartWithOfType("BankValidRecording", "PastCourseB", 2, record,
//				copy, confirm_menu);

//		for (String window : driver.getWindowHandles()) {
//			driver.switchTo().window(window);
//			break;
//		}
//		course.signOut();
//		Thread.sleep(Page.TIMEOUT_TINY);
//
//		////////////////////////// unenrolling user 1 past course A And B
//		////////////////////////// +changing recording ownership
//		////////////////////////// ///////////////////
//		//
//		// // 1. Login with SuperUser.
//		tegrity.loginCourses("User1");// log in courses page
//		initializeCourseObject();
//		// //ownership change
//
//		try {
//			Robot robot = new Robot();
//			robot.setAutoDelay(2000);
//			robot.mouseMove(0, -1000);
//		} catch (AWTException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		String past_course_a = course.selectCourseThatStartingWith("PastCourseA");
//		Thread.sleep(Page.TIMEOUT_TINY);
//		String user1 = PropertyManager.getProperty("User1");
//		String user4 = PropertyManager.getProperty("User4");
//		record.changeRecordingOwnership(confirm_menu, erp_window, user1, null);
//		Thread.sleep(Page.TIMEOUT_TINY);
//		for (String window : driver.getWindowHandles()) {
//			driver.switchTo().window(window);
//			break;
//		}
//		record.returnToCourseListPage();
//		Thread.sleep(Page.TIMEOUT_TINY);
//		String past_course_b = course.selectCourseThatStartingWith("PastCourseB");
//		Thread.sleep(Page.TIMEOUT_TINY);
//
//		record.changeRecordingOwnership(confirm_menu, erp_window, user1, null);
//		Thread.sleep(Page.TIMEOUT_TINY);
//		record.signOut();
//		Thread.sleep(Page.TIMEOUT_TINY);
//		tegrity.loginCourses("User4");// log in courses page to register user in
//										// database
//		Thread.sleep(Page.TIMEOUT_TINY);
//		course.signOut();
//		tegrity.loginAdmin("Admin");
//		Thread.sleep(Page.TIMEOUT_TINY);
//		// 4. Click on course builder href link
//
//		// String
//		// past_course_a="PastCourseAawsserverautomation113032016121315_Name";
//		// String
//		// past_course_b="PastCourseBawsserverautomation113032016121315_Name";
//
//		admin_dashboard_page.clickOnTargetSubmenuCourses("Manage Ad-hoc Courses / Enrollments (Course Builder)");
//		Thread.sleep(Page.TIMEOUT_TINY);
//		mange_adhoc_course_enrollments.unEnrollInstructorToCourse(past_course_a, user1,
//				mangage_adhoc_courses_membership_window);
//		Thread.sleep(Page.TIMEOUT_TINY);
//		for (String window : driver.getWindowHandles()) {
//			driver.switchTo().window(window);
//			break;
//		}
//		mange_adhoc_course_enrollments.unEnrollInstructorToCourse(past_course_b, user1,
//				mangage_adhoc_courses_membership_window);
//		Thread.sleep(Page.TIMEOUT_TINY);
//
//		driver.quit();

	}
}
