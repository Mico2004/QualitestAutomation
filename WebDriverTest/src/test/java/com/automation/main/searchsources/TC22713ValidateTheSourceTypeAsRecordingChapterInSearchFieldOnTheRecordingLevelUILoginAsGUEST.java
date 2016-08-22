package com.automation.main.searchsources;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.automation.main.AddAdditionalContentFileWindow;
import com.automation.main.AdminDashboardPage;
import com.automation.main.AdminDashboardViewCourseList;
import com.automation.main.AdvancedServiceSettingsPage;
import com.automation.main.ConfirmationMenu;
import com.automation.main.CopyMenu;
import com.automation.main.CourseSettingsPage;
import com.automation.main.CoursesHelperPage;
import com.automation.main.CreateNewCourseWindow;
import com.automation.main.CreateNewUserWindow;
import com.automation.main.DeleteMenu;
import com.automation.main.DriverSelector;
import com.automation.main.EditRecordinPropertiesWindow;
import com.automation.main.EmailAndConnectionSettingsPage;
import com.automation.main.EmailInboxPage;
import com.automation.main.EmailLoginPage;
import com.automation.main.EulaPage;
import com.automation.main.GetSupprtWindow;
import com.automation.main.HelpPage;
import com.automation.main.LoginHelperPage;
import com.automation.main.ManageAdHocCoursesMembershipWindow;
import com.automation.main.ManageAdhocCoursesEnrollmentsPage;
import com.automation.main.ManageAdhocUsersPage;
import com.automation.main.MoveWindow;
import com.automation.main.PlayerPage;
import com.automation.main.PublishWindow;
import com.automation.main.RecordingHelperPage;
import com.automation.main.RunDiagnosticsPage;

public class TC22713ValidateTheSourceTypeAsRecordingChapterInSearchFieldOnTheRecordingLevelUILoginAsGUEST {
	// Set Property for ATU Reporter Configuration
		{
			System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

		}

		public EditRecordinPropertiesWindow erp_window;
		public LoginHelperPage tegrity;
		public CoursesHelperPage course;
		public RecordingHelperPage record;
		public ConfirmationMenu confirm_menu;
		public DeleteMenu delete_menu;
		public MoveWindow move_window;
		WebDriver driver;
		WebDriverWait wait;
		public static WebDriver thread_driver;
		CopyMenu copy;
		String currentCourse;
		String targetCourse;
		String clickedRecording;
		WebDriver driver2;
		public AdminDashboardPage admin_dashboard_page;
		public ManageAdhocCoursesEnrollmentsPage mange_adhoc_course_enrollments;
		public CreateNewCourseWindow create_new_course_window;
		public ManageAdhocUsersPage mange_adhoc_users_page;
		public CreateNewUserWindow create_new_user_window;
		public ManageAdHocCoursesMembershipWindow mangage_adhoc_courses_membership_window;
		public AddAdditionalContentFileWindow add_additional_content_window;
		public AdvancedServiceSettingsPage advanced_services_setting_page;
		public HelpPage help_page;
		public CourseSettingsPage course_settings;
		public EmailAndConnectionSettingsPage email_setting;
		public EulaPage eula_page;
		public GetSupprtWindow support_window;
		public EmailLoginPage email_login;
		public EmailInboxPage email_inbox;
		public RunDiagnosticsPage run_diagnostics;
		public PlayerPage player_page;
		public PublishWindow publish_window;
		public AdminDashboardViewCourseList admin_view_course_list;
		String instructor1;
		String instructor2;
		List<String> for_enroll;

		@BeforeClass
		public void setup() {

			driver = DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());
			

			tegrity = PageFactory.initElements(driver, LoginHelperPage.class);

			wait = new WebDriverWait(driver, 30);
			add_additional_content_window = PageFactory.initElements(driver, AddAdditionalContentFileWindow.class);
			publish_window = PageFactory.initElements(driver, PublishWindow.class);
			email_setting = PageFactory.initElements(driver, EmailAndConnectionSettingsPage.class);
			record = PageFactory.initElements(driver, RecordingHelperPage.class);
			copy = PageFactory.initElements(driver, CopyMenu.class);
			delete_menu = PageFactory.initElements(driver, DeleteMenu.class);
			course = PageFactory.initElements(driver, CoursesHelperPage.class);
			confirm_menu = PageFactory.initElements(driver, ConfirmationMenu.class);
			course_settings = PageFactory.initElements(driver, CourseSettingsPage.class);
			wait = new WebDriverWait(driver, 30);
			move_window = PageFactory.initElements(driver, MoveWindow.class);
			erp_window = PageFactory.initElements(driver, EditRecordinPropertiesWindow.class);
			admin_dashboard_page = PageFactory.initElements(driver, AdminDashboardPage.class);
			advanced_services_setting_page = PageFactory.initElements(driver, AdvancedServiceSettingsPage.class);
			mange_adhoc_course_enrollments = PageFactory.initElements(driver, ManageAdhocCoursesEnrollmentsPage.class);
			eula_page = PageFactory.initElements(driver, EulaPage.class);
			create_new_course_window = PageFactory.initElements(driver, CreateNewCourseWindow.class);
			support_window = PageFactory.initElements(driver, GetSupprtWindow.class);
			mange_adhoc_users_page = PageFactory.initElements(driver, ManageAdhocUsersPage.class);
			email_login = PageFactory.initElements(driver, EmailLoginPage.class);
			create_new_user_window = PageFactory.initElements(driver, CreateNewUserWindow.class);
			email_inbox = PageFactory.initElements(driver, EmailInboxPage.class);
			mangage_adhoc_courses_membership_window = PageFactory.initElements(driver,
					ManageAdHocCoursesMembershipWindow.class);
			help_page = PageFactory.initElements(driver, HelpPage.class);
			run_diagnostics = PageFactory.initElements(driver, RunDiagnosticsPage.class);
			player_page = PageFactory.initElements(driver, PlayerPage.class);
			admin_view_course_list = PageFactory.initElements(driver, AdminDashboardViewCourseList.class);
		}

		@Test
		public void test22662() throws Exception {

			////pre conditions

			// 1.load page
			tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
			tegrity.waitForVisibility(tegrity.passfield);

			// 2.login as guest
			tegrity.loginAdmin("Admin");
			admin_dashboard_page.waitForVisibility(admin_dashboard_page.sign_out);

			//3.Validate "Allow students to download recordings" option in "Manage Course Settings" from "Courses" section is enable
			admin_dashboard_page.clickOnTargetSubmenuCourses("Manage Course Settings");
			course_settings.waitForVisibility(course_settings.getOk_button());
			course_settings.CheckAllowStudentDownload();


			// 3.Click on sign out
			admin_dashboard_page.waitForVisibility(admin_dashboard_page.sign_out);
			Thread.sleep(1500);
			admin_dashboard_page.signOut();
			///4. login as guest
			tegrity.waitForVisibility(tegrity.passfield);
			tegrity.loginAsguest();

	
			// 5.Select a course
			course.waitForVisibility(course.first_course_button);
			String course_name=course.selectCourseThatStartingWith("Ab");

			// 6.Click on one of the Recording link
			record.waitForVisibility(record.first_recording);
			Thread.sleep(2000);
			record.convertRecordingsListToNames();
			record.convertRecordingsListToRecorderName();
			String instructor=record.getIndexRecorderNameOfRecording(1);
			String recording_to_search=record.recording_list_names.get(0);
			Thread.sleep(2000);
			record.verifyFirstExpandableRecording();
			driver.findElement(By.cssSelector(".panel-body>.video-outer.ng-scope>.video-wrap")).click();
			Thread.sleep(15000);

			// 7.Select the Recording by clicking on one of the chapters
			player_page.verifyTimeBufferStatusForXSec(10);// check source display

			///// to go back to crecording window handler
			String curr_win=driver.getWindowHandle();	
			for (String handler : driver.getWindowHandles()) {
				driver.switchTo().window(handler);
				break;		
			}


			///8.Validate the search field is display at the top right of the UI page below the top navigation bar.
			player_page.veriySearchBoxLocation();

			///9.Validate the text in the Tegrity Player page: "Search in this recording..."
			player_page.verifySearchBoxHint();
			Thread.sleep(2000);
			//10.Search the "Recording Chapter" from the recording that we mentioned in the preconditions and press ENTER.


			player_page.verifySearchForRecordingExist(recording_to_search);
			player_page = PageFactory.initElements(driver, PlayerPage.class);


			Thread.sleep(2000);
			//11.The tegrity logo is displayed on the bottom footer bar right side.

			player_page.verifyTegrityLogoVisibilityAndLocation();


			///12.Recording timeline, controls and tasks is displayed.
			player_page.verifyPlayersButtonsAndTimeBuffer();



			///13.The next result display below the current result in case there is next result.
			player_page.verifyThatNextResultDisplayBelowCurrentResultInCaseThereIsNextResult(player_page.search_result);

			//14. The Bookmarks and Notes window is displayed.
			player_page.verifybookmarkTitle();
			for (String handler : driver.getWindowHandles()) {
				driver.switchTo().window(handler);
				break;
			}



			//15.The university logo is displayed on the top footer bar left side.
			player_page.verifyUniversityLogoVisibilityAndLocation();

			///16.search results page in the format as follows: "recording name - Search Results".
			driver.switchTo().frame(0);
			player_page = PageFactory.initElements(driver, PlayerPage.class);
			Thread.sleep(2000);
			player_page.verifySearchResultPage(recording_to_search);

			///17.The search results on a recording level is displayed in the table with the columns as follows: "Location", "Time", "Context"
			player_page.waitForVisibility(player_page.columns_title_text.get(0));
			player_page.verifySearchColumns();
			///18.The search result items is displayed as rows in the table.

			player_page.verifySearchResultsTableAreInRows();

			///19.Hover over the chapter icon:The background color change to deep gray.

			player_page.moveToElement(player_page.search_result_location.get(0), driver).perform();
			Thread.sleep(1000);
			player_page.verifyBackgroundColor("#f1f1f1",player_page.search_result.get(0));

			///20.Click on the result row.
			player_page.search_result_location.get(0).click();

			//21.The Tegrity Player page is opened and the recording start playing from the chapter start time.

			for (String handler : driver.getWindowHandles()) {
				driver.switchTo().window(handler);
				break;
			}

			Thread.sleep(10000);
			player_page.verifyTimeBufferStatusForXSec(10);// check source display

			for (String handler : driver.getWindowHandles()) {
				driver.switchTo().window(handler);
				break;
			}




			//					
			//					///16.click on a row:The Tegrity Player page is opened and the recording start playing from the chapter start time.
			//			player_page.veirfySearchRecordingClickedAndGetsNewTimeLocation(3);
			//			



			///22.verify return to recordings page
			player_page.returnToRecordingPageByNameAsUserOrGuest(course_name,record);
			//23.navigate back to player recording
			driver.navigate().back();
			Thread.sleep(4000);
			player_page.verifyTimeBufferStatusForXSec(2);// check source display
			//24.click on "Courses" and verify course page

			player_page.returnToCoursesPage(course);
			////25.navigate back to player then to recordings page
			driver.navigate().back();
			player_page.waitForVisibility(player_page.breadcrumbs_box_elements_list.get(0));
			Thread.sleep(500);
			player_page.returnToRecordingPageByNameAsUserOrGuest(course_name,record);

		
			//27.Search the "Recording Chapter" from the recording that we mentioned in the preconditions .
			record.verifySearchReturnAnyListAsUserOrGuest(recording_to_search);
			Thread.sleep(2000);
		
			///!!!!!!!!!!!!!!!!29.download recording with player controllers(Not Possible)
			///return to player
			record.breadcrumbs_box_elements_list.get(2).click();
			record.waitForVisibility(record.first_recording);

			Thread.sleep(2000);
			record.verifyFirstExpandableRecording();
			driver.findElement(By.cssSelector(".panel-body>.video-outer.ng-scope>.video-wrap")).click();
			Thread.sleep(15000);


			// to go back to crecording window handler

			for (String handler : driver.getWindowHandles()) {
				driver.switchTo().window(handler);
				break;		
			}


			///30.Validate the search field is display at the top right of the UI page below the top navigation bar.
			player_page.veriySearchBoxLocation();

			///31.Validate the text in the Tegrity Player page: "Search in this recording..."
			player_page.verifySearchBoxHint();
			Thread.sleep(2000);
			//32.Search the "Recording Chapter" from the recording that we mentioned in the preconditions and press ENTER.


			player_page.verifySearchForRecordingExist(recording_to_search);
			player_page = PageFactory.initElements(driver, PlayerPage.class);


			Thread.sleep(2000);
			//33.The tegrity logo is displayed on the bottom footer bar right side.

			player_page.verifyTegrityLogoVisibilityAndLocation();





			///34.Recording timeline, controls and tasks is displayed.
			player_page.verifyPlayersButtonsAndTimeBuffer();



			///35.The next result display below the current result in case there is next result.
			player_page.verifyThatNextResultDisplayBelowCurrentResultInCaseThereIsNextResult(player_page.search_result);

			//36. The Bookmarks and Notes window is displayed.
			player_page.verifybookmarkTitle();
			for (String handler : driver.getWindowHandles()) {
				driver.switchTo().window(handler);
				break;
			}



			//37.The university logo is displayed on the top footer bar left side.
			player_page.verifyUniversityLogoVisibilityAndLocation();

			///38.search results page in the format as follows: "recording name - Search Results".
			driver.switchTo().frame(0);
			player_page = PageFactory.initElements(driver, PlayerPage.class);
			Thread.sleep(2000);
			player_page.verifySearchResultPage(recording_to_search);

			///39.The search results on a recording level is displayed in the table with the columns as follows: "Location", "Time", "Context"
			player_page.waitForVisibility(player_page.columns_title_text.get(0));
			player_page.verifySearchColumns();
			///40.The search result items is displayed as rows in the table.

			player_page.verifySearchResultsTableAreInRows();

			///41.Hover over the chapter icon:The background color change to deep gray.

			player_page.moveToElement(player_page.search_result_location.get(0), driver).perform();
			Thread.sleep(1000);
			player_page.verifyBackgroundColor("#f1f1f1",player_page.search_result.get(0));

			///42.quit
			driver.quit();

		}
}
