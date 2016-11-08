package com.automation.main;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;import com.automation.main.page_helpers.Page;import com.automation.main.page_helpers.Page;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;

@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC18878ValidateTheSourceTypeAsCloseCaptionInSearchFieldOnTheAllCoursesLevel {

	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}
	
	public PlayerPage player_page;
	public AdminDashboardViewCourseList admin_dashboard_view_course_list;
	public AdminDashboardPage admin_dash_board_page;
	public CourseSettingsPage course_settings_page;
	public AddAdditionalContentLinkWindow add_additional_content_link_window;
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

		record = PageFactory.initElements(driver, RecordingHelperPage.class);
		copy = PageFactory.initElements(driver, CopyMenu.class);
		
		confirm_menu = PageFactory.initElements(driver, ConfirmationMenu.class);
		
		top_bar_helper = PageFactory.initElements(driver,TopBarHelper.class);
		search_page = PageFactory.initElements(driver, SearchPage.class);
		
		bottom_footer = PageFactory.initElements(driver, BottomFooter.class);
		
		edit_recording = PageFactory.initElements(driver, EditRecording.class);
		
		add_additional_content_link_window = PageFactory.initElements(driver, AddAdditionalContentLinkWindow.class);
		course_settings_page = PageFactory.initElements(driver, CourseSettingsPage.class);
		admin_dash_board_page = PageFactory.initElements(driver, AdminDashboardPage.class);
		admin_dashboard_view_course_list = PageFactory.initElements(driver, AdminDashboardViewCourseList.class);
		player_page = PageFactory.initElements(driver, PlayerPage.class);

		
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
	public void loginCourses() throws Exception
	{
		// 1. Validate there is close caption in this course. Search input specified shall be case-insensitive - Upload CloseCaption.
		tegrity.loginCourses("User1");
		initializeCourseObject();
		
		String current_course = course.selectCourseThatStartingWith("Ab");
		
		// Make course public
		record.clickOnCourseTaskThenCourseSettings();
		course_settings_page.makeSureThatMakeCoursePublicIsSelected();
		course_settings_page.clickOnOkButton();
		Thread.sleep(Page.TIMEOUT_TINY);
		
		// Upload for first recording target close catpion
		Thread.sleep(Page.TIMEOUT_TINY);
		List<String> listOfRecorders = record.getCourseRecordingList();
		record.selectIndexCheckBox(4);
		record.clickOnRecordingTaskThenEditRecording();
		
		for(int i=0; i<10; i++) {
			try {
				driver.switchTo().frame(0);
				break;
			} catch (Exception e) {
				Thread.sleep(Page.TIMEOUT_TINY);
			}
		}
		
		for(int i=0; i<20; i++) {
			try {
				if(driver.findElement(By.id("PlayButton_Img")).isDisplayed()) {
					System.out.println("2222");
					break;
				} else {
					Thread.sleep(Page.TIMEOUT_TINY);
				}
			} catch (Exception e) {
				Thread.sleep(Page.TIMEOUT_TINY);
			}
				
		}
		Thread.sleep(Page.TIMEOUT_TINY);
		for(String window_handler: driver.getWindowHandles()) {
			driver.switchTo().window(window_handler);
			break;
		}
		
		driver.findElements(By.cssSelector(".optionList>li>a")).get(4).click();
		
		Thread.sleep(Page.TIMEOUT_TINY);
		
//		driver.findElement(By.id("UploadFile")).click();
//		driver.findElement(By.id("UploadFile")).click();
//		driver.findElement(By.id("UploadFile")).click();
//		driver.findElement(By.id("UploadFile")).click();
////		driver.findElement(By.cssSelector(".jcf-button-content")).click();
//		robot.keyPress(KeyEvent.VK_ENTER);
//		robot.keyRelease(KeyEvent.VK_ENTER);
//		Thread.sleep(Page.TIMEOUT_TINY);
		// click on the upload link
		// click on the upload link
		WebElement element = driver.findElement(By.xpath(".//*[@id='AddCaptioningForm']/div[3]/span"));
		Actions actions = new Actions(driver);
		actions.moveToElement(element).click().perform();
		
		
		Robot robot = new Robot();
		robot.mouseMove(-100, 100);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		Thread.sleep(Page.TIMEOUT_TINY);
		
		String path = "C:\\WebDriverTest\\workspace\\QualitestAutomation\\resources\\documents\\CloseCaption.srt";
		
		// from here you can use as it wrote
//		path = System.getProperty("user.dir") + path;
		StringSelection ss = new StringSelection(path);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
		
		// native key strokes for CTRL, V and ENTER keys
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		Thread.sleep(Page.TIMEOUT_TINY);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		
		Thread.sleep(Page.TIMEOUT_TINY);
		driver.findElement(By.id("AddCaptioning")).click();
		Thread.sleep(Page.TIMEOUT_TINY);
		
		for(String window_handler: driver.getWindowHandles()) {
			driver.switchTo().window(window_handler);
			break;
		}
		
		while(record.isElementPresent(By.cssSelector("#ModalDialogHeader"))){
			
			WebElement ie = record.getStaleElem(By.cssSelector("#ModalDialogHeader"),driver);		
			if(ie.getText().contains("Success")){
				confirm_menu.clickOnOkButtonAfterAddCloseCaptioning();
				break;
			   }			
			else Thread.sleep(Page.TIMEOUT_TINY);
		}

		String text_from_caption_for_test = "QualitestAutomationCaption";
		record.signOut();
		
		
		// Looping for INSTRUCTOR, Student and Guest
		for(int type_of_user = 0; type_of_user < 3; type_of_user++) {
			if(type_of_user == 0) {
				// 2. Login as INSTRUCTOR.
				tegrity.loginCourses("User1");
			} else if (type_of_user == 1) {
				// 2. Login as Student.
				tegrity.loginCourses("User4");
			} else if (type_of_user == 2) {
				// 2. Login as guest
				tegrity.loginAsguest();
			}
			Thread.sleep(Page.TIMEOUT_TINY);	
			
			// 3. Set the focus to the field with a mouse pointer.
			top_bar_helper.search_box_field.click();
			
			// 4. Search some "Closed Caption" and press ENTER.
			top_bar_helper.searchForTargetText(text_from_caption_for_test);
			
			// 4.1. In case the search process takes a long time, the animated spinner icon shall be displayed within the Search results page.
			search_page.verifyLoadingSpinnerImage();
			search_page.waitUntilSpinnerImageDisappear();
			
			// 4.2. The breadcrumb structure displayed as follows: "> Courses > X results found for: "search_criterion". (X seconds)".
			search_page.verfiyBreadcrumbStructureDisplayedAsCoursesXResultsFound(current_course, text_from_caption_for_test);
			
			// 4.3. The recording chapter icon is displayed.
			search_page.verifyWebElementDisplayed(search_page.video_thumbnails_list.get(0), "The recording capter icon");
			
			// 4.4. The respective recording chapter snapshot is displayed in the icon - Cannot be automated (TODO: check another way later).
			// 4.5. The time of the caption within recording is displayed over the chapter icon.
			search_page.verifyDurationTimeDisplayed();
			
			// 4.6. The course title in the format as follows: "Course: course_name.
			search_page.verifyDisplayCourseTitleForSearchInsideTargetCourse(current_course);
			
			// 4.7. The recording title in the format as follows: "Recording: recording_title.
			search_page.verifyRecordingTitleDisplayInCorrectFormat();
			
			// 4.8. The source title in the format as follows: " Source: Closed Caption".
			search_page.verifyThatSourceTitleInTheFormatSourceClosedCaption();
			
			// 4.9. The next result display below the current result in case there is next result.
			search_page.verifyThatNextResultDisplayBelowCurrentResultInCaseThereIsNextResult();
			
			// 5. Click on the Closed Caption icon.
			search_page.clickOnChapterIconOfRecordingInTargetIndex(1);
			
			// 5.1. The Tegrity Player page is opened and the recording start playing from the caption timestam.
			player_page.verifyTimeBufferStatusForXSec(5);
			
			// 6. Click on the back cursor in the browser to navigate to the search results page.
			driver.navigate().back();
			search_page.waitUntilSpinnerImageDisappear();
			
			// 7. Click on title of the Closed Caption.
			search_page.exitInnerFrame();
			search_page.clickOnChapterTitleOfRecordingInTargetIndex(1);
			
			// 7.1. The Tegrity Player page is opened and the recording start playing from the caption timestam.
			player_page.verifyTimeBufferStatusForXSec(5);
			
			// 8. Click on the back cursor in the browser to navigate to the search results page.
			driver.navigate().back();
			search_page.waitUntilSpinnerImageDisappear();
			
			// 9. Click on the recording title of the chapter.
			search_page.exitInnerFrame();
			search_page.clickOnRecordingTitleOfChapterOfRecordingInTargetIndex(1);
			
			// 9.1. The Tegrity player page with the opened recording at the relevant time.
			player_page.verifyTimeBufferStatusForXSec(5);
			
			// 10. Click on the back cursor in the browser to navigate to the search results page.
			driver.navigate().back();
			search_page.waitUntilSpinnerImageDisappear();
			
			// 11. Sign Out.
			search_page.exitInnerFrame();
			top_bar_helper.clickOnSignOut();
		}
		
		// Unpublic Ab course1. 
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
