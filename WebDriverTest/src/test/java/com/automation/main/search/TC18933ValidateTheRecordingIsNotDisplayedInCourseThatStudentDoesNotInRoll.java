package com.automation.main.search;

//precondition student first course must have recordings in recordings tab as well as in student recordings tab


import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.DateFormat;
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

import com.automation.main.page_helpers.BottomFooter;
import com.automation.main.page_helpers.ConfirmationMenu;
import com.automation.main.page_helpers.CopyMenu;
import com.automation.main.page_helpers.CourseSettingsPage;
import com.automation.main.page_helpers.CoursesHelperPage;
import com.automation.main.page_helpers.DeleteMenu;
import com.automation.main.page_helpers.EditRecording;
import com.automation.main.page_helpers.LoginHelperPage;
import com.automation.main.page_helpers.RecordingHelperPage;
import com.automation.main.page_helpers.SearchPage;
import com.automation.main.page_helpers.TopBarHelper;
import com.automation.main.utilities.DriverSelector;

import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import atu.testng.reports.utils.Utils;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;
import junitx.util.PropertyManager;

@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC18933ValidateTheRecordingIsNotDisplayedInCourseThatStudentDoesNotInRoll {

	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}
	
	public DeleteMenu delete_menu;
	public EditRecording edit_recording;
	public BottomFooter bottom_footer;
	public SearchPage search_page;
	public TopBarHelper top_bar_helper;
	public LoginHelperPage tegrity;
	public CoursesHelperPage course;
	public CourseSettingsPage course_settings_page;
	public RecordingHelperPage record;
	public ConfirmationMenu confirm_menu;
	WebDriver driver;
	WebDriverWait wait;
	public static WebDriver thread_driver;
	CopyMenu copy;
	String current_course;
	String targetCourse;
	String clickedRecording;
    DesiredCapabilities capability;
	@BeforeClass
	public void setup() {

		
//		System.setProperty("webdriver.ie.driver", "src/test/resources/IEDriverServer.exe");
//			capability=DesiredCapabilities.internetExplorer();
//			capability.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING,true);
//			
//		driver=new InternetExplorerDriver(capability);
		driver = DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());
		ATUReports.add("selected browser type", LogAs.PASSED, new CaptureScreen( ScreenshotOf.DESKTOP));

		
		//ATUReports.setWebDriver(driver);
		//ATUReports.add("set driver", true);
		tegrity = PageFactory.initElements(driver, LoginHelperPage.class);

		record = PageFactory.initElements(driver, RecordingHelperPage.class);
		copy = PageFactory.initElements(driver, CopyMenu.class);
		
		confirm_menu = PageFactory.initElements(driver, ConfirmationMenu.class);
		
		top_bar_helper = PageFactory.initElements(driver,TopBarHelper.class);
		search_page = PageFactory.initElements(driver, SearchPage.class);
		
		bottom_footer = PageFactory.initElements(driver, BottomFooter.class);
		
		delete_menu = PageFactory.initElements(driver, DeleteMenu.class);
		course_settings_page = PageFactory.initElements(driver, CourseSettingsPage.class);
		edit_recording = PageFactory.initElements(driver, EditRecording.class);
		
		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC18933ValidateTheRecordingIsNotDisplayedInCourseThatStudentDoesNotInRoll at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC18933ValidateTheRecordingIsNotDisplayedInCourseThatStudentDoesNotInRoll at " + DateToStr,
		 "Starting the test: TC18933ValidateTheRecordingIsNotDisplayedInCourseThatStudentDoesNotInRoll at " + DateToStr, LogAs.PASSED, null);	
		
	}
	
	
	@AfterClass
	public void closeBroswer() {
		this.driver.quit();
	}

	
	// description = "get courses list"
	public void initializeCourseObject() throws InterruptedException {

		course = PageFactory.initElements(driver, CoursesHelperPage.class);
		course.courses = course.getStringFromElement(course.course_list);
	}

	
	@Test(description = "TC 18933 Validate The Recording Is Not Displayed In Course That Student Does Not In Roll")
	public void test18933() throws InterruptedException
	{
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);

		//make sure that the course Ba will not be public
		
		tegrity.loginCourses("User1");
		initializeCourseObject();
		
		course.selectCourseThatStartingWith("Ba");
						
		// Make course public
		record.clickOnCourseTaskThenCourseSettings();
		course_settings_page.makeSureThatMakeCoursePublicIsUnSelected();
		course_settings_page.clickOnOkButton();
		record.signOut();
		
		//1. Validate there is recording in this course. Search input specified shall be case-insensitive.
		// Login as User1
		// Copy one recording to Ba
		// Change that recording first chapter to target chapter name
		tegrity.loginCourses("SuperUser");

		
		course.deleteAllRecordingsInCourseStartWith("Ba", 0, record, delete_menu);
		
		course.copyOneRecordingFromCourseStartWithToCourseStartWithOfType("BankValid", "Ba", 0, record, copy, confirm_menu);
		course.verifyRecordingsStatusIsClear("BankValidRecording", 0,record);
		course.selectCourseThatStartingWith("Ba");
		Thread.sleep(1000);
		record.waitUntilFirstRecordingMovingCopyingstatusDissaper();
		
		Thread.sleep(3000);
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
		Thread.sleep(500);
		record.clickOnRecordingTaskThenEditRecording();
		Thread.sleep(2000);
		
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyhhmmss");
		String new_chapter_name = "newname" + sdf.format(date);
		
		edit_recording.changeFirstChapterRecordingNameToTargetName(new_chapter_name);
		Thread.sleep(2000);
		
		record.signOut();
		
		// 2. Log in as STUDENT (User4).
		tegrity.loginCourses("User4");
		Thread.sleep(2000);
		
		// 3. Set the focus to the field with a mouse pointer.
		top_bar_helper.search_box_field.click();
		
		// 4. Search the "Recording Chapter" that we mentioned in the preconditions and press ENTER.
		top_bar_helper.searchForTargetText(new_chapter_name);
		search_page.waitUntilSpinnerImageDisappear();
		search_page.verifyResultContainOneResultWithTargetTitle(new_chapter_name);
		
		// 5. Sign Out.
		top_bar_helper.signOut();
		Thread.sleep(3000);
		
		// 6. Log in as another Student (User2) that does'nt in rolled to the same course that contains the recording that you search earlier.
		tegrity.loginCourses("User2");
		Thread.sleep(2000);
		
		// 7. Search the "Recording Chapter" that we mentioned in the preconditions and press ENTER.
		top_bar_helper.searchForTargetText(new_chapter_name);
		search_page.waitUntilSpinnerImageDisappear();
		
		search_page.verifySearchResultIsEmpty();
		
		// 8. Sign Out.
		top_bar_helper.signOut();
		Thread.sleep(3000);
		
		tegrity.loginCourses("User1");
		
		course.selectCourseThatStartingWith("Ba");
						
		// Make course public
		record.clickOnCourseTaskThenCourseSettings();
		course_settings_page.makeSureThatMakeCoursePublicIsSelected();
		course_settings_page.clickOnOkButton();

		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
	}
}
