package com.automation.main.copy_recording;

import java.awt.List;

import org.jboss.netty.channel.ReceiveBufferSizePredictor;
import org.openqa.selenium.WebDriver;import com.automation.main.page_helpers.Page;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.automation.main.page_helpers.ConfirmationMenu;
import com.automation.main.page_helpers.CopyMenu;
import com.automation.main.page_helpers.CoursesHelperPage;
import com.automation.main.page_helpers.LoginHelperPage;
import com.automation.main.page_helpers.PlayerPage;
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

//@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC17361VerifySortingOfCoursesInCopyWindowOfRegularRecordings {

	public TC17361VerifySortingOfCoursesInCopyWindowOfRegularRecordings() {
		// TODO Auto-generated constructor stub
	}
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
		

			driver = DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());
			

			tegrity = PageFactory.initElements(driver, LoginHelperPage.class);

			record = PageFactory.initElements(driver, RecordingHelperPage.class);
			copy = PageFactory.initElements(driver, CopyMenu.class);
			player_page = PageFactory.initElements(driver, PlayerPage.class);
			
			 Date curDate = new Date();
			 String DateToStr = DateFormat.getInstance().format(curDate);
			 System.out.println("Starting the test: TC17361VerifySortingOfCoursesInCopyWindowOfRegularRecordings at " + DateToStr);
			 ATUReports.add("Message window.", "Starting the test: TC17361VerifySortingOfCoursesInCopyWindowOfRegularRecordings at " + DateToStr,
			 "Starting the test: TC17361VerifySortingOfCoursesInCopyWindowOfRegularRecordings at " + DateToStr, LogAs.PASSED, null);

	}
	
	@AfterClass
	public void quitBrowser() {
		driver.quit();
	}


	@Test (description="TC 17361 Verify Sorting Of Courses In Copy Window Of Regular Recordings")
	public void test17361() throws InterruptedException
	{
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);

		tegrity.loginCourses("User1");// log in courses page
		initializeCourseObject();
		course.selectCourseThatStartingWith("Ab");
		record = PageFactory.initElements(driver, RecordingHelperPage.class);
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
        Thread.sleep(Page.TIMEOUT_TINY);
	
        record.clickOnRecordingTaskThenCopy();
		//7.verify courses are displayed in alphabetical order
		record.verifyRecordingSortedByTitle(copy.getCourseList());///verify sorted by title
		
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
	
	}
	
	// description = "get courses list"
	public void initializeCourseObject() throws InterruptedException {

		course = PageFactory.initElements(driver, CoursesHelperPage.class);
		course.courses = course.getCoursesListFromElement(course.course_list);
		course.size = course.course_list.size();
	}
	
}
