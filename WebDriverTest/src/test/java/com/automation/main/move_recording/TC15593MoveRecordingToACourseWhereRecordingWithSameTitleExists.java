package com.automation.main.move_recording;



import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import java.text.DateFormat;
import java.util.Date;

import com.automation.main.page_helpers.ConfirmationMenu;
import com.automation.main.page_helpers.CopyMenu;
import com.automation.main.page_helpers.CoursesHelperPage;
import com.automation.main.page_helpers.DeleteMenu;
import com.automation.main.page_helpers.LoginHelperPage;
import com.automation.main.page_helpers.MoveWindow;
import com.automation.main.page_helpers.PlayerPage;
import com.automation.main.page_helpers.RecordingHelperPage;
import com.automation.main.utilities.DriverSelector;
import com.sun.jna.win32.W32APITypeMapper;
import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;


@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC15593MoveRecordingToACourseWhereRecordingWithSameTitleExists {

	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}

	public LoginHelperPage tegrity;
	public CoursesHelperPage course;
	public RecordingHelperPage record;
	public ConfirmationMenu confirm_menu;
	public DeleteMenu delete_menu;
	public PlayerPage player_page;
	public MoveWindow move_window;
	WebDriver driver;
	WebDriverWait wait;
	public static WebDriver thread_driver;
	CopyMenu copy;
	String current_course;
	String target_course;
    DesiredCapabilities capability;
	@BeforeClass
	public void setup() {

		
		driver = DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());
		
		tegrity = PageFactory.initElements(driver, LoginHelperPage.class);

		record = PageFactory.initElements(driver, RecordingHelperPage.class);
		copy = PageFactory.initElements(driver, CopyMenu.class);
		
		confirm_menu = PageFactory.initElements(driver, ConfirmationMenu.class);
		delete_menu = PageFactory.initElements(driver, DeleteMenu.class);
		player_page = PageFactory.initElements(driver, PlayerPage.class);
		move_window = PageFactory.initElements(driver, MoveWindow.class);
		
		wait = new WebDriverWait(driver, 30);
		
		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC15593MoveRecordingToACourseWhereRecordingWithSameTitleExists at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC15593MoveRecordingToACourseWhereRecordingWithSameTitleExists at " + DateToStr, "TC15593MoveRecordingToACourseWhereRecordingWithSameTitleExists at " + DateToStr, LogAs.PASSED, null);	
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

	
	@Test (description="TC15593MoveRecordingToACourseWhereRecordingWithSameTitleExists")
	public void test15593() throws InterruptedException
	{
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);

		// 1. Login as INSTRUCTOR.
		tegrity.loginCourses("User1");// log in courses page
		initializeCourseObject();
		
		// 2. Verify that recordings with same title created both in source and destination courses.
		//PreCondition
		// 2.1. Select destination course.
		target_course = course.selectCourseThatStartingWith("abc");
		
		// 2.2. Delete all recordings.
		record.deleteAllRecordings(delete_menu);
		
		// 2.3. Click on "Courses" link at breadcrumbs.
		record.returnToCourseListPage();
		
		// 2.4. Select source course.
		current_course = course.selectCourseThatStartingWith("Ab");
		
		wait.until(ExpectedConditions.elementToBeClickable(record.checkbox));
		
		// 2.5. Select source recording.
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
		String init_first_recording_name = record.getFirstRecordingTitle();
		
		// 2.6. Select "Recording Tasks -> Copy" menu item.
		record.clickOnRecordingTaskThenCopy();
		
		// 2.7. Select destination course.
		copy.selectTargetCourseFromCourseList(target_course);
		
		// 2.8 Click "Copy Recording(s)" button.
		copy.clickOnCopyButton();
		Thread.sleep(2000);
		
		// 2.9. Click "OK" button.
		confirm_menu.clickOnOkButtonAfterConfirmCopyRecording();
		Thread.sleep(2000);
		
		
		// Wait until the copy status will disspear, becuase if we will move + copy it will fail to move
		record.checkStatusExistenceForMaxTTime(450);;
		
		// 2.10. Click on "Courses" link at breadcrumbs.
		record.returnToCourseListPage();
		//END of PreCondition
		
		// 3. Select source course.
		current_course = course.selectCourseThatStartingWith("Ab");
		
		wait.until(ExpectedConditions.elementToBeClickable(record.getCheckbox()));
		
		// 4. Select source recording.
		String first_recording_name = record.getFirstRecordingTitle();
		
		// can be improved by not just checking if the first recording with the same name
		// but if not look if there is recording in the list with that name as in precondition
		// and select it
		if (first_recording_name.equals(init_first_recording_name)) {
			System.out.println("Recording name is same as in precondition.");
			ATUReports.add("Recording name is same as in precondition.", LogAs.PASSED, null);
			Assert.assertTrue(true);
			
			//so select this recoding
			record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
		} else {
			System.out.println("Recording name is not same as in precondition.");
			ATUReports.add("Recording name is not same as in precondition.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		
		// 5. Select "Recording Tasks -> Move" menu item.
		record.clickOnRecordingTaskThenMove();
		
		// 6. Select destination destination.
		copy.selectTargetCourseFromCourseList(target_course);
		
		// 7. Click "Move Recording(s)" button.
		move_window.clickOnMoveRecordings();
		Thread.sleep(2000);
		
		// 8. Click "OK" button.
		confirm_menu.clickOnOkButtonAfterConfirmMoveRecording();
		Thread.sleep(2000);
		
		// 8.1. Message box is closed
		if(confirm_menu.isConfirmationMenuClosed()) {
			System.out.println("Message box is closed");
			ATUReports.add("Message box is closed", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Message box is not closed");
			ATUReports.add("Message box is not closed", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		
		// 8.2. "Copy" window is closed
		if(copy.isCopyMenuClosed()) {
			System.out.println("Copy window is closed");
			ATUReports.add("Copy window is closed", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Copy window is not closed");
			ATUReports.add("Copy window is not closed", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		
		// 9. Source recording has a status "Being copying from".
		record.checkRecordingInIndexIStatus(1, "Being moved from");
		
		// TODO: improve it by new function that checking target by index recording
		// 10. After copying is finished, recording's status will disappear.
		record.waitUntilFirstRecordingBeingMovedFromStatusDissaper();
		
		// 11. After moving is finished, recording will disappear from recordings list.
		List<String> current_recording_list = record.getCourseRecordingList();
		
		if(current_recording_list.contains(first_recording_name)) {
			System.out.println("Recording not disappear from recordings list");
			ATUReports.add("Recording not disappear from recordings list", LogAs.FAILED, null);
			Assert.assertTrue(false);
		} else {
			System.out.println("Recording disappear from recordings list");
			ATUReports.add("Recording disappear from recordings list", LogAs.PASSED, null);
			Assert.assertTrue(true);
		}
		
		// 12. Click on "Courses" link at breadcrumbs.
		record.returnToCourseListPage();
		
		// 13. Select destination course.
		target_course = course.selectCourseThatStartingWith("abc");
		
		// 14. Verify that copied recording has a title with number mark ("recordingname (number)").
		List<String> recording_list = record.getCourseRecordingList();
		
		
		int i = 0;
		boolean is_found = false;
		for(String recording: recording_list) {
			i++;
			
			if (recording.startsWith(first_recording_name)) {
					if (recording.endsWith("(1)")) {
						is_found = true;
						break;
					}
			}
		}
		
		if (is_found) {
			System.out.println("Verified that copied recording has the title with number mark");
			ATUReports.add("Verified that copied recording has the title with number mark", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Not verified that copied recording has the title with number mark");
			ATUReports.add("Not verified that copied recording has the title with number mark", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
		
		// 15. Click on new recording's title.
		record.clickOnRecordingTitleInIndex(2);
		
		// 16. Click on any chapter. 
		//record.clickOnTheFirstCaptherWithOutTheExpand();	
		driver.findElement(By.cssSelector(".panel-body>.video-outer.ng-scope>.video-wrap")).click();
		
		//List <WebElement> panels = driver.findElements(By.cssSelector(".panel-body>.video-outer.ng-scope>.video-wrap"));
		//panels.get(new_recording_index).click();
		
		//17. "Tegrity Player" is displayed and Recording is playing correctly.
		//TODO: add WebDriverWait
		//Thread.sleep(10000);
		
		//WebDriverWait w = new WebDriverWait(driver, 10);
		
		//Thread.sleep(10000);

		player_page.verifyTimeBufferStatusForXSec(5);
		
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
	}
}
