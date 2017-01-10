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
import junitx.util.PropertyManager;

import java.awt.AWTException;
import java.text.DateFormat;
import java.util.Date;


@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC6790VerifyThatTheTestsTabIsntDisplayedForStudentsAndGuests {

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
		 System.out.println("Starting the test: TC6790VerifyThatTheTestsTabIsntDisplayedForStudentsAndGuests at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC6790VerifyThatTheTestsTabIsntDisplayedForStudentsAndGuests at " + DateToStr, "Starting the test: TC6790VerifyThatTheTestsTabIsntDisplayedForStudentsAndGuests at " + DateToStr, LogAs.PASSED, null);	
	}

	@AfterClass
	public void closeBroswer() {
		this.driver.quit();
	}
		
	// @Parameters({"web","title"}) in the future
	@Test (description="TC6790 Verify that the 'Tests' tab isn't displayed for Students & Guests")
	public void test6790() throws InterruptedException {
		
		//1.Open tegrity "Login page"
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);	
				
		//*Preconditions:*  At least one of those test recording's *belongs to that Student*
		//* At least one of those test recording's *does not belong to that Student*
		//2.Login as an INSTRUCTOR
		tegrity.loginCourses("User1");
		
		//3.Open some course link
		course.selectCourseThatStartingWith("Ab");
		
		//4.Click on the test tab
		record.clickOnTestsTab();
		
		//5.Click on the first checkbox
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
		
		//6. hover on the Recording Task and click on the edit recording properties 
		record.clickOnRecordingTaskThenEditRecording();
		
		//7.change the owner to be Student -(At least one of those test recording's *belongs to that Student*0
		edit_recording_properties_window.waitForPageToLoad();
		edit_recording_properties_window.changeOwner(PropertyManager.getProperty("User4"));
		
		//8.Click the "Save" button
		edit_recording_properties_window.clickOnSaveButton();
				
		//9.Click on the first checkbox
		record.unselectIndexCheckBox(1);
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox2);
		
		//10.hover on the Recording Task and click on the edit recording properties 
		record.clickOnRecordingTaskThenEditRecording();
		
		//11.change the owner to be Intractur - At least one of those test recording's *does not belong to that Student*
		edit_recording_properties_window.waitForPageToLoad();
		edit_recording_properties_window.changeOwner(PropertyManager.getProperty("User1"));
		
		//12.Click the "Save" button
		edit_recording_properties_window.clickOnSaveButton();
				
		//13.Make course public
		record.clickOnCourseTaskThenCourseSettings();
		course_settings_page.makeSureThatMakeCoursePublicIsSelected();
		course_settings_page.clickOnOkButton();
					
		//15. sign out
		record.signOut();
		
		//*End of preconditions*
		//16.Login as an STUDENT
		tegrity.loginCourses("User4");
		
		//17.Select the course from the precondition
		course.selectCourseThatStartingWith("Ab");
		
		//18.Verify that the "Tests" tab is *not* displayed
		record.verifyNoTestsTab();
		
		//19 sign out
		record.signOut();
		
		//20.Login as as guest
		tegrity.loginAsguest();
		
		//21.Select the course from the precondition
		course.selectCourseThatStartingWith("Ab");
				
		//22.Verify that the "Tests" tab is *not* displayed
		record.verifyNoTestsTab();
		
		//23.sign out
		record.signOut();
		
		//24.Login as an INSTRUCTOR
		tegrity.loginCourses("User1");
				
		//25.Open some course link
		course.selectCourseThatStartingWith("Ab");
		
		//13.Make course Unpublic
		record.clickOnCourseTaskThenCourseSettings();
		course_settings_page.makeSureThatMakeCoursePublicIsUnSelected();
		course_settings_page.clickOnOkButton();
		
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
	
	}
	
}