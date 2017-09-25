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
public class TC10913VerifyAfterMovingAnEmptyCourseToPastCoursesManuallyTheCourseIsStillVisible {

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
			course_settings_page = PageFactory.initElements(driver, CourseSettingsPage.class);
			delete_menu = PageFactory.initElements(driver, DeleteMenu.class);

		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC10913VerifyAfterMovingAnEmptyCourseToPastCoursesManuallyTheCourseIsStillVisible at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC10913VerifyAfterMovingAnEmptyCourseToPastCoursesManuallyTheCourseIsStillVisible at " + DateToStr, "Starting the test: TC10913VerifyAfterMovingAnEmptyCourseToPastCoursesManuallyTheCourseIsStillVisible at " + DateToStr, LogAs.PASSED, null);	
	}

	@AfterClass
	public void closeBroswer() {
		this.driver.quit();
	}
		
	// @Parameters({"web","title"}) in the future
	@Test (description="TC10913 Verify after moving an empty course to past courses manually the course is still visible")
	public void test10913() throws InterruptedException {
		
		//1.Enter the university
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);

		tegrity.loginCourses("User1");

		initializeCourseObject();

		//1.Click on the 'Past Courses' tab*
		course.clickOnPastCoursesTabButton();

		//2.Select the past course
		course.selectCourseThatStartingWith("PastCourseD");

		//3.move the course to active courses
		record.clickOnCourseTaskThenMoveToActiveCourses();

		//4.click on the ok after moving to active courses
		confirm_menu.clickOnOkButtonAfterMoveToPastCoursesOrActiveCourses("The course was successfully moved to active courses");

		//5.return to the courses page
		record.signOut();
				
		//2.Pre - test Login as INSTRUCTOR 
		tegrity.loginCourses("User1");
		
		//3.delete all the records in ad course
		course.deleteAllRecordingsInCourseStartWith("PastCourseD", 0, record, delete_menu);
		course.deleteAllRecordingsInCourseStartWith("PastCourseD", 1, record, delete_menu);
		course.deleteAllRecordingsInCourseStartWith("PastCourseD", 2, record, delete_menu);
		course.deleteAllRecordingsInCourseStartWith("PastCourseD", 3, record, delete_menu);
		
		//4.sign out
		record.signOut();
		
		//5.Login as INSTRUCTOR 
		tegrity.loginCourses("User1");
		
		//6.Open the empty course
		String course_name = course.selectCourseThatStartingWith("PastCourseD");
		
		//7.Hover over the "Course Tasks"
		record.moveToElementAndPerform(record.course_task_button, driver);
		
		//8.Click on the 'Course Tasks - Move to Past Courses'
		record.clickOnCourseTaskThenMoveToPastCourses();
		
		//9.Click on the ok after moving to past courses
		confirm_menu.clickOnOkButtonAfterMoveToPastCoursesOrActiveCourses("The course was successfully moved to past courses");
			
		//10.The course page is continue to be display
		record.verifyThatItIsRecordingsPage();
		
		//11.The "Start a Test" and the "Start a Recording" buttons are gone	
		record.verifyWebElementNotDisplayed(record.start_test_button, "Start Test");
		record.verifyWebElementNotDisplayed(record.start_recording_button, "Start Recording button");
		
		//12.Hover over the "Course Tasks"
		record.moveToElementAndPerform(record.course_task_button,driver);
				
		//13. The 'Upload a Recording', 'Upload Video File', 'Upload Audio File', 'Add Additional Content File' and 'Add additional Content Link' are not displayed
		record.verifyWebElementNotDisplayed(record.upload_video_file, "Upload a Recording");
		record.verifyWebElementNotDisplayed(record.upload_audio_file, "Upload Video File");
		record.verifyWebElementNotDisplayed(record.add_additional_content_file, "Add Additional Content File");
		record.verifyWebElementNotDisplayed(record.add_additional_content_link, "Add additional Content Link");
		
		//14.Click on the 'courses' breadcrumb
		record.returnToCourseListPage();
		
		//15.The course is not displayed in the 'Active Courses' page
		initializeCourseObject();
		course.verifyCourseNotExist(course_name);
		
		//16.Click on the 'Past Courses' tab*
		course.clickOnPastCoursesTabButton();
		
		//17.The course is displayed in the 'Past Courses' tab content*
		initializeCourseObject();
		course.verifyCourseExist(course_name);
		
		//18.post test - return the course ad to active courses
		course.selectCourseThatStartingWith("PastCourseD");
		
		//19.Hover over the "Course Tasks"
		record.moveToElementAndPerform(record.course_task_button, driver);
		
		//20.Click on the 'Course Tasks - Move to Active Courses'
		record.clickOnCourseTaskThenMoveToActiveCourses();
				
		//21.Click on the ok after moving to past courses
		confirm_menu.clickOnOkButtonAfterMoveToPastCoursesOrActiveCourses("The course was successfully moved to active courses");

		course.copyRecordingFromCourseStartWithToCourseStartWithOfType("ab", "PastCourseD", 0, record,copy, confirm_menu);

		//22.Open the empty course
		course_name = course.selectCourseThatStartingWith("PastCourseD");

		//23.Hover over the "Course Tasks"
		record.moveToElementAndPerform(record.course_task_button, driver);

		//24.Click on the 'Course Tasks - Move to Past Courses'
		record.clickOnCourseTaskThenMoveToPastCourses();

		//25.Click on the ok after moving to past courses
		confirm_menu.clickOnOkButtonAfterMoveToPastCoursesOrActiveCourses("The course was successfully moved to past courses");



		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
	
	}
	
	// description = "get courses list"
			public void initializeCourseObject() throws InterruptedException {

				course = PageFactory.initElements(driver, CoursesHelperPage.class);
				course.courses = course.getCoursesListFromElement(course.course_list);
			}
}