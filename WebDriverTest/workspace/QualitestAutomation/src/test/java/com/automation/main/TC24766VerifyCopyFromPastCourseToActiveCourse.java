package com.automation.main;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;import com.automation.main.page_helpers.Page;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import junitx.util.PropertyManager;

public class TC24766VerifyCopyFromPastCourseToActiveCourse {
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
			public PlayerPage player_page;
			String instructor1;
			String instructor2;
			List<String> for_enroll;
			List <String> list_student;
			
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
                player_page=PageFactory.initElements(driver, PlayerPage.class);
                
                Date curDate = new Date();
                String DateToStr = DateFormat.getInstance().format(curDate);
                System.out.println("Starting the test: TCase24766VerifyCopyFromPastCourseToActiveCourse at " + DateToStr);
                ATUReports.add("Message window.", "Starting the test: TCase24766VerifyCopyFromPastCourseToActiveCourse at " + DateToStr,
                "Starting the test: TCase24766VerifyCopyFromPastCourseToActiveCourse at " + DateToStr, LogAs.PASSED, null);	
                
			}

			@Test
			public void test24766() throws Exception {
				////pre conditions:creating instructor1 who has 2 past courses and 1 active
				
				// 1.load page
				/*
				tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
				instructor1 = "inst110032016025435";
				
				String superuser = PropertyManager.getProperty("SuperUser");
				// String
				// course_with_one_additional_content="CourseOneAdittionalContent08032016050918";
                String past_course_a ="PastCourseA" ;
                String past_course_b="PastCourseBawsserverautomation101032016110122_Name";
                String active_course="Abawsserverautomation101032016110122_Name";
				Date date = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyhhmmss");
				instructor1 = "inst1" + sdf.format(date);
			
				for_enroll = new ArrayList<String>();
				
				for_enroll.add(instructor1);
			///	for_enroll.add(instructor2);
			//	for_enroll.add(superuser);
				String course_with_one_test_recording = "CourseOneTestRecording" + sdf.format(date);
				tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
                tegrity.loginCourses("User1");
				Thread.sleep(Page.TIMEOUT_TINY);
				initializeCourseObject();
				
				///taking courses names and deliver them to relevant  courses  variables
				for(String element:course.courses)
				{
					if (element.startsWith("PastCourseA"))
					{
						past_course_a=element;
					}
					if (element.startsWith("PastCourseB"))
					{
						past_course_b=element;
					}
				if(element.startsWith("Ab"))
				{
					active_course=element;
				}
				}
				course.signOut();
				tegrity.loginAdmin("Admin");
				Thread.sleep(Page.TIMEOUT_TINY);

				// 2. Click on user builder href link
				admin_dashboard_page.clickOnTargetSubmenuUsers("Manage Ad-hoc Users (User Builder)");
				Thread.sleep(Page.TIMEOUT_TINY);
				// create new user instructor 1
				mange_adhoc_users_page.createNewUser(instructor1, create_new_user_window);
				Thread.sleep(Page.TIMEOUT_TINY);
				for (String window : driver.getWindowHandles()) {
					driver.switchTo().window(window);
					break;
				}
			
				
				mange_adhoc_users_page.toAdminDashBoard();

				Thread.sleep(Page.TIMEOUT_TINY);

				for (String window : driver.getWindowHandles()) {
					driver.switchTo().window(window);
					break;
				}
				Thread.sleep(Page.TIMEOUT_TINY);
				// 4. Click on course builder href link
				admin_dashboard_page.clickOnTargetSubmenuCourses("Manage Ad-hoc Courses / Enrollments (Course Builder)");
				Thread.sleep(Page.TIMEOUT_TINY);
				/// 5.build new course
				
				for (String window : driver.getWindowHandles()) {
					driver.switchTo().window(window);
					break;
				}

				// enroll instructors
				mange_adhoc_course_enrollments.enrollInstructorToCourse(past_course_a, for_enroll,mangage_adhoc_courses_membership_window);
				Thread.sleep(Page.TIMEOUT_TINY);
				for (String window : driver.getWindowHandles()) {
					driver.switchTo().window(window);
					break;
				}
				// enroll instructors
				mange_adhoc_course_enrollments.enrollInstructorToCourse(past_course_b, for_enroll,mangage_adhoc_courses_membership_window);
				Thread.sleep(Page.TIMEOUT_TINY);
				// enroll instructors
				for (String window : driver.getWindowHandles()) {
					driver.switchTo().window(window);
					break;
				}
				mange_adhoc_course_enrollments.enrollInstructorToCourse(active_course, for_enroll,mangage_adhoc_courses_membership_window);
				Thread.sleep(Page.TIMEOUT_TINY);
				for (String window : driver.getWindowHandles()) {
					driver.switchTo().window(window);
					break;
				}
				Thread.sleep(Page.TIMEOUT_TINY);
		
				mange_adhoc_course_enrollments.clickOnAdminDashboard();
				Thread.sleep(Page.TIMEOUT_TINY);

				admin_dashboard_page.signOut();
				Thread.sleep(Page.TIMEOUT_TINY);	
				
				tegrity.loginCoursesByParameter(instructor1);
				Thread.sleep(Page.TIMEOUT_TINY);
				course.selectCourseThatStartingWith("PastCourseA");
				initializeCourseObject();
				Thread.sleep(Page.TIMEOUT_TINY);
				//record.checkbox2.click();/// only one recording
				Thread.sleep(Page.TIMEOUT_TINY);
		    	record.changeRecordingOwnership(confirm_menu, erp_window, instructor1,record.checkbox2);
			    Thread.sleep(Page.TIMEOUT_TINY);
		    	
			    for (String window : driver.getWindowHandles()) {
					driver.switchTo().window(window);
					break;
				}
			    record.returnToCourseListPage();
			    Thread.sleep(Page.TIMEOUT_TINY);
			    
			    course.selectCourseThatStartingWith("PastCourseB");
				initializeCourseObject();
				Thread.sleep(Page.TIMEOUT_TINY);
			
				//record.checkbox2.click();/// only one recording
				Thread.sleep(Page.TIMEOUT_TINY);
		    	record.changeRecordingOwnership(confirm_menu, erp_window, instructor1,null);
			    Thread.sleep(Page.TIMEOUT_TINY);
		    	
			    for (String window : driver.getWindowHandles()) {
					driver.switchTo().window(window);
					break;
				}
		    	
		    	
		    	
		    	record.sign_out.click();
				Thread.sleep(Page.TIMEOUT_TINY);

	             ///1.Login as ADMIN
	             tegrity.loginAdmin("Admin");
	             Thread.sleep(Page.TIMEOUT_TINY);
	             ///2.Click the "Course Builder" link
	             admin_dashboard_page.clickOnTargetSubmenuCourses("Manage Ad-hoc Courses / Enrollments (Course Builder)");			
		 		  Thread.sleep(Page.TIMEOUT_TINY);
	             ///3.Click the "Membership" link related to the course+unenroll instructor 1
	             mange_adhoc_course_enrollments.unEnrollInstructorToCourse(past_course_a, instructor1, mangage_adhoc_courses_membership_window);
	             Thread.sleep(Page.TIMEOUT_TINY);

				 for (String window : driver.getWindowHandles()) {
				 driver.switchTo().window(window);
				 break;
				 }
				 mange_adhoc_course_enrollments.unEnrollInstructorToCourse(past_course_b, instructor1, mangage_adhoc_courses_membership_window);
	             Thread.sleep(Page.TIMEOUT_TINY);

				 for (String window : driver.getWindowHandles()) {
				 driver.switchTo().window(window);
				 break;
				 }
				 mange_adhoc_course_enrollments.sign_out.click();
				
				
				
*/
				/////////////////////////////////////////////// end of preset

/////////////////// test17929 begins///////////////
				 
				 
				 
				 
			////1. login as a instructor and change ownership
				tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
						tegrity.loginCourses("User1");
						Thread.sleep(Page.TIMEOUT_TINY);
						String des_start_with="Ab";
						String destination_course_name=null;
						for (String course_name : course.getCourseList()) {
							if (course_name.startsWith(des_start_with)) {
								destination_course_name = course_name;
							
						}
						}
			///2.click past courses tab
						course.clickOnPastCoursesTabButton();
						Thread.sleep(Page.TIMEOUT_TINY);
			///3.select past course
						course.selectCourseByName("PastCourseA");
						Thread.sleep(Page.TIMEOUT_TINY);
                        ///4.Select the recording
					     record.recordings_tab.click(); 
					     Thread.sleep(Page.TIMEOUT_TINY);
					     record.convertRecordingsListToNames();
					     String recordings=record.getFirstRecordingTitle();
					record.getCheckbox().click();
					//5.Select the "Recording Tasks -> Copy" menu item
					record.clickOnRecordingTaskThenCopy();
					Thread.sleep(Page.TIMEOUT_TINY);
					//6.Select an active course and click the 'Copy' button
					copy.selectTargetCourseFromCourseList(destination_course_name);
					Thread.sleep(Page.TIMEOUT_TINY);
						copy.clickOnCopyButton();
					Thread.sleep(Page.TIMEOUT_TINY);
					confirm_menu.clickOnOkButton();
					Thread.sleep(Page.TIMEOUT_TINY);
					//7.Click the "Additional Content" tab
						record.clickOnAdditionContentTab();
						Thread.sleep(Page.TIMEOUT_TINY);
						String additional_content=record.first_additional_content_title.getText();
						record.getCheckbox().click();	
			///8.Select the "Content tasks -> Copy" menu item
				
						record.clickOnContentTaskThenCopy();
					  Thread.sleep(Page.TIMEOUT_TINY);
						copy.selectTargetCourseFromCourseList(destination_course_name);
						Thread.sleep(Page.TIMEOUT_TINY);
							copy.clickOnCopyButton();
						Thread.sleep(Page.TIMEOUT_TINY);	    
						confirm_menu.clickOnOkButton();
						Thread.sleep(Page.TIMEOUT_TINY);
						//9.Click the "student recordings" tab
						record.clickOnStudentRecordingsTab();
						Thread.sleep(Page.TIMEOUT_TINY);
						String student_recording=record.getFirstRecordingTitle();
						record.getCheckbox().click();	
			///10.Select the "Content tasks -> Copy" menu item
						record.clickOnRecordingTaskThenCopy();
						Thread.sleep(Page.TIMEOUT_TINY);
						copy.selectTargetCourseFromCourseList(destination_course_name);						
						Thread.sleep(Page.TIMEOUT_TINY);
							copy.clickOnCopyButton();
						Thread.sleep(Page.TIMEOUT_TINY);	  
						confirm_menu.clickOnOkButton();
						Thread.sleep(Page.TIMEOUT_TINY);
					///11.Click on the 'Courses' breadcrumb	
						record.returnToCourseListPage();
				///12.Select the destination course
                      Thread.sleep(Page.TIMEOUT_TINY);
				course.clickOnActiveCoursesTabButton();
				Thread.sleep(Page.TIMEOUT_TINY);
				course.selectCourseThatStartingWith("Ab");
				Thread.sleep(Page.TIMEOUT_TINY);
				///13.Verfiy the copied recording was moved correctly
			  record.clickOnRecordingsTab();
				record.convertRecordingsListToNames();
			int index=record.recording_list_names.indexOf(recordings);
			if((record.recording_list_names.contains(recordings))&&(record.checkThatRecordingStatusTargetIndexIsNotXWithTimeout(index, "Error", 1)))	
			{
				System.out.println("Verfiy the copied recording was moved correctly");
				ATUReports.add("Verfiy the copied recording was moved correctly", "status", "Not Error", "Not Error", LogAs.PASSED, null);
				Assert.assertTrue(true);
			}
			else
			{
				System.out.println("Fail Verfiy the copied recording was moved correctly");
				ATUReports.add("Verfiy the copied recording was moved correctly", "status", "Not Error", " Error", LogAs.FAILED, null);
				Assert.assertTrue(false);
			}
			//14.dispaly recording
			record.selectRecordingByName(recordings);
			Thread.sleep(Page.TIMEOUT_TINY);
			driver.findElement(By.cssSelector(".panel-body>.video-outer.ng-scope>.video-wrap")).click();
			Thread.sleep(Page.TIMEOUT_TINY);
		
			player_page.verifyTimeBufferStatusForXSec(25);// check source display

			///// to go back to crecording window handler
			for (String handler : driver.getWindowHandles()) {
				driver.switchTo().window(handler);

			}			
			
			player_page.returnToCoursesPage(course);
			Thread.sleep(Page.TIMEOUT_TINY);
			course.selectCourseThatStartingWith("Ab");
			Thread.sleep(Page.TIMEOUT_TINY);
			///15.select couese and press additional content tab
			record.clickOnAdditionContentTab();
			Thread.sleep(Page.TIMEOUT_TINY);
		//16.Verfiy the copied aditional content was moved correctly
			record.convertAdditionalContantListToNames();
			 index=record.additional_content_list_names.indexOf(additional_content);
			if((record.additional_content_list_names.contains(additional_content))&&(record.checkThatAdditionalContentFileStatusTargetIndexIsNotXWithTimeout(index, "Error", 1)))	
			{
				System.out.println("Verfiy the copied aditional content was moved correctly");
				ATUReports.add("Verfiy the copied aditional content was moved correctly", "status", "Not Error", "Not Error", LogAs.PASSED, null);
				Assert.assertTrue(true);
			}
			else
			{
				System.out.println("Fail Verfiy the copied aditional content was moved correctly");
				ATUReports.add("Verfiy the copied aditional content was moved correctly", "status", "Not Error", " Error", LogAs.FAILED, null);
				Assert.assertTrue(false);
			}

			Thread.sleep(Page.TIMEOUT_TINY);
			///17.Verfiy the copied student recording was moved correctly
			record.clickOnStudentRecordingsTab();
			Thread.sleep(Page.TIMEOUT_TINY);
			record.convertRecordingsListToNames();
			 index=record.recording_list_names.indexOf(student_recording);
			if((record.recording_list_names.contains(student_recording))&&(record.checkThatRecordingStatusTargetIndexIsNotXWithTimeout(index, "Error", 1)))	
			{
				System.out.println("Verfiy the copied student recording was moved correctly");
				ATUReports.add("Verfiy the copied student recording was moved correctly", "status", "Not Error", "Not Error", LogAs.PASSED, null);
				Assert.assertTrue(true);
			}
			else
			{
				System.out.println("Fail Verfiy the student recording content was moved correctly");
				ATUReports.add("Verfiy the copied student recording was moved correctly", "status", "Not Error", " Error", LogAs.FAILED, null);
				Assert.assertTrue(false);
			}
			record.selectRecordingByName(student_recording);
			Thread.sleep(Page.TIMEOUT_TINY);
			driver.findElement(By.cssSelector(".panel-body>.video-outer.ng-scope>.video-wrap")).click();
			Thread.sleep(Page.TIMEOUT_TINY);
		//18.1 dispaly recording
			player_page.verifyTimeBufferStatusForXSec(15);// check source display

			//18.2 go back to crecording window handler
			for (String handler : driver.getWindowHandles()) {
				driver.switchTo().window(handler);
				// System.out.println("=========================================");
				// System.out.println(driver.getPageSource());
			}
			
			System.out.println("Done.");
			ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
			
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
}
