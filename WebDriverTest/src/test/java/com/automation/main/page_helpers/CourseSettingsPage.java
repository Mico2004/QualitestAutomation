package com.automation.main.page_helpers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
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
public class CourseSettingsPage extends Page {
	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}

	public CourseSettingsPage(WebDriver browser) {
		super(browser);
		// TODO Auto-generated constructor stub
	}

	@FindBy(id = "EnableAudioPodcast")
	public WebElement enable_mp3_podcast_button;
	@FindBy(id = "EnableVideoPodcast")
	public WebElement enable_video_podcast_button;
	@FindBy(id = "SaveButton")
	public WebElement ok_button;
	@FindBy(id = "CourseSettings")
	public WebElement course_setting_title;
	@FindBy(id = "PubliclyVisible")
	public WebElement checkbox_make_course_public_visable;
	@FindBy(id = "AllowToRecord")
	public WebElement checkbox_allow_students_to_record;
	@FindBy(id = "AllowToDownload")
	public WebElement checkbox_allow_students_to_download_recordings;
	@FindBy(id = "EnableFeed")
	public WebElement checkbox_enable_rss_feed;
	@FindBy(id = "EnableCounter")
	public WebElement checkbox_enable_counter;
	@FindBy(id = "PublishAfterUpload")
	public WebElement checkbox_publish_after_upload;
	@FindBy(id = "AutomaticallyPublish")
	public WebElement checkbox_automatically_publish;
	@FindBy(id = "RequireAuthentication")
	public WebElement checkbox_require_authentication;
	@FindBy(id = "EnableVideoStreaming")
	public WebElement checkbox_enable_video_streaming;
	@FindBy(id = "DisableStudentPrinting")
	public WebElement checkbox_disable_student_printing;
	@FindBy(id = "EnableWebcast")
	public WebElement checkbox_enable_webcast;
	@FindBy(id = "PostToTwitter")
	public WebElement checkbox_post_to_twitter;
	@FindBy(id = "EnableStudentTesting")
	public WebElement checkbox_enable_student_testing;
	@FindBy(id = "PubliclyVisible")
	public WebElement visibility_of_course_checkbox;
	@FindBy(id = "EnableStudentTesting")
	public WebElement enable_student_testing_checkbox;
	@FindBy(id = "AllowToDownload")
	public WebElement allow_students_download_checkbox;
	@FindBy(id = "AllowToDownload")
	public WebElement allow_download;
	@FindBy(id = "PubliclyVisibleAvaliable")
	public WebElement checkbox_enable_PubliclyVisible;
	@FindBy(xpath= ".//*[@id='tegrityBreadcrumbsBox']/li[1]/a")
	public WebElement courses_button;
	@FindBy(xpath= ".//*[@id='tegrityBreadcrumbsBox']/li[2]/a")
	public WebElement courses_name;
	@FindBy(id = "CourseSettings")
	public WebElement courseTitle;
	@FindBy(xpath= ".//*[@id='main']/div/div/div[1]/h3")
	public WebElement recording_title;
	
	// This function clicks on ok button of copy menu
	public void clickOnOkButton() throws InterruptedException {
		try {
			//Actions builder = new Actions(driver);  //  new line
			//builder.sendKeys(Keys.PAGE_DOWN); //  new line
			//builder.moveToElement(ok_button).build().perform(); //  new line
			waitForVisibility(ok_button);
			String id = "SaveButton";
			//ok_button.click();
			((JavascriptExecutor) driver).executeScript("document.getElementById(\""+id+"\").click();");
			System.out.println("Clicked on ok button.");
			ATUReports.add("Clicked on ok button.", "True.", "True.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} catch (Exception e) {
			System.out.println("Fail click on ok button.");
			ATUReports.add("Clickedon ok button.", "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
		Thread.sleep(3000);
	}
	

	// This function force select enable audio checkbox
	public void enableVideoPodcast() throws InterruptedException {
		if (!enable_video_podcast_button.isSelected()) {
			try {
				enable_video_podcast_button.click();
				System.out.println("Enabled video podcast checkbox.");
				ATUReports.add("Enabled video podcast checkbox.", LogAs.PASSED, null);
				Assert.assertTrue(true);
			} catch (Exception e) {
				System.out.println("Fail to enable video podcast checkbox.");
				ATUReports.add("Fail to enable video podcast checkbox.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Assert.assertTrue(false);
			}
		} else {
			System.out.println("Video podcast checkbox already enabled.");
		}

		// enable_mp3_podcast_button.click();

		Thread.sleep(3000);
	}

	public void waitForPageToLoad(){
		try{
			wait.until(ExpectedConditions.visibilityOf(courses_button));
			wait.until(ExpectedConditions.visibilityOf(courses_name));
			wait.until(ExpectedConditions.visibilityOf(courseTitle));
			wait.until(ExpectedConditions.visibilityOf(recording_title));
			wait.until(ExpectedConditions.visibilityOf(checkbox_enable_counter));
			wait.until(ExpectedConditions.visibilityOf(enable_student_testing_checkbox));
			wait.until(ExpectedConditions.visibilityOf(checkbox_publish_after_upload));
				
		}catch (Exception e){
			ATUReports.add("enter to course settings faild (screenshot)", e.getMessage(),
					LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE) );
			e.printStackTrace();
			Assert.assertTrue(false);
			
		}
	}
	
	// This function force select enable audio checkbox
	public void enableAudioPodcast() throws InterruptedException {
		if (!enable_mp3_podcast_button.isSelected()) {
			try {
				enable_mp3_podcast_button.click();
				System.out.println("Enabled audio podcast checkbox.");
				ATUReports.add("Enabled audio podcast checkbox.", LogAs.PASSED, null);
				Assert.assertTrue(true);
			} catch (Exception e) {
				System.out.println("Fail to enable audio podcast checkbox.");
				ATUReports.add("Fail to enable audio podcast checkbox.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Assert.assertTrue(false);
			}
		} else {
			System.out.println("Audio podcast checkbox already enabled.");
		}

		// enable_mp3_podcast_button.click();

		Thread.sleep(3000);
	}

	// This function verify that course setting page dispaly
	public void verifyCourseSettingDisplay() {
		if (course_setting_title.getText().equals("Course Settings")) {
			System.out.println("Verified that course setting display.");
			ATUReports.add("Course setting page.", "Display.", "Display.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Course setting is not display.");
			ATUReports.add("Course setting page.", "Display.", "Not display.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}

	// This funcion check the checkbox make this course public, and verify that
	// it checks
	public void selectMakeCoursePublicAndVerifyThatItSelected() {
		try {
			waitForVisibility(checkbox_make_course_public_visable);
			clickElementJS(checkbox_make_course_public_visable);
			if (checkbox_make_course_public_visable.isSelected()) {
				System.out.println("Selected make this course public and verified that it selected.");
				ATUReports.add("Select this course public and verify that it selected.", "True.", "True.", LogAs.PASSED,
						null);
				Assert.assertTrue(true);
			} else {
				
				System.out.println("Selected make this course public but verified that it not selected.");
				ATUReports.add("Selected this course public and verify that it selected.", "True.", "False.",
						LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Assert.assertTrue(false);
			}
		} catch (Exception msg) {
			System.out.println("Fail to select the checkbox of make this course public.");
			ATUReports.add("Fail to select the checkbox of make this course public.", "True.", "False.", LogAs.WARNING, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			//Assert.assertTrue(false);
		}
	}

	// This function force select make course public if not selected, otherwise
	// it do nothing
	public void UnselectedMakeCoursePublic() {
			waitForVisibility(checkbox_enable_PubliclyVisible);
			if (checkbox_enable_PubliclyVisible.isSelected()) {
				try {
					checkbox_enable_PubliclyVisible.click();
					System.out.println("Unselected make course public.");
					ATUReports.add("Unselected make course public if selected.", "True.", "True.", LogAs.PASSED, null);
					Assert.assertTrue(true);
				} catch (Exception msg) {
					System.out.println("Fail to unselected make course public if selected.");
					ATUReports.add("Unselected make course public if selected.", "True.", "False", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
					Assert.assertTrue(false);
				}
			} else {			
				System.out.println("Already it is unselected make course public.");
				ATUReports.add("Already it is unselected make course public.", "True.", "True.", LogAs.PASSED, null);
				Assert.assertTrue(true);
			}
	}
	
	public void makeSureThatMakeCoursePublicIsSelected() {
		try {
			if (checkbox_make_course_public_visable.isSelected()) {
				System.out.println("The checkbox of make course public already selected.");
				ATUReports.add("The checkbox of make course public already selected.", "Selected.", "Selected.", LogAs.PASSED, null);
				Assert.assertTrue(true);
			} else {
				selectMakeCoursePublicAndVerifyThatItSelected();
			}
		} catch (Exception msg) {
			msg.printStackTrace();
			ATUReports.add("Make course public." + msg.getMessage(), "Selected.", "Not Selected.",LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
	}

	// This function force unselect make course public if selected, otherwise it
	// do nothing
	public void makeSureThatMakeCoursePublicIsUnSelected() {
		waitForVisibility(checkbox_make_course_public_visable);
		if (checkbox_make_course_public_visable.isSelected()) {
			try {
				clickElementJS(checkbox_make_course_public_visable);
				System.out.println("Unselected make course public.");
				ATUReports.add("Unselected make course public if selected.", "True.", "True.", LogAs.PASSED, null);
				Assert.assertTrue(true);
			} catch (Exception msg) {
				System.out.println("Fail to unselected make course public if selected.");
				ATUReports.add("Unselected make course public if selected.", "True.", "False", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Assert.assertTrue(false);
			}
		} else {			System.out.println("Already it is unselected make course public.");
			ATUReports.add("Unselected make course public if selected.", "True.", "True.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		}
	}

	// This function force checkbox_enable_rss_feed to be enabled
	public void makeSureThatEnableRSSFeedIsSelected() {
		if (checkbox_enable_rss_feed.isSelected()) {
			System.out.println("Enable RSS feed already selected.");
			ATUReports.add("Selected RSS feed checkbox.", "True.", "True.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			try {
				checkbox_enable_rss_feed.click();
				System.out.println("Selected RSS feed checkbox.");
				ATUReports.add("Selected RSS feed checkbox.", "True.", "True.", LogAs.PASSED, null);
				Assert.assertTrue(true);
			} catch (Exception msg) {
				System.out.println("Fail to select RSS feed checkbox.");
				ATUReports.add("Selected RSS feed checkbox.", "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Assert.assertTrue(false);
			}
		}
	}

	// This function force checkbox_allow_students_to_download_recordings to be
	// enabled
	public void makeSureThatAllowStudentsToDownloadRecordingsIsSelected() {
		if (checkbox_allow_students_to_download_recordings.isSelected()) {
			System.out.println("Enable allow students to download recordings already selected.");
			ATUReports.add("Selected allow students to download recordings checkbox.", "True.", "True.", LogAs.PASSED,
					null);
			Assert.assertTrue(true);
		} else {
			try {
				checkbox_allow_students_to_download_recordings.click();
				System.out.println("Selected allow students to download recordings checkbox.");
				ATUReports.add("Selected allow students to download recordings checkbox.", "True.", "True.",
						LogAs.PASSED, null);
				Assert.assertTrue(true);
			} catch (Exception msg) {
				System.out.println("Fail to select allow students to download recordings checkbox.");
				ATUReports.add("Selected allow students to download recordings checkbox.", "True.", "False.",
						LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Assert.assertTrue(false);
			}
		}
	}

	// This function force checkbox_allow_students_to_record to be enabled
	public void makeSureThatAllowStudentsToRecordIsSelected() {
		if (checkbox_allow_students_to_record.isSelected()) {
			System.out.println("Enable allow students to record already selected.");
			ATUReports.add("Selected allow students to record checkbox.", "True.", "True.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			try {
				checkbox_allow_students_to_record.click();
				System.out.println("Selected allow students to record checkbox.");
				ATUReports.add("Selected allow students to record checkbox.", "True.", "True.", LogAs.PASSED, null);
				Assert.assertTrue(true);
			} catch (Exception msg) {
				System.out.println("Fail to select allow students to record checkbox.");
				ATUReports.add("Selected allow students to record checkbox.", "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Assert.assertTrue(false);
			}
		}
	}

	// This function checks all checkbox in course settings page
	public void checkAllCourseSettingsCheckboxs() {
		try {
		// EnableCounter - Enable minute counter
		forceWebElementToBeSelected(checkbox_enable_counter, "Enable minute counter");
		// AllowToRecord - Allow students to record
		forceWebElementToBeSelected(checkbox_allow_students_to_record, "Allow students to record");

		// PublishAfterUpload - Publish recordings after upload
		forceWebElementToBeSelected(checkbox_publish_after_upload, "Publish recordings after upload");
		// AutomaticallyPublish - Automatically publish student recordings
		forceWebElementToBeSelected(checkbox_automatically_publish, "Automatically publish student recordings");
		// RequireAuthentication - Require authentication by default for direct
		// recording links
		forceWebElementToBeSelected(checkbox_require_authentication,
				"Require authentication by default for direct recording links");
		// PubliclyVisible - Make this course publicly visible
		forceWebElementToBeSelected(checkbox_make_course_public_visable, "Make this course publicly visible");
		
		// AllowToDownload - Allow students to download recordings
		forceWebElementToBeSelected(checkbox_allow_students_to_download_recordings,
				"Allow students to download recordings");
		// EnableAudioPodcast - Enable MP3 Podcast
		forceWebElementToBeSelected(enable_mp3_podcast_button, "Enable MP3 Podcast");
		// EnableVideoPodcast - Enable Video Podcast
		forceWebElementToBeSelected(enable_video_podcast_button, "Enable Video Podcast");
		// EnableVideoStreaming - Enable video streaming to iOS and Android apps
		forceWebElementToBeSelected(checkbox_enable_video_streaming, "Enable video streaming to iOS and Android apps");
		// EnableFeed - Enable RSS feed
		forceWebElementToBeSelected(checkbox_enable_rss_feed, "Enable RSS feed");
		// DisableStudentPrinting - Disable student printing
		forceWebElementToBeSelected(checkbox_disable_student_printing, "Disable student printing");
		// EnableWebcast - Enable Webcast
		forceWebElementToBeSelected(checkbox_enable_webcast, "Enable Webcast");
		// PostToTwitter - Post recordings to Twitter (Connect)
		forceWebElementToBeSelected(checkbox_post_to_twitter, "Post recordings to Twitter (Connect)");

		// EnableStudentTesting - Enable student testing (Remote Proctoring// mode)
		forceWebElementToBeSelected(checkbox_enable_student_testing, "Enable student testing (Remote Proctoring mode)");
		} catch (Exception e) {
			System.out.println("Fail click on visibility checkbox.");
			ATUReports.add("Fail click on visibility checkbox.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}

	// This function verifies course visibility is unchecked
	public void uncheckCourseVisibility() throws InterruptedException {
		try {
			if (visibility_of_course_checkbox.isSelected()) {
				visibility_of_course_checkbox.click();
				if (!visibility_of_course_checkbox.isSelected()) {
					System.out.println("Clicked on course visibility");
					ATUReports.add("Clicked on course visibility", "visibility checkbox", "unchecked", "unchecked",
							LogAs.PASSED, null);
					Assert.assertTrue(true);
				} else {
					System.out.println("Clicked on course visibility");
					ATUReports.add("Clicked on course visibility", "visibility checkbox", "unchecked", "checked",
							LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
					Assert.assertTrue(false);
				}
			} else {
				System.out.println("course visibility already unchecked");
				ATUReports.add("course visibility already unchecked", "visibility checkbox", "unchecked", "unchecked",
						LogAs.PASSED, null);
				Assert.assertTrue(true);
			}
		} catch (Exception e) {
			System.out.println("Fail click on  visibility checkbox.");
			ATUReports.add("Fail click on  visibility checkbox.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
		Thread.sleep(3000);
		clickOnOkButton();
	}

	// This function verifies course visibility is checked
	public void checkCourseVisibility() throws InterruptedException {
		try {
			if (!visibility_of_course_checkbox.isSelected()) {
				visibility_of_course_checkbox.click();
				if (visibility_of_course_checkbox.isSelected()) {
					System.out.println("Clicked on course visibility");
					ATUReports.add("Clicked on course visibility", "visibility checkbox", "checked", "checked",
							LogAs.PASSED, null);
					Assert.assertTrue(true);
				} else {
					System.out.println("Clicked on course visibility");
					ATUReports.add("Clicked on course visibility", "visibility checkbox", "checked", "unchecked",
							LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
					Assert.assertTrue(false);
				}
			} else {
				System.out.println("course visibility already checked");
				ATUReports.add("course visibility already checked", "visibility checkbox", "checked", "checked",
						LogAs.PASSED, null);
				Assert.assertTrue(true);
			}
		} catch (Exception e) {
			System.out.println("Fail click on  visibility checkbox.");
			ATUReports.add("Fail click on  visibility checkbox.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
		Thread.sleep(3000);
		clickOnOkButton();
	}

	// This function verifies enable student recording is checked
	public void CheckEnableStudentTesting() throws InterruptedException {
		try {
			waitForVisibility(enable_student_testing_checkbox);
			if (!enable_student_testing_checkbox.isSelected()) {
				enable_student_testing_checkbox.click();
				if (enable_student_testing_checkbox.isSelected()) {
					System.out.println("Clicked on enable student testing checkbox");
					ATUReports.add("Clicked on enable student testing checkbox", "visibility checkbox", "checked",
							"checked", LogAs.PASSED, null);
					Assert.assertTrue(true);
				} else {
					System.out.println("Not Clicked on enable student testing checkbox");
					ATUReports.add("Clicked on enable student testing checkbox", "visibility checkbox", "checked",
							"unchecked", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
					Assert.assertTrue(false);
				}
			} else {
				System.out.println("Enable student testing checkbox already checked");
				ATUReports.add("eEnable student testing checkbox already checked", "visibility checkbox", "checked",
						"checked", LogAs.PASSED, null);
				Assert.assertTrue(true);
			}
		} catch (Exception e) {
			System.out.println("Fail click on  enable student testing checkbox");
			ATUReports.add("Fail click on enable student testing checkbox", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
		Thread.sleep(3000);
		clickOnOkButton();
	}

	// This function verifies enable student recording is checked
	public void CheckAllowStudentDownload() throws InterruptedException {
		try {
			if (!allow_students_download_checkbox.isSelected()) {
				allow_students_download_checkbox.click();
				if (allow_students_download_checkbox.isSelected()) {
					System.out.println("Clicked on allow student downloading checkbox");
					ATUReports.add("Clicked on allow student downloading checkbox", "visibility checkbox", "checked",
							"checked", LogAs.PASSED, null);
					Assert.assertTrue(true);
				} else {
					System.out.println("Not Clicked on allow student downloading checkbox");
					ATUReports.add("Clicked on allow student downloading checkbox", "visibility checkbox", "checked",
							"unchecked", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
					Assert.assertTrue(false);
				}
			} else {
				System.out.println("allow student downloading checkbox already checked");
				ATUReports.add("allow student downloading checkbox already checked", "visibility checkbox", "checked",
						"checked", LogAs.PASSED, null);
				Assert.assertTrue(true);
			}
		} catch (Exception e) {
			System.out.println("Fail click on  allow student downloading checkbox");
			ATUReports.add("Fail click on allow student downloading checkbox", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
		Thread.sleep(3000);
		clickOnOkButton();
	}

	public WebElement getOk_button() {
		return ok_button;
	}

	public void setOk_button(WebElement ok_button) {
		this.ok_button = ok_button;
	}

	/// makes course public
	public void makeCoursePublic() throws Exception {
		checkCourseVisibility();
	}

	/// makes course not public
	public void makeCourseNotPublic() throws Exception {
		uncheckCourseVisibility();
	}

}
