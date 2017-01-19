package com.automation.main.course_detailes_content_page;


import java.util.Date;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
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
import com.automation.main.page_helpers.EditRecordingPropertiesWindow;
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

@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC15663ValidateBookmarkIconIsDisplayedWhenAddingBookmarkThrougthTheEditor {

	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}
	
	public DeleteMenu delete_menu;
	public EditRecordingPropertiesWindow edit_recording_properties_window;
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

	
		driver = DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());
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
		edit_recording_properties_window = PageFactory.initElements(driver, EditRecordingPropertiesWindow.class);
		delete_menu = PageFactory.initElements(driver, DeleteMenu.class);
		
		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC15663ValidateBookmarkIconIsDisplayedWhenAddingBookmarkThrougthTheEditor at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC15663ValidateBookmarkIconIsDisplayedWhenAddingBookmarkThrougthTheEditor at " + DateToStr,
		 "Starting the test: TC15663ValidateBookmarkIconIsDisplayedWhenAddingBookmarkThrougthTheEditor at " + DateToStr, LogAs.PASSED, null);
		
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

	
	@Test(description = "TC 15663 Validate Bookmark Icon Is Displayed When Adding Bookmark Througth The Editor")
	public void test15663() throws Exception
	{
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);

		// 1. Open tegrity "Login page".
		// 2. Login as an INSTRUCTOR (SuperUser).
		tegrity.loginCourses("SuperUser");
		initializeCourseObject();
				
		// 3. Upload a recording named "ex1" to a certain course (Copy to course2).
		String current_course = course.selectCourseThatStartingWith("Ba");
		record.returnToCourseListPage();
		course.deleteAllRecordingsInCourseStartWith("Ba", 0, record, delete_menu);
		course.copyOneRecordingFromCourseStartWithToCourseStartWithOfType("BankValid", "Ba", 0, record, copy, confirm_menu);
		course.verifyRecordingsStatusIsClear("BankValidRecording", 0,record);
		// 4. Logout.
		top_bar_helper.signOut();

		
		// 5. Login as Instructor (User1).
		tegrity.loginCourses("User1");

		
		// 6. Enter to the course page.
		course.selectCourseThatStartingWith(current_course);
		
		// 6.1. The uploaded recording is displayed with no "bookmark" symbol displayed.
		record.verifyIndexRecordingHaveNoBookmark(1);
		
		// 7. Check the "ex3" checkbox.
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
		
		// 8. Hove the "Recording Task" Drop down.
		// 9. Choose the "Edit Recording" option
		record.clickOnRecordingTaskThenEditRecording();
		
		// TODO: 10. Add a bookmark througth the "bookmarks and links" control.
		for(int i=0; i<10; i++) {
			try {
				driver.switchTo().frame(0);
				break;
			} catch (Exception e) {
				Thread.sleep(1000);
			}
		}
		
		for(int i=0; i<20; i++) {
			try {
				if(driver.findElement(By.id("PlayButton_Img")).isDisplayed()) {
					System.out.println("2222");
					break;
				} else {
					Thread.sleep(1000);
				}
			} catch (Exception e) {
				Thread.sleep(1000);
			}
				
		}
		Thread.sleep(2000);
		player_page.addTargetBookmark("First recording bookmark");
		
		// TODO: 11. Click on the course name link in the "Breadcrumb" area.
		for(String handler: driver.getWindowHandles()) {
			driver.switchTo().window(handler);
			break;
		}
		Thread.sleep(1000);
		driver.findElements(By.cssSelector("#tegrityBreadcrumbsBox>.ng-scope>.ng-scope.ng-binding")).get(1).click();
		Thread.sleep(3000);
		
		
		// 12. Validate the bookmark symbol is displayed in the "ex3" recording information.
		record.verifyIndexRecordingHaveBookmark(1);
		
		// 13. Sign out.
		top_bar_helper.signOut();

		
		// 14. Login as a STUDENT.
		tegrity.loginCourses("User4");
	
		
		// 15. Open the course that contains "ex3" recording.
		course.selectCourseThatStartingWith(current_course); 
		
		// 16. Validate the bookmark symbol is displayed in the "ex3" recording information.
		record.verifyIndexRecordingHaveBookmark(1);
		
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
		
	}
}
