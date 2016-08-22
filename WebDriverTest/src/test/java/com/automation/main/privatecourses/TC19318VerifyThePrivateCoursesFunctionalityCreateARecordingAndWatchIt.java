package com.automation.main.privatecourses;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.automation.main.AdminDashboardPage;
import com.automation.main.AdminDashboardViewCourseList;
import com.automation.main.ConfirmationMenu;
import com.automation.main.CopyMenu;
import com.automation.main.CourseSettingsPage;
import com.automation.main.CoursesHelperPage;
import com.automation.main.CreateNewUserWindow;
import com.automation.main.DeleteMenu;
import com.automation.main.DriverSelector;
import com.automation.main.LoginHelperPage;
import com.automation.main.ManageAdHocCoursesMembershipWindow;
import com.automation.main.ManageAdhocCoursesEnrollmentsPage;
import com.automation.main.ManageAdhocUsersPage;
import com.automation.main.MoveWindow;
import com.automation.main.PlayerPage;
import com.automation.main.RecordingHelperPage;
import com.automation.main.TopBarHelper;

import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import junitx.util.PropertyManager;

@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC19318VerifyThePrivateCoursesFunctionalityCreateARecordingAndWatchIt {

	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}

	public TC19318VerifyThePrivateCoursesFunctionalityCreateARecordingAndWatchIt() {
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

		// 
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
		
		// 2. Delete all recording from sand box course.
		course.deleteAllRecordingsInCourseStartWith(PropertyManager.getProperty("User1"), 0, record, delete_menu);
		
		// 3. Select Ab course and copy one recording to sand box, and return the courses page.
		course.selectCourseThatStartingWith("Ab");
		
		// Select the recording.
		String selected_recording_name = record.getFirstRecordingTitle();
		record.selectFirstCheckbox();
		
		// Select the "Recording Tasks -> Copy" menu item.
		record.clickOnRecordingTaskThenCopy();
		
		// "Copy" window is displayed.
		copy.verifyThatCopyMenuOpen();
		
		// Select the private course as destination.
		copy.selectTargetCourseFromCourseListThatStartWith(PropertyManager.getProperty("User1"));
		
		// Click the "Copy Recording" button.
		copy.clickOnCopyButton();
		
		Thread.sleep(1000);
		
		// "Recording has been queued for copy" message box is displayed.
		// Click the "OK" button.
		confirm_menu.clickOnOkButtonAfterConfirmCopyRecording();
		
		// Message box is closed.
		confirm_menu.verifyConfirmWindowIsClosed();
		
		// When the "Copy" process is finished, verify that recording is displayed in source course.
		record.checkStatusExistenceForMaxTTime(360);
		record.verifyThatTargetRecordingExistInRecordingList(selected_recording_name);
		
		// Click the "Courses" breadcrumb.
		record.returnToCourseListPage();
		
		// 4. Select the private course.
		course.selectCourseThatStartingWith(PropertyManager.getProperty("User1"));
		
		// 5. Click the recording's title (copied from Ab).
		// 6. Click at one of recording's chapters.
		record.clickOnTargetRecordingAndOpenItsPlayback(selected_recording_name);
		
		// 7. The recording playback page is opened.
		// 8. The recording is being played.
		player_page.verifyTimeBufferStatusForXSec(10);
		
}}
