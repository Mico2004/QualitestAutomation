package com.automation.main.other;

//precondition student first course must have recordings in recordings tab as well as in student recordings tab

import java.awt.AWTException;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.automation.main.page_helpers.BottomFooter;
import com.automation.main.page_helpers.ConfirmationMenu;
import com.automation.main.page_helpers.CopyMenu;
import com.automation.main.page_helpers.CoursesHelperPage;
import com.automation.main.page_helpers.EditRecording;
import com.automation.main.page_helpers.LoginHelperPage;
import com.automation.main.page_helpers.PlayerPage;
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
public class ValidateTheFunctionalityOfSearchFieldInWeeklySummaryPage {

	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}
	
	public PlayerPage player_page;
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

		
//		System.setProperty("webdriver.ie.driver", "src/test/resources/IEDriverServer.exe");
//			capability=DesiredCapabilities.internetExplorer();
//			capability.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING,true);
//			
//		driver=new InternetExplorerDriver(capability);
		driver = DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());
		ATUReports.add("selected browser type", LogAs.PASSED, new CaptureScreen( ScreenshotOf.DESKTOP));

//		
		//ATUReports.setWebDriver(driver);
		//ATUReports.add("set driver", true);
		tegrity = PageFactory.initElements(driver, LoginHelperPage.class);

		player_page = PageFactory.initElements(driver, PlayerPage.class);
		record = PageFactory.initElements(driver, RecordingHelperPage.class);
		copy = PageFactory.initElements(driver, CopyMenu.class);
		
		confirm_menu = PageFactory.initElements(driver, ConfirmationMenu.class);
		
		top_bar_helper = PageFactory.initElements(driver,TopBarHelper.class);
		search_page = PageFactory.initElements(driver, SearchPage.class);
		
		bottom_footer = PageFactory.initElements(driver, BottomFooter.class);
		
		edit_recording = PageFactory.initElements(driver, EditRecording.class);
		
		
		
	}
	
	
//	@AfterClass
//	public void closeBroswer() {
//		driver.quit();
//	}


	// @Parameters({"web","title"}) in the future
	@Test
	public void loadPage() throws InterruptedException {
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);

	}
	
	// description = "get courses list"
	public void initializeCourseObject() throws InterruptedException {

		course = PageFactory.initElements(driver, CoursesHelperPage.class);
		course.courses = course.getCoursesListFromElement(course.course_list);
	}

	
	@Test(dependsOnMethods = "loadPage", description = "Login course page")
	public void loginCourses() throws InterruptedException
	{
		// 1. Validate there is recording in this course.
		// 2. Log in as INSTRUCTOR.
		tegrity.loginCourses("User1");
		initializeCourseObject();
		
		// 3. Open some Log in as INSTRUCTOR course.
		current_course = course.selectCourseThatStartingWith("Ab");
//		current_course = course.selectCourseThatStartingWith("abc");
		System.out.println("Current course: " + current_course);
		//course.selectCourse(record);
		
		Thread.sleep(2000);
		
		// Get information about first chapter
		record.first_recording_title.click();
		Thread.sleep(1000);
		String first_chapter_title = driver.findElement(By.cssSelector(".video-wrap")).getText().split("\n")[1];
				
		String header_default_color = top_bar_helper.getBackGroundColor(top_bar_helper.header);
		
		
		// Click on "My account" link at the rigth corner of the header.
		top_bar_helper.clickOnMyAccountLink();
		Thread.sleep(1000);
		
		
		// 5. Validate the search field is display at the top right of the UI page below the top navigation bar.
		top_bar_helper.verifySearchFieldDisplayedAtTopRight();
		
		// 6. Validate the text in the Course Details page: "Search in all your courses...".
		top_bar_helper.verifyTargetTextInSearchField("Search in all of your courses...");
		
		// 7. Hover over the search field with the mouse pointer.
		// 7.1. The mouse pointer change to text cursor - Not for automation.
		// 7.2. A hint is displayed to the user: "Search in all your courses...".
		top_bar_helper.moveToElement(top_bar_helper.search_box_field, driver).perform();
		top_bar_helper.verifyWebElementHaveTargetAttributeTitle(top_bar_helper.search_box_field, "Search in all of your courses...");
		Thread.sleep(1000);
		
		// 8. Set the focus to the field with a mouse pointer.
		top_bar_helper.search_box_field.click();
		
		
		
		// 9. Search some "Recording Chapter" and press ENTER.
		top_bar_helper.searchForTargetText(first_chapter_title);
		
		// TODO: 9.1. In case there is no search result found, the empty search results page shall be displayed.
		
		// 9.2. In case the search process takes a long time, the animated spinner icon shall be displayed within the Search results page.
		search_page.verifyLoadingSpinnerImage();
		search_page.waitUntilSpinnerImageDisappear();
		
		// 9.3. The header is displayed with the default color and the logo at the top left cornner of the UI page.
		String header_search_page_color = top_bar_helper.getBackGroundColor(top_bar_helper.header);
		if(header_default_color.equals(header_search_page_color)) {
			System.out.println("Verfied header is displayed with the default color.");
			ATUReports.add("Verfied header is displayed with the default color.", "True.", "True.", LogAs.PASSED, null);
		} else {
			System.out.println("Not verfied header is displayed with the default color.");
			ATUReports.add("Verfied header is displayed with the default color.", "True.", "False.", LogAs.FAILED, null);
		}
		
		top_bar_helper.verfiyThatTheLogoAtTheTopLeft();
		
		// 9.4. The tegrity logo is displayed on the bottom footer bar in the left side and the institution logo is displayed on the top left corner of the page.
		bottom_footer.verifyThatTheTegrityLogoDisplayedOnBottomLeftSide();
		
		
		// 9.5. The breadcrumb structure displayed as follows: "> Courses > Course name > X results found for: "search_criterion". (X seconds)"
		search_page.verfiyBreadcrumbStructureDisplayedAsCoursesXResultsFound(current_course, first_chapter_title);
		
//		// 9.6. The recording chapter icon is displayed. (the play icon appears with hover over the recording). - Checking the same in step 10

		
		// 9.7. The respective recording chapter snapshot is displayed in the icon. - Cannot be automated (TODO: check another way later).
		
		// 9.8. The start time of the chapter within recording is displayed over the chapter icon.
		// 9.9. The time is displayed in the "hh:mm:ss" format.
		search_page.verifyDurationTimeDisplayed();
		
		// TODO: 9.10. The search criterion is displayed in bold. (There is a bug in the system)
		
		// 9.11. The search criterion is displayed with "..." at the beginning/end of search result item title.
		search_page.verifyThatTitlesWith3DotsAtTheBeginningEndDisplayed();
		
		// 9.12. The course title in the format as follows: "Course: course_name.
		search_page.verifyDisplayCourseTitleForSearchInsideTargetCourse(current_course);
		
		// 9.13. The recording title in the format as follows: "Recording: recording_title.
		search_page.verifyRecordingTitleDisplayInCorrectFormat();
		
		// 9.14. The source title in the format as follows: " Source: Recording Chapter".
		search_page.verifySourceTitleDisplayInCorrectFormat();
		
		// 9.15. The next result display below the current result in case there is next result.
//		search_page.verifyThatNextResultDisplayBelowCurrentResultInCaseThereIsNextResult();
		
		// 10. Hover over the chapter icon.
		Point before_hovring = search_page.video_wrap_link_to_focus_list.get(0).getLocation();
		search_page.moveToElement(search_page.video_wrap_link_to_focus_list.get(0), driver).perform();
		Thread.sleep(2000);
		
		// 10.1. The chapter icon become a bit bigger in size.
		Point after_hovring = search_page.video_wrap_link_to_focus_list.get(0).getLocation();

		if((after_hovring.x < before_hovring.x) && (after_hovring.y < before_hovring.y)) {
			System.out.println("Verifed that chapter icon become a bit bigger in size.");
			ATUReports.add("Verifed that chapter icon become a bit bigger in size.", "True.", "True.", LogAs.PASSED, null);
		} else {
			System.out.println("Not verifed that chapter icon become a bit bigger in size.");
			ATUReports.add("Verifed that chapter icon become a bit bigger in size.", "True.", "False.", LogAs.FAILED, null);
		}
		
		// 10.2. The ">" play icon is displayed over the chapter icon at the center.
		search_page.verifyWebElementDisplayed(driver.findElement(By.cssSelector(".play-button")), "The play icon");
		
		// 10.3. The cursor change its view to the hand pointer. - Not for automation.
		
		// 10.4. The recording name is displayed as a hint. - There is bug in the system
		String text_in_hint_with_recording = search_page.recording_titles_list.get(0).getText();
		String text_in_hint = text_in_hint_with_recording.substring("Recording: ".length());
		search_page.verifyWebElementHaveTargetAttributeTitle(search_page.video_wrap_link_to_focus_list.get(0), text_in_hint);
		
		// 11. Click on the chapter icon.
		search_page.clickOnChapterIconOfRecordingInTargetIndex(1);
		
		// 11.1. The Tegrity Player page is opened and the recording start playing from the chapter start time.
		player_page.verifyTimeBufferStatusForXSec(5);
		
		// 12. Click on the back cursor in the browser to navigate to the search results page.
		driver.navigate().back();
		search_page.waitUntilSpinnerImageDisappear();
		
		// 13. Click on title of the chapter.
		search_page.clickOnChapterTitleOfRecordingInTargetIndex(1);
		
		// 13.1. The Tegrity Player page is opened and the recording start playing from the chapter start time.
		player_page.verifyTimeBufferStatusForXSec(5);
		
		
		// 14. Click on the back cursor in the browser to navigate to the search results page.
		driver.navigate().back();
		search_page.waitUntilSpinnerImageDisappear();
		
		// 15. Click on the recording title of the chapter.
		search_page.clickOnRecordingTitleOfChapterOfRecordingInTargetIndex(1);
		
		// 15.1. The Tegrity player page with the opened recording at the relevant time.
		player_page.verifyTimeBufferStatusForXSec(5);
		
		// 16. Click on the back cursor in the browser to navigate to the search results page.
		driver.navigate().back();
		search_page.waitUntilSpinnerImageDisappear();
		
		// 18. Sign Out.
		top_bar_helper.clickOnSignOut();
		
	}
}

