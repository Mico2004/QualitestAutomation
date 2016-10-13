package com.automation.main.other;


import java.awt.AWTException;
import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.automation.main.page_helpers.ConfirmationMenu;
import com.automation.main.page_helpers.CopyMenu;
import com.automation.main.page_helpers.CoursesHelperPage;
import com.automation.main.page_helpers.LoginHelperPage;
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
public class TestCaseTryToCopyFromPastCourseToAnotherPastCourseToItself {

	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}

	public LoginHelperPage tegrity;
	public CoursesHelperPage course;
	public RecordingHelperPage record;
	public ConfirmationMenu confirm_menu;
	WebDriver driver;
	WebDriverWait wait;
	public static WebDriver thread_driver;
	CopyMenu copy;
	String currentCourse;
	String targetCourse;
	String clickedRecording;
    DesiredCapabilities capability;
	@BeforeClass
	public void setup() {

		
		System.setProperty("webdriver.ie.driver", "src/test/resources/IEDriverServer.exe");
			capability=DesiredCapabilities.internetExplorer();
			capability.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING,false);
			
		driver=new InternetExplorerDriver(capability);
		ATUReports.add("selected browser type", LogAs.PASSED, new CaptureScreen( ScreenshotOf.DESKTOP));

		
		//ATUReports.setWebDriver(driver);
		//ATUReports.add("set driver", true);
		tegrity = PageFactory.initElements(driver, LoginHelperPage.class);

		record = PageFactory.initElements(driver, RecordingHelperPage.class);
		copy = PageFactory.initElements(driver, CopyMenu.class);
		
		confirm_menu = PageFactory.initElements(driver, ConfirmationMenu.class);
	}
	
	@AfterTest
	public void closeBroswer() {
		this.driver.quit();
	}


	// @Parameters({"web","title"}) in the future
	@Test
	public void loadPage() throws InterruptedException {
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);

	}
	
	// description = "get courses list"
	public void initializeCourseObject() throws InterruptedException {

		course = PageFactory.initElements(driver, CoursesHelperPage.class);
		course.courses = course.getCoursesListFromElement(course.course_list);
	}

	
	@Test(dependsOnMethods = "loadPage", description = "Login course page")
	public void loginCourses() throws InterruptedException
	{
		// 1. Login as INSTRUCTOR.
		tegrity.loginCourses("Instructor");// log in courses page
		initializeCourseObject();
		
		// 2. Get active course list.
		List<String> active_course_list = course.getCourseList(); 
		
		System.out.println("Active course list:");
		for(String course_name : active_course_list) {
			System.out.println(course_name);
		}
		
		
		// 3. Click the "Past Courses" tab.
		course.clickOnPastCoursesTabButton();
		
		Thread.sleep(2000);
		
		// 4. Get past course list.
		List<String> past_course_list = course.getCourseList();
		
		System.out.println("Past course list:");
		for(String course_name : past_course_list) {
			System.out.println(course_name);
		}
		
		// 5. Select the past course.
		currentCourse = course.selectSecondCourse(record);
		System.out.println("Current course: " + currentCourse);
		
		// 6. Select the recording.
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
		
		// 7. Select the "Recording Tasks -> Copy" menu item.
		record.clickOnRecordingTaskThenCopy();
		
		Thread.sleep(2000);
		
		// 8. Verify that no past course is not displayed in list of destination courses.
		List<String> copy_menu_course_list = copy.getCourseList();
		Thread.sleep(2000);
		if(copy_menu_course_list.containsAll(active_course_list)) {
			if (!copy_menu_course_list.contains(past_course_list)) {
				System.out.println("Contain only active course.");
				ATUReports.add("Contain only active course.", LogAs.PASSED, null);
				Assert.assertTrue(true);
			} else {
				System.out.println("Not contain only active course.");
				ATUReports.add("Not contain only active course.", LogAs.FAILED, null);
				Assert.assertTrue(false);
			}
		} else {
			System.out.println("Not contain all active course.");
			ATUReports.add("Not contain all active course.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		
		// 9. Click the "Cancel" button.
		copy.clickOnCancelButton(record);
		
		// 10. Click the "Additional Content" tab.
		record.clickOnAdditionContentTab();
		
		Thread.sleep(2000);
		
		// 11. Select the content item.
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
		
		// 12. Select the "Content tasks -> Copy" menu item.
		record.clickOnContentTaskThenCopy();
		
		Thread.sleep(2000);
		
		// 13. Verify that no past course is not displayed in list of destination courses.	
		copy_menu_course_list = copy.getCourseList();
		
		if(copy_menu_course_list.containsAll(active_course_list)) {
			if (!copy_menu_course_list.contains(past_course_list)) {
				System.out.println("Contain only active course.");
				ATUReports.add("Contain only active course.", LogAs.PASSED, null);
				Assert.assertTrue(true);
			} else {
				System.out.println("Not contain only active course.");
				ATUReports.add("Not contain only active course.", LogAs.FAILED, null);
				Assert.assertTrue(false);
			}
		} else {
			System.out.println("Not contain all active course.");
			ATUReports.add("Not contain all active course.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}

	}
}
