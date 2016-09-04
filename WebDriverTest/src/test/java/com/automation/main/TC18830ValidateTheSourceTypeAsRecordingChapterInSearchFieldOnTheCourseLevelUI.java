package com.automation.main;

//precondition student first course must have recordings in recordings tab as well as in student recordings tab

import java.text.SimpleDateFormat;
import java.util.Date;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import java.text.DateFormat;
import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;


@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC18830ValidateTheSourceTypeAsRecordingChapterInSearchFieldOnTheCourseLevelUI {

	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}
	
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

		
		//ATUReports.setWebDriver(driver);
		//ATUReports.add("set driver", true);
		tegrity = PageFactory.initElements(driver, LoginHelperPage.class);

		record = PageFactory.initElements(driver, RecordingHelperPage.class);
		copy = PageFactory.initElements(driver, CopyMenu.class);
		
		confirm_menu = PageFactory.initElements(driver, ConfirmationMenu.class);
		
		top_bar_helper = PageFactory.initElements(driver,TopBarHelper.class);
		search_page = PageFactory.initElements(driver, SearchPage.class);
		
		bottom_footer = PageFactory.initElements(driver, BottomFooter.class);
		
		edit_recording = PageFactory.initElements(driver, EditRecording.class);
		
		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC18830ValidateTheSourceTypeAsRecordingChapterInSearchFieldOnTheCourseLevelUI at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC18830ValidateTheSourceTypeAsRecordingChapterInSearchFieldOnTheCourseLevelUI at " + DateToStr,
		 "Starting the test: TC18830ValidateTheSourceTypeAsRecordingChapterInSearchFieldOnTheCourseLevelUI at " + DateToStr, LogAs.PASSED, null);
		
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
		// 1. Validate there is recording in this course. Search input specified shall be case-insensitive - PreSet.
		// 2. Log in as INSTRCTOR.
		tegrity.loginCourses("User1");
		initializeCourseObject();
		
		
		
		// 3. Open some course.
		current_course = course.selectCourseThatStartingWith("Ab");
		System.out.println("Current course: " + current_course);
		//course.selectCourse(record);
		
		// 4. Validate the search field is display at the top right of the UI page below the top navigation bar.
		top_bar_helper.verifySearchFieldDisplayedAtTopRight();
		
		// 5. Validate the text in the Course Details page: "Search in this course...".
		top_bar_helper.verifyTargetTextInSearchField("Search in this course...");
		
		// 6. Hover over the search field with the mouse pointer - A hint is displayed to the user: "Search in this course...".
		top_bar_helper.moveToElementAndPerform(top_bar_helper.search_box_field, driver);
		top_bar_helper.verifyWebElementHaveTargetAttributeTitle(top_bar_helper.search_box_field, "Search in this course...");
		Thread.sleep(1000);
		
		// 7. Set the focus to the field with a mouse pointer.
		top_bar_helper.search_box_field.click();
		
		// changing the first chapter
		Thread.sleep(3000);
		int recordNumber = record.checkExistenceOfNonEditRecordingsStatusInRecordings();
		record.selectIndexCheckBox(recordNumber);
		Thread.sleep(1000);
		//String first_chapter_title = driver.findElement(By.cssSelector(".video-wrap")).getText().split("\n")[1];
		//String first_chapter_title =  driver.findElement(By.xpath(".//*[@id='scrollableArea']/div[2]/div/div/div/accordion/div/div[1]/div[2]/div/div[2]/a/div[2]/p[2]")).getText();	
		
		record.clickOnRecordingTaskThenEditRecording();
		Thread.sleep(2000);
		
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyhhmmss");
		String first_chapter_title = "newname" + sdf.format(date);
		
		edit_recording.changeFirstChapterRecordingNameToTargetNameNew(first_chapter_title);
		
		String header_default_color = top_bar_helper.getBackGroundColor(top_bar_helper.header);
		
		// 8. Search the first chapter from the recording that we mentioned in the preconditions and press ENTER.
		top_bar_helper.searchForTargetText(first_chapter_title);
		
		// 8.1. In case the search process takes a long time, the animated spinner icon shall be displayed within the Search results page.
		search_page.verifyLoadingSpinnerImage();
		
		search_page.waitUntilSpinnerImageDisappear();
		search_page.exitInnerFrame();
		
		// 8.2. The header is displayed with the default color and the logo at the top left cornner of the UI page.
		String header_search_page_color = top_bar_helper.getBackGroundColor(top_bar_helper.header);
		if(header_default_color.equals(header_search_page_color)) {
			System.out.println("Verfied header is displayed with the default color.");
			ATUReports.add("Verfied header is displayed with the default color.", "True.", "True.", LogAs.PASSED, null);
		} else {
			System.out.println("Not verfied header is displayed with the default color.");
			ATUReports.add("Verfied header is displayed with the default color.", "True.", "False.", LogAs.FAILED, null);
		}
		
		top_bar_helper.verfiyThatTheLogoAtTheTopLeft();
		
		// 8.3. The tegrity logo is displayed on the bottom footer bar in the left side and the institution logo is displayed on the top left corner of the page.
		bottom_footer.verifyThatTheTegrityLogoDisplayedOnBottomLeftSide();
		
		
		// 8.4. The breadcrumb structure displayed as follows: "> Courses > Course name > X results found for: "search_criterion". (X seconds)".
		search_page.verfiyBreadcrumbStructureDisplayedAsCoursesCoursenameXresultsfound(current_course, first_chapter_title);
		
//		// 8.5. The recording chapter icon is displayed. (the play icon appears with hover over the recording). - Checking the same in step 9
		search_page.moveToElementAndPerform(search_page.video_thumbnails_list.get(0), driver);
		search_page.verifyWebElementDisplayed(driver.findElement(By.cssSelector(".play-button")), "The play icon");
		
		// 8.6. The respective recording chapter snapshot is displayed in the icon - Cannot be automated (TODO: check another way later).
		
		// 8.7. The start time of the chapter within recording is displayed over the chapter icon.
		// 8.8. The time is displayed in the "hh:mm:ss" format.
		search_page.verifyDurationTimeDisplayed();
		
		// TODO: 8.9. The search criterion is displayed in bold. (There is a bug in the system)
		
		// 8.10. The search criterion is displayed with "..." at the beginning/end of search result item title if the search criterion is long.
		search_page.verifyThatTitlesWith3DotsAtTheBeginningEndDisplayed();
		
		// 8.11. The course title in the format as follows: "Course: course_name.
		search_page.verifyDisplayCourseTitleForSearchInsideTargetCourse(current_course);
		
		// 8.12. The recording title in the format as follows: "Recording: recording_title.
		search_page.verifyRecordingTitleDisplayInCorrectFormat();
		
		// 8.13. The source title in the format as follows: " Source: Recording Chapter"
		search_page.verifySourceTitleDisplayInCorrectFormat();
		
		// 8.14. The next result display below the current result in case there is next result.
		search_page.verifyThatNextResultDisplayBelowCurrentResultInCaseThereIsNextResultAddicnalCont();
		
		// 9. Hover over the chapter icon.
		Point before_hovring = search_page.video_wrap_link_to_focus_list.get(0).getLocation();
		search_page.moveToElementAndPerform(search_page.video_wrap_link_to_focus_list.get(0), driver);
		Thread.sleep(2000);
		
		// 9.1. The chapter icon become a bit bigger in size.
		Point after_hovring = search_page.video_wrap_link_to_focus_list.get(0).getLocation();
	
		if((after_hovring.x < before_hovring.x) && (after_hovring.y < before_hovring.y)) {
			System.out.println("Verifed that chapter icon become a bit bigger in size.");
			ATUReports.add("Verifed that chapter icon become a bit bigger in size.", "True.", "True.", LogAs.PASSED, null);
		} else {
			System.out.println("Not verifed that chapter icon become a bit bigger in size.");
			ATUReports.add("Verifed that chapter icon become a bit bigger in size.", "True.", "False.", LogAs.FAILED, null);
		}
		
		// 9.2. The ">" play icon is displayed over the chapter icon at the center.
		search_page.verifyWebElementDisplayed(driver.findElement(By.cssSelector(".play-button")), "The play icon");
		
		// 9.3. The cursor change its view to the hand pointer - Not for automation.
		
		// 9.4. The recording name is displayed as a hint - There is bug in the system
		String text_in_hint_with_recording = search_page.recording_titles_list.get(0).getText();
		String text_in_hint = text_in_hint_with_recording.substring("Recording: ".length());
		search_page.verifyWebElementHaveTargetAttributeTitle(search_page.video_wrap_link_to_focus_list.get(0), text_in_hint);
		
		// 10. Click on the chapter icon.
		search_page.clickOnChapterIconOfRecordingInTargetIndex(1);
		
		// 11. Click on the back cursor in the browser to navigate to the search results page.
		driver.navigate().back();
		search_page.waitUntilSpinnerImageDisappear();
		
		// 12. Click on title of the chapter.
		search_page.clickOnChapterTitleOfRecordingInTargetIndex(1);
		
		// 13. Click on the back cursor in the browser to navigate to the search results page.
		driver.navigate().back();
		search_page.waitUntilSpinnerImageDisappear();
		
		// 14. Click on the recording title of the chapter.
		search_page.clickOnRecordingTitleOfChapterOfRecordingInTargetIndex(1);
		
		// 15. Click on the back cursor in the browser to navigate to the search results page.
		driver.navigate().back();
		search_page.waitUntilSpinnerImageDisappear();
		
		// 16. Click on the course name in the breadcrumb.
		driver.findElements(By.cssSelector("#tegrityBreadcrumbsBox>.ng-scope>.ng-scope.ng-binding")).get(1).click();
		
		// 17. Change the name of the first chapter from the recording that we mentioned in the preconditions.
		Thread.sleep(3000);
		record.selectIndexCheckBox(recordNumber);
		Thread.sleep(500);
		
		
		record.clickOnRecordingTaskThenEditRecording();
		Thread.sleep(2000);
		
		date = new Date();
		String new_chapter_name = "newname" + sdf.format(date);	
		edit_recording.changeFirstChapterRecordingNameToTargetNameNew(new_chapter_name);
		
		Thread.sleep(2000);
		
		
		// 18. Search the first chapter with the new name - The chapter is displayed with all the details that we mentioned.
		top_bar_helper.searchForTargetText(new_chapter_name);
		search_page.waitUntilSpinnerImageDisappear();
		search_page.verifyResultContainOneResultWithTargetTitle(new_chapter_name);
		
		// 19. Search the first chapter with the old name - The chapter is'nt displayed and , the empty search results page shall be displayed.
		top_bar_helper.searchForTargetText(first_chapter_title);
		search_page.waitUntilSpinnerImageDisappear();
		search_page.verifySearchResultIsEmpty();
		
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);

		
	}
}
