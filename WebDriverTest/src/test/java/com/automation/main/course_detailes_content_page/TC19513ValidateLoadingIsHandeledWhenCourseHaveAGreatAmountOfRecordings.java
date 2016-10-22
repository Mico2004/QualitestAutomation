package com.automation.main.course_detailes_content_page;


import java.util.Date;
import org.openqa.selenium.WebDriver;import com.automation.main.page_helpers.Page;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
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
import com.automation.main.page_helpers.EditRecordinPropertiesWindow;
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
public class TC19513ValidateLoadingIsHandeledWhenCourseHaveAGreatAmountOfRecordings {

	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}
	
	public DeleteMenu delete_menu;
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
		delete_menu = PageFactory.initElements(driver, DeleteMenu.class);
		
		
		wait = new WebDriverWait(driver, 120);
		
		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC19513ValidateLoadingIsHandeledWhenCourseHaveAGreatAmountOfRecordings at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC19513ValidateLoadingIsHandeledWhenCourseHaveAGreatAmountOfRecordings at " + DateToStr,
		 "Starting the test: TC19513ValidateLoadingIsHandeledWhenCourseHaveAGreatAmountOfRecordings at " + DateToStr, LogAs.PASSED, null);	
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

	
	@Test(description = "TC 19513 Validate Loading Is Handeled When Course Have A Great Amount Of Recordings")
	public void test19513() throws Exception
	{
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);

		// 1. Make sure to have a course with at least 200 Regular recordings (Every recording contain at least 5 chapters).
		tegrity.loginCourses("SuperUser");
		initializeCourseObject();
					
		course.copyOneRecordingFromCourseStartWithToCourseStartWithOfType("BankValid", "abc", 2, record, copy, confirm_menu);
		course.verifyRecordingsStatusIsClear("BankValidRecording", 2,record);
		course.selectCourseThatStartingWith("abc");
		int number_of_recording = record.getNumberOfRecordings();
		record.returnToCourseListPage();
		course.selectCourseThatStartingWith("BankValid");
		int number_of_recording_of_the_bank = record.getNumberOfRecordings();
		int numberOfRecordsToMove = 200 - number_of_recording;
		record.checkAllCheckBox();
		
		for(int i=0; i<= (numberOfRecordsToMove/number_of_recording_of_the_bank); i++) {
			record.clickOnRecordingTaskThenCopy();
			copy.selectTargetCourseFromCourseListThatStartWith("abc");
			copy.clickOnCopyButton();
			Thread.sleep(Page.TIMEOUT_TINY);
			confirm_menu.clickOnOkButtonAfterConfirmCopyRecordings();
			Thread.sleep(Page.TIMEOUT_TINY);
		}
		
		record.checkStatusExistenceForMaxTTime(600);
		
		// 2. Login as INSTRUCTOR.
		top_bar_helper.signOut();
		Thread.sleep(Page.TIMEOUT_TINY);
		tegrity.loginCourses("User1");
		Thread.sleep(Page.TIMEOUT_TINY);
		
		// 3. Enter to the predefined course.
		course.selectCourseThatStartingWith("abc");
		
		// 3.1. The loading event is handeled properly (spinning loading symbol for example).
		wait.until(ExpectedConditions.visibilityOf(record.first_recording_title));
		
		// 3.2. The recordings are displayed and clickable.
		record.verifyPossibleToScrollTheRecordingList();
		
		// 4. Click on another Tab (Student, Additional Recordings etc).
		record.clickOnStudentRecordingsTab();
		
//		int counter = 0;
//		while(true) {
//			counter++;
//			String student_tab_color = record.getBackGroundColor(record.student_recordings_tab);
//			if (counter == 60) {
//				break;
//			} else if(student_tab_color.equals("#ffffff")) {
//				Thread.sleep(Page.TIMEOUT_TINY);
//				continue;
//			} else {
//				break;
//			}
//		}
		Thread.sleep(Page.TIMEOUT_TINY);
		
//		System.out.println(record.getBackGroundColor(record.recordings_tab));
		
		// 5. Return to the "Regular Recordings" tab.
		record.clickOnRecordingsTab();
		
//		counter = 0;
//		while(true) {
//			counter++;
//			String recording_tab = record.getBackGroundColor(record.recordings_tab);
//			if (counter == 60) {
//				break;
//			} else if(recording_tab.equals("#ffffff")) {
//				Thread.sleep(Page.TIMEOUT_TINY);
//				continue;
//			} else {
//				break;
//			}
//		}
		Thread.sleep(Page.TIMEOUT_TINY);
		
		// 5.1. The loading event is handeled properly (spinning loading symbol for example).
		wait.until(ExpectedConditions.visibilityOf(record.first_recording_title));
		
		// 5.2. The recordings are displayed and clickable.
		record.verifyPossibleToScrollTheRecordingList();
		
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);

	}
}
