package com.automation.main;


import java.awt.AWTException;
import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;import com.automation.main.page_helpers.Page;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;



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
public class TestCaseCopyRecordingWithVodcast {

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
	
		
		
		System.setProperty("webdriver.ie.driver", "src/test/resources/IEDriverServer.exe");
			capability=DesiredCapabilities.internetExplorer();
			capability.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING,false);
			
		driver=new InternetExplorerDriver(capability);
	///	driver=new FirefoxDriver();
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
	
	
//	@AfterTest
//	public void closeBroswer() {
//		this.driver.quit();
//	}


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
	public void loginCourses() throws InterruptedException, NullPointerException
	{
		// 1. Login as INSTRUCTOR.
		tegrity.loginCourses("Instructor");// log in courses page
		initializeCourseObject();
		
//		course.selectFirstCourse(record);
		course.selectFirstCourse(record);
		
		System.out.println(record.checkExistenceOfStatusInRecordings());
		
		System.out.println(record.checkIfAllRecordingsClickable());
		
		
		
		//System.out.println(driver.findElement(By.id("Recording1")).getAttribute("disabled"));
		//System.out.println(driver.findElement(By.id("Recording2")).getAttribute("disabled"));
		//System.out.println(driver.findElement(By.id("Recording3")).getAttribute("disabled"));
		//System.out.println(record.checkIfAllRecordingsClickable());
		
//		//driver.navigate().to("https://reg-qabr.tegrity.com/#/recording/ebad7e97-fc99-4c94-ae42-afb528790d77?startTime=0");
//		driver.navigate().to("http://reg-qabr.tegrity.com/#/recording/831010e2-4f22-4e45-9e2f-a8d344985152?startTime=0&loadUser=1");
//	
//		//Thread.sleep(Page.TIMEOUT_TINY);
//		//wait.until(ExpectedConditions.alertIsPresent());
//		Thread.sleep(Page.TIMEOUT_TINY);
//		//player_page.verifyTimeBufferStatusForXSec(10);
//		driver.switchTo().alert().accept();
//		
		
//		 String alertMessage = "";
//		 
//	        driver.get("http://jsbin.com/usidix/1");
//	        driver.findElement(By.cssSelector("input[value=\"Go!\"]")).click();
//	        alertMessage = driver.switchTo().alert().getText();
//	        driver.switchTo().alert().accept();
//	        
//	        System.out.println(alertMessage);
		
		
		//Thread.sleep(Page.TIMEOUT_TINY);
//		for (String handle : driver.getWindowHandles()) {
//		    driver.switchTo().window(handle);
//		    driver.switchTo().f
//		    Thread.sleep(Page.TIMEOUT_TINY);
//		    System.out.println(driver.getCurrentUrl());
//		    System.out.println(driver.getTitle());
//		    System.out.println(driver.getWindowHandle());
//		}
		
		
		//System.out.println(driver;.getPageSource());
//		System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
//		driver.switchTo().frame(1);
//		System.out.println(driver.getPageSource());
		
		//for(int i = 0; i < 60; i++) {
			
			//Thread.sleep(Page.TIMEOUT_TINY);
//			System.out.println(driver.getCurrentUrl());
//		    System.out.println(driver.getTitle());
//		    System.out.println(driver.getWindowHandle());
		//}
	
	   
//	    //14. Click "Sign Out" link.
//	    driver.close();
//	
//	    Thread.sleep(Page.TIMEOUT_TINY);
//	    
//	    for (String handle : driver.getWindowHandles()) {
//		    driver.switchTo().window(handle);
//		    if (!driver.getCurrentUrl().contains("https://reg-qabr.tegrity.com/api/rss?filters")) {
//		    	break;
//		    }
//		}
//	    
////	    System.out.println(driver.getCurrentUrl());
////	    System.out.println(driver.getTitle());
////	    System.out.println(driver.getWindowHandle());
//	    
//	    Thread.sleep(Page.TIMEOUT_TINY);
//	    
//	    record.clickOnSignOut();
//	    
//	    //15. Login as STUDENT.
//	    tegrity.loginCourses("Student");// log in courses page
//		initializeCourseObject();
//	    
//		
//		//16. Select destination course.
//		targetCourse = course.selectFirstCourse(record);
//		System.out.println("Target course: " + targetCourse);
//		
//		
//		//17. Select "Course Tasks -> Podcast" menu item.
//		record.clickOnCourseTaskThenPodcast();
//		
//		
//		//18. Click on copied recording's title.
//		for (String handle : driver.getWindowHandles()) {
//		    driver.switchTo().window(handle);
//		    if (driver.getCurrentUrl().contains("https://reg-qabr.tegrity.com/api/rss?filters")) {
//		    	break;
//		    }
//		}
//		
////		System.out.println(driver.getCurrentUrl());
////	    System.out.println(driver.getTitle());
////	    System.out.println(driver.getWindowHandle());
//	    
//	    Thread.sleep(Page.TIMEOUT_TINY);
//	    
//	    String url_of_podcast_student = podcast_page.getTargetPodcastHref(first_recording_title);
//	    podcast_page.clickOnTargetPodcast(first_recording_title);
//	    
//	    Thread.sleep(Page.TIMEOUT_TINY);
//	
//	    player_page.verifyPartiallyUrl(url_of_podcast_student.split("/")[5]);

	}
}
