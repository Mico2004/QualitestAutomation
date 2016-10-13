package com.automation.main.past_courses;

import java.security.Signature;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.openqa.selenium.WebDriver;
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

	//@Test(description = "17901 - Create a past course and verify it's appearance accordingly to the enrollement status")
	//public void preTestCreateNewUser() throws InterruptedException {

		/////////// pre-test create new user+enrolling to PastCourseA///////////
		/*
		 * instructor1 = "user03032016011609"; instructor2 =
		 * "user203032016011609"; student = "student03032016011609"; Date date =
		 * new Date(); SimpleDateFormat sdf = new
		 * SimpleDateFormat("ddMMyyyyhhmmss"); instructor1 = "user" +
		 * sdf.format(date); tegrity.loadPage(tegrity.pageUrl,
		 * tegrity.pageTitle); tegrity.loginCourses("SuperUser");// log in
		 * courses page initializeCourseObject(); Thread.sleep(2000);
		 * 
		 * // ownership change int i = 1; String past_course_a =
		 * course.selectCourseThatStartingWith("PastCourseA");
		 * Thread.sleep(3000); course.signOut(); Thread.sleep(2000); instructor2
		 * = "user2" + sdf.format(date); student="student"+sdf.format(date);
		 * tegrity.loginAdmin("Admin"); Thread.sleep(2000);
		 * 
		 * // 2. Click on user builder href link
		 * admin_dashboard_page.clickOnTargetSubmenuUsers(
		 * "Manage Ad-hoc Users (User Builder)");
		 * 
		 * Thread.sleep(10000); // 3. Click on create course href link
		 * driver.switchTo().frame(0); Thread.sleep(4000);
		 * mange_adhoc_users_page.clickOnNewUser(); Thread.sleep(2000);
		 * 
		 * create_new_user_window.createNewUser(instructor1, instructor1,
		 * "abc@com.com", "111", "111"); Thread.sleep(1000);
		 * 
		 * try {
		 * 
		 * driver.switchTo().alert().accept(); } catch (Exception msg) {
		 * 
		 * } Thread.sleep(2000); mange_adhoc_users_page.clickOnNewUser();
		 * Thread.sleep(2000);
		 * 
		 * create_new_user_window.createNewUser(instructor2, instructor2,
		 * "abc@com.com", "111", "111"); Thread.sleep(1000);
		 * 
		 * try {
		 * 
		 * driver.switchTo().alert().accept(); } catch (Exception msg) {
		 * 
		 * } Thread.sleep(2000); mange_adhoc_users_page.clickOnNewUser();
		 * Thread.sleep(2000);
		 * 
		 * create_new_user_window.createNewUser(student, student, "abc@com.com",
		 * "111", "111"); Thread.sleep(1000);
		 * 
		 * try {
		 * 
		 * driver.switchTo().alert().accept(); } catch (Exception msg) {
		 * 
		 * } Thread.sleep(2000); for (String window : driver.getWindowHandles())
		 * { driver.switchTo().window(window); break; }
		 * mange_adhoc_users_page.toAdminDashBoard();
		 * 
		 * // 1. Login as INSTRUCTOR. /// tegrity.loginAdmin("Admin");
		 * Thread.sleep(2000);
		 * 
		 * 
		 * 
		 * // 2. Click on course builder href link
		 * admin_dashboard_page.clickOnTargetSubmenuCourses(
		 * "Manage Ad-hoc Courses / Enrollments (Course Builder)");
		 * 
		 * Thread.sleep(10000);
		 * 
		 * // 3. Click on create course href link driver.switchTo().frame(0); //
		 * mange_adhoc_course_enrollments.clickOnNewCourse();
		 * Thread.sleep(2000);
		 * 
		 * // Search target course name
		 * mange_adhoc_course_enrollments.searchAndFilterCourses(past_course_a);
		 * 
		 * Thread.sleep(7000);
		 * 
		 * // Click on result first course (the only one) membership button
		 * mange_adhoc_course_enrollments.clickOnFirstCourseMembershipButton();
		 * 
		 * Thread.sleep(2000);
		 * mangage_adhoc_courses_membership_window.searchForUser(instructor1);
		 * Thread.sleep(5000);
		 * mangage_adhoc_courses_membership_window.first_user_of_user_list.click
		 * (); Thread.sleep(1000); // Add selected user to instructor list
		 * mangage_adhoc_courses_membership_window.
		 * clickOnAddSelectedUserToInstructorList(); Thread.sleep(3000);
		 * mangage_adhoc_courses_membership_window.searchForUser(instructor2);
		 * Thread.sleep(5000);
		 * mangage_adhoc_courses_membership_window.first_user_of_user_list.click
		 * (); Thread.sleep(1000); // Add selected user to instructor list
		 * mangage_adhoc_courses_membership_window.
		 * clickOnAddSelectedUserToInstructorList(); Thread.sleep(3000);
		 * mangage_adhoc_courses_membership_window.searchForUser(student);
		 * Thread.sleep(5000);
		 * mangage_adhoc_courses_membership_window.first_user_of_user_list.click
		 * (); Thread.sleep(1000); // Add selected user to student list
		 * mangage_adhoc_courses_membership_window.
		 * clickOnAddSelectedUserToStudentList(); Thread.sleep(3000);
		 * mangage_adhoc_courses_membership_window.ok_button.click();
		 * Thread.sleep(1000); driver.switchTo().alert().accept();
		 * Thread.sleep(2000); // Signout for(String window:
		 * driver.getWindowHandles()) { driver.switchTo().window(window); break;
		 * } //driver.quit();
		 * 
		 * mangage_adhoc_courses_membership_window.signOut();
		 * Thread.sleep(2000);
		 * 
		 * /////////////////// changing recording ownership ///////////////////
		 * /////////////////////////////////////////////////////////////////////
		 * ///////////////
		 * 
		 * // 1. Login with instructor 1. driver = new FirefoxDriver();
		 * 
		 * tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
		 * tegrity.loginCoursesByParameter(instructor1);// log in courses page
		 * initializeCourseObject();
		 * 
		 * past_course_a =course.selectCourseThatStartingWith("PastCourseA");
		 * Thread.sleep(3000); //change ownership
		 * record.changeRecordingOwnership(confirm_menu, erp_window,
		 * instructor1,record.getCheckbox());
		 * 
		 * record.signOut();
		 * 
		 * // 3. Login as ADMIN. tegrity.loginAdmin("Admin");
		 * Thread.sleep(2000);
		 * 
		 * // 4. Click on course builder href link
		 * admin_dashboard_page.clickOnTargetSubmenuCourses(
		 * "Manage Ad-hoc Courses / Enrollments (Course Builder)");
		 * 
		 * Thread.sleep(10000);
		 * 
		 * // 5. Click on create course href link driver.switchTo().frame(0);
		 * Thread.sleep(2000);
		 * 
		 * // 6.Search target course name
		 * mange_adhoc_course_enrollments.searchAndFilterCourses(past_course_a);
		 * 
		 * Thread.sleep(7000);
		 * 
		 * // 7.Click on result first course (the only one) membership button
		 * mange_adhoc_course_enrollments.clickOnFirstCourseMembershipButton();
		 * 
		 * Thread.sleep(3000);
		 * mangage_adhoc_courses_membership_window.selectIrUserFromUserList(
		 * mangage_adhoc_courses_membership_window.instructor_elements_list,
		 * instructor1); System.out.println("removed instructor 1");
		 * Thread.sleep(1000); // 8.Add selected user to instructor list
		 * mangage_adhoc_courses_membership_window.
		 * clickOnRemoveSelectedUserToInstructorList(); Thread.sleep(3000); //9.
		 * Click on result first course (the only one) membership button
		 * mange_adhoc_course_enrollments.clickOnFirstCourseMembershipButton();
		 * Thread.sleep(3000);
		 * mangage_adhoc_courses_membership_window.searchForUser(instructor1);
		 * 
		 * Thread.sleep(5000); // 10.Select first user from user list (the only
		 * user it found becauseof the uniq of the search)
		 * mangage_adhoc_courses_membership_window.selectFirstUserFromUserList()
		 * ; Thread.sleep(5000); //11. Add selected user to instructor list
		 * mangage_adhoc_courses_membership_window.
		 * clickOnAddSelectedUserToInstructorList(); Thread.sleep(5000);
		 * 
		 * mangage_adhoc_courses_membership_window.ok_button.click();
		 * Thread.sleep(1000); driver.switchTo().alert().accept();
		 * Thread.sleep(2000);
		 * ///mangage_adhoc_courses_membership_window.signOut(); // Quit browser
		 * 
		 * Thread.sleep(2000);
		 * 
		 * 
		 * 
		 * driver.quit();
		 */
	//}

	@Test(dependsOnMethods = "preTestCreateNewUser")
	public void test17901() throws InterruptedException {
		///// setup
		/*
		 * driver =
		 * DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());
		 * 
		 * tegrity = PageFactory.initElements(driver, LoginHelperPage.class);
		 * 
		 * wait = new WebDriverWait(driver, 30);
		 * 
		 * record = PageFactory.initElements(driver, RecordingHelperPage.class);
		 * copy = PageFactory.initElements(driver, CopyMenu.class); delete_menu
		 * = PageFactory.initElements(driver, DeleteMenu.class); course =
		 * PageFactory.initElements(driver, CoursesHelperPage.class);
		 * confirm_menu = PageFactory.initElements(driver,
		 * ConfirmationMenu.class);
		 * 
		 * wait = new WebDriverWait(driver, 30); move_window =
		 * PageFactory.initElements(driver, MoveWindow.class); erp_window =
		 * PageFactory.initElements(driver, EditRecordinPropertiesWindow.class);
		 * admin_dashboard_page = PageFactory.initElements(driver,
		 * AdminDashboardPage.class);
		 * 
		 * mange_adhoc_course_enrollments = PageFactory.initElements(driver,
		 * ManageAdhocCoursesEnrollmentsPage.class);
		 * 
		 * create_new_course_window = PageFactory.initElements(driver,
		 * CreateNewCourseWindow.class);
		 * 
		 * mange_adhoc_users_page = PageFactory.initElements(driver,
		 * ManageAdhocUsersPage.class);
		 * 
		 * create_new_user_window = PageFactory.initElements(driver,
		 * CreateNewUserWindow.class);
		 * 
		 * mangage_adhoc_courses_membership_window =
		 * PageFactory.initElements(driver,
		 * ManageAdHocCoursesMembershipWindow.class);
		 */

		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		/// test 17901/////

		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
		/// 1. Login as INSTRUCTOR1
		tegrity.loginCourses("SuperUser");// log in courses page
		initializeCourseObject();
		/// 2. Verify that "Past Courses" tab is not displayed

		Thread.sleep(3000);
		String past_course_a = course.selectCourseThatStartingWith("PastCourseA");
		Thread.sleep(3000);

		// 3. Signout
		for (String window : driver.getWindowHandles()) {
			driver.switchTo().window(window);
			break;
		}
		course.signOut();
		System.out.println("Signed out");
		// 4. Login as ADMIN.
		/*
		 * tegrity.loginAdmin("Admin"); Thread.sleep(2000); //5. Click on course
		 * builder href link admin_dashboard_page.clickOnTargetSubmenuCourses(
		 * "Manage Ad-hoc Courses / Enrollments (Course Builder)");
		 * 
		 * Thread.sleep(10000);
		 * 
		 * //6.Un-Enroll instructor1
		 * mange_adhoc_course_enrollments.unEnrollInstructorToCourse(
		 * past_course_a, instructor1, mangage_adhoc_courses_membership_window);
		 * Thread.sleep(2000);
		 * mange_adhoc_course_enrollments.clickOnFirstCourseMembershipButton();
		 * ///9.check instructor 1 is unenrolled instructor2 enrolled and
		 * student enrolled as student Thread.sleep(3000);
		 * mangage_adhoc_courses_membership_window.isInstructorEnrolledToCourse(
		 * false, instructor1); Thread.sleep(3000);
		 * mangage_adhoc_courses_membership_window.isInstructorEnrolledToCourse(
		 * true, instructor2); Thread.sleep(3000);
		 * mangage_adhoc_courses_membership_window.isStudentEnrolledToCourse(
		 * true,student); Thread.sleep(3000);
		 * 
		 * ///10.click on ok
		 * mangage_adhoc_courses_membership_window.ok_button.click();
		 * Thread.sleep(1000); driver.switchTo().alert().accept();
		 * Thread.sleep(2000);
		 * 
		 * 
		 * //11. Signout for(String window: driver.getWindowHandles()) {
		 * driver.switchTo().window(window); break; }
		 * mangage_adhoc_courses_membership_window.signOut();
		 * 
		 * Thread.sleep(2000); //12.login as instructor 1
		 */
		tegrity.loginCourses("User1");
		initializeCourseObject();
		/// 2. Verify that "Past Courses" tab is not displayed

		Thread.sleep(3000);
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

		Thread.sleep(3000);
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
		// 21.login as student
		/*
		 * tegrity.loginCoursesByParameter(student); Thread.sleep(2000);
		 * //22.Click Past Courses Tab button (without selecting a recording)
		 * course = PageFactory.initElements(driver, CoursesHelperPage.class);
		 * //23.verify patcourses tab not exist course.verifyNoPastCoursesTab();
		 * //24.verify active courses exists
		 * course.clickOnActiveCoursesTabButton(); //25. Signout for(String
		 * window: driver.getWindowHandles()) {
		 * driver.switchTo().window(window); break; } course.signOut();
		 */

		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
	}

	// description = "get courses list"
	public void initializeCourseObject() throws InterruptedException {

		course = PageFactory.initElements(driver, CoursesHelperPage.class);
		course.courses = course.getCoursesListFromElement(course.course_list);
	}

}
