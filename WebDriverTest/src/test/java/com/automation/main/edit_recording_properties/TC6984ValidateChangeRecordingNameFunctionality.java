package com.automation.main.edit_recording_properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.automation.main.page_helpers.AdminDashboardPage;
import com.automation.main.page_helpers.AdminDashboardViewCourseList;
import com.automation.main.page_helpers.CalendarPage;
import com.automation.main.page_helpers.ConfirmationMenu;
import com.automation.main.page_helpers.CopyMenu;
import com.automation.main.page_helpers.CoursesHelperPage;
import com.automation.main.page_helpers.EditRecordingPropertiesWindow;
import com.automation.main.page_helpers.LoginHelperPage;
import com.automation.main.page_helpers.RecordingHelperPage;
import com.automation.main.utilities.DriverSelector;

import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import atu.testng.reports.utils.Utils;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;
import junitx.util.PropertyManager;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;


@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC6984ValidateChangeRecordingNameFunctionality {


	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}
	
	public EditRecordingPropertiesWindow edit_recording_properties_window;
	public AdminDashboardViewCourseList admin_dashboard_view_course_list;
	public LoginHelperPage tegrity;
	public CoursesHelperPage course;
	public RecordingHelperPage record;
	public ConfirmationMenu confirm_menu;
	public CalendarPage calendarPage;
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
			confirm_menu = PageFactory.initElements(driver, ConfirmationMenu.class);
			edit_recording_properties_window = PageFactory.initElements(driver, EditRecordingPropertiesWindow.class);
			record = PageFactory.initElements(driver, RecordingHelperPage.class);
			copy = PageFactory.initElements(driver, CopyMenu.class);
		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: 6984ValidateChangeRecordingNameFunctionality at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC17000ValidateChangeRecordingNameFunctionality at " + DateToStr, "Starting the test: TC17000ValidateChangeRecordingNameFunctionality at " + DateToStr, LogAs.PASSED, null);	
	}

	@AfterClass
	public void closeBroswer() {
		this.driver.quit();
	}
		

	
	// @Parameters({"web","title"}) in the future
	@Test (description="6984 Validate Change Recording Name Functionality")
	public void test6984() throws InterruptedException, ParseException {
		
		
		
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
		
		//3.Check some recording respective checkbox 
		int recordNumber = record.checkExistenceOfNonEditRecordingsStatusInRecordings();
		record.selectIndexCheckBox(recordNumber);
		
		//get the recording length creator and date are the same as before the edit
		String recordLen = record.getTheRecordLengthByRecordIndex(recordNumber);
		String recordBy = record.getTheRecordedByRecordIndex(recordNumber);
		String recordeDate = record.getTheRecordingDateIndex(recordNumber);
		
		//4.click on the recording tasks->edit recording properties option
		record.toEditRecordingPropertiesMenu();
			
		//5.wait for edit reocrding properties window to open
		edit_recording_properties_window.waitForPageToLoad();
		
		//6.Click inside of the "Name" editbox type the text "<10 spaces>Change<10 spaces>recording name<10 spaces>"
		String newName = "Change recording name";
		edit_recording_properties_window.changeRecordingNameToTargetName(newName);
		
		//7.Click the "Save" button
		edit_recording_properties_window.clickOnSaveButton();
		
		//8.The model window is closed.
		edit_recording_properties_window.verifyConfirmWindowIsClosed();
		
		//9. The header background color is as the customize or defualt university background color.
		confirm_menu.verifyConfirmBackgroundColor(record);
			
		//10.The "Ok" Button is displayed on the bottom right corner of the model window.
		confirm_menu.verifyTheLocationOfTheOkButtonIsInTheButtomRight();
		
		//11.The "Edit Recording Properties" caption is displayed inside the header.
		//12.The informative text "Recording properties have been queued for edit" is displayed below the header.
		confirm_menu.clickOnOkButtonAfterConfirmEditRecordingProperties();
		
		//13.The second model window disappears.
		confirm_menu.verifyConfirmWindowIsClosed();
		
		//14.Validate the recording name has changed to "Change recording name" without any white spaces gaps.
		record.verifyThatTargetRecordingExistInRecordingList(newName);
		recordNumber = record.getIndexOfRecordFromRecordName(newName);
		
		//15.Validate the recording length, creator and date are the same as before the edit
		record.verifyThatTheRecordNameEqualsFromTheString(recordLen,recordNumber,"Record length");
		//record.verifyThatTheRecordNameEqualsFromTheString(recordBy,recordNumber,"Record creator");
		record.verifyThatTheRecordNameEqualsFromTheString(recordeDate,recordNumber,"Record date");
		
		//16.Validate the 'Recording is being' edited status disappears within maximum of 2 minutes
		if(type_of_user == 1){
			record.checkExistenceOfNonEditRecordingsStatusInTheIndex(recordNumber,120,"recodings");
		}else {
			record.checkExistenceOfNonEditRecordingsStatusInTheIndex(recordNumber,120,"student");
		}
		 
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

