package com.automation.main.copy_recording;



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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.automation.main.page_helpers.ConfirmationMenu;
import com.automation.main.page_helpers.CopyMenu;
import com.automation.main.page_helpers.CourseSettingsPage;
import com.automation.main.page_helpers.CoursesHelperPage;
import com.automation.main.page_helpers.DeleteMenu;
import com.automation.main.page_helpers.LoginHelperPage;
import com.automation.main.page_helpers.PlayerPage;
import com.automation.main.page_helpers.PodcastPage;
import com.automation.main.page_helpers.RecordingHelperPage;

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
import java.text.DateFormat;
import java.util.Date;


@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC15557CopyRecordingWithPodcast {

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
//			capability=DesiredCapabilities.internetExplorer();
//			capability.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING,false);
//			
//		driver=new InternetExplorerDriver(capability);
		
		/**
		 *  This test is FireFox only!!!
		 */
		driver = new FirefoxDriver();
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
		delete_menu = PageFactory.initElements(driver, DeleteMenu.class);
		
		wait = new WebDriverWait(driver, 30);
		
		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC15557CopyRecordingWithPodcast at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC15557CopyRecordingWithPodcast at " + DateToStr, "Starting the test: TC15557CopyRecordingWithPodcast at " + DateToStr, LogAs.PASSED, null);	
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

	
	@Test(description = "TC 15557 Copy Recording With Podcast")
	public void test15557() throws InterruptedException
	{
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);

		if(driver instanceof FirefoxDriver) {
			// 1. Login as INSTRUCTOR.
			tegrity.loginCourses("User1");// log in courses page
			initializeCourseObject();
			
			//2. As a source course select a course with "Enable MP3 Podcast" option enabled in "Course Settings".
			currentCourse = course.selectCourseThatStartingWith("Ab");
			System.out.println("Current course: " + currentCourse);
			Thread.sleep(2000);
			record.clickOnCourseTaskThenCourseSettings();
			Thread.sleep(2000);
			course_settings.enableAudioPodcast();
			course_settings.clickOnOkButton();
			Thread.sleep(2000);
			record.returnToCourseListPage();
			
			//3. As a destination course select a course with "Enable MP3 Podcast" option enabled in "Course Settings".
			targetCourse = course.selectCourseThatStartingWith("abc");
			System.out.println("Target course: " + targetCourse);
			Thread.sleep(2000);
			record.clickOnCourseTaskThenCourseSettings();
			Thread.sleep(2000);
			course_settings.enableAudioPodcast();
			course_settings.clickOnOkButton();
			Thread.sleep(2000);
			
			
			// If there is any recording delete them from the target course
			record.deleteAllRecordings(delete_menu);
			record.returnToCourseListPage();
			
			//4. Select source course.
			currentCourse = course.selectCourseThatStartingWith("Ab");
			System.out.println("Current course: " + currentCourse);
			
			//5. Select source recording (first recording from podcast list)
			List<String> current_recording_list = record.getCourseRecordingList(); 
			
			record.clickOnCourseTaskThenPodcast();
			for (String handle : driver.getWindowHandles()) {
			    driver.switchTo().window(handle);
			    if (driver.getCurrentUrl().contains("rss?filters")) {
			    	break;
			    }
			}
			
			wait.until(ExpectedConditions.visibilityOf(driver.findElements(By.cssSelector(".entry>h3>a>span")).get(0)));
			List<WebElement> rss_recording_list = driver.findElements(By.cssSelector(".entry>h3>a>span"));
			String recording_title = null;
			for(int i=0; i<rss_recording_list.size();i++) {
				if(current_recording_list.contains(rss_recording_list.get(i).getText())) {
					recording_title = rss_recording_list.get(i).getText();
					break;
				}
			}
//			String recording_title = driver.findElements(By.cssSelector(".entry>h3>a>span")).get(0).getText();
			
			System.out.println(recording_title);
			
			driver.close();
			
			
			for (String handle : driver.getWindowHandles()) {
			    driver.switchTo().window(handle);
			    break;
			}
			
			
			record.selectTargetRecordingCheckbox(recording_title);
			String first_recording_title = recording_title;
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
				ATUReports.add("Message window.", "Message window is closed.", "Message window is closed.", LogAs.PASSED, null);
				Assert.assertTrue(true);
			} else {
				System.out.println("Message window is not closed.");
				ATUReports.add("Message window.", "Message window is closed.", "Message window is not closed.", LogAs.FAILED, null);
				Assert.assertTrue(false);
			}
			
			// 11. Copy window is closed.
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
			
			
			// 12. Recording's status change when the copying started
			List<String> source_recording_list = record.getCourseRecordingList(); 
					
			for(int i = 0; i < source_recording_list.size(); i++) {
				if(first_recording_title.equals(source_recording_list.get(i))) {
					record.checkRecordingInIndexIStatus((i + 1), "Being copied from");
					
					// 13. Recording's status change after the copying is done
//					record.checkStatusExistenceForMaxTTime(120);
					record.checkThatRecordingStatusTargetIndexIsEmpty(i + 1, 360);
				}
			}
			
			
			
			

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
		    
		    Thread.sleep(2000);
		    
		    //String url_of_podcast_instructor = podcast_page.getTargetPodcastHref(first_recording_title);
		    podcast_page.clickOnTargetPodcastMp3(first_recording_title);
		    
		    Thread.sleep(2000);
		    
		    WebElement mp3_player = driver.findElement(By.cssSelector("html>body>video"));
		    
		    if (mp3_player.isDisplayed()) {
		    	System.out.println("MP3 player is displayed.");
		    	ATUReports.add("MP3 player.", "MP3 player is displayed.", "MP3 player is displayed.", LogAs.PASSED, null);
		    	Assert.assertTrue(true);
		    } else {
		    	System.out.println("MP3 player is not displayed.");
		    	ATUReports.add("MP3 player.", "MP3 player is displayed.", "MP3 player is not displayed.", LogAs.FAILED, null);
		    	Assert.assertTrue(false);
			}
		    
//		    Thread.sleep(5000);
//		
//		    player_page.verifyPartiallyUrl(url_of_podcast_instructor.split("/")[5]);
//		   
		    // 18. Click "Sign Out" link.
		    driver.close();
		
		    Thread.sleep(2000);
		    
		    for (String handle : driver.getWindowHandles()) {
			    driver.switchTo().window(handle);
			    if (!driver.getCurrentUrl().contains("rss?filters")) {
			    	break;
			    }
			}
		    
//		    System.out.println(driver.getCurrentUrl());
//		    System.out.println(driver.getTitle());
//		    System.out.println(driver.getWindowHandle());
		    
		    Thread.sleep(2000);
		    
		    record.clickOnSignOut();
		    
		    // 19. Login as STUDENT.
		    tegrity.loginCourses("User2");// log in courses page
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
			
//			System.out.println(driver.getCurrentUrl());
//		    System.out.println(driver.getTitle());
//		    System.out.println(driver.getWindowHandle());
		    
			podcast_page.clickOnTargetPodcastMp3(first_recording_title);
			
		    Thread.sleep(2000);
		    
		    mp3_player = driver.findElement(By.cssSelector("html>body>video"));
		    
		    if (mp3_player.isDisplayed()) {
		    	System.out.println("MP3 player is displayed.");
		    	ATUReports.add("MP3 player.", "MP3 player is displayed.", "MP3 player is displayed.", LogAs.PASSED, null);
		    	Assert.assertTrue(true);
		    } else {
		    	System.out.println("MP3 player is not displayed.");
		    	ATUReports.add("MP3 player.", "MP3 player is displayed.", "MP3 player is not displayed.", LogAs.FAILED, null);
		    	Assert.assertTrue(false);
			}
		    
		    System.out.println("Done.");
		    ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);

		}
	}
}
