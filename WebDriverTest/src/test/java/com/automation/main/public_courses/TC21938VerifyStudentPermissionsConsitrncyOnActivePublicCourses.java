package com.automation.main.public_courses;


import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.function.IntToDoubleFunction;
import java.text.DateFormat;
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

import com.automation.main.page_helpers.AdminCourseSettingsPage;
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

@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC21938VerifyStudentPermissionsConsitrncyOnActivePublicCourses {

	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}

	public TC21938VerifyStudentPermissionsConsitrncyOnActivePublicCourses() {
		// TODO Auto-generated constructor stub
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
	public AdminCourseSettingsPage admin_course_settings_page;

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

		top_bar_helper = PageFactory.initElements(driver, TopBarHelper.class);
		admin_dashboard_view_course_list = PageFactory.initElements(driver, AdminDashboardViewCourseList.class);
		manage_adhoc_courses_enrollments_page = PageFactory.initElements(driver, ManageAdhocCoursesEnrollmentsPage.class);
		mange_ad_hoc_courses_membership_window = PageFactory.initElements(driver, ManageAdHocCoursesMembershipWindow.class);
		advanced_service_settings_Page = PageFactory.initElements(driver, AdvancedServiceSettingsPage.class);
		player_page = PageFactory.initElements(driver, PlayerPage.class);
		admin_course_settings_page = PageFactory.initElements(driver, AdminCourseSettingsPage.class);
		wait = new WebDriverWait(driver, 30);
		

		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC21938VerifyStudentPermissionsConsitrncyOnActivePublicCourses at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC21938VerifyStudentPermissionsConsitrncyOnActivePublicCourses at " + DateToStr,
		 "Starting the test: TC21938VerifyStudentPermissionsConsitrncyOnActivePublicCourses at " + DateToStr, LogAs.PASSED, null);	
		
	}

	
	 @AfterClass
	 public void closeBroswer() {
		 this.driver.quit();
	 }

	// description = "get courses list"
	public void initializeCourseObject() throws InterruptedException {

		course = PageFactory.initElements(driver, CoursesHelperPage.class);
		course.courses = course.getStringFromElement(course.course_list);
	}

	@Test
	public void loginCourses() throws Exception
	{
		// 1. Make sure to have a user is enrolled to a course (Ab) as Student (User4).
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
		tegrity.loginAdmin("Admin");
		Thread.sleep(3000);
		
		//PreTest add the open enable student test in the Admin and public 
		admin_dashboard_page.clickOnTargetSubmenuAdvancedServices("Advanced Service Settings");
		advanced_service_settings_Page.enableStudyTestingCheckboxAndClickOk(confirm_menu);
		//advanced_service_settings_Page.clickElement(advanced_service_settings_Page.adminDashboard);
		Thread.sleep(1000);	
		
		admin_dashboard_page.clickOnTargetSubmenuCourses("Manage Course Settings");		
		Thread.sleep(2000);	
		
		admin_course_settings_page.makeSureThatLockMakeThisCoursePublicSelected();
		top_bar_helper.clickOnSignOut();
		
		
		// 2. On the University Course settings make sure all of the "Make this Course publicy visible" is locked and off.
		
	
		
		tegrity.loginCourses("User1");// log in courses page
		initializeCourseObject();
		
		// 2. Make sure the courses settings are all checked.
		// 3. Make sure the course has at least one of each type of recording and an additional content file - PreTest.
		String active_public_course_name = course.selectCourseThatStartingWith("Ab");
		record.clickOnCourseTaskThenCourseSettings();
		course_settings_page.checkAllCourseSettingsCheckboxs();
		course_settings_page.clickOnOkButton();
		top_bar_helper.clickOnSignOut();
		
		
		// 4. Login as Student.
		tegrity.loginCourses("User4");
		
		// NOTE: Loop active_public_course_name for Active and Public
		List<String> active_course_recording_list = null;
		List<String> public_course_recording_list = null;
		
		List<String> active_course_additional_content_list = null;
		List<String> public_course_additional_content_list = null;
		
		List<String> active_course_student_recording_list = null;
		List<String> public_course_student_recording_list = null;
		

		for(int type_of_course=0; type_of_course<2; type_of_course++) {
			if(type_of_course==1) {
				// 5. Click on the "public courses" tab.
				course.clickOnPublicCoursesTab();
				Thread.sleep(1000);
			}
			
			// 6. Click on a public course that: 1. appear on your active courses list  2. have a recordings and additional contents (uploaded files) in it. simultaneously Open the same course on the Active course tab in a different browser.
			course.selectCourseThatStartingWith(active_public_course_name);
			
			// 7. Hover over on "Course tasks" menu.
			record.moveToElementAndClick(record.course_task_button,driver);
			Thread.sleep(1000);
			
			// 8. Validate that on both browsers the options are: "Upload a recording", "Upload video file", "Upload Audio File" , separated grey line, "Subscribe to your course's" (text), "RSS feed", "Podcast", "Video podcast".
			// "Upload a recording" - UploadRecording
			record.verifyWebElementDisplayed(record.upload_recording, "Upload a recording");
			// "Upload video file" - UploadVideoFile
			record.verifyWebElementDisplayed(record.upload_video_file, "Upload video file");
			// "Upload audio file" - UploadAudioFile
			record.verifyWebElementDisplayed(record.upload_audio_file, "Upload audio file");
			
			// "Subscribe to your course's" (text)
			if(driver.findElement(By.xpath(".//*[@id='scrollableArea']/div[1]/div[1]/div[2]/div/ul/li/ul/li[8]/span")).getText().equals("Subscribe to Your Course's...")) {
				System.out.println("Verified that following text is displayed: Subscribe to your course's");
				ATUReports.add("Verified that following text is displayed: Subscribe to your course's", "True.", "True.", LogAs.PASSED, null);
				Assert.assertTrue(true);
			} else {
				System.out.println("Not verified that following text is displayed: Subscribe to your course's");
				ATUReports.add("Verified that following text is displayed: Subscribe to your course's", "True.", "False.", LogAs.FAILED, null);
				Assert.assertTrue(false);
			}
			
			// "RSS feed" - RSSFeed
			record.verifyWebElementDisplayed(record.rssfeed, "RSS feed");
			// "Podcast" - Podcast
			record.verifyWebElementDisplayed(record.podcast_button, "Podcast");
			// "Video podcast" - VideoPodcast
			record.verifyWebElementDisplayed(record.video_podcast, "Video podcast");
			
			
			// 9. Check a checkbox of one recording on both browsers.
			record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
			
			// 10. Hover over on "Recording tasks" menu.
			record.moveToElementAndClick(record.recording_tasks_button, driver);
			Thread.sleep(1000);
			// 11. Validate that the option on both browsers are ONLY: "Download recording", "tag".
			// "Tag" - TagsListTask2
			record.verifyWebElementDisplayed(record.tag_button, "Tag");
			// "Download" - DownloadRecording
			record.verifyWebElementDisplayed(record.download_button, "Download");
			
			// 12. Validate that on both browsers the button "Start a recording" in the top right of the screen is displayed.
			record.veriftyThatStartRecordingButtonDisplayed();
			
			// 13. Validate that the recordings list on both browsers containts the same recordings.
			if(type_of_course==0) {
				active_course_recording_list = record.getCourseRecordingList();
			} else {
				public_course_recording_list = record.getCourseRecordingList();
			}
			
			// 14. Click on "Additional Contents" on both browsers.
			record.clickOnAdditionContentTab();
			Thread.sleep(2000);
			
			// 15. Validate that the files list on both browsers containts the same recordings.
			if(type_of_course==0) {
				active_course_additional_content_list = record.getCoursAdditionalContentList();
			} else {
				public_course_additional_content_list = record.getCoursAdditionalContentList();
			}
			
			// 16. Click on "Student Recordings" on both browsers.
			record.clickOnStudentRecordingsTab();
			Thread.sleep(2000);
			
			// 17. Validate that the recordings list on both browsers containts the same recordings.
			if(type_of_course==0) {
				active_course_student_recording_list = record.getCourseRecordingList();
			} else {
				public_course_student_recording_list = record.getCourseRecordingList();
			}
			
			// 18. Validate the 'Test' tab is not displayed.
			record.verifyNoTestsTab();
			
			// 19. Open one of the public course playback.
			if(type_of_course==1) {
				record.clickOnRecordingsTab();
				Thread.sleep(2000);
				String first_recording_title = record.getFirstRecordingTitle();
				record.clickOnTargetRecordingAndOpenItsPlayback(first_recording_title);
				player_page.verifyTimeBufferStatusForXSec(10);
			} else {
				record.returnToCourseListPage();
			}
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
		
		// PostTest
//		driver.navigate().back();
//		Thread.sleep(1000);
		for(String handler: driver.getWindowHandles()) {
			driver.switchTo().window(handler);
			break;
		}
		top_bar_helper.clickOnSignOut();
		Thread.sleep(1000);
		
		tegrity.loginAdmin("Admin");
		Thread.sleep(1000);
			
		admin_dashboard_page.clickOnTargetSubmenuCourses("Manage Course Settings");		
		Thread.sleep(2000);		
		
		admin_course_settings_page.makeSureThatLockMakeThisCoursePublicUnSelected();
		
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);

		
	}
}
