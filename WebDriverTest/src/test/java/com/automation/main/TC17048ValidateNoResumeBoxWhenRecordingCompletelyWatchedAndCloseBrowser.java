package com.automation.main;

import java.util.Date;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import java.text.DateFormat;
import java.util.Date;
import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;

@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC17048ValidateNoResumeBoxWhenRecordingCompletelyWatchedAndCloseBrowser {

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
		 System.out.println("Starting the test: TC17048ValidateNoResumeBoxWhenRecordingCompletelyWatchedAndCloseBrowser at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC17048ValidateNoResumeBoxWhenRecordingCompletelyWatchedAndCloseBrowser at " + DateToStr,
		 "Starting the test: TC17048ValidateNoResumeBoxWhenRecordingCompletelyWatchedAndCloseBrowser at " + DateToStr, LogAs.PASSED, null);
		
		
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
		
		// 1. Make sure that the STUDENT and INSTRUCTOR users you are using never watched the recording used in this test case.
		tegrity.loginCourses("SuperUser");
		initializeCourseObject();
		
		
		String current_course = course.selectCourseThatStartingWith("abc");
		record.returnToCourseListPage();
		course.deleteAllRecordingsInCourseStartWith("abc", 0, record, delete_menu);
		course.copyOneRecordingFromCourseStartWithToCourseStartWithOfType("BankValid", "abc", 0, record, copy, confirm_menu);
		top_bar_helper.clickOnSignOut();
		Thread.sleep(1000);
		
		// 2. Repeat for INSTRUCTOR and STUDENT.
		for(int type_of_user=0; type_of_user<2; type_of_user++) {
			if(type_of_user==0) {
				// 3. Login as an INSTRUCTOR/STUDENT.
				tegrity.loginCourses("User1");
				Thread.sleep(1000);
			} else {
				// 3. Login as an INSTRUCTOR/STUDENT.
				tegrity.loginCourses("User4");
				Thread.sleep(1000);
			}
			
			
			// 4. Click on a certain course.
			course.selectCourseThatStartingWith(current_course);
			Thread.sleep(1000);
			
			// 5. Click on a certain recording.
			// 6. Click on the first chapter and wait the player will start to play.
			String first_recording_name = record.getFirstRecordingTitle();
			record.clickOnTargetRecordingAndOpenItsPlayback(first_recording_name);
			
			// 7. Watch the recording completely.
			player_page.verifyTimeBufferStatusForXSec(3);
			player_page.watchTheRecordingCompletely();
			Thread.sleep(3000);
			
			// 8. Close browser.
			String current_handler = driver.getWindowHandle();
			driver.findElement(By.tagName("body")).sendKeys(Keys.CONTROL + "n");
			
			driver.switchTo().window(current_handler);
			driver.close();
			for(String handler: driver.getWindowHandles()) {
				driver.switchTo().window(handler);
				break;
			}
			
			// 9. Login as the same INSTRUCTOR.
			tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
			
			if(type_of_user==0) {
				// Login as an INSTRUCTOR/STUDENT.
				tegrity.loginCourses("User1");
				Thread.sleep(1000);
			} else {
				// Login as an INSTRUCTOR/STUDENT.
				tegrity.loginCourses("User4");
				Thread.sleep(1000);
			}
			
			initializeCourseObject();
			
			// 10. Open the recording course you just watch.
			course.selectCourseThatStartingWith(current_course);
			Thread.sleep(1000);
			
			// 11. Click on the recording you just watched.
			record.clickElement(record.first_recording_title);
			Thread.sleep(2000);
			
			// 11.1. The "> Resume box isn't displayed.
			record.verifyWebElementNotDisplayed(record.list_of_resume_buttons.get(0), "Resume box");
						
			// Sign out
			top_bar_helper.clickOnSignOut();
			Thread.sleep(1000);
		}
		
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
	
	}
}
