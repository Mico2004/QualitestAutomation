package com.automation.main.course_detailes_content_page;


import java.text.DateFormat;
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
import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;

@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC15446ValidateExpandingViewingAndCollapsingTheChapters {

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
		
		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC15446ValidateExpandingViewingAndCollapsingTheChapters at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC15446ValidateExpandingViewingAndCollapsingTheChapters at " + DateToStr, "Starting the test: TC15446ValidateExpandingViewingAndCollapsingTheChapters at " + DateToStr, LogAs.PASSED, null);	
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

	
	@Test(description = "TC 15446 Validate Expanding Viewing And Collapsing The Chapters")
	public void test15446() throws Exception
	{
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);

		// 1. Login as INSTRUCTOR/STUDENT.
		tegrity.loginCourses("User1");
		initializeCourseObject();
		
		// 2. Select course.
		course.selectCourseThatStartingWith("Ab");
		
		// 3. Click on a recording's title.
		record.waitForVisibility(record.first_recording_title);
		String recording_init_background = record.getBackGroundColor(driver.findElement(By.cssSelector(".panel.item-list.ng-isolate-scope")));
		record.verifyFirstExpandableRecording();
	
		// 4. Verify that recording chapters are displayed as descripted.
		// 4.1. The recording chapters are displayed to the USER.
		// 4.2. The recording chapters are displayed sequentially.
		record.verifyThatRecordingChaptersAreDisplySequentially();
		
		// 4.3. The recording chapters contain the ordinal numbers.
		// 4.4. The recording chapters contain the length from � to in a format as follows: �X:XX:XX � X:XX:XX".
		record.verifyRecordingChaptersContainsOrdinalNumberAndContainLengthFromToInAFormat();
		
		
		// 4.5. The recording chapters contain the image preview.
		record.verifyThatRecordingChaptersContainImagePreview();
		
		// 4.6. The recording record background changed to grey.
		if(record.getBackGroundColor(driver.findElement(By.cssSelector(".panel.item-list.ng-isolate-scope.activeItem.selected-container"))).equals(recording_init_background)) {
			System.out.println("Not verified that background color changes.");
			ATUReports.add("Background color changes.", "True.", "False.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		} else {
			System.out.println("Verfied that background color changes.");
			ATUReports.add("Background color changes.", "True.", "True.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		}
		
		// 5. Hover a cursor over recording chapter.
		Point before_hovring = record.video_wraps_of_chapters_of_opened_recording_list.get(0).getLocation();
		record.moveToElementAndPerform(record.video_wraps_of_chapters_of_opened_recording_list.get(0), driver);
		Thread.sleep(1000);
		
		// 5.1. The hovered over chapter becomes a bit bigger in size.
		Point after_hovring = record.video_wraps_of_chapters_of_opened_recording_list.get(0).getLocation();

		if((after_hovring.x < before_hovring.x) && (after_hovring.y < before_hovring.y)) {
			System.out.println("Verifed that chapter icon become a bit bigger in size.");
			ATUReports.add("Verifed that chapter icon become a bit bigger in size.", "True.", "True.", LogAs.PASSED, null);
		} else {
			System.out.println("Not verifed that chapter icon become a bit bigger in size.");
			ATUReports.add("Verifed that chapter icon become a bit bigger in size.", "True.", "False.", LogAs.FAILED, null);
		}
		
		// 5.2. The �>� video player symbol is displayed over the chapter preview.
		record.verifyWebElementDisplayed(driver.findElements(By.cssSelector(".play-button")).get(0), "Play symbol");
		
		// 6. Click on a recording's title.
		record.clickElement(record.first_recording_title);
		Thread.sleep(1000);
		
		// 6.1. Recording collapsed.
		record.verifyThatRecordingCollapsed();
		
		// 6.2. The recording record background changed to white.
		if(record.getBackGroundColor(driver.findElement(By.cssSelector(".panel.item-list.ng-isolate-scope"))).equals(recording_init_background)) {
			System.out.println("Verfied that background changed to white.");
			ATUReports.add("Background color changed to white.", "True.", "True.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Not verfied that background changed to white");
			ATUReports.add("Background color changed to white.", "True.", "False.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		
		// 7. Click on a '>' symbol left to recording's title.
		record.clickElement(record.first_recording_title);
		Thread.sleep(1000);
		
		// 7.1. Recording expanded.
		record.verifyThatRecordingExpanded();
		
		// 8. Click on a '>' symbol left to recording's title.
		record.clickElement(record.first_recording_title);
		Thread.sleep(1000);
		
		// 8.1. Recording collapsed.
		record.verifyThatRecordingCollapsed();

		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
		
	}
}
