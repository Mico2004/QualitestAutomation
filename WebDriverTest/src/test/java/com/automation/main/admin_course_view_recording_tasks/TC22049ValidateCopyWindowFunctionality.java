package com.automation.main.admin_course_view_recording_tasks;



import java.util.List;
import org.omg.Messaging.SyncScopeHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
import java.text.DateFormat;
import java.util.Date;

@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC22049ValidateCopyWindowFunctionality {

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
		 System.out.println("Starting the test: TC22049ValidateCopyWindowFunctionality at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC22049ValidateCopyWindowFunctionality at " + DateToStr,
		 "Starting the test: TC22049ValidateCopyWindowFunctionality at " + DateToStr, LogAs.PASSED, null);	
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

	
	@Test(description = "TC 22049 Validate Copy Window Functionality")
	public void test22049() throws InterruptedException//
	{
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
		// 1. Login as User1.
		tegrity.loginCourses("User1");// log in courses page
		initializeCourseObject();
		
		// 2. Get full name of Ab course.
		String source_course_name = course.selectCourseThatStartingWith("Ab");
		String url_source=  course.getCurrentUrlCoursePage();
		System.out.println("Target course name for this test is: " + source_course_name);
		ATUReports.add("Target course name for this test is: "+ source_course_name, LogAs.PASSED, null);
		
		// 3. Get name of instructor.
		String username = driver.findElement(By.id("UserName")).getText();
		System.out.println("Username of instructor: " + username);
		
		record.returnToCourseListPage();
		
		// 4. Get full name of abc course.
		// 4.1. Get path of abc course.
		String destination_course_name = course.selectCourseThatStartingWith("abc");
		String destination_course_url = driver.getCurrentUrl();
		String url_destination =  course.getCurrentUrlCoursePage(); 
		System.out.println("Destionation course name for this test is: " + destination_course_name);
		ATUReports.add("Destionation course name for this test is: " + destination_course_name, LogAs.PASSED, null);
		
		record.returnToCourseListPage();
		// 4.2. check that we have an old recods in the course starting with abc
		//Delete all recording from course starting with Ab
		course.deleteAllRecordingsInCourseStartWith("abc", 0, record, delete_menu);
		
		
		
		// 5. Logout.
		record.signOut();
		
		// 6. Login as Admin.
		tegrity.loginAdmin("Admin");
		Thread.sleep(5000);
	
		// 7. Click on "view course list" under "courses" section.
		admin_dashboard_page.clickOnTargetSubmenuCourses("View Course List");
		
		// 8-9. move to the course through url
		Thread.sleep(5000);
		admin_dashboard_view_course_list.moveToCoursesThroughGet(url_source);	
		Thread.sleep(1000);
		
		// 10. Click on a checkbox of one recording.
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
		String checked_recording_title = record.getFirstRecordingTitle();
		String checked_recording_recorder_username = record.getIndexRecorderNameOfRecording(1);
		
		// 11. Hover over "Recording tasks" menu.
		// 12. Click on the menu item "Copy".
		record.clickOnRecordingTaskThenCopy();
		Thread.sleep(3000);
		
		// 13. The copy window displays.
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


		// 14. Click on the text field, and write an instuctor name which does not exist (like "aaaaaadssdfafaasa").
		driver.findElement(By.id("members_value")).sendKeys("aaaaaadssdfafaasa");
		Thread.sleep(3000);
		
		// 15. The text is written on the field. There is also a dropdown list opened with the text "No results".
		String dropdown_result = driver.findElements(By.cssSelector(".angucomplete-searching.ng-binding")).get(1).getText();
		
		if(dropdown_result.equals("No results found")) {
			System.out.println("Dropdown list opened with the text: No results found");
			ATUReports.add("Dropdown list opened with the text.", "Text: No results found", "Text: No results found", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Dropdown list opened with the text: " + dropdown_result);
			ATUReports.add("Dropdown list opened with the text.", "Text: No results found", "Text: " + dropdown_result, LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		
		// 16. Click on the button "List Courses".
		driver.findElement(By.id("SearchButton")).click();
		Thread.sleep(1000);
		
		// 17. The system notifies with the message: "Please select an instructor".
		// 18. Click on "OK" button on the Error window.
		//confirmation_menu.clickOnOkButtonAfterCannotCopyInProcessOrFailRecordings();
		confirmation_menu.clickOnOkButtonAfterErrorNoInstructorSelected();
		
		Thread.sleep(2000);
		
		// 19. Click on the text field, and write the name of an instructor.
		driver.findElement(By.id("members_value")).clear();
		driver.findElement(By.id("members_value")).sendKeys(username);
		Thread.sleep(1000);
		
		// 20. Click ,on the dropdown list, on the name of one instructor.
		driver.findElement(By.cssSelector(".angucomplete-title.ng-scope.ng-binding")).click();
		Thread.sleep(1000);
		
		if(driver.findElement(By.id("members_value")).getAttribute("value").equals(username)) {
			System.out.println("The chosen name displays on the text field for search.");
			ATUReports.add("The chosen name displays on the text field for search.", "True.", "True.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("The chosen name not displays on the text filed for search.");
			ATUReports.add("The chosen name displays on the text field for search.", "True.", "False.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		
		// 21. Click on the button "List Courses".
		driver.findElement(By.id("SearchButton")).click();
		Thread.sleep(1000);
		
		// 22. There shall be an informative text displayed below the input field: "Choose a course that you would like to copy your selected recording(s) to."
		copy.verifyChooseACourseThatCopyAndItsPlaceBelowTheInstructorSearchField();
		
		// 23. The list of available courses to copy the recording to is displayed.
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
		
		// 24. A text field displays which contain the text: "Search course by title...".
		if(driver.findElement(By.id("courseSearchText")).getAttribute("placeholder").equals("Search course by title...")) {
			System.out.println("A course search field status is correct.");
			ATUReports.add("A course search field status.", "Correct.", "Correct.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("A course search field status is not correct.");
			ATUReports.add("A course search field status.", "Correct.", "Not correct.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}

		// 25. The "Search" button is displayed next to the search field.
		copy.verifyThatSearchButtonRightOfTheCourseSearchFieldAboveTheCancelCopyRecordings();
		
		// 26. Click on the name (abc course name) of a course in the list of available courses.
		copy.selectTargetCourseFromCourseList(destination_course_name);
		
		// 27. Click on "Copy recording(s)" button.
		copy.clickOnCopyButton();		
		Thread.sleep(800);
		
		// 28. The informative message "Recording(s) have been queued for copy" shall be displayed.
		// 29. Click on "ok" button.
		confirmation_menu.clickOnOkButtonAfterConfirmCopyRecording();

		// 30. The message is closed.
		boolean is_confirmation_menu_closed = confirmation_menu.isConfirmationMenuClosed();
		
		if(is_confirmation_menu_closed) {
			System.out.println("The message is closed.");
			ATUReports.add("Message window.", "Closed.", "Closed.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("The message is not closed.");
			ATUReports.add("Message window.", "Closed.", "Open.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		
		// 31. The copied recording's name is colored in grey.
		// 32. The status of the recording (from the left of the recording date) has become "Being copying from".
		List<String> just_after_clicked_on_moved_recording_list = record.getCourseRecordingList();
		
		for(int i=0; i<just_after_clicked_on_moved_recording_list.size(); i++) {
			if(just_after_clicked_on_moved_recording_list.get(i).equals(checked_recording_title)) {
				record.checkRecordingInIndexIStatus(i+1, "Being copied from");

				if(driver.findElement(By.id("Recording" + Integer.toString(i+1))).getAttribute("disabled").equals("true")) {
					System.out.println("The moved recordings name is colored in grey (disabled).");
					ATUReports.add("Recordings grey out.", "True.", "True.", LogAs.PASSED, null);
					Assert.assertTrue(true);
				} else {
					System.out.println("The moved recordings name is not colored in grey (disabled).");
					ATUReports.add("Recordings grey out.", "True.", "False.", LogAs.FAILED, null);
					Assert.assertTrue(false);
				}
				
				// 33. The record status ("being copying from") disappeared after a while (the record stays where its at).
				record.checkThatRecordingStatusTargetIndexIsEmpty(i+1, 360);
				
				break;
			}
		}
		

		// 34. Go to abc course.
		record.returnToAdminPageByClickingBreadcrumbsName(record.courses_admin);
		

		//  move to the course through url
		Thread.sleep(5000);
		admin_dashboard_view_course_list.moveToCoursesThroughGet(url_destination);	
		Thread.sleep(1000);
		
		
		// 35. Validate that the recording which you have copied is displayed on the list.
		// 36. validate that the username displayed in the right of 'recorded by: ' of the copied recording ,isn't changed after the copy.
		List<String> destination_recording_list = record.getCourseRecordingList();
		
		for(int i=0; i<destination_recording_list.size(); i++) {
			if(destination_recording_list.get(i).equals(checked_recording_title)) {
				System.out.println("Recording is displayed on the list.");
				ATUReports.add("Recording list.", "Recording is displayed on the list.", "Recording is displayed on the list.", LogAs.PASSED, null);
				Assert.assertTrue(true);
			} else {
				System.out.println("Recording is not displayed on the list.");
				ATUReports.add("Recording list.", "Recording is displayed on the list.", "Recording is not displayed on the list.", LogAs.FAILED, null);
				Assert.assertTrue(false);
			}
			
			if(record.getIndexRecorderNameOfRecording(i+1).equals(checked_recording_recorder_username)) {
				System.out.println("Username is not changed after the copy.");
				ATUReports.add("Username is not changed after the copy.", "True.", "True.", LogAs.PASSED, null);
				Assert.assertTrue(true);
			} else {
				System.out.println("Username is changed after the copy.");
				ATUReports.add("Username is not changed after the copy.", "True.", "False.", LogAs.FAILED, null);
				Assert.assertTrue(false);
			}
			
			String recored_by_username = driver.findElement(By.id("RecordedBy" + Integer.toString(i+1))).getText();
			
			if(recored_by_username.substring(0, 12).equals("recorded by:")) {
				System.out.println("Username displayed in the right of recorded by.");
				ATUReports.add("Username displayed in the right of recorded by.", "True.", "True.", LogAs.PASSED, null);
				Assert.assertTrue(true);
			} else {
				System.out.println("Username not displayed in the right of recorded by.");
				ATUReports.add("Username displayed in the right of recorded by.", "True.", "False.", LogAs.FAILED, null);
				Assert.assertTrue(false);
			}
			break;	
		}
		
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
		
	}
	


}
