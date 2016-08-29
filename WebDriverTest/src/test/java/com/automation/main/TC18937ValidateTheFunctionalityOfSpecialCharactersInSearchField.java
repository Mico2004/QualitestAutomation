package com.automation.main;

//precondition student first course must have recordings in recordings tab as well as in student recordings tab


import java.util.Date;
import java.text.DateFormat;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
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
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;


@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC18937ValidateTheFunctionalityOfSpecialCharactersInSearchField {

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

		
		driver = DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());
		ATUReports.add("selected browser type", LogAs.PASSED, new CaptureScreen( ScreenshotOf.DESKTOP));

		//
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
		
		edit_recording = PageFactory.initElements(driver, EditRecording.class);
		
		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC18937ValidateTheFunctionalityOfSpecialCharactersInSearchField at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC18937ValidateTheFunctionalityOfSpecialCharactersInSearchField at " + DateToStr,
		 "Starting the test: TC18937ValidateTheFunctionalityOfSpecialCharactersInSearchField at " + DateToStr, LogAs.PASSED, null);
		
	}
	
	
	@AfterClass
	public void closeBroswer() {
		driver.quit();
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
	public void loginCourses() throws InterruptedException
	{
		// 1. Validate there is recording in this course. 
		// Login as User1
		// Copy one recording to Ba
		// Change that recording first chapter to target chapter name
		tegrity.loginCourses("SuperUser");
		initializeCourseObject();
		
		course.deleteAllRecordingsInCourseStartWith("Ba", 0, record, delete_menu);
		
		course.copyOneRecordingFromCourseStartWithToCourseStartWithOfType("BankValid", "Ba", 0, record, copy, confirm_menu);
		
		top_bar_helper.clickOnSignOut();
		Thread.sleep(1000);
		
		// 2. Log in as INSTRUCTOR.
		tegrity.loginCourses("User1");
		Thread.sleep(1000);
		
		// 3. Open some course.
		course.selectCourseThatStartingWith("Ba");
		
		// 3.1 wait until the status will disappear
		record.waitUntilFirstRecordingMovingCopyingstatusDissaper();
		// 4. Set the focus to the field with a mouse pointer.
		top_bar_helper.search_box_field.click();
	
		// 5. Change the name of the chapter that we mentioned in the preconditions to "/\[]:;|=,+*?<>".
		Thread.sleep(3000);
		record.selectIndexCheckBox(1);
		Thread.sleep(500);
		record.clickOnRecordingTaskThenEditRecording();
		Thread.sleep(2000);
		

		String new_chapter_name = "/\\[]:;|=,+*?<>";
		
		edit_recording.changeFirstChapterRecordingNameToTargetName(new_chapter_name);
		
		driver.findElements(By.cssSelector("#tegrityBreadcrumbsBox>.ng-scope>.ng-scope.ng-binding")).get(1).click();
		Thread.sleep(2000);
		
		// 6. Search the "/\[]:;|=,+*?<>" chapter in the search field.
		// 6.1. The chapter is'nt displayed.
		String course_url = driver.getCurrentUrl();
		
		top_bar_helper.searchForTargetText(new_chapter_name);
		search_page.waitUntilSpinnerImageDisappear();
		search_page.verifySearchResultIsEmpty();
		
		// 7. Change the name of the chapter that we mentioned in the preconditions to "abc?<>".
		driver.navigate().to(course_url);
		Thread.sleep(4000);
		
		record.selectIndexCheckBox(1);
		Thread.sleep(500);
		
		record.clickOnRecordingTaskThenEditRecording();
		Thread.sleep(2000);
		

		new_chapter_name = "abc?<>";
		
		edit_recording.changeFirstChapterRecordingNameToTargetName(new_chapter_name);
		
		// 8. Search the "abc?<>" chapter in the search field.
		// 8.1. The chapter is'nt displayed.
		top_bar_helper.searchForTargetText(new_chapter_name);
		search_page.waitUntilSpinnerImageDisappear();
		search_page.verifySearchResultIsEmpty();
		
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
		
	}
}
