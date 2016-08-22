package com.automation.main;

import java.awt.AWTException;
import java.io.IOException;
import java.net.URL;
import java.security.PublicKey;
import java.security.spec.ECPrivateKeySpec;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.function.IntToDoubleFunction;

import org.junit.experimental.theories.Theories;
import org.omg.CORBA.StringHolder;
import org.omg.Messaging.SyncScopeHelper;
import org.omg.PortableInterceptor.NON_EXISTENT;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Platform;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
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
import org.w3c.dom.stylesheets.LinkStyle;

import com.sun.jna.win32.W32APITypeMapper;

import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import atu.testng.reports.utils.Utils;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;
import junitx.util.PropertyManager;
import junitx.util.ResourceManager;
import net.sourceforge.htmlunit.corejs.javascript.tools.debugger.treetable.JTreeTable.ListToTreeSelectionModelWrapper;

@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC19320VerifyThePrivateCoursesFunctionalityMoveTheRecording {

	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}

	public TC19320VerifyThePrivateCoursesFunctionalityMoveTheRecording() {
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

		// driver.manage().window().maximize();
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
		// 1. Login as User1 and get the full name of Ab course.
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
		tegrity.loginCourses("User1");// log in courses page
		initializeCourseObject();
		
		// 2. Get private course name (sand box) & non private course name
		String instructor_private_course = course.selectCourseThatStartingWith(PropertyManager.getProperty("User1"));
		record.returnToCourseListPage();
		String instructor_non_private_course = course.selectCourseThatStartingWith("abc");
		record.returnToCourseListPage();
		
		// 2.1. Delete all recordings from abc course & private course.
		course.deleteAllRecordingsInCourseStartWith(instructor_non_private_course, 0, record, delete_menu);
		course.deleteAllRecordingsInCourseStartWith(instructor_private_course, 0, record, delete_menu);
		
		// 3. Select Ab course.
		// 4. Copy a few recordings to private course.
		// 5. Return to courses page.
		course.copyRecordingFromCourseStartWithToCourseStartWithOfType("Ab", instructor_private_course, 0, record, copy, confirm_menu);
		
		// 6. Select the private course.
		course.selectCourseThatStartingWith(instructor_private_course);
		
		// 7. Select the recording.
		String selected_recording_name = record.getFirstRecordingTitle();
		record.selectFirstCheckbox();
		
		// 8. Select the "Recording Tasks -> Move" menu item.
		record.clickOnRecordingTaskThenMove();
		
		// 8.1. "Move" window is displayed.
		move_window.verifyThatMoveWindowIsOpen();
		
		// 9. Select the destination course (abc).
		copy.selectTargetCourseFromCourseList(instructor_non_private_course);
		
		// 10. Click the "Move Recording" button.
		move_window.clickOnMoveRecordings();
		
		Thread.sleep(1000);
		
		// 11. "Recording has been queued for move" message box is displayed.
		// 12. Click the "OK" button.
		confirm_menu.clickOnOkButtonAfterConfirmMoveRecording();
		
		// 12.1. Message box is closed.
		confirm_menu.verifyConfirmWindowIsClosed();
		
		// 13. When the "Move" process is finished, verify that recording is not displayed ;private course.
		record.checkStatusExistenceForMaxTTime(360);
		
		record.verifyThatTargetRecordingNotExistInRecordingList(selected_recording_name);
		
		// 14. Click the "Courses" breadcrumb.
		record.returnToCourseListPage();
		
		// 15. Select the destination course.
		course.selectCourseThatStartingWith(instructor_non_private_course);
		
		// 16. Verify that moved recording is displayed.
		record.verifyThatTargetRecordingExistInRecordingList(selected_recording_name);
		
		// 17. Open the recording.
		record.clickOnTargetRecordingAndOpenItsPlayback(selected_recording_name);
		
		// 17.1. The recording playback page is displayed.
		// 17.2. The recording is being played.
		player_page.verifyTimeBufferStatusForXSec(10);
		
		// 18. Click on the 'Courses' breadcrumbs.
//		course.goToCoursesPage();
		driver.navigate().back();
		Thread.sleep(1000);
		record.returnToCourseListPage();
		
		// 19. Select a non private course (abc).
		course.selectCourseThatStartingWith(instructor_non_private_course);
		
		// 20. Select the recording.
		selected_recording_name = record.getFirstRecordingTitle();
		record.selectFirstCheckbox();
		
		// 21. Select the "Recording Tasks -> Move" menu item.
		record.clickOnRecordingTaskThenMove();
		
		// 22. "Move" window is displayed.
		move_window.verifyThatMoveWindowIsOpen(); 
		
		// 23. Select the private course as destination course (sand box).
		copy.selectTargetCourseFromCourseList(instructor_private_course);
		
		// 24. Click the "Move Recording" button.
		move_window.clickOnMoveRecordings();
		
		// 25. "Recording has been queued for move" message box is displayed.
		// 26. Click the "OK" button.
		confirm_menu.clickOnOkButtonAfterConfirmMoveRecording();
		
		// 27. Message box is closed.
		confirm_menu.verifyConfirmWindowIsClosed();
		
		// 28. When the "Move" process is finished, verify that recording is not displayed in the course.
		record.checkStatusExistenceForMaxTTime(360);
		
		record.verifyThatTargetRecordingNotExistInRecordingList(selected_recording_name);
		
		// 29. Click the "Courses" breadcrumb.
		record.returnToCourseListPage();
		
		// 30. Select the destination course.
		course.selectCourseThatStartingWith(instructor_private_course);
		
		// 31. Verify that moved recording is displayed.
		record.verifyThatTargetRecordingExistInRecordingList(selected_recording_name);
		
		// 32. Open the recording.
		record.clickOnTargetRecordingAndOpenItsPlayback(selected_recording_name);
		
		// 32.1. The recording playback page is displayed.
		// 32.2. The recording is being played.
		player_page.verifyTimeBufferStatusForXSec(10);
		

}}
