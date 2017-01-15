package com.automation.main.past_courses;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.DateFormat;
import java.util.Date;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
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

import org.testng.annotations.AfterClass;
import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import junitx.util.PropertyManager;

public class TC24835VerifyStudentsCantSeePastCourseTab {
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
	List<String> list_superuser;
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
		 System.out.println("Starting the test: TC24835VerifyStudentsCantSeePastCourseTab at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC24835VerifyStudentsCantSeePastCourseTab at " + DateToStr,
		 "Starting the test: TC24835VerifyStudentsCantSeePastCourseTab at " + DateToStr, LogAs.PASSED, null);
		
	}

	@Test (description = "TC 24835 Verify Students Cant See Past Course Tab")
	public void test24835() throws InterruptedException {
		// 1.load page
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);

		String student = PropertyManager.getProperty("User4");
		String superuser = PropertyManager.getProperty("SuperUser");
		// String
		// course_with_one_additional_content="CourseOneAdittionalContent08032016050918";
		String past_course_student2="StudentPastCourse212032016051528";
		String past_course_student ="StudentPastCourse12032016051528";
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyhhmmss");
		past_course_student = "StudentPastCourse" + sdf.format(date);
		past_course_student2 = "StudentPastCourse2" + sdf.format(date);
		list_superuser = new ArrayList<String>();
		list_student = new ArrayList<String>();

		list_superuser.add(superuser);
		list_student.add(student);

		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);

		tegrity.loginAdmin("Admin");
	

		// 4. Click on course builder href link
		admin_dashboard_page.clickOnTargetSubmenuCourses("Manage Ad-hoc Courses / Enrollments (Course Builder)");
		mange_adhoc_course_enrollments.waitForThePageToLoad();
		/// 5.build new course past course 1	
		mange_adhoc_course_enrollments.clickOnNewCourse();
		Thread.sleep(3000);

		create_new_course_window.createNewCourse(past_course_student, past_course_student);
		Thread.sleep(1000);
		try {
			driver.switchTo().alert().accept();

		} catch (Exception msg) {

		}
		Thread.sleep(1000);

		for (String window : driver.getWindowHandles()) {
			driver.switchTo().window(window);
			break;
		}
		
		
		/// 5.build new course past course 2
		driver.switchTo().frame(0);
		Thread.sleep(4000);
		mange_adhoc_course_enrollments.clickOnNewCourse();
		Thread.sleep(3000);
		create_new_course_window.createNewCourse(past_course_student2, past_course_student2);
		Thread.sleep(1000);
		try {
		
		mange_adhoc_course_enrollments.clickOkInAlertIfPresent();

		} catch (Exception msg) {
			System.out.print("Alert Handle"+msg.getMessage());

		}
		Thread.sleep(1000);

		for (String window : driver.getWindowHandles()) {
			driver.switchTo().window(window);
			break;
		}
		Thread.sleep(3000);
		// enroll instructors
		mange_adhoc_course_enrollments.enrollInstructorToCourse(past_course_student, list_superuser,mangage_adhoc_courses_membership_window);
		Thread.sleep(5000);

		for (String window : driver.getWindowHandles()) {
			driver.switchTo().window(window);
			break;
		}
		Thread.sleep(3000);
		// enroll student
		mange_adhoc_course_enrollments.enrollStudentsToCourse(past_course_student, list_student,
				mangage_adhoc_courses_membership_window);
		Thread.sleep(5000);
		for (String window : driver.getWindowHandles()) {
			driver.switchTo().window(window);
			break;
		}
		Thread.sleep(3000);
		// enroll instructors
		mange_adhoc_course_enrollments.enrollInstructorToCourse(past_course_student2, list_superuser,
				mangage_adhoc_courses_membership_window);
		Thread.sleep(5000);

		for (String window : driver.getWindowHandles()) {
			driver.switchTo().window(window);
			break;
		}
		mange_adhoc_course_enrollments.enrollStudentsToCourse(past_course_student2, list_student,mangage_adhoc_courses_membership_window);
		Thread.sleep(5000);
		for (String window : driver.getWindowHandles()) {
			driver.switchTo().window(window);
			break;
		}
		mange_adhoc_course_enrollments.clickOnAdminDashboard();
		Thread.sleep(2000);

		admin_dashboard_page.signOut();
	
		tegrity.loginCourses("SuperUser");				
		initializeCourseObject();
			
		course.copyRecordingFromCourseStartWithToCourseStartWithOfType("BankValidRecording", past_course_student, 0, record, copy, confirm_menu);
		course.copyRecordingFromCourseStartWithToCourseStartWithOfType("BankValidRecording", past_course_student2, 0, record, copy, confirm_menu);
		course.verifyRecordingsStatusIsClear("BankValidRecording", 0,record); 
		
		course.signOut();
		tegrity.waitForVisibility(tegrity.button_login);
		driver.quit();

		//// End of Preset/////////////

		driver = DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());
		
		
	////setup!!!!!!!!!!!!!
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
		
			
		// 1.load page
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
		// 2.login as admin
		tegrity.waitForVisibility(tegrity.button_login);
		tegrity.loginAdmin("Admin");
		

		// 3. Click on course builder href link
		admin_dashboard_page.clickOnTargetSubmenuCourses("Manage Ad-hoc Courses / Enrollments (Course Builder)");
		mange_adhoc_course_enrollments.waitForThePageToLoad();
		/// 4.unenroll student from course
		mange_adhoc_course_enrollments.unEnrollStusentsFromCourse(past_course_student, student,mangage_adhoc_courses_membership_window);
		Thread.sleep(4000);

		/// 5.sign out
		for (String window : driver.getWindowHandles()) {
			driver.switchTo().window(window);
			break;
		}

		mange_adhoc_users_page.toAdminDashBoard();
		admin_dashboard_page.waitForPageToLoad();
		admin_dashboard_page.signOut();
	
		/// 6. login as student
		tegrity.loginCoursesByParameter(student);	
		initializeCourseObject();
		// 7.Verify the 'Past Courses' tab isn't displayed
		course.verifyNoPastCoursesTab();
	
		course.clickOnActiveCoursesTabButton();
	
		// 8.Verfiy the un-enrolled course isn't displayed in the 'Active
		// Courses' tab
		initializeCourseObject();
		course.verifyCourseNotExist(past_course_student);
		// 9.sign out
		course.signOut();
		// 10.login as admin
		tegrity.loginAdmin("Admin");

		//11.click on manage ad-hoc course
        admin_dashboard_page.clickOnTargetSubmenuCourses("Manage Ad-hoc Courses / Enrollments (Course Builder)");
		Thread.sleep(10000);
		//12.delete course by name
		mange_adhoc_course_enrollments.deleteCourseByNameSearch(past_course_student2);
		Thread.sleep(3000);
		///13.sign out
		for (String window : driver.getWindowHandles()) {
		driver.switchTo().window(window);
		break;
		}	
		mange_adhoc_course_enrollments.clickOnAdminDashboard();
		Thread.sleep(2000);
        admin_dashboard_page.signOut();
    
    	//14.login as a student
    	tegrity.loginCoursesByParameter(student);
    	
    	// 15.Verify the 'Past Courses' tab isn't displayed
    	course.verifyNoPastCoursesTab();
 
    	course.clickOnActiveCoursesTabButton();
			
    	// 16.Verfiy the un-enrolled course isn't displayed in the 'Active
    	// Courses' tab
    	initializeCourseObject();
    	course.verifyCourseNotExist(past_course_student);
    	course.verifyCourseNotExist(past_course_student2);
    			
    	// 17.sign out
    	course.signOut();
    		
    	System.out.println("Done.");
    	ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);		
    			
	}

	// description = "get courses list"
	public void initializeCourseObject() throws InterruptedException {


		course = PageFactory.initElements(driver, CoursesHelperPage.class);		
		course.courses = course.getCoursesListFromElement(course.course_list);
	}
	
	@AfterClass
	public void closeBroswer() {
	
		this.driver.quit();
	}
}
