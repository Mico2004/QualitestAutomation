package com.automation.main;


import java.util.Date;
import java.util.List;
import java.text.DateFormat;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;import com.automation.main.page_helpers.Page;
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
public class TC18859ValidateTheSourceTypeAsRecordingChapterInSearchFieldOnThePastCourseLevel {

	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}
	public ManageAdhocCoursesEnrollmentsPage mange_adhoc_course_enrollments;
	public ManageAdHocCoursesMembershipWindow mangage_adhoc_courses_membership_window;
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
		edit_recording_properties_window = PageFactory.initElements(driver, EditRecordinPropertiesWindow.class);
		
		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC18859ValidateTheSourceTypeAsRecordingChapterInSearchFieldOnThePastCourseLevel at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC18859ValidateTheSourceTypeAsRecordingChapterInSearchFieldOnThePastCourseLevel at " + DateToStr,
		 "Starting the test: TC18859ValidateTheSourceTypeAsRecordingChapterInSearchFieldOnThePastCourseLevel at " + DateToStr, LogAs.PASSED, null);	
		
	}
	
	
	@AfterClass
	//public void closeBroswer() {
	//	driver.quit();
	//}


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
		//pre test to unroll course from active courses to past courses 
		
		
		// 1. getting the name of the past course
		tegrity.loginCourses("User1");
		initializeCourseObject();
			
		String current_course = course.selectCourseThatStartingWith("PastCourseA");
		Thread.sleep(Page.TIMEOUT_TINY); 
		
		record.signOut();
		Thread.sleep(Page.TIMEOUT_TINY); 
			
		
		// 2. move course from the bank to the past courses 
		tegrity.loginCourses("SuperUser");
		Thread.sleep(Page.TIMEOUT_TINY); 
		
		//2.1 enter to the bank
		course.selectCourseThatStartingWith("BankValid");
		Thread.sleep(Page.TIMEOUT_TINY); 
		
		// 2.2 get to the student tab
		record.clickOnStudentRecordingsTab();
		Thread.sleep(Page.TIMEOUT_TINY); 
		
		// 2.3 select the first checkbox and enter to the copy menu
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
		record.clickOnRecordingTaskThenCopy();
		Thread.sleep(Page.TIMEOUT_TINY); 
		
		// 2.4 select the copy button and wait for the record to move
		copy.clickOnCopyButton();	
		record.checkStatusExistenceForMaxTTime(360);
		Thread.sleep(Page.TIMEOUT_TINY); 
		
		record.signOut();
		Thread.sleep(Page.TIMEOUT_TINY); 
		
		// 3. enter as admin and unroll the course 
		tegrity.loginAdmin("Admin");
		initializeCourseObject();
		 
		// 3.1 Click on course builder href link
		admin_dash_board_page.clickOnTargetSubmenuCourses("Manage Ad-hoc Courses / Enrollments (Course Builder)");
	 	Thread.sleep(Page.TIMEOUT_TINY);
	 		
	 	// 3.2 Click on create course href link 
	 	driver.switchTo().frame(0);
	 		
	 	// 3.3 Search target course name
	 	mange_adhoc_course_enrollments.searchAndFilterCourses(current_course);
	 	Thread.sleep(Page.TIMEOUT_TINY);
	 	
	 		
	 	// 3.4 Click on result first course (the only one) membership button
	 	mange_adhoc_course_enrollments.clickOnFirstCourseMembershipButton();
	 	Thread.sleep(Page.TIMEOUT_TINY);
	 	
	 	// 3.5 remove the instractour from the course 
	 	mangage_adhoc_courses_membership_window.selectIrUserFromUserList(mangage_adhoc_courses_membership_window.instructor_elements_list,"User1");
	 	System.out.println("removed instructor 1");
	 	Thread.sleep(Page.TIMEOUT_TINY);
	 		
	 	// 3.6 Add selected user to instructor list
	 	mangage_adhoc_courses_membership_window.clickOnRemoveSelectedUserToInstructorList();
	 	Thread.sleep(Page.TIMEOUT_TINY);   	
	 
	 	// 3.7 click on the ok button
	 	mangage_adhoc_courses_membership_window.ok_button.click();
	 	Thread.sleep(Page.TIMEOUT_TINY);
	 	    
	 	// 3.8 click on the alert
	 	driver.switchTo().alert().accept();
	 	Thread.sleep(Page.TIMEOUT_TINY);
	 	record.signOut();
		
	 	/// end pre test
		
		// 1. Validate there is recording in past courses Tab. Search input specified shall be case-insensitive.
		tegrity.loginCourses("User1");
		
		course.clickOnPastCoursesTabButton();
		Thread.sleep(Page.TIMEOUT_TINY);
		
		course.selectCourseThatStartingWith("PastCourseA");
		Thread.sleep(Page.TIMEOUT_TINY);
		
		// Get Recording Chapter information.
		record.verifyFirstExpandableRecording();
		//record.first_recording_title.click();
		Thread.sleep(Page.TIMEOUT_TINY);
		String recording_chapter = driver.findElement(By.cssSelector(".video-wrap")).getText().split("\n")[1];

		
		top_bar_helper.clickOnSignOut();
		Thread.sleep(Page.TIMEOUT_TINY);
		
		// 2. Log in as INSTRUCTOR.
		tegrity.loginCourses("User1");
		Thread.sleep(Page.TIMEOUT_TINY);
			
		// 3. Open some course from the past courses Tab.
		course.clickOnPastCoursesTabButton();
		Thread.sleep(Page.TIMEOUT_TINY);
		course.selectCourseThatStartingWith(current_course);
		Thread.sleep(Page.TIMEOUT_TINY);
			
		// 4. Set the focus to the field with a mouse pointer.
		top_bar_helper.search_box_field.click();
			
		// 5. Search the "Recording Chapter" that we mentioned in the preconditions and press ENTER.
		top_bar_helper.searchForTargetText(recording_chapter);
			
		// 5.1. In case the search process takes a long time, the animated spinner icon shall be displayed within the Search results page.
		search_page.verifyLoadingSpinnerImage();
		search_page.waitUntilSpinnerImageDisappear();
			
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
		driver.navigate().back();
		search_page.waitUntilSpinnerImageDisappear();
		Thread.sleep(Page.TIMEOUT_TINY);
			
		// 8. Click on title of the chapter.
		search_page.exitInnerFrame();
		search_page.clickOnChapterTitleOfRecordingInTargetIndex(1);
			
		// 8.1. The Tegrity Player page is opened and the recording start playing from the chapter start time.
		player_page.verifyTimeBufferStatusForXSec(10);
			
		// 9. Click on the back cursor in the browser to navigate to the search results page.
		driver.navigate().back();
		search_page.waitUntilSpinnerImageDisappear();
		Thread.sleep(Page.TIMEOUT_TINY);
			
		// 10. Click on the recording title of the chapter.
		search_page.exitInnerFrame();
		search_page.clickOnRecordingTitleOfChapterOfRecordingInTargetIndex(1);
			
		// 10.1. The Tegrity player page with the opened recording at the relevant time.
		player_page.verifyTimeBufferStatusForXSec(10);
			
		// 11. Click on the back cursor in the browser to navigate to the search results page.
		driver.navigate().back();
		search_page.waitUntilSpinnerImageDisappear();
		Thread.sleep(Page.TIMEOUT_TINY);
			
		// 12. Click on the course name in the breadcrumb.
		search_page.exitInnerFrame();
		search_page.clickBackToCourseInBreadcrumbs();
		
		// 13. Sign Out.
		top_bar_helper.clickOnSignOut();
		Thread.sleep(Page.TIMEOUT_TINY);

		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
		
	}
}
