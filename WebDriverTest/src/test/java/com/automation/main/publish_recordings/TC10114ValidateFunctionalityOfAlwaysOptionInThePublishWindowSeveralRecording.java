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
public class TC10114ValidateFunctionalityOfAlwaysOptionInThePublishWindowSeveralRecording {

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
	public String firstRecordNameRecording;
	public String firstRecordNameStudent;
	
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
		 System.out.println("Starting the test: TC10114ValidateFunctionalityOfAlwaysOptionInThePublishWindowSeveralRecording at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC10114ValidateFunctionalityOfAlwaysOptionInThePublishWindowSeveralRecording at " + DateToStr, "Starting the test: TC10114ValidateFunctionalityOfAlwaysOptionInThePublishWindowSeveralRecording at " + DateToStr, LogAs.PASSED, null);	
	}

	@AfterClass
	public void closeBroswer() {
		this.driver.quit();
	}
		
	// @Parameters({"web","title"}) in the future
	@Test (description="TC10114 Validate functionality of always option in the publish window several recording")
	public void test10114() throws InterruptedException, ParseException {
		
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);	
		initializeCourseObject();
		
		//1.pre test login as INSTRUCTOR
		tegrity.loginCourses("User1");
		
		//2.Open the course "Course details" page 
		String current_course = course.selectCourseThatStartingWith("Ab");

		for(int type_of_tab = 0; type_of_tab < 2; type_of_tab++) {
			
			if(type_of_tab == 1){
				record.clickOnStudentRecordingsTab();
			}
		//3.check several recordings respective checkboxes
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkall);
		
		//4.Click the "Recording Tasks" drop-down menu and choose the "Publish" option
		record.clickOnRecordingTaskThenPublish();
				
		//5.Select the "Always" radio box
		publish_window.waitForPageToLoad();
		publish_window.verifyPublishWindowOpen();
		publish_window.chooseRadioButton("Always");
		
		//6.Press the "Save" button
		publish_window.clickOnSaveButton();
		publish_window.verifyPublishWindowClosed();
		
		//7.The recordings "Not Publish" status changed to "".
		record.checkStatusExistenceUnpublish();
			
		}
		
		//8. signout
		record.signOut();

		//9. Login as Student.
		tegrity.loginCourses("User4");		
		
		//10. Open some course.
		course.selectCourseThatStartingWith(current_course);
	
		for(int type_of_tab = 0; type_of_tab < 2; type_of_tab++) {
			
			if(type_of_tab == 1){
				record.clickOnStudentRecordingsTab();
			}
		
		//11.Validate the recording you uploaded earlier is displayed with "Not Published" label.
		record.checkStatusExistenceUnpublish();

		//12.Open the recording playback
		record.verifyFirstExpandableRecording();
		record.clickOnTheFirstCaptherWithOutTheExpand();
		
		//13.verify recording displaying correctly
		player_page.verifyTimeBufferStatusForXSec(2);// check source display
		player_page.exitInnerFrame();
		
		player_page.returnToRecordingPageByNameAsUserOrGuest(current_course, record);
		
		}
		
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
	
	}
	
	// description = "get courses list"
	public void initializeCourseObject() throws InterruptedException {

		course = PageFactory.initElements(driver, CoursesHelperPage.class);
		course.courses = course.getCoursesListFromElement(course.course_list);
	}
}