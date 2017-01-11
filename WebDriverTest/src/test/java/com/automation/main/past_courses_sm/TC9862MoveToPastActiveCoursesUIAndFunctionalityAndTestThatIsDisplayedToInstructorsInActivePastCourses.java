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
import com.automation.main.page_helpers.CoursesHelperPage;
import com.automation.main.page_helpers.EditRecordingPropertiesWindow;
import com.automation.main.page_helpers.ImpersonateUser;
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
import junitx.util.PropertyManager;

import java.text.DateFormat;
import java.util.Date;


@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC9862MoveToPastActiveCoursesUIAndFunctionalityAndTestThatIsDisplayedToInstructorsInActivePastCourses {

	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}
	
	public PublishWindow publish_window;
	public AdminDashboardViewCourseList admin_dashboard_view_course_list;
	public LoginHelperPage tegrity;
	public CoursesHelperPage course;
	public RecordingHelperPage record;
	public ImpersonateUser impersonate_user;
	public CalendarPage calendarPage;
	public EditRecordingPropertiesWindow edit_recording_properties_window;
	public AdminDashboardPage admin_dash_board_page;
	WebDriver driver;
	WebDriverWait wait;
	public TagMenu tag_window;
	public static WebDriver thread_driver;
	public ConfirmationMenu confirm_menu;
	public CopyMenu copy;
	DesiredCapabilities capability;
	String course_name;

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
			impersonate_user = PageFactory.initElements(driver, ImpersonateUser.class);
			
		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC9862MoveToPastActiveCoursesUIAndFunctionalityAndTestThatIsDisplayedToInstructorsInActivePastCourses at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC9862MoveToPastActiveCoursesUIAndFunctionalityAndTestThatIsDisplayedToInstructorsInActivePastCourses at " + DateToStr, "Starting the test: TC9862MoveToPastActiveCoursesUIAndFunctionalityAndTestThatIsDisplayedToInstructorsInActivePastCourses at " + DateToStr, LogAs.PASSED, null);	
	}

	@AfterClass
	public void closeBroswer() {
		this.driver.quit();
	}
		
	// @Parameters({"web","title"}) in the future
	@Test (description="TC9862 Move to Past/Active Courses - UI and Functionality - And test that is displayed to instructors in active/past courses")
	public void test9862() throws InterruptedException {
		
		//1.Enter the university
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);	
		
		//2.0-Login as INSTRUCTOR , 1- Login as Executive admin  ; 2- Impersonate User
		for(int type_of_user = 0; type_of_user < 3; type_of_user++) {
			
		if(type_of_user == 0) {
			tegrity.loginCourses("User1");
		}else if(type_of_user ==1) {
			tegrity.loginAdmin("ExcutiveAdmin");
		} else {		
			tegrity.loginAdmin("Admin");
			
			//14.Click on "Impersonate User" under the "Users" section
			admin_dash_board_page.waitForPageToLoad();
			admin_dash_board_page.clickOnTargetSubmenuUsers("Impersonate User");
			
			//15.Enter the Instructor's ID you logged in with in step 4 in the text field and click on "Impersonate"
			String current_handler = driver.getWindowHandle();		
			impersonate_user.EnterTheUserIdAndPressOnImpersonate(PropertyManager.getProperty("User1"));
		
			//16.Move to the open tab and close the old tab		
			course.waitForThePageToLoad();
			record.moveToTheOtherTabAndCloseTheOldTab(current_handler);
		}
			
		//3.Click on one of the active courses	
		if(type_of_user == 0 || type_of_user == 2){
			course_name = course.selectCourseThatStartingWith("abc");
		} else {
			course_name = course.selectCourseThatStartingWith("Ab");		
		}
		//4.Hover over the "Course Tasks"
		record.moveToElementAndPerform(record.course_task_button, driver);
		
		//5.The "Move to Past Courses" option is the last one before the "Subscribe to Your Course's..." divider
		record.verifyThatMoveToPastCoursesIsTheLastOneBeforeSubscribe("moveToPast");
		
		//6.Hover over the "Move to Past Courses"
		record.moveToElementAndPerform(record.move_to_past_courses,driver);
		
		//7.The "Move to Past Courses" option is click-able(the cursor is changed to a hand) and a "Move to Past Courses" hint is displayed
		record.verifyThatWeHaveHintToWebElement(record.move_to_past_courses, "Move to Past courses");
		
		//8.Click on the "Move to Past Courses" button
		record.clickElementJS(record.move_to_past_courses);
		
		//9.The entire page is greyed out and a message is displayed: the message title is "Move" with the text "The course was successfully moved to past courses" and there is a "Ok" button
		//10.Click on the "Ok" button
		confirm_menu.clickOnOkButtonAfterMoveToPastCoursesOrActiveCourses("The course was successfully moved to past courses");
		
		//11. The course page is continue to be display 
		record.verifyThatItIsRecordingsPage();
		
		//12.The "Start a Test" and the "Start a Recording" buttons are gone	
		record.verifyWebElementNotDisplayed(record.start_test_button, "Start Test");
		record.verifyWebElementNotDisplayed(record.start_recording_button, "Start Recording button");
		
		//13.Hover over the "Course Tasks"
		record.moveToElementAndPerform(record.course_task_button,driver);
		
		//14. The 'Upload a Recording', 'Upload Video File', 'Upload Audio File', 'Add Additional Content File' and 'Add additional Content Link' are not displayed
		record.verifyWebElementNotDisplayed(record.upload_video_file, "Upload a Recording");
		record.verifyWebElementNotDisplayed(record.upload_audio_file, "Upload Video File");
		record.verifyWebElementNotDisplayed(record.add_additional_content_file, "Add Additional Content File");
		record.verifyWebElementNotDisplayed(record.add_additional_content_link, "Add additional Content Link");
	
		//15."Move to Past Courses" is switched by "Move to Active Courses" 
		record.verifyWebElementNotDisplayed(record.move_to_past_courses, "Move to past courses");
		record.verifyWebElementDisplayed(record.move_to_active_courses, "Move to active courses");
		
		//16.Click on courses
		record.returnToCourseListPage();
		
		//17.the course that moved to be a past course for the user is not displayed in the active courses list
		initializeCourseObject();
		course.verifyCourseNotExist(course_name);
		
		//18.Click on the "Past Courses" tab
		course.clickOnPastCoursesTabButton();
		
		//19.Click on one of the past courses
		course.selectCourseThatStartingWith(course_name);
		
		//20.Hover over the "Course Tasks"
		record.moveToElementAndPerform(record.course_task_button,driver);
		
		//21.The "Move to Active Courses" option is the last one before the "Subscribe to Your Course's..." divider
		record.verifyThatMoveToPastCoursesIsTheLastOneBeforeSubscribe("moveToActive");
		
		//22.Hover over the "Move to Active courses"
		record.moveToElementAndPerform(record.move_to_active_courses,driver);
		
		//23.The "Move to Past Courses" option is click-able(the cursor is changed to a hand) and a "Move to Past Courses" hint is displayed
		record.verifyThatWeHaveHintToWebElement(record.move_to_active_courses, "Move to Active courses");
		
		//24.Click on the "Move to Active courses" button
		record.clickElementJS(record.move_to_active_courses);
		
		//25.The entire page is greyed out and a message is displayed: the message title is "Move" with the text "The course was successfully moved to active courses" and there is a "Ok" button
		//26.Click on the "Ok" button
		confirm_menu.clickOnOkButtonAfterMoveToPastCoursesOrActiveCourses("The course was successfully moved to active courses");
		
		//27.Click on the "Courses" button
		record.returnToCourseListPage();
			
		if(course.isPastCoursesTabExist()){
			
			//28.click on the "Past Courses" tab
			course.clickOnPastCoursesTabButton();
		
			//29.The course is not displayed in the "Past Course" list	
			course.verifyCourseNotExist(course_name);
		}
		//30.sign out from user
		record.signOut();
		}
				
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
	
	}
	
	// description = "get courses list"
		public void initializeCourseObject() throws InterruptedException {

			course = PageFactory.initElements(driver, CoursesHelperPage.class);
			course.courses = course.getCoursesListFromElement(course.course_list);
		}
}