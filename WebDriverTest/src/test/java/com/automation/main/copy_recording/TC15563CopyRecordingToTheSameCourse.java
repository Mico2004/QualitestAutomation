package com.automation.main.copy_recording;

// precondition no double naming 


import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;import com.automation.main.page_helpers.Page;
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
import com.automation.main.page_helpers.CopyMenu;
import com.automation.main.page_helpers.CoursesHelperPage;
import com.automation.main.page_helpers.DeleteMenu;
import com.automation.main.page_helpers.LoginHelperPage;
import com.automation.main.page_helpers.PlayerPage;
import com.automation.main.page_helpers.RecordingHelperPage;
import com.automation.main.utilities.DriverSelector;

import java.text.DateFormat;
import java.util.Date;
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
public class TC15563CopyRecordingToTheSameCourse {

	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}

	public LoginHelperPage tegrity;
	public CoursesHelperPage course;
	public RecordingHelperPage record;
	public ConfirmationMenu confirm_menu;
	public PlayerPage player_page;
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

		
//		System.setProperty("webdriver.ie.driver", "src/test/resources/IEDriverServer.exe");
//			capability=DesiredCapabilities.internetExplorer();
//			capability.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING,false);
//			
//		driver=new InternetExplorerDriver(capability);
//		ATUReports.add("selected browser type", LogAs.PASSED, new CaptureScreen( ScreenshotOf.DESKTOP));
		driver = DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());
		ATUReports.add("selected browser type", LogAs.PASSED, new CaptureScreen( ScreenshotOf.DESKTOP));

		
		//ATUReports.setWebDriver(driver);
		//ATUReports.add("set driver", true);
		tegrity = PageFactory.initElements(driver, LoginHelperPage.class);

		record = PageFactory.initElements(driver, RecordingHelperPage.class);
		copy = PageFactory.initElements(driver, CopyMenu.class);
		
		confirm_menu = PageFactory.initElements(driver, ConfirmationMenu.class);
		player_page = PageFactory.initElements(driver, PlayerPage.class);
		delete_menu = PageFactory.initElements(driver, DeleteMenu.class);
		
		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC15563CopyRecordingToTheSameCourse at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC15563CopyRecordingToTheSameCourse at " + DateToStr, "Starting the test: TC15563CopyRecordingToTheSameCourse at " + DateToStr, LogAs.PASSED, null);	
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

	
	@Test(description = "TC 15563 Copy Recording To The Same Course")
	public void test15563() throws InterruptedException
	{
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);

		// 1. Login as INSTRUCTOR.
		tegrity.loginCourses("User1");// log in courses page
		initializeCourseObject();
		
		// PreSet 
		// user - use dynamic users (1) and course(2) delete all recordings from course (2) and move one recording to the course
		// enter source course and delete all recordings
		String copy_to = course.selectCourseThatStartingWith("abc");
		record.deleteAllRecordings(delete_menu);
		
		// come back to courses, go to Ab course, and copy one recording to abc then return to courses page list
		record.returnToCourseListPage();
		course.selectCourseThatStartingWith("Ab");
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
		record.clickOnRecordingTaskThenCopy();
		copy.selectTargetCourseFromCourseList(copy_to);
		copy.clickOnCopyButton();
		Thread.sleep(Page.TIMEOUT_TINY);
		confirm_menu.clickOnOkButtonAfterConfirmCopyRecording();
		record.checkStatusExistenceForMaxTTime(360);
		record.returnToCourseListPage();
		
		
		// 2. Select course.
		currentCourse = course.selectCourseThatStartingWith("abc");
		System.out.println("Current course: " + currentCourse);
		//course.selectCourse(record);
		
		//3. Select recording.
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
		
		String selected_recording_title = record.getFirstRecordingTitle();
		
		//4. Select "Recording Tasks -> Copy"
		record.clickOnRecordingTaskThenCopy();
		
		//5. Select the same course as a destination.
		copy.selectTargetCourseFromCourseList(currentCourse);
		
		
		//6. Click "Copy Recording(s)" button without selecting a course
		copy.clickOnCopyButton();
		
		Thread.sleep(Page.TIMEOUT_TINY);
		
		//7. Click "OK" button
		confirm_menu.clickOnOkButtonAfterConfirmCopyRecording();
		
		
		// 8. Message window is closed.
		boolean is_closed = confirm_menu.isConfirmationMenuClosed();
		
		if (is_closed) {
			System.out.println("Message window is closed.");
			ATUReports.add("Message window.", "Message window is closed.", "Message window is closed.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Message window is not closed.");
			ATUReports.add("Message window.", "Message window is closed.", "Message window is not closed.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		
		// 9. Copy window is closed.
		is_closed = copy.isCopyMenuClosed();
		
		if (is_closed) {
			System.out.println("Copy window is closed.");
			ATUReports.add("Copy window.", "Copy window is closed.", "Copy window is closed.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Copy window is not closed.");
			ATUReports.add("Copy window.", "Copy window is closed.", "Copy window is not closed.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
	
		
		
		//10. Source recording has a status "Being copying from".
		//11. New recording is displayed.
		//12. New recording's has a status "Moving/Copying".
		//13. New recording's title is displayed as follows: "recordingname (number)".
		
		
		String[] splited_selected_recording_title = selected_recording_title.split(" ");
		String last_word_of_splited_selected_recording_title = splited_selected_recording_title[splited_selected_recording_title.length - 1];
		
		int recording_postfix_to_check = 1;
		String sub_selected_recording_title = selected_recording_title;
		if (last_word_of_splited_selected_recording_title.matches("[(][0-9]+[)]")) {
			String last_word_num = (String) last_word_of_splited_selected_recording_title.subSequence(1, last_word_of_splited_selected_recording_title.length() - 1);
			recording_postfix_to_check = Integer.parseInt(last_word_num) + 1;
			System.out.println(recording_postfix_to_check);
			sub_selected_recording_title = selected_recording_title.substring(0, selected_recording_title.length() - 3);
			System.out.println(sub_selected_recording_title);
		} 
		
		
		
		List <String> all_recordings_list = record.getCourseRecordingList();
		
		int i = 0;
		boolean is_new_recording_displayed = false;
		int index_copied_status = 0;
		int index_moving_copying_status = 0;
		for (String recording : all_recordings_list) {
			i++;
			if (recording.contains(sub_selected_recording_title)) {
				if(recording.equals(selected_recording_title)) {
					//check:
					//"Being copying from"
					record.checkRecordingInIndexIStatus(i, "Being copied from");
					index_copied_status = i;
				} else {
					//check:
					//New recording is displayed.
					//"Moving/Copying"
					//(number)
					
					
//					if(recording.contains("(" + Integer.toString(recording_postfix_to_check) +")")) {
					if(recording.contains("(" + Integer.toString(recording_postfix_to_check) +")")) {
						is_new_recording_displayed = true;
						record.checkRecordingInIndexIStatus(i, "Moving/Copying");
						index_moving_copying_status = i;
						
						System.out.println("New recording contain the number (" + Integer.toString(recording_postfix_to_check) +") in its name.");
						ATUReports.add("New recording contain the number (" + Integer.toString(recording_postfix_to_check) +") in its name.", LogAs.PASSED, null);
						Assert.assertTrue(true);
					} 
//					else {
//						System.out.println("New recording not contain the number (1) in its name.");
//						ATUReports.add("New recording not contain the number (1) in its name.", LogAs.FAILED, null);
//						Assert.assertTrue(false);
//					}
				}
			}
		}
		
		if (!is_new_recording_displayed) {
			System.out.println("New copied recording is not displayed.");
			ATUReports.add("New copied recording.", "New copied recording is displayed.", "New copied recording is not displayed.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		} else {
			System.out.println("New copied recording is displayed.");
			ATUReports.add("New copied recording.", "New copied recording is displayed.", "New copied recording is displayed.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		}
		
		
		
		
		//14. After copying is finished, statuses at both recordings will disappear.
			record.checkThatRecordingStatusTargetIndexIsNotXWithTimeout(index_copied_status, "Being copied from", 300);
			record.checkThatRecordingStatusTargetIndexIsNotXWithTimeout(index_moving_copying_status, "Moving/Copying", 60);
		
		//15. Verify that source recording is not removed from course.
		all_recordings_list = record.getCourseRecordingList();
		
		boolean is_original_recording_exist = false;
		int new_recording_index = 0;
		i = 0;
		for (String recording : all_recordings_list) {
			i++;
			if(recording.startsWith(sub_selected_recording_title)) {
				if(recording.equals(selected_recording_title)) {
					is_original_recording_exist = true;
				} else {
					if(recording.contains("(" + Integer.toString(recording_postfix_to_check) +")")) {
						new_recording_index = i;
					}
				}
			}
		}
		
		if (!is_original_recording_exist) {
			System.out.println("Original recording is not displayed.");
			ATUReports.add("Original recording.", "Original recording is displayed.", "Original recording is not displayed.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		} else {
			System.out.println("Original recording is displayed.");
			ATUReports.add("Original recording.", "Original recording is displayed.", "Original recording is displayed.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		}
		
		//16. Click on new recording's title.
		record.clickOnRecordingTitleInIndex(new_recording_index);
		
		
		//17. Click on any chapter.
		Thread.sleep(Page.TIMEOUT_TINY);
		
		driver.findElement(By.cssSelector(".panel-body>.video-outer.ng-scope>.video-wrap")).click();
		
		//List <WebElement> panels = driver.findElements(By.cssSelector(".panel-body>.video-outer.ng-scope>.video-wrap"));
		
		//panels.get(new_recording_index).click();
		
		//TODO: add WebDriverWait
		//Thread.sleep(Page.TIMEOUT_TINY);
		
		//18. Recording is displayed and playing correctly.
		player_page.verifyTimeBufferStatusForXSec(10);
		
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
	}
}
