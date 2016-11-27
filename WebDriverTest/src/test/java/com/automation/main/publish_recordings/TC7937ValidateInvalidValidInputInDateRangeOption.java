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
public class TC7937ValidateInvalidValidInputInDateRangeOption {

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
		 System.out.println("Starting the test: TC7937ValidateInvalidValidInputInDateRangeOption at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC7937ValidateInvalidValidInputInDateRangeOption at " + DateToStr, "Starting the test: TC7937ValidateInvalidValidInputInDateRangeOption at " + DateToStr, LogAs.PASSED, null);	
	}

	@AfterClass
	public void closeBroswer() {
		this.driver.quit();
	}
		
	
	// @Parameters({"web","title"}) in the future
	@Test (description="TC7937 validate invalid / valid input in date range option")
	public void test7937() throws InterruptedException, ParseException {
		
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);	
		initializeCourseObject();
		
		//1.pre test login as INSTRUCTOR
		tegrity.loginCourses("User1");
		
		//2.Open the course "Course details" page 
		course.selectCourseThatStartingWith("Ab");	
		
		//3.make Publish recordings after upload checked
		record.clickOnCourseTaskThenCourseSettings();
		course_settings_page.forceWebElementToBeSelected(course_settings_page.checkbox_publish_after_upload, "Publish recordings after upload");
		course_settings_page.clickOnOkButton();
		
		//4.check several recordings respective checkboxes
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
		
		//5.Click the "Recording Tasks" drop-down menu and choose the "Publish" option
		record.clickOnRecordingTaskThenPublish();
				
		//6.Select the "Never" radio box
		publish_window.waitForPageToLoad();
		publish_window.chooseRadioButton("Always");
		
		//7.Press the "Save" button
		publish_window.clickOnSaveButton();
		publish_window.verifyPublishWindowClosed();
				
		//8. signout
		record.signOut();

		//9.login as INSTRUCTOR
		tegrity.loginCourses("User1");
			
		//10.Open the course "Course details" page 
		course.selectCourseThatStartingWith("Ab");	
				
		//11.Verify that selected recording doesn't have any status
		record.verifyNoStatusInTheIndex(1);
		
		//12.Check the checkbox of the recording you uploaded earlier.
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
						
		//13.Click the "Recording Tasks" drop-down menu and choose the "Publish" option
		record.clickOnRecordingTaskThenPublish();
	
		//14.The "Publish" window is displayed.
		publish_window.waitForPageToLoad();
		publish_window.verifyPublishWindowOpen();
			
		//15.The "Always" radio button is on.
		publish_window.verifyThatAlwaysOptionSelectedOrNotSelected(1);

		//16.Change the "Always" radio button to on mode
		publish_window.chooseRadioButton("Date Range");
			
		//17."Date Range" radio box is checked
		publish_window.verifyThatDateRangeOptionSelectedOrNotSelected(1);
		
		//18. Click the left "Select Date" text box
		//19. Choose the current date
		publish_window.clickElement(publish_window.start);
		String date = calendarPage.changeCreateDayWithoutDayPickerActive(0,publish_window.titleOfCalenderStart,byStart);
		
		//20. The textbox text changes to the selected date in the following format: XX/XX/XXXX or x/x/xxxx
		publish_window.verifyThatTheCalendarInTheRightFormat(publish_window.start_date);
		
		//21. Click the right "Select Date" text box
		//22. Choose the current date
		publish_window.clickElement(publish_window.end);
		date = calendarPage.changeCreateDayWithoutDayPickerActive(1,publish_window.titleOfCalenderEnd,byEnd);
				
		//23. The textbox text changes to the selected date in the following format: XX/XX/XXXX or x/x/xxxx
		publish_window.verifyThatTheCalendarInTheRightFormat(publish_window.end_date);
			
		//24.Click on the "Save" button
		publish_window.clickOnSaveButtonWithOutCloseTheWindow();
		
		//25.The following message is displayed below the publish option: "Please enter an end-date that is after or the same as the beginning date".
		publish_window.verifyErrorTitleWrongDatesDisplayedAndValid();
		
		//26. Click the left "Select Date" text box
		//27. Delete the left textbox content
		publish_window.clickElement(publish_window.start);
		publish_window.clearTheDateOfTheWebElement(publish_window.start_date);
		
		//26. Click the right "Select Date" text box
		//27. Delete the right textbox content
		publish_window.clickElement(publish_window.end);
		publish_window.clearTheDateOfTheWebElement(publish_window.end_date);
		
		//28.Click on the "Save" button
		publish_window.clickOnSaveButtonWithOutCloseTheWindow();
				
		//29.The following message is displayed below the publish option: "Please enter an end-date that is after or the same as the beginning date".
		publish_window.verifyErrorTitleMissingDatesDisplayedAndValid();
			
		//30. Click the left "Select Date" text box
		//31. Choose the current date
		publish_window.clickElement(publish_window.start);
		date = calendarPage.changeCreateDayWithoutDayPickerActive(0,publish_window.titleOfCalenderStart,byStart);
		
		//32. The textbox text changes to the selected date in the following format: XX/XX/XXXX or x/x/xxxx
		publish_window.verifyThatTheCalendarInTheRightFormat(publish_window.start_date);
		
		//33.Click on the "Save" button
		publish_window.clickOnSaveButtonWithOutCloseTheWindow();
						
		//34.The following message is displayed below the publish option: "Please enter an end-date that is after or the same as the beginning date".
		publish_window.verifyErrorTitleMissingDatesDisplayedAndValid();
		
		//35. Click the left "Select Date" text box
		//36. Delete the left textbox content
		publish_window.clickElement(publish_window.start);
		publish_window.clearTheDateOfTheWebElement(publish_window.start_date);
		
		//37. Click the left "Select Date" text box
		//38. Choose the current date
		publish_window.clickElement(publish_window.end);
		date = calendarPage.changeCreateDayWithoutDayPickerActive(0,publish_window.titleOfCalenderEnd,byEnd);
		
		//39. The textbox text changes to the selected date in the following format: XX/XX/XXXX or x/x/xxxx
		publish_window.verifyThatTheCalendarInTheRightFormat(publish_window.end_date);
		
		//40.Click on the "Save" button
		publish_window.clickOnSaveButtonWithOutCloseTheWindow();
						
		//41.The following message is displayed below the publish option: "Please enter an end-date that is after or the same as the beginning date".
		publish_window.verifyErrorTitleMissingDatesDisplayedAndValid();
		
		//42. Click the left "Select Date" text box
		//43. Choose the current date
		publish_window.clickElement(publish_window.start);
		date = calendarPage.changeCreateDayWithoutDayPickerActive(1,publish_window.titleOfCalenderStart,byStart);
		
		//44. The textbox text changes to the selected date in the following format: XX/XX/XXXX or x/x/xxxx
		publish_window.verifyThatTheCalendarInTheRightFormat(publish_window.start_date);
		
		//45. Click the left "Select Date" text box
		//46. Choose the current date
		publish_window.clickElement(publish_window.end);
		date = calendarPage.changeCreateDayWithoutDayPickerActive(2,publish_window.titleOfCalenderEnd,byEnd);
		
		//47. The textbox text changes to the selected date in the following format: XX/XX/XXXX or x/x/xxxx
		publish_window.verifyThatTheCalendarInTheRightFormat(publish_window.end_date);
		
		//48.Click on the "Save" button
		publish_window.clickOnSaveButtonWithOutCloseTheWindow();
								
		//49.The following message is displayed below the publish option: "Please enter an end-date that is after or the same as the beginning date".
		publish_window.verifyErrorTitleWrongDatesDisplayedAndValid();
		
		//50.Change both of the "Select Date" textboxes text to "12345"
		publish_window.sendKeysToWebElementInput(publish_window.start_date, "2212345");
		publish_window.sendKeysToWebElementInput(publish_window.end_date, "2212345");
			
		//51.Click on the "Save" button
		publish_window.clickOnSaveButtonWithOutCloseTheWindow();
								
		//52.The following message is displayed below the publish option: "Please enter an end-date that is after or the same as the beginning date".
		publish_window.verifyErrorTitleInvalidDatesDisplayedAndValid();
		
		//53. Click the left "Select Date" text box
		//54. Choose the current date
		publish_window.clearTheDateOfTheWebElement(publish_window.start_date);
		publish_window.clickElement(publish_window.start);
		date = calendarPage.changeCreateDayWithoutDayPickerActive(2,publish_window.titleOfCalenderStart,byStart);
				
		//55. The textbox text changes to the selected date in the following format: XX/XX/XXXX or x/x/xxxx
		publish_window.verifyThatTheCalendarInTheRightFormat(publish_window.start_date);
				
		//56. Click the left "Select Date" text box
		//57. Choose the current date
		publish_window.clearTheDateOfTheWebElement(publish_window.end_date);
		publish_window.clickElement(publish_window.end);
		date = calendarPage.changeCreateDayWithoutDayPickerActive(1,publish_window.titleOfCalenderEnd,byEnd);
				
		//58. The textbox text changes to the selected date in the following format: XX/XX/XXXX or x/x/xxxx
		publish_window.verifyThatTheCalendarInTheRightFormat(publish_window.end_date);
				
		//59.Click on the "Save" button
		publish_window.clickOnSaveButton();
			
		//60."Publish" window is closed
		publish_window.verifyPublishWindowClosed();
		
		//61.Verify that selected recording has "Not Published" status		
		record.veirfyStatusNotPublishOnTheFirstRecord();
		
		//62.Sign out
		record.signOut();
		
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
	
	}

	// description = "get courses list"
	public void initializeCourseObject() throws InterruptedException {

		course = PageFactory.initElements(driver, CoursesHelperPage.class);
		course.courses = course.getCoursesListFromElement(course.course_list);
	}
}