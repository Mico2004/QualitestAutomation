package com.automation.main;

import java.awt.List;

import org.openqa.selenium.WebDriver;import com.automation.main.page_helpers.Page;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.text.DateFormat;
import java.util.Date;
import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import atu.testng.reports.utils.Utils;

//@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC17362VerifySortingOfCoursesInMoveWindowOfREgularRecording {

	public TC17362VerifySortingOfCoursesInMoveWindowOfREgularRecording() {
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

			driver = DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());
			

			tegrity = PageFactory.initElements(driver, LoginHelperPage.class);

			record = PageFactory.initElements(driver, RecordingHelperPage.class);
			copy = PageFactory.initElements(driver, CopyMenu.class);
			player_page = PageFactory.initElements(driver, PlayerPage.class);
			
			wait = new WebDriverWait(driver, 30);
			
			 Date curDate = new Date();
			 String DateToStr = DateFormat.getInstance().format(curDate);
			 System.out.println("Starting the test: TC17362VerifySortingOfCoursesInMoveWindowOfREgularRecording at " + DateToStr);
			 ATUReports.add("Message window.", "Starting the test: TC17362VerifySortingOfCoursesInMoveWindowOfREgularRecording at " + DateToStr,
			 "Starting the test: TC17362VerifySortingOfCoursesInMoveWindowOfREgularRecording at " + DateToStr, LogAs.PASSED, null);
		} catch (Exception e) {
			/// ATUReports.add("Fail Step", LogAs.FAILED, new
			/// CaptureScreen(ScreenshotOf.DESKTOP));
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
		ATUReports.indexPageDescription = "Verify the sorting of courses in Copy window of regular recordings";

	}
	
	@AfterClass
	public void closeBroswer() {
		this.driver.quit();
	}
	

	@Test
	public void testME() {
		setAuthorInfoForReports();
		setIndexPageDescription();
	}
	
	@Test
	public void VerifySortingOfCoursesInCopyWindow() throws Exception
	{
		//1.load page
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
        //2.login as instructor
		tegrity.loginCourses("User1");// log in courses page
		initializeCourseObject();
		//3.select course
		course.selectCourseThatStartingWith("Ab");
		record = PageFactory.initElements(driver, RecordingHelperPage.class);
		
		wait.until(ExpectedConditions.elementToBeClickable(record.getCheckbox()));
		record.getCheckbox().click();
		record.clickOnRecordingTaskThenMove();
		Thread.sleep(Page.TIMEOUT_TINY);
        //7.verify courses are displayed in alphabetical order
		record.verifyRecordingSortedByTitle(copy.getCourseList());///verify sorted by title
		
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
		
	}
	
	// description = "get courses list"
	public void initializeCourseObject() throws InterruptedException {

		course = PageFactory.initElements(driver, CoursesHelperPage.class);
		course.courses = course.getStringFromElement(course.course_list);
		course.size = course.course_list.size();
	}

   @AfterClass
   public void quit()
   {
	driver.quit();
   }

}
