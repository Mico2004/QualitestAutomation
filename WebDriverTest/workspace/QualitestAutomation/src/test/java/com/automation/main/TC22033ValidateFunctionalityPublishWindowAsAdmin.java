package com.automation.main;




import org.testng.annotations.AfterClass;
import org.openqa.selenium.WebDriver;import com.automation.main.page_helpers.Page;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import atu.testng.reports.utils.Utils;
import java.text.DateFormat;
import java.util.Date;

@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC22033ValidateFunctionalityPublishWindowAsAdmin {

	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}

	public PlayerPage player_page;
	public TopBarHelper top_bar_helper;
	public PublishWindow publish_window;
	public ConfirmationMenu confirmation_menu;
	public LoginHelperPage tegrity;
	public CoursesHelperPage course;
	public RecordingHelperPage record;
	public DeleteMenu delete_menu;
	public MoveWindow move_window;
	public AdminDashboardPage admin_dashboard_page;
	public AdminDashboardViewCourseList admin_dashboard_view_course_list;
	WebDriver driver;
	WebDriverWait wait;
	public static WebDriver thread_driver;
	CopyMenu copy;
	String currentCourse;
	String targetCourse;
	String clickedRecording;
    DesiredCapabilities capability;
	@BeforeClass
	public void setup() {


		driver = DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());
		//
		//ATUReports.setWebDriver(driver);
		//ATUReports.add("set driver", true);
		tegrity = PageFactory.initElements(driver, LoginHelperPage.class);

		record = PageFactory.initElements(driver, RecordingHelperPage.class);
		copy = PageFactory.initElements(driver, CopyMenu.class);
		delete_menu = PageFactory.initElements(driver, DeleteMenu.class);
		admin_dashboard_page = PageFactory.initElements(driver, AdminDashboardPage.class);
		admin_dashboard_view_course_list = PageFactory.initElements(driver, AdminDashboardViewCourseList.class);
		move_window = PageFactory.initElements(driver, MoveWindow.class);
		confirmation_menu = PageFactory.initElements(driver, ConfirmationMenu.class);
		publish_window = PageFactory.initElements(driver, PublishWindow.class);
		top_bar_helper = PageFactory.initElements(driver, TopBarHelper.class);
		player_page = PageFactory.initElements(driver, PlayerPage.class);
	
		wait = new WebDriverWait(driver, 30);
		
		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC22033ValidateFunctionalityPublishWindowAsAdmin at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC22033ValidateFunctionalityPublishWindowAsAdmin at " + DateToStr,
		 "Starting the test: TC22033ValidateFunctionalityPublishWindowAsAdmin at " + DateToStr, LogAs.PASSED, null);
	}
	
	@AfterClass
	public void quitBrowser() {
		driver.quit();
	}

	private void setAuthorInfoForReports() {
		ATUReports.setAuthorInfo("Qualitest Automation ", Utils.getCurrentTime(), "1.0");
	}

	private void setIndexPageDescription() {
		ATUReports.indexPageDescription = "Tests Results";
		
	}

	@Test
	public void testME() {
		setAuthorInfoForReports();
		setIndexPageDescription();
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
	public void loginCourses() throws InterruptedException//
	{
		
		// 1. Login as User1.
		tegrity.loginCourses("User1");// log in courses page
		initializeCourseObject();
		
		// 2. Get full name of Ab course.
		String source_course_name = course.selectCourseThatStartingWith("Ab");
		System.out.println("Source course name for this test is: " + source_course_name);
		ATUReports.add("Source course name for this test is: "+ source_course_name, LogAs.PASSED, null);
		
		// 3. Logout.
		record.signOut();
		
		
		// Repeat the TC as an HD admin
		for(int login_as=0; login_as<2; login_as++) {
			
			// 4. Login as Admin.
			if (login_as==0) {
				tegrity.loginAdmin("Admin");
				Thread.sleep(Page.TIMEOUT_TINY);
			} else {
				tegrity.loginAdmin("HelpdeskAdmin");
				Thread.sleep(Page.TIMEOUT_TINY);
			}
			
			// 5. Click on "view course list" under "courses" section.
			admin_dashboard_page.clickOnTargetSubmenuCourses("View Course List");
			
			// 6. In "All courses" page, search for Ab course.
			Thread.sleep(Page.TIMEOUT_TINY);
			admin_dashboard_view_course_list.searchForTargetCourseName(source_course_name);
			Thread.sleep(Page.TIMEOUT_TINY);
			
			// 7. Click on that name.
			admin_dashboard_view_course_list.clickOnFirstCourseLink();
			Thread.sleep(Page.TIMEOUT_TINY);
			
			// 8. Check the checkbox of an Published recording
			String checked_recording_name = record.selectRecordingWithOutTargetStatus("Not Published");
			
			// 	9. Choose the "Publish" option from the "Recording Tasks" drop-down menu.
			record.clickOnRecordingTaskThenPublish();
			
			// 10. The "Publish" window is displayed.
			publish_window.verifyPublishWindowOpen();
			
			// 11. The "Never" radio button is not selected.
			publish_window.verifyThatNeverOptionSelectedOrNotSelected(0);
			
			// 12. Choose the "Never" option.
			publish_window.never_option.click();
			
			// 13. The "Never" radio button is marked.
			publish_window.verifyThatNeverOptionSelectedOrNotSelected(1);
			
			// 14. Click on the "Save" button.
			publish_window.clickOnSaveButton();
			
			Thread.sleep(Page.TIMEOUT_TINY);
			
			// 15. The "Publish" window disappears.
			publish_window.verifyPublishWindowClosed();
			
			Thread.sleep(Page.TIMEOUT_TINY);
			
			// 16. The recording status changed to "Not Published".
			record.verifyTargetRecordingHaveTargetStatus(checked_recording_name, "Not Published");
			
			
			// 17. Open the recording Playback.
			// 18. The recording is playable.
			// 19. No Error is displayed.
			record.clickOnTargetRecordingAndOpenItsPlayback(checked_recording_name);
			player_page.verifyTimeBufferStatusForXSec(10);
			
//			driver.navigate().back();
//			Thread.sleep(Page.TIMEOUT_TINY);
			
			
			// 20. Sign out.
			top_bar_helper.exitInnerFrame();
			top_bar_helper.clickOnSignOut();
			
			// 21. Login as a different user who is enrolled as a STUDENT to the same course (User4).
			tegrity.loginCourses("User4");// log in courses page
			initializeCourseObject();
			
			// 22. Open the course that contains the uploaded recordings.
			course.selectCourseThatStartingWith("Ab");
			Thread.sleep(Page.TIMEOUT_TINY);
			
			// 23. Validate the recordings you changed earlier isn't displayed.
			record.verifyThatTargetRecordingNotExistInRecordingList(checked_recording_name);
			
			// 24. Sign out.
			top_bar_helper.clickOnSignOut();
			
			// 25. Login as a different user who is enrolled as a INSTRUCTOR to the same course.
			tegrity.loginCourses("User1");
			initializeCourseObject();
			
			// 26. Open the course that contains the changed recordings.
			course.selectCourseThatStartingWith("Ab");
			
			// 27. Validate the recording you changed earlier is displayed with "Not Published" label.
			record.verifyTargetRecordingHaveTargetStatus(checked_recording_name, "Not Published");
			
			// 28. Open the recording Playback & The recording is playable.
			// 29. No Error alert is displayed.
			record.clickOnTargetRecordingAndOpenItsPlayback(checked_recording_name);
			player_page.verifyTimeBufferStatusForXSec(10);
			
//			for(String handler: driver.getWindowHandles()) {
//				driver.switchTo().window(handler);
//				break;
//			}
			
			// 30. Logout.
			top_bar_helper.exitInnerFrame();
			top_bar_helper.clickOnSignOut();
			
		}

			System.out.println("Done.");
			ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
		
	}

}
