package com.automation.main.share_recording;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
import com.automation.main.page_helpers.CourseSettingsPage;
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
public class TC7972ValidateUIOfDoNotAllowAnonymousUsersToViewTheRecordingCheckbox {

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
	public CourseSettingsPage course_settings_page;
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
			course_settings_page = PageFactory.initElements(driver, CourseSettingsPage.class);
			confirm_menu = PageFactory.initElements(driver, ConfirmationMenu.class);
			tegrity = PageFactory.initElements(driver, LoginHelperPage.class);
			tag_window= PageFactory.initElements(driver, TagMenu.class);
			record = PageFactory.initElements(driver, RecordingHelperPage.class);
			copy = PageFactory.initElements(driver, CopyMenu.class);
			delete_menu = PageFactory.initElements(driver, DeleteMenu.class);
			
		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC7972ValidateUIOfDoNotAllowAnonymousUsersToViewTheRecordingCheckbox at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC7972ValidateUIOfDoNotAllowAnonymousUsersToViewTheRecordingCheckbox at " + DateToStr, "Starting the test: TC7972ValidateUIOfDoNotAllowAnonymousUsersToViewTheRecordingCheckbox at " + DateToStr, LogAs.PASSED, null);	
	}

	@AfterClass
	public void closeBroswer() {
		this.driver.quit();
	}
		
	// @Parameters({"web","title"}) in the future
	@Test (description="TC7972 Validate UI of 'Do not allow anonymous users to view the recording' checkbox")
	public void test7972() throws InterruptedException{
		
		//1.Enter the university
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);	
		
		//*Precondition:* Login as admin-(course setting) Verify "Require authentication by default for direct recording links" checkbox in course settings is checked.
		//2.Login as admin.
		tegrity.loginAdmin("Admin");
			
		//3.click on the mange course settings
		admin_dash_board_page.clickOnTargetSubmenuCourses("Manage Course Settings");	

		//4.Verify "Require authentication by default for direct recording links" checkbox in course settings is checked.
		admin_course_settings_page.waitForThePageToLoad();
		admin_course_settings_page.makeSureThatOnOffRquireAuthenticatiouNnlock();
		admin_course_settings_page.clickOnSaveButton();
		
		//5.sign out
		admin_course_settings_page.signOut();
		
		//6.Login as Instructor
		tegrity.loginCourses("User1");
		
		//7.Open some course.
		course.selectCourseThatStartingWith("Ab");
		
		//8.Open "Course Tasks"> "Course Settings".
		record.clickOnCourseTaskThenCourseSettings();
		
		//9.Uncheck the "Require authentication by default for direct recording links" checkbox.
		course_settings_page.forceWebElementToBeUnselected(course_settings_page.checkbox_require_authentication, "Require authentication by default for direct recording links");
		course_settings_page.clickOnOkButton();
		//*End of precondition*
		
		//10.Choose one recording from the course recording list.
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
		
		//11.Click on "Rcording Tasks">"Share Recording" 
		record.clickOnRecordingTaskThenShareRecording();
		
		//12.Verify the "Embed" textbox is displayed. 
		share_recording_window.waitForPageToLoad();
		share_recording_window.verifyWebElementDisplayed(share_recording_window.Embed_label_frame, "Embed");
		
		//13.The "Embed" textbox is displayed with the caption "Embed" on the left of the text box.
		share_recording_window.isFirstWebElementToTheRightSecondWebElement(share_recording_window.Embed_label_frame,share_recording_window.iframe_link);
		
		//14.There's an iframe tag inside the text box
		share_recording_window. verifyThatTheTextOfWebElemenetContainsTargetString(share_recording_window.iframe_link, "<iframe");
		share_recording_window. verifyThatTheTextOfWebElemenetContainsTargetString(share_recording_window.iframe_link, "</iframe>");
		
		//15.The iframe's width value is 420																			
		share_recording_window. verifyThatTheTextOfWebElemenetContainsTargetString(share_recording_window.iframe_link, "width=\"420\"");
		
		//16.The iframe's height value is 340
		share_recording_window. verifyThatTheTextOfWebElemenetContainsTargetString(share_recording_window.iframe_link, "height=\"340\"");
		
		//17.verify that the iframe link contains src="//<UniversityUrl>/embeddedPlayer.htm?recordingId=(anything)&playbackToken=(anything)  ** frameborder=0 ** allowfullscreen# 
		share_recording_window.checkThatTheIframeIsValid(tegrity);
		
		//18.Verify the "Embed a simplified player" option is displayed
		share_recording_window.verifyWebElementDisplayed(share_recording_window.Embed_label, "Embed a simplified player");
		
		//19.The "Embed a simplified player" option is displayed below the Embed text box
		share_recording_window.isFirstWebElementBelowSecondWebElement(share_recording_window.Embed_label_frame,share_recording_window.Embed_label);
		
		//20.The "Embed a simplified" checkbox is off
		share_recording_window.verifyWebElementNotSelected(share_recording_window.checkbox_embed_player);
		
		//21.Verify that "Do not allow anonymous users to view the recording" checkbox is not checked. 
		share_recording_window.verifyWebElementNotSelected(share_recording_window.checkbox_anonymous_users);
		
		//22. The option is displayed below the "Embed a simplified player" option
		share_recording_window.isFirstWebElementBelowSecondWebElement(share_recording_window.iframe_link,share_recording_window.anonymous_users_label);
		
		//23.The option's checkbox is align horizontally with the "Embed a simplified player" checkbox
		share_recording_window.isFirstWebElementEqualsHorizontallyOrVerticalSecondWebElement(share_recording_window.checkbox_anonymous_users, share_recording_window.checkbox_embed_player);
		
		//24.Verify "share this recording with" section is displayed
		share_recording_window.verifyWebElementDisplayed(share_recording_window.share_title, "share this recording with");
			
		//25.Hover over facebook logo and "Facebook" hint is displayed
		share_recording_window.moveToElementAndPerform(share_recording_window.facebook_button, driver);
		//record.mouseHoverJScript(share_recording_window.facebook_button);
		share_recording_window.verifyThatWeHaveHintToWebElement(share_recording_window.facebook_button,"Facebook");
		
		//26.Hover over tweet logo and "tweet" hint is displayed
		share_recording_window.moveToElementAndPerform(share_recording_window.tweet_button, driver);
		share_recording_window.verifyThatWeHaveHintToWebElement(share_recording_window.tweet_button,"Tweet");
		
		//27.Hover over google+ logo and "google+" hint is displayed
		share_recording_window.moveToElementAndPerform(share_recording_window.google_plus_button, driver);
		share_recording_window.verifyThatWeHaveHintToWebElement(share_recording_window.google_plus_button,"Google+");
		
		//28.Hover over tumblr logo and "tumblr" hint is displayed
		share_recording_window.moveToElementAndPerform(share_recording_window.tumblr_button, driver);
		share_recording_window.verifyThatWeHaveHintToWebElement(share_recording_window.tumblr_button,"Tumblr");
		
		//29.Hover over the OK button and the "OK" hint is displayed
		share_recording_window.moveToElementAndPerform(share_recording_window.okButton, driver);
		share_recording_window.verifyThatWeHaveHintToWebElement(share_recording_window.okButton,"OK");
		
		//30.Verify the "OK" button is displayed the right buttom of the window. 
		share_recording_window.isFirstWebElementBelowSecondWebElement(share_recording_window.facebook_button , share_recording_window.okButton);
		share_recording_window.isFirstWebElementToTheRightSecondWebElement(share_recording_window.facebook_button, share_recording_window.okButton);
		
		//31.Check the "Do not allow anonymous users to view the recording" option
		record.SelectOneCheckBoxOrVerifyAlreadySelected(share_recording_window.checkbox_anonymous_users);
		
		//32.The "Embed" section (textbox, captions and checkbox) disappeared.
		share_recording_window.verifyWebElementNotDisplayed(share_recording_window.Embed_label, "Embed a simplified player");
		
		//33.The URL's playbackToken parameter was excluded from the URL
		share_recording_window.veirfyThatTheTextOfWebElementNotContainsTargetString(share_recording_window.url_link, "playbackToken");
		
		//34.All other dialog entities remained as they are: ** Title - Share Recording** informative text - Get a direct link to this recording to postin emails, web pages, etc.
		share_recording_window.verifyThatTheTextOfWebElemenetIsAsExpected(share_recording_window.share_recording_title, "Share Recording");
		share_recording_window.verifyInfomativeText();
		
		//35.Share section - "Share this recording with" caption and related logos	
		share_recording_window.verifyThatTheTextOfWebElemenetIsAsExpected(share_recording_window.share_title, "Share this recording with");
		share_recording_window.verifyWebElementDisplayed(share_recording_window.facebook_button, "Facebook");
		share_recording_window.verifyWebElementDisplayed(share_recording_window.tweet_button,"Tweet");
		share_recording_window.verifyWebElementDisplayed(share_recording_window.google_plus_button,"Google+");
		share_recording_window.verifyWebElementDisplayed(share_recording_window.tumblr_button,"Tumblr");
		
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
	}		
}

