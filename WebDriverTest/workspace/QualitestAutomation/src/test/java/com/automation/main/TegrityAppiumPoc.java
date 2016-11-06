package com.automation.main;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;import com.automation.main.page_helpers.Page;import com.automation.main.page_helpers.Page;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.touch.TouchActions;
//import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import com.gargoylesoftware.htmlunit.javascript.host.event.InputEvent;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import io.appium.java_client.MobileElement;
import io.appium.java_client.SwipeElementDirection;

public class TegrityAppiumPoc {

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
	DesiredCapabilities capabilities;

	@BeforeClass
	public void setUp() throws MalformedURLException {
		// Set up desired capabilities and pass the Android app-activity and
		// app-package to Appium
		capabilities = new DesiredCapabilities();
		capabilities.setCapability("BROWSER_NAME", "Android");
		capabilities.setCapability("VERSION", "5.1");
		capabilities.setCapability("deviceName", "nexus5_1");
		capabilities.setCapability("platformName", "Android");
		/// capabilities.setCapability("app",
		/// "/data/app/com.tegrity.gui-1/base.apk");
		capabilities.setCapability("resetKeyboard", true);
		capabilities.setCapability("unicodeKeyboard", true);
		capabilities.setCapability("appPackage", "com.tegrity.gui");
		// This package name of your app (you can get it from apk info app)
		capabilities.setCapability("appActivity", "com.tegrity.gui.SplashActivit"); // This
																					// is
																					// Launcher
																					// activity
																					// of
																					// your
																					// app
																					// (you
																					// can
																					// get
																					// it
																					// from
																					// apk
																					// info
																					// app)

	}

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
		publish_window = PageFactory.initElements(driver, PublishWindow.class);
	}

	@Test
	public void test21598() throws Exception {
		/// pre condition

		// 1.load page
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
		/// 2.login as user1
		String mobile = "kosins1";
		tegrity.loginCoursesByParameter(mobile);
		course.waitForVisibility(course.active_courses_tab_button);
		// 3.click on mu account
		driver.findElement(By.id("MyAccountLink")).click();
		// 4.generate code
		Thread.sleep(Page.TIMEOUT_TINY);
		course.waitForVisibility(driver.findElement(By.id("GenerateCodeButton")));
		driver.findElement(By.id("GenerateCodeButton")).click();
		// 5.get string code
		Thread.sleep(Page.TIMEOUT_TINY);
		String code = driver.findElement(By.id("ConnectionCodeContainer")).getAttribute("value").toLowerCase();
		System.out.println(code);
		driver.quit();

		// 6. This is Launcher activity of your app (you can get it from apk
		// info app)
		// Create RemoteWebDriver instance and connect to the Appium server
		// It will launch the Calculator App in Android Device using the
		// configurations specified in Desired Capabilities
		driver = new RemoteWebDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
		wait = new WebDriverWait(driver, 30);
		Thread.sleep(Page.TIMEOUT_TINY);
		Robot robot = new Robot();
		robot.mouseMove(500, 500);
		Thread.sleep(Page.TIMEOUT_TINY);
	///	  MobileElement e = (MobileElement) driver.findElement(By.id("decor_content_parent"));
		//  e.swipe(SwipeElementDirection.LEFT, 2000);
	///	driver.findElement(By.id("connect_pager"));
		for (int i = 0; i < 10; i++) {
		
			try {
				robot.keyPress(KeyEvent.VK_RIGHT);
				Thread.sleep(Page.TIMEOUT_TINY);
				robot.keyRelease(KeyEvent.VK_RIGHT);
				Thread.sleep(Page.TIMEOUT_TINY);// solution
				if (driver.findElement(By.id("connect_edit_text")).isDisplayed())// check if visible
				{
				
					System.out.println("Clicked on right");
					Assert.assertTrue(true);
					break;
				}
			} catch (Exception e) {
			}
		
			Thread.sleep(Page.TIMEOUT_TINY);
		}
	
		Thread.sleep(Page.TIMEOUT_TINY);
		/// 7.click on edit text and enter code to login
		driver.findElement(By.id("connect_edit_text")).sendKeys(code);
		Thread.sleep(Page.TIMEOUT_TINY);
		/// driver.findElement(By.id("connect_edit_text")).sendKeys(Keys.ENTER);
		robot.keyPress(KeyEvent.VK_ENTER);
		Thread.sleep(Page.TIMEOUT_TINY);
		robot.keyRelease(KeyEvent.VK_ENTER);
		Thread.sleep(Page.TIMEOUT_TINY);
		// 8.verify "loading" appears and later dissappears
		WebElement message = driver.findElement(By.id("message"));
		wait.until(ExpectedConditions.visibilityOf(message));
		if (message.getText().contains("Loading. Please wait…")) {
			System.out.println("message is visible");
			Assert.assertTrue(true);
		} else {
			System.out.println("message is not visible");
			Assert.assertTrue(false);
		}

		/// 9.click on viewed tab
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("message")));
		WebElement viewed_tab = driver.findElement(By.name("VIEWED"));
		wait.until(ExpectedConditions.visibilityOf(viewed_tab));
		viewed_tab.click();
		System.out.println("clicked on viewed tab");
		WebElement first_video = driver.findElement(By.id("SessionUnitImageView1"));
		wait.until(ExpectedConditions.visibilityOf(first_video));
		if(viewed_tab.isSelected())
		{
			System.out.println("recordings are visible");
			Assert.assertTrue(true);
		} else {
			System.out.println("recordings are not visible");
			Assert.assertTrue(false);
		}
///10.list all recordings
		List <WebElement> list=driver.findElements(By.id("SessionUnit1"));
		for(WebElement e:list)
		{
			System.out.println(1);

		}
		
		// WebElement plus=driver.findElement(By.name("+"));
		// plus.click();
		// WebElement four=driver.findElement(By.name("4"));
		// four.click();
		// WebElement equalTo=driver.findElement(By.name("="));
		// equalTo.click();
		// //locate the edit box of the calculator by using By.tagName()
		// WebElement results=driver.findElement(By.tagName("EditText"));
		// //Check the calculated value on the edit box
		// assert results.getText().equals("6"):"Actual value is :
		// "+results.getText()+" did not match with expected value: 6";

	}

	@AfterClass
	public void teardown() {
		// close the app
	//	driver.quit();
	}
}
