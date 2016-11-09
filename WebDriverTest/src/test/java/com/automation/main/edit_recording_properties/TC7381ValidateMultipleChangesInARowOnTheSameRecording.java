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
import com.automation.main.page_helpers.ConfirmationMenu;
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
import junitx.util.PropertyManager;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;


@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC7381ValidateMultipleChangesInARowOnTheSameRecording  {


	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}
	
	public EditRecordinPropertiesWindow edit_recording_properties_window;
	public AdminDashboardViewCourseList admin_dashboard_view_course_list;
	public LoginHelperPage tegrity;
	public CoursesHelperPage course;
	public RecordingHelperPage record;
	public ConfirmationMenu confirm_menu;
	public CalendarPage calendarPage;
	public AdminDashboardPage admin_dash_board_page;
	WebDriver driver;
	WebDriverWait wait;
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
			confirm_menu = PageFactory.initElements(driver, ConfirmationMenu.class);
			edit_recording_properties_window = PageFactory.initElements(driver, EditRecordinPropertiesWindow.class);
			record = PageFactory.initElements(driver, RecordingHelperPage.class);
			copy = PageFactory.initElements(driver, CopyMenu.class);

		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC7381ValidateMultipleChangesInARowOnTheSameRecording at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC7381ValidateMultipleChangesInARowOnTheSameRecording at " + DateToStr, "Starting the test: TC7381ValidateMultipleChangesInARowOnTheSameRecording at " + DateToStr, LogAs.PASSED, null);	
	}

	@AfterClass
	public void closeBroswer() {
		this.driver.quit();
	}
		
	// @Parameters({"web","title"}) in the future
	@Test (description="TC7381 Validate multiple changes in A row on the same recording")
	public void test17022() throws InterruptedException, ParseException {
		
		
		//1.log in courses page
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);	
		tegrity.loginCourses("User1");
		initializeCourseObject();
		
		//2.open some course whom listed you as instructor
		course.selectCourseThatStartingWith("Ab");
			
	   //3.Check some recording respective checkbox 
		int recordNumber = record.checkExistenceOfNonEditRecordingsStatusInRecordings();
		record.unselectallCheckbox();
		record.selectIndexCheckBox(recordNumber);
				
		//4.click on the recording tasks->edit recording properties option
		record.toEditRecordingPropertiesMenu();
			
		//5.wait for edit reocrding properties window to open
		edit_recording_properties_window.waitForPageToLoad();
			
		//6.Type some other name to the "Name" edit box.
		String name = "Change recording name";
		edit_recording_properties_window.changeRecordingNameToTargetName(name);
		
		//7.Click the "Save" button
		edit_recording_properties_window.clickOnSaveButton();
				
		//8.The model window is closed.
		edit_recording_properties_window.verifyConfirmWindowIsClosed();
				
		//9. The header background color is as the customize or defualt university background color.
		confirm_menu.verifyConfirmBackgroundColor(record);
				
		//10.The "Ok" Button is displayed on the bottom right corner of the model window.
		confirm_menu.verifyTheLocationOfTheOkButtonIsInTheButtomRight();
				
		//11.The "Edit Recording Properties" caption is displayed inside the header.
		//12.The informative text "Recording properties have been queued for edit" is displayed below the header.	
		confirm_menu.clickOnOkButtonAfterConfirmEditRecordingProperties();
				
		//13.The second model window disappears.
		confirm_menu.verifyConfirmWindowIsClosed();
				
		//14.Validate the recording name has changed to "Change recording name" 
		record.verifyThatTargetRecordingExistInRecordingList(name);
					
		//wait the status disappear 
		record.checkExistenceOfNonEditRecordingsStatusInTheIndex(recordNumber,30);
		
		//15.Check the same recording respective checkbox 
		recordNumber = record.getIndexOfRecordFromRecordName(name);
		record.unselectallCheckbox();
		record.selectIndexCheckBox(recordNumber);
		
		//16.click on the recording tasks->edit recording properties option
		record.toEditRecordingPropertiesMenu();
					
		//17.wait for edit reocrding properties window to open
		edit_recording_properties_window.waitForPageToLoad();
		
		//18.Type some other name to the "Name" edit box.
		name = "Change recording name2";
		edit_recording_properties_window.changeRecordingNameToTargetName(name);
		
		//19.Click the "Save" button
		edit_recording_properties_window.clickOnSaveButton();
						
		//20.The model window is closed.
		edit_recording_properties_window.verifyConfirmWindowIsClosed();
						
		//21. The header background color is as the customize or defualt university background color.
		confirm_menu.verifyConfirmBackgroundColor(record);
						
		//22.The "Ok" Button is displayed on the bottom right corner of the model window.
		confirm_menu.verifyTheLocationOfTheOkButtonIsInTheButtomRight();
						
		//23.The "Edit Recording Properties" caption is displayed inside the header.
		//24.The informative text "Recording properties have been queued for edit" is displayed below the header.	
		confirm_menu.clickOnOkButtonAfterConfirmEditRecordingProperties();
						
		//25.The second model window disappears.
		confirm_menu.verifyConfirmWindowIsClosed();
						
		//26.Validate the recording name has changed to "Change recording name" 
		record.verifyThatTargetRecordingExistInRecordingList(name);
			
		//wait the status disappear 
		record.checkExistenceOfNonEditRecordingsStatusInTheIndex(recordNumber,30);
				
		//27.Check the same recording respective checkbox 
		recordNumber = record.getIndexOfRecordFromRecordName(name);
		record.unselectallCheckbox();
		record.selectIndexCheckBox(recordNumber);
			
		//28.click on the recording tasks->edit recording properties option
		record.toEditRecordingPropertiesMenu();
					
		//29.wait for edit reocrding properties window to open
		edit_recording_properties_window.waitForPageToLoad();
			
		//30.Select some other Creation date.
		calendarPage.changeCreateDay(2);
		
		//8.The date is in the following format: 'XX/XX/XXXX'.
		String correctDate =edit_recording_properties_window.verifyThatTheCalendarInTheRightFormat();
				
		//31.Click the "Save" button
		edit_recording_properties_window.clickOnSaveButton();
								
		//32.The model window is closed.
		edit_recording_properties_window.verifyConfirmWindowIsClosed();
								
		//33. The header background color is as the customize or defualt university background color.
		confirm_menu.verifyConfirmBackgroundColor(record);
								
		//34.The "Ok" Button is displayed on the bottom right corner of the model window.
		confirm_menu.verifyTheLocationOfTheOkButtonIsInTheButtomRight();
								
		//35.The "Edit Recording Properties" caption is displayed inside the header.
		//36.The informative text "Recording properties have been queued for edit" is displayed below the header.	
		confirm_menu.clickOnOkButtonAfterConfirmEditRecordingProperties();
								
		//37.The second model window disappears.
		confirm_menu.verifyConfirmWindowIsClosed();
		
		//38.The recording Creation date han't changed.
		recordNumber = record.getIndexOfRecordFromRecordName(name);
		record.verifyThatTheRecordNameEqualsFromTheString(correctDate,recordNumber,"Record date");
		
		//wait the status disappear 
		record.checkExistenceOfNonEditRecordingsStatusInTheIndex(recordNumber,30);
						
		//39.Check the same recording respective checkbox 
		record.unselectallCheckbox();
		record.selectIndexCheckBox(recordNumber);
					
		//40.click on the recording tasks->edit recording properties option
		record.toEditRecordingPropertiesMenu();
							
		//41.wait for edit reocrding properties window to open
		edit_recording_properties_window.waitForPageToLoad();
					
		//42.Select some other Creation date.
		calendarPage.changeCreateDay(3);
				
		//43.The date is in the following format: 'XX/XX/XXXX'.
		correctDate =edit_recording_properties_window.verifyThatTheCalendarInTheRightFormat();
						
		//44.Click the "Save" button
		edit_recording_properties_window.clickOnSaveButton();
										
		//45.The model window is closed.
		edit_recording_properties_window.verifyConfirmWindowIsClosed();
										
		//46. The header background color is as the customize or defualt university background color.
		confirm_menu.verifyConfirmBackgroundColor(record);
										
		//47.The "Ok" Button is displayed on the bottom right corner of the model window.
		confirm_menu.verifyTheLocationOfTheOkButtonIsInTheButtomRight();
										
		//48.The "Edit Recording Properties" caption is displayed inside the header.
		//49.The informative text "Recording properties have been queued for edit" is displayed below the header.	
		confirm_menu.clickOnOkButtonAfterConfirmEditRecordingProperties();
										
		//50.The second model window disappears.
		confirm_menu.verifyConfirmWindowIsClosed();
				
		//51.The recording Creation date han't changed.
		recordNumber = record.getIndexOfRecordFromRecordName(name);
		record.verifyThatTheRecordNameEqualsFromTheString(correctDate,recordNumber,"Record date");
		
		//wait the status disappear 
		record.checkExistenceOfNonEditRecordingsStatusInTheIndex(recordNumber,30);
								
		//52.Check the same recording respective checkbox 
		record.unselectallCheckbox();
		record.selectIndexCheckBox(recordNumber);
		String recordBy = record.getTheRecordedByRecordIndex(recordNumber);
							
		//53.click on the recording tasks->edit recording properties option
		record.toEditRecordingPropertiesMenu();
									
		//54.wait for edit reocrding properties window to open
		edit_recording_properties_window.waitForPageToLoad();
		
		//55.Click on the "Owner" drop down button
		//56.The drop down edit box contains only INSTRUCTORS users.	
		edit_recording_properties_window.addOwnersToList("Instractor",0);
		edit_recording_properties_window.verifyThatAllTheTypeInTheDropDownList();
		
		//57.Choose one of the users
		String newOwner = edit_recording_properties_window.clickOnDifferentOwnerThatTheExist(recordBy);
		
		//58.Click the "Save" button
		edit_recording_properties_window.clickOnSaveButton();
						
		//59.The model window is closed.
		edit_recording_properties_window.verifyConfirmWindowIsClosed();
						
		//60. The header background color is as the customize or defualt university background color.
		confirm_menu.verifyConfirmBackgroundColor(record);
							
		//61.The "Ok" Button is displayed on the bottom right corner of the model window.
		confirm_menu.verifyTheLocationOfTheOkButtonIsInTheButtomRight();
						
		//62.The "Edit Recording Properties" caption is displayed inside the header.
		//63.The informative text "Recording properties have been queued for edit" is displayed below the header.
		confirm_menu.clickOnOkButtonAfterConfirmEditRecordingProperties();
						
		//64.The second model window disappears.
		confirm_menu.verifyConfirmWindowIsClosed();
				
		//65.Validate The recording Owner has changed to user you selected earlier.
		recordNumber = record.getIndexOfRecordFromRecordName(name);
		record.verifyThatTheRecordNameEqualsFromTheString(newOwner,recordNumber,"Record creator");
		
		//wait the status disappear 
		record.checkExistenceOfNonEditRecordingsStatusInTheIndex(recordNumber,30);
										
		//66.Check the same recording respective checkbox 
		record.unselectallCheckbox();
		record.selectIndexCheckBox(recordNumber);
									
		//67.click on the recording tasks->edit recording properties option
		record.toEditRecordingPropertiesMenu();
											
		//68.wait for edit reocrding properties window to open
		edit_recording_properties_window.waitForPageToLoad();
				
		//69.Click on the "Owner" drop down button
		//70.The drop down edit box contains only INSTRUCTORS users.	
		edit_recording_properties_window.addOwnersToList("Instractor",0);
		edit_recording_properties_window.verifyThatAllTheTypeInTheDropDownList();
				
		//71.Choose one of the users
		newOwner = edit_recording_properties_window.clickOnDifferentOwnerThatTheExist(newOwner);
				
		//72.Click the "Save" button
		edit_recording_properties_window.clickOnSaveButton();
								
		//73.The model window is closed.
		edit_recording_properties_window.verifyConfirmWindowIsClosed();
								
		//74. The header background color is as the customize or defualt university background color.
		confirm_menu.verifyConfirmBackgroundColor(record);
									
		//75.The "Ok" Button is displayed on the bottom right corner of the model window.
		confirm_menu.verifyTheLocationOfTheOkButtonIsInTheButtomRight();
								
		//76.The "Edit Recording Properties" caption is displayed inside the header.
		//77.The informative text "Recording properties have been queued for edit" is displayed below the header.
		confirm_menu.clickOnOkButtonAfterConfirmEditRecordingProperties();
								
		//78.The second model window disappears.
		confirm_menu.verifyConfirmWindowIsClosed();
						
		//79.Validate The recording Owner has changed to user you selected earlier.
		recordNumber = record.getIndexOfRecordFromRecordName(name);
		record.verifyThatTheRecordNameEqualsFromTheString(newOwner,recordNumber,"Record creator");
		
		//wait the status disappear 
		record.checkExistenceOfNonEditRecordingsStatusInTheIndex(recordNumber,30);
									
		//80.Check the same recording respective checkbox 
		recordNumber = record.getIndexOfRecordFromRecordName(name);
		record.unselectallCheckbox();
		record.selectIndexCheckBox(recordNumber);
									
		//81.click on the recording tasks->edit recording properties option
		record.toEditRecordingPropertiesMenu();
											
		//82.wait for edit reocrding properties window to open
		edit_recording_properties_window.waitForPageToLoad();
		
		//83.Verify drop down list is displayed with the following options: Regular recording,Proctoring recording,Student recording
		edit_recording_properties_window.verifyThatAllTheOptionsListInTheDropDwon();
				
		//84.Choose Student recording
		edit_recording_properties_window.ChooseDiffrenetType("Student recording");
		edit_recording_properties_window.verifyThatTheTypeWasChoosen("Student recording");
					
		//85.The drop down edit box contains only STUDENT users.	
		edit_recording_properties_window.addOwnersToList("Student",0);
		edit_recording_properties_window.verifyThatAllTheTypeInTheDropDownList();
		
		//get the record by after changing the owner for verify later
		recordBy =  edit_recording_properties_window.getRecordBy(new Select(edit_recording_properties_window.owner_select).getFirstSelectedOption().getText());
		
		//86.Click the "Save" button
		edit_recording_properties_window.clickOnSaveButton();
								
		//87.The model window is closed.
		edit_recording_properties_window.verifyConfirmWindowIsClosed();
								
		//88. The header background color is as the customize or defualt university background color.
		confirm_menu.verifyConfirmBackgroundColor(record);
									
		//89.The "Ok" Button is displayed on the bottom right corner of the model window.
		confirm_menu.verifyTheLocationOfTheOkButtonIsInTheButtomRight();
								
		//90.The "Edit Recording Properties" caption is displayed inside the header.
		//91.The informative text "Recording properties have been queued for edit" is displayed below the header.
		confirm_menu.clickOnOkButtonAfterConfirmEditRecordingProperties();
								
		//92.The second model window disappears.
		confirm_menu.verifyConfirmWindowIsClosed();
		
		//93.Click on the "Students Recordings" tab
		record.clickOnStudentRecordingsTab();
		
		//94. The recording information - "recorded by" value has changed to the STUDENT user selected earlier.
		recordNumber = record.getIndexOfRecordFromRecordName(name);
		record.verifyThatTheRecordNameEqualsFromTheString(recordBy,recordNumber,"Record creator");	
		
		//wait the status disappear 
		record.checkExistenceOfNonEditRecordingsStatusInTheIndex(recordNumber,30);
		
		//95.Check some recording respective checkbox 
		record.selectIndexCheckBox(recordNumber);
		
		//96.click on the recording tasks->edit recording properties option
		record.toEditRecordingPropertiesMenu();
							
		//97.wait for edit reocrding properties window to open
		edit_recording_properties_window.waitForPageToLoad();
				
		//98.Verify drop down list is displayed with the following options: Regular recording,Proctoring recording,Student recording
		edit_recording_properties_window.verifyThatAllTheOptionsListInTheDropDwon();
						
		//99.Choose Proctoring recording
		edit_recording_properties_window.ChooseDiffrenetType("Proctoring recording");
		
		//getting the creator name for later
		String recordNameForTestTab = edit_recording_properties_window.getNewRecordNameForTest(new Select(edit_recording_properties_window.owner_select).getFirstSelectedOption().getText());
		
		//100.Click the "Save" button
		edit_recording_properties_window.clickOnSaveButton();
										
		//101.The model window is closed.
		edit_recording_properties_window.verifyConfirmWindowIsClosed();
										
		//102. The header background color is as the customize or defualt university background color.
		confirm_menu.verifyConfirmBackgroundColor(record);
											
		//103.The "Ok" Button is displayed on the bottom right corner of the model window.
		confirm_menu.verifyTheLocationOfTheOkButtonIsInTheButtomRight();
										
		//104.The "Edit Recording Properties" caption is displayed inside the header.
		//105.The informative text "Recording properties have been queued for edit" is displayed below the header.
		confirm_menu.clickOnOkButtonAfterConfirmEditRecordingProperties();
										
		//106.The second model window disappears.
		confirm_menu.verifyConfirmWindowIsClosed();
		
		//107.Click on the "Tests" tab
		record.clickOnTestsTab();
		
		//108.The recording name has changed to the user name of the recording creator.
		record.verifyThatTargetRecordingExistInRecordingList(recordNameForTestTab);
		
		
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
	}

	// description = "get courses list"
	public void initializeCourseObject() throws InterruptedException {

		course = PageFactory.initElements(driver, CoursesHelperPage.class);
		course.courses = course.getCoursesListFromElement(course.course_list);
	}
}

