package com.automation.main.move_recording;


import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.PrimitiveIterator.OfDouble;
import javax.xml.parsers.ParserConfigurationException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.automation.main.page_helpers.ConfirmationMenu;
import com.automation.main.page_helpers.CopyMenu;
import com.automation.main.page_helpers.CoursesHelperPage;
import com.automation.main.page_helpers.LoginHelperPage;
import com.automation.main.page_helpers.MoveWindow;
import com.automation.main.page_helpers.PlayerPage;
import com.automation.main.page_helpers.RecordingHelperPage;
import com.automation.main.utilities.DriverSelector;

import org.testng.annotations.AfterClass;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import junit.framework.Assert;

public class TC15582MoveOneRecording {
	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}

	public LoginHelperPage tegrity;
	public CoursesHelperPage course;
	public RecordingHelperPage record;
	public PlayerPage player_page;
	WebDriver driver;
	WebDriverWait wait;
	public static WebDriver thread_driver;
	CopyMenu copy;
	DesiredCapabilities capability;
	String os;
	List recording_list_object;
	ConfirmationMenu confirm;
	MoveWindow move_menu;
	ArrayList<String> list;
	
	@AfterClass
	public void closeBroswer() {		
		this.driver.quit();
	}

	@BeforeClass
	public void setup() {
		try {

			System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
			driver = new ChromeDriver();///// MUST FOR TEST TO GET XML		
			tegrity = PageFactory.initElements(driver, LoginHelperPage.class);
		} catch (Exception e) {
			/// ATUReports.add("Fail Step", LogAs.FAILED, new
			/// CaptureScreen(ScreenshotOf.DESKTOP));
		}
		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC15582MoveOneRecording at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC15582MoveOneRecording at " + DateToStr, "Starting the test: TC15582MoveOneRecording at " + DateToStr, LogAs.PASSED, null);	
	}


	@Test (description="TC 15582 Move One Recording")
	public void test15582() throws InterruptedException, ParserConfigurationException, Exception, IOException {
		// 1.load main page chrome
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
		// 2.login as instructor
		tegrity.loginCourses("User1");// log in courses page
		
		String login_url = driver.getCurrentUrl();
		String university_name  = login_url.split("/")[2].substring(0,  login_url.split("/")[2].length() - 12);
		driver.navigate().to("https://" + university_name + ".tegrity.com/api/courses/Active");
		Thread.sleep(1000);
		String xml_source_code = driver.findElement(By.tagName("body")).getText();
		driver.quit();

		// 1.load main page
		driver = DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());
		//
		tegrity = PageFactory.initElements(driver, LoginHelperPage.class);
		record = PageFactory.initElements(driver, RecordingHelperPage.class);
		copy = PageFactory.initElements(driver, CopyMenu.class);
		player_page = PageFactory.initElements(driver, PlayerPage.class);
		move_menu = PageFactory.initElements(driver, MoveWindow.class);
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
		// 2.login as instructor
		tegrity.loginCourses("User1");// log in courses page

		/// initialize courses
		initializeCourseObject();
		
		// 4.select first course
		String first_course = course.first_course_button.getText();
		course.selectFirstCourse(record);
		
		// 5.verify check box is selected and then load move menu
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
		record.clickOnRecordingTaskThenMove();
		
		// 6.source course is not displayed in move window list
		move_menu.verifySourceCourseNotInMoveMenuCourseList(first_course);
		
		// 7.return to recording page and than to course page
		move_menu.clickOnCancelButton();
		
		record.returnToCourseListPage();
		
		
		// 8. Verify Only courses where this USER signed as INSTRUCTOR are
		// displayed in "Course List"
		first_course = course.first_course_button.getText();
		course.selectFirstCourse(record);
		
		
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
		record.clickOnRecordingTaskThenMove();
		
		record.waitForVisibility(move_menu.course_list_select);
		move_menu.move_course_list = move_menu.getStringFromElement(move_menu.course_list);
		int course_number = move_menu.course_list.size();
		int count_instructors = course.patternAppearenceinString(xml_source_code,"<CurrentUserRole>Instructor</CurrentUserRole>") - 1;
		if (count_instructors == course_number) {
			for (int i = 0; i < move_menu.move_course_list.length; i++) {
				boolean result = move_menu.searchWordInString(move_menu.move_course_list[i], xml_source_code);
				if (result == false) {
					Assert.assertTrue(false);
					System.out.println("xml courses name not equals move menu courses names");
					ATUReports.add("xml courses name not equals move menu courses names", "xml file",
							"num of instructors:" + count_instructors, "num of instructors:" + course_number,
							LogAs.FAILED, null);

				}
			}
			System.out.println("number Of count_instructors role equals Number Of courses");
			ATUReports.add("all courses assigned as instructors", "xml file", "num of instructors:" + count_instructors,
					"num of instructors:" + course_number, LogAs.PASSED, null);
			Assert.assertTrue(true);
			driver.navigate().back();
			Thread.sleep(2000);

		} else {
			System.out.println("number Of count_instructors role not equals Number Of courses");
			ATUReports.add("all courses assigned as instructors", "xml file", "num of instructors:" + count_instructors,
					"num of instructors:" + course_number, LogAs.FAILED, null);
			Assert.assertTrue(false);
			

		}

		// 9.Select destination course:select first course
		move_menu.clickOnCancelButton();
		Thread.sleep(2000);
		course.selectFirstCourse(record);
		String original_recorder_name =driver.findElement(By.id("RecordedBy1")).getText();//take recorder name for later
		
		// Select destination course:verify check box is selected and then load
		// move menu
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
		record.clickOnRecordingTaskThenMove();
		
		// Select destination course:mark destination course by clicking on it
		String destination_course_name = move_menu.course_list.get(0).getText();
		move_menu.course_list.get(0).click();
		System.out.println("first destination was marked");
		
		/// 10.Click "Move Recording(s)"
		move_menu.moveRecording.click();
		System.out.println("moving recording");
		Thread.sleep(2000);
		
		// 11.click on ok button in confirmation menu
		confirm = PageFactory.initElements(driver, ConfirmationMenu.class);
		confirm.clickOnOkButton();
		
		// source recording name to check not exists later in course page
		String source_recording=record.first_recording.getText();
		/// 12.The source recording has status "Being moving from"
		record.checkRecordingInIndexIStatus(1, "Being moved from");
		
		// 13.After copying is completed, verify that recording isn't displayed
		// in "Course Details" page
		record.waitUntilFirstRecordingBeingMovedFromStatusDissaper();
		record.isRecordingExist(source_recording, false);
		// 14.Click "Courses" link at breadcrumbs
		record.returnToCourseListPage();
		// 15.Select destination course
		course.selectSecondCourse(record);
		/// 16.moved recording is displayed
		record.isRecordingExist(source_recording, true);
		// 17.Date of recording is displayed correctly+first sort by date
	
		record.pressViewButtonAndSelect("Date");
		record.pressViewButtonAndSelect("Date");
		record.convertRecordingsListToDate();/// check sort by date
		record.searchbox.click();// prevent ekement not clickable
		record.verifyDate(record.driver.findElement(By.id("RecordingDate1")).getText());
		// 18.Duration of recording is displayed correctly
		String dur = driver.findElement(By.id("RecordingLength1")).getText();
		dur = record.trimDuration(dur);
		record.verifyTimeDuration(dur);

		// 19.The Username that appears next to the "Recording by: " is the same
		// name of USER who moved the recording
		record.convertRecordingsListToRecorderName();
		record.VerifyRecorderNameAsOriginal(original_recorder_name);
		// 20.font is bold
		record.recordingPageBoldFont();/// verify bold style in recording title
		// 21."Recording Chapters" are expanded
		record.verifyFirstExpandableRecording();
		
		// 22.player is working
		record.clickOnTheFirstCaptherWithOutTheExpand();
		player_page.verifyTimeBufferStatusForXSec(5);// check source display
		///// to go back to crecording window handler
		for (String handler : driver.getWindowHandles()) {
			driver.switchTo().window(handler);

		}
		// 23.sign out
		record.signOut();// sign out
		// 24.login as student
		tegrity.loginCourses("User4");// log in courses page
		initializeCourseObject();
		
		// 25.select destination course
		course.selectCourseByName(destination_course_name);
		
		// 26."Recording Chapters" are expanded
		record.pressViewButtonAndSelect("Date");/// 2 times sort by date to make
												/// recording first
		record.pressViewButtonAndSelect("Date");
		Thread.sleep(2000);
		record.searchbox.click();// prevent ekement not clickable
		record.verifyFirstExpandableRecording();
	
		// 27.player is working
		record.clickOnTheFirstCaptherWithOutTheExpand();
		player_page.verifyTimeBufferStatusForXSec(5);// check source display
 
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);

	}

	// description = "get courses list"
	public void initializeCourseObject() throws InterruptedException {
		try {
			course = PageFactory.initElements(driver, CoursesHelperPage.class);
			course.courses = course.getCoursesListFromElement(course.course_list);
			course.size = course.course_list.size();
			System.out.println("initialize courses succeeded");
		} catch (Exception e) {
			System.out.println("initialize courses failed...");
		}
	}

}
