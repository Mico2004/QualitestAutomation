package com.automation.main.edit_recording_properties;


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
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;


@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC6994ValidateRecordingDateChangeFunctionality {


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
		try {


			driver = DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());
			calendarPage = PageFactory.initElements(driver, CalendarPage.class);
			admin_dash_board_page = PageFactory.initElements(driver, AdminDashboardPage.class);
			admin_dashboard_view_course_list = PageFactory.initElements(driver, AdminDashboardViewCourseList.class);
			tegrity = PageFactory.initElements(driver, LoginHelperPage.class);
			confirm_menu = PageFactory.initElements(driver, ConfirmationMenu.class);
			course = PageFactory.initElements(driver, CoursesHelperPage.class);
			edit_recording_properties_window = PageFactory.initElements(driver, EditRecordingPropertiesWindow.class);
			record = PageFactory.initElements(driver, RecordingHelperPage.class);
			copy = PageFactory.initElements(driver, CopyMenu.class);
		} catch (Exception e) {
			ATUReports.add("Fail Step", LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
		}
		

		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC6994ValidateRecordingDateChangeFunctionality at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC6994ValidateRecordingDateChangeFunctionality at " + DateToStr, "Starting the test: TC6994ValidateRecordingDateChangeFunctionality at " + DateToStr, LogAs.PASSED, null);	
	}

	@AfterClass
	public void closeBroswer() {
		this.driver.quit();
	}
		
	// @Parameters({"web","title"}) in the future
	@Test (description="TC6994 validate recording date change functionality")
	public void test6994() throws InterruptedException, ParseException {
		
		
		//1.log in courses page
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);	
		tegrity.loginCourses("User1");
	
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
		//String recordBy = record.getTheRecordedByRecordIndex(recordNumber);
		String recordName = record.getTheRecordingNameIndex(recordNumber);
		
		//4.click on the recording tasks->edit recording properties option
		record.toEditRecordingPropertiesMenu();
			
		//5.wait for edit reocrding properties window to open
		edit_recording_properties_window.waitForPageToLoad();
			
		//6.Click inside of the "Date" editbox 
		//7.Pick [<today>- (2 days) ] date
		calendarPage.changeCreateDay(2);
		
		//8.The date is in the following format: 'XX/XX/XXXX'.
		String correctDate =edit_recording_properties_window.verifyThatTheCalendarInTheRightFormat();
			
		//9.Click the "Save" button
		edit_recording_properties_window.clickOnSaveButton();
				
		//10.The model window is closed.
		edit_recording_properties_window.verifyConfirmWindowIsClosed();
				
		//11. The header background color is as the customize or defualt university background color.
		confirm_menu.verifyConfirmBackgroundColor(record);
					
		//12.The "Ok" Button is displayed on the bottom right corner of the model window.
		confirm_menu.verifyTheLocationOfTheOkButtonIsInTheButtomRight();
				
		//13.The "Edit Recording Properties" caption is displayed inside the header.
		//14.The informative text "Recording properties have been queued for edit" is displayed below the header.
		confirm_menu.clickOnOkButtonAfterConfirmEditRecordingProperties();
				
		//15.The second model window disappears.
		confirm_menu.verifyConfirmWindowIsClosed();
		
		//refresh the website to check bug
		record.refresh();
		record.waitForThePageToLoad();
		if(type_of_user == 1){
			record.clickOnStudentRecordingsTab();
		}
		
		//16.Validate the recording creation date has changed to the date you selected earlier
		recordNumber = record.getIndexOfRecordFromRecordName(recordName);
		record.verifyThatTheRecordNameEqualsFromTheString(correctDate,recordNumber,"Record date");
		
		//17.Validate the recording name, creator and duration are the same as before the edit
		record.verifyThatTheRecordNameEqualsFromTheString(recordName,recordNumber,"Record name");
		record.verifyThatTheRecordNameEqualsFromTheString(recordLen,recordNumber,"Record length");
		//record.verifyThatTheRecordNameEqualsFromTheString(recordBy,recordNumber,"Record creator");
		
	}
		
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
	}

}

