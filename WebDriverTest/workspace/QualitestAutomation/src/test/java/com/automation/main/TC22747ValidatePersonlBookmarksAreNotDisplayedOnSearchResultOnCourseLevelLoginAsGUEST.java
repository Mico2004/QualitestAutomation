package com.automation.main;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.testng.annotations.AfterClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
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
import com.automation.main.SearchPage;
import com.automation.main.TopBarHelper;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;

public class TC22747ValidatePersonlBookmarksAreNotDisplayedOnSearchResultOnCourseLevelLoginAsGUEST {
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
		public TopBarHelper top_bar_helper;
		public SearchPage search_page;
		

		@AfterClass
		public void closeBroswer() {
		
			this.driver.quit();
		}

		
		
		@BeforeClass
		public void setup() {

			driver = DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());
			driver.manage().window().maximize();

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
			top_bar_helper = PageFactory.initElements(driver,TopBarHelper.class);
			search_page = PageFactory.initElements(driver, SearchPage.class);
			
			Date curDate = new Date();
			 String DateToStr = DateFormat.getInstance().format(curDate);
			 System.out.println("Starting the test: TCase22747ValidatePersonlBookmarksAreNotDisplayedOnSearchResultOnCourseLevelLoginAsGUEST at " + DateToStr);
			 ATUReports.add("Message window.", "Starting the test: TCase22747ValidatePersonlBookmarksAreNotDisplayedOnSearchResultOnCourseLevelLoginAsGUEST at " + DateToStr, "Starting the test: TCase22747ValidatePersonlBookmarksAreNotDisplayedOnSearchResultOnCourseLevelLoginAsGUEST at " + DateToStr, LogAs.PASSED, null);	
			
		}

		@Test
		public void test22746() throws Exception {

			////pre conditions

			
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
			 record.waitForVisibility(record.course_tasks_button);
			 record.toCourseSettingsPage();
			
			 // 5.verify checked visibility of course(make it public)
			 course_settings.checkCourseVisibility();
			 Thread.sleep(2000);
			 course_settings.signOut();
			
			
			tegrity.waitForVisibility(tegrity.passfield);
			// 6.login as student
			tegrity.loginCourses("User4");
			course.waitForVisibility(course.first_course_button);

			//7 take course being copied to name and then return
			course.selectCourseByName(course_name);
			record.waitForVisibility(record.first_recording);

			//8.Click on the recording's title.
			record.verifyFirstExpandableRecording();

			///9.Click on the first chapter
			driver.findElement(By.cssSelector(".panel-body>.video-outer.ng-scope>.video-wrap")).click();
			Thread.sleep(15000);
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
			top_bar_helper.moveToElement(top_bar_helper.search_box_field, driver).perform();
			Thread.sleep(1000);
			
			if(!(driver instanceof InternetExplorerDriver)) {
				// 8. Set the focus to the field with a mouse pointer.
				top_bar_helper.search_box_field.click();
			}
			
			// 17. Search some "Recording Chapter" and press ENTER.
			top_bar_helper.searchForTargetText(to_search);
			
			List<String> listOfNames = record.getCourseRecordingList();
			
			if(listOfNames.size() == 0) {
				System.out.println("Their aren't records after the search.");
				ATUReports.add("Their aren't records after the search.", LogAs.PASSED, null);
				Assert.assertTrue(true);
			} else {
				System.out.println("Their are records after the search.");
				ATUReports.add("Their are records after the search", LogAs.FAILED, null);
				Assert.assertTrue(false);
				
			}
			
			System.out.println("Done.");
			ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
		}
}
