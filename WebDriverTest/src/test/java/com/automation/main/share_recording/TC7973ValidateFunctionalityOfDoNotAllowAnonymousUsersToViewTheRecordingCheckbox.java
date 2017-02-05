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
public class TC7973ValidateFunctionalityOfDoNotAllowAnonymousUsersToViewTheRecordingCheckbox {

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
		
		for(int type_of_recording = 0;  type_of_recording < 2 ; type_of_recording ++) {
		
			//2.Login as Instructor
			tegrity.loginCourses("User1");
		
			//3.Open some course.
			course.selectCourseThatStartingWith("ad");
		
			if(type_of_recording == 1) {
				record.clickOnStudentRecordingsTab();
			}
			
			//4.Choose one recording from the course recording list.
			record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
		
			//5.Click on "Rcording Tasks">"Share Recording" 
			record.clickOnRecordingTaskThenShareRecording();
		
			//6.Checked the "Do not allow anonymous users to view the recording" checkbox
			share_recording_window.waitForPageToLoad();
			record.SelectOneCheckBoxOrVerifyAlreadySelected(share_recording_window.checkbox_anonymous_users);
		
			//7.Copy the URL address to notepad and click the "OK" button. 
			String url_for_playing = share_recording_window.getPlayerUrl();
			share_recording_window.clickElementJS(share_recording_window.okButton);
			
			//8.Take the URL address, open a new browser window and paste the URL and open it. 
			closeBroswer();
			setup();
			record.changeUrl(url_for_playing);
			//share_recording_window.openNewBrowserWithThatLink(url_for_playing);
		
			//9.Enter as a user which has permissions to the course
			tegrity.loginCourses("User1");
		
			//10.The recording playback page is loaded
			//11.The recording playback begins
			player_page.verifyTimeBufferStatusForXSec(2);
			player_page.exitInnerFrame();
			
			//12.The institution logo is displayed
			share_recording_window.verifyLogoVisibilityAndLocation();
		
			//13.The "Username", "my account", "report (if instructor or admin), "help" and "sign out" options are displayed
			player_page.verifyWebElementDisplayed(record.user_name,"user name");
			player_page.verifyWebElementDisplayed(record.my_account,"my account");
			player_page.verifyWebElementDisplayed(record.reports,"report");
			player_page.verifyWebElementDisplayed(record.help,"help");
			player_page.verifyWebElementDisplayed(record.sign_out,"sign out");
		
			//14.The breadcrumbs are displayed both specific course and all courses
			player_page.verifyWebElementDisplayed(player_page.breadcrumbs_box_elements_list.get(0),"Courses");
			player_page.verifyWebElementDisplayed(player_page.breadcrumbs_box_elements_list.get(1),"Courses Name");
			
			//15.The searchbox is displayed
			player_page.verifyWebElementDisplayed(player_page.search_box,"searchbox");
		
			//16.Sign out
			player_page.exitInnerFrame();
			player_page.signOut();
			
			//17.Paste the URL again and press enter
			record.changeUrl(url_for_playing);
			
			//18.Login with a user which has permissions to the university but not to the shared recording's course
			tegrity.loginCourses("User2");
		
			//19.Sign Out
			record.signOut();
		
			//20.Paste the URL again and press enter
			record.changeUrl(url_for_playing);
		
			//21.Login as admin
			tegrity.loginAdmin("Admin");
		
			//22. The recording playback page is loaded
			//23.The recording playback begins
			player_page.verifyTimeBufferStatusForXSec(2);
			player_page.exitInnerFrame();
					
			//24.The institution logo is displayed
			share_recording_window.verifyLogoVisibilityAndLocation();
		
			//25.The "Username", "my account", "report (if instructor or admin), "help" and "sign out" options are displayed
			player_page.verifyWebElementDisplayed(record.user_name,"user name");
			player_page.verifyWebElementDisplayed(record.reports,"report");
			player_page.verifyWebElementDisplayed(record.help,"help");
			player_page.verifyWebElementDisplayed(record.sign_out,"sign out");
		
			//26.The breadcrumbs are displayed both specific course and all courses
			player_page.verifyWebElementDisplayed(player_page.breadcrumbs_box_elements_list.get(0),"Courses");
			player_page.verifyWebElementDisplayed(player_page.breadcrumbs_box_elements_list.get(1),"Courses Name");
		
			//27.The searchbox is displayed
			player_page.verifyWebElementDisplayed(player_page.search_box,"searchbox");
			
			//28.sign out
			player_page.signOut();
		
		}
		
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
	}		
}

