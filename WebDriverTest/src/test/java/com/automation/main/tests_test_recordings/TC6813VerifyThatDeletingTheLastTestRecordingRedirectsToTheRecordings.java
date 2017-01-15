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
import com.automation.main.page_helpers.ConfirmationMenu;
import com.automation.main.page_helpers.CopyMenu;
import com.automation.main.page_helpers.CourseSettingsPage;
import com.automation.main.page_helpers.CoursesHelperPage;
import com.automation.main.page_helpers.DeleteMenu;
import com.automation.main.page_helpers.EditRecordingPropertiesWindow;
import com.automation.main.page_helpers.LoginHelperPage;
import com.automation.main.page_helpers.MoveWindow;
import com.automation.main.page_helpers.PlayerPage;
import com.automation.main.page_helpers.PublishWindow;
import com.automation.main.page_helpers.RecordingHelperPage;
import com.automation.main.utilities.DriverSelector;
import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import junitx.util.PropertyManager;

import java.text.DateFormat;
import java.util.Date;


@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC6813VerifyThatDeletingTheLastTestRecordingRedirectsToTheRecordings {

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
	public MoveWindow move_menu;
	public DeleteMenu delete_menu;
	public CourseSettingsPage course_settings_page;
	public AdvancedServiceSettingsPage advanced_service_settings_page;
	public AdminDashboardPage admin_dash_board_page;
	public PlayerPage player_page;
	WebDriver driver;
	WebDriverWait wait;
	public static WebDriver thread_driver;
	public ConfirmationMenu confirm_menu;
	public CopyMenu copy;
	DesiredCapabilities capability;
	String url;

	@BeforeClass
	public void setup() {

			driver = DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());
			course = PageFactory.initElements(driver, CoursesHelperPage.class);
			admin_dash_board_page = PageFactory.initElements(driver, AdminDashboardPage.class);
			admin_dashboard_view_course_list = PageFactory.initElements(driver, AdminDashboardViewCourseList.class);
			advanced_service_settings_page = PageFactory.initElements(driver, AdvancedServiceSettingsPage.class);
			edit_recording_properties_window = PageFactory.initElements(driver, EditRecordingPropertiesWindow.class);
			confirm_menu = PageFactory.initElements(driver, ConfirmationMenu.class);
			tegrity = PageFactory.initElements(driver, LoginHelperPage.class);
			player_page = PageFactory.initElements(driver, PlayerPage.class);
			move_menu = PageFactory.initElements(driver, MoveWindow.class);
			record = PageFactory.initElements(driver, RecordingHelperPage.class);
			copy = PageFactory.initElements(driver, CopyMenu.class);
			course_settings_page = PageFactory.initElements(driver, CourseSettingsPage.class);
			delete_menu = PageFactory.initElements(driver, DeleteMenu.class);
		

		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC6813VerifyThatDeletingTheLastTestRecordingRedirectsToTheRecordings at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC6813VerifyThatDeletingTheLastTestRecordingRedirectsToTheRecordings at " + DateToStr, "Starting the test: TC6813VerifyThatDeletingTheLastTestRecordingRedirectsToTheRecordings at " + DateToStr, LogAs.PASSED, null);	
	}

	@AfterClass
	public void closeBroswer() {
		this.driver.quit();
	}
		
	// @Parameters({"web","title"}) in the future
	@Test (description="TC6813 Verify that deleting the last Test recording redirects to the Recordings tab")
	public void test6813() throws InterruptedException {
		
		//1.Open tegrity "Login page"
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);	
				
		//2.Login as INSTRUCTOR - *Preconditions:* There is an Instructor enrolled to a course with Test recordings of User 4(Student)
		tegrity.loginCourses("User1");
		
		//3.Select the course from the precondition
		course.selectCourseThatStartingWith("Ab");
			
		//4.Open the "Tests" tab
		record.clickOnTestsTab();
		
		//5.Click on the first checkbox
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
				
		//6. hover on the Recording Task and click on the edit recording properties 
		record.toEditRecordingPropertiesMenu();
				
		//7.change the owner to be Student -(At least one of those test recording's *belongs to that Student*0
		edit_recording_properties_window.waitForPageToLoad();
		edit_recording_properties_window.changeOwner(PropertyManager.getProperty("User4"));
				
		//8.Click the "Save" button
		edit_recording_properties_window.clickOnSaveButton();
				
		//9.Click on Ok after change the owner
		confirm_menu.clickOnOkButtonAfterConfirmEditRecordingProperties();	
		
		//The same Instructor is enrolled to a second course that doesn't have Test recordings from the same student from the first course
		//10.Return to the Course menu
		record.returnToCourseListPage();
		
		//11.Select the second course from the precondition
		String distanction_course = course.selectCourseThatStartingWith("abc");
		
		//12.verify that the test tab is empty
		if(record.isTestTabDisplay()){
			
			//13.Open the "Tests" tab
			record.clickOnTestsTab();
			
			//14.Select *all* Test recordings
			record.SelectOneCheckBoxOrVerifyAlreadySelected(record.check_all_checkbox);
			
			//15.Hover over the "Recording Tasks" menu and select the "Delete" option
			record.clickOnRecordingTaskThenDelete();
			
			//16.Click the "Delete" button
			delete_menu.clickOnDeleteButton();
		}
		
		//*End of preconditions*	
		//sign out 
		record.signOut();
		
		//17.Login as Instructor
		tegrity.loginCourses("User1");
		
		//18.open the first course from the precondition
		course.selectCourseThatStartingWith("Ab");
		
		//19.Open the "Tests" tab
		record.clickOnTestsTab();
		
		//20.Select Test Recording
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
		String first_recording_test = record.getFirstRecordingTitleTest();
		
		//21.Hover over "Recording Tasks" button and select the "Move" menu item
		record.clickOnRecordingTaskThenMove();
		move_menu.verifyThatCopyMenuOpen();
		
		//22.Select the second course from the precondition as the destination course
		copy.selectTargetCourseFromCourseList(distanction_course);
		
		//23.Click the "Move" button
		move_menu.clickOnMoveRecordings();
		
		//23.Click the "OK" button
		confirm_menu.clickOnOkButtonAfterConfirmMoveRecording();
		
		//24."Move" window is closed
		move_menu.verifyThatMoveMenuClose();
		
		//25.Selected Recording has a "Moving/Copying" status
		record.checkRecordingInIndexIStatus(1, "Moving/Copying");
		
		//26.Verify that when the moving operation is done - the Test recording is no longer displayed in the Test recordings list
		record.checkStatusExistenceForMaxTTime(220);
		record.verifyThatTargetRecordingNotExistInRecordingList(first_recording_test);
		
		//27.Click on the "Courses" link in the breadcrumbs
		record.returnToCourseListPage();
		
		//28.Open the second course from the precondition & open it's "Tests" tab
		course.selectCourseThatStartingWith("abc");
		
		//29.Open the "Tests" tab
		record.clickOnTestsTab();
		
		//30.*Verify that moved recording is displayed in the "Tests" tab* & play it
		record.verifyThatTargetRecordingExistInRecordingList(first_recording_test);
		
		//31.play it 
		record.verifyFirstExpandableTestRecording(1);
		record.clickOnTheFirstCaptherWithOutTheExpand();
		
		//32.display recording and click on the stop button
		player_page.verifyTimeBufferStatusForXSec(2);
		
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
	
	}
	
}