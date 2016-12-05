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
import com.automation.main.page_helpers.CoursesHelperPage;
import com.automation.main.page_helpers.EditRecordingPropertiesWindow;
import com.automation.main.page_helpers.LoginHelperPage;
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
public class TC7894ValidatePublishRecordingUI {

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

		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC7894ValidatePublishRecordingUI at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC7894ValidatePublishRecordingUI at " + DateToStr, "Starting the test: TC7894ValidatePublishRecordingUI at " + DateToStr, LogAs.PASSED, null);	
	}

	@AfterClass
	public void closeBroswer() {
		this.driver.quit();
	}
		
	
	// @Parameters({"web","title"}) in the future
	@Test (description="TC7894 validate publish recording UI")
	public void test7894() throws InterruptedException, ParseException {
		
		
		//1.log in courses page
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);	
		tegrity.loginCourses("User1");
		initializeCourseObject();
		
		//2.open some course whom listed you as instructor
		course.selectCourseThatStartingWith("Ab");
			
		for(int type_of_user = 0; type_of_user < 2; type_of_user++) {
			
			if(type_of_user == 1){
				record.clickOnStudentRecordingsTab();
			}
		
		//3.click on the recording tasks drop_down button
		record.mouseHoverJScript(record.recording_tasks_button);
		
		//4. verify that the publish button option is greyed out and disable
		record.verifyRecordingMenuColor(record.publish_button);

		//5.check several recordings respective checkboxes
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
		
		//6.Click the "Recording Tasks" drop-down menu and choose the "Publish" option
		record.clickOnRecordingTaskThenPublish();
			
		//7.Validate "Publish" window content is displayed correctly
		//The window title is "Publish".
		publish_window.waitForPageToLoad();
		publish_window.verifyPublishWindowTitle();
	
		//8.window title background is as the color selected for university
		publish_window.verifyPublishColor(record);
		
		//9. There shall be an informative text displayed below the window title: "Publishing allows you to control when recordings are made available to students".
		publish_window.verifyInfomativeText();
			
		//10.The text "When would you like the selected recordings to be available?" shall be displayed below.
		publish_window.verifyInfomativeTextAndVerifyBelowTheOtherInfoText();
		
		//11.The list of available options shall be displayed next.
		publish_window.verifyThatTheRadioButtonsWillDisplayBelow();
		
		//12.The "Save" button is displayed at the bottom right of the window.
		//13.The "Cancel" button is displayed left to the "Save" button.
		publish_window.VerifyTheLocationOfTheSaveAndCancel();
		
		//14.Validate the content of the available publishing options
		publish_window.VerifyTheContentOfTheAvailablePublishingOptions();
		
		//15.Click on the "From" text box
		//16. The current month is presented - year-month in the format of (xxxx)-(yyy)
		publish_window.verifyThatAfterClickingOnTheFromTheCalenderWidgetIsDisplayed(publish_window.start,publish_window.calenderStart);
		calendarPage.verifyThatFormatOfTheMonthAndYear(publish_window.titleOfCalenderStart);
		
		//15.Click on the "From" text box
		//16. The current month is presented - year-month in the format of (xxxx)-(yyy)
		publish_window.verifyThatAfterClickingOnTheFromTheCalenderWidgetIsDisplayed(publish_window.end,publish_window.calenderEnd);
		calendarPage.verifyThatFormatOfTheMonthAndYear(publish_window.titleOfCalenderEnd);
		publish_window.clickOnCancelButton();
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