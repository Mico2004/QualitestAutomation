package com.automation.main;



import java.util.List;
import org.openqa.selenium.WebDriver;import com.automation.main.page_helpers.Page;
import org.openqa.selenium.remote.DesiredCapabilities;
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


@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC15583CancelTheMoving {

	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}

	public LoginHelperPage tegrity;
	public CoursesHelperPage course;
	public RecordingHelperPage record;
	WebDriver driver;
	WebDriverWait wait;
	public static WebDriver thread_driver;
	CopyMenu copy;
	String currentCourse;
	String targetCourse;
	public DeleteMenu delete_menu;
	String clickedRecording;
    DesiredCapabilities capability;
	@BeforeClass
	public void setup() {
	
		driver=DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());
		ATUReports.add("selected browser type", LogAs.PASSED, new CaptureScreen( ScreenshotOf.DESKTOP));

 		
		//ATUReports.setWebDriver(driver);
		//ATUReports.add("set driver", true);
		tegrity = PageFactory.initElements(driver, LoginHelperPage.class);

		record = PageFactory.initElements(driver, RecordingHelperPage.class);
		copy = PageFactory.initElements(driver, CopyMenu.class);
		
	     Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC15538CancelTheCopying at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC15538CancelTheCopying at " + DateToStr, "Starting the test: TC15538CancelTheCopying at " + DateToStr, LogAs.PASSED, null);	
	}
	
	@AfterClass
	public void closeBroswer() {
		this.driver.quit();
	}

	private void setAuthorInfoForReports() {
		ATUReports.setAuthorInfo("McGraw-Hill Automation ", Utils.getCurrentTime(), "1.0");
	}

	private void setIndexPageDescription() {
		ATUReports.indexPageDescription = "McGraw-Hill Verify <br/> <b> UI existence</b>";
		
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
		delete_menu = PageFactory.initElements(driver, DeleteMenu.class); 
	}

	@Test(dependsOnMethods = "loadPage", description = "Login course page")
	public void loginCourses() throws InterruptedException//
	{
		// 1. Login as INSTRUCTOR.
		tegrity.loginCourses("User1");// log in courses page
		initializeCourseObject();
				// 2. Select course.
		currentCourse = course.selectCourseThatStartingWith("Ab");
		System.out.println("Current course: " + currentCourse);
		//course.selectCourse(record);
		
		
		//delete all the courses start with abc
		course.goToCoursesPage();
		Thread.sleep(Page.TIMEOUT_TINY);
		targetCourse = course.selectCourseThatStartingWith("abc");
		System.out.println("Target course: " + targetCourse);
		
		List<String> listOfCourseToDelete = record.getCourseRecordingList();
		if(!listOfCourseToDelete.isEmpty()){
			record.checkAllCheckBox();
			record.clickOnRecordingTaskThenDelete();
			delete_menu.clickOnDeleteButton();
		}
		System.out.println("delete all records in course: " + targetCourse );
		
		//return to the selected course 
		course.goToCoursesPage();
		course.selectCourseByName(currentCourse);
		
		
		// 3. Select source recording.
		// 4. Select "Recording Tasks -> Move" menu item.
		clickedRecording = record.getFirstRecordingTitle();
		record.selectFirstCheckbox();
		record.clickOnRecordingTaskThenMove();
		System.out.println("Clicked on recording: " + clickedRecording);
		
		
		//5. Select destination course.
		targetCourse = copy.selectCourseFromCourseListOtherThenCurrentCourse(currentCourse);
		System.out.println("Target course: " + targetCourse);
		
		//6. Click "Cancel" button.
		Thread.sleep(Page.TIMEOUT_TINY);
		copy.clickOnCancelButton(record);
		
		Thread.sleep(Page.TIMEOUT_TINY);
		

		//7. Verify that recording is not removed current course.
		record.verifyThatTargetRecordingExistInRecordingList(clickedRecording);
		
		//8. Click "Courses" link at breadcrumbs.
		record.returnToCourseListPage();
		
		Thread.sleep(Page.TIMEOUT_TINY);
		
		//9. Select the destination course.
		boolean isTargetCourseClicked = course.clickOnTargetCourseName(targetCourse);
		if (isTargetCourseClicked) {
			System.out.println("Target course name clicked: " + targetCourse);
		} else {
			System.out.println("Target course name is not clicked: " + targetCourse);
		}
		
		Thread.sleep(Page.TIMEOUT_TINY);
		
		//10. Verify that recording wasn't copied.
		List<String> current_recording_list = record.getCourseRecordingList();
		
		if(current_recording_list.contains(clickedRecording)) {
			System.out.println("Recording is exist");
			ATUReports.add("Recording is exist", LogAs.FAILED, null);
			Assert.assertTrue(false);
		} else {
			System.out.println("Recording is not exist.");
			ATUReports.add("Recording is not exist.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		}
		
		Thread.sleep(Page.TIMEOUT_TINY);
		
		//11. Click "Courses" link at breadcrumbs.
		record.returnToCourseListPage();
		
		Thread.sleep(Page.TIMEOUT_TINY);
		
		//12. Select the source course.
		isTargetCourseClicked = course.clickOnTargetCourseName(currentCourse);
		if (isTargetCourseClicked) {
			System.out.println("Target course name clicked: " + currentCourse);
		} else {
			System.out.println("Target course name is not clicked: " + currentCourse);
		}
		
		//13. Select source recording.
		//14. Select "Recording Tasks -> Move" menu item.
		clickedRecording = record.getFirstRecordingTitle();
		record.selectFirstCheckbox();
		record.clickOnRecordingTaskThenMove();
		System.out.println("Clicked on recording: " + clickedRecording);
		
		//15. Select destination course.
		targetCourse = copy.selectCourseFromCourseListOtherThenCurrentCourse(this.currentCourse);
		System.out.println("Target course: " + targetCourse);
		
		//16. Click on Esc button in the keyboard.
		copy.clickEscOnKeyBoardToCloseCopyWindow();
		
		//17. Verify that isn't removed from the current course.
		record.verifyThatTargetRecordingExistInRecordingList(clickedRecording);
		
		//18. Click "Courses" link at breadcrumbs.
		record.returnToCourseListPage();
		
		//19. Select the destination course.
		isTargetCourseClicked = course.clickOnTargetCourseName(targetCourse);
		if (isTargetCourseClicked) {
			System.out.println("Target course name clicked: " + targetCourse);
		} else {
			System.out.println("Target course name is not clicked: " + targetCourse);
		}
		
		//20. Verify that recording wasn't copied.
		current_recording_list = record.getCourseRecordingList();
		
		if(current_recording_list.contains(clickedRecording)) {
			System.out.println("Recording is exist");
			ATUReports.add("Recording is exist", LogAs.FAILED, null);
			Assert.assertTrue(false);
		} else {
			System.out.println("Recording is not exist.");
			ATUReports.add("Recording is not exist.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		}
		
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
		
	}

}
