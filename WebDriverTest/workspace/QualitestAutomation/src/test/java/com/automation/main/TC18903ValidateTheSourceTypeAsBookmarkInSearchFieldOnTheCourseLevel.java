package com.automation.main;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;

@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC18903ValidateTheSourceTypeAsBookmarkInSearchFieldOnTheCourseLevel {

	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}
	
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

//		driver.manage().window().maximize();

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
		
		
		
	}
	
	
//	@AfterClass
//	public void closeBroswer() {
//		driver.quit();
//	}


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
		// TOODO: 1. Validate there is bookmark in this course. Search input specified shall be case-insensitive.
		
		tegrity.loginCourses("User1");
		initializeCourseObject();
		
		String current_course = course.selectCourseThatStartingWith("Ab");
		
		// Make course public
		record.clickOnCourseTaskThenCourseSettings();
		course_settings_page.makeSureThatMakeCoursePublicIsSelected();
		course_settings_page.clickOnOkButton();
		Thread.sleep(1000);
		
		// TODO: Delete all bookmarks and create new bookmark
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyhhmmss"); 
		String bookmark_for_search = "NewBookmark" + sdf.format(date);
		
		String bookmarked_recording_title = record.getFirstRecordingTitle();
		record.clickOnTargetRecordingAndOpenItsPlayback(bookmarked_recording_title);
		player_page.verifyTimeBufferStatusForXSec(10);
//		player_page.addTargetBookmark(bookmark_for_search);
//		player_page.deleteAllBookmark();
		player_page.moveToElement(driver.findElement(By.cssSelector(".BookmarkSelected")),driver).perform();
		
//		top_bar_helper.clickOnSignOut();
//		Thread.sleep(3000);
		
		
//		// Looping for Student, Guest and ADMIN
//		for(int type_of_user = 0; type_of_user < 4; type_of_user++) {
//			
//			if(type_of_user == 0) {
//				// 2. Login as ADMIN
//				tegrity.loginAdmin("Admin");
//			} else if (type_of_user == 1) {
//				// 2. Login as Student.
//				tegrity.loginCourses("User4");
//			} else if (type_of_user == 2) {
//				// 2. Login as guest
//				tegrity.loginAsguest();
//			} else {
//				// 2. Login as INSTRUCTOR.
//				tegrity.loginCourses("User1");
//			}
//			Thread.sleep(3000);
//			
//			if(type_of_user != 0) {
//				// 3. Open some course.
//				course.selectCourseThatStartingWith(current_course);
//			} else {
//				// Click on "view course list" under "courses" section.
//				admin_dash_board_page.clickOnTargetSubmenuCourses("View Course List");
//				
//				// In "All courses" page, search for Ab course.
//				Thread.sleep(8000);
//				admin_dashboard_view_course_list.searchForTargetCourseName(current_course);
//				Thread.sleep(3000);
//				
//				// Click on that course name.
//				admin_dashboard_view_course_list.clickOnFirstCourseLink();
//				Thread.sleep(1000);
//			}
//			
//			
//			// 4. Set the focus to the field with a mouse pointer.
//			top_bar_helper.search_box_field.click();
//			
//			// 5. Search the "Bookmark" that we mentioned in the preconditions and press ENTER.
//			top_bar_helper.searchForTargetText(bookmark_for_search);
//			
//			// 5.1. In case the search process takes a long time, the animated spinner icon shall be displayed within the Search results page.
//			search_page.verifyLoadingSpinnerImage();
//			search_page.waitUntilSpinnerImageDisappear();
//			
//			// 5.2. The breadcrumb structure displayed as follows: "> Courses > Course name > X results found for: "search_criterion". (X seconds)".
//			if(type_of_user != 0) {
//				search_page.verfiyBreadcrumbStructureDisplayedAsCoursesCoursenameXresultsfound(current_course, bookmark_for_search);
//			} else {
//				search_page.verfiyBreadcrumbStructureDisplayedAsCoursesCoursenameXresultsfoundForAdminDashboard(current_course, bookmark_for_search);
//			}
//			
//			// 5.3. The bookmark icon is displayed.
//			search_page.verifyBookmarkIconDisplayedIndexSearchResult(1);
//			
//			
//			// 5.4. The course title in the format as follows: "Course: course_name.
//			search_page.verifyDisplayCourseTitleForSearchInsideTargetCourse(current_course);
//			
//			// TODO: 5.5. The recording title contain the exact time the bookmark was added at.
//			
//			// 5.6. The source title in the format as follows: "Source: Bookmark".
//			search_page.verifyThatSourceTitleForTargetRecordingInTargetFormat(bookmark_for_search, "Source: Bookmark");
//			
//			// 5.7. The next result display below the current result in case there is next result.
//			search_page.verifyThatNextResultDisplayBelowCurrentResultInCaseThereIsNextResult();
//			
//			// 6. Hover over the chapter icon.
//			search_page.moveToElement(search_page.link_icon_list.get(0), driver).perform();
//			Thread.sleep(2000);
//			
//			// 6.1. The source displayed as a hint.
//			search_page.verifyWebElementHaveTargetAttributeTitle(search_page.link_icon_list.get(0), "Bookmark");
//			
//			// 7. Click on the chapter icon.
//			search_page.clickElement(search_page.link_icon_list.get(0));
//			
//			// 7.1. The Tegrity Player page is opened and the recording start playing from the timestamp of the bookmark.
//			player_page.verifyTimeBufferStatusForXSec(5);
//			
//			// 8. Click on the back cursor in the browser to navigate to the search results page.
//			driver.navigate().back();
//			search_page.waitUntilSpinnerImageDisappear();
//			Thread.sleep(2000);
//			
//			// 9. Click on title of the bookmark.
//			search_page.clickOnChapterTitleOfRecordingInTargetIndex(1);
//			
//			// 9.1. The Tegrity Player page is opened and the recording start playing from the timestamp of the bookmark.
//			player_page.verifyTimeBufferStatusForXSec(5);
//			
//			// Click on the back cursor in the browser to navigate to the search results page.
//			driver.navigate().back();
//			search_page.waitUntilSpinnerImageDisappear();
//			Thread.sleep(2000);
//			
//			if(type_of_user==3) {
//				// 10. Click on the course name in the breadcrumb.
//				search_page.clickBackToCourseInBreadcrumbs();
//				Thread.sleep(2000);
//				
//				// TODO: 11. Delete the bookmark from the recording that we mentioned in the preconditions.
//				
//				// 12. Search the bookmark from the recording that we mentioned in the preconditions.
//				top_bar_helper.searchForTargetText(bookmark_for_search);
//				search_page.verifyLoadingSpinnerImage();
//				search_page.waitUntilSpinnerImageDisappear();
//				
//				// 16.1. The chapter is'nt displayed and ,the empty search results page shall be displayed.
//				search_page.verifySearchResultIsEmpty();
//				
//			}
//			Thread.sleep(1000);
//			
//			// 14. Sign Out.
//			top_bar_helper.clickOnSignOut();
//			Thread.sleep(3000);
//		}
//		
//		// Unpublic Ab course1. 
//		tegrity.loginCourses("User1");
//						
//		course.selectCourseThatStartingWith("Ab");
//						
//		// Make course public
//		record.clickOnCourseTaskThenCourseSettings();
//		course_settings_page.makeSureThatMakeCoursePublicIsUnSelected();
//		course_settings_page.clickOnOkButton();
		
		
		
	}
}
