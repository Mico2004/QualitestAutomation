package com.automation.main.tags;

import org.openqa.selenium.By;
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
public class TC7990ValidateTagMenuItemUI {

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
    
	

	@BeforeClass
	public void setup() {

			driver = DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());
			calendarPage = PageFactory.initElements(driver, CalendarPage.class);
			admin_dash_board_page = PageFactory.initElements(driver, AdminDashboardPage.class);
			admin_dashboard_view_course_list = PageFactory.initElements(driver, AdminDashboardViewCourseList.class);
			tegrity = PageFactory.initElements(driver, LoginHelperPage.class);
			tag_window= PageFactory.initElements(driver, TagMenu.class);
			record = PageFactory.initElements(driver, RecordingHelperPage.class);
			copy = PageFactory.initElements(driver, CopyMenu.class);

		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC7990ValidateTagMenuItemUI at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC7990ValidateTagMenuItemUI at " + DateToStr, "Starting the test: TC7990ValidateTagMenuItemUI at " + DateToStr, LogAs.PASSED, null);	
	}

	@AfterClass
	public void closeBroswer() {
		this.driver.quit();
	}
		
	
	// @Parameters({"web","title"}) in the future
	@Test (description="TC7990 Validate tag menu item UI")
	public void test7990() throws InterruptedException, ParseException {
		
		
		//1.log in courses page
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);	
		tegrity.loginCourses("User1");
		initializeCourseObject();
		
		//2.open some course whom listed you as instructor
		course.selectCourseThatStartingWith("Ab");
			
		for(int type_of_user = 0; type_of_user < 2; type_of_user++) {
			
			if(type_of_user == 1){
				record.clickOnStudentRecordingsTab();
			}
		
		//3.check several recordings respective checkboxes
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);	
					
		//4.click on the recording tasks drop_down button
		record.mouseHoverJScript(record.recording_tasks_button);
		
		//5.click on the recording tasks drop_down button
		record.mouseHoverJScript(record.tag_button);
		 
		//6.the "Tag" hint is displayed
		record.verifyThatWeHaveHintToWebElement(record.tag_button,"Tag");
		
		//7.Uncheck all of the recordings checkboxs.
		record.unselectallCheckbox();
		
		//8.click on the recording tasks drop_down button
		record.mouseHoverJScript(record.recording_tasks_button);
				
		//9. verify that the tag button option is greyed out and disable
		record.verifyRecordingMenuColor(record.tag_button);

		//10.check several recordings respective checkboxes
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
		
		//11.Click the "Recording Tasks" drop-down menu and choose the "Tag" option
		record.clickOnRecordingTaskThenTag();
			
		//12.The "Tag" Dialog window is appeared.
		tag_window.waitForPageToLoad();
		tag_window.verifyTagWindowOpen();
	
		//13. The color of the Top Panel is configurable in the "Customize User Interface" window in Admin Dashboard.
		tag_window.verifyTagColor(record);
		
		//we need to create tag to verify that checkbox and all other things
		tag_window.createNewTag("Exmple");
		
		//14.The "Tags List" is displayed and contain the list of tags.
		tag_window.verifyWebElementDisplayed(tag_window.tableOfTags, "Tags List");
		
		//15.The "Header" Checkbox is displayed.
		tag_window.verifyWebElementDisplayed(tag_window.header_checkbox, "Header Checkbox");
						
		//16.The "Tag" Checkbox is displayed.
		tag_window.verifyWebElementDisplayed(tag_window.first_tag_checkbox, "Tag Checkbox");
			
		//17.The "Private" Checkbox is displayed.
		tag_window.verifyWebElementDisplayed(tag_window.first_private_checkbox, "Private Checkbox");
					
		//18.The "Edit" Tag icon is displayed.
		tag_window.verifyWebElementDisplayed(tag_window.edit_tag_button_list.get(0), "Edit button");	
		
		//19.The "Delete" Tag icon is displayed.
		tag_window.verifyWebElementDisplayed(tag_window.delete_tag_button_list.get(0), "Delete button");	
		
		//20.The "Create New Tag" Button is displayed.
		tag_window.verifyWebElementDisplayed(tag_window.create_new_tag_button, "Create_new_tag_button");	
		
		//21.The "Cancel" Button is displayed.
		tag_window.verifyWebElementDisplayed(tag_window.cancel_button, "Cancel_button");	
		
		//22.The "Apply" Button is displayed.
		tag_window.verifyWebElementDisplayed(tag_window.apply_button, "Apply_button");	
		
		//we need to create tag to verify that "Tags List" rows is sorted by tag name.
		tag_window.createNewTag("test");
		
		//23.Validate the "Tags List" rows is sorted by tag name.
		tag_window.validateTheTagsListRowsIsSortedByTagName();
		
		//24. Check the "Header" checkbox.
		record.SelectOneCheckBoxOrVerifyAlreadySelected(tag_window.header_checkbox);
		tag_window.verifyAllTheTagCheckboxesAreChecked();

		//25. unCheck the "Header" checkbox.
		record.SelectOneCheckBoxOrVerifyAlreadySelected(tag_window.header_checkbox);
		tag_window.verifyAllTheTagCheckboxesAreUnChecked();
			
		//10.The text "When would you like the selected recordings to be available?" shall be displayed below.
		publish_window.verifyInfomativeTextAndVerifyBelowTheOtherInfoText();
		
		//11.The list of available options shall be displayed next.
		publish_window.verifyThatTheRadioButtonsWillDisplayBelow();
		
		//12.The "Save" button is displayed at the bottom right of the window.
		//13.The "Cancel" button is displayed left to the "Save" button.
		publish_window.VerifyTheLocationOfTheSaveAndCancel();
		
		//14.Validate the content of the available publishing options
		publish_window.VerifyTheContentOfTheAvailablePublishingOptions();
		
		//15.Click on the "From" text box
		//16. The current month is presented - year-month in the format of (xxxx)-(yyy)
		publish_window.verifyThatAfterClickingOnTheFromTheCalenderWidgetIsDisplayed(publish_window.start,publish_window.calenderStart);
		calendarPage.verifyThatFormatOfTheMonthAndYear(publish_window.titleOfCalenderStart);
		
		//15.Click on the "From" text box
		//16. The current month is presented - year-month in the format of (xxxx)-(yyy)
		publish_window.verifyThatAfterClickingOnTheFromTheCalenderWidgetIsDisplayed(publish_window.end,publish_window.calenderEnd);
		calendarPage.verifyThatFormatOfTheMonthAndYear(publish_window.titleOfCalenderEnd);
		publish_window.clickOnCancelButton();
		}
		
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
	
	}

	// description = "get courses list"
	public void initializeCourseObject() throws InterruptedException {

		course = PageFactory.initElements(driver, CoursesHelperPage.class);
		course.courses = course.getCoursesListFromElement(course.course_list);
	}
}