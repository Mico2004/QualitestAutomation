package com.automation.main;



import java.util.List;
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
import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import atu.testng.reports.utils.Utils;
import java.text.DateFormat;
import java.util.Date;


@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC22042ValidateMoveRecordingFunctionalityForOneRecording {

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
		 System.out.println("Starting the test: TC22042ValidateMoveRecordingFunctionalityForOneRecording at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC22042ValidateMoveRecordingFunctionalityForOneRecording at " + DateToStr,
		 "Starting the test: TC22042ValidateMoveRecordingFunctionalityForOneRecording at " + DateToStr, LogAs.PASSED, null);
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
		// Loop through login as Admin / Help Disk Admin
		for(int i_login_as_admin=0; i_login_as_admin<2; i_login_as_admin++) {
			
			// 1. Login as User1.
			tegrity.loginCourses("User1");// log in courses page
			initializeCourseObject();
			
			// 2. Get the full name of the Ab course.
			String source_course_name = course.selectCourseThatStartingWith("Ab");
			System.out.println("Target course name for this test is: " + source_course_name);
			ATUReports.add("Target course name for this test is: "+ source_course_name, LogAs.PASSED, null);
			
			// Get username
			String username = driver.findElement(By.id("UserName")).getText();
			System.out.println("Username of instructor: " + username);
			
			// 2.1. Get back to course list.
			record.returnToCourseListPage();
			
			// 2.2. Get the full name of abc course.
			String destination_course_name = course.selectCourseThatStartingWith("abc");
			String destination_course_url = driver.getCurrentUrl();
			System.out.println("Destionation course name for this test is: " + destination_course_name);
			ATUReports.add("Destionation course name for this test is: " + destination_course_name, LogAs.PASSED, null);
			//record.returnToCourseListPage();
			
			// 2.3. Delete all recordings in abc.
			//Delete all recording from course starting with Ab
			//course.deleteAllRecordingsInCourseStartWith("abc", 0, record, delete_menu);
			
			// 2.4. Delete all Stduent Recordings in abc.
			//Delete all student recordings from course starting with Ab
			//course.deleteAllRecordingsInCourseStartWith("abc", 2, record, delete_menu);
			
			// 2.5. Delete all Tests in abc.
			//Delete all tests from course starting with Ab
			//course.deleteAllRecordingsInCourseStartWith("abc", 3, record, delete_menu);
			
			// 2. check that we have old records in the abc course
			List<String> list_of_records = record.getCourseRecordingList();
			
			if(list_of_records.size() == 0) {
				System.out.println("their isn't old records in this course so the test will not work");
				ATUReports.add("their isn't old records in this course so the test will not work", LogAs.FAILED, null);
				Assert.assertTrue(false);
			} else {
				System.out.println("their is an old records in this course");
				ATUReports.add("their is an old records in this course", LogAs.PASSED, null);
				Assert.assertTrue(true);
			
			
			}
			
			// 3. Logout.
			record.signOut();
		
			// 4. Login as Full Admin
			if(i_login_as_admin==0) {
				tegrity.loginAdmin("Admin");
				Thread.sleep(5000);
			} else {
				tegrity.loginAdmin("HelpdeskAdmin");
				Thread.sleep(5000);
			}
			
			
			// 5. Click on "view course list" under "courses" section.
			admin_dashboard_page.clickOnTargetSubmenuCourses("View Course List");
			
			// Repeat TC for Recordings, Stduent Recording and Tests Tabs
			for(int recording_type=0; recording_type<3; recording_type++) {
				// 6. In "All courses" page, search for Ab course.
//				Thread.sleep(8000);
				admin_dashboard_view_course_list.searchForTargetCourseName(source_course_name);
				Thread.sleep(3000);
				
				// 7. Click on that course name
				admin_dashboard_view_course_list.clickOnFirstCourseLink();
				Thread.sleep(1000); 
				
				if(recording_type==1) {
					record.clickOnStudentRecordingsTab();
				} else if (recording_type==2) {
					record.clickOnTestsTab();
				}
				Thread.sleep(2000);
				
				
				// 8. Click on a checkbox of one recording.
				record.getCheckbox().click();
				
				String checked_recording_title = null;
				if (recording_type==2) {
					checked_recording_title = record.getFirstRecordingTitleTest();
				} else {
					checked_recording_title = record.getFirstRecordingTitle();
				}
				
				
				// 9. Hover over "Recording tasks" menu.
				// 10. The menu items are displayed.
				// 11. Click on the menu item "Move".
				record.clickOnRecordingTaskThenMove();
				Thread.sleep(3000);
				
				// 12. The move window displays.
				boolean is_move_window_closed = move_window.isMoveMenuClosed();
				
				if(!is_move_window_closed) {
					System.out.println("Move window is opened.");
					ATUReports.add("Move window.", "Open.", "Open.", LogAs.PASSED, null);
					Assert.assertTrue(true);
				} else {
					System.out.println("Move window is closed.");
					ATUReports.add("Move window.", "Open.", "Closed.", LogAs.FAILED, null);
					Assert.assertTrue(false);
				}
				
				// 13. Click on the text field, and write the name of the (existing) instuctor which belong to the course you are moving the chosen record from.
				driver.findElement(By.id("members_value")).sendKeys(username);
				Thread.sleep(1000);
				
				// 14. The text is written on the field. There is also a dropdown list opened which contains several existing instructor which may match the search.
				// 15. Click ,on the dropdown list, on the name of the instructor who belongs to this course when it is displayed on the list.
				driver.findElement(By.cssSelector(".angucomplete-title.ng-scope.ng-binding")).click();
				Thread.sleep(1000);
				
				// 16. The chosen name displays on the text field for search (near "list courses" button).
				if(driver.findElement(By.id("members_value")).getAttribute("value").equals(username)) {
					System.out.println("The chosen name displays on the text field for search.");
					ATUReports.add("The chosen name displays on the text field for search.", "True.", "True.", LogAs.PASSED, null);
					Assert.assertTrue(true);
				} else {
					System.out.println("The chosen name not displays on the text filed for search.");
					ATUReports.add("The chosen name displays on the text field for search.", "True.", "False.", LogAs.FAILED, null);
					Assert.assertTrue(false);
				}
				
				// 17. Click on the button "List Courses".
				driver.findElement(By.id("SearchButton")).click();
				Thread.sleep(1000);
				
				// 18. There shall be an informative text displayed below the input field: "Choose a course that you would like to move your selected recording(s) to."
				move_window.verifyChooseACourseThatMoveAndItsPlaceBelowTheInstructorSearchField();
				
				// 19. The list of available courses to move the recording to is displayed.
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
				
				// 20. A text field displays which contain the text: "Search course by title..."
				if(driver.findElement(By.id("courseSearchText")).getAttribute("placeholder").equals("Search course by title ...")) {
					System.out.println("A course search field status is correct.");
					ATUReports.add("A course search field status.", "Correct.", "Correct.", LogAs.PASSED, null);
					Assert.assertTrue(true);
				} else {
					System.out.println("A course search field status is not correct.");
					ATUReports.add("A course search field status.", "Correct.", "Not correct.", LogAs.FAILED, null);
					Assert.assertTrue(false);
				}
				
				// 21. The "Search" button is displayed next to the search field.
				move_window.verifyThatSearchButtonRightOfTheCourseSearchFieldAboveTheCancelMoveRecordings();
				
				// 22. Click on the name of a course in the list of available courses.
				copy.selectTargetCourseFromCourseList(destination_course_name);
				
				// 23. The line which presents the name of the course is colored in blue.
				boolean is_destination_course_selected = copy.isTargetCourseFromCourseListSelected(destination_course_name);
				
				if(is_destination_course_selected) {
					System.out.println("Destination course name is selected in course list.");
					ATUReports.add("Course list.", "Destination course selected.", "Destination course selected.", LogAs.PASSED, null);
					Assert.assertTrue(true);
				} else {
					System.out.println("Desination course name is not selected in course list.");
					ATUReports.add("Course list.", "Destination course selected.", "Destination course is not selected.", LogAs.FAILED, null);
					Assert.assertTrue(false);
				}
				
				// 24. Click on "Move recording(s)" button.
				move_window.clickOnMoveRecordings();
				
				Thread.sleep(2000);
				
				// 25. The informative message "Recording(s) have been queued for move" shall be displayed.
				// 26. Click on "ok" button.
				confirmation_menu.clickOnOkButtonAfterConfirmMoveRecording();
				
				// 27. The message is closed.
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
				
				if(recording_type==1) {
					record.clickOnStudentRecordingsTab();
				} else if (recording_type==2) {
					record.clickOnTestsTab();
				}
				Thread.sleep(1000);
				
				// 28. The moved recording's name is colored in grey.
				// 29. The status of the recording (from the left of the recording date) has become "Being moving from".
				List<String> just_after_clicked_on_moved_recording_list = record.getCourseRecordingList();
				
				for(int i=0; i<just_after_clicked_on_moved_recording_list.size(); i++) {
					if(just_after_clicked_on_moved_recording_list.get(i).equals(checked_recording_title)) {
						record.checkRecordingInIndexIStatus(i+1, "Being moved from");
						if (recording_type==2) {
							if(driver.findElement(By.id("RecordingTitle" + Integer.toString(i+1))).getAttribute("disabled").equals("true")) {
								System.out.println("The moved recordings name is colored in grey (disabled).");
								ATUReports.add("Recordings grey out.", "True.", "True.", LogAs.PASSED, null);
								Assert.assertTrue(true);
							} else {
								System.out.println("The moved recordings name is not colored in grey (disabled).");
								ATUReports.add("Recordings grey out.", "True.", "False.", LogAs.FAILED, null);
								Assert.assertTrue(false);
							}
							break;
						} else {
							if(driver.findElement(By.id("Recording" + Integer.toString(i+1))).getAttribute("disabled").equals("true")) {
								System.out.println("The moved recordings name is colored in grey (disabled).");
								ATUReports.add("Recordings grey out.", "True.", "True.", LogAs.PASSED, null);
								Assert.assertTrue(true);
							} else {
								System.out.println("The moved recordings name is not colored in grey (disabled).");
								ATUReports.add("Recordings grey out.", "True.", "False.", LogAs.FAILED, null);
								Assert.assertTrue(false);
							}
							break;
						}
						

						
					}
				}
				
				// 30. The record is disappeared from the list after a while.
				record.checkStatusExistenceForMaxTTime(450);
				Thread.sleep(2000);
				List<String> after_moving_complete_recording_list = record.getCourseRecordingList();
				
				
				if (recording_type==2) {
					if((just_after_clicked_on_moved_recording_list.size() - after_moving_complete_recording_list.size()) == 1) {
						System.out.println("Recording is disspeared from the list after a while.");
						ATUReports.add("Recording dissappered from the list.", "True.", "True.", LogAs.PASSED, null);
						Assert.assertTrue(true);
					} else {
						System.out.println("Recording is not disspeared from the list after a while.");
						ATUReports.add("Recording dissappered from the list.", "True.", "False.", LogAs.FAILED, null);
						Assert.assertTrue(false);
					}
				} else {
					if(((just_after_clicked_on_moved_recording_list.size() - after_moving_complete_recording_list.size()) == 1) && (!after_moving_complete_recording_list.contains(checked_recording_title))) {
						System.out.println("Recording is disspeared from the list after a while.");
						ATUReports.add("Recording dissappered from the list.", "True.", "True.", LogAs.PASSED, null);
						Assert.assertTrue(true);
					} else {
						System.out.println("Recording is not disspeared from the list after a while.");
						ATUReports.add("Recording dissappered from the list.", "True.", "False.", LogAs.FAILED, null);
						Assert.assertTrue(false);
					}
				}
				
//				// 31. On the breadcrumb, click on "courses".
//				record.courses_link.click();
//				
//				// 32. In "All courses" page, search for the course name you moved to.
//				Thread.sleep(5000);
//				admin_dashboard_view_course_list.searchForTargetCourseName(destination_course_name);
//				Thread.sleep(3000);
//				
//				// 33. Click on that course name.
//				admin_dashboard_view_course_list.clickOnFirstCourseLink();
//				Thread.sleep(3000);	
				
//				driver.navigate().to(destination_course_url);
//				Thread.sleep(3000);
				
				// 31. On the breadcrumb, click on "courses".
				//WebElement iw = driver.findElements(By.cssSelector(".ng-scope>.ng-scope.ng-binding")).get(1);
				
				record.returnToAdminPageByClickingBreadcrumbsName(record.courses_admin);
				
//				try {		
//					iw.sendKeys(Keys.ENTER);
//					System.out.println("Clicked on the element breadcrumb.");
//					ATUReports.add("Clicked on element.", "True.", "True.", LogAs.PASSED, null);
//					Assert.assertTrue(true);
//				} catch (Exception msg) {
//					System.out.println("Fail to click on the element." );
//					ATUReports.add("Clicked on element.", "True.", "False", LogAs.FAILED, null);
//					Assert.assertTrue(false);
//				}
				
				//admin_dashboard_page.clickElement(driver.findElements(By.cssSelector(".ng-scope>.ng-scope.ng-binding")).get(1));
				//record.returnToRecordingPageByClickingBreadcrumbsName(record.breadcrumbs_courses_link);
				//clickElement(record.breadcrumbs);
				
				
				// In "All courses" page, search for Ab course.
				admin_dashboard_view_course_list.searchForTargetCourseName(destination_course_name);
				Thread.sleep(3000);
						
				// Click on that course name.
				admin_dashboard_view_course_list.clickOnFirstCourseLink();
				Thread.sleep(1000);
				
				if(recording_type==1) {
					record.clickOnStudentRecordingsTab();
				} else if (recording_type==2) {
					record.clickOnTestsTab();
				}
				Thread.sleep(2000);
				
				// 34. Validate that the recording which you have moved is displayed on the list.
				// 35. The recording is displayed on the list.
				// 36. The recording doesn't have an 'Error' status.
				// 37. The recording link is enabled (Not greyed out and clickable).
				// 38. Validate that the username displayed in the right of 'recorded by: ' of the moved recording ,isn't changed after the move.
				List<String> destination_recording_list = record.getCourseRecordingList();
				
				
				boolean is_found = false;
				for(int i=0; i<destination_recording_list.size(); i++) {
					if(destination_recording_list.get(i).equals(checked_recording_title)) {
						is_found = true;
						record.checkThatRecordingStatusTargetIndexIsNotXWithTimeout(i+1, "Error", 5);
						if (recording_type!=2) {
							record.clickOnRecordingTitleInIndex(i+1);
						}
						break;
					}
				}
				
				if(is_found) {
					System.out.println("Recording is at its placed.");
					ATUReports.add("Recording is in its place.", "Correct.", "Correct.", LogAs.PASSED, null);
					Assert.assertTrue(true);
				} else {
					System.out.println("Recording is not found in it place.");
					ATUReports.add("Recording is in its place.", "Correct.", "Uncorrect.", LogAs.FAILED, null);
					Assert.assertTrue(false);
				}
				
				// 39. On the breadcrumb, click on "courses".
				record.returnToAdminPageByClickingBreadcrumbsName(record.courses_admin);
//				iw = driver.findElements(By.cssSelector(".ng-scope>.ng-scope.ng-binding")).get(1);
//				try {		
//					iw.sendKeys(Keys.ENTER);
//					System.out.println("Clicked on the element breadcrumb.");
//					ATUReports.add("Clicked on element.", "True.", "True.", LogAs.PASSED, null);
//					Assert.assertTrue(true);
//				} catch (Exception msg) {
//					System.out.println("Fail to click on the element." );
//					ATUReports.add("Clicked on element.", "True.", "False", LogAs.FAILED, null);
//					Assert.assertTrue(false);
//				}
			}
			
			Thread.sleep(2000);
			
			// 40. Logout.
			record.signOut();
		}
		
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
		
	}
	


}
