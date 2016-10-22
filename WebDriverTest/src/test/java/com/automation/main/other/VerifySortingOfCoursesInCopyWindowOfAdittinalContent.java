package com.automation.main.other;

import java.awt.List;

import org.openqa.selenium.WebDriver;import com.automation.main.page_helpers.Page;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.automation.main.page_helpers.ConfirmationMenu;
import com.automation.main.page_helpers.CopyMenu;
import com.automation.main.page_helpers.CoursesHelperPage;
import com.automation.main.page_helpers.LoginHelperPage;
import com.automation.main.page_helpers.PlayerPage;
import com.automation.main.page_helpers.RecordingHelperPage;

public class VerifySortingOfCoursesInCopyWindowOfAdittinalContent {
	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}

	public LoginHelperPage tegrity;
	public CoursesHelperPage course;
	public RecordingHelperPage record;
	public PlayerPage player_page;
	WebDriver driver;
	WebDriverWait wait;
	public static WebDriver thread_driver;
	CopyMenu copy;
	DesiredCapabilities capability;
	String os;
	List recording_list_object;
	ConfirmationMenu confirm;

	@BeforeClass
	public void setup() {
		try {

			System.setProperty("webdriver.ie.driver", "src/test/resources/IEDriverServer.exe");
			capability=DesiredCapabilities.internetExplorer();
			capability.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING,false);
			
		driver=new InternetExplorerDriver(capability);
			// ATUReports.add("selected browser type", LogAs.PASSED, new
			// CaptureScreen(ScreenshotOf.DESKTOP));
			

			tegrity = PageFactory.initElements(driver, LoginHelperPage.class);

			record = PageFactory.initElements(driver, RecordingHelperPage.class);
			copy = PageFactory.initElements(driver, CopyMenu.class);
			player_page = PageFactory.initElements(driver, PlayerPage.class);
		} catch (Exception e) {
			/// ATUReports.add("Fail Step", LogAs.FAILED, new
			/// CaptureScreen(ScreenshotOf.DESKTOP));
		}
		driver.quit();
	}

	@Test
	public void VerifySortingOfCoursesInCopyWindow() throws InterruptedException
	{
		///1.login page 
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
        //2.login as instructor
		tegrity.loginCourses("Instructor");// log in courses page
		//initialize course list
		initializeCourseObject();
	//3.select course
		course.selectFirstCourse(record);
		record = PageFactory.initElements(driver, RecordingHelperPage.class);
		//4.click on  test tab
		record.clickOnTestAdittionalContentTab();
		Thread.sleep(Page.TIMEOUT_TINY);
		//5.select check box
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
        record.getCheckbox().click();
		///6.select copy menu
    	copy.verifyCopyMenuByContentTasks(record);
	//7.verify courses are displayed in alphabetical order
		record.verifyRecordingSortedByTitle(copy.getCourseList());///verify sorted by title
	
	}
	
	// description = "get courses list"
	public void initializeCourseObject() throws InterruptedException {

		course = PageFactory.initElements(driver, CoursesHelperPage.class);
		course.courses = course.getCoursesListFromElement(course.course_list);
		course.size = course.course_list.size();
	}
}
