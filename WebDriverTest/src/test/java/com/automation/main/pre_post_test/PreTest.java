package com.automation.main.pre_post_test;



import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.automation.main.page_helpers.AdminDashboardPage;
import com.automation.main.page_helpers.ConfirmationMenu;
import com.automation.main.page_helpers.CopyMenu;
import com.automation.main.page_helpers.CoursesHelperPage;
import com.automation.main.page_helpers.CreateNewCourseWindow;
import com.automation.main.page_helpers.CreateNewUserWindow;
import com.automation.main.page_helpers.LoginHelperPage;
import com.automation.main.page_helpers.ManageAdHocCoursesMembershipWindow;
import com.automation.main.page_helpers.ManageAdhocCoursesEnrollmentsPage;
import com.automation.main.page_helpers.ManageAdhocUsersPage;
import com.automation.main.page_helpers.RecordingHelperPage;

import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import atu.testng.reports.utils.Utils;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;
import junitx.util.PropertyManager;


@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class PreTest {

	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}

	public LoginHelperPage tegrity;
	WebDriver driver;
	WebDriverWait wait;
	public static WebDriver thread_driver;
	public AdminDashboardPage admin_dashboard_page;
	public ManageAdhocCoursesEnrollmentsPage mange_adhoc_course_enrollments;
	public CreateNewCourseWindow create_new_course_window;
	public ManageAdhocUsersPage mange_adhoc_users_page;
	public CreateNewUserWindow create_new_user_window;
	public ManageAdHocCoursesMembershipWindow mangage_adhoc_courses_membership_window;
	public CoursesHelperPage course;
	public RecordingHelperPage record;
	CopyMenu copy;
	public ConfirmationMenu confirm_menu;
	String SuperUsername="";
	String user1Username="";
	
	
	public void initializeCourseObject() throws InterruptedException {

		course = PageFactory.initElements(driver, CoursesHelperPage.class);
		course.courses = course.getStringFromElement(course.course_list);
	}
	
	@BeforeClass
	public void setup() {

		driver = new FirefoxDriver();
		ATUReports.add("selected browser type", LogAs.PASSED, new CaptureScreen( ScreenshotOf.DESKTOP));

		tegrity = PageFactory.initElements(driver, LoginHelperPage.class);
		
		admin_dashboard_page = PageFactory.initElements(driver, AdminDashboardPage.class);
		
		mange_adhoc_course_enrollments = PageFactory.initElements(driver, ManageAdhocCoursesEnrollmentsPage.class);
		
		create_new_course_window = PageFactory.initElements(driver, CreateNewCourseWindow.class);
		
		mange_adhoc_users_page = PageFactory.initElements(driver, ManageAdhocUsersPage.class);
		
		create_new_user_window = PageFactory.initElements(driver, CreateNewUserWindow.class);
		
		mangage_adhoc_courses_membership_window = PageFactory.initElements(driver, ManageAdHocCoursesMembershipWindow.class);
		
		record = PageFactory.initElements(driver, RecordingHelperPage.class);
		
		copy = PageFactory.initElements(driver, CopyMenu.class);
		
		confirm_menu = PageFactory.initElements(driver, ConfirmationMenu.class);
		
		wait = new WebDriverWait(driver, 30);
		
		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: PreTest at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: PreTest at " + DateToStr,
		 "Starting the test: PreTest at " + DateToStr, LogAs.PASSED, null);
		
	}
	
	@AfterClass
	public void closeBroswer() {
		this.driver.quit();
	}



	@Test(description = "Pre Test")
	public void preTest() throws InterruptedException, UnsupportedEncodingException, FileNotFoundException
	{
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
		
		// 1. Login as INSTRUCTOR.
		tegrity.loginAdmin("Admin");
		
		String login_url = driver.getCurrentUrl();
		String university_name  = login_url.split("/")[2].substring(0,  login_url.split("/")[2].length() - 12);
		
		System.out.println("Current unviersity name: " + university_name);

		// 2. Click on course builder href link
		Thread.sleep(3000);
		admin_dashboard_page.clickOnTargetSubmenuCourses("Manage Ad-hoc Courses / Enrollments (Course Builder)");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("SelfRegConfig")));
			

		// 3. Click on create course href link 
		driver.switchTo().frame(0);
		//mange_adhoc_course_enrollments.clickOnNewCourse();
		admin_dashboard_page.waitForVisibility(mange_adhoc_course_enrollments.new_course_button);
		// 4. Create six dynamic courses
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyhhmmss");
		
		List<String> created_course_list = new ArrayList<String>();
		
		for(int i = 0; i < 9; i++) {
			String course_name = "";
			if (i == 0) {
				course_name = "Ab" + university_name + sdf.format(date);
			} else if (i == 1) {
				course_name = "abc" + university_name + sdf.format(date);
			} else if (i == 2) {
				course_name = "ad" + university_name + sdf.format(date);
			} else if (i == 3) {
				course_name = "Ba" + university_name + sdf.format(date);
			} else if(i == 4){
				course_name = "BAc" + university_name + sdf.format(date);
			} else if (i == 5) {
				created_course_list.add("BankValidRecording");
				continue;
			} else if (i == 6) {
				created_course_list.add("BankInValidRecording");
				continue;
			} else if (i == 7) {
				course_name = "PastCourseA" + university_name + sdf.format(date);
			} else if (i == 8) {
				course_name = "PastCourseB" + university_name + sdf.format(date);
			}
			
			created_course_list.add(course_name);
			
			wait.until(ExpectedConditions.visibilityOf(mange_adhoc_course_enrollments.new_course_button));
			mange_adhoc_course_enrollments.clickOnNewCourse();
			create_new_course_window.waitForVisibility(create_new_course_window.course_id_input);
			
			create_new_course_window.createNewCourse(course_name + "_Name", course_name);
			
			for(int j=0;j<5;j++) {
				try {
					driver.switchTo().alert().accept();
					break;
				} catch (Exception msg) {
					Thread.sleep(1000);
				}
			}
			
			
		}
		
		for(String window: driver.getWindowHandles()) {
			driver.switchTo().window(window);
			break;
		}
		
		mange_adhoc_course_enrollments.clickOnAdminDashboard();
		
		Thread.sleep(2000);
		
	
		// 2. Click on user builder href link
		admin_dashboard_page.clickOnTargetSubmenuUsers("Manage Ad-hoc Users (User Builder)");
				
		// 3. Click on create course href link 
		for(int i=0; i<5; i++ ) {
			try {
				driver.switchTo().frame(0);
				break;
			} catch(Exception msg) {
				Thread.sleep(1000);
			}
		}
		
		
		// 4. Create four dynamic users	
		List<String> created_new_user = new ArrayList<String>(); 
		
		for(int i = 0; i < 5; i++) {
			String user_name = "";
			if (i == 0) {
				user_name = "User1" + sdf.format(date);
				user1Username=user_name;
			} else if (i == 1) {
				user_name = "User2" + sdf.format(date);
			} else if (i == 2) {
				user_name = "User3" + sdf.format(date);
			} else if (i == 3) {
				user_name = "User4" + sdf.format(date);
			} else if(i == 4) {
				user_name = "SuperUser" + sdf.format(date);
				SuperUsername = user_name;
			}
				
			created_new_user.add(user_name);
			
			mange_adhoc_users_page.clickOnNewUser();
			
			create_new_user_window.createNewUser(user_name, user_name, "abc@com.com", "111", "111");

		}
		
		for(String window: driver.getWindowHandles()) {
			driver.switchTo().window(window);
			break;
		}

		// Writing the registered user to user.properties file at src/test/resources that other tests will use it
		File file_to_write = new File("src/test/resources/local.properties");
		//	String charset = "UTF-8";
		String charset = "Cp1252";
		PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file_to_write), charset));
		
		for(int i = 1; i <= created_new_user.size(); i++) {
			if (i == created_new_user.size()) {
				writer.println("SuperUser=" + created_new_user.get(i-1));
			} else {
				writer.println("User" + i + "=" + created_new_user.get(i-1));
			}
		}
		
		writer.println("Password=111");
		writer.println("Admin=mickael");
		writer.println("Browser=firefox");//for manual testing
		writer.println("HelpdeskAdmin=hdadmin");
		writer.println("ExcutiveAdmin=executivead");
		System.out.println(file_to_write.getPath());
		writer.close();
		mange_adhoc_course_enrollments.clickOnAdminDashboard();
		
		Thread.sleep(2000);
		
		// 2. Click on course builder href link
		admin_dashboard_page.clickOnTargetSubmenuCourses("Manage Ad-hoc Courses / Enrollments (Course Builder)");
			
		// 3. Click on create course href link 
		for(int i=0; i<10; i++) {
			try {
				driver.switchTo().frame(0);
				break;
			} catch(Exception msg) {
				Thread.sleep(1000);
			}
		}
		
	 
		for(int i=0; i < 9; i++) {
			// Search target course name
			mange_adhoc_course_enrollments.searchAndFilterCourses(created_course_list.get(i));
			
			Thread.sleep(1000);
			// Click on result first course (the only one) membership button
			mange_adhoc_course_enrollments.clickOnFirstCourseMembershipButton();
			Thread.sleep(10000);		
			
			if(i == 0) {
				// Search target user name in membership window
				mangage_adhoc_courses_membership_window.searchForUser(created_new_user.get(0));	

				// Select first user from user list (the only user it found because of the uniq of the search)
				mangage_adhoc_courses_membership_window.selectFirstUserFromUserList();

				// Add selected user to instructor list
				mangage_adhoc_courses_membership_window.clickOnAddSelectedUserToInstructorList();
				
				mangage_adhoc_courses_membership_window.waitMaxTimeUntillInstructorEnrollToCourse(created_new_user.get(0));
				
				// Search target user name in membership window
				mangage_adhoc_courses_membership_window.searchForUser(created_new_user.get(1));	

				// Select first user from user list (the only user it found because of the uniq of the search)
				mangage_adhoc_courses_membership_window.selectFirstUserFromUserList();

				// Add selected user to instructor list
				mangage_adhoc_courses_membership_window.clickOnAddSelectedUserToInstructorList();
				
				mangage_adhoc_courses_membership_window.waitMaxTimeUntillInstructorEnrollToCourse(created_new_user.get(1));
						
				// Search target user name in membership window
				mangage_adhoc_courses_membership_window.searchForUser(created_new_user.get(2));	

				// Select first user from user list (the only user it found because of the uniq of the search)
				mangage_adhoc_courses_membership_window.selectFirstUserFromUserList();

				// Add selected user to instructor list
				mangage_adhoc_courses_membership_window.clickOnAddSelectedUserToStudentList();
				
				mangage_adhoc_courses_membership_window.waitMaxTimeUntillStudentEnrollToCourse(created_new_user.get(2));
					
				
				// Search target user name in membership window
				mangage_adhoc_courses_membership_window.searchForUser(created_new_user.get(3));	
				
				// Select first user from user list (the only user it found because of the uniq of the search)
				mangage_adhoc_courses_membership_window.selectFirstUserFromUserList();

				// Add selected user to instructor list
				mangage_adhoc_courses_membership_window.clickOnAddSelectedUserToStudentList();
			
				mangage_adhoc_courses_membership_window.waitMaxTimeUntillStudentEnrollToCourse(created_new_user.get(3));
						
				// Search target user name in membership window
				mangage_adhoc_courses_membership_window.searchForUser(created_new_user.get(4));	

				// Select first user from user list (the only user it found because of the uniq of the search)
				mangage_adhoc_courses_membership_window.selectFirstUserFromUserList();

				// Add selected user to instructor list
				mangage_adhoc_courses_membership_window.clickOnAddSelectedUserToInstructorList();

				mangage_adhoc_courses_membership_window.waitMaxTimeUntillInstructorEnrollToCourse(created_new_user.get(4));

				// Search target user name in membership window
				mangage_adhoc_courses_membership_window.searchForUser("executivead");	
				
				// Select first user from user list (the only user it found because of the uniq of the search)
				mangage_adhoc_courses_membership_window.selectFirstUserFromUserList();
				
				// Add selected user to instructor list
				mangage_adhoc_courses_membership_window.clickOnAddSelectedUserToInstructorList();
				
				mangage_adhoc_courses_membership_window.waitMaxTimeUntillInstructorEnrollToCourse("executivead");

			} else if (i == 1) {
				// Search target user name in membership window
				mangage_adhoc_courses_membership_window.searchForUser(created_new_user.get(0));	

				// Select first user from user list (the only user it found because of the uniq of the search)
				mangage_adhoc_courses_membership_window.selectFirstUserFromUserList();

				// Add selected user to instructor list
				mangage_adhoc_courses_membership_window.clickOnAddSelectedUserToInstructorList();
				
				mangage_adhoc_courses_membership_window.waitMaxTimeUntillInstructorEnrollToCourse(created_new_user.get(0));
				

				
				// Search target user name in membership window
				mangage_adhoc_courses_membership_window.searchForUser(created_new_user.get(1));	

				// Select first user from user list (the only user it found because of the uniq of the search)
				mangage_adhoc_courses_membership_window.selectFirstUserFromUserList();

				// Add selected user to instructor list
				mangage_adhoc_courses_membership_window.clickOnAddSelectedUserToStudentList();
				
				mangage_adhoc_courses_membership_window.waitMaxTimeUntillStudentEnrollToCourse(created_new_user.get(1));
	
				// Search target user name in membership window
				mangage_adhoc_courses_membership_window.searchForUser(created_new_user.get(2));	

				// Select first user from user list (the only user it found because of the uniq of the search)
				mangage_adhoc_courses_membership_window.selectFirstUserFromUserList();

				// Add selected user to instructor list
				mangage_adhoc_courses_membership_window.clickOnAddSelectedUserToInstructorList();
				
				mangage_adhoc_courses_membership_window.waitMaxTimeUntillInstructorEnrollToCourse(created_new_user.get(2));

				// Search target user name in membership window
				mangage_adhoc_courses_membership_window.searchForUser(created_new_user.get(3));	

				// Select first user from user list (the only user it found because of the uniq of the search)
				mangage_adhoc_courses_membership_window.selectFirstUserFromUserList();

				// Add selected user to instructor list
				mangage_adhoc_courses_membership_window.clickOnAddSelectedUserToStudentList();

				
				mangage_adhoc_courses_membership_window.waitMaxTimeUntillStudentEnrollToCourse(created_new_user.get(3));
				

				
				// Search target user name in membership window
				mangage_adhoc_courses_membership_window.searchForUser(created_new_user.get(4));	

				// Select first user from user list (the only user it found because of the uniq of the search)
				mangage_adhoc_courses_membership_window.selectFirstUserFromUserList();

				// Add selected user to instructor list
				mangage_adhoc_courses_membership_window.clickOnAddSelectedUserToInstructorList();
				
				mangage_adhoc_courses_membership_window.waitMaxTimeUntillInstructorEnrollToCourse(created_new_user.get(4));
				
				
				// Search target user name in membership window
				mangage_adhoc_courses_membership_window.searchForUser("executivead");	
				
				// Select first user from user list (the only user it found because of the uniq of the search)
				mangage_adhoc_courses_membership_window.selectFirstUserFromUserList();
				
				// Add selected user to instructor list
				mangage_adhoc_courses_membership_window.clickOnAddSelectedUserToStudentList();
				
				mangage_adhoc_courses_membership_window.waitMaxTimeUntillStudentEnrollToCourse("executivead");


			} else if (i == 2) {
				// Search target user name in membership window
				mangage_adhoc_courses_membership_window.searchForUser(created_new_user.get(0));	

				// Select first user from user list (the only user it found because of the uniq of the search)
				mangage_adhoc_courses_membership_window.selectFirstUserFromUserList();

				// Add selected user to instructor list
				mangage_adhoc_courses_membership_window.clickOnAddSelectedUserToInstructorList();
				
				mangage_adhoc_courses_membership_window.waitMaxTimeUntillInstructorEnrollToCourse(created_new_user.get(0));


				
				// Search target user name in membership window
				mangage_adhoc_courses_membership_window.searchForUser(created_new_user.get(3));	

				// Select first user from user list (the only user it found because of the uniq of the search)
				mangage_adhoc_courses_membership_window.selectFirstUserFromUserList();

				// Add selected user to instructor list
				mangage_adhoc_courses_membership_window.clickOnAddSelectedUserToStudentList();
				

				mangage_adhoc_courses_membership_window.waitMaxTimeUntillStudentEnrollToCourse(created_new_user.get(3));

				
				// Search target user name in membership window
				mangage_adhoc_courses_membership_window.searchForUser(created_new_user.get(4));	

				// Select first user from user list (the only user it found because of the uniq of the search)
				mangage_adhoc_courses_membership_window.selectFirstUserFromUserList();

				// Add selected user to instructor list
				mangage_adhoc_courses_membership_window.clickOnAddSelectedUserToInstructorList();
				
				mangage_adhoc_courses_membership_window.waitMaxTimeUntillInstructorEnrollToCourse(created_new_user.get(4));


			} else if (i == 3) {
				// Search target user name in membership window
				mangage_adhoc_courses_membership_window.searchForUser(created_new_user.get(0));	

				// Select first user from user list (the only user it found because of the uniq of the search)
				mangage_adhoc_courses_membership_window.selectFirstUserFromUserList();

				// Add selected user to instructor list
				mangage_adhoc_courses_membership_window.clickOnAddSelectedUserToInstructorList();
				
				mangage_adhoc_courses_membership_window.waitMaxTimeUntillInstructorEnrollToCourse(created_new_user.get(0));
		
				// Search target user name in membership window
				mangage_adhoc_courses_membership_window.searchForUser(created_new_user.get(3));	

				// Select first user from user list (the only user it found because of the uniq of the search)
				mangage_adhoc_courses_membership_window.selectFirstUserFromUserList();

				// Add selected user to instructor list
				mangage_adhoc_courses_membership_window.clickOnAddSelectedUserToStudentList();
				

				mangage_adhoc_courses_membership_window.waitMaxTimeUntillStudentEnrollToCourse(created_new_user.get(3));
							
				// Search target user name in membership window
				mangage_adhoc_courses_membership_window.searchForUser(created_new_user.get(4));	

				// Select first user from user list (the only user it found because of the uniq of the search)
				mangage_adhoc_courses_membership_window.selectFirstUserFromUserList();

				// Add selected user to instructor list
				mangage_adhoc_courses_membership_window.clickOnAddSelectedUserToInstructorList();
				
				mangage_adhoc_courses_membership_window.waitMaxTimeUntillInstructorEnrollToCourse(created_new_user.get(4));
			
			}  else if (i == 4) {
				// Search target user name in membership window
				mangage_adhoc_courses_membership_window.searchForUser(created_new_user.get(0));	

				// Select first user from user list (the only user it found because of the uniq of the search)
				mangage_adhoc_courses_membership_window.selectFirstUserFromUserList();

				// Add selected user to instructor list
				mangage_adhoc_courses_membership_window.clickOnAddSelectedUserToInstructorList();
				
				mangage_adhoc_courses_membership_window.waitMaxTimeUntillInstructorEnrollToCourse(created_new_user.get(0));
		
				// Search target user name in membership window
				mangage_adhoc_courses_membership_window.searchForUser(created_new_user.get(3));	

				// Select first user from user list (the only user it found because of the uniq of the search)
				mangage_adhoc_courses_membership_window.selectFirstUserFromUserList();

				// Add selected user to instructor list
				mangage_adhoc_courses_membership_window.clickOnAddSelectedUserToStudentList();
							
				mangage_adhoc_courses_membership_window.waitMaxTimeUntillStudentEnrollToCourse(created_new_user.get(3));
				
				// Search target user name in membership window
				mangage_adhoc_courses_membership_window.searchForUser(created_new_user.get(4));	

				// Select first user from user list (the only user it found because of the uniq of the search)
				mangage_adhoc_courses_membership_window.selectFirstUserFromUserList();

				// Add selected user to instructor list
				mangage_adhoc_courses_membership_window.clickOnAddSelectedUserToInstructorList();
				
				mangage_adhoc_courses_membership_window.waitMaxTimeUntillInstructorEnrollToCourse(created_new_user.get(4));


			}  else if (i == 5) {
				// Search target user name in membership window
				mangage_adhoc_courses_membership_window.searchForUser(created_new_user.get(4));	

				// Select first user from user list (the only user it found because of the uniq of the search)
				mangage_adhoc_courses_membership_window.selectFirstUserFromUserList();

				// Add selected user to instructor list
				mangage_adhoc_courses_membership_window.clickOnAddSelectedUserToInstructorList();
				
				mangage_adhoc_courses_membership_window.waitMaxTimeUntillInstructorEnrollToCourse(created_new_user.get(4));

			} else if (i == 6) {
				// Search target user name in membership window
				mangage_adhoc_courses_membership_window.searchForUser(created_new_user.get(0));	

				// Select first user from user list (the only user it found because of the uniq of the search)
				mangage_adhoc_courses_membership_window.selectFirstUserFromUserList();

				// Add selected user to instructor list
				mangage_adhoc_courses_membership_window.clickOnAddSelectedUserToInstructorList();
				
				mangage_adhoc_courses_membership_window.waitMaxTimeUntillInstructorEnrollToCourse(created_new_user.get(0));


				
				// Search target user name in membership window
				mangage_adhoc_courses_membership_window.searchForUser(created_new_user.get(1));	

				// Select first user from user list (the only user it found because of the uniq of the search)
				mangage_adhoc_courses_membership_window.selectFirstUserFromUserList();

				// Add selected user to instructor list
				mangage_adhoc_courses_membership_window.clickOnAddSelectedUserToInstructorList();
				
				mangage_adhoc_courses_membership_window.waitMaxTimeUntillInstructorEnrollToCourse(created_new_user.get(1));

				
				// Search target user name in membership window
				mangage_adhoc_courses_membership_window.searchForUser(created_new_user.get(2));	

				// Select first user from user list (the only user it found because of the uniq of the search)
				mangage_adhoc_courses_membership_window.selectFirstUserFromUserList();

				// Add selected user to instructor list
				mangage_adhoc_courses_membership_window.clickOnAddSelectedUserToStudentList();
				

				mangage_adhoc_courses_membership_window.waitMaxTimeUntillStudentEnrollToCourse(created_new_user.get(2));

				
				// Search target user name in membership window
				mangage_adhoc_courses_membership_window.searchForUser(created_new_user.get(3));	

				// Select first user from user list (the only user it found because of the uniq of the search)
				mangage_adhoc_courses_membership_window.selectFirstUserFromUserList();

			    // Add selected user to instructor list
				mangage_adhoc_courses_membership_window.clickOnAddSelectedUserToStudentList();
				

				mangage_adhoc_courses_membership_window.waitMaxTimeUntillStudentEnrollToCourse(created_new_user.get(3));

				
				// Search target user name in membership window
				mangage_adhoc_courses_membership_window.searchForUser(created_new_user.get(4));	

				// Select first user from user list (the only user it found because of the uniq of the search)
				mangage_adhoc_courses_membership_window.selectFirstUserFromUserList();
	
				// Add selected user to instructor list
				mangage_adhoc_courses_membership_window.clickOnAddSelectedUserToInstructorList();
				
				mangage_adhoc_courses_membership_window.waitMaxTimeUntillInstructorEnrollToCourse(created_new_user.get(4));


			} else if (i == 7) {
				// Search target user name in membership window
				mangage_adhoc_courses_membership_window.searchForUser(created_new_user.get(0));	

				// Select first user from user list (the only user it found because of the uniq of the search)
				mangage_adhoc_courses_membership_window.selectFirstUserFromUserList();

				// Add selected user to instructor list
				mangage_adhoc_courses_membership_window.clickOnAddSelectedUserToInstructorList();
				
				mangage_adhoc_courses_membership_window.waitMaxTimeUntillInstructorEnrollToCourse(created_new_user.get(0));

				mangage_adhoc_courses_membership_window.searchForUser(created_new_user.get(4));	

				// Select first user from user list (the only user it found because of the uniq of the search)
				mangage_adhoc_courses_membership_window.selectFirstUserFromUserList();

				// Add selected user to instructor list
				mangage_adhoc_courses_membership_window.clickOnAddSelectedUserToInstructorList();
				
				mangage_adhoc_courses_membership_window.waitMaxTimeUntillInstructorEnrollToCourse(created_new_user.get(4));

				// Search target user name in membership window
				mangage_adhoc_courses_membership_window.searchForUser(created_new_user.get(3));	

				// Select first user from user list (the only user it found because of the uniq of the search)
				mangage_adhoc_courses_membership_window.selectFirstUserFromUserList();

			    // Add selected user to instructor list
				mangage_adhoc_courses_membership_window.clickOnAddSelectedUserToStudentList();
				

				mangage_adhoc_courses_membership_window.waitMaxTimeUntillStudentEnrollToCourse(created_new_user.get(3));
				
				
				// Search target user name in membership window
				mangage_adhoc_courses_membership_window.searchForUser(created_new_user.get(4));	

				// Select first user from user list (the only user it found because of the uniq of the search)
				mangage_adhoc_courses_membership_window.selectFirstUserFromUserList();

				// Add selected user to instructor list
				mangage_adhoc_courses_membership_window.clickOnAddSelectedUserToInstructorList();

				mangage_adhoc_courses_membership_window.waitMaxTimeUntillInstructorEnrollToCourse(created_new_user.get(4));

				
			} else if (i == 8) {
				// Search target user name in membership window
				mangage_adhoc_courses_membership_window.searchForUser(created_new_user.get(0));	

				// Select first user from user list (the only user it found because of the uniq of the search)
				mangage_adhoc_courses_membership_window.selectFirstUserFromUserList();

				// Add selected user to instructor list
				mangage_adhoc_courses_membership_window.clickOnAddSelectedUserToInstructorList();
				
				mangage_adhoc_courses_membership_window.waitMaxTimeUntillInstructorEnrollToCourse(created_new_user.get(0));

				
				mangage_adhoc_courses_membership_window.searchForUser(created_new_user.get(4));	

				// Select first user from user list (the only user it found because of the uniq of the search)
				mangage_adhoc_courses_membership_window.selectFirstUserFromUserList();

				// Add selected user to instructor list
				mangage_adhoc_courses_membership_window.clickOnAddSelectedUserToInstructorList();
				
				mangage_adhoc_courses_membership_window.waitMaxTimeUntillInstructorEnrollToCourse(created_new_user.get(4));
				
				
				// Search target user name in membership window
				mangage_adhoc_courses_membership_window.searchForUser(created_new_user.get(3));	

				// Select first user from user list (the only user it found because of the uniq of the search)
				mangage_adhoc_courses_membership_window.selectFirstUserFromUserList();

			    // Add selected user to instructor list
				mangage_adhoc_courses_membership_window.clickOnAddSelectedUserToStudentList();
				

				mangage_adhoc_courses_membership_window.waitMaxTimeUntillStudentEnrollToCourse(created_new_user.get(3));
				
				// Search target user name in membership window
				mangage_adhoc_courses_membership_window.searchForUser(created_new_user.get(4));	

				// Select first user from user list (the only user it found because of the uniq of the search)
				mangage_adhoc_courses_membership_window.selectFirstUserFromUserList();

				// Add selected user to instructor list
				mangage_adhoc_courses_membership_window.clickOnAddSelectedUserToInstructorList();

				mangage_adhoc_courses_membership_window.waitMaxTimeUntillInstructorEnrollToCourse(created_new_user.get(4));
				
			}
			mangage_adhoc_courses_membership_window.waitForVisibility(mangage_adhoc_courses_membership_window.ok_button);
			
			// Confirm user membership list
			mangage_adhoc_courses_membership_window.clickOnOkButton();
		
			mangage_adhoc_courses_membership_window.wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("ctl00_ContentPlaceHolder1_ucAddMemberships_ucDialog_ButtonAddInstructor")));
			
			
		}
		
		// Signout
		for(String window: driver.getWindowHandles()) {
			driver.switchTo().window(window);
			break;
		}
		mange_adhoc_course_enrollments.waitForVisibility(driver.findElement(By.id("SignOutLink")));
		mange_adhoc_course_enrollments.signOut();
		tegrity.loginCoursesByParameter(SuperUsername);
		initializeCourseObject();
	    course.copyRecordingFromCourseStartWithToCourseStartWithOfType("BankValid", "PastCourseA", 3, record, copy, confirm_menu);
	    course.copyRecordingFromCourseStartWithToCourseStartWithOfType("BankValid", "PastCourseB", 3, record, copy, confirm_menu);
		course.signOut();
		System.out.println("6"); 
		tegrity.loginCoursesByParameter(user1Username);		
		initializeCourseObject();	
		Thread.sleep(2000);
		course.selectCourseThatStartingWith("PastCourseA");	
		Thread.sleep(2000);
		wait.until(ExpectedConditions.visibilityOf(record.course_title));
		String past_courseA = record.course_title.getText().toString();		
		record.returnToCourseListPage();
		Thread.sleep(3000);
		course.selectCourseThatStartingWith("PastCourseB");
		Thread.sleep(2000);
		wait.until(ExpectedConditions.visibilityOf(record.course_title));
		String past_courseB = record.course_title.getText().toString();		
		record.returnToCourseListPage();
		record.signOut();
		wait.until(ExpectedConditions.visibilityOf(tegrity.usernamefield));

		tegrity.loginAdmin("Admin");
		Thread.sleep(2000);
		/// 2.Click the "Course Builder" link
		admin_dashboard_page.clickOnTargetSubmenuCourses("Manage Ad-hoc Courses / Enrollments (Course Builder)");
		Thread.sleep(10000);
		/// 3.Click the "Membership" link related to the course+unenroll
		/// instructor 1
		System.out.println("before 3");
		mange_adhoc_course_enrollments.unEnrollInstructorToCourse(past_courseA, user1Username,
				mangage_adhoc_courses_membership_window);
		Thread.sleep(4000);
	
		for (String window : driver.getWindowHandles()) {
			driver.switchTo().window(window);
			break;
		}

		System.out.println("before 3");
		mange_adhoc_course_enrollments.unEnrollInstructorToCourse(past_courseB, user1Username,
				mangage_adhoc_courses_membership_window);
		Thread.sleep(4000);		


		for (String window : driver.getWindowHandles()) {
			driver.switchTo().window(window);
			break;
		}

		mange_adhoc_course_enrollments.clickOnAdminDashboard();
		Thread.sleep(2000);
		admin_dashboard_page.signOut();
		
		tegrity.loginCoursesByParameter(SuperUsername);
		initializeCourseObject();
		course.verifyRecordingsStatusIsClear("BankValid", 3, record);
		
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
		
		
		
	//	tegrity.loginCourses("User1");
		
		
		
		
		// Make reading from user.properties, and then login with user1, ... , user4 + superuser 
		// Or make function that login with user name and not taking the username from the properties and then login
		// By looping created user
		
		
//		// Login with each user, click on eula if needed, and logout
//		for (String user_name: created_new_user)
//		{
//			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ButtonLogin")));
//			
//			tegrity.loginCourses(user_name);
//			
//			wait.until(ExpectedConditions.elementToBeClickable(By.id("SignOutLink")));
//			
//			driver.findElement(By.id("SignOutLink")).click();
//		}

		
	}

}