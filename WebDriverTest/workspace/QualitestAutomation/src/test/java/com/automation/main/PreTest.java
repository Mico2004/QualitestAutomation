package com.automation.main;


import java.awt.AWTException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.apache.http.impl.client.EntityEnclosingRequestWrapper;
import org.junit.AfterClass;
import org.omg.PortableInterceptor.AdapterManagerIdHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.os.WindowsRegistryException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.google.gson.annotations.Until;
import com.sun.jna.win32.W32APITypeMapper;

import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import atu.testng.reports.utils.Utils;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;
import junitx.util.PropertyManager;
import net.sourceforge.htmlunit.corejs.javascript.ast.NewExpression;

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

	@BeforeClass
	public void setup() {

		driver = DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());
		ATUReports.add("selected browser type", LogAs.PASSED, new CaptureScreen( ScreenshotOf.DESKTOP));

//		driver.manage().window().maximize();
		//ATUReports.setWebDriver(driver);
		//ATUReports.add("set driver", true);
		tegrity = PageFactory.initElements(driver, LoginHelperPage.class);
		
		admin_dashboard_page = PageFactory.initElements(driver, AdminDashboardPage.class);
		
		mange_adhoc_course_enrollments = PageFactory.initElements(driver, ManageAdhocCoursesEnrollmentsPage.class);
		
		create_new_course_window = PageFactory.initElements(driver, CreateNewCourseWindow.class);
		
		mange_adhoc_users_page = PageFactory.initElements(driver, ManageAdhocUsersPage.class);
		
		create_new_user_window = PageFactory.initElements(driver, CreateNewUserWindow.class);
		
		mangage_adhoc_courses_membership_window = PageFactory.initElements(driver, ManageAdHocCoursesMembershipWindow.class);
		
		wait = new WebDriverWait(driver, 30);
	
	}
	
	@AfterClass
	public void closeBroswer() {
		this.driver.quit();
	}

	private void setAuthorInfoForReports() {
		ATUReports.setAuthorInfo("McGraw-Hill Automation ", Utils.getCurrentTime(), "1.0");
	}

	private void setIndexPageDescription() {
		ATUReports.indexPageDescription = "McGraw-Hill Verify <br/> <b> UI existence</b>";
		
	}

	@Test
	public void testME() {
		setAuthorInfoForReports();
		setIndexPageDescription();
	}
	// @Parameters({"web","title"}) in the future
	@Test
	public void loadPage() throws InterruptedException {
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);

	}

	
	@Test(dependsOnMethods = "loadPage", description = "Login course page")
	public void loginCourses() throws InterruptedException//
, UnsupportedEncodingException, FileNotFoundException
	{
		// 1. Login as INSTRUCTOR.
		tegrity.loginAdmin("Admin");
		
		String login_url = driver.getCurrentUrl();
		String university_name  = login_url.split("/")[2].substring(0,  login_url.split("/")[2].length() - 12);
		
		System.out.println("Current unviersity name: " + university_name);

		// 2. Click on course builder href link
		Thread.sleep(2000);
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
				created_course_list.add("BankValidRecording" + university_name);
				continue;
			} else if (i == 6) {
				created_course_list.add("BankInValidRecording" + university_name);
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
			} else if (i == 1) {
				user_name = "User2" + sdf.format(date);
			} else if (i == 2) {
				user_name = "User3" + sdf.format(date);
			} else if (i == 3) {
				user_name = "User4" + sdf.format(date);
			} else {
				user_name = "SuperUser" + sdf.format(date);
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
		File file_to_write = new File("\\WebDriverTest\\src\\test\\resources\\local.properties");
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
			Thread.sleep(1000);		
			
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
		driver.findElement(By.id("SignOutLink")).click();
		
		
	
		driver.quit();
		
		
		
		
		
		
		
		
		
		
		
		
		
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
