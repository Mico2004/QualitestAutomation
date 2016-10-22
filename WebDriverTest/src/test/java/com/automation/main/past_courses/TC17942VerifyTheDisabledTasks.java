package com.automation.main.past_courses;

import org.openqa.selenium.WebDriver;import com.automation.main.page_helpers.Page;
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
import com.automation.main.page_helpers.EditRecordinPropertiesWindow;
import com.automation.main.page_helpers.LoginHelperPage;
import com.automation.main.page_helpers.ManageAdHocCoursesMembershipWindow;
import com.automation.main.page_helpers.ManageAdhocCoursesEnrollmentsPage;
import com.automation.main.page_helpers.ManageAdhocUsersPage;
import com.automation.main.page_helpers.MoveWindow;
import com.automation.main.page_helpers.RecordingHelperPage;
import com.automation.main.utilities.DriverSelector;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import java.text.DateFormat;
import java.util.Date;
import org.testng.annotations.AfterClass;

public class TC17942VerifyTheDisabledTasks {
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
		 System.out.println("Starting the test: TC17942VerifyTheDisabledTasks at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC17942VerifyTheDisabledTasks at " + DateToStr,
		 "Starting the test: TC17942VerifyTheDisabledTasks at " + DateToStr, LogAs.PASSED, null);

	}
	

	@AfterClass
	public void closeBroswer() {
	
		this.driver.quit();
	}
	
	@Test (description = "TC 17942 Verify The Disabled Tasks")
	public void test17942() throws InterruptedException {
		// 1.load page
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
		// 2.login as instructor
        tegrity.loginCourses("User1");// log in courses page
		Thread.sleep(Page.TIMEOUT_TINY);
	//3.click on past couses tab
		course.clickOnPastCoursesTabButton();
		Thread.sleep(Page.TIMEOUT_TINY);
		//4.select course
		course.selectCourseThatStartingWith("PastCourseA");
		//5.Verify that "Start Recording" button is not displayed

	    record.verifyNoStartRecording();
	    //6.verify no start teest button
	    record.verifyNoStartTest();
	    
	    //7.Verify that next menu items are not displayed:
        record.verifyNoNextMenuItemsDisplayed();
        
        System.out.println("Done.");
        ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
	}
}
