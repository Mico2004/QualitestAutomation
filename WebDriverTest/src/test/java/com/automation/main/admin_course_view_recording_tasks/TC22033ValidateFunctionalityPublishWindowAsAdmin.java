package com.automation.main.admin_course_view_recording_tasks;




import org.testng.annotations.AfterClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.automation.main.page_helpers.AdminDashboardPage;
import com.automation.main.page_helpers.AdminDashboardViewCourseList;
import com.automation.main.page_helpers.ConfirmationMenu;
import com.automation.main.page_helpers.CopyMenu;
import com.automation.main.page_helpers.CoursesHelperPage;
import com.automation.main.page_helpers.DeleteMenu;
import com.automation.main.page_helpers.LoginHelperPage;
import com.automation.main.page_helpers.MoveWindow;
import com.automation.main.page_helpers.PlayerPage;
import com.automation.main.page_helpers.PublishWindow;
import com.automation.main.page_helpers.RecordingHelperPage;
import com.automation.main.page_helpers.TopBarHelper;
import com.automation.main.utilities.DriverSelector;

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


	
	// description = "get courses list"
	public void initializeCourseObject() throws InterruptedException {

		course = PageFactory.initElements(driver, CoursesHelperPage.class);
		course.courses = course.getStringFromElement(course.course_list);
	}

	
	@Test(description = "TC 22033 Validate Functionality Publish Window As Admin")
	public void test22033() throws InterruptedException//
	{
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);

		// 1. Login as User1.
		tegrity.loginCourses("User1");// log in courses page
		initializeCourseObject();
		
		// 2. Get full name of Ab course.
		String source_course_name = course.selectCourseThatStartingWith("Ab");
		String url =  course.getCurrentUrlCoursePage(); 
		System.out.println("Source course name for this test is: " + source_course_name);
		ATUReports.add("Source course name for this test is: "+ source_course_name, LogAs.PASSED, null);
		
		// 3. Logout.
		record.signOut();
		
		
		// Repeat the TC as an HD admin
		for(int login_as=0; login_as<2; login_as++) {
			
			// 4. Login as Admin.
			if (login_as==0) {
				tegrity.loginAdmin("Admin");
				Thread.sleep(5000);
			} else {
				tegrity.loginAdmin("HelpdeskAdmin");
				Thread.sleep(5000);
			}
			
			// 5. Click on "view course list" under "courses" section.
			admin_dashboard_page.clickOnTargetSubmenuCourses("View Course List");
			
			// 6. move to the course through url
			Thread.sleep(5000);
			admin_dashboard_view_course_list.moveToCoursesThroughGet(url);	
			Thread.sleep(1000);

			
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
			
			Thread.sleep(1000);
			
			// 15. The "Publish" window disappears.
			publish_window.verifyPublishWindowClosed();
			
			Thread.sleep(1000);
			
			// 16. The recording status changed to "Not Published".
			record.verifyTargetRecordingHaveTargetStatus(checked_recording_name, "Not Published");
			
			
			// 17. Open the recording Playback.
			// 18. The recording is playable.
			// 19. No Error is displayed.
			record.clickOnTargetRecordingAndOpenItsPlayback(checked_recording_name);
			player_page.verifyTimeBufferStatusForXSec(10);
			
//			driver.navigate().back();
//			Thread.sleep(1000);
			
			
			// 20. Sign out.
			top_bar_helper.exitInnerFrame();
			top_bar_helper.signOut();
			
			// 21. Login as a different user who is enrolled as a STUDENT to the same course (User4).
			tegrity.loginCourses("User4");// log in courses page
			initializeCourseObject();
			
			// 22. Open the course that contains the uploaded recordings.
			course.selectCourseThatStartingWith("Ab");
			Thread.sleep(2000);
			
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
