package com.automation.main.share_recording;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.browserlaunchers.locators.GoogleChromeLocator;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
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
import com.automation.main.page_helpers.FacebookLoginPage;
import com.automation.main.page_helpers.GooglePlusHelperPage;
import com.automation.main.page_helpers.LoginHelperPage;
import com.automation.main.page_helpers.PlayerPage;
import com.automation.main.page_helpers.RecordingHelperPage;
import com.automation.main.page_helpers.ShareRecordingWindow;
import com.automation.main.page_helpers.TagMenu;
import com.automation.main.page_helpers.TumblrLoginHelper;
import com.automation.main.page_helpers.TwitterLoginPage;
import com.automation.main.utilities.DriverSelector;
import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC7977ValidateInstructorIsAbleToShareTheLinkOnSocialNetworks {

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
	public FacebookLoginPage facebook_login_page;
	public TwitterLoginPage twitter_login_page;
	public TumblrLoginHelper tumbler_login_page;
	public GooglePlusHelperPage google_plus_login_page;
	public CourseSettingsPage course_settings_page;
	public ShareRecordingWindow share_recording_window;
	WebDriver driver;
	WebDriverWait wait;
	public TagMenu tag_window;
	public static WebDriver thread_driver;
	public ConfirmationMenu confirm_menu;
	public CopyMenu copy;
	DesiredCapabilities capability;
	public String facebook_url = "https://www.facebook.com";
	public String twitter_url = "https://www.twitter.com";
	public String google_url = "https://plus.google.com";
	public String tumblr_url = "https://www.tumblr.com/";
	Date date = new Date();
	SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyhhmmss");
	String new_date_for_twitter="New TestKey"+sdf.format(date);


	@BeforeClass
	public void setup() {

			driver = DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());
			player_page = PageFactory.initElements(driver, PlayerPage.class);
			course = PageFactory.initElements(driver, CoursesHelperPage.class);
			admin_dash_board_page = PageFactory.initElements(driver, AdminDashboardPage.class);
			admin_dashboard_view_course_list = PageFactory.initElements(driver, AdminDashboardViewCourseList.class);
			admin_course_settings_page =  PageFactory.initElements(driver, AdminCourseSettingsPage.class);
			share_recording_window =  PageFactory.initElements(driver, ShareRecordingWindow.class);
			facebook_login_page = PageFactory.initElements(driver, FacebookLoginPage.class);
			twitter_login_page = PageFactory.initElements(driver, TwitterLoginPage.class);
			google_plus_login_page = PageFactory.initElements(driver, GooglePlusHelperPage.class);
			tumbler_login_page = PageFactory.initElements(driver, TumblrLoginHelper.class);
			course_settings_page = PageFactory.initElements(driver, CourseSettingsPage.class);
			confirm_menu = PageFactory.initElements(driver, ConfirmationMenu.class);
			tegrity = PageFactory.initElements(driver, LoginHelperPage.class);
			tag_window= PageFactory.initElements(driver, TagMenu.class);
			record = PageFactory.initElements(driver, RecordingHelperPage.class);
			copy = PageFactory.initElements(driver, CopyMenu.class);
			delete_menu = PageFactory.initElements(driver, DeleteMenu.class);
			
		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC7977ValidateInstructorIsAbleToShareTheLinkOnSocialNetworks at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC7977ValidateInstructorIsAbleToShareTheLinkOnSocialNetworks at " + DateToStr, "Starting the test: TC7977ValidateInstructorIsAbleToShareTheLinkOnSocialNetworks at " + DateToStr, LogAs.PASSED, null);	
	}

	@AfterClass
	public void closeBroswer() {
		this.driver.quit();
	}
		
	// @Parameters({"web","title"}) in the future
	@Test(description="TC7977 Validate Instructor is able to share the link on social networks")
	public void test7977() throws InterruptedException{
		
		//1.Enter the university
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);	

		//2.Precondition: Choose a recording as Instructor, click to "Share the Recording". Choose to allowed anonymous access. (The "Do not allow anonymos users to view the recording" is unchecked)
		//Login as instructor
		tegrity.loginCourses("User1");
			
		//3.Click on any course where that user is the Instructor in
		String course_name = course.selectCourseThatStartingWith("Ab");
			
		//4.Click on the checkbox of any (published) recording, hover over "Recording Tasks" & click on "Share Recording"
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
			
		//5.Click on "Rcording Tasks">"Share Recording" 
		record.clickOnRecordingTaskThenShareRecording();
		
		//6.Uncheck the "Do not allow anonymous users to view the recording" checkbox
		share_recording_window.waitForPageToLoad();
		record.unClickOneCheckBoxOrVerifyNotSelected(share_recording_window.checkbox_anonymous_users);
		share_recording_window.clickElementJS(share_recording_window.okButton);
		
		//7.End of Precodntion : sign out
		record.signOut();
		
		//8.Login as instructor
		tegrity.loginCourses("User1");
		
		//9.Click on any course where that user is the Instructor in
		course.selectCourseThatStartingWith("Ab");
			
		//10.Click on the checkbox of any (published) recording, hover over "Recording Tasks" & click on "Share Recording"
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
		String first_recod = record.getFirstRecordingTitle();
		
		//11.Click on "Rcording Tasks">"Share Recording" 
		record.clickOnRecordingTaskThenShareRecording();
		
		//12.Verify the "Share this recording with" section is displayed. 
		share_recording_window.waitForPageToLoad();
		share_recording_window.verifyWebElementDisplayed(share_recording_window.share_title, "Share this recording with");
		
		//13.Click on the "facebook" icon.
		String current_handler = driver.getWindowHandle();
		share_recording_window.clickElementJS(share_recording_window.facebook_button);
			
		//15.Enter the crdentials and click on the login button
		share_recording_window.moveToTheOtherTab(current_handler);		
		if(!(driver instanceof InternetExplorerDriver)) {		
			facebook_login_page.sendKeysToWebElementInput(facebook_login_page.facebook_email, facebook_login_page.mail);
			facebook_login_page.sendKeysToWebElementInput(facebook_login_page.facebook_pass, facebook_login_page.password);
			current_handler = driver.getWindowHandle();
			facebook_login_page.clickElementJS(facebook_login_page.facebook_login_button);
		
			//16.Click on the "Post" button
			facebook_login_page.clickElementJS(facebook_login_page.facebook_post_button);
		} else {
			//16.Click on the "Post" button
			current_handler = driver.getWindowHandle();
			facebook_login_page.clickElement(facebook_login_page.facebook_post_button);
			driver.switchTo().alert().accept();
			driver.switchTo().alert().accept();
		}
			
		Thread.sleep(6000);
		//17.Open your facebook timeline page	
		facebook_login_page.moveToTheOtherTab(current_handler);
		record.changeUrl(facebook_url);
		current_handler = driver.getWindowHandle();
		
		//18.* The post has the following elements: ** Tegrity recordings: '<recording name>' ** <university url> (uppercase)
		String title_of_recording = "Tegrity Recording: '" + first_recod +"'";
		facebook_login_page = PageFactory.initElements(driver, FacebookLoginPage.class);
		facebook_login_page.verifyRecordingAndUniversityNameAreDisplay(title_of_recording , tegrity);
		
		//19.Click on the link
		facebook_login_page.clickElement(facebook_login_page.facebook_cards_links.get(0));
		share_recording_window.moveToTheOtherTabAndCloseTheOldTab(current_handler);
		
		//19.The recording page is loaded and the recording is being played.	
		if(driver instanceof FirefoxDriver)
			tegrity.loginCourses("User1");
		player_page.verifyTimeBufferStatusForXSec(2);
		player_page.exitInnerFrame();
		
		//20.Go back to Tegrity share recording dialog
		player_page.returnToRecordingPageByNameAsUserOrGuest(course_name, record);
		
		//21.Click on the checkbox of any (published) recording, hover over "Recording Tasks" & click on "Share Recording"
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
					
		//22.Click on "Rcording Tasks">"Share Recording" 
		record.clickOnRecordingTaskThenShareRecording();
				
		//23.Click on the facebook logo again
		share_recording_window.waitForPageToLoad();
		current_handler = driver.getWindowHandle();
		share_recording_window.clickElementJS(share_recording_window.facebook_button);
		
		//24.The post page is displayed without needing to enter credentials again
		share_recording_window.moveToTheOtherTab(current_handler);	
		share_recording_window.verifyWebElementNotDisplayed(facebook_login_page.facebook_email, "mail");
		share_recording_window.verifyWebElementNotDisplayed(facebook_login_page.facebook_pass, "password");
		current_handler = driver.getWindowHandle();
		
		//25.Go back to Tegrity share recording dialog
		share_recording_window.moveToTheOtherTabAndCloseTheOldTab(current_handler);
		current_handler = driver.getWindowHandle();
		share_recording_window.clickElementJS(share_recording_window.tweet_button);
		
		//29.Enter your twitter credentials
		share_recording_window.moveToTheOtherTab(current_handler);		
		twitter_login_page.sendKeysToWebElementInput(twitter_login_page.twitter_email, twitter_login_page.mail);
		twitter_login_page.sendKeysToWebElementInput(twitter_login_page.twitter_pass, twitter_login_page.password);
		
		//30.Click 'Login and tweet' button
		
		twitter_login_page.status_of_uploading.sendKeys(new_date_for_twitter);
		twitter_login_page.clickElementWithOutIdJS(twitter_login_page.twitter_login_button);
			
		//31.click on post the tweet to twitter
		String status_for_twitter = twitter_login_page.getStatus();	
		current_handler = driver.getWindowHandle();	
		twitter_login_page.clickElement(twitter_login_page.twitter_post_button);
		
		//32.Open your twitter feed page	
		twitter_login_page.moveToTheOtherTab(current_handler);
		record.changeUrl(twitter_url);
		current_handler = driver.getWindowHandle();
		
		//33.* * The post has the following elements: ** Tegrity recording: '<recording name>' ** <Recording link>
		twitter_login_page.verifyRecordingAndUniversityNameAreDisplay(status_for_twitter ,title_of_recording, tegrity);
		
		//34.Go back to Tegrity share recording dialog
		record.changeUrl(tegrity.pageUrl);
					
		//37.Click on the checkbox of any (published) recording, hover over "Recording Tasks" & click on "Share Recording"
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
			
		//38.Click on "Rcording Tasks">"Share Recording" 
		record.clickOnRecordingTaskThenShareRecording();
		
		//39.Click on the "Google+" icon.
		share_recording_window.waitForPageToLoad();
		share_recording_window.clickElementJS(share_recording_window.google_plus_button);
		
		//40.Enter your twitter credentials
		share_recording_window.moveToTheOtherTab(current_handler);		
		google_plus_login_page.sendKeysToWebElementInput(google_plus_login_page.google_email, google_plus_login_page.mail);
		google_plus_login_page.clickElementJS(google_plus_login_page.google_next);
		google_plus_login_page.sendKeysToWebElementInput(google_plus_login_page.google_pass, google_plus_login_page.password);
		google_plus_login_page.clickElementJS(google_plus_login_page.google_login_button);
		
		//41.Click on Post
		current_handler = driver.getWindowHandle();	
		google_plus_login_page.waitForVisibility(google_plus_login_page.google_post_button.get(1));
		google_plus_login_page.clickElement(google_plus_login_page.google_post_button.get(1));
		
		//42.Open you Google+ feed page	
		Thread.sleep(3000);
		google_plus_login_page.moveToTheOtherTab(current_handler);
		record.changeUrl(google_url);
		current_handler = driver.getWindowHandle();
		
		//43.* * * The post has the following elements:** Tegrity   ** <university url> 
		google_plus_login_page.verifyRecordingAndUniversityNameAreDisplay(tegrity.getPageUrl());
		
		//44.Click on the link
		google_plus_login_page.clickElement(google_plus_login_page.tegrity_url.get(2));
		
		//45 Playback begin in guest mode (Only help link and no breadcrumbs)
		google_plus_login_page.moveToTheOtherTabAndCloseTheOldTab(current_handler);
		tegrity.loginCourses("User1");
		player_page.verifyTimeBufferStatusForXSec(2);
		player_page.exitInnerFrame();
		
		//46.Go back to Tegrity share recording dialog
		player_page.returnToRecordingPageByNameAsUserOrGuest(course_name, record);
		
		//47.Click on the checkbox of any (published) recording, hover over "Recording Tasks" & click on "Share Recording"
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
					
		//48.Click on "Rcording Tasks">"Share Recording" 
		record.clickOnRecordingTaskThenShareRecording();
				
		//49.Click on the "Tumblr" icon
		share_recording_window.waitForPageToLoad();
		current_handler = driver.getWindowHandle();
		share_recording_window.clickElementJS(share_recording_window.tumblr_button);
		
		//50.Enter your Tumblr credentials
		share_recording_window.moveToTheOtherTab(current_handler);		
		tumbler_login_page.sendKeysToWebElementInput(tumbler_login_page.tumblr_email, tumbler_login_page.mail);
		tumbler_login_page.clickElementJS(tumbler_login_page.tumblr_next);
		tumbler_login_page.clickElementJS(tumbler_login_page.tumblr_next2);
		tumbler_login_page.sendKeysToWebElementInput(tumbler_login_page.tumblr_pass, tumbler_login_page.password);
		tumbler_login_page.clickElementJS(tumbler_login_page.tumblr_login_button);
				
		//51.Click on Post
		//google_plus_login_page.clickElementJS(google_plus_login_page.google_post_button);
				
		//52.Open you Google+ feed page
		//current_handler = driver.getWindowHandle();	
		//google_plus_login_page.moveToTheOtherTab(current_handler);
		//record.changeUrl(google_url);
		//current_handler = driver.getWindowHandle();
				
		//53.* * * * The post has the following elements:** <university url>    ** Tegrity recordings: '<recording name>'
		//google_plus_login_page.verifyRecordingAndUniversityNameAreDisplay(tegrity.getPageUrl());
			
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
	}		
}

