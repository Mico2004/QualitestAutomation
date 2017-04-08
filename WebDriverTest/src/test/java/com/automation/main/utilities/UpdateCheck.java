package com.automation.main.utilities;



import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.automation.main.page_helpers.AdminDashboardPage;
import com.automation.main.page_helpers.CopyMenu;
import com.automation.main.page_helpers.CoursesHelperPage;
import com.automation.main.page_helpers.LoginHelperPage;
import com.automation.main.page_helpers.RecordingHelperPage;
import com.automation.main.utilities.*;

import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import atu.testng.reports.utils.Utils;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;
import junit.framework.Assert;

import java.text.DateFormat;
import java.util.Date;


//@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class UpdateCheck {

	// Set Property for ATU Reporter Configuration
	/*{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}*/

	public LoginHelperPage tegrity;
	public CoursesHelperPage course;
	public RecordingHelperPage record;
	public AdminDashboardPage admin;
	public TxtParserUtility parser;
	WebDriver driver;
	WebDriverWait wait;
	public static WebDriver thread_driver;
	CopyMenu copy;
	DesiredCapabilities capability;
	String os;


	@BeforeClass
	public void setup() {
		try {


			driver = DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());
			ATUReports.add("selected browser type", LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
			parser=new TxtParserUtility();
			tegrity = PageFactory.initElements(driver, LoginHelperPage.class);
			admin=PageFactory.initElements(driver, AdminDashboardPage.class);
			record = PageFactory.initElements(driver, RecordingHelperPage.class);
			copy = PageFactory.initElements(driver, CopyMenu.class);
			
		} catch (Exception e) {
			org.testng.Assert.assertTrue(false);
		}
		

		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("UpdateCheck at " + DateToStr);
		 	
	}

	@AfterClass
	public void closeBroswer() {
		this.driver.quit();
	}
		

	
	// @Parameters({"web","title"}) in the future
	@Test (description="Update Check")
	public void test15534() throws InterruptedException {
		
		try{
	
		// log in courses page
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
		
		tegrity.loginAdmin("Admin");
		
		admin.waitForPageToLoad();
		
		Thread.sleep(3000);
		
		if(admin.versionChanged()){
			org.testng.Assert.assertTrue(true);
			System.out.println("Version changed");
		}
		else{
			org.testng.Assert.assertTrue(false);
			System.out.println("Version didn't changed");
		}
		
	}catch(Exception e){
		System.out.println("Version didn't changed");
		org.testng.Assert.assertTrue(false);
		
	}
		
		
		}

	// description = "get courses list"
	public void initializeCourseObject() throws InterruptedException {

		course = PageFactory.initElements(driver, CoursesHelperPage.class);
		course.courses = course.getCoursesListFromElement(course.course_list);
	}
}
