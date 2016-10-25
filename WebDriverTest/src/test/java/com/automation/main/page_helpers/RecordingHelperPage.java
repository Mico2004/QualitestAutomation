package com.automation.main.page_helpers;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Listeners;
//import com.automation.objects.RecordingObject;
import org.testng.asserts.LoggingAssert;
import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;
import java.io.StringReader;
import java.util.Set;
import java.util.concurrent.TimeoutException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class RecordingHelperPage extends Page {
	@FindBy(xpath = "//*[@id=\"wrapper\"]/div[1]")
	public WebElement background;
	@FindBy(id = "StartRecordingButton")
	public WebElement recording_button;
	@FindBy(linkText = "Recording Tasks")
	public WebElement recording_tasks_button;
	@FindBy(id = "CourseTask")
	public WebElement course_tasks_button;
	@FindBy(id = "CourseSettings")
	public WebElement course_settings_button;
	@FindBy(id = "Podcast")
	public WebElement podcast_button;
	@FindBy(linkText = "Copy")
	public WebElement copy_button;
	@FindBy(id = "DeleteTask2")
	public WebElement delete_button;
	@FindBy(id = "DeleteTask")
	public WebElement content_tasks_delete_button;
	@FindBy(xpath= "//*[@id='RecordedBy1']")
	public WebElement FirstRecordByName;
	@FindBy(id = "Checkbox1")
	public WebElement checkbox;
	@FindBy(xpath = "//*[@id=\"CopyButton\"]")
	public WebElement copy_menu_recbtn;
	@FindBy(id = "tegritySearchBox")
	public WebElement searchbox;
	@FindBy(id = "CheckAll")
	public WebElement checkall;
	@FindBy(xpath = "//*[starts-with(@id,'Checkbox')]")
	public List<WebElement> checkboxlist;// @FindBy(css="//input[@type='checkbox']")// List<WebElement> checkboxlist;
	@FindBy(xpath = "//*[@id='Recording1']/strong")
	public WebElement first_recording_title;
	@FindBy(xpath = "//*[@id='Recording1']/strong")
	WebElement first_course_title;
	@FindBy(xpath = "//*[@id='Recording2']/strong")
	WebElement second_course_title;
	@FindBy(xpath = "//*[@id='RecordingTitle1']/strong")
	WebElement first_course_title_tests;
	@FindBy(css="#tegrityBreadcrumbsBox>.ng-scope>.ng-scope.ng-binding")
	public WebElement courses_link;
	@FindBy(id = "CheckAll")
	public WebElement check_all_checkbox;
	@FindBy(css = ".ng-pristine.ng-valid")
	public List<WebElement> recordings_list_checkbox;
	@FindBy(id = "CourseTask")
	public WebElement course_task_button;
	@FindBy(id = "SignOutLink")
	public  WebElement signout_link;
	@FindBy(id = "RecordingStatus1")
	public WebElement first_recording_status;
	@FindBy(id = "StudentRecordingsTab")
	public WebElement student_recordings_tab;
	@FindBy(id = "TestsTab")
	public  WebElement tests_tab;
	@FindBy(id = "AdditionalContentTab")
	public WebElement additional_content_tab;
	@FindBy(id = "MoveTask2")
	public WebElement move_button_on_recording_tasks_menu;
	@FindBy(id = "CourseSettings")
	WebElement course_setting_in_menu;
	@FindBy(id = "RecordingStatus1")
	public WebElement course_being_copied_status;
	@FindBy(id = "RecordingLength1")
	WebElement duration_first_rec;
	@FindBy(xpath = "//*[starts-with(@id,'RecordingLength')]")
	List<WebElement> recordings_list_duratuon;
	@FindBy(xpath = "//*[starts-with(@id,'RecordingDate')]")
	List<WebElement> recordings_list_date;
	@FindBy(xpath = "//*[starts-with(@id,'RecordedBy')]")
	List<WebElement> recordings_list_recordr_name;/// recorder name list
	public List<String> recordings_list_date_string;
	public List<String> recording_list_names;
	public List<String> recording_list_duration_string;
	List<String> recordings_list_recorder_name_string;
	@FindBy(xpath = "//*[@id=\"Recording1\"]/strong ")
	public WebElement first_recording; /// first recording
	@FindBy(xpath = "//*[@id=\"scrollableArea\"]/div[2]/div/div/div/accordion/div/div[1]/div[2]/div/div[2]/a")
	WebElement first_video_recording;/// first video in first recording
	@FindBy(xpath = ".//*[@id='scrollableArea']/div[2]/div/div/div/accordion/div/div[1]/div[2]/div/div[3]/a/div[2]")
	WebElement second_record_player;
	@FindBy(xpath = "//*[@id=\"scrollableArea\"]/div[2]/div/div/div/accordion/div/div[1]/div[2]/div/div[2]/a/div[2]")
	WebElement firsr_record_player_name;
	@FindBy(id = "CourseTask")
	WebElement course_task;
	@FindBy(id = "SortingTasks")
	public WebElement view_button; /// view button
	@FindBy(id = "SortingTasks")
	WebElement view_buttonTest;
	@FindBy(linkText = "Title")
	public WebElement sort_by_title; /// title sort
	@FindBy(linkText = "Duration")
	public WebElement sort_by_duration; /// title sort
	@FindBy(linkText = "Date")
	public WebElement sort_by_date; /// title sort
	@FindBy(id = "RecordingsTab")
	public WebElement recording_font_title;///
	@FindBy(linkText = "Move")
	public WebElement move_button;///
	@FindBy(id = "ContentTasks")
	public WebElement content_tasks_button;
	@FindBy(id = "UserName")
	WebElement user_name;
	@FindBy(id = "CopyTask2")
	public WebElement copy_button2;
	@FindBy (xpath="//*[@class=\"recordingInfoContainer ng-scope\"]/div")
	List <WebElement> record_list;
	@FindBy(id = "TestsTab")
	public WebElement test_tab;
	@FindBy(id = "EditRecordingProperties")
	public WebElement edit_rec_properties_button;
	@FindBy(xpath=".//*[@id='tegrityBreadcrumbsBox']/li[2]/a")
	public WebElement courses_admin;
	@FindBy(xpath = "//a[starts-with(@id,'Recording')]")
	List<WebElement> recordings_list;
	@FindBy(xpath = "//a[starts-with(@id,'NewItem')]")
	List<WebElement> addition_content_list;
	@FindBy(id = "tegrityBreadcrumbsBox")
	public  WebElement breadcrumbs;
	@FindBy(id = "RSSFeed")
	public WebElement rssfeed;
	@FindBy(xpath = "//*[@id=\"scrollableArea\"]/div[1]/div[1]/div[2]/div/ul/li/ul/li[10]/span")
	public  WebElement subscribe_button;
	@FindBy(xpath ="//*[@id='scrollableArea']/div[2]/div/div/div/accordion/div/div[1]/div[1]/div/h4/div/div[1]/a/strong")
	public  WebElement firstRec;
	@FindBy(id = "VideoPodcast")
	public WebElement video_podcast;
	@FindBy(id = "PublishTask")
	public WebElement publish_button;
	@FindBy(id = "TagsListTask2")
	public WebElement tag_button;
	@FindBy(id = "DownloadRecording")
	public WebElement download_button;
	@FindBy(id = "EditRecording")
	public WebElement edit_rec_button;
	@FindBy(id = "ShareRecording")
	public WebElement share_recording_button;
	@FindBy(id = "StartRecordingButton")
	public WebElement start_recording_button;
	@FindBy(id = "StartTestButton")
	public WebElement start_test_button;
	@FindBy(xpath=".//*[@id='tegrityBreadcrumbsBox']/li/a")
	public WebElement course_link;
	@FindBy(id = "UploadRecording")
	public WebElement upload_recording;
	@FindBy(id = "UploadVideoFile")
	public WebElement upload_video_file;
	@FindBy(id = "UploadAudioFile")
	public WebElement upload_audio_file;
	@FindBy(id = "AddAdditionalContentFile")
	public WebElement Add_Additional_Content_File;
	@FindBy(id = "AddAdditionalContentLink")
	public WebElement Add_Additional_Content_Link;
	@FindBy(id = "GetLiveWebcast")
	public WebElement get_live_webcast;
	public List<String> additional_content_list_names;
	@FindBy(xpath = "//a[starts-with(@id,'NewItem')]")
	public
	List<WebElement> additional_content_list;
	@FindBy(id = "RecordingsTab")
	public WebElement recordings_tab;
	@FindBy(id = "UploadFile")
	public WebElement select_upload_additional_file;
	@FindBy(id = "AddFileButton")
	public WebElement add_additional_file_button;
	@FindBy(id = "CourseTitle")
	public WebElement course_title;
	@FindBy(id = "NewItem1")
	public WebElement first_additional_content_title;
	@FindBy(id = "Checkbox2")
	public WebElement checkbox2;
	@FindBy(id = "AddAdditionalContentLink")
	public WebElement add_additional_content_link;
	@FindBy(id = "DisclaimerLink")
	public WebElement disclaimer;
	@FindBy(id = "HelpLink")
	public WebElement help;
	@FindBy(id = "ReportsLink")
	public WebElement reports;
	@FindBy(id = "MyAccountLink")
	public WebElement my_account;
	@FindBy(id = "AddAdditionalContentFile")
	public WebElement add_additional_content_file;
	@FindBy(id = "uploadToYoutube")
	public WebElement upload_to_youtube_button;
	@FindBy(id = "requestCaptions")
	public WebElement request_captions_button;
	@FindBy(id = "DownloadRecording")
	public WebElement download;
	@FindBy(css = ".bookmark")
	List<WebElement> bookmarks_list;
	@FindBy(css=".video-outer:nth-of-type(2)")
	public WebElement visibleFirstChapter;
	@FindBy(css = ".video-thumbnail")
	List<WebElement> visibleChapters;
	@FindBy(id="scrollableArea")
	WebElement TabContainer;
	public @FindBy(xpath =".//*[@id='scrollableArea']/div[1]/div[1]/div[2]/div/ul/li/ul/li[10]/span") WebElement SubscribeToACourse;
	public @FindBy(css = ".resume-button.ng-scope>a") List<WebElement> list_of_resume_buttons;
	public @FindBy(css = ".video-wrap") List<WebElement> video_wraps_of_chapters_of_opened_recording_list;
	public @FindBy(css = ".thumbnail-image") List<WebElement> images_thumbnail_of_recording_chapters_list;
	public @FindBy(id = "TegrityLogo") WebElement tegrity_logo;
	public @FindBy(css = ".ng-scope>.ng-scope.ng-binding") WebElement breadcrumbs_courses_link;
	public @FindBy(id = "InstituteLogotype") WebElement institute_logo;
	public @FindBy(css = ".dropdown-menu.text-left>div>li>span") WebElement view_menu_tags_text;
	public ConfirmationMenu confirm_menu;
	public CopyMenu copyMenu;
	
	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}

	public RecordingHelperPage(WebDriver browser) {
		super(browser);
		confirm_menu = PageFactory.initElements(driver, ConfirmationMenu.class);	
		// TODO Auto-generated constructor stub
	}

	public void deleteAllRecordings(DeleteMenu delete_menu) throws InterruptedException {
		try {
			Thread.sleep(1000);
			System.out.println("deleteAllRecordings1");
			wait.until(ExpectedConditions.visibilityOf(check_all_checkbox));
			System.out.println("deleteAllRecordings2");
			int number_of_recordings_in_target_course = getNumberOfRecordings();

			System.out.println("Number of recordings to delete: " + number_of_recordings_in_target_course);

			if (number_of_recordings_in_target_course == 0) {
				System.out.println("There are no recordings in target course.");
				ATUReports.add("Recordings.", "There are no recordings in target course.",
						"There are no recordings in target course.", LogAs.PASSED, null);
			} else {
				System.out.println("There are recordings in target course, starting deletion of all of them");
				ATUReports.add("Recordings.", "There are recordings in target course, starting deletion of all of them",
						"There are recordings in target course, then it will delete them all.", LogAs.PASSED, null);
						
				checkAllCheckBox();
				clickOnRecordingTaskThenDelete();	
				
				delete_menu.clickOnDeleteButton();				
				Thread.sleep(1000);;
			}
		} catch (Exception msg) {
			System.out.println("Failed to check the checkbox and delete all recordings"+msg.getLocalizedMessage());
			ATUReports.add("Recordings.", "Failed to check the checkbox and delete all recordings",
					"Failed to check the checkbox and delete all recordings", LogAs.WARNING, null);
		}
	}

	public boolean checkRecordingInIndexIStatus(int index, String status) {
		String recording_status = driver.findElement(By.id("RecordingStatus" + Integer.toString(index))).getText();
		System.out.println(recording_status);
		if (recording_status.equals(status)) {
			System.out.println(
					"Recordings in index: " + index + " status is correct - " + recording_status + " == " + status);
			ATUReports.add(
					"Recordings in index: " + index + " status is correct - " + recording_status + " == " + status,
					LogAs.PASSED, null);
			Assert.assertEquals(recording_status, status);
			return true;
		}

		System.out.println(
				"Recordings in index: " + index + " status is not correct - " + recording_status + " != " + status);
		ATUReports.add(
				"Recordings in index: " + index + " status is not correct - " + recording_status + " != " + status,
				LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));

		Assert.assertEquals(recording_status, status);
		return false;
	}

	// This function check for existence in status of any recording for t time
	// If after t time there is still status, then it will Assert error + log it
	// Otherewise it will pass
	public void checkStatusExistenceForMaxTTime(int time_in_sec) throws InterruptedException {
		System.out.println("Begin Status Check");	
		try{
		new WebDriverWait(driver, 7).until(ExpectedConditions.textToBePresentInElement(driver.findElement(By.id("wrapper")), "length: "));
		}catch(org.openqa.selenium.TimeoutException msg){
			ATUReports.add("There are no recordings in this course tab ",LogAs.PASSED, null);
			return;
		}		
		for (int i = 0; i < (time_in_sec / 3 + 1); i++) {
			if (!checkExistenceOfStatusInRecordings()) {
				System.out.println("There is no more status for any recording.");
				ATUReports.add("There is no more status for any recording.", LogAs.PASSED, null);
				System.out.println("after There is no more status for any recording.");
				Assert.assertTrue(true);	
				return;
			}
			System.out.println("Status Check iteration"+i);
			Thread.sleep(3000);
		}

		System.out.println("There is still status for some recording.");
		ATUReports.add("There is still status for some recording.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		Assert.assertFalse(true);
	}


	// This function check for all recordings if they are clickable.
	// if all recordings clickable it return true,
	// otherwise it will return false
	public boolean checkIfAllRecordingsClickable() {
		int number_of_recordings = driver.findElements(By.cssSelector(".recordingData")).size();

		for (int i = 1; i <= number_of_recordings; i++) {
			try {
				if (driver.findElement(By.id("Recording" + Integer.toString(i))).getAttribute("disabled")
						.equals(true)) {
					System.out.println("Not all recordings is clickable.");
					ATUReports.add("Not all recordings is clickable.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
					Assert.assertTrue(false);
					return false;
				}
			} catch (java.lang.NullPointerException msg) {

			}
		}
		System.out.println("All recordings is clickable.");
		ATUReports.add("All recordings is clickable.", LogAs.PASSED, null);
		Assert.assertTrue(true);
		return true;
	}

	// This function check for all recordings if any of them have status.
	// If there is recording that have status it will return true,
	// otherwise it will return false
	public boolean checkExistenceOfStatusInRecordings() throws InterruptedException {
		int i = 1;
		System.out.println("Begin checkExistenceOfStatusInRecordings");
		Thread.sleep(1000);
		for (WebElement e : driver.findElements(By.cssSelector(".recordingData"))) {			    		
			if(isElementPresent(By.id(("RecordingStatus")+ Integer.toString(i)))){
				WebElement recordStatus =getStaleElem(By.id("RecordingStatus"+ Integer.toString(i)), driver,5);
				String current_element = getTextFromWebElement(recordStatus,5);						
				if (!current_element.equals("")) {
					return true;
				}
				i++;
			   } 		
			}
		return false;
	}
	
	public void returnToCourseListPage(CoursesHelperPage course) throws InterruptedException {		
		
		try{		
			System.out.println("returnToCourseListPage1");		
			wait.until(ExpectedConditions.visibilityOf(courses_link));		
			courses_link.click();			
			Thread.sleep(2000);		
			System.out.println("returnToCourseListPage2");	
			ATUReports.add("Clicked on Courses link.", "Go back to courses page",		
					"Go back to courses page", LogAs.PASSED, null);		
			wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("ActiveCourses"))));
			System.out.println("returnToCourseListPage3");	
			wait.until(ExpectedConditions.textToBePresentInElementLocated(By.id("wrapper"), "recordings -"));
			System.out.println("returnToCourseListPage4");	
		}		
		 catch (Exception msg) {		
			 System.out.println("returnToCourseListPage5");	
			try{
				driver.navigate().refresh();		
			wait.until(ExpectedConditions.visibilityOf(courses_link));		
			courses_link.click();	
			}catch(Exception e){		
				System.out.println("Not clicked on Courses link." + msg.getMessage() );		
				ATUReports.add("Not clicked on Courses link.", "Courses page heading",		
						"Not found courses page heading. Page url: " + driver.getCurrentUrl(), LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));		
			}		
			System.out.println("returnToCourseListPage6");			
					
		}		
		System.out.println("returnToCourseListPage7");	
	}

	// This function clicks on title of recording in index i
	public void clickOnRecordingTitleInIndex(int index) throws InterruptedException {
		System.out.println("Click on title in index: " + index);
		WebElement element=first_recording;
		String id="Recording" + Integer.toString(index);
		try {
			waitForVisibility(element);
			((JavascriptExecutor) driver).executeScript("document.getElementById(\""+id+"\").click();");
			System.out.println("The screen chapter was expaned");
			ATUReports.add("The screen chapter was expaned", LogAs.PASSED, null);							
			Thread.sleep(1500);
			Assert.assertTrue(true);
			return;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("The screen chapter wasn't expaned");
			ATUReports.add("The screen chapter wasn't expaned", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
		
	}
	
	// This function clicks on first recording title
	public void clickOnRecordingInIndex(int index) {
		driver.findElement(By.xpath("//*[@id='Recording" + Integer.toString(index) + "']/strong")).click();
	}

	// This function clicks on first recording title
	public void clickOnFirstRecordingTitle() {
		first_recording_title.click();

	}

	// This function return first recording title
	public String getFirstRecordingTitle() {
		try{
			waitForVisibility(first_recording_title);
		}catch (Exception e){
			ATUReports.add("Timeout for first recording", e.getMessage(),
					LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE) );
			Assert.assertTrue(false);
			return null;
		}
		return first_recording_title.getText();
	}
	
	public String getFirstRecordingTitleTest() {
		wait.until(ExpectedConditions.visibilityOf(first_course_title_tests));
		return first_course_title_tests.getText();
	}
	
	public String getSecondRecordingTitleTest() {
		wait.until(ExpectedConditions.visibilityOf(first_course_title_tests));
		return second_course_title.getText();
	}
	
	public String getFirstRecordingByName() {
		wait.until(ExpectedConditions.visibilityOf(FirstRecordByName));
		return FirstRecordByName.getText();
	}
	
	// This function waits until first recording status "Being Copied from"
	// dissaper
	public void waitUntilFirstRecordingBeingCopiedFromStatusDissaper() throws InterruptedException {
		int time_counter = 0;
		String statusName = getTextFromWebElement(first_recording_status,5);
		while (statusName.contains("Being copied from")) {
			time_counter++;
			Thread.sleep(1000);
			
			if (time_counter > 220) {
				System.out.println("Timeout - Being copied from still appears after 220 seconds");
				ATUReports.add("Timeout - Being copied from still appears after 220 seconds", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Assert.assertTrue(false);
				return;
			} else {
				statusName = getTextFromWebElement(first_recording_status,5);
			}
		}

		System.out.println("Being copied from disappeared from first recording.");
		ATUReports.add("Being copied from dissapear from first recording.", LogAs.PASSED, null);
		Assert.assertTrue(true);
	}

	// This function click on Course Task then on Settings in the sub menu
	public void clickOnCourseTaskThenPodcast() throws InterruptedException {

		waitForVisibility(course_tasks_button);
		course_tasks_button.sendKeys(Keys.TAB);

		for (int i = 0; i < 10; i++) {
			course_tasks_button.sendKeys(Keys.TAB);// solution
			try {
				podcast_button.click();
				if (driver.getWindowHandles().size() > 1)// check if list of
															// courses are
															// present
				{
					for(String handle: driver.getWindowHandles()) {
						driver.switchTo().window(handle);
						if(driver.getPageSource().contains("feedHeader")) { 
							break;
						}
					}
					
					ATUReports.add("Clicked on podcast.", LogAs.PASSED, null);
					System.out.println("Clicked on podcast.");
					Assert.assertTrue(true);
					break;
				}
			} catch (Exception e) {
			}
			moveToElementAndClick(course_tasks_button, driver);// solution to solve hover and click

			Thread.sleep(1000);
			try {

				podcast_button.click();
				ATUReports.add("Click succeeded.", LogAs.PASSED, null);
				System.out.println("Click succeeded.");
			} catch (Exception e) {
				ATUReports.add("Click failed.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				System.out.println("Click failed.");
			}
		}

	}

	// This function click on Course Task then on Settings in the sub menu
	public void clickOnCourseTaskThenCourseSettings() throws InterruptedException {
		WebElement element=course_tasks_button;
		String id="CourseSettings";
		try {
			System.out.println("clickOnRecordingTaskThen1");
			waitForVisibility(element);
			System.out.println("Afterwait");
			((JavascriptExecutor) driver).executeScript("document.getElementById(\""+id+"\").click();");
			System.out.println("course_settings displayed");
			ATUReports.add("Select Recording Tasks -> "+id+" menu items", id+" window is displayed",
					id+" window is displayed", LogAs.PASSED, null);
			Thread.sleep(1500);
			Assert.assertTrue(true);
			return;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("clickOnRecordingTaskThen6");
			ATUReports.add("Select Recording Tasks -> "+id+" menu items", id+" window is displayed",
					id+" window isn't displayed", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			System.out.println(id+" window not displayed");
			Assert.assertTrue(false);
		}

	}

	// This function click on Recorind Task then on delete in the sub menu
	public void clickOnRecordingTaskThenDelete() throws InterruptedException {
		
		WebElement element=recording_tasks_button;
		String id="DeleteTask2";
		try {
			System.out.println("clickOnRecordingTaskThen1");
			waitForVisibility(element);
			((JavascriptExecutor) driver).executeScript("document.getElementById(\""+id+"\").click();");
			System.out.println("clickOnRecordingTaskThen1");
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ModalDialogHeader")));
			System.out.println("Delete window displayed");
			ATUReports.add("Select Recording Tasks -> "+id+" menu items", id+" window is displayed",
					id+" window is displayed", LogAs.PASSED, null);
			Assert.assertTrue(true);
			return;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("clickOnRecordingTaskThen6");
			ATUReports.add("Select Recording Tasks -> "+id+" menu items", id+" window is displayed",
					id+" window isn't displayed", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			System.out.println(id+" window not displayed");
			Assert.assertTrue(false);
		}
	}

	// This function click on Recorind Task then on copy in the sub menu
	public void clickOnRecordingTaskThenCopy() throws InterruptedException {
		
		WebElement element=recording_tasks_button;
		String id="CopyTask2";
		try {
			System.out.println("clickOnRecordingTaskThen1");
			waitForVisibility(element);
			((JavascriptExecutor) driver).executeScript("document.getElementById(\""+id+"\").click();");
			System.out.println("clickOnRecordingTaskThen1");
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ModalDialogHeader")));
			System.out.println("copy window displayed");
			ATUReports.add("Select Recording Tasks -> "+id+" menu items", id+" window is displayed",
					id+" window is displayed", LogAs.PASSED, null);
			Assert.assertTrue(true);
			return;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("clickOnRecordingTaskThen6");
			ATUReports.add("Select Recording Tasks -> "+id+" menu items", id+" window is displayed",
					id+" window isn't displayed", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			System.out.println(id+" window not displayed");
			Assert.assertTrue(false);
		}
	
}

	// This function return all recordings of current course
	
	public List<String> getCourseRecordingList() {

		List<String> recording_names_list = new ArrayList<String>();

		try {
			new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(first_recording_title));
			
		} catch (Exception msg) {
			System.out.println("TimeoutGetCourseRecordingList");
		}

		for (int i = 0; i < recordings_list.size(); i++) {
			System.out.println("getCourseRecordingListfor"+i);
			String current_name = recordings_list.get(i).getText();
			if (current_name.equals("Recordings")) {
				System.out.println("getCourseRecordingListif"+i);
				continue;
			} else if (current_name.equals("Recording Tasks")) {
				System.out.println("getCourseRecordingListelse"+i);
				continue;
			}
			recording_names_list.add(current_name);
		}

		return recording_names_list;
	}

	// This function return number of recordings
	public int getNumberOfRecordings() {
		return getCourseRecordingList().size();
	}

	// This function clicks on checkbox which is checking all checkbox.
	
	public List<String> checkAllCheckBox()
 {
		System.out.println("checkAllCheckBox1");
		wait.until(ExpectedConditions.visibilityOf(check_all_checkbox));
		wait.until(ExpectedConditions.elementToBeClickable(check_all_checkbox));
		System.out.println("checkAllCheckBox2");		
		((JavascriptExecutor) driver).executeScript("document.getElementById(\"CheckAll\").click();");		
		System.out.println("checkAllCheckBox4");
		List<String> recording_names_list = getCourseRecordingList();
		
		for (int i = 0; i < recording_names_list.size(); i++) {
			int j = i + 1;
			String checkbox_indexed = "Checkbox" + Integer.toString(j);
			if (!driver.findElement(By.id(checkbox_indexed)).isSelected()) {
				System.out.println("Not all recording checked.");
				ATUReports.add("Select several recordings", "All recording checked", "Not all recording checked.",
						LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Assert.assertTrue(false);

				return recording_names_list;
			}
		}

		System.out.println("All recording checked.");
		ATUReports.add("All recording checked.", "All recording checked", "All recording checked", LogAs.PASSED,
				null);
		Assert.assertTrue(true);

		return recording_names_list;
	}

	// This function get String as recording name, and compare this string to
	// all title of all recordings in course.
	// It will return true if the recording found. Otherwise it will return
	// false.
	
public boolean isRecordingExist(String recording_name, boolean need_to_be_exists) {
		
		//wait.until(ExpectedConditions.visibilityOf(first_course_title));
		recording_list_names = convertRecordingsListToNames();
		if (recording_list_names.contains(recording_name)) {
			if (need_to_be_exists == true) {
				System.out.println("Recording is exist.");
				ATUReports.add("Recording is exist.", LogAs.PASSED, null);
				Assert.assertTrue(true);
				return true;
			} else {
				System.out.println("Recording is exist.");
				ATUReports.add("Recording is exist.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Assert.assertTrue(false);
				return false;
			}

		}
		if (need_to_be_exists == true) {
			System.out.println("Recording is not exist.");
			ATUReports.add("Recording is not exist.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
			return false;
		} else {
			System.out.println("Recording is not exist.");
			ATUReports.add("Recording is not exist.", LogAs.PASSED, null);
			Assert.assertTrue(true);
			return true;
		}
	}
	

	// This function clicks on coures link, and return to courses list page
	public void returnToCourseListPage() throws InterruptedException {
		System.out.println("returnToCourseListPage0");
		for (int i = 0; i < 5; i++) {
			try {
				wait.until(ExpectedConditions.visibilityOf(courses_link));
				courses_link.click();
				Thread.sleep(1000);
				break;
			} catch (Exception msg) {
				System.out.println("Catch returnToCourseListPage"+msg.getMessage());				
				
			}
		}		

		try {
			WebElement courses_title = driver.findElement(By.id("CoursesHeading"));

			if (courses_title.getText().equals("Courses")) {
				System.out.println("Click on Courses link.");
				ATUReports.add("Click on Courses link.", "Courses page heading", "Courses page heading", LogAs.PASSED,
						null);
				Assert.assertTrue(true);
			} else {
				System.out.println("Not clicked on Courses link.");
				ATUReports.add("Not clicked on Courses link.", "Courses page heading",
						"Found courses page heading, but not correct. Page heading: " + courses_title.getText(),
						LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Assert.assertTrue(false);
			}
		} catch (Exception msg) {
			System.out.println("Not clicked on Courses link.");
			ATUReports.add("Not clicked on Courses link.", "Courses page heading",
					"Not found courses page heading. Page url: " + driver.getCurrentUrl(), LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}

	// This function clicks on signout link, and return to login page
	public void clickOnSignOut() throws InterruptedException {
		try {
		//	signout_link.click();
			((JavascriptExecutor) driver).executeScript("document.getElementById(\"SignOutLink\").click();");
			System.out.println("Click on signout link.");
			ATUReports.add("Click on signout link.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} catch (Exception e) {
			System.out.println("Not clicked on signout link.");
			ATUReports.add("Not clicked on signout link.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}

		Thread.sleep(8000);
	}

	// This function get recording name.
	// It check if the recording name is exist as first recording, if so it
	// returns true.
	// Otherwise it return false.
	public boolean isRecordingExistAsTopRecording(String recording_name, boolean need_to_be_exists) {

		this.waitForVisibility(first_recording_title);

		String newest_recording = first_recording_title.getText();

		if (recording_name.equals(newest_recording)) {
			if (need_to_be_exists == true) {
				ATUReports.add("Recording is exists.", LogAs.PASSED, null);
				Assert.assertTrue(true);
				return true;
			} else {
				ATUReports.add("Recording is exists.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Assert.assertTrue(false);
				return false;
			}
		}
		if (need_to_be_exists == true) {
			ATUReports.add("Recording is not exists.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
			return false;
		} else {
			ATUReports.add("Recording is not exists.", LogAs.PASSED, null);
			Assert.assertTrue(true);
			return true;
		}
	}
	
	
	public void mouseHoverJScript(WebElement HoverElement) {
		
			if (isElementPresent(HoverElement)) {
					
				String mouseOverScript = "if(document.createEvent){var evObj = document.createEvent('MouseEvents');evObj.initEvent('mouseover', true, false); arguments[0].dispatchEvent(evObj);} else if(document.createEventObject) { arguments[0].fireEvent('onmouseover');}";
				((JavascriptExecutor) driver).executeScript(mouseOverScript,HoverElement);

			} else {
				System.out.println("Element was not visible to hover");

			}
		
	}

	public void verifyAllCheckedboxSelected() throws InterruptedException {// verify// all// check// box// are// selected// or// not

		for (WebElement el : checkboxlist) {
			if ((el.isSelected())) {
				continue;
			} else {
				System.out.println("one or more checkboxes are not selected");
				ATUReports.add("one or more checkboxes are not selected", LogAs.FAILED,
						new CaptureScreen(ScreenshotOf.DESKTOP));
				Assert.assertTrue(false);
			}

		}
		System.out.println("all checkboxes are  selected");
		ATUReports.add("all checkboxes are  selected", LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
		Assert.assertTrue(true);

	}

	public void verifyAllCheckedboxNotSelected() {// verify all check box are
													// selected or not
		for (WebElement element : checkboxlist) {
			if ((element.isSelected())) {
				System.out.println("Some/all checkboxes are  selected");
				ATUReports.add("All checkboxes are not selected.", "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Assert.assertTrue(false);
			}

		}
		System.out.println("All checkboxes are not selected.");
		ATUReports.add("All checkboxes are not selected.", "True.", "True.", LogAs.PASSED, null);
		Assert.assertTrue(true);

	}

	// verify copy button is disabled
	public void verifyDisabledMenu() throws InterruptedException {
		try {
			moveToElementAndClick(searchbox, driver);
			waitForVisibility(copy_button);
			copy_button.click();// try to click button if clickable assert false
								// if not clickable
			Assert.assertTrue(false);
			Thread.sleep(2000);
			System.out.println("copy button is enabled");
			ATUReports.add("copy button is enabled", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		} catch (Exception e) {
			Assert.assertTrue(true); // assert true
			System.out.println("copy button is disabled");
			ATUReports.add("copy button is disabled", LogAs.PASSED, null);
		}
	}

	// verify recording menu color
	public void verifyRecordingMenuColor() throws InterruptedException {

		String grey_color = "rgba(128, 128, 128, 1)";
		moveToElementAndClick(recording_tasks_button, driver);
		boolean assertion = verifyColor(grey_color, copy_button);
		if (assertion == true) {
			System.out.println("menu color is grey");
			ATUReports.add("menu color is grey", LogAs.PASSED, null);
		} else {
			ATUReports.add("menu color is not grey", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			System.out.println("menu color is not grey");
		}
		Assert.assertTrue(assertion);// compare
										// 2
										// colors
		Thread.sleep(2000);

	}

	// this function check that recording status in target index is empty
	// with max timeout
	public boolean checkThatRecordingStatusTargetIndexIsEmpty(int index, int time_interval)
			throws InterruptedException {
		for (int i = 0; i < time_interval; i++) {
			WebElement el = getStaleElem(By.id("RecordingStatus" + Integer.toString(index)),driver,5);
			String recording_status = el.getText();
			if (recording_status.equals("")) {
				System.out.println("Recordings in index: " + index + " status empty");
				ATUReports.add("Recordings in index: " + index + " status empty", LogAs.PASSED, null);
				Assert.assertTrue(true);
				return true;
			}
			Thread.sleep(1000);
			
		}

		System.out.println("Recordings in index: " + index + " status not empty");
		ATUReports.add("Recordings in index: " + index + " status not empty", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		Assert.assertTrue(false);
		return false;
	}

	// this function check that recording status in target ind ex is not target
	// status
	// with max timeout
	public boolean checkThatRecordingStatusTargetIndexIsNotXWithTimeout(int index, String is_not, int time_interval)
			throws InterruptedException {
		for (int i = 0; i < time_interval; i++) {
			
			System.out.print("RecordingStatus" + (Integer.toString(index)+1));
			String recording_status = driver.findElement(By.id("RecordingStatus" + (Integer.toString(index)))).getText();
			if (!recording_status.equals(is_not)) {
				System.out.println("Recordings in index: " + index + " is not: " + is_not);
				ATUReports.add("Recordings in index: " + index + " is not: " + is_not, LogAs.PASSED, null);
				Assert.assertTrue(true);
				return true;
			}

			Thread.sleep(1000);
		}
		System.out.println("Recordings in index: " + index + " status is: " + is_not);
		ATUReports.add("Recordings in index: " + index + " status is: " + is_not, LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		Assert.assertTrue(false);
		return false;
	}

	// this function return true if copy displayed in recording tasks menu,
	// and false otherwise
	public boolean isCopyButtonDisplayedInRecordingTasks() {
		try {
			copy_button.isDisplayed();
			return true;
		} catch (org.openqa.selenium.NoSuchElementException msg) {
			return false;
		}
	}

	// thic function clicks on student recordings tab (in type of recordings
	// menu)
	public void clickOnStudentRecordingsTab() {
		WebElement element=student_recordings_tab;
		String id="StudentRecordingsTab";
		try {
			String initialText=TabContainer.getText();
			System.out.println("StudentRecordingsTab1");
			waitForVisibility(element);
			((JavascriptExecutor) driver).executeScript("document.getElementById(\""+id+"\").click();");	
			waitForContentOfTabToLoad(initialText,TabContainer);
			System.out.println("StudentRecordingsTab3");
			ATUReports.add("Select StudentRecordingsTab -> "+id, id+" was click",
					id+" was clicked", LogAs.PASSED, null);
			Assert.assertTrue(true);
			return;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("StudentRecordingsTab4");
			ATUReports.add("Select StudentRecordingsTab -> "+id, id+" was click",
					id+" wasn't clicked", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			System.out.println(id+" was clicked");
			Assert.assertTrue(false);
		}
		

	}

	// This function click on Recorind Task then on move in the sub menu
	public void clickOnRecordingTaskThenMove() throws InterruptedException {

		WebElement element=recording_tasks_button;
		String id="MoveTask2";
		try {
			System.out.println("clickOnRecordingTaskThen1");
			waitForVisibility(element);
			((JavascriptExecutor) driver).executeScript("document.getElementById(\""+id+"\").click();");
			System.out.println("clickOnRecordingTaskThen1");
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ModalDialogHeader")));
			System.out.println("Delete window displayed");
			ATUReports.add("Select Recording Tasks -> "+id+" menu items", id+" window is displayed",
					id+" window is displayed", LogAs.PASSED, null);
			Assert.assertTrue(true);
			return;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("clickOnRecordingTaskThen6");
			ATUReports.add("Select Recording Tasks -> "+id+" menu items", id+" window is displayed",
					id+" window isn't displayed", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			System.out.println(id+" window not displayed");
			Assert.assertTrue(false);
		}
	}

	// this function get index and return status of recording in that index
	public String getIndexRecordingStatus(int index) {
		try {
			String recording_status = driver.findElement(By.id("RecordingStatus" + Integer.toString(index))).getText();
			return recording_status;
		} catch (Exception msg) {
			return null;
		}
	}

	// This function select first recording from recording list
	public void selectIndexCheckBox(int index) throws InterruptedException {	
		try{
		new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.id("Checkbox"+index)));	
		WebElement target_checkbox = driver.findElement(By.id("Checkbox" + Integer.toString(index)));	
		SelectOneCheckBoxOrVerifyAlreadySelected(target_checkbox);
		}
		catch(Exception e){
			System.out.println("catch selectIndexCheckBox");
			ATUReports.add("Fail to select checkbox","Check succeeded","Check failed",LogAs.FAILED,new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			
			
		}
	}

	// verify copied course is displaied correctly
	public void verifyRecordingDisplayedCorrectly(String name) {

		if (recording_list_names.contains(name)) {
			Assert.assertTrue(true);
			ATUReports.add("recording copied successfully", LogAs.PASSED, null);
		} else {
			Assert.assertTrue(false);
			ATUReports.add("recording not copied", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
	}

	// take recording list of elements and extracts its name
	public List<String> convertRecordingsListToNames() {
		int i = 1;
		recording_list_names = new ArrayList<String>();
		for (WebElement el : recordings_list) {
			String recording = el.getText();
			if ((!recording.equals("Recordings")) && (!recording.equals("Recording Tasks"))) {

				recording_list_names.add(recording);

			}
		}
		return recording_list_names; // System.out.println(i);
	}

	// take recording list of elements and extracts its recorder name
	public List<String> convertRecordingsListToRecorderName() {
		int i = 1;
		recordings_list_recorder_name_string = new ArrayList<String>();
		for (WebElement el : recordings_list_recordr_name) {
			String recorder_name = el.getText();

			// date=date.substring(11);
			recordings_list_recorder_name_string.add(recorder_name);

		}
		return recordings_list_recorder_name_string;
	}

	// take recording list of elements and extracts its recording date
	public List<String> convertRecordingsListToDate() {
		int i = 1;
		recordings_list_date_string = new ArrayList<String>();
		for (WebElement el : recordings_list_date) {
			String date = el.getText();
			if ((date.length() < 12)) {
				// date=date.substring(11);
				recordings_list_date_string.add(date);

			} else {
				String date2 = date.substring(date.length() - 10);
				recordings_list_date_string.add(date2);

			}

		}
		return recordings_list_date_string;// System.out.println(i);
	}

	// take recording list of elements and extracts its duration time of
	// recording
	public List<String> convertRecordingsListToDuration() {
		int i = 1;
		recording_list_duration_string = new ArrayList<String>();
		for (WebElement el : recordings_list_duratuon) {
			String duration = el.getText();
			// if((!recording.equals("Recordings"))&&(!recording.equals("Recording
			// Tasks")))
			{
				duration = trimDuration(duration);
				recording_list_duration_string.add(duration);
				// System.out.println(el.getText());
				i++;
			}
		}
		return recording_list_duration_string;
	}

	/// create new recording list object
/*	public List<RecordingObject> createRecordingObjectList() {
		convertRecordingsListToDuration();
		convertRecordingsListToDate();
		convertRecordingsListToNames();
		convertRecordingsListToRecorderName();
		List<RecordingObject> recording_list_object = new ArrayList<RecordingObject>();
		for (int i = 0; i < recordings_list_date.size(); i++) {
			RecordingObject object = new RecordingObject(recordings_list_date_string.get(i),
					recording_list_names.get(i), recording_list_duration_string.get(i),
					recordings_list_recorder_name_string.get(i));
			recording_list_object.add(object);
		}
		return recording_list_object;

	}*/

	// trim duration string to get only the duration time in string
	public String trimDuration(String s) {
		String[] arr = s.split(" ");
		return arr[1];
	}

	/// verify recording is expandable
	public void verifyFirstExpandableRecording() throws InterruptedException {	

		      System.out.println("Click on title in index: " + 1);
				WebElement element=first_recording;
				String id="Recording1";
				try {
					waitForVisibility(element);
					((JavascriptExecutor) driver).executeScript("document.getElementById(\""+id+"\").click();");
					System.out.println("recording chapter was expaned");
					ATUReports.add("recording chapter was expaned", LogAs.PASSED, null);							
					Thread.sleep(1500);
					Assert.assertTrue(true);
					return;
				} catch (Exception e) {
					System.out.println(e.getMessage());
					System.out.println("recording chapter wasn't expaned");
					ATUReports.add("recording chapter wasn't expaned", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
					Assert.assertTrue(false);
				}
		         
		       if (isElementPresent(firsr_record_player_name)) {
		           ATUReports.add("video recording was displayed", LogAs.PASSED, null);
		           System.out.println("video recording was displayed");
		           Assert.assertTrue(true);
		       } else {
		           ATUReports.add("video recording was not displayed", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		           System.out.println("video recording was not displayed");
		           Assert.assertTrue(false);
		       }
	 }
	
	// press on view button and than select option
	public void pressViewButtonAndSelect(String choice) throws InterruptedException {

		WebElement element=view_buttonTest;
		String id = null;
		try {	
			waitForVisibility(element);
			switch (choice) {
			case "Title":				
				id= "Title";
				break;
			case "Date":
				id = "Date";
				break;
			case "Duration":
				id = "Duration";
				break;

			}
			String script=
			"var aTags = document.getElementsByTagName(\'a\');"
			+ " var searchText = \""+id+"\";"
			+ "  var found;"
			+ " for (var i = 0; i < aTags.length; i++)"
			+ " {"
			+ "if"
			+ " (aTags[i].textContent == searchText)"
			+ " {found = aTags[i];break;}"
			+ "}"
			+ " $(aTags[i]).click();";
			
			((JavascriptExecutor) driver).executeScript(script);
			System.out.println("press on sort record" + choice);	
			ATUReports.add("Select sort_recording_tab -> "+choice, choice+" was click",
					choice+" was clicked", LogAs.PASSED, null);
			Assert.assertTrue(true);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			ATUReports.add("click not succeded ", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			System.out.println(choice+" was clicked");
			Assert.assertTrue(false);
		}

	}

	/// check if sorted alphabatically
	public void verifyRecordingSortedByTitle(List<String> title) {
		String previous = ""; // empty string: guaranteed to be less than or
								// equal to any other

		for (String current : title) {

			if (current.compareToIgnoreCase(previous) < 0) {
				ATUReports.add("not sorted by title...  ", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Assert.assertTrue(false);
			}
			previous = current;
		}
		ATUReports.add("sorted by title correctly ", LogAs.PASSED, null);
		Assert.assertTrue(true);
	}

	/// check sort by date
	public void verifyRecordingSortedByDate(List<String> date_list) {
		String[] previous = { "0", "0", "0" }; // empty string: guaranteed to be
												// less than or equal to any
												// other

		for (String current : date_list) {
			String[] splitdate = current.split("/");
			if ((Integer.valueOf(splitdate[2]) < Integer.valueOf(previous[2])))/// check
																				/// years
			{
				System.out.println("Not sorted by date correctly.");
				ATUReports.add("Sorted by date correctly.", "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Assert.assertTrue(false);
			} else {
				if ((Integer.valueOf(splitdate[2]) == Integer.valueOf(previous[2])))// if
																					// years
																					// equal
																					// check
																					// months
				{
					if ((Integer.valueOf(splitdate[0]) < Integer.valueOf(previous[0]))) {
						System.out.println("Not sorted by date correctly.");
						ATUReports.add("Sorted by date correctly.", "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
						Assert.assertTrue(false);
					} else {
						if ((Integer.valueOf(splitdate[0]) == Integer.valueOf(previous[0])))// if
																							// years
																							// equal
																							// check
																							// days
						{
							if ((Integer.valueOf(splitdate[1]) < Integer.valueOf(previous[1]))) {
								System.out.println("Not sorted by date correctly.");
								ATUReports.add("Sorted by date correctly.", "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
								Assert.assertTrue(false);
							}
						}

					}
				}
			}
			previous[0] = splitdate[0];
			previous[1] = splitdate[1];
			previous[2] = splitdate[2];

		}
		System.out.println("Sorted by date correctly.");
		ATUReports.add("Sorted by date correctly.", "True.", "True.", LogAs.PASSED, null);
		Assert.assertTrue(true);
	}

	public void verifyRecordingSortedByDuration(List<String> dur_list) {
		String[] previous = { "0", "00", "00" }; // empty string: guaranteed to
													// be less than or equal to
													// any other

		for (String current : dur_list) {
			String[] splitdate = current.split(":");
			if ((Integer.valueOf(splitdate[0]) < Integer.valueOf(previous[0])))/// check
																				/// years
			{
				System.out.println("Not sorted by duration...");
				ATUReports.add("Not sorted by duration...", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Assert.assertTrue(false);
			} else {
				if ((Integer.valueOf(splitdate[0]) == Integer.valueOf(previous[0])))// if
																					// years
																					// equal
																					// check
																					// months
				{
					if ((Integer.valueOf(splitdate[1]) < Integer.valueOf(previous[1]))) {
						System.out.println("Not sorted by duration...");
						ATUReports.add("Not sorted by duration...  ", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
						Assert.assertTrue(false);
					} else {
						if ((Integer.valueOf(splitdate[1]) == Integer.valueOf(previous[1])))// if
																							// years
																							// equal
																							// check
																							// days
						{
							if ((Integer.valueOf(splitdate[2]) < Integer.valueOf(previous[2]))) {
								System.out.println("SNot sorted by duration...");
								ATUReports.add("Not sorted by duration...  ", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
								Assert.assertTrue(false);
							}
						}

					}
				}
			}
			previous[0] = splitdate[0];
			previous[1] = splitdate[1];
			previous[2] = splitdate[2];

		}
		System.out.println("Sorted by duration correctly.");
		ATUReports.add("Sorted by duration correctly.", LogAs.PASSED, null);
		Assert.assertTrue(true);
	}

	/// verify if recorder name of source recording matches the destination copy
	public void VerifyRecorderNameAsOriginal(String name) {
		for (String original_recorder : recordings_list_recorder_name_string) {

			if (original_recorder.equals(name)) {

				ATUReports.add("recorder name equals the original recorder ", LogAs.PASSED, null);
				Assert.assertTrue(true);
				return;
			}

		}
		ATUReports.add("recorder  doesn't name equals the original recorder name ", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		Assert.assertTrue(false);
	}

	public void recordingPageBoldFont() {
		System.out.println(recording_font_title.getCssValue("font-weight"));
		if ((recording_font_title.getCssValue("font-weight").equals("bold"))
				|| (recording_font_title.getCssValue("font-weight").equals("700"))) {
			ATUReports.add("font style is bold ", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {

			ATUReports.add("font style is not bold", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}

	}

	/// selects recording by name
	public void selectRecordingByName(String original_recorder_name) throws InterruptedException {
		try{
		wait.until(ExpectedConditions.textToBePresentInElementLocated(By.id("wrapper"), "recorded by"));
		}catch(Exception e)
		{
			System.out.println("There are not recordings in the course tab");
			ATUReports.add("Select recording","RecordingName: "+original_recorder_name,"Recording clicked","There are no recordings in the course tab",LogAs.FAILED,null);
			Assert.assertTrue(false);
		}			
		WebElement recording=null;		
		System.out.println(original_recorder_name);
		try{
			for (WebElement el : recordings_list) {		
			System.out.println(el.getText());
			if ((el.getText().equals(original_recorder_name))) {				
				recording= driver.findElement(By.linkText((original_recorder_name)));			
				waitForVisibility(recording);				
				clickElementJS(recording);		
				System.out.println(" Recording found");
				ATUReports.add(" Recording found", LogAs.PASSED, null);
				Assert.assertTrue(true);
				return;
			}
		}
		
		}catch(WebDriverException e){
			handlesClickIsNotVisible(recording);
			waitForVisibility(visibleFirstChapter);
			System.out.println(" no such recording found");
			ATUReports.add("no such recording", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
			
		}
	}
	
	public String selectRecordingThatChangeFromThatName(String original_recorder_name) throws InterruptedException {
		try{
		wait.until(ExpectedConditions.textToBePresentInElementLocated(By.id("wrapper"), "recorded by"));
		}catch(Exception e)
		{
			System.out.println("There are not recordings in the course tab");
			ATUReports.add("Select recording","RecordingName: "+original_recorder_name,"Recording clicked","There are no recordings in the course tab",LogAs.FAILED,null);
			Assert.assertTrue(false);
		}			
		WebElement recording=null;		
		String differentRecordName = null;
		System.out.println(original_recorder_name);
		try{
			for (WebElement el : recordings_list) {		
			System.out.println(el.getText());
			if (!(el.getText().equals(original_recorder_name)) && !(el.getText().equals("Recordings")) && !(el.getText().equals("Recording Tasks"))) {
				differentRecordName = el.getText();
				System.out.println(" Recording found");
				ATUReports.add(" Recording found", LogAs.PASSED, null);
				Assert.assertTrue(true);
				break;
			}
		
		}
		
		}catch(WebDriverException e){
			handlesClickIsNotVisible(recording);
			waitForVisibility(visibleFirstChapter);
			System.out.println(" no such recording found");
			ATUReports.add("no such recording", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
			
		}
		return differentRecordName;
	}

	
	
	
		
	// verify move menu
	public void toMoveMenu() throws InterruptedException {

		// waitForVisibility(recording_tasks_button);
		// moveToElementAndClick(recording_tasks_button, driver);
		// for (int i = 0; i < 10; i++) {
		// recording_tasks_button.sendKeys(Keys.TAB);// solution
		// try { // to
		// move_button.click(); // solve
		//
		// Thread.sleep(1000);
		// if (isElementPresent(By.id("ModalDialogHeader"))) {
		// System.out.println("Move menu confirmed");
		// ATUReports.add("click succeeded", LogAs.PASSED, null);
		// }
		//
		// return;
		// } catch (Exception e) {
		//
		// }
		// }
		// // hover
		// // and
		// // click
		//
		// ATUReports.add("click failed ", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		// System.out.println("Move to move menu failed");
		clickOnRecordingTaskThenMove();
	}

	// verify move menu
	public void toMoveMenuByContentTask() throws InterruptedException {

		// waitForVisibility(content_tasks_button);
		// moveToElementAndClick(content_tasks_button, driver);
		// for (int i = 0; i < 10; i++) {
		// content_tasks_button.sendKeys(Keys.TAB);// solution
		// try { // to
		// move_button.click(); // solve
		//
		// Thread.sleep(1000);
		// if (isElementPresent(By.id("ModalDialogHeader"))) {
		// System.out.println("Move menu confirmed");
		// ATUReports.add("click succeeded", LogAs.PASSED, null);
		// }
		//
		// return;
		// } catch (Exception e) {
		//
		// }
		// }
		// // hover
		// // and
		// // click
		//
		// ATUReports.add("click failed ", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		// System.out.println("Move to move menu failed");
		clickOnContentTaskThenMove();
	}

	// this function check if index recording is clickable
	public boolean isIndexRecordingClickable(int index) {
		if (driver.findElement(By.id("Recording" + Integer.toString(index))).getAttribute("disabled").equals(true)) {
			return true;
		} else {
			return false;
		}
	}

	// this function get recording name and DeleteMenu object, and delete that
	// recording if it exists.
	public void deleteTargetRecordingIfExist(String recording_name, DeleteMenu delete_menu)
			throws InterruptedException {
		List<String> recording_list = getCourseRecordingList();

		for (int i = 0; i < recording_list.size(); i++) {
			if (recording_list.get(i).equals(recording_name)) {
				selectIndexCheckBox(i + 1);
				clickOnRecordingTaskThenDelete();
				delete_menu.clickOnDeleteButton();
				System.out.println("Recording is found, and deleted: " + recording_name);
				ATUReports.add("Recording is found, and deleted: " + recording_name, LogAs.PASSED, null);
				Assert.assertTrue(true);
			}
		}
	}

	// this function get recording name and select it checkbox
	public void selectTargetRecordingCheckbox(String recording_name) throws InterruptedException {
		wait.until(ExpectedConditions.elementToBeClickable(checkbox));
		List<String> recording_list = getCourseRecordingList();

		for (int i = 0; i < recording_list.size(); i++) {
			if (recording_list.get(i).equals(recording_name)) {
				selectIndexCheckBox(i + 1);
			}
		}
	}

	// This function check if recording in target index is select
	public boolean isIndexRecordingSelected(int index) {
		try {
			driver.findElement(By.id("Checkbox" + Integer.toString(index))).isSelected();
			return true;
		} catch (Exception msg) {
			return false;
		}
	}

	// thic function clicks on additional tab tab (in type of recordings
	// menu)
	public void clickOnAdditionContentTab() {
		WebElement element=additional_content_tab;
		String id="AdditionalContentTab";
		try {
			System.out.println("AdditionalContentTab1");
			String initialText=TabContainer.getText();
			waitForVisibility(element);
			((JavascriptExecutor) driver).executeScript("document.getElementById(\""+id+"\").click();");
			System.out.println("AdditionalContentTab2");	
			waitForContentOfTabToLoad(initialText,TabContainer);
			ATUReports.add("Select additional_content_tab -> "+id, id+" was click",
					id+" was clicked", LogAs.PASSED, null);
			Assert.assertTrue(true);
			return;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("AdditionalContentTab6");
			ATUReports.add("Select additional_content_tab -> "+id, id+" was click",
					id+" wasn't clicked", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			System.out.println(id+" was clicked");
			Assert.assertTrue(false);
		}
	}
		
		
		
		
/*		try {   // old clickOnAdditionContentTab implementation
			Thread.sleep(1000);
			waitForVisibility(additional_content_tab);
			additional_content_tab.click();
			System.out.println("Clicked on additional tab");
			ATUReports.add("Clicked on additional tab", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} catch (WebDriverException msg){
			handlesClickIsNotVisible(additional_content_tab);
			
		}catch (Exception msg) {
			System.out.println("Failed to click on additional tab. ERROR: " + msg);
			ATUReports.add("Failed to click on additional tab. ERROR: " + msg, LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}*/
	

	// This function click on Content Task then on copy in the sub menu
	public void clickOnContentTaskThenCopy() throws InterruptedException {

		WebElement element=content_tasks_button;
		String id="CopyTask";
		try {
			System.out.println("clickOnRecordingTaskThen1");
			waitForVisibility(element);
			((JavascriptExecutor) driver).executeScript("document.getElementById(\""+id+"\").click();");
			System.out.println("clickOnRecordingTaskThen1");
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ModalDialogHeader")));
			System.out.println("Delete window displayed");
			ATUReports.add("Select Content Tasks -> "+id+" menu items", id+" window is displayed",
					id+" window is displayed", LogAs.PASSED, null);
			Assert.assertTrue(true);
			return;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("clickOnRecordingTaskThen6");
			ATUReports.add("Select Content Tasks -> "+id+" menu items", id+" window is displayed",
					id+" window isn't displayed", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			System.out.println(id+" window not displayed");
			Assert.assertTrue(false);
		}
	}

	// This function click on Content Task then on move in the sub menu
	public void clickOnContentTaskThenMove() throws InterruptedException {
		WebElement element=content_tasks_button;
		String id="MoveTask";
		try {
			System.out.println("clickOnRecordingTaskThen1");
			waitForVisibility(element);
			((JavascriptExecutor) driver).executeScript("document.getElementById(\""+id+"\").click();");
			System.out.println("clickOnRecordingTaskThen1");
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ModalDialogHeader")));
			System.out.println("Delete window displayed");
			ATUReports.add("Select Content Tasks -> "+id+" menu items", id+" window is displayed",
					id+" window is displayed", LogAs.PASSED, null);
			Assert.assertTrue(true);
			return;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("clickOnRecordingTaskThen6");
			ATUReports.add("Select Content Tasks -> "+id+" menu items", id+" window is displayed",
					id+" window isn't displayed", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			System.out.println(id+" window not displayed");
			Assert.assertTrue(false);
		}
	}

	// this function return true if move displayed in recording tasks menu,
	// and false otherwise
	public boolean isMoveButtonDisplayedInRecordingTasks() {
		try {
			move_button.isDisplayed();
			return true;
		} catch (org.openqa.selenium.NoSuchElementException msg) {
			return false;
		}
	}

	// this function clicks on tests tab (in type of recordings
	// menu)
	public void clickOnTestsTab() {
		WebElement element=test_tab;
		String id="TestsTab";
		try {
			System.out.println("TestsTab1");
			waitForVisibility(element);
			String initialText=TabContainer.getText();
			((JavascriptExecutor) driver).executeScript("document.getElementById(\""+id+"\").click();");
			System.out.println("TestsTab2");
			waitForContentOfTabToLoad(initialText,TabContainer);
			ATUReports.add("Select TestsTab -> "+id, id+" was click",
					id+" was clicked", LogAs.PASSED, null);
			Assert.assertTrue(true);
			return;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("TestsTab4");
			ATUReports.add("Select TestsTab -> "+id, id+" was click",
					id+" wasn't clicked", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			System.out.println(id+" was clicked");
			Assert.assertTrue(false);
		}

	}

	// this function returns username of logined user
	public String getUserNmae() {
		try {
			return user_name.getText();
		} catch (Exception msg) {
			return null;
		}
	}

	// this function get user name and return index of recording of this user if
	// such recording exist
	public int getTargetUserNameRecordingIndex(String username) {
		int num_of_recordings = getCourseRecordingList().size();

		for (int i = 0; i < num_of_recordings; i++) {
			String recorded_by = driver.findElement(By.id("RecordedBy" + Integer.toString(i + 1))).getText();
			if (recorded_by.equals("recorded by: " + username)) {
				return i + 1;
			}
		}

		return 0;
	}

	// this function get user name and return index of recording of other user
	// if such recording exist
	public int getOtherThenTargetUserNameRecordingIndex(String username) {
		int num_of_recordings = getCourseRecordingList().size();

		for (int i = 0; i < num_of_recordings; i++) {
			String recorded_by = driver.findElement(By.id("RecordedBy" + Integer.toString(i + 1))).getText();
			if (!recorded_by.equals("recorded by: " + username)) {
				return i + 1;
			}
		}

		return 0;
	}

	// This function unselect index recording from recording list
	public void unselectIndexCheckBox(int index) {
		try {
			checkbox = driver.findElement(By.id("Checkbox" + Integer.toString(index)));
			checkbox.click();
			if (checkbox.isSelected()) {
				checkbox.click();
				System.out.println("Checkbox is not selected in index: " + index);
				ATUReports.add("Checkbox not selected in index: " + index, LogAs.PASSED, null);

				Assert.assertTrue(true);
			} else {
				System.out.println("Checkbox not selected in index: " + index);
				ATUReports.add("Checkbox not selected in index: " + index, LogAs.PASSED, null);
				Assert.assertTrue(true);
			}

		} catch (Exception msg) {
			System.out.println("Checkbox not selected in index: " + index);
			ATUReports.add("Checkbox not selected in index: " + index, LogAs.PASSED, null);
			Assert.assertTrue(true);
		}
	}

	public void unselectallCheckbox() {
		try {	
			if (check_all_checkbox.isSelected()) {
				check_all_checkbox.click();
				System.out.println("Checkbox all is not selected");
				ATUReports.add("Checkbox all is not selected", LogAs.PASSED, null);

				Assert.assertTrue(true);
			} else {
				check_all_checkbox.click();
				check_all_checkbox.click();
				System.out.println("Checkbox all is selected");
				ATUReports.add("Checkbox all is selected", LogAs.PASSED, null);
				Assert.assertTrue(true);
			}

		} catch (Exception msg) {
			System.out.println("Checkbox all is not selected");
			ATUReports.add("Checkbox all is not selected", LogAs.PASSED, null);
			Assert.assertTrue(true);
		}
	}
	
	
	
	// This function get recording name.
	// It check if the recording name is exist as first recording, if so it
	// returns true.
	// Otherwise it return false.
	public boolean isRecordingExistAsNewestRecording(String recording_name, boolean need_to_be_exists) {

		this.waitForVisibility(first_course_title);

		String newest_recording = first_course_title.getText();

		if (recording_name.equals(newest_recording)) {
			if (need_to_be_exists == true) {
				ATUReports.add("Recording is exists.", LogAs.PASSED, null);
				Assert.assertTrue(true);
				return true;
			} else {
				ATUReports.add("Recording is exists.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Assert.assertTrue(false);
				return false;
			}
		}
		if (need_to_be_exists == true) {
			ATUReports.add("Recording is not exists.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
			return false;
		} else {
			ATUReports.add("Recording is not exists.", LogAs.PASSED, null);
			Assert.assertTrue(true);
			return true;
		}
	}

	// verify recording menu color
	public void verifyRecordingMenuColor(WebElement el) throws InterruptedException {

		String grey_color = "rgba(128, 128, 128, 1)";
		moveToElementAndClick(recording_tasks_button, driver);

		waitForVisibility(recording_tasks_button);
		mouseHoverJScript(recording_tasks_button);
		//moveToElementAndClick(recording_tasks_button, driver);
		//for (int i = 0; i < 5; i++) {
		//	recording_tasks_button.sendKeys(Keys.TAB);// solution

		//}
		
		boolean assertion = verifyColor(grey_color, el);
		if (assertion == true) {
			System.out.println("Menu color is grey");
			ATUReports.add("Menu color is grey", LogAs.PASSED, null);
		} else {
			System.out.println("Menu color is not grey");
			ATUReports.add("Menu color is not grey", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
		Assert.assertTrue(assertion);// compare
										// 2
										// colors
		Thread.sleep(2000);

	}



	// thic function clicks on student recordings tab (in type of recordings
	// menu)
	public void clickOnTestAdittionalContentTab() {
		// try {
		// additional_content_tab.click();
		// System.out.println("Clicked on test recordings tab");
		// ATUReports.add("Clicked on test recordings tab", LogAs.PASSED, null);
		// Assert.assertTrue(true);
		// } catch (Exception msg) {
		// System.out.println("Failed to click on test recordings tab. ERROR: "
		// + msg);
		// ATUReports.add("Failed to click on test recordings tab. ERROR: " +
		// msg, LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		// Assert.assertTrue(false);
		// }
		clickOnAdditionContentTab();

	}

	// This function waits until first recording status "Being Copied from"
	// dissaper
	public void waitUntilFirstRecordingBeingMovedFromStatusDissaper() throws InterruptedException {
		int time_counter = 0;
		waitForVisibility(first_recording_status);
		while (isElementPresent(By.id("RecordingStatus1"))){
				String currentStatus = getTextFromWebElement(first_recording_status, 5);
			if(currentStatus.contains("Being moved from")) {
				time_counter++;
				Thread.sleep(1000);

			if (time_counter > 260) {
				System.out.println("Timeout - Being moved from still appears after 260 seconds");
				ATUReports.add("Timeout - Being moved from still appears after 260 seconds", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Assert.assertTrue(false);
				return;
			}
		}
			else break;
	}
		System.out.println("Being moved from dissapear from first recording.");
		ATUReports.add("Being moved from dissapear from first recording.", LogAs.PASSED, null);
		Assert.assertTrue(true);
	}

	// verify move menu
	public void toDeleteMenu() throws InterruptedException {
//		waitForVisibility(recording_tasks_button);
//		moveToElementAndClick(recording_tasks_button, driver);
//		for (int i = 0; i < 10; i++) {
//			recording_tasks_button.sendKeys(Keys.TAB);
//			// solution to solve
//			try {
//				delete_button.click();
//
//				Thread.sleep(1000);
//				if (isElementPresent(By.id("ModalDialogHeader"))) {
//					System.out.println("Delete menu confirmed");
//					ATUReports.add("click succeeded", LogAs.PASSED, null);
//					Assert.assertTrue(true);
//				}
//
//				return;
//			} catch (Exception e) {
//				System.out.println("Move to delete menu failed");
//				ATUReports.add("click failed ", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
//			}
//		}
		clickOnRecordingTaskThenDelete();
	}

	// Verify that recooding has an error status
	public void verifyErrorStatus(WebElement element) {
		String status = element.getText();
		if (status.equals("Error")) {
			System.out.println(" recording status is Error");
			Assert.assertTrue(true);
			ATUReports.add("verify recording status is Error", "status", "Error", "Error", LogAs.PASSED, null);
		} else {
			System.out.println(" recording status is not Error");
			ATUReports.add("verify recording status is Error", "status", "Error", "Not Error", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}

	// This function click on Content Task then on delete in the sub menu
	public void clickOnContentTaskThenDelete() throws InterruptedException {

		WebElement element=content_tasks_button;
		String id="DeleteTask";
		try {			
			new WebDriverWait(driver, 120).until((ExpectedConditions.invisibilityOfElementWithText(By.id("scrollableArea"), "Uploading")));
			System.out.println("clickOnRecordingTaskThen1");
			waitForVisibility(element);
			((JavascriptExecutor) driver).executeScript("document.getElementById(\""+id+"\").click();");
			System.out.println("clickOnRecordingTaskThen1");
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ModalDialogHeader")));
			System.out.println("Delete window displayed");
			ATUReports.add("Select Recording Tasks -> "+id+" menu items", id+" window is displayed",
					id+" window is displayed", LogAs.PASSED, null);
			Assert.assertTrue(true);
			return;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("clickOnRecordingTaskThen6");
			ATUReports.add("Select Recording Tasks -> "+id+" menu items", id+" window is displayed",
					id+" window isn't displayed", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			System.out.println(id+" window not displayed");
			Assert.assertTrue(false);
		}
	}

	// verify move menu
	public void toEditRecordingPropertiesMenu() throws InterruptedException {

		WebElement element=recording_tasks_button;
		String id="EditRecordingProperties";
		try {			
			System.out.println("clickOnRecordingTaskThen1");
			waitForVisibility(element);
			((JavascriptExecutor) driver).executeScript("document.getElementById(\""+id+"\").click();");
			System.out.println("clickOnRecordingTaskThen1");
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ModalDialogHeader")));
			System.out.println("Delete window displayed");
			ATUReports.add("Select Recording Tasks -> "+id+" menu items", id+" window is displayed",
					id+" window is displayed", LogAs.PASSED, null);
			Assert.assertTrue(true);
			return;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("clickOnRecordingTaskThen6");
			ATUReports.add("Select Recording Tasks -> "+id+" menu items", id+" window is displayed",
					id+" window isn't displayed", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			System.out.println(id+" window not displayed");
			Assert.assertTrue(false);
		}
		
		
		
		
	}

	// check if recording has a being copied from status
	
	public boolean recordingBeingEditedStatus(WebElement element) {
		String val = element.getText();
		if (val.equals("Recording is being edited.")) {
			System.out.println("has  Recording is being edited status");

			return true;
		}
		System.out.println("dont have Recording is being edited status");

		return false;

	}

	// this function gets recording name and clicks it checkbox
	public int clickCheckBoxByName(String reco) {
		convertRecordingsListToNames();
		int index = recording_list_names.indexOf(reco) + 1;
		driver.findElement(By.id("Checkbox" + Integer.toString(index))).click();
		return index;
	}

	// check if recording has a being copied from status
	public boolean hasBeingcopiedfromStatus(WebElement element) {
		String val = element.getText();
		if (val.equals("Being copied from")) {
			ATUReports.add("check status", "status text", "Being copied from", val, LogAs.PASSED, null);
			System.out.println("has Being copied from status");
			Assert.assertTrue(true);
			return true;
		}
		System.out.println("dont have Being copied from status");
		Assert.assertTrue(false);
		{
			ATUReports.add("check status", "status text", "Being copied from", val, LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));

			return false;
		}
	}

	// check if recording has a being copied from status
	public boolean hasMovingCopyingStatus(WebElement element) {
		String val = element.getText();
		if (val.equals("Moving/Copying")) {
			ATUReports.add("check status", "status text", "Moving/Copying", val, LogAs.PASSED, null);
			System.out.println("has Moving/Copying status");
			Assert.assertTrue(true);
			return true;
		}
		System.out.println("dont have Moving/Copying status");
		Assert.assertTrue(false);
		{
			ATUReports.add("check status", "status text", "Moving/Copying", val, LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));

			return false;
		}
	}

	// verify tabs location side by side
	public boolean tabsLocationVerified() {
		Point course_name = course_title.getLocation();
		Point studtab = student_recordings_tab.getLocation();
		Point testtab = tests_tab.getLocation();
		Point recordings = recordings_tab.getLocation();
		Point additionaltab = additional_content_tab.getLocation();
		
		if ((course_name.getY() < recordings.getY()) && 
			(studtab.getY() == testtab.getY()) && 
			(testtab.getY() == recordings.getY()) && 
			(recordings.getY() == additionaltab.getY()) && 
			(studtab.getX() < testtab.getX()) && 
			(additionaltab.getX() > recordings.getX()) && 
			(studtab.getX() > additionaltab.getX())) 
		{
			System.out.println("Verfied tabs location.");
			ATUReports.add("Verfied tabs location.", "True.", "True.", LogAs.PASSED, null);
//			Assert.assertTrue(true);
			return true;
		} else {
			System.out.println("Not verfied tabs location.");
			ATUReports.add("Verfied tabs location.", "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
//			Assert.assertTrue(true);
			return false;
		}

	}

	// verify there is no Start Recording button
	public void verifyNoStartRecording() {
		try {
			if (start_recording_button.isDisplayed()) {
				ATUReports.add("Click on start recording button.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				System.out.println("Click on start recording button.");
				Assert.assertTrue(false);
			} else {
				ATUReports.add("No start recording button", LogAs.PASSED, null);
				System.out.println("No start recording button");
				Assert.assertTrue(true);
			}
		} catch (Exception e) {
			ATUReports.add("No start recording button", LogAs.PASSED, null);
			System.out.println("No start recording button");
			Assert.assertTrue(true);
		}
	}

	// verify there is no Start Test button
	public void verifyNoStartTest() {
		try {
			if (start_test_button.isDisplayed()) {
				ATUReports.add("Click on start test button.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				System.out.println("Click on start test button.");
				Assert.assertTrue(false);
			} else {
				ATUReports.add("No start test button", LogAs.PASSED, null);
				System.out.println("No start test button");
				Assert.assertTrue(true);
			}
		} catch (Exception e) {
			ATUReports.add("No start test button", LogAs.PASSED, null);
			System.out.println("No start test button");
			Assert.assertTrue(true);
		}
	}

	/// Verify that next menu items are not displayed:1.'Upload recording,2.
	/// 'Add Additional Content File',3. 'Add Additional Content Link'
	/// 4. 'Take In Class Notes',5. 'Get Live Webcast',6. 'Upload Video File',7.
	/// 'Upload Audio File'
	public void verifyNoNextMenuItemsDisplayed() {
		try {

			Robot robot = new Robot();
			robot.delay(1000);
			robot.mouseMove(-1000, 0);
			Thread.sleep(1000);
			moveToElementAndClick(course_tasks_button, driver);
			Thread.sleep(2000);
			if ((upload_recording.isDisplayed()) && (Add_Additional_Content_File.isDisplayed())
					&& (upload_audio_file.isDisplayed()) && (upload_video_file.isDisplayed())
					&& (get_live_webcast.isDisplayed()) && (Add_Additional_Content_Link.isDisplayed())) {
				ATUReports.add("menu items are displayed", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				System.out.println("menu items are displayed");
				Assert.assertTrue(false);
			} else {
				ATUReports.add("Verify that next menu items are not displayed ", LogAs.PASSED, null);
				System.out.println("Verify that next menu items are not displayed");
				Assert.assertTrue(true);
			}
		} catch (Exception e) {
			ATUReports.add("Verify that next menu items are not displayed ", LogAs.PASSED, null);
			System.out.println("Verify that next menu items are not displayed");
			Assert.assertTrue(true);
		}
	}

	/// changing recording ownership
	public void changeRecordingOwnership(ConfirmationMenu confirm_menu, EditRecordinPropertiesWindow erp_window,
			String new_owner, WebElement checkbox_element) throws InterruptedException {
		int i = 1;
		/// check for free status checkbox for edit properties
		while (recordingBeingEditedStatus(driver.findElement(By.id("RecordingStatus" + Integer.toString(i)))) == true) {
			i++;
		}
		WebElement to_click = driver.findElement(By.id("Checkbox" + Integer.toString(i)));
		/// meaning we want first available checkbox to change ownership
		if (checkbox_element == null) {
			to_click.click();
		} else {
			/// meaning we want specific checkbox to change ownership
			checkbox_element.click();
		}
		toEditRecordingPropertiesMenu();
		Thread.sleep(2000);
		erp_window.changeOwner(new_owner);

		erp_window.save_button.click();

		Thread.sleep(11000);

		System.out.println("before ok");
		confirm_menu.clickOnOkButtonAfterConfirmEditRecordingProperties();
		System.out.println("after ok ok");

	}

	// Return true or false if the Recording Tasks menu open
	public boolean isRecordingTasksShown() {
		return driver.findElement(By.xpath(".//*[@id='scrollableArea']/div[1]/div[2]/div/div[1]/div[1]/ul/li/ul"))
				.isDisplayed();
	}

	// Check that target message shown under Recording Tasks
	public boolean isTargetMessageShownUnderRecordingTasks(String target_message) {
		return driver
				.findElement(By.xpath(".//*[@id='scrollableArea']/div[1]/div[2]/div/div[1]/div[1]/ul/li/ul/li[1]/em"))
				.getText().equals(target_message);
	}

	// Check if Move is appear and if it enable or disable under Recording Task
	// menu
	public boolean isMoveTaskDisableOrEnable() {
		try {
			driver.findElement(By.cssSelector(".disabled>#MoveTask2")).isDisplayed();
			return false;
		} catch (Exception msg) {
			driver.findElement(By.id("MoveTask2")).isDisplayed();
			return true;
		}
	}

	// Check if Copy is appear and if it enable or disable under Recording Task
	// menu
	public boolean isCopyTaskDisableOrEnable() {
		try {
			driver.findElement(By.cssSelector(".disabled>#CopyTask2")).isDisplayed();
			return false;
		} catch (Exception msg) {
			driver.findElement(By.id("CopyTask2")).isDisplayed();
			return true;
		}
	}

	// Check if Delete is appear and if it enable or disable under Recording
	// Task menu
	public boolean isDeleteTaskDisableOrEnable() {
		try {
			driver.findElement(By.cssSelector(".disabled>#DeleteTask2")).isDisplayed();
			return false;
		} catch (Exception msg) {
			driver.findElement(By.id("DeleteTask2")).isDisplayed();
			return true;
		}
	}

	// Check if Publish Task is appear and if it enable or disable under
	// Recording Task menu
	public boolean isPublishTaskDisableOrEnable() {
		try {
			driver.findElement(By.cssSelector(".disabled>#PublishTask")).isDisplayed();
			return false;
		} catch (Exception msg) {
			driver.findElement(By.id("PublishTask")).isDisplayed();
			return true;
		}
	}

	// Check if Upload to Youtube is appear and if it enable or disable under
	// Recording Task menu
	public boolean isUploadToYoutubeDisableOrEnable() {
		try {
			driver.findElement(By.cssSelector(".disabled>#uploadToYoutube")).isDisplayed();
			return false;
		} catch (Exception msg) {
			driver.findElement(By.id("uploadToYoutube")).isDisplayed();
			return true;
		}
	}

	// Check if Request Captions is appear and if it enable or disable under
	// Recording Task menu
	public boolean isRequestCaptionsDisableOrEnable() {
		try {
			driver.findElement(By.cssSelector(".disabled>#requestCaptions")).isDisplayed();
			return false;
		} catch (Exception msg) {
			driver.findElement(By.id("requestCaptions")).isDisplayed();
			return true;
		}
	}

	// Check if Download Recording is appear and if it enable or disable under
	// Recording Task menu
	public boolean isDownloadRecordingDisableOrEnable() {
		try {
			driver.findElement(By.cssSelector(".disabled>#DownloadRecording")).isDisplayed();
			return false;
		} catch (Exception msg) {
			driver.findElement(By.id("DownloadRecording")).isDisplayed();
			return true;
		}
	}

	// Check if Edit Recording is appear and if it enable or disable under
	// Recording Task menu
	public boolean isEditRecordingDisableOrEnable() {
		try {
			driver.findElement(By.cssSelector(".disabled>#EditRecording")).isDisplayed();
			return false;
		} catch (Exception msg) {
			driver.findElement(By.id("EditRecording")).isDisplayed();
			return true;
		}
	}

	// Check if Edit Recording Properties is appear and if it enable or disable
	// under Recording Task menu
	public boolean isEditRecordingPropertiesDisableOrEnable() {
		try {
			driver.findElement(By.cssSelector(".disabled>#EditRecordingProperties")).isDisplayed();
			return false;
		} catch (Exception msg) {
			driver.findElement(By.id("EditRecordingProperties")).isDisplayed();
			return true;
		}
	}

	// Check if Edit Recording Properties is appear and if it enable or disable
	// under Recording Task menu
	public boolean isShareRecordingDisableOrEnable() {
		try {
			driver.findElement(By.cssSelector(".disabled>#ShareRecording")).isDisplayed();
			return false;
		} catch (Exception msg) {
			driver.findElement(By.id("ShareRecording")).isDisplayed();
			return true;
		}
	}

	// verify there is no Start Test button
	public void verifyNoAdditionalContentTab() {
		try {
			if (additional_content_tab.isDisplayed()) {
				ATUReports.add("Click on additional tab", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				System.out.println("Click on additional tab");
				Assert.assertTrue(false);
			} else {
				ATUReports.add("No Click on additional tab", LogAs.PASSED, null);
				System.out.println("No Click on additional tab");
				Assert.assertTrue(true);
			}
		} catch (Exception e) {
			ATUReports.add("No Click on additional tab", LogAs.PASSED, null);
			System.out.println("No Click on additional tab");
			Assert.assertTrue(true);
		}
	}

	/// clicks on content task->upload additonal content file
	public void toUploadAdditionalContentFile(ConfirmationMenu confirm) throws InterruptedException, Exception {
	try{	
		Robot robot = new Robot();
		robot.delay(3000);
		robot.mouseMove(0, -100);
		/// clickOnAdditionContentTab();
		Thread.sleep(2000);
		course_tasks_button.click();
		Thread.sleep(2000);
		Add_Additional_Content_File.click();
		Thread.sleep(2000);
		select_upload_additional_file.click();
		Thread.sleep(2000);
		String fullPathToFile = "src\\test\\resources\\additional_file.txt"; // System.getProperty("user.dir")																										//// +
	//// "\\src\\main\\resources\\ImsImportDataCreation.xml";

		uploadFile(fullPathToFile);
		Thread.sleep(2000);
		add_additional_file_button.click();
		Thread.sleep(2000);
		confirm.clickOnOkButton();}
	catch(Exception e){
		System.out.println(e.getMessage());
	}
	}

	/// clicks on content task->upload additonal content file by path
	public void toUploadAdditionalContentFile() throws InterruptedException, Exception {
//		Robot robot = new Robot();
//		robot.delay(3000);
//		robot.mouseMove(0, -100);
//		Thread.sleep(2000);
//		course_tasks_button.click();
//		Thread.sleep(2000);
//		add_additional_content_file.click();
//		Thread.sleep(2000);
		
		WebElement element=course_task_button;
		String id="AddAdditionalContentFile";
		try {
			System.out.println("clickOnRecordingTaskThen1");
			waitForVisibility(element);
			((JavascriptExecutor) driver).executeScript("document.getElementById(\""+id+"\").click();");
			System.out.println("clickOnRecordingTaskThen1");
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ModalDialogHeader")));
			System.out.println("Delete window displayed");
			ATUReports.add("Select Content Tasks -> "+id+" menu items", id+" window is displayed",
					id+" window is displayed", LogAs.PASSED, null);
			Assert.assertTrue(true);
			return;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("clickOnRecordingTaskThen6");
			ATUReports.add("Select Content Tasks -> "+id+" menu items", id+" window is displayed",
					id+" window isn't displayed", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			System.out.println(id+" window not displayed");
			Assert.assertTrue(false);
		}
		
	}
	
	//verify Additional Content Tab Is On Right Of Recording Tab
	public void verifyAdditionalContentTabIsOnRightOfRecordingTab() {
		
		waitForVisibility(additional_content_tab);
		Point additonal=additional_content_tab.getLocation();
		Point record=recordings_tab.getLocation();
		
		if ((additional_content_tab.isDisplayed())&&(additonal.getX()>record.getX())) {
			System.out.println("additional content tab is on right of recording tab");
			ATUReports.add("additional content tab is on right of recording tab", LogAs.PASSED, null);
			Assert.assertTrue(true);
			return;
		} else {
			System.out.println("additional content tab is not on right of recording tab");
			ATUReports.add("additional content tab is on right of recording tab", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}

	

	////// upload additional content
	public void uploadFile(String path) throws Exception {

		// from here you can use as it wrote
		System.out.println("uploadFile1");
		String new_path = System.getProperty("user.dir") + path;
	
		
		System.out.println(path);
		System.out.println("uploadFile2");
		StringSelection ss = new StringSelection(path);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
		System.out.println("uploadFile3");
		// native key strokes for CTRL, V and ENTER keys
		Robot robot = new Robot();
		System.out.println("uploadFile4");
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		Thread.sleep(5000);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		System.out.println("uploadFile5");

	}

	// verify there is no Student Tab
	public void verifyNoStudentTab() {
		try {
			if (student_recordings_tab.isDisplayed()) {
				System.out.println("The Student Recordings tab shouldn't be displayed, but it is.");
				ATUReports.add("Verified that there is no Student Recordings tab.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Assert.assertTrue(false);
			} else {
				System.out.println("Verified that there is no Student Recordings tab.");
				ATUReports.add("Verified that there is no Student Recordings tab.", "True.", "True.", LogAs.PASSED,
						null);
				Assert.assertTrue(true);
			}
		} catch (Exception e) {
			System.out.println("Verified that there is no Student Recordings tab.");
			ATUReports.add("Verified that there is no Student Recordings tab.", "True.", "True.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		}
	}

	// verify there is no tests Tab
	public void verifyNoTestsTab() {
		try {
			if (tests_tab.isDisplayed()) {
				ATUReports.add("Not verify no test tab.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				System.out.println("Not verify no test tab.");
				Assert.assertTrue(false);
			} else {
				ATUReports.add("Verify no tests tab.", LogAs.PASSED, null);
				System.out.println("Verify no tests tab.");
				Assert.assertTrue(true);
			}
		} catch (Exception e) {
			ATUReports.add("Verify no tests tab.", LogAs.PASSED, null);
			System.out.println("Verify no tests tab.");
			Assert.assertTrue(true);
		}
	}

	// thic function clicks on Recordings tab tab (in type of recordings
	// menu)
	public void clickOnRecordingsTab() {
		boolean clicked=false;
		int i=0;
		while(i<3 && !clicked){
		try {
			String initialText=TabContainer.getText();
			waitForVisibility(recordings_tab);
			recordings_tab.click();
			waitForContentOfTabToLoad(initialText,TabContainer);
			System.out.println("Clicked on recordings tab");
			ATUReports.add("Clicked on recordings tab", LogAs.PASSED, null);
			clicked=true;
			Assert.assertTrue(true);
		} catch (Exception msg) {
			clicked=handlesClickIsNotVisible(recordings_tab);
			if(i>=3){
			System.out.println("Failed to click on recordings tab. ERROR: " + msg);
			ATUReports.add("Failed to click on recordings tab. ERROR: " + msg, LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);}
		}
		i++;
		}
	}

	// Verify that recooding has no error status
	public void verifyNoErrorStatus(WebElement element) {
		try {
			String status = element.getText();
			if (status.equals("Error")) {
				System.out.println(" recording status is Error");
				Assert.assertTrue(false);
				ATUReports.add("verify recording status is Error", "status", "Error", "Error", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			} else {
				System.out.println(" recording status is not Error");
				ATUReports.add("verify recording status isnot  Error", "status", "Error", "Not Error", LogAs.PASSED,
						null);
				Assert.assertTrue(true);
			}
		} catch (Exception e) {
			System.out.println(" recording status is not Error");
			ATUReports.add("verify recording status isnot  Error", "status", "Error", "Not Error", LogAs.PASSED, null);
			Assert.assertTrue(true);

		}
	}

	// take additional content list of elements and extracts its name
	public List<String> convertAdditionalContantListToNames() {
		int i = 1;
		additional_content_list_names = new ArrayList<String>();
		for (WebElement el : additional_content_list) {
			String recording = el.getText();
			if ((!recording.equals("Recordings")) && (!recording.equals("Content Tasks"))) {

				additional_content_list_names.add(recording);

			}
		}
		return additional_content_list_names; // System.out.println(i);
	}

	// this function check that additional content file status in target index
	// is not target
	// status
	// with max timeout
	public boolean checkThatAdditionalContentFileStatusTargetIndexIsNotXWithTimeout(int index, String is_not,
			int time_interval) throws InterruptedException {
		for (int i = 0; i < time_interval; i++) {
			String recording_status = driver.findElement(By.id("ItemStatus" + (Integer.toString(index)))).getText();
			if (!recording_status.equals(is_not)) {
				System.out.println("additional content file in index: " + index + " is not: " + is_not);
				ATUReports.add("additional content file in index: " + index + " is not: " + is_not, LogAs.PASSED, null);
				Assert.assertTrue(true);
				return true;
			}

			Thread.sleep(1000);
		}
		System.out.println("additional content file in index: " + index + " status is: " + is_not);
		ATUReports.add("additional content file in index: " + index + " status is: " + is_not, LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		Assert.assertTrue(false);
		return false;
	}

	// This function return the i-th recorder username of the i-th recording
	public String getIndexRecorderNameOfRecording(int index) {
		String recorder_username = driver.findElement(By.id("RecordedBy" + Integer.toString(index))).getText();
		return recorder_username.substring(13, recorder_username.length());
	}

	// This function click on Recorind Task then on Publish in the sub menu
	public void clickOnRecordingTaskThenPublish() throws InterruptedException {

		WebElement element=recording_tasks_button;
		String id="PublishTask";
		try {
			System.out.println("clickOnRecordingTaskThen1");
			waitForVisibility(element);
			((JavascriptExecutor) driver).executeScript("document.getElementById(\""+id+"\").click();");
			System.out.println("clickOnRecordingTaskThen1");
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ModalDialogHeader")));
			System.out.println("Delete window displayed");
			ATUReports.add("Select Content Tasks -> "+id+" menu items", id+" window is displayed",
					id+" window is displayed", LogAs.PASSED, null);
			Assert.assertTrue(true);
			return;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("clickOnRecordingTaskThen6");
			ATUReports.add("Select Content Tasks -> "+id+" menu items", id+" window is displayed",
					id+" window isn't displayed", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			System.out.println(id+" window not displayed");
			Assert.assertTrue(false);
		}
	}

	// This function get target recording name, and target status, and checks if
	// that recording have that status
	public void verifyTargetRecordingHaveTargetStatus(String target_recording, String target_status) {
		List<String> recording_list = getCourseRecordingList();

		for (int i = 0; i < recording_list.size(); i++) {
			if (recording_list.get(i).equals(target_recording)) {
				checkRecordingInIndexIStatus(i + 1, target_status);
				return;
			}
		}

	}

	// This function get target recording name. Return true if it exists in
	// recording list, and false otherwise.
	public boolean isTargetRecordingExist(String target_recording_name) {
		List<String> current_recording_list = getCourseRecordingList();

		for (String current_recording : current_recording_list) {
			if (current_recording.equals(target_recording_name)) {
				return true;
			}
		}

		return false;
	}

	// This function verify that target recording not exist in recording list
	public void verifyThatTargetRecordingNotExistInRecordingList(String target_recording) {
		boolean is_exist = isTargetRecordingExist(target_recording);

		if (!is_exist) {
			System.out.println("Target recording not exist in recording list.");
			ATUReports.add("Recording list.", "Target recording not exist.", "Target recording not exist.",
					LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Target recording exist in recording list.");
			ATUReports.add("Recording list.", "Target recording not exist.", "Target recording exist.", LogAs.FAILED,
					null);
			Assert.assertTrue(false);
		}
	}

	// This function verify that target recording exist in recording list
	public void verifyThatTargetRecordingExistInRecordingList(String target_recording) {
		boolean is_exist = isTargetRecordingExist(target_recording);

		if (is_exist) {
			System.out.println("Target recording exist in recording list.");
			ATUReports.add("Recording list.", "Target recording exist.", "Target recording exist.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Target recording not exist in recording list.");
			ATUReports.add("Recording list.", "Target recording exist.", "Target recording not exist.", LogAs.FAILED,
					null);
			Assert.assertTrue(false);
		}
	}

	// This function select first recording without target status?
	public String selectRecordingWithOutTargetStatus(String target_status) throws InterruptedException {
		// wait.until(ExpectedConditions.elementToBeClickable(checkbox));
		List<String> current_recording_list = getCourseRecordingList();

		for (int i = 0; i < current_recording_list.size(); i++) {
			String current_recording_status = getIndexRecordingStatus(i + 1);

			if (!current_recording_status.equals(target_status)) {
				selectIndexCheckBox(i + 1);
				return current_recording_list.get(i);
			}
		}

		Assert.assertTrue(false);
		return null;
	}

	// This function open target recoring playback
	public void clickOnTargetRecordingAndOpenItsPlayback(String target_recording) throws InterruptedException {
		List<String> current_recording_list = getCourseRecordingList();

		for (int i = 0; i < current_recording_list.size(); i++) {
			if (current_recording_list.get(i).equals(target_recording)) {
				clickOnRecordingTitleInIndex(i + 1);
				clickOnTheCaptherWithOutTheExpandOnTheIdnex(i+1);
				break;
			}
		}

	}

	// This function checks if Content Tasks button displyed. Return true if it
	// is, and false otherwise
	public boolean isContentTasksButtonDisplay() {
	;
		if (content_tasks_button.isDisplayed()) {
			return true;
		} else {
			return false;
		}
	}

	// This function verify that Content Tasks button is not displayed
	public void verifyContentTasksButtonNotDisplayed() {
		boolean is_content_tasks_button_displayed = isContentTasksButtonDisplay();

		if (!is_content_tasks_button_displayed) {
			System.out.println("Content Tasks button is not displayed.");
			ATUReports.add("Content Taks button.", "Not displayed.", "Not displayed.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Content Tasks button is displayed.");
			ATUReports.add("Content Taks button.", "Not displayed.", "Displayed.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}

	// This function verify that checkbox which check all checkbox + first
	// checkbox not dispalyed => no checkbox is displayed
	public void verifyThatNoCheckboxIsDisplayed() {
		if ((!checkbox.isDisplayed()) && (!check_all_checkbox.isDisplayed())) {
			System.out.println("No checkbox is displayed.");
			ATUReports.add("Checkboxs.", "No checkbox displayed.", "No checkbox displayed.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("There is checkbox that displayed.");
			ATUReports.add("Checkboxs.", "No checkbox displayed.", "There is checkbox which displayed.", LogAs.FAILED,
					null);
			Assert.assertTrue(false);
		}
	}

	// This function return the name of the additional content in index
	public String getNameTargetIndexAdditionalContent(int index) {
		return driver.findElement(By.cssSelector("#NewItem" + Integer.toString(index) + ">span")).getText();

	}

	// This function get additional content (as string), and checks if it is
	// exist in additional content list
	public void verifyTargetAdditionalContentInAdditionalContentList(String target_additional_content) {
		int number_of_items_in_additional_content_list = driver.findElements(By.cssSelector(".itemTitle")).size();

		for (int i = 0; i < number_of_items_in_additional_content_list; i++) {
			String current_additiona_content = getNameTargetIndexAdditionalContent(i + 1);

			if (current_additiona_content.equals(target_additional_content)) {
				System.out.println("Target additional content is in additional content list.");
				ATUReports.add("Additional content list.", "Target additional content in the list.",
						"Target additional content in the list.", LogAs.PASSED, null);
				Assert.assertTrue(true);
				return;
			}
		}

		System.out.println("Target additional content is not in additional content list.");
		ATUReports.add("Additional content list.", "Target additional content in the list.",
				"Target additional content not in the list.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		Assert.assertTrue(false);
	}

	// This function get additional content (as string), and checks if it is
	// not exist in additional content list
	public void verifyTargetAdditionalContentNotInAdditionalContentList(String target_additional_content) {
		int number_of_items_in_additional_content_list = driver.findElements(By.cssSelector(".itemTitle")).size();

		for (int i = 0; i < number_of_items_in_additional_content_list; i++) {
			String current_additiona_content = getNameTargetIndexAdditionalContent(i + 1);

			if (current_additiona_content.equals(target_additional_content)) {
				System.out.println("Target additional content is in additional content list.");
				ATUReports.add("Additional content list.", "Target additional content not in the list.",
						"Target additional content in the list.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Assert.assertTrue(false);
				return;
			}
		}

		System.out.println("Target additional content is not in additional content list.");
		ATUReports.add("Additional content list.", "Target additional content not in the list.",
				"Target additional content not in the list.", LogAs.PASSED, null);
		Assert.assertTrue(true);
	}

	// This function return the course title of the recordings
	public String getCourseTitle() {
		try {
			return course_title.getText();
		} catch (Exception msg) {
			return null;
		}
	}

	// This function get string (subname) and checks if it exists in part of
	// course title
	public void verifyThatStringExistsInCourseName(String subname) {
		if (getCourseTitle().contains(subname)) {
			System.out.println("Verified that course name contain target subname: " + subname);
			ATUReports.add("Verified that course name contain target subname.", "True.", "True.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Not verified that course name contain target subname: " + subname);
			ATUReports.add("Verified that course name contain target subname.", "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}
	
	// This function verify that Start Recording button is displayed
	public void veriftyThatStartRecordingButtonDisplayed() {
		if (recording_button.isDisplayed() &&
				(institute_logo.getLocation().x < recording_button.getLocation().x) &&
				(searchbox.getLocation().y < recording_button.getLocation().x) &&
				(recording_button.getLocation().y) < recording_tasks_button.getLocation().y) {
			System.out.println("Verifed that Start Recording button is displayed.");
			ATUReports.add("Start recording button.", "Displayed.", "Displayed.", LogAs.PASSED, null);
			Assert.assertTrue(true);

		} else {
			System.out.println("Not verified that Start Recording button is displayed.");
			ATUReports.add("Start recording button.", "Displayed.", "Not displayed.", LogAs.PASSED, null);
			Assert.assertTrue(false);
		}
	}

	// This function verify that it is recording page
	public void verifyThatItIsRecordingsPage() {
		if ((recordings_tab.isDisplayed()) && (recording_tasks_button.isDisplayed())) {
			System.out.println("Course details page is displayed.");
			ATUReports.add("Course details page.", "Displayed.", "Displayed.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Course details page is not displayed.");
			ATUReports.add("Course details page.", "Displayed.", "Not displayed.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}

	// This function verify that no recording exist in the course
	public void verifyThatNoRecordingExistInTheCourse() {
		if (getNumberOfRecordings() == 0) {
			System.out.println("Verified that no recording exist in the course.");
			ATUReports.add("Recording list.", "No recording is exist.", "No recording is exist.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Not verified that no recording exist in the course.");
			ATUReports.add("Recording list.", "No recording is exist.", "There is recording in the recording list.",
					LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}

	/// selects dditional content file by name
	public void selectAdditionalContentByName(String additional_content_name) throws InterruptedException {

		for (WebElement el : additional_content_list) {
			if ((el.getText().equals(additional_content_name))) {

						WebElement recording = driver.findElement(By.linkText((additional_content_name)));
						waitForVisibility(recording);			
						clickElementJS(recording);
						System.out.println("additional content file found");
						ATUReports.add("additional content file found", LogAs.PASSED, null);
						Assert.assertTrue(true);
						Thread.sleep(3000);
						return;											
				}			
			}


	
		System.out.println("no such additional content file name");
		ATUReports.add("no such additional content file name", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		Assert.assertTrue(false);

	}

	/// verify downloaded file is valid
	public void VerifyDownloadedFileIsValid(String file_name) throws Exception {
		String download_path = System.getProperty("user.home") + File.separatorChar + "Downloads" + File.separatorChar
				+ file_name;

		Path download_path_to_delete = Paths.get(download_path);
		String resource_file_path = System.getProperty("user.dir")+ "\\workspace\\QualitestAutomation\\resources\\documents\\" + file_name;
		String file1_md5 = getMD5Sum(resource_file_path);
		String file2_md5 = getMD5Sum(download_path);
		try {
			Files.delete(download_path_to_delete);
			File fileTemp = new File(download_path);
			if (fileTemp.exists()) {
				System.out.println("fail to delete file");
				ATUReports.add("fail to delete file", "delete old file", " deleted successfully", "not deleted",
						LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Assert.assertTrue(false);
			}
		} catch (NoSuchFileException x) {
			System.err.format("%s: no such" + " file or directory%n", download_path);
		} catch (DirectoryNotEmptyException x) {
			System.err.format("%s not empty%n", download_path);
		} catch (IOException x) {
			// File permission problems are caught here.
			System.err.println(x);
		}
		if (file1_md5.equals(file2_md5)) {
			System.out.println("2 files are equal so file is valid");
			ATUReports.add("2 files are equal so file is valid", LogAs.PASSED, null);
			Assert.assertTrue(true);

		} else {
			System.out.println("2 files are  not equal so file is invalid");
			ATUReports.add("2 files are  not equal so file is invalid", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}

	}

	/// try to delete file by path
	public void tryToDeleteOlderFile(String file_name_path) {
		try {
			// Delete if tempFile exists
			File fileTemp = new File(file_name_path);
				try {
					
					if (fileTemp.exists()) {
						fileTemp.delete();
						System.out.println("delete old file");
						ATUReports.add("try to delete old file", "delete old file", " deleted successfully", "not deleted",
								LogAs.PASSED, null);
						Assert.assertTrue(true);
					} else {
					System.out.println("file wasn't found");
					ATUReports.add(" old file deleted succesfully", "delete old file", " deleted successfully"," deleted successfully", LogAs.PASSED, null);
					Assert.assertTrue(true);
					}
				} catch (Exception e) {
					System.out.println("fail to delete file");
					ATUReports.add("fail to delete file", "delete old file", " deleted successfully", "not deleted",
							LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
					Assert.assertTrue(false);
				}
			
		} catch (Exception e) {
			System.out.println("old file does not exist");
			ATUReports.add("old file does not exist", "old file does not exist", "old file does not exist",
					"old file does not exist", LogAs.PASSED, null);
			Assert.assertTrue(true);
		}
	}

	// this function gets recording name and clicks it checkbox
	public int clickAdditionalContentCheckBoxByName(String reco) {
		convertAdditionalContantListToNames();
		int index = additional_content_list_names.indexOf(reco) + 1;
		driver.findElement(By.id("Checkbox" + Integer.toString(index))).click();
		return index;
	}

	// verify additional content file name is not displayed in additional
	// content tab list
	public Boolean verifyNoAdditionalContentFileName(String name) throws InterruptedException {
		Thread.sleep(3000);
		convertAdditionalContantListToNames();
		for (String file_name : additional_content_list_names) {

			if (file_name.equals(name)) {
				System.out.println("selected file name is  displayed.");
				ATUReports.add("selected file name is  displayed.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));	
				return false;

			}

		}
		System.out.println("selected file name is not displayed.");
		ATUReports.add("selected file name is not displayed.", LogAs.PASSED, null);
		Assert.assertTrue(true);
		return true;

	}

	/// Verify that number mark isn't added to file's title
	public void verifyNoCopiesExist(List<String> list, String name_to_check) {
		for (String name : list) {
			if (name_to_check.equals(name)) {
				System.out.println(" number mark isn't added to file's title");
				ATUReports.add("number mark isn't added to file's title", "File name and List",
						"number mark isn't added to file's title", "number mark isn't added to file's title",
						LogAs.PASSED, null);
				Assert.assertTrue(true);
				return;
			}
		}
		System.out.println(" number mark is added to file's title");
		ATUReports.add("number mark isn't added to file's title", "File name and List",
				"number mark isn't added to file's title", "number mark is added to file's title", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		Assert.assertTrue(false);
	}

	/// clicks on content task->upload additonal content file by path
	public void toUploadAdditionalContentLink() throws InterruptedException, Exception {
		Robot robot = new Robot();
		robot.delay(3000);
		robot.mouseMove(0, -100);
		Thread.sleep(2000);
		course_tasks_button.click();
		Thread.sleep(2000);
		add_additional_content_link.click();
		Thread.sleep(2000);

	}

	/// clicks on content task->course settings
	public void toCourseSettingsPage() throws InterruptedException, Exception {
		Robot robot = new Robot();
		robot.delay(3000);
		robot.mouseMove(0, -100);
		Thread.sleep(2000);
		course_tasks_button.click();
		Thread.sleep(2000);
		course_settings_button.click();
		Thread.sleep(2000);

	}

	/// Validate the top bar menu "Guest | Disclaimer | help | sign out"
	public void verifyTopBarMenu() {
		if ((user_name.isDisplayed()) && (disclaimer.isDisplayed()) && (help.isDisplayed())
				&& (sign_out.isDisplayed())) {
			ATUReports.add("top bar menu is displayed", LogAs.PASSED, null);
			System.out.println("top bar menu is displayed");
			Assert.assertTrue(true);
		} else {
			ATUReports.add("top bar menu is not displayed", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			System.out.println("top bar menu is not displayed");
			Assert.assertTrue(false);
		}
	}

	// This function verify that there are at least two recordings
	public void verifyThatThereAreAtLeastTwoRecordings() {
		if (getCourseRecordingList().size() >= 2) {
			System.out.println("There are at least 2 recordings in the course.");
			ATUReports.add("There are at least 2 recordings in the course.", "True.", "True.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("There is less then two recordings in the test.");
			ATUReports.add("There are at least 2 recordings in the course.", "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}

	// This function verify that Content Task button is not displayed
	public void verifyThatContentTaskButtonNotDisplayed() {
		if (content_tasks_button.isDisplayed()) {
			System.out.println("Not verified that Content Task not displayed.");
			ATUReports.add("Verfied that Content Task button not displayed.", "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		} else {
			System.out.println("Verfied that Content Task button not displayed.");
			ATUReports.add("Verfied that Content Task button not displayed.", "True.", "True.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		}
	}

	// This function verify that "Start a Test" button displayed
	public void verifyThatStartATestDisplayed() {
		if (start_test_button.isDisplayed() &&
				(institute_logo.getLocation().x < start_test_button.getLocation().x) &&
				(searchbox.getLocation().y < start_test_button.getLocation().x) &&
				(start_test_button.getLocation().y) < recording_tasks_button.getLocation().y) {
			System.out.println("Verifed that Start a Test button is displayed.");
			ATUReports.add("Start a Test button.", "Displayed.", "Displayed.", LogAs.PASSED, null);
			Assert.assertTrue(true);

		} else {
			System.out.println("Not verified that Start a Test button is displayed.");
			ATUReports.add("Start a Test button.", "Displayed.", "Not displayed.", LogAs.PASSED, null);
			Assert.assertTrue(false);
		}
		
		
		
		
		
		
	}

	// This function return all additional content of current course
	public List<String> getCoursAdditionalContentList() {

		List<String> additional_content_names_list = new ArrayList<String>();
		// try {
		// wait.until(ExpectedConditions.visibilityOf(first_recording_title));
		// } catch (Exception msg) {
		// }

		for (int i = 0; i < addition_content_list.size(); i++) {
			String current_name = addition_content_list.get(i).getText();
			if (current_name.equals("Recordings")) {
				System.out.println("111111111111111111111111111");
				continue;
			} else if (current_name.equals("Recording Tasks")) {
				System.out.println("222222222222222222222222222");
				continue;
			}
			additional_content_names_list.add(current_name);
		}
		return additional_content_names_list;

	}

	// This function click on Recoring Task then on Edit Recording in the sub
	// menu
	public void clickOnRecordingTaskThenEditRecording() throws InterruptedException {
		WebElement element=recording_tasks_button;
		String id="EditRecording";
		try {
			System.out.println("clickOnRecordingTaskThen1");
			waitForVisibility(element);
			((JavascriptExecutor) driver).executeScript("document.getElementById(\""+id+"\").click();");
			System.out.println("clickOnRecordingTaskThen1");
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(".//*[@id='main']/div/div[1]/ul/li[1]/a")));			
			System.out.println("edit page displayed");
			ATUReports.add("Select Recording Tasks -> "+id+" menu items", id+" window is displayed",
					id+" window is displayed", LogAs.PASSED, null);
			Assert.assertTrue(true);
			return;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("clickOnRecordingTaskThen6");
			ATUReports.add("Select Recording Tasks -> "+id+" menu items", id+" window is displayed",
					id+" window isn't displayed", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			System.out.println(id+" window not displayed");
			Assert.assertTrue(false);
		}

	}

	// Wait until recording list is empty with max timeout
	public void waitUntilRecordingListIsEmptyWithMaxTimeOut(int max_time_out) throws InterruptedException {
		for (int i = 0; i < max_time_out; i++) {
			if (getCourseRecordingList().size() == 0) {
				break;
			} else {
				Thread.sleep(1000);
			}
		}
	}

	/// this function upublish recording by giving a tab
	public void unpublishFirstRecording(WebElement tab, PublishWindow publish) {
		waitForVisibility(tab);
		try {
			clickElementJS(tab);	
			SelectOneCheckBoxOrVerifyAlreadySelected(checkbox);				
			clickElementJS(publish_button);
			clickElementJS(publish.never_select_button);						
			waitForVisibility(publish.save_button);	
			clickElementJS(publish.save_button);
			if (isElementPresent(By.id("ModalDialogHeader"))) {
				clickElementJS(publish.save_button);
			}
			
		} catch (Exception e) {
			System.out.println("failed clicking on tab");
			ATUReports.add("failed clicking on tab: "+tab.getText(),  "tab  not clicked"+e.getMessage(), LogAs.FAILED,new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}

	}

	/// to publish recording window
	public void toPublishRecording(PublishWindow publish) {
		try {
			Robot robot = new Robot();
			robot.mouseMove(-100, -100);
			moveToElementAndClick(recording_tasks_button, driver);
			System.out.println("moved to recording task button");
			waitForVisibility(publish_button);
			publish_button.click();
			waitForVisibility(publish.publish_window_title);
			System.out.println("verify publish window");
			ATUReports.add("verify publish window", " wait for title", "title visible", "title visible", LogAs.PASSED,
					null);
			Assert.assertTrue(true);

		} catch (Exception e) {
			System.out.println("not verify publish window");
			ATUReports.add("verify publish window", " wait for title", "title visible", "title not visible",
					LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}

	}

	// this function verifies if an element is bold
	public void recordingBoldFont(WebElement e) {
		System.out.println(e.getCssValue("font-weight"));
		if ((e.getCssValue("font-weight").equals("bold")) || (e.getCssValue("font-weight").equals("700"))) {
			System.out.println("Font style is bold.");
			ATUReports.add("Font style is bold.", "True.", "True.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Font style is not bold.");
			ATUReports.add("font style is bold.", "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}

	}
	
	// This function verifies if an element is not bold
	public void recordingNotBoldFont(WebElement e) {
		System.out.println(e.getCssValue("font-weight"));
		if ((e.getCssValue("font-weight").equals("bold")) || (e.getCssValue("font-weight").equals("700"))) {
			System.out.println("Font style is bold.");
			ATUReports.add("Font style is not bold.", "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		} else {
			System.out.println("Font style is not bold.");
			ATUReports.add("Font style is not bold.", "True.", "True.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		}

	}
	
	public void returnToAdminPageByClickingBreadcrumbsName(WebElement element) {
		try {
			waitForVisibility(element);
			element.click();
			System.out.println("click on course name");
			ATUReports.add("click on course name", "course name", "clicked", "clicked", LogAs.PASSED, null);
			Assert.assertTrue(true);
			if(!isElementPresent(By.xpath(".//*[@id='tegrityBreadcrumbsBox']/li/a"))){
				waitForVisibility(element);
				element.click();
			}
	
		} catch (Exception exception) {
			
			System.out.println("failed clicking on course name breadcrumbs");
			ATUReports.add("click on course name", "course name", "clicked", " not clicked", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}
	

	//// return to recordings page
	public void returnToRecordingPageByClickingBreadcrumbsName(WebElement element) {
		try {
			waitForVisibility(element);
			element.click();
			System.out.println("click on course name");
			ATUReports.add("click on course name", "course name", "clicked", "clicked", LogAs.PASSED, null);
			Assert.assertTrue(true);
			try {
				waitForVisibility(recordings_tab);
				System.out.println("record page verified");
				ATUReports.add("wait for course element visibility", "recordings tab", "visible", "visible",
						LogAs.PASSED, null);
				Assert.assertTrue(true);
			} catch (Exception exception) {
				System.out.println(" Timeout course page");
				ATUReports.add("wait for course element visibility", "recordings tab list", "visible", " not visible",
						LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Assert.assertTrue(false);
			}
		} catch (Exception exception) {
			System.out.println("failed clicking on course name breadcrumbs");
			ATUReports.add("click on course name", "course name", "clicked", " not clicked", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}

	/// this function verifies that after login as guest and choosing public
	/// course,and checkbox unchecked download option is disabled and also
	/// message appears:list "To perform one of the tasks please select one or
	/// more from the recordings list below using the checkboxes on the right."
	// after hovering
	public void verifyDisableDownloadAndMessageAppears() {
		Robot robot;
		try {
			robot = new Robot();
			robot.mouseMove(-100, -100);
			moveToElementAndClick(recording_tasks_button, driver);
			String message = driver
					.findElement(
							By.xpath("//*[@id=\"scrollableArea\"]/div[1]/div[2]/div/div[1]/div[1]/ul/li/ul/li[2]/em"))
					.getText();
			if (message.equals(
					"To perform one of the tasks please select one or more recordings from the list below using the checkboxes on the right.")) {
				System.out.println("message is correct");
				ATUReports.add("verify message is correct", "message", "visible", " visible", LogAs.PASSED, null);
				Assert.assertTrue(true);

				if (download.getAttribute("class").equals("disabled")) {
					System.out.println("download is not disabled");
					ATUReports.add("download is disabled", "download", "not clickable", " clickable", LogAs.FAILED,
							null);
					Assert.assertTrue(false);
				} else {
					System.out.println("download is disabled");
					ATUReports.add("download is disabled", "download", "not clickable", "not clickable", LogAs.PASSED,
							null);
					Assert.assertTrue(true);
				}
			} else {
				System.out.println("message is not correct");
				ATUReports.add("verify message is correct", "message", "visible", "Not visible", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Assert.assertTrue(false);
			}
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/// verify download Option is enabled
	public void verifyEnabledDownload() {
		Robot robot;
		try {
			robot = new Robot();
			robot.mouseMove(-100, -100);
			moveToElementAndClick(recording_tasks_button, driver);

			if (!download.getAttribute("class").equals("disabled")) {
				System.out.println("download is not disabled");
				ATUReports.add("download is not disabled", "download", " clickable", " clickable", LogAs.PASSED, null);
				Assert.assertTrue(true);
			} else {
				System.out.println("download is disabled");
				ATUReports.add("download is disabled", "download", "clickable", "not clickable", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Assert.assertTrue(false);
			}

		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/// gets element and it string name and verifies its not clickable and not
	/// visible
	public void verifyClickableElementIsNotVisible(WebElement element, String element_name) {
		try {
			element.click();
			System.out.println(element_name + "is  visible");
			ATUReports.add(element_name + "is  visible", element_name, "not clickable", "clickable", LogAs.FAILED,
					null);
			Assert.assertTrue(false);

		} catch (Exception e) {
			System.out.println(element_name + "is not visible");
			ATUReports.add(element_name + "is not visible", element_name, "not clickable", "clickable", LogAs.PASSED,
					null);
			Assert.assertTrue(true);
		}
	}

	// verify element is enabled using his element and his name
	public void verifyElementIsEnabled(WebElement element, String element_name) {
		if (!element.getAttribute("class").equals("disabled")) {
			System.out.println(element_name + "is enabled");
			ATUReports.add(element_name + " is enabled", element_name, "clickable", "clickable", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println(element_name + "is disabled");
			ATUReports.add(element_name + "is disabled", element_name, "clickable", " not clickable", LogAs.FAILED,
					null);
			Assert.assertTrue(false);
		}
	}

	// verify element is disabled using his element and his name
	public void verifyElementIsDisabled(WebElement element, String element_name) {
		if (element.getAttribute("class").equals("disabled")) {
			System.out.println(element_name + "is disabled");
			ATUReports.add(element_name + " is disabled", element_name, " not clickable", " not clickable",
					LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(true);

		} else {
			System.out.println(element_name + "is enabled");
			ATUReports.add(element_name + " is enabled", element_name, "not clickable", "clickable", LogAs.FAILED,
					null);
			Assert.assertTrue(false);
		}
	}


	/// to rss feed page
	public void toRssFeedPage(WebDriver driver) throws Exception {
		// TODO Auto-generated method stub
		Robot robot = new Robot();
		robot.mouseMove(0, -100);
		waitForVisibility(course_tasks_button);
		course_tasks_button.click();
		waitForVisibility(rssfeed);
		
//		if(driver instanceof ChromeDriver){
//			WebElement we =driver.findElement(By.tagName("body")); 
//			we.sendKeys(Keys.SHIFT);
//		}
		Actions builder = new Actions(driver);
		builder.keyDown(Keys.SHIFT).click(rssfeed).keyUp(Keys.SHIFT).build().perform();		
		//rssfeed.click();	
		System.out.println("Return to rss feed page.");
		
//		if(driver instanceof ChromeDriver){
//			WebElement we =driver.findElement(By.tagName("body")); 
//			we.sendKeys(Keys.SHIFT);
//		}

	}

	/// verify rss feed page first clicking course_tasks-->rss_feed
	public String verifyRssFeedPage(WebDriver driver, LoginHelperPage tegrity) {
		
		String xml_source_code = null;
		String current = null;
		String ChildWindow = null;
		try {

			String parentWindow = driver.getWindowHandle();
			System.out.println(parentWindow);
			toRssFeedPage(driver);	
			Set<String> handles = driver.getWindowHandles();

			for (String windowHandle : handles) {
				if (!windowHandle.equals(parentWindow)) {
					System.out.println(windowHandle);
		            driver.switchTo().window(windowHandle);
//					//current = driver.getCurrentUrl();
					break;	
				}
			}
			//current =((JavascriptExecutor) driver).executeScript("return window.document.location.href").toString();
			current = driver.getCurrentUrl();
			Thread.sleep(2000);
			String Rss_url_xml = "view-source:" + current;
			driver.get(Rss_url_xml);
			xml_source_code = driver.findElement(By.tagName("body")).getText();	
			Thread.sleep(2000);
			String rss_title = tegrity.getPageUrl() + "/api/rss";
			//.substring(0, tegrity.getPageUrl().length() - 8)
			if (current.contains(rss_title)) {
				System.out.println("verified rss page");
				ATUReports.add("verified rss page", rss_title, "contained", "contained", LogAs.PASSED, null);
				Assert.assertTrue(true);
			} else {
				System.out.println("not verified rss page");
				System.out.println(current);
				ATUReports.add("not verified rss page" + rss_title, current, "contained", "not contained", LogAs.FAILED,  new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Assert.assertTrue(false);
			}

		} catch (Exception e) {
			System.out.println("not able to click to rss feed page");
			ATUReports.add("verify rss feed page", "Rss_Feed", "clickable", "not clickable", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
		return xml_source_code;
	}

	public void clickOnThePodcast() throws AWTException { 
		
		Robot robot = new Robot();
		robot.mouseMove(0, -100);
		waitForVisibility(course_tasks_button);
		course_tasks_button.click();
		waitForVisibility(rssfeed);
		podcast_button.click();
	}
	
	
	/// verify podcast page first clicking course_tasks-->rss_feed
	public String verifyPodcastPage(WebDriver driver, LoginHelperPage tegrity) {
		
		String xml_source_code = null;
		
		try {
			
			clickOnThePodcast();		
			String parentWindow = driver.getWindowHandle();
			Set<String> handles = driver.getWindowHandles();
			String current = "";
			for (String windowHandle : handles) {
				if (!windowHandle.equals(parentWindow)) {
					driver.switchTo().window(windowHandle);
					current = driver.getCurrentUrl();
					break;										// window
				}
			}

			Thread.sleep(2000);
			String podcast_url_xml = "view-source:" + current;
			driver.get(podcast_url_xml);
			xml_source_code = driver.findElement(By.tagName("body")).getText();	
			System.out.println("clicked to podcast page");
			ATUReports.add("verify podcast page", "Rss_Feed", "clickable", "clickable", LogAs.PASSED, null);
			Assert.assertTrue(true);
			String podcast_title = tegrity.getPageUrl() + "/api/rss";
		   //.substring(0, tegrity.getPageUrl().length() - 8) 

			if (current.contains(podcast_title)) {
				System.out.println("verified podcast page");
				ATUReports.add("verified podcast page", podcast_title, "contained", "contained", LogAs.PASSED, null);
				Assert.assertTrue(false);
			} else {
				System.out.println(" not verified podcast page");
				ATUReports.add("not verified podcast page" + podcast_title, current, " contained", "not contained", LogAs.FAILED,new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Assert.assertTrue(true);
			}

		} catch (Exception e) {
			System.out.println("not able to click to podcast page");
			ATUReports.add("verify podcast page", "podcast", "clickable", "not clickable", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
		return xml_source_code;

	}

	/// this function verifies podcast url pattern it gets university
	/// url+recording string of chracters
	// first convert string of xml to xml file then it get attribute only url
	/// and chooses if it contains
	// university url+recording string of chracters if it does 2 conditions are
	/// matched and then it verifies last condition:pattern with '0'-'f' and '-'
	public ArrayList<String> podcastUrlVerification(String xml, String podcast_url, LoginHelperPage teg) {

		ArrayList<String> arrayList = new ArrayList<String>();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();

			// Use String reader
			Document document = builder.parse(new InputSource(new StringReader(xml)));
			TransformerFactory tranFactory = TransformerFactory.newInstance();
			Transformer aTransformer = tranFactory.newTransformer();
			Source src = new DOMSource(document);
			Result dest = new StreamResult(new File("xmlFileName.xml"));
			aTransformer.transform(src, dest);
			document.getDocumentElement().normalize();
			System.out.println("Root element " + document.getDocumentElement().getNodeName());
			String first = teg.getPageUrl().substring(0, teg.getPageUrl().length() - 8) + "/api/podcast/";
			String university = teg.getPageUrl().substring(0, teg.getPageUrl().length() - 8);
			String third = podcast_url.substring(podcast_url.length() - (university.length() +2 ));
			String guid = third.substring(0,36);
			org.w3c.dom.NodeList nodeList = document.getElementsByTagName("enclosure");
			for (int i = 0; i < nodeList.getLength(); i++) {
				// Get element
				String element = ((Element) nodeList.item(i)).getAttribute("url");
				if ((element.contains(first)) && ((element.contains(guid)))) {
					// extract only pattern
					String pattern = element.substring(59, 95);
					arrayList.add(pattern);
					/// check pattern
					podcastPatternVerification(pattern);

					return arrayList;
				}

			}
			if (!arrayList.isEmpty()) {
				System.out.println("first part(university url) of rss url and third(recording characters)  verified");
				ATUReports.add("first part(university url) of rss url and third(recording characters)  verified",
						"podcast", "clickable", "not clickable", LogAs.PASSED, null);
				Assert.assertTrue(true);

			} else {
				System.out
						.println("first part(university url) of rss url and third(recording characters) not verified");
				ATUReports.add("first part(university url) of rss url and third(recording characters) not verified",
						"podcast", "clickable", "not clickable", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Assert.assertTrue(false);

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("first part(university url) of rss url and third(recording characters) not verified");
			ATUReports.add("first part(university url) of rss url and third(recording characters) not verified",
					"podcast", "clickable", "not clickable", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}

		return arrayList;

	}

	
	/// this function verifies podcast url pattern it gets university
	/// url+recording string of chracters
	// first convert string of xml to xml file then it get attribute only url
	/// and chooses if it contains
	// university url+recording string of chracters if it does 2 conditions are
	/// matched and then it verifies last condition:pattern with '0'-'f' and '-'
	public ArrayList<String> RssUrlVerification(String xml, String rss_url) {

		ArrayList<String> arrayList = new ArrayList<String>();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();

			// Use String reader
			Document document = builder.parse(new InputSource(new StringReader(xml)));

			TransformerFactory tranFactory = TransformerFactory.newInstance();
			Transformer aTransformer = tranFactory.newTransformer();
			Source src = new DOMSource(document);
			Result dest = new StreamResult(new File("xmlFileName.xml"));
			aTransformer.transform(src, dest);
			document.getDocumentElement().normalize();
			System.out.println("Root element " + document.getDocumentElement().getNodeName());
			org.w3c.dom.NodeList nodeList = document.getElementsByTagName("link");
			for (int i = 0; i < nodeList.getLength(); i++) {
				// Get element		
				String element = ((Element) nodeList.item(i)).getTextContent();
				System.out.println(element);
				if (element.equals(rss_url)) {
					// extract only pattern
					String pattern = element.substring(59, 95);
					arrayList.add(pattern);
					/// check pattern
					podcastPatternVerification(pattern);
					return arrayList;
				}
				 
			}
			if (!arrayList.isEmpty()) {
				System.out.println("first part(university url) of rss url and third(recording characters)  verified");
				ATUReports.add("first part(university url) of rss url and third(recording characters)  verified",
						"podcast", "clickable", "not clickable", LogAs.PASSED, null);
				Assert.assertTrue(true);

			} else {
				System.out
						.println("first part(university url) of rss url and third(recording characters) not verified");
				ATUReports.add("first part(university url) of rss url and third(recording characters) not verified",
						"podcast", "clickable", "not clickable", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Assert.assertTrue(false);

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("first part(university url) of rss url and third(recording characters) not verified");
			ATUReports.add("first part(university url) of rss url and third(recording characters) not verified",
					"podcast", "clickable", "not clickable", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}

		return arrayList;

	}
	
	
	
	
	//// verifies pattern for podcast
	public void podcastPatternVerification(String element) {
		System.out.println(element);
		if (element.length() != 36) {
			// TODO Auto-generated catch block
			System.out.println("pattern of podcast not verified");
			ATUReports.add("pattern of podcast not verified", "podcast", "clickable", "not clickable", LogAs.FAILED,
					null);
			Assert.assertTrue(false);

		} else {

			for (int i = 0; i < 36; i++) {
				if ((((element.charAt(i) <= '9') && (element.charAt(i) >= '0'))
						|| ((element.charAt(i) >= 'a') && (element.charAt(i) <= 'f')))) {

				} else {
					if (!((element.charAt(i) == '-') && ((i == 8) || (i == 13) || (i == 18) || (i == 23)))) {
						// TODO Auto-generated catch block
						System.out.println("pattern of podcast not verified");
						ATUReports.add("pattern of podcast not verified", "podcast", "clickable", "not clickable",
								LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
						Assert.assertTrue(false);
						return;
					}
				}
			}

			// TODO Auto-generated catch block
			System.out.println("pattern of podcast  verified");
			ATUReports.add("pattern of podcast  verified", "podcast", "clickable", " clickable", LogAs.PASSED, null);
			Assert.assertTrue(true);
		}
	}

	// verify file was downloaded succesfully by giving it a path to file
	public void VerifyDownloadedFileIsExist(String download_path) {

		/// Files.delete(download_path_to_delete);
		File fileTemp = new File(download_path);
		if (fileTemp.exists()) {
			System.out.println("file exists");
			ATUReports.add("file exists", LogAs.PASSED, null);
			Assert.assertTrue(true);

		} else {
			System.out.println("file not exists");
			ATUReports.add("file not exists", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}

	}

	// verify copy menu
	public String verifyCopyMenu() throws InterruptedException {

		String clickedRecordingName = first_course_title.getText();
		WebElement element=recording_tasks_button;
		String id="CopyTask2";
		System.out.println("clickOnRecordingTaskThen1");
		waitForVisibility(element);
		((JavascriptExecutor) driver).executeScript("document.getElementById(\""+id+"\").click();");
		if (isElementPresent(By.id("copyCourseWindow"))) {
			System.out.println("copy menu verified");
			ATUReports.add("copy menu verified", LogAs.PASSED, null);
		} else {
			System.out.println("no copy menu verification");
			ATUReports.add("no copy menu verification", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
		return clickedRecordingName;
	}

	public void copyRecordsToAnotherCourse(String targetCourse) throws InterruptedException
	
	{
		try {
			Robot robot = new Robot();
			robot.mouseMove(-100, -100);
		} catch (AWTException e1) {
			// TODO Auto-generated catch block
			System.out.println("Could not use the robot.");
		}
		waitForVisibility(recording_tasks_button);
		moveToElementAndClick(recording_tasks_button, driver);
		copy_button.click(); // solve
		copyMenu =  PageFactory.initElements(driver, CopyMenu.class);
		copyMenu.selectTargetCourseFromCourseList(targetCourse);
		copyMenu.clickOnCopyButton();

		try {
			Thread.sleep(1000);
			waitForVisibility(driver.findElement(By.id("ModalDialogHeader")));
			if (driver.findElement(By.id("ModalDialogHeader")).getText().equals("Copy"))// check if
			{
				System.out.println("Copy menu is displayed.");
				ATUReports.add("Clicked on Recording Task -> Copy.", "Copy menu is displayed.",
						"Copy menu is displayed.", LogAs.PASSED, null);
				Assert.assertTrue(true);
			}
		} catch (Exception e) {
			System.out.println("Copy menu not verified.");
			ATUReports.add("Clicked on Recording Task -> Copy.", "Copy menu is displayed.",
					"Copy menu is not displayed.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}

	// verify check box is selected
	public void SelectOneCheckBoxOrVerifyAlreadySelected(WebElement checkbox) throws InterruptedException {
		try {
			waitForVisibility(checkbox);
			if (checkbox.isSelected()) {
				System.out.println("Checkbox is already selected.");
				ATUReports.add("Checkbox.", "Selected/Already selected.", "Already selected.", LogAs.PASSED, null);
				Assert.assertTrue(true);
				return;
			} else {
				
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkbox);			
				//checkbox.click();
				System.out.println("Checkbox is selected");
				ATUReports.add("Checkbox.", "Success to select.", "Sucess to select.", LogAs.PASSED, null);
				Assert.assertTrue(true);
			}
		} catch (Exception e) {
			System.out.println("Checkbox is not selected.");
			ATUReports.add("Checkbox.", "Success select.", "Fail to select.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}

	}

	// verify check box is selected
	public void unClickOneCheckBoxOrVerifyNotSelected(WebElement check) throws InterruptedException {
		try {
			waitForVisibility(check);
			if (!check.isSelected()) {
				System.out.println("one checkbox is not selected");
				ATUReports.add("one checkbox is not selected", LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
				Assert.assertTrue(true);
				return;
			} else {
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", check);			
				//checkbox.click();
				if (!check.isSelected()) {
					System.out.println("one checkbox is not selected");
					ATUReports.add("one checkbox is not selected", LogAs.PASSED,new CaptureScreen(ScreenshotOf.DESKTOP));
					Assert.assertTrue(true);
				} else {
					System.out.println("one checkbox is  selected");
					ATUReports.add("one checkbox is selected", LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
					Assert.assertTrue(false);
				}
			}
		} catch (Exception e) {
			System.out.println("one checkbox is selected");
			ATUReports.add("one checkbox is selected", LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
			Assert.assertTrue(false);
		}

	}

	// This function click on Recoring Task then on Tag in the sub menu
	public void clickOnRecordingTaskThenTag() throws InterruptedException {
		WebElement element=recording_tasks_button;
		String id="TagsListTask2";
		try {
			System.out.println("clickOnRecordingTaskThen1");
			waitForVisibility(element);
			((JavascriptExecutor) driver).executeScript("document.getElementById(\""+id+"\").click();");
			System.out.println("clickOnRecordingTaskThen1");
			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ModalDialogHeader")));
			System.out.println("tag window displayed");
			ATUReports.add("Select Recording Tasks -> "+id+" menu items", id+" window is displayed",
					id+" window is displayed", LogAs.PASSED, null);
			Assert.assertTrue(true);
			return;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("clickOnRecordingTaskThen6");
			ATUReports.add("Select Recording Tasks -> "+id+" menu items", id+" window is displayed",
					id+" window isn't displayed", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			System.out.println(id+" window not displayed");
			Assert.assertTrue(false);
		}
	}

	// This function get recording name and return it's index if the recording
	// exist
	public int getTargetRecordingIndex(String target_recording) {
		int target_recording_index = 0;
		if (isTargetRecordingExist(target_recording)) {

			List<String> current_recording_list = getCourseRecordingList();
			for (int i = 0; i < current_recording_list.size(); i++) {
				target_recording_index++;
				if (current_recording_list.get(i).equals(target_recording)) {
					break;
				}
			}
		} else {
			System.out.println("Target recording is not exist.");
			return -1;
		}
		return target_recording_index;
	}

	// This function return the name of the additional content in index
	public String getTypeTargetIndexAdditionalContent(int index) throws InterruptedException {
		String additional_content_type = null;
		for (int i = 0; i < 10; i++) {
			try {
						if(!isElementPresent(By.cssSelector("#ItemSize" + Integer.toString(index)))) {
								if(isElementPresent(By.cssSelector("#ItemUrl" +Integer.toString(index)))){
									additional_content_type = driver.findElement(By.cssSelector("#ItemUrl" +Integer.toString(index))).getText();
									break;
								}
						} else {
							additional_content_type = driver.findElement(By.cssSelector("#ItemSize" + Integer.toString(index))).getText();
							break;
						}
			} catch (Exception msg) {
				Thread.sleep(500);
			}
		}
		return additional_content_type;

	}

	// This function get additional content (as string) and type (as string),
	// and checks if it is
	// not exist in additional content list
	public void verifyTargetAdditionalContentIncludingTypeNotInAdditionalContentList(String target_additional_content,
			String target_additional_content_type) throws InterruptedException {
		int number_of_items_in_additional_content_list = driver.findElements(By.cssSelector(".itemTitle")).size();

		for (int i = 0; i < number_of_items_in_additional_content_list; i++) {
			String current_additional_content = getNameTargetIndexAdditionalContent(i + 1);
			String current_additional_content_type = getTypeTargetIndexAdditionalContent(i + 1);

			if ((current_additional_content.equals(target_additional_content))
					&& (current_additional_content_type.equals(target_additional_content_type))) {
				System.out.println("Target additional content is in additional content list.");
				ATUReports.add("Additional content list.", "Target additional content not in the list.",
						"Target additional content in the list.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Assert.assertTrue(false);
				return;
			}
		}

		System.out.println("Target additional content is not in additional content list.");
		ATUReports.add("Additional content list.", "Target additional content not in the list.",
				"Target additional content not in the list.", LogAs.PASSED, null);
		Assert.assertTrue(true);
	}

	// This function get additional content (as string), and checks if it is
	// exist in additional content list
	public void verifyTargetAdditionalContentIncludingTypeInAdditionalContentList(String target_additional_content,
			String target_additional_content_type) throws InterruptedException {
		int number_of_items_in_additional_content_list = driver.findElements(By.cssSelector(".itemTitle")).size();

		for (int i = 0; i < number_of_items_in_additional_content_list; i++) {
			String current_additional_content = getNameTargetIndexAdditionalContent(i + 1);
			String current_additional_content_type = getTypeTargetIndexAdditionalContent(i + 1);

			if ((current_additional_content.equals(target_additional_content))
					&& (current_additional_content_type.equals(target_additional_content_type))) {
				System.out.println("Target additional content is in additional content list.");
				ATUReports.add("Additional content list.", "Target additional content in the list.",
						"Target additional content in the list.", LogAs.PASSED, null);
				Assert.assertTrue(true);
				return;
			}
		}
	}

	// This function get list of options and checks if it is the only options
	// enabled in Recording Task submenu
	public void verifyTargetListOfOptionIsTheOnlyOptionsWhichEnabledInRecordingTaskMenu(List<String> target_option_list)
			throws InterruptedException {
		moveToElement(recording_tasks_button, driver).perform();
		Thread.sleep(1000);

		String[] all_options = driver.findElements(By.cssSelector(".menu-container>.dropdown-menu")).get(3).getText()
				.split("\n");

		for (String option : all_options) {
			if (target_option_list.contains(option)) {
				System.out.println("T"); // Call this option verify that this
											// option enabled
				verifyTargetStringWebElementEnableDisable(option, true);
			} else {
				System.out.println("F"); // Call this option verify that this
											// option diabled
				verifyTargetStringWebElementEnableDisable(option, false);
			}
		}

	}

	// This function get list of options and checks if it is the only options
	// enabled in Course Task submenu
	public void verifyTargetListOfOptionIsTheOnlyOptionsWhichEnabledInCourseTasksMenu(List<String> target_option_list)
			throws InterruptedException { 
		wait.until(ExpectedConditions.visibilityOf(course_task_button));
		Actions builder = new Actions(driver);
		builder.moveToElement(course_task_button).build().perform();

		String[] all_options = driver.findElements(By.cssSelector(".menu-container>.dropdown-menu")).get(2).getText()
				.split("\n");

		for (String option : all_options) {
			if (target_option_list.contains(option)) {
				System.out.println("T"); // Call this option verify that this
											// option enabled
				verifyTargetStringWebElementEnableDisable(option, true);
			} else {
				System.out.println("F"); // Call this option verify that this
											// option diabled
				verifyTargetStringWebElementEnableDisable(option, false);
			}
		}

	}

	// This function get option (String) and status (enable/disable)
	// This function will assgin for that string the correct WebElement
	public void verifyTargetStringWebElementEnableDisable(String target, boolean enable_disable) {
	// This function will check if for that WebElement it is the correct status
		WebElement webElement = null;
		
		System.out.println(target);
		switch (target){
		case "Download": 		webElement = download_button; 
		break;
		case "RSS Feed": 		webElement = rssfeed; 		  
		break;
		case "Podcast": 		webElement = podcast_button;  
		break;
		case "Video Podcast": 	webElement = video_podcast;   
		break;
		case "Move": 		webElement = move_button_on_recording_tasks_menu; 
		break;
		case "Copy": 		webElement = copy_button2; 
		break;
		case "Delete": 		webElement = delete_button; 
		break;
		case "Publish": 		webElement = publish_button; 		  
		break;
		case "Upload to YouTube": 		webElement = upload_to_youtube_button;  
		break;
		case "Request Captions": 	webElement = request_captions_button;   
		break;
		case "Edit recording": 	webElement = edit_rec_button;   
		break;
		case "Edit recording properties": 	webElement = edit_rec_properties_button;   
		break;
		case "Share recording": 	webElement = share_recording_button;   
		break;	
		case "Tag": 	webElement = tag_button;   
		break;	
		default:	webElement = download_button; 
		break;
		
		}

		if (enable_disable) {
			System.out.println("if");
			verifyElementIsEnabled(webElement, target);
		} else {
			System.out.println("else");
			verifyElementIsDisabled(webElement, target);
		}
	}

	// This function waits until first recording status "Moving\Copying"
	// dissaper
	public void waitUntilFirstRecordingMovingCopyingstatusDissaper() throws InterruptedException {
		int time_counter = 0;
		while (first_recording_status.getText().contains("Moving/Copying")) {
			time_counter++;
			Thread.sleep(1000);

			if (time_counter > 120) {
				System.out.println("Timeout - Moving/Copying still appears after 120 seconds");
				ATUReports.add("Timeout -Moving/Copying still appears after 120 seconds", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Assert.assertTrue(false);
				return;
			}
		}

		System.out.println("Moving/Copying dissapear from first recording.");
		ATUReports.add("Moving/Copying dissapear from first recording.", LogAs.PASSED, null);
		Assert.assertTrue(true);
	}

	public WebElement getCheckbox() {

			wait.until(ExpectedConditions.presenceOfElementLocated(By.id("Checkbox1")));
			wait.until(ExpectedConditions.elementToBeClickable(By.id("Checkbox1")));		
		
		return checkbox;
	}

	public void setCheckbox(WebElement checkbox) {
		this.checkbox = checkbox;
	}

	// This function get index i, and check that recording in this index have no
	// bookmark
	public void verifyIndexRecordingHaveNoBookmark(int index) {
		if (bookmarks_list.get(index - 1).getAttribute("class").contains("ng-hide")) {
			System.out.println("Verified that target recording have no bookmark.");
			ATUReports.add("Target recording have no bookmark.", "True.", "True.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Not verified that target recording have no bookmark.");
			ATUReports.add("Target recording have no bookmark.", "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}

	// This function get index i, and check that recording in this index have
	// bookmark
	public void verifyIndexRecordingHaveBookmark(int index) {
		if (bookmarks_list.get(index - 1).getAttribute("class").equals("bookmark")) {
			System.out.println("Verified that target recording have bookmark.");
			ATUReports.add("Target recording have bookmark.", "True.", "True.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Not verified that target recording have bookmark.");
			ATUReports.add("Target recording have bookmark.", "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}
	
	// This function get index i, and return it correct Bookmark WebElement
	public WebElement getIndexBookmarkWebElement(int index) {
		return bookmarks_list.get(index - 1);
	}
	
	// This function get index i, and return it correct Date WebElement
	public WebElement getIndexDateWebElement(int index) {
		return driver.findElement(By.id("RecordingDate" + Integer.toString(index)));
	}
	
	// This function verify that recording chapters are display sequentially.
	public void verifyThatRecordingChaptersAreDisplySequentially() {
		WebElement pre_web_element = video_wraps_of_chapters_of_opened_recording_list.get(0);
		for(int i=1; i<video_wraps_of_chapters_of_opened_recording_list.size(); i++) {
			WebElement curr_web_element = video_wraps_of_chapters_of_opened_recording_list.get(i);
			
			if(pre_web_element.getLocation().x < curr_web_element.getLocation().x) {
				pre_web_element = curr_web_element;
			} else {
				if(pre_web_element.getLocation().y < curr_web_element.getLocation().y) {
					pre_web_element = curr_web_element;
				} else {
					System.out.println("Not verified that recording chapters are display sequentially.");
					ATUReports.add("Recording chapters are display sequentially.", "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
					Assert.assertTrue(false);
				}
			}
		}
		System.out.println("Verified that recording chapters are display sequentially.");
		ATUReports.add("Recording chapters are display sequentially.", "True.", "True.", LogAs.PASSED, null);
		Assert.assertTrue(true);
	}
	
	// This function verify that it's possible to scroll the recordings listing page to get to each recording.
	public void verifyPossibleToScrollTheRecordingList() throws InterruptedException {
		List<WebElement> recordings_list = driver.findElements(By.cssSelector(".recordingTitle"));
		for(WebElement webElement: recordings_list) {
//			org.openqa.selenium.interactions.Action move_to_element = moveToElement(webElement, driver);
//			if(move_to_element==null) {
//				System.out.println("It is not possible to scroll the recordings list page.");
//				ATUReports.add("It is possible to scroll the recording list page.", "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
//				Assert.assertTrue(false);
//			}
//			move_to_element.perform();
			moveToElement(webElement,driver).perform();
			Thread.sleep(100);
		}
		System.out.println("It is possible to scroll the recordings list page.");
		ATUReports.add("It is possible to scroll the recording list page.", "True.", "True.", LogAs.PASSED, null);
		Assert.assertTrue(true);
	}
	
	// This function verify that when scrolling the page down, Once the menu bar is scrolled to the upper part of the page, His position become's absolute and he does not leave the frame.
	// For recording page that have more then 1 page of recordings
	public void verifyWhenScrollingThePageDownMenuBarIsScrolledToTheUpperPartOfThePage() throws InterruptedException {
		verifyPossibleToScrollTheRecordingList();
		List<WebElement> recordings_list = driver.findElements(By.cssSelector(".recordingTitle"));
		WebElement menu_bar = driver.findElement(By.cssSelector(".row.menu-wrap"));
		moveToElementAndPerform(menu_bar,driver);
	
		
		if(recordings_list.get(0).getLocation().y < menu_bar.getLocation().y)
		{
			System.out.println("Verified that menu bar is scrolled to the uppser part of the page.");
			ATUReports.add("Verified that menu bar is scrolled to the uppser part of the page.", "True.", "True.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Not verified that menu bar is scrolled to the uppser part of the page.");
			ATUReports.add("Verified that menu bar is scrolled to the uppser part of the page.", "True.", "False", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}
	
	
	// This function verifys that 
	// The recording chapters contain the ordinal numbers.
	// The recording chapters contain the length from – to in a format as follows: “X:XX:XX – X:XX:XX".
	public void verifyRecordingChaptersContainsOrdinalNumberAndContainLengthFromToInAFormat() {
		
			List<WebElement> ordinal_numbers_and_length = driver.findElements(By.cssSelector(".duration"));
			List<String> to_parse = new ArrayList<String>();
			for(WebElement webElement: ordinal_numbers_and_length) {
				to_parse.add(webElement.getText());
			}
			
			int pre_seq = 0;
			for(int i=0; i<to_parse.size(); i++) {
				if(pre_seq==0) {
					pre_seq = Integer.parseInt(to_parse.get(0).split(" ")[0].substring(0, to_parse.get(0).split(" ")[0].length()-1));
				} else {
					int curr_seq = Integer.parseInt(to_parse.get(i).split(" ")[0].substring(0, to_parse.get(i).split(" ")[0].length()-1));
					if((curr_seq - pre_seq) != 1) {
						System.out.println("Recording chapters not contain the ordinal numbers.");
						ATUReports.add("Recording chapters contain the ordinal numbers.", "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
						Assert.assertTrue(false);
					}
					pre_seq=curr_seq;
				}
				
				int init_hour = Integer.parseInt(to_parse.get(i).split(" ")[1].split(":")[0]);
				int init_min = Integer.parseInt(to_parse.get(i).split(" ")[1].split(":")[1]);
				int init_sec = Integer.parseInt(to_parse.get(i).split(" ")[1].split(":")[2]);
				
				int end_hour = Integer.parseInt(to_parse.get(i).split(" ")[3].split(":")[0]);
				int end_min = Integer.parseInt(to_parse.get(i).split(" ")[3].split(":")[1]);
				int end_sec = Integer.parseInt(to_parse.get(i).split(" ")[3].split(":")[2]);
				
				if(		(init_hour<0) &&
						(end_hour<0) &&
						(init_min<0) &&
						(end_min<0) &&
						(init_sec<0) &&
						(end_sec<0) &&
						(init_min>59) &&
						(end_min>59) &&
						(init_sec>59) &&
						(end_sec>59)) {
					System.out.println("The recording chapters contain the length from - to in an incorrect format.");
					ATUReports.add("The recording chapters contain the length from – to in an correct format.", "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
					Assert.assertTrue(false);
				}
				
			}
			System.out.println("Recording chapters contain the ordinal numbers.");
			ATUReports.add("Recording chapters contain the ordinal numbers.", "True.", "True.", LogAs.PASSED, null);
			Assert.assertTrue(true);
			
			System.out.println("The recording chapters contain the length from - to in an correct format.");
			ATUReports.add("The recording chapters contain the length from – to in an correct format.", "True.", "True.", LogAs.PASSED, null);
			Assert.assertTrue(true);
			
	}
	
	// This function return number of chapters of opened recordings
	public int getNumberOfChaptersOfOpenedExpandedRecordings() {
		return driver.findElements(By.cssSelector(".video-wrap")).size();
	}
	
	// This function verify that the recording chapters contain the image preview.
	public void verifyThatRecordingChaptersContainImagePreview() {
		int number_of_recordings = getNumberOfChaptersOfOpenedExpandedRecordings();
		
		if(number_of_recordings == driver.findElements(By.cssSelector(".thumbnail-image")).size()) {
			System.out.println("Verified that the recording capters contain the image preview.");
			ATUReports.add("Recording chapters contain the image preview.", "True.", "True.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Not verified that the recording capters contain the image preview.");
			ATUReports.add("Recording chapters contain the image preview.", "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}
	
	// This function verify that recording collapsed.
	public void verifyThatRecordingCollapsed() {
		verifyWebElementNotDisplayed(driver.findElements(By.cssSelector(".video-wrap")).get(0), "Recording chapters");
	}
	
	// This function verify that recording expanded.
	public void verifyThatRecordingExpanded() {
		verifyWebElementDisplayed(driver.findElements(By.cssSelector(".video-wrap")).get(0), "Recording chapters");
	}
	
	// This function verify that the blue bookmark icon is displayed prior to the recording adding date (If bookmarks/notes exist).	
	public void verifyThatBookmarkIconDisplayedLeftToRecordingDateIfExist() {
		for(WebElement webElement: recordings_list_date) {
			String[] splited_date_bookmark = webElement.getText().split("\n");
			if(splited_date_bookmark.length == 2) {
				if (!splited_date_bookmark[0].equals("bookmark")) {
					System.out.println("Not verify that the bookmark icon is displayed prior to the recording date.");
					ATUReports.add("Verify that the bookmark icon is displayed prior to the recording date.", "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
					Assert.assertTrue(false);
				}
			}
		}
		System.out.println("Verify that the bookmark icon is displayed prior to the recording date.");
		ATUReports.add("Verify that the bookmark icon is displayed prior to the recording date.", "True.", "True.", LogAs.PASSED, null);
		Assert.assertTrue(true);
	}
	
	// This function verify that the format is: length: X:XX:XX.
	public void verifyFormatOfRecordingLength() {
		for(WebElement webElement: recordings_list_duratuon) {
			String[] splited_length = webElement.getText().split(" ");
			
			int hour = Integer.parseInt(splited_length[1].split(":")[0]);
			int min = Integer.parseInt(splited_length[1].split(":")[1]);
			int sec = Integer.parseInt(splited_length[1].split(":")[2]);
			
			if((0<=hour) && (0<=min) && (0<=sec) && (min<=60) && (sec<=60) && (splited_length[0].equals("length:"))) {
				continue;
			} else {
				System.out.println("Not verfiy that the format is: length: X:XX:XX.");
				ATUReports.add("Verify that the format is: length: X:XX:XX.", "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Assert.assertTrue(false);
			}
		}
		System.out.println("Verfiy that the format is: length: X:XX:XX.");
		ATUReports.add("Verify that the format is: length: X:XX:XX.", "True.", "True.", LogAs.PASSED, null);
		Assert.assertTrue(true);
	}
	
	// This function verify that the format of recorded by is: Recorded by: Username.
	public void verifyFormatOfRecordingRecordedBy() {
		int number_of_recordings = getNumberOfRecordings();
		for(int i=1; i<=number_of_recordings; i++) {
			String[] splited_recorded_by = driver.findElement(By.id("RecordedBy" + Integer.toString(i))).getText().split(" ");
			
			if((splited_recorded_by[0].equals("recorded")) && (splited_recorded_by[1].equals("by:")) && (splited_recorded_by[2].length() > 0)) {
				continue;
			} else {
				System.out.println("No verifed that the format is: recorded by: Username");
				ATUReports.add("Verifed that the format is: recorded by: Username", "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Assert.assertTrue(false);
			}
		}
		
		System.out.println("Verifed that the format is: recorded by: Username");
		ATUReports.add("Verifed that the format is: recorded by: Username", "True.", "True.", LogAs.PASSED, null);
		Assert.assertTrue(true);
	}
	
	// This function verify that the format of Recording adding date is: “mm/dd/yyyy”
	public void verifyFormatOfRecordingsAddingDate() {
		for(WebElement webElement: recordings_list_date) {
			String[] splited_date_bookmark = webElement.getText().split("\n");
			String date = null;
			if(splited_date_bookmark.length == 2) {
				date = splited_date_bookmark[1];
			} else {
				date = splited_date_bookmark[0];
			}
			
			String[] splited_date = date.split("/");
			int mm = Integer.parseInt(splited_date[0]);
			int dd = Integer.parseInt(splited_date[1]);
			int yyyy = Integer.parseInt(splited_date[2]);
			
			if((mm>0) && (dd>0) && (yyyy>1000) && (mm<13) && (dd<31) && (yyyy<10000)) {
				continue;
			} else {
				System.out.println("Not verfied that the format of recording adding date is: mm/dd/yyyy.");
				ATUReports.add("Verfied that the format of recording adding date is: mm/dd/yyyy.", "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Assert.assertTrue(false);
			}
		}
		
		System.out.println("Verfied that the format of recording adding date is: mm/dd/yyyy.");
		ATUReports.add("Verfied that the format of recording adding date is: mm/dd/yyyy.", "True.", "True.", LogAs.PASSED, null);
		Assert.assertTrue(true);
		
	}
	
	// Thie function verify that
	// Validate the "Breadcrumb" links are displayed correctly.
	// The courses "breadcrumb" link is displayed at the top left corner of the page.
	// The '>' character is displayed before the breadcrumb link.
	public void verifyThatBreadcrumbDisplayedCorrectly() {
		String[] splited_breadcrumb = breadcrumbs.getText().split(" ");
		
		if((splited_breadcrumb[0].equals(">")) && (splited_breadcrumb[1].equals("Courses")) && (breadcrumbs.getLocation().x < searchbox.getLocation().x) &&
				(searchbox.getLocation().y < breadcrumbs.getLocation().y) && (breadcrumbs.getLocation().y < tegrity_logo.getLocation().y)) {
			System.out.println("Verfied the breadcrumb UI.");
			ATUReports.add("Verfied the breadcrumb UI.", "True.", "True.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Not verfied the breadcrumb UI.");
			ATUReports.add("Verfied the breadcrumb UI.", "True.", "True.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}
	
	// This function get string and check if is the correct course title
	public void verifyThatStringIsCourseName(String subname) {
		if (getCourseTitle().equals(subname)) {
			System.out.println("Verified that course name is target name: " + subname);
			ATUReports.add("Verified that course name is target name.", "True.", "True.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Not verified that course name is target name: " + subname);
			ATUReports.add("Verified that course name is target name.", "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}
	
	// This function verify that target type of tab is chosen
	// 0 - recordings, 1 - additional content, 2 - student recordings, 3 - tests
	public void verifyThatTargetTypeOfTabIsChosen(int type) {
		if((type>4) || (type<0)) {
			System.out.println("Invalid input for checking if target tab is chosen.");
		}
		
		String init_background_color = null;
		String str_tab = null;
		
		
		switch (type) {
		case 0:
			init_background_color = getBackGroundColor(recordings_tab);
			str_tab = "Recordings";
			break;
		case 1:
			init_background_color = getBackGroundColor(additional_content_tab);
			str_tab = "Additional Content";
			break;
		case 2:
			init_background_color = getBackGroundColor(student_recordings_tab);
			str_tab = "Student Recordings";
			break;
		case 3:
			init_background_color = getBackGroundColor(test_tab);
			str_tab = "Tests";
		}
		
		
		if(init_background_color.equals("#ffffff")) {
			System.out.println("Verfied that the " + str_tab + " tab is chosen by default");
			ATUReports.add("Verfied that " + str_tab + " tab is chosen.", "True.", "True.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Not verfied that the " + str_tab + " tab is chosen by default");
			ATUReports.add("Verfied that " + str_tab + " tab is chosen.", "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}
	
	// This function verify recordings menu locations
	// Validate the tab menu is displayed correctly under the course name.
	// The "View" drop-down is displayed and left aligned.
	// The "Course Tasks" drop-down is displayed next to the "View" element.
	// The "Recording Task" drop-down is displayed and right aligned.
	// The check box to select/deselect all the records in the course is displayed on the right of the "Recording Task".
	public void verifyMenuLocations() {
		Point course_name = course_title.getLocation();
		Point view_but = view_button.getLocation();
		Point course_task_but = course_task_button.getLocation();
		Point recording_task_but = recording_tasks_button.getLocation();
		Point checkbox = check_all_checkbox.getLocation();
			
		if ((course_name.getY() < view_but.getY()) &&
				(view_but.getY() == course_task_but.getY()) &&
				(course_task_but.getY() == recording_task_but.getY()) &&
				(view_but.getX() < course_task_but.getX()) &&
				(course_task_but.getX() < recording_task_but.getX()) &&
				(recording_task_but.getX() < checkbox.getX())) {
			System.out.println("Verfied menus location.");
			ATUReports.add("Verfied menus location.", "True.", "True.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Not verfied menus location.");
			ATUReports.add("Verfied menus location.", "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}

	}
	
	// This function clicks on recording tasks then download
	// This function click on Recorind Task then on copy in the sub menu
	public void clickOnRecordingTasksThenDownload() throws InterruptedException {

		try {
			Robot robot = new Robot();
			robot.mouseMove(-100, -100);
		} catch (AWTException e1) {
			// TODO Auto-generated catch block
			System.out.println("Could not use the robot.");
		}
		
		try {
			waitForVisibility(recording_tasks_button);
			moveToElementAndClick(recording_tasks_button, driver);
			download_button.click();
			
			System.out.println("Clicked on Recording Tasks then Download.");
			ATUReports.add("Clicked on Recording Tasks then Download.", "True.", "True.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} catch(Exception msg) {
			System.out.println("Fail to on Recording Tasks then Download.");
			ATUReports.add("Clicked on Recording Tasks then Download.", "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
		
		
	}
	
	// This function go over all recording status and checks that recording status of type which available for delete that recordings
	
	public void checkExistenceOfNonDeleteRecordingsStatusInRecordings() throws InterruptedException {
		int i = 0;
		Thread.sleep(1000);
		for (WebElement e : driver.findElements(By.cssSelector(".recordingStatus"))) {
			
			
				String current_element = e.getText();
				System.out.println(current_element);
			if ((!current_element.equals("")) && (!current_element.equals("IE, FF, Safari Ready")) && (!current_element.equals("Not Published"))) {
				System.out.println("Not verfired that all recordings have non delete status.");
				ATUReports.add("Verfied that all recordings have delete available status.", "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				ATUReports.add("the status is: " + current_element, "True.", "False.", LogAs.WARNING, null);
				Assert.assertTrue(false);
			  }
			 else {
				break;
			}
		}
		System.out.println("Verfired that all recordings have delete available status.");
		ATUReports.add("Verfied that all recordings have delete available status.", "True.", "True.", LogAs.PASSED, null);
		Assert.assertTrue(true);
	}
	
	public void checkExistenceOfNoncopyableRecordingsStatusInRecordings() throws InterruptedException {
		int i = 0;
		Thread.sleep(1000);
		for (WebElement e : driver.findElements(By.cssSelector(".recordingStatus"))) {
			
			
				String current_element = e.getText();
				System.out.println(current_element);
			if ((!current_element.equals("")) && (!current_element.equals("IE, FF, Safari Ready")) && (!current_element.equals("Not Published"))&& (!current_element.equals("Being copied from"))) {
				System.out.println("Not verfired that all recordings have non delete status.");
				ATUReports.add("Verfied that all recordings have delete available status.", "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				ATUReports.add("the status is: " + current_element, "True.", "False.", LogAs.WARNING, null);
				Assert.assertTrue(false);
			  }
			 else {
				break;
			}
		}
		System.out.println("Verfired that all recordings have delete available status.");
		ATUReports.add("Verfied that all recordings have delete available status.", "True.", "True.", LogAs.PASSED, null);
		Assert.assertTrue(true);
	}
	
	public void checkExistenceOfNonDeleteRecordingsStatusInRecordingsAndUncheckUndeleteableRecordings() throws InterruptedException {
		int i = 0;
		Thread.sleep(1000);
		for (WebElement e : driver.findElements(By.cssSelector(".recordingStatus"))) {
			
			
				String current_element = e.getText();
				System.out.println(current_element);
			if ((!current_element.equals("")) && (!current_element.equals("IE, FF, Safari Ready")) && (!current_element.equals("Not Published"))) {
				System.out.println("Unchecked checkbox"+e.getAttribute("id").substring(15, 16));
				driver.findElement(By.id("Checkbox"+e.getAttribute("id").substring(15, 16))).click();				
				
				ATUReports.add("Unchecked checkbox"+e.getAttribute("id").substring(15, 15), "", LogAs.PASSED, null);				
			  }
			 else {
				break;
			}
		}
		System.out.println("Verfired that all recordings have delete available status.");
		ATUReports.add("Verfied that all recordings have delete available status.", "True.", "True.", LogAs.PASSED, null);
		Assert.assertTrue(true);
	}
	
	
	public int checkExistenceOfNonEditRecordingsStatusInRecordings() throws InterruptedException {
		int i = 1;
		Thread.sleep(1000);
		for (WebElement e : driver.findElements(By.cssSelector(".recordingStatus"))) {
				String current_element = getTextFromWebElement(e,5);
				System.out.println(current_element);
			if ((!current_element.equals(""))) {
				System.out.println("This record canot been edit.");
				ATUReports.add("This record canot been edit.", "True.", "True.", LogAs.PASSED, null);
				i++;
			  }
			 else {
				break;
			}
		}
		return i;
	}
	
	
	// This function go over all recording status and checks that recording status of type which available for delete that recordings
	public void checkExistenceOfNonDeleteItemsStatusInAdditionalContent() throws InterruptedException {
		int i = 1;
		wait.until(ExpectedConditions.attributeContains(By.xpath("//*[@id=\"main\"]/div[2]/ul/li[3]"), "class", "active"));
		Thread.sleep(1000);
		List<WebElement> elements=driver.findElements(By.cssSelector(".recordingData"));
		
		int size=elements.size();
		System.out.print(size);
		while(i<=size) {
			System.out.println("loop"+i);
				
				Thread.sleep(1000);
				System.out.println("in if");
				WebElement el = driver.findElement(By.id("ItemStatus" + Integer.toString(i)));
				String current_element = el.getText();	
				System.out.println(current_element);
			if ((!current_element.equals("Available"))) {
				new WebDriverWait(driver, 30).until(ExpectedConditions.textToBePresentInElement(el, "Available"));
				System.out.println("Not verfired that all additional Content have non delete status.");
				ATUReports.add("Verfied that all additional Content have delete available status.", "True.", "Status of "+i+" content is: "+current_element, LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				ATUReports.add("the status is: " + current_element, "True.", "False.", LogAs.WARNING, null);
			  }
			 
			
			i++;
		}
		System.out.println("Verfired that all recordings have delete available status.");
		ATUReports.add("Verfied that all recordings have delete available status.", "True.", "True.", LogAs.PASSED, null);
		Assert.assertTrue(true);
	}
	
	public void checkExistenceOfNonDeleteItemsStatusInAdditionalContentAndUncheckUndeleteableContent() throws InterruptedException {
		int i = 1;
		wait.until(ExpectedConditions.attributeContains(By.xpath("//*[@id=\"main\"]/div[2]/ul/li[3]"), "class", "active"));
		List<WebElement> elements=driver.findElements(By.cssSelector(".recordingData"));
		
		int size=elements.size();
		System.out.print(size);
		while(i<=size) {
				System.out.println("loop"+i);
				Thread.sleep(200);
				System.out.println("in if");
				WebElement el = driver.findElement(By.id("ItemStatus" + Integer.toString(i)));
				String current_element = el.getText();	
				System.out.println(current_element);
			if ((!current_element.equals("Available"))) {
				try{
				new WebDriverWait(driver, 10).until(ExpectedConditions.textToBePresentInElement(el, "Available"));
				}catch(org.openqa.selenium.TimeoutException msg){}
				String uncheckId = "Checkbox" + Integer.toString(i);                              
				((JavascriptExecutor) driver).executeScript("document.getElementById(\""+uncheckId+"\").click();");				
				System.out.println("Unchecked checkbox: "+i);
				ATUReports.add("Unchecked checkbox: "+i, "", LogAs.PASSED, null);					
			  }			 
			
			i++;
		}
		System.out.println("Verfired that all recordings have delete available status.");
		ATUReports.add("Verfied that all recordings have delete available status.", "True.", "True.", LogAs.PASSED, null);
		Assert.assertTrue(true);
	}
	
	public boolean tabExists(int tab){
		try{
		System.out.println("tabexist1");
		waitForVisibility(recordings_tab);
		System.out.println("tabexist2");
		boolean exist=false;
		if(tab==1)
			exist=additional_content_tab.isDisplayed();
		else if(tab==2)
			exist=student_recordings_tab.isDisplayed();
		else if(tab==3)
			exist=test_tab.isDisplayed();
		System.out.println("tabexist3");
		return exist;			
		}
		catch(Exception msg){
			System.out.println(msg.getMessage());
			return false;
			
			
		}
		
	}
	
	public String getFirstRecordPlayerName() {
		waitForVisibility(firsr_record_player_name);
		return firsr_record_player_name.getText();
	}
	
	public String getSecondRecordPlayerName() {
		waitForVisibility(second_record_player);
		return second_record_player.getText();
	}
	

	public void clickOnFirstVisibleChapter() {
		try {
			new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(By.className(".panel-collapse collapse in")));
			System.out.println("clickOnFirstVisibleChapter1");
			((JavascriptExecutor) driver).executeScript("document.getElementsByClassName(\"video-wrap\")[0].click();");
			System.out.println("clickOnFirstVisibleChapter1");
			ATUReports.add("Clicked on first chapter", "First Chapter was clicked", "First chapter was clicked",
					LogAs.PASSED, null);
		} catch (org.openqa.selenium.TimeoutException e) {
			try {
				System.out.println("clickOnFirstVisibleChapter3");
				((JavascriptExecutor) driver)
						.executeScript("document.getElementsByClassName(\"video-wrap\")[0].click();");
				System.out.println("clickOnFirstVisibleChapter4"+e.getMessage());
				
				ATUReports.add("Clicked on first chapter", "First Chapter was clicked", "First chapter was clicked",
						LogAs.PASSED, null);
			} catch (Exception e1) {
				System.out.println("clickOnFirstVisibleChapter5");
				ATUReports.add("Clicked on first chapter failed", e1.getMessage(), LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				System.out.println("clickOnFirstVisibleChapter6"+e.getMessage());
				e1.printStackTrace();
				Assert.assertTrue(false);
			}

		} catch (Exception e) {
			System.out.println("clickOnFirstVisibleChapter7");
			ATUReports.add("Clicked on first chapter failed", e.getMessage(), LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			System.out.println("clickOnFirstVisibleChapter8"+e.getMessage());
			Assert.assertTrue(false);
			
		
		}

	}
	
	public void clickOnTheFirstCaptherWithOutTheExpand(){
	
		try{
			System.out.println("clickOnFirstVisibleChapter1");			
			waitForVisibility(first_video_recording);
			((JavascriptExecutor) driver).executeScript("document.getElementsByClassName(\"video-wrap\")[0].click();");
			System.out.println("clickOnFirstVisibleChapter1");
			ATUReports.add("Clicked on first chapter", "First Chapter was clicked", "First chapter was clicked",LogAs.PASSED, null);
		} catch (org.openqa.selenium.TimeoutException e) {
		
			System.out.println("clickOnFirstVisibleChapter3");
			((JavascriptExecutor) driver).executeScript("document.getElementsByClassName(\"video-wrap\")[0].click();");
			System.out.println("clickOnFirstVisibleChapter4"+e.getMessage());
			ATUReports.add("Clicked on first chapter", "First Chapter was clicked", "First chapter was clicked",LogAs.PASSED, null);
				
		}
	}
	
	
	public void clickOnTheCaptherWithOutTheExpandOnTheIdnex(int index){
		
		try{
			System.out.println("clickOnFirstVisibleChapter1");
			waitForVisibility(driver.findElement(By.xpath(".//*[@id='scrollableArea']/div[2]/div/div/div/accordion/div/div[" + Integer.toString(index) + "]/div[2]/div/div[2]/a")));
			((JavascriptExecutor) driver).executeScript("document.getElementsByClassName(\"video-wrap\")[0].click();");
			System.out.println("clickOnFirstVisibleChapter1");
			ATUReports.add("Clicked on first chapter", "First Chapter was clicked", "First chapter was clicked",LogAs.PASSED, null);
		} catch (org.openqa.selenium.TimeoutException e) {
		
			System.out.println("clickOnFirstVisibleChapter3");
			((JavascriptExecutor) driver).executeScript("document.getElementsByClassName(\"video-wrap\")[0].click();");
			System.out.println("clickOnFirstVisibleChapter4"+e.getMessage());
			ATUReports.add("Clicked on first chapter", "First Chapter was clicked", "First chapter was clicked",LogAs.PASSED, null);
				
		}
	}
	
	
	
	public boolean checkIfThereAreRecordingsInTab(){
		try{
			Thread.sleep(1500);
			wait.until(ExpectedConditions.textToBePresentInElementLocated(By.id("wrapper"), "length: "));
			return true;
			}catch(Exception e)
			{
				System.out.println("There are not recordings in the course tab");
				ATUReports.add("There are not recordings in the course tab","There are no recordings in the course tab"+e.getMessage(),LogAs.INFO,null);
				return false;
			}	
	}
	
	public boolean checkIfThereAreContentsInAdditionalTab(){
		try{
			Thread.sleep(1500);
			wait.until(ExpectedConditions.textToBePresentInElementLocated(By.id("wrapper"), "file size:"));
			return true;
			}catch(Exception e)
			{
				try{
					wait.until(ExpectedConditions.textToBePresentInElementLocated(By.id("wrapper"), "url: "));				
					return true;
				}catch(Exception ex){
					System.out.println("There are no contents in the Additional tab");
					ATUReports.add("There are no contents in the Additional tab","There are no contents in the Additional tab"+ex.getMessage(),LogAs.INFO,null);
					return false;					
				}				
			}	
	}

}
