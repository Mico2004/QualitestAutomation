package com.automation.main;

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
import java.util.List;

import javax.lang.model.util.ElementScanner6;
import javax.xml.bind.ParseConversionEvent;

import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.http.impl.client.EntityEnclosingRequestWrapper;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Listeners;

import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;

import java.io.StringReader;

import java.util.Set;

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
	WebElement background;
	@FindBy(id = "StartRecordingButton")
	WebElement recording_button;
	@FindBy(linkText = "Recording Tasks")
	WebElement recording_tasks_button;
	@FindBy(id = "CourseTask")
	WebElement course_tasks_button;
	@FindBy(id = "CourseSettings")
	WebElement course_settings_button;
	@FindBy(id = "Podcast")
	WebElement podcast_button;
	@FindBy(linkText = "Copy")
	WebElement copy_button;
	@FindBy(id = "DeleteTask2")
	WebElement delete_button;
	@FindBy(id = "DeleteTask")
	WebElement content_tasks_delete_button;
	@FindBy(id = "Checkbox1")
	WebElement checkbox;
	@FindBy(xpath = "//*[@id=\"CopyButton\"]")
	WebElement copy_menu_recbtn;
	@FindBy(id = "tegritySearchBox")
	WebElement searchbox;
	@FindBy(id = "CheckAll")
	WebElement checkall;
	@FindBy(xpath = "//*[starts-with(@id,'Checkbox')]")
	List<WebElement> checkboxlist;// @FindBy(css="//input[@type='checkbox']")
									// List<WebElement> checkboxlist;
	@FindBy(xpath = "//*[@id='Recording1']/strong")
	WebElement first_recording_title;
	@FindBy(xpath = "//*[@id='Recording1']/strong")
	WebElement first_course_title;
	@FindBy(partialLinkText = "Courses")
	WebElement courses_link;

	@FindBy(id = "CheckAll")
	WebElement check_all_checkbox;
	@FindBy(css = ".ng-pristine.ng-valid")
	List<WebElement> recordings_list_checkbox;
	@FindBy(id = "CourseTask")
	WebElement course_task_button;
	@FindBy(id = "SignOutLink")
	WebElement signout_link;
	@FindBy(id = "RecordingStatus1")
	WebElement first_recording_status;
	@FindBy(id = "StudentRecordingsTab")
	public
	WebElement student_recordings_tab;
	@FindBy(id = "TestsTab")
	WebElement tests_tab;
	@FindBy(id = "AdditionalContentTab")
	WebElement additional_content_tab;
	@FindBy(id = "MoveTask2")
	WebElement move_button_on_recording_tasks_menu;
	@FindBy(id = "CourseSettings")
	WebElement course_setting_in_menu;

	@FindBy(id = "RecordingStatus1")
	WebElement course_being_copied_status;
	@FindBy(id = "RecordingLength1")
	WebElement duration_first_rec;
	@FindBy(xpath = "//*[starts-with(@id,'RecordingLength')]")
	List<WebElement> recordings_list_duratuon;
	@FindBy(xpath = "//*[starts-with(@id,'RecordingDate')]")
	List<WebElement> recordings_list_date;
	@FindBy(xpath = "//*[starts-with(@id,'RecordedBy')]")
	List<WebElement> recordings_list_recordr_name;/// recorder name list
	List<String> recordings_list_date_string;
	public List<String> recording_list_names;
	List<String> recording_list_duration_string;
	List<String> recordings_list_recorder_name_string;
	@FindBy(xpath = "//*[@id=\"Recording1\"]/strong ")
	WebElement first_recording; /// first recording
	@FindBy(xpath = "//*[@id=\"scrollableArea\"]/div[2]/div/div/div/accordion/div/div[1]/div[2]/div/div[2]/a ")
	WebElement first_video_recording;/// first video in first recording
	@FindBy(id = "SortingTasks")
	WebElement view_button; /// view button
	@FindBy(linkText = "Title")
	WebElement sort_by_title; /// title sort
	@FindBy(linkText = "Duration")
	WebElement sort_by_duration; /// title sort
	@FindBy(linkText = "Date")
	WebElement sort_by_date; /// title sort
	@FindBy(id = "RecordingsTab")
	WebElement recording_font_title;///
	@FindBy(linkText = "Move")
	WebElement move_button;///
	@FindBy(id = "ContentTasks")
	WebElement content_tasks_button;
	@FindBy(id = "UserName")
	WebElement user_name;
	@FindBy(id = "CopyTask2")
	WebElement copy_button2;
	// @FindBy (xpath="//*[@class=\"recordingInfoContainer ng-scope\"]/div")
	// List <WebElement> recordings_list;
	@FindBy(id = "TestsTab")
	WebElement test_tab;
	@FindBy(id = "EditRecordingProperties")
	WebElement edit_rec_properties_button;

	@FindBy(xpath = "//a[starts-with(@id,'Recording')]")
	List<WebElement> recordings_list;
	@FindBy(xpath = "//a[starts-with(@id,'NewItem')]")
	List<WebElement> addition_content_list;
	@FindBy(id = "tegrityBreadcrumbsBox")
	WebElement breadcrumbs;
	@FindBy(id = "RSSFeed")
	WebElement rssfeed;
	@FindBy(xpath = "//*[@id=\"scrollableArea\"]/div[1]/div[1]/div[2]/div/ul/li/ul/li[10]/span")
	WebElement subscribe_button;
	@FindBy(id = "VideoPodcast")
	WebElement video_podcast;
	@FindBy(id = "PublishTask")
	WebElement publish_button;
	@FindBy(id = "TagsListTask2")
	WebElement tag_button;
	@FindBy(id = "DownloadRecording")
	WebElement download_button;
	@FindBy(id = "EditRecording")
	WebElement edit_rec_button;
	@FindBy(id = "ShareRecording")
	WebElement share_recording_button;
	@FindBy(id = "StartRecordingButton")
	WebElement start_recording_button;
	@FindBy(id = "StartTestButton")
	WebElement start_test_button;

	@FindBy(id = "UploadRecording")
	WebElement upload_recording;
	@FindBy(id = "UploadVideoFile")
	WebElement upload_video_file;
	@FindBy(id = "UploadAudioFile")
	WebElement upload_audio_file;
	@FindBy(id = "AddAdditionalContentFile")
	WebElement Add_Additional_Content_File;
	@FindBy(id = "AddAdditionalContentLink")
	WebElement Add_Additional_Content_Link;
	@FindBy(id = "GetLiveWebcast")
	WebElement get_live_webcast;
	List<String> additional_content_list_names;
	@FindBy(xpath = "//a[starts-with(@id,'NewItem')]")
	List<WebElement> additional_content_list;
	@FindBy(id = "RecordingsTab")
	public
	WebElement recordings_tab;
	@FindBy(id = "UploadFile")
	WebElement select_upload_additional_file;
	@FindBy(id = "AddFileButton")
	WebElement add_additional_file_button;
	@FindBy(id = "CourseTitle")
	WebElement course_title;
	@FindBy(id = "NewItem1")
	WebElement first_additional_content_title;
	@FindBy(id = "Checkbox2")
	WebElement checkbox2;
	@FindBy(id = "AddAdditionalContentLink")
	WebElement add_additional_content_link;
	@FindBy(id = "DisclaimerLink")
	WebElement disclaimer;
	@FindBy(id = "HelpLink")
	WebElement help;
	@FindBy(id = "ReportsLink")
	WebElement reports;
	@FindBy(id = "MyAccountLink")
	WebElement my_account;
	@FindBy(id = "AddAdditionalContentFile")
	WebElement add_additional_content_file;
	@FindBy(id = "uploadToYoutube")
	WebElement upload_to_youtube_button;
	@FindBy(id = "requestCaptions")
	WebElement request_captions_button;
	@FindBy(id = "DownloadRecording")
	WebElement download;

	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}

	public RecordingHelperPage(WebDriver browser) {
		super(browser);
		// TODO Auto-generated constructor stub
	}

	public void deleteAllRecordings(DeleteMenu delete_menu) throws InterruptedException {
		try {
			wait.until(ExpectedConditions.visibilityOf(checkbox));

			int number_of_recordings_in_target_course = getNumberOfRecordings();

			System.out.println("Number of recordings to delete: " + number_of_recordings_in_target_course);

			if (number_of_recordings_in_target_course == 0) {
				System.out.println("There is no recordings in target course.");
				ATUReports.add("Recordings.", "There is no recordings in target course.",
						"There is no recordings in target course.", LogAs.PASSED, null);
			} else {
				System.out.println("There is recordings in target course, then it will delete them all.");
				ATUReports.add("Recordings.", "There is recordings in target course, then it will delete them all.",
						"There is recordings in target course, then it will delete them all.", LogAs.PASSED, null);

				checkAllCheckBox();
				clickOnRecordingTaskThenDelete();
				Thread.sleep(2000);
				delete_menu.clickOnDeleteButton();
				Thread.sleep(2000);
			}
		} catch (Exception msg) {
			System.out.println("There is no recordings in target course.");
			ATUReports.add("Recordings.", "There is no recordings in target course.",
					"There is no recordings in target course.", LogAs.PASSED, null);
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
				LogAs.FAILED, null);

		Assert.assertEquals(recording_status, status);
		return false;
	}

	// This function check for existence in status of any recording for t time
	// If after t time there is still status, then it will Assert error + log it
	// Otherewise it will pass
	public void checkStatusExistenceForMaxTTime(int time_in_sec) throws InterruptedException {
		for (int i = 0; i < (time_in_sec / 3 + 1); i++) {
			if (!checkExistenceOfStatusInRecordings()) {
				System.out.println("There is no more status for any recording.");
				ATUReports.add("There is no more status for any recording.", LogAs.PASSED, null);
				Assert.assertTrue(true);
				return;
			}
			Thread.sleep(3000);
		}

		System.out.println("There is still status for some recording.");
		ATUReports.add("There is still status for some recording.", LogAs.FAILED, null);
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
					ATUReports.add("Not all recordings is clickable.", LogAs.FAILED, null);
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
	public boolean checkExistenceOfStatusInRecordings() {
		int i = 0;
		for (WebElement e : driver.findElements(By.cssSelector(".recordingData"))) {
			i++;
			String current_element = driver.findElement(By.id("RecordingStatus" + Integer.toString(i))).getText();
			if (!current_element.equals("")) {
				return true;
			}
		}

		return false;
	}

	// This function select first recording from recording list
	public void selectFirstCheckbox() throws InterruptedException {
		for (int i = 0; i < 5; i++) {
			wait.until(ExpectedConditions.elementToBeClickable(checkbox));
//			checkbox.click();
			ClickOneCheckBoxOrVerifyAlreadySelected(checkbox);
			
			if (checkbox.isSelected()) {
				System.out.println("First checkbox is selected");
				ATUReports.add("Select any recording", "First checkbox is selected", "First checkbox is selected",
						LogAs.PASSED, null);
				Assert.assertTrue(true);
				break;
			} else {
				System.out.println("First checkbox is not selected");
				ATUReports.add("Select any recording", "First checkbox is selected", "First checkbox is not selected",
						LogAs.FAILED, null);
				Assert.assertTrue(false);
				Thread.sleep(1000);
			}
		}

	}

	// This function clicks on title of recording in index i
	public void clickOnRecordingTitleInIndex(int index) {
		System.out.println("Click on title in index: " + index);
		searchbox.click();
		wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//*[@id='Recording" + Integer.toString(index) + "']/strong")));
		driver.findElement(By.xpath("//*[@id='Recording" + Integer.toString(index) + "']/strong")).click();

		// driver.findElement(By.xpath("html/body/google-analytics/google-analytics/div[1]/div[2]/div[3]/div[2]/div/div/div/accordion/div/div[1]/div[1]/div/h4/div/div[1]/a/strong")).click();
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
		wait.until(ExpectedConditions.visibilityOf(first_recording_title));
		return first_recording_title.getText();
	}

	// This function waits until first recording status "Being Copied from"
	// dissaper
	public void waitUntilFirstRecordingBeingCopiedFromStatusDissaper() throws InterruptedException {
		int time_counter = 0;
		while (first_recording_status.getText().contains("Being copied from")) {
			time_counter++;
			Thread.sleep(1000);

			if (time_counter > 60) {
				System.out.println("Timeout - Being copied from still appears after 60 seconds");
				ATUReports.add("Timeout - Being copied from still appears after 60 seconds", LogAs.FAILED, null);
				Assert.assertTrue(false);
				return;
			}
		}

		System.out.println("Being copied from dissapear from first recording.");
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
					ATUReports.add("Clicked on podcast.", LogAs.PASSED, null);
					System.out.println("Clicked on podcast.");
					Assert.assertTrue(true);
					break;
				}
			} catch (Exception e) {
			}
			moveToElementAndClick(course_tasks_button, driver);// solution
																// to
																// solve
																// hover
																// and
																// click
			Thread.sleep(1000);
			try {

				podcast_button.click();
				ATUReports.add("Click succeeded.", LogAs.PASSED, null);
				System.out.println("Click succeeded.");
			} catch (Exception e) {
				ATUReports.add("Click failed.", LogAs.FAILED, null);
				System.out.println("Click failed.");
			}
		}

	}

	// This function click on Course Task then on Settings in the sub menu
	public void clickOnCourseTaskThenCourseSettings() throws InterruptedException {
		// waitForVisibility(checkbox);
		// checkbox.click();
		// String clickedRecordingName = first_course_title.getText();
		waitForVisibility(course_tasks_button);
		course_tasks_button.sendKeys(Keys.TAB);

		for (int i = 0; i < 10; i++) {
			course_tasks_button.sendKeys(Keys.TAB);// solution
			try { // to
				course_settings_button.click();

				Thread.sleep(1000);
				if (isElementPresent(By.id("CourseSettings"))) {
					ATUReports.add("Clicked on course settings.", LogAs.PASSED, null);
					System.out.println("Clicked on course settings.");
					Assert.assertTrue(true);
					break;

				}

			} catch (Exception e) {

			}
		}
		Thread.sleep(1000);
		try {

			course_settings_button.click();
			ATUReports.add("Click succeeded.", LogAs.PASSED, null);
			System.out.println("Click succeeded.");
		} catch (Exception e) {
			ATUReports.add("Click failed.", LogAs.FAILED, null);
			System.out.println("Click failed.");
		}

	}

	// This function click on Recorind Task then on delete in the sub menu
	public void clickOnRecordingTaskThenDelete() throws InterruptedException {

		waitForVisibility(recording_tasks_button);
		moveToElementAndClick(recording_tasks_button, driver);
		for (int i = 0; i < 10; i++) {
			recording_tasks_button.sendKeys(Keys.TAB);// solution
			try { // to
				Thread.sleep(500);
				delete_button.click(); // solve

				Thread.sleep(1000);
				if (isElementPresent(By.id("ModalDialogHeader"))) {
					System.out.println("Delete window displayed");
					ATUReports.add("Select Recording Tasks -> Delete menu items", "Delete window is displayed",
							"Delete window is displayed", LogAs.PASSED, null);
					Assert.assertTrue(true);
				}

				return;
			} catch (Exception e) {

			}
		}

		ATUReports.add("Select Recording Tasks -> Delete menu items", "Delete window is displayed",
				"Delete window not displayed", LogAs.FAILED, null);
		System.out.println("Delete window not displayed");
		Assert.assertTrue(false);

	}

	// This function click on Recorind Task then on copy in the sub menu
	public void clickOnRecordingTaskThenCopy() throws InterruptedException {

		try {
			Robot robot = new Robot();
			robot.mouseMove(-100, -100);
		} catch (AWTException e1) {
			// TODO Auto-generated catch block
			System.out.println("couldn't use robot");
		}
		waitForVisibility(recording_tasks_button);
		moveToElementAndClick(recording_tasks_button, driver);
		copy_button.click(); // solve

		try {

			waitForVisibility(driver.findElement(By.id("ModalDialogHeader")));
			if (isElementPresent(By.id("ModalDialogHeader")))// check if
			{
				System.out.println("copy menu verified ");
				ATUReports.add("copy menu verified ", LogAs.PASSED, null);
				Assert.assertTrue(true);
			}
		} catch (Exception e) {
			System.out.println("copy menu not verified ");
			ATUReports.add("copy menu not verified ", LogAs.PASSED, null);
			Assert.assertTrue(false);
		}
	}

	// This function return all recordings of current course
	public List<String> getCourseRecordingList() {

		List<String> recording_names_list = new ArrayList<String>();

		try {
			wait.until(ExpectedConditions.visibilityOf(first_recording_title));
		} catch (Exception msg) {

		}

		for (int i = 0; i < recordings_list.size(); i++) {
			String current_name = recordings_list.get(i).getText();
			if (current_name.equals("Recordings")) {
				continue;
			} else if (current_name.equals("Recording Tasks")) {
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
	public List<String> checkAllCheckBox() {
		wait.until(ExpectedConditions.visibilityOf(checkbox));
		wait.until(ExpectedConditions.visibilityOf(first_course_title));

		check_all_checkbox.click();

		List<String> recording_names_list = getCourseRecordingList();

		for (int i = 0; i < recording_names_list.size(); i++) {
			int j = i + 1;
			String checkbox_indexed = "Checkbox" + Integer.toString(j);
			if (!driver.findElement(By.id(checkbox_indexed)).isSelected()) {
				System.out.println("Not all recording checked.");
				ATUReports.add("Select several recordings", "All recording checked", "Not all recording checked.",
						LogAs.PASSED, null);
				Assert.assertTrue(false);

				return recording_names_list;
			}
		}

		System.out.println("All recording checked.");
		ATUReports.add("Select several recordings", "All recording checked", "All recording checked", LogAs.PASSED,
				null);
		Assert.assertTrue(true);

		return recording_names_list;
	}

	// This function get String as recording name, and compare this string to
	// all title of all recordings in course.
	// It will return true if the recording found. Otherwise it will return
	// false.
	public boolean isRecordingExist(String recording_name, boolean need_to_be_exists) {
		// waitForVisibility(first_course_title);
		wait.until(ExpectedConditions.visibilityOf(first_course_title));
		recording_list_names = convertRecordingsListToNames();
		if (recording_list_names.contains(recording_name)) {
			if (need_to_be_exists == true) {
				System.out.println("Recording is exist.");
				ATUReports.add("Recording is exist.", LogAs.PASSED, null);
				Assert.assertTrue(true);
				return true;
			} else {
				System.out.println("Recording is exist.");
				ATUReports.add("Recording is exist.", LogAs.FAILED, null);
				Assert.assertTrue(false);
				return false;
			}

		}
		if (need_to_be_exists == true) {
			System.out.println("Recording is not exist.");
			ATUReports.add("Recording is not exist.", LogAs.FAILED, null);
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
		for (int i = 0; i < 5; i++) {
			try {
				wait.until(ExpectedConditions.visibilityOf(courses_link));
				courses_link.click();
				Thread.sleep(1000);
			} catch (Exception msg) {
				break;
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
						LogAs.FAILED, null);
				Assert.assertTrue(false);
			}
		} catch (Exception msg) {
			System.out.println("Not clicked on Courses link.");
			ATUReports.add("Not clicked on Courses link.", "Courses page heading",
					"Not found courses page heading. Page url: " + driver.getCurrentUrl(), LogAs.FAILED, null);
			Assert.assertTrue(false);
		}

	}

	// This function clicks on signout link, and return to login page
	public void clickOnSignOut() throws InterruptedException {
		try {
			signout_link.click();
			System.out.println("Click on signout link.");
			ATUReports.add("Click on signout link.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} catch (Exception e) {
			System.out.println("Not clicked on signout link.");
			ATUReports.add("Not clicked on signout link.", LogAs.FAILED, null);
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
				ATUReports.add("Recording is exists.", LogAs.FAILED, null);
				Assert.assertTrue(false);
				return false;
			}
		}
		if (need_to_be_exists == true) {
			ATUReports.add("Recording is not exists.", LogAs.FAILED, null);
			Assert.assertTrue(false);
			return false;
		} else {
			ATUReports.add("Recording is not exists.", LogAs.PASSED, null);
			Assert.assertTrue(true);
			return true;
		}
	}

	// verify check box is selected
	public void ClickOneCheckedboxSelected(WebElement check) throws InterruptedException {
		ClickOneCheckBoxOrVerifyAlreadySelected(check);
//		try {
//			waitForVisibility(check);
//			check.click();
//			System.out.println("one checkboxe is selected");
//			ATUReports.add("one checkboxe is selected", LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
//		} catch (Exception e) {
//			System.out.println("one checkboxe is not selected");
//			ATUReports.add("one checkboxe is  not selected", LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
//		}
//		Thread.sleep(1000);
//		Assert.assertTrue(check.isSelected());

	}

	// verify check box is selected
	public void ClickOneCheckedboxNotSelected(WebElement check) throws InterruptedException {
		unClickOneCheckBoxOrVerifyNotSelected(check);
//		try {
//			waitForVisibility(check);
//			check.click();
//			Thread.sleep(1000);
//			System.out.println("one checkboxe is not selected");
//			ATUReports.add("one checkboxe is not selected", LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
//		} catch (Exception e) {
//			Thread.sleep(1000);
//			System.out.println("one checkboxe is selected");
//			ATUReports.add("one checkboxe is selected", LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
//		}
//
//		Assert.assertFalse(check.isSelected());

	}

	public void verifyAllCheckedboxSelected() throws InterruptedException {// verify
																			// all
																			// check
																			// box
																			// are
																			// selected
																			// or
																			// not

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
		boolean result = false;
		for (WebElement element : checkboxlist) {
			if ((element.isSelected())) {
				result = true;
				System.out.println("some/all checkboxes are  selected");
				ATUReports.add("some/all checkboxes are  selected", LogAs.FAILED,
						new CaptureScreen(ScreenshotOf.DESKTOP));
			}

		}
		if (result == false)
			System.out.println("all checkboxes are  not selected");
		ATUReports.add("all checkboxes are  not selected", LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
		Assert.assertFalse(result);

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
			ATUReports.add("copy button is enabled", LogAs.FAILED, null);
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
			ATUReports.add("menu color is not grey", LogAs.FAILED, null);
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
			String recording_status = driver.findElement(By.id("RecordingStatus" + Integer.toString(index))).getText();
			if (recording_status.equals("")) {
				System.out.println("Recordings in index: " + index + " status empty");
				ATUReports.add("Recordings in index: " + index + " status empty", LogAs.PASSED, null);
				Assert.assertTrue(true);
				return true;
			}

			Thread.sleep(1000);
		}

		System.out.println("Recordings in index: " + index + " status not empty");
		ATUReports.add("Recordings in index: " + index + " status not empty", LogAs.FAILED, null);
		Assert.assertTrue(false);
		return false;
	}

	// this function check that recording status in target index is not target
	// status
	// with max timeout
	public boolean checkThatRecordingStatusTargetIndexIsNotXWithTimeout(int index, String is_not, int time_interval)
			throws InterruptedException {
		for (int i = 0; i < time_interval; i++) {
			String recording_status = driver.findElement(By.id("RecordingStatus" + Integer.toString(index))).getText();
			if (!recording_status.equals(is_not)) {
				System.out.println("Recordings in index: " + index + " is not: " + is_not);
				ATUReports.add("Recordings in index: " + index + " is not: " + is_not, LogAs.PASSED, null);
				Assert.assertTrue(true);
				return true;
			}

			Thread.sleep(1000);
		}
		System.out.println("Recordings in index: " + index + " status is: " + is_not);
		ATUReports.add("Recordings in index: " + index + " status is: " + is_not, LogAs.FAILED, null);
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
		try {
			wait.until(ExpectedConditions.elementToBeClickable(student_recordings_tab));
			student_recordings_tab.click();
			System.out.println("Clicked on student recordings tab");
			ATUReports.add("Click Student Recordings tab", "Clicked on student recordings tab",
					"Clicked on student recordings tab", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} catch (Exception msg) {
			System.out.println("Failed to click on student recordings tab. ERROR: " + msg);
			ATUReports.add("Click Student Recordings tab", "Clicked on student recordings tab",
					"Failed to click on student recordings tab. ERROR: " + msg, LogAs.FAILED, null);
			Assert.assertTrue(false);
		}

	}

	// This function click on Recorind Task then on move in the sub menu
	public void clickOnRecordingTaskThenMove() throws InterruptedException {

		waitForVisibility(recording_tasks_button);
		moveToElementAndClick(recording_tasks_button, driver);

		for (int i = 0; i < 10; i++) {
			recording_tasks_button.sendKeys(Keys.TAB);// solution // to

			try {
				move_button_on_recording_tasks_menu.click();
				// check if list of courses are present
				Thread.sleep(500);
				if (driver.findElement(By.id("ModalDialogHeader")).getText().equals("Move")) {
					ATUReports.add("Select Recording Tasks -> Move menu item", "Move window is displayed",
							"Copy window is displayed", LogAs.PASSED, null);
					Assert.assertTrue(true);
					return;
				}
			} catch (Exception e) {
			}
			Thread.sleep(1000);
		}

		ATUReports.add("Select Recording Tasks -> Move menu item", "Move window is displayed",
				"Move window not displayed", LogAs.FAILED, null);

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
	public void selectIndexCheckBox(int index) {
		try {
			checkbox = driver.findElement(By.id("Checkbox" + Integer.toString(index)));
			checkbox.click();
			if (checkbox.isSelected()) {
				System.out.println("Checkbox is selected in index: " + index);
				ATUReports.add("Checkbox is selected in index: " + index, LogAs.PASSED, null);
				Assert.assertTrue(true);
			} else {
				System.out.println("Checkbox not selected in index: " + index);
				ATUReports.add("Checkbox not selected in index: " + index, LogAs.FAILED, null);
				Assert.assertTrue(false);
			}

		} catch (Exception msg) {
			System.out.println("Checkbox not selected in index: " + index);
			ATUReports.add("Checkbox not selected in index: " + index, LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
	}

	// verify copied course is displaied correctly
	public void verifyRecordingDisplayedCorrectly(String name) {

		if (recording_list_names.contains(name)) {
			Assert.assertTrue(true);
			ATUReports.add("recording copied successfully", LogAs.PASSED, null);
		} else {
			Assert.assertTrue(false);
			ATUReports.add("recording not copied", LogAs.FAILED, null);
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
//
//	/// create new recording list object
//	public List<RecordingObject> createRecordingObjectList() {
//		convertRecordingsListToDuration();
//		convertRecordingsListToDate();
//		convertRecordingsListToNames();
//		convertRecordingsListToRecorderName();
//		List<RecordingObject> recording_list_object = new ArrayList<RecordingObject>();
//		for (int i = 0; i < recordings_list_date.size(); i++) {
//			RecordingObject object = new RecordingObject(recordings_list_date_string.get(i),
//					recording_list_names.get(i), recording_list_duration_string.get(i),
//					recordings_list_recorder_name_string.get(i));
//			recording_list_object.add(object);
//		}
//		return recording_list_object;
//
//	}

	// trim duration string to get only the duration time in string
	public String trimDuration(String s) {
		String[] arr = s.split(" ");
		return arr[1];
	}

	/// verify recording is expandable
	public void verifyFirstExpandableRecording() throws InterruptedException {
		first_recording.click();
		Thread.sleep(1000);
		if (first_video_recording.isDisplayed()) {
			ATUReports.add("video recording was displaied", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			ATUReports.add("video recording was not displaied", LogAs.FAILED, null);
			Assert.assertTrue(true);
		}
		// waitForVisibility(driver.findElement(By.tagName("tegrecorder.exe")));
		// System.out.print(first_recording.getAttribute("type"));
	}

	// press on view button and than select option
	public void pressViewButtonAndSelect(String choice) throws InterruptedException {

		moveToElementAndClick(view_button, driver);
		// rec.copy_button.click();
		for (int i = 0; i < 8; i++)
			view_button.sendKeys(Keys.TAB);// solution
		// hover and click
		// to

		try {
			switch (choice) {
			case "Title":
				sort_by_title.click();
				break;
			case "Date":
				sort_by_date.click();
				break;
			case "Duration":
				sort_by_duration.click();
				break;

			}
			ATUReports.add("click succeded ", LogAs.PASSED, null); // solve

		} catch (Exception e) {
			ATUReports.add("click failed ", LogAs.FAILED, null);

		}

	}

	/// check if sorted alphabatically
	public void verifyRecordingSortedByTitle(List<String> title) {
		String previous = ""; // empty string: guaranteed to be less than or
								// equal to any other

		for (String current : title) {
			current = current.toLowerCase();
			if (current.compareTo(previous) < 0) {
				ATUReports.add("not sorted by title...  ", LogAs.FAILED, null);
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
				ATUReports.add("not sorted by date...  ", LogAs.FAILED, null);
				Assert.assertTrue(false);
			} else {
				if ((Integer.valueOf(splitdate[2]) == Integer.valueOf(previous[2])))// if
																					// years
																					// equal
																					// check
																					// months
				{
					if ((Integer.valueOf(splitdate[0]) < Integer.valueOf(previous[0]))) {
						ATUReports.add("not sorted by date...  ", LogAs.FAILED, null);
						Assert.assertTrue(false);
					} else {
						if ((Integer.valueOf(splitdate[0]) == Integer.valueOf(previous[0])))// if
																							// years
																							// equal
																							// check
																							// days
						{
							if ((Integer.valueOf(splitdate[1]) < Integer.valueOf(previous[1]))) {
								ATUReports.add("not sorted by date...  ", LogAs.FAILED, null);
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
		ATUReports.add("sorted by date correctly ", LogAs.PASSED, null);
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
				ATUReports.add("not sorted by duration...  ", LogAs.FAILED, null);
				Assert.assertTrue(false);
			} else {
				if ((Integer.valueOf(splitdate[0]) == Integer.valueOf(previous[0])))// if
																					// years
																					// equal
																					// check
																					// months
				{
					if ((Integer.valueOf(splitdate[1]) < Integer.valueOf(previous[1]))) {
						ATUReports.add("not sorted by duration...  ", LogAs.FAILED, null);
						Assert.assertTrue(false);
					} else {
						if ((Integer.valueOf(splitdate[1]) == Integer.valueOf(previous[1])))// if
																							// years
																							// equal
																							// check
																							// days
						{
							if ((Integer.valueOf(splitdate[2]) < Integer.valueOf(previous[2]))) {
								ATUReports.add("not sorted by duration...  ", LogAs.FAILED, null);
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
		ATUReports.add("sorted by duration correctly ", LogAs.PASSED, null);
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
		ATUReports.add("recorder  doesn't name equals the original recorder name ", LogAs.FAILED, null);
		Assert.assertTrue(false);
	}

	public void recordingPageBoldFont() {
		System.out.println(recording_font_title.getCssValue("font-weight"));
		if ((recording_font_title.getCssValue("font-weight").equals("bold"))
				|| (recording_font_title.getCssValue("font-weight").equals("700"))) {
			ATUReports.add("font style is bold ", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {

			ATUReports.add("font style is not bold", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}

	}

	/// selects recording by name
	public void selectRecordingByName(String original_recorder_name) throws InterruptedException {

		for (WebElement el : recordings_list) {
			if ((el.getText().equals(original_recorder_name))) {
				WebElement recording = driver.findElement(By.linkText((original_recorder_name)));

				recording.click();
				System.out.println(" course found");
				ATUReports.add(" course found", LogAs.PASSED, null);
				Assert.assertTrue(true);

				return;
			}

		}
		System.out.println(" no such course found");
		ATUReports.add("no such course", LogAs.FAILED, null);
		Assert.assertTrue(false);

	}

	// verify move menu
	public void toMoveMenu() throws InterruptedException {

		waitForVisibility(recording_tasks_button);
		moveToElementAndClick(recording_tasks_button, driver);
		for (int i = 0; i < 10; i++) {
			recording_tasks_button.sendKeys(Keys.TAB);// solution
			try { // to
				move_button.click(); // solve

				Thread.sleep(1000);
				if (isElementPresent(By.id("ModalDialogHeader"))) {
					System.out.println("Move menu confirmed");
					ATUReports.add("click succeeded", LogAs.PASSED, null);
				}

				return;
			} catch (Exception e) {

			}
		}
		// hover
		// and
		// click

		ATUReports.add("click failed ", LogAs.FAILED, null);
		System.out.println("Move to move menu failed");
	}

	// verify move menu
	public void toMoveMenuByContentTask() throws InterruptedException {

//		waitForVisibility(content_tasks_button);
//		moveToElementAndClick(content_tasks_button, driver);
//		for (int i = 0; i < 10; i++) {
//			content_tasks_button.sendKeys(Keys.TAB);// solution
//			try { // to
//				move_button.click(); // solve
//
//				Thread.sleep(1000);
//				if (isElementPresent(By.id("ModalDialogHeader"))) {
//					System.out.println("Move menu confirmed");
//					ATUReports.add("click succeeded", LogAs.PASSED, null);
//				}
//
//				return;
//			} catch (Exception e) {
//
//			}
//		}
//		// hover
//		// and
//		// click
//
//		ATUReports.add("click failed ", LogAs.FAILED, null);
//		System.out.println("Move to move menu failed");
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
	public void selectTargetRecordingCheckbox(String recording_name) {
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
		try {
			additional_content_tab.click();
			System.out.println("Clicked on additional tab");
			ATUReports.add("Clicked on additional tab", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} catch (Exception msg) {
			System.out.println("Failed to click on additional tab. ERROR: " + msg);
			ATUReports.add("Failed to click on additional tab. ERROR: " + msg, LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
	}

	// This function click on Content Task then on copy in the sub menu
	public void clickOnContentTaskThenCopy() throws InterruptedException {

		waitForVisibility(content_tasks_button);
		moveToElementAndClick(content_tasks_button, driver);
		content_tasks_button.sendKeys(Keys.TAB);

		for (int i = 0; i < 6; i++) {
			content_tasks_button.sendKeys(Keys.TAB);// solution
			try {
				copy_button.click();

				ATUReports.add("Clicked on podcast.", LogAs.PASSED, null);
				System.out.println("Clicked on podcast.");
				Assert.assertTrue(true);
				return;

			} catch (Exception e) {
			}
			try {
				if (isElementPresent(By.id("ModalDialogHeader")))// check if
																	// list of
																	// courses
																	// are
																	// present
				{
					ATUReports.add("copy menu verified ", LogAs.PASSED, null);
					Assert.assertTrue(true);
					break;

				}
			} catch (Exception e) {
			}

			Thread.sleep(1000);
			try {

				copy_button.click();
				ATUReports.add("click succeeded", LogAs.PASSED, null);
			} catch (Exception e) {
				ATUReports.add("click failed ", LogAs.FAILED, null);
			}
		}
	}

	// This function click on Content Task then on move in the sub menu
	public void clickOnContentTaskThenMove() throws InterruptedException {
		Robot robot;
		try {
			robot = new Robot();
			robot.mouseMove(-100, 100);
			/// click on content task
			waitForVisibility(content_tasks_button);
			moveToElementAndClick(content_tasks_button, driver);
			try {
				move_button.click();
				System.out.println("Clicked on move");
				waitForVisibility(driver.findElement(By.id("ModalDialogHeader")));
				if (isElementPresent(By.id("ModalDialogHeader"))) {
					System.out.println("move menu verified ");
					ATUReports.add("move menu verified ", LogAs.PASSED, null);
					Assert.assertTrue(true);

				} else {
					System.out.println("move menu not verified ");
					ATUReports.add("move menu verified ", LogAs.FAILED, null);
					Assert.assertTrue(false);

				}

			} catch (Exception e) {
				ATUReports.add("Couldnt Clicked on move", LogAs.FAILED, null);
				System.out.println("Couldnt Clicked on move");
				Assert.assertTrue(false);
			}
		} catch (AWTException e1) {
			System.out.println("couldnt move mouse or clcik on button");
			ATUReports.add("couldnt move mouse or clcik on button", LogAs.FAILED, null);
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
		try {
			tests_tab.click();
			System.out.println("Clicked on tests tab");
			ATUReports.add("Clicked on tests tab", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} catch (Exception msg) {
			System.out.println("Failed to click on tests tab. ERROR: " + msg);
			ATUReports.add("Failed to click ontests tab. ERROR: " + msg, LogAs.FAILED, null);
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
				System.out.println("Checkbox is selected in index: " + index);
				ATUReports.add("Checkbox is selected in index: " + index, LogAs.FAILED, null);
				Assert.assertTrue(false);
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
				ATUReports.add("Recording is exists.", LogAs.FAILED, null);
				Assert.assertTrue(false);
				return false;
			}
		}
		if (need_to_be_exists == true) {
			ATUReports.add("Recording is not exists.", LogAs.FAILED, null);
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
		moveToElementAndClick(recording_tasks_button, driver);
		for (int i = 0; i < 5; i++) {
			recording_tasks_button.sendKeys(Keys.TAB);// solution

		}
		boolean assertion = verifyColor(grey_color, el);
		if (assertion == true) {
			System.out.println("menu color is grey");
			ATUReports.add("menu color is grey", LogAs.PASSED, null);
		} else {
			System.out.println("menu color is not grey");
			ATUReports.add("menu color is not grey", LogAs.FAILED, null);
		}
		Assert.assertTrue(assertion);// compare
										// 2
										// colors
		Thread.sleep(2000);

	}

	// thic function clicks on student recordings tab (in type of recordings
	// menu)
	public void clickOnTestTab() {
		try {
			test_tab.click();
			System.out.println("Clicked on test recordings tab");
			ATUReports.add("Clicked on test recordings tab", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} catch (Exception msg) {
			System.out.println("Failed to click on test recordings tab. ERROR: " + msg);
			ATUReports.add("Failed to click on test recordings tab. ERROR: " + msg, LogAs.FAILED, null);
			Assert.assertTrue(false);
		}

	}

	// thic function clicks on student recordings tab (in type of recordings
	// menu)
	public void clickOnTestAdittionalContentTab() {
//		try {
//			additional_content_tab.click();
//			System.out.println("Clicked on test recordings tab");
//			ATUReports.add("Clicked on test recordings tab", LogAs.PASSED, null);
//			Assert.assertTrue(true);
//		} catch (Exception msg) {
//			System.out.println("Failed to click on test recordings tab. ERROR: " + msg);
//			ATUReports.add("Failed to click on test recordings tab. ERROR: " + msg, LogAs.FAILED, null);
//			Assert.assertTrue(false);
//		}
		clickOnAdditionContentTab();

	}

	// This function waits until first recording status "Being Copied from"
	// dissaper
	public void waitUntilFirstRecordingBeingMovedFromStatusDissaper() throws InterruptedException {
		int time_counter = 0;
		while (first_recording_status.getText().contains("Being moved from")) {
			time_counter++;
			Thread.sleep(1000);

			if (time_counter > 60) {
				System.out.println("Timeout - Being moved from still appears after 60 seconds");
				ATUReports.add("Timeout - Being moved from still appears after 60 seconds", LogAs.FAILED, null);
				Assert.assertTrue(false);
				return;
			}
		}

		System.out.println("Being moved from dissapear from first recording.");
		ATUReports.add("Being moved from dissapear from first recording.", LogAs.PASSED, null);
		Assert.assertTrue(true);
	}

	// verify move menu
	public void toDeleteMenu() throws InterruptedException {
		waitForVisibility(recording_tasks_button);
		moveToElementAndClick(recording_tasks_button, driver);
		for (int i = 0; i < 10; i++) {
			recording_tasks_button.sendKeys(Keys.TAB);
			// solution to solve
			try {
				delete_button.click();

				Thread.sleep(1000);
				if (isElementPresent(By.id("ModalDialogHeader"))) {
					System.out.println("Delete menu confirmed");
					ATUReports.add("click succeeded", LogAs.PASSED, null);
				}

				return;
			} catch (Exception e) {
				System.out.println("Move to delete menu failed");
				ATUReports.add("click failed ", LogAs.FAILED, null);
			}
		}
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
			ATUReports.add("verify recording status is Error", "status", "Error", "Not Error", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
	}

	// This function click on Content Task then on delete in the sub menu
	public void clickOnContentTaskThenDelete() throws InterruptedException {

		waitForVisibility(content_tasks_button);
		moveToElementAndClick(content_tasks_button, driver);
		content_tasks_button.sendKeys(Keys.TAB);

		for (int i = 0; i < 6; i++) {
			content_tasks_button.sendKeys(Keys.TAB);// solution
			try {
				content_tasks_delete_button.click();

				System.out.println("Clicked on content tasks then delete button.");
				ATUReports.add("Click on Content Tasks then Delete.", "Click succeeded.", "Click succeeded.",
						LogAs.PASSED, null);
				Assert.assertTrue(true);
				return;

			} catch (Exception e) {
			}

			try {
				if (isElementPresent(By.id("ModalDialogHeader"))) {
					System.out.println("Delete menu verified.");
					ATUReports.add("Delete menu.", "Delete menu verified.", "Delete menu verified.", LogAs.PASSED,
							null);
					Assert.assertTrue(true);
					break;
				}
			} catch (Exception e) {
			}

			Thread.sleep(1000);
			try {
				content_tasks_delete_button.click();
				ATUReports.add("Click on Content Tasks then Delete.", "Click succeeded.", "Click succeeded.",
						LogAs.PASSED, null);
			} catch (Exception e) {
				ATUReports.add("Click on Content Tasks then Delete.", "Click succeeded.", "Click failed.", LogAs.FAILED,
						null);
			}
		}
	}

	// verify move menu
	public void toEditRecordingPropertiesMenu() throws InterruptedException {
		waitForVisibility(recording_tasks_button);
		moveToElementAndClick(recording_tasks_button, driver);
		for (int i = 0; i < 10; i++) {
			recording_tasks_button.sendKeys(Keys.TAB);
			// solution to solve
			try {
				edit_rec_properties_button.click();

				Thread.sleep(1000);
				if (isElementPresent(By.id("ModalDialogHeaderWrap"))) {
					System.out.println("Edit recording properties menu confirmed");
					ATUReports.add("click succeeded", LogAs.PASSED, null);
				}

				return;
			} catch (Exception e) {
				System.out.println("Move to Edit recording properties menu menu failed");
				ATUReports.add("click failed ", LogAs.FAILED, null);
			}
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
			ATUReports.add("check status", "status text", "Being copied from", val, LogAs.FAILED, null);

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
			ATUReports.add("check status", "status text", "Moving/Copying", val, LogAs.FAILED, null);

			return false;
		}
	}

	// verify tabs location side by side
	public boolean tabsLocationVerified() {
		Point studtab = student_recordings_tab.getLocation();
		Point testtab = tests_tab.getLocation();
		Point recordings = recording_font_title.getLocation();
		Point additionaltab = additional_content_tab.getLocation();
		if ((studtab.getY() == testtab.getY()) && (testtab.getY() == recordings.getY())
				&& (recordings.getY() == additionaltab.getY()) && (studtab.getX() < testtab.getX())
				&& (additionaltab.getX() > recordings.getX()) && (studtab.getX() > additionaltab.getX())) {
			System.out.println("location verified");
			ATUReports.add("location verified", LogAs.PASSED, null);
			Assert.assertTrue(true);
			return true;
		} else {
			ATUReports.add("location not verified", LogAs.FAILED, null);

			System.out.println("location not verified");
			Assert.assertTrue(true);
			return false;
		}
		
	}

	// verify there is no Start Recording button
	public void verifyNoStartRecording() {
		try {
			if (start_recording_button.isDisplayed()) {
				ATUReports.add("Click on start recording button.", LogAs.FAILED, null);
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
				ATUReports.add("Click on start test button.", LogAs.FAILED, null);
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
				ATUReports.add("menu items are displayed", LogAs.FAILED, null);
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
				ATUReports.add("Click on additional tab", LogAs.FAILED, null);
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
		String fullPathToFile = "\\workspace\\QualitestAutomation\\src\\test\\resources\\additional_file.txt"; //// System.getProperty("user.dir")
																												//// +
																												//// "\\src\\main\\resources\\ImsImportDataCreation.xml";

		uploadFile(fullPathToFile);
		Thread.sleep(2000);
		add_additional_file_button.click();
		Thread.sleep(2000);
		confirm.clickOnOkButton();
	}

	/// clicks on content task->upload additonal content file by path
	public void toUploadAdditionalContentFile() throws InterruptedException, Exception {
		Robot robot = new Robot();
		robot.delay(3000);
		robot.mouseMove(0, -100);
		Thread.sleep(2000);
		course_tasks_button.click();
		Thread.sleep(2000);
		add_additional_content_file.click();
		Thread.sleep(2000);

	}

	////// upload additional content
	public void uploadFile(String path) throws Exception {

		// from here you can use as it wrote
		path = System.getProperty("user.dir") + path;
		StringSelection ss = new StringSelection(path);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
		// native key strokes for CTRL, V and ENTER keys
		Robot robot = new Robot();

		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		Thread.sleep(5000);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);

	}

	// verify there is no Student Tab
	public void verifyNoStudentTab() {
		try {
			if (student_recordings_tab.isDisplayed()) {
				System.out.println("Not verfied that there is no Student tab.");
				ATUReports.add("Verfied that there is no Student tab.", "True.", "False.", LogAs.FAILED, null);
				Assert.assertTrue(false);
			} else {
				System.out.println("Verfied that there is no Student tab.");
				ATUReports.add("Verfied that there is no Student tab.", "True.", "True.", LogAs.PASSED, null);
				Assert.assertTrue(true);
			}
		} catch (Exception e) {
			System.out.println("Verfied that there is no Student tab.");
			ATUReports.add("Verfied that there is no Student tab.", "True.", "True.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		}
	}

	// verify there is no tests Tab
	public void verifyNoTestsTab() {
		try {
			if (tests_tab.isDisplayed()) {
				ATUReports.add("Click on tests tab", LogAs.FAILED, null);
				System.out.println("Click on tests tab");
				Assert.assertTrue(false);
			} else {
				ATUReports.add("No Click on tests tab", LogAs.PASSED, null);
				System.out.println("No Click on tests tab");
				Assert.assertTrue(true);
			}
		} catch (Exception e) {
			ATUReports.add("No Click on tests tab", LogAs.PASSED, null);
			System.out.println("No Click on tests tab");
			Assert.assertTrue(true);
		}
	}

	// thic function clicks on Recordings tab tab (in type of recordings
	// menu)
	public void clickOnRecordingsTab() {
		try {
			recordings_tab.click();
			System.out.println("Clicked on recordings tab");
			ATUReports.add("Clicked on recordings tab", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} catch (Exception msg) {
			System.out.println("Failed to click on recordings tab. ERROR: " + msg);
			ATUReports.add("Failed to click on recordings tab. ERROR: " + msg, LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
	}

	// Verify that recooding has no error status
	public void verifyNoErrorStatus(WebElement element) {
		try {
			String status = element.getText();
			if (status.equals("Error")) {
				System.out.println(" recording status is Error");
				Assert.assertTrue(false);
				ATUReports.add("verify recording status is Error", "status", "Error", "Error", LogAs.FAILED, null);
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
			String recording_status = driver.findElement(By.id("ItemStatus" + Integer.toString(index))).getText();
			if (!recording_status.equals(is_not)) {
				System.out.println("additional content file in index: " + index + " is not: " + is_not);
				ATUReports.add("additional content file in index: " + index + " is not: " + is_not, LogAs.PASSED, null);
				Assert.assertTrue(true);
				return true;
			}

			Thread.sleep(1000);
		}
		System.out.println("additional content file in index: " + index + " status is: " + is_not);
		ATUReports.add("additional content file in index: " + index + " status is: " + is_not, LogAs.FAILED, null);
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

		waitForVisibility(recording_tasks_button);
		moveToElementAndClick(recording_tasks_button, driver);
		for (int i = 0; i < 10; i++) {
			recording_tasks_button.sendKeys(Keys.TAB);// solution
			try { // to
				Thread.sleep(500);
				publish_button.click(); // solve

				Thread.sleep(1000);
				if (isElementPresent(By.id("ModalDialogHeader"))) {
					System.out.println("Publish window displayed");
					ATUReports.add("Select Recording Tasks -> Publish item.", "Publish window is displayed.",
							"Publish window is displayed", LogAs.PASSED, null);
					Assert.assertTrue(true);
				}

				return;
			} catch (Exception e) {

			}
		}

		ATUReports.add("Select Recording Tasks -> Publish item.", "Publish window is displayed.",
				"Publish window not displayed.", LogAs.FAILED, null);
		System.out.println("Publish window not displayed.");
		Assert.assertTrue(false);

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
	public String selectRecordingWithOutTargetStatus(String target_status) {
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
				Thread.sleep(1000);
				driver.findElement(By.cssSelector(".panel-body>.video-outer.ng-scope>.video-wrap")).click();
				break;
			}
		}

	}

	// This function checks if Content Tasks button displyed. Return true if it
	// is, and false otherwise
	public boolean isContentTasksButtonDisplay() {
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
			ATUReports.add("Content Taks button.", "Not displayed.", "Displayed.", LogAs.FAILED, null);
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
				"Target additional content not in the list.", LogAs.FAILED, null);
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
						"Target additional content in the list.", LogAs.FAILED, null);
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
			ATUReports.add("Verified that course name contain target subname.", "True.", "False.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
	}

	// This function verify that Start Recording button is displayed
	public void veriftyThatStartRecordingButtonDisplayed() {
		if (recording_button.isDisplayed()) {
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
			ATUReports.add("Course details page.", "Displayed.", "Not displayed.", LogAs.FAILED, null);
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
					LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
	}

	/// selects dditional content file by name
	public void selectAdditionalContentByName(String additional_content_name) throws InterruptedException {

		for (WebElement el : additional_content_list) {
			if ((el.getText().equals(additional_content_name))) {
				WebElement recording = driver.findElement(By.linkText((additional_content_name)));

				recording.click();
				System.out.println(" additional content file found");
				Thread.sleep(3000);

				ATUReports.add(" additional content file found", LogAs.PASSED, null);
				Assert.assertTrue(true);

				return;
			}

		}
		System.out.println(" no such additional content file name");
		ATUReports.add(" no such additional content file name", LogAs.FAILED, null);
		Assert.assertTrue(false);

	}

	/// verify downloaded file is valid
	public void VerifyDownloadedFileIsValid(String file_name) throws Exception {
		String download_path = System.getProperty("user.home") + File.separatorChar + "Downloads" + File.separatorChar
				+ file_name;

		Path download_path_to_delete = Paths.get(download_path);
		String resource_file_path = System.getProperty("user.dir")
				+ "\\workspace\\QualitestAutomation\\resources\\documents\\" + file_name;
		String file1_md5 = getMD5Sum(resource_file_path);
		String file2_md5 = getMD5Sum(download_path);
		try {
			Files.delete(download_path_to_delete);
			File fileTemp = new File(download_path);
			if (fileTemp.exists()) {
				System.out.println("fail to delete file");
				ATUReports.add("fail to delete file", "delete old file", " deleted successfully", "not deleted",
						LogAs.FAILED, null);
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
			ATUReports.add("2 files are  not equal so file is invalid", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}

	}

	/// try to delete file by path
	public void tryToDeleteOlderFile(String file_name_path) {
		try {
			// Delete if tempFile exists
			File fileTemp = new File(file_name_path);
			if (fileTemp.exists()) {
				try {
					fileTemp.delete();
					if (fileTemp.exists()) {
						System.out.println("fail to delete file");
						ATUReports.add("fail to delete file", "delete old file", " deleted successfully", "not deleted",
								LogAs.FAILED, null);
						Assert.assertTrue(false);
					}
					System.out.println("file deleted succesfully");
					ATUReports.add(" old file deleted succesfully", "delete old file", " deleted successfully",
							" deleted successfully", LogAs.PASSED, null);
					Assert.assertTrue(true);
				} catch (Exception e) {
					System.out.println("fail to delete file");
					ATUReports.add("fail to delete file", "delete old file", " deleted successfully", "not deleted",
							LogAs.FAILED, null);
					Assert.assertTrue(false);
				}
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
	public void verifyNoAdditionalContentFileName(String name) throws InterruptedException {
		Thread.sleep(3000);
		convertAdditionalContantListToNames();
		for (String file_name : additional_content_list_names) {

			if (file_name.equals(name)) {
				System.out.println(" selected file name is  displayed.");
				ATUReports.add(" selected file name is  displayed.", LogAs.FAILED, null);
				Assert.assertTrue(false);
				return;
			}

		}
		System.out.println(" selected file name is not displayed.");
		ATUReports.add(" selected file name is not displayed.", LogAs.PASSED, null);
		Assert.assertTrue(true);

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
				"number mark isn't added to file's title", "number mark is added to file's title", LogAs.FAILED, null);
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
			ATUReports.add("top bar menu is not displayed", LogAs.FAILED, null);
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
			ATUReports.add("There are at least 2 recordings in the course.", "True.", "False.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
	}

	// This function verify that Content Task button is not displayed
	public void verifyThatContentTaskButtonNotDisplayed() {
		if (content_tasks_button.isDisplayed()) {
			System.out.println("Not verified that Content Task not displayed.");
			ATUReports.add("Verfied that Content Task button not displayed.", "True.", "False.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		} else {
			System.out.println("Verfied that Content Task button not displayed.");
			ATUReports.add("Verfied that Content Task button not displayed.", "True.", "True.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		}
	}

	// This function verify that "Start a Test" button displayed
	public void verifyThatStartATestDisplayed() {
		verifyWebElementDisplayed(start_test_button, "Start a test");
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
		waitForVisibility(recording_tasks_button);
		moveToElementAndClick(recording_tasks_button, driver);

		for (int i = 0; i < 8; i++) {
			recording_tasks_button.sendKeys(Keys.TAB);
			try {
				String course_url = driver.getCurrentUrl();
				edit_rec_button.click();
				Thread.sleep(500);
				if (!driver.getCurrentUrl().equals(course_url)) {
					ATUReports.add("Clicked on Recording then Edit Recording.", "True.", "True.", LogAs.PASSED, null);
					Assert.assertTrue(true);
					break;

				}
			} catch (Exception e) {
			}
			moveToElementAndClick(recording_tasks_button, driver);

			Thread.sleep(1000);

		}

		Thread.sleep(1000);

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
			tab.click();
			System.out.println("clicked on tab");
			ATUReports.add("failed clicking on tab", " click tab", "tab clicked", "tab clicked", LogAs.PASSED, null);
			Assert.assertTrue(true);
			try {
				Thread.sleep(2000);
				waitForVisibility(checkbox);
				checkbox.click();
				System.out.println("clicked on checkbox");
				toPublishRecording(publish);
				publish.never_select_button.click();
				Thread.sleep(1000);
				publish.save_button.click();
			} catch (Exception t) {
				System.out.println("failed publishing recording");
				ATUReports.add("publishing recording", " click tab", "tab clicked", "tab  not clicked", LogAs.FAILED,
						null);
				Assert.assertTrue(false);
			}
		} catch (Exception e) {
			System.out.println("failed clicking on tab");
			ATUReports.add("failed clicking on tab", " click tab", "tab clicked", "tab  not clicked", LogAs.FAILED,
					null);
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
					LogAs.FAILED, null);
			Assert.assertTrue(false);
		}

	}

	// this function verifies if an element is bold
	public void recordingBoldFont(WebElement e) {
		System.out.println(e.getCssValue("font-weight"));
		if ((e.getCssValue("font-weight").equals("bold")) || (e.getCssValue("font-weight").equals("700"))) {
			ATUReports.add("font style is bold ", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {

			ATUReports.add("font style is not bold", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}

	}

	//// return to recordings page
	public void returnToRecordingPageByClickingBreadcrumbsName(WebElement element) {
		try {
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
						LogAs.FAILED, null);
				Assert.assertTrue(false);
			}
		} catch (Exception exception) {
			System.out.println("failed clicking on course name breadcrumbs");
			ATUReports.add("click on course name", "course name", "clicked", " not clicked", LogAs.FAILED, null);
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
				ATUReports.add("verify message is correct", "message", "visible", "Not visible", LogAs.FAILED, null);
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
				ATUReports.add("download is disabled", "download", " clickable", " clickable", LogAs.PASSED, null);
				Assert.assertTrue(true);
			} else {
				System.out.println("download is disabled");
				ATUReports.add("download is disabled", "download", "clickable", "not clickable", LogAs.FAILED, null);
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
			ATUReports.add(element_name + "is  visible", element_name, "not clickable", "clickable", LogAs.PASSED,
					null);
			Assert.assertTrue(false);

		} catch (Exception e) {
			System.out.println(element_name + "is not visible");
			ATUReports.add(element_name + "is not visible", element_name, "not clickable", "clickable", LogAs.FAILED,
					null);
			Assert.assertTrue(true);
		}
	}

	// verify element is enabled using his element and his name
	public void verifyElementIsEnabled(WebElement element, String element_name) {
		if (!element.getAttribute("class").equals("disabled")) {
			System.out.println(element_name + "is enabled");
			ATUReports.add(element_name + "is enabled", element_name, "clickable", "clickable", LogAs.PASSED, null);
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
			ATUReports.add(element_name + "is disabled", element_name, " not clickable", " not clickable", LogAs.FAILED,
					null);
			Assert.assertTrue(true);

		} else {
			System.out.println(element_name + "is enabled");
			ATUReports.add(element_name + "is enabled", element_name, "not clickable", "clickable", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
	}

	/// to rss feed page
	public String toRssFeed(WebDriver driver) throws Exception {
		// TODO Auto-generated method stub
		Robot robot = new Robot();
		robot.mouseMove(0, -100);
		waitForVisibility(course_tasks_button);
		course_tasks_button.click();
		waitForVisibility(rssfeed);
		rssfeed.click();
		String parentWindow = driver.getWindowHandle();
		Set<String> handles = driver.getWindowHandles();
		String current = "";
		for (String windowHandle : handles) {
			if (!windowHandle.equals(parentWindow)) {
				driver.switchTo().window(windowHandle);
				current = driver.getCurrentUrl();
				driver.close();
				driver.switchTo().window(parentWindow); // cntrl to parent
														// window
			}
		}

		Thread.sleep(2000);
		String rss_url_xml = "view-source:" + current;
		driver.get(rss_url_xml);
		return current;
	}

	/// to rss feed page
	public void toRssFeedPage(WebDriver driver) throws Exception {
		// TODO Auto-generated method stub
		Robot robot = new Robot();
		robot.mouseMove(0, -100);
		waitForVisibility(course_tasks_button);
		course_tasks_button.click();
		waitForVisibility(rssfeed);
		rssfeed.click();

	}

	/// verify rss feed page first clicking course_tasks-->rss_feed
	public void verifyRssFeedPage(WebDriver driver, LoginHelperPage tegrity) {
		try {
			String url = toRssFeed(driver);
			System.out.println("clicked to rss feed page");
			ATUReports.add("verify rss feed page", "Rss_Feed", "clickable", "clickable", LogAs.PASSED, null);
			Assert.assertTrue(true);
			String rss_title = tegrity.getPageUrl().substring(0, tegrity.getPageUrl().length() - 8) + "/api/rss";
			;

			if (url.contains(rss_title)) {
				System.out.println("verified rss page");
				ATUReports.add("verified rss page", rss_title, " contained", " contained", LogAs.PASSED, null);
				Assert.assertTrue(true);
			} else {
				System.out.println(" not verified rss page");
				ATUReports.add("not verified rss page", rss_title, " contained", "not contained", LogAs.PASSED, null);
				Assert.assertTrue(true);
			}

		} catch (Exception e) {
			System.out.println("not able to click to rss feed page");
			ATUReports.add("verify rss feed page", "Rss_Feed", "clickable", "not clickable", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}

	}

	/// to rss feed page
	public String toPodCast(WebDriver driver) throws Exception {
		// TODO Auto-generated method stub
		Robot robot = new Robot();
		robot.mouseMove(0, -100);
		waitForVisibility(course_tasks_button);
		course_tasks_button.click();
		waitForVisibility(rssfeed);
		podcast_button.click();
		String parentWindow = driver.getWindowHandle();
		Set<String> handles = driver.getWindowHandles();
		String current = "";
		for (String windowHandle : handles) {
			if (!windowHandle.equals(parentWindow)) {
				driver.switchTo().window(windowHandle);
				current = driver.getCurrentUrl();
				driver.close();
				driver.switchTo().window(parentWindow); // cntrl to parent
														// window
			}
		}

		Thread.sleep(2000);
		String podcast_url_xml = "view-source:" + current;
		driver.get(podcast_url_xml);
		return current;
	}

	/// verify podcast page first clicking course_tasks-->rss_feed
	public void verifyPodcastPage(WebDriver driver, LoginHelperPage tegrity) {
		try {
			String url = toPodCast(driver);
			System.out.println("clicked to podcast page");
			ATUReports.add("verify podcast page", "Rss_Feed", "clickable", "clickable", LogAs.PASSED, null);
			Assert.assertTrue(true);
			String podcast_title = tegrity.getPageUrl().substring(0, tegrity.getPageUrl().length() - 8) + "/api/rss";

			if (url.contains(podcast_title)) {
				System.out.println("verified podcast page");
				ATUReports.add("verified podcast page", podcast_title, " contained", " contained", LogAs.PASSED, null);
				Assert.assertTrue(true);
			} else {
				System.out.println(" not verified podcast page");
				ATUReports.add("not verified podcast page", podcast_title, " contained", "not contained", LogAs.PASSED,
						null);
				Assert.assertTrue(true);
			}

		} catch (Exception e) {
			System.out.println("not able to click to podcast page");
			ATUReports.add("verify podcast page", "podcast", "clickable", "not clickable", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}

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
			String third = podcast_url.substring(podcast_url.length() - 48, podcast_url.length() - 12);
			org.w3c.dom.NodeList nodeList = document.getElementsByTagName("enclosure");
			for (int i = 0; i < nodeList.getLength(); i++) {
				// Get element
				String element = ((Element) nodeList.item(i)).getAttribute("url");
				if ((element.contains(first)) && ((element.contains(third)))) {
					// extract only pattern
					String pattern = element.substring(53, 89);
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
						"podcast", "clickable", "not clickable", LogAs.FAILED, null);
				Assert.assertTrue(false);

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("first part(university url) of rss url and third(recording characters) not verified");
			ATUReports.add("first part(university url) of rss url and third(recording characters) not verified",
					"podcast", "clickable", "not clickable", LogAs.FAILED, null);
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
								LogAs.FAILED, null);
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
			ATUReports.add("file not exists", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}

	}

	// verify copy menu
	public String verifyCopyMenu() throws InterruptedException {
		try {
			Robot robot = new Robot();
			robot.mouseMove(-100, -100);
		} catch (AWTException e1) {
			// TODO Auto-generated catch block
			System.out.println("couldn't use robot");
		}

		String clickedRecordingName = first_course_title.getText();
		waitForVisibility(recording_tasks_button);
		moveToElementAndClick(recording_tasks_button, driver);
		copy_button.click();
		if (isElementPresent(By.id("copyCourseWindow"))) {
			System.out.println("copy menu verified");
			ATUReports.add("copy menu verified", LogAs.PASSED, null);
		} else {
			System.out.println("no copy menu verification");
			ATUReports.add("no copy menu verification", LogAs.FAILED, null);
		}
		return clickedRecordingName;
	}

	// verify check box is selected
	public void ClickOneCheckBoxOrVerifyAlreadySelected(WebElement checkbox) throws InterruptedException {
		try {
			waitForVisibility(checkbox);
			if (checkbox.isSelected()) {
				System.out.println("one checkbox is selected");
				ATUReports.add("one checkbox is selected", LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
				Assert.assertTrue(true);
				return;
			} else {
				checkbox.click();
				System.out.println("one checkbox is selected");
				ATUReports.add("one checkbox is selected", LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
				Assert.assertTrue(true);
			}
		} catch (Exception e) {
			System.out.println("one checkbox is not selected");
			ATUReports.add("one checkbox is  not selected", LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
			Assert.assertTrue(false);
		}

	}

	// verify check box is selected
	public void unClickOneCheckBoxOrVerifyNotSelected(WebElement check) throws InterruptedException {
		try {
			waitForVisibility(checkbox);
			if (!checkbox.isSelected()) {
				System.out.println("one checkbox is not selected");
				ATUReports.add("one checkbox is not selected", LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
				Assert.assertTrue(true);
				return;
			} else {
				checkbox.click();
				if (!checkbox.isSelected()) {
					System.out.println("one checkbox is not selected");
					ATUReports.add("one checkbox is not selected", LogAs.PASSED,
							new CaptureScreen(ScreenshotOf.DESKTOP));
					Assert.assertTrue(true);
				} else {
					System.out.println("one checkbox is  selected");
					ATUReports.add("one checkbox is   selected", LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
					Assert.assertTrue(false);
				}
			}
		} catch (Exception e) {
			System.out.println("one checkbox is  selected");
			ATUReports.add("one checkbox is   selected", LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
			Assert.assertTrue(false);
		}

	}
	
	// This function click on Recoring Task then on Tag in the sub menu
	public void clickOnRecordingTaskThenTag() throws InterruptedException {

		try {
			Robot robot = new Robot();
			robot.mouseMove(-100, -100);
		} catch (AWTException e1) {
			// TODO Auto-generated catch block
			System.out.println("couldn't use robot");
		}
		waitForVisibility(recording_tasks_button);
		moveToElementAndClick(recording_tasks_button, driver);
		tag_button.click(); // solve
		Thread.sleep(1000);
		try {

			waitForVisibility(driver.findElement(By.id("ModalDialogHeader")));
			if (isElementPresent(By.id("ModalDialogHeader")))// check if
			{
				System.out.println("Tag menu verified.");
				ATUReports.add("Tag menu verified.", LogAs.PASSED, null);
				Assert.assertTrue(true);
			}
		} catch (Exception e) {
			System.out.println("Tag menu not verified.");
			ATUReports.add("Tag menu not verified.", LogAs.PASSED, null);
			Assert.assertTrue(false);
		}
	}
}
