package com.automation.main.help_get_support;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import com.automation.main.page_helpers.AdminDashboardPage;
import com.automation.main.page_helpers.AdminDashboardViewCourseList;
import com.automation.main.page_helpers.ConfirmationMenu;
import com.automation.main.page_helpers.CopyMenu;
import com.automation.main.page_helpers.CoursesHelperPage;
import com.automation.main.page_helpers.EditRecordingPropertiesWindow;
import com.automation.main.page_helpers.EmailAndConnectionSettingsPage;
import com.automation.main.page_helpers.GetSupprtWindow;
import com.automation.main.page_helpers.LoginHelperPage;
import com.automation.main.page_helpers.PublishWindow;
import com.automation.main.page_helpers.RecordingHelperPage;
import com.automation.main.page_helpers.TagMenu;
import com.automation.main.utilities.DriverSelector;
import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;


@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC6513TryToSendAMessageWithoutUserEmail {

	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}
	
	public PublishWindow publish_window;
	public AdminDashboardViewCourseList admin_dashboard_view_course_list;
	public LoginHelperPage tegrity;
	public CoursesHelperPage course;
	public RecordingHelperPage record;
	public GetSupprtWindow get_support_window;
	public EditRecordingPropertiesWindow edit_recording_properties_window;
	public EmailAndConnectionSettingsPage email_and_connection_settings_page;
	public ConfirmationMenu confirm_menu;
	public AdminDashboardPage admin_dash_board_page;
	WebDriver driver;
	WebDriverWait wait;
	public TagMenu tag_window;
	public static WebDriver thread_driver;
	CopyMenu copy;
	DesiredCapabilities capability;
	String firstTag;
	

	@BeforeClass
	public void setup() {

			driver = DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());
			admin_dash_board_page = PageFactory.initElements(driver, AdminDashboardPage.class);
			admin_dashboard_view_course_list = PageFactory.initElements(driver, AdminDashboardViewCourseList.class);
			tegrity = PageFactory.initElements(driver, LoginHelperPage.class);
			edit_recording_properties_window = PageFactory.initElements(driver, EditRecordingPropertiesWindow.class);
			email_and_connection_settings_page =  PageFactory.initElements(driver, EmailAndConnectionSettingsPage.class);
			get_support_window = PageFactory.initElements(driver, GetSupprtWindow.class);
			record = PageFactory.initElements(driver, RecordingHelperPage.class);
			confirm_menu = PageFactory.initElements(driver, ConfirmationMenu.class);
			copy = PageFactory.initElements(driver, CopyMenu.class);
			course = PageFactory.initElements(driver, CoursesHelperPage.class);
			
		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC6513TryToSendAMessageWithoutUserEmail at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC6513TryToSendAMessageWithoutUserEmail at " + DateToStr, "Starting the test: TC6513TryToSendAMessageWithoutUserEmail at " + DateToStr, LogAs.PASSED, null);	
	}

	@AfterClass
	public void closeBroswer() {
		this.driver.quit();
	}
		
	
	// @Parameters({"web","title"}) in the future
	@Test (description="TC6513 Try to send a message without User Email")
	public void test6513() throws InterruptedException, ParseException {
		
		//1.log in courses page
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);	
		
		//*+Precondition:+*Helpdesk email must be set up in *Set Email and Connection Settings* on Admin Dashboard
		//2.login as Admin
		tegrity.loginAdmin("Admin");
			
		//3.Enter to Set Email and Connection Settings on Admin Dashboard
		admin_dash_board_page.clickOnTargetSubmenuAdvancedServices("Set Email and Connection Settings");
				
		//4.set mail and clean all other options
		email_and_connection_settings_page.waitForThePageToLoad();
		email_and_connection_settings_page.cleanAllOptionsAndPutMail("qualitestautomation@sharklasers.com");
		
		confirm_menu.clickOnOkButtonAfterConfirmEmailSetting();
				
		//5.endPrecondition : sign out
		email_and_connection_settings_page.signOut();
				
		//6.login as INSTRUCTOR
		tegrity.loginCourses("User1");
	
		//7.Hover over the "Help" link
		course.moveToElementAndPerform(course.help, driver);
			
		//8.Click the "Get Support" link
		course.clickElementJS(course.get_support);
		
		//9.Delete the text from the *From* textfield
		get_support_window.verifySupportWindow();
		get_support_window.clearTheTextInTheTextArea(get_support_window.from_email_field,"From Email");
		
		//10.Click the "Send" button
		get_support_window.clickElement(get_support_window.send_button);
		
		//11."Please set a valid sender Email address" message is displayed
		get_support_window.checkThatErrorMassageDesplayAndTheStringIsRight("Please set a valid sender Email address");
		
		//12.Fill the email field with a string not in the format of  x@x.x
		get_support_window.sendKeysToWebElementInput(get_support_window.from_email_field, "test5613");
		
		//13.Click the "Send" button
		get_support_window.clickElement(get_support_window.send_button);
				
		//14."Please set a valid sender Email address" message is displayed
		get_support_window.checkThatErrorMassageDesplayAndTheStringIsRight("Please set a valid sender Email address");
			
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
	
	}
}