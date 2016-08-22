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

public class TC17944VerifyCoursedisplayedInPastCoursesAfterDeletion {
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
        List <String> for_enroll;
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
		public void testUiExistence() throws InterruptedException {
		
			
			
			// 1.load page
			tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
		
			Date date = new Date();
			 SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyhhmmss");
			 instructor1 = "user" + sdf.format(date);
			 for_enroll=new ArrayList<String>();
				for_enroll.add(instructor1);
				
			 String course_for_delete="CourseForDelete"+sdf.format(date);
			 tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);

			 tegrity.loginAdmin("Admin");
			 Thread.sleep(5000);
		
			 // 2. Click on user builder href link
			 admin_dashboard_page.clickOnTargetSubmenuUsers("Manage Ad-hoc Users (User Builder)");
			 Thread.sleep(10000);
			 //create new user
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
			 // 4. Click on course builder href link
			  admin_dashboard_page.clickOnTargetSubmenuCourses("Manage Ad-hoc Courses / Enrollments (Course Builder)");			
			  Thread.sleep(10000);
			 ///5.build new course
			  driver.switchTo().frame(0);
				 Thread.sleep(4000);
			  mange_adhoc_course_enrollments.clickOnNewCourse();
			Thread.sleep(3000);
			
			create_new_course_window.createNewCourse(course_for_delete , course_for_delete);
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
		       
		 		//enroll first user
			    mange_adhoc_course_enrollments.enrollInstructorToCourse(course_for_delete , for_enroll, mangage_adhoc_courses_membership_window);
				Thread.sleep(5000); 
		 		mangage_adhoc_courses_membership_window.searchForUser(PropertyManager.getProperty("SuperUser"));
		 		Thread.sleep(5000);
				// Select first user from user list (the only user it found because of the uniq of the search)
				mangage_adhoc_courses_membership_window.selectFirstUserFromUserList();
				Thread.sleep(5000);
				// Add selected user to instructor list
				mangage_adhoc_courses_membership_window.clickOnAddSelectedUserToInstructorList();
				Thread.sleep(5000); 
		 		mangage_adhoc_courses_membership_window.ok_button.click();
		 		Thread.sleep(1000);
		 	    driver.switchTo().alert().accept();
		 	    Thread.sleep(2000);
		 	    
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
			     course.copyRecordingFromCourseStartWithToCourseStartWithOfType("BankValidRecordings",course_for_delete ,0, record, copy, confirm_menu);
			     Thread.sleep(2000);
			     for (String window : driver.getWindowHandles()) {
					 driver.switchTo().window(window);
					 break;
					 }
			     course.sign_out.click();
			     
			     //////ownership change
			     tegrity.loginCoursesByParameter(instructor1);
			     Thread.sleep(3000);
			     course.selectCourseThatStartingWith(course_for_delete);
			     Thread.sleep(3000);
			     ///check for free status checkbox for edit properties
		    	record.changeRecordingOwnership(confirm_menu, erp_window, instructor1,record.checkbox);
		           record.clickOnSignOut();
			     
				 
			
			///////////////////////////////////////////////end of preset
		           
			
			
			
			
			
			
			// 2.login as admin
	        tegrity.loginAdmin("Admin");// log in courses page
			Thread.sleep(5000);
		//3.click on course builder
	       admin_dashboard_page.clickOnTargetSubmenuCourses("Manage Ad-hoc Courses / Enrollments (Course Builder)");
		Thread.sleep(5000);
	       ///4.Click the "Delete" link relevant to the course	

 		Thread.sleep(10000);
 		
 		// 3. Click on create course href link 
 		driver.switchTo().frame(0);
 		//mange_adhoc_course_enrollments.clickOnNewCourse();
 		Thread.sleep(2000);
 		
 		// Search target course name
 		mange_adhoc_course_enrollments.searchAndFilterCourses(course_for_delete);
 		
 		Thread.sleep(7000);
			mange_adhoc_course_enrollments.clickOnFirstCourseDeleteButton();
			Thread.sleep(3000);
			///5.click on ok to delete
			
			
			Thread.sleep(2000);	
			 driver.switchTo().alert().accept();
			System.out.println("clicked on ok");
			   Thread.sleep(2000);
		 	  ///6. sign out  
				 for (String window : driver.getWindowHandles()) {
				 driver.switchTo().window(window);
				 break;
				 }
				
			     mange_adhoc_course_enrollments.clickOnAdminDashboard();
			     Thread.sleep(3000);
			      admin_dashboard_page.sign_out.click();
			     Thread.sleep(2000);
			      ///7.login as instructor
			     tegrity.loginCoursesByParameter(instructor1);
			     initializeCourseObject();
			     Thread.sleep(2000);
			     //8.click on past couses tab
			     course.clickOnPastCoursesTabButton();
			     Thread.sleep(2000);
			//9.Verify that the deleted course is displayed in "Past Courses" tab
			try {
			     course.selectCourseByName(course_for_delete);
		         System.out.println("contains course: "+course_for_delete );
					ATUReports.add("contains course: "+course_for_delete, LogAs.PASSED, null);
                    Assert.assertTrue(true);
			}
			catch(Exception e)
			{
				 System.out.println(" doe not contain course: "+course_for_delete );
				 ATUReports.add(" doe not contain course: "+course_for_delete, LogAs.FAILED, null);
				    Assert.assertTrue(false);
			}
			//quit
			    driver.quit();
		}
		// description = "get courses list"
		public void initializeCourseObject() throws InterruptedException {

			course = PageFactory.initElements(driver, CoursesHelperPage.class);
			course.courses = course.getStringFromElement(course.course_list);
		}
}
