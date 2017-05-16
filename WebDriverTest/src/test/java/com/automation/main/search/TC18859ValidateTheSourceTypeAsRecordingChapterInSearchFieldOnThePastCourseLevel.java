package com.automation.main.search;


import java.util.Date;
import java.text.DateFormat;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
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
import com.automation.main.page_helpers.ManageAdHocCoursesMembershipWindow;
import com.automation.main.page_helpers.ManageAdhocCoursesEnrollmentsPage;
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
import utils.WaitDriverUtility;

@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC18859ValidateTheSourceTypeAsRecordingChapterInSearchFieldOnThePastCourseLevel {

	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}
	public ManageAdhocCoursesEnrollmentsPage mange_adhoc_course_enrollments;
	public ManageAdHocCoursesMembershipWindow mangage_adhoc_courses_membership_window;
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
		mangage_adhoc_courses_membership_window = PageFactory.initElements(driver, ManageAdHocCoursesMembershipWindow.class);
		edit_recording = PageFactory.initElements(driver, EditRecording.class);
		mange_adhoc_course_enrollments = PageFactory.initElements(driver, ManageAdhocCoursesEnrollmentsPage.class);
		add_additional_content_link_window = PageFactory.initElements(driver, AddAdditionalContentLinkWindow.class);
		course_settings_page = PageFactory.initElements(driver, CourseSettingsPage.class);
		admin_dash_board_page = PageFactory.initElements(driver, AdminDashboardPage.class);
		admin_dashboard_view_course_list = PageFactory.initElements(driver, AdminDashboardViewCourseList.class);
		player_page = PageFactory.initElements(driver, PlayerPage.class);
		edit_recording_properties_window = PageFactory.initElements(driver, EditRecordingPropertiesWindow.class);
		
		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC18859ValidateTheSourceTypeAsRecordingChapterInSearchFieldOnThePastCourseLevel at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC18859ValidateTheSourceTypeAsRecordingChapterInSearchFieldOnThePastCourseLevel at " + DateToStr,
		 "Starting the test: TC18859ValidateTheSourceTypeAsRecordingChapterInSearchFieldOnThePastCourseLevel at " + DateToStr, LogAs.PASSED, null);	
		
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

	
	@Test(description = "TC 18859 Validate The Source Type As RecordingChapterInSearchFieldOnThePastCourseLevel")
	public void test18859() throws Exception
	{
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);

		//pre test to unroll course from active courses to past courses 
			
		// 1. getting the name of the past course
		tegrity.loginCourses("User1");
		initializeCourseObject();
		
		course.clickOnPastCoursesTabButton();
		
		String current_course = course.selectCourseThatStartingWith("PastCourseA");
		System.out.println("The course that selected is: " + current_course);
	 
		
		// 2. move course from the bank to the past courses 
		record.signOut();	
		
//		tegrity.loginCourses("SuperUser");
//
//		//2.1 enter to the bank
//		course.selectCourseThatStartingWith("BankValid");
//
//
//		// 2.2 get to the student tab
//		record.clickOnStudentRecordingsTab();
//
//
//		// 2.3 select the first checkbox and enter to the copy menu
//		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
//		record.clickOnRecordingTaskThenCopy();
//
//		copy.selectTargetCourseFromCourseList(current_course);
//		Thread.sleep(1000);
//
//		// 2.4 select the copy button and wait for the record to move
//		copy.clickOnCopyButton();
//		Thread.sleep(1000);
//
//		// 2.5 click on the ok button
//		confirm_menu.clickOnOkButtonAfterConfirmCopyRecording();
//
//		record.checkStatusExistenceForMaxTTime(360);
//
//		record.signOut();
//
	 	/// end pre test
		
		// 1. Validate there is recording in past courses Tab. Search input specified shall be case-insensitive.
		tegrity.loginCourses("User1");
		
		course.clickOnPastCoursesTabButton();
	
		course.selectCourseThatStartingWith("PastCourseA");
	
		record.clickOnStudentRecordingsTab();
	
		// Get Recording Chapter information.
		record.verifyFirstExpandableRecording();
		//record.first_recording_title.click();
		Thread.sleep(1000);
		String recording_chapter = driver.findElement(By.cssSelector(".video-wrap")).getText().split("\n")[1];

		
		top_bar_helper.signOut();
	
		
		// 2. Log in as INSTRUCTOR.
		tegrity.loginCourses("User1");
		
			
		// 3. Open some course from the past courses Tab.
		course.clickOnPastCoursesTabButton();
		
		course.selectCourseThatStartingWith(current_course);
		
		waitingForGetResults(recording_chapter);

		Thread.sleep(2000);

		// 5.2. The breadcrumb structure displayed as follows: "Courses > X results found for: "search_criterion". (X seconds)".
		search_page.verfiyBreadcrumbStructureDisplayedAsCoursesCoursenameXresultsfound(current_course, recording_chapter);
			
		// 5.3. The recording chapter icon is displayed.
		search_page.verifyWebElementDisplayed(search_page.video_thumbnails_list.get(0), "The recording capter icon");
			
		// 5.4. The respective recording chapter snapshot is displayed in the icon - Cannot be automated (TODO: check another way later).
		// 5.5. The start time of the chapter within recording is displayed over the chapter icon.
		search_page.verifyDurationTimeDisplayed();
			
		// 5.6. The course title in the format as follows: "Course: course_name.
		search_page.verifyDisplayCourseTitleForSearchInsideTargetCourse(current_course);
			
		// 5.7. The recording title in the format as follows: "Recording: recording_title.
		search_page.verifyRecordingTitleDisplayInCorrectFormat();
			
		// 5.8. The source title in the format as follows: " Source: Recording Chapter".
		search_page.verifyThatSourceTitleForTargetRecordingInTargetFormat(recording_chapter, "Source: Chapter");
			
		// 5.9. The next result display below the current result in case there is next result.
		search_page.verifyThatNextResultDisplayBelowCurrentResultInCaseThereIsNextResult();
			
		// 6. Click on the chapter icon.
		search_page.clickOnChapterIconOfRecordingInTargetIndex(1);
			
		// 6.1. The Tegrity Player page is opened and the recording start playing from the chapter start time.
		player_page.verifyTimeBufferStatusForXSec(5);
			
		// 7. Click on the back cursor in the browser to navigate to the search results page.
		goBackToPageResults();
//		search_page.waitUntilSpinnerImageDisappear();
//		Thread.sleep(2000);
//
		// 8. Click on title of the chapter.
		search_page.exitInnerFrame();
		search_page.clickOnChapterTitleOfRecordingInTargetIndex(1);
			
		// 8.1. The Tegrity Player page is opened and the recording start playing from the chapter start time.
		player_page.verifyTimeBufferStatusForXSec(10);
			
		// 9. Click on the back cursor in the browser to navigate to the search results page.
		goBackToPageResults();
//		search_page.waitUntilSpinnerImageDisappear();
//		Thread.sleep(2000);
//
		// 10. Click on the recording title of the chapter.
		search_page.exitInnerFrame();
		search_page.clickOnRecordingTitleOfChapterOfRecordingInTargetIndex(1);
			
		// 10.1. The Tegrity player page with the opened recording at the relevant time.
		player_page.verifyTimeBufferStatusForXSec(10);
			
		// 11. Click on the back cursor in the browser to navigate to the search results page.
		goBackToPageResults();
//		driver.navigate().back();
//		search_page.waitUntilSpinnerImageDisappear();
//		Thread.sleep(2000);
//
		// 12. Click on the course name in the breadcrumb.
		search_page.exitInnerFrame();
		search_page.clickBackToCourseInBreadcrumbs();
		
		// 13. Sign Out.
		record.signOut();
	
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
		
	}

	private void goBackToPageResults() {
		int timeOut = 30;
		driver.navigate().back();
		WaitDriverUtility.waitToPageBeLoaded(driver);
		try {
			boolean isDisplayed = search_page.checkSearchResultsDisplayed();
			WaitDriverUtility.sleepInSeconds(3);
			while (timeOut > 0||!isDisplayed){
				driver.navigate().forward();
				WaitDriverUtility.waitToPageBeLoaded(driver);
				driver.navigate().back();
				WaitDriverUtility.sleepInSeconds(6);
				isDisplayed = search_page.checkSearchResultsDisplayed();
				if (isDisplayed){
					return;
				}
				timeOut--;
			}
		} catch (InterruptedException e) {

		}
	}

	private void waitingForGetResults(String recording_chapter) {

		try {
			boolean isDisplayed = true;
			int timeOut =30;
			while (timeOut>0) {
				top_bar_helper.clickElementJS(top_bar_helper.search_box_field);

				// 5. Search the "Recording Chapter" that we mentioned in the preconditions and press ENTER.
				top_bar_helper.searchForTargetText(recording_chapter);
				Thread.sleep(3000);
				// 5.1. In case the search process takes a long time, the animated spinner icon shall be displayed within the Search results page.
				search_page.verifyLoadingSpinnerImage();
				System.out.println("Waiting for get results");
				isDisplayed = search_page.checkSearchResultsDisplayed();
				if (isDisplayed){
					break;
				}
				driver.navigate().back();
                Thread.sleep(1000);

				timeOut--;
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
