package com.automation.main;



import java.util.List;
import java.text.DateFormat;
import java.util.Date;
import org.testng.annotations.AfterClass;
import org.openqa.selenium.WebDriver;import com.automation.main.page_helpers.Page;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import atu.testng.reports.utils.Utils;


@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC22034ValidateChangeRecordingNameFunctionalityAsAdmin {

	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}

	
	public ConfirmationMenu confirmation_menu;
	public LoginHelperPage tegrity;
	public CoursesHelperPage course;
	public RecordingHelperPage record;
	public DeleteMenu delete_menu;
	public MoveWindow move_window;
	public AdminDashboardPage admin_dashboard_page;
	public EditRecordinPropertiesWindow edit_recording_properties_window;
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
		edit_recording_properties_window = PageFactory.initElements(driver, EditRecordinPropertiesWindow.class);
		wait = new WebDriverWait(driver, 30);
		
		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC22034ValidateChangeRecordingNameFunctionalityAsAdmin at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC22034ValidateChangeRecordingNameFunctionalityAsAdmin at " + DateToStr,
		 "Starting the test: TC22034ValidateChangeRecordingNameFunctionalityAsAdmin at " + DateToStr, LogAs.PASSED, null);
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
		
		// 1. Login with User1.
		tegrity.loginCourses("User1");// log in courses page
		initializeCourseObject();
		
		// 2. Get full name of Ab course.
		String source_course_name = course.selectCourseThatStartingWith("Ab");
		System.out.println("Target course name for this test is: " + source_course_name);
		ATUReports.add("Target course name for this test is: "+ source_course_name, LogAs.PASSED, null);
		
		// 3. Logout.
		record.signOut();
		
		// 4. Login as Admin.
		tegrity.loginAdmin("Admin");
		Thread.sleep(Page.TIMEOUT_TINY);
	
		// 5. Click on "view course list" under "courses" section.
		admin_dashboard_page.clickOnTargetSubmenuCourses("View Course List");
		
		// 6. In "All courses" page, search for Ab course.
		Thread.sleep(Page.TIMEOUT_TINY);
		admin_dashboard_view_course_list.searchForTargetCourseName(source_course_name);
		Thread.sleep(Page.TIMEOUT_TINY);
		
		// 7. Click on that course name.
		admin_dashboard_view_course_list.clickOnFirstCourseLink();
		Thread.sleep(Page.TIMEOUT_TINY);
		
		// 8. Click on a checkbox of one recording.
		record.selectIndexCheckBox(1);
		String checked_recording_title = record.getFirstRecordingTitle();
		
		// 10. The "Edit recording properties" option is enabled.
		if(record.isEditRecordingPropertiesDisableOrEnable()) {
			System.out.println("Edit recording properties option is enabled.");
			ATUReports.add("Edit recording properties option.", "Enabled.", "Enabled.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Edit recording properties option is disabled.");
			ATUReports.add("Edit recording properies option.", "Enabled.", "Disabled.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		Thread.sleep(Page.TIMEOUT_TINY);
		
		// 9. Click on the "Recording Tasks" drop-down button.
		// 11. Click on the "Edit Recording properties".
		record.toEditRecordingPropertiesMenu();
		Thread.sleep(Page.TIMEOUT_TINY);
		
		// 12. The "Edit recording properties" modal window is displayed.
		boolean is_closed = edit_recording_properties_window.isEditRecordingProperiesClosed();
		
		if(!is_closed) {
			System.out.println("Edit recording proerties modal is displayed.");
			ATUReports.add("Edit recording properties modal window.", "Open.", "Open.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Edit recording proerties modal is not displayed.");
			ATUReports.add("Edit recording properties modal window.", "Open.", "Close.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		Thread.sleep(Page.TIMEOUT_TINY);
		
		// 13. Click inside of the "Name" text box & type the text "Change recording name"
		String change_to_name = "Change recording name";
		edit_recording_properties_window.changeRecordingNameToTargetName(change_to_name);
		Thread.sleep(Page.TIMEOUT_TINY);
		
		// 14. Click the "Save" button.
		edit_recording_properties_window.clickOnSaveButton();
		Thread.sleep(Page.TIMEOUT_TINY);
		
		// 15. Click the "Ok" button.
		confirmation_menu.clickOnOkButton();
		Thread.sleep(Page.TIMEOUT_TINY);
		
		// 16. The modal window is closed.
		is_closed = edit_recording_properties_window.isEditRecordingProperiesClosed();
		
		if(is_closed) {
			System.out.println("Edit recording proerties modal is not displayed.");
			ATUReports.add("Edit recording properties modal window.", "Open.", "Close.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Edit recording proerties modal is displayed.");
			ATUReports.add("Edit recording properties modal window.", "Open.", "Open.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		Thread.sleep(Page.TIMEOUT_TINY);
		
		// 17. The confirmation window disappears.
		is_closed = confirmation_menu.isConfirmationMenuClosed();
		
		if(is_closed) {
			System.out.println("The confirmation window is not displayed.");
			ATUReports.add("The confirmation window.", "Open.", "Close.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("The confirmation window is displayed.");
			ATUReports.add("The confirmation window.", "Open.", "Open.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		Thread.sleep(Page.TIMEOUT_TINY);
		
		// 18. Validate the recording name has changed to "Change recording name".
		List<String> recording_list = record.getCourseRecordingList(); 
		
		if(recording_list.contains(change_to_name)) {
			System.out.println("Recording name has changed to: " + change_to_name);
			ATUReports.add("Recording name has changed to: " + change_to_name, "True.", "True.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Recording name has changed to: " + change_to_name);
			ATUReports.add("Recording name has changed to: " + change_to_name, "True.", "False.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
		
	}
	


}
