package com.automation.main.course_detailes_content_page;


import java.util.Date;
import java.util.List;
import javax.swing.ListModel;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
public class TC17041ValidateResumeBoxWhenRecordingIsPaused {

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
		 System.out.println("Starting the test: TC17041ValidateResumeBoxWhenRecordingIsPaused at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC17041ValidateResumeBoxWhenRecordingIsPaused at " + DateToStr,
		 "Starting the test: TC17041ValidateResumeBoxWhenRecordingIsPaused at " + DateToStr, LogAs.PASSED, null);	
		
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

	
	@Test(description = "TC 17041 Validate Resume Box When Recording Is Paused")
	public void test17041() throws Exception
	{
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);

		// 1. Make sure that the STUDENT and INSTRUCTOR users you are using never watched the recording used in this test case.
		tegrity.loginCourses("SuperUser");
		initializeCourseObject();
		
		
		String current_course = course.selectCourseThatStartingWith("abc");
		record.returnToCourseListPage();
		course.deleteAllRecordingsInCourseStartWith("abc", 0, record, delete_menu);
		course.copyOneRecordingFromCourseStartWithToCourseStartWithOfType("BankValid", "abc", 0, record, copy, confirm_menu);
		course.verifyRecordingsStatusIsClear("BankValidRecording", 0,record);
		record.signOut();
		
		
		// 2. Repeat for INSTRUCTOR and STUDENT.
		for(int type_of_user=0; type_of_user<2; type_of_user++) {
			if(type_of_user==0) {
				// 3. Login as an INSTRUCTOR/STUDENT.
				tegrity.loginCourses("User1");	
			} else {
				// 3. Login as an INSTRUCTOR/STUDENT.
				tegrity.loginCourses("User4");
			}
				
			// 4. Click on a certain course.
			course.selectCourseThatStartingWith(current_course);
		
			
			// 5. Click on the first chapter and wait the player will start play the recording
			String first_recording_name = record.getFirstRecordingTitle();
			record.clickOnTargetRecordingAndOpenItsPlayback(first_recording_name);
			
			// 6. Click on the first chapter and wait the player will start to play.
			player_page.verifyTimeBufferStatusForXSec(3);
			player_page.exitInnerFrame();
					
			// 7. Navigate back with the browser back button.
			driver.navigate().back();
			record.waitForThePageToLoad();
			
			WebElement we = driver.findElement(By.cssSelector(".panel.item-list.ng-isolate-scope"));
			String recording_init_background = record.getBackGroundColor(we);
			
			// 8. Click on the recording you just watched.
			record.verifyFirstExpandableRecording();
		
			// 8.1. The "> Resume watching + (slide #)" box is displayed.
			record.verifyWebElementDisplayed(record.list_of_resume_buttons.get(0), "Resume box");
			
			// 8.2. The recording chapters are displayed to the user.
			// 8.3. The recording chapters are display sequentially.
			record.verifyThatRecordingChaptersAreDisplySequentially();
			
			// 8.4. The recording background turns to gray. 
			if(record.getBackGroundColor(driver.findElement(By.cssSelector(".panel.item-list.ng-isolate-scope.activeItem.selected-container"))).equals(recording_init_background)) {
				System.out.println("Not verified that background color changes.");
				ATUReports.add("Background color changes.", "True.", "False.", LogAs.FAILED, null);
				Assert.assertTrue(false);
			} else {
				System.out.println("Verfied that background color changes.");
				ATUReports.add("Background color changes.", "True.", "True.", LogAs.PASSED, null);
				Assert.assertTrue(true);
			}
						
			String before_click_background = record.getBackGroundColor(record.list_of_resume_buttons.get(0));
			
			// 9. Hover over the slide box.
			record.moveToElementAndPerform(record.list_of_resume_buttons.get(0), driver);
						
			// 9.1. The box background changes to grey.
			if(record.getBackGroundColor(record.list_of_resume_buttons.get(0)).equals(before_click_background)) {
				System.out.println("Not verified that background color changes.");
				ATUReports.add("Background color changes.", "True.", "False.", LogAs.FAILED, null);
				Assert.assertTrue(false);
			} else {
				System.out.println("Verfied that background color changes.");
				ATUReports.add("Background color changes.", "True.", "True.", LogAs.PASSED, null);
				Assert.assertTrue(true);
			}
			
			// 10. Click the slide box link.
			record.clickElementWithOutIdJS(record.list_of_resume_buttons.get(0));					
			// 10.1. Redirect to player page.
			// 10.2. The player start playing the recording from the last slide mentioned in the "Resume Watching" text box.
			player_page.verifyTimeBufferStatusForXSec(5);
			player_page.exitInnerFrame();
			record.signOut();
		}
		

		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);

		
	}
}
