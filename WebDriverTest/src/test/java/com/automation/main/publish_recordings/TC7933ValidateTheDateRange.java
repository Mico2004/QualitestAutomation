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
public class TC7933ValidateTheDateRange {

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
		 System.out.println("Starting the test: TC7933ValidateTheDateRange at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC7933ValidateTheDateRange at " + DateToStr, "Starting the test: TC7933ValidateTheDateRange at " + DateToStr, LogAs.PASSED, null);	
	}

	@AfterClass
	public void closeBroswer() {
		this.driver.quit();
	}
		
	
	// @Parameters({"web","title"}) in the future
	@Test (description="TC7933 Validate The Date Range")
	public void test7933() throws InterruptedException, ParseException {
		
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);	
		initializeCourseObject();
		
		//1.pre test login as INSTRUCTOR
		tegrity.loginCourses("User1");
		
		//2.Make sure to have a unpublished regular and student recording
		course.selectCourseThatStartingWith("Ab");	
		
		//3.check several recordings respective checkboxes
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
				
		//4.Click the "Recording Tasks" drop-down menu and choose the "Publish" option
		record.clickOnRecordingTaskThenPublish();
		
		//5.Select the "Date Range" radio box
		publish_window.waitForPageToLoad();
		publish_window.verifyPublishWindowOpen();
		
		publish_window.chooseRadioButton("Date Range");
		
		//6."Date Range" radio box is checked
		publish_window.verifyThatDateRangeOptionSelectedOrNotSelected(1);
		
		//7. In left calendar select the passed date
		publish_window.clickElementJS(publish_window.start);
		String date = calendarPage.changeCreateDayWithoutDayPickerActive(2,publish_window.titleOfCalenderStart,byStart ,calendarPage.arrowRight , calendarPage.arrowLeft);
		
		//8.Selected date is displayed in left textbox
		publish_window.veirfyDateFromThisDate(date,publish_window.start_date);
		
		//9. In right calendar select the passed date
		publish_window.clickElementJS(publish_window.end);
		date = calendarPage.changeCreateDayWithoutDayPickerActive(1,publish_window.titleOfCalenderEnd,byEnd ,calendarPage.arrowRightPublishRight , calendarPage.arrowLeftPublishRight);
		
		//10.Selected date is displayed in right textbox
		publish_window.veirfyDateFromThisDate(date,publish_window.end_date);
		
		//11.Press the "Save" button
		publish_window.clickOnSaveButton();
		
		//12."Publish" window is closed
		publish_window.verifyPublishWindowClosed();
		
		//13.Verify that selected recording has "Not Published" status		
		record.veirfyStatusNotPublishOnTheFirstRecord();
		
		//14.check several recordings respective checkboxes
		record.unselectallCheckbox();
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
						
		//15.Click the "Recording Tasks" drop-down menu and choose the "Publish" option
		record.clickOnRecordingTaskThenPublish();
					
		//16"Publish" modal window is displayed
		publish_window.waitForPageToLoad();
		publish_window.verifyPublishWindowOpen();
		
		//16.Select the "Date Range" radio box
		publish_window.chooseRadioButton("Date Range");
		
		//17. In right calendar select the passed date
		publish_window.clickElementJS(publish_window.end);
		date = calendarPage.changeCreateDayWithoutDayPickerActive(-2,publish_window.titleOfCalenderEnd,byEnd,calendarPage.arrowRightPublishRight , calendarPage.arrowLeftPublishRight);
				
		//18.Selected date is displayed in right textbox
		publish_window.veirfyDateFromThisDate(date,publish_window.end_date);
		
		//19.Press the "Save" button
		publish_window.clickOnSaveButton();
				
		//20."Publish" window is closed
		publish_window.verifyPublishWindowClosed();
		
		//21.Verify that selected recording doesn't have any status
		record.verifyNoStatusInTheIndex(1);
		
		//22.check several recordings respective checkboxes
		record.unselectallCheckbox();
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
								
		//23.Click the "Recording Tasks" drop-down menu and choose the "Publish" option
		record.clickOnRecordingTaskThenPublish();
							
		//24."Publish" modal window is displayed
		publish_window.waitForPageToLoad();
		publish_window.verifyPublishWindowOpen();
				
		//25.Select the "Date Range" radio box
		publish_window.chooseRadioButton("Date Range");
				
		//26. In right calendar select the passed date
		publish_window.clickElement(publish_window.start_date);
		date = calendarPage.changeCreateDayWithoutDayPickerActive(-1,publish_window.titleOfCalenderStart,byStart , calendarPage.arrowRight , calendarPage.arrowLeft);
						
		//27.Selected date is displayed in right textbox
		publish_window.veirfyDateFromThisDate(date,publish_window.start_date);
				
		//28.Press the "Save" button
		publish_window.clickOnSaveButton();
						
		//29."Publish" window is closed
		publish_window.verifyPublishWindowClosed();
		
		//30.Verify that selected recording doesn't have any status
		record.veirfyStatusNotPublishOnTheFirstRecord();
		
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
	
	}

	// description = "get courses list"
	public void initializeCourseObject() throws InterruptedException {

		course = PageFactory.initElements(driver, CoursesHelperPage.class);
		course.courses = course.getCoursesListFromElement(course.course_list);
	}
}