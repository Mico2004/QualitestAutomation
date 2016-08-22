package com.automation.main;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.StringReader;
import java.net.URLConnection;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;

import java.io.IOException;

import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;

import javax.print.DocFlavor.URL;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;



import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;

public class TC21599GuestPremissions {
	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}

	public EditRecordinPropertiesWindow erp_window;
	public LoginHelperPage tegrity;
	public CoursesHelperPage course;
	public RecordingHelperPage record;
	public ConfirmationMenu confirm_menu;
	public DeleteMenu delete_menu;
	public MoveWindow move_window;
	WebDriver driver;
	WebDriverWait wait;
	public static WebDriver thread_driver;
	CopyMenu copy;
	String currentCourse;
	String targetCourse;
	String clickedRecording;
	WebDriver driver2;
	public AdminDashboardPage admin_dashboard_page;
	public ManageAdhocCoursesEnrollmentsPage mange_adhoc_course_enrollments;
	public CreateNewCourseWindow create_new_course_window;
	public ManageAdhocUsersPage mange_adhoc_users_page;
	public CreateNewUserWindow create_new_user_window;
	public ManageAdHocCoursesMembershipWindow mangage_adhoc_courses_membership_window;
	public AddAdditionalContentFileWindow add_additional_content_window;
	public AdvancedServiceSettingsPage advanced_services_setting_page;
	public HelpPage help_page;
	public CourseSettingsPage course_settings;
	public EmailAndConnectionSettingsPage email_setting;
	public EulaPage eula_page;
	public GetSupprtWindow support_window;
	public EmailLoginPage email_login;
	public EmailInboxPage email_inbox;
	public RunDiagnosticsPage run_diagnostics;
	public PlayerPage player_page;
	public PublishWindow publish_window;
	String instructor1;
	String instructor2;
	List<String> for_enroll;

	@BeforeClass
	public void setup() {

		driver = DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());
		driver.manage().window().maximize();

		tegrity = PageFactory.initElements(driver, LoginHelperPage.class);

		wait = new WebDriverWait(driver, 30);
		add_additional_content_window = PageFactory.initElements(driver, AddAdditionalContentFileWindow.class);
		publish_window = PageFactory.initElements(driver, PublishWindow.class);
		email_setting = PageFactory.initElements(driver, EmailAndConnectionSettingsPage.class);
		record = PageFactory.initElements(driver, RecordingHelperPage.class);
		copy = PageFactory.initElements(driver, CopyMenu.class);
		delete_menu = PageFactory.initElements(driver, DeleteMenu.class);
		course = PageFactory.initElements(driver, CoursesHelperPage.class);
		confirm_menu = PageFactory.initElements(driver, ConfirmationMenu.class);
		course_settings = PageFactory.initElements(driver, CourseSettingsPage.class);
		wait = new WebDriverWait(driver, 30);
		move_window = PageFactory.initElements(driver, MoveWindow.class);
		erp_window = PageFactory.initElements(driver, EditRecordinPropertiesWindow.class);
		admin_dashboard_page = PageFactory.initElements(driver, AdminDashboardPage.class);
		advanced_services_setting_page = PageFactory.initElements(driver, AdvancedServiceSettingsPage.class);
		mange_adhoc_course_enrollments = PageFactory.initElements(driver, ManageAdhocCoursesEnrollmentsPage.class);
		eula_page = PageFactory.initElements(driver, EulaPage.class);
		create_new_course_window = PageFactory.initElements(driver, CreateNewCourseWindow.class);
		support_window = PageFactory.initElements(driver, GetSupprtWindow.class);
		mange_adhoc_users_page = PageFactory.initElements(driver, ManageAdhocUsersPage.class);
		email_login = PageFactory.initElements(driver, EmailLoginPage.class);
		create_new_user_window = PageFactory.initElements(driver, CreateNewUserWindow.class);
		email_inbox = PageFactory.initElements(driver, EmailInboxPage.class);
		mangage_adhoc_courses_membership_window = PageFactory.initElements(driver,
				ManageAdHocCoursesMembershipWindow.class);
		help_page = PageFactory.initElements(driver, HelpPage.class);
		run_diagnostics = PageFactory.initElements(driver, RunDiagnosticsPage.class);
		player_page = PageFactory.initElements(driver, PlayerPage.class);
	}

	@Test
	public void test21599() throws Exception {
		/// pre conditions

		// 1.load page
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
		String url_topass = tegrity.pageUrl;
		/// 2.login as user1
		tegrity.loginCourses("User1");
		course.waitForVisibility(course.active_courses_tab_button);

		// 3.click on course
		String course_name = course.selectCourseThatStartingWith("Ab");
		
		 // 4.to course settings
		 record.waitForVisibility(record.course_tasks_button);
		 record.toCourseSettingsPage();
		
		 // 5.verify checked visibility of course
		 course_settings.checkCourseVisibility();
		
		 // 6.Unpublish one Regular recordings and one Student recording
		 record.waitForVisibility(record.course_tasks_button);
		 record.convertRecordingsListToNames();
		 String recording_publish=record.recording_list_names.get(0);
		 record.unpublishFirstRecording(record.recordings_tab,
		 publish_window);
		 Thread.sleep(3000);
		 record.unpublishFirstRecording(record.student_recordings_tab,
		 publish_window);
		 Thread.sleep(3000);
		 record.convertRecordingsListToNames();
		 String student_publish=record.recording_list_names.get(0);
		 // 7.Go to the university's 'Course Settings' and enable ' Enable
		 // student testing'
		 record.waitForVisibility(record.course_tasks_button);
		 record.toCourseSettingsPage();
		 course_settings.waitForVisibility(course_settings.enable_student_testing_checkbox);
		 course_settings.CheckEnableStudentTesting();
		 record.waitForVisibility(record.course_tasks_button);
		 record.toCourseSettingsPage();
		 //8.verify allow all students to download is checked
		 course_settings.CheckAllowStudentDownload();

		// 9.sign out
		for (String handler : driver.getWindowHandles()) {
			driver.switchTo().window(handler);
		}
		// 9.1.Click "Courses" link at breadcrumbs
		record.returnToCourseListPage();
		course.waitForVisibility(course.sign_out);
		course.signOut();

		////////////////////// End of
		////////////////////// pre-conditions//////////////////////////////

		// 10.login as guest
		tegrity.waitForVisibility(tegrity.passfield);
		tegrity.loginAsguest();

		// 11.Make sure the 'Active Courses' and the 'Past Courses' tab are not
		// displayed
		course.waitForVisibility(course.public_courses_tab_button);
		course.verifyNoActiveCoursesTab();
		course.verifyNoPastCoursesTab();

		// 12.Make sure the 'Start a Recording' buttton is not displayed
		course.verifyNoStartRecording();

		// 13.Click on the course that we mention in preconditions
		course.selectCourseByName(course_name);
		record.waitForVisibility(record.sign_out);
		
		 //14.Make sure the 'Tests' tab is not displayed
		 record.verifyNoTestsTab();
		
		 //15.Make sure the 'Start a Recording' buttton is not displayed
		 record.verifyNoStartRecording();
		
		 //16.Make sure the 'Start a Test' button is not displayed
		 record.verifyNoStartTest();
		
		 //17.Make sure the regular recording you unpublished is not displayed
		 record.clickOnRecordingsTab();
		 Thread.sleep(2000);
		 record.isRecordingExist(recording_publish,false);
		
		 //18.Click the 'Student Recordings' tab
		 record.clickOnStudentRecordingsTab();
		 Thread.sleep(2000);
		 //19.Make sure the student recording you unpublished is not displayed
		 record.isRecordingExist(student_publish, false);
		
		 //20.Click the 'Recordings' tab
		 record.clickOnRecordingsTab();
		 Thread.sleep(3000);
		 //21.Click on some recording
		 record.verifyFirstExpandableRecording();
		 driver.findElement(By.cssSelector(".panel-body>.video-outer.ng-scope>.video-wrap")).click();
		 Thread.sleep(15000);
		 player_page.verifyTimeBufferStatusForXSec(10);// check source display
		 ///// to go back to crecording window handler
		 for (String handler : driver.getWindowHandles()) {
		 driver.switchTo().window(handler);
		
		 }
		 //22.Verify the recording you watched is still bold
		 record.recordingBoldFont(driver.findElement(By.xpath("//*[@id=\"tegrityBreadcrumbsBox\"]/li[2]/a")));
		
		 //23.click course name breadcrumbs
		 record.returnToRecordingPageByClickingBreadcrumbsName(driver.findElement(By.xpath("//*[@id=\"tegrityBreadcrumbsBox\"]/li[2]/a")));
		 //24.Hover over the "Recording tasks" drop down list.
		 record.verifyDisableDownloadAndMessageAppears();

		// 25.Check recording checkbox.
		record.moveToElementAndClick(record.searchbox, driver);
		record.ClickOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
		// 26.Hover over the "Recoding tasks" drop down list.
		record.verifyEnabledDownload();
		// 27.verify download is the only element visible(meaning message is not
		// clickable)
		try {
			if (driver
					.findElement(
							By.xpath("//*[@id=\"scrollableArea\"]/div[1]/div[2]/div/div[1]/div[1]/ul/li/ul/li[2]/em"))
					.isDisplayed()) {
				System.out.println("message is visible");
				ATUReports.add("message is invisible", "message", "not visible", "visible", LogAs.FAILED, null);
				Assert.assertTrue(false);
			} else {
				System.out.println("message is invisible");
				ATUReports.add("message is invisible", "message", "not visible", "not visible", LogAs.PASSED, null);
				Assert.assertTrue(true);
			}
		} catch (Exception e) {
			System.out.println("message is invisible");
			ATUReports.add("message is invisible", "message", "not visible", "not visible", LogAs.PASSED, null);
			Assert.assertTrue(true);
		}
		/// 28.Hover over the "Course tasks" drop down list:
		/// 1. "Subscribe to Your Course's..." is disable.
		// 2. Separate line is displayed below the "Subscribe to Your
		/// Course's..."
		/// 3. "RSS Feed" is enable. 4. "Podcast" is enable. 5. "Video podcast"
		/// is enable.

		Robot robot = null;
		try {
			robot = new Robot();
			robot.mouseMove(-100, -100);
			record.moveToElementAndClick(record.course_tasks_button, driver);

			// subscribe not visible
			record.verifyClickableElementIsNotVisible(record.subscribe_button, "subscribe");

			/// rss feed is enabled
			record.verifyElementIsEnabled(record.rssfeed, "rss_feed");

			/// "Podcast" is enable
			record.verifyElementIsEnabled(record.podcast_button, "Podcast");

			/// "Video podcast" is enable.
			record.verifyElementIsEnabled(record.video_podcast, "Video Podcast");

		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/// 29.Click on the 'Student Recording' tab
		record.clickOnStudentRecordingsTab();

		/// 30.Hover over the "Recording tasks" drop down list.
		record.moveToElementAndClick(record.recording_tasks_button, driver);
		Thread.sleep(2000);
		record.verifyDisableDownloadAndMessageAppears();

		// 31.Check recording checkbox.
		record.moveToElementAndClick(record.searchbox, driver);
		record.ClickOneCheckBoxOrVerifyAlreadySelected(record.checkbox);

		// 32.verify download is the only element visible(meaning message is not
		// clickable)
		try {
			if (driver
					.findElement(
							By.xpath("//*[@id=\"scrollableArea\"]/div[1]/div[2]/div/div[1]/div[1]/ul/li/ul/li[2]/em"))
					.isDisplayed()) {
				System.out.println("message is visible");
				ATUReports.add("message is invisible", "message", "not visible", "visible", LogAs.FAILED, null);
				Assert.assertTrue(false);
			} else {
				System.out.println("message is invisible");
				ATUReports.add("message is invisible", "message", "not visible", "not visible", LogAs.PASSED, null);
				Assert.assertTrue(true);
			}
		} catch (Exception e) {
			System.out.println("message is invisible");
			ATUReports.add("message is invisible", "message", "not visible", "not visible", LogAs.PASSED, null);
			Assert.assertTrue(true);
		}
		/// 33.Hover over the "Course tasks" drop down list:
		/// 1. "Subscribe to Your Course's..." is disable.
		// 2. Separate line is displayed below the "Subscribe to Your
		/// Course's..."
		/// 3. "RSS Feed" is enable. 4. "Podcast" is enable. 5. "Video podcast"
		/// is enable.

		try {
			robot = new Robot();
			robot.mouseMove(-100, -100);
			record.moveToElementAndClick(record.course_tasks_button, driver);
			// subscribe not visible
			record.verifyClickableElementIsNotVisible(record.subscribe_button, "subscribe");

			/// rss feed is enabled
			record.verifyElementIsEnabled(record.rssfeed, "rss_feed");

			/// "Podcast" is enable
			record.verifyElementIsEnabled(record.podcast_button, "Podcast");

			/// "Video podcast" is enable.
			record.verifyElementIsEnabled(record.video_podcast, "Video Podcast");

		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 34.Click on the 'Additional Content' tab
		record.clickOnAdditionContentTab();

		// 35.Make sure the 'Content Tasks' button is not displayed under the
		// 'Start a Recording' button.(its actully not visible at all)
		record.verifyClickableElementIsNotVisible(record.content_tasks_button, "content task");

		/// 36.Hover over the "Course tasks" drop down list:
		/// 1. "Subscribe to Your Course's..." is disable.
		// 2. Separate line is displayed below the "Subscribe to Your
		/// Course's..."
		/// 3. "RSS Feed" is enable. 4. "Podcast" is enable. 5. "Video podcast"
		/// is enable.

		try {
			robot = new Robot();
			robot.mouseMove(-100, -100);
			record.moveToElementAndClick(record.course_tasks_button, driver);
			// subscribe not visible
			record.verifyClickableElementIsNotVisible(record.subscribe_button, "subscribe");

			/// rss feed is enabled
			record.verifyElementIsEnabled(record.rssfeed, "rss_feed");

			/// "Podcast" is enable
			record.verifyElementIsEnabled(record.podcast_button, "Podcast");

			/// "Video podcast" is enable.
			record.verifyElementIsEnabled(record.video_podcast, "Video Podcast");

		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 37.Click on the 'Recordings' tab
		record.clickOnRecordingsTab();
	
		// 38.Click on RSS Feed menu item: 1) The RSS Feed page is displayed.

		record.verifyRssFeedPage(driver, tegrity);
		// 38.1 2) The recording <item> is displayed in the course
		// ctrl+a
		robot.mouseMove(500, 500);
		robot.keyPress(KeyEvent.VK_CONTROL);
		Thread.sleep(200);
		robot.keyPress(KeyEvent.VK_A);
		Thread.sleep(200);
		robot.keyRelease(KeyEvent.VK_A);
		Thread.sleep(200);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		Thread.sleep(200);
		// ctrl+c
		robot.keyPress(KeyEvent.VK_CONTROL);
		Thread.sleep(200);
		robot.keyPress(KeyEvent.VK_C);
		Thread.sleep(200);
		robot.keyRelease(KeyEvent.VK_C);
		Thread.sleep(200);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		Thread.sleep(200);
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Clipboard clipboard = toolkit.getSystemClipboard();
		String result = (String) clipboard.getData(DataFlavor.stringFlavor);
		String test = "<title>" + course_name + " recordings</title>";
		driver.navigate().back();
		record.waitForVisibility(record.course_tasks_button);
		record.toRssFeedPage(driver);
        record.waitForVisibility(record.first_recording);
		record.verifyFirstExpandableRecording();
		record.waitForVisibility(driver.findElement(By.cssSelector(".panel-body>.video-outer.ng-scope>.video-wrap")));
		driver.findElement(By.cssSelector(".panel-body>.video-outer.ng-scope>.video-wrap")).click();
		Thread.sleep(15000);
		////player_page.verifyTimeBufferStatusForXSec(10);// check source display
		String rss_feed_course_link = driver.getCurrentUrl().substring(4);
		for (String handler : driver.getWindowHandles()) {
			driver.switchTo().window(handler);
			// System.out.println("=========================================");
			// System.out.println(driver.getPageSource());
		}
		
		record.returnToCourseListPage();
		course.waitForVisibility(course.public_courses_tab_button);
		course.selectCourseByName(course_name);
		System.out.println(rss_feed_course_link);

		if ((result.contains(test)) && (result.contains("<link>" +"https"+rss_feed_course_link + "</link>"))) {
			System.out.println("contain title and correct link");
			ATUReports.add("contain title and correct link", "xml", " visible", "visible", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("not contain title and correct link");
			ATUReports.add("contain title and link", "xml", " visible", "not visible", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
       
		/// 39.Click on Podcast menu item.
		record.waitForVisibility(record.recordings_tab);
		record.clickOnRecordingsTab();

		// 38.Click on RSS Feed menu item: 1) The RSS Feed page is displayed.

		record.verifyPodcastPage(driver, tegrity);
		// 38.1 2) The recording <item> is displayed in the course
		// ctrl+a
		robot.mouseMove(500, 500);
		robot.keyPress(KeyEvent.VK_CONTROL);
		Thread.sleep(200);
		robot.keyPress(KeyEvent.VK_A);
		Thread.sleep(200);
		robot.keyRelease(KeyEvent.VK_A);
		Thread.sleep(200);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		Thread.sleep(200);
		// ctrl+c
		robot.keyPress(KeyEvent.VK_CONTROL);
		Thread.sleep(200);
		robot.keyPress(KeyEvent.VK_C);
		Thread.sleep(200);
		robot.keyRelease(KeyEvent.VK_C);
		Thread.sleep(200);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		Thread.sleep(200);
		toolkit = Toolkit.getDefaultToolkit();
		clipboard = toolkit.getSystemClipboard();
		result = (String) clipboard.getData(DataFlavor.stringFlavor);
		test = "<title>" + course_name + " recordings</title>";
		driver.navigate().back();
		record.waitForVisibility(record.course_tasks_button);
		record.toPodCast(driver);
         rss_feed_course_link="https"+rss_feed_course_link;
		String podcast_feed_course_link = rss_feed_course_link;
	
		
////find if xml contains title link and enclosure
		if ((result.contains(test)) && (result.contains("<link>"+podcast_feed_course_link + "</link>"))) {
			System.out.println("contain title and correct link");
			ATUReports.add("contain title and correct link", "xml", " visible", "visible", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("not contain title and correct link");
			ATUReports.add("contain title and link", "xml", " visible", "not visible", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		driver.navigate().back();
		record.waitForVisibility(record.course_tasks_button);
	    
      
       ////url attribute +later call function to verify pattern
       record.podcastUrlVerification(result,podcast_feed_course_link,tegrity);
       
      //39.verify sorted by time date title and duration

       record.pressViewButtonAndSelect("Title");
	
		record.convertRecordingsListToNames();
	    record.verifyRecordingSortedByTitle(record.recording_list_names);
		record.pressViewButtonAndSelect("Date");
		Thread.sleep(1000);
		
		record.convertRecordingsListToDate();/// check sort by date
		record.verifyRecordingSortedByDate(record.recordings_list_date_string);
       record.pressViewButtonAndSelect("Duration");
       Thread.sleep(1000);
		
       record.convertRecordingsListToDuration();/// check sort by date
		record.verifyRecordingSortedByDuration(record.recording_list_duration_string);
       
		  ///40.click on additional content tab
	       record.clickOnAdditionContentTab();
	       Thread.sleep(2000);
	       record.convertAdditionalContantListToNames();
	       String file_name=record.additional_content_list_names.get(0);
	       String download_path= System.getProperty("user.home") + File.separatorChar +"Downloads"+ File.separatorChar+file_name;
			record.tryToDeleteOlderFile(download_path);
		
		driver.quit();
		
        
        
////////set up for download file

		System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();

		tegrity = PageFactory.initElements(driver, LoginHelperPage.class);

		wait = new WebDriverWait(driver, 30);
		add_additional_content_window = PageFactory.initElements(driver, AddAdditionalContentFileWindow.class);
		record = PageFactory.initElements(driver, RecordingHelperPage.class);
		copy = PageFactory.initElements(driver, CopyMenu.class);
		delete_menu = PageFactory.initElements(driver, DeleteMenu.class);
		course = PageFactory.initElements(driver, CoursesHelperPage.class);
		confirm_menu = PageFactory.initElements(driver, ConfirmationMenu.class);

		wait = new WebDriverWait(driver, 30);
		move_window = PageFactory.initElements(driver, MoveWindow.class);
		erp_window = PageFactory.initElements(driver, EditRecordinPropertiesWindow.class);
		admin_dashboard_page = PageFactory.initElements(driver, AdminDashboardPage.class);

		mange_adhoc_course_enrollments = PageFactory.initElements(driver, ManageAdhocCoursesEnrollmentsPage.class);

		create_new_course_window = PageFactory.initElements(driver, CreateNewCourseWindow.class);

		mange_adhoc_users_page = PageFactory.initElements(driver, ManageAdhocUsersPage.class);

		create_new_user_window = PageFactory.initElements(driver, CreateNewUserWindow.class);

		mangage_adhoc_courses_membership_window = PageFactory.initElements(driver,
				ManageAdHocCoursesMembershipWindow.class);

		///// end of set up

		// 1.load page
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
		/// 2.login as guest
		tegrity.loginAsguest();
		// 3.Select course
		course.waitForVisibility(course.public_courses_tab_button);
		course.selectCourseByName(course_name);
		/// 4.select additional content tab
		record.waitForVisibility(record.additional_content_tab);
		record.clickOnAdditionContentTab();
		Thread.sleep(3000);
		///try to delete in file in download file folder
		try {
			Path download_path_to_delete = Paths.get(download_path);
			Files.delete(download_path_to_delete);
			System.out.println("file  deleted");

		}
		catch(Exception e){
			System.out.println("no old file to delete");
		}
		/// 5.select file by its name
		record.selectAdditionalContentByName(file_name);
		Thread.sleep(5000);
		// 6.verify downloaded file is valid using md5
		record.VerifyDownloadedFileIsExist(download_path);

		driver.quit();
      
	///41.download file and verify its existence
		

	
	}
		
		
		
		
		
		



	// description = "get courses list"
	public void initializeCourseObject() throws InterruptedException {

		course = PageFactory.initElements(driver, CoursesHelperPage.class);
		course.courses = course.getStringFromElement(course.course_list);
	}
}
