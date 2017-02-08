package com.automation.main.share_recording;

import org.openqa.selenium.By;
import org.openqa.selenium.Point;
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
public class TC7984VerifyThatTheSharedRecordingLinkWorksWithAndWithoutShibbolet {

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
		 System.out.println("Starting the test: TC7984VerifyThatTheSharedRecordingLinkWorksWithAndWithoutShibbolet at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC7984VerifyThatTheSharedRecordingLinkWorksWithAndWithoutShibbolet at " + DateToStr, "Starting the test: TC7984VerifyThatTheSharedRecordingLinkWorksWithAndWithoutShibbolet at " + DateToStr, LogAs.PASSED, null);	
	}

	@AfterClass
	public void closeBroswer() {
		this.driver.quit();
	}
		
	// @Parameters({"web","title"}) in the future
	@Test(description="TC7984 Verify that the shared recording link works (with & without Shibbolet)")
	public void test7984() throws InterruptedException{
		
		//1.Enter the university
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);	

		//2.Login as Instructor
		tegrity.loginCourses("User1");
		
		//3.Click on any course where that user is the Instructor in
		course.selectCourseThatStartingWith("Ab");
			
		//4.Click on the checkbox of any (published) recording, hover over "Recording Tasks" & click on "Share Recording"
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
		
		//5.Click on "Rcording Tasks">"Share Recording" 
		record.clickOnRecordingTaskThenShareRecording();
		
		//6.Verify the 'Do not allow anon...' checkbox is unchecked
		share_recording_window.waitForPageToLoad();
		record.SelectOneCheckBoxOrVerifyAlreadySelected(share_recording_window.checkbox_anonymous_users);
		
		//7.Copy the URL from the "URL" field
		String player_url = share_recording_window.getPlayerUrl();
		share_recording_window.clickElementJS(share_recording_window.okButton);
		record.signOut();
		
		//8.Open a different browser or logout, and go to the link you've just copied
		record.changeUrl(player_url);
		
		//9.Login as any Instructor/Student that are enrolled to that course
		tegrity.loginCourses("User1");
		
		//10.The recording page is loaded and the recording is being played.	
		player_page.verifyTimeBufferStatusForXSec(2);
		player_page.exitInnerFrame();
			
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
	}		
}

