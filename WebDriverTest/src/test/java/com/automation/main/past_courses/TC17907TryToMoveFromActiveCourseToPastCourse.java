package com.automation.main.past_courses;



import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import org.openqa.selenium.WebDriver;import com.automation.main.page_helpers.Page;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.automation.main.page_helpers.ConfirmationMenu;
import com.automation.main.page_helpers.CoursesHelperPage;
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
public class TC17907TryToMoveFromActiveCourseToPastCourse {

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
	MoveWindow move_window;
	String currentCourse;
	String targetCourse;
	String clickedRecording;
    DesiredCapabilities capability;
	@BeforeClass
	public void setup() {

		
		System.setProperty("webdriver.ie.driver", "src/test/resources/IEDriverServer.exe");
			capability=DesiredCapabilities.internetExplorer();
			capability.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING,true);
			
	//	driver=new InternetExplorerDriver(capability);
		
			driver = DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());
			ATUReports.add("selected browser type", LogAs.PASSED, new CaptureScreen( ScreenshotOf.DESKTOP));

		
		//ATUReports.setWebDriver(driver);
		//ATUReports.add("set driver", true);
		tegrity = PageFactory.initElements(driver, LoginHelperPage.class);

		record = PageFactory.initElements(driver, RecordingHelperPage.class);
		move_window = PageFactory.initElements(driver, MoveWindow.class);
		
		confirm_menu = PageFactory.initElements(driver, ConfirmationMenu.class);
		
		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC17907TryToMoveFromActiveCourseToPastCourse at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC17907TryToMoveFromActiveCourseToPastCourse at " + DateToStr,
		 "Starting the test: TC17907TryToMoveFromActiveCourseToPastCourse at " + DateToStr, LogAs.PASSED, null);
		 
	}
	
	
	@AfterClass
	public void closeBroswer() {
		this.driver.quit();
	}

	
	// description = "get courses list"
	public void initializeCourseObject() throws InterruptedException {

		course = PageFactory.initElements(driver, CoursesHelperPage.class);
		course.courses = course.getCoursesListFromElement(course.course_list);
	}

	
	@Test( description = "TC 17907 Try To Move From Active Course To Past Course")
	public void test17907() throws InterruptedException
	{	
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);

		
		// 1. Login as INSTRUCTOR.
		tegrity.loginCourses("User1");// log in courses page
		initializeCourseObject();
		
		// 2. Click on past course tab.
		course.clickOnPastCoursesTabButton();
		
		Thread.sleep(Page.TIMEOUT_TINY);
		
		// 3. Get past course list.
		List<String> past_course_list = course.getCourseList();
		
		// 4. Click on active course tab.
		course.clickOnActiveCoursesTabButton();
		
		Thread.sleep(Page.TIMEOUT_TINY);
		
		// 5. Select the active course.
		currentCourse = course.selectCourseThatStartingWith("Ab");
		System.out.println("Current course: " + currentCourse);
		
		// 6. Select the recording.
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
		
		// 7. Select the "Recording Tasks -> Move" menu item.
		record.clickOnRecordingTaskThenMove();
		
		// 8. Verify that past course is not displayed in list of destination courses.
		List <String> move_menu_course_list = move_window.getCourseList();
		
		boolean is_past_course_found_in_copy_menu_course_list = false;
		for(String course_name: past_course_list) {
			if (move_menu_course_list.contains(course_name)) {
				is_past_course_found_in_copy_menu_course_list = true;
			}
		}
				
		if(is_past_course_found_in_copy_menu_course_list) {
			System.out.println("There is past course which displayed in list of destination courses.");
			ATUReports.add("There is past course which displayed in list of destination courses.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		} else {
			System.out.println("Verify that past course is not displayed in list of destination courses.");
			ATUReports.add("Verify that past course is not displayed in list of destination courses.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		}
		
		// 9. Click the "Cancel" button.
		move_window.clickOnCancelButton();
		
		// 10. Click the "Additional Content" tab.
		record.clickOnAdditionContentTab();
				
		Thread.sleep(Page.TIMEOUT_TINY);
			
		// 11. Select the content item.
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
		
		// 12. Select the "Content tasks -> Move" menu item.
		record.clickOnContentTaskThenMove();
		
		// 13. Verify that past course is not displayed in list of destination courses.
		move_menu_course_list = move_window.getCourseList();
		
		is_past_course_found_in_copy_menu_course_list = false;
		for(String course_name: past_course_list) {
			if (move_menu_course_list.contains(course_name)) {
				is_past_course_found_in_copy_menu_course_list = true;
			}
		}
				
		if(is_past_course_found_in_copy_menu_course_list) {
			System.out.println("There is past course which displayed in list of destination courses.");
			ATUReports.add("There is past course which displayed in list of destination courses.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		} else {
			System.out.println("Verify that past course is not displayed in list of destination courses.");
			ATUReports.add("Verify that past course is not displayed in list of destination courses.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		}

		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
	}
}
