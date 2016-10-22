package com.automation.main.other;

// Precondition: first recording in second course must be long duration

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
public class TestCase15898VerifyTheCopyingMovingStatusOfADestinationRecording {

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
	String target_course;
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
		
		// 2. Select course.
		target_course = course.getCourseInIndex(1);
		current_course = course.selectSecondCourse(record);
		System.out.println("Current course: " + current_course);
		//course.selectCourse(record);
		
		// 3. Select recording.
		String selected_recording_name = record.getFirstRecordingTitle();
		System.out.println("Record to select: " + selected_recording_name);
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
		Thread.sleep(Page.TIMEOUT_TINY);
		// 4. Select "Recording Tasks -> Copy"
		record.clickOnRecordingTaskThenCopy();
		
		// 5. Select destination course.
		copy.selectTargetCourseFromCourseList(target_course);
		
		// 6. Click "Copy Recording(s)".
		copy.clickOnCopyButton();
		
		// 7. Click "OK" button.
		confirm_menu.clickOnOkButtonAfterConfirmCopyRecording();
		
		// 7.1. Message is closed.
		boolean is_closed = confirm_menu.isConfirmationMenuClosed();
		
		if(is_closed) {
			System.out.println("Message is closed.");
			ATUReports.add("Message is closed.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Message is not closed.");
			ATUReports.add("Message is not closed.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		
		// 7.2. "Copy" window is closed.
		is_closed = copy.isCopyMenuClosed();
		
		if(is_closed) {
			System.out.println("Copy window is closed.");
			ATUReports.add("Copy window is closed.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Copy window is not closed.");
			ATUReports.add("Copy window is not closed.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		
		// 7.3. The source recording has status "Being copying from".
		record.checkRecordingInIndexIStatus(1, "Being copied from");
		
		// 8. While recording is being copied, click the "Courses" link at the breadcrumbs.
		record.returnToCourseListPage();
		
		Thread.sleep(Page.TIMEOUT_TINY);
		
		// 9. Select destination course.
		course.selectTargetCourse(target_course);
		
		// 10. Verify that destination recording has a "Moving/Copying" status.
		// 10.1. Recr`row is grayed out.
		// 10.2. Recording has a "Moving/Copying" status.	
		List<String> current_recording_list = record.getCourseRecordingList();
		
		for(int i = 0; i < current_recording_list.size(); i++) {
			if(current_recording_list.get(i).equals(selected_recording_name)) {
				record.checkRecordingInIndexIStatus(i + 1, "Moving/Copying");
				if(!record.isIndexRecordingClickable(i + 1)) {
					System.out.println("Recording is grayed out");
					ATUReports.add("Recording is grayed out", LogAs.PASSED, null);
					Assert.assertTrue(true);
				} else {
					System.out.println("Recording is not grayed out");
					ATUReports.add("Recording is not grayed out", LogAs.FAILED, null);
					Assert.assertTrue(false);
				}
	
			}
		}
		driver.quit();
	}
}
