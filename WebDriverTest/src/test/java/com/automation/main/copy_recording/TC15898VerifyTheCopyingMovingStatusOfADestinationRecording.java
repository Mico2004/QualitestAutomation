package com.automation.main.copy_recording;

// Precondition: first recording in second course must be long duration


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

import com.automation.main.page_helpers.ConfirmationMenu;
import com.automation.main.page_helpers.CopyMenu;
import com.automation.main.page_helpers.CoursesHelperPage;
import com.automation.main.page_helpers.DeleteMenu;
import com.automation.main.page_helpers.LoginHelperPage;
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
public class TC15898VerifyTheCopyingMovingStatusOfADestinationRecording {

	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}

	public LoginHelperPage tegrity;
	public CoursesHelperPage course;
	public RecordingHelperPage record;
	public ConfirmationMenu confirm_menu;
	public DeleteMenu delete_menu;
	WebDriver driver;
	WebDriverWait wait;
	CopyMenu copy;
	String current_course;
	String target_course;
	String clickedRecording;
DesiredCapabilities capability;
	
@BeforeClass
	public void setup() {

//		System.setProperty("webdriver.ie.driver", "c:/selenium-drivers/IEDriverServer.exe");
//		capability=DesiredCapabilities.internetExplorer();
//		capability.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING,false);
//		
//	driver=new InternetExplorerDriver(capability);
		driver = DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());
		ATUReports.add("selected browser type", LogAs.PASSED, new CaptureScreen( ScreenshotOf.DESKTOP));

		
		//ATUReports.setWebDriver(driver);
		//ATUReports.add("set driver", true);
		tegrity = PageFactory.initElements(driver, LoginHelperPage.class);

		record = PageFactory.initElements(driver, RecordingHelperPage.class);
		copy = PageFactory.initElements(driver, CopyMenu.class);
		
		confirm_menu = PageFactory.initElements(driver, ConfirmationMenu.class);
		delete_menu = PageFactory.initElements(driver, DeleteMenu.class);
		
		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC15898VerifyTheCopyingMovingStatusOfADestinationRecording at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC15898VerifyTheCopyingMovingStatusOfADestinationRecording at " + DateToStr,
		 "Starting the test: TC15898VerifyTheCopyingMovingStatusOfADestinationRecording at " + DateToStr, LogAs.PASSED, null);	
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

	
	@Test(description = "TC 15898 Verify The Copying Moving Status Of A Destination Recording")
	public void test15898() throws InterruptedException
	{
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);

		// 1. Login as INSTRUCTOR.
		tegrity.loginCourses("User1");// log in courses page
		initializeCourseObject();
		
		// Find the target full course name + delete all recording in target course if they exists
		target_course = course.selectCourseThatStartingWith("abc");
		String target_course_url = driver.getCurrentUrl();
		record.deleteAllRecordings(delete_menu);
		record.returnToCourseListPage();
		
		// 2. Select course.
		current_course = course.selectCourseThatStartingWith("Ab");
		System.out.println("Current course: " + current_course);
		//course.selectCourse(record);
		
		// 3. Select recording.
		String selected_recording_name = record.getFirstRecordingTitle();
		System.out.println("Record to select: " + selected_recording_name);
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
		
		// 4. Select "Recording Tasks -> Copy"
		record.clickOnRecordingTaskThenCopy();
		
		// 5. Select destination course.
		copy.selectTargetCourseFromCourseList(target_course);
		
		// 6. Click "Copy Recording(s)".
		copy.clickOnCopyButton();
		
		// 7. Click "OK" button.
		confirm_menu.clickOnOkButtonAfterConfirmCopyRecording();
		
		// 7.1. Message is closed.
		boolean is_closed = confirm_menu.isConfirmationMenuClosed();
		
		if(is_closed) {
			System.out.println("Message window is closed.");
			ATUReports.add("Message window.", "Message window is closed.", "Message window is closed.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Message window is not closed.");
			ATUReports.add("Message window.", "Message window is closed.", "Message window is not closed.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		
		// 7.2. "Copy" window is closed.
		is_closed = copy.isCopyMenuClosed();
		
		if(is_closed) {
			System.out.println("Copy window is closed.");
			ATUReports.add("Copy window.", "Copy window is closed.", "Copy window is closed.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Copy window is not closed.");
			ATUReports.add("Copy window.", "Copy window is closed.", "Copy window is not closed.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		
		// 7.3. The source recording has status "Being copying from".
		record.checkRecordingInIndexIStatus(1, "Being copied from");
		
		// 8. While recording is being copied, click the "Courses" link at the breadcrumbs.
		record.returnToCourseListPage();
		
		// 9. Select destination course.
		course.selectTargetCourse(target_course);
		
//		// 8-9. Go to target course url.
//		driver.navigate().to(target_course_url);
//		Thread.sleep(3000);
		
		// 10. Verify that destination recording has a "Moving/Copying" status.
		// 10.1. Recr`row is grayed out.
		// 10.2. Recording has a "Moving/Copying" status.	
		List<String> current_recording_list = record.getCourseRecordingList();
		
		for(int i = 0; i < current_recording_list.size(); i++) {
			if(current_recording_list.get(i).equals(selected_recording_name)) {
				if(record.isIndexRecordingClickable(i + 1)) {
					if(!record.checkRecordingInIndexIStatus(i + 1, "")){
						if(!record.checkRecordingInIndexIStatus(i + 1, "IE, FF, Safari Ready")){	
							ATUReports.add("Destination recording verification", "Recording is'nt grayed out and has no status", "Recording is'nt grayed out and has not status", LogAs.PASSED, null);
							Assert.assertTrue(true);}
						else
							ATUReports.add("Destination recording verification", "Recording is'nt grayed out and has no status", "Recording is'nt grayed out but has a status", LogAs.FAILED,  new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
					}				
				}else {
						if(record.checkRecordingInIndexIStatus(i + 1, "Moving/Copying")){
						System.out.println("Recording is grayed out");
						ATUReports.add("Destination recording verification", "Recording is'nt grayed out and has not status", "Recording is'nt grayed out and has not status", LogAs.PASSED, null);
						Assert.assertTrue(true);}
						else
							ATUReports.add("Destination recording verification", "Recording is and has status", "Recording is grayed out but has a status", LogAs.FAILED,  new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				} 
	
			}
		}
		
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
		
	}
}
