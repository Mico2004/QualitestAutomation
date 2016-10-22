package com.automation.main;

import java.util.List;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.openqa.selenium.WebDriver;import com.automation.main.page_helpers.Page;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
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
public class TC18879ValidateTheSourceTypeAsRecordingTextInSearchFieldOnTheAllCourseLevel {

	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}
	
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
		
		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC18879ValidateTheSourceTypeAsRecordingTextInSearchFieldOnTheAllCourseLevel at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC18879ValidateTheSourceTypeAsRecordingTextInSearchFieldOnTheAllCourseLevel at " + DateToStr,
		 "Starting the test: TC18879ValidateTheSourceTypeAsRecordingTextInSearchFieldOnTheAllCourseLevel at " + DateToStr, LogAs.PASSED, null);
		
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
		// 1. Validate there is recording text in this course. Search input specified shall be case-insensitive - Using preset recording: Wed, Apr 13, 11 12 AM 
		tegrity.loginCourses("SuperUser");
		initializeCourseObject();
		
		current_course = course.selectCourseThatStartingWith("Ab");
		
		// Make course public
		record.clickOnCourseTaskThenCourseSettings();
		course_settings_page.makeSureThatMakeCoursePublicIsSelected();
		course_settings_page.clickOnOkButton();
		Thread.sleep(Page.TIMEOUT_TINY);
		
		course.selectCourseThatStartingWith("Ab");
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
		record.clickOnRecordingTaskThenEditRecording();
		
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyhhmmss");
		String recording_text = "reocrd" + sdf.format(date); 
		edit_recording.changeFirstChapterRecordingNameToTargetNameNew(recording_text);
		Thread.sleep(Page.TIMEOUT_TINY);

		
		top_bar_helper.clickOnSignOut();
		
		
		// Looping for Student and Guest
		for(int type_of_user = 0; type_of_user < 4; type_of_user++) {
			if(type_of_user == 0) {
				// 2. Login as INSTRUCTOR.
				tegrity.loginCourses("User1");
			} else if (type_of_user == 1) {
				// 2. Login as Student.
				tegrity.loginCourses("User4");
			} else if (type_of_user == 2) {
				// 2. Login as guest
				tegrity.loginAsguest();
			} else {
				// 2. Login as ADMIN
				tegrity.loginAdmin("Admin");
			} 
			Thread.sleep(Page.TIMEOUT_TINY);
		
			// 3. Open some course.
			if(type_of_user == 3) {
		
				// Click on "view course list" under "courses" section.
				Thread.sleep(Page.TIMEOUT_TINY);
				admin_dash_board_page.clickOnTargetSubmenuCourses("View Course List");
				
				// In "All courses" page, search for Ab course.
				Thread.sleep(Page.TIMEOUT_TINY);
				admin_dashboard_view_course_list.searchForTargetCourseName(current_course);
				Thread.sleep(Page.TIMEOUT_TINY);
				
				// Click on that course name.
				admin_dashboard_view_course_list.clickOnFirstCourseLink();
				Thread.sleep(Page.TIMEOUT_TINY);
				
			}
				
			// 3. Set the focus to the field with a mouse pointer.
			top_bar_helper.search_box_field.click();
			
			// 4. Search the "Recording Text" that we mentioned in the preconditions and press ENTER.
			top_bar_helper.searchForTargetText(recording_text);
			
			// 4.1. In case the search process takes a long time, the animated spinner icon shall be displayed within the Search results page.
			search_page.verifyLoadingSpinnerImage();
			search_page.waitUntilSpinnerImageDisappear();
			search_page.exitInnerFrame();
			
			// 4.2. The breadcrumb structure displayed as follows: "> Courses > Course name > X results found for: "search_criterion". (X seconds)".
			if(type_of_user < 3) {
				search_page.verfiyBreadcrumbStructureDisplayedAsCoursesXResultsFound(current_course, recording_text);
			} else {	
				search_page.verfiyBreadcrumbStructureDisplayedAsCoursesCoursenameXresultsfoundForAdminDashboard(current_course, recording_text);
			}
			
		
			// 4.3. The recording chapter icon is displayed.
			search_page.verifyWebElementDisplayed(search_page.video_thumbnails_list.get(0), "The recording capter icon");
			
			// 4.4. The respective recording chapter snapshot is displayed in the icon - Cannot be automated (TODO: check another way later).
			// 4.5. The time of the text captured within recording is displayed over the chapter icon.
			search_page.verifyDurationTimeDisplayed();
			
			// 4.6. The course title in the format as follows: "Course: course_name.
			search_page.verifyDisplayCourseTitleForSearchInsideTargetCourse(current_course);
			
			// 4.7. The recording title in the format as follows: "Recording: recording_title.
			search_page.verifyRecordingTitleDisplayInCorrectFormat();
			
			// 4.8. The source title in the format as follows: " Source: Recording Text".
			search_page.verifyThatSourceTitleForTargetRecordingInTargetFormat(recording_text, "Source: Recording Text");
			
			// 4.9. The next result display below the current result in case there is next result.
			search_page.verifyThatNextResultDisplayBelowCurrentResultInCaseThereIsNextResultAddicnalCont();
			
			// 5. Click on the chapter icon.
			search_page.clickOnChapterIconOfRecordingInTargetIndex(1);
			
			// 5.1. The Tegrity Player page shall be opened and the recording shall start playing from the caption timestamp.
			player_page.verifyTimeBufferStatusForXSec(5);
			
			// 6. Click on the back cursor in the browser to navigate to the search results page.
			driver.navigate().back();
			search_page.waitUntilSpinnerImageDisappear();
			search_page.exitInnerFrame();
			
			// 7. Click on title of the chapter.
			search_page.clickOnChapterTitleOfRecordingInTargetIndex(1);
			
			// 7.1. The Tegrity Player page shall be opened and the recording shall start playing from the caption timestamp.
			player_page.verifyTimeBufferStatusForXSec(5);
			
			// 8. Click on the back cursor in the browser to navigate to the search results page.
			driver.navigate().back();
			search_page.waitUntilSpinnerImageDisappear();
			search_page.exitInnerFrame();
			
			// 9. Click on the recording title of the chapter.
			search_page.clickOnRecordingTitleOfChapterOfRecordingInTargetIndex(1);
			
			// 9.1. The Tegrity player page with the opened recording at the relevant time.
			player_page.verifyTimeBufferStatusForXSec(5);
			
			// 10. Click on the back cursor in the browser to navigate to the search results page.
			driver.navigate().back();
			search_page.waitUntilSpinnerImageDisappear();
			search_page.exitInnerFrame();
			
			// 11. Sign Out.
			top_bar_helper.clickOnSignOut();
		}
		
		// Unpublic Ab course1. 
		tegrity.loginCourses("User1");
						
		course.selectCourseThatStartingWith("Ab");
						
		// Make course public
		record.clickOnCourseTaskThenCourseSettings();
		course_settings_page.makeSureThatMakeCoursePublicIsUnSelected();
		course_settings_page.clickOnOkButton();
		
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
		
	}
}
