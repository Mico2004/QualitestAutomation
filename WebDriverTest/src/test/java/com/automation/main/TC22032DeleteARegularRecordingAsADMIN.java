package com.automation.main;


import java.util.List;
import java.text.DateFormat;
import java.util.Date;


import org.omg.Messaging.SyncScopeHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
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
import org.testng.internal.Nullable;
import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import atu.testng.reports.utils.Utils;


@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC22032DeleteARegularRecordingAsADMIN {

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
		ATUReports.setWebDriver(driver);
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
		 System.out.println("Starting the test: TC22032DeleteARegularRecordingAsADMIN at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC22032DeleteARegularRecordingAsADMIN at " + DateToStr,
		 "Starting the test: TC22032DeleteARegularRecordingAsADMIN at " + DateToStr, LogAs.PASSED, null);
			
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
		
		// 1. Login as SuperUser.
		tegrity.loginCourses("SuperUser");// log in courses page
		initializeCourseObject();
		
		// 2. Delete all Recordings, Student Recordings and Tests from abc.
		course.deleteAllRecordingsInCourseStartWith("abc", 0, record, delete_menu);
		course.deleteAllRecordingsInCourseStartWith("abc", 2, record, delete_menu);
		course.deleteAllRecordingsInCourseStartWith("abc", 3, record, delete_menu);
		
		// 3. Using course functions copy Recordings, Student Recording and Tests from ValidBank to abc.
		// Copy all recording from Bank Valid Recording to course starting with Ab
		course.copyRecordingFromCourseStartWithToCourseStartWithOfType("BankValidRecording", "abc", 0, record, copy, confirmation_menu);
		// Copy all student recordings from Bank Valid Recording to course starting with Ab
		course.copyRecordingFromCourseStartWithToCourseStartWithOfType("BankValidRecording", "abc", 2, record, copy, confirmation_menu);
		// Copy all tests from Bank Valid Recording to course starting with Ab
		course.copyRecordingFromCourseStartWithToCourseStartWithOfType("BankValidRecording", "abc", 3, record, copy, confirmation_menu);
		
		course.verifyRecordingsStatusIsClear("BankValidRecording",0,record);
		System.out.println("1");  
		course.verifyRecordingsStatusIsClear("BankValidRecording",2,record);
		System.out.println("3");
		course.verifyRecordingsStatusIsClear("BankValidRecording",3,record);
		System.out.println("4");
		

		// 4. Get full name of abc course.
		String source_course_name = course.selectCourseThatStartingWith("abc");
		System.out.println("Target course name for this test is: " + source_course_name);
		ATUReports.add("Target course name for this test is: "+ source_course_name, LogAs.PASSED, null);
			
		// 5. Logout.
		record.signOut();
		
		
		// Loop through login as Admin / Help Disk Admin
		for(int i_login_as_admin=0; i_login_as_admin<2; i_login_as_admin++) {
		
			// 6. Login as Admin.
			if(i_login_as_admin==0) {
				tegrity.loginAdmin("Admin");
				Thread.sleep(5000);
			} else {
				tegrity.loginAdmin("HelpdeskAdmin");
				Thread.sleep(5000);
			}
			
			
			// 7. Click on "view course list" under "courses" section.
			admin_dashboard_page.clickOnTargetSubmenuCourses("View Course List");
			
			// 8. In "All courses" page, search for Ab course.
			Thread.sleep(8000);
			admin_dashboard_view_course_list.searchForTargetCourseName(source_course_name);
			Thread.sleep(3000);
			
			// 9. Click on that course name
			admin_dashboard_view_course_list.clickOnFirstCourseLink();
			Thread.sleep(1000);
			
		
			// Repeat TC for Recordings, Stduent Recording and Tests Tabs
			for(int recording_type=0; recording_type<3; recording_type++) {
				
				if(recording_type==1) {
					record.clickOnStudentRecordingsTab();
				} else if (recording_type==2) {
					record.clickOnTestsTab();
				}
				Thread.sleep(1000);
				
				// recording list before delete the recording
				List<String> recording_list_before_delete_recording = record.getCourseRecordingList(); 
				
				// 8. Click on a checkbox of one recording.
				WebElement checkbox= record.getCheckbox();
				record.ClickOneCheckBoxOrVerifyAlreadySelected(checkbox);
				
				
				String checked_recording_title = null;
				if (recording_type==2) {
					checked_recording_title = driver.findElement(By.id("RecordingTitle1")).getText();
				} else {
					checked_recording_title = record.getFirstRecordingTitle();
				}
				
				
				// 12. Select "Recording Tasks -> Delete".
				record.clickOnRecordingTaskThenDelete();
				Thread.sleep(3000);
				
				// 13. "Delete" window is displayed.
				boolean is_delete_window_closed = delete_menu.isDeleteMenuClose();
				
				if(!is_delete_window_closed) {
					System.out.println("Delete window is opened.");
					ATUReports.add("Delete window.", "Open.", "Open.", LogAs.PASSED, null);
					Assert.assertTrue(true);
				} else {
					System.out.println("Delete window is closed.");
					ATUReports.add("Delete window.", "Open.", "Closed.", LogAs.FAILED, null);
					Assert.assertTrue(false);
				}
				
				if(!(driver instanceof ChromeDriver)) {
					// 14. Verify that only selected recording displayed in "List of Recordings".
					if (recording_type==2) {
						List<String> delete_menu_recording_list_to_delete =  delete_menu.getRecordingList();
						
						if(delete_menu_recording_list_to_delete.size()==1) {
							System.out.println("Verified that only selected recording displayed in List of Recordings: " + checked_recording_title);
							ATUReports.add("Only selected recording displayed in List of Recordings.", "True.", "True.", LogAs.PASSED, null);
							Assert.assertTrue(true);
						} else {
							System.out.println("Not verified that only selected recording displayed in List of Recordings.");
							ATUReports.add("Only selected recording displayed in List of Recordings.", "True.", "False.", LogAs.FAILED, null);
							Assert.assertTrue(false);
						}
					} else {
						List<String> delete_menu_recording_list_to_delete =  delete_menu.getRecordingList();
						
						if((delete_menu_recording_list_to_delete.size()==1) && (delete_menu_recording_list_to_delete.get(0).equals(checked_recording_title))) {
							System.out.println("Verified that only selected recording displayed in List of Recordings: " + checked_recording_title);
							ATUReports.add("Only selected recording displayed in List of Recordings.", "True.", "True.", LogAs.PASSED, null);
							Assert.assertTrue(true);
						} else {
							System.out.println("Not verified that only selected recording displayed in List of Recordings.");
							ATUReports.add("Only selected recording displayed in List of Recordings.", "True.", "False.", LogAs.FAILED, null);
							Assert.assertTrue(false);
						}
					}
				}
				
				
				
				// 15. Click "Delete" button.
				delete_menu.clickOnDeleteButton();
				
				Thread.sleep(2000);
				
				// 16. Delete window is closed.
				is_delete_window_closed = delete_menu.isDeleteMenuClose();
				
				if(is_delete_window_closed) {
					System.out.println("Delete window is closed.");
					ATUReports.add("Delete window.", "Open.", "Closed.", LogAs.PASSED, null);
					Assert.assertTrue(true);
				} else {
					System.out.println("Delete window is opened.");
					ATUReports.add("Delete window.", "Open.", "Open.", LogAs.FAILED, null);
					Assert.assertTrue(false);
				}
				
				// 17. Verify that selected recording is deleted - Selected recording is not displayed in "Recordings" tab.
				List<String> recording_list_after_delete_recording = record.getCourseRecordingList();
				
				if((!recording_list_after_delete_recording.contains(checked_recording_title)) && ((recording_list_before_delete_recording.size() - recording_list_after_delete_recording.size())==1)) {
					System.out.println("Verified that selected recording is deleted and not displayed.");
					ATUReports.add("Recording is not dispaly in recording list.", "True.", "True.", LogAs.PASSED, null);
					Assert.assertTrue(true);
				} else {
					System.out.println("Not verified that selected recording is deleted and not displayed.");
					ATUReports.add("Recording is not dispaly in recording list.", "True.", "False.", LogAs.FAILED, null);
					Assert.assertTrue(false);
				}
		
			}
			
			Thread.sleep(2000);
			// 18. Logout.
			record.signOut();
			
		}
		
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
	}
	
	}