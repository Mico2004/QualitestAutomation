package com.automation.main.past_courses;

import java.awt.AWTException;
import java.awt.Robot;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.util.Asserts;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;import com.automation.main.page_helpers.Page;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.text.DateFormat;
import java.util.Date;

import com.automation.main.page_helpers.CopyMenu;
import com.automation.main.page_helpers.CoursesHelperPage;
import com.automation.main.page_helpers.DeleteMenu;
import com.automation.main.page_helpers.LoginHelperPage;
import com.automation.main.page_helpers.MoveWindow;
import com.automation.main.page_helpers.RecordingHelperPage;
import com.automation.main.utilities.DriverSelector;
import com.sun.jna.platform.unix.X11.Cursor;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;

public class TC17903VerifyTheUI {

	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}

	public DeleteMenu delete;
	public MoveWindow move_Window;
	public LoginHelperPage tegrity;
	public CoursesHelperPage course;
	public RecordingHelperPage record;
	WebDriver driver;
	WebDriverWait wait;
	public static WebDriver thread_driver;
	CopyMenu copy;
	DesiredCapabilities capability;
	String os;
	public List<String> recording_for_delete;

	@BeforeClass
	public void setup() {

	

			/// System.setProperty("webdriver.ie.driver",
			/// "src/test/resources/chromedriver.exe");

			driver = DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());
			/// ATUReports.add("selected browser type", LogAs.PASSED, new
			/// CaptureScreen(ScreenshotOf.DESKTOP));
			
			recording_for_delete = new ArrayList<String>();
			tegrity = PageFactory.initElements(driver, LoginHelperPage.class);
			delete = PageFactory.initElements(driver, DeleteMenu.class);
			record = PageFactory.initElements(driver, RecordingHelperPage.class);
			copy = PageFactory.initElements(driver, CopyMenu.class);
			move_Window = PageFactory.initElements(driver, MoveWindow.class);
			
			 Date curDate = new Date();
			 String DateToStr = DateFormat.getInstance().format(curDate);
			 System.out.println("Starting the test: TC17903VerifyTheUI at " + DateToStr);
			 ATUReports.add("Message window.", "Starting the test: TC17903VerifyTheUI at " + DateToStr,
			 "Starting the test: TC17903VerifyTheUI at " + DateToStr, LogAs.PASSED, null);
			 
	}

	@AfterClass
	public void closeBroswer() {		
		this.driver.quit();
	}

	// @Parameters({"web","title"}) in the future
	@Test (description = "TC 17903 Verify The UI")
	public void test17903() throws InterruptedException {
		// 1.load page
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
		// 2.login as instructor

		tegrity.loginCourses("User1");// log in courses page
		Thread.sleep(Page.TIMEOUT_TINY);
		initializeCourseObject();
		// 3.Click Past Courses Tab button (without selecting a recording)
		course = PageFactory.initElements(driver, CoursesHelperPage.class);
		course.clickOnPastCoursesTabButton();
		
		// 4.click course
		Thread.sleep(Page.TIMEOUT_TINY);
		course.selectFirstCourse(record);
		Thread.sleep(Page.TIMEOUT_TINY);
		// 5.validae bBdcrumbrea visibility
		boolean result = record.isElementPresent(By.partialLinkText("Courses"));
		if (result == true) {
			ATUReports.add("courses breadcrumb exists", LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
			System.out.println("courses breadcrumb exists");
			Assert.assertTrue(result);
		} else {
			ATUReports.add("courses breadcrumb doesnt exist", LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
			System.out.println("courses breadcrumb doesnt exist");
			Assert.assertTrue(result);
		}

		// 6.2. The '>' character is displayed before the breadcrumb link.
		String cha = record.breadcrumbs.getText();

		if (cha.equals("> Courses")) {
			ATUReports.add("courses breadcrumb > exists", LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
			System.out.println("courses breadcrumb > exists");
			Assert.assertTrue(result);
		} else {
			ATUReports.add("courses breadcrumb > doesnt exist", LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
			System.out.println("courses breadcrumb > doesnt exist");
			Assert.assertTrue(result);
		}

		Thread.sleep(Page.TIMEOUT_TINY);
		// 7.check underline
		Point courses_link = record.courses_link.getLocation();
		try {
			Robot robot = new Robot();
			robot.delay(1000);
			robot.mouseMove(courses_link.getX() + 20, courses_link.getY() - 1000);

			System.out.println("");
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		boolean underline = record.verifyUnderlineCss(record.courses_link, driver);
		if (underline == true) {
			Assert.assertTrue(true);
			System.out.println("The underline of the link caption appears");
			ATUReports.add("The underline of the link caption appears", LogAs.PASSED,
					new CaptureScreen(ScreenshotOf.DESKTOP));

		} else {
			Assert.assertTrue(false);
			System.out.println("The underline of the link caption doesnt appear");
			ATUReports.add("The underline of the link caption doesnt appear", LogAs.PASSED,
					new CaptureScreen(ScreenshotOf.DESKTOP));

		}

		record.moveToElement(record.courses_link, driver).perform();

		// 8.has title of "Courses" so we can be sure when we move courser to it
		// "courses" appear next to it
		if (record.courses_link.getAttribute("title").equals("Courses")) {
			Assert.assertTrue(true);
			System.out.println("The hint with the link caption appears");
			ATUReports.add("The hint with the link caption appears", LogAs.PASSED,
					new CaptureScreen(ScreenshotOf.DESKTOP));

		} else {
			Assert.assertTrue(false);
			System.out.println("The hint with the link caption doesnt appear");
			ATUReports.add("The hint with the link caption doesnt appear", LogAs.PASSED,
					new CaptureScreen(ScreenshotOf.DESKTOP));

		}

		/// 9.not able to check cursor change

		// 10.Validate tabs are displayed correctlly
		record.tabsLocationVerified();
		Thread.sleep(Page.TIMEOUT_TINY);
		// 11.Validate the tab menu is displayed correctly under the tab name.
		// record.moveToElementAndClick(record.view_button, driver);
		System.out.println("UI1");
		Point checkbox_select_all = record.checkall.getLocation();
		System.out.println("UI2");
		Point view = record.view_button.getLocation();
		System.out.println("UI3");
		Point rec_tasks = record.recording_tasks_button.getLocation();
		System.out.println("UI4");
		System.out.println(rec_tasks);
		System.out.println(view);
		Point course_tasks = record.course_task_button.getLocation();
		System.out.println("rec_tasks.getX(): "+rec_tasks.getX());
		System.out.println("view.getY(): "+view.getY());
		System.out.println("rec_tasks.getY(): "+rec_tasks.getY());
		System.out.println("view.getX(): "+view.getX());
		System.out.println("course_tasks.getX(): "+course_tasks.getX());
		System.out.println("record.view_button.getLocation().getX(): "+view.getX());
		System.out.println("checkbox_select_all.getX()): "+checkbox_select_all.getX());
		if ((rec_tasks.getX() > course_tasks.getX()) && 
		    (view.getY() == rec_tasks.getY()) && 
		    (view.getX() < course_tasks.getX())	&& 
		    (course_tasks.getX() > record.view_button.getLocation().getX())	&& 
		    (course_tasks.getX() < checkbox_select_all.getX())) 
		{
			System.out.println(	"courses button right to view button,view dropdown alligned left,recording tasks alligned right and checkbox is right to it");
			ATUReports.add(	"courses button right to view button,view dropdown alligned left,recording tasks alligned right",LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));

			Assert.assertTrue(true);
		} else {
			System.out.println("Tab menu is not displayed correctly");
			Assert.assertTrue(false);
			ATUReports.add("Tab menu is not displayed correctly", LogAs.FAILED,
					new CaptureScreen(ScreenshotOf.DESKTOP));

		}

		// 12.Hover over "Course Tasks" element
		record.moveToElementAndClick(record.course_task_button, driver);
		if ((record.course_task_button.getAttribute("title").equals("Course Tasks"))
				&& (record.podcast_button.isDisplayed()) && (record.course_settings_button.isDisplayed())
				&& (record.video_podcast.isDisplayed()) && (record.subscribe_button.isDisplayed())
				&& (record.rssfeed.isDisplayed())) {
			System.out.println("all buttons are located and there is a hint");
			Assert.assertTrue(true);
			ATUReports.add("all buttons are located and there is a hint", LogAs.PASSED,
					new CaptureScreen(ScreenshotOf.DESKTOP));

		} else {

			System.out.println(" not all buttons are located");
			Assert.assertTrue(false);
			ATUReports.add(" not all buttons are located", LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));

		}

		// 13.Hover over "Recordings Tasks" element
		record.moveToElementAndClick(record.recording_tasks_button, driver);
		if ((record.recording_tasks_button.getAttribute("title").equals("Recording Tasks"))
				&& (record.copy_button.isDisplayed()) && (record.delete_button.isDisplayed())
				&& (record.move_button.isDisplayed()) && (record.share_recording_button.isDisplayed())
				&& (record.edit_rec_properties_button.isDisplayed()) && (record.edit_rec_button.isDisplayed())) {
			System.out.println("all buttons are located and there is a hint");
			Assert.assertTrue(true);
			ATUReports.add("all buttons are located and there is a hint", LogAs.PASSED,
					new CaptureScreen(ScreenshotOf.DESKTOP));

		} else {

			System.out.println(" not all buttons are located");
			Assert.assertTrue(false);
			ATUReports.add(" not all buttons are located", LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));

		}
		// 14.click one check box
		record.searchbox.click();
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);

		// 15.un-check one check box
		record.unClickOneCheckBoxOrVerifyNotSelected(record.checkbox);

		// 16.click all check box
		record.checkall.click();// make all checkboxes marked
		Thread.sleep(Page.TIMEOUT_TINY);
		record = PageFactory.initElements(driver, RecordingHelperPage.class);
		record.verifyAllCheckedboxSelected();

		// 17.verify all check box not selected
		record.checkall.click();// make all checkboxes unmarked
		record.verifyAllCheckedboxNotSelected();

		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
	}

	// description = "get courses list"
	public void initializeCourseObject() throws InterruptedException {

		course = PageFactory.initElements(driver, CoursesHelperPage.class);
		course.courses = course.getCoursesListFromElement(course.course_list);
	}
}
