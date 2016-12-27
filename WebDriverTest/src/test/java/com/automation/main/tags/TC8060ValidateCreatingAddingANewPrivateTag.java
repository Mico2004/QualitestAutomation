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
public class TC8060ValidateCreatingAddingANewPrivateTag {

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
		 System.out.println("Starting the test: TC8060ValidateCreatingAddingANewPrivateTag at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC8060ValidateCreatingAddingANewPrivateTag at " + DateToStr, "Starting the test: TC8060ValidateCreatingAddingANewPrivateTag at " + DateToStr, LogAs.PASSED, null);	
	}

	@AfterClass
	public void closeBroswer() {
		this.driver.quit();
	}
		
	// @Parameters({"web","title"}) in the future
	@Test (description="TC8060 Validate creating/adding a new private tag")
	public void test8060() throws InterruptedException {
		
		//1.Enter the university
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);	
		
		//2.Login as INSTRUCTOR 
		tegrity.loginCourses("User1");
		
		//3.Click on certain course from the courses list.
		course.selectCourseThatStartingWith("Ab");
		String url =  course.getCurrentUrlCoursePage(); 
		
		//*Repeat the steps for student recordings*
		for(int type_of_tab = 0; type_of_tab < 2; type_of_tab++) {
			
			if(type_of_tab == 1){
				record.clickOnStudentRecordingsTab();
			}
			
		//4.Check one available recording checkbox. 
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
		
		// repeat steps 6-12 
		for(int numberOfTag = 0 ;numberOfTag < 2 ; numberOfTag++ ){
		
			//5.Click the "Recording Tasks" drop-down menu and choose the "Tag" option
			record.clickOnRecordingTaskThenTag();
		
			//6.The "Tag" Dialog window is appeared.
			tag_window.waitForPageToLoad();
			tag_window.verifyTagWindowOpen();
		
			if(numberOfTag == 0) {
				validNewName = "Test";
			} else {
				validNewName = "Example";
			}
			//7.Click on the "Create New Tag" Button.
			tag_window.clickElementJS(tag_window.create_new_tag_button);
			
			//8.Enter Tag name that more than 20 characters long.
			tag_window.sendStringwithAction(tag_window.edit_new_tag_input, validNewName);	
			
			//9.Check the "Private" checkbox.
			record.SelectOneCheckBoxOrVerifyAlreadySelected(tag_window.privte_checkbox);
			
			//10.Click on the "Submit" button.
			tag_window.clickElementWithOutIdJS(tag_window.submit_edit_button);
			
			//11.The created name is displayed in the "Edit Tag" list.
			tag_window.verifyCreatedNameIsDisplayedInTheEditTagList(validNewName);
			
			//12.Validate the created tag checkbox is checked.
			tag_window.verifyAllTheTagCheckboxesAreChecked();

			//14.Click on the "Apply" button
			record.clickElementJS(tag_window.apply_button);
				
			//15.The "Tag Dialog" is disappeared.
			tag_window.verifyTagWindowClose();
		
			//16.The tag is appeared under the selected recordings.
			record.verifyTagApperedUderTheSelectRecordings();
		
			//17.The tags in the "Tag Indicator" are sorted by the name.
			record.validateTheSeveralTaggedRecordingTagsAreSortedByName();
		}
		
		
		//18.Click the "Recording Tasks" drop-down menu and choose the "Tag" option
		record.clickOnRecordingTaskThenTag();
	
		//19.The "Tag" Dialog window is appeared.
		tag_window.waitForPageToLoad();
		tag_window.verifyTagWindowOpen();
		
		//20.Click on the "Create New Tag" Button.
		tag_window.clickElementJS(tag_window.create_new_tag_button);
		
		//21.Enter Tag name that more than 20 characters long.
		tag_window.sendStringwithAction(tag_window.edit_new_tag_input, validNewName);	
		
		//22.Check the "Private" checkbox.
		record.SelectOneCheckBoxOrVerifyAlreadySelected(tag_window.privte_checkbox);
		
		//23.Click on the "Submit" button.
		tag_window.clickElementWithOutIdJS(tag_window.submit_edit_button);
		
		//24.The "Cannot add tag with the same name" error message is displayed				
		confirm_menu.clickOnOkButtonOnErrorTagWindow("Cannot add tag with the same name");
		
		//25.Enter Tag name that more than 20 characters long.
		validNewName= "Tegrity";
		tag_window.sendStringwithAction(tag_window.edit_new_tag_input, validNewName);	
		
		//26.Click on the "Submit" button.
		tag_window.clickElementWithOutIdJS(tag_window.submit_edit_button);
		
		//27.Validate the created tag checkbox is checked.
		tag_window.verifyAllTheTagCheckboxesAreChecked();
		
		//11.The created name is displayed in the "Edit Tag" list.
		tag_window.verifyCreatedNameIsDisplayedInTheEditTagList(validNewName);
		
		//28.Click on the "Apply" button
		record.clickElementJS(tag_window.apply_button);
			
		//29.The "Tag Dialog" is disappeared.
		tag_window.verifyTagWindowClose();
	
		//30. The tag is appeared under the selected recordings.
		record.verifyTagApperedUderTheSelectRecordings();
	
		//31.The tags in the "Tag Indicator" are sorted by the name.
		record.validateTheSeveralTaggedRecordingTagsAreSortedByName();
			
		//32. sign out
		record.signOut();
		
		//0 -  Login as another INSTRUCTOR ,1 - Login as a STUDENT ,2 - Login as Full Admin
		for(int type_of_user = 0; type_of_user < 3; type_of_user++) {
			
			if(type_of_user == 0){
				//33. Login as another INSTRUCTOR
				tegrity.loginCourses("User2");
			} else if ((type_of_user == 1)){
				//33. Login as a STUDENT
				tegrity.loginCourses("User4");
			} else {
				//33. Login as Full Admin
				tegrity.loginAdmin("Admin");
			}
			if(type_of_user < 2) {
				//34.Click on certain course from the courses list.
				course.selectCourseThatStartingWith("Ab");
			
			} else {
				//34. Click on "view course list" under "courses" section.
				admin_dash_board_page.clickOnTargetSubmenuCourses("View Course List");	
				// In "All courses" page, search for Ab course.
				admin_dashboard_view_course_list.waitForThePageToLoad();
				admin_dashboard_view_course_list.moveToCoursesThroughGet(url);
			}
			
			if(type_of_tab == 1){
				record.clickOnStudentRecordingsTab();
			}
	
			//35. The tag is appeared under the selected recordings.
			record.verifyTagNotAppearedUderTheSelectRecordings();
		
			//36.The tags are displayed in the "View" dropdown list
			record.moveToElementAndPerform(record.view_tasks_button,driver);
			record.verifyTheTagsAreNotDisplayInTheViewDropdown();
						
			//37. signout
			record.signOut();
		  }
		
			//post test - delete all the tags
			//38.Login as INSTRUCTOR 
			tegrity.loginCourses("User1");
				
			//39.Click on certain course from the courses list.
			course.selectCourseThatStartingWith("Ab");
						
			//40.Check one available recording checkbox. 
			record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
						
			//41.Click the "Recording Tasks" drop-down menu and choose the "Tag" option
			record.clickOnRecordingTaskThenTag();
				
			//42.The "Tag" Dialog window is appeared.
			tag_window.waitForPageToLoad();
			tag_window.verifyTagWindowOpen();
		
			//43. delete all the existing tags
			tag_window.deleteAllExistingTags();
			
			//44.click on the cancel button
			tag_window.clickElementJS(tag_window.cancel_button);
		
		}
		
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
	
	}
}