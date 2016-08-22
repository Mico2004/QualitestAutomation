package com.automation.main;

import org.testng.annotations.Test;

import com.sun.jna.win32.W32APITypeMapper;

import org.testng.annotations.BeforeClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import atu.testng.reports.utils.Utils;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;
import junitx.util.PropertyManager;

public class PreTestPastCourses {

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

	public AdminDashboardPage admin_dashboard_page;
	public ManageAdhocCoursesEnrollmentsPage mange_adhoc_course_enrollments;
	public CreateNewCourseWindow create_new_course_window;
	public ManageAdhocUsersPage mange_adhoc_users_page;
	public CreateNewUserWindow create_new_user_window;
	public ManageAdHocCoursesMembershipWindow mangage_adhoc_courses_membership_window;
	
	
	
	@BeforeClass
	public void setup() {

		driver=new FirefoxDriver();
	///	ATUReports.add("selected browser type", LogAs.PASSED, new CaptureScreen( ScreenshotOf.DESKTOP));

		driver.manage().window().maximize();
		//ATUReports.setWebDriver(driver);
		//ATUReports.add("set driver", true);
		tegrity = PageFactory.initElements(driver, LoginHelperPage.class);

		record = PageFactory.initElements(driver, RecordingHelperPage.class);
		copy = PageFactory.initElements(driver, CopyMenu.class);
		delete_menu = PageFactory.initElements(driver, DeleteMenu.class);
		course=PageFactory.initElements(driver, CoursesHelperPage.class);
		confirm_menu = PageFactory.initElements(driver, ConfirmationMenu.class);
	    admin_dashboard_page = PageFactory.initElements(driver, AdminDashboardPage.class);
		
		mange_adhoc_course_enrollments = PageFactory.initElements(driver, ManageAdhocCoursesEnrollmentsPage.class);
		
		create_new_course_window = PageFactory.initElements(driver, CreateNewCourseWindow.class);
		
		mange_adhoc_users_page = PageFactory.initElements(driver, ManageAdhocUsersPage.class);
		
		create_new_user_window = PageFactory.initElements(driver, CreateNewUserWindow.class);
		
		mangage_adhoc_courses_membership_window = PageFactory.initElements(driver, ManageAdHocCoursesMembershipWindow.class);
		
		wait = new WebDriverWait(driver, 30);
		move_window = PageFactory.initElements(driver, MoveWindow.class);
		erp_window=PageFactory.initElements(driver, EditRecordinPropertiesWindow.class);
	
	}
	
	

	
	

	
	@Test(description = "Login course page")
	public void loginCourses() throws InterruptedException
	{
		
tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
		



//1. Login with SuperUser.
		tegrity.loginCourses("User1");// log in courses page
	   initializeCourseObject();	
		
	

	//ownership change	
		int i=1;
     String past_course_a=course.selectCourseThatStartingWith("PastCourseA");
	    Thread.sleep(3000);

	    ///check for free status checkbox for edit properties
	    while(record.recordingBeingEditedStatus(driver.findElement(By.id("RecordingStatus"+Integer.toString(i))))==true)
		{
			i++;
		}
	   driver.findElement(By.id("Checkbox"+Integer.toString(i))).click();
	

		record.toEditRecordingPropertiesMenu();
		Thread.sleep(2000);
		erp_window.changeOwner("User1");
		
		erp_window.save_button.click();
		

	  Thread.sleep(11000);
	
		System.out.println("before ok");
    confirm_menu.clickOnOkButtonAfterConfirmEditRecordingProperties();
		System.out.println("after ok ok");

    record.clickOnSignOut();
 // 1. Login with SuperUser.
 		tegrity.loginCourses("User1");// log in courses page
 	   initializeCourseObject();	
 		
 	

 	  past_course_a=course.selectCourseThatStartingWith("PastCourseA");

 	    ///check for free status checkbox for edit properties
 	    while(record.recordingBeingEditedStatus(driver.findElement(By.id("RecordingStatus"+Integer.toString(i))))==true)
 		{
 			i++;
 		}
 	   driver.findElement(By.id("Checkbox"+Integer.toString(i))).click();
 	

 		record.toEditRecordingPropertiesMenu();
 		Thread.sleep(2000);
 		erp_window.changeOwner("User1");
 		
 		erp_window.save_button.click();
 		

 	  Thread.sleep(11000);
 	
 		System.out.println("before ok");
        confirm_menu.clickOnOkButtonAfterConfirmEditRecordingProperties();
 		System.out.println("after ok ok");

        record.clickOnSignOut();
 		
    	// 1. Login as ADMIN.
 		tegrity.loginAdmin("Admin");
 		Thread.sleep(2000);
 		
 		
 		
 		// 2. Click on course builder href link
 		admin_dashboard_page.clickOnTargetSubmenuCourses("Manage Ad-hoc Courses / Enrollments (Course Builder)");
 		
 		Thread.sleep(10000);
 		
 		// 3. Click on create course href link 
 		driver.switchTo().frame(0);
 		//mange_adhoc_course_enrollments.clickOnNewCourse();
 		Thread.sleep(2000);
 		
 		// Search target course name
 		mange_adhoc_course_enrollments.searchAndFilterCourses(past_course_a);
 		
 		Thread.sleep(7000);
 	
 		
 		// Click on result first course (the only one) membership button
 		mange_adhoc_course_enrollments.clickOnFirstCourseMembershipButton();
 		
 		
 		Thread.sleep(2000);
 		mangage_adhoc_courses_membership_window.selectIrUserFromUserList(mangage_adhoc_courses_membership_window.instructor_elements_list,"User1");
 	    System.out.println("removed instructor 1");
 		Thread.sleep(1000);
 		// Add selected user to instructor list
 		mangage_adhoc_courses_membership_window.clickOnRemoveSelectedUserToInstructorList();
 		Thread.sleep(3000);   		
 		mangage_adhoc_courses_membership_window.ok_button.click();
 		Thread.sleep(1000);
 	    driver.switchTo().alert().accept();
 	    Thread.sleep(2000);
 	    
 		// Quit browser

 	  Thread.sleep(2000);
 	    driver.quit();
   	
	    
	
		
		
		
		
		
		
	
		
	}
	// description = "get courses list"
		public void initializeCourseObject() throws InterruptedException {

			course = PageFactory.initElements(driver, CoursesHelperPage.class);
			course.courses = course.getStringFromElement(course.course_list);
		}
}
