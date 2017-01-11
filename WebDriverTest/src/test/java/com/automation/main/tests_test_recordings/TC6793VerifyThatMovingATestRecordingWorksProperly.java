package com.automation.main.tests_test_recordings;

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
import com.automation.main.page_helpers.AdvancedServiceSettingsPage;
import com.automation.main.page_helpers.CalendarPage;
import com.automation.main.page_helpers.ConfirmationMenu;
import com.automation.main.page_helpers.CopyMenu;
import com.automation.main.page_helpers.CourseSettingsPage;
import com.automation.main.page_helpers.CoursesHelperPage;
import com.automation.main.page_helpers.DeleteMenu;
import com.automation.main.page_helpers.EditRecordingPropertiesWindow;
import com.automation.main.page_helpers.LoginHelperPage;
import com.automation.main.page_helpers.PublishWindow;
import com.automation.main.page_helpers.RecordingHelperPage;
import com.automation.main.page_helpers.StartTestWindow;
import com.automation.main.page_helpers.TagMenu;
import com.automation.main.utilities.DriverSelector;
import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import java.text.DateFormat;
import java.util.Date;


@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC6793VerifyThatMovingATestRecordingWorksProperly {

	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}
	
	public PublishWindow publish_window;
	public AdminDashboardViewCourseList admin_dashboard_view_course_list;
	public EditRecordingPropertiesWindow edit_recording_properties_window;
	public LoginHelperPage tegrity;
	public CoursesHelperPage course;
	public RecordingHelperPage record;
	public CalendarPage calendarPage;
	public DeleteMenu delete_menu;
	public CourseSettingsPage course_settings_page;
	public AdvancedServiceSettingsPage advanced_service_settings_page;
	public AdminDashboardPage admin_dash_board_page;
	public StartTestWindow start_test_window;
	WebDriver driver;
	WebDriverWait wait;
	public TagMenu tag_window;
	public static WebDriver thread_driver;
	public ConfirmationMenu confirm_menu;
	public CopyMenu copy;
	DesiredCapabilities capability;
	String url;

	@BeforeClass
	public void setup() {

			driver = DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());
			calendarPage = PageFactory.initElements(driver, CalendarPage.class);
			course = PageFactory.initElements(driver, CoursesHelperPage.class);
			admin_dash_board_page = PageFactory.initElements(driver, AdminDashboardPage.class);
			admin_dashboard_view_course_list = PageFactory.initElements(driver, AdminDashboardViewCourseList.class);
			advanced_service_settings_page = PageFactory.initElements(driver, AdvancedServiceSettingsPage.class);
			edit_recording_properties_window = PageFactory.initElements(driver, EditRecordingPropertiesWindow.class);
			confirm_menu = PageFactory.initElements(driver, ConfirmationMenu.class);
			tegrity = PageFactory.initElements(driver, LoginHelperPage.class);
			tag_window= PageFactory.initElements(driver, TagMenu.class);
			record = PageFactory.initElements(driver, RecordingHelperPage.class);
			copy = PageFactory.initElements(driver, CopyMenu.class);
			course_settings_page = PageFactory.initElements(driver, CourseSettingsPage.class);
			delete_menu = PageFactory.initElements(driver, DeleteMenu.class);
			start_test_window = PageFactory.initElements(driver, StartTestWindow.class);

		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC6793VerifyThatMovingATestRecordingWorksProperly at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC6793VerifyThatMovingATestRecordingWorksProperly at " + DateToStr, "Starting the test: TC6793VerifyThatMovingATestRecordingWorksProperly at " + DateToStr, LogAs.PASSED, null);	
	}

	@AfterClass
	public void closeBroswer() {
		this.driver.quit();
	}
		
	// @Parameters({"web","title"}) in the future
	@Test (description="TC6793 Verify that moving a Test recording works properly")
	public void test6793() throws InterruptedException {
		
		//1.Open tegrity "Login page"
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);	
				
		//2.Login as INSTRUCTOR
		tegrity.loginCourses("User1");
		
		//3.Select the course from the precondition
		course.selectCourseThatStartingWith("Ab");
		
		//4.Open the "Tests" tab
		record.clickOnTestsTab();
		
		//5.Select *all* Test recordings
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.check_all_checkbox);
		
		//6.Hover over the "Recording Tasks" menu and select the "Delete" option
		record.clickOnRecordingTaskThenDelete();
		
		//7.Click the "Delete" button
		delete_menu.verifyDeleteWindowOpen();
		delete_menu.clickOnDeleteButton();
				
		//8.The Delete window is closed & all of the selected recordings are (being) deleted
		delete_menu.verifyDeleteWindowClosed();
		
		//9.*Verify that once all of the recordings in the "Tests" tab are deleted*
		record.verifyNoTestsTab();
			
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
	
	}
	
}