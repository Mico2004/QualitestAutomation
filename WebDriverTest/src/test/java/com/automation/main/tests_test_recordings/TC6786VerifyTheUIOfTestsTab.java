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
import com.automation.main.page_helpers.PlayerPage;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC6786VerifyTheUIOfTestsTab {

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
	public PlayerPage player_page;
	WebDriver driver;
	WebDriverWait wait;
	public TagMenu tag_window;
	public static WebDriver thread_driver;
	public ConfirmationMenu confirm_menu;
	public CopyMenu copy;
	DesiredCapabilities capability;
	String url;
	List<String> namesOfbookmarks = new ArrayList<String>();
	
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
			player_page = PageFactory.initElements(driver, PlayerPage.class);
			tegrity = PageFactory.initElements(driver, LoginHelperPage.class);
			tag_window= PageFactory.initElements(driver, TagMenu.class);
			record = PageFactory.initElements(driver, RecordingHelperPage.class);
			copy = PageFactory.initElements(driver, CopyMenu.class);
			course_settings_page = PageFactory.initElements(driver, CourseSettingsPage.class);
			delete_menu = PageFactory.initElements(driver, DeleteMenu.class);
			start_test_window = PageFactory.initElements(driver, StartTestWindow.class);

		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC6786VerifyTheUIOfTestsTab at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC6786VerifyTheUIOfTestsTab at " + DateToStr, "Starting the test: TC6786VerifyTheUIOfTestsTab at " + DateToStr, LogAs.PASSED, null);	
	}

	@AfterClass
	public void closeBroswer() {
		this.driver.quit();
	}
		
	// @Parameters({"web","title"}) in the future
	@Test (description="TC6790 Verify that the 'Tests' tab isn't displayed for Students & Guests")
	public void test6790() throws Exception {
		
		//1.Open tegrity "Login page"
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);	
				
		//*Preconditions:* The following items must be enabled as Admin under "Advanced Service Settings":
		//** "YouTube integration"
		//** "Enabled captioning workflow"
		//* Have a course with at least one item in each tab:
		//** Recordings
		//** Bookmarks
		//** Additional Content
		//** Student Recordings
		//** Tests
		//* The Tests tab must have at least two recordings
		//* The following items must be enabled in that course's "course settings":
		//** Enable MP3 Podcast
		//** Enable Video Podcast
		//** Enable RSS feed
		//** Enable Webcast
		//*End of preconditions*

		//2. login as Admin
		tegrity.loginAdmin("Admin");
		
		//3.Preconditions:Click on the "Advanced Service Settings" from the "Service Settings and Maintenance" section.
		admin_dash_board_page.waitForPageToLoad();
		admin_dash_board_page.clickOnTargetSubmenuAdvancedServices("Advanced Service Settings");
				
		//4.Check the "Enable student testing" and "YouTube integration" checkbox
		advanced_service_settings_page.waitForThePageToLoad();
		advanced_service_settings_page.forceWebElementToBeSelected(advanced_service_settings_page.enable_automated_capitioning, "enable_automated_capitioning");
		advanced_service_settings_page.forceWebElementToBeSelected(advanced_service_settings_page.enable_youtube_integration, "enable_youtube_integration");
		advanced_service_settings_page.clickOnOkbutton();
		
		//5. sign out 
		advanced_service_settings_page.signOut();
		
		//6. login as Instructor
		tegrity.loginCourses("User1");
		
		//7.Open some course link
		String course_name = course.selectCourseThatStartingWith("Ab");
		
		//8.hover on course task -> course settings enable MP3 Podcast, Video Podcast,RSS feed,Webcast
		record.clickOnCourseTaskThenCourseSettings();
		course_settings_page.forceWebElementToBeSelected(course_settings_page.enable_mp3_podcast_button, "enable_mp3_podcast_button");
		course_settings_page.forceWebElementToBeSelected(course_settings_page.enable_video_podcast_button, "enable_video_podcast_button");
		course_settings_page.forceWebElementToBeSelected(course_settings_page.checkbox_enable_rss_feed, "enable_rss_feed");
		course_settings_page.forceWebElementToBeSelected(course_settings_page.checkbox_enable_webcast, "enable_webcast");
		course_settings_page.clickOnOkButton();

		//9.add bookmark to the first record	
		String recordName = record.getFirstRecordingTitle();
		record.verifyFirstExpandableRecording();
		record.clickOnTheFirstCaptherWithOutTheExpand();
				
		//10.display recording and click on the stop button
		player_page.verifyTimeBufferStatusForXSec(2);

		//11.add new unclear bookmark
		String bookmark_to_add= "TestUnclearIns";
		namesOfbookmarks.add(bookmark_to_add);
		player_page.addTargetBookmark(bookmark_to_add);
		player_page.exitInnerFrame();
					
		//*End of preconditions*
		//12. sign out
		player_page.signOut();
		
		//13.*Login as Instructor*
		tegrity.loginCourses("User1");
				
		//14.Open the course from the precondition
		course.selectCourseThatStartingWith(course_name);
		
		//15.Click on the test tab
		record.clickOnTestsTab();
		
		//16.Verify the UI of "Tests" tab
		//16.1 The "Tests" tab appears on the Course details page - to the right of the "Student Recordings" tab
		record.isFirstWebElementToTheRightSecondWebElement(record.student_recordings_tab,record.test_tab);
		
		//16.2 There's a "View" button below the tabs, on the left
		record.isFirstWebElementBelowSecondWebElement(record.view_button,record.test_tab);
		
		//16.3 There's a "Course Tasks" button, to the right of the "View" button
		record.isFirstWebElementToTheRightSecondWebElement(record.view_button , record.course_task_button);
		
		//16.4 There's a "Recording Tasks" button, on the same row as the previous two buttons, but all the way to the right
		record.verifyThatRecordingTaskButtonAsTheSameRowAsViewAndCourseTaskButtons();
		
		//17.Verify that the "*Course Tasks*" menu consists of these menu items:
		//# Course Settings
		//# Upload a Recording
		//# Upload Video File
		//# Upload Audio File
		//# Add Additional Content File
		//# Add Additional Content Link
		//# Get Live Webcast
		//# Move to Past Courses
		//# ----
		//# "_Subscribe to Your Course's..._" text field
		//# ----
		//# RSS Feed
		//# Podcast
		//# Video Podcast
		record.moveToElementAndPerform(record.course_task_button, driver);
		record.verifyWebElementDisplayed(record.course_settings_button, "Course Settings");
		record.verifyWebElementDisplayed(record.upload_recording, "Upload a Recording");
		record.verifyWebElementDisplayed(record.upload_video_file, "Upload Video File");
		record.verifyWebElementDisplayed(record.upload_audio_file, "Upload Audio File");
		record.verifyWebElementDisplayed(record.add_additional_content_file, "Add Additional Content File");
		record.verifyWebElementDisplayed(record.add_additional_content_link, "Add Additional Content Link");
		record.verifyWebElementDisplayed(record.get_live_webcast, "Get Live Webcast");
		record.verifyWebElementDisplayed(record.move_to_past_courses, "Move to Past Courses");
		record.verifyWebElementDisplayed(record.SubscribeToACourse, "_Subscribe to Your Course's..._ text field");
		record.verifyWebElementDisplayed(record.rssfeed, "RSS Feed");
		record.verifyWebElementDisplayed(record.podcast_button, "Podcast");
		record.verifyWebElementDisplayed(record.video_podcast, "Video Podcast");
//		record.verifyWebElementDisplayed(record.dividers.get(2),"divider1");
//		record.verifyWebElementDisplayed(record.dividers.get(3),"divider2");
		
		//18.*Hover over the "Recording Tasks" button while no recording is displayed*
		//* The following text is displayed on top:
		//"*_To make changes to recording(s) please select one or more recordings from the list below using the checkboxes on the right._*"
		//* ----
		//* *All other options must be disabled*
		//			"Move",
		//			"Copy",
		//			"delete",
		//			"publish",
		//			"upload to youtube",
		//			"request captions", 
		//			"download", 
		//			"edit recording",
		//			"edit recording properties",
		//			"share recording"
		// are all displayed and enabled.
		record.moveToElementAndPerform(record.recording_tasks_button, driver);
		record.verifyThatMessageShownUnderRecordingTasks();
		record.checkCorrectEnableDisableStatus(record.isMoveTaskDisableOrEnable(), false, "Move");
		record.checkCorrectEnableDisableStatus(record.isCopyTaskDisableOrEnable(), false, "Copy");
		record.checkCorrectEnableDisableStatus(record.isDeleteTaskDisableOrEnable(), false, "Delete");
		record.checkCorrectEnableDisableStatus(record.isPublishTaskDisableOrEnable(), false, "Publish");
		record.checkCorrectEnableDisableStatus(record.isUploadToYoutubeDisableOrEnable(), false, "Upload to Youtube");
		record.checkCorrectEnableDisableStatus(record.isRequestCaptionsDisableOrEnable(), false, "Request Captions");
		record.checkCorrectEnableDisableStatus(record.isDownloadRecordingDisableOrEnable(), false, "Download");
		record.checkCorrectEnableDisableStatus(record.isEditRecordingDisableOrEnable(), false, "Edit Recording");
		record.checkCorrectEnableDisableStatus(record.isEditRecordingPropertiesDisableOrEnable(), false, "Edit Recording Properties");
		record.checkCorrectEnableDisableStatus(record.isShareRecordingDisableOrEnable(), false, "Share Recording");
		
	
		//19.Select one recording & verify that the "Recording Tasks" menu consists of the following options:
		//# Move
		//# Copy
		//# Delete
		//# Publish
		//# Upload to YouTube
		//# Request Captions
		//# Download
		//# ----
		//# Edit Recording
		//# Edit Recording Properties
		//# Share Recording
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
		record.moveToElementAndPerform(record.recording_tasks_button, driver);
		record.checkCorrectEnableDisableStatus(record.isMoveTaskDisableOrEnable(), true, "Move");
		record.checkCorrectEnableDisableStatus(record.isCopyTaskDisableOrEnable(), true, "Copy");
		record.checkCorrectEnableDisableStatus(record.isDeleteTaskDisableOrEnable(), true, "Delete");
		record.checkCorrectEnableDisableStatus(record.isPublishTaskDisableOrEnable(), true, "Publish");
		record.checkCorrectEnableDisableStatus(record.isUploadToYoutubeDisableOrEnable(), true, "Upload to Youtube");
		record.checkCorrectEnableDisableStatus(record.isRequestCaptionsDisableOrEnable(), true, "Request Captions");
		record.checkCorrectEnableDisableStatus(record.isDownloadRecordingDisableOrEnable(), true, "Download");
		record.checkCorrectEnableDisableStatus(record.isEditRecordingDisableOrEnable(), true, "Edit Recording");
		record.checkCorrectEnableDisableStatus(record.isEditRecordingPropertiesDisableOrEnable(), true, "Edit Recording Properties");
		record.checkCorrectEnableDisableStatus(record.isShareRecordingDisableOrEnable(), true, "Share Recording");
			
		//20.Select at least two recordings & hover over the "Recording Tasks" button
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox2);
		record.moveToElementAndPerform(record.recording_tasks_button, driver);
		
		//21.The following items *should be selectable*:
		//** Move
		//** Copy
		//** Delete
		//** Publish
		//** Upload to YouTube
		//** Request Captions
		//** Download
		record.checkCorrectEnableDisableStatus(record.isMoveTaskDisableOrEnable(), true, "Move");
		record.checkCorrectEnableDisableStatus(record.isCopyTaskDisableOrEnable(), true, "Copy");
		record.checkCorrectEnableDisableStatus(record.isDeleteTaskDisableOrEnable(), true, "Delete");
		record.checkCorrectEnableDisableStatus(record.isPublishTaskDisableOrEnable(), true, "Publish");
		record.checkCorrectEnableDisableStatus(record.isUploadToYoutubeDisableOrEnable(), true, "Upload to Youtube");
		record.checkCorrectEnableDisableStatus(record.isRequestCaptionsDisableOrEnable(), true, "Request Captions");
		record.checkCorrectEnableDisableStatus(record.isDownloadRecordingDisableOrEnable(), true, "Download");
			
		//22.The following items *should not be selectable*:
		//** ----
		//** This text: "_To edit a recording or its properties please select only one recording at a time._"
		//** ----
		//** Edit Recording
		//** Edit Recording Properties
		//** Share Recording
		record.verifyThatMessageShownUnderRecordingTasksWhenChooseTwoRecording();
		record.checkCorrectEnableDisableStatus(record.isEditRecordingDisableOrEnable(), false, "Edit Recording");
		record.checkCorrectEnableDisableStatus(record.isEditRecordingPropertiesDisableOrEnable(), false, "Edit Recording Properties");
		record.checkCorrectEnableDisableStatus(record.isShareRecordingDisableOrEnable(), false, "Share Recording");
		
		//delete the bookmark
		record.clickOnBookmarksTab();
		record.deleteBookmarkInBookmarkTab(bookmark_to_add);
		
		
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
	
	}
	
}