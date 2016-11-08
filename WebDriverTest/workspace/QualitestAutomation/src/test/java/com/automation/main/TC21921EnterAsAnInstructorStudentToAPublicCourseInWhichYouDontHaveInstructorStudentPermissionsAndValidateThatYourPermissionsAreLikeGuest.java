package com.automation.main;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.function.IntToDoubleFunction;
import java.text.DateFormat;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;import com.automation.main.page_helpers.Page;import com.automation.main.page_helpers.Page;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import junitx.util.PropertyManager;


@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC21921EnterAsAnInstructorStudentToAPublicCourseInWhichYouDontHaveInstructorStudentPermissionsAndValidateThatYourPermissionsAreLikeGuest {

	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}

	public TC21921EnterAsAnInstructorStudentToAPublicCourseInWhichYouDontHaveInstructorStudentPermissionsAndValidateThatYourPermissionsAreLikeGuest() {
		// TODO Auto-generated constructor stub
	}

	public CreateNewCourseWindow create_new_course_window;
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

	@BeforeClass
	public void setup() {

		driver = DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());

		// 
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
		player_page = PageFactory.initElements(driver, PlayerPage.class);
		create_new_course_window = PageFactory.initElements(driver, CreateNewCourseWindow.class);
		
		wait = new WebDriverWait(driver, 30);
		
		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC21921EnterAsAnInstructorStudentToAPublicCourseInWhichYouDontHaveInstructorStudentPermissionsAndValidateThatYourPermissionsAreLikeGuest at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC21921EnterAsAnInstructorStudentToAPublicCourseInWhichYouDontHaveInstructorStudentPermissionsAndValidateThatYourPermissionsAreLikeGuest at " + DateToStr,
		 "Starting the test: TC21921EnterAsAnInstructorStudentToAPublicCourseInWhichYouDontHaveInstructorStudentPermissionsAndValidateThatYourPermissionsAreLikeGuest at " + DateToStr, LogAs.PASSED, null);	
		
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
		// 1. Make sure to have a course to which both of the users are not enrolled to neither as Student nor Instructor - Create course and enroll SuperUser.
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
		tegrity.loginAdmin("Admin");
		initializeCourseObject();
		
		
		// Click on course builder href link
		Thread.sleep(Page.TIMEOUT_TINY);
		admin_dashboard_page.clickOnTargetSubmenuCourses("Manage Ad-hoc Courses / Enrollments (Course Builder)");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("SelfRegConfig")));
				

		// Click on create course href link 
		driver.switchTo().frame(0);
		//mange_adhoc_course_enrollments.clickOnNewCourse();
		admin_dashboard_page.waitForVisibility(manage_adhoc_courses_enrollments_page.new_course_button);
		// Create six dynamic courses
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyhhmmss");
				
		String temp_course_name = "TempCourseName" + sdf.format(date);
					
		wait.until(ExpectedConditions.visibilityOf(manage_adhoc_courses_enrollments_page.new_course_button));
		
		manage_adhoc_courses_enrollments_page.clickOnNewCourse();
		
		create_new_course_window.waitForVisibility(create_new_course_window.course_id_input);
		
		create_new_course_window.createNewCourse(temp_course_name + "_Name", temp_course_name);
		
		for(int j=0;j<5;j++) {
			try {
				driver.switchTo().alert().accept();
				break;
			} catch (Exception msg) {
				Thread.sleep(Page.TIMEOUT_TINY);
			}
		}
		
		// Search target course name
		manage_adhoc_courses_enrollments_page.searchAndFilterCourses(temp_course_name);
					
		Thread.sleep(Page.TIMEOUT_TINY);
		// Click on result first course (the only one) membership button
		manage_adhoc_courses_enrollments_page.clickOnFirstCourseMembershipButton();
		Thread.sleep(Page.TIMEOUT_TINY);		
		
		
		// Search target user name in membership window
		mange_ad_hoc_courses_membership_window.searchForUser(PropertyManager.getProperty("SuperUser"));	

		// Select first user from user list (the only user it found because of the uniq of the search)
		mange_ad_hoc_courses_membership_window.selectFirstUserFromUserList();

		// Add selected user to instructor list
		mange_ad_hoc_courses_membership_window.clickOnAddSelectedUserToInstructorList();
		mange_ad_hoc_courses_membership_window.waitMaxTimeUntillInstructorEnrollToCourse(PropertyManager.getProperty("SuperUser"));
		mange_ad_hoc_courses_membership_window.waitForVisibility(mange_ad_hoc_courses_membership_window.ok_button);
		
		// Confirm user membership list
		mange_ad_hoc_courses_membership_window.clickOnOkButton();
		mange_ad_hoc_courses_membership_window.wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("ctl00_ContentPlaceHolder1_ucAddMemberships_ucDialog_ButtonAddInstructor")));
		
		
		// Sign out
		for(String window: driver.getWindowHandles()) {
			driver.switchTo().window(window);
			break;
		}
		manage_adhoc_courses_enrollments_page.waitForVisibility(driver.findElement(By.id("SignOutLink")));
		driver.findElement(By.id("SignOutLink")).click();
		
		// 2. Make sure that course is publicly visible (Click on "Manage Course Settings" in the "Course" section).
		// 3. That course must have these course settings enabled:
		// Allow students to record
		// Make this course publicly visible
		// Allow students to download recordings
		// Enable MP3 Podcast
		// Enable Video Podcast
		// Enable RSS feed
		tegrity.loginCourses("SuperUser");
		
		course.selectCourseThatStartingWith(temp_course_name);
		
		record.clickOnCourseTaskThenCourseSettings();
		
		course_settings_page.makeSureThatMakeCoursePublicIsSelected();
		course_settings_page.makeSureThatAllowStudentsToDownloadRecordingsIsSelected();
		course_settings_page.makeSureThatEnableRSSFeedIsSelected();
		course_settings_page.makeSureThatAllowStudentsToRecordIsSelected();
		course_settings_page.enableAudioPodcast();
		course_settings_page.enableVideoPodcast();
		
		course_settings_page.clickOnOkButton();
		
		record.returnToCourseListPage();
		
		// 4. Make sure the course has at least one of each type of recording and an additional content file.
		course.copyOneRecordingFromCourseStartWithToCourseStartWithOfType("BankValid", temp_course_name, 0, record, copy, confirm_menu);
		course.copyOneRecordingFromCourseStartWithToCourseStartWithOfType("BankValid", temp_course_name, 1, record, copy, confirm_menu);
		course.copyOneRecordingFromCourseStartWithToCourseStartWithOfType("BankValid", temp_course_name, 2, record, copy, confirm_menu);
//		
		top_bar_helper.clickOnSignOut();
		
		
		// 29. Repeat the test as the Student from the precondition.
		for(int user_type=0; user_type<2; user_type++) {
			if(user_type==0) {
				// 5. Login as INSTRUCTOR.
				tegrity.loginCourses("User1");
			} else {
				// 5. Login as student.
				tegrity.loginCourses("User4");
			}
			
			
			// 6. Click on "public courses" tab.
			course.clickOnPublicCoursesTab();
			Thread.sleep(Page.TIMEOUT_TINY);
			
			// 7. Click on a public course.
			course.selectCourseThatStartingWith(temp_course_name);
			Thread.sleep(Page.TIMEOUT_TINY);
			
			// 8. Click on some recording.
			String first_recording_name = record.getFirstRecordingTitle();
			record.clickOnTargetRecordingAndOpenItsPlayback(first_recording_name);
			
			// 9. Click on a specific chapter - the recording page opens, the recording is running.
			player_page.verifyTimeBufferStatusForXSec(5);
			
			// 10. Validate that you cannot write any bookmarks to the recording - There is not 'Add' button in the 'Bookmarks and Notes' controller.
			player_page.verifyThatUserCannotAddBookmark();
			
			// 11. In the breadcrumbs, click on the course name.
			driver.navigate().back();
			
			Thread.sleep(Page.TIMEOUT_TINY);
			
			// 12. Validate there is no "Start a test" nor "Start a recording" (big blue) buttons at the top right of the screen.
			record.verifyNoStartRecording();
			record.verifyNoStartTest();
			
			// 13. Hover over "Course tasks" menu.
			wait.until(ExpectedConditions.visibilityOf(record.course_task_button));
			Actions builder = new Actions(driver);
			builder.moveToElement(record.course_task_button).build().perform();
			Thread.sleep(Page.TIMEOUT_TINY);
			
			// 14. Validate that the options: "RSS feed", "Podcast" and "Video podcast" are the ONLY menu options and they are all enabled (clickable).
//			record.verifyElementIsEnabled(record.rssfeed, "RSS feed");
//			record.verifyElementIsEnabled(record.podcast_button, "Podcast");
//			record.verifyElementIsEnabled(record.video_podcast, "Video podcast");
			List<String> target_option_list1 = new ArrayList<String>();
			target_option_list1.add("RSS Feed");
			target_option_list1.add("Podcast");
			target_option_list1.add("Video Podcast");
			record.verifyTargetListOfOptionIsTheOnlyOptionsWhichEnabledInCourseTasksMenu(target_option_list1);
			
			
			// 15. Click on a checkbox of a specific recording.
			record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
			
			// 16. Hover over "Recording tasks" menu.
			record.moveToElement(record.recording_tasks_button, driver).perform();
			Thread.sleep(Page.TIMEOUT_TINY);
			
			// 17. Validate that the option "Download recording" is the ONLY displayed menu option and it is enabled (clickable).
//			record.verifyElementIsEnabled(record.download_button, "Download recording");
			List<String> target_option_list = new ArrayList<String>();
			target_option_list.add("Download");
			record.verifyTargetListOfOptionIsTheOnlyOptionsWhichEnabledInRecordingTaskMenu(target_option_list);
			
			// 18. Click on the "Additional Content" tab.
			record.clickOnAdditionContentTab();
			Thread.sleep(Page.TIMEOUT_TINY);
			
			// 19. Hover over "Course tasks" menu.
			record.moveToElement(record.course_task_button, driver).perform();
			Thread.sleep(Page.TIMEOUT_TINY);
			
			// 20. Validate that the options: "RSS feed", "Podcast" and "Video podcast" are the ONLY menu options and they are all enabled (clickable)
//			record.verifyElementIsEnabled(record.rssfeed, "RSS feed");
//			record.verifyElementIsEnabled(record.podcast_button, "Podcast");
//			record.verifyElementIsEnabled(record.video_podcast, "Video podcast");
			record.verifyTargetListOfOptionIsTheOnlyOptionsWhichEnabledInCourseTasksMenu(target_option_list1);
			
			// 21. Validate the 'Content tasks' button is not displayed.
			record.verifyThatContentTaskButtonNotDisplayed();
			
			// 22. Click on the 'Student Recordings' tab.
			record.clickOnStudentRecordingsTab();
			Thread.sleep(Page.TIMEOUT_TINY);
			
			// 23. Hover over "Course tasks" menu.
			record.moveToElement(record.course_task_button, driver).perform();
			Thread.sleep(Page.TIMEOUT_TINY);
			
			// 24. Validate that the options: "RSS feed", "Podcast" and "Video podcast" are the ONLY menu options and they are all enabled (clickable).
//			record.verifyElementIsEnabled(record.rssfeed, "RSS feed");
//			record.verifyElementIsEnabled(record.podcast_button, "Podcast");
//			record.verifyElementIsEnabled(record.video_podcast, "Video podcast");
			record.verifyTargetListOfOptionIsTheOnlyOptionsWhichEnabledInCourseTasksMenu(target_option_list1);
			
			// 25. Click on a checkbox of a specific recording.
			record.selectFirstCheckbox();
			
			// 26. Hover over "Recording tasks" menu.
			record.moveToElement(record.recording_tasks_button, driver).perform();
			Thread.sleep(Page.TIMEOUT_TINY);
			
			// 27. Validate that the option "Download recording" is the ONLY displayed menu option and it is enabled (clickable).
//			record.verifyElementIsEnabled(record.download_button, "Download recording");
			record.verifyTargetListOfOptionIsTheOnlyOptionsWhichEnabledInRecordingTaskMenu(target_option_list);
			
			
			// 28. Validate the 'Tests' tab is not displayed.
			record.verifyNoTestsTab();
			
			// Sign out
			top_bar_helper.clickOnSignOut();
			
			Thread.sleep(Page.TIMEOUT_TINY);
		}
		
		// 30. Uncheck make this course public and uneroll SuperUser.
		tegrity.loginCourses("SuperUser");
		course.selectCourseThatStartingWith(temp_course_name);
		record.clickOnCourseTaskThenCourseSettings();
		course_settings_page.makeSureThatMakeCoursePublicIsUnSelected();
		course_settings_page.clickOnOkButton();
		Thread.sleep(Page.TIMEOUT_TINY);
		top_bar_helper.clickOnSignOut();
					
		Thread.sleep(Page.TIMEOUT_TINY);
			
		tegrity.loginAdmin("Admin");
			
		// Click on course builder href link
		Thread.sleep(Page.TIMEOUT_TINY);
		admin_dashboard_page.clickOnTargetSubmenuCourses("Manage Ad-hoc Courses / Enrollments (Course Builder)");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("SelfRegConfig")));
					

		// Click on create course href link 
		driver.switchTo().frame(0);
		//mange_adhoc_course_enrollments.clickOnNewCourse();
		admin_dashboard_page.waitForVisibility(manage_adhoc_courses_enrollments_page.new_course_button);
			
		for(int j=0;j<5;j++) {
			try {
				driver.switchTo().alert().accept();
				break;
			} catch (Exception msg) {
				Thread.sleep(Page.TIMEOUT_TINY);
			}
		}
			
		// Search target course name
		manage_adhoc_courses_enrollments_page.searchAndFilterCourses(temp_course_name);
						
		Thread.sleep(Page.TIMEOUT_TINY);
		// Click on result first course (the only one) membership button
		manage_adhoc_courses_enrollments_page.clickOnFirstCourseMembershipButton();
		Thread.sleep(Page.TIMEOUT_TINY);		
			
			
		// Search target user name in membership window
		mange_ad_hoc_courses_membership_window.waitMaxTimeUntillInstructorEnrollToCourse(PropertyManager.getProperty("SuperUser"));


		// Remove selected user to instructor list
		mange_ad_hoc_courses_membership_window.clickOnRemoveSelectedUserToInstructorList();
		mange_ad_hoc_courses_membership_window.waitForVisibility(mange_ad_hoc_courses_membership_window.ok_button);
			
		// Confirm user membership list
		mange_ad_hoc_courses_membership_window.clickOnOkButton();
		mange_ad_hoc_courses_membership_window.wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("ctl00_ContentPlaceHolder1_ucAddMemberships_ucDialog_ButtonAddInstructor")));
			
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);

		
}}
