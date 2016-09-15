package com.automation.main.login_as_guest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.text.DateFormat;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.automation.main.page_helpers.AddAdditionalContentFileWindow;
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
import com.automation.main.page_helpers.RecordingHelperPage;
import com.automation.main.page_helpers.RunDiagnosticsPage;
import com.automation.main.utilities.DriverSelector;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;

public class TC21597ValidateMenuBarsFunctionlityAsGuest {
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
	String instructor1;
	String instructor2;
	List<String> for_enroll;

	@BeforeClass
	public void setup() {

		driver = DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());
		

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
		
		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC21597ValidateMenuBarsFunctionlityAsGuestat " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC21597ValidateMenuBarsFunctionlityAsGuest at " + DateToStr,
		 "Starting the test: TC21597ValidateMenuBarsFunctionlityAsGuest at " + DateToStr, LogAs.PASSED, null);	
		
	}

	@AfterClass
	public void closeBroswer() {		
		this.driver.quit();
	}
	
	@Test
	public void TC21597() throws Exception {
		//// pre condition

		// 1.load page
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
		String university_url=tegrity.pageUrl.substring(0, tegrity.pageUrl.length()-8);
		/// 2.login as user1
		tegrity.loginCourses("User1");
		course.waitForVisibility(course.active_courses_tab_button);
		// 3.click on course
		course.selectCourseThatStartingWith("Ab");
		// 4.to course settings
		record.waitForVisibility(record.course_tasks_button);
		record.toCourseSettingsPage();
		// 5.to allow students download
		if (!course_settings.allow_download.isSelected()) {
			course_settings.allow_download.click();
		}
		/// 6.Enable 'Require first time users to accept a EULA' in 'Advance
		/// Servie Settings' as admin
		course_settings.signOut();
		tegrity.waitForVisibility(tegrity.usernamefield);
		tegrity.loginAdmin("Admin");
		admin_dashboard_page.waitForVisibility(admin_dashboard_page.sign_out);
		/// 7.verify select 'Require first time users to accept a EULA'
		admin_dashboard_page.clickOnTargetSubmenuAdvancedServices("Advanced Service Settings");
		advanced_services_setting_page.waitForVisibility(advanced_services_setting_page.eula_checkbox);
		String eula = advanced_services_setting_page.clickOnEulaCheckboxAndClickOk(driver, confirm_menu);

		// sign out and start test
		for (String window : driver.getWindowHandles()) {
			driver.switchTo().window(window);
			break;
		}
		Thread.sleep(3000);
		String sender = "no-reply@tegrity.com";
		String comment = "dsfasfagagagsgsgg";
		Thread.sleep(4000);
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyhhmmss");
		String subject = sdf.format(date);
		mange_adhoc_course_enrollments.clickOnAdminDashboard();
		admin_dashboard_page.waitForVisibility(admin_dashboard_page.sign_out);
		admin_dashboard_page.clickOnTargetSubmenuAdvancedServices("Set Email and Connection Settings");
		email_setting.waitForVisibility(email_setting.admin_email);
		email_setting.fillNewEmailSetting("rndsupport@tegrity.com", "qualitestmcgrawhill@armyspy.com",
				"qualitestmcgrawhill@armyspy.com", confirm_menu);
		/// sign out and start test
		for (String window : driver.getWindowHandles()) {
			driver.switchTo().window(window);
			break;
		}
		Thread.sleep(3000);
		admin_dashboard_page.signOut();
		//////////////////////////////////////////////////////////////////// End
		//////////////////////////////////////////////////////////////////// of
		//////////////////////////////////////////////////////////////////// preset////////////////////////////////////////////////////////////
		;
		tegrity.waitForVisibility(tegrity.Login_as_guest_info);
		/// start test

		// 1.login as guest
		tegrity.loginAsguest();
		/// 3.verify redirect to public courses+verify user name is Guest
		course.waitForVisibility(course.public_courses_tab_button);

		/// 4.verify "Disclaimer" leads [Guest] User to the EULA accepting page.
		try {
			course.disclaimer.click();
			Thread.sleep(3000);
			eula_page.verifyElementsOfEula(eula);/// configure manually
			eula_page.waitForVisibility(eula_page.accept_button);
			/// 5.click on accept
			eula_page.clickOnAccept();
		} catch (Exception e) {
			System.out.println("time out or un clickable");
			ATUReports.add("click on accept ", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		course.waitForVisibility(driver.findElement(By.id("PublicCourses")));
		/// 6.check redirected to public courses tab
		//if (driver.findElement(By.xpath("//*[@id=\"main\"]/div[3]/ul/li[3]")).getAttribute("class").equals("active")) {
		if (driver.findElement(By.cssSelector(".active>#PublicCourses")).isDisplayed()) {
			System.out.println("redirected to public courses tab");
			ATUReports.add("redirected to public courses tab", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("not redirected to public courses tab");
			ATUReports.add("not redirected to public courses tab", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}

		/// 7.verify "Disclaimer" leads [Guest] User to the EULA accepting page.
		try {
			course.disclaimer.click();
			Thread.sleep(3000);
			eula_page.verifyElementsOfEula(eula);/// configure manually
			course.waitForVisibility(driver.findElement(By.xpath("//*[@id=\"main\"]/div/div[2]/form/input[1]")));
			/// 8.click on decline
			eula_page.clickOnDecline();
		} catch (Exception e) {
			System.out.println("time out or un clickable");
			ATUReports.add("click on accept ", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}

		// 9.verify return to login page
		tegrity.waitForVisibility(tegrity.passfield);
		if (tegrity.passfield.isDisplayed()) {
			System.out.println("redirected to Login Page");
			ATUReports.add("redirected to Login Page", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("not redirected to Login Page");
			ATUReports.add("not redirected to Login Page", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		/// 10.login as admin
		tegrity.loginAdmin("Admin");
		admin_dashboard_page.waitForVisibility(admin_dashboard_page.sign_out);
		/// 11.verify select 'Require first time users to accept a EULA'
		admin_dashboard_page.clickOnTargetSubmenuAdvancedServices("Advanced Service Settings");
		advanced_services_setting_page.waitForVisibility(advanced_services_setting_page.eula_checkbox);
		/// 12.disable checkbox
		advanced_services_setting_page.disableEulaCheckboxAndClickOk(confirm_menu);

		// 13.sign out
		/// sign out and start test
		admin_dashboard_page.waitForVisibility(admin_dashboard_page.sign_out);
		for (String window : driver.getWindowHandles()) {
			driver.switchTo().window(window);
			break;
		}
		Thread.sleep(3000);
		admin_dashboard_page.signOut();
		// 14.login as guest
		tegrity.waitForVisibility(tegrity.Login_as_guest_button);
		tegrity.loginAsguest();
		course.waitForVisibility(course.sign_out);
		// 15.verify no disclaimer option in menu bar
		course.verifyNoDisclaimerLink();
		// 16.Make sure the displayed texts are 'Guest' , 'help' and 'sign out'
		// from left to right

		if ((course.user_name.getLocation().getX() < course.help.getLocation().getX())
				&& (course.help.getLocation().getX() < course.sign_out.getLocation().getX())) {
			System.out.println("elements location is ok");
			ATUReports.add("elements location is ok", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("elements location is not ok");
			ATUReports.add("elements location is not ok", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		/// 17.Hover over "Help" work item menu
		course.verifyHelpElements();

		//// Click on "Online help" menu item.

		course.toHelpMenu(course.online_help);
		Thread.sleep(3000);

		// 18.verify help page
		String parentWindow = driver.getWindowHandle();
		Set<String> handles = driver.getWindowHandles();
		for (String windowHandle : handles) {
			if (!windowHandle.equals(parentWindow)) {
				driver.switchTo().window(windowHandle);
				help_page = PageFactory.initElements(driver, HelpPage.class);

				help_page.verifyHelpPageUrlGuest(driver);
				driver.close(); // closing child window
				driver.switchTo().window(parentWindow); // cntrl to parent
														// window
			}
		}
		/// 19.Click on "Get started" menu item
		course.toHelpMenu(course.get_started);
		course.verifyGetStartedBlock();
		/// 20.Click on the 'X' on the top right corner of the 'Get started
		/// block
		Thread.sleep(3000);
		course.close_get_started_block.click();
		Thread.sleep(3000);
		course.verifyNoGetStartedBlock();
		/// 21.Click on "Get started" menu item.
		course.toHelpMenu(course.get_started);
		course.verifyGetStartedBlock();
		// 22.Click on "Get support" menu item.
		course.toHelpMenu(course.get_support);
		support_window.waitForVisibility(support_window.support_window_info);
		support_window.verifySupportWindow();
		// 23.fill window with email,user name and comments and then send
		support_window.fillSupportWindowAndSend("rndsupport@tegrity.com", "Guest", subject, comment,
				confirm_menu, driver);
		/// 24.Open your email account
		driver.get("http://www.fakemailgenerator.com/#/armyspy.com/Snothe/");
		email_login.LoginEmailPage("qualitestmcgrawhill");
		/// 25.Make sure you recived the email
        Thread.sleep(3000);
       
		email_inbox.verifyEmailMessage(driver, subject, sender, comment,"Guest (rndsupport@tegrity.com)");
		/// 26.Click on "Run diagnostic" menu item.
		// 1.load page
		Thread.sleep(3000);
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
		Thread.sleep(3000);
		/// 2.LoginHelperPage as guest
	//	tegrity.loginAsguest();
		/// 26.1 Click on "run diagnostics" menu item
		course.waitForVisibility(course.sign_out);
		course.toHelpMenu(course.run_diagnostics);
		Thread.sleep(3000);
		// 27.verify run diagnostics page
		parentWindow = driver.getWindowHandle();
		handles = driver.getWindowHandles();
		for (String windowHandle : handles) {
			if (!windowHandle.equals(parentWindow)) {
				driver.switchTo().window(windowHandle);
				run_diagnostics = PageFactory.initElements(driver, RunDiagnosticsPage.class);

				run_diagnostics.verifyRunDiagnosticsPage(university_url);
				driver.close(); // closing child window
				driver.switchTo().window(parentWindow); // cntrl to parent
														// window
			}
		}
		/// 28.Open some public course.
		course.selectCourseThatStartingWith("abc");
		record.waitForVisibility(record.sign_out);
		// 29.clcik on additional content tab
	    ////record.clickOnAdditionContentTab();

		// 30.verify top bar menu
		/// record.verifyTopBarMenu();
		// 31.click recording and play it
		record.clickOnRecordingsTab();
		record.waitForVisibility(record.getCheckbox());
		Thread.sleep(3000);
		record.verifyFirstExpandableRecording();
		Thread.sleep(2000);
		// 27.player is working
		record.clickOnTheFirstCaptherWithOutTheExpand();
		player_page.verifyTimeBufferStatusForXSec(10);// check source display
	///// to go back to crecording window handler
			for (String handler : driver.getWindowHandles()) {
				driver.switchTo().window(handler);
				// System.out.println("=========================================");
				// System.out.println(driver.getPageSource());
			}
			//21.Click "Courses" link at breadcrumbs
			record.returnToCourseListPage();
		course.waitForVisibility(course.sign_out);
		// 32.verify top bar menu
		/// record.verifyTopBarMenu();

		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
	}
}