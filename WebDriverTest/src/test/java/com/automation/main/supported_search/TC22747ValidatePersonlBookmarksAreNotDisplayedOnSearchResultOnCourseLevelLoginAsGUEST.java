package com.automation.main.supported_search;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.testng.annotations.AfterClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
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
import com.automation.main.page_helpers.EditRecordingPropertiesWindow;
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
import com.automation.main.page_helpers.TopBarHelper;
import com.automation.main.utilities.DriverSelector;

import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;


@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC22747ValidatePersonlBookmarksAreNotDisplayedOnSearchResultOnCourseLevelLoginAsGUEST {
	// Set Property for ATU Reporter Configuration
		{
			System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

		}

		public EditRecordingPropertiesWindow erp_window;
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
		public TopBarHelper top_bar_helper;
		public SearchPage search_page;
		

		@AfterClass
		public void closeBroswer() {
		
			this.driver.quit();
		}

		
		
		@BeforeClass
		public void setup() {

			driver = DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());
			tegrity = PageFactory.initElements(driver, LoginHelperPage.class);
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
			erp_window = PageFactory.initElements(driver, EditRecordingPropertiesWindow.class);
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
			top_bar_helper = PageFactory.initElements(driver,TopBarHelper.class);
			search_page = PageFactory.initElements(driver, SearchPage.class);
			
			Date curDate = new Date();
			 String DateToStr = DateFormat.getInstance().format(curDate);
			 System.out.println("Starting the test: TC22747ValidatePersonlBookmarksAreNotDisplayedOnSearchResultOnCourseLevelLoginAsGUEST at " + DateToStr);
			 ATUReports.add("Message window.", "Starting the test: TC22747ValidatePersonlBookmarksAreNotDisplayedOnSearchResultOnCourseLevelLoginAsGUEST at " + DateToStr,
					 "Starting the test: TC22747ValidatePersonlBookmarksAreNotDisplayedOnSearchResultOnCourseLevelLoginAsGUEST at " + DateToStr, LogAs.PASSED, null);	
			
		}

		@Test (description = "TC 22747 Validate Personl Bookmarks Are Not Displayed On Search Result On Course Level Login As GUEST")
		public void test22747() throws Exception {
				
			/// pre conditions

			// 1.load page
			tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
			String url_topass = tegrity.pageUrl;
			/// 2.login as user1
			tegrity.loginCourses("User1");
			course.waitForVisibility(course.active_courses_tab_button);

			// 3.click on course
			String course_name = course.selectCourseThatStartingWith("Ab");
			
			// 4.to course settings
			// 5.verify checked visibility of course(make it public) 
			record.waitForVisibility(record.course_tasks_button);
			record.clickOnCourseTaskThenCourseSettings();
			course_settings.makeSureThatMakeCoursePublicIsSelected();
			course_settings.clickOnOkButton();
			Thread.sleep(2000);
			record.signOut();
			
			// 6.login as student
			tegrity.loginCourses("User4");
			course.waitForVisibility(course.first_course_button);

			//7 take course being copied to name and then return
			course.selectCourseByName(course_name);
			record.waitForVisibility(record.first_recording);

			//8.Click on the recording's title.
			record.verifyFirstExpandableRecording();

			///9.Click on the first chapter
			record.clickOnTheFirstCaptherWithOutTheExpand();
			// 10.Select the Recording by clicking on one of the chapters
			player_page.verifyTimeBufferStatusForXSec(2);// check source display

			///11.add bookmark
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyhhmmss");
			String bookmark_to_add=sdf.format(date);
			Thread.sleep(1000);
			player_page.addBookmarkInSpecificTime(bookmark_to_add, "0:00:32");


			for (String handler : driver.getWindowHandles()) {
				driver.switchTo().window(handler);
			}
			/// 12.sign out super user
			record.signOut();
			Thread.sleep(1000);
			tegrity.waitForVisibility(tegrity.passfield);

			// 13.login as guest
			tegrity.loginAsguest();
			course.waitForVisibility(course.sign_out);
			// 14.Select a course
			Thread.sleep(1500);
			course.selectCourseByName(course_name);

			/// 15.Search the "Bookmark" that we mentioned in the preconditions and press ENTER.
			Thread.sleep(1000);
			record.waitForVisibility(record.first_recording);

			record.convertRecordingsListToNames();
			///16.The "Bookmark" is not displayed on the search results 
			String to_search=bookmark_to_add;  ///search bookmark

			// 17. Search some "Recording Chapter" and press ENTER.
			top_bar_helper.searchForTargetText(to_search);		
			Thread.sleep(1000);
			// verify that we got bookmark and one result
			search_page.verifySearchResultIsEmpty();
	
			//post test
			record.signOut();
		
			tegrity.loginCourses("User4");
			course.selectCourseThatStartingWith("Ab");
			//3.Click on the recording's title.
			record.verifyFirstExpandableRecording();

			///4.Click on the first chapter
			record.clickOnTheFirstCaptherWithOutTheExpand();
			// 5.Select the Recording by clicking on one of the chapters
			player_page.verifyTimeBufferStatusForXSec(2);// check source display
			// 6. delete bookmarks
			player_page.deleteAllBookmark();
			
			System.out.println("Done.");
			ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
		}
}
