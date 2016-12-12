package com.automation.main.move_recording;


import org.testng.annotations.AfterClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.automation.main.page_helpers.CopyMenu;
import com.automation.main.page_helpers.CoursesHelperPage;
import com.automation.main.page_helpers.LoginHelperPage;
import com.automation.main.page_helpers.MoveWindow;
import com.automation.main.page_helpers.RecordingHelperPage;
import com.automation.main.utilities.DriverSelector;

import java.text.DateFormat;
import java.util.Date;
import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;

public class TC15580ExistenceOfUIItemsMove {

	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}
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
	String [] records_for_delete;
	
	
	@AfterClass
	public void closeBroswer() {		
		this.driver.quit();
	}

    @BeforeClass
	public void setup() {
		


			driver = DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());
			
			wait = new WebDriverWait(driver, 30);
			
			ATUReports.setWebDriver(driver);
			tegrity = PageFactory.initElements(driver, LoginHelperPage.class);
			 /// delete=PageFactory.initElements(driver,DeleteMenu.class);
			record = PageFactory.initElements(driver, RecordingHelperPage.class);
			copy = PageFactory.initElements(driver, CopyMenu.class);
		    move_Window= PageFactory.initElements(driver, MoveWindow.class);
	
		
		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC15580ExistenceOfUIItemsMove at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC15580ExistenceOfUIItemsMove at " + DateToStr, "Starting the test: TC15580ExistenceOfUIItemsMove at " + DateToStr, LogAs.PASSED, null);	

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



	// @Parameters({"web","title"}) in the future
	@Test (description="TC 15580 Existence Of UI Items Move")
	public void test15580() throws Exception {
	//1.load page
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
		//2.login as instructor
		tegrity.loginCourses("User1");// log in courses page
		initializeCourseObject();
		//3.Click "Recording Tasks" button (without selecting a recording)
		course.selectCourseThatStartingWith("Ab");
		
		//4.a.verify move button is grey
		record.verifyRecordingMenuColor(record.move_button);
		//4.b.verify move button is disabled
		record.verifyDisabledMenu();
		
		//5.click one check box
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
		
		//6.un-check one check box
		record.unClickOneCheckBoxOrVerifyNotSelected(record.checkbox);
	
		//7.click all check box
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkall);
		record = PageFactory.initElements(driver, RecordingHelperPage.class);
		record.verifyAllCheckedboxSelected();
		
		//8.verify all check box not selected
		record.unselectallCheckbox();
		record.verifyAllCheckedboxNotSelected();
		
		//9.to move menu
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
		record.clickOnRecordingTaskThenMove();

		//10.verify move title
		move_Window.verifyMoveMenuTitle();
	    Thread.sleep(2000);
		//11.verify menu color
	    move_Window.verifyMenuColor(record);
		///12.verify text
	    move_Window.verifyInfoText();
	   //13.verify has same lists of courses in move menu and course page
	    move_Window.verifyCoursesInMoveMenu(course);
		//14.verify search course
	    copy.verifySearchCourseBox();
		//15.verify search course text
	    move_Window.verifySearchCourseBoxText();
		//16.verify button locations
	    move_Window.verifyMoveMenuElementsLocation();
	  
	    System.out.println("Done.");
	    ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
	}

	// description = "get courses list"
	public void initializeCourseObject() throws InterruptedException {

		course = PageFactory.initElements(driver, CoursesHelperPage.class);
		course.courses = course.getCoursesListFromElement(course.course_list);
	}
}
