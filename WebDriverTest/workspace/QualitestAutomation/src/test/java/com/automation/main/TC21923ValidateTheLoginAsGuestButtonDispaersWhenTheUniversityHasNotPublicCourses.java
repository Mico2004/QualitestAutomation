package com.automation.main;

import java.awt.AWTException;
import java.io.IOException;
import java.net.URL;
import java.security.PublicKey;
import java.security.spec.ECPrivateKeySpec;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.function.IntToDoubleFunction;

import org.junit.experimental.theories.Theories;
import org.omg.CORBA.StringHolder;
import org.omg.Messaging.SyncScopeHelper;
import org.omg.PortableInterceptor.NON_EXISTENT;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Platform;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.w3c.dom.stylesheets.LinkStyle;

import com.sun.jna.win32.W32APITypeMapper;

import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import atu.testng.reports.utils.Utils;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;
import junitx.util.PropertyManager;
import junitx.util.ResourceManager;
import net.sourceforge.htmlunit.corejs.javascript.tools.debugger.treetable.JTreeTable.ListToTreeSelectionModelWrapper;

@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC21923ValidateTheLoginAsGuestButtonDispaersWhenTheUniversityHasNotPublicCourses {

	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}

	public TC21923ValidateTheLoginAsGuestButtonDispaersWhenTheUniversityHasNotPublicCourses() {
		// TODO Auto-generated constructor stub
	}

	public AdminCourseSettingsPage admin_course_settings_page;
	public PlayerPage player_page;
	public ManageAdHocCoursesMembershipWindow mange_ad_hoc_courses_membership_window;
	public ManageAdhocCoursesEnrollmentsPage manage_adhoc_courses_enrollments_page;
	public CourseSettingsPage course_settings_page;
	public TopBarHelper top_bar_helper;
	public LoginHelperPage tegrity;
	public CoursesHelperPage course;
	public RecordingHelperPage record;
	public ConfirmationMenu confirm_menu;
	public MoveWindow move_window;
	public DeleteMenu delete_menu;
	WebDriver driver;
	WebDriverWait wait;
	public static WebDriver thread_driver;
	CopyMenu copy;
	String currentCourse;
	String targetCourse;
	String clickedRecording;
	DesiredCapabilities capability;
	public AdminDashboardPage admin_dashboard_page;
	public AdminDashboardViewCourseList admin_dashboard_view_course_list;
	public ManageAdhocUsersPage mange_adhoc_users_page;
	public CreateNewUserWindow create_new_user_window;

	@BeforeClass
	public void setup() {

		driver = DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());

		// driver.manage().window().maximize();
		ATUReports.setWebDriver(driver);

		tegrity = PageFactory.initElements(driver, LoginHelperPage.class);

		record = PageFactory.initElements(driver, RecordingHelperPage.class);
		copy = PageFactory.initElements(driver, CopyMenu.class);
		
		mange_adhoc_users_page = PageFactory.initElements(driver, ManageAdhocUsersPage.class);
		create_new_user_window = PageFactory.initElements(driver, CreateNewUserWindow.class);

		confirm_menu = PageFactory.initElements(driver, ConfirmationMenu.class);

		move_window = PageFactory.initElements(driver, MoveWindow.class);

		delete_menu = PageFactory.initElements(driver, DeleteMenu.class);

		course_settings_page = PageFactory.initElements(driver, CourseSettingsPage.class);

		admin_dashboard_page = PageFactory.initElements(driver, AdminDashboardPage.class);

		top_bar_helper = PageFactory.initElements(driver, TopBarHelper.class);
		admin_dashboard_view_course_list = PageFactory.initElements(driver, AdminDashboardViewCourseList.class);
		manage_adhoc_courses_enrollments_page = PageFactory.initElements(driver, ManageAdhocCoursesEnrollmentsPage.class);
		mange_ad_hoc_courses_membership_window = PageFactory.initElements(driver, ManageAdHocCoursesMembershipWindow.class);
		player_page = PageFactory.initElements(driver, PlayerPage.class);
		
		admin_course_settings_page = PageFactory.initElements(driver, AdminCourseSettingsPage.class);
		
		wait = new WebDriverWait(driver, 30);
	}

	
	 @AfterClass
	 public void closeBroswer() {
		 this.driver.quit();
	 }

	// description = "get courses list"
	public void initializeCourseObject() throws InterruptedException {

		course = PageFactory.initElements(driver, CoursesHelperPage.class);
		course.courses = course.getStringFromElement(course.course_list);
	}

	@Test
	public void loginCourses() throws Exception
	{
		// 1. Login as ADMIN.
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
		tegrity.loginAdmin("Admin");
		initializeCourseObject();
		
		// 2. On the University Course settings make sure all of the "Make this Course publicy visible" is locked and off.
		Thread.sleep(2000);
		
		admin_dashboard_page.clickOnTargetSubmenuCourses("Manage Course Settings");
		
		Thread.sleep(2000);
		
		admin_course_settings_page.makeSureThatLockMakeThisCoursePublicSelected();
		admin_course_settings_page.makeSureThatOnOffMakeThisCoursePublicUnSelected();
		
		
		admin_course_settings_page.clickOnSaveButton();
		
		// 3. Sign Out.
		top_bar_helper.clickOnSignOut();
		
		// 4. Clear cache.
		// 5. Refresh the browser.
		driver.quit();	
		setup();
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
		
		wait.until(ExpectedConditions.visibilityOf(tegrity.button_login));

		// 6. Validate the login as guest button is not displayed.
		// 7. The login as guest button is not displayed.
		tegrity.verifyThatLoginAsGuestButtonNotDisplayed();
		
		// 8. The "Some courses may allow guest access" text is not displayed.
		tegrity.verifyThatTheTextSomeCoursesMyAllowGuestAccessNotDisplayed();
		
		// 9. Login as Admin.
		tegrity.loginAdmin("Admin");
		
		// 10. On the University Course settings make sure all of the "Make this Course publicy visible" is unlocked and off.
		Thread.sleep(2000);
		
		admin_dashboard_page.clickOnTargetSubmenuCourses("Manage Course Settings");
		
		Thread.sleep(2000);
		
		admin_course_settings_page.makeSureThatLockMakeThisCoursePublicUnSelected();
		admin_course_settings_page.makeSureThatOnOffMakeThisCoursePublicUnSelected();
		
		admin_course_settings_page.clickOnSaveButton();
		
		
}}
