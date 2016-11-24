package com.automation.main.past_courses;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
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
import com.automation.main.page_helpers.EditRecordingPropertiesWindow;
import com.automation.main.page_helpers.LoginHelperPage;
import com.automation.main.page_helpers.ManageAdHocCoursesMembershipWindow;
import com.automation.main.page_helpers.ManageAdhocCoursesEnrollmentsPage;
import com.automation.main.page_helpers.ManageAdhocUsersPage;
import com.automation.main.page_helpers.MoveWindow;
import com.automation.main.page_helpers.PlayerPage;
import com.automation.main.page_helpers.RecordingHelperPage;
import com.automation.main.utilities.DriverSelector;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import junitx.util.PropertyManager;

public class TC24766VerifyCopyFromPastCourseToActiveCourse {
	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}

	public EditRecordingPropertiesWindow erp_window;
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
	public PlayerPage player_page;
	String instructor1;
	String instructor2;
	List<String> for_enroll;
	List<String> list_student;

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
		erp_window = PageFactory.initElements(driver, EditRecordingPropertiesWindow.class);
		admin_dashboard_page = PageFactory.initElements(driver, AdminDashboardPage.class);

		mange_adhoc_course_enrollments = PageFactory.initElements(driver, ManageAdhocCoursesEnrollmentsPage.class);

		create_new_course_window = PageFactory.initElements(driver, CreateNewCourseWindow.class);

		mange_adhoc_users_page = PageFactory.initElements(driver, ManageAdhocUsersPage.class);

		create_new_user_window = PageFactory.initElements(driver, CreateNewUserWindow.class);

		mangage_adhoc_courses_membership_window = PageFactory.initElements(driver,
				ManageAdHocCoursesMembershipWindow.class);
		player_page = PageFactory.initElements(driver, PlayerPage.class);

		Date curDate = new Date();
		String DateToStr = DateFormat.getInstance().format(curDate);
		System.out.println("Starting the test: TC24766VerifyCopyFromPastCourseToActiveCourse at " + DateToStr);
		ATUReports.add("Message window.",
				"Starting the test: TC24766VerifyCopyFromPastCourseToActiveCourse at " + DateToStr,
				"Starting the test: TC24766VerifyCopyFromPastCourseToActiveCourse at " + DateToStr, LogAs.PASSED,
				null);

	}

	// s
	@Test (description = "TC 24766 Verify Copy From Past Course To Active Course")
	public void test24766() throws Exception {
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
		tegrity.loginCourses("User1");		
		Thread.sleep(4000); 
		System.out.println("a3");
		course.deleteAllRecordingsInCourseStartWith("Ab", 0, record,delete_menu);		
		course.deleteAllRecordingsInCourseStartWith("Ab", 1, record,delete_menu);		
		course.deleteAllRecordingsInCourseStartWith("Ab", 2, record,delete_menu); 
		Thread.sleep(3000);
		String des_start_with = "Ab";
		String destination_course_name = null;
		for (String course_name : course.getCourseList()) {
			if (course_name.startsWith(des_start_with)) {
				destination_course_name = course_name;

			}
		}
		/// 2.click past courses tab
		course.clickOnPastCoursesTabButton();
		Thread.sleep(3000);
		/// 3.select past course
		course.selectCourseByName("PastCourseA");
		Thread.sleep(3000);
		/// 4.Select the recording
		record.recordings_tab.click();
		Thread.sleep(3000);
		record.convertRecordingsListToNames();
		String recordings = record.getFirstRecordingTitle();
		wait.until(ExpectedConditions.elementToBeClickable(record.getCheckbox()));
		record.getCheckbox().click();
		// 5.Select the "Recording Tasks -> Copy" menu item
		record.clickOnRecordingTaskThenCopy();
		Thread.sleep(3000);
		// 6.Select an active course and click the 'Copy' button
		copy.selectTargetCourseFromCourseList(destination_course_name);
		Thread.sleep(2000);
		copy.clickOnCopyButton();
		Thread.sleep(3000);
		confirm_menu.clickOnOkButton();
		Thread.sleep(3000);
		// 7.Click the "Additional Content" tab
		record.clickOnAdditionContentTab();
		Thread.sleep(4000);
		wait.until(ExpectedConditions.visibilityOf(record.first_additional_content_title));
		String additional_content = record.first_additional_content_title.getText();
		
		record.getCheckbox().click();
		/// 8.Select the "Content tasks -> Copy" menu item

		record.clickOnContentTaskThenCopy();
		Thread.sleep(3000);
		copy.selectTargetCourseFromCourseList(destination_course_name);
		Thread.sleep(2000);
		copy.clickOnCopyButton();
		Thread.sleep(3000);
		confirm_menu.clickOnOkButton();
		Thread.sleep(2000);
		// 9.Click the "student recordings" tab
		record.clickOnStudentRecordingsTab();
		Thread.sleep(2000);
		String student_recording = record.getFirstRecordingTitle();
		wait.until(ExpectedConditions.elementToBeClickable(record.getCheckbox()));
		record.getCheckbox().click();
		/// 10.Select the "Content tasks -> Copy" menu item
		record.clickOnRecordingTaskThenCopy();
		Thread.sleep(3000);
		copy.selectTargetCourseFromCourseList(destination_course_name);
		Thread.sleep(2000);
		copy.clickOnCopyButton();
		Thread.sleep(3000);
		confirm_menu.clickOnOkButton();
		Thread.sleep(2000);
		/// 11.Click on the 'Courses' breadcrumb
		record.returnToCourseListPage();
		/// 12.Select the destination course
		Thread.sleep(3000);
		course.clickOnActiveCoursesTabButton();
		Thread.sleep(2000);
		course.selectCourseThatStartingWith("Ab");
		/// 13.Verfiy the copied recording was moved correctly
		record.clickOnRecordingsTab();
		record.convertRecordingsListToNames();
		int index = record.recording_list_names.indexOf(recordings) + 1;
		if ((record.recording_list_names.contains(recordings))
				&& (record.checkThatRecordingStatusTargetIndexIsNotXWithTimeout(index, "Error", 1))) {
			System.out.println("Verfiy the copied recording was moved correctly");
			ATUReports.add("Verfiy the copied recording was moved correctly", "status", "Not Error", "Not Error",
					LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Fail Verfiy the copied recording was moved correctly");
			ATUReports.add("Verfiy the copied recording was moved correctly", "status", "Not Error", " Error",
					LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		// 14.dispaly recording
		record.selectRecordingByName(recordings);
		Thread.sleep(3000);
		
		record.clickOnFirstVisibleChapter();
		Thread.sleep(15000);

		player_page.verifyTimeBufferStatusForXSec(25);// check source display

		///// to go back to crecording window handler
		for (String handler : driver.getWindowHandles()) {
			driver.switchTo().window(handler);

		}

		player_page.returnToCoursesPage(course);
		Thread.sleep(3000);
		course.selectCourseThatStartingWith("Ab");
		Thread.sleep(3000);
		/// 15.select couese and press additional content tab
		record.clickOnAdditionContentTab();
		Thread.sleep(2000);
		// 16.Verfiy the copied aditional content was moved correctly
		record.convertAdditionalContantListToNames();
		index = record.additional_content_list_names.indexOf(additional_content) + 1;
		if ((record.additional_content_list_names.contains(additional_content))
				&& (record.checkThatAdditionalContentFileStatusTargetIndexIsNotXWithTimeout(index, "Error", 1))) {
			System.out.println("Verfiy the copied aditional content was moved correctly");
			ATUReports.add("Verfiy the copied aditional content was moved correctly", "status", "Not Error",
					"Not Error", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Fail Verfiy the copied aditional content was moved correctly");
			ATUReports.add("Verfiy the copied aditional content was moved correctly", "status", "Not Error", " Error",
					LogAs.FAILED, null);
			Assert.assertTrue(false);
		}

		Thread.sleep(2000);
		/// 17.Verfiy the copied student recording was moved correctly
		record.clickOnStudentRecordingsTab();
		Thread.sleep(2000);
		record.convertRecordingsListToNames();
		index = record.recording_list_names.indexOf(student_recording) + 1;
		if ((record.recording_list_names.contains(student_recording))
				&& (record.checkThatRecordingStatusTargetIndexIsNotXWithTimeout(index, "Error", 1))) {
			System.out.println("Verfiy the copied student recording was moved correctly");
			ATUReports.add("Verfiy the copied student recording was moved correctly", "status", "Not Error",
					"Not Error", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Fail Verfiy the student recording content was moved correctly");
			ATUReports.add("Verfiy the copied student recording was moved correctly", "status", "Not Error", " Error",
					LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		record.selectRecordingByName(student_recording);
		Thread.sleep(3000);
		wait.until(ExpectedConditions
				.visibilityOfAllElementsLocatedBy(By.cssSelector(".panel-body>.video-outer.ng-scope>.video-wrap")));
		driver.findElement(By.cssSelector(".panel-body>.video-outer.ng-scope>.video-wrap")).click();
		Thread.sleep(15000);
		// 18.1 dispaly recording
		player_page.verifyTimeBufferStatusForXSec(15);// check source display

		// 18.2 go back to crecording window handler
		for (String handler : driver.getWindowHandles()) {
			driver.switchTo().window(handler);
			// System.out.println("=========================================");
			// System.out.println(driver.getPageSource());
		}

		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);

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
}
