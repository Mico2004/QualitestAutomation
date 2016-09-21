
package com.automation.main.admin_course_view_recording_tasks;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
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
import com.automation.main.page_helpers.AdvancedServiceSettingsPage;
import com.automation.main.page_helpers.CopyMenu;
import com.automation.main.page_helpers.CoursesHelperPage;
import com.automation.main.page_helpers.DeleteMenu;
import com.automation.main.page_helpers.LoginHelperPage;
import com.automation.main.page_helpers.RecordingHelperPage;
import com.automation.main.page_helpers.TopBarHelper;
import com.automation.main.utilities.DriverSelector;

import java.text.DateFormat;
import java.util.Date;
import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import atu.testng.reports.utils.Utils;

@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC21988ValidateRecordingTasksUIInRecordingsStudentRecordingsAndTestsTabsAsADMIN {

	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}

	public TopBarHelper top_bar_helper;
	public AdvancedServiceSettingsPage advanced_service_settings_page;
	public LoginHelperPage tegrity;
	public CoursesHelperPage course;
	public RecordingHelperPage record;
	public DeleteMenu delete_menu;
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
		advanced_service_settings_page = PageFactory.initElements(driver, AdvancedServiceSettingsPage.class);
		top_bar_helper = PageFactory.initElements(driver, TopBarHelper.class);
		
		wait = new WebDriverWait(driver, 30);
		
		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC21988ValidateRecordingTasksUIInRecordingsStudentRecordingsAndTestsTabsAsADMIN at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC21988ValidateRecordingTasksUIInRecordingsStudentRecordingsAndTestsTabsAsADMIN at " + DateToStr,
		 "Starting the test: TC21988ValidateRecordingTasksUIInRecordingsStudentRecordingsAndTestsTabsAsADMIN at " + DateToStr, LogAs.PASSED, null);	
		
	}
	
	@AfterClass
	public void closeBroswer() {
		driver.quit();
	}


	// @Parameters({"web","title"}) in the future

	
	// description = "get courses list"
	public void initializeCourseObject() throws InterruptedException {

		course = PageFactory.initElements(driver, CoursesHelperPage.class);
		course.courses = course.getStringFromElement(course.course_list);
	}

	@Test (description="TC 21988 Validate Recording Tasks UI In Recordings Student Recordings And Tests Tabs As ADMIN")
	public void loginCourses() throws InterruptedException//
	{
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
		// Precondition
		initializeCourseObject();
		tegrity.loginAdmin("Admin");
		Thread.sleep(4000);
		admin_dashboard_page.clickOnTargetSubmenuAdvancedServices("Advanced Service Settings");
		Thread.sleep(2000);
		advanced_service_settings_page.forceWebElementToBeSelected(advanced_service_settings_page.enable_youtube_integration, "enable youtube integration");
		advanced_service_settings_page.forceWebElementToBeSelected(advanced_service_settings_page.enable_automated_capitioning, "enable automated captioning");
		advanced_service_settings_page.clickOnOkbutton();
		Thread.sleep(2000);
		top_bar_helper.clickOnSignOut();
		
		
		
		// 1. Login as User1.
		tegrity.loginCourses("User1");// log in courses page
		
		
		// 2. Get the full name of the Ab course.
		String target_course_name = course.selectCourseThatStartingWith("Ab");
		String url =  course.getCurrentUrlCoursePage(); 
		System.out.println("Target course name for this test is: " + target_course_name);
		ATUReports.add("Target course name for this test is: "+ target_course_name, LogAs.PASSED, null);
		
		// 3. Logout.
		record.signOut();
		
		// Loop through Full Admin and Help Desk Admin
		for(int i_login=0; i_login<2; i_login++) {
			
			// 4. Login as Full Admin / Help Desk Admin
			if (i_login == 0) {
				tegrity.loginAdmin("Admin");
				Thread.sleep(2000);
			} else {
				tegrity.loginAdmin("HelpdeskAdmin");
				Thread.sleep(2000);
			}
			
			// 5. Click on "view course list" under "courses" section.
			admin_dashboard_page.clickOnTargetSubmenuCourses("View Course List");
		
			
			// 6. move to the course through 
			Thread.sleep(5000);
			admin_dashboard_view_course_list.moveToCoursesThroughGet(url);
			Thread.sleep(1000);
			
			
			// Loop through Recordings, Student Recordings and Tests Tab
			for (int i_tabs = 0; i_tabs<3; i_tabs++) {
				if (i_tabs == 1) {
					record.clickOnStudentRecordingsTab();
					Thread.sleep(2000);
				} else if (i_tabs == 2) {
					record.clickOnTestsTab();
					Thread.sleep(2000);
				}
				
				
				// 8. Make sure no checkbox is checked.
				wait.until(ExpectedConditions.elementToBeClickable(record.getCheckbox()));
				record.verifyAllCheckedboxNotSelected();
				
				
				// 9. Hover over "Recording tasks" menu.
				record.recording_tasks_button.click();
				
				Thread.sleep(3000);
				
				// 10. The "recording tasks" menu opens.
				boolean is_shown = record.isRecordingTasksShown();
				
				if (is_shown) {
					System.out.println("Recording tasks is shown.");
					ATUReports.add("Recording tasks is shown.", "True", "True", LogAs.PASSED, null);
					Assert.assertTrue(true);
				} else {
					System.out.println("Recording tasks is not shown.");
					ATUReports.add("Recording tasks is shown.", "True", "False", LogAs.FAILED, null);
					Assert.assertTrue(false);
				}
				
				// 11. The informartive message: 
				// "To make changes to recording(s) please select one or more recordings from the list below using the checkboxes on the right." 
				// is displayed.
				boolean is_target_message_shown = record.isTargetMessageShownUnderRecordingTasks("To make changes to recording(s) please select one or more recordings from the list below using the checkboxes on the right.");
				
				if (is_target_message_shown) {
					System.out.println("The message: To make changes to recording(s) please select one or more recordings from the list below using the checkboxes on the right. is shown");
					ATUReports.add("Target message: To make changes to recording(s) please select one or more recordings from the list below using the checkboxes on the right.", "True", "True", LogAs.PASSED, null);
					Assert.assertTrue(true);
				} else {
					System.out.println("The message: To make changes to recording(s) please select one or more recordings from the list below using the checkboxes on the right. is not shown");
					ATUReports.add("Target message: To make changes to recording(s) please select one or more recordings from the list below using the checkboxes on the right.", "True", "Fail", LogAs.FAILED, null);
					Assert.assertTrue(false);
				}
				
				
				
				// 12. Validate that the menu items:
				//			"Move",
				//			"Copy",
				//			"delete",
				//			"publish",
				//			"upload to youtube",
				//			"request captions",
				//			"download", 
				//			"edit recording", 
				//			"edit recording properties", 
				//			"share recording"
				// are all displayed and disabled.
				this.checkCorrectEnableDisableStatus(record.isMoveTaskDisableOrEnable(), false, "Move");
				this.checkCorrectEnableDisableStatus(record.isCopyTaskDisableOrEnable(), false, "Copy");
				this.checkCorrectEnableDisableStatus(record.isDeleteTaskDisableOrEnable(), false, "Delete");
				this.checkCorrectEnableDisableStatus(record.isPublishTaskDisableOrEnable(), false, "Publish");
				this.checkCorrectEnableDisableStatus(record.isUploadToYoutubeDisableOrEnable(), false, "Upload to Youtube");
				this.checkCorrectEnableDisableStatus(record.isRequestCaptionsDisableOrEnable(), false, "Request Captions");
				this.checkCorrectEnableDisableStatus(record.isDownloadRecordingDisableOrEnable(), false, "Download");
				this.checkCorrectEnableDisableStatus(record.isEditRecordingDisableOrEnable(), false, "Edit Recording");
				this.checkCorrectEnableDisableStatus(record.isEditRecordingPropertiesDisableOrEnable(), false, "Edit Recording Properties");
				this.checkCorrectEnableDisableStatus(record.isShareRecordingDisableOrEnable(), false, "Share Recording");
				
				// 13. Click on a checkbox of one recording.
				// But first click on search filed to make recording tasks menu go away
				record.searchbox.click();
				record.getCheckbox().click();
				
				Thread.sleep(1000);
				
				// 14. The checkbox is checked.
				boolean is_first_checkbox_selected = record.getCheckbox().isSelected();
				
				if(is_first_checkbox_selected) {
					System.out.println("First checkbox is selected.");
					ATUReports.add("First checkbox.", "Selected.", "Selected.", LogAs.PASSED, null);
					Assert.assertTrue(true);
				} else {
					System.out.println("First checkbox is not selected.");
					ATUReports.add("First checkbox.", "Selected.", "Not selected.", LogAs.FAILED, null);
					Assert.assertTrue(false);
				}
				
				
				// 15. Hover over "Recording tasks" menu.
				record.recording_tasks_button.click();
				Thread.sleep(3000);
				
				// 16. The "recording tasks" menu opens.
				is_shown = record.isRecordingTasksShown();
				
				if (is_shown) {
					System.out.println("Recording tasks is shown.");
					ATUReports.add("Recording tasks is shown.", "True", "True", LogAs.PASSED, null);
					Assert.assertTrue(true);
				} else {
					System.out.println("Recording tasks is not shown.");
					ATUReports.add("Recording tasks is shown.", "True", "False", LogAs.FAILED, null);
					Assert.assertTrue(false);
				}
				
				// 17. Validate that the menu items: 
				//			"Move",
				//			"Copy",
				//			"delete",
				//			"publish",
				//			"upload to youtube",
				//			"request captions", 
				//			"download", 
				//			"edit recording",
				//			"edit recording properties",
				//			"share recording"
				// are all displayed and enabled.
				this.checkCorrectEnableDisableStatus(record.isMoveTaskDisableOrEnable(), true, "Move");
				this.checkCorrectEnableDisableStatus(record.isCopyTaskDisableOrEnable(), true, "Copy");
				this.checkCorrectEnableDisableStatus(record.isDeleteTaskDisableOrEnable(), true, "Delete");
				this.checkCorrectEnableDisableStatus(record.isPublishTaskDisableOrEnable(), true, "Publish");
				this.checkCorrectEnableDisableStatus(record.isUploadToYoutubeDisableOrEnable(), true, "Upload to Youtube");
				this.checkCorrectEnableDisableStatus(record.isRequestCaptionsDisableOrEnable(), true, "Request Captions");
				this.checkCorrectEnableDisableStatus(record.isDownloadRecordingDisableOrEnable(), true, "Download");
				this.checkCorrectEnableDisableStatus(record.isEditRecordingDisableOrEnable(), true, "Edit Recording");
				this.checkCorrectEnableDisableStatus(record.isEditRecordingPropertiesDisableOrEnable(), true, "Edit Recording Properties");
				this.checkCorrectEnableDisableStatus(record.isShareRecordingDisableOrEnable(), true, "Share Recording");
				
				// 18. Check several checkboxes .
				// But first click on search filed to make recording tasks menu go away
				record.searchbox.click();
				record.check_all_checkbox.click();
				
				// 19. The checkbox is checked.
				record.verifyAllCheckedboxSelected();
				
				// 20. Hover over "Recording tasks" menu.
				record.recording_tasks_button.click();
				Thread.sleep(3000);
				
				// 21. The "recording tasks" menu opens.
				is_shown = record.isRecordingTasksShown();
				
				if (is_shown) {
					System.out.println("Recording tasks is shown.");
					ATUReports.add("Recording tasks is shown.", "True", "True", LogAs.PASSED, null);
					Assert.assertTrue(true);
				} else {
					System.out.println("Recording tasks is not shown.");
					ATUReports.add("Recording tasks is shown.", "True", "False", LogAs.FAILED, null);
					Assert.assertTrue(false);
				}
				
				// 22. Validate that the menu items: 
				//				"Move", 
				//				"Copy", 
				//				"delete", 
				//				"publish",
				//				"upload to youtube", 
				//				"request captions",
				//				"download"
				// are displayed and enabled.
				this.checkCorrectEnableDisableStatus(record.isMoveTaskDisableOrEnable(), true, "Move");
				this.checkCorrectEnableDisableStatus(record.isCopyTaskDisableOrEnable(), true, "Copy");
				this.checkCorrectEnableDisableStatus(record.isDeleteTaskDisableOrEnable(), true, "Delete");
				this.checkCorrectEnableDisableStatus(record.isPublishTaskDisableOrEnable(), true, "Publish");
				this.checkCorrectEnableDisableStatus(record.isUploadToYoutubeDisableOrEnable(), true, "Upload to Youtube");
				this.checkCorrectEnableDisableStatus(record.isRequestCaptionsDisableOrEnable(), true, "Request Captions");
				this.checkCorrectEnableDisableStatus(record.isDownloadRecordingDisableOrEnable(), true, "Download");
				
				
				// 23. Verify 
				//				"edit recording", 
				//				"edit recording properties", 
				//				"share recording"
				// are all displayed and disabled.
				this.checkCorrectEnableDisableStatus(record.isEditRecordingDisableOrEnable(), false, "Edit Recording");
				this.checkCorrectEnableDisableStatus(record.isEditRecordingPropertiesDisableOrEnable(), false, "Edit Recording Properties");
				this.checkCorrectEnableDisableStatus(record.isShareRecordingDisableOrEnable(), false, "Share Recording");	
				
				record.searchbox.click();
			}		// End of regular/student/tests tab for()
			
		
			
			// 24. Logout.
			//driver.navigate().back();
			driver.findElement(By.id("SignOutLink")).click();
			
		}	// End of admin/helpdesk admin for()
		

		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);

		
	}
	
	public void checkCorrectEnableDisableStatus(boolean is_enable_disable, boolean need_to_be_enable_disable, String button) {
		if (is_enable_disable == need_to_be_enable_disable) {
			System.out.println("The button: " + button + " is correct.");
			ATUReports.add("The button: " + button, "Correct", "Correct", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("The button: " + button + " is not correct.");
			ATUReports.add("The button: " + button, "Correct", "Incorrect", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
	}

}
