package com.automation.main;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.openqa.selenium.WebDriver;import com.automation.main.page_helpers.Page;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
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
		 System.out.println("Starting the test: TC17925VerifyPastCourseWithRemovedWhenHasNoContent at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC17925VerifyPastCourseWithRemovedWhenHasNoContent at " + DateToStr,
		 "Starting the test: TC17925VerifyPastCourseWithRemovedWhenHasNoContent at " + DateToStr, LogAs.PASSED, null);

	}

	@AfterClass
	public void closeBroswer() {		
		this.driver.quit();
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
		 Thread.sleep(Page.TIMEOUT_TINY);
	
		 // 2. Click on user builder href link
		 admin_dashboard_page.clickOnTargetSubmenuUsers("Manage Ad-hoc Users (User Builder)");
		 Thread.sleep(Page.TIMEOUT_TINY);
		 //create new user instructor 1
		 mange_adhoc_users_page.createNewUser(instructor1, create_new_user_window);
		 Thread.sleep(Page.TIMEOUT_TINY);
		 for (String window : driver.getWindowHandles()) {
		 driver.switchTo().window(window);
		 break;
		 }
		 //create new user instructor 2
		 mange_adhoc_users_page.createNewUser(instructor2, create_new_user_window);
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
		 ///5.build new course
		  driver.switchTo().frame(0);
			 Thread.sleep(Page.TIMEOUT_TINY);
		  mange_adhoc_course_enrollments.clickOnNewCourse();
		Thread.sleep(Page.TIMEOUT_TINY);
		
		create_new_course_window.createNewCourse(course_with_one_recording , course_with_one_recording);
		Thread.sleep(Page.TIMEOUT_TINY);
		try {
			driver.switchTo().alert().accept();
			
		} catch (Exception msg) {
			
		}
			Thread.sleep(Page.TIMEOUT_TINY);
	
		 for (String window : driver.getWindowHandles()) {
		 driver.switchTo().window(window);
		 break;
		 }
		    Thread.sleep(Page.TIMEOUT_TINY);
	       
	 		//enroll first user
		    mange_adhoc_course_enrollments.enrollInstructorToCourse(course_with_one_recording , for_enroll, mangage_adhoc_courses_membership_window);
			Thread.sleep(Page.TIMEOUT_TINY); 
		
			
	 	    
			 for (String window : driver.getWindowHandles()) {
			 driver.switchTo().window(window);
			 break;
			 }
			
		     mange_adhoc_course_enrollments.clickOnAdminDashboard();
		     Thread.sleep(Page.TIMEOUT_TINY);
		     admin_dashboard_page.signOut();
		     Thread.sleep(Page.TIMEOUT_TINY);
		     tegrity.loginCourses("SuperUser");
		     Thread.sleep(Page.TIMEOUT_TINY);
             ///copy one recording to course
		     course.selectCourseThatStartingWith("BankValidRecording");
		     Thread.sleep(Page.TIMEOUT_TINY);
		     record.getCheckbox().click();
		     record.clickOnRecordingTaskThenCopy();
		     Thread.sleep(Page.TIMEOUT_TINY);
		     copy.selectTargetCourseFromCourseList(course_with_one_recording);
		     Thread.sleep(Page.TIMEOUT_TINY);
		     copy.clickOnCopyButton();
		     Thread.sleep(Page.TIMEOUT_TINY);
		     confirm_menu.clickOnOkButtonAfterConfirmCopyRecording();
		     ///make sure recording ended being copied
		     record.waitUntilFirstRecordingBeingCopiedFromStatusDissaper();
		     Thread.sleep(Page.TIMEOUT_TINY);
		    
		     for (String window : driver.getWindowHandles()) {
				 driver.switchTo().window(window);
				 break;
				 }
		     record.signOut();
		     Thread.sleep(Page.TIMEOUT_TINY);
		     tegrity.loginCoursesByParameter(instructor1);
		     Thread.sleep(Page.TIMEOUT_TINY);
		     course.selectCourseByName(course_with_one_recording);
		     Thread.sleep(Page.TIMEOUT_TINY);
		     record.changeRecordingOwnership(confirm_menu, erp_window, instructor1,record.getCheckbox());
		     Thread.sleep(Page.TIMEOUT_TINY);
		     for (String window : driver.getWindowHandles()) {
				 driver.switchTo().window(window);
				 break;
				 }
		     course.sign_out.click();
             Thread.sleep(Page.TIMEOUT_TINY);
		  
             ///////////////////////////////////////////////end of preset 

////test17925 begins
		  
             ///1.Login as ADMIN
             tegrity.loginAdmin("Admin");
             Thread.sleep(Page.TIMEOUT_TINY);
             ///2.Click the "Course Builder" link
             admin_dashboard_page.clickOnTargetSubmenuCourses("Manage Ad-hoc Courses / Enrollments (Course Builder)");			
	 		  Thread.sleep(Page.TIMEOUT_TINY);
             ///3.Click the "Membership" link related to the course+unenroll instructor 1
             mange_adhoc_course_enrollments.unEnrollInstructorToCourse(course_with_one_recording, instructor1, mangage_adhoc_courses_membership_window);
             Thread.sleep(Page.TIMEOUT_TINY);

			 for (String window : driver.getWindowHandles()) {
			 driver.switchTo().window(window);
			 break;
			 }
			
		     mange_adhoc_course_enrollments.clickOnAdminDashboard();
		     Thread.sleep(Page.TIMEOUT_TINY);
		     admin_dashboard_page.signOut();
		     Thread.sleep(Page.TIMEOUT_TINY);
             tegrity.loginCoursesByParameter(instructor1);
		     Thread.sleep(Page.TIMEOUT_TINY);
		     ///by clicking it we verify it exists
		     course.clickOnPastCoursesTabButton();
		      Thread.sleep(Page.TIMEOUT_TINY);
		     initializeCourseObject();
		     ///verify course exists in list
		     course.verifyCourseExist(course_with_one_recording);
		     ///select course
		     course.selectCourseThatStartingWith(course_with_one_recording);
		     Thread.sleep(Page.TIMEOUT_TINY);
		     //delete recording
		     record.getCheckbox().click();
		     record.toDeleteMenu();
		     Thread.sleep(Page.TIMEOUT_TINY);
		     delete_menu.clickOnDeleteButton();
		     ///click om breadcrumbs
		     Thread.sleep(Page.TIMEOUT_TINY);
		     for (String window : driver.getWindowHandles()) {
				 driver.switchTo().window(window);
				 break;
				 }
		     record.returnToCourseListPage();
		     Thread.sleep(Page.TIMEOUT_TINY);
		     ///verify no past courses tab
             course.verifyNoPastCoursesTab();
             
             for (String window : driver.getWindowHandles()) {
				 driver.switchTo().window(window);
				 break;
				 }
	          course.signOut();
	          Thread.sleep(Page.TIMEOUT_TINY);
	          //login as instructor 2
	          tegrity.loginCoursesByParameter(instructor2);
	          Thread.sleep(Page.TIMEOUT_TINY);
	          initializeCourseObject();
	          course.clickOnActiveCoursesTabButton();
	          course.verifyCourseExist(course_with_one_recording);

	          System.out.println("Done.");
	          ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
	           
}
	
	// description = "get courses list"
	public void initializeCourseObject() throws InterruptedException {

		course = PageFactory.initElements(driver, CoursesHelperPage.class);
		course.courses = course.getStringFromElement(course.course_list);
	}
}