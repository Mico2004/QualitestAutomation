package com.automation.main.other;


import java.awt.AWTException;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.util.Date;
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
import com.automation.main.page_helpers.DeleteMenu;
import com.automation.main.page_helpers.LoginHelperPage;
import com.automation.main.page_helpers.MoveWindow;
import com.automation.main.page_helpers.RecordingHelperPage;
import com.automation.main.utilities.DriverSelector;

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
public class TestCase15639TryToDeleteARegularRecordingAsAStudent {

	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}

	public LoginHelperPage tegrity;
	public CoursesHelperPage course;
	public RecordingHelperPage record;
	public ConfirmationMenu confirm_menu;
	public MoveWindow move_window;
	public DeleteMenu delete_menu;
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
		
        driver=DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());
		 ATUReports.add("selected browser type", LogAs.PASSED, new CaptureScreen( ScreenshotOf.DESKTOP));

		
		//ATUReports.setWebDriver(driver);
		//ATUReports.add("set driver", true);
		tegrity = PageFactory.initElements(driver, LoginHelperPage.class);

		record = PageFactory.initElements(driver, RecordingHelperPage.class);
		copy = PageFactory.initElements(driver, CopyMenu.class);
		
		confirm_menu = PageFactory.initElements(driver, ConfirmationMenu.class);
		
		move_window = PageFactory.initElements(driver, MoveWindow.class);
		
		delete_menu = PageFactory.initElements(driver, DeleteMenu.class);
		
		Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TestCase15639TryToDeleteARegularRecordingAsAStudent at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TestCase15639TryToDeleteARegularRecordingAsAStudent at " + DateToStr,
		 "Starting the test: TestCase15639TryToDeleteARegularRecordingAsAStudent at " + DateToStr, LogAs.PASSED, null);	
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
		// 1. Login as Student.
		tegrity.loginCourses("User4");// log in courses page
		initializeCourseObject();
		
		// 2. Select course.
		currentCourse = course.selectCourseThatStartingWith("Ab");
		System.out.println("Current course: " + currentCourse);
		
		// 3. Select recording to delete.
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
		
		// 4. Verify that "Delete" menu item isn't dispalyed in menu.
		record.recording_tasks_button.click();
		
		if (record.delete_button.isDisplayed()) {
			System.out.println("Verify that delete menu item is displayed in menu.");
			ATUReports.add("Verify that delete menu item is displayed in menu.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		} else {
			System.out.println("Verify that delete menu item is not displayed in menu.");
			ATUReports.add("Verify that delete menu item is not displayed in menu.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		}
		
		// 5. Select several recordings.
		try {
			record.check_all_checkbox.click();
			System.out.println("Clicked on selected all checkbox.");
			ATUReports.add("Clicked on selected all checkbox.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} catch (Exception msg) {
			System.out.println("Fail to clicked on selected all checkbox.");
			ATUReports.add("Fail to clicked on selected all checkbox.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		
		record.verifyAllCheckedboxSelected();
		
		// 6. Click the "Recording Tasks" button.
		record.recording_tasks_button.click();
		
		// 7. Verify that "Delete" menu item isn't dispalyed in menu.
		if (record.delete_button.isDisplayed()) {
			System.out.println("Verify that delete menu item is displayed in menu.");
			ATUReports.add("Verify that delete menu item is displayed in menu.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		} else {
			System.out.println("Verify that delete menu item is not displayed in menu.");
			ATUReports.add("Verify that delete menu item is not displayed in menu.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		}
		
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
	}
}
