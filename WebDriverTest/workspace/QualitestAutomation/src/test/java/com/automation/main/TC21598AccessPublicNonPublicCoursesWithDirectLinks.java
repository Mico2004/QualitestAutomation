package com.automation.main;

import java.util.List;

import org.jboss.netty.channel.ReceiveBufferSizePredictor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TC21598AccessPublicNonPublicCoursesWithDirectLinks {
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
		publish_window=PageFactory.initElements(driver, PublishWindow.class);
	}

	@Test
	public void test21598() throws Exception {
		///pre condition
		

		// 1.load page
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
		/// 2.login as user1
		tegrity.loginCourses("User1");
		course.waitForVisibility(course.active_courses_tab_button);
		// 3.click on course
		String course_name=course.selectCourseThatStartingWith("Ab");
		// 4.to course settings
		record.waitForVisibility(record.course_tasks_button);
		record.toCourseSettingsPage();
		// 5.verify unchecked visibility of course 
		course_settings.uncheckCourseVisibility();
	//6.Go back the to course content page
		record.returnToCourseListPage();
		course.waitForVisibility(course.sign_out);
       //7.Copy the URL
       String current_url=driver.getCurrentUrl(); 	
       //8.sign out
       course.signOut();
       ///9.Past the copied url to the browser and press enter
       driver.get(current_url);
       course.waitForVisibility(course.sign_out);
       //10.Validate the course that we mention in the preconditions is not displayed in the course list.
	   initializeCourseObject();
	   course.verifyCourseNotExist(course_name);
	   ///11.Sign out
	   course.signOut();
	   //12.Login as INSTRUCTOR
	   tegrity.waitForVisibility(tegrity.passfield);
		tegrity.loginCourses("User1");
		course.waitForVisibility(course.active_courses_tab_button);
		// 13.click on course
				String course_name2=course.selectCourseThatStartingWith("Ab");
				// 14.to course settings
				record.waitForVisibility(record.course_tasks_button);
				record.toCourseSettingsPage();
				// 15.verify checked visibility of course 
				course_settings.checkCourseVisibility();
				///16.sign-out
				record.waitForVisibility(record.sign_out);
				record.returnToCourseListPage();
			    course.waitForVisibility(course.sign_out);
				course.signOut();
	   //17.Past the copied url to the browser and press enter
				driver.get(current_url);
				course.waitForVisibility(course.public_courses_tab_button);
				//18.verify course exists and start a recording
				initializeCourseObject();
				course.verifyCourseExist(course_name2);
				course.selectCourseByName(course_name2);
				record.waitForVisibility(record.checkbox);
				record.verifyFirstExpandableRecording();
				Thread.sleep(2000);
				// 19.player is working
				driver.findElement(By.cssSelector(".panel-body>.video-outer.ng-scope>.video-wrap")).click();
				Thread.sleep(15000);
				player_page.verifyTimeBufferStatusForXSec(10);// check source display
                driver.quit();
	}
	// description = "get courses list"
	public void initializeCourseObject() throws InterruptedException {

		course = PageFactory.initElements(driver, CoursesHelperPage.class);
		course.courses = course.getStringFromElement(course.course_list);
	}	
}
