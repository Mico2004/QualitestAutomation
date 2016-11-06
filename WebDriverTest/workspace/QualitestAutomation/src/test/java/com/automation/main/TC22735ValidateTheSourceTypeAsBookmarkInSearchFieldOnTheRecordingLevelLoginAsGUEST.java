package com.automation.main;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;import com.automation.main.page_helpers.Page;import com.automation.main.page_helpers.Page;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.annotations.AfterClass;
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
import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;

public class TC22735ValidateTheSourceTypeAsBookmarkInSearchFieldOnTheRecordingLevelLoginAsGUEST {
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
		
		Date curDate = new Date();
		String DateToStr = DateFormat.getInstance().format(curDate);
		System.out.println("Starting the test: TC21807ValidatePublicCoursesUILogIn at " + DateToStr);
		ATUReports.add("Message window.", "Starting the test: TC21807ValidatePublicCoursesUILogIn at " + DateToStr,
		"Starting the test: TC21807ValidatePublicCoursesUILogIn at " + DateToStr, LogAs.PASSED, null);	
				
	}

	@Test
	public void test22693() throws Exception {

		////pre conditions

		// 1.load page
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
		tegrity.waitForVisibility(tegrity.passfield);
		// 2.login as super-user
		tegrity.loginAdmin("SuperUser");
		course.waitForVisibility(course.first_course_button);
		// 3.Click on "BankValid" link
		course.selectCourseThatStartingWith("BankValid");
		record.waitForVisibility(record.recordings_tab);
		Thread.sleep(Page.TIMEOUT_TINY);
		// 4.verify all courses page
		record.clickCheckBoxByName("This Recording Has Bookmark");
		// 5.copy recording to course
		record.clickOnRecordingTaskThenCopy();
		copy.selectTargetCourseFromCourseListThatStartWith("Ab");
		copy.clickOnCopyButton();
		confirm_menu.waitForVisibility(confirm_menu.ok_button);
		confirm_menu.clickOnOkButtonAfterConfirmCopyRecording();
		///5.1 wait for finish copying
		record.waitUntilFirstRecordingBeingCopiedFromStatusDissaper();
		/// 6.sign out super user
		record.signOut();
		Thread.sleep(Page.TIMEOUT_TINY);
		
		
		tegrity.waitForVisibility(tegrity.passfield);
	
		// 2.login as guest
	
		tegrity.loginAsguest();
		//3.Select a course
		course.waitForVisibility(course.first_course_button);
		String course_name=	course.selectCourseThatStartingWith("Ab");
		///4.Click on one of the Recording link
		record.waitForVisibility(record.recordings_tab);
		Thread.sleep(Page.TIMEOUT_TINY);
		record.convertRecordingsListToNames();
		record.verifyFirstExpandableRecording();
		driver.findElement(By.cssSelector(".panel-body>.video-outer.ng-scope>.video-wrap")).click();
		Thread.sleep(Page.TIMEOUT_TINY);
		// 8.Select the Recording by clicking on one of the chapters
		player_page.verifyTimeBufferStatusForXSec(2);// check source display

		for (String handler : driver.getWindowHandles()) {
			driver.switchTo().window(handler);
		}
		//9.Search the Recording by entering the "Recording Title" you chose before and press ENTER.
		//  plus +10.The search results statistics in the format as follows: "X results found for: search criterion. (XX sec)"
		String recording_to_search="bookmark";  ///search bookmark
		player_page.verifySearchForRecordingExist(recording_to_search);
		player_page = PageFactory.initElements(driver, PlayerPage.class);

		///10.The next result display below the current result in case there is next result.
		player_page.verifyThatNextResultDisplayBelowCurrentResultInCaseThereIsNextResult(player_page.search_result);

		///11.search results page in the format as follows: "recording name - Search Results".

		player_page.verifySearchResultPage(recording_to_search);
		///12.click on a row:The Tegrity Player page is opened and the recording start playing from the chapter start time.
		player_page.veirfySearchRecordingClickedAndGetsNewTimeLocation(5);
		////
		for (String handler : driver.getWindowHandles()) {
			driver.switchTo().window(handler);
			break;
		}
		System.out.println(player_page.breadcrumbs_box_elements_list.get(1).getText());
		System.out.println(player_page.breadcrumbs_box_elements_list.get(0).getText());
		///13.The breadcrumb structure is displayed as follows: "> Courses > course name".
		player_page.verifyBreadcrumbsForSearcRecording(course_name);

		///14.verify return to recordings page
		player_page.returnToRecordingPageByNameAsUserOrGuest(course_name,record);
		//15.navigate back to player recording
		driver.navigate().back();
		Thread.sleep(Page.TIMEOUT_TINY);
		player_page.verifyTimeBufferStatusForXSec(2);// check source display
		//16.click on "Courses" and verify course page

		player_page.returnToCoursesPage(course);

		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
	}
}
