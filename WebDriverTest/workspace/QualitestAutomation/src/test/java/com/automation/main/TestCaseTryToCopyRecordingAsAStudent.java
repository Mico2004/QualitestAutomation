package com.automation.main;

//precondition student first course must have recordings in recordings tab as well as in student recordings tab

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
public class TestCaseTryToCopyRecordingAsAStudent {

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
	String current_course;
	String targetCourse;
	String clickedRecording;
    DesiredCapabilities capability;
	@BeforeClass
	public void setup() {

		
		System.setProperty("webdriver.ie.driver", "src/test/resources/IEDriverServer.exe");
			capability=DesiredCapabilities.internetExplorer();
			capability.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING,true);
			
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
		course.courses = course.getStringFromElement(course.course_list);
	}

	
	@Test(dependsOnMethods = "loadPage", description = "Login course page")
	public void loginCourses() throws InterruptedException
	{
		// 1. Login as Student.
		tegrity.loginCourses("Student");
		initializeCourseObject();
		
		// 2. Select course.
		current_course = course.selectFirstCourse(record);
		System.out.println("Current course: " + current_course);
		//course.selectCourse(record);
		
		// 3. Select any recording.
		record.selectFirstCheckbox();
		
		// 4. Click "Recording Tasks" button.
		record.recording_tasks_button.click();
		
		// 5. Verify that "Copy" menu item is not displayed in "Recording Tasks" menu.
		boolean is_displayed = record.isCopyButtonDisplayedInRecordingTasks();
		
		if(!is_displayed) {
			System.out.println("Copy button is not displayed in recording tasks menu");
			ATUReports.add("Copy button is not displayed in recording tasks menu", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Copy button is displayed in recording tasks menu");
			ATUReports.add("Copy button is displayed in recording tasks menu", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		
		// 6. Click "Student Recordings" tab.
		record.clickOnStudentRecordingsTab();
		
		Thread.sleep(1000);
		
		// 7. Select any recording.
		record.selectFirstCheckbox();
		
		// 8. Click "Recording Tasks" button.
		record.recording_tasks_button.click();
		
		// 9. Verify that "Copy" menu item is not displayed in "Recording Tasks" menu
		record.isCopyButtonDisplayedInRecordingTasks();
		
		if(!is_displayed) {
			System.out.println("Copy button is not displayed in recording tasks menu");
			ATUReports.add("Copy button is not displayed in recording tasks menu", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Copy button is displayed in recording tasks menu");
			ATUReports.add("Copy button is displayed in recording tasks menu", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		driver.quit();
	}
}
