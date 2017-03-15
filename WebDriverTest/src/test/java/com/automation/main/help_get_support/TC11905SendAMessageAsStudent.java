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
import com.automation.main.page_helpers.ConfirmationMenu;
import com.automation.main.page_helpers.CopyMenu;
import com.automation.main.page_helpers.CoursesHelperPage;
import com.automation.main.page_helpers.EditRecordingPropertiesWindow;
import com.automation.main.page_helpers.EmailAndConnectionSettingsPage;
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
import junitx.util.PropertyManager;
import java.text.DateFormat;
import java.util.Date;

@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC11905SendAMessageAsStudent {

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
	public EmailAndConnectionSettingsPage email_and_connection_settings_page;
	public ConfirmationMenu confirm_menu;
	WebDriver driver;
	WebDriverWait wait;
	public TagMenu tag_window;
	public static WebDriver thread_driver;
	CopyMenu copy;
	DesiredCapabilities capability;
	String url_of_mail = "https://www.guerrillamail.com";
	String DateToStr;
	String user_Agent, recording_name,course_id;

	@BeforeClass
	public void setup() {

			driver = DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());
			admin_dash_board_page = PageFactory.initElements(driver, AdminDashboardPage.class);
			admin_dashboard_view_course_list = PageFactory.initElements(driver, AdminDashboardViewCourseList.class);
			tegrity = PageFactory.initElements(driver, LoginHelperPage.class);
			edit_recording_properties_window = PageFactory.initElements(driver, EditRecordingPropertiesWindow.class);
			email_and_connection_settings_page =  PageFactory.initElements(driver, EmailAndConnectionSettingsPage.class);
			get_support_window = PageFactory.initElements(driver, GetSupprtWindow.class);
			record = PageFactory.initElements(driver, RecordingHelperPage.class);
			copy = PageFactory.initElements(driver, CopyMenu.class);
			course = PageFactory.initElements(driver, CoursesHelperPage.class);
			confirm_menu =  PageFactory.initElements(driver, ConfirmationMenu.class);
			
		 Date curDate = new Date();
		 DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test:  TC11905SendAMessageAsStudent at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test:  TC11905SendAMessageAsStudent at " + DateToStr, "Starting the test:  TC11905SendAMessageAsStudent at " + DateToStr, LogAs.PASSED, null);	
	}

	@AfterClass
	public void closeBroswer() {
		this.driver.quit();
	}
		
	
	// @Parameters({"web","title"}) in the future
	@Test (description="TC11905 Send a message as Instructor")
	public void test11905() throws InterruptedException {
		
		//1.log in courses page
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);	
		
		//*+Precondition:+*Helpdesk email must be set up in *Set Email and Connection Settings* on Admin Dashboard
		//2.login as Admin
		tegrity.loginAdmin("Admin");
	
		//3.Enter to Set Email and Connection Settings on Admin Dashboard
		admin_dash_board_page.clickOnTargetSubmenuAdvancedServices("Set Email and Connection Settings");
		
		//4.set mail and clean all other options
		email_and_connection_settings_page.waitForThePageToLoad();
		email_and_connection_settings_page.cleanAllOptionsAndPutMail("qualitestautomation@sharklasers.com");
		
		//5.Click on ok after the confirm on the email setting
		confirm_menu.clickOnOkButtonAfterConfirmEmailSetting();
		
		//6.endPrecondition : sign out
		email_and_connection_settings_page.signOut();
		
		//7.login as student
		tegrity.loginCourses("User4");
		
		for(int CorusePageOrRecordingPage = 0 ; CorusePageOrRecordingPage < 3 ; CorusePageOrRecordingPage++){
		
		if(CorusePageOrRecordingPage == 1) {
			course.selectCourseThatStartingWith("Ab");
		} else if(CorusePageOrRecordingPage == 2) {	
			course.selectCourseThatStartingWith("Ab");
			recording_name = record.getFirstRecordingTitle();
			record.verifyFirstExpandableRecording();
			record.clickOnTheFirstCaptherWithOutTheExpand();
		}
		
		//8.Hover over the "Help" link	
		String url =  course.getCurrentUrlCoursePage(); 
		course.moveToElementAndPerform(course.help, driver);
			
		//9.Click the "Get Support" link
		course.clickElementJS(course.get_support);
		
		//10.*Get Support* form is displayed in new window.
		get_support_window.waitForThePageToLoad();
		get_support_window.verifyThatGetSupportMenuOpen();
		
		//11.Verify that *From* field contain the email you registered with and below it is your *User Name*
		get_support_window.verifyThatTheEmailAdressFieldHelpDeskOrPlaceHolder("qualitestautomation@sharklasers.com");
		
		//12.Enter the text in *Subject* textfield (Not mandatory)
		String subject = "Test 11905 " + DateToStr ;
		get_support_window.sendKeysToWebElementInput(get_support_window.subject_field, subject);
	
		//13.Enter the text in *Comments* textfield (Not mandatory)
		get_support_window.sendKeysToWebElementInput(get_support_window.comments_field,"Test 11905");
		
		//14.put the mail in the to field
		get_support_window.sendKeysToWebElementInput(get_support_window.from_email_field,"test@test.com");
		
		//15.User name field. Used name of the logged user as default value.
		get_support_window.verifyThatTheTextOfWebElemenetIsAsExpected(course.user_name, get_support_window.from_name_field.getAttribute("value"));
		
		//16.Press the *Send* button
		get_support_window.clickElement(get_support_window.send_button);
		
		//17.*Get Support* window is closed.
		get_support_window.verifyThatGetSupportMenuClose();
				
		//18. *Email sent successfully* message is displayed.
		confirm_menu.clickOnOkButtonAfterConfirmEmailSentSuccessfully();
				
		//16. *User Agent:* <Press F12 -> refresh the page if needed -> Network -> Choose a line -> Headers -> Request Headers -> User-Agent -> +Compare+>
		user_Agent = get_support_window.getUserAgentByLogs();
		
		//17.Open the Helpdesk email in new tab\window
		record.changeUrl(url_of_mail);
		
		//.Email is displayed.
		//18.enter the mail username 
		get_support_window.clickElement(get_support_window.changeGuerrillamailButton);
		get_support_window.sendKeysToWebElementInput(get_support_window.guerrillaMailEdittext, "qualitestautomation");
		
		//19.press on set
		get_support_window.clickElement(get_support_window.guerrillaMailSet);
			 	
		//20.Verify that sent mail is received.
		get_support_window.waitForTheMailToLoad();
		get_support_window.verifyThatCssValueIsAsExpected(get_support_window.mail_index_of_sending.get(0), "font-weight", "bold");
		//get_support_window.verifyThatTheTextOfWebElemenetIsAsExpected(get_support_window.mail_time_of_sending.get(2), subject);
	
		//21.Verify received email by the next order: Received mail from: <Various. Depends on who sent it>
		get_support_window.clickElement(get_support_window.mail_subject_of_sending.get(0));
		get_support_window.verifyWebElementTargetText(get_support_window.contant_of_mail,"test@test.com");
			
		//22 *From:* <User Name> (<From email>) <Comments>
		String userName = PropertyManager.getProperty("User4");
		get_support_window.verifyWebElementTargetText(get_support_window.contant_of_mail,"From: " + userName);
				
		//23.Separator line
		//get_support_window.verifyWebElementTargetText(get_support_window.contant_of_mail,"<hr/>");
		
		//24. *User ID:* <User Id>
		get_support_window.verifyWebElementTargetText(get_support_window.contant_of_mail,"User ID: " +userName.toLowerCase());
			
		//25. *User Name:* <User Name>
		get_support_window.verifyWebElementTargetText(get_support_window.contant_of_mail,"User Name: " +userName);
		
		//26.*Customer Name:* <Institution Name>
		String customer_Name =  get_support_window.moveToUpperCase(get_support_window.getTheUniverstyNameFromTheUrl(url));
		get_support_window.verifyWebElementTargetText(get_support_window.contant_of_mail,"Customer Name: " +customer_Name);
		
		if(CorusePageOrRecordingPage == 1 || CorusePageOrRecordingPage == 2){
			//26.*Course Id:* <Course Id>
			if(CorusePageOrRecordingPage == 1){
				course_id =  get_support_window.getTheGgidFromTheUrl(url);
			}
			get_support_window.verifyWebElementTargetText(get_support_window.contant_of_mail,"Course ID: " +course_id);
		
			//26.1 *Course Name:* <Course name>
			get_support_window.verifyWebElementTargetText(get_support_window.contant_of_mail,"Course Name: " +PropertyManager.getProperty("course1"));		
		} 
		if (CorusePageOrRecordingPage == 2){
			//26.2 *Recording ID:* <Recording Id>
			String recording_id =  get_support_window.getTheGgidFromTheUrlForRecording(url);
			get_support_window.verifyWebElementTargetText(get_support_window.contant_of_mail,"Recording ID: " +recording_id);
			
			//26.3. *Recording Name:* <recording name>
			get_support_window.verifyWebElementTargetText(get_support_window.contant_of_mail,"Recording Name: " +recording_name);
		}
		//27. *Page URL:* <Full page URL>
		get_support_window.verifyWebElementTargetText(get_support_window.contant_of_mail,"User Agent: " +user_Agent);
				
		//28. *Page URL:* <Full page URL>
		get_support_window.verifyWebElementTargetText(get_support_window.contant_of_mail,"Page URL: " +url);
		
		//28. click on the back button
		get_support_window.clickElementJS(get_support_window.backButton);
				
		//29. select the first item to delete
		record.SelectOneCheckBoxOrVerifyAlreadySelected(get_support_window.firstCheckbox.get(0));
				
		//30. click on the delete button
		get_support_window.clickElementJS(get_support_window.deleteButton);
		
		//31.return back to the course page
		record.changeUrl(url);
		
		}
					
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
	
	}
}