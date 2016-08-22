package com.automation.main;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.text.DateFormat;
import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import junitx.util.PropertyManager;

public class TC17929And17941And17944PastCourses {
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
	public Page page;
	String instructor1;
	String instructor2;
	List<String> for_enroll;
	String user;
	String PastTempCourse;
	String PastTemp2Course;

	@BeforeClass
	public void setup() {

		driver = DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());

		tegrity = PageFactory.initElements(driver, LoginHelperPage.class);

		wait = new WebDriverWait(driver, 30);
		page=PageFactory.initElements(driver, Page.class);
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

		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyhhmmss");
		user = "Past17929TempUser" + sdf.format(date);
		PastTempCourse = "Past17929TempCourse" + sdf.format(date);
		PastTemp2Course = "Past17929Temp2Course" + sdf.format(date);

		Date curDate = new Date();
		String DateToStr = DateFormat.getInstance().format(curDate);
		System.out.println("Starting the test: TC17929And17941And17944PastCourses at " + DateToStr);
		ATUReports.add("Message window.",
				"Starting the test: TC17929And17941And17944PastCourses at " + DateToStr,
				"Starting the test: TC17929And17941And17944PastCourses at " + DateToStr, LogAs.PASSED, null);

	}

	@AfterClass
	public void closeBroswer() {
		this.driver.quit();
	}

	@Test
	public void test17929() throws Exception {
		
		
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
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

		create_new_course_window.createNewCourse(PastTempCourse + "_Name", PastTempCourse);

		for (int j = 0; j < 5; j++) {
			try {
				driver.switchTo().alert().accept();
				break;
			} catch (Exception msg) {
				Thread.sleep(1000);
			}
		}
		Thread.sleep(3000);

		mange_adhoc_course_enrollments.clickOnNewCourse();
		create_new_course_window.waitForVisibility(create_new_course_window.course_id_input);

		create_new_course_window.createNewCourse(PastTemp2Course + "_Name", PastTemp2Course);

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

		// Search target course name
		mange_adhoc_course_enrollments.searchAndFilterCourses(PastTemp2Course);
		System.out.println("Past5_1");
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
		mange_adhoc_course_enrollments.waitForVisibility(driver.findElement(By.id("SignOutLink")));
		driver.findElement(By.id("SignOutLink")).click();

		System.out.println("Past6");

		// login with instructor so the courses will be registered in DB
		Thread.sleep(2000);
		tegrity.loginCoursesByParameter(user);
		Thread.sleep(2000);
		course.signOut();

		// *****************
		// 1. Login with SuperUser.

		tegrity.loginCoursesByParameter(user);
		Thread.sleep(3000);
		course.selectCourseThatStartingWith(PastTempCourse);
		Thread.sleep(3000);
		record.toUploadAdditionalContentFile(confirm_menu);
		Thread.sleep(2000);

		Thread.sleep(2000);
		for (String window : driver.getWindowHandles()) {
			driver.switchTo().window(window);
			break;
		}
		course.sign_out.click();

		tegrity.loginCourses("SuperUser");
		Thread.sleep(3000);
		course.selectCourseThatStartingWith(PastTemp2Course);
		Thread.sleep(3000);
		record.toUploadAdditionalContentFile(confirm_menu);
		Thread.sleep(2000);

		Thread.sleep(2000);
		for (String window : driver.getWindowHandles()) {
			driver.switchTo().window(window);
			break;
		}
		course.sign_out.click();

		// unenroll user from courses

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

		mange_adhoc_course_enrollments.unEnrollInstructorToCourse(PastTemp2Course, user,
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
		course.verifyCourseNotExist(PastTemp2Course + "_Name");

		course.deleteAllRecordingsInCourseStartWith(PastTempCourse, 1, record, delete_menu);
		course.verifyPastCoursesTabNotDisplayed();
		course.signOut();

		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);

	}

	@Test(dependsOnMethods = "test17929")
	public void test17941() throws Exception {

		Thread.sleep(2000);

		tegrity.loginAdmin("Admin");
		Thread.sleep(2000);
		System.out.println("Past1");

		// 2. Click on course builder href link
		admin_dashboard_page.clickOnTargetSubmenuCourses("Manage Ad-hoc Courses / Enrollments (Course Builder)");
		System.out.println("Past4");
		Thread.sleep(10000);

		// 3. Click on create course href link
		driver.switchTo().frame(0);
		// mange_adhoc_course_enrollments.clickOnNewCourse();

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

		// Search target course name
		mange_adhoc_course_enrollments.searchAndFilterCourses(PastTemp2Course);
		System.out.println("Past5_1");
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

		for (String window : driver.getWindowHandles()) {
			driver.switchTo().window(window);
			break;
		}
		mange_adhoc_course_enrollments.waitForVisibility(driver.findElement(By.id("SignOutLink")));
		driver.findElement(By.id("SignOutLink")).click();

		System.out.println("Past6");

		// login with instructor so the courses will be registered in DB
		Thread.sleep(2000);
		tegrity.loginCoursesByParameter(user);
		Thread.sleep(2000);
		course.verifyCourseExist(PastTempCourse);
		course.signOut();

	}

	@Test(dependsOnMethods = "test17941")
	public void test17944() throws Exception {
		Thread.sleep(2000);
		tegrity.loginCoursesByParameter(user);
		Thread.sleep(3000);
		course.selectCourseThatStartingWith(PastTempCourse);
		Thread.sleep(3000);
		record.toUploadAdditionalContentFile(confirm_menu);
		Thread.sleep(2000);

		Thread.sleep(2000);
		for (String window : driver.getWindowHandles()) {
			driver.switchTo().window(window);
			break;
		}
		course.sign_out.click();

		Thread.sleep(2000);

		tegrity.loginAdmin("Admin");
		Thread.sleep(2000);
		System.out.println("Past1");

		// 2. Click on course builder href link
		admin_dashboard_page.clickOnTargetSubmenuCourses("Manage Ad-hoc Courses / Enrollments (Course Builder)");
		System.out.println("Past4");
		Thread.sleep(10000);

		// 3. Click on create course href link
		driver.switchTo().frame(0);
		// mange_adhoc_course_enrollments.clickOnNewCourse();

		Thread.sleep(3000);

		// Search target course name
		mange_adhoc_course_enrollments.searchAndFilterCourses(PastTempCourse);
		System.out.println("Past5");
		Thread.sleep(5000);

		// Click on result first course (the only one) membership button
		mange_adhoc_course_enrollments.clickOnFirstCourseDeleteButton();

		// Confirm user membership list			
		page.waitForAlert(60);
		try {
			
			driver.switchTo().alert().accept();			
			System.out.println("Accecpt alert message.");	
			Thread.sleep(2000);
		}catch (Exception msg) {
			System.out.println("Accecpt alert message.");			
			}
		

		for (String window : driver.getWindowHandles()) {
			driver.switchTo().window(window);
			break;
		}
		mange_adhoc_course_enrollments.waitForVisibility(driver.findElement(By.id("SignOutLink")));
		driver.findElement(By.id("SignOutLink")).click();

		System.out.println("Past6");

		// login with instructor so the courses will be registered in DB
		Thread.sleep(2000);
		tegrity.loginCoursesByParameter(user);
		Thread.sleep(2000);
		course.clickOnPastCoursesTabButton();
		course.verifyCourseExist(PastTempCourse);
		course.signOut();

	}

	// description = "get courses list"
	public void initializeCourseObject() throws InterruptedException {

		course = PageFactory.initElements(driver, CoursesHelperPage.class);
		course.courses = course.getStringFromElement(course.course_list);
	}
}
