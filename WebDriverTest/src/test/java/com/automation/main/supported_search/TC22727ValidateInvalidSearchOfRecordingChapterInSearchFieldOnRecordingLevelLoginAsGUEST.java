package com.automation.main.supported_search;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.annotations.AfterClass;
import com.automation.main.page_helpers.AddAdditionalContentFileWindow;
import com.automation.main.page_helpers.AdminDashboardPage;
import com.automation.main.page_helpers.AdvancedServiceSettingsPage;
import com.automation.main.page_helpers.ConfirmationMenu;
import com.automation.main.page_helpers.CopyMenu;
import com.automation.main.page_helpers.CourseSettingsPage;
import com.automation.main.page_helpers.CoursesHelperPage;
import com.automation.main.page_helpers.CreateNewCourseWindow;
import com.automation.main.page_helpers.CreateNewUserWindow;
import com.automation.main.page_helpers.DeleteMenu;
import com.automation.main.page_helpers.EditRecordinPropertiesWindow;
import com.automation.main.page_helpers.EditRecording;
import com.automation.main.page_helpers.LoginHelperPage;
import com.automation.main.page_helpers.ManageAdHocCoursesMembershipWindow;
import com.automation.main.page_helpers.ManageAdhocCoursesEnrollmentsPage;
import com.automation.main.page_helpers.ManageAdhocUsersPage;
import com.automation.main.page_helpers.MoveWindow;
import com.automation.main.page_helpers.PlayerPage;
import com.automation.main.page_helpers.RecordingHelperPage;
import com.automation.main.utilities.DriverSelector;
import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;




@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC22727ValidateInvalidSearchOfRecordingChapterInSearchFieldOnRecordingLevelLoginAsGUEST {
	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}

	public EditRecordinPropertiesWindow erp_window;
	public LoginHelperPage tegrity;
	public CoursesHelperPage course;
	public RecordingHelperPage record;
	public ConfirmationMenu confirm_menu;
	public DeleteMenu delete_menu;
	public MoveWindow move_window;
	WebDriver driver;
	WebDriverWait wait;
	public EditRecording edit_recording;
	public static WebDriver thread_driver;
	CopyMenu copy;
	String currentCourse;
	String targetCourse;
	String clickedRecording;
	WebDriver driver2;
	public AdminDashboardPage admin_dashboard_page;
	public ManageAdhocCoursesEnrollmentsPage mange_adhoc_course_enrollments;
	public CreateNewCourseWindow create_new_course_window;
	public ManageAdhocUsersPage mange_adhoc_users_page;
	public CreateNewUserWindow create_new_user_window;
	public ManageAdHocCoursesMembershipWindow mangage_adhoc_courses_membership_window;
	public AddAdditionalContentFileWindow add_additional_content_window;
	public AdvancedServiceSettingsPage advanced_services_setting_page;
	public CourseSettingsPage course_settings;
	public PlayerPage player_page;
	String instructor1;
	String instructor2;
	List<String> for_enroll;

	
	@AfterClass
	public void closeBroswer() {
	
		this.driver.quit();
	}

	@BeforeClass
	public void setup() {

		driver = DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());
		tegrity = PageFactory.initElements(driver, LoginHelperPage.class);
		wait = new WebDriverWait(driver, 30);
		add_additional_content_window = PageFactory.initElements(driver, AddAdditionalContentFileWindow.class);
		record = PageFactory.initElements(driver, RecordingHelperPage.class);
		copy = PageFactory.initElements(driver, CopyMenu.class);
		delete_menu = PageFactory.initElements(driver, DeleteMenu.class);
		course = PageFactory.initElements(driver, CoursesHelperPage.class);
		confirm_menu = PageFactory.initElements(driver, ConfirmationMenu.class);
		player_page = PageFactory.initElements(driver, PlayerPage.class);	
		edit_recording = PageFactory.initElements(driver, EditRecording.class);
		course_settings = PageFactory.initElements(driver, CourseSettingsPage.class);
		Date curDate = new Date();
		String DateToStr = DateFormat.getInstance().format(curDate);
		System.out.println("Starting the test: TC22727ValidateInvalidSearchOfRecordingChapterInSearchFieldOnRecordingLevelLoginAsGUEST at " + DateToStr);
		ATUReports.add("Message window.", "Starting the test: TC22727ValidateInvalidSearchOfRecordingChapterInSearchFieldOnRecordingLevelLoginAsGUEST at " + DateToStr,
		"Starting the test: TC22727ValidateInvalidSearchOfRecordingChapterInSearchFieldOnRecordingLevelLoginAsGUEST at " + DateToStr, LogAs.PASSED, null);	
	}

	@Test (description = "TC 22727 Validate Invalid Search Of Recording Chapter In Search Field On Recording Level Login As GUEST")
	public void test22727() throws Exception {

		
        // 1.load page
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
		tegrity.waitForVisibility(tegrity.passfield);
	
		
		//pre test , enter as User1 and edit the chapter name from another record
		
		// 2.login as user1 to get the GGID
		tegrity.loginCourses("User1");
		course.waitForVisibility(course.first_course_button);
						
		//2.1 take course being copied to name and then return
		String course_name=course.selectCourseThatStartingWith("Ab");
		
		// Make course public
		record.clickOnCourseTaskThenCourseSettings();
		course_settings.makeSureThatMakeCoursePublicIsSelected();
		course_settings.clickOnOkButton();
		Thread.sleep(1000);
			
		record.waitForVisibility(record.checkbox2);
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox2);
				
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyhhmmss");
		String recording_name="ChapterNewName" + sdf.format(date);
				
		record.clickOnRecordingTaskThenEditRecording();	        
		edit_recording.changeFirstChapterRecordingNameToTargetNameNew(recording_name);				
		record.signOut();
		

		// 2.login as guest
		tegrity.loginAsguest();
		
		//3.Select a course
		course.waitForVisibility(course.first_course_button);
		course.selectCourseThatStartingWith("Ab");
		
		///4.Click on one of the Recording link 
		record.waitForVisibility(record.recordings_tab);
		Thread.sleep(2000);
		record.convertRecordingsListToNames();
		record.verifyFirstExpandableRecording();
		///5.Click on the first chapter.
		record.clickOnTheFirstCaptherWithOutTheExpand();
	
		// 6.Select the Recording by clicking on one of the chapters
		player_page.verifyTimeBufferStatusForXSec(2);// check source display
		for (String handler : driver.getWindowHandles()) {
			driver.switchTo().window(handler);
			break;		
		}

		System.out.println(player_page.breadcrumbs_box_elements_list.get(0));
		///7.Validate the search field is display at the top right of the UI page below the top navigation bar.
        player_page.veriySearchBoxLocation();
      
        ///8.Validate the text in the Tegrity Player page: "Search in this recording..."
        player_page.verifySearchBoxHint();
        
		/// 9.Enter a "Recording Title" of another Recording from the same
		/// course and press ENTER
		player_page.verifySearchReturnEmptyList(recording_name);
		player_page.exitInnerFrame();
		record.signOut();
		
		// Unpublic Ab course1. 
		tegrity.loginCourses("User1");
						
		course.selectCourseThatStartingWith("Ab");
						
		// Make course public
		record.clickOnCourseTaskThenCourseSettings();
		course_settings.makeSureThatMakeCoursePublicIsUnSelected();
		course_settings.clickOnOkButton();
			
		//10.quit
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);

	}
}
