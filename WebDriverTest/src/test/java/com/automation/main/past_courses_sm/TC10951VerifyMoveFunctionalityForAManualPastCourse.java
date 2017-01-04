package com.automation.main.past_courses_sm;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import com.automation.main.page_helpers.AdminDashboardPage;
import com.automation.main.page_helpers.AdminDashboardViewCourseList;
import com.automation.main.page_helpers.CalendarPage;
import com.automation.main.page_helpers.ConfirmationMenu;
import com.automation.main.page_helpers.CopyMenu;
import com.automation.main.page_helpers.CourseSettingsPage;
import com.automation.main.page_helpers.CoursesHelperPage;
import com.automation.main.page_helpers.DeleteMenu;
import com.automation.main.page_helpers.EditRecordingPropertiesWindow;
import com.automation.main.page_helpers.LoginHelperPage;
import com.automation.main.page_helpers.MoveWindow;
import com.automation.main.page_helpers.PublishWindow;
import com.automation.main.page_helpers.RecordingHelperPage;
import com.automation.main.page_helpers.TagMenu;
import com.automation.main.utilities.DriverSelector;
import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import java.text.DateFormat;
import java.util.Date;


@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC10951VerifyMoveFunctionalityForAManualPastCourse {

	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}
	
	public PublishWindow publish_window;
	public AdminDashboardViewCourseList admin_dashboard_view_course_list;
	public LoginHelperPage tegrity;
	public CoursesHelperPage course;
	public RecordingHelperPage record;
	public CalendarPage calendarPage;
	public MoveWindow move_Window;
	public DeleteMenu delete_menu;
	public CourseSettingsPage course_settings_page;
	public EditRecordingPropertiesWindow edit_recording_properties_window;
	public AdminDashboardPage admin_dash_board_page;
	WebDriver driver;
	WebDriverWait wait;
	public TagMenu tag_window;
	public static WebDriver thread_driver;
	public ConfirmationMenu confirm_menu;
	public CopyMenu copy;
	DesiredCapabilities capability;
	String url;

	@BeforeClass
	public void setup() {

			driver = DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());
			calendarPage = PageFactory.initElements(driver, CalendarPage.class);
			course = PageFactory.initElements(driver, CoursesHelperPage.class);
			admin_dash_board_page = PageFactory.initElements(driver, AdminDashboardPage.class);
			admin_dashboard_view_course_list = PageFactory.initElements(driver, AdminDashboardViewCourseList.class);
			confirm_menu = PageFactory.initElements(driver, ConfirmationMenu.class);
			tegrity = PageFactory.initElements(driver, LoginHelperPage.class);
			tag_window= PageFactory.initElements(driver, TagMenu.class);
			record = PageFactory.initElements(driver, RecordingHelperPage.class);
			copy = PageFactory.initElements(driver, CopyMenu.class);
			move_Window= PageFactory.initElements(driver, MoveWindow.class);
			course_settings_page = PageFactory.initElements(driver, CourseSettingsPage.class);
			delete_menu = PageFactory.initElements(driver, DeleteMenu.class);

		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC10951VerifyMoveFunctionalityForAManualPastCourse at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC10951VerifyMoveFunctionalityForAManualPastCourse at " + DateToStr, "Starting the test: TC10951VerifyMoveFunctionalityForAManualPastCourse at " + DateToStr, LogAs.PASSED, null);	
	}

	@AfterClass
	public void closeBroswer() {
		this.driver.quit();
	}
		
	// @Parameters({"web","title"}) in the future
	@Test (description="TC10951 Verify move functionality for a manual past course")
	public void test10951() throws InterruptedException {
		
		//1.Enter the university
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);	
				
		//2.Login as INSTRUCTOR 
		tegrity.loginCourses("User1");
		
		//pre-test!! move record to the pastCourseA
		
		//3.Click on the 'Past Courses' tab*
		course.clickOnPastCoursesTabButton();
				
		//4.Select the past course
		course.selectCourseThatStartingWith("PastCourseA");
				
		//5.move the course to active courses
		record.clickOnCourseTaskThenMoveToActiveCourses();
		
		//6.click on the ok after moving to active courses
		confirm_menu.clickOnOkButtonAfterMoveToPastCoursesOrActiveCourses("The course was successfully moved to active courses");
		
		//7.return to the courses page
		record.returnToCourseListPage();
		
		//8.copy on record to pastcoursesA
		course.copyOneRecordingFromCourseStartWithToCourseStartWithOfType("Ab", "PastCourseA", 0,record, copy, confirm_menu);
		
		//9.Select the past course
		course.selectCourseThatStartingWith("PastCourseA");
		
		//10.wait until the moving will finish
		record.checkStatusExistenceForMaxTTime(220);
		
		//11.move to pass courses
		record.clickOnCourseTaskThenMoveToPastCourses();
		
		//12.click on the ok after moving to past courses
		confirm_menu.clickOnOkButtonAfterMoveToPastCoursesOrActiveCourses("The course was successfully moved to past courses");
	
		//13.return to the courses page
		record.returnToCourseListPage();
		
		////////////End pre test///////////////
		
		//14.Click on the 'Past Courses' tab*
		course.clickOnPastCoursesTabButton();
		
		//get the second course for later use
		String PastCourseB = course.getCourseInIndex(2);
		
		//15.Select the past course
		course.selectCourseThatStartingWith("PastCourseA");
		
		//16.Select a recording
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
		
		//get recording name for later use
		String recordName = record.getFirstRecordingTitle();
		
		//17.Select the "Recording Tasks -> Move" menu item
		record.clickOnRecordingTaskThenMove();
		
		//18."Move" window is displayed
		move_Window.verifyThatMoveWindowIsOpen();
			
		//19.Verify that no past course is not displayed in list of destination courses
		copy.VerifyThatCourseIsNotDisplayedInTheListOfCourseDestination(PastCourseB);
		
		//20.Click the "Cancel" button	
		move_Window.clickOnCancelButton();
		
		//21.Select the "Recording Tasks -> Move" menu item
		record.clickOnRecordingTaskThenMove();
				
		//22.Choose an active course
		copy.selectTargetCourseFromCourseListThatStartWith("abc");
		
		//23.Click on 'Move' button
		move_Window.clickOnMoveRecordings();
		
		//24.Click on ok button after moving recording
		confirm_menu.clickOnOkButtonAfterConfirmMoveRecording();
		
		//25.wait until the recording finish moving
		record.checkStatusExistenceForMaxTTime(220);
		
		//26.Verify the recording is not present in the source course
		record.verifyThatTargetRecordingNotExistInRecordingList(recordName);
		
		//27.Go to destination course
		record.returnToCourseListPage();
		course.selectCourseThatStartingWith("abc");
		
		//28.Verify the recording was Moved to the destination course
		record.verifyThatTargetRecordingExistInRecordingList(recordName);
		
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
	
	}
	
	// description = "get courses list"
			public void initializeCourseObject() throws InterruptedException {

				course = PageFactory.initElements(driver, CoursesHelperPage.class);
				course.courses = course.getCoursesListFromElement(course.course_list);
			}
}