package com.automation.main;

import java.awt.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import java.text.DateFormat;
import java.util.Date;

public class TC17370VerifySortingOfCoursesInMoveWindowOfAdittinalContent {
	// Set Property for ATU Reporter Configuration
				{
					System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

				}

				public LoginHelperPage tegrity;
				public CoursesHelperPage course;
				public RecordingHelperPage record;
				public PlayerPage player_page;
				WebDriver driver;
				WebDriverWait wait;
				public static WebDriver thread_driver;
				CopyMenu copy;
				DesiredCapabilities capability;
				String os;
				List recording_list_object;
				ConfirmationMenu confirm;

				@BeforeClass
				public void setup() {
					try {

						driver = DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());
						
						

						tegrity = PageFactory.initElements(driver, LoginHelperPage.class);

						record = PageFactory.initElements(driver, RecordingHelperPage.class);
						copy = PageFactory.initElements(driver, CopyMenu.class);
						player_page = PageFactory.initElements(driver, PlayerPage.class);
						
						 Date curDate = new Date();
						 String DateToStr = DateFormat.getInstance().format(curDate);
						 System.out.println("Starting the test: TC17370VerifySortingOfCoursesInMoveWindowOfAdittinalContent at " + DateToStr);
						 ATUReports.add("Message window.", "Starting the test: TC17370VerifySortingOfCoursesInMoveWindowOfAdittinalContent at " + DateToStr,
						 "Starting the test: TC17370VerifySortingOfCoursesInMoveWindowOfAdittinalContent at " + DateToStr, LogAs.PASSED, null);	
						 
					} catch (Exception e) {
						/// ATUReports.add("Fail Step", LogAs.FAILED, new
						/// CaptureScreen(ScreenshotOf.DESKTOP));
					}

				}
				
				@AfterClass
				public void closeBroswer() {
					this.driver.quit();
				}
				
				@Test
				public void VerifySortingOfCoursesInCopyWindow() throws InterruptedException
				{
					///1.login page 
					tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
	            	//2.login as instructor
					tegrity.loginCourses("User1");// log in courses page
					//initialize course list
					initializeCourseObject();
				    //3.select course
					course.selectCourseThatStartingWith("Ab");
					Thread.sleep(1000);
					record = PageFactory.initElements(driver, RecordingHelperPage.class);
					//4.click on  test tab
					record.clickOnAdditionContentTab();
					Thread.sleep(2000);
					//5.select check box
	
			        record.getCheckbox().click();
					///6.select move menu
			   
			        record.clickOnContentTaskThenMove();
			        Thread.sleep(2000);
			        
				    //7.verify courses are displayed in alphabetical order
					record.verifyRecordingSortedByTitle(copy.getCourseList());///verify sorted by title
					
					System.out.println("Done.");
					ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
				}
				
				// description = "get courses list"
				public void initializeCourseObject() throws InterruptedException {

					course = PageFactory.initElements(driver, CoursesHelperPage.class);
					course.courses = course.getStringFromElement(course.course_list);
					course.size = course.course_list.size();
				}
}
