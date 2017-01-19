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
import com.automation.main.page_helpers.AdvancedServiceSettingsPage;
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
public class TC15626ValidateCourseContentPageUIAsInstructor {

	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}
	
	public AdvancedServiceSettingsPage advanced_service_settings_page;
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
		ATUReports.add("selected browser type", LogAs.PASSED, new CaptureScreen( ScreenshotOf.DESKTOP));

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
		advanced_service_settings_page = PageFactory.initElements(driver, AdvancedServiceSettingsPage.class);
		
		Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC15626ValidateCourseContentPageUIAsInstructor at " + DateToStr);
		 ATUReports.add("Message window.", "TC15626ValidateCourseContentPageUIAsInstructor at " + DateToStr, "Starting the test: TC15626ValidateCourseContentPageUIAsInstructor at " + DateToStr, LogAs.PASSED, null);	
		
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

	
	@Test(description = "TC 15626 Validate Course Content Page UI As Instructor")
	public void test15626() throws Exception
	{
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);

		initializeCourseObject();
		
		// Preconditions:
		// 1. Login as Admin.
		tegrity.loginAdmin("Admin");
		
		
		// 2. Validate that "Enable student testing" is checked in "Advance service setting".
		admin_dash_board_page.clickOnTargetSubmenuAdvancedServices("Advanced Service Settings");
	
		advanced_service_settings_page.waitForThePageToLoad();
		advanced_service_settings_page.forceWebElementToBeSelected(advanced_service_settings_page.enable_student_testing_checkbox, "Enable student testing checkbox");
		advanced_service_settings_page.clickOnOkbutton();
		
		// 3. Logout.
		record.signOut();
		
		// 4. Login as INSTRUCTOR.
		tegrity.loginCourses("SuperUser");
		
		// 5. Click on the course link your about to test.
		course.selectCourseThatStartingWith("Ba");
		
		// 6. Hover over "Course Tasks" drop-down".
		// 7. Choose "Course settings" option.
		record.clickOnCourseTaskThenCourseSettings();
		
		
		// 8. Enable all settings in "Course settings" page.
		course_settings_page.waitForPageToLoad();
		course_settings_page.checkAllCourseSettingsCheckboxs();
		
		// 9. Click "Ok" button.
		course_settings_page.clickOnOkButton();
	
		// 10. Add Student recordings, Test recordings, Additional content to the course you are about to test.
		record.returnToCourseListPage();
		
		
		course.deleteAllRecordingsInCourseStartWith("Ba", 0, record, delete_menu);
		course.deleteAllRecordingsInCourseStartWith("Ba", 1, record, delete_menu);
		course.deleteAllRecordingsInCourseStartWith("Ba", 2, record, delete_menu);
		course.deleteAllRecordingsInCourseStartWith("Ba", 3, record, delete_menu);
		
		course.copyOneRecordingFromCourseStartWithToCourseStartWithOfType("BankValid", "Ba", 0, record, copy, confirm_menu);
		course.copyOneRecordingFromCourseStartWithToCourseStartWithOfType("BankValid", "Ba", 1, record, copy, confirm_menu);
		course.copyOneRecordingFromCourseStartWithToCourseStartWithOfType("BankValid", "Ba", 2, record, copy, confirm_menu);
		course.copyOneRecordingFromCourseStartWithToCourseStartWithOfType("BankValid", "Ba", 3, record, copy, confirm_menu);
		course.verifyRecordingsStatusIsClear("BankValidRecording", 0,record);
		course.verifyRecordingsStatusIsClear("BankValidRecording", 1,record);
		course.verifyRecordingsStatusIsClear("BankValidRecording", 2,record);
		course.verifyRecordingsStatusIsClear("BankValidRecording", 3,record);
		// 11. Sign Out.
		top_bar_helper.signOut();
		
		// END of Precondition
		
		// 12. Login as Instructor.
		tegrity.loginCourses("User1");
		
		// 13. Click on Course from the 'Active Courses' tab.
		course.selectCourseThatStartingWith("Ba");
		
		// 14. Hover over an available recording.
		record.moveToElementAndPerform(record.first_recording_title, driver);
		
		// 15. Click on a recording name.
		String recording_init_background = record.getBackGroundColor(driver.findElement(By.cssSelector(".panel.item-list.ng-isolate-scope")));
		record.clickElement(record.first_recording_title);
		Thread.sleep(3000);
		
		// TODO: 15.1. The '>' symbol turns to 'v'.
	
		// 15.2. The recording background turns to grey.
		if(record.getBackGroundColor(driver.findElement(By.cssSelector(".panel.item-list.ng-isolate-scope.activeItem.selected-container"))).equals(recording_init_background)) {
			System.out.println("Not verified that background color changes.");
			ATUReports.add("Background color changes.", "True.", "False.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		} else {
			System.out.println("Verfied that background color changes.");
			ATUReports.add("Background color changes.", "True.", "True.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		}

		// 16. Validate recording information is correct -  Recording information is in a format as follows.
		// 16.1. Length: X:XX:XX.
		record.verifyFormatOfRecordingLength();

		
		// 16.2. Recorded by: Username.
		record.verifyFormatOfRecordingRecordedBy();
		
		
		// 16.3. Recording adding date: “mm/dd/yyyy” (right aligned).
		record.verifyFormatOfRecordingsAddingDate();
	
		
		// 16.4. The blue bookmark icon is displayed prior to the recording adding date (If bookmarks/notes exist).
		record.verifyThatBookmarkIconDisplayedLeftToRecordingDateIfExist();

		// 17. Click on a recording's title - Recording is expanded. Verify that recording chapters are displayed as descripted - already expanded in 15
		// 17.1. The recording chapters are displayed to the USER.
		// 17.2. The recording chapters are displayed sequentially.
		record.verifyThatRecordingChaptersAreDisplySequentially();
		
		// 17.3. The recording chapters contain the ordinal numbers.
		// 17.4. The recording chapters contain the length from – to in a format as follows: “X:XX:XX – X:XX:XX"
		record.verifyRecordingChaptersContainsOrdinalNumberAndContainLengthFromToInAFormat();
		
		// 17.5. The recording chapters contain the image preview.
		record.verifyThatRecordingChaptersContainImagePreview();
		
		// 17.6. The recording record background changed to grey - already checked in 15.2

		// 18. Hover a cursor over recording chapter.
		Point before_hovring = record.video_wraps_of_chapters_of_opened_recording_list.get(0).getLocation();
		record.moveToElementAndPerform(record.video_wraps_of_chapters_of_opened_recording_list.get(0), driver);
		Thread.sleep(1000);
		
		// 18.1. The hovered over chapter becomes a bit bigger in size.
		Point after_hovring = record.video_wraps_of_chapters_of_opened_recording_list.get(0).getLocation();

		if((after_hovring.x < before_hovring.x) && (after_hovring.y < before_hovring.y)) {
			System.out.println("Verifed that chapter icon become a bit bigger in size.");
			ATUReports.add("Verifed that chapter icon become a bit bigger in size.", "True.", "True.", LogAs.PASSED, null);
		} else {
			System.out.println("Not verifed that chapter icon become a bit bigger in size.");
			ATUReports.add("Verifed that chapter icon become a bit bigger in size.", "True.", "False.", LogAs.FAILED, null);
		}
		
		// 18.2. The ‘>’ video player symbol is displayed over the chapter preview. 
		record.verifyWebElementDisplayed(driver.findElements(By.cssSelector(".play-button")).get(0), "Play symbol");

		// 19. Click on a recording's title.
		record.clickElement(record.first_recording_title);
		Thread.sleep(3000);
		
		// 19.1. Recording collapsed.
		record.verifyThatRecordingCollapsed();
		
		// 19.2. The recording record background changed to white.
		if(record.getBackGroundColor(driver.findElement(By.cssSelector(".panel.item-list.ng-isolate-scope"))).equals(recording_init_background)) {
			System.out.println("Verfied that background changed to white.");
			ATUReports.add("Background color changed to white.", "True.", "True.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Not verfied that background changed to white");
			ATUReports.add("Background color changed to white.", "True.", "False.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}

		// 20. Click on a '>' symbol left to recording's title.
		record.clickElement(record.first_recording_title);
		Thread.sleep(3000);
		
		// 20.1. Recording expanded.
		record.verifyThatRecordingExpanded();
		
		// 21. Click on a '>' symbol left to recording's title.
		record.clickElement(record.first_recording_title);
		Thread.sleep(3000);
		
		// 21.1. Recording collapsed.
		record.verifyThatRecordingCollapsed();

		// 22. Validate the "Breadcrumb" links are displayed correctly.
		// 22.1. The courses "breadcrumb" link is displayed at the top left corner of the page.
		// 22.2. The '>' character is displayed before the breadcrumb link.
		record.verifyThatBreadcrumbDisplayedCorrectly();

		// 23. Hover over the breadcrumbs link.
		record.moveToElementAndPerform(record.breadcrumbs_courses_link, driver);
		Thread.sleep(1000);
		
		// 23.1. The exact link the user has hovered over becomes underlined.
		if((record.breadcrumbs_courses_link.getCssValue("text-decoration")).equals("underline")) {
			System.out.println("Verfied that hovering over breadcrumbs courses link have underline.");
			ATUReports.add("Verfied that hovering over breadcrumbs courses link have underline.", "True.", "True.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("No verfied that hovering over breadcrumbs courses link have underline.");
			ATUReports.add("Verfied that hovering over breadcrumbs courses link have underline.", "True.", "False.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		
		// 23. The hint with the link caption appears.
		record.verifyWebElementHaveTargetAttributeTitle(record.breadcrumbs_courses_link, "Courses");

		// 24. Click on Courses "breadcrumb" link.
		record.clickElement(record.breadcrumbs_courses_link);
//		record.returnToCourseListPage();
		Thread.sleep(2000);
		
		// 25. Click on the course name that you about to test.
		current_course = course.selectCourseThatStartingWith("Ba");
		
		// 26. Validate the course name caption is displayed at the top left corner (Under the "Breadcrumb").
		record.verifyThatStringIsCourseName(current_course);
		
		// 27. Click on "Course Tasks" -> "Course Settings" and check the "Enable student testing (Remote Proctoring mode) checkbox, and click ok.
		record.clickOnCourseTaskThenCourseSettings();
		course_settings_page.CheckEnableStudentTesting();
		
		// 28. Validate the "Start a Recording" button is displayed at the top right of the page.
		record.veriftyThatStartRecordingButtonDisplayed();
		
		// 29. Hover over the "Start a Recording" button.
		String init_start_recording_button_background = record.getBackGroundColor(record.start_recording_button);
		record.moveToElementAndPerform(record.start_recording_button, driver);
		Thread.sleep(1000);
		
		// 29.1. The hint with the link caption appears.
		record.verifyWebElementHaveTargetAttributeTitle(record.start_recording_button, "Start a Recording");
		
		// 29.2. The button background-color changes (brighter).
		String hovred_start_recording_button_background = record.getBackGroundColor(record.start_recording_button);
		
		if(hovred_start_recording_button_background.equals(init_start_recording_button_background)) {
			System.out.println("The Start a Recording button background-color not changed.");
			ATUReports.add("The Start a Recording button background-color changed.", "True.", "False.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		} else {
			System.out.println("The Start a Recording button background-color changed.");
			ATUReports.add("The Start a Recording button background-color changed.", "True.", "True.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		}

		// 30. Validate the "Start a Test" button is displayed at the top right of the page.
		record.verifyThatStartATestDisplayed();
		
		// 31. Hover over the "Start a Test" button.
		String init_start_test_background = record.getBackGroundColor(record.start_test_button);
		record.moveToElementAndPerform(record.start_test_button, driver);
		Thread.sleep(1000);
		
		// 31.1. The hint with the link caption appears.
		record.verifyWebElementHaveTargetAttributeTitle(record.start_test_button, "Start a Test");
		
		// 31.2. The button background-color changes (brighter).
		String hovred_start_test_button_background = record.getBackGroundColor(record.start_test_button);
		
		if(hovred_start_test_button_background.equals(init_start_test_background)) {
			System.out.println("The Start a Test button background-color not changed.");
			ATUReports.add("The Start a Test button background-color changed.", "True.", "False.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		} else {
			System.out.println("The Start a Test button background-color changed.");
			ATUReports.add("The Start a Test button background-color changed.", "True.", "True.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		}

		// 32. Validate tabs are displayed correctlly.
		// 32.1. The "Recordings" tab is displayed under the course name.
		// 32.2. The "Additional Content" tab is displayed next to the ‘Recordings’ tab.
		// 32.3. The "Student Recordings" tab is displayed next to the ‘Additional Content’ tab.
		// 32.4. The "Tests" tab is displayed next to the ‘Student Recordings’ tab.
		record.tabsLocationVerified();

		// 33. Return to course list
		record.returnToCourseListPage();
		
		//34. Open chosen course.
		course.selectCourseThatStartingWith("Ba");
		
		// 35. Validate that the "Recordings" tab is chosen by defualt.
		record.verifyThatTargetTypeOfTabIsChosen(0);

		// 36. Validate the tab menu is displayed correctly under the tab name - Validate the tab menu is displayed correctly under the tab name.
		// 36.1. The "View" drop-down is displayed and left aligned.
		// 36.2. The "Course Tasks" drop-down is displayed next to the "View" element.
		// 36.3. The "Recording Task" drop-down is displayed and right aligned.
		// 36.4. The check box to select/deselect all the records in the course is displayed on the right of the "Recording Task".
		record.verifyMenuLocations();

		// 37. Click on the 'Courses' breadcrumb. - Checked in 33.
		// 38. Open "Course details" page. - Checked in 34.
		// 38.1. The "Recordings" tab is chosen by defualt. - Checked in 35.

		// 39. Hover over "View" element.
		record.moveToElement(record.view_button, driver).perform();
		Thread.sleep(1000);
		
		// 39.1. The hint with the name of the menu item is displayed to the user. 
		record.verifyWebElementHaveTargetAttributeTitle(record.view_button, "View");
		
		// 39.2. The 'View’ menu element consists of the items as follows: 'Sort By' ‘Title’, ‘Date’, ‘Duration ’, ‘ Tags ' with gray separator (might be Tags under it).
		record.verifyWebElementDisplayed(record.sort_by_title, "Title");
		record.verifyWebElementDisplayed(record.sort_by_date, "Date");
		record.verifyWebElementDisplayed(record.sort_by_duration, "Duration");
		record.verifyWebElementDisplayed(driver.findElements(By.cssSelector(".dropdown-menu.text-left>li>span")).get(0), "Sort By");
		record.verifyWebElementDisplayed(record.view_menu_tags_text, "Tags");
		
		// 40. Hover over "Course Tasks" element.
		record.moveToElement(record.course_task_button, driver).perform();
		Thread.sleep(1000);
		
		// 40.1. The hint with the name of the menu item is displayed to the user. 
		record.verifyWebElementHaveTargetAttributeTitle(record.course_task_button, "Course Tasks");
		
		// 40.2. The ‘Course Tasks’ menu element consists of the items as follows: ‘Course Settings’, ‘Upload a Recording',‘Upload Video File’, ‘Upload Audio File’(not in mac,‘Add Additional Content File’, ‘Add Additional Content Link’, Get Live Webcast’ ‘Subscribe to Your Course’s...’ menu sections separator (grayed out text), ‘RSS Feed’, ‘Podcast’, ‘Video Podcast’.
		record.verifyWebElementDisplayed(record.course_settings_button, "Course Settings");
		record.verifyWebElementDisplayed(record.upload_recording, "Upload a Recording");
		record.verifyWebElementDisplayed(record.upload_video_file, "Upload Video File");
		record.verifyWebElementDisplayed(record.upload_audio_file, "Upload Audio File");
		record.verifyWebElementDisplayed(record.add_additional_content_file, "Add Additional Content File");
		record.verifyWebElementDisplayed(record.get_live_webcast, "Get Live Webcast");
		record.verifyWebElementDisplayed(record.rssfeed, "RSS Feed");
		record.verifyWebElementDisplayed(record.podcast_button, "Podcast");
		record.verifyWebElementDisplayed(record.video_podcast, "Video Podcast");
		record.verifyWebElementDisplayed(record.SubscribeToACourse, "Subscribe to Your Course’s...");
		
		
		// 41. Hover over "Recording Tasks" element.
		record.moveToElement(record.recording_tasks_button, driver).perform();
		Thread.sleep(1000);
		
		// 41.1. The hint with the name of the menu item is displayed to the user. 
		record.verifyWebElementHaveTargetAttributeTitle(record.recording_tasks_button, "Recording Tasks");
		
		// 41.2. The ‘Recording Tasks’ menu element consists of the items as follows: ‘Move’, ‘Copy’, ‘Delete’, 'Publish', 'Tag',’, ‘Download Recording’. menu sections separation line (grayed out), ‘Edit Recording’, ‘Edit Recording Properties’, ‘Share Recording'.
		record.verifyWebElementDisplayed(record.move_button, "Move");
		record.verifyWebElementDisplayed(record.copy_button, "Copy");
		record.verifyWebElementDisplayed(record.delete_button, "Delete");
		record.verifyWebElementDisplayed(record.publish_button, "Publish");
		record.verifyWebElementDisplayed(record.tag_button, "Tag");
		record.verifyWebElementDisplayed(record.download, "Download Recording");
		record.verifyWebElementDisplayed(record.edit_rec_button, "Edit Recording");
		record.verifyWebElementDisplayed(record.edit_rec_properties_button, "Edit Recording Properties");
		record.verifyWebElementDisplayed(record.share_recording_button, "Share Recording");
		
		
		// 42. Check the course "select/deselect" checkbox.
		// 42.1. The textbox is checked.
		// 42.2. All of the recordings checkboxes are checked.
		record.checkAllCheckBox();
		record.verifyWebElementSelected(record.check_all_checkbox);
		Thread.sleep(1000);
		
		// 43. Uncheck the course "select/deselect" checkbox.
		record.clickElement(record.check_all_checkbox);
		
		// 43.1. The textbox is unchecked.
		record.verifyWebElementNotSelected(record.check_all_checkbox);
		
		// 43.2. All of the recordings checkboxes are unchecked.
		record.verifyAllCheckedboxNotSelected();
		
		// 44. Validate the recordings are sorted from new to old - The recordings are sorted by default date. asc. (after click on view->date. is old to new).
		record.pressViewButtonAndSelect("Date");
		record.clickElement(record.searchbox);
		Thread.sleep(1000);
		record.verifyRecordingSortedByDate(record.convertRecordingsListToDate());
		
		// TODO: 45. Validate the recordings are separated with gray line - Not for automation, recheck it.
//		Thread.sleep(2000);
//		
//		System.out.println(driver.findElement(By.cssSelector(".recordingInfoContainer")));
//
//		System.out.println(driver.findElement(By.cssSelector(".recordingInfoContainer.ng-scope")).getCssValue("border-bottom-color"));
//		System.out.println(driver.findElement(By.cssSelector(".recordingInfoContainer.ng-scope")).getCssValue("border-bottom-style"));
//		System.out.println(driver.findElement(By.cssSelector(".recordingInfoContainer.ng-scope")).getAttribute("border-bottom-width"));
		
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);

		
	}
}
