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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC8062ValidateEditingPublicTag {

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
	public ConfirmationMenu confirm_menu;
	public CopyMenu copy;
	DesiredCapabilities capability;
	String validNewName;
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

		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC8062ValidateEditingPublicTag at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC8062ValidateEditingPublicTag at " + DateToStr, "Starting the test: TC8062ValidateEditingPublicTag at " + DateToStr, LogAs.PASSED, null);	
	}

	@AfterClass
	public void closeBroswer() {
		this.driver.quit();
	}
		
	// @Parameters({"web","title"}) in the future
	@Test (description="TC8062 Validate editing public tag")
	public void test8062() throws InterruptedException {
		
		//1.Enter the university
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);	
			
		//*+preconditions:+*Recordings in course with several tags is required for this test.	
		//2.Login as INSTRUCTOR 
		tegrity.loginCourses("User1");
		
		//3.Click on certain course from the courses list.
		course.selectCourseThatStartingWith("Ab");
		
		//0 - for regular recording ,1- for student recording 
		for(int type_of_user = 0; type_of_user < 2; type_of_user++) {
					
			if(type_of_user == 1){
				record.clickOnStudentRecordingsTab();
			}
			
		//4.Check one available recording checkbox. 
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
		
		//5.Click the "Recording Tasks" drop-down menu and choose the "Tag" option
		record.clickOnRecordingTaskThenTag();
	
		//6.The "Tag" Dialog window is appeared.
		tag_window.waitForPageToLoad();
		
		//7.create tags for the test	
		if(type_of_user == 0){
			validNewName = "Test";
		}else if (type_of_user == 1) {
			validNewName = "Exam";			
		}
		tag_window.createNewTag(validNewName);	
		//8.Click on the "Apply" button
		record.clickElementJS(tag_window.apply_button);
		
		//9. sign out
		record.signOut();
			
		//10.Login as INSTRUCTOR 
		tegrity.loginCourses("User1");
	
		//11.Click on preconditional course from the courses list.
		course.selectCourseThatStartingWith("Ab");
		
		if(type_of_user == 1){
			record.clickOnStudentRecordingsTab();
		}
						
		//12.Check one available recording checkbox. 
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
					
		//13.Click the "Recording Tasks" drop-down menu and choose the "Tag" option
		record.clickOnRecordingTaskThenTag();
				
		//14.The "Tag" Dialog window is appeared.
		tag_window.waitForPageToLoad();
		tag_window.verifyTagWindowOpen();
					
		//15.Click on the "Edit Tag" icon.
		if(type_of_user ==0){
		    validNewName = "Tegrity";
		} else if(type_of_user ==1) {
			validNewName = "Mik";
		}	
		tag_window.clickElement(tag_window.edit_tag_button_list.get(0));
					
		//16.The "Edit Tag" window is appeared.
		tag_window.verifyTagEditWindowOpen();
					
		//17.Enter some valid new name.
		tag_window.sendStringwithAction(tag_window.edit_new_tag_input, validNewName);
					
		//18.Click on the "Submit" button.
		tag_window.clickElementWithOutIdJS(tag_window.submit_edit_button);
					
		//19.The created name is displayed in the "Edit Tag" list.
		tag_window.verifyCreatedNameIsDisplayedInTheEditTagList(validNewName);
					
		//20.Validate the edited tag checkbox is checked.
		tag_window.verifyAllTheTagCheckboxesAreChecked();
					
		//21.Click on the "Apply" button
		record.clickElementJS(tag_window.apply_button);
					
		//22.The "Tag Dialog" is disappeared.
		tag_window.verifyTagWindowClose();
					
		//23.The modified tag is appeared under the selected recordings.
		record.verifyTagApperedUderTheSelectRecordings();
					
		//24.The tags in the "Tag Indicator" are sorted by the name.
		record.validateTheSeveralTaggedRecordingTagsAreSortedByName();
					
		//25.sign out
		record.signOut();
					
		//26.Login as other enrolled to the preconditional INSTRUCTOR
		tegrity.loginCourses("User2");
						
		//26.Click on certain course from the courses list.
		course.selectCourseThatStartingWith("Ab");
					
		if(type_of_user == 1) {
			record.clickOnStudentRecordingsTab();
		}
		
		//27. verify that we can see the new tag name			
		record.verifyTagApperedUderTheSelectRecording(validNewName);
					
		//28. sign out
		record.signOut();
					
		//29.Login as INSTRUCTOR 
		tegrity.loginCourses("User1");
		
		//30.Click on preconditional course from the courses list.
		course.selectCourseThatStartingWith("Ab");
				
		if(type_of_user == 1){
			record.clickOnStudentRecordingsTab();
		}
								
		//31.Check one available recording checkbox. 
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
		
		//32.Click the "Recording Tasks" drop-down menu and choose the "Tag" option
		record.clickOnRecordingTaskThenTag();
						
		//33.The "Tag" Dialog window is appeared.
		tag_window.waitForPageToLoad();
		tag_window.verifyTagWindowOpen();
		
		//34. delete all the existing tags
		tag_window.deleteAllExistingTags();
					
		//35.click on the cancel button
		tag_window.clickElementJS(tag_window.cancel_button);
		
		}
		
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
	
	}

}