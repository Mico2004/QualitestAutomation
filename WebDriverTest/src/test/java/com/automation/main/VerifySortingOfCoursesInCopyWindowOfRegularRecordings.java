package com.automation.main;

import java.awt.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.utils.Utils;

//@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class VerifySortingOfCoursesInCopyWindowOfRegularRecordings {

	public VerifySortingOfCoursesInCopyWindowOfRegularRecordings() {
		// TODO Auto-generated constructor stub
	}
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

	@BeforeClass
	public void setup() {
		try {
			System.setProperty("webdriver.ie.driver", "src/test/resources/IEDriverServer.exe");
			capability=DesiredCapabilities.internetExplorer();
			capability.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING,false);
			
		driver=new InternetExplorerDriver(capability);
			

			tegrity = PageFactory.initElements(driver, LoginHelperPage.class);

			record = PageFactory.initElements(driver, RecordingHelperPage.class);
			copy = PageFactory.initElements(driver, CopyMenu.class);
			player_page = PageFactory.initElements(driver, PlayerPage.class);
		} catch (Exception e) {
			
		}

	}


	private void setAuthorInfoForReports() {
		ATUReports.setAuthorInfo("McGrawHill Automation ", Utils.getCurrentTime(), "1.0");
	}

	private void setIndexPageDescription() {
		ATUReports.indexPageDescription = "Verify the sorting of courses in Copy window of regular recordings";

	}
	

	@Test
	public void testME() {
		setAuthorInfoForReports();
		setIndexPageDescription();
	}
	
	@Test
	public void VerifySortingOfCoursesInCopyWindow() throws InterruptedException
	{
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);

		tegrity.loginCourses("Instructor");// log in courses page
		initializeCourseObject();
		course.selectFirstCourse(record);
		record = PageFactory.initElements(driver, RecordingHelperPage.class);
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
        record.getCheckbox().click();
		copy.verifyCopyMenu(record);
		//7.verify courses are displayed in alphabetical order
		record.verifyRecordingSortedByTitle(copy.getCourseList());///verify sorted by title
		driver.quit();
	}
	
	// description = "get courses list"
	public void initializeCourseObject() throws InterruptedException {

		course = PageFactory.initElements(driver, CoursesHelperPage.class);
		course.courses = course.getStringFromElement(course.course_list);
		course.size = course.course_list.size();
	}
	
}
