package com.automation.main.admin_course_view_recording_tasks;



import java.util.List;
import java.text.DateFormat;
import java.util.Date;
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
import com.automation.main.page_helpers.RecordingHelperPage;
import com.automation.main.utilities.DriverSelector;

import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import atu.testng.reports.utils.Utils;


@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC24923ValidateThatYouCannotClickOnCopyRecordingsWhenYouHaventChoseACourseYet {

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

		wait = new WebDriverWait(driver, 30);
		
		Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC24923ValidateThatYouCannotClickOnCopyRecordingsWhenYouHaventChoseACourseYet at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC24923ValidateThatYouCannotClickOnCopyRecordingsWhenYouHaventChoseACourseYet at " + DateToStr,
		 "Starting the test: TC24923ValidateThatYouCannotClickOnCopyRecordingsWhenYouHaventChoseACourseYet at " + DateToStr, LogAs.PASSED, null);	
		
	}
	
	@AfterClass
	public void closeBroswer() {		
		this.driver.quit();
	}

	
	// description = "get courses list"
	public void initializeCourseObject() throws InterruptedException {

		course = PageFactory.initElements(driver, CoursesHelperPage.class);
		course.courses = course.getCoursesListFromElement(course.course_list);
	}

	@Test(description = "TC 24923 Validate That You Cannot Click On Copy Recordings When You Havent Chose A Course Yet")
	public void test24923() throws InterruptedException//
	{
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);

		// 1. Login as User1.
		tegrity.loginCourses("User1");// log in courses page
		initializeCourseObject();
		
		// 2. Get full name of Ab course.
		String source_course_name = course.selectCourseThatStartingWith("Ab");
		String url =  course.getCurrentUrlCoursePage(); 
		System.out.println("Target course name for this test is: " + source_course_name);
		ATUReports.add("Target course name for this test is: "+ source_course_name, LogAs.PASSED, null);
		
		// 3. Get name of instructor.
		String username = driver.findElement(By.id("UserName")).getText();
		System.out.println("Username of instructor: " + username);
		
		// 4. Logout.
		record.signOut();
		
		// 5. Login as Admin.
		tegrity.loginAdmin("Admin");
		Thread.sleep(5000);
	
		// 6. Click on "view course list" under "courses" section.
		admin_dashboard_page.clickOnTargetSubmenuCourses("View Course List");
		
		// 7-8. move to the course through url
		Thread.sleep(5000);
		admin_dashboard_view_course_list.moveToCoursesThroughGet(url);	
		Thread.sleep(1000);

		
		// 9. Click on a checkbox of one recording.
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
		
		// 10. Hover over "Recording tasks" menu.
		// 11. Click on the menu item "Copy".
		record.clickOnRecordingTaskThenCopy();
		Thread.sleep(3000);
		
		// 12. The copy window displays.
		boolean is_copy_window_closed = copy.isCopyMenuClosed();
		
		if(!is_copy_window_closed) {
			System.out.println("Copy window is opened.");
			ATUReports.add("Copy window.", "Open.", "Open.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Copy window is closed.");
			ATUReports.add("Copy window.", "Open.", "Closed.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		
		// 13. On the window, click on "Copy Recording(s)" button.
		copy.ClickOnCopyButtonWithoutChoosingCourse();
		Thread.sleep(1000);
		
		
		// 14. There is a error window displays with the text "a course must be selected".
		// 15. On the error window, click on "ok" button.
		confirmation_menu.clickOnOkButtonOnErrorWindow();
		
		
		// 16. Error window is closed.
		boolean is_closed = confirmation_menu.checkIfWindowModalWithTargetNameIsClosed("Error");
		
		if(is_closed) {
			System.out.println("Error window is closed.");
			ATUReports.add("Error window.", "Closed.", "Closed.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Error window is not closed.");
			ATUReports.add("Error window.", "Closed.", "Open.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		
		// 17. Click on the text field, and write the name an instructor.
		driver.findElement(By.id("members_value")).sendKeys(username);
		Thread.sleep(3000);
		
		// 18. The text is written on the field. There is also a dropdown list opened which contains several existing instructor which may match the search.
		String dropdown_result = driver.findElement(By.cssSelector(".angucomplete-title.ng-scope.ng-binding")).getText();
		
		if(dropdown_result.equals(username)) {
			System.out.println("Dropdown list opened with the text: " + username);
			ATUReports.add("Dropdown list opened with the text.", username, username, LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Dropdown list opened with the text: " + dropdown_result);
			ATUReports.add("Dropdown list opened with the text.", username, dropdown_result, LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		
		// 19. Click ,on the dropdown list, on the name of one instructor.
		driver.findElement(By.cssSelector(".angucomplete-title.ng-scope.ng-binding")).click();
		Thread.sleep(1000);
		
		// 20. The chosen name displays on the text field for search (near "list courses" button).
		String chosen_name = driver.findElement(By.id("members_value")).getAttribute("value");
		
		if(chosen_name.equals(username)) {
			System.out.println("Chosen name displays on the text field for search.");
			ATUReports.add("Chosen name displays on the text field for the search.", username, username, LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Chosen name not displays on the text field for search.");
			ATUReports.add("Chosen name displays on the text field for the search.", username, chosen_name, LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		
		// 21. Click on "Copy Recording(s)" button.
		copy.ClickOnCopyButtonWithoutChoosingCourse();
		
		Thread.sleep(1000);
		
		// 22. There is a error window displays with the text "a course must be selected".
		// 23. On the error window, click on "ok" button.
		confirmation_menu.clickOnOkButtonOnErrorWindow();
		
		Thread.sleep(1000);
		
		// 24. Error window is closed.
		is_closed = confirmation_menu.checkIfWindowModalWithTargetNameIsClosed("Error");
		
		if(is_closed) {
			System.out.println("Error window is closed.");
			ATUReports.add("Error window.", "Closed.", "Closed.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Error window is not closed.");
			ATUReports.add("Error window.", "Closed.", "Open.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		
		// 25. Click on "List courses" button.
		driver.findElement(By.id("SearchButton")).click();
		Thread.sleep(1000);

		// 26. The list of available courses to copy the recording to is displayed.
		List<String> courses_to_move = copy.getCourseList();
		
		if(courses_to_move.size() >= 0) {
			System.out.println("The list of available course to move the recording to is displayed.");
			ATUReports.add("The list of available courses.", "Is displayed.", "Is displayed.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("The list of available course to move the recording to is not displayed.");
			ATUReports.add("The list of available courses.", "Is displayed.", "Is not displayed.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		
		// 27. Click on "Copy Recording(s)" button.
		copy.ClickOnCopyButtonWithoutChoosingCourse();
		Thread.sleep(1000);
		
		// 28. There is a error window displays with the text "a course must be selected".
		// 29. On the error window, click on "ok" button.
		confirmation_menu.clickOnOkButtonOnErrorWindow();
		Thread.sleep(1000);
		
		// 30. Error window is closed.
		is_closed = confirmation_menu.checkIfWindowModalWithTargetNameIsClosed("Error");
		
		if(is_closed) {
			System.out.println("Error window is closed.");
			ATUReports.add("Error window.", "Closed.", "Closed.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Error window is not closed.");
			ATUReports.add("Error window.", "Closed.", "Open.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
		
	}
	


}
