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
public class TC7985VerifyPermissionsForRecordingsSharingAsAdmin {

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
		 System.out.println("Starting the test: TC7985VerifyPermissionsForRecordingsSharingAsAdmin at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC7985VerifyPermissionsForRecordingsSharingAsAdmin at " + DateToStr, "Starting the test: TC7985VerifyPermissionsForRecordingsSharingAsAdmin at " + DateToStr, LogAs.PASSED, null);	
	}

	@AfterClass
	public void closeBroswer() {
		this.driver.quit();
	}
		
	// @Parameters({"web","title"}) in the future
	@Test(description="TC7985 Verify permissions for recording's sharing as Admin")
	public void test7985() throws InterruptedException{
		
		//1.Enter the university
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);	

		//2.Login as instructor
		tegrity.loginCourses("User1");
			
		//3.Click on any course where that user is the Instructor in
		course.selectCourseThatStartingWith("Ab");
		String url =  course.getCurrentUrlCoursePage();
		
		//4.sign out
		record.signOut();
				
		//5.Login as ADMIN
		tegrity.loginAdmin("Admin");
		
		//6.Click on "view course list" under "courses" section.
		admin_dash_board_page.clickOnTargetSubmenuCourses("View Course List");	
		
		//7.In "All courses" page, search for Ab course.
		admin_dashboard_view_course_list.waitForThePageToLoad();
		admin_dashboard_view_course_list.moveToCoursesThroughGet(url);
		
		//8.Click on the checkbox of any (published) recording, hover over "Recording Tasks" & click on "Share Recording"
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox2);
		
		//9.Hover over the "Recording Tasks" dropdown menu
		record.moveToElementAndClick(record.recording_tasks_button, driver);
		
		//10.Verify that the "Share Recordings" option is grayed out and unclickable
		record.checkCorrectEnableDisableStatus(record.isShareRecordingDisableOrEnable(), false, "Share Recording");
		
		//11.Uncheck one of the checkboxes from a previous step and leave the other one checked
		record.unClickOneCheckBoxOrVerifyNotSelected(record.checkbox2);
		
		//12.Hover over the "Recording Tasks" dropdown menu
		//13.Click on "Rcording Tasks">"Share Recording" 
		record.clickOnRecordingTaskThenShareRecording();
		
		//14.Uncheck the "Do not allow anonymous users to view the recording" checkbox
		share_recording_window.waitForPageToLoad();
		record.unClickOneCheckBoxOrVerifyNotSelected(share_recording_window.checkbox_anonymous_users);
		
		//15.Verify that the provided URL works by opening it another tab/browser
		String player_url = share_recording_window.getPlayerUrl();
		share_recording_window.clickElementJS(share_recording_window.okButton);
			
		//16.Open a different browser or logout, and go to the link you've just copied
		record.changeUrl(player_url);
		
		//10.The recording page is loaded and the recording is being played.	
		player_page.verifyTimeBufferStatusForXSec(2);
		player_page.exitInnerFrame();
			
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
	}		
}

