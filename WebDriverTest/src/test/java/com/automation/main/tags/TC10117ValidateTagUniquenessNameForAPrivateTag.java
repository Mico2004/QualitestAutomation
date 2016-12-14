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
public class TC10117ValidateTagUniquenessNameForAPrivateTag {

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
			delete_menu = PageFactory.initElements(driver, DeleteMenu.class);
			
		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC10117ValidateTagUniquenessNameForAPrivateTag at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC10117ValidateTagUniquenessNameForAPrivateTag at " + DateToStr, "Starting the test: TC10117ValidateTagUniquenessNameForAPrivateTag at " + DateToStr, LogAs.PASSED, null);	
	}

	@AfterClass
	public void closeBroswer() {
		this.driver.quit();
	}
		
	// @Parameters({"web","title"}) in the future
	@Test (description="TC10117 Validate tag uniqueness name for a private tag")
	public void test10117() throws InterruptedException {
		
		//1.Enter the university
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);	
		
		//2.Login as INSTRUCTOR 
		tegrity.loginCourses("User1");
		
		//3.Click on certain course from the courses list.
		course.selectCourseThatStartingWith("Ab");
				
		//4.Check one available recording checkbox. 
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
				
		//5.Click the "Recording Tasks" drop-down menu and choose the "Tag" option
		record.clickOnRecordingTaskThenTag();
		
		//5.The "Tag" Dialog window is appeared.
		tag_window.waitForPageToLoad();
		tag_window.verifyTagWindowOpen();
		
		//6.Click on the "Create New Tag" Button.
		tag_window.clickElementJS(tag_window.create_new_tag_button);
		
		//7.Make sure the "Private" checkbox is unchecked
		record.unClickOneCheckBoxOrVerifyNotSelected(tag_window.privte_checkbox);
			
		//8.Enter name that contains only lower-case characters.
		String nameToSubmit = "shahar";
		tag_window.sendStringwithAction(tag_window.edit_new_tag_input, nameToSubmit);	
		
		//9.Click on the "Submit" button.
		tag_window.clickElementWithOutIdJS(tag_window.submit_edit_button);
		
		//10.The created name is displayed in the "Edit Tag" list.
		tag_window.verifyCreatedNameIsDisplayedInTheEditTagList(nameToSubmit);
			
		//11.Click on the "Create New Tag" Button.
		tag_window.clickElementJS(tag_window.create_new_tag_button);
							
		//12.Enter the same name as before but this time the name contains only upper-case characters.
		nameToSubmit = "SHAHAR";
		tag_window.sendStringwithAction(tag_window.edit_new_tag_input, nameToSubmit);	
				
		//13.Click on the "Submit" button.
		tag_window.clickElementWithOutIdJS(tag_window.submit_edit_button);
		
		//14.The created name is displayed in the "Edit Tag" list.
		tag_window.verifyCreatedNameIsDisplayedInTheEditTagList(nameToSubmit);
		
		//15.Click on the "Create New Tag" Button.
		tag_window.clickElementJS(tag_window.create_new_tag_button);
		
		//16.Make sure the "Private" checkbox is unchecked
		record.unClickOneCheckBoxOrVerifyNotSelected(tag_window.privte_checkbox);
		
		//17.Enter the same name.
		tag_window.sendStringwithAction(tag_window.edit_new_tag_input, nameToSubmit);
		
		//18.Click on the "Submit" button.
		tag_window.clickElementWithOutIdJS(tag_window.submit_edit_button);
				
		//19.The "Cannot add tag with the same name" error message is displayed				
		confirm_menu.clickOnOkButtonOnErrorTagWindow("Cannot add tag with the same name");
		
		//20.Enter the same name.
		tag_window.sendStringwithAction(tag_window.edit_new_tag_input, nameToSubmit);	
				
		//21.Check the "Private" checkbox.
		record.SelectOneCheckBoxOrVerifyAlreadySelected(tag_window.privte_checkbox);		
		
		//22.Click on the "Submit" button.
		tag_window.clickElementWithOutIdJS(tag_window.submit_edit_button);
		
		//23.The "Cannot add tag with the same name" error message is displayed				
		confirm_menu.clickOnOkButtonOnErrorTagWindow("Cannot add tag with the same name");
		
		//24.Click on the "Cancel" button.
		tag_window.clickElementWithOutIdJS(tag_window.cancel_edit_button);
			
		//45. delete all the existing tags
		tag_window.deleteAllExistingTags();
		
		//26.Click on the "Cancel edit" button.
		tag_window.clickElementWithOutIdJS(tag_window.cancel_edit_button);
		
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
			
			}

}