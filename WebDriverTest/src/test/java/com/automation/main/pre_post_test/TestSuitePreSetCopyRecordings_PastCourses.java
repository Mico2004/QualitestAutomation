package com.automation.main.pre_post_test;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.AfterClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
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
import com.automation.main.utilities.DriverSelector;

import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import junitx.util.PropertyManager;

@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TestSuitePreSetCopyRecordings_PastCourses {

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

		// System.setProperty("webdriver.edge.driver",
		// "src/test/resources/MicrosoftWebDriver.exe");
		// capability=DesiredCapabilities.internetExplorer();
		// capability.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING,false);
		//

		driver = DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());

		//
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
		System.out.println("Starting the test: TestSuitePreSetCopyRecordingsAndPastCourse at " + DateToStr);
		ATUReports.add("Message window.", "Starting the test: TestSuitePreSetCopyRecordingsAndPastCourse at " + DateToStr,
				"Starting the test: TestSuitePreSetCopyRecordingsAndPastCourse at " + DateToStr, LogAs.PASSED, null);
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

	@Test( description = "Past Courses Pretest")
	public void testSuitePreSetCopyRecordingsAndPastCourse() throws InterruptedException {
		// 1. Login with SuperUser.
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
		Thread.sleep(2000);
				
	String login_url = driver.getCurrentUrl();
		String university_name  = login_url.split("/")[2].substring(0,  login_url.split("/")[2].length() - 12);
		String user = PropertyManager.getProperty("User1");
		System.out.println("PastCourseA"+university_name+user.substring(5, user.length()));
		String PastCourseA="PastCourseA"+university_name+user.substring(5, user.length());
		String PastCourseB="PastCourseB"+university_name+user.substring(5, user.length());
		
		
		tegrity.loginAdmin("Admin");
		Thread.sleep(2000);
		/// 2.Click the "Course Builder" link
		admin_dashboard_page.clickOnTargetSubmenuCourses("Manage Ad-hoc Courses / Enrollments (Course Builder)");
		Thread.sleep(10000);
		/// 3.Click the "Membership" link related to the course+unenroll
		/// instructor 1
		System.out.println("before 3");
		mange_adhoc_course_enrollments.unEnrollInstructorToCourse(PastCourseA, PropertyManager.getProperty("User1"),
				mangage_adhoc_courses_membership_window);
		Thread.sleep(4000);
	
		for (String window : driver.getWindowHandles()) {
			driver.switchTo().window(window);
			break;
		}

		System.out.println("before 3");
		mange_adhoc_course_enrollments.unEnrollInstructorToCourse(PastCourseB, PropertyManager.getProperty("User1"),
				mangage_adhoc_courses_membership_window);
		Thread.sleep(4000);

		for (String window : driver.getWindowHandles()) {
			driver.switchTo().window(window);
			break;
		}

		mange_adhoc_course_enrollments.clickOnAdminDashboard();
		Thread.sleep(2000);
		admin_dashboard_page.signOut();
		
		
		System.out.println("b0");
		  final List<Integer> CourseAbContent = Arrays.asList(0,1,2,3); //For Ab	
		  final List<Integer> CoursePastAContent = Arrays.asList(0,1,2,3); //For PAST A
		  final List<Integer> CoursePastBContent = Arrays.asList(0); //For PAST B
		  
		  Map<String,List<Integer>> CoursesAndContent = new HashMap<String,List<Integer>>() {
			{
				put(PropertyManager.getProperty("course1"),CourseAbContent);	
				put(PropertyManager.getProperty("course8"),CoursePastAContent);
				put(PropertyManager.getProperty("course9"),CoursePastBContent);
			}
			};
		TestSuitePreSetGeneric h=new TestSuitePreSetGeneric(driver);
		System.out.println("b1");
		h.GenricPreset(CoursesAndContent);
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
	
	/*
		
		
		
		tegrity.loginCourses("SuperUser");// log in courses page
		initializeCourseObject();

		// Copy all recording from Bank Valid Recording to course starting with 
		// Ab and to past courses, before hand checks if there are any recordings in those pages
		
		course.waitForVisibility(course.active_courses_tab_button);
		wait.until(ExpectedConditions.textToBePresentInElementLocated(By.id("wrapper"), "recordings -"));

		course.selectCourseThatStartingWith("Ab"); 	
		boolean	additionalExist=record.tabExists(1); 
		boolean	StudentExist=record.tabExists(2); 
		boolean	TestExist=record.tabExists(3); 
		System.out.println(additionalExist+""+StudentExist+""+TestExist);
		record.returnToCourseListPage(course);
		System.out.println("pastpreset1");
		course.deleteAllRecordingsInCourseStartWith("Ab", 0, record,delete_menu);
		System.out.println("pastpreset2");
		if(additionalExist)
		course.deleteAllRecordingsInCourseStartWith("Ab", 1, record,delete_menu);
		System.out.println("pastpreset3");
		if(StudentExist)
		course.deleteAllRecordingsInCourseStartWith("Ab", 2, record,delete_menu);
		System.out.println("pastpreset4");
		if(TestExist)
		course.deleteAllRecordingsInCourseStartWith("Ab", 3, record,delete_menu);
		System.out.println("pastpreset5");
		
		course.selectCourseThatStartingWith("PastCourseA");
		System.out.println("pastpreset6");
		additionalExist=record.tabExists(1);
		StudentExist=record.tabExists(2); 
		TestExist=record.tabExists(3);
		System.out.println("a2"); 
		record.returnToCourseListPage(course);
		Thread.sleep(4000);
		System.out.println("a3");
		
		course.deleteAllRecordingsInCourseStartWith("PastCourseA", 0, record,
		delete_menu); 
		
		if(additionalExist)
		course.deleteAllRecordingsInCourseStartWith("PastCourseA", 1, record,
		delete_menu); 
		
		if(StudentExist)
		course.deleteAllRecordingsInCourseStartWith("PastCourseA", 2, record,
		delete_menu); 
		
		if(TestExist)
		course.deleteAllRecordingsInCourseStartWith("PastCourseA", 3, record,
		delete_menu);
		Thread.sleep(4000);
		
		System.out.println("a5");
		course.deleteAllRecordingsInCourseStartWith("PastCourseB", 0, record,
		delete_menu); 
		System.out.println("a5.1");
		Thread.sleep(4000); 
		course.copyRecordingFromCourseStartWithToCourseStartWithOfType("BankValidRecording", "Ab", 0, record, copy, confirm_menu);
		Thread.sleep(4000); 
		System.out.println("a6");
		course.copyRecordingFromCourseStartWithToCourseStartWithOfType("BankValidRecording", "Ab", 1, record, copy, confirm_menu);
		Thread.sleep(4000); 
		System.out.println("a7"); 
		course.copyRecordingFromCourseStartWithToCourseStartWithOfType(	"BankValidRecording", "Ab", 2, record, copy, confirm_menu);
		Thread.sleep(4000); 
		System.out.println("a7");
		// Copy all student recordings from Bank Valid Recording to course 
		//starting with Ab
		course.copyRecordingFromCourseStartWithToCourseStartWithOfType(	"BankValidRecording", "Ab", 3, record, copy, confirm_menu);
		Thread.sleep(4000);
		System.out.println("a8");
		course.copyRecordingFromCourseStartWithToCourseStartWithOfType(	"BankValidRecording", "PastCourseA", 3, record, copy, confirm_menu);
		Thread.sleep(4000);
		course.copyRecordingFromCourseStartWithToCourseStartWithOfType( "BankValidRecording", "PastCourseA", 0, record, copy, confirm_menu);
		Thread.sleep(4000); 
		// Copy all additional content from Bank Valid Recording to course 
		// starting with Ab
	

		
		System.out.println("a9");
		course.copyRecordingFromCourseStartWithToCourseStartWithOfType(	"BankValidRecording", "PastCourseA", 1, record, copy, confirm_menu);
		Thread.sleep(4000);

		System.out.println("a10"); 
		// Copy all student recordings from Bank Valid Recording to course 
		// starting with Ab
		course.copyRecordingFromCourseStartWithToCourseStartWithOfType(	"BankValidRecording", "PastCourseA", 2, record, copy, confirm_menu);
		Thread.sleep(4000); System.out.println("a11");
		course.copyRecordingFromCourseStartWithToCourseStartWithOfType(	"BankValidRecording", "PastCourseB",0, record, copy, confirm_menu);
		Thread.sleep(4000); System.out.println("a12");
		course.verifyRecordingsStatusIsClear("BankValidRecording", 0,record);
		System.out.println("1"); Thread.sleep(4000);
		course.verifyRecordingsStatusIsClear("BankValidRecording", 2,record); 
		System.out.println("3"); Thread.sleep(4000);
		course.verifyRecordingsStatusIsClear("BankValidRecording", 3,record); 
		System.out.println("4"); Thread.sleep(4000);
		course.signOut();


		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);*/

	

	}
}
