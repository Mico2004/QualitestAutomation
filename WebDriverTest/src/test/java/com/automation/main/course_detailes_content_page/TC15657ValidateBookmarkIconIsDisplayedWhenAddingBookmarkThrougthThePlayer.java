package com.automation.main.course_detailes_content_page;


import java.util.Date;
import java.util.List;

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

import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;
import java.text.DateFormat;


@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC15657ValidateBookmarkIconIsDisplayedWhenAddingBookmarkThrougthThePlayer {

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
		course_settings_page = PageFactory.initElements(driver, CourseSettingsPage.class);
		admin_dash_board_page = PageFactory.initElements(driver, AdminDashboardPage.class);
		admin_dashboard_view_course_list = PageFactory.initElements(driver, AdminDashboardViewCourseList.class);
		player_page = PageFactory.initElements(driver, PlayerPage.class);
		edit_recording_properties_window = PageFactory.initElements(driver, EditRecordingPropertiesWindow.class);
		delete_menu = PageFactory.initElements(driver, DeleteMenu.class);
		
		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC15657ValidateBookmarkIconIsDisplayedWhenAddingBookmarkThrougthThePlayer at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC15657ValidateBookmarkIconIsDisplayedWhenAddingBookmarkThrougthThePlayer at " + DateToStr, "Starting the test: TC15657ValidateBookmarkIconIsDisplayedWhenAddingBookmarkThrougthThePlayer at " + DateToStr, LogAs.PASSED, null);	
		
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

	
	@Test(description = "TC 15657 Validate Bookmark Icon Is Displayed When Adding Bookmark Througth The Player")
	public void test15657() throws Exception
	{
	
		// 1. Open tegrity "Login page".
		// 2. Login as an INSTRUCTOR (SuperUser)2. Login as an INSTRUCTOR (SuperUser)
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
		tegrity.loginCourses("SuperUser");
		initializeCourseObject();
		
		// 3. Upload a recording named "ex1" to a certain course (Copy to course2).
		String course_name = course.selectCourseThatStartingWith("abc");
		record.returnToCourseListPage();
		course.deleteAllRecordingsInCourseStartWith("abc", 0, record, delete_menu);
		course.selectCourseThatStartingWith("BankValid");
		
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox2);
	
		record.clickOnRecordingTaskThenCopy();
		copy.selectTargetCourseFromCourseListThatStartWith("abc");
		copy.clickOnCopyButton();	
		confirm_menu.clickOnOkButtonAfterConfirmCopyRecordings();
		record.checkStatusExistenceForMaxTTime(360);
		
		// 4. Logout.
		record.signOut();
		
		
		// 5. Login as an INSTRUCTOR (User1)
		tegrity.loginCourses("User1");
			
		// 6. Enter to the course page (Course2).
		course.selectCourseThatStartingWith("abc");
		//getting the url for the admin
		String url =  course.getCurrentUrlCoursePage(); 
			
		// 7.1. The uploaded recording is displayed with no "bookmark" symbol displayed.
		record.verifyIndexRecordingHaveNoBookmark(1);
		List<String> recording_list = record.getCourseRecordingList();
		String first_recording_name = recording_list.get(0);
		String second_recording_name = recording_list.get(1);
		
		// 8. Click on "ex1" recording link.
		// 9. Click on the first chapter.
		record.clickOnTargetRecordingAndOpenItsPlayback(first_recording_name);
		
		// 10. Add a bookmark througth the "bookmarks and links" control.
		player_page.verifyTimeBufferStatusForXSec(5);
		player_page.addTargetBookmark("First recording bookmark");
		
		//11. Click on the course name link in the "Breadcrumb" area.
		player_page.returnToRecordingPageByNameAsUserOrGuest(course_name, record);
		
		
		// 12. Validate the bookmark symbol is displayed in the "ex1" recording information.
		if(first_recording_name.equals(record.getFirstRecordingTitle())) {
			record.verifyIndexRecordingHaveBookmark(1);
		} else {
			record.verifyIndexRecordingHaveBookmark(2);
		}
		
		// 13. Sign out.
		record.signOut();
		
		// 14. Login as a STUDENT (User4).
		tegrity.loginCourses("User4");
		
		// 15. Open the course that contains "ex1" recording.
		course.selectCourseThatStartingWith("abc");
		
		// 16. Validate the bookmark symbol is displayed in the "ex1" recording information.
		if(first_recording_name.equals(record.getFirstRecordingTitle())) {
			record.verifyIndexRecordingHaveBookmark(1);
		} else {
			record.verifyIndexRecordingHaveBookmark(2);
		}
		
		// Sign Out
		record.signOut();
		
		// 17. Login as a Admin.
		tegrity.loginAdmin("Admin");
		
		//18. Click on "view course list" under "courses" section.
		admin_dash_board_page.clickOnTargetSubmenuCourses("View Course List");	
		// In "All courses" page, search for Ab course.
		admin_dashboard_view_course_list.waitForThePageToLoad();
		admin_dashboard_view_course_list.moveToCoursesThroughGet(url);
		
		// 19. Validate the bookmark symbol is displayed in the "ex1" recording information.
		if(first_recording_name.equals(record.getFirstRecordingTitle())) {
			record.verifyIndexRecordingHaveBookmark(1);
		} else {
			record.verifyIndexRecordingHaveBookmark(2);
		}
		
		// 20. Sign Out.
		record.signOut();
	
		
		// 21. Login as student (User4).
		tegrity.loginCourses("User4");
	
		// 22. Enter to the course page.
		course.selectCourseThatStartingWith("abc");
		
		// 23. Click on another recording (Not the one you used before).
		// 24. Click on the first chapter.
		if(first_recording_name.equals(record.getFirstRecordingTitle())) {
			record.clickOnTargetRecordingAndOpenItsPlayback(second_recording_name);
		} else {
			record.clickOnTargetRecordingAndOpenItsPlayback(first_recording_name);
		}
		
		// 25. Add a bookmark througth the "bookmarks and links" control.
		player_page.verifyTimeBufferStatusForXSec(2);
		player_page.addTargetBookmark("Second recording bookmark");
	
		
		for(String handler: driver.getWindowHandles()) {
			driver.switchTo().window(handler);
			break;
		}
		
		// Sign Out
		record.signOut();
			
		// 26. Login as another student to the same course (User2).
		tegrity.loginCourses("User2");
			
		// 27. Enter to the same course page
		course.selectCourseThatStartingWith("abc");
		
		
		// 28. Make sure the bookmark symbol do no appear for the recording you just add a bookmark.
		if(first_recording_name.equals(record.getFirstRecordingTitle())) {
			record.verifyIndexRecordingHaveNoBookmark(2);
		} else {
			record.verifyIndexRecordingHaveNoBookmark(1);
		}
		
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
	}
}
