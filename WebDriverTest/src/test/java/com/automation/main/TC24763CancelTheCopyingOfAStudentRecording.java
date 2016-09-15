package com.automation.main;



import java.util.List;
import java.text.DateFormat;
import java.util.Date;
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
import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import atu.testng.reports.utils.Utils;


@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC24763CancelTheCopyingOfAStudentRecording {

	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}

	public LoginHelperPage tegrity;
	public CoursesHelperPage course;
	public RecordingHelperPage record;
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
//		capability=DesiredCapabilities.internetExplorer();
//		capability.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING,false);
//		
//	driver=new InternetExplorerDriver(capability);
//		ATUReports.add("selected browser type", LogAs.PASSED, new CaptureScreen( ScreenshotOf.DESKTOP));
		
		driver = DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());

		
		//ATUReports.setWebDriver(driver);
		//ATUReports.add("set driver", true);
		tegrity = PageFactory.initElements(driver, LoginHelperPage.class);

		record = PageFactory.initElements(driver, RecordingHelperPage.class);
		copy = PageFactory.initElements(driver, CopyMenu.class);
		delete_menu = PageFactory.initElements(driver, DeleteMenu.class);
		
		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC24763CancelTheCopyingOfAStudentRecording at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC24763CancelTheCopyingOfAStudentRecording at " + DateToStr,
		 "Starting the test: TC24763CancelTheCopyingOfAStudentRecording at " + DateToStr, LogAs.PASSED, null);	
	}
	
	private void setAuthorInfoForReports() {
		ATUReports.setAuthorInfo("Qualitest Automation ", Utils.getCurrentTime(), "1.0");
	}

	private void setIndexPageDescription() {
		ATUReports.indexPageDescription = "Tests Results";
		
	}

	@Test
	public void testME() {
		setAuthorInfoForReports();
		setIndexPageDescription();
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
	
	@AfterClass
	public void closeBroswer() {
		this.driver.quit();
	}

	@Test(dependsOnMethods = "loadPage", description = "Login course page")
	public void loginCourses() throws InterruptedException//
	{
		// 1. Login as INSTRUCTOR.
		tegrity.loginCourses("User1");// log in courses page
		initializeCourseObject();
		
		// delete all courses at destination course
		course.deleteAllRecordingsInCourseStartWith("abc", 2, record, delete_menu);
		
		// Get course name of destination
		targetCourse = course.selectCourseThatStartingWith("abc");
		
		record.returnToCourseListPage();
		
		
		// 2. Select course.
		currentCourse = course.selectCourseThatStartingWith("Ab");
		System.out.println("Current course: " + currentCourse);
		//course.selectCourse(record);
		
		// 3. Click the 'Student Recording' tab.
		record.clickOnStudentRecordingsTab();
		Thread.sleep(1000);
		
		// 4. Select source recording.
		// 5. Select "Recording Tasks -> Copy" menu item.
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
		clickedRecording = copy.verifyCopyMenu(record);
		System.out.println("Clicked on recording: " + clickedRecording);
		
		
		// 6. Select destination course.
		copy.selectTargetCourseFromCourseList(targetCourse);
		System.out.println("Target course: " + targetCourse);
		
		// 7. Click "Cancel" button.
		Thread.sleep(2000);
		copy.clickOnCancelButton(record);
		Thread.sleep(2000);

		// 8. Verify that recording is not removed current course.
		record.verifyThatTargetRecordingExistInRecordingList(clickedRecording);
		
		
		// 8.1. Verify that recording is not copied from current course
		int clicked_recording_index = record.getTargetRecordingIndex(clickedRecording);
		String recording_status = record.getIndexRecordingStatus(clicked_recording_index);
		
		if((recording_status.equals("IE, FF, Safari Ready")) || recording_status.equals("")) {
			System.out.println("The recording has this status IE, FF, Safari Ready or none at all.");
			ATUReports.add("The recording has this status IE, FF, Safari Ready or none at all.", "True.", "True.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("The recording has not this status IE, FF, Safari Ready or none at all.");
			ATUReports.add("The recording has this status IE, FF, Safari Ready or none at all.", "True.", "False.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		
		
		// 9. Click "Courses" link at breadcrumbs.
		record.returnToCourseListPage();
		
		Thread.sleep(2000);
		
		// 10. Select the destination course.
		boolean isTargetCourseClicked = course.clickOnTargetCourseName(targetCourse);
		if (isTargetCourseClicked) {
			System.out.println("Target course name clicked: " + targetCourse);
		} else {
			System.out.println("Target course name is not clicked: " + targetCourse);
		}
		
		Thread.sleep(2000);
		
		// 11. Verify that recording wasn't copied.
		if(record.student_recordings_tab.isDisplayed()) {
			record.clickOnStudentRecordingsTab();
			
			List<String> current_recording_list = record.getCourseRecordingList();
			
			if(current_recording_list.contains(clickedRecording)) {
				System.out.println("Recording is exist");
				ATUReports.add("Recording title.", "Recording is exist.", "Recording is exist.", LogAs.FAILED, null);
				Assert.assertTrue(false);
			} else {
				System.out.println("Recording is not exist.");
				ATUReports.add("Recording title.", "Recording is exist.", "Recording is not exist.", LogAs.PASSED, null);
				Assert.assertTrue(true);
			}
			
			Thread.sleep(2000);
		} else {
			record.verifyNoStudentTab();
		}
		
		
		
		// 12. Click "Courses" link at breadcrumbs.
		record.returnToCourseListPage();
		Thread.sleep(2000);
		
		// 13. Select the source course.
		isTargetCourseClicked = course.clickOnTargetCourseName(currentCourse);
		if (isTargetCourseClicked) {
			System.out.println("Target course name clicked: " + currentCourse);
		} else {
			System.out.println("Target course name is not clicked: " + currentCourse);
		}
		
		// 14. Click the 'Student Recording' tab.
		record.clickOnStudentRecordingsTab();
		Thread.sleep(1000);
		
		// 15. Select source recording.
		// 16. Select "Recording Tasks -> Copy" menu item.
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
		clickedRecording = copy.verifyCopyMenu(record);
		System.out.println("Clicked on recording: " + clickedRecording);
		
		// 17. Select destination course.
		copy.selectTargetCourseFromCourseList(targetCourse);
		System.out.println("Target course: " + targetCourse);
		
		// 18. Click outside the "Copy" window (ESC button).
		copy.clickEscOnKeyBoardToCloseCopyWindow();
		
		// 19. Verify that isn't removed from the current course.
		record.verifyThatTargetRecordingExistInRecordingList(clickedRecording);
		
		
		// 19.1. Verify that recording is not copied from current course
		clicked_recording_index = record.getTargetRecordingIndex(clickedRecording);
		recording_status = record.getIndexRecordingStatus(clicked_recording_index);
				
		if((recording_status.equals("IE, FF, Safari Ready")) || recording_status.equals("")) {
			System.out.println("The recording has this status IE, FF, Safari Ready or none at all.");
			ATUReports.add("The recording has this status IE, FF, Safari Ready or none at all.", "True.", "True.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("The recording has not this status IE, FF, Safari Ready or none at all.");
			ATUReports.add("The recording has this status IE, FF, Safari Ready or none at all.", "True.", "False.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}

		
		// 20. Click "Courses" link at breadcrumbs.
		record.returnToCourseListPage();
		
		// 21. Select the destination course.
		isTargetCourseClicked = course.clickOnTargetCourseName(targetCourse);
		if (isTargetCourseClicked) {
			System.out.println("Target course name clicked: " + targetCourse);
		} else {
			System.out.println("Target course name is not clicked: " + targetCourse);
		}
		
		// 22. Verify that recording wasn't copied.
		if(record.student_recordings_tab.isDisplayed()) {
			record.clickOnStudentRecordingsTab();
			
			List<String> current_recording_list = record.getCourseRecordingList();
			
			if(current_recording_list.contains(clickedRecording)) {
				System.out.println("Recording is exist");
				ATUReports.add("Recording title.", "Recording is exist.", "Recording is exist.", LogAs.FAILED, null);
				Assert.assertTrue(false);
			} else {
				System.out.println("Recording is not exist.");
				ATUReports.add("Recording title.", "Recording is exist.", "Recording is not exist.", LogAs.PASSED, null);
				Assert.assertTrue(true);
			}
			
			Thread.sleep(2000);
		} else {
			record.verifyNoStudentTab();
		}
		
		// NOTE: This test is skipping Test Case steps 23-33 due to inability to automate clicking outside the modal window.
		
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
		
	}

}
