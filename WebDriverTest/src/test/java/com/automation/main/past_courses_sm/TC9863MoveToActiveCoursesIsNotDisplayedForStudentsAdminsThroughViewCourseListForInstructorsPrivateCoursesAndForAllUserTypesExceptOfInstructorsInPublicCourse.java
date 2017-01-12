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
import java.text.ParseException;
import java.util.Date;


@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC9863MoveToActiveCoursesIsNotDisplayedForStudentsAdminsThroughViewCourseListForInstructorsPrivateCoursesAndForAllUserTypesExceptOfInstructorsInPublicCourse {

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

		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC9863MoveToActiveCoursesIsNotDisplayedForStudentsAdminsThroughViewCourseListForInstructorsPrivateCoursesAndForAllUserTypesExceptOfInstructorsInPublicCourse at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC9863MoveToActiveCoursesIsNotDisplayedForStudentsAdminsThroughViewCourseListForInstructorsPrivateCoursesAndForAllUserTypesExceptOfInstructorsInPublicCourse at " + DateToStr, "Starting the test: TC9863MoveToActiveCoursesIsNotDisplayedForStudentsAdminsThroughViewCourseListForInstructorsPrivateCoursesAndForAllUserTypesExceptOfInstructorsInPublicCourse at " + DateToStr, LogAs.PASSED, null);	
	}

	@AfterClass
	public void closeBroswer() {
		this.driver.quit();
	}
		
	// @Parameters({"web","title"}) in the future
	@Test (description="TC9863 'Move to Active Courses' - Is not displayed for students,admins(through 'view course list'),for instructors private courses, and for all user types except of instructors in public courses")
	public void test9863() throws InterruptedException {
		
		//1.Enter the university
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);	
		
		//pre test- make the course punlic
		tegrity.loginCourses("User1");
		
		course.selectCourseThatStartingWith("Ab");
		String url =  course.getCurrentUrlCoursePage(); 

		// Make course public
		record.clickOnCourseTaskThenCourseSettings();
		course_settings_page.makeSureThatMakeCoursePublicIsSelected();
		course_settings_page.clickOnOkButton();
		
		// signout
		record.signOut();
		
		//2.0-Log in as guest , 1- Log in as student ,2-Log in as admin , 3- Log in as instructor
		for(int type_of_user = 0; type_of_user < 4; type_of_user++) {
			
			if(type_of_user == 0 ){
				//2.Log in as guest
				tegrity.loginAsguest();
			} else if(type_of_user == 1) {
				//2. Log in as student
				tegrity.loginCourses("User4");
			} else if(type_of_user == 2)  {
				//2.Log in as instructor
				tegrity.loginCourses("User1");
			} else{
				//2. Log in as admin
				tegrity.loginAdmin("Admin");
			} 
		
			if(type_of_user < 2) {
				// 3. Open some course.
				course.selectCourseThatStartingWith("Ab");
			} else if(type_of_user ==2) {
				course.selectCourseThatStartingWith("User1");
			} else {
				// Click on "view course list" under "courses" section.
				admin_dash_board_page.clickOnTargetSubmenuCourses("View Course List");	
				// In "All courses" page, search for Ab course.
				admin_dashboard_view_course_list.waitForThePageToLoad();
				admin_dashboard_view_course_list.moveToCoursesThroughGet(url);
			}
			
			//4.Hover over the "Course Tasks"
			record.moveToElementAndPerform(record.course_task_button, driver);
		
			//5.The "Move to Active Courses" option is not exist
			record.verifyWebElementNotDisplayed(record.move_to_past_courses, "Move to past courses");
		
			if(type_of_user == 1) {
				
				//6.Click on the "Courses" button
				record.returnToCourseListPage();
				
				//7.Click on the "Public course" tab
				course.clickOnPublicCoursesTab();
				
				//8.Click on the one of the public courses
				course.selectCourseThatStartingWith("Ab");
				
				//9.Hover over the "Course Tasks"
				record.moveToElementAndPerform(record.course_task_button, driver);
			
				//10.The "Move to Active Courses" option is not exist
				record.verifyWebElementNotDisplayed(record.move_to_past_courses, "Move to past courses");
			}
			
			//6.Sign out
			record.signOut();
		
		}
		
		//pre test- make the course punlic
		tegrity.loginCourses("User1");
				
		course.selectCourseThatStartingWith("Ab");
			
		// Make course public
		record.clickOnCourseTaskThenCourseSettings();
		course_settings_page.makeSureThatMakeCoursePublicIsUnSelected();
		course_settings_page.clickOnOkButton();
		 
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
	
	}
}