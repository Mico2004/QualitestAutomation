package com.automation.main.public_courses;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.function.IntToDoubleFunction;
import java.text.DateFormat;
import org.openqa.selenium.By;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.CapabilityType;
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

import com.automation.main.page_helpers.AddAdditionalContentLinkWindow;
import com.automation.main.page_helpers.AdminCourseSettingsPage;
import com.automation.main.page_helpers.AdminDashboardPage;
import com.automation.main.page_helpers.AdminDashboardViewCourseList;
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
import com.automation.main.page_helpers.PlayerPage;
import com.automation.main.page_helpers.RecordingHelperPage;
import com.automation.main.page_helpers.TopBarHelper;
import com.automation.main.utilities.DriverSelector;

import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC21856ValidateThePublicCoursesTabUIAfterChanges {

	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}

	public TC21856ValidateThePublicCoursesTabUIAfterChanges() {
		// TODO Auto-generated constructor stub
	}

	public AddAdditionalContentLinkWindow add_additional_content_link_window;
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
	public AdminCourseSettingsPage admin_course_settings_page;

	
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
		player_page = PageFactory.initElements(driver, PlayerPage.class);
		add_additional_content_link_window = PageFactory.initElements(driver, AddAdditionalContentLinkWindow.class);
		admin_course_settings_page = PageFactory.initElements(driver, AdminCourseSettingsPage.class);
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		capabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);	
		wait = new WebDriverWait(driver, 30);
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		
		
		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC21856ValidateThePublicCoursesTabUIAfterChanges at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC21856ValidateThePublicCoursesTabUIAfterChanges at " + DateToStr,
		 "Starting the test: TC21856ValidateThePublicCoursesTabUIAfterChanges at " + DateToStr, LogAs.PASSED, null);	
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

	@Test (description = "TC 21856 Validate The Public Courses Tab UI After Changes")
	public void test21856() throws Exception
	{
		// 1. Get Ab course name of User1.
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
		tegrity.loginCourses("User1");// log in courses page
		initializeCourseObject();
		
		String Ab_course_name = course.selectCourseThatStartingWith("Ab");
		
		top_bar_helper.clickOnSignOut();
	
		
		// 2. Create 1 instructor and 1 student and enroll them to Ab course.
		tegrity.loginAdmin("Admin");	
		Thread.sleep(2000);
		
		//make sure that we have public tabs
		admin_dashboard_page.clickOnTargetSubmenuCourses("Manage Course Settings");		
		Thread.sleep(2000);		
		
		admin_course_settings_page.makeSureThatLockMakeThisCoursePublicUnSelected();
		admin_course_settings_page.clickOnSaveButton();
		 
		admin_dashboard_page.clickOnTargetSubmenuUsers("Manage Ad-hoc Users (User Builder)");
		 
		Thread.sleep(1000);
		  
		for(int i=0; i<10; i++ ) {
			try {
				driver.switchTo().frame(0);
				break;
			} catch(Exception msg) {
				Thread.sleep(500);
			}
		}
		
//		Thread.sleep(100);
		
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyhhmmss");
		String temp_instructor_user_name = "InstructorTemp" + sdf.format(date);
		String temp_student_user_name = "StudentTemp" + sdf.format(date);
		 
		mange_adhoc_users_page.clickOnNewUser();
		create_new_user_window.createNewUser(temp_instructor_user_name, temp_instructor_user_name, "abc@com.com", "111", "111");
		
		Thread.sleep(1000);
		
		mange_adhoc_users_page.clickOnNewUser();
		create_new_user_window.createNewUser(temp_student_user_name, temp_student_user_name, "abc@com.com", "111", "111");
	
		for(String window: driver.getWindowHandles()) {
			driver.switchTo().window(window);
			break;
		}
		 
		manage_adhoc_courses_enrollments_page.clickOnAdminDashboard();
		
		Thread.sleep(2000);
		
		// 3. Make sure to have two users which are enroll to the same course, first as Instructor and Second as Student both of the users don't have past courses.
		// Click on course builder href link
		admin_dashboard_page.clickOnTargetSubmenuCourses("Manage Ad-hoc Courses / Enrollments (Course Builder)");
		
		// Click on create course href link 
		for(int i=0; i<10; i++) {
			try {
				driver.switchTo().frame(0);
				break;
			} catch(Exception msg) {
				Thread.sleep(2000);
			}
		}
		
		manage_adhoc_courses_enrollments_page.searchAndFilterCourses(Ab_course_name);
		
		Thread.sleep(1000);
		// Click on result first course (the only one) membership button
		manage_adhoc_courses_enrollments_page.clickOnFirstCourseMembershipButton();
		Thread.sleep(1000);	
		
		// Search target user name in membership window
		mange_ad_hoc_courses_membership_window.searchForUser(temp_instructor_user_name);	

		// Select first user from user list (the only user it found because of the uniq of the search)
		mange_ad_hoc_courses_membership_window.selectFirstUserFromUserList();

		// Add selected user to instructor list
		mange_ad_hoc_courses_membership_window.clickOnAddSelectedUserToInstructorList();
		
		mange_ad_hoc_courses_membership_window.waitMaxTimeUntillInstructorEnrollToCourse(temp_instructor_user_name);
		
		Thread.sleep(1000);
		
		// Search target user name in membership window
		mange_ad_hoc_courses_membership_window.searchForUser(temp_student_user_name);	

		// Select first user from user list (the only user it found because of the uniq of the search)
		mange_ad_hoc_courses_membership_window.selectFirstUserFromUserList();

		// Add selected user to instructor list
		mange_ad_hoc_courses_membership_window.clickOnAddSelectedUserToStudentList();
		
		
		mange_ad_hoc_courses_membership_window.waitMaxTimeUntillStudentEnrollToCourse(temp_student_user_name);
		
		mange_ad_hoc_courses_membership_window.waitForVisibility(mange_ad_hoc_courses_membership_window.ok_button);
		
		// Confirm user membership list
		mange_ad_hoc_courses_membership_window.clickOnOkButton();
	
		mange_ad_hoc_courses_membership_window.wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("ctl00_ContentPlaceHolder1_ucAddMemberships_ucDialog_ButtonAddInstructor")));
		
		// Signout
		for(String window: driver.getWindowHandles()) {
			driver.switchTo().window(window);
			break;
		}
		manage_adhoc_courses_enrollments_page.waitForVisibility(driver.findElement(By.id("SignOutLink")));
		driver.findElement(By.id("SignOutLink")).click();
		
		// 4. Make sure the 'Make this course publicly visible' university course settings are 'Lock' and 'On'.
		// 5. Make sure to have a public course in the university.
		tegrity.loginCoursesByParameter(temp_instructor_user_name);
		
		course.selectCourseThatStartingWith("Ab");
		
		record.clickOnAdditionContentTab();
		record.toUploadAdditionalContentLink();
		add_additional_content_link_window.createNewAdditionalContentLink(confirm_menu, "current page", driver.getCurrentUrl());
		
		record.clickOnCourseTaskThenCourseSettings();
		
		course_settings_page.makeSureThatMakeCoursePublicIsSelected();
		Thread.sleep(2000);
		course_settings_page.clickOnOkButton();
		
		// 6. Login as Instructor.
		record.returnToCourseListPage();
		
		// 7. The 'Active Courses' tab is displayed first from the left.
		// 8. The 'Public Courses' tab is displayed second from the left.
		course.verifyActiveCoursesTabDisplayed();
		course.verifyPublicCoursesTabDisplayed();
		course.verifyTabsOrder();
		
		// 9. Sign out.
		top_bar_helper.clickOnSignOut();
		
		// 10. Login as Student.
		tegrity.loginCoursesByParameter(temp_student_user_name);
		
		// 11. The 'Active Courses' tab is displayed first from the left.
		// 12. The 'Public Courses' tab is displayed second from the left.
		course.verifyActiveCoursesTabDisplayed();
		course.verifyPublicCoursesTabDisplayed();
		course.verifyTabsOrder();
		
		// 13. Sign out.
		top_bar_helper.clickOnSignOut();
		
		// 14. Login as Admin.
		tegrity.loginAdmin("Admin");
		
		// 15. Unenroll both Instructor and Student from some course.
		Thread.sleep(2000);
		
		
		// 3. Make sure to have two users which are enroll to the same course, first as Instructor and Second as Student both of the users don't have past courses.
		// Click on course builder href link
		admin_dashboard_page.clickOnTargetSubmenuCourses("Manage Ad-hoc Courses / Enrollments (Course Builder)");
		
		// Click on create course href link 
		for(int i=0; i<10; i++) {
			try {
				driver.switchTo().frame(0);
				break;
			} catch(Exception msg) {
				Thread.sleep(1000);
			}
		}
		
		
		
		manage_adhoc_courses_enrollments_page.searchAndFilterCourses(Ab_course_name);
		
		Thread.sleep(1000);
		// Click on result first course (the only one) membership button
		manage_adhoc_courses_enrollments_page.clickOnFirstCourseMembershipButton();
		Thread.sleep(1000);	
		
		// Search target user name in membership window
		mange_ad_hoc_courses_membership_window.waitMaxTimeUntillInstructorEnrollToCourse(temp_instructor_user_name);

		// Add selected user to instructor list
		mange_ad_hoc_courses_membership_window.clickOnRemoveSelectedUserToInstructorList();
		
		Thread.sleep(3000);
		
		// Search target user name in membership window
		mange_ad_hoc_courses_membership_window.waitMaxTimeUntillStudentEnrollToCourse(temp_student_user_name);

		// Add selected user to instructor list
		mange_ad_hoc_courses_membership_window.clickOnRemoveSelectedUserToStudentsList();
		
		Thread.sleep(3000);
		
		mange_ad_hoc_courses_membership_window.waitForVisibility(mange_ad_hoc_courses_membership_window.ok_button);
		
		// Confirm user membership list
		mange_ad_hoc_courses_membership_window.clickOnOkButton();
	
		mange_ad_hoc_courses_membership_window.wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("ctl00_ContentPlaceHolder1_ucAddMemberships_ucDialog_ButtonAddInstructor")));
		
		// Signout
		for(String window: driver.getWindowHandles()) {
			driver.switchTo().window(window);
			break;
		}
		manage_adhoc_courses_enrollments_page.waitForVisibility(driver.findElement(By.id("SignOutLink")));
		driver.findElement(By.id("SignOutLink")).click();
		
		// 16. Login as Instructor.
		tegrity.loginCoursesByParameter(temp_instructor_user_name);
		
		// 17. The 'Active Courses' tab is displayed first from the left.
		// 18. The 'Past Courses' tab is displayed second from the left.
		// 19. The 'Public Courses' tab is displayed Third from the left.
		course.verifyActiveCoursesTabDisplayed();
		course.verifyPastCoursesTabDisplayed();
		course.verifyPublicCoursesTabDisplayed();
		course.verifyTabsOrder();
		
		Thread.sleep(5000);
		// 20. Hover over 'Past courses' tab.
		// 20.1. The font color changes to black.
		// 20.2. The tab background change to white.
		// 20.3. A hint appears with 'Past Courses' text.
		course.verifyUIHoveringPastCoursesTab();
		
		Thread.sleep(5000);
		
//		driver.findElement(By.id("tegritySearchBox")).click();
		
		// 21. Hover over 'Public courses' tab.
		// 21.1. The font color changes to black.
		// 21.2. The tab background change to white.
		// 21.3. A hint appears with 'Public Courses' text.
		course.verifyUIHoveringPublicCoursesTab();
		
		Thread.sleep(5000);
		
		// 22. Sign out.
		top_bar_helper.clickOnSignOut();
		
		// 23. Login as Student.
		tegrity.loginCoursesByParameter(temp_student_user_name);
		
		// 24. The 'Active Courses' tab is displayed first from the left.
		course.verifyActiveCoursesTabDisplayed();
		
		// 25. The 'Past Courses' tab isn't displayed.
		course.verifyPastCoursesTabNotDisplayed();
		
		// 26. The 'Public Courses' tab is displayed second from the left.
		course.verifyPublicCoursesTabDisplayed();
		
		// 27. Hover over 'Public courses' tab.
		// 27.1. The font color changes to black.
		// 27.2. The tab background change to white.
		// 27.3. A hint appears with 'Public Courses' text.
		course.verifyUIHoveringPublicCoursesTab();
		
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
		
}}
