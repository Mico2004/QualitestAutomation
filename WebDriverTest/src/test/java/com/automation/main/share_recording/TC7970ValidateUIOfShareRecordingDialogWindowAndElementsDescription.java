package com.automation.main.share_recording;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import com.automation.main.page_helpers.AdminCourseSettingsPage;
import com.automation.main.page_helpers.AdminDashboardPage;
import com.automation.main.page_helpers.AdminDashboardViewCourseList;
import com.automation.main.page_helpers.ConfirmationMenu;
import com.automation.main.page_helpers.CopyMenu;
import com.automation.main.page_helpers.CoursesHelperPage;
import com.automation.main.page_helpers.DeleteMenu;
import com.automation.main.page_helpers.LoginHelperPage;
import com.automation.main.page_helpers.PlayerPage;
import com.automation.main.page_helpers.RecordingHelperPage;
import com.automation.main.page_helpers.ShareRecordingWindow;
import com.automation.main.page_helpers.TagMenu;
import com.automation.main.utilities.DriverSelector;
import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import java.text.DateFormat;
import java.util.Date;


@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC7970ValidateUIOfShareRecordingDialogWindowAndElementsDescription {

	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}
	
	public AdminDashboardViewCourseList admin_dashboard_view_course_list;
	public LoginHelperPage tegrity;
	public CoursesHelperPage course;
	public RecordingHelperPage record;
	public PlayerPage player_page;
	public AdminDashboardPage admin_dash_board_page;
	public AdminCourseSettingsPage admin_course_settings_page;
	public DeleteMenu delete_menu;
	public ShareRecordingWindow share_recording_window;
	WebDriver driver;
	WebDriverWait wait;
	public TagMenu tag_window;
	public static WebDriver thread_driver;
	public ConfirmationMenu confirm_menu;
	public CopyMenu copy;
	DesiredCapabilities capability;
	String bookmark_to_add;
	String recordName;
	boolean isDeleteDisplay;
	String url;
	
	@BeforeClass
	public void setup() {

			driver = DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());
			player_page = PageFactory.initElements(driver, PlayerPage.class);
			course = PageFactory.initElements(driver, CoursesHelperPage.class);
			admin_dash_board_page = PageFactory.initElements(driver, AdminDashboardPage.class);
			admin_dashboard_view_course_list = PageFactory.initElements(driver, AdminDashboardViewCourseList.class);
			admin_course_settings_page =  PageFactory.initElements(driver, AdminCourseSettingsPage.class);
			share_recording_window =  PageFactory.initElements(driver, ShareRecordingWindow.class);
			confirm_menu = PageFactory.initElements(driver, ConfirmationMenu.class);
			tegrity = PageFactory.initElements(driver, LoginHelperPage.class);
			tag_window= PageFactory.initElements(driver, TagMenu.class);
			record = PageFactory.initElements(driver, RecordingHelperPage.class);
			copy = PageFactory.initElements(driver, CopyMenu.class);
			delete_menu = PageFactory.initElements(driver, DeleteMenu.class);
			
		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC7970ValidateUIOfShareRecordingDialogWindowAndElementsDescription at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC7970ValidateUIOfShareRecordingDialogWindowAndElementsDescription at " + DateToStr, "Starting the test: TC7970ValidateUIOfShareRecordingDialogWindowAndElementsDescription at " + DateToStr, LogAs.PASSED, null);	
	}

	@AfterClass
	public void closeBroswer() {
		this.driver.quit();
	}
		
	// @Parameters({"web","title"}) in the future
	@Test (description="TC7970 Validate UI of Share Recording dialog window and elements  description")
	public void test7970() throws InterruptedException {
		
		//1.Enter the university
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);	
		
		//*Precondition:* Login as admin-(course setting) Verify "Require authentication by default for direct recording links" checkbox in course settings is checked.
		//2.Login as admin.
		tegrity.loginAdmin("Admin");
			
		//3.click on the mange course settings
		admin_dash_board_page.clickOnTargetSubmenuCourses("Manage Course Settings");	

		//4.Verify "Require authentication by default for direct recording links" checkbox in course settings is checked.
		admin_course_settings_page.waitForThePageToLoad();
		admin_course_settings_page.makeSureThatOnOffRquireAuthentication();
		admin_course_settings_page.makeSureThatOnOffRquireAuthenticationlock();
		admin_course_settings_page.clickOnSaveButton();
		
		//5.sign out
		admin_course_settings_page.signOut();
		
		//*End of precondition*
		
		//6.0-Login as Instructor,1-Login as Admin,2-Login as HdAdmin.
		for(int type_of_user = 0 ;type_of_user <3 ; type_of_user++ ){
		
		if(type_of_user == 0) {
			tegrity.loginCourses("User1");
		} else if(type_of_user == 1){
			tegrity.loginAdmin("Admin");
		} else {
			tegrity.loginAdmin("HelpdeskAdmin");
		}
	
		if(type_of_user < 1) {
			//7.Open some course.
			course.selectCourseThatStartingWith("Ab");
			url = course.getCurrentUrlCoursePage();
		} else {
			//7.Click on "view course list" under "courses" section.
			admin_dash_board_page.clickOnTargetSubmenuCourses("View Course List");	
			// In "All courses" page, search for Ab course.
			admin_dashboard_view_course_list.waitForThePageToLoad();
			admin_dashboard_view_course_list.moveToCoursesThroughGet(url);
		}
		
		//8.Choose one recording and checked the recording checkbox.
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
		
		//9.Open "Recording Tasks".
		//10.Click on "Share Recording" option. 
		record.clickOnRecordingTaskThenShareRecording();
		
		//11.Verify the "Share Recording" title line is in plane text and the background color is equal as it was specified in university UI settings
		share_recording_window.waitForPageToLoad();
		share_recording_window.verifyShareRecordingColor(record);
		
		//12.Verify the "Get a direct link to this recording to post in emails, web pages, etc." is in plain text.
		share_recording_window.verifyInfomativeText();
		
		//13.Verify the "URL" is in plain text.
		share_recording_window.verifyThatTheElementIsLabel(share_recording_window.url_label);
		
		//14.Verify the URL contain a link (full URL ) to the page where user will be able to watch the recording.
		share_recording_window.checkThatTheUrlIsValid(tegrity);
		
		//15.Verify that upon clicking anywhere in the textbox field, automatically select the entire text in the textbox.
		share_recording_window.veirfyThatClickingOnTheTextSelectTheEntireTextInTheTextbox(share_recording_window.url_link);
		
		//16.Verify "Do not allow anonymous users to view the recording" checkbox is displayed and checked.
		share_recording_window.verifyWebElementDisplayed(share_recording_window.checkbox_anonymous_users, "Do not allow anonymous users to view the recording");
		record.veirfyThatTheCheckboxIsSelect(share_recording_window.checkbox_anonymous_users);
		
		//17.Verify the Share Buttons container (all the logs are appeared)
		share_recording_window.verifyWebElementDisplayed(share_recording_window.facebook_button, "facebook logo");
		share_recording_window.verifyWebElementDisplayed(share_recording_window.tweet_button, "tweet logo");
		share_recording_window.verifyWebElementDisplayed(share_recording_window.google_plus_button, "google plus logo");
		share_recording_window.verifyWebElementDisplayed(share_recording_window.tumblr_button, "tumblr logo");
		
		//18.The title should be "Share this recording with".
		share_recording_window.verifyThatTheTextOfWebElemenetIsAsExpected(share_recording_window.share_title, "Share this recording with");
		
		//19.Verify "share this recording with" section is displayed
		//# The "share this recording with" caption is displayed below the "Do not allow ano...." option
		//# The section checkbox is align horizontally with the "Embed a simplified player" checkbox
		//# below the caption "Facebook, twitter, google plus and Tumblr" logos are displayed
		share_recording_window.isFirstWebElementBelowSecondWebElement(share_recording_window.anonymous_users_label, share_recording_window.share_title);
		share_recording_window.isFirstWebElementBelowSecondWebElement(share_recording_window.share_title, share_recording_window.facebook_button);
		share_recording_window.isFirstWebElementBelowSecondWebElement(share_recording_window.share_title, share_recording_window.tweet_button);
		share_recording_window.isFirstWebElementBelowSecondWebElement(share_recording_window.share_title, share_recording_window.google_plus_button);
		share_recording_window.isFirstWebElementBelowSecondWebElement(share_recording_window.share_title, share_recording_window.tumblr_button);
		share_recording_window.isFirstWebElementEqualsHorizontallyOrVerticalSecondWebElement(share_recording_window.anonymous_users_label, share_recording_window.share_title);
		
		//20.Hover over facebook logo and "Facebook" hint is displayed
		share_recording_window.moveToElementAndPerform(share_recording_window.facebook_button, driver);
		//record.mouseHoverJScript(share_recording_window.facebook_button);
		share_recording_window.verifyThatWeHaveHintToWebElement(share_recording_window.facebook_button,"Facebook");
		
		//21.Hover over tweet logo and "tweet" hint is displayed
		share_recording_window.moveToElementAndPerform(share_recording_window.tweet_button, driver);
		share_recording_window.verifyThatWeHaveHintToWebElement(share_recording_window.tweet_button,"Tweet");
		
		//22.Hover over google+ logo and "google+" hint is displayed
		share_recording_window.moveToElementAndPerform(share_recording_window.google_plus_button, driver);
		share_recording_window.verifyThatWeHaveHintToWebElement(share_recording_window.google_plus_button,"Google+");
		
		//23.Hover over tumblr logo and "tumblr" hint is displayed
		share_recording_window.moveToElementAndPerform(share_recording_window.tumblr_button, driver);
		share_recording_window.verifyThatWeHaveHintToWebElement(share_recording_window.tumblr_button,"Tumblr");
		
		//24.Hover over the OK button and the "OK" hint is displayed
		share_recording_window.moveToElementAndPerform(share_recording_window.okButton, driver);
		share_recording_window.verifyThatWeHaveHintToWebElement(share_recording_window.okButton,"OK");
		
		//25.Verify the "OK" button is displayed the right buttom of the window. 
		share_recording_window.isFirstWebElementBelowSecondWebElement(share_recording_window.facebook_button , share_recording_window.okButton);
		share_recording_window.isFirstWebElementToTheRightSecondWebElement(share_recording_window.facebook_button, share_recording_window.okButton);
		share_recording_window.clickElementJS(share_recording_window.okButton);
		
		//26.Check several recording's checkboxes
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox2);
		
		//27.Open "Recording Tasks"
		//The Share Recording option is disabled and grayed out
		record.moveToElementAndClick(record.recording_tasks_button, driver);
		record.checkCorrectEnableDisableStatus(record.isShareRecordingDisableOrEnable(), false, "Share Recording");
		
		//28.sign out 
		record.signOut();
		
		}
		
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
	}		
}

