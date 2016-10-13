package com.automation.main.private_courses;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.text.DateFormat;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.automation.main.page_helpers.AdminDashboardPage;
import com.automation.main.page_helpers.AdminDashboardViewCourseList;
import com.automation.main.page_helpers.AdvancedServiceSettingsPage;
import com.automation.main.page_helpers.ConfirmationMenu;
import com.automation.main.page_helpers.CopyMenu;
import com.automation.main.page_helpers.CourseSettingsPage;
import com.automation.main.page_helpers.CoursesHelperPage;
import com.automation.main.page_helpers.CreateNewUserWindow;
import com.automation.main.page_helpers.DeleteMenu;
import com.automation.main.page_helpers.LoginHelperPage;
import com.automation.main.page_helpers.ManageAdHocCoursesMembershipWindow;
import com.automation.main.page_helpers.ManageAdhocCoursesEnrollmentsPage;
import com.automation.main.page_helpers.ManageAdhocUsersPage;
import com.automation.main.page_helpers.MoveWindow;
import com.automation.main.page_helpers.RecordingHelperPage;
import com.automation.main.page_helpers.TopBarHelper;
import com.automation.main.utilities.DriverSelector;

import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;

@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC19317VerifyThatPrivateCourseIsNotAccessibleForStudents {

	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}

	public TC19317VerifyThatPrivateCourseIsNotAccessibleForStudents() {
		// TODO Auto-generated constructor stub
	}

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
	public AdvancedServiceSettingsPage advanced_service_settings_page;

	@BeforeClass
	public void setup() {

		 driver = DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());
		 
		 
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
			advanced_service_settings_page = PageFactory.initElements(driver, AdvancedServiceSettingsPage.class);
			
			//DesiredCapabilities capabilities = null;
			//capabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);
			
			wait = new WebDriverWait(driver, 30);
		 
		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC19317VerifyThatPrivateCourseIsNotAccessibleForStudents at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC19317VerifyThatPrivateCourseIsNotAccessibleForStudents at " + DateToStr,
		 "Starting the test: TC19317VerifyThatPrivateCourseIsNotAccessibleForStudents at " + DateToStr, LogAs.PASSED, null);	
		
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

	@Test (description="TC 19317 Verify That Private Course Is Not Accessible For Students")
	public void test19317() throws InterruptedException
	{
		// 1. Login as User1 and get the full name of Ab course.
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
		tegrity.loginCourses("User1");// log in courses page
		initializeCourseObject();
		
		// 1.1. Get the of Ab course.
		String instructor_public_course = course.selectCourseThatStartingWith("Ab");
		
		// 2. Logout.
		top_bar_helper.signOut();
		
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
		
		// 3. Create a new user, don't enroll him to any courses.
		tegrity.loginAdmin("Admin");	
		 
		 Thread.sleep(2000);
		 
//		 admin_dashboard_page.clickOnTargetSubmenuAdvancedServices("Advanced Service Settings");
//		 
//		 advanced_service_settings_page.disableEulaCheckboxAndClickOk(confirm_menu);
//		 
//		 wait.until(ExpectedConditions.visibilityOf(admin_dashboard_page.users_submenu.get(1)));
		 	 
		 admin_dashboard_page.clickOnTargetSubmenuUsers("Manage Ad-hoc Users (User Builder)");
		 
		 Date date = new Date();
		 SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyhhmmss");
		 String temp_student_user_name = "StudentTemp" + sdf.format(date);
		  
		 for(int i=0; i<5; i++ ) {
				try {
					driver.switchTo().frame(0);
					break;
				} catch(Exception msg) {
					Thread.sleep(1000);
				}
		}
		 
		wait.until(ExpectedConditions.visibilityOf(mange_adhoc_users_page.new_user_button));
		 
		 mange_adhoc_users_page.clickOnNewUser();
			
		 create_new_user_window.createNewUser(temp_student_user_name, temp_student_user_name, "abc@com.com", "111", "111");
		 
		
		 for(String window: driver.getWindowHandles()) {
		 	driver.switchTo().window(window);
		 	break;
		 }
		 
		 manage_adhoc_courses_enrollments_page.clickOnAdminDashboard();
			
			
		 for(String window: driver.getWindowHandles()) {
			driver.switchTo().window(window);
			break;
		}
			
			
		top_bar_helper.signOut();
		
		
		// 4. Login as USER.
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
		tegrity.loginCoursesByParameter(temp_student_user_name);// log in courses page
		initializeCourseObject();
		
		Thread.sleep(2000);
		
		// 5. Verify that USER's private course is not displayed.
		course.verifyThatNoCourseIsDisplayed();
		
		// 6. Sign out.
		top_bar_helper.signOut();
		
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
		
		// 7. Enroll this USER as STUDENT to any course.
		tegrity.loginAdmin("Admin");	
		 
		 Thread.sleep(1000);
		
		
		 
		 // Enroll the user as student to a course.
		 admin_dashboard_page.clickOnTargetSubmenuCourses("Manage Ad-hoc Courses / Enrollments (Course Builder)");
			
		 for(int i=0; i<10; i++) {
			 try {
				 driver.switchTo().frame(0);
				 break;
			 } catch(Exception msg) {
				 Thread.sleep(1000);
			 }
		 }
		 
		 
		// Search target course name
		manage_adhoc_courses_enrollments_page.searchAndFilterCourses(instructor_public_course);
						
		Thread.sleep(1000);
		// Click on result first course (the only one) membership button
		manage_adhoc_courses_enrollments_page.clickOnFirstCourseMembershipButton(); 
		
		// Search target user name in membership window
		mange_ad_hoc_courses_membership_window.searchForUser(temp_student_user_name);	

		// Select first user from user list (the only user it found because of the uniq of the search)
		mange_ad_hoc_courses_membership_window.selectFirstUserFromUserList();

		// Add selected user to instructor list
		mange_ad_hoc_courses_membership_window.clickOnAddSelectedUserToStudentList();
		
		mange_ad_hoc_courses_membership_window.waitMaxTimeUntillStudentEnrollToCourse(temp_student_user_name);
		
			 
		// Confirm user membership list
		mange_ad_hoc_courses_membership_window.clickOnOkButton();
					
		mange_ad_hoc_courses_membership_window.wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("ctl00_ContentPlaceHolder1_ucAddMemberships_ucDialog_ButtonAddInstructor")));
			 
		// Sign Out.
		for(String window: driver.getWindowHandles()) {
			driver.switchTo().window(window);
			break;
		}
		
		manage_adhoc_courses_enrollments_page.clickOnAdminDashboard();
		
		
		 for(String window: driver.getWindowHandles()) {
			driver.switchTo().window(window);
			break;
		}
			
			
		top_bar_helper.signOut();
		

		// 8. Login as STUDENT.
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
		tegrity.loginCoursesByParameter(temp_student_user_name);// log in courses page
		initializeCourseObject();
		
		// 9. Verify that private course is not displayed.
		// 10. Open the following URL - https://<UNIVERSITYURL/api/courses/active.
		// 11. Make sure there is NO course with true in the <IsPrivate> tag.
		course.goToAPICoursesActive(temp_student_user_name,1);
		Thread.sleep(1000);
		course.veriftyThatNoCourseSetIsPrivateAsTrueInAPICourseActive();
		
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
			
}}
