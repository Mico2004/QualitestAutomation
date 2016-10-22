package com.automation.main;


import java.awt.AWTException;
import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;import com.automation.main.page_helpers.Page;
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
public class TC15553TryToMoveWithoutSelectingACourse {

	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}

	public LoginHelperPage tegrity;
	public CoursesHelperPage course;
	public RecordingHelperPage record;
	public ConfirmationMenu confirm_menu;
	public MoveWindow move_window;
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
/*
		System.setProperty("webdriver.ie.driver", "src/test/resources/IEDriverServer.exe");
		capability=DesiredCapabilities.internetExplorer();
		capability.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING,false);
		*/
	//driver=new InternetExplorerDriver(capability);
		driver=new FirefoxDriver();
		ATUReports.add("selected browser type", LogAs.PASSED, new CaptureScreen( ScreenshotOf.DESKTOP));

		driver.manage().window().maximize();
		ATUReports.setWebDriver(driver);
		//ATUReports.add("set driver", true);
		tegrity = PageFactory.initElements(driver, LoginHelperPage.class);

		record = PageFactory.initElements(driver, RecordingHelperPage.class);
		copy = PageFactory.initElements(driver, CopyMenu.class);
		
		confirm_menu = PageFactory.initElements(driver, ConfirmationMenu.class);
		
		move_window = PageFactory.initElements(driver, MoveWindow.class);
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
	public void loginCourses() throws Exception
	{
		// 1. Login as INSTRUCTOR.
		tegrity.loginCourses("Instructor");// log in courses page
		initializeCourseObject();
		
		// 2. Select course.
		currentCourse = course.selectSecondCourse(record);
		System.out.println("Current course: " + currentCourse);
		//course.selectCourse(record);
		
		// 3. Select recording.
		record.selectFirstCheckbox();
		
		// 4. Select "Recording Tasks -> Move"
		record.clickOnRecordingTaskThenMove();
		
		// 5. Click "Move Recording(s)" button without selecting a course.
		move_window.clickOnMoveRecordings();
		
		Thread.sleep(Page.TIMEOUT_TINY);
		
		
		// 6. Click "OK" button + Message box "A course must be selected." is displayed
		confirm_menu.clickOnOkButtonOnErrorWindow();
		
		// 7. Message box is closed
		
		if(!driver.getPageSource().contains("A course must be selected")) {
			System.out.println("Message box is closed");
			ATUReports.add("Message box.", "Message box is closed", "Message box is closed", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Message box is not closed");
			ATUReports.add("Message box.", "Message box is closed", "Message box is not closed", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		
		// 8. Click "Cancel" button.
		copy.clickOnCancelButton(record);
		
		// 9. "Copy" window is closed
		boolean is_closed = copy.isCopyMenuClosed();
		
		if(is_closed) {
			System.out.println("Copy window is closed");
			ATUReports.add("Copy window.", "Copy window is closed.", "Copy window is closed.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Copy window is not closed");
			ATUReports.add("Copy window.", "Copy window is closed.", "Copy window is not closed", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}

		// Quit the browser.
		driver.quit();
	}
}
