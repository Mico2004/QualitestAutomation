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
public class TC19319VerifyThePrivateCoursesFunctionalityCopyTheRecording {

	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}

	public TC19319VerifyThePrivateCoursesFunctionalityCopyTheRecording() {
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
		
		// 1.1. Delete all recordings from private course.
		course.deleteAllRecordingsInCourseStartWith(PropertyManager.getProperty("User1"), 0, record, delete_menu);
		
		// 1.2. Delete all recording from abc.
		course.deleteAllRecordingsInCourseStartWith("abc", 0, record, delete_menu);
		
		// 1.3. Delete all recording from ad.
		course.deleteAllRecordingsInCourseStartWith("ad", 0, record, delete_menu);
		
		// 1.5. Copy first recording to private course.
		// 1.6. Copy second recording to ad.
		// 1.7. Return to courses page.
		course.selectCourseThatStartingWith("Ab");
		
		// Select the recording.
		record.getFirstRecordingTitle();
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
		
		
		// Unselect first checkbox, and select second checkbox
		Thread.sleep(1000);
		record.unselectIndexCheckBox(1);
		Thread.sleep(1000);
		record.selectIndexCheckBox(2);
		
		// Select the "Recording Tasks -> Copy" menu item.
		record.clickOnRecordingTaskThenCopy();
				
		// "Copy" window is displayed.
		copy.verifyThatCopyMenuOpen();
				
		// Select the private course as destination.
		copy.selectTargetCourseFromCourseListThatStartWith("ad");
				
		// Click the "Copy Recording" button.
		copy.clickOnCopyButton();
				
		Thread.sleep(1000);
				
		// "Recording has been queued for copy" message box is displayed.
		// Click the "OK" button.
		confirm_menu.clickOnOkButtonAfterConfirmCopyRecording();
				
		// Message box is closed.
		confirm_menu.verifyConfirmWindowIsClosed();
		
		// When the "Copy" process is finished, verify that recording is displayed in source course.
//		record.checkThatRecordingStatusTargetIndexIsEmpty(1, 360);
		
		// Click the "Courses" breadcrumb.
		record.returnToCourseListPage();
		
		Thread.sleep(2000);
		
		// 2. Select the private course.
		course.selectCourseThatStartingWith(PropertyManager.getProperty("User1"));
		
		Thread.sleep(2000);
		
		// 3. Select the recording.
		record.checkStatusExistenceForMaxTTime(360);
		record.selectIndexCheckBox(1);
		String selected_recording = record.getFirstRecordingTitle();
		
		// 4. Select the "Recording Tasks -> Copy" menu item.
		record.clickOnRecordingTaskThenCopy();
		
		// 5. "Copy" window is displayed.
		copy.verifyThatCopyMenuOpen();
		
		// 6. Select the destination course (abc).
		copy.selectTargetCourseFromCourseListThatStartWith("abc");
		
		// 7. Click the "Copy Recording" button.
		copy.clickOnCopyButton();
		
		// 8. "Recording has been queued for copy" message box is displayed.
		// 9. Click the "OK" button.
		confirm_menu.clickOnOkButtonAfterConfirmCopyRecording();
		
		// 10. Message box is closed.
		confirm_menu.verifyConfirmWindowIsClosed();
		
		// 11. When the "Copy" process is finished, verify that recording is displayed in private course.
		record.checkStatusExistenceForMaxTTime(360);
		record.verifyThatTargetRecordingExistInRecordingList(selected_recording);
		
		// 12. Click the "Courses" breadcrumb.
		record.returnToCourseListPage();
		
		Thread.sleep(2000);
		
		// 13. Select the destination course (abc).
		course.selectCourseThatStartingWith("abc");
		
		Thread.sleep(1000);
		
		// 14. Verify that copied recording is displayed.
		record.verifyThatTargetRecordingExistInRecordingList(selected_recording);
		
		// 15. Click on the recording.
		// 16. Click on one of it's chapters.
		record.clickOnTargetRecordingAndOpenItsPlayback(selected_recording);
		
		
		// 17. The recording playback page is displayed.
		// 18. The recording is being played.
		player_page.verifyTimeBufferStatusForXSec(10);
		
		//19. Click on the 'Courses' breadcrumb
		driver.navigate().back();
		Thread.sleep(1000);
		record.returnToCourseListPage();
		Thread.sleep(1000);
		
		// 20. Select a non private course (ad).
		course.selectCourseThatStartingWith("ad");
		
		Thread.sleep(2000);
		
		// 21. Select a recording.
		record.selectIndexCheckBox(1);
		selected_recording = record.getFirstRecordingTitle();
		
		// 22. Select the "Recording Tasks -> Copy" menu item.
		record.clickOnRecordingTaskThenCopy();
		
		// 23. "Copy" window is displayed.
		copy.verifyThatCopyMenuOpen();
		
		// 24. Select a private course as the destination course.
		copy.selectTargetCourseFromCourseListThatStartWith(PropertyManager.getProperty("User1"));
		
		// 25. Click the "Copy Recording" button.
		copy.clickOnCopyButton();
		Thread.sleep(1000);
		
		// 26. "Recording has been queued for copy" message box is displayed.
		// 27. Click the "OK" button.
		confirm_menu.clickOnOkButtonAfterConfirmCopyRecording();
		
		// 28. Message box is closed.
		confirm_menu.verifyConfirmWindowIsClosed();
		
		// 29. When the "Copy" process is finished, verify that recording is displayed in the course.
		record.checkStatusExistenceForMaxTTime(360);
		record.verifyThatTargetRecordingExistInRecordingList(selected_recording);
		
		// 30. Click the "Courses" breadcrumb.
		record.returnToCourseListPage();
		Thread.sleep(1000);
		
		// 31. Select the destination private course.
		course.selectCourseThatStartingWith(PropertyManager.getProperty("User1"));
		
		Thread.sleep(1000);
		
		// 32. Verify that copied recording is displayed.
		record.verifyThatTargetRecordingExistInRecordingList(selected_recording);
		
		// 33. Click on the recording.
		// 34. Click on one of it's chapters.
		record.clickOnTargetRecordingAndOpenItsPlayback(selected_recording);
		
		// 35. The recording playback page is displayed.
		// 36. The recording is being played.
		player_page.verifyTimeBufferStatusForXSec(10);
		
		
}}
