package com.automation.main.test_policy;

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
public class TC6795VerifyTheUIForTheStartATestModalWindows {

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
		 System.out.println("Starting the test: TC6795VerifyTheUIForTheStartATestModalWindows at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC6795VerifyTheUIForTheStartATestModalWindows at " + DateToStr, "Starting the test: TC6795VerifyTheUIForTheStartATestModalWindows at " + DateToStr, LogAs.PASSED, null);	
	}

	@AfterClass
	public void closeBroswer() {
		this.driver.quit();
	}
		
	// @Parameters({"web","title"}) in the future
	@Test (description="TC6795 Verify the UI for the 'Start a Test' modal windows")
	public void test6795() throws InterruptedException {
		
		//1.Open tegrity "Login page"
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);	
				
		//2.Login as an Admin
		tegrity.loginAdmin("Admin");
		
		//3.Preconditions:Click on the "Advanced Service Settings" from the "Service Settings and Maintenance" section.
		admin_dash_board_page.waitForPageToLoad();
		admin_dash_board_page.clickOnTargetSubmenuAdvancedServices("Advanced Service Settings");
		
		//4.Check the "Enable student testing" checkbox
		advanced_service_settings_page.forceWebElementToBeSelected(advanced_service_settings_page.enable_student_testing_checkbox, "enable_student_testing_checkbox");
		
		//5.Check the "Show institutional testing policy" checkbox
		//6.Fill some unique text into the institution's policy text area & use the "OK" button on the bottom of the page to save the changes
		String messageAdmin = advanced_service_settings_page.showInstitutionTestPolicyAndClickOk(confirm_menu);

		//7.sign out
		record.signOut();
		
		//8.Login as INSTRUCTOR 
		tegrity.loginCourses("User1");
		
		//9.Open some course link
		String course_name = course.selectCourseThatStartingWith("Ab");
		
		//10.Choose the "Course settings" option from the "Course Tasks" manu
		record.clickOnCourseTaskThenCourseSettings();
		
		//11.Check the "Enable students testing" "On/Off" checkbox
		course_settings_page.forceWebElementToBeSelected(course_settings_page.checkbox_enable_student_testing, "Enable student testing (Remote Proctoring mode)");
		
		//12.Check the "Show course testing policy" "On/Off" checkbox
		course_settings_page.forceWebElementToBeSelected(course_settings_page.checkbox_show_testing_policy, "Enable show testing policy");
		String messageCourse = course_settings_page.addTextToShowCourseTesting();
		course_settings_page.clickOnOkButton();
		
		//13.End of preconditions - sign out
		record.signOut();
				
		//14.Login as INSTRUCTOR 
		tegrity.loginCourses("User1");
			
		//15.Click on some course link
		course.selectCourseThatStartingWith("Ab");
		
		//16.Use the "Start a Test" button
		record.clickElementJS(record.start_test_button);
		
		//17.The color of the window's header is the same as the university's header color.
		start_test_window.verifyBackgroundColor(record);
		
		//18.The "Start a Test" window title is displayed in the header.
		start_test_window.verifyStartTestTitle();
				
		//19.The Institution's policy is displayed in the modal window's content, below the header - this must match the unique text you filled in the preconditions,as admin 
		start_test_window.verifyThatTheTextInTheTextboxIsEqualsToTheTextFromAdmin(messageAdmin);
		
		//20.verify background and text color of accept button
		copy.verifyBlueColor(start_test_window.getBackGroundColor(start_test_window.accept_button)); 
		
		//21.verify background and text color of cancel button
		record.verifyColorButton(start_test_window.getBackGroundColor(start_test_window.cancel_button));

		//22.verify that location of the save and cancel
		start_test_window.VerifyTheLocationOfTheSaveAndCancel();
	
		//14.Click the "Accept" button
		start_test_window.clickOnAcceptButton();
		
		//15.The course is not displayed in the 'Active Courses' page
		course.verifyCourseNotExist(course_name);
		
		//16.Click on the 'Past Courses' tab*
		course.clickOnPastCoursesTabButton();
		
		//17.The course is displayed in the 'Past Courses' tab content*
		course.verifyCourseExist(course_name);
		
		//18.post test - return the course ad to active courses
		course.selectCourseThatStartingWith("ad");
		
		//19.Hover over the "Course Tasks"
		record.moveToElementAndPerform(record.course_task_button, driver);
		
		//20.Click on the 'Course Tasks - Move to Active Courses'
		record.clickOnCourseTaskThenMoveToActiveCourses();
				
		//21.Click on the ok after moving to past courses
		confirm_menu.clickOnOkButtonAfterMoveToPastCoursesOrActiveCourses("The course was successfully moved to active courses");
			
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
	
	}
	
}