package com.automation.main;



import org.openqa.selenium.WebDriver;import com.automation.main.page_helpers.Page;import com.automation.main.page_helpers.Page;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import atu.testng.reports.utils.Utils;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;
import java.text.DateFormat;
import java.util.Date;


@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC15534ExixtenceOfUIItemsCopy {

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
	

	@BeforeClass
	public void setup() {
		try {


			driver = DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());
			ATUReports.add("selected browser type", LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
			

			tegrity = PageFactory.initElements(driver, LoginHelperPage.class);

			record = PageFactory.initElements(driver, RecordingHelperPage.class);
			copy = PageFactory.initElements(driver, CopyMenu.class);
		} catch (Exception e) {
			ATUReports.add("Fail Step", LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
		}
		

		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC15534ExixtenceOfUIItemsCopy at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC15534ExixtenceOfUIItemsCopy at " + DateToStr, "Starting the test: TC15534ExixtenceOfUIItemsCopy at " + DateToStr, LogAs.PASSED, null);	
	}

	@AfterClass
	public void closeBroswer() {
		this.driver.quit();
	}
		
	private void setAuthorInfoForReports() {
		ATUReports.setAuthorInfo("Qualitest Automation ", Utils.getCurrentTime(), "1.0");
	}

	private void setIndexPageDescription() {
		ATUReports.indexPageDescription = "McGraw-Hill Automation</b>";

	}

	@Test
	public void testME() {
		setAuthorInfoForReports();
		setIndexPageDescription();
	}
	
	// @Parameters({"web","title"}) in the future
	@Test
	public void testUiExistence() throws InterruptedException {
		
		// log in courses page
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
		tegrity.loginCourses("User1");
		initializeCourseObject();
		
		//course.selectCourse(record);
		course.selectCourseThatStartingWith("Ab");
		Thread.sleep(Page.TIMEOUT_TINY);
		
		//verify that the color of the menu is grey
		record.verifyRecordingMenuColor(record.copy_button);
		record.verifyDisabledMenu();
		
		
		record.ClickOneCheckBoxOrVerifyAlreadySelected(record.getCheckbox());
		record.unClickOneCheckBoxOrVerifyNotSelected(record.getCheckbox());
		record.checkall.click();// make all checkboxes marked
		Thread.sleep(Page.TIMEOUT_TINY);
		
		record = PageFactory.initElements(driver, RecordingHelperPage.class);
		record.verifyAllCheckedboxSelected();
		
		/// driver.navigate().refresh();
		record.checkall.click();// make all checkboxes unmarked
		
		/// record.checkall.click();// make all checkboxes unmarked
		record.verifyAllCheckedboxNotSelected();
		
		record.getCheckbox().click();
		record.clickOnRecordingTaskThenCopy();
		copy.verifyCopyMenuTitle();
		Thread.sleep(Page.TIMEOUT_TINY);
		
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
		course.courses = course.getStringFromElement(course.course_list);
	}
}