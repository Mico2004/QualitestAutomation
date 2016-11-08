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
public class PreTestCreateDynamicCourse {

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
		mange_adhoc_course_enrollments.clickOnNewCourse();
		Thread.sleep(Page.TIMEOUT_TINY);
		
		// 4. Create four dynamic courses
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
		
		for(int i = 0; i < 7; i++) {
			String course_name = "";
			if (i == 0) {
				course_name = "Ab" + university_name + sdf.format(date);
			} else if (i == 1) {
				course_name = "abc" + university_name + sdf.format(date);
			} else if (i == 2) {
				course_name = "ac" + university_name + sdf.format(date);
			} else if (i == 3) {
				course_name = "Ba" + university_name + sdf.format(date);
			} else if (i == 4) {
				course_name = "BAb" + university_name + sdf.format(date);
			}else if (i == 5) {
				course_name = "PastCourseA" + university_name + sdf.format(date);
			}else if (i == 6) {
				course_name = "PastCourseB" + university_name + sdf.format(date);
			}
			
			mange_adhoc_course_enrollments.clickOnNewCourse();
			Thread.sleep(Page.TIMEOUT_TINY);
			
			create_new_course_window.createNewCourse(course_name + "_Name", course_name);
			Thread.sleep(Page.TIMEOUT_TINY);
			driver.switchTo().alert().accept();
			Thread.sleep(Page.TIMEOUT_TINY);
			
		}
		
		
		
	
		
	}

}
