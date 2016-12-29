package com.automation.main.bookmark_tab;

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
import com.automation.main.page_helpers.TagMenu;
import com.automation.main.utilities.DriverSelector;
import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import junitx.util.PropertyManager;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;


@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC10345VerifyInstructorAndStudentBookmarkPermissions {

	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}
	
	public AdminDashboardViewCourseList admin_dashboard_view_course_list;
	public LoginHelperPage tegrity;
	public CoursesHelperPage course;
	public RecordingHelperPage record;
	public PlayerPage player_page;
	public PublishWindow publish_window;
	public AdminDashboardPage admin_dash_board_page;
	public DeleteMenu delete_menu;
	public CourseSettingsPage course_settings_page;
	WebDriver driver;
	WebDriverWait wait;
	public TagMenu tag_window;
	public static WebDriver thread_driver;
	public ConfirmationMenu confirm_menu;
	public CopyMenu copy;
	public MoveWindow move_window;
	public EditRecordingPropertiesWindow edit_recording_properties_window;
	DesiredCapabilities capability;
	String bookmark_to_add;
	String recordName,time,course_name;
	boolean isDeleteDisplay;
	Hashtable<String,String> bookmarksNameAndTime = new Hashtable<String,String>();
	List<String> bookmarksName = new ArrayList<String>();
	@BeforeClass
	public void setup() {

			driver = DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());
			player_page = PageFactory.initElements(driver, PlayerPage.class);
			course = PageFactory.initElements(driver, CoursesHelperPage.class);
			admin_dash_board_page = PageFactory.initElements(driver, AdminDashboardPage.class);
			admin_dashboard_view_course_list = PageFactory.initElements(driver, AdminDashboardViewCourseList.class);
			confirm_menu = PageFactory.initElements(driver, ConfirmationMenu.class);
			tegrity = PageFactory.initElements(driver, LoginHelperPage.class);
			tag_window= PageFactory.initElements(driver, TagMenu.class);
			record = PageFactory.initElements(driver, RecordingHelperPage.class);
			copy = PageFactory.initElements(driver, CopyMenu.class);
			delete_menu = PageFactory.initElements(driver, DeleteMenu.class);
			move_window = PageFactory.initElements(driver, MoveWindow.class);
			edit_recording_properties_window = PageFactory.initElements(driver, EditRecordingPropertiesWindow.class);
			publish_window = PageFactory.initElements(driver, PublishWindow.class);
			course_settings_page = PageFactory.initElements(driver, CourseSettingsPage.class);
			
		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC10345VerifyInstructorAndStudentBookmarkPermissions at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC10345VerifyInstructorAndStudentBookmarkPermissions at " + DateToStr, "Starting the test: TC10345VerifyInstructorAndStudentBookmarkPermissions at " + DateToStr, LogAs.PASSED, null);	
	}

	@AfterClass
	public void closeBroswer() {
		this.driver.quit();
	}
		
	// @Parameters({"web","title"}) in the future
	@Test (description="Verify instructor and student bookmark permissions")
	public void test10345() throws InterruptedException {
		

		//1.Enter the university
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);	
				
		//pre testing - Make sure to upload a 2 student recordings to the course as Student A and unpublish one of them
		//2.Login as INSTRUCTOR 
		tegrity.loginCourses("User1");	
		
		//3.Click on the course Start with Ab
		course_name = course.selectCourseThatStartingWith("Ab");	
		String url = course.getCurrentUrlCoursePage(); 
		
		//4.Open the course setting page and check the 'Make this course publicly visible' setting
		record.clickOnCourseTaskThenCourseSettings();
		course_settings_page.makeSureThatMakeCoursePublicIsSelected();
		course_settings_page.clickOnOkButton();
		
		//pre test- Make sure to upload a 2 student recordings to the course as Student A 
		for(int number_of_record = 0 ; number_of_record <2 ; number_of_record++){
		
			//5.check several recordings respective checkboxes
			record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
		
			//6.click on the recording tasks->edit recording properties option
			record.toEditRecordingPropertiesMenu();
			edit_recording_properties_window.waitForPageToLoad();			
		
			//7.Choose the "Student Recording" option from the Type drop down list		
			edit_recording_properties_window.ChooseDiffrenetType("Student recording");
		
			//8.Change the owner of the record to be student record 
			edit_recording_properties_window.changeOwner(PropertyManager.getProperty("User4"));
		
			//9.Click the "Save" button
			edit_recording_properties_window.clickOnSaveButton();
		
		}
		
		//pre test -unpublish one of them + one of regular recording
		for(int number_of_tab = 0 ; number_of_tab <2 ; number_of_tab++){
		
		if(number_of_tab == 1){
			//10.Click on student tab
			record.clickOnStudentRecordingsTab();
		}
		
		//11.check several recordings respective checkboxes
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
		
		//12.unpublish one recording
		record.clickOnRecordingTaskThenPublish();
		publish_window.waitForPageToLoad();
	
		//13.Select the "Never" radio box
		publish_window.chooseRadioButton("Never");
		
		//14.Press the "Save" button
		publish_window.clickOnSaveButton();
		publish_window.verifyPublishWindowClosed();
		
		}
		
		////15.Click on recording tab
		record.clickOnRecordingsTab();
		
		//pre test -Make sure to upload a 2 Recgular recordings and 2 proctoring recordings  to the course as Instructor A 
		for(int number_of_tab = 0 ; number_of_tab <2 ; number_of_tab++){
			
			if(number_of_tab == 1) {
				record.clickOnTestsTab();
			}
			
			for(int number_of_record = 0 ; number_of_record <2 ; number_of_record++) {
				
				//16.check several recordings respective checkboxes
				if(number_of_record == 0) {
					record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
				} else {
					record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox2);
				}
				//17.click on the recording tasks->edit recording properties option
				record.toEditRecordingPropertiesMenu();
				edit_recording_properties_window.waitForPageToLoad();			
				
				//18.Change the owner of the record to be student record 
				edit_recording_properties_window.changeOwner(PropertyManager.getProperty("User1"));
			
				//19.Click the "Save" button
				edit_recording_properties_window.clickOnSaveButton();				
			}
			
		}
		record.signOut();
				
		//20.Log in as Instructor A
		tegrity.loginCourses("User1");	
		
		//21.Click on the course Start with Ab
		course.selectCourseThatStartingWith("Ab");	
	
		//0 - for regular recording ,1- for student user , 2- for another student user
		for(int type_of_tab = 0; type_of_tab < 3; type_of_tab++) {
			
			if(type_of_tab == 1) {
				record.clickOnStudentRecordingsTab();
			} else if(type_of_tab == 2) {
				record.clickOnTestsTab();
			}
			
			for(int type_of_recording = 0; type_of_recording < 2; type_of_recording++) {
									
				//22.Click on the title of one of the recordings and then click on one of the chapters to play it
				if(type_of_recording == 0){
					record.verifyFirstExpandableRecording();
					record.clickOnTheFirstCaptherWithOutTheExpand();
				} else {
					record.clickOnRecordingTitleInIndex(2);
					record.clickOnTheCaptherWithOutTheExpandOnTheIndex(2);
				}
				//23.The tegrity player is displayed and the recording began to play
				player_page.verifyTimeBufferStatusForXSec(2);

				//24.Add a bookmark with the text: "Instructor"
				if(type_of_tab == 0){
					bookmark_to_add= "Instructor" + Integer.toString(type_of_recording);
				}else if(type_of_tab == 1){
					bookmark_to_add= "Student" + Integer.toString(type_of_recording);
				} else {
					bookmark_to_add= "Test" + Integer.toString(type_of_recording);
				}
				time = player_page.addTargetBookmark(bookmark_to_add);
				//25.save the bookmark name and time for later
				bookmarksName.add(bookmark_to_add);
				bookmarksNameAndTime.put(bookmark_to_add, time);
			}
			
		}
		
		//26.Click on the "Bookmarks" tab
		record.clickOnBookmarksTab();
			
		//27.Make sure all of the bookmarks you just created are visible
		record.verifyThatAllTheBookmarksDisplayInTheBookmarkTab(bookmarksName);
		
		//28.Sign Out
		record.signOut();
		
		for(int type_of_Student = 0; type_of_Student < 2; type_of_Student++) {
		
		if(type_of_Student == 0){
			//29.Login as Student A (Which uploaded the student recordings)
			tegrity.loginCourses("User4");	
		} else {
			//29.Login as Student B (Which doesnt uploaded the student recordings)
			tegrity.loginCourses("User3");	
		}
		
		//30.Open the same course
		course.selectCourseThatStartingWith("Ab");	

		//31.Click on the 'Bookmarks' tab
		record.clickOnBookmarksTab();
		
		//32.Make sure the unpublished regular recording bookmark is not displayed
		record.verifyBookmarkIsNotDisplay("Instructor1");
				
		if(type_of_Student == 0){
			//33.Make sure the unpublished student recording bookmark is displayed(Student A)
			record.verifyBookmarkIsDisplay("Student1");
		}else {
			//33.Make sure the unpublished student recording bookmark is not displayed(Student B)
			record.verifyBookmarkIsNotDisplay("Student1");
		}
		
		//34.Make sure the published student recording bookmark is displayed
		record.verifyBookmarkIsDisplay("Student2");
		
		//35.Make sure the test recordings bookmarks are not displayed
		record.verifyBookmarkIsNotDisplay("Instructor1");
		
		//36.Make sure the proctoring recording's bookmarks are not displayed
		record.verifyBookmarkIsNotDisplay("Test1");
		record.verifyBookmarkIsNotDisplay("Test2");
		
		//37.Sign Out
		record.signOut();
		
		}
		
		//38.Login as Admin
		tegrity.loginAdmin("Admin");
		
		//39.Click on "view course list" under "courses" section.
		admin_dash_board_page.clickOnTargetSubmenuCourses("View Course List");	
		
		//40. In "All courses" page, search for Ab course.
		admin_dashboard_view_course_list.waitForThePageToLoad();
		admin_dashboard_view_course_list.moveToCoursesThroughGet(url);
		
		//41.Click on the 'Bookmarks' tab
		record.clickOnBookmarksTab();
		
		//42.Make sure all bookmarks are displayed
		record.verifyThatAllTheBookmarksDisplayInTheBookmarkTab(bookmarksName);
		
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
	}
	
}