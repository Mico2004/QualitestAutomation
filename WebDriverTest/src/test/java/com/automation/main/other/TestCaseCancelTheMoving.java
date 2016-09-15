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
public class TestCaseCancelTheMoving {

	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}

	public LoginHelperPage tegrity;
	public CoursesHelperPage course;
	public RecordingHelperPage record;
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
	
	// description = "get courses list"
	public void initializeCourseObject() throws InterruptedException {

		course = PageFactory.initElements(driver, CoursesHelperPage.class);
		course.courses = course.getStringFromElement(course.course_list);
	}

	
	@Test(dependsOnMethods = "loadPage", description = "Login course page")
	public void loginCourses() throws InterruptedException//
	{
		// 1. Login as INSTRUCTOR.
		tegrity.loginCourses("Instructor");// log in courses page
		initializeCourseObject();
		
		// 2. Select course.
		currentCourse = course.selectSecondCourse(record);
		System.out.println("Current course: " + currentCourse);
		//course.selectCourse(record);
		
		
		// 3. Select source recording.
		// 4. Select "Recording Tasks -> Move" menu item.
		clickedRecording = record.getFirstRecordingTitle();
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
		record.clickOnRecordingTaskThenMove();
		System.out.println("Clicked on recording: " + clickedRecording);
		
		
		//5. Select destination course.
		targetCourse = copy.selectCourseFromCourseListOtherThenCurrentCourse(this.currentCourse);
		System.out.println("Target course: " + targetCourse);
		
		//6. Click "Cancel" button.
		copy.clickOnCancelButton(record);
		
		Thread.sleep(3000);
		

		//7. Verify that recording is not removed current course.
		boolean isRecordingExist = record.isRecordingExistAsTopRecording(clickedRecording, true);
		if (isRecordingExist) {
			System.out.println("Recording is exist.");
		} else {
			System.out.println("Recording is not exist");
		}
		
		//8. Click "Courses" link at breadcrumbs.
		record.returnToCourseListPage();
		
		Thread.sleep(2000);
		
		//9. Select the destination course.
		boolean isTargetCourseClicked = course.clickOnTargetCourseName(targetCourse);
		if (isTargetCourseClicked) {
			System.out.println("Target course name clicked: " + targetCourse);
		} else {
			System.out.println("Target course name is not clicked: " + targetCourse);
		}
		
		Thread.sleep(2000);
		
		//10. Verify that recording wasn't copied.
		List<String> current_recording_list = record.getCourseRecordingList();
		
		if(current_recording_list.contains(clickedRecording)) {
			System.out.println("Recording is exist");
			ATUReports.add("Recording is exist", LogAs.FAILED, null);
			Assert.assertTrue(false);
		} else {
			System.out.println("Recording is not exist.");
			ATUReports.add("Recording is not exist.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		}
		
		Thread.sleep(2000);
		
		//11. Click "Courses" link at breadcrumbs.
		record.returnToCourseListPage();
		
		Thread.sleep(2000);
		
		//12. Select the source course.
		isTargetCourseClicked = course.clickOnTargetCourseName(currentCourse);
		if (isTargetCourseClicked) {
			System.out.println("Target course name clicked: " + currentCourse);
		} else {
			System.out.println("Target course name is not clicked: " + currentCourse);
		}
		
		//13. Select source recording.
		//14. Select "Recording Tasks -> Move" menu item.
		clickedRecording = record.getFirstRecordingTitle();
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
		record.clickOnRecordingTaskThenMove();
		System.out.println("Clicked on recording: " + clickedRecording);
		
		//15. Select destination course.
		targetCourse = copy.selectCourseFromCourseListOtherThenCurrentCourse(this.currentCourse);
		System.out.println("Target course: " + targetCourse);
		
		//16. Click on Esc button in the keyboard.
		copy.clickEscOnKeyBoardToCloseCopyWindow();
		
		//17. Verify that isn't removed from the current course.
		isRecordingExist = record.isRecordingExistAsTopRecording(clickedRecording, true);
		if (isRecordingExist) {
			System.out.println("Recording is exist.");
		} else {
			System.out.println("Recording is not exist");
		}
		
		//18. Click "Courses" link at breadcrumbs.
		record.returnToCourseListPage();
		
		//19. Select the destination course.
		isTargetCourseClicked = course.clickOnTargetCourseName(targetCourse);
		if (isTargetCourseClicked) {
			System.out.println("Target course name clicked: " + targetCourse);
		} else {
			System.out.println("Target course name is not clicked: " + targetCourse);
		}
		
		//20. Verify that recording wasn't copied.
		current_recording_list = record.getCourseRecordingList();
		
		if(current_recording_list.contains(clickedRecording)) {
			System.out.println("Recording is exist");
			ATUReports.add("Recording is exist", LogAs.FAILED, null);
			Assert.assertTrue(false);
		} else {
			System.out.println("Recording is not exist.");
			ATUReports.add("Recording is not exist.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		}
		driver.quit();
	}

}
