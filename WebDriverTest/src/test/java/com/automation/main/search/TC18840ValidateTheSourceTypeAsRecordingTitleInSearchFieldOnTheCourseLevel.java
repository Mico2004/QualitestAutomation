package com.automation.main.search;


import java.text.SimpleDateFormat;

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
public class TC18840ValidateTheSourceTypeAsRecordingTitleInSearchFieldOnTheCourseLevel {

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
		
		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC18840ValidateTheSourceTypeAsRecordingTitleInSearchFieldOnTheCourseLevel at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC18840ValidateTheSourceTypeAsRecordingTitleInSearchFieldOnTheCourseLevel at " + DateToStr,
		 "Starting the test: TC18840ValidateTheSourceTypeAsRecordingTitleInSearchFieldOnTheCourseLevel at " + DateToStr, LogAs.PASSED, null);
		
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

	
	@Test(description = "TC 18840 Validate TheSource Type As Recording Title In Search Field On The Course Level")
	public void test18840() throws Exception
	{
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);

		// 1. Validate there is recording title in this course. Search input specified shall be case-insensitive.
		tegrity.loginCourses("User1");
		initializeCourseObject();
		
		String current_course = course.selectCourseThatStartingWith("Ab");
		String url =  course.getCurrentUrlCoursePage(); 

		// Make course public
		record.clickOnCourseTaskThenCourseSettings();
		course_settings_page.makeSureThatMakeCoursePublicIsSelected();
		course_settings_page.clickOnOkButton();
		
		
		// Get 4 recording titles
		List<String> recording_list = record.getCourseRecordingList();
		int numberOfRecords = recording_list.size();
		
		//if we have last then 4 records we need to copy record from the bank
		if(numberOfRecords < 4) {
		
			record.signOut();
					
			tegrity.loginCourses("SuperUser");
						
			course.selectCourseThatStartingWith("BankValid");
			
			if(numberOfRecords <4 ) {		
				record.selectIndexCheckBox(1);
			}
			else if (numberOfRecords <3){
				record.selectIndexCheckBox(2);
			} else if (numberOfRecords <2){
				record.selectIndexCheckBox(3);
			}else if (numberOfRecords <1){
				record.selectIndexCheckBox(4);
			}
			  record.clickOnRecordingTaskThenCopy();		  
			  copy.selectTargetCourseFromCourseList(current_course);
			  copy.clickOnCopyButton();
			  
			  course.verifyRecordingsStatusIsClear("BankValidRecording",0,record);
			  record.returnToCourseListPage();
			  
			  course.selectCourseThatStartingWith("Ab");
			
		}
		int recordNumber = record.checkExistenceOfNonEditRecordingsStatusInRecordings();
		
       // change to unique records names before the test starts	
		for(int index = 0 ; index<=3 ; index++) {
			
			record.selectIndexCheckBox(recordNumber+index);
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyhhmmss"); 
			String new_recording_name = "NewName" + sdf.format(date);
			
			record.toEditRecordingPropertiesMenu();
			edit_recording_properties_window.changeRecordingName(new_recording_name, confirm_menu);	
			Thread.sleep(2000);
			record.unselectallCheckbox();
			
		}
		
        recording_list = record.getCourseRecordingList();
		
		String recording_for_instructor = recording_list.get(recordNumber-1);
		String recording_for_student = recording_list.get(recordNumber);
		String recording_for_guest = recording_list.get(recordNumber+1);
		String recording_for_admin = recording_list.get(recordNumber+2);
		
		record.signOut();
		
		// Looping for Student, Guest and ADMIN
		for(int type_of_user = 0; type_of_user < 4; type_of_user++) {
			String recording_title_for_the_test = null;
			
			if(type_of_user == 0) {
				// 2. Login as INSTRUCTOR.
				tegrity.loginCourses("User1");
				recording_title_for_the_test = recording_for_instructor;
			} else if (type_of_user == 1) {
				// 2. Login as Student.
				tegrity.loginCourses("User4");
				recording_title_for_the_test = recording_for_student;
			} else if (type_of_user == 2) {
				// 2. Login as guest
				tegrity.loginAsguest();
				recording_title_for_the_test = recording_for_guest;
			} else {
				// 2. Login as ADMIN
				tegrity.loginAdmin("Admin");
				recording_title_for_the_test = recording_for_admin;
			}
	
			if(type_of_user < 3) {
				// 3. Open some course.
				course.selectCourseThatStartingWith(current_course);
			} else {
				// Click on "view course list" under "courses" section.
				admin_dash_board_page.clickOnTargetSubmenuCourses("View Course List");	
				// In "All courses" page, search for Ab course.
				admin_dashboard_view_course_list.waitForThePageToLoad();
				admin_dashboard_view_course_list.moveToCoursesThroughGet(url);
			}
			
			
			// 4. Set the focus to the field with a mouse pointer.
			top_bar_helper.clickElementJS(top_bar_helper.search_box_field);
			
			// 5. Search the "Recording Title" that we mentioned in the preconditionsand press ENTER.
			top_bar_helper.searchForTargetText(recording_title_for_the_test);
			Thread.sleep(2000);
			
			// 5.1. In case the search process takes a long time, the animated spinner icon shall be displayed within the Search results page.
			search_page.verifyLoadingSpinnerImage();
			search_page.waitUntilSpinnerImageDisappear();
			
			// 5.2. The breadcrumb structure displayed as follows: "> Courses > Course name > X results found for: "search_criterion". (X seconds)".
			if(type_of_user < 3) {
				search_page.verfiyBreadcrumbStructureDisplayedAsCoursesCoursenameXresultsfound(current_course, recording_title_for_the_test);
			} else {
				search_page.verfiyBreadcrumbStructureDisplayedAsCoursesCoursenameXresultsfoundForAdminDashboard(current_course, recording_title_for_the_test);
			}
			
			// 5.3. The recording title icon is displayed.
			search_page.verifyWebElementDisplayed(search_page.video_thumbnails_list.get(0), "The recording capter icon");
			
			// 5.4. The respective recording chapter snapshot is displayed in the icon - Cannot be automated (TODO: check another way later).
			// 5.5. 00:00:00 is displayed over the chapter icon.
			search_page.verifyDurationTimeDisplayed();
			
			// 5.6. The course title in the format as follows: "Course: course_name.
			search_page.verifyDisplayCourseTitleForSearchInsideTargetCourse(current_course);
			
			// 5.7. The recording title in the format as follows: "Recording: recording_title.
			search_page.verifyRecordingTitleDisplayInCorrectFormat();
			
			// 5.8. The source title in the format as follows: " Source: Recording Title".
			search_page.verifyThatSourceTitleForTargetRecordingInTargetFormat(recording_title_for_the_test, "Source: Recording Title");
			
			// 5.9. The next result display below the current result in case there is next result.
			search_page.verifyThatNextResultDisplayBelowCurrentResultInCaseThereIsNextResultAddicnalCont();
			
			// 6. Hover over the chapter icon.
			Point before_hovring = search_page.video_wrap_link_to_focus_list.get(0).getLocation();
			search_page.moveToElementAndPerform(search_page.video_wrap_link_to_focus_list.get(0), driver);
			Thread.sleep(2000);
			
			// 6.1. The chapter icon become a bit bigger in size.
			Point after_hovring = search_page.video_wrap_link_to_focus_list.get(0).getLocation();

			if((after_hovring.x < before_hovring.x) && (after_hovring.y < before_hovring.y)) {
				System.out.println("Verifed that chapter icon become a bit bigger in size.");
				ATUReports.add("Verifed that chapter icon become a bit bigger in size.", "True.", "True.", LogAs.PASSED, null);
			} else {
				System.out.println("Not verifed that chapter icon become a bit bigger in size.");
				ATUReports.add("Verifed that chapter icon become a bit bigger in size.", "True.", "False.", LogAs.FAILED, null);
			}
			
			// 6.2. The ">" play icon is displayed over the chapter icon at the center.
			search_page.verifyWebElementDisplayed(driver.findElement(By.cssSelector(".play-button")), "The play icon");
			
			// 6.3. The recording name is displayed as a hint.
			String text_in_hint_with_recording = search_page.recording_titles_list.get(0).getText();
			String text_in_hint = text_in_hint_with_recording.substring("Recording: ".length());
			search_page.verifyWebElementHaveTargetAttributeTitle(search_page.video_wrap_link_to_focus_list.get(0), text_in_hint);
			
			// 7. Click on the recording title icon.
			search_page.clickOnChapterIconOfRecordingInTargetIndex(1);
			
			// 7.1. The Tegrity Player page is opened and the recording start playing from the beginning.
			player_page.verifyTimeBufferStatusForXSec(5);
			
			// 8. Click on the back cursor in the browser to navigate to the search results page.
			driver.navigate().back();
			search_page.waitUntilSpinnerImageDisappear();
			search_page.exitInnerFrame();
			Thread.sleep(2000);
			
			// 9. Click on title of the chapter.
			search_page.clickOnChapterTitleOfRecordingInTargetIndex(1);
			
			// 9.1. The Tegrity Player page is opened and the recording start playing from the beginning.
			player_page.verifyTimeBufferStatusForXSec(5);
			
			// 10. Click on the back cursor in the browser to navigate to the search results page.
			driver.navigate().back();
			search_page.waitUntilSpinnerImageDisappear();
			search_page.exitInnerFrame();
			Thread.sleep(2000);
			
			// 11. Click on the recording title of the chapter.
			search_page.clickOnRecordingTitleOfChapterOfRecordingInTargetIndex(1);
			
			// 11.1. The Tegrity player page with the opened recording at the relevant time.
			player_page.verifyTimeBufferStatusForXSec(5);
			
			// 12. Click on the back cursor in the browser to navigate to the search results page.
			driver.navigate().back();
			search_page.waitUntilSpinnerImageDisappear();
			search_page.exitInnerFrame();
			Thread.sleep(2000);
			
			if((type_of_user!=2) && (type_of_user!=1)) {
				// 13. Click on the course name in the breadcrumb.
				if(type_of_user!=3) {
					search_page.clickBackToCourseInBreadcrumbs();
				} else {
					search_page.clickBackToCourseInBreadcrumbsForAdminDashBoard();
				}
				Thread.sleep(2000);
				
				// 14. Change the name of the recording title that we mentioned in the preconditions.
				 Date date = new Date();
				 SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyhhmmss"); 
				 String new_recording_name = "NewName" + sdf.format(date);
				
				int recording_title_index = -1;
				List<String> current_recording_list = record.getCourseRecordingList();
				for(int i = 0; i < current_recording_list.size(); i++) {
					if(current_recording_list.get(i).equals(recording_title_for_the_test)) {
						recording_title_index = i + 1;
						break;
					}
				}
				
				record.selectIndexCheckBox(recording_title_index);
				record.toEditRecordingPropertiesMenu();
				edit_recording_properties_window.changeRecordingName(new_recording_name, confirm_menu);
				
				// 15. Search the recording title with the new name.
				top_bar_helper.searchForTargetText(new_recording_name);
				search_page.verifyLoadingSpinnerImage();
				search_page.waitUntilSpinnerImageDisappear();
				search_page.exitInnerFrame();
				// 15.1. The chapter is displayed with all the details that we mentioned.
				search_page.verifyResultContainOneResultWithTargetTitle(new_recording_name);
				
				
				// 16. Search the recording title with the old name.
				top_bar_helper.searchForTargetText(recording_title_for_the_test);
				search_page.verifyLoadingSpinnerImage();
				search_page.waitUntilSpinnerImageDisappear();
				search_page.exitInnerFrame();
				// 16.1. The chapter is'nt displayed and ,the empty search results page shall be displayed.
				search_page.verifySearchResultIsEmpty();
			}
			
			// 17. Sign Out.
			record.signOut();
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
