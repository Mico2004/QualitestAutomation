package com.automation.main.delete_additional_content;



import org.openqa.selenium.WebDriver;import com.automation.main.page_helpers.Page;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.automation.main.page_helpers.ConfirmationMenu;
import com.automation.main.page_helpers.CopyMenu;
import com.automation.main.page_helpers.CoursesHelperPage;
import com.automation.main.page_helpers.DeleteMenu;
import com.automation.main.page_helpers.LoginHelperPage;
import com.automation.main.page_helpers.MoveWindow;
import com.automation.main.page_helpers.RecordingHelperPage;
import com.automation.main.utilities.DriverSelector;

import java.text.DateFormat;
import java.util.Date;
import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;


@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC15824TryToDeleteContentAsAStudent {

	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}

	public LoginHelperPage tegrity;
	public CoursesHelperPage course;
	public RecordingHelperPage record;
	public ConfirmationMenu confirm_menu;
	public MoveWindow move_window;
	public DeleteMenu delete_menu;
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

    	driver = DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());
    	
	
		ATUReports.setWebDriver(driver);
	
		tegrity = PageFactory.initElements(driver, LoginHelperPage.class);

		record = PageFactory.initElements(driver, RecordingHelperPage.class);
		copy = PageFactory.initElements(driver, CopyMenu.class);
		
		confirm_menu = PageFactory.initElements(driver, ConfirmationMenu.class);
		
		move_window = PageFactory.initElements(driver, MoveWindow.class);
		
		delete_menu = PageFactory.initElements(driver, DeleteMenu.class);
		
		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC15824TryToDeleteContentAsAStudent at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC15824TryToDeleteContentAsAStudent at " + DateToStr,
		 "Starting the test: TC15824TryToDeleteContentAsAStudent at " + DateToStr, LogAs.PASSED, null);	
	}
	
	
	@AfterClass
	public void closeBroswer() {
		this.driver.quit();
	}


	
	// description = "get courses list"
	public void initializeCourseObject() throws InterruptedException {

		course = PageFactory.initElements(driver, CoursesHelperPage.class);
		course.courses = course.getCoursesListFromElement(course.course_list);
	}

	@Test (description="TC 15824 Try To Delete Content As A Student")
	public void test15824() throws InterruptedException
	{
		// 1. Login as Student.
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
		tegrity.loginCourses("User4");// log in courses page
		initializeCourseObject();
		
		// 2. Select course.
		currentCourse = course.selectCourseThatStartingWith("Ab");
		System.out.println("Current course: " + currentCourse);
		
		// 3. Click on "Student Recordings" tab.
		record.clickOnAdditionContentTab();
		Thread.sleep(Page.TIMEOUT_TINY);
		
		// 4. Verify that STUDENT can't delete an additional content.
		// 4.1. "Content Tasks" button isn't displayed.
		record.verifyContentTasksButtonNotDisplayed();
		
		// 4.2. Checkboxes are not displayed.
		record.verifyThatNoCheckboxIsDisplayed();
		
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
	}
}
