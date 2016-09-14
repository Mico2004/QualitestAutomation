package com.automation.main;



import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
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
public class TC15627DeleteARegularRecordingAsInstructor {

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

		driver = DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());
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
		 System.out.println("Starting the test: TC15627DeleteARegularRecordingAsInstructor at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC15627DeleteARegularRecordingAsInstructor at " + DateToStr, "Starting the test: TC15627DeleteARegularRecordingAsInstructor at " + DateToStr, LogAs.PASSED, null);	
	}
	
	
	@AfterClass
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
		tegrity.loginCourses("User1");// log in courses page
		initializeCourseObject();
		
		// 2. Select course.
		currentCourse = course.selectFirstCourse(record);
		System.out.println("Current course: " + currentCourse);
		//course.selectCourse(record);
		
		// 3. check that the first record don't have any status 
		record.checkThatRecordingStatusTargetIndexIsEmpty(1,120);
	
		// 3.1 Select recording.
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
		String selected_recording_title = record.getFirstRecordingTitle().replaceAll(" ", "");
		//List<String> initial_recording_list = record.getCourseRecordingList();
		
		// 4. Select "Recording Tasks -> Delete".
		record.clickOnRecordingTaskThenDelete();
		
	
		// 5. Verify that only selected recording displayed in "List of Recordings".
		List<String> delete_recording_list = delete_menu.getRecordingList();
		
		if(delete_recording_list.size() == 1) {
			if(delete_recording_list.get(0).replaceAll(" ", "").equals(selected_recording_title)) {
				System.out.println("Verified that only selected recording displayed in list of recordings.");
				ATUReports.add("Verified that only selected recording displayed in list of recordings.", LogAs.PASSED, null);
				Assert.assertTrue(true);
			} else {
				System.out.println("Verified that not selected recording displayed in list of recordings.");
				ATUReports.add("Verified that not selected recording displayed in list of recordings.", LogAs.FAILED, null);
				Assert.assertTrue(false);
			}
  		} else {
			System.out.println("Delete recording list not contain just one recording. It contains: " + delete_recording_list.size());
			ATUReports.add("Delete recording list not contain just one recording. It contains: " + delete_recording_list.size(), LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		
		// 6. Click "Delete" button
		delete_menu.clickOnDeleteButton();
			
		Thread.sleep(1000);
		
		// 7. "Delete" window is closed.
		boolean is_delete_window_closed = delete_menu.isDeleteMenuClose();
	
		if(is_delete_window_closed) {
			System.out.println("Delete window is closed.");
			ATUReports.add("Delete window is closed.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Delete window not closed.");
			ATUReports.add("Delete window not closed.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		
		// 8. Verify that selected recording is deleted.
		List <String> current_recording_list = record.getCourseRecordingList();
		
		if(current_recording_list.contains(selected_recording_title)) {
			System.out.println("Verified that selected recording is not deleted.");
			ATUReports.add("Verified that selected recording is not deleted.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		} else {
			System.out.println("Verified that selected recording is deleted.");
			ATUReports.add("Verified that selected recording is deleted.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		}
		
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);


	}
}
