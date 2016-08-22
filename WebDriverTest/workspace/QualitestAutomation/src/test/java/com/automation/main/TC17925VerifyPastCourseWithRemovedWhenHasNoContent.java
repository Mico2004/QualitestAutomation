package com.automation.main;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import junitx.util.PropertyManager;

public class TC17925VerifyPastCourseWithRemovedWhenHasNoContent {

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
	public void test17925() throws InterruptedException {


		// 1.load page
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
	
		Date date = new Date();
		 SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyhhmmss");
		 instructor1 = "inst1" + sdf.format(date);
		 instructor2 = "inst2" + sdf.format(date);
		String superuser=PropertyManager.getProperty("SuperUser");
		 for_enroll=new ArrayList<String>();
		for_enroll.add(instructor1);
		for_enroll.add(instructor2);
		for_enroll.add(superuser);
		 String course_with_one_recording="CourseOneRecording"+sdf.format(date);
		 tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);

		 tegrity.loginAdmin("Admin");
		 Thread.sleep(5000);
	
		 // 2. Click on user builder href link
		 admin_dashboard_page.clickOnTargetSubmenuUsers("Manage Ad-hoc Users (User Builder)");
		 Thread.sleep(10000);
		 //create new user instructor 1
		 mange_adhoc_users_page.createNewUser(instructor1, create_new_user_window);
		 Thread.sleep(2000);
		 for (String window : driver.getWindowHandles()) {
		 driver.switchTo().window(window);
		 break;
		 }
		 //create new user instructor 2
		 mange_adhoc_users_page.createNewUser(instructor2, create_new_user_window);
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
		
		create_new_course_window.createNewCourse(course_with_one_recording , course_with_one_recording);
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
		    mange_adhoc_course_enrollments.enrollInstructorToCourse(course_with_one_recording , for_enroll, mangage_adhoc_courses_membership_window);
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
             ///copy one recording to course
		     course.selectCourseThatStartingWith("BankValidRecording");
		     Thread.sleep(3000);
		     record.checkbox.click();
		     record.clickOnRecordingTaskThenCopy();
		     Thread.sleep(3000);
		     copy.selectTargetCourseFromCourseList(course_with_one_recording);
		     Thread.sleep(1000);
		     copy.clickOnCopyButton();
		     Thread.sleep(2000);
		     confirm_menu.clickOnOkButtonAfterConfirmCopyRecording();
		     ///make sure recording ended being copied
		     record.waitUntilFirstRecordingBeingCopiedFromStatusDissaper();
		     Thread.sleep(2000);
		    
		     for (String window : driver.getWindowHandles()) {
				 driver.switchTo().window(window);
				 break;
				 }
		     record.signOut();
		     Thread.sleep(3000);
		     tegrity.loginCoursesByParameter(instructor1);
		     Thread.sleep(3000);
		     course.selectCourseByName(course_with_one_recording);
		     Thread.sleep(3000);
		     record.changeRecordingOwnership(confirm_menu, erp_window, instructor1,record.checkbox);
		     Thread.sleep(2000);
		     for (String window : driver.getWindowHandles()) {
				 driver.switchTo().window(window);
				 break;
				 }
		     course.sign_out.click();
             Thread.sleep(3000);
		  
             ///////////////////////////////////////////////end of preset 

////test17925 begins
		  
             ///1.Login as ADMIN
             tegrity.loginAdmin("Admin");
             Thread.sleep(2000);
             ///2.Click the "Course Builder" link
             admin_dashboard_page.clickOnTargetSubmenuCourses("Manage Ad-hoc Courses / Enrollments (Course Builder)");			
	 		  Thread.sleep(10000);
             ///3.Click the "Membership" link related to the course+unenroll instructor 1
             mange_adhoc_course_enrollments.unEnrollInstructorToCourse(course_with_one_recording, instructor1, mangage_adhoc_courses_membership_window);
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
		     ///by clicking it we verify it exists
		     course.clickOnPastCoursesTabButton();
		      Thread.sleep(1500);
		     initializeCourseObject();
		     ///verify course exists in list
		     course.verifyCourseExist(course_with_one_recording);
		     ///select course
		     course.selectCourseThatStartingWith(course_with_one_recording);
		     Thread.sleep(3000);
		     //delete recording
		     record.checkbox.click();
		     record.toDeleteMenu();
		     Thread.sleep(2000);
		     delete_menu.clickOnDeleteButton();
		     ///click om breadcrumbs
		     Thread.sleep(2000);
		     for (String window : driver.getWindowHandles()) {
				 driver.switchTo().window(window);
				 break;
				 }
		     record.returnToCourseListPage();
		     Thread.sleep(3000);
		     ///verify no past courses tab
             course.verifyNoPastCoursesTab();
             
             for (String window : driver.getWindowHandles()) {
				 driver.switchTo().window(window);
				 break;
				 }
	          course.signOut();
	          Thread.sleep(3000);
	          //login as instructor 2
	          tegrity.loginCoursesByParameter(instructor2);
	          Thread.sleep(3000);
	          initializeCourseObject();
	          course.clickOnActiveCoursesTabButton();
	          course.verifyCourseExist(course_with_one_recording);
	        //quit
	          driver.quit();
		     
		
		     
	          
			 
		
	
	           
}
	// description = "get courses list"
	public void initializeCourseObject() throws InterruptedException {

		course = PageFactory.initElements(driver, CoursesHelperPage.class);
		course.courses = course.getStringFromElement(course.course_list);
	}
}