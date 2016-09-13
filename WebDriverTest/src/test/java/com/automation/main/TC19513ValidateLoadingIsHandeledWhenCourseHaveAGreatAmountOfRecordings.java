package com.automation.main;


import java.util.Date;
import org.openqa.selenium.WebDriver;
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
		
		// 1. Make sure to have a course with at least 200 Regular recordings (Every recording contain at least 5 chapters).
		tegrity.loginCourses("SuperUser");
		initializeCourseObject();
		
		course.copyOneRecordingFromCourseStartWithToCourseStartWithOfType("BankValid", "abc", 2, record, copy, confirm_menu);
		course.verifyRecordingsStatusIsClear("BankValidRecording", 2,record);
		course.selectCourseThatStartingWith("BankValid");
		int number_of_recording = record.getNumberOfRecordings();
		
		record.checkAllCheckBox();
		
		for(int i=0; i<= (200/number_of_recording); i++) {
			record.clickOnRecordingTaskThenCopy();
			copy.selectTargetCourseFromCourseListThatStartWith("abc");
			copy.clickOnCopyButton();
			Thread.sleep(2000);
			confirm_menu.clickOnOkButtonAfterConfirmCopyRecordings();
			Thread.sleep(2000);
		}
		
		record.checkStatusExistenceForMaxTTime(600);
		
		// 2. Login as INSTRUCTOR.
		top_bar_helper.clickOnSignOut();
		Thread.sleep(1000);
		tegrity.loginCourses("User1");
		Thread.sleep(1000);
		
		// 3. Enter to the predefined course.
		course.selectCourseThatStartingWith("abc");
		
		// 3.1. The loading event is handeled properly (spinning loading symbol for example).
		wait.until(ExpectedConditions.visibilityOf(record.first_recording_title));
		
//		System.out.println(record.getBackGroundColor(record.recordings_tab));
		
		// 3.2. The recordings are displayed and clickable.
		record.verifyPossibleToScrollTheRecordingList();
		
		// 4. Click on another Tab (Student, Additional Recordings etc).
		String first_recording_title = record.getFirstRecordingTitle();
		record.clickOnStudentRecordingsTab();
		
//		int counter = 0;
//		while(true) {
//			counter++;
//			String student_tab_color = record.getBackGroundColor(record.student_recordings_tab);
//			if (counter == 60) {
//				break;
//			} else if(student_tab_color.equals("#ffffff")) {
//				Thread.sleep(1000);
//				continue;
//			} else {
//				break;
//			}
//		}
		Thread.sleep(10000);
		
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
//				Thread.sleep(1000);
//				continue;
//			} else {
//				break;
//			}
//		}
		Thread.sleep(10000);
		
		// 5.1. The loading event is handeled properly (spinning loading symbol for example).
		wait.until(ExpectedConditions.visibilityOf(record.first_recording_title));
		
		// 5.2. The recordings are displayed and clickable.
		record.verifyPossibleToScrollTheRecordingList();
		
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);

	}
}
