package com.automation.main.past_courses;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.openqa.selenium.By;
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
import com.automation.main.page_helpers.EditRecordingPropertiesWindow;
import com.automation.main.page_helpers.LoginHelperPage;
import com.automation.main.page_helpers.ManageAdHocCoursesMembershipWindow;
import com.automation.main.page_helpers.ManageAdhocCoursesEnrollmentsPage;
import com.automation.main.page_helpers.ManageAdhocUsersPage;
import com.automation.main.page_helpers.MoveWindow;
import com.automation.main.page_helpers.RecordingHelperPage;

import java.text.DateFormat;
import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import junitx.util.PropertyManager;

public class TC17941VerifyCourseDisplayedInActiveCoursesTabAfterInstructorReEnrolled {

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
	String instructor1;
	String instructor2;
	String student;
    List<String> for_enroll;
    
	@BeforeClass
	public void setup() {

		driver = new FirefoxDriver();
		

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
		
		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC17941VerifyCourseDisplayedInActiveCoursesTabAfterInstructorReEnrolled at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC17941VerifyCourseDisplayedInActiveCoursesTabAfterInstructorReEnrolled at " + DateToStr,
		 "Starting the test: TC17941VerifyCourseDisplayedInActiveCoursesTabAfterInstructorReEnrolled at " + DateToStr, LogAs.PASSED, null);	

	}

	@AfterClass
	public void closeBroswer() {		
		this.driver.quit();
	}
	
	@Test (description = "TC 17941 Verify Course Displayed In Active Courses Tab After Instructor Re Enrolled")
	public void test17941() throws InterruptedException {

		// 1.load page
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);

		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyhhmmss");
		instructor1 = "user" + sdf.format(date);
		    for_enroll=new ArrayList<String>();
			for_enroll.add(instructor1);
			
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
		tegrity.loginCourses("SuperUser");// log in courses page
		
		// // ownership change
		String past_course_a = course.selectCourseThatStartingWith("PastCourseA");/// get
																					/// course																			/// name
		course.signOut();

		tegrity.loginAdmin("Admin");
	
		// 2. Click on user builder href link
		admin_dashboard_page.clickOnTargetSubmenuUsers("Manage Ad-hoc Users (User Builder)");
  
    	mange_adhoc_users_page.waitForPageToLoad();
    	mange_adhoc_users_page.createNewUser(instructor1, create_new_user_window);
		Thread.sleep(2000);
		for (String window : driver.getWindowHandles()) {
			driver.switchTo().window(window);
			break;
		}
		mange_adhoc_users_page.toAdminDashBoard();

		Thread.sleep(5000);

		for (String window : driver.getWindowHandles()) {
			driver.switchTo().window(window);
			break;
		}
		Thread.sleep(4000);
		// 3.click on course builder
		admin_dashboard_page.clickOnTargetSubmenuCourses("Manage Ad-hoc Courses / Enrollments (Course Builder)");

		// 3. Click on create course href link
		mange_adhoc_course_enrollments.waitForThePageToLoad();
        mange_adhoc_course_enrollments.enrollInstructorToCourse(past_course_a,for_enroll,mangage_adhoc_courses_membership_window);
		Thread.sleep(2000);

		for (String window : driver.getWindowHandles()) {
			driver.switchTo().window(window);
			break;
		}

		mange_adhoc_course_enrollments.clickOnAdminDashboard();
		Thread.sleep(2000);
		admin_dashboard_page.signOut();
	

		////// ownership change
		tegrity.loginCoursesByParameter(instructor1);

		course.selectCourseThatStartingWith(past_course_a);

        record.changeRecordingOwnership(confirm_menu, erp_window,instructor1,record.getCheckbox());
		record.signOut();

		/////////////////////////////////////////////// end of preset

		// 2.login as admin
		tegrity.loginAdmin("Admin");// log in courses page

		// 3.click on course builder
		admin_dashboard_page.clickOnTargetSubmenuCourses("Manage Ad-hoc Courses / Enrollments (Course Builder)");

		mange_adhoc_course_enrollments.waitForThePageToLoad();
        mange_adhoc_course_enrollments.unEnrollInstructorToCourse(past_course_a,instructor1,mangage_adhoc_courses_membership_window);
		Thread.sleep(2000);
		/// 6. sign out
		for (String window : driver.getWindowHandles()) {
			driver.switchTo().window(window);
			break;
		}

		mange_adhoc_course_enrollments.clickOnAdminDashboard();
		Thread.sleep(3000);
		admin_dashboard_page.sign_out.click();

		Thread.sleep(2000);
		/// 7.login as instructor
		tegrity.loginCoursesByParameter(instructor1);
		initializeCourseObject();
	;
		// 8.click on past couses tab
		course.clickOnPastCoursesTabButton();
	
		// 9.Verify that the deleted course is displayed in "Past Courses" tab
		try {
			course.selectCourseByName(past_course_a);
			System.out.println("contains course: " + past_course_a);
			ATUReports.add("contains course: " + past_course_a, LogAs.PASSED, null);
			Assert.assertTrue(true);
		} catch (Exception e) {
			System.out.println(" doe not contain course: " + past_course_a);
			ATUReports.add(" doe not contain course: " + past_course_a, LogAs.FAILED, null);
			Assert.assertTrue(false);
		}

		// 10.Sign out
		record.signOut();
	
		// 11.login as admin
		tegrity.loginAdmin("Admin");
	
	// 12.click on course builder
		admin_dashboard_page.clickOnTargetSubmenuCourses("Manage Ad-hoc Courses / Enrollments (Course Builder)");

		mange_adhoc_course_enrollments.waitForThePageToLoad();
		mange_adhoc_course_enrollments.enrollInstructorToCourse(past_course_a, for_enroll, mangage_adhoc_courses_membership_window);
		Thread.sleep(2000);

		/// 16. sign out
		for (String window : driver.getWindowHandles()) {
			driver.switchTo().window(window);
			break;
		}

		mange_adhoc_course_enrollments.clickOnAdminDashboard();
		Thread.sleep(3000);
		admin_dashboard_page.sign_out.click();
		/// 17.login as instructor
		tegrity.loginCoursesByParameter(instructor1);

		// 18.Verify that the course is displayed in "Active Courses" tab
		course.clickOnActiveCoursesTabButton();
		initializeCourseObject();
		boolean result = course.verifyCourseExist(past_course_a);
		if (result == true) {
			System.out.println("course exists");
			ATUReports.add("course exists", LogAs.PASSED, null);
			Assert.assertTrue(result);
		} else {
			System.out.println("course does not exists");
			ATUReports.add("course exists", LogAs.FAILED, null);
			Assert.assertTrue(result);
		}
	   //19.Verify that "Past Courses" tab is not displayed
		
		course.verifyNoPastCoursesTab();
		
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
	}

	// description = "get courses list"
	public void initializeCourseObject() throws InterruptedException {

		course = PageFactory.initElements(driver, CoursesHelperPage.class);
		course.courses = course.getCoursesListFromElement(course.course_list);
	}

}
