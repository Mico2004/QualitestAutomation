package com.automation.main.edit_recording_properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
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
import com.automation.main.page_helpers.EditRecordinPropertiesWindow;
import com.automation.main.page_helpers.LoginHelperPage;
import com.automation.main.page_helpers.RecordingHelperPage;
import com.automation.main.utilities.DriverSelector;

import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import atu.testng.reports.utils.Utils;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;
import junitx.util.PropertyManager;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;


@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC16999ValidateEditRecordingPropertiesModelWindowUI {

	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}
	
	public EditRecordinPropertiesWindow edit_recording_properties_window;
	public AdminDashboardViewCourseList admin_dashboard_view_course_list;
	public LoginHelperPage tegrity;
	public CoursesHelperPage course;
	public RecordingHelperPage record;
	public CalendarPage calendarPage;
	public AdminDashboardPage admin_dash_board_page;
	WebDriver driver;
	WebDriverWait wait;
	public static WebDriver thread_driver;
	CopyMenu copy;
	DesiredCapabilities capability;
    
	

	@BeforeClass
	public void setup() {
		try {


			driver = DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());
			calendarPage = PageFactory.initElements(driver, CalendarPage.class);
			admin_dash_board_page = PageFactory.initElements(driver, AdminDashboardPage.class);
			admin_dashboard_view_course_list = PageFactory.initElements(driver, AdminDashboardViewCourseList.class);
			tegrity = PageFactory.initElements(driver, LoginHelperPage.class);
			edit_recording_properties_window = PageFactory.initElements(driver, EditRecordinPropertiesWindow.class);
			record = PageFactory.initElements(driver, RecordingHelperPage.class);
			copy = PageFactory.initElements(driver, CopyMenu.class);
		} catch (Exception e) {
			ATUReports.add("Fail Step", LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
		}
		

		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC16999ValidateEditRecordingPropertiesModelWindowUI at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC16999ValidateEditRecordingPropertiesModelWindowUI at " + DateToStr, "Starting the test: TC16999ValidateEditRecordingPropertiesModelWindowUI at " + DateToStr, LogAs.PASSED, null);	
	}

	@AfterClass
	public void closeBroswer() {
		this.driver.quit();
	}
		

	
	// @Parameters({"web","title"}) in the future
	@Test (description="TC 16999 Validate edit recording properties model window UI")
	public void test16999() throws InterruptedException, ParseException {
		
		
		
		//1.log in courses page
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);	
		tegrity.loginCourses("User1");
		initializeCourseObject();
		
		//2.open some course whom listed you as instructor
		course.selectCourseThatStartingWith("Ab");
		
		//getting the information for later
		String racordingName = record.getFirstRecordingTitle();
		WebElement creationDate = record.getIndexDateWebElement(1);
		String creationDateString = creationDate.getText();
		
		//3.click on the recording tasks drop_down button
		record.mouseHoverJScript(record.recording_tasks_button);
		
		//4. verify that the edit recording properties option is greyed out and disable
		record.verifyRecordingMenuColor(record.edit_rec_properties_button);

		//5.check several recordings respective checkboxes
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.check_all_checkbox);
		
		//6.verify that the edit recording properties option is greyed out and disable
		record.verifyRecordingMenuColor(record.edit_rec_properties_button);
		
		//7.unchecked the checkbox of all records
		record.unClickOneCheckBoxOrVerifyNotSelected(record.check_all_checkbox);
		
		//8.check one recording tasks drop down button
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
		
		//9.click on the recording tasks->edit recording properties option
		record.toEditRecordingPropertiesMenu();
			
		//10.validate model window header is displayed correctly
		edit_recording_properties_window.waitForPageToLoad();
		edit_recording_properties_window.verifyEditPropertiesColor(record);
		Thread.sleep(2000);
		
		//11.the caption "edit recording properties" is displayed and aligned left
		edit_recording_properties_window.verifyEditRecordingPropertiesTitle();
		edit_recording_properties_window.verifyEditRedordingPropertiesTitleIsAlignrdLeft();
		
		//12.Validate the Recording name section is displayed correctly
		edit_recording_properties_window.verifyLabelExist(edit_recording_properties_window.name_label,"Name");
		edit_recording_properties_window.verifyLabelIsAlignrdLeftTOtheEditBoxOrSelector(edit_recording_properties_window.name_label,edit_recording_properties_window.recording_title);
			
		//13.the edit box contains the recording name
		edit_recording_properties_window.verifyInputEqualsOutSideString(edit_recording_properties_window.recording_title,racordingName);
			
		//14.Validate the recording date section
		edit_recording_properties_window.verifyLabelExist(edit_recording_properties_window.date_label,"Date");
		edit_recording_properties_window.verifyLabelIsAlignrdLeftTOtheEditBoxOrSelector(edit_recording_properties_window.date_label,edit_recording_properties_window.date_Field);
		edit_recording_properties_window.verifyInputEqualsOutSideString(edit_recording_properties_window.date_Field,creationDateString);
		edit_recording_properties_window.verifyInfomativeText();
		
		//15. Validate the Owner section
		edit_recording_properties_window.verifyLabelExist(edit_recording_properties_window.owner_label,"Owner");
		edit_recording_properties_window.verifyLabelIsAlignrdLeftTOtheEditBoxOrSelector(edit_recording_properties_window.owner_label,edit_recording_properties_window.owner_select);
		edit_recording_properties_window.verifyThatTheCreatorOwnerIsOnTheOwnerList("User1");
			
		//16. Validate the Type section
		edit_recording_properties_window.verifyLabelExist(edit_recording_properties_window.type_label,"Type");
		edit_recording_properties_window.verifyLabelIsAlignrdLeftTOtheEditBoxOrSelector(edit_recording_properties_window.type_label,edit_recording_properties_window.type_select);
		edit_recording_properties_window.checkTheFirstTypeThatAprearsOnLabelIsCorrect(record.recordings_tab);
		
		//17.Validate the buttons section are displayed correctly
		
		//verify background and text color of save button
		copy.verifyBlueColor(edit_recording_properties_window.getBackGroundImageColor(edit_recording_properties_window.save_button)); 
		edit_recording_properties_window.VerifyFontColor(edit_recording_properties_window.save_button,edit_recording_properties_window.SAVE_FONT_COLOR);
		
		//verify background and text color of cancel button
		record.verifyColorButton(edit_recording_properties_window.getBackGroundImageColor(edit_recording_properties_window.cancel_button));
		edit_recording_properties_window.VerifyFontColor(edit_recording_properties_window.cancel_button,edit_recording_properties_window.CANCEL_FONT_COLOR);
		
		edit_recording_properties_window.VerifyTheLocationOfTheSaveAndCancel();
		
		//18.Delete all text from the Name edit box
		edit_recording_properties_window.verifyWarningDisplay();
		edit_recording_properties_window.verifySaveButtonDisable();
		
		//19.Valid that The current creation date is displayed within the edit box.
		calendarPage.verifyTheCurrentCreationDateIsDisplayedWithinTheEditBox();
		
		//20.Verify drop down list is displayed with all available users option to assign as the recording creator.
		edit_recording_properties_window.addOwnersToList("Instractor",0);
		edit_recording_properties_window.verifyThatAllTheTypeInTheDropDownList();

		edit_recording_properties_window.verifyThatBoardersOfTheDropDownAreInBlack(edit_recording_properties_window.owner_select);
		
		//21.Verify drop down list is displayed with the following options: Regular recording,Proctoring recording,Student recording
		edit_recording_properties_window.verifyThatAllTheOptionsListInTheDropDwon();
		edit_recording_properties_window.verifyThatBoardersOfTheDropDownAreInBlack(edit_recording_properties_window.type_select);
		
		//22.insert a recording name in "Name" text box
		edit_recording_properties_window.insertChapterName(racordingName);
		
		//23.Hover over the "Save" button
		edit_recording_properties_window.verifyThatHoverOnButtonAndSeeShadow(edit_recording_properties_window.save_button,"Save");
		
		//24.Hover over the "Save" button
		edit_recording_properties_window.verifyThatHoverOnButtonAndSeeShadow(edit_recording_properties_window.cancel_button,"Cancel");
		
		//26.Click on cancel for the next test
		edit_recording_properties_window.clickElement(edit_recording_properties_window.cancel_button);
		
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
	}

	// description = "get courses list"
	public void initializeCourseObject() throws InterruptedException {

		course = PageFactory.initElements(driver, CoursesHelperPage.class);
		course.courses = course.getCoursesListFromElement(course.course_list);
	}
}