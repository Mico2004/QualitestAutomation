package com.automation.main.past_courses;

import java.security.Signature;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.openqa.selenium.WebDriver;import com.automation.main.page_helpers.Page;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.automation.main.page_helpers.AdminDashboardPage;
import com.automation.main.page_helpers.ConfirmationMenu;
import com.automation.main.page_helpers.CopyMenu;
import com.automation.main.page_helpers.CoursesHelperPage;
import com.automation.main.page_helpers.CreateNewCourseWindow;
import com.automation.main.page_helpers.CreateNewUserWindow;
import com.automation.main.page_helpers.DeleteMenu;
import com.automation.main.page_helpers.EditRecordinPropertiesWindow;
import com.automation.main.page_helpers.LoginHelperPage;
import com.automation.main.page_helpers.ManageAdHocCoursesMembershipWindow;
import com.automation.main.page_helpers.ManageAdhocCoursesEnrollmentsPage;
import com.automation.main.page_helpers.ManageAdhocUsersPage;
import com.automation.main.page_helpers.MoveWindow;
import com.automation.main.page_helpers.RecordingHelperPage;
import com.automation.main.utilities.DriverSelector;

import java.text.DateFormat;
import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;

public class TC17901CreatePastCourseAndVerifyAppearanceAccordinglyToEnrollementStatus {

	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}

	public EditRecordinPropertiesWindow erp_window;
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
	WebDriver driver2;
	public AdminDashboardPage admin_dashboard_page;
	public ManageAdhocCoursesEnrollmentsPage mange_adhoc_course_enrollments;
	public CreateNewCourseWindow create_new_course_window;
	public ManageAdhocUsersPage mange_adhoc_users_page;
	public CreateNewUserWindow create_new_user_window;
	public ManageAdHocCoursesMembershipWindow mangage_adhoc_courses_membership_window;
	String instructor1;
	String instructor2;
	String student;

	@BeforeClass
	public void setup() {

		driver = DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());

		tegrity = PageFactory.initElements(driver, LoginHelperPage.class);

		wait = new WebDriverWait(driver, 30);

		record = PageFactory.initElements(driver, RecordingHelperPage.class);
		copy = PageFactory.initElements(driver, CopyMenu.class);
		delete_menu = PageFactory.initElements(driver, DeleteMenu.class);
		course = PageFactory.initElements(driver, CoursesHelperPage.class);
		confirm_menu = PageFactory.initElements(driver, ConfirmationMenu.class);

		wait = new WebDriverWait(driver, 30);
		move_window = PageFactory.initElements(driver, MoveWindow.class);
		erp_window = PageFactory.initElements(driver, EditRecordinPropertiesWindow.class);
		admin_dashboard_page = PageFactory.initElements(driver, AdminDashboardPage.class);

		mange_adhoc_course_enrollments = PageFactory.initElements(driver, ManageAdhocCoursesEnrollmentsPage.class);

		create_new_course_window = PageFactory.initElements(driver, CreateNewCourseWindow.class);

		mange_adhoc_users_page = PageFactory.initElements(driver, ManageAdhocUsersPage.class);

		create_new_user_window = PageFactory.initElements(driver, CreateNewUserWindow.class);

		mangage_adhoc_courses_membership_window = PageFactory.initElements(driver,
				ManageAdHocCoursesMembershipWindow.class);

		Date curDate = new Date();
		String DateToStr = DateFormat.getInstance().format(curDate);
		System.out.println(
				"Starting the test: TC17901CreatePastCourseAndVerifyAppearanceAccordinglyToEnrollementStatus at "
						+ DateToStr);
		ATUReports.add("Message window.",
				"Starting the test: TC17901CreatePastCourseAndVerifyAppearanceAccordinglyToEnrollementStatus at "
						+ DateToStr,
				"Starting the test: TC17901CreatePastCourseAndVerifyAppearanceAccordinglyToEnrollementStatus at "
						+ DateToStr,
				LogAs.PASSED, null);

	}

	@AfterClass
	public void closeBroswer() {
		this.driver.quit();
	}


	@Test(description = "test17901")
	public void test17901() throws InterruptedException {
	
		/// test 17901/////

		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
		/// 1. Login as INSTRUCTOR1
		tegrity.loginCourses("SuperUser");// log in courses page
		initializeCourseObject();
		/// 2. Verify that "Past Courses" tab is not displayed

		Thread.sleep(Page.TIMEOUT_TINY);
		String past_course_a = course.selectCourseThatStartingWith("PastCourseA");
		Thread.sleep(Page.TIMEOUT_TINY);

		// 3. Signout
		for (String window : driver.getWindowHandles()) {
			driver.switchTo().window(window);
			break;
		}
		course.signOut();
		System.out.println("Signed out");
		tegrity.loginCourses("User1");
		initializeCourseObject();
		/// 2. Verify that "Past Courses" tab is not displayed

		Thread.sleep(Page.TIMEOUT_TINY);
		// 13.Click Past Courses Tab button (without selecting a recording)
		course = PageFactory.initElements(driver, CoursesHelperPage.class);
		// 14.verify patcourses button
		course.verifyPastCoursesTabDisplayed();
		for (String window : driver.getWindowHandles()) {
			driver.switchTo().window(window);
			break;
		}
		
		course.signOut();
		// 16.login as instructor 2
		tegrity.loginCourses("User4");
		initializeCourseObject();
		/// 2. Verify that "Past Courses" tab is not displayed

		Thread.sleep(Page.TIMEOUT_TINY);
		// 17.Click Past Courses Tab button (without selecting a recording)
		course = PageFactory.initElements(driver, CoursesHelperPage.class);
		// 18.verify patcourses tab not exist
		course.verifyNoPastCoursesTab();
		// 19.verify active courses exists
		course.clickOnActiveCoursesTabButton();
		// 20.Signout
		for (String window : driver.getWindowHandles()) {
			driver.switchTo().window(window);
			break;
		}
		course.signOut();
	

		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
	}

	// description = "get courses list"
	public void initializeCourseObject() throws InterruptedException {

		course = PageFactory.initElements(driver, CoursesHelperPage.class);
		course.courses = course.getCoursesListFromElement(course.course_list);
	}

}
