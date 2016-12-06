package com.automation.main.tags;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import com.automation.main.page_helpers.AdminDashboardPage;
import com.automation.main.page_helpers.AdminDashboardViewCourseList;
import com.automation.main.page_helpers.CalendarPage;
import com.automation.main.page_helpers.CopyMenu;
import com.automation.main.page_helpers.CoursesHelperPage;
import com.automation.main.page_helpers.EditRecordingPropertiesWindow;
import com.automation.main.page_helpers.LoginHelperPage;
import com.automation.main.page_helpers.PublishWindow;
import com.automation.main.page_helpers.RecordingHelperPage;
import com.automation.main.page_helpers.TagMenu;
import com.automation.main.utilities.DriverSelector;
import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;


@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC8032ValidateTheTagCheckboxesUI {

	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}
	
	public PublishWindow publish_window;
	public AdminDashboardViewCourseList admin_dashboard_view_course_list;
	public LoginHelperPage tegrity;
	public CoursesHelperPage course;
	public RecordingHelperPage record;
	public CalendarPage calendarPage;
	public EditRecordingPropertiesWindow edit_recording_properties_window;
	public AdminDashboardPage admin_dash_board_page;
	WebDriver driver;
	WebDriverWait wait;
	public TagMenu tag_window;
	public static WebDriver thread_driver;
	CopyMenu copy;
	DesiredCapabilities capability;
	int listSizeRegularRecording , listSizeStudentRecording;
	String recordNameStudentRecording ,recordNameRegularRecording;

	@BeforeClass
	public void setup() {

			driver = DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());
			calendarPage = PageFactory.initElements(driver, CalendarPage.class);
			course = PageFactory.initElements(driver, CoursesHelperPage.class);
			admin_dash_board_page = PageFactory.initElements(driver, AdminDashboardPage.class);
			admin_dashboard_view_course_list = PageFactory.initElements(driver, AdminDashboardViewCourseList.class);
			tegrity = PageFactory.initElements(driver, LoginHelperPage.class);
			tag_window= PageFactory.initElements(driver, TagMenu.class);
			record = PageFactory.initElements(driver, RecordingHelperPage.class);
			copy = PageFactory.initElements(driver, CopyMenu.class);

		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC8032ValidateTheTagCheckboxesUI at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC8032ValidateTheTagCheckboxesUI at " + DateToStr, "Starting the test: TC8032ValidateTheTagCheckboxesUI at " + DateToStr, LogAs.PASSED, null);	
	}

	@AfterClass
	public void closeBroswer() {
		this.driver.quit();
	}
		
	// @Parameters({"web","title"}) in the future
	@Test (description="TC8032 Validate the tag checkboxes UI")
	public void test8032() throws InterruptedException, ParseException {
		
		//1.Enter the university
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);	
		
		//*+preconditions:+*	
		//Recordings in course with several tags is required for this test.	
		//End of preconditions*	
		tegrity.loginCourses("User1");
		
		//2.Click on preconditional course from the courses list.
		course.selectCourseThatStartingWith("Ab");
			
		//3.check several recordings respective checkboxes
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
				
		//4.Click the "Recording Tasks" drop-down menu and choose the "Tag" option
		record.clickOnRecordingTaskThenTag();
		
		//5.The "Tag" Dialog window is appeared.
		tag_window.waitForPageToLoad();
		tag_window.verifyTagWindowOpen();
		
		//6.we need to create tags
		tag_window.createNewTag("Record");
		tag_window.saveAllTheInstractors();
		
		//8.click on the cancel button
		tag_window.clickElementJS(tag_window.apply_button);
						
		//9.sign out end preconditions
		record.signOut();
			
		//10.Login as INSTRUCTOR 
		tegrity.loginCourses("User1");
		
		//11.Click on preconditional course from the courses list.
		course.selectCourseThatStartingWith("Ab");
		
		//12.check several recordings respective checkboxes
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);	
				
		//13.Click the "Recording Tasks" drop-down menu and choose the "Tag" option
		record.clickOnRecordingTaskThenTag();
				
		//14.The "Tag" Dialog window is appeared.
		tag_window.waitForPageToLoad();
		tag_window.verifyTagWindowOpen();
		
		//15.The selected recording tags checkbox is checked.
		tag_window.verifyAllTheTagCheckboxesAreChecked();
		
		//16.All the INSTRUCTOR owned tags is displayed.
		tag_window.verifyAllInstractorAreDisplay();
		
		//17.Click on the "Cancel" button.
		tag_window.clickElementJS(tag_window.cancel_button);
		
		//19.The "Tag Dialog" window is disappeared.
		tag_window.verifyTagWindowClose();
		
		//20.Check recordings without tags checkboxes.
		record.unselectIndexCheckBox(1);
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox2);	
		
		//21.Click the "Recording Tasks" drop-down menu and choose the "Tag" option
		record.clickOnRecordingTaskThenTag();
				
		//22.The "Tag" Dialog window is appeared.
		tag_window.waitForPageToLoad();
		tag_window.verifyTagWindowOpen();
		
		//23.The selected recording tags checkbox is checked.
		tag_window.verifyAllTheTagCheckboxesAreUnChecked();
				
		//24.All the INSTRUCTOR owned tags is displayed.
		tag_window.verifyAllInstractorAreDisplay();
		
		//25.Click on the "Cancel" button.
		tag_window.clickElementJS(tag_window.cancel_button);
		
		//26.The "Tag Dialog" window is disappeared.
		tag_window.verifyTagWindowClose();
		
		//27.Check at least one tagged recordings and at least one not tagged recordings checkboxes. 
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);	
		
		//28.Click the "Recording Tasks" drop-down menu and choose the "Tag" option
		record.clickOnRecordingTaskThenTag();
				
		//29.The "Tag" Dialog window is appeared.
		tag_window.waitForPageToLoad();
		tag_window.verifyTagWindowOpen();
		
		//30. The selected recording tags checkbox are in undefined state.
		tag_window.verifyCheckboxInUndefinedState();
		
		//31.All the INSTRUCTOR owned tags is displayed.
		tag_window.verifyAllInstractorAreDisplay();
		
		//32.Click on the "Cancel" button.
		tag_window.clickElementJS(tag_window.cancel_button);
				
		//33.The "Tag Dialog" window is disappeared.
		tag_window.verifyTagWindowClose();
		
		//post test delete all the tags
		//34.Click the "Recording Tasks" drop-down menu and choose the "Tag" option
		record.clickOnRecordingTaskThenTag();
				
		//35.The "Tag" Dialog window is appeared.
		tag_window.waitForPageToLoad();
		tag_window.verifyTagWindowOpen();
		
		//36.delete all tags
		tag_window.deleteAllExistingTags();
		
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
	
	}
}