package com.automation.main.course_detailes_content_page;

import org.openqa.selenium.WebDriver;import com.automation.main.page_helpers.Page;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.automation.main.page_helpers.AddAdditionalContentLinkWindow;
import com.automation.main.page_helpers.AdminDashboardPage;
import com.automation.main.page_helpers.AdminDashboardViewCourseList;
import com.automation.main.page_helpers.BottomFooter;
import com.automation.main.page_helpers.ConfirmationMenu;
import com.automation.main.page_helpers.CopyMenu;
import com.automation.main.page_helpers.CourseSettingsPage;
import com.automation.main.page_helpers.CoursesHelperPage;
import com.automation.main.page_helpers.DeleteMenu;
import com.automation.main.page_helpers.EditRecordinPropertiesWindow;
import com.automation.main.page_helpers.EditRecording;
import com.automation.main.page_helpers.LoginHelperPage;
import com.automation.main.page_helpers.PlayerPage;
import com.automation.main.page_helpers.RecordingHelperPage;
import com.automation.main.page_helpers.SearchPage;
import com.automation.main.page_helpers.TopBarHelper;
import com.automation.main.utilities.DriverSelector;

import java.text.DateFormat;
import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;
import java.util.Date;

@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC24617ValidateBookmarkIconIsDisplayedForAnHelpDeskAdmin {

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
    String recodingDateNumber;
	String bookmark ;
	
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
		 System.out.println("Starting the test: TC24617ValidateBookmarkIconIsDisplayedForAnHelpDeskAdmin at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC24617ValidateBookmarkIconIsDisplayedForAnHelpDeskAdmin at " + DateToStr,
		 "Starting the test: TC24617ValidateBookmarkIconIsDisplayedForAnHelpDeskAdmin at " + DateToStr, LogAs.PASSED, null);
		
	}
	
	
	@AfterClass
	public void closeBroswer() {
		driver.quit();
	}


		
	// description = "get courses list"
	public void initializeCourseObject() throws InterruptedException {

		course = PageFactory.initElements(driver, CoursesHelperPage.class);
		course.courses = course.getCoursesListFromElement(course.course_list);
	}

	
	@Test(description = "TC 24617 Validate Bookmark Icon Is Displayed For An Help Desk Admin")
	public void test24617() throws Exception
	{
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
		
		// 1. For this test you need course with at least one recording.
		tegrity.loginCourses("SuperUser");
		
		String current_course = course.selectCourseThatStartingWith("abc");
		String url =  course.getCurrentUrlCoursePage(); 
		record.returnToCourseListPage();
		course.deleteAllRecordingsInCourseStartWith("abc", 0, record, delete_menu);
		course.copyOneRecordingFromCourseStartWithToCourseStartWithOfType("BankValid", "abc", 0, record, copy, confirm_menu);
		course.verifyRecordingsStatusIsClear("BankValidRecording", 0,record);
		top_bar_helper.signOut();
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
		top_bar_helper.signOut();
		Thread.sleep(Page.TIMEOUT_TINY);
		
		// 7. Login as HelpDesk Admin.
		tegrity.loginAdmin("HelpdeskAdmin");
		Thread.sleep(Page.TIMEOUT_TINY);
		
		// 8. Click on "View Course List".
		admin_dash_board_page.clickOnTargetSubmenuCourses("View Course List");
		
		// 9. move to the course through url
		Thread.sleep(Page.TIMEOUT_TINY);
		admin_dashboard_view_course_list.moveToCoursesThroughGet(url);	
		Thread.sleep(Page.TIMEOUT_TINY);
		
		// 10. Validate that bookmark sign is not displayed in the recording, left of recording date.
		if(first_recording_name.equals(record.getFirstRecordingTitle())) {
			record.verifyIndexRecordingHaveNoBookmark(1);
		} else {
			System.out.println("Recording is not displayed.");
			ATUReports.add("Recording list.", "Recording which student add bookmark is displayed.", "Recording is not displayed.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		
		top_bar_helper.signOut();
		Thread.sleep(Page.TIMEOUT_TINY);
		
		// 11. Login as Instructor.
		tegrity.loginCourses("User1");
		Thread.sleep(Page.TIMEOUT_TINY);
		
		// 12. Click on the Precondtional Course.
		course.selectCourseThatStartingWith(current_course);
		
		// 13. Select some Recording and click on it.
		// 14. Click on the first chapter.
		first_recording_name = record.getFirstRecordingTitle();
		record.clickOnTargetRecordingAndOpenItsPlayback(first_recording_name);
		
		// 15. Add some "Unclear" bookmark.
		player_page.verifyTimeBufferStatusForXSec(5);
		player_page.addTargetBookmark("Unclear by instructor");
		
		for(String handler: driver.getWindowHandles()) {
			driver.switchTo().window(handler);
			break;
		}
		top_bar_helper.signOut();
		Thread.sleep(Page.TIMEOUT_TINY);
		
		// 16. Login as HelpDesk Admin.
		tegrity.loginAdmin("HelpdeskAdmin");
		Thread.sleep(Page.TIMEOUT_TINY);
				
		// 17. Click on "View Course List".
		admin_dash_board_page.clickOnTargetSubmenuCourses("View Course List");
				
		// 18. move to the course through url
		Thread.sleep(Page.TIMEOUT_TINY);
		admin_dashboard_view_course_list.moveToCoursesThroughGet(url);	
		Thread.sleep(Page.TIMEOUT_TINY);

	
		// 19. Validate that bookmark sign is displayed in the recording, left of recording date.
		if(first_recording_name.equals(record.getFirstRecordingTitle())) {
			record.verifyIndexRecordingHaveBookmark(1);
		} else {
			System.out.println("Recording is not displayed.");
			ATUReports.add("Recording list.", "Recording which instructor add bookmark is displayed.", "Recording is not displayed.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		
		String recording_date = record.getIndexDateWebElement(1).getText();
		
		if(driver instanceof InternetExplorerDriver) {
			recodingDateNumber = recording_date.split(" ")[1];
			bookmark = recording_date.split(" ")[0];
		} else {		
			recodingDateNumber = recording_date.split("\n")[1];
			bookmark = recording_date.split("\n")[0];
		}
		try {
			Date date = new Date();
			date.parse(recodingDateNumber);
			if(bookmark.equals("bookmark")) {
				System.out.println("Verified that bookmark sign is to the left of recording date.");
				ATUReports.add("Verified that bookmark sign is to the left of recording date.", "True.", "True.", LogAs.PASSED, null);
				Assert.assertTrue(true);
			} else {
				System.out.println("Bookmark text is not found.");
				ATUReports.add("Bookmark text.", "Found.", "Not found.", LogAs.FAILED, null);
				Assert.assertTrue(false);
			}
		} catch(Exception msg) {
			msg.printStackTrace();
			System.out.println("Date is not correct/wrong.");
			ATUReports.add("Date is correct.", "True.", "False.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
		
	}
}
