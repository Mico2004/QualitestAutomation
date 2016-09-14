package com.automation.main;


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
import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import junitx.util.PropertyManager;


@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC19321VerifyThePrivateCoursesFunctionalityDeleteTheRecording {

	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}

	public TC19321VerifyThePrivateCoursesFunctionalityDeleteTheRecording() {
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
		 System.out.println("Starting the test: TC19321VerifyThePrivateCoursesFunctionalityDeleteTheRecording at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC19321VerifyThePrivateCoursesFunctionalityDeleteTheRecording at " + DateToStr,
		 "Starting the test: TC19321VerifyThePrivateCoursesFunctionalityDeleteTheRecording at " + DateToStr, LogAs.PASSED, null);	
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

	@Test
	public void loginCourses() throws InterruptedException
	{
		// 1. Login as INSTRUCTOR.
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
		tegrity.loginCourses("User1");// log in courses page
		initializeCourseObject();
		
		// 2. Select the private course.
		course.selectCourseThatStartingWith(PropertyManager.getProperty("User1"));
		
		if(record.getNumberOfRecordings() == 0) {
			record.returnToCourseListPage();
			course.copyOneRecordingFromCourseStartWithToCourseStartWithOfType("Ab", PropertyManager.getProperty("User1"), 0, record, copy, confirm_menu);
			course.selectCourseThatStartingWith(PropertyManager.getProperty("User1"));
		}
		
		// 3. Select the recording.
		String selected_recording_name = record.getFirstRecordingTitle();
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
		
		// 4. Select the "Recording Tasks -> Delete" menu item.
		record.clickOnRecordingTaskThenDelete();
		
		// 5. "Delete" window is displayed.
		delete_menu.verifyDeleteWindowDisplayed();
		
		// 6. Press the "Delete" button.
		delete_menu.clickOnDeleteButton();
		
		// 7. Verify that recording is deleted
		Thread.sleep(3000);
		record.verifyThatTargetRecordingNotExistInRecordingList(selected_recording_name);
		
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
}}
