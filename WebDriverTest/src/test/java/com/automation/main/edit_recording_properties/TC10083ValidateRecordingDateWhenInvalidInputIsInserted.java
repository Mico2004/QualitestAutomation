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
import atu.testng.reports.utils.Utils;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;
import junitx.util.PropertyManager;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;


@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC10083ValidateRecordingDateWhenInvalidInputIsInserted {


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
		 System.out.println("Starting the test: TC10083ValidateRecordingDateWhenInvalidInputIsInserted at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC10083ValidateRecordingDateWhenInvalidInputIsInserted at " + DateToStr, "Starting the test: TC10083ValidateRecordingDateWhenInvalidInputIsInserted at " + DateToStr, LogAs.PASSED, null);	
	}

	@AfterClass
	public void closeBroswer() {
		this.driver.quit();
	}
		
	// @Parameters({"web","title"}) in the future
	@Test (description="TC10083 validate recording date change functionality")
	public void test10083() throws InterruptedException, ParseException {
		
		
		//1.log in courses page
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);	
		tegrity.loginCourses("User1");
		initializeCourseObject();
		
		//2.open some course whom listed you as instructor
		course.selectCourseThatStartingWith("Ab");
				
		//3.Check some recording respective checkbox 
		int recordNumber = record.checkExistenceOfNonEditRecordingsStatusInRecordings();
		record.selectIndexCheckBox(recordNumber);

		//4.click on the recording tasks->edit recording properties option
		record.toEditRecordingPropertiesMenu();
			
		//5.wait for edit reocrding properties window to open
		edit_recording_properties_window.waitForPageToLoad();
		
		//6.Click inside of the "Date" editbox 
		edit_recording_properties_window.clickElementJS(edit_recording_properties_window.date_Field);	
		
		//7.Type "32/01/2000" inside the date text box
		edit_recording_properties_window.changeDate("01/32/2000");
		
		//8.The save button is disabled
		edit_recording_properties_window.verifySaveButtonDisable();
		
		//9.Type "01/13/2000" inside the date text box
		edit_recording_properties_window.changeDate("13/01/2000");
				
		//10.The save button is disabled
		edit_recording_properties_window.verifySaveButtonDisable();
		
		//11.Type "00/01/2000" inside the date text box
		edit_recording_properties_window.changeDate("00/01/2000");
				
		//12.The save button is disabled
		edit_recording_properties_window.verifySaveButtonDisable();
				
		//13.Type "01/00/2000" inside the date text box
		edit_recording_properties_window.changeDate("01/00/2000");
						
		//14.The save button is enable
		edit_recording_properties_window.verifySaveButtonDisable();
		
		//15.Type "01/01/99999" inside the date text box
		edit_recording_properties_window.changeDate("01/01/99999");
				
		//16.The save button is disabled
		edit_recording_properties_window.verifySaveButtonDisable();
				
		//17.Type "aa/bb/asas" inside the date text box
		edit_recording_properties_window.changeDate("aa/bb/asas");
						
		//18.The save button is disabled
		edit_recording_properties_window.verifySaveButtonDisable();
		
		//19.Type "32/01/2000" inside the date text box
		edit_recording_properties_window.changeDate("01/01/2017");
		
		//20.The save button is enabled
		edit_recording_properties_window.verifySaveButtonEnable();
		
		//21. click on cancel for the next test
		edit_recording_properties_window.clickOnCancelButton();
		
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
	}

	// description = "get courses list"
	public void initializeCourseObject() throws InterruptedException {

		course = PageFactory.initElements(driver, CoursesHelperPage.class);
		course.courses = course.getCoursesListFromElement(course.course_list);
	}
}

