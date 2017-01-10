package com.automation.main.test_policy;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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

import java.awt.AWTException;
import java.text.DateFormat;
import java.util.Date;


@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC7026VerifyThatTheRecorderIsOpenedInProctoringModeAfterAcceptingBothTestingPolicies {

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
	public DeleteMenu delete_menu;
	public CourseSettingsPage course_settings_page;
	public EditRecordingPropertiesWindow edit_recording_properties_window;
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
		 System.out.println("Starting the test: TC7026VerifyThatTheRecorderIsOpenedInProctoringModeAfterAcceptingBothTestingPolicies at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC7026VerifyThatTheRecorderIsOpenedInProctoringModeAfterAcceptingBothTestingPolicies at " + DateToStr, "Starting the test: TC7026VerifyThatTheRecorderIsOpenedInProctoringModeAfterAcceptingBothTestingPolicies at " + DateToStr, LogAs.PASSED, null);	
	}

	@AfterClass
	public void closeBroswer() {
		this.driver.quit();
	}
		
	// @Parameters({"web","title"}) in the future
	@Test (description="TC7026 Verify that the Recorder is opened in Proctoring mode after accepting both Testing Policies")
	public void test7026() throws InterruptedException, AWTException {
		
		//1.Open tegrity "Login page"
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);	
				
		//2.Login as an Admin
		tegrity.loginAdmin("Admin");
		
		//3.Preconditions:Click on the "Advanced Service Settings" from the "Service Settings and Maintenance" section.
		admin_dash_board_page.waitForPageToLoad();
		admin_dash_board_page.clickOnTargetSubmenuAdvancedServices("Advanced Service Settings");
		
		//4.*check* the Enable student testing (Remote Proctoring Mode) checkbox
		advanced_service_settings_page.waitForThePageToLoad();
		advanced_service_settings_page.forceWebElementToBeSelected(advanced_service_settings_page.enable_student_testing_checkbox, "Enable student testing (Remote Proctoring Mode)");
		
		//5.Check the "Show institutional testing policy" checkbox
		advanced_service_settings_page.forceWebElementToBeSelected(advanced_service_settings_page.show_institution_testing_policy, "Show institutional testing policy");
		advanced_service_settings_page.clickOnOkbutton();
		
		//6.sign out
		record.signOut();
		
		//7.Login as INSTRUCTOR 
		tegrity.loginCourses("User1");
		
		//8.Open some course link
		course.selectCourseThatStartingWith("Ab");
		
		//9.Choose the "Course settings" option from the "Course Tasks" manu
		record.clickOnCourseTaskThenCourseSettings();
			
		//10.Check the "Show course testing policy" "On/Off" checkbox
		course_settings_page.forceWebElementToBeSelected(course_settings_page.checkbox_enable_student_testing, "Enable student testing (Remote Proctoring mode)");
		
		//11.Check the "Show course testing policy" "On/Off" checkbox
		course_settings_page.forceWebElementToBeSelected(course_settings_page.checkbox_show_testing_policy, "Enable show testing policy");
		course_settings_page.clickOnOkButton();
		
		//11.End of preconditions - sign out
		record.signOut();
				
		//12.Login as INSTRUCTOR 
		tegrity.loginCourses("User1");
		
		//13.Click on some course link
		course.selectCourseThatStartingWith("Ab");
		
		 //14.The "Start a Test" button is present
		 record.verifyWebElementDisplayed(record.start_test_button, "Start a Test");
			
		 //15.Use the "Start a Test" button
		record.clickElementJS(record.start_test_button);
			
		//16.The "Start a Test" modal window is displayed containing the Institution's Testing Policy
		 start_test_window.verifyStartATestWindowOpen();
			
		//17.Click the "Accept" button for the Institution testing
		start_test_window.clickOnAcceptButton();
		
		//18.Click the "Accept" button for the Course's testing
		start_test_window.clickOnAcceptButton();
		
		//19.*The PC/Mac Recorder is opened*
		record.startingATestThruogthKeys();
		
		//TODO: 20.The Recorder is in a Proctoring Recording state*
		
				
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
	
	}
	
}