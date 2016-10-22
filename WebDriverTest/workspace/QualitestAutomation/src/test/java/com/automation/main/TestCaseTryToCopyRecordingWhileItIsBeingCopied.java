package com.automation.main;

// precondition course in index 3 and 4 is empty of not share recording with name as in second course first recording

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
public class TestCaseTryToCopyRecordingWhileItIsBeingCopied {

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
		// 1. Login as INSTRUCTOR.
		tegrity.loginCourses("Instructor");// log in courses page
		initializeCourseObject();
		
		// 2. Select course.
		current_course = course.selectSecondCourse(record);
		System.out.println("Current course: " + current_course);
		//course.selectCourse(record);
		
		// 3. Select recording.
		record.selectFirstCheckbox();
		String selected_recording_title = record.getFirstRecordingTitle();
		
		// 4. Select "Recording Tasks -> Copy"
		record.clickOnRecordingTaskThenCopy();
		
		Thread.sleep(Page.TIMEOUT_TINY);
		
		// save course list, we will copy in first time to course in 3rd index
		// and in second time to course in 4th index
		List<String> current_copy_menu_course_list = copy.getCourseList(); 
		int number_of_courses_in_course_list = current_copy_menu_course_list.size();
		String first_target_course = current_copy_menu_course_list.get(2);
		String second_target_course = current_copy_menu_course_list.get(3);
		
//		// save course list, we will copy in first time to course in first random index
//		// and in second time to course in second random index
//		List<String> current_copy_menu_course_list = copy.getCourseList(); 
//		int number_of_courses_in_course_list = current_copy_menu_course_list.size();	
//		Random rand_num = new Random();
//		int first_rand_num = rand_num.nextInt(number_of_courses_in_course_list);
//		int second_rand_num = rand_num.nextInt(number_of_courses_in_course_list);	
//		String first_target_course = current_copy_menu_course_list.get(first_rand_num);
//		String second_target_course = current_copy_menu_course_list.get(second_rand_num);
				
		
		// 5. Select destination course.
		copy.selectTargetCourseFromCourseList(first_target_course);
		
		// 6. Selected course is marked.
		copy.isTargetCourseSelected(first_target_course);
		
		// 7. Click "Copy Recording(s)" button.
		copy.clickOnCopyButton();
		
		Thread.sleep(Page.TIMEOUT_TINY);
		
		// 8. Click "OK" button.
		confirm_menu.clickOnOkButtonAfterConfirmCopyRecording();
		
		// 9. The source recording has status "Being copied from".
		record.checkRecordingInIndexIStatus(1, "Being copied from");
		
		// 10. While recording has a "Being copying from", select "Recording Tasks -> Copy" menu item.
		record.clickOnRecordingTaskThenCopy();
		
		// 11. Select destination course.
		copy.selectTargetCourseFromCourseList(second_target_course);

		// 12. Selected course is marked.
		copy.isTargetCourseSelected(second_target_course);
		
		// 13. Click "Copy Recording(s)" button.
		copy.clickOnCopyButton();
		
		// 14. Click "OK" button.
		confirm_menu.clickOnOkButtonAfterConfirmCopyRecording();
		
		// 15. The source recording has status "Being copied from".
		record.checkRecordingInIndexIStatus(1, "Being copied from");
		
		record.checkThatRecordingStatusTargetIndexIsNotXWithTimeout(1, "Being copied from", 30);
		
		// 16. When "Copy" process is done, verify that source recoding isn't damaged
		// 16.1. Recording is displayed in "Recordings" tab
		if (selected_recording_title.equals(record.getFirstRecordingTitle())) {
			System.out.println("Recording is displayed in recordings tab");
			ATUReports.add("Recording is displayed in recordings tab", LogAs.PASSED, null);
			Assert.assertEquals(selected_recording_title, record.getFirstRecordingTitle());
		} else {
			System.out.println("Recording is not displayed in recordings tab");
			ATUReports.add("Recording is not displayed in recordings tab", LogAs.FAILED, null);
			Assert.assertEquals(selected_recording_title, record.getFirstRecordingTitle());
		}
		
		// 16.2. Recording doesn't have a "Failed" status
		record.checkThatRecordingStatusTargetIndexIsNotXWithTimeout(1, "Failed", 1);
		
		// 17. Click "Courses" link in the breadcrumbs
		record.returnToCourseListPage();
		
		// 18. Select first destination course
		course.clickOnTargetCourseName(first_target_course);
		
		// 19. Verify that recording is copied successfully
		// 19.1. Recording is displayed in "Recordings" tab
		
		List<String> recordings_of_first_target_course = record.getCourseRecordingList();
		
		int i = 0;
		boolean is_recording_found = false;
		for(String recording: recordings_of_first_target_course) {
			i++;
			
			if (recording.equals(selected_recording_title)) {
				is_recording_found = true;
				break;
			}
		}
		
		if (is_recording_found) {
			System.out.println("Recording is displayed in recordings tab");
			ATUReports.add("Recording is displayed in recordings tab", LogAs.PASSED, null);
			Assert.assertEquals(selected_recording_title, record.getFirstRecordingTitle());
		} else {
			System.out.println("Recording is not displayed in recordings tab");
			ATUReports.add("Recording is not displayed in recordings tab", LogAs.FAILED, null);
			Assert.assertEquals(selected_recording_title, record.getFirstRecordingTitle());
		}
		
		// 19.2. Recording doesn't have a "Failed" status
		record.checkThatRecordingStatusTargetIndexIsNotXWithTimeout(i, "Failed", 1);
		
		// 20. Click "Courses" link in the breadcrumbs
		record.returnToCourseListPage();
			
		// 21. Select second destination course
		course.clickOnTargetCourseName(second_target_course);
				
		// 22. Verify that recording is copied successfully
		// 23.1. Recording is displayed in "Recordings" tab
		
		List<String> recordings_of_second_target_course = record.getCourseRecordingList();
		
		i = 0;
		is_recording_found = false;
		for(String recording: recordings_of_second_target_course) {
			i++;
			
			if (recording.equals(selected_recording_title)) {
				is_recording_found = true;
				break;
			}
		}
		
		if (is_recording_found) {
			System.out.println("Recording is displayed in recordings tab");
			ATUReports.add("Recording is displayed in recordings tab", LogAs.PASSED, null);
			Assert.assertEquals(selected_recording_title, record.getFirstRecordingTitle());
		} else {
			System.out.println("Recording is not displayed in recordings tab");
			ATUReports.add("Recording is not displayed in recordings tab", LogAs.FAILED, null);
			Assert.assertEquals(selected_recording_title, record.getFirstRecordingTitle());
		}
		
		// 23.2. Recording doesn't have a "Failed" status
		record.checkThatRecordingStatusTargetIndexIsNotXWithTimeout(i, "Failed", 1);
		driver.quit();
		}
}
