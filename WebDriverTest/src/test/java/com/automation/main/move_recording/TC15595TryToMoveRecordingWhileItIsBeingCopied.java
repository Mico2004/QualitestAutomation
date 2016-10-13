package com.automation.main.move_recording;

// precondition course in index 3 and 4 is empty of not share recording with name as in second course first recording


import java.util.List;


import org.openqa.selenium.WebDriver;
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
import com.automation.main.page_helpers.MoveWindow;
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
public class TC15595TryToMoveRecordingWhileItIsBeingCopied {

	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}

	public LoginHelperPage tegrity;
	public CoursesHelperPage course;
	public RecordingHelperPage record;
	public ConfirmationMenu confirm_menu;
	public DeleteMenu delete_menu;
	public MoveWindow move_window;
	WebDriver driver;
	WebDriverWait wait;
	public static WebDriver thread_driver;
	CopyMenu copy;
	String current_course;
	String targetCourse;
	String clickedRecording;

	@BeforeClass
	public void setup() {
		driver = DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());
		
		ATUReports.add("selected browser type", LogAs.PASSED, new CaptureScreen( ScreenshotOf.DESKTOP));

		//ATUReports.setWebDriver(driver);
		//ATUReports.add("set driver", true);
		tegrity = PageFactory.initElements(driver, LoginHelperPage.class);
        course=PageFactory.initElements(driver, CoursesHelperPage.class);
		record = PageFactory.initElements(driver, RecordingHelperPage.class);
		copy = PageFactory.initElements(driver, CopyMenu.class);
		
		confirm_menu = PageFactory.initElements(driver, ConfirmationMenu.class);
		
		delete_menu = PageFactory.initElements(driver, DeleteMenu.class);
		
		move_window = PageFactory.initElements(driver, MoveWindow.class);
	 	
		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC15595TryToMoveRecordingWhileItIsBeingCopied at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC15595TryToMoveRecordingWhileItIsBeingCopied at " + DateToStr, "Starting the test: TC15595TryToMoveRecordingWhileItIsBeingCopied at " + DateToStr, LogAs.PASSED, null);	
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

	
	@Test(description = "TC 15595 Try To Move Recording While It Is Being Copied")
	public void test15595() throws InterruptedException
	{
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);

		// 1. Login as INSTRUCTOR.
		tegrity.loginCourses("User1");// log in courses page
		initializeCourseObject();
		
		
		// Preset delete all recordings in destinations
		// Delete all recordings.
		String first_target_course = course.selectCourseThatStartingWith("abc");
		record.deleteAllRecordings(delete_menu);
		record.returnToCourseListPage();
		
		
		String second_target_course = course.selectCourseThatStartingWith("ad");
		record.deleteAllRecordings(delete_menu);
		record.returnToCourseListPage();
		
		// 2. Select course.
		current_course = course.selectCourseThatStartingWith("Ab");
		System.out.println("Current course: " + current_course);
		//course.selectCourse(record);
		
		// 3. Select recording.
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
		String selected_recording_title = record.getFirstRecordingTitle();
		
		// 4. Select "Recording Tasks -> Copy"
		record.clickOnRecordingTaskThenCopy();
		
		Thread.sleep(1000);
		
		// save course list, we will copy in first time to course in 3rd index
		// and in second time to course in 4th index
		//List<String> current_copy_menu_course_list = copy.getCourseList(); 
		//int number_of_courses_in_course_list = current_copy_menu_course_list.size();
		//String first_target_course = current_copy_menu_course_list.get(2);
		//String second_target_course = current_copy_menu_course_list.get(3);
		
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
		
		Thread.sleep(1000);
		
		// 8. Click "OK" button.
		confirm_menu.clickOnOkButtonAfterConfirmCopyRecording();
		
		// 9. The source recording has status "Being copied from".
		record.checkRecordingInIndexIStatus(1, "Being copied from");
		
		// 10. While recording has a "Being copying from", select "Recording Tasks -> Copy" menu item.
		//record.clickOnRecordingTaskThenCopy();
		record.clickOnRecordingTaskThenMove();
		
		// 11. click on the OK Error after failed to move
		confirm_menu.clickOnOkButtonAfterCannotMoveInProcessOrFailRecordings();
		
		// 12. check that we finish moving the record to the destination 
		record.checkStatusExistenceForMaxTTime(360);
		
//		
//		// 11. Select destination course.
//		copy.selectTargetCourseFromCourseList(first_target_course);
//
//		// 12. Selected course is marked.
//		copy.isTargetCourseSelected(second_target_course);
//		
//		// 13. Click "Move Recording(s)" button.
//		move_window.clickOnMoveRecordings();
//		
//		// 14. Click "OK" button.
//		confirm_menu.clickOnOkButtonAfterConfirmMoveRecording();
//		
//		// 15. The source recording has status "Being moved from".
//		record.checkRecordingInIndexIStatus(1, "Being moved from");
//		
//		record.checkThatRecordingStatusTargetIndexIsNotXWithTimeout(1, "Being moved from", 30);
//		
//		// 16. When "Move" process is done, verify that source recoding isn't damaged
//		// 16.1. Recording is not displayed in "Recordings" tab
//		if (selected_recording_title.equals(record.getFirstRecordingTitle())) {
//			System.out.println("Recording is displayed in recordings tab");
//			ATUReports.add("Recording is displayed in recordings tab", LogAs.FAILED, null);
//			Assert.assertTrue(false);
//		} else {
//			System.out.println("Recording is not displayed in recordings tab");
//			ATUReports.add("Recording is not displayed in recordings tab", LogAs.PASSED, null);
//			Assert.assertTrue(true);
//		}
		
//		// 16.2. Recording doesn't have a "Failed" status
//		record.checkThatRecordingStatusTargetIndexIsNotXWithTimeout(1, "Failed", 1);
		
		// 17. Click "Courses" link in the breadcrumbs
		record.returnToCourseListPage();
		Thread.sleep(2000);
		
		
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
			Assert.assertTrue(true);
		} else {
			System.out.println("Recording is not displayed in recordings tab");
			ATUReports.add("Recording is not displayed in recordings tab", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		
		// 19.2. Recording doesn't have a "Failed" status
		record.checkThatRecordingStatusTargetIndexIsNotXWithTimeout(i, "Failed", 1);
		
		// 20. Click "Courses" link in the breadcrumbs
//		record.returnToCourseListPage();
//		
//		Thread.sleep(2000);
//		
//		// 21. Select second destination course
//		course.clickOnTargetCourseName(second_target_course);
//				
//		// 22. Verify that recording is copied successfully
//		// 23.1. Recording is displayed in "Recordings" tab
//		
//		List<String> recordings_of_second_target_course = record.getCourseRecordingList();
//		
//		i = 0;
//		is_recording_found = false;
//		for(String recording: recordings_of_second_target_course) {
//			i++;
//			
//			if (recording.equals(selected_recording_title)) {
//				is_recording_found = true;
//				break;
//			}
//		}
//		
//		if (is_recording_found) {
//			System.out.println("Recording is displayed in recordings tab");
//			ATUReports.add("Recording is displayed in recordings tab", LogAs.PASSED, null);
//			Assert.assertTrue(true);
//		} else {
//			System.out.println("Recording is not displayed in recordings tab");
//			ATUReports.add("Recording is not displayed in recordings tab", LogAs.FAILED, null);
//			Assert.assertTrue(false);
//		}
//		
//		// 23.2. Recording doesn't have a "Failed" status
//		record.checkThatRecordingStatusTargetIndexIsNotXWithTimeout(i, "Failed", 1);
		
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
		
		
	}
}
