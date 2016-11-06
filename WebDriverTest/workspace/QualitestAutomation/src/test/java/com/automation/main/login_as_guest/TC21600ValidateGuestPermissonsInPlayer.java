package com.automation.main.login_as_guest;

import java.util.List;
import java.text.DateFormat;
import java.util.Date;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.automation.main.page_helpers.AddAdditionalContentFileWindow;
import com.automation.main.page_helpers.AdminCourseSettingsPage;
import com.automation.main.page_helpers.AdminDashboardPage;
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
import com.automation.main.utilities.DriverSelector;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;

public class TC21600ValidateGuestPermissonsInPlayer {
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
		public AdminCourseSettingsPage admin_course_settings_page;
		String instructor1;
		String instructor2;
		List<String> for_enroll;

		@BeforeClass
		public void setup() {

			driver = DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());
			
			if(!(driver instanceof FirefoxDriver)) {
				
			}

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
			admin_course_settings_page = PageFactory.initElements(driver, AdminCourseSettingsPage.class);
			
			 Date curDate = new Date();
			 String DateToStr = DateFormat.getInstance().format(curDate);
			 System.out.println("Starting the test: TC21600ValidateGuestPermissonsInPlayer at " + DateToStr);
			 ATUReports.add("Message window.", "Starting the test: TC21600ValidateGuestPermissonsInPlayer at " + DateToStr,
			 "Starting the test: TC21600ValidateGuestPermissonsInPlayer at " + DateToStr, LogAs.PASSED, null);	
		}

		@AfterClass
		public void closeBroswer() {		
			this.driver.quit();
		}
		
		@Test (description="TC 21600 Validate Guest Permissons In Player")
		public void test21600() throws Exception {
			/// pre conditions

			// 1.load page
		    tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
		    
		    //make sure that we have public tab
		    tegrity.loginAdmin("Admin");
			Thread.sleep(1000);
			
		    admin_dashboard_page.clickOnTargetSubmenuCourses("Manage Course Settings");		
			Thread.sleep(2000);		
			
			admin_course_settings_page.makeSureThatLockMakeThisCoursePublicUnSelected();
			admin_course_settings_page.clickOnSaveButton();
			
			admin_course_settings_page.waitForVisibility(driver.findElement(By.id("SignOutLink")));
			driver.findElement(By.id("SignOutLink")).click();
			
		    tegrity.waitForVisibility(tegrity.passfield);
			//2.login as guest
			tegrity.loginAsguest();
			//3.select course
			course.waitForVisibility(course.public_courses_tab_button);
			String course_name=course.selectCourseThatStartingWith("Ab");
			//4.Validate the "Start a recording" button not displayed.
			record.waitForVisibility(record.sign_out);
			record.verifyNoStartRecording();
			//5.Click on some recording
			if(!(driver instanceof InternetExplorerDriver)) {
				record.clickOnRecordingsTab();
			}
			 Thread.sleep(2000);	 
			 //6.Click on some recording
			 record.verifyFirstExpandableRecording();
			 record.clickOnTheFirstCaptherWithOutTheExpand();
			 Thread.sleep(15000);
		//	 player_page.verifyTimeBufferStatusForXSec(10);// check source display
			 ///// to go back to crecording window handler
		

			///7.click on Ctrl+Alt+L on the keyboard
			/// player_page.toPlayersDialog();
			 for (String handler : driver.getWindowHandles()) {
				 driver.switchTo().window(handler);
				
				 }
			Thread.sleep(3000);
			player_page.verifyTimeBufferStatusForXSec(5);
			 for (String handler : driver.getWindowHandles()) {
				 driver.switchTo().window(handler);
			
				 }
		///8.In the player logger type insert "alert(g_oPlayerEnv)" and hit enter.
	

				String attr=player_page.iframe.getAttribute("iframe-src");
			    String part=attr.substring(attr.length()-7);
				System.out.println(part);
			    if(part.equals("/Layout"))
				{
					System.out.println("user name is empty in iframe");
					ATUReports.add("user name is empty in iframe", LogAs.PASSED, null);
					Assert.assertTrue(true);
				}
				else
				{
					System.out.println("user name is  not empty in iframe");
					ATUReports.add("user name is  not empty in iframe", LogAs.FAILED, null);
					Assert.assertTrue(false);
				}
		

			    System.out.println("Done.");
			    ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
			    
		}
}
