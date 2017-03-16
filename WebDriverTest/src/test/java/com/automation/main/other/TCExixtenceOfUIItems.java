package com.automation.main.other;



import java.text.DateFormat;
import java.util.Date;

import org.openqa.selenium.WebDriver;

import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.automation.main.page_helpers.CopyMenu;
import com.automation.main.page_helpers.CoursesHelperPage;
import com.automation.main.page_helpers.LoginHelperPage;
import com.automation.main.page_helpers.RecordingHelperPage;
import com.automation.main.utilities.DriverSelector;

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
public class TCExixtenceOfUIItems {

	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}

	public LoginHelperPage tegrity;
	public CoursesHelperPage course;
	public RecordingHelperPage record;
	WebDriver driver;
	WebDriverWait wait;
	public static WebDriver thread_driver;
	CopyMenu copy;
	DesiredCapabilities capability;
String os;
	
@AfterClass
public void closeBroswer() {		
	this.driver.quit();
}

@BeforeClass
	public void setup() {
		try {

			System.setProperty("webdriver.ie.driver", "src/test/resources/IEDriverServer.exe");
			capability=DesiredCapabilities.internetExplorer();
			capability.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING,false);
			
		driver=DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());
			///
		//	driver = new FirefoxDriver();
			ATUReports.add("selected browser type", LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
			

			tegrity = PageFactory.initElements(driver, LoginHelperPage.class);

			record = PageFactory.initElements(driver, RecordingHelperPage.class);
			copy = PageFactory.initElements(driver, CopyMenu.class);
			
			 Date curDate = new Date();
			 String DateToStr = DateFormat.getInstance().format(curDate);
			 System.out.println("Starting the test: TCExixtenceOfUIItems at " + DateToStr);
			 ATUReports.add("Message window.", "Starting the test: TCExixtenceOfUIItems at " + DateToStr,
			 "Starting the test: TCExixtenceOfUIItems at " + DateToStr, LogAs.PASSED, null);
			 
		} catch (Exception e) {
			ATUReports.add("Fail Step", LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
		}

	}

	/*
	 * @Test public void testNewLogs() throws AWTException, IOException {
	 * 
	 * ATUReports.add("INfo Step", LogAs.INFO, new CaptureScreen(
	 * ScreenshotOf.BROWSER_PAGE)); ATUReports.add("Pass Step", LogAs.PASSED,
	 * new CaptureScreen( ScreenshotOf.DESKTOP)); /// WebElement element =
	 * driver /// .findElement(By.xpath("/html/body/div/h1/a")); ATUReports.add(
	 * "Warning Step", LogAs.WARNING, new CaptureScreen(element));
	 * ATUReports.add("Fail step","" ,"",LogAs.FAILED, new CaptureScreen(
	 * ScreenshotOf.DESKTOP)); }
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
	public void testUiExistence() throws InterruptedException {
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
		tegrity.loginCourses("Instructor");// log in courses page
		initializeCourseObject();
		//course.selectCourse(record);
		record.verifyRecordingMenuColor();
		record.verifyDisabledMenu();
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
		record.unClickOneCheckBoxOrVerifyNotSelected(record.checkbox);
		record.checkall.click();// make all checkboxes marked
		Thread.sleep(2000);
		record = PageFactory.initElements(driver, RecordingHelperPage.class);
		record.verifyAllCheckedboxSelected();
		/// driver.navigate().refresh();
		record.checkall.click();// make all checkboxes unmarked
		/// record.checkall.click();// make all checkboxes unmarked
		record.verifyAllCheckedboxNotSelected();
		copy.verifyCopyMenu(record);
		copy.verifyCopyMenuTitle();
		Thread.sleep(2000);
		copy.verifyMenuColor(record);
		copy.verifyInfoText();
		copy.verifyCoursesInCopyMenu(course);
		copy.verifySearchCourseBox();
		copy.verifySearchCourseBoxText();
		copy.verifyCopyMenuElementsLocation();
		
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
	}

	// description = "get courses list"
	public void initializeCourseObject() throws InterruptedException {

		course = PageFactory.initElements(driver, CoursesHelperPage.class);
		course.courses = course.getCoursesListFromElement(course.course_list);
	}
}
