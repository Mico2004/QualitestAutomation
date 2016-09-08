package com.automation.main;



import java.text.DateFormat;
import java.util.Date;
import org.eclipse.jetty.io.ClientConnectionFactory.Helper;
import org.omg.Messaging.SyncScopeHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
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
public class TC22007ValidateCopyWindowUI {

	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}

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
		wait = new WebDriverWait(driver, 30);
		

		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC22007ValidateCopyWindowUI at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC22007ValidateCopyWindowUI at " + DateToStr,
		 "Starting the test: TC22007ValidateCopyWindowUI at " + DateToStr, LogAs.PASSED, null);
		
	}
	
	@AfterClass
	public void closeBroswer() {
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
		
		// 2. Get the full name of the Ab course.
		String target_course_name = course.selectCourseThatStartingWith("Ab");
		String url =  course.getCurrentUrlCoursePage(); 
		System.out.println("Target course name for this test is: " + target_course_name);
		ATUReports.add("Target course name for this test is: "+ target_course_name, LogAs.PASSED, null);
		
		
		// Get username
		String username = driver.findElement(By.id("UserName")).getText();
		
		// 3. Logout.
		record.signOut();
		
			
		// 4. Login as Full Admin
		tegrity.loginAdmin("Admin");
		Thread.sleep(2000);
			
			
		// 5. Click on "view course list" under "courses" section.
		admin_dashboard_page.clickOnTargetSubmenuCourses("View Course List");
		
			
		// 6. move to the course through url
		Thread.sleep(5000);
		admin_dashboard_view_course_list.moveToCoursesThroughGet(url);	
		Thread.sleep(1000);
		
		// 8. Click on a checkbox of one recording.
		record.getCheckbox().click();
			
		// 9. Hover over "Recording tasks" menu.
		// 10. The menu items are displayed.
		// 11. Click on the menu item "copy".
		record.clickOnRecordingTaskThenCopy();
		Thread.sleep(3000);
		
		// 12. The copy window displays.
		boolean is_copy_window_closed = copy.isCopyMenuClosed();
		
		if(!is_copy_window_closed) {
			System.out.println("Copy window is opened.");
			ATUReports.add("Copy window.", "Open.", "Open.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Move window is closed.");
			ATUReports.add("Copy window.", "Open.", "Closed.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		
		
		// 13. Verify that window items are displayed as descripted.
		// 13.1. The window title is: "Copy".
		copy.verifyCopyMenuTitle();
	
		// 13.2. The window title background same as the color selected for university.
		copy.verifyMenuColor(record);
		
		// 13.3. There is a text field to search the instructor, and it contains the default text "Type Instructor Name...".
		copy.verifyThatAdminRecordingTasksMoveContainsDefaultTextTypeInstructorName();
		
		// 13.4. The "List Courses" button is displayed next to the search field.
		copy.verifyThatListCoursesButtonIsDisplayedNextToTheSearchFieldInAdminMoveRecordingWindow();
		
		// 13.5. The "Move Recording(s)" button is displayed at the bottom right of the window.
		// 13.6. The "Cancel" button is displayed left to the "Move Recording(s)" button.
		copy.verifyThatCancelButtonDisplayedLeftToMoveRecordingsButtonInAdminMoveRecordingWindow();
		
		// Fill in a name of an Instructor into the text field, pick that Instructor from the autocomplete dropdown & click on "List Courses"
		driver.findElement(By.id("members_value")).sendKeys(username);
		Thread.sleep(1000);
		driver.findElement(By.cssSelector(".angucomplete-title.ng-scope.ng-binding")).click();
		Thread.sleep(1000);
		driver.findElement(By.id("SearchButton")).click();
		Thread.sleep(1000);
		
		// A "Choose a course that you would like to move your selected recording(s) to" text below the Instructor's search field
		copy.verifyChooseACourseThatCopyAndItsPlaceBelowTheInstructorSearchField();
		
		// A course search field, below the course selection area with a "Search course by title ..." placeholder text
		
		if(driver.findElement(By.id("courseSearchText")).getAttribute("placeholder").equals("Search course by title...")) {
			System.out.println("A course search field status is correct.");
			ATUReports.add("A course search field status.", "Correct.", "Correct.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("A course search field status is not correct.");
			ATUReports.add("A course search field status.", "Correct.", "Not correct.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		
		// A "Search" button to the right of the course search field & above the "Cancel"/"Copy recording(s)"
		copy.verifyThatSearchButtonRightOfTheCourseSearchFieldAboveTheCancelCopyRecordings();
		
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);

		
	}
	
}
