package com.automation.main.past_courses;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
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
import junitx.util.PropertyManager;
/////////////////////////system bug with past courses///////!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
public class TC17933VerifypastCourseWithStudentRecording {
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
	String user;
	String instructor1;
	String instructor2;
	List<String> for_enroll;

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
		 System.out.println("Starting the test: TC17933VerifypastCourseWithStudentRecording at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC17933VerifypastCourseWithStudentRecording at " + DateToStr,
		 "Starting the test: TC17933VerifypastCourseWithStudentRecording at " + DateToStr, LogAs.PASSED, null);
	}

	@AfterClass
	public void closeBroswer() {		
		this.driver.quit();
	}
	
	@Test (description="17933 Verify the past course with Student recording")
	public void test17933() throws Exception {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyhhmmss");
		user = "user" + sdf.format(date);
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);

		initializeCourseObject();
		admin_dashboard_page = PageFactory.initElements(driver, AdminDashboardPage.class);

		mange_adhoc_course_enrollments = PageFactory.initElements(driver, ManageAdhocCoursesEnrollmentsPage.class);

		create_new_course_window = PageFactory.initElements(driver, CreateNewCourseWindow.class);

		mange_adhoc_users_page = PageFactory.initElements(driver, ManageAdhocUsersPage.class);

		create_new_user_window = PageFactory.initElements(driver, CreateNewUserWindow.class);

		mangage_adhoc_courses_membership_window = PageFactory.initElements(driver,
				ManageAdHocCoursesMembershipWindow.class);

		Thread.sleep(2000);

		tegrity.loginAdmin("Admin");
		Thread.sleep(2000);
		System.out.println("Past1");
		// 2. Click on user builder href link
		admin_dashboard_page.clickOnTargetSubmenuUsers("Manage Ad-hoc Users (User Builder)");

		Thread.sleep(10000);
		// 3. Click on create course href link
		driver.switchTo().frame(0);
		Thread.sleep(4000);
		mange_adhoc_users_page.clickOnNewUser();
		Thread.sleep(2000);
		System.out.println("Past2");
		create_new_user_window.createNewUser(user, user, "abc@com.com", "111", "111");
		Thread.sleep(1000);

		try {

			driver.switchTo().alert().accept();
		} catch (Exception msg) {

		}
		Thread.sleep(2000);
		for (String window : driver.getWindowHandles()) {
			driver.switchTo().window(window);
			break;
		}
		mange_adhoc_users_page.toAdminDashBoard();
		System.out.println("Past3");
		// 1. Login as INSTRUCTOR.
		/// tegrity.loginAdmin("Admin");
		Thread.sleep(2000);

		String login_url = driver.getCurrentUrl();
		String university_name = login_url.split("/")[2].substring(0, login_url.split("/")[2].length() - 12);

		// 2. Click on course builder href link
		admin_dashboard_page.clickOnTargetSubmenuCourses("Manage Ad-hoc Courses / Enrollments (Course Builder)");
		System.out.println("Past4");
		Thread.sleep(10000);

		// 3. Click on create course href link
		driver.switchTo().frame(0);
		// mange_adhoc_course_enrollments.clickOnNewCourse();
		Thread.sleep(2000);

		mange_adhoc_course_enrollments.clickOnNewCourse();
		create_new_course_window.waitForVisibility(create_new_course_window.course_id_input);
		String PastTempCourse = "PastTempCourse" + sdf.format(date);
		create_new_course_window.createNewCourse(PastTempCourse + "_Name", PastTempCourse + sdf.format(date));

		for (int j = 0; j < 5; j++) {
			try {
				driver.switchTo().alert().accept();
				break;
			} catch (Exception msg) {
				Thread.sleep(1000);
			}
		}
		Thread.sleep(3000);

		// Search target course name
		mange_adhoc_course_enrollments.searchAndFilterCourses(PastTempCourse);
		System.out.println("Past5");
		Thread.sleep(5000);

		// Click on result first course (the only one) membership button
		mange_adhoc_course_enrollments.clickOnFirstCourseMembershipButton();

		// Search target user name in membership window
		mangage_adhoc_courses_membership_window.searchForUser(user);

		// Select first user from user list (the only user it found because of
		// the uniq of the search)
		mangage_adhoc_courses_membership_window.selectFirstUserFromUserList();

		// Add selected user to instructor list
		mangage_adhoc_courses_membership_window.clickOnAddSelectedUserToInstructorList();

		mangage_adhoc_courses_membership_window.waitMaxTimeUntillInstructorEnrollToCourse(user);

		mangage_adhoc_courses_membership_window.waitForVisibility(mangage_adhoc_courses_membership_window.ok_button);

		// Confirm user membership list
		mangage_adhoc_courses_membership_window.clickOnOkButton();

	/*	mangage_adhoc_courses_membership_window.wait.until(ExpectedConditions.invisibilityOfElementLocated(
				By.id("ctl00_ContentPlaceHolder1_ucAddMemberships_ucDialog_ButtonAddInstructor")));
*/
		Thread.sleep(2000);
		// enroll the super user to the course
		// Click on result first course (the only one) membership button
		mange_adhoc_course_enrollments.clickOnFirstCourseMembershipButton();

		// Search target user name in membership window
		mangage_adhoc_courses_membership_window.searchForUser(PropertyManager.getProperty("SuperUser"));

		// Select first user from user list (the only user it found because of
		// the uniq of the search)
		mangage_adhoc_courses_membership_window.selectFirstUserFromUserList();

		// Add selected user to instructor list
		mangage_adhoc_courses_membership_window.clickOnAddSelectedUserToInstructorList();

		mangage_adhoc_courses_membership_window
				.waitMaxTimeUntillInstructorEnrollToCourse(PropertyManager.getProperty("SuperUser"));

		mangage_adhoc_courses_membership_window.waitForVisibility(mangage_adhoc_courses_membership_window.ok_button);

		// Confirm user membership list
		mangage_adhoc_courses_membership_window.clickOnOkButton();

	/*	mangage_adhoc_courses_membership_window.wait.until(ExpectedConditions.invisibilityOfElementLocated(
				By.id("ctl00_ContentPlaceHolder1_ucAddMemberships_ucDialog_ButtonAddInstructor")));
*/
		Thread.sleep(2000);

		for (String window : driver.getWindowHandles()) {
			driver.switchTo().window(window);
			break;
		}
		
		tegrity.signOut();

		System.out.println("Past6");
		// 1. Login with SuperUser.
		tegrity.loginCourses("SuperUser");// log
																					// in
																					// courses
																					// page
		initializeCourseObject();
		course.copyRecordingFromCourseStartWithToCourseStartWithOfType("BankValidRecording", PastTempCourse, 2, record,
				copy, confirm_menu);
		course.verifyRecordingsStatusIsClear("BankValidRecording", 2, record);

		course.signOut();
		System.out.println("Past8");
		// 1. Login as ADMIN.
		Thread.sleep(2000);
		tegrity.loginCoursesByParameter(user);
		Thread.sleep(2000);
		course.signOut();

		tegrity.loginAdmin("Admin");
		Thread.sleep(2000);

		// 2. Click on course builder href link
		admin_dashboard_page.clickOnTargetSubmenuCourses("Manage Ad-hoc Courses / Enrollments (Course Builder)");

		Thread.sleep(10000);

		mange_adhoc_course_enrollments.unEnrollInstructorToCourse(PastTempCourse, user,
				mangage_adhoc_courses_membership_window);

		for (String window : driver.getWindowHandles()) {
			driver.switchTo().window(window);
			break;
		}
		mange_adhoc_course_enrollments.waitForVisibility(driver.findElement(By.id("SignOutLink")));
		driver.findElement(By.id("SignOutLink")).click();

		tegrity.loginCoursesByParameter(user);
		initializeCourseObject();
		course.clickOnPastCoursesTabButton();
		Thread.sleep(2000);		
		course.deleteAllRecordingsInCourseStartWith(PastTempCourse, 2, record, delete_menu);
		course.verifyPastCoursesTabNotDisplayed();

		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
	
	}

	// description = "get courses list"
	public void initializeCourseObject() throws InterruptedException {

		course = PageFactory.initElements(driver, CoursesHelperPage.class);
		course.courses = course.getCoursesListFromElement(course.course_list);
	}
}
