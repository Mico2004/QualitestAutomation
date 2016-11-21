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
import com.automation.main.page_helpers.EditRecordinPropertiesWindow;
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
public class TC7898ValidateFunctionalityOfAlwaysOptionInThePublishWindowSingleRecording {

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
	public EditRecordinPropertiesWindow edit_recording_properties_window;
	public AdminDashboardPage admin_dash_board_page;
	WebDriver driver;
	WebDriverWait wait;
	public static WebDriver thread_driver;
	CopyMenu copy;
	DesiredCapabilities capability;
	public PlayerPage player_page;
	public CourseSettingsPage course_settings_page;
    
	

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
		 System.out.println("Starting the test: TC7898ValidateFunctionalityOfAlwaysOptionInThePublishWindowSingleRecording at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC7898ValidateFunctionalityOfAlwaysOptionInThePublishWindowSingleRecording at " + DateToStr, "Starting the test: TC7898ValidateFunctionalityOfAlwaysOptionInThePublishWindowSingleRecording at " + DateToStr, LogAs.PASSED, null);	
	}

	@AfterClass
	public void closeBroswer() {
		this.driver.quit();
	}
		
	
	// @Parameters({"web","title"}) in the future
	@Test (description="TC7898 Validate functionality of always option in the publish window single recording")
	public void test7898() throws InterruptedException, ParseException {
		
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);	
		initializeCourseObject();
		
		//1.pre test login as INSTRUCTOR
		tegrity.loginCourses("User1");
		
		//2.Make sure to have a unpublished regular and student recording
		course.selectCourseThatStartingWith("Ab");	
		record.unpublishFirstRecording(record.recordings_tab, publish_window);
		record.veirfyStatusNotPublishOnTheFirstRecord();
		
		record.unpublishFirstRecording(record.student_recordings_tab, publish_window);
		record.veirfyStatusNotPublishOnTheFirstRecord();
		
		// Make course public
		record.clickOnCourseTaskThenCourseSettings();
		course_settings_page.makeSureThatMakeCoursePublicIsSelected();
		course_settings_page.clickOnOkButton();
			
		//3.End of preconditions
		record.signOut();
				
		//4.login as INSTRUCTOR
		tegrity.loginCourses("User1");
		
		//5.Open the course "Course details" page 
		String current_course = course.selectCourseThatStartingWith("Ab");	
		String url =  course.getCurrentUrlCoursePage(); 
		
		for(int type_of_tab = 0; type_of_tab < 2; type_of_tab++) {
		
		if(type_of_tab == 1)
			record.clickOnStudentRecordingsTab();
			
		//6.Check the checkbox of the recording 
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
		
		//7.Choose the "Publish" option from the "Recording Tasks" drop-down menu
		record.clickOnRecordingTaskThenPublish();
		publish_window.waitForPageToLoad();
		
		//8. The "Never" radio button is marked.
		publish_window.verifyPublishWindowOpen();
		publish_window.verifyThatNeverOptionSelectedOrNotSelected(1);
		
		//9.Choose the "Always" option
		publish_window.chooseRadioButton("Always");
		publish_window.verifyThatAlwaysOptionSelectedOrNotSelected(1);
		
		//10.Click on the "Save" button
		publish_window.clickOnSaveButton();
		
		//11.The "Publish" window disappears.
		publish_window.verifyPublishWindowClosed();
		
		//12.The recording "Not Publish" status changed to "".
		record.verifyNoStatusInTheIndex(1);
		
		}
		
		//13.Sign out
		record.signOut();
		
		for(int type_of_tab = 0; type_of_tab < 2; type_of_tab++) {
			
			// Looping for Student, Guest and ADMIN
			for(int type_of_user = 0; type_of_user < 4; type_of_user++) {
			
				if (type_of_user == 0) {
					// 2. Login as Student.
					tegrity.loginCourses("User4");
				} else if (type_of_user == 1) {
					// 2. Login as guest
					tegrity.loginAsguest();
				} else if(type_of_user == 2) {
				// 2. Login as ADMIN
					tegrity.loginAdmin("Admin");
				}else if(type_of_user == 3) {
					// 2. Login as  HD ADMIN
					tegrity.loginAdmin("HelpdeskAdmin");
			} 
						
				if(type_of_user < 2) {
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
		
				//16.The recording "Not Publish" status changed to "".
				record.verifyNoStatusInTheIndex(1);
		
				//17.Open the recording playback
				record.verifyFirstExpandableRecording();
				record.clickOnTheFirstCaptherWithOutTheExpand();
		
				//18.display recording
				player_page.verifyTimeBufferStatusForXSec(5);// check source display
				player_page.exitInnerFrame();
		
				//19.Sign out
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