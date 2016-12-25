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
	String firstTag;
	

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
		
		//login as INSTRUCTOR
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
		tag_window.verifyTagColor(record,tag_window.tag_window_title_background);
		
		//we need to create tag to verify that checkbox and all other things
		tag_window.createNewTag("Example");
		
		//14.The "Tags List" is displayed and contain the list of tags.
		tag_window.verifyWebElementDisplayed(tag_window.tableOfTags, "Tags List");
		
		//15.The "Header" Checkbox is displayed.
		tag_window.verifyWebElementDisplayed(tag_window.header_checkbox.get(1), "Header Checkbox");
						
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
		tag_window.createNewTag("Test");
		tag_window.saveAllTheInstractors();
		
		//23.Validate the "Tags List" rows is sorted by tag name.
		tag_window.validateTheTagsListRowsIsSortedByTagName();
		
		//24. Check the "Header" checkbox.
		record.SelectOneCheckBoxOrVerifyAlreadySelected(tag_window.header_checkbox.get(1));
		tag_window.verifyAllTheTagCheckboxesAreChecked();

		//25. Uncheck the "Header" checkbox.
		record.unClickOneCheckBoxOrVerifyNotSelected(tag_window.header_checkbox.get(1));
		tag_window.verifyAllTheTagCheckboxesAreUnChecked();
		
		//26.Check all the "Tag" checkboxes.
		tag_window.CheckAllTheTagCheckboxes();
		tag_window.verifyTheHeaderCheckboxIsChecked();
			
		//27.unCheck all the "Tag" checkboxes.
		tag_window.unCheckAllTheTagCheckboxes();
		tag_window.verifyTheHeaderCheckboxIsUnChecked();
		
		//28.Validate the unchecked "Tag" checkbox is with white background.
		tag_window.verifyTheTableColor("White");
		
		//29.Hover over unchecked "Tag" checkbox.
		tag_window.moveToElementAndPerform(tag_window.first_name, driver);
		tag_window.verifyTheTableColor("Black");
		
		//30.Validate Checked "Tag" checkbox is with grey background.
		tag_window.CheckAllTheTagCheckboxes();
		tag_window.verifyTheTableColor("Grey");
		
		//31.Hover over "Edit" icon. 
		record.moveToElementAndPerform(tag_window.edit_tag_button_list.get(0), driver);
		 
		//32.the "Edit" hint is displayed
		record.verifyThatWeHaveHintToWebElement(tag_window.edit_tag_button_list.get(0),"Edit selected row");
		
		//33.Hover over "Delete" icon. 
		record.moveToElementAndPerform(tag_window.delete_tag_button_list.get(0), driver);
				 
		//34.the "Delete" hint is displayed
		record.verifyThatWeHaveHintToWebElement(tag_window.delete_tag_button_list.get(0),"Delete selected row");
		
		//35.Hover over "Create Tag" icon. 
		record.moveToElementAndPerform(tag_window.create_new_tag_button, driver);
				 
		//36.the "Create Tag" hint is displayed
		record.verifyThatWeHaveHintToWebElement(tag_window.create_new_tag_button,"Create New Tag");
		
		//37.Hover over "Cancel" icon. 
		record.moveToElementAndPerform(tag_window.cancel_button, driver);
				 
		//38.the "Cancel" hint is displayed
		record.verifyThatWeHaveHintToWebElement(tag_window.cancel_button,"Cancel");
		
		//39.Hover over "Apply" icon. 
		record.moveToElementAndPerform(tag_window.apply_button, driver);
						 
		//40.the "Apply" hint is displayed
		record.verifyThatWeHaveHintToWebElement(tag_window.apply_button,"Apply");
		
		//41.click on the apply button
		tag_window.clickElementJS(tag_window.apply_button);
		
		//42.Hover over the "View" drop-down list menu.
		record.moveToElementAndPerform(record.view_tasks_button,driver);
				
		//43.The "Tags Filter" header is displayed.
		record.verifyWebElementDisplayed(record.view_menu_tags_text, "Tags Filter");	
		
		//44. The "(Show all recording)" chceckbox is displayed.
		record.verifyWebElementDisplayed(record.showAllRecordings,"Show all recording");
		
		//45. The "Tag Filtering" checkboxs" are displayed.
		record.verifyWebElementDisplayed(record.first_tag,"Tag Filtering");
		
		//46.Check the "(Show all recordings)" checkbox.
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.showAllRecordings);
		
		//47. All the tags checkboxes are unchecked. 
		record.veirfyThatTheCheckboxIsNotSelect(record.checkboxs_tags.get(0));
		
		//48.Check any tag checkbox.
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkboxs_tags.get(0));
		
		//49.The "(Show all recordings)" checkbox is unchecked.
		record.veirfyThatTheCheckboxIsNotSelect(record.showAllRecordings);
			
		//Check the "(Show all recordings)" checkbox. for later
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.showAllRecordings);
		
		//50.Check one available recording checkbox. 
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
		
		//51.Click the "Recording Tasks" drop-down menu and choose the "Tag" option
		record.clickOnRecordingTaskThenTag();
					
		//52.The "Tag" Dialog window is appeared.
		tag_window.waitForPageToLoad();
		tag_window.verifyTagWindowOpen();
		
		//53.Click on the "Edit Tag" icon/ Click on the "Create New Tag" Button.
		for(int click_on = 0; click_on < 2; click_on++) {
	
			if(click_on == 0){
				firstTag = tag_window.getFirstTagName();
				tag_window.clickElement(tag_window.edit_tag_button_list.get(0));
			} else{
				firstTag = "";
				tag_window.clickElement(tag_window.create_new_tag_button);
			}
		
		//54.The color of the TopPanel is configurable in the "Customize User Interface" window in Admin Dashboard
		tag_window.waitForEditPageToLoad();
		tag_window.verifyTagEditWindowOpen();
		tag_window.verifyTheTableColor("Edit_Grey");
		
		//55.The Tag textbox is displayed.
		record.verifyWebElementDisplayed(tag_window.edit_new_tag_input, "Tag textbox");
		
		//56.The Tag textbox is editable and contains the tag name.
		tag_window.verifyEditTextboxEditableAndContainsTheTagName(firstTag);
		
		if(type_of_user == 0){
			//57.The "Private" checkbox is displayed and checkable.
			tag_window.verifyWebElementDisplayed(tag_window.privte_checkbox,"Private checkbox");
			tag_window.verifyWebElementDisplayed(tag_window.private_edit_label,"Private label checkbox");
			record.verifyWebElementisCheckable(tag_window.privte_checkbox);
		}
		
		//58. The "Private" checkbox is chackable and unchecked by default.
		record.veirfyThatTheCheckboxIsNotSelect(tag_window.privte_checkbox);
		
		//59. The "Cancel" button is displayed.
		tag_window.verifyWebElementDisplayed(tag_window.cancel_edit_button,"cancel edit button");
		
		//60.The "Cancel" button color is grey.
		record.verifyColorButton(tag_window.getBackGroundImageColor(tag_window.cancel_edit_button));
		
		//62.the "Cancel" hint is displayed
		record.verifyThatWeHaveHintToWebElement(tag_window.cancel_edit_button,"Cancel");
		
		//61. The "Submit" button is displayed.
		tag_window.verifyWebElementDisplayed(tag_window.submit_edit_button,"Submit edit button");
		
		//62. The "Submit "button color is blue.
		copy.verifyBlueColor(tag_window.getBackGroundImageColor(tag_window.submit_edit_button)); 
		
		//62.the "Submit" hint is displayed
		record.verifyThatWeHaveHintToWebElement(tag_window.submit_edit_button,"Submit");
			
		//63. Click on the "Cancel" button.
		tag_window.clickElement(tag_window.cancel_edit_button);
		
		}
		
		//63. Click on the "apply" button.
		tag_window.clickElement(tag_window.cancel_button);
		
		//64.The "Tag Dialog" window is disappeared.
		tag_window.isTagWindowClosed();
		 
		//65.Check recordings with tags checkboxes
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
		
		//66.Click on the "Tag" in the "Recording Tasks" drop-down list menu item.
		record.clickOnRecordingTaskThenTag();
								
		//67.The "Tag" Dialog window is appeared.
		tag_window.waitForPageToLoad();
		tag_window.verifyTagWindowOpen();
		
		//68. All the tags checkboxes are unchecked.
		tag_window.verifyAllTheTagCheckboxesAreChecked();
		
		//69. All the INSTRUCTOR owned tags is displayed.
		tag_window.verifyAllInstractorAreDisplay();
		
		//70. Click on the "apply" button.
		tag_window.clickElement(tag_window.cancel_button);
		
		//71.Check recordings without tags checkboxes.
		record.unselectallCheckbox();
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox2);
		 
		//66.Click on the "Tag" in the "Recording Tasks" drop-down list menu item.
		record.clickOnRecordingTaskThenTag();
						
		//67.The "Tag" Dialog window is appeared.
		tag_window.waitForPageToLoad();
		tag_window.verifyTagWindowOpen();
		
		//68. All the tags checkboxes are unchecked.
		tag_window.verifyAllTheTagCheckboxesAreUnChecked();
		
		//69. All the INSTRUCTOR owned tags is displayed.
		tag_window.verifyAllInstractorAreDisplay();
			
		//70.Click on the "Cancel" button.
		tag_window.clickElement(tag_window.cancel_button);
		
		//71.The "Tag Dialog" window is disappeared.
		tag_window.verifyTagWindowClose();
		
		//72.Check at least one tagged recordings and at least one not tagged recordings checkboxes.
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
		
		//73.Click on the "Tag" in the "Recording Tasks" drop-down list menu item.
		record.clickOnRecordingTaskThenTag();
								
		//74.The "Tag" Dialog window is appeared.
		tag_window.waitForPageToLoad();
		tag_window.verifyTagWindowOpen();
			
		//75. The selected recording tags checkbox are in undefined state.
		tag_window.verifyCheckboxInUndefinedState();
		
		//76. All the INSTRUCTOR owned tags is displayed.
		tag_window.verifyAllInstractorAreDisplay();
		
		//delete the tag for later use
		tag_window.deleteAllExistingTags();
		
		//77.Click on the "Cancel" button.
		tag_window.clickElement(tag_window.cancel_button);
				
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