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
public class TC8025ValidateFilteringRecordingsByTags {

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
		 System.out.println("Starting the test: TC8025ValidateFilteringRecordingsByTags at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC8025ValidateFilteringRecordingsByTags at " + DateToStr, "Starting the test: TC8025ValidateFilteringRecordingsByTags at " + DateToStr, LogAs.PASSED, null);	
	}

	@AfterClass
	public void closeBroswer() {
		this.driver.quit();
	}
		
	
	// @Parameters({"web","title"}) in the future
	@Test (description="TC8025 validate filtering recordings by tags")
	public void test8025() throws InterruptedException, ParseException {
		
		//1.enter the university
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);	
		
		//*+preconditions:+*	
		//Recordings in course with several tags is required for this test.	
		//End of preconditions*	
		tegrity.loginCourses("User1");
		
		//2.Click on preconditional course from the courses list.
		course.selectCourseThatStartingWith("Ab");
		
		for(int type_of_user = 0; type_of_user <2; type_of_user++) {
			
			if(type_of_user == 1){
				record.clickOnStudentRecordingsTab();
			}
		
		// pre need the recording list size and record name for later use 
		if(type_of_user== 0)	{
			listSizeRegularRecording = 	record.getCourseRecordingList().size();
			recordNameRegularRecording = record.getFirstRecordingTitle();
		} else {
			listSizeStudentRecording = 	record.getCourseRecordingList().size();
			recordNameStudentRecording = record.getFirstRecordingTitle();
		}
		//3.check several recordings respective checkboxes
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
				
		//4.Click the "Recording Tasks" drop-down menu and choose the "Tag" option
		record.clickOnRecordingTaskThenTag();
		
		//5.The "Tag" Dialog window is appeared.
		tag_window.waitForPageToLoad();
		tag_window.verifyTagWindowOpen();
		
		//6.we need to create tags
		if(type_of_user== 0) {
			tag_window.createNewTag("Record");
		} else {
			tag_window.createNewTag("Student");
		}
		//8.click on the cancel button
		tag_window.clickElementJS(tag_window.apply_button);
				
		}
			
		//9.sign out end preconditions
		record.signOut();
			
		//10.Login as INSTRUCTOR 
		tegrity.loginCourses("User1");
		
		//11.Click on preconditional course from the courses list.
		course.selectCourseThatStartingWith("Ab");
		
		//0 - for regular recording ,1- for student recording ,2- for student user
		for(int type_of_user = 0; type_of_user < 3; type_of_user++) {
			
			if(type_of_user == 1){
				record.clickOnStudentRecordingsTab();
			}
			
			if(type_of_user ==2){
				
				//sign out 
				record.signOut();
					
				//9.Login as STUDENT 
				tegrity.loginCourses("User4");
				
				//10.Click on the Ab course
				course.selectCourseThatStartingWith("Ab");
			}
		
		//12.check several recordings respective checkboxes
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);	
				
		//13.Hover over the "View" drop-down list menu.
		record.moveToElementAndPerform(record.view_tasks_button,driver);
				
		//14.Check the "Show all recordings" checkbox from the "View" drop-down list menu.
		record.veirfyThatTheCheckboxIsSelect(record.showAllRecordings);
		
		//15.verify All the tag checkboxes are unchecked.
		record.verifyAllTheTagCheckedboxNotSelected();
		
		//16.All the recordings are appeared
		if(type_of_user == 0 || type_of_user == 2) {
			record.checkThatAllTheRecordingsAreAppeared(listSizeRegularRecording);
		} else {
			record.checkThatAllTheRecordingsAreAppeared(listSizeStudentRecording);
		}
		//17.Check any tag checkbox.
		if(type_of_user == 0 || type_of_user == 2) {
			record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkboxs_tags.get(0));
		} else {
			record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkboxs_tags.get(1));
		}
		//18.The "(Show all recordings)" checkbox is unchecked.
		record.veirfyThatTheCheckboxIsNotSelect(record.showAllRecordings);
			
		//19.The selected tag checkboxes are checked.
		if(type_of_user == 0 || type_of_user == 2) {
			record.veirfyThatTheCheckboxIsSelect(record.checkboxs_tags.get(0));
		}else {
			record.veirfyThatTheCheckboxIsSelect(record.checkboxs_tags.get(1));
		}
		//20.The selected tagged recording are displayed.
		if(type_of_user == 0 || type_of_user == 2) {
			record.verifyThatTargetRecordingExistInRecordingList(recordNameRegularRecording);
		} else {
			record.verifyThatTargetRecordingExistInRecordingList(recordNameStudentRecording);
		}
		//21. enter to the other tabs 
		if(type_of_user == 0 || type_of_user == 2) {
			record.clickOnStudentRecordingsTab();
		}else {
			record.clickOnRecordingsTab();
		}
		//22. Recording tabs which not contain the filtered recording tags are empty from recordings.
		record.waitUntilRecordingListIsEmptyWithMaxTimeOut(1);
		
		//23.post step to return the recording state
		if(type_of_user == 0 || type_of_user == 2) {
			record.clickOnRecordingsTab();
		}else {
			record.clickOnStudentRecordingsTab();
		}

		//24.Hover over the "View" drop-down list menu.
		record.moveToElementAndPerform(record.view_tasks_button,driver);
		
		//25.The "(Show all recordings)" checkbox is unchecked.
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.showAllRecordings);
		
		}
		
		//post test delete all the tags
		//26.sign out 
		record.signOut();
					
		//27.Login as INSTRUCTOR 
		tegrity.loginCourses("User1");
				
		//28.Click on preconditional course from the courses list.
		course.selectCourseThatStartingWith("Ab");
			
		//29.Click the "Recording Tasks" drop-down menu and choose the "Tag" option
		record.clickOnRecordingTaskThenTag();
				
		//30.The "Tag" Dialog window is appeared.
		tag_window.waitForPageToLoad();
		tag_window.verifyTagWindowOpen();
		
		//31.delete all tags
		tag_window.deleteAllExistingTags();
		
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
	
	}
}