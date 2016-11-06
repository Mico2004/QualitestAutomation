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
public class TC24615ValidateBookmarkIconIsDisplayedForAnExcutiveAdminUser {

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
		 System.out.println("Starting the test: TC24615ValidateBookmarkIconIsDisplayedForAnExcutiveAdminUser at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC24615ValidateBookmarkIconIsDisplayedForAnExcutiveAdminUser at " + DateToStr,
		 "Starting the test: TC24615ValidateBookmarkIconIsDisplayedForAnExcutiveAdminUser at " + DateToStr, LogAs.PASSED, null);	
		
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
		// 1. For this test you need course with at least one recording.
		tegrity.loginCourses("SuperUser");
		initializeCourseObject();
		
		
		String current_course = course.selectCourseThatStartingWith("abc");
		record.returnToCourseListPage();
		course.deleteAllRecordingsInCourseStartWith("abc", 0, record, delete_menu);
		course.copyOneRecordingFromCourseStartWithToCourseStartWithOfType("BankValid", "abc", 0, record, copy, confirm_menu);
		top_bar_helper.clickOnSignOut();
		Thread.sleep(Page.TIMEOUT_TINY);
		
		// 2. Login as Student.
		tegrity.loginCourses("User4");
		Thread.sleep(Page.TIMEOUT_TINY);
		
		// 3. Click on the Precondtional Course.
		course.selectCourseThatStartingWith(current_course);
		Thread.sleep(Page.TIMEOUT_TINY);
		
		// 4. Select some Recording and click on it.
		// 5. Click on the first chapter.
		String first_recording_name = record.getFirstRecordingTitle();
		record.clickOnTargetRecordingAndOpenItsPlayback(first_recording_name);
		
		// 6. Add some "Unclear" bookmark.
		player_page.verifyTimeBufferStatusForXSec(5);
		player_page.addTargetBookmark("Unclear by student");
		
		for(String handler: driver.getWindowHandles()) {
			driver.switchTo().window(handler);
			break;
		}
		top_bar_helper.clickOnSignOut();
		Thread.sleep(Page.TIMEOUT_TINY);
		
		// 7. Login as Excutive Admin.
		tegrity.loginCourses("ExcutiveAdmin");
		Thread.sleep(Page.TIMEOUT_TINY);
		
		
		// 8. Click on the Preconditional Course.
		course.selectCourseThatStartingWith(current_course);
		Thread.sleep(Page.TIMEOUT_TINY);

		
		// 9. Validate that bookmark sign is not displayed in the recording, left of recording date.
		if(first_recording_name.equals(record.getFirstRecordingTitle())) {
			record.verifyIndexRecordingHaveNoBookmark(1);
		} else {
			System.out.println("Recording is not displayed.");
			ATUReports.add("Recording list.", "Recording which student add bookmark is displayed.", "Recording is not displayed.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		
		top_bar_helper.clickOnSignOut();
		Thread.sleep(Page.TIMEOUT_TINY);
		
		// 10. Login as Instructor.
		tegrity.loginCourses("User1");
		Thread.sleep(Page.TIMEOUT_TINY);
		
		// 11. Click on the Precondtional Course.
		course.selectCourseThatStartingWith(current_course);
		
		// 12. Select some Recording and click on it.
		// 13. Click on the first chapter.
		first_recording_name = record.getFirstRecordingTitle();
		record.clickOnTargetRecordingAndOpenItsPlayback(first_recording_name);
		
		// 14. Add some "Unclear" bookmark.
		player_page.verifyTimeBufferStatusForXSec(5);
		player_page.addTargetBookmark("Unclear by instructor");
		
		for(String handler: driver.getWindowHandles()) {
			driver.switchTo().window(handler);
			break;
		}
		top_bar_helper.clickOnSignOut();
		Thread.sleep(Page.TIMEOUT_TINY);
		
		// 15. Login as HelpDesk Admin.
		tegrity.loginCourses("ExcutiveAdmin");
		Thread.sleep(Page.TIMEOUT_TINY);

				
		// 16. Click on the Preconditional Course.
		course.selectCourseThatStartingWith(current_course);
		Thread.sleep(Page.TIMEOUT_TINY);
		
		// 17. Validate that bookmark sign is displayed in the recording, left of recording date.
		if(first_recording_name.equals(record.getFirstRecordingTitle())) {
			record.verifyIndexRecordingHaveBookmark(1);
		} else {
			System.out.println("Recording is not displayed.");
			ATUReports.add("Recording list.", "Recording which instructor add bookmark is displayed.", "Recording is not displayed.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		
		String recording_date = record.getIndexDateWebElement(1).getText();
		
		try {
			Date date = new Date();
			date.parse(recording_date.split("\n")[1]);
			if(recording_date.split("\n")[0].equals("bookmark")) {
				System.out.println("Verified that bookmark sign is to the left of recording date.");
				ATUReports.add("Verified that bookmark sign is to the left of recording date.", "True.", "True.", LogAs.PASSED, null);
				Assert.assertTrue(true);
			} else {
				System.out.println("Bookmark text is not found.");
				ATUReports.add("Bookmark text.", "Found.", "Not found.", LogAs.FAILED, null);
				Assert.assertTrue(false);
			}
		} catch(Exception msg) {
			System.out.println("Date is not correct/wrong.");
			ATUReports.add("Date is correct.", "True.", "False.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
	
	}
}
