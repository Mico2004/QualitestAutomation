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
public class TC11541ValidateFunctionalityOfPastCoursesRecordingSharing {

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
	String player_url , iframe_url ;
	String url_for_frame = "http://talya.tegrity.com/qap/sergey/embedded.htm";
	
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
		 System.out.println("Starting the test: TC11541ValidateFunctionalityOfPastCoursesRecordingSharing at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC11541ValidateFunctionalityOfPastCoursesRecordingSharing at " + DateToStr, "Starting the test: TC11541ValidateFunctionalityOfPastCoursesRecordingSharing at " + DateToStr, LogAs.PASSED, null);	
	}

	@AfterClass
	public void closeBroswer() {
		this.driver.quit();
	}
		
	// @Parameters({"web","title"}) in the future
	@Test(description="TC11541 Validate functionality of past course's recording sharing")
	public void test11541() throws InterruptedException{
		
		//1.Enter the university
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
		String url =  course.getCurrentUrlCoursePage(); 
 	
		for(int type_of_funntion = 0 ; type_of_funntion < 2 ; type_of_funntion++) {
			
		//2.Login as instructor
		tegrity.loginCourses("User1");
			
		//3.Open on a past course.
		course.clickOnPastCoursesTabButton();
			
		//4. click on the course
		course.selectCourseThatStartingWith("PastCourseA");
					
		//5.Choose one recording from the course recording list.
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
	
		//6.Click on "Recording Tasks">"Share Recording"  
		record.clickOnRecordingTaskThenShareRecording();
		
		//7.Uncheck the "Do not allow anonymous users to view the recording" checkbox
		share_recording_window.waitForPageToLoad();
		record.unClickOneCheckBoxOrVerifyNotSelected(share_recording_window.checkbox_anonymous_users);
		
		//8.Verify that the provided URL works by opening it another tab/browser
		if(type_of_funntion == 0 ){
			player_url = share_recording_window.getPlayerUrl();		
		} else { 
			iframe_url = share_recording_window.getFrameUrl();
		}
		share_recording_window.clickElementJS(share_recording_window.okButton);
		
		//10.Take the URL address, open a new browser window and paste the URL and open it. 
		closeBroswer();
		setup();
		
		if(type_of_funntion == 0){
			record.changeUrl(player_url);					
		}else {
			//11.Click on the "Open in fullscreen" button
			record.changeUrl(url_for_frame);
			
			//11.1 paste the iframe url and clock on the resove iframe
			share_recording_window.sendKeysToWebElementInput(share_recording_window.textbox_mvs_player, iframe_url);
			share_recording_window.clickElementJS(share_recording_window.resolve_iframe);
			
			//11.2 press on the full screen on new tab
			String current_handler = driver.getWindowHandle();
			share_recording_window.moveToFullScreenFrame();
			share_recording_window.clickElementJS(share_recording_window.full_screen_button);
			
			//11.3.The playback page is opened in a new tab
			share_recording_window.moveToTheOtherTabAndCloseTheOldTab(current_handler);			
		}
		
		//12.The recording page is loaded and the recording is being played.
		player_page.verifyTimeBufferStatusForXSec(2);
		player_page.exitInnerFrame();
		
		//13.The "Username", "my account", "report (if instructor or admin), and "sign out" options are not displayed
		player_page.verifyWebElementNotDisplayed(record.user_name,"user name");
		player_page.verifyWebElementNotDisplayed(record.my_account,"my account");
		player_page.verifyWebElementNotDisplayed(record.reports,"report");
		player_page.verifyWebElementNotDisplayed(record.sign_out,"sign out");
		
		//14.The breadcrumbs are not displayed both specific course and all courses
		player_page.verifyBreadcrumbsAreNotDisplayed();

		//15.The searchbox is displayed
		player_page.verifyWebElementDisplayed(player_page.search_box,"searchbox");
	
		//16. return to the login page
		record.changeUrl(url);
		
	}	
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
	}		
}

