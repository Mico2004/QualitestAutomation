package com.automation.main.course_detailes_content_page;



import javax.swing.ListModel;
import java.text.DateFormat;
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

import java.util.Date;
import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;


@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC15754ValidateBookmarkIconIsDisplayedWhenAddingBookmarkThrougthThePlayerAsStudent {

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
		 System.out.println("Starting the test: TC15754ValidateBookmarkIconIsDisplayedWhenAddingBookmarkThrougthThePlayerAsStudent at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC15754ValidateBookmarkIconIsDisplayedWhenAddingBookmarkThrougthThePlayerAsStudent at " + DateToStr,
		 "Starting the test: TC15754ValidateBookmarkIconIsDisplayedWhenAddingBookmarkThrougthThePlayerAsStudent at " + DateToStr, LogAs.PASSED, null);	
		
	}
	
	
	@AfterClass
	public void closeBroswer() {
		driver.quit();
	}


	
	// description = "get courses list"
	public void initializeCourseObject() throws InterruptedException {

		course = PageFactory.initElements(driver, CoursesHelperPage.class);
		course.courses = course.getStringFromElement(course.course_list);
	}

	
	@Test(description = "TC 15754 Validate Bookmark Icon Is Displayed When Adding Bookmark Througth The Player As Student")
	public void test15754() throws Exception
	{
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);

		// 1. Make sure there is at least one student recording.
		tegrity.loginCourses("SuperUser");
		initializeCourseObject();
		
		
		String current_course = course.selectCourseThatStartingWith("abc");
		record.returnToCourseListPage();
		course.deleteAllRecordingsInCourseStartWith("abc", 2, record, delete_menu);
		course.copyOneRecordingFromCourseStartWithToCourseStartWithOfType("BankValid", "abc", 2, record, copy, confirm_menu);
		course.verifyRecordingsStatusIsClear("BankValidRecording", 2,record);
		top_bar_helper.clickOnSignOut();
		Thread.sleep(1000);
		
		// 2. Login as an STUDENT.
		tegrity.loginCourses("User4");
		Thread.sleep(1000);
		
		// 3. Enter to the course page.
		course.selectCourseThatStartingWith(current_course);
		Thread.sleep(1000);
		
		// 4. Click on "Student recordings" tab.
		record.clickOnStudentRecordingsTab();
		
		// 5. Click on "ex1" recording link.
		// 6. Click on the first chapter.
		String first_recording_name = record.getFirstRecordingTitle();
		record.clickOnTargetRecordingAndOpenItsPlayback(first_recording_name);
		
		// 7. Add a bookmark througth the "bookmarks and links" control.
		player_page.verifyTimeBufferStatusForXSec(5);
		player_page.addTargetBookmark("Unclear by student");
		
		for(String handler: driver.getWindowHandles()) {
			driver.switchTo().window(handler);
			break;
		}
		
		player_page.returnToRecordingPageByNameAsUserOrGuest(current_course, record);
		
		// 9. Validate the bookmark symbol is displayed in the "ex1" recording information.
		record.clickOnStudentRecordingsTab();
		Thread.sleep(1000);
		record.verifyIndexRecordingHaveBookmark(1);
		
		// 10. Sign out.
		record.clickOnSignOut();
		
		// 11. Login as a INSTRUCTOR.
		tegrity.loginCourses("User1");
	
		// 12. Open the course that contains "ex1" recording.
		course.selectCourseThatStartingWith(current_course);
		record.clickOnStudentRecordingsTab();
		Thread.sleep(1000);
		
		// 13. Validate the bookmark symbol is not displayed in the "ex1" recording information.
		record.verifyIndexRecordingHaveNoBookmark(1);
		
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);

		
	}
}
