package com.automation.main;


import java.util.Date;
import java.util.List;
import java.text.DateFormat;
import org.openqa.selenium.WebDriver;import com.automation.main.page_helpers.Page;import com.automation.main.page_helpers.Page;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;

@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC17050ValidateUserSeperationWhenWatchingRecording {

	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}
	
	public DeleteMenu delete_menu;
	public EditRecordinPropertiesWindow edit_recording_properties_window;
	public PlayerPage player_page;
	public AdminDashboardViewCourseList admin_dashboard_view_course_list;
	public AdminDashboardPage admin_dash_board_page;
	public CourseSettingsPage course_settings_page;
	public AddAdditionalContentLinkWindow add_additional_content_link_window;
	public EditRecording edit_recording;
	public BottomFooter bottom_footer;
	public SearchPage search_page;
	public TopBarHelper top_bar_helper;
	public LoginHelperPage tegrity;
	public CoursesHelperPage course;
	public RecordingHelperPage record;
	public ConfirmationMenu confirm_menu;
	WebDriver driver;
	WebDriverWait wait;
	public static WebDriver thread_driver;
	CopyMenu copy;
	String current_course;
	String targetCourse;
	String clickedRecording;
    DesiredCapabilities capability;
	@BeforeClass
	public void setup() {

		
//		System.setProperty("webdriver.ie.driver", "src/test/resources/IEDriverServer.exe");
//			capability=DesiredCapabilities.internetExplorer();
//			capability.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING,true);
//			
//		driver=new InternetExplorerDriver(capability);
		driver = DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());
		ATUReports.add("selected browser type", LogAs.PASSED, new CaptureScreen( ScreenshotOf.DESKTOP));

		
		//ATUReports.setWebDriver(driver);
		//ATUReports.add("set driver", true);
		tegrity = PageFactory.initElements(driver, LoginHelperPage.class);

		record = PageFactory.initElements(driver, RecordingHelperPage.class);
		copy = PageFactory.initElements(driver, CopyMenu.class);
		
		confirm_menu = PageFactory.initElements(driver, ConfirmationMenu.class);
		
		top_bar_helper = PageFactory.initElements(driver,TopBarHelper.class);
		search_page = PageFactory.initElements(driver, SearchPage.class);
		
		bottom_footer = PageFactory.initElements(driver, BottomFooter.class);
		
		edit_recording = PageFactory.initElements(driver, EditRecording.class);
		
		add_additional_content_link_window = PageFactory.initElements(driver, AddAdditionalContentLinkWindow.class);
		course_settings_page = PageFactory.initElements(driver, CourseSettingsPage.class);
		admin_dash_board_page = PageFactory.initElements(driver, AdminDashboardPage.class);
		admin_dashboard_view_course_list = PageFactory.initElements(driver, AdminDashboardViewCourseList.class);
		player_page = PageFactory.initElements(driver, PlayerPage.class);
		edit_recording_properties_window = PageFactory.initElements(driver, EditRecordinPropertiesWindow.class);
		delete_menu = PageFactory.initElements(driver, DeleteMenu.class);
		
		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC17050ValidateUserSeperationWhenWatchingRecording at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC17050ValidateUserSeperationWhenWatchingRecording at " + DateToStr,
		 "Starting the test: TC17050ValidateUserSeperationWhenWatchingRecording at " + DateToStr, LogAs.PASSED, null);	
	}
	
	
	@AfterClass
	public void closeBroswer() {
		driver.quit();
	}


	// @Parameters({"web","title"}) in the future
	@Test
	public void loadPage() throws InterruptedException {
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);

	}
	
	// description = "get courses list"
	public void initializeCourseObject() throws InterruptedException {

		course = PageFactory.initElements(driver, CoursesHelperPage.class);
		course.courses = course.getStringFromElement(course.course_list);
	}

	
	@Test(dependsOnMethods = "loadPage", description = "Login course page")
	public void loginCourses() throws Exception
	{
		
		// 1. Make sure to preapare two users: INSTRCTOR, and some other user (INSTRUCTOR/STUDENT) that never watched the recording you are using in this test case.
		tegrity.loginCourses("SuperUser");
		initializeCourseObject();
		
		
		String current_course = course.selectCourseThatStartingWith("abc");
		record.returnToCourseListPage();
		course.deleteAllRecordingsInCourseStartWith("abc", 0, record, delete_menu);
		course.copyOneRecordingFromCourseStartWithToCourseStartWithOfType("BankValid", "abc", 0, record, copy, confirm_menu);
		top_bar_helper.clickOnSignOut();
		Thread.sleep(Page.TIMEOUT_TINY);
		

		// 2. Login as an INSTRUCTOR.
		tegrity.loginCourses("User1");
		Thread.sleep(Page.TIMEOUT_TINY);
		
		// 3. Click on a certain course.
		course.selectCourseThatStartingWith(current_course);
		Thread.sleep(Page.TIMEOUT_TINY);
			
		// 4. Click on a certain recording.
		String first_recording_name = record.getFirstRecordingTitle();
		record.clickOnTargetRecordingAndOpenItsPlayback(first_recording_name);
			
		// 5. Click on the first chapter and wait the player will start to play.
		player_page.verifyTimeBufferStatusForXSec(5);
		
		// 6. Click the "> Courses" breadcrumb link while recording is playing the first chapter.
		for(String handler: driver.getWindowHandles()) {
			driver.switchTo().window(handler);
			break;
		}
		player_page.returnToRecordingPageByNameAsUserOrGuest(current_course, record);
			
		// 7. Sign out.
		top_bar_helper.clickOnSignOut();
		Thread.sleep(Page.TIMEOUT_TINY);
			
		// 8. Login as another STUDENT/INSTRUCTOR user who is enrolled to the same course.
		tegrity.loginCourses("User4");
		Thread.sleep(Page.TIMEOUT_TINY);
		
			
		// 9. Open the recording course you just watch.
		course.selectCourseThatStartingWith(current_course);
		Thread.sleep(Page.TIMEOUT_TINY);
			
		// 10. Click on the recording you just watched.
		record.clickElement(record.first_recording_title);
		Thread.sleep(Page.TIMEOUT_TINY);
			
		// 10.1. The "> Resume watching box isn't displayed.
		record.verifyWebElementNotDisplayed(record.list_of_resume_buttons.get(0), "Resume box");
		
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
		
	}
}