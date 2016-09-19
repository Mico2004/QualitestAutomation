package com.automation.main.supported_search_sources_admin_anonymous;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.automation.main.page_helpers.AddAdditionalContentFileWindow;
import com.automation.main.page_helpers.AdminDashboardPage;
import com.automation.main.page_helpers.AdminDashboardViewCourseList;
import com.automation.main.page_helpers.AdvancedServiceSettingsPage;
import com.automation.main.page_helpers.ConfirmationMenu;
import com.automation.main.page_helpers.CopyMenu;
import com.automation.main.page_helpers.CourseSettingsPage;
import com.automation.main.page_helpers.CoursesHelperPage;
import com.automation.main.page_helpers.CreateNewCourseWindow;
import com.automation.main.page_helpers.CreateNewUserWindow;
import com.automation.main.page_helpers.DeleteMenu;
import com.automation.main.page_helpers.EditRecordinPropertiesWindow;
import com.automation.main.page_helpers.EmailAndConnectionSettingsPage;
import com.automation.main.page_helpers.EmailInboxPage;
import com.automation.main.page_helpers.EmailLoginPage;
import com.automation.main.page_helpers.EulaPage;
import com.automation.main.page_helpers.GetSupprtWindow;
import com.automation.main.page_helpers.HelpPage;
import com.automation.main.page_helpers.LoginHelperPage;
import com.automation.main.page_helpers.ManageAdHocCoursesMembershipWindow;
import com.automation.main.page_helpers.ManageAdhocCoursesEnrollmentsPage;
import com.automation.main.page_helpers.ManageAdhocUsersPage;
import com.automation.main.page_helpers.MoveWindow;
import com.automation.main.page_helpers.PlayerPage;
import com.automation.main.page_helpers.PublishWindow;
import com.automation.main.page_helpers.RecordingHelperPage;
import com.automation.main.page_helpers.RunDiagnosticsPage;
import com.automation.main.page_helpers.SearchPage;
import com.automation.main.utilities.DriverSelector;

public class TC23077ValidateTheFunctionalityOfResultsNumberInSearchFieldAsGUEST {

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
		public SearchPage search_window;
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
	     	search_window=PageFactory.initElements(driver, SearchPage.class);
		
		
		}

		@Test
		public void test23077() throws Exception {

			////pre conditions

			// 1.load page
			tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
			tegrity.waitForVisibility(tegrity.passfield);

			// 2.login as admin
			tegrity.loginAdmin("Admin");
			admin_dashboard_page.waitForVisibility(admin_dashboard_page.sign_out);

			//3.Validate "Allow students to download recordings" option in "Manage Course Settings" from "Courses" section is enable
			admin_dashboard_page.clickOnTargetSubmenuCourses("Manage Course Settings");
			course_settings.waitForVisibility(course_settings.getOk_button());
			course_settings.CheckAllowStudentDownload();


			// 3.sign out
			admin_dashboard_page.waitForVisibility(admin_dashboard_page.sign_out);
			Thread.sleep(1500);
			admin_dashboard_page.signOut();
           
			///4.login as guest
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
			Thread.sleep(3000);
			
		    ///7.Search some "Recording Chapter" and press ENTER.

			record.verifySearchReturnAnyListAsUserOrGuest(recording_to_search);
	        
	        
			///8.Validate the search field is display at the top right of the UI page below the top navigation bar.
			player_page.veriySearchBoxLocation();
			Thread.sleep(2000);
			
			
			///10.Validate the number of results that displayed in the breadcrumb is indeed the actual number of results you received.
			search_window.verifySearchResultNumberAsWrittenAsUserOrGuest();
		
		    /////11.cant verify download so skipping straight forward to player and serach there
			///Click on the result row.
	         search_window.clickOnChapterIconOfRecordingInTargetIndex(1);
			//12.The Tegrity Player page is opened and the recording start playing from the chapter start time.

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

		
		//13.search:
			Thread.sleep(3000);
			player_page.verifySearchForRecordingExist(recording_to_search);
		
			//14.Validate the number of results that displayed in the breadcrumb is indeed the actual number of results you received.
	        record.verifySearchResultNumberAsWritten();
		///15.quit
	        driver.quit();
		
		}
	
}