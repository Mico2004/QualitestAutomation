package com.automation.main.private_courses;


import java.text.SimpleDateFormat;

import java.util.Date;
import java.text.DateFormat;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
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
import com.thoughtworks.selenium.webdriven.commands.WaitForPageToLoad;

import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import junitx.util.PropertyManager;
import junitx.util.ResourceManager;

@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC19307VerifyThatPrivateCourseIsAvailableForEachInstructor {

	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}

	public TC19307VerifyThatPrivateCourseIsAvailableForEachInstructor() {
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

	@BeforeClass
	public void setup() {

		/*
		 * Firefox only
		 */
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
		
		wait = new WebDriverWait(driver, 30);
		
		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC19307VerifyThatPrivateCourseIsAvailableForEachInstructor at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC19307VerifyThatPrivateCourseIsAvailableForEachInstructor at " + DateToStr,
		 "Starting the test: TC19307VerifyThatPrivateCourseIsAvailableForEachInstructor at " + DateToStr, LogAs.PASSED, null);
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

	@Test (description="TC 19307 Verify That Private Course Is Available For Each Instructor")
	public void test19307() throws InterruptedException
	{
		// 1. Login as an existing Instructor (User1).
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
	/*	tegrity.loginCoursesByParameter("InstructorTemp14092016082749");
		initializeCourseObject();
		course.verifyCourseExist("User113092016032614 sandbox course");
		Thread.sleep(5000);*/
		
		tegrity.loginCourses("User1");// log in courses page
		initializeCourseObject();
		
		// 1.1. Get the of Ab course.
		String instructor_public_course = course.selectCourseThatStartingWith(PropertyManager.getProperty("course2"));
		
		// 2. Open the following URL - https://<UNIVERSITYURL/api/courses/active.
		course.goToAPICoursesActive("User1",0);
		
		// 3. Make sure there is only one course with true in the <IsPrivate> tag ("Username sandbox course").
		String parsed_private_course_name = course.veriftyThatOnlyOneCourseSetIsPrivateAsTrueInAPICourseActive();
	
		 // 4. Make sure the private course ("Username sandbox course") is displayed in the course list page.
		 // 5. Make sure the private course name is assembeled from the <Instructor Name> (Not id) and 'sandbox course' text.
		 // 6. Open the private course.
		 course.goToCoursesPage();
		 
//		 wait.until(ExpectedConditions.visibilityOf(course.course_list.get(1)));
		 Thread.sleep(5000);
		
		 course.verifyCourseExist(parsed_private_course_name);
		 
		 String username = PropertyManager.getProperty("User1");
		 
		 System.out.println(course.getCourseList().size());
		 
		 
		 String private_course_name_from_course_list = course.selectCourseThatStartingWith(username);
		
		
		 if((private_course_name_from_course_list.contains(username)) && (private_course_name_from_course_list.contains("sandbox course"))) {
			 System.out.println("Verified that private course name is assembled from <Instructor> + sandbox course.");
			 ATUReports.add("Verified that private course name constructed correctly.", "True.", "True.", LogAs.PASSED, null);
			 Assert.assertTrue(true);
		 } else {
			 System.out.println("Fail to verify that private course name constructed as requested.");
			 ATUReports.add("Verified that private course name constructed correctly.", "True.", "False.", LogAs.FAILED, null);
			 Assert.assertTrue(false);
		 }
		 
		 String course_name_of_recordings_page  = record.getCourseTitle();
		 record.verifyThatStringExistsInCourseName(course_name_of_recordings_page.substring(0, username.length()));
		 record.verifyThatStringExistsInCourseName(course_name_of_recordings_page.substring(username.length()+1, course_name_of_recordings_page.length()-1));
	
		 // 7. The course details page is ;.
		 record.verifyThatItIsRecordingsPage();
		 
		 // 8. The 'Start a Recording' button is present.
		 record.veriftyThatStartRecordingButtonDisplayed();
		 
		 // 8.1. Logout.
		 top_bar_helper.signOut();
		 
		 // 9. Login as admin.
		 tegrity.loginAdmin("Admin");
		 
		 // 9.1. Go to user builder page on admin dashboard.

		 Thread.sleep(4000);
		 
		 admin_dashboard_page.clickOnTargetSubmenuUsers("Manage Ad-hoc Users (User Builder)");
		 
		 Thread.sleep(10000);
	
		 // 10. Create a new user and assign him to a course as Instructor (User1 sandbox course).
		 Date date = new Date();
		 SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyhhmmss");
		 String temp_instructor_user_name = "InstructorTemp" + sdf.format(date);
		  
		 mange_adhoc_users_page.waitForPageToLoad();
		 
		 mange_adhoc_users_page.clickOnNewUser();
			
		 create_new_user_window.createNewUser(temp_instructor_user_name, temp_instructor_user_name, "abc@com.com", "111", "111");
	
	
		
		 for(String window: driver.getWindowHandles()) {
		 	driver.switchTo().window(window);
		 	break;
		 }
		 
		 manage_adhoc_courses_enrollments_page.clickOnAdminDashboard();
		 
		 wait.until(ExpectedConditions.visibilityOf(admin_dashboard_page.courses_submenu.get(1)));
		 
		 // 10.1. Enroll the user to one course
		 admin_dashboard_page.clickOnTargetSubmenuCourses("Manage Ad-hoc Courses / Enrollments (Course Builder)");
		
		 Thread.sleep(5000);
		 
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
		mange_ad_hoc_courses_membership_window.searchForUser(temp_instructor_user_name);	
	
		// Select first user from user list (the only user it found because of the uniq of the search)
		mange_ad_hoc_courses_membership_window.selectFirstUserFromUserList();
	
		// Add selected user to instructor list
		mange_ad_hoc_courses_membership_window.clickOnAddSelectedUserToInstructorList();
		
		// TODO: repair - remove the errors msg that appear till the inst moved to inst list
		mange_ad_hoc_courses_membership_window.waitMaxTimeUntillInstructorEnrollToCourse(temp_instructor_user_name);
		 
		// Confirm user membership list
		mange_ad_hoc_courses_membership_window.clickOnOkButton();
				
		mange_ad_hoc_courses_membership_window.wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("ctl00_ContentPlaceHolder1_ucAddMemberships_ucDialog_ButtonAddInstructor")));	
		 
		// 11. Sign Out.
		for(String window: driver.getWindowHandles()) {
			driver.switchTo().window(window);
			break;
		}
		
		
		top_bar_helper.signOut();
		 
		// 12. Login as the instructor you created.
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
		tegrity.loginCoursesByParameter(temp_instructor_user_name);
		initializeCourseObject();
 
		// 13. Open the following URL - https://<UNIVERSITYURL/api/courses/active.
		course.goToAPICoursesActive(temp_instructor_user_name,1);
		 
		 
		 // 14. Make sure there is only one course  with true in the <IsPrivate> tag . 
		 course.veriftyThatOnlyOneCourseSetIsPrivateAsTrueInAPICourseActive();
		 
		 // 15. Make sure the private course is displayed in the course list page.
		 // 16. Make sure the private course name is assembeled from the <Instructor Name> (Not id) and 'sandbox course.
		 // 17. Open the private course.
		 course.goToCoursesPage();
		 
		 Thread.sleep(5000);
		
		 course.verifyCourseExist(temp_instructor_user_name+" sandbox course");
		 
		 
		 private_course_name_from_course_list = course.selectCourseThatStartingWith(temp_instructor_user_name);
		
		 if((private_course_name_from_course_list.contains(temp_instructor_user_name)) && (private_course_name_from_course_list.contains("sandbox course"))) {
			 System.out.println("Verified that private course name is assembled from <Instructor> + sandbox course.");
			 ATUReports.add("Verified that private course name constructed correctly.", "True.", "True.", LogAs.PASSED, null);
			 Assert.assertTrue(true);
		 } else {
			 System.out.println("Fail to verify that private course name constructed as requested.");
			 ATUReports.add("Verified that private course name constructed correctly.", "True.", "False.", LogAs.FAILED, null);
			 Assert.assertTrue(false);
		 }
		 
		 course_name_of_recordings_page  = record.getCourseTitle();
		 record.verifyThatStringExistsInCourseName(course_name_of_recordings_page.substring(0, username.length()));
		 record.verifyThatStringExistsInCourseName(course_name_of_recordings_page.substring(username.length()+1, course_name_of_recordings_page.length()-1));
	
		 
		 // 18. The course details page is disaplyed.
		 record.verifyThatItIsRecordingsPage();
		 
		 // 19. The 'Start a Recording' button is present.
		 record.veriftyThatStartRecordingButtonDisplayed();
		 
		 // 20. Sign Out.
		 top_bar_helper.signOut();
		
		 
		 // 20.1. Login as User1.
		 tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
		 tegrity.loginCourses("User1");// log in courses page
		 initializeCourseObject();
		 
		 // 20.2. Get name of Ab course.
		 String Ab_course_name = course.selectCourseThatStartingWith("Ab");
		 
		 // 20.3. Logout.
		 top_bar_helper.signOut();
		 
		 // 21. Login as admin.
		 tegrity.loginAdmin("Admin");
		 
		 // 22. Enroll the instructor you created earlier to Ab course as instructor.
		 Thread.sleep(3000);
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
		manage_adhoc_courses_enrollments_page.searchAndFilterCourses(Ab_course_name);
		 
		// Click on result first course (the only one) membership button
		manage_adhoc_courses_enrollments_page.clickOnFirstCourseMembershipButton();	
		 
		// Search target user name in membership window
		mange_ad_hoc_courses_membership_window.searchForUser(temp_instructor_user_name);	
	
		// Select first user from user list (the only user it found because of the uniq of the search)
		mange_ad_hoc_courses_membership_window.selectFirstUserFromUserList();
	
		// Add selected user to instructor list
		mange_ad_hoc_courses_membership_window.clickOnAddSelectedUserToInstructorList();
		
		mange_ad_hoc_courses_membership_window.waitMaxTimeUntillInstructorEnrollToCourse(temp_instructor_user_name);
		
		mange_ad_hoc_courses_membership_window.waitForVisibility(mange_ad_hoc_courses_membership_window.ok_button);
		
		// Confirm user membership list
		mange_ad_hoc_courses_membership_window.clickOnOkButton();
	
		mange_ad_hoc_courses_membership_window.wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("ctl00_ContentPlaceHolder1_ucAddMemberships_ucDialog_ButtonAddInstructor")));
		
		// 23. Sign Out.
		for(String window: driver.getWindowHandles()) {
			driver.switchTo().window(window);
			break;
		}
	
				
		top_bar_helper.signOut();
		
		// 24. Login as the instructor you created.
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
		tegrity.loginCoursesByParameter(temp_instructor_user_name);// log in courses page
		initializeCourseObject(); 
		 
		// 25. Open the following URL - https://<UNIVERSITYURL/api/courses/active.
		course.goToAPICoursesActive(temp_instructor_user_name,1);
		
		// 26. Make sure there is only one course with true in the <IsPrivate> tag.
		course.veriftyThatOnlyOneCourseSetIsPrivateAsTrueInAPICourseActive();	

		 // 27. Make sure the private course is displayed in the course list page.
		 course.goToCoursesPage();
		 
		 Thread.sleep(5000);
		 
		 wait.until(ExpectedConditions.visibilityOf(course.course_list.get(0)));
		
		 course.verifyCourseExist(temp_instructor_user_name+" sandbox course");
		 
		 // 28. Sign Out.
		 top_bar_helper.signOut();
		 
		 // 29 Login as admin.
		 tegrity.loginAdmin("Admin");	
		 
		 // 30. Create a user. 
		 Thread.sleep(4000);
		 admin_dashboard_page.clickOnTargetSubmenuUsers("Manage Ad-hoc Users (User Builder)");
		 
		 date = new Date();
		 sdf = new SimpleDateFormat("ddMMyyyyhhmmss");
		 String temp_student_user_name = "StudentTemp" + sdf.format(date);
		  
		 for(int i=0; i<5; i++ ) {
				try {
					driver.switchTo().frame(0);
					break;
				} catch(Exception msg) {
					Thread.sleep(1000);
				}
		}
		 
		 mange_adhoc_users_page.clickOnNewUser();
			
		 create_new_user_window.createNewUser(temp_student_user_name, temp_student_user_name, "abc@com.com", "111", "111");
	
	
		
		 for(String window: driver.getWindowHandles()) {
		 	driver.switchTo().window(window);
		 	break;
		 }
		 
		 manage_adhoc_courses_enrollments_page.clickOnAdminDashboard();
		 
		 wait.until(ExpectedConditions.visibilityOf(admin_dashboard_page.courses_submenu.get(1)));
		 
		 // 31. Enroll the user as student to a course.
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
		mange_ad_hoc_courses_membership_window.clickOnAddSelectedUserToInstructorList();
		
		mange_ad_hoc_courses_membership_window.waitMaxTimeUntillInstructorEnrollToCourse(temp_student_user_name);
		
			 
		// Confirm user membership list
		mange_ad_hoc_courses_membership_window.clickOnOkButton();
					
		mange_ad_hoc_courses_membership_window.wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("ctl00_ContentPlaceHolder1_ucAddMemberships_ucDialog_ButtonAddInstructor")));	
			 
		// 32. Sign Out.
		for(String window: driver.getWindowHandles()) {
			driver.switchTo().window(window);
			break;
		}
			
			
		top_bar_helper.signOut();
		
		
		// 33. Login as the student.
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
		tegrity.loginCoursesByParameter(temp_student_user_name);
		initializeCourseObject();
		
		// 34. Sign Out.
		top_bar_helper.signOut();
		
		// 35. Login as Admin.
		tegrity.loginAdmin("Admin");	
		
		Thread.sleep(4000);
		
		// 36. Enroll the student user to a course as instructor.
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
		manage_adhoc_courses_enrollments_page.searchAndFilterCourses(Ab_course_name);
		 
		// Click on result first course (the only one) membership button
		manage_adhoc_courses_enrollments_page.clickOnFirstCourseMembershipButton();	
		 
		// Search target user name in membership window
		mange_ad_hoc_courses_membership_window.searchForUser(temp_student_user_name);	
	
		// Select first user from user list (the only user it found because of the uniq of the search)
		mange_ad_hoc_courses_membership_window.selectFirstUserFromUserList();
	
		// Add selected user to instructor list
		mange_ad_hoc_courses_membership_window.clickOnAddSelectedUserToInstructorList();
		
		mange_ad_hoc_courses_membership_window.waitMaxTimeUntillInstructorEnrollToCourse(temp_instructor_user_name);
		
		mange_ad_hoc_courses_membership_window.waitForVisibility(mange_ad_hoc_courses_membership_window.ok_button);
		
		// Confirm user membership list
		mange_ad_hoc_courses_membership_window.clickOnOkButton();
	
		mange_ad_hoc_courses_membership_window.wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("ctl00_ContentPlaceHolder1_ucAddMemberships_ucDialog_ButtonAddInstructor")));
		
		// 37. Sign Out.
		for(String window: driver.getWindowHandles()) {
			driver.switchTo().window(window);
			break;
		}
	
				
		top_bar_helper.signOut();
		
		// 38. Login as the Instructor.
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
		tegrity.loginCoursesByParameter(temp_student_user_name);
		initializeCourseObject();
		 
		// 39. Go to url - https://<UNIVERSITYURL/api/courses/active.
		course.goToAPICoursesActive(temp_student_user_name,1);
				 
				 
		// 40. Make sure there is only one course with true in the <IsPrivate> tag.
		parsed_private_course_name = course.veriftyThatOnlyOneCourseSetIsPrivateAsTrueInAPICourseActive();
				 
		// 41. Make sure the private course is displayed in the course list page.
		// 42. Make sure the private course name is assembeled from the <Instructor Name> (Not id) and 'sandbox course' text.
		// 43. Open the private course.
		course.goToCoursesPage();
				 
		Thread.sleep(5000);
				
		course.verifyCourseExist(parsed_private_course_name);
				 
				 
		private_course_name_from_course_list = course.selectCourseThatStartingWith(temp_student_user_name);
				
		if((private_course_name_from_course_list.contains(temp_student_user_name)) && (private_course_name_from_course_list.contains("sandbox course"))) {
			 System.out.println("Verified that private course name is assembled from <Instructor> + sandbox course.");
			 ATUReports.add("Verified that private course name constructed correctly.", "True.", "True.", LogAs.PASSED, null);
			 Assert.assertTrue(true);
		} else {
			System.out.println("Fail to verify that private course name constructed as requested.");
			ATUReports.add("Verified that private course name constructed correctly.", "True.", "False.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
				 
		course_name_of_recordings_page  = record.getCourseTitle();
		record.verifyThatStringExistsInCourseName(course_name_of_recordings_page.substring(0, username.length()));
		record.verifyThatStringExistsInCourseName(course_name_of_recordings_page.substring(username.length()+1, course_name_of_recordings_page.length()-1));
		Thread.sleep(1000);	
				 
		// 44. The course details page is disaplyed.
		record.verifyThatItIsRecordingsPage();
				 
		// 45. The 'Start a Recording' button is present.
		record.veriftyThatStartRecordingButtonDisplayed();
		
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);

}}
