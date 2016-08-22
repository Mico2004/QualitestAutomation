package com.automation.main;

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
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import junitx.util.PropertyManager;
/////////////////awork around because i cant upload recording only copy!!!!!!!!!!!!!!!!!!!!!
public class TC17938VerifyPastCourseWithTestRecording {
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
		List<String> for_enroll;
		List <String> list_student;
		@BeforeClass
		public void setup() {

			driver = DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());
			driver.manage().window().maximize();

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

		}

		@Test
		public void test17938() throws Exception {

			// 1.load page
			tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
			instructor1 = "inst109032016024010";
			instructor2 = "inst209032016024010";
			String student="student09032016024010";
			String superuser = PropertyManager.getProperty("SuperUser");
			// String
			// course_with_one_additional_content="CourseOneAdittionalContent08032016050918";

			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyhhmmss");
			instructor1 = "inst1" + sdf.format(date);
			instructor2 = "inst2" + sdf.format(date);
            student="student"+ sdf.format(date);
			for_enroll = new ArrayList<String>();
			list_student= new ArrayList<String>();
			for_enroll.add(instructor1);
			for_enroll.add(instructor2);
			for_enroll.add(superuser);
            list_student.add(student);
			String course_with_one_test_recording = "CourseOneTestRecording" + sdf.format(date);
			tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);

			tegrity.loginAdmin("Admin");
			Thread.sleep(5000);

			// 2. Click on user builder href link
			admin_dashboard_page.clickOnTargetSubmenuUsers("Manage Ad-hoc Users (User Builder)");
			Thread.sleep(10000);
			// create new user instructor 1
			mange_adhoc_users_page.createNewUser(instructor1, create_new_user_window);
			Thread.sleep(2000);
			for (String window : driver.getWindowHandles()) {
				driver.switchTo().window(window);
				break;
			}
			// create new user instructor 2
			mange_adhoc_users_page.createNewUser(instructor2, create_new_user_window);
			Thread.sleep(2000);
			for (String window : driver.getWindowHandles()) {
				driver.switchTo().window(window);
				break;
			}
			// create new user student
						mange_adhoc_users_page.createNewUser(student, create_new_user_window);
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
			// 4. Click on course builder href link
			admin_dashboard_page.clickOnTargetSubmenuCourses("Manage Ad-hoc Courses / Enrollments (Course Builder)");
			Thread.sleep(10000);
			/// 5.build new course
			driver.switchTo().frame(0);
			Thread.sleep(4000);
			mange_adhoc_course_enrollments.clickOnNewCourse();
			Thread.sleep(3000);

			create_new_course_window.createNewCourse(course_with_one_test_recording, course_with_one_test_recording);
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
			Thread.sleep(3000);

			// enroll instructors
			mange_adhoc_course_enrollments.enrollInstructorToCourse(course_with_one_test_recording, for_enroll,
					mangage_adhoc_courses_membership_window);
			Thread.sleep(5000);
		
			for (String window : driver.getWindowHandles()) {
				driver.switchTo().window(window);
				break;
			}
			Thread.sleep(3000);
			// enroll student
						mange_adhoc_course_enrollments.enrollStudentsToCourse(course_with_one_test_recording, list_student, mangage_adhoc_courses_membership_window);
						Thread.sleep(5000);
			for (String window : driver.getWindowHandles()) {
				driver.switchTo().window(window);
				break;
			}

			mange_adhoc_course_enrollments.clickOnAdminDashboard();
			Thread.sleep(2000);

			admin_dashboard_page.signOut();
			Thread.sleep(3000);
			tegrity.loginCourses("SuperUser");
			Thread.sleep(3000);
			course.selectCourseThatStartingWith("BankValidRecordings");
			initializeCourseObject();
			Thread.sleep(3000);
			record.clickOnTestsTab();
			Thread.sleep(2000);
			record.checkbox.click();/// only one recording
			Thread.sleep(2000);
			record.clickOnRecordingTaskThenCopy();
			Thread.sleep(3000);
			copy.selectTargetCourseFromCourseList(course_with_one_test_recording);
			Thread.sleep(2000);
			copy.clickOnCopyButton();
			Thread.sleep(2000);
			confirm_menu.clickOnOkButtonAfterConfirmCopyRecording();
			Thread.sleep(2000);
			record.waitUntilFirstRecordingBeingCopiedFromStatusDissaper();
			Thread.sleep(2000);
			for (String window : driver.getWindowHandles()) {
				driver.switchTo().window(window);
				break;
			}
			record.sign_out.click();
			Thread.sleep(3000);
			

			/////////////////////////////////////////////// end of preset

			//// test17929 begins
			// String
			//// course_with_one_additional_content="CourseOneAdittionalContent08032016014656";
			
			tegrity.loginCoursesByParameter(student);
			Thread.sleep(3000);
			course.signOut();
			Thread.sleep(2000);
		//// login as a instructor and change ownership
					tegrity.loginCoursesByParameter(instructor1);
					Thread.sleep(3000);
					course.selectCourseThatStartingWith(course_with_one_test_recording);
					Thread.sleep(3000);
					record.clickOnTestTab();
					Thread.sleep(2000);
					record.changeRecordingOwnership(confirm_menu, erp_window, student,record.checkbox);
					Thread.sleep(2000);
					for (String window : driver.getWindowHandles()) {
						driver.switchTo().window(window);
						break;
					}
					course.sign_out.click();
					Thread.sleep(2000);
			
		
			/// 1.Login as ADMIN
			tegrity.loginAdmin("Admin");
			Thread.sleep(2000);
			/// 2.Click the "Course Builder" link
			admin_dashboard_page.clickOnTargetSubmenuCourses("Manage Ad-hoc Courses / Enrollments (Course Builder)");
			Thread.sleep(10000);
			/// 3.Click the "Membership" link related to the course+unenroll
			/// instructor 1
			System.out.println("before 3");
			mange_adhoc_course_enrollments.unEnrollInstructorToCourse(course_with_one_test_recording, instructor1,
					mangage_adhoc_courses_membership_window);
			Thread.sleep(4000);

			for (String window : driver.getWindowHandles()) {
				driver.switchTo().window(window);
				break;
			}

			mange_adhoc_course_enrollments.clickOnAdminDashboard();
			Thread.sleep(2000);
			admin_dashboard_page.signOut();
			Thread.sleep(2000);
			tegrity.loginCoursesByParameter(instructor1);
			Thread.sleep(3000);

			/// 4.by clicking it we verify it exists
			course.clickOnPastCoursesTabButton();
			System.out.println("after 4");
			Thread.sleep(1500);
			initializeCourseObject();
			/// 5.verify course exists in list
			course.verifyCourseExist(course_with_one_test_recording);
			System.out.println("after 5");
			/// 6.select course
			course.selectCourseThatStartingWith(course_with_one_test_recording);
			Thread.sleep(3000);
			/// 7.Click the "Additional Content" tab
			record.clickOnTestTab();
			Thread.sleep(2000);
			// 8.delete recording
			System.out.println("before 8");
			record.checkbox.click();
			record.clickOnRecordingTaskThenDelete();
			Thread.sleep(2000);
			delete_menu.clickOnDeleteButton();
			Thread.sleep(2000);
			/// 9.verify no tests tab after delete
			record.verifyNoTestsTab();
			/// 10. USER automatically redirected to "Recordings" tab
			System.out.println(driver.findElement(By.xpath("//*[@id=\"main\"]/div[2]/ul/li[1]")).getAttribute("class"));
		     ///9.verify redirected to recording
		     if(driver.findElement(By.xpath("//*[@id=\"main\"]/div[2]/ul/li[1]")).getAttribute("class").equals("active"))
		     {
				System.out.println("redirected to recording tab");
				ATUReports.add("redirected to recording tab", LogAs.PASSED, null);
				Assert.assertTrue(true);
			} else {
				System.out.println("not redirected to recording tab");
				ATUReports.add("not redirected to recording tab", LogAs.FAILED, null);
				Assert.assertTrue(false);
			}

			for (String window : driver.getWindowHandles()) {
				driver.switchTo().window(window);
				break;
			}
			record.returnToCourseListPage();
			Thread.sleep(3000);
			/// verify no past courses tab
			course.verifyNoPastCoursesTab();

			for (String window : driver.getWindowHandles()) {
				driver.switchTo().window(window);
				break;
			}
			course.signOut();
			Thread.sleep(3000);
			// login as instructor 2
			tegrity.loginCoursesByParameter(instructor2);
			Thread.sleep(3000);
			initializeCourseObject();
			course.clickOnActiveCoursesTabButton();
			course.verifyCourseExist(course_with_one_test_recording);
			// quit
			driver.quit();

		}

		// description = "get courses list"
		public void initializeCourseObject() throws InterruptedException {

			course = PageFactory.initElements(driver, CoursesHelperPage.class);
			course.courses = course.getStringFromElement(course.course_list);
		}
}
