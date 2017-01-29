package com.automation.main.share_recording;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import com.automation.main.page_helpers.AdminDashboardPage;
import com.automation.main.page_helpers.AdminDashboardViewCourseList;
import com.automation.main.page_helpers.ConfirmationMenu;
import com.automation.main.page_helpers.CopyMenu;
import com.automation.main.page_helpers.CoursesHelperPage;
import com.automation.main.page_helpers.DeleteMenu;
import com.automation.main.page_helpers.LoginHelperPage;
import com.automation.main.page_helpers.PlayerPage;
import com.automation.main.page_helpers.RecordingHelperPage;
import com.automation.main.page_helpers.TagMenu;
import com.automation.main.utilities.DriverSelector;
import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC7969ValidateThatIfAUserWasEnrolledInTheCourseAsStudentCorrespondingMenuItemShouldBeHidden {

	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}
	
	public AdminDashboardViewCourseList admin_dashboard_view_course_list;
	public LoginHelperPage tegrity;
	public CoursesHelperPage course;
	public RecordingHelperPage record;
	public PlayerPage player_page;
	public AdminDashboardPage admin_dash_board_page;
	public DeleteMenu delete_menu;
	WebDriver driver;
	WebDriverWait wait;
	public TagMenu tag_window;
	public static WebDriver thread_driver;
	public ConfirmationMenu confirm_menu;
	public CopyMenu copy;
	DesiredCapabilities capability;
	String bookmark_to_add;
	String recordName;
	boolean isDeleteDisplay;
	List<String> namesOfbookmarks = new ArrayList<String>();
	@BeforeClass
	public void setup() {

			driver = DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());
			player_page = PageFactory.initElements(driver, PlayerPage.class);
			course = PageFactory.initElements(driver, CoursesHelperPage.class);
			admin_dash_board_page = PageFactory.initElements(driver, AdminDashboardPage.class);
			admin_dashboard_view_course_list = PageFactory.initElements(driver, AdminDashboardViewCourseList.class);
			confirm_menu = PageFactory.initElements(driver, ConfirmationMenu.class);
			tegrity = PageFactory.initElements(driver, LoginHelperPage.class);
			tag_window= PageFactory.initElements(driver, TagMenu.class);
			record = PageFactory.initElements(driver, RecordingHelperPage.class);
			copy = PageFactory.initElements(driver, CopyMenu.class);
			delete_menu = PageFactory.initElements(driver, DeleteMenu.class);
			
		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC7969ValidateThatIfAUserWasEnrolledInTheCourseAsStudentCorrespondingMenuItemShouldBeHidden at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC7969ValidateThatIfAUserWasEnrolledInTheCourseAsStudentCorrespondingMenuItemShouldBeHidden at " + DateToStr, "Starting the test: TC7969ValidateThatIfAUserWasEnrolledInTheCourseAsStudentCorrespondingMenuItemShouldBeHidden at " + DateToStr, LogAs.PASSED, null);	
	}

	@AfterClass
	public void closeBroswer() {
		this.driver.quit();
	}
		
	// @Parameters({"web","title"}) in the future
	@Test (description="TC7969 Validate that if a user was enrolled in the course as Student - corresponding menu item should be hidden")
	public void test7969() throws InterruptedException {
		
		//1.Enter the university
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);	
		
		//2.Login as a Student.
		tegrity.loginCourses("User4");
		
		//3.Open some course.
		course.selectCourseThatStartingWith("Ab");

		//4.Choose one recording and checked the recording checkbox.
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
		
		//5.Open "Recording Tasks".
		record.moveToElementAndClick(record.recording_tasks_button, driver);
		
		//6. Validate that "Share Recording" option is not displayed in the menu. 
		record.verifyWebElementNotDisplayed(record.share_recording_button, "Share Recording");
		
		
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
	}		
}

