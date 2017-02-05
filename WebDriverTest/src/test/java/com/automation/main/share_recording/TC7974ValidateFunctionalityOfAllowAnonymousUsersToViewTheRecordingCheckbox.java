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
public class TC7974ValidateFunctionalityOfAllowAnonymousUsersToViewTheRecordingCheckbox {

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
		 System.out.println("Starting the test: TC7974ValidateFunctionalityOfAllowAnonymousUsersToViewTheRecordingCheckbox at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC7974ValidateFunctionalityOfAllowAnonymousUsersToViewTheRecordingCheckbox at " + DateToStr, "Starting the test: TC7974ValidateFunctionalityOfAllowAnonymousUsersToViewTheRecordingCheckbox at " + DateToStr, LogAs.PASSED, null);	
	}

	@AfterClass
	public void closeBroswer() {
		this.driver.quit();
	}
		
	// @Parameters({"web","title"}) in the future
	@Test (description="TC7974 Validate functionality of allow anonymous users to view the recording checkbox")
	public void test7974() throws InterruptedException{
		
		//1.Enter the university
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);	

		//2.Login as Instructor
		tegrity.loginCourses("User1");
		
		//3.Open some course.
		course.selectCourseThatStartingWith("Ab");
			
		//4.Choose one recording from the course recording list.
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
		
		//5.Click on "Rcording Tasks">"Share Recording" 
		record.clickOnRecordingTaskThenShareRecording();
		
		//6.If the "Do not allow anonymous users to view the recording" checkbox is not checked - check it
		share_recording_window.waitForPageToLoad();
		record.SelectOneCheckBoxOrVerifyAlreadySelected(share_recording_window.checkbox_anonymous_users);
		
		//7.The URL does not have a *playbackToken* component
		share_recording_window.verifyIfThePlaybackTokenIsAppearedInTheLink(false);
			
		//8.Verify that when unchecking the "Do not allow anonymous users to view the recording" checkbox, the link now contains a *playbackToken* component
		record.unClickOneCheckBoxOrVerifyNotSelected(share_recording_window.checkbox_anonymous_users);
		share_recording_window.verifyIfThePlaybackTokenIsAppearedInTheLink(true);
			
		//9.Copy the URL address and click the "OK" button. 
		String url_for_playing = share_recording_window.getPlayerUrl();
		share_recording_window.clickElementJS(share_recording_window.okButton);
		
		//9.sign out
		record.signOut();
		
		//10.Open a new browser window and paste the link. 
		closeBroswer();
		setup();
		record.changeUrl(url_for_playing);
			
		//11.The recording page is loaded and the recording is being played. no credentials are required
		player_page.verifyTimeBufferStatusForXSec(2);
		player_page.exitInnerFrame();
				
		//12.The "Username", "my account", "report (if instructor or admin), and "sign out" options are not displayed
		player_page.verifyWebElementNotDisplayed(record.user_name,"user name");
		player_page.verifyWebElementNotDisplayed(record.my_account,"my account");
		player_page.verifyWebElementNotDisplayed(record.reports,"report");
		player_page.verifyWebElementNotDisplayed(record.sign_out,"sign out");
		
		//14.The breadcrumbs are not displayed both specific course and all courses
		player_page.verifyBreadcrumbsAreNotDisplayed();

		//15.The searchbox is displayed
		player_page.verifyWebElementDisplayed(player_page.search_box,"searchbox");
	
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
	}		
}

