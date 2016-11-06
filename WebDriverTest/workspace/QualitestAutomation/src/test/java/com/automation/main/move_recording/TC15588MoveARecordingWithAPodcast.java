package com.automation.main.move_recording;



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

import com.automation.main.page_helpers.ConfirmationMenu;
import com.automation.main.page_helpers.CopyMenu;
import com.automation.main.page_helpers.CourseSettingsPage;
import com.automation.main.page_helpers.CoursesHelperPage;
import com.automation.main.page_helpers.DeleteMenu;
import com.automation.main.page_helpers.LoginHelperPage;
import com.automation.main.page_helpers.MoveWindow;
import com.automation.main.page_helpers.PlayerPage;
import com.automation.main.page_helpers.PodcastPage;
import com.automation.main.page_helpers.RecordingHelperPage;
import com.automation.main.utilities.DriverSelector;
import com.automation.main.utilities.DriverSelector.BrowserType;

import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;

@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC15588MoveARecordingWithAPodcast {

	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}

	public MoveWindow move_window;
	public LoginHelperPage tegrity;
	public CoursesHelperPage course;
	public RecordingHelperPage record;
	public ConfirmationMenu confirm_menu;
	public DeleteMenu delete_menu;
	public CourseSettingsPage course_settings;
	public PodcastPage podcast_page;
	public PlayerPage player_page;
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
		
		

		tegrity = PageFactory.initElements(driver, LoginHelperPage.class);

		record = PageFactory.initElements(driver, RecordingHelperPage.class);
		copy = PageFactory.initElements(driver, CopyMenu.class);
		course_settings = PageFactory.initElements(driver, CourseSettingsPage.class);
		
		confirm_menu = PageFactory.initElements(driver, ConfirmationMenu.class);
		podcast_page = PageFactory.initElements(driver, PodcastPage.class);
		player_page = PageFactory.initElements(driver, PlayerPage.class);
		move_window = PageFactory.initElements(driver, MoveWindow.class);
		delete_menu = PageFactory.initElements(driver, DeleteMenu.class);
		
		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC15588MoveARecordingWithAPodcast at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC15588MoveARecordingWithAPodcast at " + DateToStr,
		 "Starting the test: TC15588MoveARecordingWithAPodcast at " + DateToStr, LogAs.PASSED, null);	
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

	
	@Test (description = "TC 15588 Move A Recording With A Podcast")
	public void test15588() throws InterruptedException
	{
		// 1. Login as INSTRUCTOR.
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
			
		tegrity.loginCourses("User1");// log in courses page
		initializeCourseObject();
		
		//2. As a source course select a course with "Enable MP3 Podcast" option enabled in "Course Settings".
		currentCourse = course.selectCourseThatStartingWith("Ab");
		System.out.println("Current course: " + currentCourse);
		Thread.sleep(3000);
		record.clickOnCourseTaskThenCourseSettings();
		Thread.sleep(3000);
		course_settings.enableAudioPodcast();
		course_settings.clickOnOkButton();
		
		List<String> records = record.getCourseRecordingList();
		Boolean isThereRecordIntheRecord = false;
		if(records.size() > 0){
			isThereRecordIntheRecord = true;
		}
		record.returnToCourseListPage();
		
		//3. As a destination course select a course with "Enable MP3 Podcast" option enabled in "Course Settings".
		targetCourse = course.selectCourseThatStartingWith("abc");
		System.out.println("Target course: " + targetCourse);
		Thread.sleep(3000);
		record.clickOnCourseTaskThenCourseSettings();
		Thread.sleep(3000);
		course_settings.enableAudioPodcast();
		course_settings.clickOnOkButton();
		Thread.sleep(3000);
		record.deleteAllRecordings(delete_menu);
		record.returnToCourseListPage();
		
		//if we don't have record we go to get them from the bank
		if(isThereRecordIntheRecord == false) {
			
			//move to the superUser
			record.signOut();
			tegrity.loginCourses("SuperUser");
			
			//click on the bank course and then select the first record
			course.selectCourseThatStartingWith("BankValid");
			record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);			
			record.clickOnRecordingTaskThenCopy();
			
			//copy the first record to the course that start with Ab
		    copy.selectTargetCourseFromCourseList(currentCourse);
		    copy.clickOnCopyButton();
		    confirm_menu.clickOnOkButtonAfterConfirmCopyRecording();
			record.waitUntilFirstRecordingBeingCopiedFromStatusDissaper();
			
			
			//return to the instructor
			record.signOut();
			tegrity.loginCourses("User1");
		}
		

		//4. Select source course.
		currentCourse = course.selectCourseThatStartingWith("Ab");
		System.out.println("Current course: " + currentCourse);
		
		//5. Select source recording.
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
		String first_recording_title = record.getFirstRecordingTitle();
		System.out.println("Selected recording for copy: " + first_recording_title);
		
		//6. Select "Recording Tasks -> Move" menu item.
		record.clickOnRecordingTaskThenMove();
		
		//7. Select destination.
		copy.selectTargetCourseFromCourseList(targetCourse);
		
		//8. Click "Move Recording(s)" button.
		move_window.clickOnMoveRecordings();
		
		//9. Click "OK" button.
		confirm_menu.clickOnOkButtonAfterConfirmMoveRecording();
		
		
		// 10. Message window is closed.
		boolean is_closed = confirm_menu.isConfirmationMenuClosed();
		
		if (is_closed) {
			System.out.println("Message window is closed.");
			ATUReports.add("Message window is closed.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Message window is not closed.");
			ATUReports.add("Message window is not closed.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		
		// 11. Move window is closed.
		is_closed = copy.isCopyMenuClosed();
			
		if (is_closed) {
			System.out.println("Copy window is closed.");
			ATUReports.add("Copy window is closed.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Copy window is not closed.");
			ATUReports.add("Copy window is not closed.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		
		Thread.sleep(2000);
		// 12. Recording's status change when the copying started
		List<String> source_recording_list = record.getCourseRecordingList(); 
				
		for(int i = 0; i < source_recording_list.size(); i++) {
			if(first_recording_title.equals(source_recording_list.get(i))) {
				record.checkRecordingInIndexIStatus((i + 1), "Being moved from");
			}
		}
		
		
		// 13. Recording's status change after the copying is done
		record.checkStatusExistenceForMaxTTime(360);
		

		// 14. After finishing a copy, recording status disappear click "Courses" link at the breadcrumbs.
		record.returnToCourseListPage();
		
		// 15. Select destination course.
		targetCourse = course.selectCourseThatStartingWith("abc");
		System.out.println("Target course: " + targetCourse);
		
		// 16. Select "Course Tasks -> Podcast" menu item.
		record.clickOnCourseTaskThenPodcast();
		
		
		// 17. Click on copied recording's title.
		for (String handle : driver.getWindowHandles()) {
		    driver.switchTo().window(handle);
		    if (driver.getCurrentUrl().contains("rss?filters")) {
		    	break;
		    }
		}
		
		System.out.println(driver.getCurrentUrl());
	    System.out.println(driver.getTitle());
	    System.out.println(driver.getWindowHandle());
	    
	    Thread.sleep(3000);
	    
	    System.out.println(driver.getPageSource());
	    
	    String url_of_podcast_instructor = podcast_page.getTargetPodcastHref(first_recording_title);
	    podcast_page.clickOnTargetPodcast(first_recording_title);
	    
	    Thread.sleep(5000);
	
	    player_page.verifyPartiallyUrl(url_of_podcast_instructor.split("/")[5]);
	   
	    // 18. Click "Sign Out" link.
	    driver.close();
	
	    Thread.sleep(2000);
	    
	    for (String handle : driver.getWindowHandles()) {
		    driver.switchTo().window(handle);
		    if (!driver.getCurrentUrl().contains("rss?filters")) {
		    	break;
		    }
		}
	    
//	    System.out.println(driver.getCurrentUrl());
//	    System.out.println(driver.getTitle());
//	    System.out.println(driver.getWindowHandle());
	    
	    Thread.sleep(2000);
	    
	    record.signOut();
	    
	    // 19. Login as STUDENT.
	    tegrity.loginCourses("User4");// log in courses page
		initializeCourseObject();
	    
		
		// 20. Select destination course.
		targetCourse = course.selectCourseThatStartingWith("abc");
		System.out.println("Target course: " + targetCourse);
		
		
		// 21. Select "Course Tasks -> Podcast" menu item.
		record.clickOnCourseTaskThenPodcast();
		
		
		// 22. Click on copied recording's title.
		for (String handle : driver.getWindowHandles()) {
		    driver.switchTo().window(handle);
		    if (driver.getCurrentUrl().contains("rss?filters")) {
		    	break;
		    }
		}
		
//		System.out.println(driver.getCurrentUrl());
//	    System.out.println(driver.getTitle());
//	    System.out.println(driver.getWindowHandle());
	    
	    Thread.sleep(2000);
	    
	    String url_of_podcast_student = podcast_page.getTargetPodcastHref(first_recording_title);
	    podcast_page.clickOnTargetPodcast(first_recording_title);
	    
	    Thread.sleep(5000);
	
	    player_page.verifyPartiallyUrl(url_of_podcast_student.split("/")[5]);
	    
	    System.out.println("Done.");
	    ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);


	}
}
