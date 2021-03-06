package com.automation.main.private_courses;


import java.util.Date;
import java.text.DateFormat;
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
import com.automation.main.page_helpers.ConfirmationMenu;
import com.automation.main.page_helpers.CopyMenu;
import com.automation.main.page_helpers.CourseSettingsPage;
import com.automation.main.page_helpers.CoursesHelperPage;
import com.automation.main.page_helpers.CreateNewUserWindow;
import com.automation.main.page_helpers.DeleteMenu;
import com.automation.main.page_helpers.LoginHelperPage;
import com.automation.main.page_helpers.ManageAdHocCoursesMembershipWindow;
import com.automation.main.page_helpers.ManageAdhocCoursesEnrollmentsPage;
import com.automation.main.page_helpers.ManageAdhocUsersPage;
import com.automation.main.page_helpers.MoveWindow;
import com.automation.main.page_helpers.PlayerPage;
import com.automation.main.page_helpers.RecordingHelperPage;
import com.automation.main.page_helpers.TopBarHelper;
import com.automation.main.utilities.DriverSelector;

import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;

import junitx.util.PropertyManager;

@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC19322VerifyThePrivateCoursesFunctionalityCopyTheRecordingToPrivateCourse {

	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}

	public TC19322VerifyThePrivateCoursesFunctionalityCopyTheRecordingToPrivateCourse() {
		// TODO Auto-generated constructor stub
	}

	public PlayerPage player_page;
	public ManageAdHocCoursesMembershipWindow mange_ad_hoc_courses_membership_window;
	public ManageAdhocCoursesEnrollmentsPage manage_adhoc_courses_enrollments_page;
	public CourseSettingsPage course_settings_page;
	public TopBarHelper top_bar_helper;
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
	public AdminDashboardPage admin_dashboard_page;
	public AdminDashboardViewCourseList admin_dashboard_view_course_list;
	public ManageAdhocUsersPage mange_adhoc_users_page;
	public CreateNewUserWindow create_new_user_window;

	@BeforeClass
	public void setup() {

		driver = DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());

		
		ATUReports.setWebDriver(driver);

		tegrity = PageFactory.initElements(driver, LoginHelperPage.class);

		record = PageFactory.initElements(driver, RecordingHelperPage.class);
		copy = PageFactory.initElements(driver, CopyMenu.class);
		
		mange_adhoc_users_page = PageFactory.initElements(driver, ManageAdhocUsersPage.class);
		create_new_user_window = PageFactory.initElements(driver, CreateNewUserWindow.class);

		confirm_menu = PageFactory.initElements(driver, ConfirmationMenu.class);

		move_window = PageFactory.initElements(driver, MoveWindow.class);

		delete_menu = PageFactory.initElements(driver, DeleteMenu.class);

		course_settings_page = PageFactory.initElements(driver, CourseSettingsPage.class);

		admin_dashboard_page = PageFactory.initElements(driver, AdminDashboardPage.class);

		top_bar_helper = PageFactory.initElements(driver, TopBarHelper.class);
		admin_dashboard_view_course_list = PageFactory.initElements(driver, AdminDashboardViewCourseList.class);
		manage_adhoc_courses_enrollments_page = PageFactory.initElements(driver, ManageAdhocCoursesEnrollmentsPage.class);
		mange_ad_hoc_courses_membership_window = PageFactory.initElements(driver, ManageAdHocCoursesMembershipWindow.class);
		player_page = PageFactory.initElements(driver, PlayerPage.class);
		
		wait = new WebDriverWait(driver, 30);
		
		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC19322VerifyThePrivateCoursesFunctionalityCopyTheRecordingToPrivateCourse at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC19322VerifyThePrivateCoursesFunctionalityCopyTheRecordingToPrivateCourse at " + DateToStr,
		 "Starting the test: TC19322VerifyThePrivateCoursesFunctionalityCopyTheRecordingToPrivateCourse at " + DateToStr, LogAs.PASSED, null);
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

	@Test (description = "TC 19322 Verify The Private Courses Functionality Copy The Recording To Private Course")
	public void test19322() throws InterruptedException
	{
		// 1. Login as INSTRUCTOR.
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
		tegrity.loginCourses("User1");// log in courses page
		initializeCourseObject();
		
		// Delete all recording from private course
		course.deleteAllRecordingsInCourseStartWith(PropertyManager.getProperty("User1"), 0, record, delete_menu);
		
		// 2. Select the course (not private - Ab).
		course.selectCourseThatStartingWith("Ab");
		
		// 3. Select the recording.
		String selected_recording_name = record.getFirstRecordingTitle();
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
		
		// 4. Select the "Recording Tasks -> Copy" menu item.
		record.clickOnRecordingTaskThenCopy();
		
		// 5. "Copy" window is displayed.
		copy.verifyThatCopyMenuOpen();
		
		// 6. Select the private course as destination.
		copy.selectTargetCourseFromCourseListThatStartWith(PropertyManager.getProperty("User1"));
		
		// 7. Click the "Copy Recording" button.
		copy.clickOnCopyButton();
		
		Thread.sleep(1000);
		
		// 8. "Recording has been queued for copy" message box is displayed.
		// 9. Click the "OK" button.
		confirm_menu.clickOnOkButtonAfterConfirmCopyRecording();
		
		// 10. Message box is closed.
		confirm_menu.verifyConfirmWindowIsClosed();
		
		// 11. When the "Copy" process is finished, verify that recording is displayed in source course.
		record.checkStatusExistenceForMaxTTime(360);
		record.verifyThatTargetRecordingExistInRecordingList(selected_recording_name);
		
		// 12. Click the "Courses" breadcrumb.
		record.returnToCourseListPage();
		
		// 13. Select the private course.
		course.selectCourseThatStartingWith(PropertyManager.getProperty("User1"));
		
		// 14. Verify that copied recording is displayed.
		record.verifyThatTargetRecordingExistInRecordingList(selected_recording_name);
		
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
		
}}
