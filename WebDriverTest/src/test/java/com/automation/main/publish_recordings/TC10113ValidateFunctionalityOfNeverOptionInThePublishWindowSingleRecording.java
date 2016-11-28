package com.automation.main.publish_recordings;

import org.openqa.selenium.By;
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
import com.automation.main.page_helpers.CopyMenu;
import com.automation.main.page_helpers.CourseSettingsPage;
import com.automation.main.page_helpers.CoursesHelperPage;
import com.automation.main.page_helpers.EditRecordingPropertiesWindow;
import com.automation.main.page_helpers.LoginHelperPage;
import com.automation.main.page_helpers.PlayerPage;
import com.automation.main.page_helpers.PublishWindow;
import com.automation.main.page_helpers.RecordingHelperPage;
import com.automation.main.utilities.DriverSelector;

import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;


@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC10113ValidateFunctionalityOfNeverOptionInThePublishWindowSingleRecording {

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
	public EditRecordingPropertiesWindow edit_recording_properties_window;
	public AdminDashboardPage admin_dash_board_page;
	WebDriver driver;
	WebDriverWait wait;
	public static WebDriver thread_driver;
	CopyMenu copy;
	DesiredCapabilities capability;
	public PlayerPage player_page;
	public CourseSettingsPage course_settings_page;
	public String firstRecordNameRecording;
	public String firstRecordNameStudent;
	
	@BeforeClass
	public void setup() {

			driver = DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());
			calendarPage = PageFactory.initElements(driver, CalendarPage.class);
			admin_dash_board_page = PageFactory.initElements(driver, AdminDashboardPage.class);
			admin_dashboard_view_course_list = PageFactory.initElements(driver, AdminDashboardViewCourseList.class);
			tegrity = PageFactory.initElements(driver, LoginHelperPage.class);
			publish_window = PageFactory.initElements(driver, PublishWindow.class);
			record = PageFactory.initElements(driver, RecordingHelperPage.class);
			copy = PageFactory.initElements(driver, CopyMenu.class);
			player_page = PageFactory.initElements(driver, PlayerPage.class);
			course_settings_page = PageFactory.initElements(driver, CourseSettingsPage.class);

		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC10113ValidateFunctionalityOfNeverOptionInThePublishWindowSingleRecording at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC10113ValidateFunctionalityOfNeverOptionInThePublishWindowSingleRecording at " + DateToStr, "Starting the test: TC10113ValidateFunctionalityOfNeverOptionInThePublishWindowSingleRecording at " + DateToStr, LogAs.PASSED, null);	
	}

	@AfterClass
	public void closeBroswer() {
		this.driver.quit();
	}
		
	// @Parameters({"web","title"}) in the future
	@Test (description="TC10113 validate functionality of never option in the publish window single recording")
	public void test10113() throws InterruptedException, ParseException {
		
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);	
		initializeCourseObject();
		
		//1.pre test login as INSTRUCTOR
		tegrity.loginCourses("User1");
		
		//2.Open the course "Course details" page 
		String current_course = course.selectCourseThatStartingWith("Ab");
		String url =  course.getCurrentUrlCoursePage(); 
		
		//3.Make course public
		record.clickOnCourseTaskThenCourseSettings();
		course_settings_page.makeSureThatMakeCoursePublicIsSelected();
		course_settings_page.clickOnOkButton();
		
		for(int type_of_tab = 0; type_of_tab < 2; type_of_tab++) {
			
			if(type_of_tab == 1){
				record.clickOnStudentRecordingsTab();
			}
		
			//4.check several recordings respective checkboxes
			record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
		
			//5. take the name of the record for later use
			if(type_of_tab == 0){
				firstRecordNameRecording = record.getFirstRecordingTitle();
			} else firstRecordNameStudent = record.getFirstRecordingTitle();
		
			//6.Click the "Recording Tasks" drop-down menu and choose the "Publish" option
			record.clickOnRecordingTaskThenPublish();
				
			//7.Select the "Never" radio box
			publish_window.waitForPageToLoad();
			publish_window.verifyPublishWindowOpen();
			publish_window.chooseRadioButton("Never");
		
			//8.Press the "Save" button
			publish_window.clickOnSaveButton();
			publish_window.verifyPublishWindowClosed();
		
			//9.The recording status changed to "Not Published".
			record.veirfyStatusNotPublishOnTheFirstRecord();
		
		}
				
		//10. signout
		record.signOut();

		for(int type_of_tab = 0; type_of_tab < 2; type_of_tab++) {
		
		// Looping for Student, Guest and ADMIN
		for(int type_of_user = 0; type_of_user < 5; type_of_user++) {
					
			if(type_of_user == 0) {
				// 11. Login as Student.
				tegrity.loginCourses("User4");		
			} else if (type_of_user == 1) {
				// 11. Login as INSTRUCTOR.
				tegrity.loginCourses("User2");
			} else if (type_of_user == 2) {
				// 11. Login as guest
				tegrity.loginAsguest();
			}  else if (type_of_user == 3){
				// 11. Login as admin
			tegrity.loginAdmin("Admin");
			}  else if (type_of_user == 4){
				// 11. Login as admin
			tegrity.loginAdmin("HelpdeskAdmin");
			}

			if(type_of_user < 3) {
				// 3. Open some course.
				course.selectCourseThatStartingWith(current_course);
			} else {
				// Click on "view course list" under "courses" section.
				admin_dash_board_page.clickOnTargetSubmenuCourses("View Course List");
					
				// In "All courses" page, search for Ab course.
				Thread.sleep(8000);
				admin_dashboard_view_course_list.moveToCoursesThroughGet(url);
				record.waitForThePageToLoad();		
			}
			
			if(type_of_tab == 1){
				record.clickOnStudentRecordingsTab();
			}
				
			if(type_of_user == 0) {	
				//12.Validate the recording isn't displayed.
				if(type_of_tab == 0){
					record.verifyThatTargetRecordingNotExistInRecordingList(firstRecordNameRecording);
				} else record.verifyThatTargetRecordingNotExistInRecordingList(firstRecordNameStudent);
			} else if(type_of_user == 2) {
				//12.Validate the recording you uploaded earlier is displayed with "Not Published" label.
				record.verifyNoStatusInTheIndex(1);
			}else{
				//12.Verify that selected recording has "Not Published" status		
				record.veirfyStatusNotPublishOnTheFirstRecord();
			}
			
			if(type_of_tab != 0 && type_of_tab  != 2){
				//13.Open the recording playback
				record.verifyFirstExpandableRecording();
				record.clickOnTheFirstCaptherWithOutTheExpand();
		
				// 14.verify recording displaying correctly
				player_page.verifyTimeBufferStatusForXSec(2);// check source display
				player_page.exitInnerFrame();
			}

			//15. signout
			record.signOut();
		
		 }
		
		}
		// Unpublic Ab course1. 
		tegrity.loginCourses("User1");
						
		course.selectCourseThatStartingWith("Ab");
						
		// Make course public
		record.clickOnCourseTaskThenCourseSettings();
		course_settings_page.makeSureThatMakeCoursePublicIsUnSelected();
		course_settings_page.clickOnOkButton();
		
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
	
		

	}
	
	// description = "get courses list"
	public void initializeCourseObject() throws InterruptedException {

		course = PageFactory.initElements(driver, CoursesHelperPage.class);
		course.courses = course.getCoursesListFromElement(course.course_list);
	}
}