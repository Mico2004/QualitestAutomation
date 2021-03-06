package com.automation.main.public_courses;


import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
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
import com.automation.main.page_helpers.CreateNewUserWindow;
import com.automation.main.page_helpers.DeleteMenu;
import com.automation.main.page_helpers.LoginHelperPage;
import com.automation.main.page_helpers.ManageAdHocCoursesMembershipWindow;
import com.automation.main.page_helpers.ManageAdhocCoursesEnrollmentsPage;
import com.automation.main.page_helpers.ManageAdhocUsersPage;
import com.automation.main.page_helpers.MoveWindow;
import com.automation.main.page_helpers.PlayerPage;
import com.automation.main.page_helpers.RecordingHelperPage;
import com.automation.main.page_helpers.TopBarHelper;
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


@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC21924VerifyInstructorPermissionsConsitrncyOnActivePastPublicCourses {

	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}

	public PlayerPage player_page;
	public ManageAdHocCoursesMembershipWindow mange_ad_hoc_courses_membership_window;
	public ManageAdhocCoursesEnrollmentsPage manage_adhoc_courses_enrollments_page;
	public CourseSettingsPage course_settings_page;
	public TopBarHelper top_bar_helper;
	public LoginHelperPage tegrity;
	public CoursesHelperPage course;
	public RecordingHelperPage record;
	public ConfirmationMenu confirm_menu;
	public MoveWindow move_window;
	public DeleteMenu delete_menu;
	WebDriver driver;
	WebDriverWait wait;
	public static WebDriver thread_driver;
	CopyMenu copy;
	String currentCourse;
	String targetCourse;
	String clickedRecording;
	DesiredCapabilities capability;
	public AdminDashboardPage admin_dashboard_page;
	public AdminDashboardViewCourseList admin_dashboard_view_course_list;
	public ManageAdhocUsersPage mange_adhoc_users_page;
	public CreateNewUserWindow create_new_user_window;
	public AdvancedServiceSettingsPage advanced_service_settings_Page;
	public ManageAdhocCoursesEnrollmentsPage mange_adhoc_course_enrollments;
	public ManageAdHocCoursesMembershipWindow mangage_adhoc_courses_membership_window;

	@BeforeClass
	public void setup() {

		driver = DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());
		ATUReports.setWebDriver(driver);
		tegrity = PageFactory.initElements(driver, LoginHelperPage.class);
		record = PageFactory.initElements(driver, RecordingHelperPage.class);
		copy = PageFactory.initElements(driver, CopyMenu.class);		
		mange_adhoc_users_page = PageFactory.initElements(driver, ManageAdhocUsersPage.class);
		create_new_user_window = PageFactory.initElements(driver, CreateNewUserWindow.class);
		confirm_menu = PageFactory.initElements(driver, ConfirmationMenu.class);
		move_window = PageFactory.initElements(driver, MoveWindow.class);
		delete_menu = PageFactory.initElements(driver, DeleteMenu.class);
		course_settings_page = PageFactory.initElements(driver, CourseSettingsPage.class);
		admin_dashboard_page = PageFactory.initElements(driver, AdminDashboardPage.class);	
		mange_adhoc_course_enrollments = PageFactory.initElements(driver, ManageAdhocCoursesEnrollmentsPage.class);
		top_bar_helper = PageFactory.initElements(driver, TopBarHelper.class);
		admin_dashboard_view_course_list = PageFactory.initElements(driver, AdminDashboardViewCourseList.class);
		manage_adhoc_courses_enrollments_page = PageFactory.initElements(driver, ManageAdhocCoursesEnrollmentsPage.class);
		mangage_adhoc_courses_membership_window = PageFactory.initElements(driver, ManageAdHocCoursesMembershipWindow.class);
		player_page = PageFactory.initElements(driver, PlayerPage.class);
		advanced_service_settings_Page = PageFactory.initElements(driver, AdvancedServiceSettingsPage.class);
		
		wait = new WebDriverWait(driver, 30);
		
		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC21924VerifyInstructorPermissionsConsitrncyOnActivePastPublicCourses at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC21924VerifyInstructorPermissionsConsitrncyOnActivePastPublicCourses at " + DateToStr,
		 "Starting the test: TC21924VerifyInstructorPermissionsConsitrncyOnActivePastPublicCourses at " + DateToStr, LogAs.PASSED, null);	
		
	}

	
	 @AfterClass
	 public void closeBroswer() {
		 this.driver.quit();
	 }

	// description = "get courses list"
	public void initializeCourseObject() throws InterruptedException {

		course = PageFactory.initElements(driver, CoursesHelperPage.class);
		course.courses = course.getCoursesListFromElement(course.course_list);
	}

	@Test (description = "TC 21924 Verify Instructor Permissions Consitrncy On Active Past Public Courses")
	public void test21924() throws Exception
	{
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);	
		wait.until(ExpectedConditions.titleContains("Tegrity Lecture Capture"));
		String login_url = driver.getCurrentUrl();
		String university_name  = login_url.split("/")[2].substring(0,  login_url.split("/")[2].length() - 12);
		String PastCourseA="PastCourseA"+university_name+PropertyManager.getProperty("User1").substring(5, PropertyManager.getProperty("User1").length());
		
		
	
/*	tegrity.loginAdmin("Admin");	
	Thread.sleep(3000);
	
	//PreTest enable student test +youtube + capition
	admin_dashboard_page.clickOnTargetSubmenuAdvancedServices("Advanced Service Settings");
	advanced_service_settings_Page.enableYoutbeCapitionStudent(confirm_menu);	
	Thread.sleep(2000);
		
	Thread.sleep(4000);	
		
		admin_dashboard_page.clickOnTargetSubmenuCourses("Manage Ad-hoc Courses / Enrollments (Course Builder)");
		for(int i=0; i<10; i++) {
			try {
				driver.switchTo().frame(0);
				break;
			} catch(Exception msg) {
				Thread.sleep(1000);
			}
		}
		
		mange_adhoc_course_enrollments.searchAndFilterCourses(PastCourseA);
		
		mange_adhoc_course_enrollments.clickOnFirstCourseMembershipButton();
		
		mangage_adhoc_courses_membership_window.searchForUser(PropertyManager.getProperty("User1"));	
		Thread.sleep(2000);
		// Select first user from user list (the only user it found because of the uniq of the search)
		mangage_adhoc_courses_membership_window.selectFirstUserFromUserList();

		// Add selected user to instructor list
		mangage_adhoc_courses_membership_window.clickOnAddSelectedUserToInstructorList();
		
		mangage_adhoc_courses_membership_window.waitMaxTimeUntillInstructorEnrollToCourse(PropertyManager.getProperty("User1"));
		
		mangage_adhoc_courses_membership_window.waitForVisibility(mangage_adhoc_courses_membership_window.ok_button);
		
		// Confirm user membership list
		mangage_adhoc_courses_membership_window.clickOnOkButton();
		
			for(String window: driver.getWindowHandles()) {
			driver.switchTo().window(window);
			break;
		}
		mange_adhoc_course_enrollments.signOut();*/
		
		
		
		// 1. Make sure the Instructor has past course which is publicly visible.
		// Login with SuperUser
		tegrity.loginCourses("SuperUser");// log in courses page
		initializeCourseObject();		
		
		//  Go to PastCourseA (which is past course of User1) and make it public	
		String past_public_course_name = course.selectCourseThatStartingWith("PastCourseA"+course.getFQDN()+course.getPreSetTimeStamp());		
		record.clickOnCourseTaskThenCourseSettings();
		course_settings_page.checkAllCourseSettingsCheckboxs();
		course_settings_page.clickOnOkButton();
		top_bar_helper.signOut();
		
		// 2. Make sure to have a user is enrolled to a course as Instructor (User1).
		tegrity.loginCourses("User1");
		
		
		// 3. Make sure the courses settings are all checked.
		String active_public_course_name = course.selectCourseThatStartingWith("Ab");
		record.clickOnCourseTaskThenCourseSettings();
		course_settings_page.checkAllCourseSettingsCheckboxs();
		course_settings_page.clickOnOkButton();
		
		// 4. Make sure the course has at least one of each type of recording and an additional content file - PreTest
		
		// 5. Login as INSTRUCTOR.
		record.returnToCourseListPage();
		
		// NOTE: Loop active_public_course_name for Active and Public
		List<String> active_course_recording_list = null;
		List<String> public_course_recording_list = null;
		
		List<String> active_course_additional_content_list = null;
		List<String> public_course_additional_content_list = null;
		
		List<String> active_course_student_recording_list = null;
		List<String> public_course_student_recording_list = null;
		
		List<String> active_course_tests_list = null;
		List<String> public_course_tests_list = null; 
		
		for(int type_of_course=0; type_of_course<2; type_of_course++) {
			if(type_of_course==1) {
				// 6. Click on the "public courses" tab.
				course.clickOnPublicCoursesTab();
				Thread.sleep(1000);
			}
			
			// 7. Click on a public course that: appear on your active courses list as well.
			course.selectCourseThatStartingWith(active_public_course_name);
			
			// 8. Hover over on "Course tasks" menu.
			record.moveToElement(record.course_task_button, driver).perform();
			
			
			
			// 9. Validate that the options are: "Course settings", "Upload a recording", "Upload video file", "Upload audio file", "Add additional content file", "Add additional content link", "Get live webcast", separated grey line, "RSS feed", "Podcast", "Video podcast".
			// "Course settings" - CourseSettings
			record.verifyWebElementDisplayed(record.course_settings_button, "Course settings");
			// "Upload a recording" - UploadRecording
			record.verifyWebElementDisplayed(record.upload_recording, "Upload a recording");
			// "Upload video file" - UploadVideoFile
			record.verifyWebElementDisplayed(record.upload_video_file, "Upload video file");
			// "Upload audio file" - UploadAudioFile
			record.verifyWebElementDisplayed(record.upload_audio_file, "Upload audio file");
			// "Add additional content file" - AddAdditionalContentFile
			record.verifyWebElementDisplayed(record.add_additional_content_file, "Add additional content file");
			// "Add additional content link" - AddAdditionalContentLink
			record.verifyWebElementDisplayed(record.add_additional_content_link, "Add additional content link");
			// "Get live webcast" - GetLiveWebcast
			record.verifyWebElementDisplayed(record.get_live_webcast, "Get live webcast");
			// "RSS feed" - RSSFeed
			record.verifyWebElementDisplayed(record.rssfeed, "RSS feed");
			// "Podcast" - Podcast
			record.verifyWebElementDisplayed(record.podcast_button, "Podcast");
			// "Video podcast" - VideoPodcast
			record.verifyWebElementDisplayed(record.video_podcast, "Video podcast");
			
			
	
						
						
			// 10. Check a checkbox of one recording on both browsers.
			record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
			
			// 11. Hover over on "Recording tasks" menu.
			record.moveToElement(record.recording_tasks_button, driver).perform();
			
			// 12. Validate that the options are: "Move", "Copy", "Delete", "Publish", "Tag", "Upload to YouTube", "Request Captions","Download". seperated grey line, "Edit recording", "Edit recording properties", "Share recording".
			// "Move" - MoveTask2
			record.verifyWebElementDisplayed(record.move_button_on_recording_tasks_menu, "Move");
			// "Copy" - CopyTask2
			record.verifyWebElementDisplayed(record.copy_button2, "Copy");
			// "Delete" - DeleteTask2
			record.verifyWebElementDisplayed(record.delete_button, "Delete");
			// "Publish" - PublishTask
			record.verifyWebElementDisplayed(record.publish_button, "Publish");
			
			// "Upload to YouTube" - uploadToYoutube
			record.verifyWebElementDisplayed(record.upload_to_youtube_button, "Upload to YouTube");
			// "Request Captions" - requestCaptions
			record.verifyWebElementDisplayed(record.request_captions_button, "Request Captions");
			// "Download" - DownloadRecording
			record.verifyWebElementDisplayed(record.download_button, "Download");
			// "Edit recording" - EditRecording
			record.verifyWebElementDisplayed(record.edit_rec_button, "Edit recording");
			// "Edit recording properties" - EditRecordingProperties
			record.verifyWebElementDisplayed(record.edit_rec_properties_button, "Edit recording properties");
			// "Share recording" - ShareRecording
			record.verifyWebElementDisplayed(record.share_recording_button, "Share recording");
			// "Tag" - TagsListTask2			
			record.verifyWebElementDisplayed(record.tag_button, "Tag");
		
			// 13. Validate that both buttons "Start a test" and "Start a recording" in the top right of the screen are displayed.
			record.veriftyThatStartRecordingButtonDisplayed();
			record.verifyThatStartATestDisplayed();
			
			// 14. Open another browser and login as the same instructor, Open the same course from the 'Active Courses' Tab (by looping). - Already do it from Step 6.
			
			// 15. Validate that the recordings list on both browsers contains the same recordings.
			if(type_of_course==0) {
				active_course_recording_list = record.getCourseRecordingList();
			} else {
				public_course_recording_list = record.getCourseRecordingList();
			}
			
			// 16. Click on "Additional Contents" on both browsers.
			record.clickOnAdditionContentTab();
			Thread.sleep(1000);
			
			// 17. Validate that the files list on both browsers contains the same recordings.
			if(type_of_course==0) {
				active_course_additional_content_list = record.getCoursAdditionalContentList();
			} else {
				public_course_additional_content_list = record.getCoursAdditionalContentList();
			}
			
			// 18. Click the 'Student Recordings' tab.
			record.clickOnStudentRecordingsTab();
			Thread.sleep(1000);
			
			// 19. Validate that the recordings list on both browsers contains the same recordings.
			if(type_of_course==0) {
				active_course_student_recording_list = record.getCourseRecordingList();
			} else {
				public_course_student_recording_list = record.getCourseRecordingList();
			}
			
			// 20 Click the 'Tests' tab.
			record.clickOnTestsTab();
			Thread.sleep(1000);
			
			// 21. Validate that the recordings list on both browsers contains the same recordings.
			if(type_of_course==0) {
				active_course_tests_list = record.getCourseRecordingList();
			} else {
				public_course_tests_list = record.getCourseRecordingList();
			}
			
			// 22. On the browser in which you have checked the course under "public courses" tab, click on "courses" breadcrumb.
			record.returnToCourseListPage();
		}
		
		// Validate that the recordings list on both browsers contains the same recordings - Recordings
		if(active_course_recording_list.size() == public_course_recording_list.size()) {
			active_course_recording_list.removeAll(public_course_recording_list);
			if(active_course_recording_list.size() == 0) {
				System.out.println("Verfied that recordings list on public and active course contains the same recordings.");
				ATUReports.add("Verfied that recordings list on public and active course contains the same recordings.", "True.", "True.", LogAs.PASSED, null);
				Assert.assertTrue(true);
			} else {
				System.out.println("Not verfied that recordings list on public and active course contains the same recordings.");
				ATUReports.add("Verfied that recordings list on public and active course contains the same recordings.", "True.", "False.", LogAs.FAILED, null);
				Assert.assertTrue(false);
			}
		} else {
			System.out.println("Not verfied that recordings list on public and active course contains the same recordings.");
			ATUReports.add("Verfied that recordings list on public and active course contains the same recordings.", "True.", "False.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		
		// Validate that the recordings list on both browsers contains the same recordings - Additional Content
		if(active_course_additional_content_list.size() == public_course_additional_content_list.size()) {
			active_course_additional_content_list.removeAll(public_course_additional_content_list);
			if(active_course_recording_list.size() == 0) {
				System.out.println("Verfied that additional content list on public and active course contains the same additional content.");
				ATUReports.add("Verfied that additional content list on public and active course contains the same additional content.", "True.", "True.", LogAs.PASSED, null);
				Assert.assertTrue(true);
			} else {
				System.out.println("Not verfied that additional content list on public and active course contains the same additional content.");
				ATUReports.add("Verfied that additional content list on public and active course contains the same additional content.", "True.", "False.", LogAs.FAILED, null);
				Assert.assertTrue(false);
			}
		} else {
			System.out.println("Not verfied that additional content list on public and active course contains the same additional content.");
			ATUReports.add("Verfied that additional content list on public and active course contains the same additional content.", "True.", "False.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		
		
		// Validate that the recordings list on both browsers contains the same recordings - Student Recordings
		if(active_course_student_recording_list.size() == public_course_student_recording_list.size()) {
			active_course_student_recording_list.removeAll(public_course_student_recording_list);
			if(active_course_recording_list.size() == 0) {
				System.out.println("Verfied that student recordings list on public and active course contains the same student recordings.");
				ATUReports.add("Verfied that student recordings list on public and active course contains the same student recordings.", "True.", "True.", LogAs.PASSED, null);
				Assert.assertTrue(true);
			} else {
				System.out.println("Not verfied that student recordings list on public and active course contains the same student recordings.");
				ATUReports.add("Verfied that student recordings list on public and active course contains the same student recordings.", "True.", "False.", LogAs.FAILED, null);
				Assert.assertTrue(false);
			}
		} else {
			System.out.println("Not verfied that student recordings list on public and active course contains the same student recordings.");
			ATUReports.add("Verfied that student recordings list on public and active course contains the same student recordings.", "True.", "False.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		
		// Validate that the recordings list on both browsers contains the same recordings - Tests
		if(active_course_tests_list.size() == public_course_tests_list.size()) {
			active_course_tests_list.removeAll(public_course_tests_list);
			if(active_course_recording_list.size() == 0) {
				System.out.println("Verfied that tests list on public and active course contains the same tests.");
				ATUReports.add("Verfied that tests list on public and active course contains the same tests.", "True.", "True.", LogAs.PASSED, null);
				Assert.assertTrue(true);
			} else {
				System.out.println("Not verfied that tests list on public and active course contains the same tests.");
				ATUReports.add("Verfied that tests list on public and active course contains the same tests.", "True.", "False.", LogAs.FAILED, null);
				Assert.assertTrue(false);
			}
		} else {
			System.out.println("Not verfied that tests list on public and active course contains the same tests.");
			ATUReports.add("Verfied that tests list on public and active course contains the same tests.", "True.", "False.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		
		// NOTE: Loop active_public_course_name for Active and Public
		List<String> past_course_recording_list = null;
		public_course_recording_list = null;
				
		List<String> past_course_additional_content_list = null;
		public_course_additional_content_list = null;
				
		List<String> past_course_student_recording_list = null;
		public_course_student_recording_list = null;
				
		List<String> past_course_tests_list = null;
		public_course_tests_list = null; 
				
		for(int type_of_course=0; type_of_course<2; type_of_course++) {
			if(type_of_course==1) {
				// 23. Click on "public courses" tab.
				course.clickOnPublicCoursesTab();
			} else {
				
				course.clickOnPastCoursesTabButton();
			}
			Thread.sleep(1000);
					
			// 24. Click on a public course that: 1. appear on your past courses list. 2. have a recordings and additional contents (uploaded files) in it.
			// 25. Open the same course on the Past course tab in a different browser (the same browser in which you have checked the previous course under "active courses" tab) (By looping).
			course.selectCourseThatStartingWith(past_public_course_name);
					
			// 26. Hover over on "Course tasks" menu.
			record.moveToElement(record.course_task_button, driver).perform();
			
			// 27. Validate that the options are: "Course Settings", separated grey line, "RSS feed", "Podcast", "Video podcast".
			// "Course settings" - CourseSettings
			record.verifyWebElementDisplayed(record.course_settings_button, "Course settings");		
			// "RSS feed" - RSSFeed
			record.verifyWebElementDisplayed(record.rssfeed, "RSS feed");			
			// "Podcast" - Podcast
			record.verifyWebElementDisplayed(record.podcast_button, "Podcast");	
			// "Video podcast" - VideoPodcast
			record.verifyWebElementDisplayed(record.video_podcast, "Video podcast");
			
			
			// 28. Check a checkbox of one recording on both browsers.
			record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
					
			// 29. Hover over on "Recording tasks" menu.
			record.moveToElement(record.recording_tasks_button, driver).perform();
			
			// 30. Validate that the options on both browsers are: "Move", "Copy", "Delete", "Publish", "Tag", "Upload to YouTube", "Request Captions",", "Download". separator grey line, "Edit recording", "Edit recording properties", "Share recording".
			// "Move" - MoveTask2
			record.verifyWebElementDisplayed(record.move_button_on_recording_tasks_menu, "Move");
			// "Copy" - CopyTask2
			record.verifyWebElementDisplayed(record.copy_button2, "Copy");
			// "Delete" - DeleteTask2
			record.verifyWebElementDisplayed(record.delete_button, "Delete");
			// "Publish" - PublishTask
			record.verifyWebElementDisplayed(record.publish_button, "Publish");
			// "Tag" - TagsListTask2		
			record.verifyWebElementNotDisplayed(record.tag_button, "Tag");
			// "Upload to YouTube" - uploadToYoutube
			record.verifyWebElementDisplayed(record.upload_to_youtube_button, "Upload to YouTube");
			// "Request Captions" - requestCaptions
			record.verifyWebElementDisplayed(record.request_captions_button, "Request Captions");
			// "Download" - DownloadRecording
			record.verifyWebElementDisplayed(record.download_button, "Download");
			// "Edit recording" - EditRecording
			record.verifyWebElementDisplayed(record.edit_rec_button, "Edit recording");
			// "Edit recording properties" - EditRecordingProperties
			record.verifyWebElementDisplayed(record.edit_rec_properties_button, "Edit recording properties");
			// "Share recording" - ShareRecording
			record.verifyWebElementDisplayed(record.share_recording_button, "Share recording");
		/*	List<String> target_option_list_PastCourse = new ArrayList<String>();
			target_option_list_PastCourse.add("Move");
			target_option_list_PastCourse.add("Copy");
			target_option_list_PastCourse.add("Delete");
			target_option_list_PastCourse.add("Publish");
			if(type_of_course!=0) // tag isn't displayed in past courses
			target_option_list_PastCourse.add("Tag");
			target_option_list_PastCourse.add("Upload to YouTube");
			target_option_list_PastCourse.add("Request Captions");
			target_option_list_PastCourse.add("Edit recording");
			target_option_list_PastCourse.add("Edit recording properties");
			target_option_list_PastCourse.add("Share recording");
			record.verifyTargetListOfOptionIsTheOnlyOptionsWhichEnabledInRecordingTaskMenu(target_option_list_PastCourse);*/
			// 31. Validate that on both browsers both buttons "Start a test" and "Start a recording" in the top right of the screen are not displayed.
			record.verifyNoStartRecording();
			record.verifyNoStartTest();
					
			// 32. Validate that the recordings list on both browsers contains the same recordings.
			if(type_of_course==0) {
				past_course_recording_list = record.getCourseRecordingList();
			} else {
				public_course_recording_list = record.getCourseRecordingList();
			}
					
			// 33. Click on "Additional Contents" on both browsers.
			record.clickOnAdditionContentTab();
			Thread.sleep(1000);
					
			// 34. Validate that the files list on both browsers contains the same recordings.
			if(type_of_course==0) {
				past_course_additional_content_list = record.getCoursAdditionalContentList();
			} else {
				public_course_additional_content_list = record.getCoursAdditionalContentList();
			}
					
			// 35. Click the 'Student Recordings' tab.
			record.clickOnStudentRecordingsTab();
			Thread.sleep(1000);
					
			// 36. Validate that the recordings list on both browsers contains the same recordings.
			if(type_of_course==0) {
				past_course_student_recording_list = record.getCourseRecordingList();
			} else {
				public_course_student_recording_list = record.getCourseRecordingList();
			}
					
			// 37. Click the 'Tests' tab.
			record.clickOnTestsTab();
			Thread.sleep(1000);
			
			// 38. Validate that the recordings list on both browsers contains the same recordings.
			if(type_of_course==0) {
				past_course_tests_list = record.getCourseRecordingList();
			} else {
				public_course_tests_list = record.getCourseRecordingList();
			}
					
			// 39. Open one of the public course playback - The recording is playable.
			if(type_of_course==1) {
				record.clickOnRecordingsTab();
				String first_recording_title = record.getFirstRecordingTitle();
				record.clickOnTargetRecordingAndOpenItsPlayback(first_recording_title);
				player_page.verifyTimeBufferStatusForXSec(5);
			} else {
				record.returnToCourseListPage();
			}
			
		}
				
		// Validate that the recordings list on both browsers contains the same recordings - Recordings
		if(past_course_recording_list.size() == public_course_recording_list.size()) {
			past_course_recording_list.removeAll(public_course_recording_list);
			if(past_course_recording_list.size() == 0) {
				System.out.println("Verfied that recordings list on public and past course contains the same recordings.");
				ATUReports.add("Verfied that recordings list on public and past course contains the same recordings.", "True.", "True.", LogAs.PASSED, null);
				Assert.assertTrue(true);
			} else {
				System.out.println("Not verfied that recordings list on public and past course contains the same recordings.");
				ATUReports.add("Verfied that recordings list on public and past course contains the same recordings.", "True.", "False.", LogAs.FAILED, null);
				Assert.assertTrue(false);
			}
		} else {
			System.out.println("Not verfied that recordings list on public and past course contains the same recordings.");
			ATUReports.add("Verfied that recordings list on public and past course contains the same recordings.", "True.", "False.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
				
		// Validate that the recordings list on both browsers contains the same recordings - Additional Content
		if(past_course_additional_content_list.size() == public_course_additional_content_list.size()) {
			past_course_additional_content_list.removeAll(public_course_additional_content_list);
			if(past_course_additional_content_list.size() == 0) {
				System.out.println("Verfied that additional content list on public and past course contains the same additional content.");
				ATUReports.add("Verfied that additional content list on public and past course contains the same additional content.", "True.", "True.", LogAs.PASSED, null);
				Assert.assertTrue(true);
			} else {
				System.out.println("Not verfied that additional content list on public and past course contains the same additional content.");
				ATUReports.add("Verfied that additional content list on public and past course contains the same additional content.", "True.", "False.", LogAs.FAILED, null);
				Assert.assertTrue(false);
			}
		} else {
			System.out.println("Not verfied that additional content list on public and past course contains the same additional content.");
			ATUReports.add("Verfied that additional content list on public and past course contains the same additional content.", "True.", "False.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
					
		// Validate that the recordings list on both browsers contains the same recordings - Student Recordings
		if(past_course_student_recording_list.size() == public_course_student_recording_list.size()) {
			past_course_student_recording_list.removeAll(public_course_student_recording_list);
			if(past_course_student_recording_list.size() == 0) {
				System.out.println("Verfied that student recordings list on public and past course contains the same student recordings.");
				ATUReports.add("Verfied that student recordings list on public and past course contains the same student recordings.", "True.", "True.", LogAs.PASSED, null);
				Assert.assertTrue(true);
			} else {
				System.out.println("Not verfied that student recordings list on public and past course contains the same student recordings.");
				ATUReports.add("Verfied that student recordings list on public and past course contains the same student recordings.", "True.", "False.", LogAs.FAILED, null);
				Assert.assertTrue(false);
			}
		} else {
			System.out.println("Not verfied that student recordings list on public and past course contains the same student recordings.");
			ATUReports.add("Verfied that student recordings list on public and past course contains the same student recordings.", "True.", "False.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
				
		// Validate that the recordings list on both browsers contains the same recordings - Tests
		if(past_course_tests_list.size() == public_course_tests_list.size()) {
			past_course_tests_list.removeAll(public_course_tests_list);
			if(past_course_tests_list.size() == 0) {
				System.out.println("Verfied that tests list on public and past course contains the same tests.");
				ATUReports.add("Verfied that tests list on public and past course contains the same tests.", "True.", "True.", LogAs.PASSED, null);
				Assert.assertTrue(true);
			} else {
				System.out.println("Not verfied that tests list on public and past course contains the same tests.");
				ATUReports.add("Verfied that tests list on public and past course contains the same tests.", "True.", "False.", LogAs.FAILED, null);
				Assert.assertTrue(false);
			}
		} else {
			System.out.println("Not verfied that tests list on public and past course contains the same tests.");
			ATUReports.add("Verfied that tests list on public and past course contains the same tests.", "True.", "False.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}

		// PostTest
		//signout
		record.exitInnerFrame();
		record.signOut();
		
		// login as super user
		tegrity.loginCourses("SuperUser");
		
		// enter to the public course
		course.selectCourseThatStartingWith(active_public_course_name);
		
		//make course public
		record.clickOnCourseTaskThenCourseSettings();
		course_settings_page.makeSureThatMakeCoursePublicIsUnSelected();
		course_settings_page.clickOnOkButton();
		
		// return to the course list page
		record.returnToCourseListPage();
		
		//enter to the public past course
		course.selectCourseThatStartingWith(past_public_course_name);
		
		//make course public
		record.clickOnCourseTaskThenCourseSettings();
		course_settings_page.makeSureThatMakeCoursePublicIsUnSelected();
		course_settings_page.clickOnOkButton();
		
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
		
	}
}
