package com.automation.main.pre_post_test;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.util.Date;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.automation.main.page_helpers.AdminDashboardPage;
import com.automation.main.page_helpers.AdminDashboardViewCourseList;
import com.automation.main.page_helpers.ConfirmationMenu;
import com.automation.main.page_helpers.CopyMenu;
import com.automation.main.page_helpers.CoursesHelperPage;
import com.automation.main.page_helpers.CreateNewCourseWindow;
import com.automation.main.page_helpers.CreateNewUserWindow;
import com.automation.main.page_helpers.LoginHelperPage;
import com.automation.main.page_helpers.ManageAAIRS;
import com.automation.main.page_helpers.ManageAdHocCoursesMembershipWindow;
import com.automation.main.page_helpers.ManageAdhocCoursesEnrollmentsPage;
import com.automation.main.page_helpers.ManageAdhocUsersPage;
import com.automation.main.page_helpers.ManageExcelCoursesEnrollments;
import com.automation.main.page_helpers.RecordingHelperPage;
import com.automation.main.utilities.DriverSelector;

import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;
import junitx.util.PropertyManager;


@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class preTestUniversity {

	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}

	public LoginHelperPage tegrity;
	WebDriver driver;
	WebDriverWait wait;
	public static WebDriver thread_driver;
	public AdminDashboardPage admin_dashboard_page;
	public ManageAdhocCoursesEnrollmentsPage mange_adhoc_course_enrollments;
	public CreateNewCourseWindow create_new_course_window;
	public ManageAdhocUsersPage mange_adhoc_users_page;
	public CreateNewUserWindow create_new_user_window;
	public ManageAdHocCoursesMembershipWindow mangage_adhoc_courses_membership_window;
	public ManageExcelCoursesEnrollments manageExcelCoursesEnrollments;
	public CoursesHelperPage course;
	public ManageAAIRS manageAAIRS;
	public RecordingHelperPage record;
	public AdminDashboardViewCourseList admin_Dashboard_view_courseList;
	CopyMenu copy;
	public ConfirmationMenu confirm_menu;
	String SuperUsername="";
	String user1Username="";
	
	
	public void initializeCourseObject() throws InterruptedException {

		course = PageFactory.initElements(driver, CoursesHelperPage.class);
		course.courses = course.getCoursesListFromElement(course.course_list);
	}
	
	@BeforeClass
	public void setup() {

		//driver = new FirefoxDriver();
		driver = DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());
		ATUReports.add("selected browser type", LogAs.PASSED, new CaptureScreen( ScreenshotOf.DESKTOP));

		tegrity = PageFactory.initElements(driver, LoginHelperPage.class);
		
		admin_dashboard_page = PageFactory.initElements(driver, AdminDashboardPage.class);
		
		mange_adhoc_course_enrollments = PageFactory.initElements(driver, ManageAdhocCoursesEnrollmentsPage.class);
		
		create_new_course_window = PageFactory.initElements(driver, CreateNewCourseWindow.class);
		
		mange_adhoc_users_page = PageFactory.initElements(driver, ManageAdhocUsersPage.class);
		
		create_new_user_window = PageFactory.initElements(driver, CreateNewUserWindow.class);
		
		mangage_adhoc_courses_membership_window = PageFactory.initElements(driver, ManageAdHocCoursesMembershipWindow.class);
		
		admin_Dashboard_view_courseList = PageFactory.initElements(driver, AdminDashboardViewCourseList.class);
		
		manageAAIRS =  PageFactory.initElements(driver,ManageAAIRS.class);
		
		record = PageFactory.initElements(driver, RecordingHelperPage.class);
		
		copy = PageFactory.initElements(driver, CopyMenu.class);
		
		confirm_menu = PageFactory.initElements(driver, ConfirmationMenu.class);
		
		manageExcelCoursesEnrollments = PageFactory.initElements(driver,ManageExcelCoursesEnrollments.class);
		
		wait = new WebDriverWait(driver, 30);
		
		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: PreTest at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: PreTest at " + DateToStr,
		 "Starting the test: PreTest at " + DateToStr, LogAs.PASSED, null);
		
	}
	
	@AfterClass
	public void closeBroswer() {
		this.driver.quit();
	}

	@Test(description = "pre Test University")
	public void preTestUniversity() throws InterruptedException
	{
		
		String excelmem = "src/test/resources/excelmem.xls";
		String users = "src/test/resources/users.xls";
		
		//login as instructor
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);

		//3.add records from the bank to the automation course
		tegrity.loginAdmin("Admin");
		
		//4. enter to manage AAIRS
		admin_dashboard_page.clickOnTargetSubmenuIntegration("Manage AAIRS");
		
		//5. add excel properties
		manageAAIRS.waitForPageToLoad();
		manageAAIRS.AddExcelImportToAuthentication();
		
		//6.return to the admin dashboard
		manageAAIRS.clickOnAdminDashboard();
		
		//7. click on Manage Excel Courses / Enrollments
		admin_dashboard_page.clickOnTargetSubmenuCourses("Manage Excel Courses / Enrollments");
		
		//8. wait until the page finish loading
		manageExcelCoursesEnrollments.waitForPageToLoad();
		
		//9.upload excelmem
		manageExcelCoursesEnrollments.uploadExcelFile(excelmem);
		manageExcelCoursesEnrollments.clickElement(manageExcelCoursesEnrollments.importButton);
		manageExcelCoursesEnrollments.checkThatTheUploadSucceded();
		
		//10.upload users
		manageExcelCoursesEnrollments.uploadExcelFile(users);
		manageExcelCoursesEnrollments.clickElement(manageExcelCoursesEnrollments.importButton);
		manageExcelCoursesEnrollments.checkThatTheUploadSucceded();
		
		//signout
		tegrity.signOut();
		
		
		//1.enter to each user to see him at the owner edit records properties
		for(int i=1 ; i<=2; i++){
			tegrity.loginCoursesByParameter("automationInstructorUser" +i);
			tegrity.signOut();
		}
	
		//2.enter to each user to see him at the owner edit records properties
		for(int i=1 ; i<=135; i++){
			tegrity.loginCoursesByParameter("automationStudentUser" +i);
			tegrity.signOut();
		}
		
		//3.add records from the bank to the automation course
		tegrity.loginAdmin("Admin");
		
		//4.enter to View Course List
		admin_dashboard_page.clickOnTargetSubmenuCourses("View Course List");
		
		//5.enter to the bank 
		admin_Dashboard_view_courseList.searchForTargetCourseName("BankValid");
		admin_Dashboard_view_courseList.clickOnFirstCourseLink();
		
		//6.copy records form the bank to the instructor course
		//select all the checkboxs
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.check_all_checkbox);
		
		//7.hover on the recording tasks and click on copy
		record.clickOnRecordingTaskThenCopy();
		
		//8.enter the instructor and and click on the list 
		copy.copyFromAdmin(PropertyManager.getProperty("StaticInstructor"));
		
		//9.select the static course
		copy.selectTargetCourseFromCourseList(PropertyManager.getProperty("StaticCourse"));
		
		//10.click on the copy button
		copy.clickOnCopyButton();
		
		//11.click on the ok button
		confirm_menu.clickOnOkButtonAfterConfirmCopyRecordings();
		
		//12.wait until the status finish
		record.checkStatusExistenceForMaxTTime(6000);
		
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
	}
	
}