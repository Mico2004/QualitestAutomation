package com.automation.main.copy_recording;

/*
	#############################################################################################################################
	# Test Name				: Try To Copy Failed Recording				  							         					#
	# Author Name			: Roman Polonsky                                      												#
	# Date 					: 01-14-2016	                                      												#
	# Modified by			: N/A       		                                  												#
	# Date of modification	: N/A           			                          												#
	# Modification details	: N/A                                                 												#
	# Description : 																											#												
	#									                                                                                        #
	#		Precondition configuration:	Failed recording should be in course: "z 15660 - Try to copy failed recording"			#
	#																															#
	#																															#
	#																															#
	#																															#
	#																															#
	#																															#
	#				Test steps:                                                                                                 #
	#					1. Login as INSTRUCTOR.																					#
	#					2. Select course (named: "z 15660 - Try to copy failed recording").										#
	#					3. Select recording with "Erorr" status.																#
	#					4. Select "Recording Tasks -> Copy" menu item.															#
	#					5. Message box "Cannot copy in-process or failed recording" is displayed.								#
	#					6. Click "OK" button.																					#
	#																															#
	# Remarks: N/A																												#
	#############################################################################################################################
*/
//Precondition: Failed recording should be in course: "z 15660 - Try to copy failed recording"


import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.automation.main.page_helpers.ConfirmationMenu;
import com.automation.main.page_helpers.CopyMenu;
import com.automation.main.page_helpers.CoursesHelperPage;
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
public class TC15660TryToCopyFailedRecording {

	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}

	public LoginHelperPage tegrity;
	public CoursesHelperPage course;
	public RecordingHelperPage record;
	public ConfirmationMenu confirm_menu;
	WebDriver driver;
	WebDriverWait wait;
	public static WebDriver thread_driver;
	CopyMenu copy;
	String current_course;
	String targetCourse;
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

		
	///	ATUReports.setWebDriver(driver);
		//ATUReports.add("set driver", true);
		tegrity = PageFactory.initElements(driver, LoginHelperPage.class);

		record = PageFactory.initElements(driver, RecordingHelperPage.class);
		copy = PageFactory.initElements(driver, CopyMenu.class);
		
		confirm_menu = PageFactory.initElements(driver, ConfirmationMenu.class);
		
		wait = new WebDriverWait(driver, 10);
		
		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC15660TryToCopyFailedRecording at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC15660TryToCopyFailedRecording at " + DateToStr, "Starting the test: TC15660TryToCopyFailedRecording at " + DateToStr, LogAs.PASSED, null);	
	}
	
	@AfterTest
	public void closeBroswer() {
		this.driver.quit();
	}

		
	// description = "get courses list"
	public void initializeCourseObject() throws InterruptedException {

		course = PageFactory.initElements(driver, CoursesHelperPage.class);
		course.courses = course.getCoursesListFromElement(course.course_list);
	}

	
	@Test(description = "TC 15660 Try To Copy Failed Recording")
	public void test15660() throws InterruptedException
	{
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);

		// 1. Login as INSTRUCTOR.
		tegrity.loginCourses("User1");// log in courses page
		initializeCourseObject();
		
		// 2. Select course (named start with: "BankInvalidRecordings").
		course.selectCourseThatStartingWith("BankInvalidRecording");
	
		wait.until(ExpectedConditions.visibilityOf(record.first_recording_title));
		
		// 3. Select recording with "Erorr" status.
		List<String> recording_list = record.getCourseRecordingList(); 
		
		for(int i = 0; i < recording_list.size(); i++) {
			if(record.getIndexRecordingStatus(i + 1).equals("Error")) {
				//System.out.println("Found");
				record.selectIndexCheckBox(i + 1);
				break;
			}
		}
		
		// 4. Select "Recording Tasks -> Copy" menu item
		record.clickOnRecordingTaskThenCopy();
		
		Thread.sleep(1000);
		
		// 5. Message box "Cannot copy in-process or failed recording" is displayed.
		// 6. Click "OK" button.
		confirm_menu.clickOnOkButtonAfterCannotCopyInProcessOrFailRecordings();
		
		// 7. Message box is closed.
		boolean is_message_box_closed = confirm_menu.isConfirmationMenuClosed();
		
		if(is_message_box_closed) {
			System.out.println("Message box is closed.");
			ATUReports.add("Message box.", "Message box is closed.", "Message box is closed.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Message box not closed.");
			ATUReports.add("Message box.", "Message box is closed.", "Message box not closed.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}


		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
	}
}
