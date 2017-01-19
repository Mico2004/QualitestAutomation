package com.automation.main.search_permissions;

import java.util.Date;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import com.automation.main.page_helpers.BottomFooter;
import com.automation.main.page_helpers.ConfirmationMenu;
import com.automation.main.page_helpers.CopyMenu;
import com.automation.main.page_helpers.CourseSettingsPage;
import com.automation.main.page_helpers.CoursesHelperPage;
import com.automation.main.page_helpers.EditRecording;
import com.automation.main.page_helpers.EditRecordingPropertiesWindow;
import com.automation.main.page_helpers.LoginHelperPage;
import com.automation.main.page_helpers.PlayerPage;
import com.automation.main.page_helpers.PublishWindow;
import com.automation.main.page_helpers.RecordingHelperPage;
import com.automation.main.page_helpers.SearchPage;
import com.automation.main.page_helpers.TagMenu;
import com.automation.main.page_helpers.TopBarHelper;
import com.automation.main.utilities.DriverSelector;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import junitx.util.PropertyManager;


@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC10902AndTC10903AndTC10907AndTC10908 {

	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	} 
	
	public EditRecording edit_recording;
	public EditRecordingPropertiesWindow edit_recording_properties_window;
	public BottomFooter bottom_footer;
	public SearchPage search_page;
	public TagMenu tag_window;
	public PublishWindow publish_window;
	public TopBarHelper top_bar_helper;
	public LoginHelperPage tegrity;
	public CoursesHelperPage course;
	public RecordingHelperPage record;
	public ConfirmationMenu confirm_menu;
	public CourseSettingsPage course_settings_page;
	public PlayerPage player_page;
	WebDriver driver;
	WebDriverWait wait;
	public static WebDriver thread_driver;
	public CopyMenu copy;
	String current_course;
	String validNewName;
	String publish_record,unpublish_regular_record, unpublish_student_record,test_student_record,test_different_student;
	String publish_regualr_tag_diffrenet_student,publish_regular_difftenet_student_bookmark , publish_regular_private_tag_ins;
	String publish_diffrenet_student_tag,publish_student_difftenet_student_bookmark,publish_student_private_tag_ins;
	Date date = new Date();
	SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyhhmmss"); 
	String new_recording_name = "NewName" + sdf.format(date);	

	@BeforeTest
	public void setup() {
		
		driver = DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());
		tegrity = PageFactory.initElements(driver, LoginHelperPage.class);
		record = PageFactory.initElements(driver, RecordingHelperPage.class);
		copy = PageFactory.initElements(driver, CopyMenu.class);		
		confirm_menu = PageFactory.initElements(driver, ConfirmationMenu.class);		
		top_bar_helper = PageFactory.initElements(driver,TopBarHelper.class);
		tag_window= PageFactory.initElements(driver, TagMenu.class);
		player_page = PageFactory.initElements(driver, PlayerPage.class);
		course = PageFactory.initElements(driver, CoursesHelperPage.class);
		course_settings_page = PageFactory.initElements(driver, CourseSettingsPage.class);
		edit_recording_properties_window = PageFactory.initElements(driver, EditRecordingPropertiesWindow.class);
		publish_window = PageFactory.initElements(driver, PublishWindow.class);
		search_page = PageFactory.initElements(driver, SearchPage.class);	
		bottom_footer = PageFactory.initElements(driver, BottomFooter.class);	
		edit_recording = PageFactory.initElements(driver, EditRecording.class);
		
		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC10902VerifyThatStudentsCantFindWhatTheyreNotSupposedToOnTheAllCoursesLevel at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC10902VerifyThatStudentsCantFindWhatTheyreNotSupposedToOnTheAllCoursesLevel at " + DateToStr,
		 "Starting the test: TC10902VerifyThatStudentsCantFindWhatTheyreNotSupposedToOnTheAllCoursesLevel at " + DateToStr, LogAs.PASSED, null);
		
	}
	
	@AfterTest
	public void closeBroswer() {
		this.driver.quit();
	}
	
	@AfterClass
	public void postTest() throws InterruptedException {

		for(int type_of_user = 0 ; type_of_user <2; type_of_user++){
			
			if(type_of_user == 0){
				//19.Login as the Instructor 
				tegrity.loginCourses("User1");
			}else {
				//19.Login as the Student
				tegrity.loginCourses("User3");
			}
			
			//20.Open some course for that.
			course.selectCourseThatStartingWith("Ab");
				
			//21.Check the first publish record
			record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
			
			//22.Click the "Recording Tasks" drop-down menu and choose the "Tag" option
			record.clickOnRecordingTaskThenTag();
	
			//23.The "Tag" Dialog window is appeared.
			tag_window.waitForPageToLoad();
			tag_window.verifyTagWindowOpen();
			
			//24.delete all the tags
			tag_window.deleteAllExistingTags();
			
			//25.Click on the "Apply" button
			tag_window.clickElementJS(tag_window.apply_button);
		
			//26.delete all the bookmarks
			if(type_of_user == 1) {
				
				record.clickOnBookmarksTab();					
				record.deleteBookmarkInBookmarkTab("InsTestUnclearStu");
				record.deleteBookmarkInBookmarkTab("StuTestUnclearStu");
			}
								
			//27.sign out
			record.signOut();
		}
	}

	@BeforeClass
	public void preTest() throws InterruptedException {
		
		//1.Open tegrity "Login page"
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
				
		// pre preconditions
		//1 change the names of the record that will be unique
		tegrity.loginCourses("User1");
				
		//1.1.Open some course for that.
		current_course = course.selectCourseThatStartingWith("Ab");
				
		for(int number_of_tab = 0 ; number_of_tab <2 ; number_of_tab++){
					
			if(number_of_tab == 1 ){
				record.clickOnStudentRecordingsTab();
			}
			//2.1.change the first name
			record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
			edit_recording_properties_window.changeNameOfRecord(record,confirm_menu);
					
			//2.2 change the second name
			record.unselectIndexCheckBox(1);
			record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox2);
			edit_recording_properties_window.changeNameOfRecord(record,confirm_menu);
		}
			//1.2 sign out
			record.signOut();
				
			//2.*Preconditions: 
			//Have a Student enrolled to a course with these:
			//At least one Unpublished Regular Recording , Student Recording 
			tegrity.loginCourses("User1");
						
			//2.1.Open some course for that.
			course.selectCourseThatStartingWith(current_course);
				
			for(int number_of_tab = 0 ; number_of_tab <2 ; number_of_tab++){
				
				if(number_of_tab == 1 ){
					record.clickOnStudentRecordingsTab();
				}
				//2.2.check several recordings respective checkboxes
				record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
						
				//2.3.Click the "Recording Tasks" drop-down menu and choose the "Publish" option
				record.clickOnRecordingTaskThenPublish();
					
				//2.4.Validate "Publish" window content is displayed correctly
				//The window title is "Publish".
				publish_window.waitForPageToLoad();
				publish_window.chooseRadioButton("Never");
					
				//2.5.Press the "Save" button
				publish_window.clickOnSaveButton();
					
				//need that names for later use
				if(number_of_tab == 0){	
					unpublish_regular_record = record.getFirstRecordingTitle();
				} else {
					unpublish_student_record = record.getFirstRecordingTitle();
				}
			}
				
			//need that for later
			publish_record = record.getSecondRecordingTitle();
					
			//3.change the owner of the Student recording and test recordings X2
			for(int number_of_tab = 0 ; number_of_tab <3; number_of_tab++){
					
				if(number_of_tab == 1) {
					record.clickOnTestsTab();
				}
					
				//3.1.check several recordings respective checkboxes
				if(number_of_tab <2){
					record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
				} else {
					record.unselectIndexCheckBox(1);
					record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox2);
				}
				//3.2 hover on the Recording Task and click on the edit recording properties 
				record.toEditRecordingPropertiesMenu();
					
				//3.3 change the owner to be Student -(At least one of those test recording's *belongs to that Student*0
				edit_recording_properties_window.waitForPageToLoad();
				if(number_of_tab <2){
					edit_recording_properties_window.changeOwner(PropertyManager.getProperty("User3"));					
				}else {
					edit_recording_properties_window.changeOwner(PropertyManager.getProperty("User4"));
				}		
				//3.4 Click the "Save" button
				edit_recording_properties_window.clickOnSaveButton();
					
				//3.5 Click on Ok after change the owner
				confirm_menu.clickOnOkButtonAfterConfirmEditRecordingProperties();	
					
			}
				
				//need that for later use
				test_student_record = record.getFirstRecordingTitleTest();
				test_different_student = record.getSecondRecordingTestTitle();
				
				//3.5 sign out
				record.signOut();
				
				//3.6 Login as STUDENT
				tegrity.loginCourses("User3");
				
				//3.7.Open some course for that.
				course.selectCourseThatStartingWith(current_course);
					
				//4.At least one Published Regular or Student Recording with - a different Student's bookmark	
				for(int number_of_tab = 0 ; number_of_tab <2; number_of_tab++){
					
					if(number_of_tab == 1){
						record.clickOnStudentRecordingsTab();
					}
						
				//4.1.move to the player to add bookmark 
				record.clickOnTargetRecordingAndOpenItsPlayback(record.getSecondRecordingTitle());
			
				//4.2.display recording and click on the stop button
				player_page.verifyTimeBufferStatusForXSec(2);

				///4.3.add new unclear bookmark
				if(number_of_tab == 0){
					player_page.addTargetBookmark("InsTestUnclearStu");
					publish_regular_difftenet_student_bookmark = "InsTestUnclearStu";
				} else {
					player_page.addTargetBookmark("StuTestUnclearStu");
					publish_student_difftenet_student_bookmark ="StuTestUnclearStu";
				}
				player_page.exitInnerFrame();
					
				//4.4.return to the recording tab
				player_page.returnToRecordingPageByNameAsUserOrGuest(current_course, record);
					
				}
				
				//5. At least one Published Regular Or Student Recording with - a different Student's tag
				for(int type_of_user = 0 ; type_of_user <2; type_of_user++){
				
					for(int number_of_tab = 0 ; number_of_tab <2; number_of_tab++){
					
						if(number_of_tab == 1){
							record.clickOnStudentRecordingsTab();
						}
							
						//5.1.Check the first publish record
						record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox2);
						
						//5.2.Click the "Recording Tasks" drop-down menu and choose the "Tag" option
						record.clickOnRecordingTaskThenTag();
				
						//5.3.The "Tag" Dialog window is appeared.
						tag_window.waitForPageToLoad();
						
						//5.4.Click on the "Create New Tag" Button.
						if(type_of_user== 0) {
							if(number_of_tab ==0){
								publish_regualr_tag_diffrenet_student = "RegularRecordingStu";
								validNewName = publish_regualr_tag_diffrenet_student;
							} else {
								publish_diffrenet_student_tag = "StudentRecordingStu";
								validNewName = publish_diffrenet_student_tag;
							}
						} else {
							if(number_of_tab ==0){
								publish_regular_private_tag_ins = "RegularRecordinIns";
								validNewName = publish_regular_private_tag_ins;
								
							} else {
								publish_student_private_tag_ins = "StudentRecordingIns";
								validNewName = publish_student_private_tag_ins;
							}
						}
						if(type_of_user == 0){
							tag_window.createNewTag(validNewName);
						} else {
							tag_window.createPrivateNewTag(validNewName);
						}
						//5.5.Click on the "Apply" button
						record.clickElementJS(tag_window.apply_button);
					}
					
					//5.6 sign out
					record.signOut();
					
					//5.7 Login as Instructor
					tegrity.loginCourses("User1");
					
					//5.8.Open some course for that.
					course.selectCourseThatStartingWith("Ab");
				}
				//*End of preconditions*
				//6.sign out
				record.signOut();
	
	}
	
	@Test(description = "TC10902 Verify that Students can't find Tests AND unpublished regular/other student's recordings AND other user's private tags AND other Student's bookmarks on the all courses level")
	public void test10902() throws InterruptedException
	{
		
		//7.Login as the Student from the precondition
		tegrity.loginCourses("User4");
		
		//8.Set the focus to the field with a mouse pointer.
		top_bar_helper.clickElementJS(top_bar_helper.search_box_field);
		
		//9.Search the first chapter from the recording that we mentioned in the preconditions and press ENTER.
		top_bar_helper.searchForTargetText(publish_record);
			
		//10.In case the search process takes a long time, the animated spinner icon shall be displayed within the Search results page.
		search_page.verifyLoadingSpinnerImage();	
		search_page.waitUntilSpinnerImageDisappear();
		search_page.exitInnerFrame();
		
		//11.*The recording is found* and displayed in the search results
		search_page.verifyWebElementDisplayed(search_page.title_list.get(0), "Recording name");
		
		//12.Click on the "Courses" breadcrumb & click on the Search field
		search_page.clickBackToCoursesInBreadcrumbs();
		
		for(int name_of_record = 0 ; name_of_record < 10 ;name_of_record++ ){
			
			//13.Set the focus to the field with a mouse pointer.
			top_bar_helper.clickElementJS(top_bar_helper.search_box_field);
					
			if(name_of_record == 0){
				//14.Search for the *Unpublished Regular Recording* from the precondition by *name*	
				top_bar_helper.searchForTargetText(unpublish_regular_record);
			}else if(name_of_record == 1){
				//14.Search for the *Unpublished Student Recording* from the precondition by *name*
				top_bar_helper.searchForTargetText(unpublish_student_record);
			}else if(name_of_record == 2){
				//14.Search for the *Test Recording* - +by that Student+ from the precondition by *name*
				top_bar_helper.searchForTargetText(test_student_record);
			}else if(name_of_record == 3){
				//14.Search for the *Test Recording* - +by a different Student+ from the precondition by *name*
				top_bar_helper.searchForTargetText(test_different_student);
			}else if(name_of_record == 4){
				//14.Search for the *Published Regular Recording* from the precondition *by another Student's Tag*
				top_bar_helper.searchForTargetText(publish_regualr_tag_diffrenet_student);
			}else if(name_of_record == 5){
				//14.Search for the *Published Regular Recording* from the precondition *by another Student's Bookmark*
				top_bar_helper.searchForTargetText(publish_regular_difftenet_student_bookmark);
			}else if(name_of_record == 6){
				//14.Search for the *Published Regular Recording* from the precondition by an *Instructor's Private Tag*
				top_bar_helper.searchForTargetText(publish_regular_private_tag_ins);
			}else if(name_of_record == 7){
				//14.Search for the *Published Student Recording* from the precondition *by another Student's Tag*
				top_bar_helper.searchForTargetText(publish_diffrenet_student_tag);
			}else if(name_of_record == 8){
				//14.Search for the *Published Student Recording* from the precondition by *another Student's Bookmark*
				top_bar_helper.searchForTargetText(publish_student_difftenet_student_bookmark);
			}else if(name_of_record == 9){
				//14.Search for the *Published Student Recording* from the precondition by an *Instructor's Private Tag*
				top_bar_helper.searchForTargetText(publish_student_private_tag_ins);
			}
	
			//15.In case the search process takes a long time, the animated spinner icon shall be displayed within the Search results page.
			search_page.verifyLoadingSpinnerImage();	
			search_page.waitUntilSpinnerImageDisappear();
			search_page.exitInnerFrame();
			
			//16.*It is NOT found*
			search_page.verifySearchResultIsEmpty();
			
			//17.Click on the "Courses" breadcrumb & click on the Search field
			search_page.clickBackToCoursesInBreadcrumbs();
		}
		
		//After test delete the tags and the bookmarks
		//18.sign out
		record.signOut();
				
	
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);

		
	}
	
	@Test(description = "TC10903 Verify that Students can't find Tests AND unpublished regular/other student's recordings AND other user's private tags AND other Student's bookmarks on the course level")
	public void test10903() throws InterruptedException
	{
		
		//7.Login as the Student from the precondition
		tegrity.loginCourses("User4");
		
		//8.Open the course from the precondition
		current_course = course.selectCourseThatStartingWith("Ab");		
		
		//9.Set the focus to the field with a mouse pointer.- click on the Search field
		top_bar_helper.clickElementJS(top_bar_helper.search_box_field);
				
		//9.Search the first chapter from the recording that we mentioned in the preconditions and press ENTER.
		top_bar_helper.searchForTargetText(publish_record);
					
		//10.In case the search process takes a long time, the animated spinner icon shall be displayed within the Search results page.
		search_page.verifyLoadingSpinnerImage();	
		search_page.waitUntilSpinnerImageDisappear();
		search_page.exitInnerFrame();
				
		//11.*The recording is found* and displayed in the search results
		search_page.verifyWebElementDisplayed(search_page.title_list.get(0), "Recording name");
				
		//12.Click on the course's name breadcrumb & click on the Search field
		search_page.clickBackToCourseInBreadcrumbs();
				
		for(int name_of_record = 0 ; name_of_record < 10 ;name_of_record++ ){
		
			//13.Set the focus to the field with a mouse pointer.
			top_bar_helper.clickElementJS(top_bar_helper.search_box_field);
					
			if(name_of_record == 0){
				//14.Search for the *Unpublished Regular Recording* from the precondition by *name*	
				top_bar_helper.searchForTargetText(unpublish_regular_record);
			}else if(name_of_record == 1){
				//14.Search for the *Unpublished Student Recording* from the precondition by *name*
				top_bar_helper.searchForTargetText(unpublish_student_record);
			}else if(name_of_record == 2){
				//14.Search for the *Test Recording* - +by that Student+ from the precondition by *name*
				top_bar_helper.searchForTargetText(test_student_record);
			}else if(name_of_record == 3){
				//14.Search for the *Test Recording* - +by a different Student+ from the precondition by *name*
				top_bar_helper.searchForTargetText(test_different_student);
			}else if(name_of_record == 4){
				//14.Search for the *Published Regular Recording* from the precondition *by another Student's Tag*
				top_bar_helper.searchForTargetText(publish_regualr_tag_diffrenet_student);
			}else if(name_of_record == 5){
				//14.Search for the *Published Regular Recording* from the precondition *by another Student's Bookmark*
				top_bar_helper.searchForTargetText(publish_regular_difftenet_student_bookmark);
			}else if(name_of_record == 6){
				//14.Search for the *Published Regular Recording* from the precondition by an *Instructor's Private Tag*
				top_bar_helper.searchForTargetText(publish_regular_private_tag_ins);
			}else if(name_of_record == 7){
				//14.Search for the *Published Student Recording* from the precondition *by another Student's Tag*
				top_bar_helper.searchForTargetText(publish_diffrenet_student_tag);
			}else if(name_of_record == 8){
				//14.Search for the *Published Student Recording* from the precondition by *another Student's Bookmark*
				top_bar_helper.searchForTargetText(publish_student_difftenet_student_bookmark);
			}else if(name_of_record == 9){
				//14.Search for the *Published Student Recording* from the precondition by an *Instructor's Private Tag*
				top_bar_helper.searchForTargetText(publish_student_private_tag_ins);
			}
	
			//15.In case the search process takes a long time, the animated spinner icon shall be displayed within the Search results page.
			search_page.verifyLoadingSpinnerImage();	
			search_page.waitUntilSpinnerImageDisappear();
			search_page.exitInnerFrame();
			
			//16.*It is NOT found*
			search_page.verifySearchResultIsEmpty();
			
			//17.Click on the "Courses" breadcrumb & click on the Search field
			search_page.clickBackToCourseInBreadcrumbs();
			
		}
		
		//After test delete the tags and the bookmarks
		//18.sign out
		record.signOut();
		
		
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
	
	
	}
	
	@Test(description = "TC10907 Verify that Guests can't find Tests AND unpublished regular/student recordings AND private tags AND Student bookmarks on the all courses level")
	public void test10907() throws InterruptedException

	{
		//pre condition -make the course public 
		//Login as INSTRUCTOR
		tegrity.loginCourses("User1");
		
		//1. enter to the course Ab
		current_course =course.selectCourseThatStartingWith("Ab");

		//1.1 Make course public
		record.clickOnCourseTaskThenCourseSettings();
		course_settings_page.makeSureThatMakeCoursePublicIsSelected();
		course_settings_page.clickOnOkButton();
		
		//1.2 log out
		record.signOut();
		
		//2.Login as the guest from the precondition
		tegrity.loginAsguest();
		
		//3.Open the course from the precondition
		 course.selectCourseThatStartingWith(current_course);		
		
		//4.Set the focus to the field with a mouse pointer.- click on the Search field
		top_bar_helper.clickElementJS(top_bar_helper.search_box_field);
				
		//5.Search the first chapter from the recording that we mentioned in the preconditions and press ENTER.
		top_bar_helper.searchForTargetText(publish_record);
					
		//6.In case the search process takes a long time, the animated spinner icon shall be displayed within the Search results page.
		search_page.verifyLoadingSpinnerImage();	
		search_page.waitUntilSpinnerImageDisappear();
		search_page.exitInnerFrame();
				
		//7.*The recording is found* and displayed in the search results
		search_page.verifyWebElementDisplayed(search_page.title_list.get(0), "Recording name");
				
		//8.Click on the course's name breadcrumb & click on the Search field
		search_page.clickBackToCourseInBreadcrumbs();
				
		for(int name_of_record = 0 ; name_of_record < 9 ;name_of_record++ ){
		
			//9.Set the focus to the field with a mouse pointer.
			top_bar_helper.clickElementJS(top_bar_helper.search_box_field);
					
			if(name_of_record == 0){
				//10.Search for the *Unpublished Regular Recording* from the precondition by *name*	
				top_bar_helper.searchForTargetText(unpublish_regular_record);
			}else if(name_of_record == 1){
				//10.Search for the *Unpublished Student Recording* from the precondition by *name*
				top_bar_helper.searchForTargetText(unpublish_student_record);
			}else if(name_of_record == 2){
				//10.Search for the *Test Recording* - +by that Student+ from the precondition by *name*
				top_bar_helper.searchForTargetText(test_student_record);
			}else if(name_of_record == 3){
				//10.Search for the Published Regular Recording from the precondition by a Student's Tag
				top_bar_helper.searchForTargetText(publish_regualr_tag_diffrenet_student);
			}else if(name_of_record == 4){
				//10.Search for the Published Regular Recording from the precondition by a Student's Bookmark
				top_bar_helper.searchForTargetText(publish_regular_difftenet_student_bookmark);
			}else if(name_of_record == 5){
				//10.Search for the Published Regular Recording from the precondition by an Instructor's Private Tag
				top_bar_helper.searchForTargetText(publish_regular_private_tag_ins);
			}else if(name_of_record == 6){
				//10.Search for the Published Student Recording from the precondition by a Student's Tag
				top_bar_helper.searchForTargetText(publish_diffrenet_student_tag);
			}else if(name_of_record == 7){
				//10.Search for the Published Regular Recording from the precondition by a Student's Bookmark
				top_bar_helper.searchForTargetText(publish_student_difftenet_student_bookmark);
			}else if(name_of_record == 8){
				//10.Search for the *Published Student Recording* from the precondition by an *Instructor's Private Tag*
				top_bar_helper.searchForTargetText(publish_student_private_tag_ins);
			}
	
			//11.In case the search process takes a long time, the animated spinner icon shall be displayed within the Search results page.
			search_page.verifyLoadingSpinnerImage();	
			search_page.waitUntilSpinnerImageDisappear();
			search_page.exitInnerFrame();
			
			//12.*It is NOT found*
			search_page.verifySearchResultIsEmpty();
			
			//13.Click on the "Courses" breadcrumb & click on the Search field
			search_page.clickBackToCourseInBreadcrumbs();
			
		}
		
		//After test delete the tags and the bookmarks
		//14.sign out
		record.signOut();
		
		//15.login as INSTRUCTOR
		tegrity.loginCourses("User1");
		
		//16. enter to the public course
		course.selectCourseThatStartingWith("Ab");
						
		// Make course public
		record.clickOnCourseTaskThenCourseSettings();
		course_settings_page.makeSureThatMakeCoursePublicIsUnSelected();
		course_settings_page.clickOnOkButton();
		
		
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
	
	
	}

	@Test(description = "TC10908 Verify that Guests can't find Tests AND unpublished regular/student recordings AND private tags AND Student bookmarks on the course level")
	public void test10908() throws InterruptedException
	{
		//pre condition -make the course public 
		//Login as INSTRUCTOR
		tegrity.loginCourses("User1");
		
		//1. enter to the course Ab
		current_course =course.selectCourseThatStartingWith("Ab");

		//1.1 Make course public
		record.clickOnCourseTaskThenCourseSettings();
		course_settings_page.makeSureThatMakeCoursePublicIsSelected();
		course_settings_page.clickOnOkButton();
		
		//1.2 log out
		record.signOut();
		
		//2.Login as guest
		tegrity.loginAsguest();
		
		//3.Open the course from the precondition
		current_course = course.selectCourseThatStartingWith("Ab");		
		
		//4.Set the focus to the field with a mouse pointer.- click on the Search field
		top_bar_helper.clickElementJS(top_bar_helper.search_box_field);
				
		//5.Search the first chapter from the recording that we mentioned in the preconditions and press ENTER.
		top_bar_helper.searchForTargetText(publish_record);
					
		//6.In case the search process takes a long time, the animated spinner icon shall be displayed within the Search results page.
		search_page.verifyLoadingSpinnerImage();	
		search_page.waitUntilSpinnerImageDisappear();
		search_page.exitInnerFrame();
				
		//7.*The recording is found* and displayed in the search results
		search_page.verifyWebElementDisplayed(search_page.title_list.get(0), "Recording name");
				
		//8.Click on the course's name breadcrumb & click on the Search field
		search_page.clickBackToCourseInBreadcrumbs();
				
		for(int name_of_record = 0 ; name_of_record < 9 ;name_of_record++ ){
		
			//9.Set the focus to the field with a mouse pointer.
			top_bar_helper.clickElementJS(top_bar_helper.search_box_field);
					
			if(name_of_record == 0){
				//10.Search for the *Unpublished Regular Recording* from the precondition by *name*	
				top_bar_helper.searchForTargetText(unpublish_regular_record);
			}else if(name_of_record == 1){
				//10.Search for the *Unpublished Student Recording* from the precondition by *name*
				top_bar_helper.searchForTargetText(unpublish_student_record);
			}else if(name_of_record == 2){
				//10.Search for the *Test Recording* - +by that Student+ from the precondition by *name*
				top_bar_helper.searchForTargetText(test_student_record);
			}else if(name_of_record == 3){
				//10.Search for the Published Regular Recording from the precondition by a Student's Tag
				top_bar_helper.searchForTargetText(publish_regualr_tag_diffrenet_student);
			}else if(name_of_record == 4){
				//10.Search for the *Published Regular Recording* from the precondition *by another Student's Bookmark*
				top_bar_helper.searchForTargetText(publish_regular_difftenet_student_bookmark);
			}else if(name_of_record == 5){
				//10.Search for the *Published Regular Recording* from the precondition by an *Instructor's Private Tag*
				top_bar_helper.searchForTargetText(publish_regular_private_tag_ins);
			}else if(name_of_record == 6){
				//10.Search for the *Published Student Recording* from the precondition by Student's Tag
				top_bar_helper.searchForTargetText(publish_diffrenet_student_tag);
			}else if(name_of_record == 7){
				//10.Search for the *Published Student Recording* from the precondition by student's Bookmark
				top_bar_helper.searchForTargetText(publish_student_difftenet_student_bookmark);
			}else if(name_of_record == 8){
				//10.Search for the *Published Student Recording* from the precondition by an *Instructor's Private Tag*
				top_bar_helper.searchForTargetText(publish_student_private_tag_ins);
			}
	
			//11.In case the search process takes a long time, the animated spinner icon shall be displayed within the Search results page.
			search_page.verifyLoadingSpinnerImage();	
			search_page.waitUntilSpinnerImageDisappear();
			search_page.exitInnerFrame();
			
			//12.*It is NOT found*
			search_page.verifySearchResultIsEmpty();
			
			//13.Click on the "Courses" breadcrumb & click on the Search field
			search_page.clickBackToCourseInBreadcrumbs();
			
		}
		
		//After test delete the tags and the bookmarks
		//14.sign out
		record.signOut();
				
		//15.login as INSTRUCTOR
		tegrity.loginCourses("User1");
		
		//16. enter to the public course
		course.selectCourseThatStartingWith("Ab");
						
		// Make course public
		record.clickOnCourseTaskThenCourseSettings();
		course_settings_page.makeSureThatMakeCoursePublicIsUnSelected();
		course_settings_page.clickOnOkButton();
		
		
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
	
	
	}
}
