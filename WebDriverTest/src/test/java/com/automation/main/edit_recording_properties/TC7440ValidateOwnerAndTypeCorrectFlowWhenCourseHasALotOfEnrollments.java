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
import com.automation.main.page_helpers.EditRecordinPropertiesWindow;
import com.automation.main.page_helpers.LoginHelperPage;
import com.automation.main.page_helpers.RecordingHelperPage;
import com.automation.main.utilities.DriverSelector;
import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;


@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC7440ValidateOwnerAndTypeCorrectFlowWhenCourseHasALotOfEnrollments {


	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}
	
	public EditRecordinPropertiesWindow edit_recording_properties_window;
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
		try {


			driver = DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());
			calendarPage = PageFactory.initElements(driver, CalendarPage.class);
			admin_dash_board_page = PageFactory.initElements(driver, AdminDashboardPage.class);
			admin_dashboard_view_course_list = PageFactory.initElements(driver, AdminDashboardViewCourseList.class);
			tegrity = PageFactory.initElements(driver, LoginHelperPage.class);
			confirm_menu = PageFactory.initElements(driver, ConfirmationMenu.class);
			edit_recording_properties_window = PageFactory.initElements(driver, EditRecordinPropertiesWindow.class);
			record = PageFactory.initElements(driver, RecordingHelperPage.class);
			copy = PageFactory.initElements(driver, CopyMenu.class);
		} catch (Exception e) {
			ATUReports.add("Fail Step", LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
		}
		

		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC7440ValidateOwnerAndTypeCorrectFlowWhenCourseHasALotOfEnrollments at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC7440ValidateOwnerAndTypeCorrectFlowWhenCourseHasALotOfEnrollments at " + DateToStr, "Starting the test: TC7440ValidateOwnerAndTypeCorrectFlowWhenCourseHasALotOfEnrollments at " + DateToStr, LogAs.PASSED, null);	
	}

	@AfterClass
	public void closeBroswer() {
		this.driver.quit();
	}
		

	
	// @Parameters({"web","title"}) in the future
	@Test (description="TC7440 Validate Owner and type correct flow when course has a lot of enrollments")
	public void test7440() throws InterruptedException, ParseException {
		
		
		//1.log in courses page
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);	
		
		//Login as INSTRUCTOR
		tegrity.loginCourses("User1");
		initializeCourseObject();
		
		//2.open some course whom listed you as instructor
		course.selectCourseThatStartingWith("Ab");
		
		//3.Check some recording respective checkbox 
		int recordNumber = record.checkExistenceOfNonEditRecordingsStatusInRecordings();
		record.selectIndexCheckBox(recordNumber);
		
		//get the recording length creator and date are the same as before the edit;
		String recordBy = record.getTheRecordedByRecordIndex(recordNumber);
		String recordeDate = record.getTheRecordingDateIndex(recordNumber);
		
		//4.click on the recording tasks->edit recording properties option
		record.toEditRecordingPropertiesMenu();
			
		//5.wait for edit reocrding properties window to open
		edit_recording_properties_window.waitForPageToLoad();
		
		///6.Type some other name to the "Name" edit box.
		String name = "newNameForTesting2";
		edit_recording_properties_window.changeRecordingNameToTargetName(name);
		
		//7.Select some other Creation date.
		calendarPage.changeCreateDay(2);
		
		//get the new date for later
		String correctDate =edit_recording_properties_window.verifyThatTheCalendarInTheRightFormat();
		
		//8.Choose some other type from the type drop down list
		edit_recording_properties_window.ChooseDiffrenetType("Student recording");
		
		//9.Choose some other owner from the owner drop down list
		String newOwner = edit_recording_properties_window.clickOnDifferentOwnerThatTheExist(recordBy);
				
		//10.Click the "Save" button
		edit_recording_properties_window.clickOnSaveButton();
		
		//11.The model window is closed.
		edit_recording_properties_window.verifyConfirmWindowIsClosed();
		
		//12. The header background color is as the customize or defualt university background color.
		confirm_menu.verifyConfirmBackgroundColor(record);
			
		//13.The "Ok" Button is displayed on the bottom right corner of the model window.
		confirm_menu.verifyTheLocationOfTheOkButtonIsInTheButtomRight();
		
		//14.The "Edit Recording Properties" caption is displayed inside the header.
		//15.The informative text "Recording properties have been queued for edit" is displayed below the header.
		confirm_menu.clickOnOkButtonAfterConfirmEditRecordingProperties();
		
		//16.The second model window disappears.
		confirm_menu.verifyConfirmWindowIsClosed();
		
		//17.Validate the recording has been passed to "Student Recordings" tab
		record.verifyThatTargetRecordingNotExistInRecordingList(name);
		
		//19.Select "Students Recordings" tab
		record.clickOnStudentRecordingsTab();
		
		//20.Validate the Recording name has changed.
		record.verifyThatTargetRecordingExistInRecordingList(name);
		recordNumber = record.getIndexOfRecordFromRecordName(name);
			
		//21.Validate the recording "Recorded by" value has been changed.
		record.verifyThatTheRecordNameEqualsFromTheString(newOwner,recordNumber,"Record creator");
		
		//22.Validate the recording "Recording Date" has been changed.
		record.verifyThatTheRecordNameEqualsFromTheString(correctDate,recordNumber,"Record date");
		
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
	}

	// description = "get courses list"
	public void initializeCourseObject() throws InterruptedException {

		course = PageFactory.initElements(driver, CoursesHelperPage.class);
		course.courses = course.getCoursesListFromElement(course.course_list);
	}
}

