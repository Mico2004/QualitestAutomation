package com.automation.main.delete_recordings;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

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
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;
import junit.framework.Assert;

public class TC15896DeleteALastRecording {

	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}

	public DeleteMenu delete;
	public MoveWindow move_Window;
	public LoginHelperPage tegrity;
	public CoursesHelperPage course;
	public RecordingHelperPage record;
	WebDriver driver;
	WebDriverWait wait;
	public static WebDriver thread_driver;
	CopyMenu copy;
	DesiredCapabilities capability;
	String os;

	public List<String> recording_for_delete;

	@AfterClass
	public void closeBroswer() {
		this.driver.quit();
	}

	@BeforeClass
	public void setup() {
	

			/// System.setProperty("webdriver.ie.driver",
			/// "src/test/resources/chromedriver.exe");

			driver = DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());
			/// ATUReports.add("selected browser type", LogAs.PASSED, new
			/// CaptureScreen(ScreenshotOf.DESKTOP));
			 
			recording_for_delete = new ArrayList<String>();
			tegrity = PageFactory.initElements(driver, LoginHelperPage.class);
			delete = PageFactory.initElements(driver, DeleteMenu.class);
			record = PageFactory.initElements(driver, RecordingHelperPage.class);
			copy = PageFactory.initElements(driver, CopyMenu.class);
			move_Window = PageFactory.initElements(driver, MoveWindow.class);
		
		
		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC15896DeleteALastRecording at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC15896DeleteALastRecording at " + DateToStr,
		 "Starting the test: TC15896DeleteALastRecording at " + DateToStr, LogAs.PASSED, null);

	}

	/*
	 * @Test public void testNewLogs() throws AWTException, IOException {
	 * 
	 * ATUReports.add("INfo Step", LogAs.INFO, new CaptureScreen(
	 * ScreenshotOf.BROWSER_PAGE)); ATUReports.add("Pass Step", LogAs.PASSED,
	 * new CaptureScreen( ScreenshotOf.DESKTOP)); /// WebElement element =
	 * driver /// .findElement(By.xpath("/html/body/div/h1/a")); ATUReports.add(
	 * "Warning Step", LogAs.WARNING, new CaptureScreen(element));
	 * ATUReports.add("Fail step","" ,"",LogAs.FAILED, new CaptureScreen(
	 * ScreenshotOf.DESKTOP)); }
	 */

	@Test (description= "TC 15896 Delete A Last Recording")
	public void test15896() throws InterruptedException {
		
		// 1.load page
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
		/// tegrity.loginCourses("SuperUser");// log in courses page

		// 2.login as instructor
		tegrity.loginCourses("User1");// log in courses page
		initializeCourseObject();
		
		// 3.Click "Recording Tasks" button (without selecting a recording)
		course.selectCourseThatStartingWith("Ab");
		Thread.sleep(2000);
		
		if(record.getNumberOfRecordings() > 1) {
			// Preset
			
			// 4. Delete all recording except the first
			record.checkAllCheckBox();
			record.clickElement(record.checkboxlist.get(0));
			
			// 5. Click on delete menu and delete all recording
			record.clickOnRecordingTaskThenDelete();
			delete.clickOnDeleteButton();
			
			// End preset
		}
		
		// 6. Select recording to delete
		String recording_name = record.getFirstRecordingTitle().replaceAll(" ", "");
		record.checkAllCheckBox();
		
		// 7. Select "Recording Tasks -> Delete"
		record.clickOnRecordingTaskThenDelete();
		
		// 8. Verify that only selected recording displayed in "List of Recordings"
		List<String> delete_recording_list = delete.getRecordingList();
		
		if((delete_recording_list.size() == 1) && (delete_recording_list.get(0).replaceAll(" ", "").equals(recording_name))) {
			System.out.println("Verfed that only selected recording displayed in List of Recordings");
			ATUReports.add("Verfed that only selected recording displayed in List of Recordings", "True.", "True.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Not verfed that only selected recording displayed in List of Recordings");
			ATUReports.add("Verfed that only selected recording displayed in List of Recordings", "True.", "False.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		
		// 9. Click "Delete" button
		delete.clickOnDeleteButton();
		
		// 10. Verify that "Recordings" tab is displayed without any recordings
		if(record.getNumberOfRecordings() == 0) {
			System.out.println("Verfied that Recordings tab is displayed without any recordings.");
			ATUReports.add("Verfied that Recordings tab is displayed without any recordings.", "True.", "True.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Not verfied that Recordings tab is displayed without any recordings.");
			ATUReports.add("Verfied that Recordings tab is displayed without any recordings.", "True.", "False.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
		
	}

	// description = "get courses list"
	public void initializeCourseObject() throws InterruptedException {

		course = PageFactory.initElements(driver, CoursesHelperPage.class);
		course.courses = course.getCoursesListFromElement(course.course_list);
	}

}