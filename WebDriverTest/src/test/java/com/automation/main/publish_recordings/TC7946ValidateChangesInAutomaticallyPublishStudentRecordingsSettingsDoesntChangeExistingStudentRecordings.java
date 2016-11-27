package com.automation.main.publish_recordings;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.automation.main.page_helpers.AdminDashboardPage;
import com.automation.main.page_helpers.AdminDashboardViewCourseList;
import com.automation.main.page_helpers.CalendarPage;
import com.automation.main.page_helpers.CopyMenu;
import com.automation.main.page_helpers.CourseSettingsPage;
import com.automation.main.page_helpers.CoursesHelperPage;
import com.automation.main.page_helpers.EditRecordingPropertiesWindow;
import com.automation.main.page_helpers.LoginHelperPage;
import com.automation.main.page_helpers.PlayerPage;
import com.automation.main.page_helpers.PublishWindow;
import com.automation.main.page_helpers.RecordingHelperPage;
import com.automation.main.utilities.DriverSelector;

import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;


@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC7946ValidateChangesInAutomaticallyPublishStudentRecordingsSettingsDoesntChangeExistingStudentRecordings {

	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}
	
	public PublishWindow publish_window;
	public AdminDashboardViewCourseList admin_dashboard_view_course_list;
	public LoginHelperPage tegrity;
	public CoursesHelperPage course;
	public RecordingHelperPage record;
	public CalendarPage calendarPage;
	public EditRecordingPropertiesWindow edit_recording_properties_window;
	public AdminDashboardPage admin_dash_board_page;
	WebDriver driver;
	WebDriverWait wait;
	public static WebDriver thread_driver;
	CopyMenu copy;
	DesiredCapabilities capability;
	public PlayerPage player_page;
	public CourseSettingsPage course_settings_page;
	public By byStart = By.xpath(".//*[@id='publishRecordingWindow']/form/div[1]/div[1]/div[2]/div/div[1]/div[2]/div/table");
	public By byEnd = By.xpath(".//*[@id='publishRecordingWindow']/form/div[1]/div[1]/div[2]/div/div[2]/div[2]/div/table");

	@BeforeClass
	public void setup() {

			driver = DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());
			calendarPage = PageFactory.initElements(driver, CalendarPage.class);
			admin_dash_board_page = PageFactory.initElements(driver, AdminDashboardPage.class);
			admin_dashboard_view_course_list = PageFactory.initElements(driver, AdminDashboardViewCourseList.class);
			tegrity = PageFactory.initElements(driver, LoginHelperPage.class);
			publish_window = PageFactory.initElements(driver, PublishWindow.class);
			record = PageFactory.initElements(driver, RecordingHelperPage.class);
			copy = PageFactory.initElements(driver, CopyMenu.class);
			player_page = PageFactory.initElements(driver, PlayerPage.class);
			course_settings_page = PageFactory.initElements(driver, CourseSettingsPage.class);

		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC7946ValidateChangesInAutomaticallyPublishStudentRecordingsSettingsDoesntChangeExistingStudentRecordings at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC7946ValidateChangesInAutomaticallyPublishStudentRecordingsSettingsDoesntChangeExistingStudentRecordings at " + DateToStr, "Starting the test: TC7946ValidateChangesInAutomaticallyPublishStudentRecordingsSettingsDoesntChangeExistingStudentRecordings at " + DateToStr, LogAs.PASSED, null);	
	}

	@AfterClass
	public void closeBroswer() {
		this.driver.quit();
	}
		
	
	// @Parameters({"web","title"}) in the future
	@Test (description="TC7946 validate changes in automatically publish student recordings settings doesnt change existing student recordings")
	public void test7946() throws InterruptedException, ParseException {
		
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);	
		initializeCourseObject();
		
		//1.pre test upload record login as INSTRUCTOR
		tegrity.loginCourses("User4");
		
		//2.Open the course "Course details" page 
		course.selectCourseThatStartingWith("Ab");	
		
		//3.click on upload a recording
		record.clickOnCourseTaskThenUploadARecording();
		
		//4.upload keys
		record.uploadRecordingThruogthKeys();
		
		//5. check that the pending for record to upload is over	
		record.checkStatusExistenceForMaxTTime(450);
		
		//6. sign out
		record.signOut();
			
		//7.pre test upload record login as INSTRUCTOR
		tegrity.loginCourses("User1");
				
		//8.Open the course "Course details" page 
		course.selectCourseThatStartingWith("Ab");	
			
		//9.make Publish recordings after upload checked
		record.clickOnCourseTaskThenCourseSettings();
		course_settings_page.forceWebElementToBeUnselected(course_settings_page.checkbox_publish_after_upload, "Publish recordings after upload");
		course_settings_page.clickOnOkButton();
		
		//10.Click on "Student recordings" tab
		record.clickOnStudentRecordingsTab();
		
		//11.Validate the Students recordings you uploaded earlier are still published.
		record.verifyNoStatusInTheIndex(1);
		
	
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
	
	}

	// description = "get courses list"
	public void initializeCourseObject() throws InterruptedException {

		course = PageFactory.initElements(driver, CoursesHelperPage.class);
		course.courses = course.getCoursesListFromElement(course.course_list);
	}
}