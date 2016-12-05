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
import junitx.util.PropertyManager;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;


@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC7315ValidateDiscardingChangesFunctionlity  {


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
		 System.out.println("Starting the test: TC7315ValidateDiscardingChangesFunctionlity at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC7315ValidateDiscardingChangesFunctionlity at " + DateToStr, "Starting the test: TC7315ValidateDiscardingChangesFunctionlity at " + DateToStr, LogAs.PASSED, null);	
	}

	@AfterClass
	public void closeBroswer() {
		this.driver.quit();
	}
		
	// @Parameters({"web","title"}) in the future
	@Test (description="TC7315 Validate discarding changes functionlity")
	public void test7315() throws InterruptedException, ParseException {
		
		
		//1.log in courses page
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);	
		tegrity.loginCourses("User1");
		initializeCourseObject();
		
		//2.open some course whom listed you as instructor
		course.selectCourseThatStartingWith("Ab");
			
		for(int type_of_function = 0; type_of_function < 2; type_of_function++) {
		
	   //3.Check some recording respective checkbox 
		int recordNumber = record.checkExistenceOfNonEditRecordingsStatusInRecordings();
		record.unselectallCheckbox();
		record.selectIndexCheckBox(recordNumber);
			
			
		//4.click on the recording tasks->edit recording properties option
		record.toEditRecordingPropertiesMenu();
			
		//5.wait for edit reocrding properties window to open
		edit_recording_properties_window.waitForPageToLoad();
			
		//6.Type some other name to the "Name" edit box.
		String name = "newNameForTesting";
		edit_recording_properties_window.changeRecordingNameToTargetName(name);
		
		//7.Click on the "Cancel" button or click the grey area outside of the model window*
		if(type_of_function == 0 ){
			edit_recording_properties_window.clickOnCancelButton();
		} else {
			edit_recording_properties_window.clickOnTheGreyArea();
		}
		
		//8.Validate the Recording name didn't changed.
		record.verifyThatTargetRecordingNotExistInRecordingList(name);
					
		//9.Check some recording respective checkbox 
		recordNumber = record.checkExistenceOfNonEditRecordingsStatusInRecordings();
		record.unselectallCheckbox();
		record.selectIndexCheckBox(recordNumber);
		
		//get the recording date for using later
		String recordDate = record.getTheRecordingDateIndex(recordNumber);
	
		//10.click on the recording tasks->edit recording properties option
		record.toEditRecordingPropertiesMenu();
					
		//11.wait for edit reocrding properties window to open
		edit_recording_properties_window.waitForPageToLoad();
		
		//12.Select some other Creation date.
		calendarPage.changeCreateDay(2);
				
		//13.Click on the "Cancel" button or click the grey area outside of the model window*
		if(type_of_function == 0 ){
			edit_recording_properties_window.clickOnCancelButton();
		} else {
			edit_recording_properties_window.clickOnTheGreyArea();
		}
		
		//14.The recording Creation date han't changed.
		record.verifyThatTheRecordNameEqualsFromTheString(recordDate,recordNumber,"Record date");
				
		//15.Check some recording respective checkbox 
		recordNumber = record.checkExistenceOfNonEditRecordingsStatusInRecordings();
		record.unselectallCheckbox();
		record.selectIndexCheckBox(recordNumber);
				
		//get the recording owner for using later
		String recordBy = record.getTheRecordedByRecordIndex(recordNumber);
			
		//16.click on the recording tasks->edit recording properties option
		record.toEditRecordingPropertiesMenu();
				
		//17.wait for edit reocrding properties window to open
		edit_recording_properties_window.waitForPageToLoad();
	
		//18.Choose some other owner from the owner drop down list
		edit_recording_properties_window.changeOwner(PropertyManager.getProperty("User2"));
		
		//19.Click on the "Cancel" button or click the grey area outside of the model window*
		if(type_of_function == 0 ){
			edit_recording_properties_window.clickOnCancelButton();
		} else {
			edit_recording_properties_window.clickOnTheGreyArea();
		}
		
		//20.Validate the recording "Recorded by" value hasn't been changed.
		record.verifyThatTheRecordNameEqualsFromTheString(recordBy,recordNumber,"Record creator");
		
		//21.Check some recording respective checkbox 
		recordNumber = record.checkExistenceOfNonEditRecordingsStatusInRecordings();
		record.unselectallCheckbox();
		record.selectIndexCheckBox(recordNumber);
						
		//get the recording name for using later
		 String recordName = record.getTheRecordingNameIndex(recordNumber);
					
		//22.click on the recording tasks->edit recording properties option
		record.toEditRecordingPropertiesMenu();
						
		//23.wait for edit reocrding properties window to open
		edit_recording_properties_window.waitForPageToLoad();
		
		//24.Choose some other type from the type drop down list
		edit_recording_properties_window.ChooseDiffrenetType("Student recording");
				
		//25.Click on the "Cancel" button or click the grey area outside of the model window*
		if(type_of_function == 0 ){
			edit_recording_properties_window.clickOnCancelButton();
		} else {
			edit_recording_properties_window.clickOnTheGreyArea();
		}
							
		//26.Validate the recording is still displayed on the "Recording" tab content.
		record.verifyThatTheRecordNameEqualsFromTheString(recordName,recordNumber,"Record name");
		
		//27.Check some recording respective checkbox 
		recordNumber = record.checkExistenceOfNonEditRecordingsStatusInRecordings();
		record.unselectallCheckbox();
		record.selectIndexCheckBox(recordNumber);
		
		//get the recording name for using later
		 recordName = record.getTheRecordingNameIndex(recordNumber);
		 recordBy = record.getTheRecordedByRecordIndex(recordNumber);
		 recordDate = record.getTheRecordingDateIndex(recordNumber);
		
		//28.click on the recording tasks->edit recording properties option
		record.toEditRecordingPropertiesMenu();
					
		//29.wait for edit reocrding properties window to open
		edit_recording_properties_window.waitForPageToLoad();
		
		//30.Type some other name to the "Name" edit box.
		name = "newNameForTesting";
		edit_recording_properties_window.changeRecordingNameToTargetName(name);
		
		//31.Select some other Creation date.
		calendarPage.changeCreateDay(2);
		
		//32.Choose some other owner from the owner drop down list
		edit_recording_properties_window.changeOwner(PropertyManager.getProperty("User2"));
		
		//33.Choose some other type from the type drop down list
		edit_recording_properties_window.ChooseDiffrenetType("Student recording");
		
		//34.Click on the "Cancel" button or click the grey area outside of the model window*
		if(type_of_function == 0 ){
			edit_recording_properties_window.clickOnCancelButton();
		} else {
			edit_recording_properties_window.clickOnTheGreyArea();
		}
		
		//35.Validate the recording is still displayed on the "Recording" tab content.
		record.verifyThatTheRecordNameEqualsFromTheString(recordName,recordNumber,"Record name");
		
		//36.The recording Creation date han't changed.
		record.verifyThatTheRecordNameEqualsFromTheString(recordDate,recordNumber,"Record date");
		
		//37.Validate the recording "Recorded by" value hasn't been changed.
		record.verifyThatTheRecordNameEqualsFromTheString(recordBy,recordNumber,"Record creator");
		
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

