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
import com.automation.main.page_helpers.ConfirmationMenu;
import com.automation.main.page_helpers.CopyMenu;
import com.automation.main.page_helpers.CoursesHelperPage;
import com.automation.main.page_helpers.DeleteMenu;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC8079ValidateTheTagsCopiedAfterRecordingIsCopied {

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
	public DeleteMenu delete_menu;
	WebDriver driver;
	WebDriverWait wait;
	public TagMenu tag_window;
	public static WebDriver thread_driver;
	public ConfirmationMenu confirm_menu;
	public CopyMenu copy;
	DesiredCapabilities capability;
	String firstValidName,SecondVaildName;
	String firstRecordingName,secondRecordingName;
	List<String> namesOfTags = new ArrayList<String>();
	@BeforeClass
	public void setup() {

			driver = DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());
			calendarPage = PageFactory.initElements(driver, CalendarPage.class);
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
		 System.out.println("Starting the test: TC8079ValidateTheTagsCopiedAfterRecordingIsCopied at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC8079ValidateTheTagsCopiedAfterRecordingIsCopied at " + DateToStr, "Starting the test: TC8079ValidateTheTagsCopiedAfterRecordingIsCopied at " + DateToStr, LogAs.PASSED, null);	
	}

	@AfterClass
	public void closeBroswer() {
		this.driver.quit();
	}
		
	// @Parameters({"web","title"}) in the future
	@Test (description="TC8079 Validate the tags copied after recording is copied")
	public void test8079() throws InterruptedException {
		
		//1.Enter the university
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);	
		
		//*+preconditions:+*Recordings in course with several tags is required for this test.	
		//2.pre test - Login as INSTRUCTOR 
		tegrity.loginCourses("User1");
		
		//3.preconditions delete all courses at destination course
		course.deleteAllRecordingsInCourseStartWith("abc", 0, record, delete_menu);
		course.deleteAllRecordingsInCourseStartWith("abc", 2, record, delete_menu);
		
		//4.Click on certain course from the courses list.
		course.selectCourseThatStartingWith("Ab");
		
		//0 - for regular recording ,1- for student recording 
		for(int type_of_user = 0; type_of_user < 2; type_of_user++) {
				
			for(int number_of_recording = 0; number_of_recording < 2; number_of_recording++) {
			
				if(type_of_user == 1) {
					record.clickOnStudentRecordingsTab();
				}
		
				//5.Check one available recording checkbox. 
				if(number_of_recording == 0){
					record.getTheElementAndClick("Checkbox1");
					firstRecordingName = record.getFirstRecordingTitle();
				} else {
					record.unselectIndexCheckBox(1);
					record.getTheElementAndClick("Checkbox2");
					secondRecordingName = record.getSecondRecordingTitle();
				}
				//6.Click the "Recording Tasks" drop-down menu and choose the "Tag" option
				record.clickOnRecordingTaskThenTag();
	
				//7.The "Tag" Dialog window is appeared.
				tag_window.waitForPageToLoad();
		
				//8.pick the name of the relevant tag
				if(type_of_user == 0){
					if (number_of_recording == 0) {
						firstValidName = "Test";
					} else {
						SecondVaildName = "Mos";
					}		
				}else {
					if (number_of_recording == 0) {
						firstValidName = "Exam";
					} else {
						SecondVaildName = "Mk";
					}
				}
				
				//9.create 2 tags for the test
				if(number_of_recording == 0) {
					tag_window.createNewTag(firstValidName);
				}else if(number_of_recording ==1) {
					tag_window.createNewTag(SecondVaildName);
					//10. make the tag private
					tag_window.clickElement(tag_window.edit_tag_button_list.get(0));
					record.SelectOneCheckBoxOrVerifyAlreadySelected(tag_window.privte_checkbox);
				}
				//11.Click on the "Apply" button
				record.clickElementJS(tag_window.apply_button);
		
		}
		//13. sign out
		record.signOut();
		
		//14.Login as INSTRUCTOR 
		tegrity.loginCourses("User1");
		
		//15.Click on preconditional course from the courses list.
		course.selectCourseThatStartingWith("Ab");
		
		for(int number_of_recording = 0; number_of_recording < 2; number_of_recording++) {
				
			if(type_of_user == 1) {
				record.clickOnStudentRecordingsTab();
			}
			
			//16.Check one available recording checkbox. 
			if(number_of_recording == 0){
				record.getTheElementAndClick("Checkbox1");
			}else {
				record.unselectIndexCheckBox(1);
				record.getTheElementAndClick("Checkbox2");
			}
			//17.Click on the "Copy" button from the "Recording Tasks" drop-down list item. 
			record.clickOnRecordingTaskThenCopy();
				
			//18.The "Copy" window appeared.
			copy.waitForPageToLoad();
			copy.verifyThatCopyMenuOpen();
			
			//19.Select other course that does not contain the recording.
			copy.selectTargetCourseFromCourseListThatStartWith("abc");
		
			//19.Click on the "Copy Recording(s)" button.
			copy.clickOnCopyButton();
		
			//20.click on ok button
			confirm_menu.clickOnOkButton();
			
			//20. wait until the record finish coping to the course abc
			record.checkStatusExistenceForMaxTTime(220);
		
			//21.return to course page
			record.returnToCourseListPage();
	
			//22.Click on the copied recording course. 
			course.selectCourseThatStartingWith("abc");
			
			if(type_of_user == 1) {
				record.clickOnStudentRecordingsTab();
			}
	
			//23.Validate that the recording and the tags are appeared.
			//24.The recording is appeared.
			//25.The tag names are appeared.
			if(number_of_recording == 0){
				record.verifyThatTargetRecordingExistInRecordingList(firstRecordingName);
				record.verifyTagApperedUderTheSelectRecording(firstValidName);
			} else {
				record.verifyThatTargetRecordingExistInRecordingList(secondRecordingName);
				record.verifyTagApperedUderTheSelectRecording(SecondVaildName);
			}
			
			
		
			//26.return to course page
			record.returnToCourseListPage();
			
			//27.Click on certain course from the courses list.
			course.selectCourseThatStartingWith("Ab");
			}
		}		
				
		for(int type_of_user = 0; type_of_user < 2; type_of_user++) {
						
		if(type_of_user == 1) {
			record.clickOnStudentRecordingsTab();
		}
		
		//28.Check one available recording checkbox. 
		record.getTheElementAndClick("Checkbox1");
					
		//29.Click the "Recording Tasks" drop-down menu and choose the "Tag" option
		record.clickOnRecordingTaskThenTag();
				
		//30.The "Tag" Dialog window is appeared.
		tag_window.waitForPageToLoad();
		tag_window.verifyTagWindowOpen();
			
		//31.delete all the tag for the next test
		tag_window.deleteAllExistingTags();
							
		}		
	
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
	
	}

}