package com.automation.main.other;


import java.awt.AWTException;
import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;import com.automation.main.page_helpers.Page;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.automation.main.page_helpers.ConfirmationMenu;
import com.automation.main.page_helpers.CopyMenu;
import com.automation.main.page_helpers.CoursesHelperPage;
import com.automation.main.page_helpers.LoginHelperPage;
import com.automation.main.page_helpers.RecordingHelperPage;

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
public class TestCaseSearchCourseInMoveWindow {

	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}

	public LoginHelperPage tegrity;
	public CoursesHelperPage course;
	public RecordingHelperPage record;
	public ConfirmationMenu confirm_menu;
	WebDriver driver;
	WebDriverWait wait;
	public static WebDriver thread_driver;
	CopyMenu copy;
	String currentCourse;
	String targetCourse;
	String clickedRecording;
    DesiredCapabilities capability;
	@BeforeClass
	public void setup() {
		System.setProperty("webdriver.ie.driver", "src/test/resources/IEDriverServer.exe");
		capability=DesiredCapabilities.internetExplorer();
		capability.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING,false);
		
	driver=new InternetExplorerDriver(capability);
		ATUReports.add("selected browser type", LogAs.PASSED, new CaptureScreen( ScreenshotOf.DESKTOP));

		
	ATUReports.setWebDriver(driver);
	ATUReports.add("set driver", true);
		tegrity = PageFactory.initElements(driver, LoginHelperPage.class);

		record = PageFactory.initElements(driver, RecordingHelperPage.class);
		copy = PageFactory.initElements(driver, CopyMenu.class);
		
		confirm_menu = PageFactory.initElements(driver, ConfirmationMenu.class);
	}
	
/*	
	@AfterTest
	public void closeBroswer() {
		this.driver.quit();
	}
*/
	private void setAuthorInfoForReports() {
		ATUReports.setAuthorInfo("McGrawHill Automation ", Utils.getCurrentTime(), "1.0");
	}

	private void setIndexPageDescription() {
		ATUReports.indexPageDescription = "Mcgeawhill Verify <br/> <b> UI existence</b>";
		
	}

	@Test
	public void testME() {
		setAuthorInfoForReports();
		setIndexPageDescription();
	}
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
		// 1. Login as INSTRUCTOR.
		tegrity.loginCourses("Instructor");// log in courses page
		initializeCourseObject();
		
		Thread.sleep(Page.TIMEOUT_TINY);
		
		// 2. Select course.
		currentCourse = course.selectSecondCourse(record);
		System.out.println("Current course: " + currentCourse);
		//course.selectCourse(record);
		
		Thread.sleep(Page.TIMEOUT_TINY);
		
		// 3. Select recording.
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
		
		Thread.sleep(Page.TIMEOUT_TINY);
		
		// 4. Select "Recording Tasks -> Move"
		record.toMoveMenu();
		
		// get course list to compare with course list from point #6
		List<String> course_list_preclick_on_search_button = copy.getCourseList(); 
		
		Thread.sleep(Page.TIMEOUT_TINY);
		
		// 5. Click the "Search" button.
		copy.clickOnSearchButton();
		
		Thread.sleep(Page.TIMEOUT_TINY);
		
		// 6. "Courses List" is not changed.
		List<String> course_list_after_clicking_on_search_button = copy.getCourseList();
		
		course_list_preclick_on_search_button.removeAll(course_list_after_clicking_on_search_button);
		
		if(course_list_preclick_on_search_button.isEmpty()) {
			System.out.println("Course list is not changed");
			ATUReports.add("Course list", "Course list is not changed", "Course list is not changed", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Course list is changed");
			ATUReports.add("Course list", "Course list is not changed", "Course list is changed", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		
		Thread.sleep(Page.TIMEOUT_TINY);
		
		// 7. Enter text which isn't a part of any course's title in "Search" field.
		String no_course_match_text = "sd5fg87d68f6sd87";
		copy.sendKeysToSearchInputBox(no_course_match_text);

		Thread.sleep(Page.TIMEOUT_TINY);
		
		// 8. Text is displayed in a "Search" textbox.
		copy.isTextDisplayedInSearchBox(no_course_match_text);
		
		Thread.sleep(Page.TIMEOUT_TINY);
		
		// 9. Click the "Search" button.
		copy.clickOnSearchButton();
		
		Thread.sleep(Page.TIMEOUT_TINY);
		
		// 10. "Courses List" is empty.
		List<String> current_course_list = copy.getCourseList();
		
		if(current_course_list.size() == 0) {
			System.out.println("Course list is empty");
			ATUReports.add("Course list", "Course list is empty", "Course list is empty", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Course list is not empty");
			ATUReports.add("Course list", "Course list is empty", "Course list is not empty", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		
		Thread.sleep(Page.TIMEOUT_TINY);
		
		// 11. Enter course name which you do not have premissions to work with.
		String course_name_not_have_premissions = "15567NOTALLOW";
		copy.deleteValueInSearchInputBox();
		copy.sendKeysToSearchInputBox(course_name_not_have_premissions);
		
		Thread.sleep(Page.TIMEOUT_TINY);
		
		// 12. Text is displayed in a "Search" textbox.
		copy.isTextDisplayedInSearchBox(course_name_not_have_premissions);
		
		Thread.sleep(Page.TIMEOUT_TINY);
		
		// 13. Click the "Search" button.
		copy.clickOnSearchButton();
		
		Thread.sleep(Page.TIMEOUT_TINY);
		
		// 14. "Courses List" is empty.
		current_course_list = copy.getCourseList();
		
		if(current_course_list.size() == 0) {
			System.out.println("Course list is empty");
			ATUReports.add("Course list", "Course list is empty", "Course list is empty", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Course list is not empty");
			ATUReports.add("Course list", "Course list is empty", "Course list is not empty", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		
		Thread.sleep(Page.TIMEOUT_TINY);
		
		// 15. Enter specific course's title in "Search" field.
		String specific_course_title = course_list_after_clicking_on_search_button.get(1);
		copy.deleteValueInSearchInputBox();
		copy.sendKeysToSearchInputBox(specific_course_title);
		
		Thread.sleep(Page.TIMEOUT_TINY);
		
		// 16. Text is displayed in a "Search" textbox.
		copy.isTextDisplayedInSearchBox(specific_course_title);
		
		Thread.sleep(Page.TIMEOUT_TINY);
		
		// 17. Click the "Search" button.
		copy.clickOnSearchButton();
		
		Thread.sleep(Page.TIMEOUT_TINY);
		
		// 18. Only specific course's title is displayed in "Courses List".
		current_course_list = copy.getCourseList();
		
		if((current_course_list.size() == 1) && (current_course_list.get(0).equals(specific_course_title))) {
			System.out.println("Specific cours titile is displayed");
			ATUReports.add("Course list", "Specific cours titile is displayed", "Specific cours titile is displayed", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Specific cours titile is not displayed");
			ATUReports.add("Course list", "Specific cours titile is displayed", "Specific cours titile is not displayed", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		
		Thread.sleep(Page.TIMEOUT_TINY);
		
		// 19. Enter text which is part of several courses titles in "Search" field.
		String course_name_that_part_of_serveral_courses = "Course";
		copy.deleteValueInSearchInputBox();
		copy.sendKeysToSearchInputBox(course_name_that_part_of_serveral_courses);
		
		Thread.sleep(Page.TIMEOUT_TINY);
		
		// 20. Text is displayed in a "Search" textbox.
		copy.isTextDisplayedInSearchBox(course_name_that_part_of_serveral_courses);
		
		Thread.sleep(Page.TIMEOUT_TINY);
		
		// 21. Click the "Search" button.
		copy.clickOnSearchButton();
		
		Thread.sleep(Page.TIMEOUT_TINY);
		
		// 22. Only courses with common text in titles are displayed in "Courses List".
		current_course_list = copy.getCourseList();
		
		boolean is_all_courses_contain_searched_word = true;
		for(String course_name: current_course_list) {
			if (!course_name.toLowerCase().contains(course_name_that_part_of_serveral_courses.toLowerCase())) {
				System.out.println(course_name);
				is_all_courses_contain_searched_word = false;
				break;
			}
		}
		
		if((is_all_courses_contain_searched_word) && (current_course_list.size() > 1)) {
			System.out.println("Only courses with common text in titles are displayed in course list");
			ATUReports.add("Course list", "Only courses with common text in titles are displayed in course list", "Only courses with common text in titles are displayed in course list", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			if ((current_course_list.size() <= 1)) {
				System.out.println("Error - number of common  courses which displayed with same name is 1 or less");
				ATUReports.add("Course list", "Only courses with common text in titles are displayed in course list", "Error - number of common  courses which displayed with same name is 1 or less", LogAs.FAILED, null);
				Assert.assertTrue(false);
			} else {
				System.out.println("Not only courses with common text in titles are displayed in course list");
				ATUReports.add("Course list", "Only courses with common text in titles are displayed in course list", "Not only courses with common text in titles are displayed in course list", LogAs.FAILED, null);
				Assert.assertTrue(false);
			}
			
		}
		driver.quit();
	}
}
