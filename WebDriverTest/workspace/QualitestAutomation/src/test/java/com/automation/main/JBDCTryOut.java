package com.automation.main;

import org.testng.annotations.Test;


import com.sun.jna.win32.W32APITypeMapper;
import com.thoughtworks.selenium.webdriven.commands.IsAlertPresent;

import org.testng.annotations.BeforeClass;

import java.sql.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;import com.automation.main.page_helpers.Page;import com.automation.main.page_helpers.Page;
import org.openqa.selenium.chrome.ChromeDriver;
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

public class JBDCTryOut {

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
	public PlayerPage player_page;
	public EditRecordinPropertiesWindow edit_window;
	public ConfirmationMenu confirmationM_window;
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
		/*driver=new ChromeDriver();
		driver.manage().window().maximize();
		
		driver=new FirefoxDriver();
		ATUReports.add("selected browser type", LogAs.PASSED, new CaptureScreen( ScreenshotOf.DESKTOP));

		driver.manage().window().maximize();
		ATUReports.setWebDriver(driver);
		ATUReports.add("set driver", true);
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
		
		player_page = PageFactory.initElements(driver, PlayerPage.class);
		
		edit_window=PageFactory.initElements(driver, EditRecordinPropertiesWindow.class);
		
		confirmationM_window=PageFactory.initElements(driver, ConfirmationMenu.class);
		
		
		
		wait = new WebDriverWait(driver, 30);
		move_window = PageFactory.initElements(driver, MoveWindow.class);
		erp_window=PageFactory.initElements(driver, EditRecordinPropertiesWindow.class);
	*/
	}
	
	

	
	

	
	@Test(description = "Login course page")
	public void loginCourses() throws InterruptedException
	{
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");	
			Connection con=DriverManager.getConnection("jdbc:sqlserver://tlc-ssrs-qa.cga3dpgjvy5l.us-east-1.rds.amazonaws.com,1433/TegReport"
			,"tlc_qa","P791!q788=*B");
			Statement state=con.createStatement();
			String query="SELECT userid FROM svcusers where user_id=\'9be8212e-99a3-4a38-bbd3-12622cdb85b5\'";
			ResultSet rset=state.executeQuery(query);
			String r="";
			while(rset.next()){
				for(int i=0; i<3; i++){
					r+=rset.getString(i+1);
				}
				
				
			}
			
			System.out.println(r+"res");
			
			
			
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

		

// 1. login as admin
/*
		tegrity.loginAdmin("mickael");
			
// 2. Click on user builder href link
	//	admin_dashboard_page.clickOnTargetSubmenuUsers("Manage Ad-hoc Users (User Builder)");
		
// 3.Create 600 Pairs of instructors and students
//  Click on create course href link 
		Thread.sleep(Page.TIMEOUT_TINY);		
		driver.switchTo().frame(0);		
		Thread.sleep(Page.TIMEOUT_TINY);
				
// 4. Create 2 dynamic users
		/*
	for (int z=1; z<600;z++){
		for(int i = 0; i < 2; i++) {
			String user_name = "";
			if (i == 0) {
				user_name = "test2ReportIns" + z;
			} else if (i == 1) {
				user_name = "test2ReportStu" + z;
			} 
			
			mange_adhoc_users_page.clickOnNewUser();
			Thread.sleep(Page.TIMEOUT_TINY);
			
			create_new_user_window.createNewUser(user_name, user_name, "abc@com.com", "111", "111");
			Thread.sleep(Page.TIMEOUT_TINY);
			try{
			driver.switchTo().alert().accept();
			}catch(Exception e){
				
			}
			Thread.sleep(Page.TIMEOUT_TINY);
			
		}
	}
	
	// 5. go to adminDashboard 
	
	mange_adhoc_users_page.toAdminDashBoard();
	
	
	// 6. go to Manage course builder

	Thread.sleep(Page.TIMEOUT_TINY);
	
	
	
	// 2. Click on course builder href link
	admin_dashboard_page.clickOnTargetSubmenuCourses("Manage Ad-hoc Courses / Enrollments (Course Builder)");
	Thread.sleep(Page.TIMEOUT_TINY);
	driver.switchTo().frame(0);	

	
	
	
	// 7. Create 600 courses and enroll 1 instructor and 1 corresponding user to course 
	for(int z=39;z<600;z++){
		
		mange_adhoc_course_enrollments.clickOnNewCourse();
		create_new_course_window.createNewCourse("nameReportCourse"+z, "idReportCourse"+z);
		mange_adhoc_course_enrollments.setFilterSearchBox("idReportCourse"+z);
		Thread.sleep(Page.TIMEOUT_TINY);
		mange_adhoc_course_enrollments.clickOnFilterButton();
		Thread.sleep(Page.TIMEOUT_TINY);
		mange_adhoc_course_enrollments.clickOnFirstCourseMembershipButton();
		mangage_adhoc_courses_membership_window.searchForUser("test2ReportIns"+z);
		Thread.sleep(Page.TIMEOUT_TINY);
		mangage_adhoc_courses_membership_window.selectFirstUserFromUserList();
		mangage_adhoc_courses_membership_window.clickOnAddSelectedUserToInstructorList();
		mangage_adhoc_courses_membership_window.waitMaxTimeUntillInstructorEnrollToCourse("test2ReportIns"+z);
		mangage_adhoc_courses_membership_window.searchForUser("test2ReportStu"+z);
		mangage_adhoc_courses_membership_window.selectFirstUserFromUserList();
		mangage_adhoc_courses_membership_window.clickOnAddSelectedUserToStudentList();
		mangage_adhoc_courses_membership_window.waitMaxTimeUntillStudentEnrollToCourse("test2ReportStu"+z);
	
		// enroll general instructor to course		
		mangage_adhoc_courses_membership_window.searchForUser("mickaelins");
		Thread.sleep(Page.TIMEOUT_TINY);
		mangage_adhoc_courses_membership_window.selectFirstUserFromUserList();
		mangage_adhoc_courses_membership_window.clickOnAddSelectedUserToInstructorList();
		mangage_adhoc_courses_membership_window.waitMaxTimeUntillInstructorEnrollToCourse("test2ReportIns"+z);
		mangage_adhoc_courses_membership_window.clickOnOkButton();
		// enroll instructor to bank course
		mange_adhoc_course_enrollments.setFilterSearchBox("BankValidRecordingawsserverautomation1");
		Thread.sleep(Page.TIMEOUT_TINY);
		mange_adhoc_course_enrollments.clickOnFilterButton();
		Thread.sleep(Page.TIMEOUT_TINY);
		mange_adhoc_course_enrollments.clickOnFirstCourseMembershipButton();
		mangage_adhoc_courses_membership_window.searchForUser("test2ReportIns"+z);
		Thread.sleep(Page.TIMEOUT_TINY);
		mangage_adhoc_courses_membership_window.selectFirstUserFromUserList();
		mangage_adhoc_courses_membership_window.clickOnAddSelectedUserToInstructorList();
		mangage_adhoc_courses_membership_window.waitMaxTimeUntillInstructorEnrollToCourse("test2ReportIns"+z);
		mangage_adhoc_courses_membership_window.clickOnOkButton();	
		Thread.sleep(Page.TIMEOUT_TINY);
		
	}
	
	driver.quit();
	
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
    
    
	for(int i=1;i<301;i++){	
		
		
	
	
	
	
	System.out.println("Before Login");
	/*tegrity.loginAdmin("test2ReportIns"+i);
	course.waitForFirstCourse();
	course.clickOnTargetCourseName("BankValidRecordingawsserverautomation1");
	record.clickCheckBoxByName("Anatomy Head & neck 3");
	record.clickOnRecordingTaskThenCopy();
	/*copy.isTextDisplayedInSearchBox("nameReportCourse"+i);
	copy.clickOnSearchButton();
	copy.selectTargetCourseFromCourseList("nameReportCourse"+i);
	copy.clickOnCopyButton();
	confirmationM_window.clickOnOkButtonAfterConfirmCopyRecording();
	record.checkThatRecordingStatusTargetIndexIsEmpty(1,60);
	record.returnToCourseListPage();
	course.clickOnTargetCourseName("nameReportCourse"+i);
	record.clickCheckBoxByName("Anatomy Head & neck 3");
	record.toEditRecordingPropertiesMenu();
	edit_window.changeOwner("test2ReportIns"+i);
	edit_window.clickOnSaveButton();	
	confirmationM_window.clickOnOkButtonAfterConfirmEditRecordingProperties();
	record.clickOnTargetRecordingAndOpenItsPlayback("Anatomy Head & neck 3");
	player_page.verifyTimeBufferStatusForXSec(15);// check source display
	player_page.returnToCoursesPage(course);
	course.signOut();
	tegrity.loginAdmin("test2ReportStu"+i);
	course.waitForFirstCourse();
	course.signOut();
	tegrity.loginAdmin("test2ReportStu"+i+"A1");
	course.waitForFirstCourse();
	course.signOut();
	tegrity.loginAdmin("test2ReportStu"+i+"A2");
	course.waitForFirstCourse();
	course.signOut();
	tegrity.loginAdmin("test2ReportStu"+i+"A3");
	course.waitForFirstCourse();
	course.signOut();
	tegrity.loginAdmin("test2ReportStu"+i+"A4");
	course.waitForFirstCourse();
	course.signOut();
	tegrity.loginAdmin("test2ReportStu"+i+"A5");
	course.waitForFirstCourse();
	course.signOut();
	tegrity.loginAdmin("test2ReportStu"+i+"A6");
	course.waitForFirstCourse();
	course.signOut();
	tegrity.loginAdmin("test2ReportStu"+i+"A7");
	course.waitForFirstCourse();
	
	
	
	/*
	course.clickOnTargetCourseName("nameReportCourse"+i);
	record.clickOnTargetRecordingAndOpenItsPlayback("Anatomy Head & neck 3");
	player_page.verifyTimeBufferStatusForXSec(15);// check source display
	player_page.returnToCoursesPage(course);
	course.signOut();
	

	
	
	
	
	
	
	
	
	}
	
		
	/*	

  //1. Login with SuperUser.
		tegrity.loginCourses("User1");// log in courses page
	   initializeCourseObject();	
		
	

	//ownership change	
		int i=1;
     String past_course_a=course.selectCourseThatStartingWith("PastCourseA");
	    Thread.sleep(Page.TIMEOUT_TINY);

	    ///check for free status checkbox for edit properties
	    while(record.recordingBeingEditedStatus(driver.findElement(By.id("RecordingStatus"+Integer.toString(i))))==true)
		{
			i++;
		}
	   driver.findElement(By.id("Checkbox"+Integer.toString(i))).click();
	

		record.toEditRecordingPropertiesMenu();
		Thread.sleep(Page.TIMEOUT_TINY);
		erp_window.changeOwner("User1");
		
		erp_window.save_button.click();
		

	  Thread.sleep(Page.TIMEOUT_TINY);
	
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
 		Thread.sleep(Page.TIMEOUT_TINY);
 		erp_window.changeOwner("User1");
 		
 		erp_window.save_button.click();
 		

 	  Thread.sleep(Page.TIMEOUT_TINY);
 	
 		System.out.println("before ok");
        confirm_menu.clickOnOkButtonAfterConfirmEditRecordingProperties();
 		System.out.println("after ok ok");

        record.clickOnSignOut();
 		
    	// 1. Login as ADMIN.
 		tegrity.loginAdmin("Admin");
 		Thread.sleep(Page.TIMEOUT_TINY);
 		
 		
 		
 		// 2. Click on course builder href link
 		admin_dashboard_page.clickOnTargetSubmenuCourses("Manage Ad-hoc Courses / Enrollments (Course Builder)");
 		
 		Thread.sleep(Page.TIMEOUT_TINY);
 		
 		// 3. Click on create course href link 
 		driver.switchTo().frame(0);
 		//mange_adhoc_course_enrollments.clickOnNewCourse();
 		Thread.sleep(Page.TIMEOUT_TINY);
 		
 		// Search target course name
 		mange_adhoc_course_enrollments.searchAndFilterCourses(past_course_a);
 		
 		Thread.sleep(Page.TIMEOUT_TINY);
 	
 		
 		// Click on result first course (the only one) membership button
 		mange_adhoc_course_enrollments.clickOnFirstCourseMembershipButton();
 		
 		
 		Thread.sleep(Page.TIMEOUT_TINY);
 		mangage_adhoc_courses_membership_window.selectIrUserFromUserList(mangage_adhoc_courses_membership_window.instructor_elements_list,"User1");
 	    System.out.println("removed instructor 1");
 		Thread.sleep(Page.TIMEOUT_TINY);
 		// Add selected user to instructor list
 		mangage_adhoc_courses_membership_window.clickOnRemoveSelectedUserToInstructorList();
 		Thread.sleep(Page.TIMEOUT_TINY);   		
 		mangage_adhoc_courses_membership_window.ok_button.click();
 		Thread.sleep(Page.TIMEOUT_TINY);
 	    driver.switchTo().alert().accept();
 	    Thread.sleep(Page.TIMEOUT_TINY);
 	    
 		// Quit browser

 	  Thread.sleep(Page.TIMEOUT_TINY);
 	    driver.quit();
   	
	    */
	
		
		
	
		
		
		
	
		
	}
	// description = "get courses list"
		public void initializeCourseObject() throws InterruptedException {

			course = PageFactory.initElements(driver, CoursesHelperPage.class);
			course.courses = course.getStringFromElement(course.course_list);
		}
}
