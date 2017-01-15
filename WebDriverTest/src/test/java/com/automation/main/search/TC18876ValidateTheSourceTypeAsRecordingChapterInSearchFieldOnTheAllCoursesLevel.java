package com.automation.main.search;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.DateFormat;
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
public class TC18876ValidateTheSourceTypeAsRecordingChapterInSearchFieldOnTheAllCoursesLevel {

	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}
	
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

		
//		System.setProperty("webdriver.ie.driver", "src/test/resources/IEDriverServer.exe");
//			capability=DesiredCapabilities.internetExplorer();
//			capability.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING,true);
//			
//		driver=new InternetExplorerDriver(capability);
		driver = DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());
		ATUReports.add("selected browser type", LogAs.PASSED, new CaptureScreen( ScreenshotOf.DESKTOP));

		//
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
		edit_recording_properties_window = PageFactory.initElements(driver, EditRecordingPropertiesWindow.class);
		
		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC18876ValidateTheSourceTypeAsRecordingChapterInSearchFieldOnTheAllCoursesLevel at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC18876ValidateTheSourceTypeAsRecordingChapterInSearchFieldOnTheAllCoursesLevel at " + DateToStr,
		 "Starting the test: TC18876ValidateTheSourceTypeAsRecordingChapterInSearchFieldOnTheAllCoursesLevel at " + DateToStr, LogAs.PASSED, null);
		
	}
	
	
	@AfterClass
	public void closeBroswer() {
		this.driver.quit();
	}

	
	// description = "get courses list"
	public void initializeCourseObject() throws InterruptedException {

		course = PageFactory.initElements(driver, CoursesHelperPage.class);
		course.courses = course.getStringFromElement(course.course_list);
	}

	
	@Test (description = "TC 18876 Validate The Source Type As Recording Chapter In Search Field On The All Courses Level")
	public void test18876() throws Exception
	{
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);

		// 1. Validate there is recording in this course. Search input specified shall be case-insensitive.
		tegrity.loginCourses("User1");
		initializeCourseObject();
		
		String current_course = course.selectCourseThatStartingWith("Ab");
		
		// Change first chapter name
		int recordNumber = record.checkExistenceOfNonEditRecordingsStatusInRecordings();
		record.selectIndexCheckBox(recordNumber);
		record.clickOnRecordingTaskThenEditRecording();
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyhhmmss");
		edit_recording.changeFirstChapterRecordingNameToTargetNameNew("NewChapterName" + sdf.format(date));
		
		for(String handler: driver.getWindowHandles()) {
			driver.switchTo().window(handler);
			break;
		}
		
		// Make course public
		record.clickOnCourseTaskThenCourseSettings();
		course_settings_page.makeSureThatMakeCoursePublicIsSelected();
		course_settings_page.clickOnOkButton();
	
		
		// Get Recording Chapter information.
		record.verifyFirstExpandableRecording();
		String recording_chapter = driver.findElement(By.cssSelector(".video-wrap")).getText().split("\n")[1];
		
		record.signOut();
	
		for(int type_of_user=0; type_of_user<3; type_of_user++) {
			if(type_of_user==0) {
				// 2. Log in as INSTRUCTOR.
				tegrity.loginCourses("User1");
			} else if (type_of_user==1) {
				// 2. Log in as Student.
				tegrity.loginCourses("User4");
			} else {
				// 2. Log in as anonymous.
				tegrity.loginAsguest();
			}
				
			// 3. Set the focus to the field with a mouse pointer.
			top_bar_helper.clickElementJS(top_bar_helper.search_box_field);
				
			// 4. Search the "Recording Chapter" that we mentioned in the preconditions and press ENTER.
			top_bar_helper.searchForTargetText(recording_chapter);
			Thread.sleep(2000);
			
			// 4.1. In case the search process takes a long time, the animated spinner icon shall be displayed within the Search results page.
			search_page.verifyLoadingSpinnerImage();
			search_page.waitUntilSpinnerImageDisappear();
				
			// 4.2. The breadcrumb structure displayed as follows: "Courses > X results found for: "search_criterion". (X seconds)".
			search_page.verfiyBreadcrumbStructureDisplayedAsCoursesXResultsFound(current_course, recording_chapter);
				
			// 4.3. The recording chapter icon is displayed.
			search_page.verifyWebElementDisplayed(search_page.video_thumbnails_list.get(0), "The recording capter icon");
				
			// 4.4. The respective recording chapter snapshot is displayed in the icon - Cannot be automated (TODO: check another way later).
			// 4.5. The start time of the chapter within recording is displayed over the chapter icon.
			search_page.verifyDurationTimeDisplayed();
				
			// 4.6. The course title in the format as follows: "Course: course_name.
			search_page.verifyDisplayCourseTitleForSearchInsideTargetCourse(current_course);
				
			// 4.7. The recording title in the format as follows: "Recording: recording_title.
			search_page.verifyRecordingTitleDisplayInCorrectFormat();
				
			// 4.8. The source title in the format as follows: " Source: Recording Chapter".
			search_page.verifyThatSourceTitleForTargetRecordingInTargetFormat(recording_chapter, "Source: Chapter");
				
			// 4.9. The next result display below the current result in case there is next result.
			search_page.verifyThatNextResultDisplayBelowCurrentResultInCaseThereIsNextResultAddicnalCont();
				
			// 5. Click on the chapter icon.
			search_page.clickOnChapterIconOfRecordingInTargetIndex(1);
				
			// 5.1. The Tegrity Player page is opened and the recording start playing from the chapter start time.
			player_page.verifyTimeBufferStatusForXSec(5);
				
			// 6. Click on the back cursor in the browser to navigate to the search results page.
			driver.navigate().back();
			search_page.waitUntilSpinnerImageDisappear();
			Thread.sleep(2000);
			search_page.exitInnerFrame();
			
			// 7. Click on title of the chapter.
			search_page.clickOnChapterTitleOfRecordingInTargetIndex(1);
				
			// 7.1. The Tegrity Player page is opened and the recording start playing from the chapter start time.
			player_page.verifyTimeBufferStatusForXSec(5);
				
			// 8. Click on the back cursor in the browser to navigate to the search results page.
			driver.navigate().back();
			search_page.waitUntilSpinnerImageDisappear();
			Thread.sleep(2000);
			search_page.exitInnerFrame();
				
			// 9. Click on the recording title of the chapter.
			search_page.clickOnRecordingTitleOfChapterOfRecordingInTargetIndex(1);
				
			// 9.1. The Tegrity player page with the opened recording at the relevant time.
			player_page.verifyTimeBufferStatusForXSec(5);
				
			// 10. Click on the back cursor in the browser to navigate to the search results page.
			driver.navigate().back();
			search_page.waitUntilSpinnerImageDisappear();
			Thread.sleep(2000);
			search_page.exitInnerFrame();
				
			// 11. Click on the course name in the breadcrumb.
			search_page.clickBackToCoursesInBreadcrumbs();
			
			// 12. Sign Out.
			record.signOut();
		}
		
		
		// Unpublic Ab course1. 
		tegrity.loginCourses("User1");
		course.selectCourseThatStartingWith("Ab");
								
		record.clickOnCourseTaskThenCourseSettings();
		course_settings_page.makeSureThatMakeCoursePublicIsUnSelected();
		course_settings_page.clickOnOkButton();

		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);

		
	}
}
