package com.automation.main.delete_recordings;



import org.openqa.selenium.WebDriver;
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
import atu.testng.reports.utils.Utils;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;
import junitx.util.PropertyManager;

@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC15640TryToDeleteAStudentRecordingAsAStudent {

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
		ATUReports.add("selected browser type", LogAs.PASSED, new CaptureScreen( ScreenshotOf.DESKTOP));

		
		ATUReports.setWebDriver(driver);
		ATUReports.add("set driver", true);
		tegrity = PageFactory.initElements(driver, LoginHelperPage.class);

		record = PageFactory.initElements(driver, RecordingHelperPage.class);
		copy = PageFactory.initElements(driver, CopyMenu.class);
		
		confirm_menu = PageFactory.initElements(driver, ConfirmationMenu.class);
		
		move_window = PageFactory.initElements(driver, MoveWindow.class);
		
		delete_menu = PageFactory.initElements(driver, DeleteMenu.class);
		
		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC15640TryToDeleteAStudentRecordingAsAStudent at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC15640TryToDeleteAStudentRecordingAsAStudent at " + DateToStr, "Starting the test: TC15640TryToDeleteAStudentRecordingAsAStudent at " + DateToStr, LogAs.PASSED, null);	
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

	@Test (description = "TC 15640 Try To Delete A Student Recording As A Student")
	public void test15640() throws InterruptedException
	{
		// 1. Login as INSTRUCTOR.
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
		tegrity.loginCourses("User4");// log in courses page
		initializeCourseObject();
		
		// 2. Select course.
		currentCourse = course.selectFirstCourse(record);
		System.out.println("Current course: " + currentCourse);
		
		// 3. Click on "Student Recordings" tab.
		record.clickOnStudentRecordingsTab();
		
		// 4. Select recording made by this STUDENT.
//		String user_name_of_student = record.getUserNmae();
//		int recording_by_user_index = record.getTargetUserNameRecordingIndex(user_name_of_student);
//		Thread.sleep(2000);
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
	
		// 5. Click the "Recording Tasks" button.
		record.recording_tasks_button.click();
		
		
		// 6. Verify that "Delete" menu item isn't dispalyed in menu.
		if (record.delete_button.isDisplayed()) {
			System.out.println("Verify that delete menu item is displayed in menu.");
			ATUReports.add("Verify that delete menu item is displayed in menu.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		} else {
			System.out.println("Verify that delete menu item is not displayed in menu.");
			ATUReports.add("Verify that delete menu item is not displayed in menu.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		}
		
		
		record.clickOnStudentRecordingsTab();
		
// 		Removed
//		//unselect user recorded checkbox 
//		record.unselectIndexCheckBox(recording_by_user_index);
//		
//		
//		// 7. Select recording made by another STUDENT.
//		int recording_by_other_user_index = record.getOtherThenTargetUserNameRecordingIndex(user_name_of_student);
//		record.selectIndexCheckBox(recording_by_other_user_index);
//		
//		// 8. Click the "Recording Tasks" button.
//		record.recording_tasks_button.click();
//		
//		// 9. Verify that "Delete" menu item isn't dispalyed in menu.
//		if (record.delete_button.isDisplayed()) {
//			System.out.println("Verify that delete menu item is displayed in menu.");
//			ATUReports.add("Verify that delete menu item is displayed in menu.", LogAs.FAILED, null);
//			Assert.assertTrue(false);
//		} else {
//			System.out.println("Verify that delete menu item is not displayed in menu.");
//			ATUReports.add("Verify that delete menu item is not displayed in menu.", LogAs.PASSED, null);
//			Assert.assertTrue(true);
//		}
//		
//		record.clickOnStudentRecordingsTab();
//		
//		//unselect other user checkboxx
//		record.unselectIndexCheckBox(recording_by_other_user_index);

		
		// 10. Select several recordings.
		try {
			record.check_all_checkbox.click();
			System.out.println("Clicked on selected all checkbox.");
			ATUReports.add("Clicked on selected all checkbox.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} catch (Exception msg) {
			System.out.println("Fail to clicked on selected all checkbox.");
			ATUReports.add("Fail to clicked on selected all checkbox.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		
		record.verifyAllCheckedboxSelected();
		
		// 11. Click the "Recording Tasks" button.
		record.recording_tasks_button.click();
		
		// 12. Verify that "Delete" menu item isn't dispalyed in menu.
		if (record.delete_button.isDisplayed()) {
			System.out.println("Verify that delete menu item is displayed in menu.");
			ATUReports.add("Verify that delete menu item is displayed in menu.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		} else {
			System.out.println("Verify that delete menu item is not displayed in menu.");
			ATUReports.add("Verify that delete menu item is not displayed in menu.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		}
		
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
		
	}
}
