package com.automation.main.other;


import java.awt.AWTException;
import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.automation.main.page_helpers.ConfirmationMenu;
import com.automation.main.page_helpers.CopyMenu;
import com.automation.main.page_helpers.CourseSettingsPage;
import com.automation.main.page_helpers.CoursesHelperPage;
import com.automation.main.page_helpers.LoginHelperPage;
import com.automation.main.page_helpers.PlayerPage;
import com.automation.main.page_helpers.PodcastPage;
import com.automation.main.page_helpers.RecordingHelperPage;

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
public class TestCaseCopyRecordingWithPodcast {

	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}

	public LoginHelperPage tegrity;
	public CoursesHelperPage course;
	public RecordingHelperPage record;
	public ConfirmationMenu confirm_menu;
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

		
		System.setProperty("webdriver.ie.driver", "c:/selenium-drivers/IEDriverServer.exe");
			capability=DesiredCapabilities.internetExplorer();
			capability.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING,false);
			
		driver=new InternetExplorerDriver(capability);
		ATUReports.add("selected browser type", LogAs.PASSED, new CaptureScreen( ScreenshotOf.DESKTOP));

		
		//ATUReports.setWebDriver(driver);
		//ATUReports.add("set driver", true);
		tegrity = PageFactory.initElements(driver, LoginHelperPage.class);

		record = PageFactory.initElements(driver, RecordingHelperPage.class);
		copy = PageFactory.initElements(driver, CopyMenu.class);
		course_settings = PageFactory.initElements(driver, CourseSettingsPage.class);
		
		confirm_menu = PageFactory.initElements(driver, ConfirmationMenu.class);
		podcast_page = PageFactory.initElements(driver, PodcastPage.class);
		player_page = PageFactory.initElements(driver, PlayerPage.class);
	}
	
	
	@AfterTest
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
		course.courses = course.getCoursesListFromElement(course.course_list);
	}

	
	@Test(dependsOnMethods = "loadPage", description = "Login course page")
	public void loginCourses() throws InterruptedException
	{
		// 1. Login as INSTRUCTOR.
		tegrity.loginCourses("Instructor");// log in courses page
		initializeCourseObject();
		
		//2. As a source course select a course with "Enable MP3 Podcast" option enabled in "Course Settings".
		currentCourse = course.selectSecondCourse(record);
		System.out.println("Current course: " + currentCourse);
		Thread.sleep(2000);
		record.clickOnCourseTaskThenCourseSettings();
		Thread.sleep(2000);
		course_settings.enableAudioPodcast();
		course_settings.clickOnOkButton();
		Thread.sleep(2000);
		record.returnToCourseListPage();
		
		//3. As a destination course select a course with "Enable MP3 Podcast" option enabled in "Course Settings".
		targetCourse = course.selectFirstCourse(record);
		System.out.println("Target course: " + targetCourse);
		Thread.sleep(2000);
		record.clickOnCourseTaskThenCourseSettings();
		Thread.sleep(2000);
		course_settings.enableAudioPodcast();
		course_settings.clickOnOkButton();
		Thread.sleep(2000);
		record.returnToCourseListPage();
		
		//4. Select source course.
		currentCourse = course.selectSecondCourse(record);
		System.out.println("Current course: " + currentCourse);
		
		//5. Select source recording.
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
		String first_recording_title = record.getFirstRecordingTitle();
		System.out.println("Selected recording for copy: " + first_recording_title);
		
		//6. Select "Recording Tasks -> Copy" menu item.
		record.clickOnRecordingTaskThenCopy();
		
		//7. Select destination.
		copy.selectTargetCourseFromCourseList(targetCourse);
		
		//8. Click "Copy Recording(s)" button.
		copy.clickOnCopyButton();
		
		//9. Click "OK" button.
		confirm_menu.clickOnOkButtonAfterConfirmCopyRecording();
		
		
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
		
		// 11. Copy window is closed.
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
		
		
		// 12. Recording's status change when the copying started
		List<String> source_recording_list = record.getCourseRecordingList(); 
				
		for(int i = 0; i < source_recording_list.size(); i++) {
			if(first_recording_title.equals(source_recording_list.get(i))) {
				record.checkRecordingInIndexIStatus((i + 1), "Being copied from");
			}
		}
		
		
		// 13. Recording's status change after the copying is done
		record.checkStatusExistenceForMaxTTime(30);
		

		// 14. After finishing a copy, recording status disappear click "Courses" link at the breadcrumbs.
		record.returnToCourseListPage();
		
		// 15. Select destination course.
		targetCourse = course.selectFirstCourse(record);
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
	    
	    Thread.sleep(2000);
	    
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
	    tegrity.loginCourses("Student");// log in courses page
		initializeCourseObject();
	    
		
		// 20. Select destination course.
		targetCourse = course.selectFirstCourse(record);
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
	    driver.quit();
	}
}
