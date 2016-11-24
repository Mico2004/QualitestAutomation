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
public class TC10084ValidateOwnerAndTypeCorrectFlowWhenCourseHasALotOfEnrollments {


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
			edit_recording_properties_window = PageFactory.initElements(driver, EditRecordingPropertiesWindow.class);
			record = PageFactory.initElements(driver, RecordingHelperPage.class);
			copy = PageFactory.initElements(driver, CopyMenu.class);
		} catch (Exception e) {
			ATUReports.add("Fail Step", LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
		}
		

		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC10084ValidateOwnerAndTypeCorrectFlowWhenCourseHasALotOfEnrollments at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC10084ValidateOwnerAndTypeCorrectFlowWhenCourseHasALotOfEnrollments at " + DateToStr, "Starting the test: TC10084ValidateOwnerAndTypeCorrectFlowWhenCourseHasALotOfEnrollments at " + DateToStr, LogAs.PASSED, null);	
	}

	@AfterClass
	public void closeBroswer() {
		this.driver.quit();
	}
		
	// @Parameters({"web","title"}) in the future
	@Test (description="TC10084 Validate owner and type correct flow when course has a lot of enrollments")
	public void test10084() throws InterruptedException, ParseException {
		
		
		//1.log in courses page
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);	
		tegrity.loginCourses("StaticInstructor");
		initializeCourseObject();
		
		//2.open some course whom listed you as instructor
		course.selectCourseByName(PropertyManager.getProperty("StaticCourse"));
				
		//3.Check some recording respective checkbox 
		int recordNumber = record.checkExistenceOfNonEditRecordingsStatusInRecordings();
		record.selectIndexCheckBox(recordNumber);

		//4.click on the recording tasks->edit recording properties option
		record.toEditRecordingPropertiesMenu();
			
		//5.wait for edit reocrding properties window to open
		edit_recording_properties_window.waitForPageToLoad();
		
		//6.The drop down edit box contains only INSTRUCTORS users.	
		edit_recording_properties_window.addOwnersToList("Instractor",4);
		edit_recording_properties_window.verifyThatAllTheTypeInTheDropDownList();
		
		//7.Choose the "Student Recording" option from the Type drop down list
		edit_recording_properties_window.ChooseDiffrenetType("Student recording");
		edit_recording_properties_window.verifyThatTheTypeWasChoosen("Student recording");
		
		//9.The Owner drop down list consists only from STUDENTS users.
		edit_recording_properties_window.addOwnersToList("Student",5);
		edit_recording_properties_window.verifyThatAllTheTypeInTheDropDownList();
									
		//10.Choose the "Proctoring Recordings" option from the Type drop down list
		edit_recording_properties_window.ChooseDiffrenetType("Proctoring recording");
		edit_recording_properties_window.verifyThatTheTypeWasChoosen("Proctoring recording");
				
		//11.The Owner drop down list is consist from all INSTRUCTORS and STUDENTS users.
		edit_recording_properties_window.addOwnersToList("Proctoring",6);
		edit_recording_properties_window.verifyThatAllTheTypeInTheDropDownList();
		
		//12. click on cancel for the next test
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

