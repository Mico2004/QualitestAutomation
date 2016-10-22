package com.automation.main.delete_additional_content;



import java.text.DateFormat;
import java.util.Date;

import org.openqa.selenium.WebDriver;import com.automation.main.page_helpers.Page;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
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

import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;

@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC15812ExistenceofUIitems {

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
		 System.out.println("Starting the test: TC15812ExistenceofUIitems at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC15812ExistenceofUIitems at " + DateToStr,
		 "Starting the test: TC15812ExistenceofUIitems at " + DateToStr, LogAs.PASSED, null);
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

	@Test (description="TC 15812 Existence of UI items")
	public void test15812() throws InterruptedException
	{
		// 1. Login as INSTRUCTOR (User1).
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
		tegrity.loginCourses("User1");// log in courses page
		initializeCourseObject();
		
		// 2. Select course (Ab).
		currentCourse = course.selectCourseThatStartingWith("Ab");
		System.out.println("Current course: " + currentCourse);
		
		// 3. Click the "Additional Content" tab.
		record.clickOnAdditionContentTab();
		Thread.sleep(Page.TIMEOUT_TINY);
		
		// 4. Select content item.
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
		String target_recording = record.getNameTargetIndexAdditionalContent(1);
		
		System.out.println(target_recording);

		
		// 5. Select "Content Tasks -> Delete" menu item.
		record.clickOnContentTaskThenDelete();
		
		// 6. "Delete" window is displayed.
		delete_menu.verifyDeleteWindowDisplayed();
		
		// 7. The window title is: "Delete".
		delete_menu.verifyDeleteWindowTitleIsDelete();
		
		// 8. The window title background is the same as the color selected for university.
		copy.verifyMenuColor(record);
		
		// 9. There is an informative text displayed below the window title: "Are you sure you want to delete the following items?".
		delete_menu.verifyInfoText();
		
		// 10. The list of additional content files and/or links to delete is displayed in the "Delete" window.
		delete_menu.verifyTargetRecordingIsTheOnlyRecordingInAdditionalContentDeleteWindowRecordingList(target_recording);
		
		// 11. The "Delete" button is displayed at the bottom right of the window.
		// 12. The "Cancel" button is displayed left to the "Delete" button.
		delete_menu.verifyDeleteMenuElementsLocation();
		
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);

	
	}
}
