package com.automation.main.copy_recording;



import java.text.DateFormat;
import java.util.Date;
import java.util.HashSet;
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

import com.automation.main.page_helpers.CopyMenu;
import com.automation.main.page_helpers.CoursesHelperPage;
import com.automation.main.page_helpers.DeleteMenu;
import com.automation.main.page_helpers.LoginHelperPage;
import com.automation.main.page_helpers.RecordingHelperPage;
import com.automation.main.utilities.DriverSelector;

import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import atu.testng.reports.utils.Utils;


@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC15538CancelTheCopying {

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
		 System.out.println("Starting the test: TC15538CancelTheCopying at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC15538CancelTheCopying at " + DateToStr, "Starting the test: TC15538CancelTheCopying at " + DateToStr, LogAs.PASSED, null);	
		
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

	
	@Test(description = "TC 15538 Cancel The Copying")
	public void test15538() throws InterruptedException//
	{
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);

		// 1. Login as INSTRUCTOR.
		tegrity.loginCourses("User1");// log in courses page
		initializeCourseObject();
		
		// delete all courses at destination course
		course.deleteAllRecordingsInCourseStartWith("abc", 0, record, delete_menu);
		
		
		// 2. Select course.
		currentCourse = course.selectCourseThatStartingWith("Ab");
		System.out.println("Current course: " + currentCourse);
		//course.selectCourse(record);
		
		
		// 3. Select source recording.
		// 4. Select "Recording Tasks -> Copy" menu item.
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
		clickedRecording = copy.verifyCopyMenu(record);
		System.out.println("Clicked on recording: " + clickedRecording);
		
		
		//5. Select destination course.
		targetCourse = copy.selectCourseFromCourseListOtherThenCurrentCourse(this.currentCourse);
		System.out.println("Target course: " + targetCourse);
		
		//6. Click "Cancel" button.
		Thread.sleep(2000);
		copy.clickOnCancelButton(record);	
		Thread.sleep(3000);
		

		//7. Verify that recording ;is not removed current course.
		record.verifyThatTargetRecordingExistInRecordingList(clickedRecording);
		
		//8. Click "Courses" link at breadcrumbs.
		record.returnToCourseListPage();
		
		Thread.sleep(2000);
		
		//9. Select the destination course.
		boolean isTargetCourseClicked = course.clickOnTargetCourseName(targetCourse);
		if (isTargetCourseClicked) {
			System.out.println("Target course name clicked: " + targetCourse);
		} else {
			System.out.println("Target course name is not clicked: " + targetCourse);
		}
		
		Thread.sleep(2000);
		
		//10. Verify that recording wasn't copied.
		List<String> current_recording_list = record.getCourseRecordingList();
		
		if(current_recording_list.contains(clickedRecording)) {
			System.out.println("Recording is exist");
			ATUReports.add("Recording title.", "Recording is not exist.", "Recording is exist.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		} else {
			System.out.println("Recording is not exist.");
			ATUReports.add("Recording title.", "Recording is not exist.", "Recording is not exist.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		}
		
		Thread.sleep(2000);
		
		//11. Click "Courses" link at breadcrumbs.
		record.returnToCourseListPage();
		
		Thread.sleep(2000);
		
		//12. Select the source course.
		isTargetCourseClicked = course.clickOnTargetCourseName(currentCourse);
		if (isTargetCourseClicked) {
			System.out.println("Target course name clicked: " + currentCourse);
		} else {
			System.out.println("Target course name is not clicked: " + currentCourse);
		}
		
		//13. Select source recording.
		//14. Select "Recording Tasks -> Copy" menu item.
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
		clickedRecording = copy.verifyCopyMenu(record);
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
			ATUReports.add("Recording title.", "Recording is exist.", "Recording is exist.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		} else {
			System.out.println("Recording is not exist.");
			ATUReports.add("Recording title.", "Recording is exist.", "Recording is not exist.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		}
		
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
		
	}

}
