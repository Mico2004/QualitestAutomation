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
public class TC8082ValidateAttachingTag {

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
		 System.out.println("Starting the test: TC8082ValidateAttachingTag at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC8082ValidateAttachingTag at " + DateToStr, "Starting the test: TC8082ValidateAttachingTag at " + DateToStr, LogAs.PASSED, null);	
	}

	@AfterClass
	public void closeBroswer() {
		this.driver.quit();
	}
		
	// @Parameters({"web","title"}) in the future
	@Test (description="TC8082 Validate attaching tag")
	public void test8082() throws InterruptedException {
		
		//1.Enter the university
				tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);	
				
				//0 - for regular recording ,1- for student recording ,2- for student user
				for(int type_of_user = 0; type_of_user < 3; type_of_user++) {
				
				//*+preconditions:+*Recordings in course with several tags is required for this test.	
				if(type_of_user < 2){
					//2.pre test - Login as INSTRUCTOR 
					tegrity.loginCourses("User1");
				} else {
					//2.pre test - Login as STUDENT
					tegrity.loginCourses("User4");
				}
				
				//3.Click on certain course from the courses list.
				course.selectCourseThatStartingWith("Ab");
				
				if(type_of_user == 1) {
					record.clickOnStudentRecordingsTab();
				}
				
				//4.Check one available recording checkbox. 
				record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
						
				//5.Click the "Recording Tasks" drop-down menu and choose the "Tag" option
				record.clickOnRecordingTaskThenTag();
			
				//6.The "Tag" Dialog window is appeared.
				tag_window.waitForPageToLoad();
				
				//7.create 2 tags for the test
				if(type_of_user == 0){
					validNewName = "Test";
					tag_window.createNewTag(validNewName);		
				}else if (type_of_user == 1) {
					validNewName = "Exam";
					tag_window.createNewTag(validNewName);
				} else {
					validNewName = "Tag";
					tag_window.createNewTag(validNewName);
				}
				//8.Click on the "Apply" button
				tag_window.saveAllTheInstractors();
				record.clickElementJS(tag_window.apply_button);
				
				//9. sign out
				record.signOut();
				
				if(type_of_user < 2){
					//10.Login as INSTRUCTOR 
					tegrity.loginCourses("User1");
				} else {
					//10.Login as STUDENT
					tegrity.loginCourses("User4");
				}
				
				//11.Click on preconditional course from the courses list.
				course.selectCourseThatStartingWith("Ab");
				
				if(type_of_user == 1) {
					record.clickOnStudentRecordingsTab();
				}
							
				//12.Check one available recording checkbox. 
				record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
							
				//13.Click the "Recording Tasks" drop-down menu and choose the "Tag" option
				record.clickOnRecordingTaskThenTag();
						
				//14.The "Tag" Dialog window is appeared.
				tag_window.waitForPageToLoad();
				tag_window.verifyTagWindowOpen();
					
				//15. The selected recording tags checkboxes are checked.
				tag_window.verifyAllTheTagCheckboxesAreChecked();
				
				//16. All the INSTRUCTOR owned tags is displayed.
				tag_window.verifyAllInstractorAreDisplay();
				
				//17.Click on the "Apply" button
				record.clickElementJS(tag_window.apply_button);
				
				//18.Check one available recording checkbox to create new tag that will be uncheck. 
				record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
			
				//19.Click the "Recording Tasks" drop-down menu and choose the "Tag" option
				record.clickOnRecordingTaskThenTag();
						
				//20.The "Tag" Dialog window is appeared.
				tag_window.waitForPageToLoad();
				tag_window.verifyTagWindowOpen();
				
				//21.Create new tag
				validNewName = "NewTag";
				tag_window.createNewTag(validNewName);
				
//				//22.unselect the new tag
//				tag_window.unCheckOneTagCheckboxes(validNewName);
//				
//				//22.Click on the "Apply" button
//				record.clickElementJS(tag_window.apply_button);
//				
//				//23.select that tag
//				record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
//				
//				//24.Click the "Recording Tasks" drop-down menu and choose the "Tag" option
//				record.clickOnRecordingTaskThenTag();
//						
//				//25.The "Tag" Dialog window is appeared.
//				tag_window.waitForPageToLoad();
//				tag_window.verifyTagWindowOpen();
//				
				//26.Check additional tag checkbox in the "Tag List"
				tag_window.CheckAllTheTagCheckboxes();
				
				//27.Additional tag checkbox in the "Tag List" is checked.
				tag_window.verifyAllTheTagCheckboxesAreChecked();
				
				//28.The list is sorted by name.
				tag_window.validateTheTagsListRowsIsSortedByTagName();
				
				//29.Click on the "Apply" button
				record.clickElementJS(tag_window.apply_button);
				
				//30.The "Tag Dialog" is disappeared.
				tag_window.verifyTagWindowClose();
					
				//31.The additional tag has been attached to the selected recordings.
				record.verifyTagApperedUderTheSelectRecording(validNewName);
						
				//32. The tags in the "Tag Indicator" are sorted by the name.
				record.validateTheSeveralTaggedRecordingTagsAreSortedByName();				
								
				//33.Check one available recording checkbox. 
				record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
							
				//34.Click the "Recording Tasks" drop-down menu and choose the "Tag" option
				record.clickOnRecordingTaskThenTag();
						
				//35.The "Tag" Dialog window is appeared.
				tag_window.waitForPageToLoad();
				tag_window.verifyTagWindowOpen();
				
				//36.delete all the tag for the next test
				tag_window.deleteAllExistingTags();
							
				//37.sign out
				record.signOut();
							
				}		
			
				System.out.println("Done.");
				ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
			
			}

}