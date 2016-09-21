package com.automation.main.copy_recording;



import java.util.List;
import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;
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
import com.automation.main.page_helpers.PlayerPage;
import com.automation.main.page_helpers.RecordingHelperPage;
import com.automation.main.utilities.DriverSelector;
import com.sun.jna.win32.W32APITypeMapper;
import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import atu.testng.reports.utils.Utils;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;


@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC24764CopyAStudentRecordingToACourseWhereRecordingWithSameTitleExists {

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
		ATUReports.add("selected browser type", LogAs.PASSED, new CaptureScreen( ScreenshotOf.DESKTOP));

	
		//ATUReports.setWebDriver(driver);
		//ATUReports.add("set driver", true);
		tegrity = PageFactory.initElements(driver, LoginHelperPage.class);

		record = PageFactory.initElements(driver, RecordingHelperPage.class);
		copy = PageFactory.initElements(driver, CopyMenu.class);
		
		confirm_menu = PageFactory.initElements(driver, ConfirmationMenu.class);
		delete_menu = PageFactory.initElements(driver, DeleteMenu.class);
		player_page = PageFactory.initElements(driver, PlayerPage.class);
		
		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC24764CopyAStudentRecordingToACourseWhereRecordingWithSameTitleExists at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC24764CopyAStudentRecordingToACourseWhereRecordingWithSameTitleExists at " + DateToStr,
		 "Starting the test: TC24764CopyAStudentRecordingToACourseWhereRecordingWithSameTitleExists at " + DateToStr, LogAs.PASSED, null);
	}
	
	@AfterClass
	public void closeBroswer() {
		this.driver.quit();
	}


	
	// description = "get courses list"
	public void initializeCourseObject() throws InterruptedException {

		course = PageFactory.initElements(driver, CoursesHelperPage.class);
		course.courses = course.getStringFromElement(course.course_list);
	}

	@Test(description = "TC 24764 Copy A Student Recording To ACourse Where Recording With Same Title Exists")
	public void test24764() throws InterruptedException
	{
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);

		// 1. Verify that recordings with same title created both in source and destination courses.
		tegrity.loginCourses("User1");// log in courses page
		initializeCourseObject();
		
		// Select destination course.
		target_course = course.selectCourseThatStartingWith("abc");
		record.returnToCourseListPage();
		
		// Delete all recordings.
		course.deleteAllRecordingsInCourseStartWith("abc", 2, record, delete_menu);
		
		// Select source course.
		current_course = course.selectCourseThatStartingWith("Ab");
		
		// Click student recording
		record.clickOnStudentRecordingsTab();
		Thread.sleep(2000);
		
		// Select source recording.
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
		String init_first_recording_name = record.getFirstRecordingTitle();
		
		// Select "Recording Tasks -> Copy" menu item.
		record.clickOnRecordingTaskThenCopy();
		
		// Select destination course.
		copy.selectTargetCourseFromCourseList(target_course);
		
		// Click "Copy Recording(s)" button.
		copy.clickOnCopyButton();
		Thread.sleep(2000);
		
		// Click "OK" button.
		confirm_menu.clickOnOkButtonAfterConfirmCopyRecording();
		Thread.sleep(2000);
		
		// Click on "Courses" link at breadcrumbs.
		record.returnToCourseListPage();
		//END of PreCondition
		
		// 2. Login as INSTRUCTOR - Already login.
		
		// 3. Select source course.
		current_course = course.selectCourseThatStartingWith("Ab");
		
		// 4. Click the 'Student Recording' tab.
		record.clickOnStudentRecordingsTab();
		Thread.sleep(1000);
		
		// 5. Select source recording.
		String first_recording_name = record.getFirstRecordingTitle();
		
		// can be improved by not just checking if the first recording with the same name
		// but if not look if there is recording in the list with that name as in precondition
		// and select it
		if (first_recording_name.equals(init_first_recording_name)) {
			System.out.println("Recording name is same as in precondition.");
			ATUReports.add("Recording name.", "Recording name is same as in precondition.", "Recording name is same as in precondition.", LogAs.PASSED, null);
			Assert.assertTrue(true);
			
			//so select this recoding
			record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
		} else {
			System.out.println("Recording name is not same as in precondition.");
			ATUReports.add("Recording name.", "Recording name is same as in precondition.", "Recording name is not same as in precondition.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		
		// 6. Select "Recording Tasks -> Copy" menu item.
		record.clickOnRecordingTaskThenCopy();
		
		// 7. Select destination destination.
		copy.selectTargetCourseFromCourseList(target_course);
		
		// 8. Click "Copy Recording(s)" button.
		copy.clickOnCopyButton();
		Thread.sleep(2000);
		
		// 9. Message box "Recording has been queued for copy" is displayed.
		// 10. Click "OK" button.
		confirm_menu.clickOnOkButtonAfterConfirmCopyRecording();
		Thread.sleep(2000);
		
		// 11. Message box is closed.
		confirm_menu.verifyConfirmWindowIsClosed();
		
		
		// 12. "Copy" window is closed.	
		copy.verifyThatCopyMenuClose();
		
		// 13. Source recording has a status "Being copying from".
		record.checkRecordingInIndexIStatus(1, "Being copied from");
//		record.verifyTargetRecordingHaveTargetStatus(first_recording_name, "Being copied from");
		
		// TODO: improve it by new function that checking target by index recording
		// 14. After copying is finished, recording's status will disappear.
		record.waitUntilFirstRecordingBeingCopiedFromStatusDissaper();
//		record.checkThatRecordingStatusTargetIndexIsNotXWithTimeout(1, "Being copied from", 180);
		
		// 15. Click on "Courses" link at breadcrumbs.
		record.returnToCourseListPage();
		
		// 16. Select destination course.
		target_course = course.selectCourseThatStartingWith("abc");
		
		// 17. Click the 'Student Recording' tab.
		record.clickOnStudentRecordingsTab();
		
		Thread.sleep(1000);
		
		// 18. Verify that copied recording has a title with number mark.
		List<String> recording_list = record.getCourseRecordingList();
		
		int i = 0;
		boolean is_found = false;
		for(String recording: recording_list) {
			i++;
			
			if (recording.startsWith(first_recording_name)) {
				if (recording.equals(first_recording_name + " (1)")) {
						is_found = true;
						break;
					}
			}
		}
		
		if (is_found) {
			System.out.println("Verified that copied recording has the title with number mark");
			ATUReports.add("Copied recording name.", "Verified that copied recording has the title with number mark.", "Verified that copied recording has the title with number mark.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Not verified that copied recording has the title with number mark");
			ATUReports.add("Copied recording name.", "Verified that copied recording has the title with number mark.", "Not verified that copied recording has the title with number mark.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		
		// 19. Click on new recording's title.
		record.clickOnRecordingTitleInIndex(i);
		
		// 20. Click on any chapter.
		Thread.sleep(2000);
		driver.findElement(By.cssSelector(".panel-body>.video-outer.ng-scope>.video-wrap")).click();
		
		// 21. "Tegrity Player" is displayed - Recording is playing correctly.
		player_page.verifyTimeBufferStatusForXSec(10);
		
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
	}
}
