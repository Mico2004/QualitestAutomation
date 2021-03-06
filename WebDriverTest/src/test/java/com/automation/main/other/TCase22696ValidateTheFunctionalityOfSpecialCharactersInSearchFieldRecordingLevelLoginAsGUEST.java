package com.automation.main.other;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
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
import com.automation.main.utilities.DriverSelector;

public class TCase22696ValidateTheFunctionalityOfSpecialCharactersInSearchFieldRecordingLevelLoginAsGUEST {
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
	}

	@Test
	public void test22662() throws Exception {

	
        String recording_name="\\/[]:;|=,+*?<>";
		// 1.load page
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
		tegrity.waitForVisibility(tegrity.passfield);
	
		// 2.login as user1
		tegrity.loginCourses("User1");
		course.waitForVisibility(course.sign_out);
	
		// 3.Click on course
		Thread.sleep(1500);
		String course_name=course.selectCourseThatStartingWith("Ab");

		/// 4.change Recording name
		record.waitForVisibility(record.getCheckbox());
		Thread.sleep(1000);
		record.getCheckbox().click();
		record.toEditRecordingPropertiesMenu();
		erp_window.waitForVisibility(erp_window.save_button);
		erp_window.changeRecordingName(recording_name, confirm_menu);

		///5.sign out and login as guest
		record.waitForVisibility(record.sign_out);
		record.signOut();
		tegrity.waitForVisibility(tegrity.passfield);
		tegrity.loginAsguest();
		
		///6.select course by name
		
		course.waitForVisibility(course.first_course_button);
		Thread.sleep(2000);
		initializeCourseObject();
		course.selectCourseByName(course_name);
		
		// 7.Click on one of the Recording link
		record.waitForVisibility(record.first_recording);
		record.verifyFirstExpandableRecording();
		record.clickOnTheFirstCaptherWithOutTheExpand();
	
		// 8.Select the Recording by clicking on one of the chapters
		player_page.verifyTimeBufferStatusForXSec(10);// check source display
	
		///// to go back to crecording window handler
		String curr_win=driver.getWindowHandle();	
		for (String handler : driver.getWindowHandles()) {
				driver.switchTo().window(handler);
		break;		
		}
			
		/// 9.Enter invalid "Recording Title" in the search field and press
		/// ENTER
		player_page.verifySearchReturnEmptyList(recording_name);
	    recording_name="abc?<>";
		
	    ///10.return to recording page
	    player_page.waitForVisibility(player_page.breadcrumbs_box_elements_list.get(2));
		player_page.returnToRecordingPageByNameAsUserOrGuest(course_name,record);
	    record.waitForVisibility(record.first_recording);
	    record.signOut();
	    
	    //11.login as user1
	    tegrity.waitForVisibility(tegrity.passfield);
		tegrity.loginCourses("User1");
		course.waitForVisibility(course.sign_out);
	
		// 12.Click on course
		Thread.sleep(1500);
		initializeCourseObject();
		course.selectCourseByName(course_name);

		/// 13.change Recording name
		record.waitForVisibility(record.getCheckbox());
		Thread.sleep(1000);
		record.getCheckbox().click();
		record.toEditRecordingPropertiesMenu();
		erp_window.waitForVisibility(erp_window.save_button);
		erp_window.changeRecordingName(recording_name, confirm_menu);

		///14.sign out and login as guest
		record.waitForVisibility(record.sign_out);
		record.signOut();
		tegrity.waitForVisibility(tegrity.passfield);
		tegrity.loginAsguest();
		
		///6.select course by name
		
		course.waitForVisibility(course.first_course_button);
		Thread.sleep(2000);
		initializeCourseObject();
		course.selectCourseByName(course_name);
	    
	    
	   ///11.change recording name
		record.clickCheckBoxByName("\\/[]:;|=,+*?<>");
		record.toEditRecordingPropertiesMenu();
		erp_window.waitForVisibility(erp_window.save_button);
		erp_window.changeRecordingName(recording_name, confirm_menu);

		// 12.Click on one of the Recording link
		record.verifyFirstExpandableRecording();
		record.clickOnTheFirstCaptherWithOutTheExpand();
		// 13.Select the Recording by clicking on one of the chapters
		player_page.verifyTimeBufferStatusForXSec(10);// check source display
	
		///// to go back to crecording window handler
		
		for (String handler : driver.getWindowHandles()) {
				driver.switchTo().window(handler);
		break;		
		}
			
		///14.Enter invalid "Recording Title" in the search field and press
		/// ENTER
		player_page.verifySearchReturnEmptyList(recording_name);
	    

	
	    ///11.quit
	      driver.quit();
	}

	
	// description = "get courses list"
	public void initializeCourseObject() throws InterruptedException {

		course = PageFactory.initElements(driver, CoursesHelperPage.class);
		course.courses = course.getCoursesListFromElement(course.course_list);
		course.size = course.course_list.size();
	}
}
