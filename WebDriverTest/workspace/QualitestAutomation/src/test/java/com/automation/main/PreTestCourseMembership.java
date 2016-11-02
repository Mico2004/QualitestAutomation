package com.automation.main;


import java.awt.AWTException;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.apache.http.impl.client.EntityEnclosingRequestWrapper;
import org.omg.PortableInterceptor.AdapterManagerIdHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;import com.automation.main.page_helpers.Page;import com.automation.main.page_helpers.Page;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
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
import junitx.util.PropertyManager;

@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class PreTestCourseMembership {

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
		
		mangage_adhoc_courses_membership_window = PageFactory.initElements(driver, ManageAdHocCoursesMembershipWindow.class);
	
	}
	
//	@AfterTest
//	public void closeBroswer() {
//		this.driver.quit();
//	}

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
	{
		// 1. Login as INSTRUCTOR.
		tegrity.loginAdmin("Admin");
		Thread.sleep(Page.TIMEOUT_TINY);
		
		String login_url = driver.getCurrentUrl();
		String university_name  = login_url.split("/")[2].substring(0,  login_url.split("/")[2].length() - 12);
		
		// 2. Click on course builder href link
		admin_dashboard_page.clickOnTargetSubmenuCourses("Manage Ad-hoc Courses / Enrollments (Course Builder)");
		
		Thread.sleep(Page.TIMEOUT_TINY);
		
		// 3. Click on create course href link 
		driver.switchTo().frame(0);
		Thread.sleep(Page.TIMEOUT_TINY);
		
		
		// Search target course name
		mange_adhoc_course_enrollments.searchAndFilterCourses("abcreg-qabr03432016");
		
		Thread.sleep(Page.TIMEOUT_TINY);
		
		// Click on result first course (the only one) membership button
		mange_adhoc_course_enrollments.clickOnFirstCourseMembershipButton();
		
		Thread.sleep(Page.TIMEOUT_TINY);
		
		// Search target user name in membership window
		mangage_adhoc_courses_membership_window.searchForUser("User2040220161025");
		
		Thread.sleep(Page.TIMEOUT_TINY);
		
		// Select first user from user list (the only user it found because of the uniq of the search)
		mangage_adhoc_courses_membership_window.selectFirstUserFromUserList();
		
		// Add selected user to instructor list
		mangage_adhoc_courses_membership_window.clickOnAddSelectedUserToInstructorList();
		
		Thread.sleep(Page.TIMEOUT_TINY);
		
		// Confirm user membership list
		mangage_adhoc_courses_membership_window.clickOnOkButton();
		
		driver.switchTo().alert().accept();
		
		
		
		
	
		
	}

}
