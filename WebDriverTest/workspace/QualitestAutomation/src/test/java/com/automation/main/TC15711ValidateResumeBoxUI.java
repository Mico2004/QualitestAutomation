package com.automation.main;


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
import java.text.DateFormat;
import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;
import java.util.Date;


@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC15711ValidateResumeBoxUI {

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
		
		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC15711ValidateResumeBoxUI at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC15711ValidateResumeBoxUI at " + DateToStr,
		 "Starting the test: TC15711ValidateResumeBoxUI at " + DateToStr, LogAs.PASSED, null);
		
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
		// Precondition
		tegrity.loginCourses("SuperUser");
		initializeCourseObject();
				
		// Copy one recording to Ba course
		course.deleteAllRecordingsInCourseStartWith("Ba", 0, record, delete_menu);
		course.copyOneRecordingFromCourseStartWithToCourseStartWithOfType("BankValid", "Ba", 0, record, copy, confirm_menu);
		
		// Logout.
		top_bar_helper.clickOnSignOut();
		Thread.sleep(Page.TIMEOUT_TINY);
		
		
		// 2. Login as an INSTRUCTOR.
		tegrity.loginCourses("User1");
		Thread.sleep(Page.TIMEOUT_TINY);
		
		// 3. Click on a certain course.
		String current_course = course.selectCourseThatStartingWith("Ba");
		
		// 4. Click on a certain recording.
		String first_recording_title = record.getFirstRecordingTitle();
		
		// 5. Click on one of the chapter and wait the player will start play the recording until you get to the second slide. 
		record.clickOnTargetRecordingAndOpenItsPlayback(first_recording_title);
		player_page.verifyTimeBufferStatusForXSec(3);
		player_page.clickElement(player_page.next_button);
		
		for(String handle: driver.getWindowHandles()) {
			driver.switchTo().window(handle);
			break;
		}
		
		// 6. Click on the " Breadcrumb" Course name link.
		player_page.returnToRecordingPageByNameAsUserOrGuest(current_course, record);
		
		
		String recording_init_background = record.getBackGroundColor(driver.findElement(By.cssSelector(".panel.item-list.ng-isolate-scope")));
		
		// 7. Click on the recording you just watched.
		record.clickElement(record.first_recording_title);
		
		// 7.1. The "> Resume watching +(slide 2)" box is displayed.
		Thread.sleep(Page.TIMEOUT_TINY);
		record.verifyWebElementTargetText(record.list_of_resume_buttons.get(0), "Resume Watching (slide 2)");

		// 7.2. The recording chapters are displayed to the user.
		// 7.3. The recording chapters are display sequentially.
		record.verifyThatRecordingChaptersAreDisplySequentially();
		
		// 7.4. The recording background turns to gray. 
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
		
		// 8. Hover over the slide box.
		record.moveToElement(record.list_of_resume_buttons.get(0), driver).perform();
		
		// 8.1. The box background changes to grey.
		if(record.getBackGroundColor(record.list_of_resume_buttons.get(0)).equals(before_click_background)) {
			System.out.println("Not verified that background color changes.");
			ATUReports.add("Background color changes.", "True.", "False.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		} else {
			System.out.println("Verfied that background color changes.");
			ATUReports.add("Background color changes.", "True.", "True.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		}
		
		// 9. Click the slide box link.
		record.list_of_resume_buttons.get(0).click();
		
		// 9.1. Redirect to player page.
		// 9.2. The player start playing the recording from the slide mentioned in the "Resume Watching" text box.
		player_page.verifyTimeBufferStatusForXSec(5);
		
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
	}
}
