package com.automation.main;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.text.DateFormat;
import java.util.Date;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;import com.automation.main.page_helpers.Page;import com.automation.main.page_helpers.Page;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;


@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC19316CreateAndDeleteAPrivateCourse {

	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}

	public TC19316CreateAndDeleteAPrivateCourse() {
		// TODO Auto-generated constructor stub
	}

	public CreateNewCourseWindow create_new_course_window;
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

	@BeforeClass
	public void setup() {
		/*
		 * Firefox only
		 */
//		driver = DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());
		driver = new FirefoxDriver();
		
		//
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
		create_new_course_window = PageFactory.initElements(driver, CreateNewCourseWindow.class);
		
		wait = new WebDriverWait(driver, 30);
		
		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC19316CreateAndDeleteAPrivateCourse at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC19316CreateAndDeleteAPrivateCourse at " + DateToStr,
		 "Starting the test: TC19316CreateAndDeleteAPrivateCourse at " + DateToStr, LogAs.PASSED, null);	
		
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

	@Test
	public void loginCourses() throws InterruptedException
	{
		// pretest
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
		tegrity.loginCourses("User1");
		initializeCourseObject();
		
		String abc_course_name = course.selectCourseThatStartingWith("abc");
		
		record.returnToCourseListPage();
		
		String Ab_course_name = course.selectCourseThatStartingWith("Ab");
		
		top_bar_helper.clickOnSignOut();
		
		// 1. Create a new user, don't enroll him to any courses.
		tegrity.loginAdmin("Admin");
		 
		// Go to user builder page on admin dashboard.

		Thread.sleep(Page.TIMEOUT_TINY);
		 
		admin_dashboard_page.clickOnTargetSubmenuUsers("Manage Ad-hoc Users (User Builder)");
		 
		Thread.sleep(Page.TIMEOUT_TINY);
	
		// 10. Create a new user and assign him to a course as Instructor (User1 sandbox course).
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyhhmmss");
		String temp_instructor_user_name = "InstructorTemp" + sdf.format(date);
		  
		for(int i=0; i<5; i++ ) {
			try {
				driver.switchTo().frame(0);
				break;
			} catch(Exception msg) {
				Thread.sleep(Page.TIMEOUT_TINY);
			}
		}
		 
		mange_adhoc_users_page.clickOnNewUser();
			
		create_new_user_window.createNewUser(temp_instructor_user_name, temp_instructor_user_name, "abc@com.com", "111", "111");
	
	
		// Sign out
		for(String window: driver.getWindowHandles()) {
			driver.switchTo().window(window);
			break;
		}
		
		top_bar_helper.clickOnSignOut();
		
		// 2. Login as USER.
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
		tegrity.loginCoursesByParameter(temp_instructor_user_name);// log in courses page
		initializeCourseObject();
		
		// 3. Verify that USER's private course is not displayed.
		course.verifyThatNoCourseIsDisplayed();
		
		// 4. Sign out.
		top_bar_helper.clickOnSignOut();
		
		// 5. Enroll this USER as INSTRUCTOR to any course.
		tegrity.loginAdmin("Admin");
		
		Thread.sleep(Page.TIMEOUT_TINY);
		
		wait.until(ExpectedConditions.visibilityOf(admin_dashboard_page.courses_submenu.get(1)));
		 
		// Enroll the user to one course
		admin_dashboard_page.clickOnTargetSubmenuCourses("Manage Ad-hoc Courses / Enrollments (Course Builder)");
		
		Thread.sleep(Page.TIMEOUT_TINY);
		 
		for(int i=0; i<10; i++) {
			try {
				driver.switchTo().frame(0);
				break;
		} catch(Exception msg) {
			Thread.sleep(Page.TIMEOUT_TINY);
			}
		}
		
		// Search target course name
		manage_adhoc_courses_enrollments_page.searchAndFilterCourses(abc_course_name);
					
		Thread.sleep(Page.TIMEOUT_TINY);
		// Click on result first course (the only one) membership button
		manage_adhoc_courses_enrollments_page.clickOnFirstCourseMembershipButton(); 
		 
		// Search target user name in membership window
		mange_ad_hoc_courses_membership_window.searchForUser(temp_instructor_user_name);	
	
		// Select first user from user list (the only user it found because of the uniq of the search)
		mange_ad_hoc_courses_membership_window.selectFirstUserFromUserList();
	
		// Add selected user to instructor list
		mange_ad_hoc_courses_membership_window.clickOnAddSelectedUserToInstructorList();
		
		// repair - remove the errors msg that appear till the inst moved to inst list
		mange_ad_hoc_courses_membership_window.waitMaxTimeUntillInstructorEnrollToCourse(temp_instructor_user_name);
		 
		// Confirm user membership list
		mange_ad_hoc_courses_membership_window.clickOnOkButton();
				
		mange_ad_hoc_courses_membership_window.wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("ctl00_ContentPlaceHolder1_ucAddMemberships_ucDialog_ButtonAddInstructor")));	
		 
		// Sign Out.
		for(String window: driver.getWindowHandles()) {
			driver.switchTo().window(window);
			break;
		}
		
		top_bar_helper.clickOnSignOut(); 
		
		// 6. Login as INSTRUCTOR.
		tegrity.loginCoursesByParameter(temp_instructor_user_name);// log in courses page
		
		// 7. Verify that INSTRUCTOR's private course is displayed.
		course.goToAPICoursesActive("User1",0);
		
		course.veriftyThatOnlyOneCourseSetIsPrivateAsTrueInAPICourseActive();
		
		// 8. Select the private course.
		driver.navigate().back();
		
		String private_course_name = course.selectCourseThatStartingWith(temp_instructor_user_name);
		
		// 9. Verify that private course doesn't contains any recordings.
		record.verifyThatNoRecordingExistInTheCourse();
		
		// 10. Sign out.
		top_bar_helper.clickOnSignOut();
		
		// 11. Disenroll this USER from his course. 
		tegrity.loginAdmin("Admin");
		
		Thread.sleep(Page.TIMEOUT_TINY);
		
		wait.until(ExpectedConditions.visibilityOf(admin_dashboard_page.courses_submenu.get(1)));
		 
		// 10.1. Enroll the user to one course
		admin_dashboard_page.clickOnTargetSubmenuCourses("Manage Ad-hoc Courses / Enrollments (Course Builder)");
		
		Thread.sleep(Page.TIMEOUT_TINY);
		 
		for(int i=0; i<10; i++) {
			try {
				driver.switchTo().frame(0);
				break;
		} catch(Exception msg) {
			Thread.sleep(Page.TIMEOUT_TINY);
			}
		}
		
		// Search target course name
		manage_adhoc_courses_enrollments_page.searchAndFilterCourses(abc_course_name);
					
		Thread.sleep(Page.TIMEOUT_TINY);
		// Click on result first course (the only one) membership button
		manage_adhoc_courses_enrollments_page.clickOnFirstCourseMembershipButton(); 
		
		// Select user from instructor list
		mange_ad_hoc_courses_membership_window.waitMaxTimeUntillInstructorEnrollToCourse(temp_instructor_user_name);
		 
		// Search target user name in membership window
		mange_ad_hoc_courses_membership_window.clickOnRemoveSelectedUserToInstructorList();
		 
		// Confirm user membership list
		mange_ad_hoc_courses_membership_window.clickOnOkButton();
				
		mange_ad_hoc_courses_membership_window.wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("ctl00_ContentPlaceHolder1_ucAddMemberships_ucDialog_ButtonAddInstructor")));	
		 
		// Sign Out.
		for(String window: driver.getWindowHandles()) {
			driver.switchTo().window(window);
			break;
		}
		
		top_bar_helper.clickOnSignOut(); 
		
		// 12. Login as USER.
		tegrity.loginCoursesByParameter(temp_instructor_user_name);// log in courses page
		
		// 13. Verify that USER's private course is not displayed in the 'Active Courses' tab.
		course.verifyThatNoCourseIsDisplayed();
		
		// 14. Click on the 'Past Courses' tab.
		course.clickOnPastCoursesTabButton();
		
		// 15. Make sure the private course is not displayed in the 'Past Courses' tab.
		course.verifyCourseNotExist(private_course_name);
		
		// 16. Sign Out.
		top_bar_helper.clickOnSignOut();
		
		// 17. Login as admin and enroll the user to a new course as Instructor.
		tegrity.loginAdmin("Admin");
		
		Thread.sleep(Page.TIMEOUT_TINY);
		
		// Click on course builder href link
		admin_dashboard_page.clickOnTargetSubmenuCourses("Manage Ad-hoc Courses / Enrollments (Course Builder)");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("SelfRegConfig")));
		
		// Click on create course href link 
		driver.switchTo().frame(0);
		manage_adhoc_courses_enrollments_page.clickOnNewCourse();
		
		wait.until(ExpectedConditions.visibilityOf(manage_adhoc_courses_enrollments_page.new_course_button));
		manage_adhoc_courses_enrollments_page.clickOnNewCourse();
		create_new_course_window.waitForVisibility(create_new_course_window.course_id_input);
		
		String temp_course_name = "TempCourseOf" + temp_instructor_user_name;
		
		create_new_course_window.createNewCourse(temp_course_name + "_Name", temp_course_name);
		
		for(String window: driver.getWindowHandles()) {
			driver.switchTo().window(window);
			break;
		}
		
		manage_adhoc_courses_enrollments_page.clickOnAdminDashboard();
		
		Thread.sleep(Page.TIMEOUT_TINY);
		
		wait.until(ExpectedConditions.visibilityOf(admin_dashboard_page.courses_submenu.get(1)));
		 
		// Enroll the user to one course
		admin_dashboard_page.clickOnTargetSubmenuCourses("Manage Ad-hoc Courses / Enrollments (Course Builder)");
		
		Thread.sleep(Page.TIMEOUT_TINY);
		 
		for(int i=0; i<10; i++) {
			try {
				driver.switchTo().frame(0);
				break;
		} catch(Exception msg) {
			Thread.sleep(Page.TIMEOUT_TINY);
			}
		}
		
		// Enroll to temp course
		// Search target course name
		manage_adhoc_courses_enrollments_page.searchAndFilterCourses(temp_course_name);
					
		Thread.sleep(Page.TIMEOUT_TINY);
		// Click on result first course (the only one) membership button
		manage_adhoc_courses_enrollments_page.clickOnFirstCourseMembershipButton(); 
		 
		// Search target user name in membership window
		mange_ad_hoc_courses_membership_window.searchForUser(temp_instructor_user_name);	
	
		// Select first user from user list (the only user it found because of the uniq of the search)
		mange_ad_hoc_courses_membership_window.selectFirstUserFromUserList();
	
		// Add selected user to instructor list
		mange_ad_hoc_courses_membership_window.clickOnAddSelectedUserToInstructorList();
		
		// repair - remove the errors msg that appear till the inst moved to inst list
		mange_ad_hoc_courses_membership_window.waitMaxTimeUntillInstructorEnrollToCourse(temp_instructor_user_name);
		 
		// Confirm user membership list
		mange_ad_hoc_courses_membership_window.clickOnOkButton();
				
		mange_ad_hoc_courses_membership_window.wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("ctl00_ContentPlaceHolder1_ucAddMemberships_ucDialog_ButtonAddInstructor")));	
		
		// Enroll to Ab course
		// Search target course name
		manage_adhoc_courses_enrollments_page.searchAndFilterCourses(Ab_course_name);
							
		Thread.sleep(Page.TIMEOUT_TINY);
		// Click on result first course (the only one) membership button
		manage_adhoc_courses_enrollments_page.clickOnFirstCourseMembershipButton(); 
				 
		// Search target user name in membership window
		mange_ad_hoc_courses_membership_window.searchForUser(temp_instructor_user_name);	
			
		// Select first user from user list (the only user it found because of the uniq of the search)
		mange_ad_hoc_courses_membership_window.selectFirstUserFromUserList();
			
		// Add selected user to instructor list
		mange_ad_hoc_courses_membership_window.clickOnAddSelectedUserToInstructorList();
				
		// repair - remove the errors msg that appear till the inst moved to inst list
		mange_ad_hoc_courses_membership_window.waitMaxTimeUntillInstructorEnrollToCourse(temp_instructor_user_name);
				 
		// Confirm user membership list
		mange_ad_hoc_courses_membership_window.clickOnOkButton();
						
		mange_ad_hoc_courses_membership_window.wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("ctl00_ContentPlaceHolder1_ucAddMemberships_ucDialog_ButtonAddInstructor")));	
		
		// 18. Sign Out.
		for(String window: driver.getWindowHandles()) {
			driver.switchTo().window(window);
			break;
		}
		
		top_bar_helper.clickOnSignOut(); 
		
		// 19. Login as the instructor.
		tegrity.loginCoursesByParameter(temp_instructor_user_name);
		
		// 20. Open the private course.
		// 21. Upload a Recording to the Course.
		course.selectCourseThatStartingWith("Ab");
		
		Thread.sleep(Page.TIMEOUT_TINY);
		
		// Select first recording
		record.selectIndexCheckBox(1);
		String selected_recording = record.getFirstRecordingTitle();
		
		// Click on Recording Tasks -> copy
		record.clickOnRecordingTaskThenCopy();
		
		// Select private course as target course
		copy.selectTargetCourseFromCourseListThatStartWith(temp_instructor_user_name);
		
		// Click on copy recording
		copy.clickOnCopyButton();
		
		Thread.sleep(Page.TIMEOUT_TINY);
		
		// Confirm menu click ok
		confirm_menu.clickOnOkButtonAfterConfirmCopyRecording();
		
		// 22. Sign Out.
		top_bar_helper.clickOnSignOut();
		
		// 23. Login as Admin.
		tegrity.loginAdmin("Admin");
		
		// 24. Disenroll the user from the course.
		Thread.sleep(Page.TIMEOUT_TINY);
		
		wait.until(ExpectedConditions.visibilityOf(admin_dashboard_page.courses_submenu.get(1)));
		 
		// 10.1. Enroll the user to one course
		admin_dashboard_page.clickOnTargetSubmenuCourses("Manage Ad-hoc Courses / Enrollments (Course Builder)");
		
		Thread.sleep(Page.TIMEOUT_TINY);
		 
		for(int i=0; i<10; i++) {
			try {
				driver.switchTo().frame(0);
				break;
		} catch(Exception msg) {
			Thread.sleep(Page.TIMEOUT_TINY);
			}
		}
		
		// Search target course name
		manage_adhoc_courses_enrollments_page.searchAndFilterCourses(temp_course_name);
					
		Thread.sleep(Page.TIMEOUT_TINY);
		// Click on result first course (the only one) membership button
		manage_adhoc_courses_enrollments_page.clickOnFirstCourseMembershipButton(); 
		
		// Select user from instructor list
		mange_ad_hoc_courses_membership_window.waitMaxTimeUntillInstructorEnrollToCourse(temp_instructor_user_name);
		 
		// Search target user name in membership window
		mange_ad_hoc_courses_membership_window.clickOnRemoveSelectedUserToInstructorList();
		 
		// Confirm user membership list
		mange_ad_hoc_courses_membership_window.clickOnOkButton();
				
		mange_ad_hoc_courses_membership_window.wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("ctl00_ContentPlaceHolder1_ucAddMemberships_ucDialog_ButtonAddInstructor")));	
		 
		// Sign Out.
		for(String window: driver.getWindowHandles()) {
			driver.switchTo().window(window);
			break;
		}
		
		top_bar_helper.clickOnSignOut(); 
		
		// 25. Login as the Instructor.
		tegrity.loginCoursesByParameter(temp_instructor_user_name);
		
		// 26. Make sure the course isn't displayed in the 'Active Courses' tab.
		course.verifyCourseNotExist(temp_course_name);
		
		// 27. Make sure the course is displayed in the 'Past Courses' tab.
		course.clickOnPastCoursesTabButton();
		course.verifyCourseExist(temp_course_name);
		
		// 28. Open the past course.
		course.selectCourseThatStartingWith(temp_course_name);
		
		Thread.sleep(Page.TIMEOUT_TINY);
		
		// 29. The recording you uploaded earlier is displayed.
		record.verifyThatTargetRecordingExistInRecordingList(selected_recording);
		
		// 30. Open the recording playback.
		record.clickOnTargetRecordingAndOpenItsPlayback(selected_recording);
		
		// 31. The recording is being played.
		player_page.verifyTimeBufferStatusForXSec(10);
		
		// 32. Sign Out.
		driver.navigate().back();
		top_bar_helper.clickOnSignOut();
		
		// 33. Login as admin and enroll the user to a new course as Instructor.
		tegrity.loginAdmin("Admin");
		
		Thread.sleep(Page.TIMEOUT_TINY);
		
		// Click on course builder href link
		admin_dashboard_page.clickOnTargetSubmenuCourses("Manage Ad-hoc Courses / Enrollments (Course Builder)");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("SelfRegConfig")));
		
		// Click on create course href link 
		driver.switchTo().frame(0);
		manage_adhoc_courses_enrollments_page.clickOnNewCourse();
		
		wait.until(ExpectedConditions.visibilityOf(manage_adhoc_courses_enrollments_page.new_course_button));
		manage_adhoc_courses_enrollments_page.clickOnNewCourse();
		create_new_course_window.waitForVisibility(create_new_course_window.course_id_input);
		
		String temp_another_course_name = "TempAnotherCourseOf" + temp_instructor_user_name;
		
		create_new_course_window.createNewCourse(temp_course_name + "_Name", temp_course_name);
		
		for(String window: driver.getWindowHandles()) {
			driver.switchTo().window(window);
			break;
		}
		
		manage_adhoc_courses_enrollments_page.clickOnAdminDashboard();
		
		Thread.sleep(Page.TIMEOUT_TINY);
		
		wait.until(ExpectedConditions.visibilityOf(admin_dashboard_page.courses_submenu.get(1)));
		 
		// Enroll the user to one course
		admin_dashboard_page.clickOnTargetSubmenuCourses("Manage Ad-hoc Courses / Enrollments (Course Builder)");
		
		Thread.sleep(Page.TIMEOUT_TINY);
		 
		for(int i=0; i<10; i++) {
			try {
				driver.switchTo().frame(0);
				break;
		} catch(Exception msg) {
			Thread.sleep(Page.TIMEOUT_TINY);
			}
		}
		
		// Enroll to temp course
		// Search target course name
		manage_adhoc_courses_enrollments_page.searchAndFilterCourses(temp_another_course_name);
					
		Thread.sleep(Page.TIMEOUT_TINY);
		// Click on result first course (the only one) membership button
		manage_adhoc_courses_enrollments_page.clickOnFirstCourseMembershipButton(); 
		 
		// Search target user name in membership window
		mange_ad_hoc_courses_membership_window.searchForUser(temp_instructor_user_name);	
	
		// Select first user from user list (the only user it found because of the uniq of the search)
		mange_ad_hoc_courses_membership_window.selectFirstUserFromUserList();
	
		// Add selected user to instructor list
		mange_ad_hoc_courses_membership_window.clickOnAddSelectedUserToInstructorList();
		
		// repair - remove the errors msg that appear till the inst moved to inst list
		mange_ad_hoc_courses_membership_window.waitMaxTimeUntillInstructorEnrollToCourse(temp_instructor_user_name);
		 
		// Confirm user membership list
		mange_ad_hoc_courses_membership_window.clickOnOkButton();
				
		mange_ad_hoc_courses_membership_window.wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("ctl00_ContentPlaceHolder1_ucAddMemberships_ucDialog_ButtonAddInstructor")));	
		
		// 34. Sign Out.
		for(String window: driver.getWindowHandles()) {
			driver.switchTo().window(window);
			break;
		}
		
		top_bar_helper.clickOnSignOut(); 
		
		// 35. Login as the instructor.
		tegrity.loginCoursesByParameter(temp_another_course_name);
		
		Thread.sleep(Page.TIMEOUT_TINY);
		
		// 36. Open the private course.
		// 37. Upload an additional content file to the course.
		course.selectCourseThatStartingWith("Ab");
		
		// Go to additional content tab
		record.clickOnAdditionContentTab();
		
		// Select first addition content
		record.selectFirstCheckbox();
		
		// Click on Content Tasks -> Copy
		record.clickOnContentTaskThenCopy();
		
		// Select target as private course
		copy.selectTargetCourseFromCourseListThatStartWith(temp_instructor_user_name);
		
		// Click copy content
		copy.clickOnCopyButton();
		Thread.sleep(Page.TIMEOUT_TINY);
		
		// Confirm menu
		confirm_menu.clickOnOkButton();
		Thread.sleep(Page.TIMEOUT_TINY);
		
		// 38. Sign Out.
		top_bar_helper.clickOnSignOut();
		
		// 39. Login as Admin.
		tegrity.loginCoursesByParameter(temp_instructor_user_name);
		
		// 40. Disenroll the user from the course.
		Thread.sleep(Page.TIMEOUT_TINY);
		
		wait.until(ExpectedConditions.visibilityOf(admin_dashboard_page.courses_submenu.get(1)));
		 
		// 10.1. Enroll the user to one course
		admin_dashboard_page.clickOnTargetSubmenuCourses("Manage Ad-hoc Courses / Enrollments (Course Builder)");
		
		Thread.sleep(Page.TIMEOUT_TINY);
		 
		for(int i=0; i<10; i++) {
			try {
				driver.switchTo().frame(0);
				break;
		} catch(Exception msg) {
			Thread.sleep(Page.TIMEOUT_TINY);
			}
		}
		
		// Search target course name
		manage_adhoc_courses_enrollments_page.searchAndFilterCourses(temp_another_course_name);
					
		Thread.sleep(Page.TIMEOUT_TINY);
		// Click on result first course (the only one) membership button
		manage_adhoc_courses_enrollments_page.clickOnFirstCourseMembershipButton(); 
		
		// Select user from instructor list
		mange_ad_hoc_courses_membership_window.waitMaxTimeUntillInstructorEnrollToCourse(temp_instructor_user_name);
		 
		// Search target user name in membership window
		mange_ad_hoc_courses_membership_window.clickOnRemoveSelectedUserToInstructorList();
		 
		// Confirm user membership list
		mange_ad_hoc_courses_membership_window.clickOnOkButton();
				
		mange_ad_hoc_courses_membership_window.wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("ctl00_ContentPlaceHolder1_ucAddMemberships_ucDialog_ButtonAddInstructor")));	
		 
		// Sign Out.
		for(String window: driver.getWindowHandles()) {
			driver.switchTo().window(window);
			break;
		}
		
		top_bar_helper.clickOnSignOut(); 
		
		// 41. Login as the Instructor
		tegrity.loginCoursesByParameter(temp_instructor_user_name);
		
		Thread.sleep(Page.TIMEOUT_TINY);
		
		// 42. Make sure the course isn't displayed in the 'Active Courses' tab.
		course.verifyCourseNotExist(temp_another_course_name);
		
		// 43. Make sure the course is displayed in the 'Past Courses' tab.
		course.clickOnPastCoursesTabButton();
		Thread.sleep(Page.TIMEOUT_TINY);
		course.verifyCourseExist(temp_another_course_name);
		
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);

}}
