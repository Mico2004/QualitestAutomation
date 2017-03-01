package com.automation.main.share_recording;

import org.openqa.selenium.Point;
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
public class TC11538ValidateSimplfiedPlayerFunctionality {

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
	String iframe_url ;
	String url = "http://talya.tegrity.com/qap/sergey/embedded.htm";
	
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
		 System.out.println("Starting the test: TC11538ValidateSimplfiedPlayerFunctionality at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC11538ValidateSimplfiedPlayerFunctionality at " + DateToStr, "Starting the test: TC11538ValidateSimplfiedPlayerFunctionality at " + DateToStr, LogAs.PASSED, null);	
	}

	@AfterClass
	public void closeBroswer() {
		this.driver.quit();
	}
		
	// @Parameters({"web","title"}) in the future
	@Test(description="TC11538 Validate simplfied player functionality")
	public void test11538() throws InterruptedException{
		
		//1.Enter the university
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
		
	
		//2.Login as instructor
		tegrity.loginCourses("User1");
					
		//3. click on the course
		course.selectCourseThatStartingWith("Ab");
					
		//5.Choose one recording from the course recording list.
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
	
		//6.Click on "Recording Tasks">"Share Recording"  
		record.clickOnRecordingTaskThenShareRecording();
		
		//7.Uncheck the "Do not allow anonymous users to view the recording" checkbox
		share_recording_window.waitForPageToLoad();
		record.unClickOneCheckBoxOrVerifyNotSelected(share_recording_window.checkbox_anonymous_users);
		
		//8.Check the "Embed a simplified player" checkbox
		record.SelectOneCheckBoxOrVerifyAlreadySelected(share_recording_window.checkbox_embed_player);
		
		//8.Copy to clipboard the Embed textbox
		iframe_url = share_recording_window.getFrameUrl();
		share_recording_window.clickElementJS(share_recording_window.okButton);
		
		//10.Take the URL address, open a new browser window and paste the URL and open it. 
		closeBroswer();
		setup();
		
		for(int httpToggle = 0; httpToggle<2 ; httpToggle ++){
			
		//11.Open the MVSPlayer test page 
		record.changeUrl(url);
					
		if(httpToggle  == 1) {
					share_recording_window.clickElementJS(share_recording_window.http_toggle);
		}
					
		//12 paste the iframe url and clock on the resove iframe
		share_recording_window.sendKeysToWebElementInput(share_recording_window.textbox_mvs_player, iframe_url);
		share_recording_window.clickElementJS(share_recording_window.resolve_iframe);
			
		//13.The first image is displayed with a playing logo centered in the middle of the image
		share_recording_window.moveToPlayFrame();
		Point play_location = share_recording_window.veirfyPlayingLogoInTheMiddleOfTheImage();
				
		//14.The 'Open in Fullscreen' button is displayed below the chapter image and is centered in the middle of it
		share_recording_window.moveToFullScreenFrame();
		share_recording_window.verifyWebElementDisplayed(share_recording_window.full_screen_button, "full screen");
		share_recording_window.verifyTheLocationOfTheFullScreen(play_location);
		
		//15.Click the play button
		share_recording_window.moveToPlayFrame();
		share_recording_window.clickElementJS(share_recording_window.play_iframe);
				
		//16.The recording page is loaded and the recording is being played.	
		player_page.verifyTimeBufferStatusForXSeciFrame(2);
		player_page.exitInnerFrame();
		
		//17.The "Username", "my account", "report (if instructor or admin), and "sign out" options are not displayed 
		//and 'Ask a question' and 'Printer Friendly Page' and 'Show Chapters' and 'Download button' are not displayed
		//The instructor controller and The Bookmarks controller are not displayed
		share_recording_window.moveToPlayFrame();
		player_page.verifyWebElementNotDisplayed(record.user_name,"user name");
		player_page.verifyWebElementNotDisplayed(record.my_account,"my account");
		player_page.verifyWebElementNotDisplayed(record.reports,"report");
		player_page.verifyWebElementNotDisplayed(record.help,"help");
		player_page.verifyWebElementNotDisplayed(record.sign_out,"sign out");
		player_page.verifyWebElementNotDisplayed(player_page.questionButton,"question button");
		player_page.verifyWebElementNotDisplayed(player_page.printButton,"print button");
		player_page.verifyWebElementNotDisplayed(player_page.showChapters,"Show Chapters");
		player_page.verifyWebElementNotDisplayed(player_page.downloadButton,"Download button");
		player_page.verifyWebElementNotDisplayed(player_page.instructorController,"instructor controller");
		player_page.verifyWebElementNotDisplayed(player_page.bookmarkController,"bookmark controller");
		
		//18.The search reuslts are not displayed
		player_page.verifySearchResultsAreNotDisplayed();
		
		//19.The Full screen button is displayed in the right botton corner of the player
		player_page.verifyWebElementDisplayed(player_page.fullScreen,"full screen");
		player_page.isFirstWebElementToTheRightSecondWebElement(player_page.player, player_page.fullScreen);
			
		}
			
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
	}		
}

