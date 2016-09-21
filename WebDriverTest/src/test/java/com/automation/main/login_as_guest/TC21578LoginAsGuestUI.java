package com.automation.main.login_as_guest;

import java.util.List;
import java.util.Set;

import org.eclipse.jetty.io.ClientConnectionFactory.Helper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
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
import com.automation.main.page_helpers.CoursesHelperPage;
import com.automation.main.page_helpers.CreateNewCourseWindow;
import com.automation.main.page_helpers.CreateNewUserWindow;
import com.automation.main.page_helpers.DeleteMenu;
import com.automation.main.page_helpers.EditRecordinPropertiesWindow;
import com.automation.main.page_helpers.HelpPage;
import com.automation.main.page_helpers.LoginHelperPage;
import com.automation.main.page_helpers.ManageAdHocCoursesMembershipWindow;
import com.automation.main.page_helpers.ManageAdhocCoursesEnrollmentsPage;
import com.automation.main.page_helpers.ManageAdhocUsersPage;
import com.automation.main.page_helpers.MoveWindow;
import com.automation.main.page_helpers.RecordingHelperPage;
import com.automation.main.utilities.DriverSelector;

import java.text.DateFormat;
import java.util.Date;
import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;

public class TC21578LoginAsGuestUI {
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
	String instructor1;
	String instructor2;
	List<String> for_enroll;
	public AdminCourseSettingsPage admin_course_settings_page;

	@BeforeClass
	public void setup() {

		driver = DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());
		

		tegrity = PageFactory.initElements(driver, LoginHelperPage.class);

		wait = new WebDriverWait(driver, 30);
		add_additional_content_window = PageFactory.initElements(driver, AddAdditionalContentFileWindow.class);
		record = PageFactory.initElements(driver, RecordingHelperPage.class);
		copy = PageFactory.initElements(driver, CopyMenu.class);
		delete_menu = PageFactory.initElements(driver, DeleteMenu.class);
		course = PageFactory.initElements(driver, CoursesHelperPage.class);
		confirm_menu = PageFactory.initElements(driver, ConfirmationMenu.class);

		wait = new WebDriverWait(driver, 30);
		move_window = PageFactory.initElements(driver, MoveWindow.class);
		erp_window = PageFactory.initElements(driver, EditRecordinPropertiesWindow.class);
		admin_dashboard_page = PageFactory.initElements(driver, AdminDashboardPage.class);
		advanced_services_setting_page = PageFactory.initElements(driver, AdvancedServiceSettingsPage.class);
		mange_adhoc_course_enrollments = PageFactory.initElements(driver, ManageAdhocCoursesEnrollmentsPage.class);

		create_new_course_window = PageFactory.initElements(driver, CreateNewCourseWindow.class);

		mange_adhoc_users_page = PageFactory.initElements(driver, ManageAdhocUsersPage.class);

		create_new_user_window = PageFactory.initElements(driver, CreateNewUserWindow.class);

		mangage_adhoc_courses_membership_window = PageFactory.initElements(driver,
				ManageAdHocCoursesMembershipWindow.class);
		help_page = PageFactory.initElements(driver, HelpPage.class);
		admin_course_settings_page = PageFactory.initElements(driver, AdminCourseSettingsPage.class);
		 
		Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC21578LoginAsGuestUI at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC21578LoginAsGuestUI at " + DateToStr,
		 "Starting the test: TC21578LoginAsGuestUI at " + DateToStr, LogAs.PASSED, null);	
	}

	@AfterClass
	public void closeBroswer() {		
		this.driver.quit();
	}

	@Test
	public void test21578() throws Exception {
		//// pre condition

		// 1.load page
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
		
		/// 2.login as admin
		tegrity.loginAdmin("Admin");
		Thread.sleep(2000);
		
		//pretest enable public visibility  
		admin_dashboard_page.clickOnTargetSubmenuCourses("Manage Course Settings");		
		Thread.sleep(2000);		
		
		admin_course_settings_page.makeSureThatLockMakeThisCoursePublicUnSelected();
		admin_course_settings_page.clickOnSaveButton();
		Thread.sleep(2000);
		
		/// 3.verify select 'Require first time users to accept a EULA'
		admin_dashboard_page.clickOnTargetSubmenuAdvancedServices("Advanced Service Settings");
		advanced_services_setting_page.waitForVisibility(advanced_services_setting_page.eula_checkbox);
		advanced_services_setting_page.clickOnEulaCheckboxAndClickOk(driver, confirm_menu);
		
		// sign out and start test
		for (String window : driver.getWindowHandles()) {
			driver.switchTo().window(window);
			break;
		}
		Thread.sleep(3000);
		mange_adhoc_course_enrollments.clickOnAdminDashboard();
		admin_dashboard_page.waitForVisibility(admin_dashboard_page.sign_out);
		admin_dashboard_page.signOut();
		tegrity.waitForVisibility(tegrity.Login_as_guest_info);
		
		/// start test
		/// 1.verify The "Some courses may allow guest access" static text is
		/// displayed below the "Login" button.
		tegrity.verifyGuestInfo();
		tegrity.verifyGuestButton();
		
		// 2.login as guest
		tegrity.loginAsguest();
		
		/// 3.verify redirect to public courses+verify user name is Guest
		course.waitForVisibility(course.public_courses_tab_button);
		course.verifyUserGuest();

		// 4.check if redirected to additional content tab
		if (driver.findElement(By.cssSelector(".active>#PublicCourses")).isDisplayed()) {
			System.out.println("redirected to public courses tab");
			ATUReports.add("redirected to public courses tab", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("not redirected to public courses tab");
			ATUReports.add("not redirected to public courses tab", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}

		// 5. Validate the "Get Started" box is displayed to the [Guest] User
		course.verifyGetStartedBox();
		
		// 6. Validate the top bar menu "Guest | Disclaimer | help | sign out"
		course.verifyTopBarMenu();
		
		/// 7. verify "Disclaimer" leads [Guest] User to the EULA accepting page.
		try {
			course.disclaimer.click();
			Thread.sleep(3000);
			course.waitForVisibility(driver.findElement(By.xpath("//*[@id=\"main\"]/div/div[2]/form/input[1]")));
			if (driver.findElement(By.xpath("//*[@id=\"main\"]/div/div[2]/form/input[1]")).isDisplayed()) {
				driver.findElement(By.xpath("//*[@id=\"main\"]/div/div[2]/form/input[1]")).click();
				System.out.println("click on accept");
				ATUReports.add("click on accept", LogAs.PASSED, null);
				Assert.assertTrue(true);
			}
		} catch (Exception e) {
			System.out.println("time out or un clickable");
			ATUReports.add("click on accept ", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		
		// 7.1. verify return to course page
		course.waitForVisibility(course.help);
		
		// 8.3. "Help" leads to Tergity help page
		// Store the current window handle
		course.help.click();
		Thread.sleep(3000);


		// 9. verify help page
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

		/// 10. verify no reports link (my account
		course.verifyNoReportsAndMyAccountLink();
		
		/// 11. verify only public tab is available
		course.verifyNoPastCoursesTab();
		course.verifyNoPrivateCoursesTab();
		course.verifyNoActiveCoursesTab();
		
		/// 11. click on public courses
		course.clickOnPublicCoursesTab();
		
		/// 12. verify sign out
		course.signOut();
		
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
		
	}

}
