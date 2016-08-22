package com.automation.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By.ByXPath;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import atu.testng.reports.utils.Utils;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;
import junit.framework.Assert;

@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class PostTestDeleteUsers {
	

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

			driver=new FirefoxDriver();
			ATUReports.add("selected browser type", LogAs.PASSED, new CaptureScreen( ScreenshotOf.DESKTOP));

			
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
		
			

		

		
		@Test(description = "Login course page")
		public void loginCourses() throws InterruptedException,UnsupportedEncodingException, FileNotFoundException
		{
			// 1. Login as INSTRUCTOR.
			tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
        	tegrity.loginAdmin("Admin");
			Thread.sleep(2000);
			
			String login_url = driver.getCurrentUrl();
			String university_name  = login_url.split("/")[2].substring(0,  login_url.split("/")[2].length() - 12);
			
			
			
			for(String window: driver.getWindowHandles()) {
				driver.switchTo().window(window);
				break;
			}
			
			mange_adhoc_course_enrollments.clickOnAdminDashboard();
			
			Thread.sleep(2000);
			
		
			// 2. Click on user builder href link
			admin_dashboard_page.clickOnTargetSubmenuUsers("Manage Ad-hoc Users (User Builder)");
			
			Thread.sleep(10000);
					
			// 3. Click on create course href link 
			driver.switchTo().frame(0);
			Thread.sleep(2000);
			
			// 4. delete users-                          delete button id
	WebElement delete_user_button=driver.findElement(By.id("ctl00_ContentPlaceHolder1_rptUserBuilder_ctl02_lbDelUser"));
	WebElement search_user_button=driver.findElement(By.xpath("//*[@id=\"ctl00_ContentPlaceHolder1_pSearchUserList\"]"));
		
	while(delete_user_button.isDisplayed())
			{
				try {
				delete_user_button.click();
				System.out.println("clicked on delete");
				Thread.sleep(500);
				driver.switchTo().alert().accept();
			    System.out.println("user deleted");
				Thread.sleep(1500);
				try {
				delete_user_button=driver.findElement(By.id("ctl00_ContentPlaceHolder1_rptUserBuilder_ctl02_lbDelUser"));
				}
				catch(Exception e)
				{
					System.out.println("finished delete all users");
				Assert.assertTrue(true);
					return;
				}
				}
				catch(Exception e)
				{
					System.out.println("tried to delete not existed user");
					Assert.assertTrue(false);
				}
				}
			
	
		}			
	}


