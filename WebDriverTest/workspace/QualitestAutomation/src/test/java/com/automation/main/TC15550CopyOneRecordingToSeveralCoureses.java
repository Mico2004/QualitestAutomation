package com.automation.main;



import java.util.ArrayList;
import java.util.List;
import java.text.DateFormat;
import java.util.Date;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;import com.automation.main.page_helpers.Page;import com.automation.main.page_helpers.Page;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;

@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC15550CopyOneRecordingToSeveralCoureses {

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
	public static WebDriver thread_driver;
	CopyMenu copy;
	String currentCourse;
	String targetCourse;
	String source_recording;
    DesiredCapabilities capability;
	@BeforeClass
	public void setup() {

		
//		System.setProperty("webdriver.ie.driver", "src/test/resources/IEDriverServer.exe");
//			capability=DesiredCapabilities.internetExplorer();
//			capability.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING,false);
//			
//		driver=new InternetExplorerDriver(capability);
//		capability=DesiredCapabilities.internetExplorer();
//		capability.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING,false);
//		
		driver = DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());

//		
		//ATUReports.setWebDriver(driver);
		//ATUReports.add("set driver", true);
		tegrity = PageFactory.initElements(driver, LoginHelperPage.class);

		record = PageFactory.initElements(driver, RecordingHelperPage.class);
		copy = PageFactory.initElements(driver, CopyMenu.class);
		
		confirm_menu = PageFactory.initElements(driver, ConfirmationMenu.class);
		
		delete_menu = PageFactory.initElements(driver, DeleteMenu.class);
		
		wait = new WebDriverWait(driver, 10);
		
		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC15550CopyOneRecordingToSeveralCoureses at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC15550CopyOneRecordingToSeveralCoureses at " + DateToStr, "Starting the test: TC15550CopyOneRecordingToSeveralCoureses at " + DateToStr, LogAs.PASSED, null);	
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
		
		// 2. Get destination and source course names
		List <String> current_course_list = course.getCourseList();
		List <String> target_course_list = new ArrayList<String>();
		
		for(int i = 0; i < 4; i++) {
			if(current_course_list.get(i).startsWith("abc")) {
				target_course_list.add(current_course_list.get(i));
			} else if (current_course_list.get(i).startsWith("ad")) {
				target_course_list.add(current_course_list.get(i));
			} else if (current_course_list.get(i).startsWith("Ba")) {
				target_course_list.add(current_course_list.get(i));
			} else if (current_course_list.get(i).startsWith("BAc")) {
				target_course_list.add(current_course_list.get(i));
			} else if (current_course_list.get(i).startsWith("Ab")) {
				currentCourse = current_course_list.get(i);
			}
		}
		
		// Precondition - go into each course from target course list and delete all recording in this course
		for (int i = 0; i < target_course_list.size(); i++) {
			course.deleteAllRecordingsInCourseStartWith(target_course_list.get(i), 0, record, delete_menu);
		}
		
		// 3. Select course.
		course.selectCourseThatStartingWith("Ab");

		
		// 4. Get wanted recording name.
		source_recording = record.getFirstRecordingTitle();
		System.out.println("Target recording name: " + source_recording);
		
		
		// 5. Click the "Courses" link at breadcrumbs.
		record.returnToCourseListPage();
		
		// 6. Go through destination courses and check that target recording not appear, is so delete it
		for (String course_name : target_course_list) {
			
			Thread.sleep(Page.TIMEOUT_TINY);
			
			course.clickOnTargetCourseName(course_name);
			
			record.deleteTargetRecordingIfExist(source_recording, delete_menu);
			
			record.returnToCourseListPage();
			
		}
		
		Thread.sleep(Page.TIMEOUT_TINY);
		
		// 7. Select course.
		course.clickOnTargetCourseName(currentCourse);

		
		// 8. Select target recording
		record.selectTargetRecordingCheckbox(source_recording);
		
		
		// 9. Select "Recording Tasks -> Copy" menu item.
		record.clickOnRecordingTaskThenCopy();
		
		
		// 10. Select several destination courses by clicking "Ctrl+Click" (command + click in mac).
		copy.selectTargetCourses(target_course_list);
		
		
		// 11. Click "Copy Recording(s)" button.
		copy.clickOnCopyButton();
		
		Thread.sleep(Page.TIMEOUT_TINY);
		
		// 12. Click "OK".
		confirm_menu.clickOnOkButtonAfterConfirmCopyRecording();
		
		// 13. Message window is closed.
		boolean is_closed = confirm_menu.isConfirmationMenuClosed();
		
		if (is_closed) {
			System.out.println("Message window is closed.");
			ATUReports.add("Message window.", "Message window is closed.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Message window is not closed.");
			ATUReports.add("Message window.", "Message window is closed.", "Message window is not closed.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		
		// 14. Copy window is closed.
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
		
		// 15. Recording's status change when the copying started
		List<String> source_recording_list = record.getCourseRecordingList(); 
		
		for(int i = 0; i < source_recording_list.size(); i++) {
			if(source_recording.equals(source_recording_list.get(i))) {
				record.checkRecordingInIndexIStatus((i + 1), "Being copied from");
				// 16. Recording's status change after the copying is done
				//record.checkStatusExistenceForMaxTTime(120);
				record.checkThatRecordingStatusTargetIndexIsEmpty((i + 1), 440);
				break;
			}
		}
		
		
		// 17. Verify that source recording is not removed from source course.
		boolean isRecordingExist = record.isRecordingExist(source_recording, true);
		if (isRecordingExist) {
			System.out.println("Recording is exist.");
		} else {
			System.out.println("Recording is not exist");
		}
		
		// 18. Click the "Courses" link at breadcrumbs.
		record.returnToCourseListPage();
		
		for(int i = 0; i < target_course_list.size(); i++) {
		
			// 19. Select destination course.
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("Course" + Integer.toString(i + 1))));		
			boolean isTargetCourseClicked = course.clickOnTargetCourseName(target_course_list.get(i));
	
			if (isTargetCourseClicked) {
				System.out.println("Target course name clicked: " + target_course_list.get(i));
			} else {
				System.out.println("Target course name is not clicked: " + target_course_list.get(i));
			}
			Thread.sleep(Page.TIMEOUT_TINY);
			// 20. Verify that recording is copied to course.
			isRecordingExist = record.isRecordingExist(source_recording, true);
			
			// 21. Click the "Courses" link at breadcrumbs.
			record.returnToCourseListPage();
			
		}
		
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);

	}
}
