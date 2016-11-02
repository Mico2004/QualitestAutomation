package com.automation.main.pre_post_test;
		


import java.awt.AWTException;
import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.apache.http.impl.client.EntityEnclosingRequestWrapper;
import org.omg.PortableInterceptor.AdapterManagerIdHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
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

import com.automation.main.page_helpers.AdminDashboardPage;
import com.automation.main.page_helpers.CreateNewUserWindow;
import com.automation.main.page_helpers.LoginHelperPage;
import com.automation.main.page_helpers.ManageAdhocUsersPage;

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
public class PreTestCreateDynamicUsers {

	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}

	public LoginHelperPage tegrity;
	WebDriver driver;
	WebDriverWait wait;
	public static WebDriver thread_driver;
	public AdminDashboardPage admin_dashboard_page;
	public ManageAdhocUsersPage mange_adhoc_users_page;
	public CreateNewUserWindow create_new_user_window;

	@BeforeClass
	public void setup() {

		driver=new FirefoxDriver();
		ATUReports.add("selected browser type", LogAs.PASSED, new CaptureScreen( ScreenshotOf.DESKTOP));

		
		//ATUReports.setWebDriver(driver);
		//ATUReports.add("set driver", true);
		tegrity = PageFactory.initElements(driver, LoginHelperPage.class);
		
		admin_dashboard_page = PageFactory.initElements(driver, AdminDashboardPage.class);
		
		mange_adhoc_users_page = PageFactory.initElements(driver, ManageAdhocUsersPage.class);
		
		create_new_user_window = PageFactory.initElements(driver, CreateNewUserWindow.class);
	
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
		Thread.sleep(2000);
		
		String login_url = driver.getCurrentUrl();
		String university_name  = login_url.split("/")[2].substring(0,  login_url.split("/")[2].length() - 12);
		
		// 2. Click on user builder href link
		admin_dashboard_page.clickOnTargetSubmenuUsers("Manage Ad-hoc Users (User Builder)");
		
		Thread.sleep(10000);
		
		// 3. Click on create course href link 
		driver.switchTo().frame(0);
		Thread.sleep(2000);
		
		// 4. Create four dynamic users
		
		
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyhhmmss");
		
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
				user_name = "User5" + sdf.format(date);
			}
			
			mange_adhoc_users_page.clickOnNewUser();
			Thread.sleep(2000);
			
			create_new_user_window.createNewUser(user_name, user_name, "abc@com.com", "111", "111");
			Thread.sleep(1000);
			driver.switchTo().alert().accept();
			Thread.sleep(1000);
			
		}
		
		
		
	
		
	}

}
