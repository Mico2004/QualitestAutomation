package com.automation.main.supported_search_sources_admin_anonymous;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.annotations.AfterClass;

import com.automation.main.page_helpers.AddAdditionalContentFileWindow;
import com.automation.main.page_helpers.AdminDashboardPage;
import com.automation.main.page_helpers.AdminDashboardViewCourseList;
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
import com.automation.main.page_helpers.EmailAndConnectionSettingsPage;
import com.automation.main.page_helpers.EmailInboxPage;
import com.automation.main.page_helpers.EmailLoginPage;
import com.automation.main.page_helpers.EulaPage;
import com.automation.main.page_helpers.GetSupprtWindow;
import com.automation.main.page_helpers.HelpPage;
import com.automation.main.page_helpers.LoginHelperPage;
import com.automation.main.page_helpers.ManageAdHocCoursesMembershipWindow;
import com.automation.main.page_helpers.ManageAdhocCoursesEnrollmentsPage;
import com.automation.main.page_helpers.ManageAdhocUsersPage;
import com.automation.main.page_helpers.MoveWindow;
import com.automation.main.page_helpers.PlayerPage;
import com.automation.main.page_helpers.PublishWindow;
import com.automation.main.page_helpers.RecordingHelperPage;
import com.automation.main.page_helpers.RunDiagnosticsPage;
import com.automation.main.page_helpers.SearchPage;
import com.automation.main.page_helpers.TopBarHelper;
import com.automation.main.utilities.DriverSelector;

import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;



@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC22689ValidateSourceTypeAsRecordingChapterInSearchFieldOnTheRecordingLevelUILoginAsADMIN {
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
	public static WebDriver thread_driver;
	CopyMenu copy;
	String currentCourse;
	String targetCourse;
	String clickedRecording;
	WebDriver driver2;
	public EditRecording edit_recording;
	public AdminDashboardPage admin_dashboard_page;
	public ManageAdhocCoursesEnrollmentsPage mange_adhoc_course_enrollments;
	public CreateNewCourseWindow create_new_course_window;
	public ManageAdhocUsersPage mange_adhoc_users_page;
	public CreateNewUserWindow create_new_user_window;
	public ManageAdHocCoursesMembershipWindow mangage_adhoc_courses_membership_window;
	public AddAdditionalContentFileWindow add_additional_content_window;
	public AdvancedServiceSettingsPage advanced_services_setting_page;
	public HelpPage help_page;
	public CourseSettingsPage course_settings;
	public EmailAndConnectionSettingsPage email_setting;
	public EulaPage eula_page;
	public GetSupprtWindow support_window;
	public EmailLoginPage email_login;
	public EmailInboxPage email_inbox;
	public RunDiagnosticsPage run_diagnostics;
	public EditRecordinPropertiesWindow edit_recording_properties_window;
	public PlayerPage player_page;
	public PublishWindow publish_window;
	public AdminDashboardViewCourseList admin_view_course_list;
	String instructor1;
	String instructor2;
	public SearchPage search_page;
	public TopBarHelper top_bar_helper;
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

		edit_recording = PageFactory.initElements(driver, EditRecording.class);
		record = PageFactory.initElements(driver, RecordingHelperPage.class);
		copy = PageFactory.initElements(driver, CopyMenu.class);
		delete_menu = PageFactory.initElements(driver, DeleteMenu.class);
		course = PageFactory.initElements(driver, CoursesHelperPage.class);
		confirm_menu = PageFactory.initElements(driver, ConfirmationMenu.class);
		course_settings = PageFactory.initElements(driver, CourseSettingsPage.class);
		wait = new WebDriverWait(driver, 30);
		move_window = PageFactory.initElements(driver, MoveWindow.class);
		erp_window = PageFactory.initElements(driver, EditRecordinPropertiesWindow.class);
		admin_dashboard_page = PageFactory.initElements(driver, AdminDashboardPage.class);
		advanced_services_setting_page = PageFactory.initElements(driver, AdvancedServiceSettingsPage.class);
		mange_adhoc_course_enrollments = PageFactory.initElements(driver, ManageAdhocCoursesEnrollmentsPage.class);
		eula_page = PageFactory.initElements(driver, EulaPage.class);
		create_new_course_window = PageFactory.initElements(driver, CreateNewCourseWindow.class);
		support_window = PageFactory.initElements(driver, GetSupprtWindow.class);
		mange_adhoc_users_page = PageFactory.initElements(driver, ManageAdhocUsersPage.class);
		email_login = PageFactory.initElements(driver, EmailLoginPage.class);
		create_new_user_window = PageFactory.initElements(driver, CreateNewUserWindow.class);
		email_inbox = PageFactory.initElements(driver, EmailInboxPage.class);
		mangage_adhoc_courses_membership_window = PageFactory.initElements(driver,ManageAdHocCoursesMembershipWindow.class);
		top_bar_helper = PageFactory.initElements(driver,TopBarHelper.class);
		search_page = PageFactory.initElements(driver, SearchPage.class);
		player_page = PageFactory.initElements(driver, PlayerPage.class);
		admin_view_course_list = PageFactory.initElements(driver, AdminDashboardViewCourseList.class);
		edit_recording_properties_window = PageFactory.initElements(driver, EditRecordinPropertiesWindow.class);
		Date curDate = new Date();
		String DateToStr = DateFormat.getInstance().format(curDate);
		System.out.println("Starting the test: TC22689ValidateSourceTypeAsRecordingChapterInSearchFieldOnTheRecordingLevelUILoginAsADMIN at " + DateToStr);
		ATUReports.add("Message window.", "Starting the test: TC22689ValidateSourceTypeAsRecordingChapterInSearchFieldOnTheRecordingLevelUILoginAsADMIN at " + DateToStr,
		"Starting the test: TC22689ValidateSourceTypeAsRecordingChapterInSearchFieldOnTheRecordingLevelUILoginAsADMIN at " + DateToStr, LogAs.PASSED, null);	
				

	}

	@Test
	public void TC22689() throws Exception {

		// 1.load page
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
		tegrity.waitForVisibility(tegrity.passfield);
		
		// 2.login as user1
		tegrity.loginCourses("User1");
		course.waitForVisibility(course.first_course_button);
				
		//2.1 take course being copied to name and then return
		String course_name=course.selectCourseThatStartingWith("Ab");
		String url =  course.getCurrentUrlCoursePage(); 

		
		//2.2 edit the first chapter for the rest of the test
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
		record.clickOnRecordingTaskThenEditRecording();
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyhhmmss");
		String RecordingChapter = "NewChapterName" + sdf.format(date); 
		edit_recording.changeFirstChapterRecordingNameToTargetNameNew(RecordingChapter);
		Thread.sleep(2000);
			
		record.signOut();
		Thread.sleep(1000);
		tegrity.waitForVisibility(tegrity.passfield);


		// 2.login as admin
		tegrity.loginAdmin("Admin");
		admin_dashboard_page.waitForVisibility(admin_dashboard_page.sign_out);
		
		//3.Validate "Allow students to download recordings" option in "Manage Course Settings" from "Courses" section is enable
        admin_dashboard_page.clickOnTargetSubmenuCourses("Manage Course Settings");
        course_settings.waitForVisibility(course_settings.getOk_button());
        course_settings.CheckAllowStudentDownload();
	   
	    
		// 3.Click on "View Course List" link
		admin_dashboard_page.waitForVisibility(admin_dashboard_page.sign_out);
		Thread.sleep(1500);
		admin_dashboard_page.clickOnTargetSubmenuCourses("View Course List");
		Thread.sleep(1500);
		
		// 4.verify all courses page
		admin_view_course_list.verifyAllCoursesPage();
		
		// 5.Select a course
		admin_view_course_list.waitForVisibility(admin_view_course_list.first_course_link);
		admin_view_course_list.moveToCoursesThroughGet(url);
	
		// 7.Click on one of the Recording link
	    record.waitForVisibility(record.first_recording);
	    record.convertRecordingsListToNames();
	    record.convertRecordingsListToRecorderName();
	    
	
	   	String recording_to_search=record.recording_list_names.get(0);
	    record.verifyFirstExpandableRecording();
	    record.clickOnTheFirstCaptherWithOutTheExpand();
	
		// 8.Select the Recording by clicking on one of the chapters
		player_page.verifyTimeBufferStatusForXSec(10);// check source display

			///// to go back to crecording window handler	
		for (String handler : driver.getWindowHandles()) {
					driver.switchTo().window(handler);
			break;		
			}
				
		///9.Validate the search field is display at the top right of the UI page below the top navigation bar.
	     player_page.veriySearchBoxLocation();
	      
	      ///10.Validate the text in the Tegrity Player page: "Search in this recording..."
	     player_page.verifySearchBoxHint();
	        
	     //11.Search the "Recording Chapter" from the recording that we mentioned in the preconditions and press ENTER.
	     player_page.verifySearchForRecordingExist(RecordingChapter);

		///12.The header is displayed with the default color and the logo at the top left cornner of the UI page.
		///// to go back to crecording window handler

	        for (String handler : driver.getWindowHandles()) {
	        		driver.switchTo().window(handler);
	        		break;		
	        }
			
			Thread.sleep(2000);
			player_page.verifyLogoVisibilityAndLocation();
			
			///13.The next result display below the current result in case there is next result.
			player_page.verifyThatNextResultDisplayBelowCurrentResultInCaseThereIsNextResultOneResult(player_page.search_result);
			Thread.sleep(2000);
			
			///14.search results page in the format as follows: "recording name - Search Results".
			driver.switchTo().frame(driver.findElement(By.id("playerContainer")));
			player_page.verifySearchResultPage(recording_to_search);
			
			//12. The search results statistics in the format as follows: "X results found for: search criterion. (XX sec)"
			player_page.verifyResultsStatisticsInFormat(RecordingChapter);
			
		    ///15.The search results on a recording level is displayed in the table with the columns as follows: "Location", "Time", "Context"
			player_page.waitForVisibility(player_page.columns_title_text.get(0));
					player_page.verifySearchColumns();
			
			///16.click on a row:The Tegrity Player page is opened and the recording start playing from the chapter start time.
			player_page.veirfySearchRecordingClickedAndGetsNewTimeLocation(0);
				
			
			for (String handler : driver.getWindowHandles()) {
				driver.switchTo().window(handler);
				break;
			}
		
			///13.The breadcrumb structure is displayed as follows: "> Courses > course name".
			player_page.verifyBreadcrumbsForSearcRecordingAsAdmin(course_name);

			///14.verify return to recordings page
			player_page.returnToRecordingPageByNameAsAdmin(course_name,record);
			
			//15.navigate back to player recording
			driver.navigate().back();
			Thread.sleep(4000);
			player_page.verifyTimeBufferStatusForXSec(2);// check source display
			
			//16.click on "Courses" and verify course page
			player_page.returnToCoursesPageAsAdmin(course);
			////17.navigate back to player then to recordings page
			driver.navigate().back();
			
			Thread.sleep(4000);
			player_page.verifyTimeBufferStatusForXSec(2);// check source display
				
			// 18. check that we are in the record page
			player_page.returnToRecordingPageByNameAsAdmin(course_name,record);
					
			// 19. change the name of the first recording chapter at the edit recored properties 
			record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
			record.clickOnRecordingTaskThenEditRecording();
			date = new Date();
			sdf = new SimpleDateFormat("ddMMyyyyhhmmss");
			String new_recording_text = "NewChapterName" + sdf.format(date); 
			edit_recording.changeFirstChapterRecordingNameToTargetNameNew(new_recording_text);
			Thread.sleep(2000);
			
			//20. return to the relevant course
			admin_view_course_list.moveToCoursesThroughGet(url);
				
			// 21. go back to the player
		    record.verifyFirstExpandableRecording();
		    record.clickOnTheFirstCaptherWithOutTheExpand();
			
			
			// 22.Select the Recording by clicking on one of the chapters
			player_page.verifyTimeBufferStatusForXSec(10);// check source display

				///// to go back to crecording window handler	
				for (String handler : driver.getWindowHandles()) {
						driver.switchTo().window(handler);
				break;		
				}
						
			
			///23.Validate the search field is display at the top right of the UI page below the top navigation bar.
	        player_page.veriySearchBoxLocation();
	      
	        ///24.Validate the text in the Tegrity Player page: "Search in this recording..."
	        player_page.verifySearchBoxHint();
	        
	        //25.Search the "Recording Chapter" from the recording that we mentioned in the preconditions and press ENTER.
	        player_page.searchRecord(RecordingChapter);
				
			///26.The header is displayed with the default color and the logo at the top left cornner of the UI page.
			///// to go back to crecording window handler
	    	
			//27. verify that the search is empty 	
			player_page.verifySearchResultIsEmpty();	
 
			
	        player_page.verifySearchForRecordingExist(new_recording_text);
	        

		///29.The header is displayed with the default color and the logo at the top left cornner of the UI page.
		///// to go back to crecording window handler
	    	
	        //30. verify that the search is not empty 	
	        player_page.verifySearchResultIsNotEmpty();
	        
	        for (String handler : driver.getWindowHandles()) {
        		driver.switchTo().window(handler);
        		break;		
        }
		
	        Thread.sleep(2000);
	            
	        //31. verify that the search is not empty 	
			player_page.verifyLogoVisibilityAndLocation();
			
			///32.The next result display below the current result in case there is next result.
			player_page.verifyThatNextResultDisplayBelowCurrentResultInCaseThereIsNextResultOneResult(player_page.search_result);
			Thread.sleep(2000);
			
			///33.search results page in the format as follows: "recording name - Search Results".
			driver.switchTo().frame(driver.findElement(By.id("playerContainer")));
			player_page.verifySearchResultPage(recording_to_search);
			
			//12. The search results statistics in the format as follows: "X results found for: search criterion. (XX sec)"
			player_page.verifyResultsStatisticsInFormat(new_recording_text);
			
		    ///34.The search results on a recording level is displayed in the table with the columns as follows: "Location", "Time", "Context"
			player_page.waitForVisibility(player_page.columns_title_text.get(0));
					player_page.verifySearchColumns();
			
			///35.click on a row:The Tegrity Player page is opened and the recording start playing from the chapter start time.
			player_page.veirfySearchRecordingClickedAndGetsNewTimeLocation(0);
				
			
			for (String handler : driver.getWindowHandles()) {
				driver.switchTo().window(handler);
				break;
			}
		
			///36.The breadcrumb structure is displayed as follows: "> Courses > course name".
			player_page.verifyBreadcrumbsForSearcRecordingAsAdmin(course_name);


			System.out.println("Done.");
			ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
	   
	}
}
