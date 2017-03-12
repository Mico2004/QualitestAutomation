package com.automation.main.help_get_support;

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
import com.automation.main.page_helpers.CopyMenu;
import com.automation.main.page_helpers.CoursesHelperPage;
import com.automation.main.page_helpers.EditRecordingPropertiesWindow;
import com.automation.main.page_helpers.GetSupprtWindow;
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
public class TC6512VerifyTheGetSupportUI {

	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}
	
	public PublishWindow publish_window;
	public AdminDashboardViewCourseList admin_dashboard_view_course_list;
	public LoginHelperPage tegrity;
	public CoursesHelperPage course;
	public RecordingHelperPage record;
	public GetSupprtWindow get_support_window;
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
			admin_dash_board_page = PageFactory.initElements(driver, AdminDashboardPage.class);
			admin_dashboard_view_course_list = PageFactory.initElements(driver, AdminDashboardViewCourseList.class);
			tegrity = PageFactory.initElements(driver, LoginHelperPage.class);
			edit_recording_properties_window = PageFactory.initElements(driver, EditRecordingPropertiesWindow.class);
			get_support_window = PageFactory.initElements(driver, GetSupprtWindow.class);
			record = PageFactory.initElements(driver, RecordingHelperPage.class);
			copy = PageFactory.initElements(driver, CopyMenu.class);
			course = PageFactory.initElements(driver, CoursesHelperPage.class);
			
		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC6512VerifyTheGetSupportUI at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC6512VerifyTheGetSupportUI at " + DateToStr, "Starting the test: TC6512VerifyTheGetSupportUI at " + DateToStr, LogAs.PASSED, null);	
	}

	@AfterClass
	public void closeBroswer() {
		this.driver.quit();
	}
		
	
	// @Parameters({"web","title"}) in the future
	@Test (description="TC6512 Verify the Get Support UI")
	public void test6512() throws InterruptedException, ParseException {
		
		//1.log in courses page
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);	
		
		//2.login as INSTRUCTOR
		tegrity.loginCourses("User1");
	
		//3.Hover over the "Help" link
		course.moveToElementAndPerform(course.help, driver);
			
		//4.Click the "Get Support" link
		course.clickElementJS(course.get_support);
		
		//5.Verify that "Get Support" form is displayed as described:
		// User email address field. If logged user has email, then this field use it as default value. Otherwise field has placeholder value "Email address".
		get_support_window.verifySupportWindow();
		get_support_window.verifyPlaceHolderIsDisplay(get_support_window.from_email_field, "E-mail address");
		
		//6.User name field. Used name of the logged user as default value.
		get_support_window.verifyThatTheTextOfWebElemenetIsAsExpected(course.user_name, get_support_window.from_name_field.getAttribute("value"));
		
		//7.Support email address field -  Support email address field is read only.
		get_support_window.verifyThatTheElementIsReadOnly(get_support_window.to_email_field);
		
		//8.Support email address field has the value of the helpdesk email or a placeholder text of "Email address".
		get_support_window.verifyThatTheEmailAdressFieldHelpDeskOrPlaceHolder("helpdesk@mheducation.com");
		
		//9. Support title field- Support title field is read only.
		get_support_window.verifyThatTheElementIsReadOnly(get_support_window.to_name_field);
		
		//10.Support title field has default value "Institution Help Desk"
		get_support_window.verifyThatTheSupportEmailHasDefultValue();
		
		//11.Subject field - Subject field is editable.
		get_support_window.verifyThatThisElementIsEditable(get_support_window.subject_field);
		
		//12.Subject field has placeholder value "Subject..."
		get_support_window.verifyPlaceHolderIsDisplay(get_support_window.subject_field, "Subject...");
		
		//13.Comment textarea field-Comment textarea field is empty by default.
		get_support_window.verifyThatTextAreaIsEmpty(get_support_window.comments_field);
		
		//14.Comment textarea field is editable.
		get_support_window.verifyThatThisElementIsEditable(get_support_window.comments_field);
		
		//15.Verify the phrase under the comment testarea - The phrase is "* Indicates a required field"
		get_support_window.verifyThatTheTextOfWebElemenetIsAsExpected(get_support_window.support_window_info, "* Indicates a required field");
		
		//16.Cancel button - Cancel button background is white and the font is black	
		copy.verifyBlueColor(get_support_window.getBackGroundImageColor(get_support_window.send_button)); 
		get_support_window.VerifyFontColor(get_support_window.send_button,edit_recording_properties_window.SAVE_FONT_COLOR);
		
		//17.verify background and text color of cancel button
		record.verifyColorButton(get_support_window.getBackGroundImageColor(get_support_window.cancel_button));
		get_support_window.VerifyFontColor(get_support_window.cancel_button,edit_recording_properties_window.CANCEL_FONT_COLOR);
		
		//18.Hover over 'Cancel'
		get_support_window.moveToElementAndPerform(get_support_window.cancel_button, driver);
		get_support_window.verifyThatWeHaveHintToWebElement(get_support_window.cancel_button, "Cancel");
		
		//19.Hover over 'Send'
		get_support_window.moveToElementAndPerform(get_support_window.send_button, driver);
		get_support_window.verifyThatWeHaveHintToWebElement(get_support_window.send_button, "Send");
		
		//20.click on the cancel button
		get_support_window.clickElement(get_support_window.cancel_button);
		
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
	
	}
}