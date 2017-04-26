package com.automation.main.pre_post_test;


import java.text.DateFormat;
import java.util.Date;
import org.openqa.selenium.WebDriver;
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

import com.automation.main.page_helpers.AdminDashboardPage;
import com.automation.main.page_helpers.ConfirmationMenu;
import com.automation.main.page_helpers.CopyMenu;
import com.automation.main.page_helpers.CoursesHelperPage;
import com.automation.main.page_helpers.CreateNewCourseWindow;
import com.automation.main.page_helpers.CreateNewUserWindow;
import com.automation.main.page_helpers.DeleteMenu;
import com.automation.main.page_helpers.EditRecordingPropertiesWindow;
import com.automation.main.page_helpers.LoginHelperPage;
import com.automation.main.page_helpers.ManageAdHocCoursesMembershipWindow;
import com.automation.main.page_helpers.ManageAdhocCoursesEnrollmentsPage;
import com.automation.main.page_helpers.ManageAdhocUsersPage;
import com.automation.main.page_helpers.MoveWindow;
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
public class TestSuitePreRecordingsAdmin {

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
	public EditRecordingPropertiesWindow erp_window;

	@BeforeClass
	public void setup() {

//		System.setProperty("webdriver.edge.driver", "src/test/resources/MicrosoftWebDriver.exe");
		// capability=DesiredCapabilities.internetExplorer();
		// capability.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING,false);
		//

		System.setProperty("webdriver.chrome.driver", "c:/selenium-drivers/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();

		
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
		erp_window = PageFactory.initElements(driver, EditRecordingPropertiesWindow.class);
		
		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TestSuitePreSetCopyRecordingsAdmin at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TestSuitePreSetCopyRecordingsAdmin at " + DateToStr, "Starting the test: TestSuitePreSetCopyRecordingsAdmin at " + DateToStr, LogAs.PASSED, null);	
	}

	@AfterClass
	public void closeBroswer() {
		driver.quit();
	}


	// description = "get courses list"
	public void initializeCourseObject() throws InterruptedException {

		course = PageFactory.initElements(driver, CoursesHelperPage.class);
		course.courses = course.getCoursesListFromElement(course.course_list);
	}

	@Test(description = "Test Suite Pre Set Copy Recordings Admin")
	public void testSuitePreSetCopyRecordingsAdmin() throws InterruptedException {
		// 1. Login with SuperUser.
		
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);

		tegrity.loginCourses("SuperUser");// log in courses page
		initializeCourseObject();

		// Copy all recording from Bank Valid Recording to course starting with
		// Ab
		// Copy all tests from Bank Valid Recording to course starting
		// with Ab
//		course.waitForVisibility(course.active_courses_tab_button);
		Thread.sleep(2000);
		
		course.deleteAllRecordingsInCourseStartWith("Ab", 0, record, delete_menu);
		course.deleteAllRecordingsInCourseStartWith("Ab", 1, record, delete_menu);
		course.deleteAllRecordingsInCourseStartWith("Ab", 2, record, delete_menu);
		course.deleteAllRecordingsInCourseStartWith("Ab", 3, record, delete_menu);

		
		
		course.copyRecordingFromCourseStartWithToCourseStartWithOfType("BankValidRecording", "Ab", 3, record, copy,
				confirm_menu);
		course.copyRecordingFromCourseStartWithToCourseStartWithOfType("BankValidRecording", "Ab", 0, record, copy,
				confirm_menu);
		// Copy all additional content from Bank Valid Recording to course
		// starting with Ab
		course.copyRecordingFromCourseStartWithToCourseStartWithOfType("BankValidRecording", "Ab", 1, record, copy,
				confirm_menu);
		// Copy all student recordings from Bank Valid Recording to course
		// starting with Ab
		course.copyRecordingFromCourseStartWithToCourseStartWithOfType("BankValidRecording", "Ab", 2, record, copy,
				confirm_menu);
		
		course.verifyRecordingsStatusIsClear("BankValidRecording",0,record);
		System.out.println("1");  
		course.verifyRecordingsStatusIsClear("BankValidRecording",2,record);
		System.out.println("3");
		course.verifyRecordingsStatusIsClear("BankValidRecording",3,record);
		System.out.println("4");
		

		Thread.sleep(60*10000);
		
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);

		

	}
}
