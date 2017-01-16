package com.automation.main.search_permissions;

import java.util.Date;
import org.openqa.selenium.WebDriver;
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
import com.automation.main.page_helpers.EditRecording;
import com.automation.main.page_helpers.EditRecordingPropertiesWindow;
import com.automation.main.page_helpers.LoginHelperPage;
import com.automation.main.page_helpers.ManageAdHocCoursesMembershipWindow;
import com.automation.main.page_helpers.ManageAdhocCoursesEnrollmentsPage;
import com.automation.main.page_helpers.PlayerPage;
import com.automation.main.page_helpers.PublishWindow;
import com.automation.main.page_helpers.RecordingHelperPage;
import com.automation.main.page_helpers.SearchPage;
import com.automation.main.page_helpers.TagMenu;
import com.automation.main.page_helpers.TopBarHelper;
import com.automation.main.utilities.DriverSelector;

import java.awt.AWTException;
import java.text.DateFormat;
import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import junitx.util.PropertyManager;



@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC10905VerifyStudentsCantFindContentFromUnenrolledCoursesNonpublicOnAllCoursesLevel {

	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	} 
	
	public EditRecording edit_recording;
	public EditRecordingPropertiesWindow edit_recording_properties_window;
	public AdminDashboardPage admin_dash_board_page;
	public SearchPage search_page;
	public TagMenu tag_window;
	public AdminDashboardViewCourseList admin_dashboard_view_course_list;
	public ManageAdhocCoursesEnrollmentsPage mange_adhoc_course_enrollments;
	public ManageAdHocCoursesMembershipWindow mangage_adhoc_courses_membership_window;
	public PublishWindow publish_window;
	public TopBarHelper top_bar_helper;
	public LoginHelperPage tegrity;
	public CoursesHelperPage course;
	public RecordingHelperPage record;
	public ConfirmationMenu confirm_menu;
	public PlayerPage player_page;
	WebDriver driver;
	WebDriverWait wait;
	public static WebDriver thread_driver;
	public CopyMenu copy;
	String current_course;
	String validNewName;
	String student_bookmark_unclear,student_bookmark_important,student_tag1,student_tag2,caption_student,first_chapter_recording_name,first_reqular_recording;
	String first_student_recording,first_test_recording,first_link_recording,first_file_recording,first_link_name,first_record_tag,first_recording_bookmarks;


	@BeforeClass
	public void setup() {
		
		driver = DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());
		tegrity = PageFactory.initElements(driver, LoginHelperPage.class);
		record = PageFactory.initElements(driver, RecordingHelperPage.class);
		copy = PageFactory.initElements(driver, CopyMenu.class);		
		confirm_menu = PageFactory.initElements(driver, ConfirmationMenu.class);		
		top_bar_helper = PageFactory.initElements(driver,TopBarHelper.class);
		tag_window= PageFactory.initElements(driver, TagMenu.class);
		mange_adhoc_course_enrollments = PageFactory.initElements(driver, ManageAdhocCoursesEnrollmentsPage.class);
		player_page = PageFactory.initElements(driver, PlayerPage.class);
		admin_dashboard_view_course_list = PageFactory.initElements(driver, AdminDashboardViewCourseList.class);
		course = PageFactory.initElements(driver, CoursesHelperPage.class);
		edit_recording_properties_window = PageFactory.initElements(driver, EditRecordingPropertiesWindow.class);
		mangage_adhoc_courses_membership_window = PageFactory.initElements(driver, ManageAdHocCoursesMembershipWindow.class);
		publish_window = PageFactory.initElements(driver, PublishWindow.class);
		search_page = PageFactory.initElements(driver, SearchPage.class);	
		admin_dash_board_page = PageFactory.initElements(driver, AdminDashboardPage.class);	
		edit_recording = PageFactory.initElements(driver, EditRecording.class);
		
		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC10905VerifyStudentsCantFindContentFromUnenrolledCoursesNonpublicOnAllCoursesLevel at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC10905VerifyStudentsCantFindContentFromUnenrolledCoursesNonpublicOnAllCoursesLevel at " + DateToStr,
		 "Starting the test: TC10905VerifyStudentsCantFindContentFromUnenrolledCoursesNonpublicOnAllCoursesLevel at " + DateToStr, LogAs.PASSED, null);
		
	}
	
	@AfterClass
	public void closeBroswer() {
		this.driver.quit();
	}
	
	@Test(description = "TC10905 Verify Students can't find content from unenrolled courses (non-public) on all courses level")
	public void test10905() throws InterruptedException, AWTException
	{
		//1.Open tegrity "Login page"
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
				
		// pre preconditions
		//1.add caption to the first record
		tegrity.loginCourses("User1");
		
		//1.1.Open some course for that.
		current_course = course.selectCourseThatStartingWith("abc");
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
		
		//1.2.enter to edit recording
		record.clickOnRecordingTaskThenEditRecording();
				
		//1.3.add caption to the first chapter
		String path = System.getProperty("user.dir") + "\\workspace\\QualitestAutomation\\resources\\documents\\CloseCaption.srt";	
		caption_student = "QualitestAutomationCaption";	
		edit_recording.addCaptionSrtToFirstChapterRecording(path);
				
		//1.4 return to the recording page
		player_page.returnToCoursesPage(course);
		
		//1.5 enter to the course to get the test record name for later
		course.selectCourseThatStartingWith(current_course);
					
		//change the names of the records
		for(int number_of_tab = 0 ; number_of_tab <2 ; number_of_tab++){
					
			if(number_of_tab == 1 ){
				record.clickOnStudentRecordingsTab();
			}
			//1.6.change the first name
			record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
			edit_recording_properties_window.changeNameOfRecord(record,confirm_menu);
		}
			
		//4.get one name of record in each 
		record.clickOnRecordingsTab();
		for(int number_of_tab = 0 ; number_of_tab < 4 ; number_of_tab ++){
			
			if(number_of_tab == 0){
				//4.1.get the first regular recording title 
				first_reqular_recording = record.getFirstRecordingTitle();	
			} else if(number_of_tab == 1){
				//4.1.get the first student recording title 
				record.clickOnStudentRecordingsTab();
				first_student_recording = record.getFirstRecordingTitle();
			} else if(number_of_tab ==2) {		
				record.clickOnAdditionContentTab();					
				//4.1.get the first link and first name link title 
				record.convertAdditionalContantListToNames();
				int index = record.getIndexOfFirstLinkAdditionalContent();	
				first_link_recording = record.getNameTargetIndexAdditionalContent(index);
				first_link_name = record.getLinkIndexAdditionalContent(index);
				
				//4.1.get the first file and first name file title 
				index = record.getIndexOfFirstFileAdditionalContent();
				first_file_recording = record.getNameTargetIndexAdditionalContent(index);
			} else if(number_of_tab ==3) {			
				//4.2.get the first test recording title 
				record.clickOnTestsTab();
				first_test_recording = record.getFirstRecordingTitleTest();
			}	
		}
			
		//1.7 sign out		
		record.signOut();
		
		//2.add 2 bookmark to the user4(Student)
		tegrity.loginCourses("User4");
					
		//2.1.Open some course for that.
		course.selectCourseThatStartingWith(current_course);
		String url =  course.getCurrentUrlCoursePage();
						
		//2.2.move to the player to add bookmark 
		first_recording_bookmarks = record.getFirstRecordingTitle();
		record.clickOnTargetRecordingAndOpenItsPlayback(first_recording_bookmarks);
			
		//2.3.display recording and click on the stop button
		player_page.verifyTimeBufferStatusForXSec(2);

		///2.4.add new unclear bookmark
		player_page.addTargetBookmark("UnclearStudent");
		student_bookmark_unclear = "UnclearStudent";
		
		//2.5.add new important bookmark
		player_page.changeTheBookmarkToBeImportant();
		player_page.addTargetBookmark("ImportantStudent");
		student_bookmark_important ="ImportantStudent";
		
		//2.6.return to the recording page
		player_page.exitInnerFrame();
		player_page.returnToRecordingPageByNameAsUserOrGuest(current_course,record);			
		
		//3.add 2 tags to user4(Student) 
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
		first_record_tag = record.getFirstRecordingTitle();
		
		//3.1.Click the "Recording Tasks" drop-down menu and choose the "Tag" option
		record.clickOnRecordingTaskThenTag();

		//3.2.The "Tag" Dialog window is appeared.
		tag_window.waitForPageToLoad();
		tag_window.verifyTagWindowOpen();
		
		//3.3.Click on the "Create New Tag" Button.
		student_tag1 = "StudentTag1";
		tag_window.createNewTag(student_tag1);
		
		//3.4.Click on the "Create New Tag" Button.
		student_tag2 = "StudentTag2";
		tag_window.createNewTag(student_tag2);
					
		//3.5.Click on the "Apply" button
		record.clickElementJS(tag_window.apply_button);
		
		//4.take chapter name for later use	
		record.verifyFirstExpandableRecording();
		first_chapter_recording_name = record.getfirstChapterOfFirstRecord();
					
		//5.sign out
		record.signOut();
		
		//6.Log in as Admin
		tegrity.loginAdmin("Admin");

		//7.Click on Manage "Ad-hoc Courses / Enrollments (Course Buil under the "Courses" sedtionder)
		admin_dash_board_page.clickOnTargetSubmenuCourses("Manage Ad-hoc Courses / Enrollments (Course Builder)");	
		mange_adhoc_course_enrollments.waitForThePageToLoad();
			
		//8.Unenroll the student from the course from the precondition
		mange_adhoc_course_enrollments.searchAndFilterCourses(current_course);
		
		//9. Click on result first course (the only one) membership button
		mange_adhoc_course_enrollments.clickOnFirstCourseMembershipButton();
		
		//10.Select user from student list
		mangage_adhoc_courses_membership_window.waitMaxTimeUntillStudentEnrollToCourse(PropertyManager.getProperty("User4"));
		 
		//11.Search target user name in membership window
		mangage_adhoc_courses_membership_window.clickOnRemoveSelectedUserToStudentsList();
		 
		//12.Confirm user membership list
		mangage_adhoc_courses_membership_window.clickOnOkButton();
				
		//13. Sign Out.
		mangage_adhoc_courses_membership_window.exitInnerFrame();
		record.signOut(); 
		
		//14.Relog as the Student from the precondition
		tegrity.loginCourses("User4");
		
		for(int name_of_record = 0 ; name_of_record < 9 ;name_of_record++ ){
			
			//15.Set the focus to the field with a mouse pointer.
			top_bar_helper.clickElementJS(top_bar_helper.search_box_field);
					
			if(name_of_record == 0){
				//16.Search for the recording name from one of the recordings from the course from the precondition and press ENTER.
				top_bar_helper.searchForTargetText(first_reqular_recording);
			}else if(name_of_record == 1){
				//16.Click in the search field and search for one of the student recordings from the course from the precondition and press ENTER. 
				top_bar_helper.searchForTargetText(first_student_recording);
			}else if(name_of_record == 2){
				//16.Click in the search field and search for one of the tests from the course from the precondition and press ENTER. 
				top_bar_helper.searchForTargetText(first_test_recording);
			}else if(name_of_record == 3){
				//16.Click in the search field and search for one of the additional content files from the course from the precondition and press ENTER. 
				top_bar_helper.searchForTargetText(first_file_recording);
			}else if(name_of_record == 4){
				//16.Click in the search field and search for one of the additional content links from the course from the precondition and press ENTER. 
				top_bar_helper.searchForTargetText(first_link_recording);
			}else if(name_of_record == 5){
				//16.Click in the search field and search for one of the additional content links title from the course from the precondition and press ENTER. 
				top_bar_helper.searchForTargetText(first_link_name);
			}else if(name_of_record == 6){
				//16.Click in the search field and search for one of the recordings tags from the course from the precondition and press ENTER. 
				top_bar_helper.searchForTargetText(first_record_tag);
			}else if(name_of_record == 7){
				//16.Click in the search field and search for one of the recordings bookmarks from the course from the precondition and press ENTER. 
				top_bar_helper.searchForTargetText(first_recording_bookmarks);
			}else if(name_of_record == 8){
				//16.Click in the search field and search for one of the recordings captions text from the course from the precondition and press ENTER.
				top_bar_helper.searchForTargetText(caption_student);
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
		
		//18.sign out
		record.signOut();
		
		//post test - roll the student back to the course	
		//19.Log in as Admin
		tegrity.loginAdmin("Admin");

		//20.Click on Manage "Ad-hoc Courses / Enrollments (Course Buil under the "Courses" sedtionder)
		admin_dash_board_page.clickOnTargetSubmenuCourses("Manage Ad-hoc Courses / Enrollments (Course Builder)");	
		mange_adhoc_course_enrollments.waitForThePageToLoad();
					
		//21.Unenroll the student from the course from the precondition
		mange_adhoc_course_enrollments.searchAndFilterCourses(current_course);
		
		//22.Click on result first course (the only one) membership button
		mange_adhoc_course_enrollments.clickOnFirstCourseMembershipButton();
				
		//23.Search target user name in membership window
		mangage_adhoc_courses_membership_window.searchForUser(PropertyManager.getProperty("User4"));

		//24.Select first user from user list (the only user it found because the unique of the search)
		mangage_adhoc_courses_membership_window.selectFirstUserFromUserList();

		//25.Add selected user to instructor list
		mangage_adhoc_courses_membership_window.clickOnAddSelectedUserToStudentList();
		mangage_adhoc_courses_membership_window.waitMaxTimeUntillStudentEnrollToCourse(PropertyManager.getProperty("User4"));

		//26.Confirm user membership list
		mangage_adhoc_courses_membership_window.clickOnOkButton();
		
		//27.sign out
		mangage_adhoc_courses_membership_window.exitInnerFrame();
		tegrity.signOut();
		
		//28.login 
		tegrity.loginCourses("User4");
			
		//29.Open some course for that.
		course.selectCourseThatStartingWith(current_course);
				
		//30.Check the first publish record
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
			
		//31.Click the "Recording Tasks" drop-down menu and choose the "Tag" option
		record.clickOnRecordingTaskThenTag();
	
		//32.The "Tag" Dialog window is appeared.
		tag_window.waitForPageToLoad();
		tag_window.verifyTagWindowOpen();
			
		//33.delete all the tags
		tag_window.deleteAllExistingTags();
			
		//34.Click on the "Apply" button
		tag_window.clickElementJS(tag_window.apply_button);
		
		//35.delete all the bookmarks	
		record.clickOnBookmarksTab();					
		record.deleteBookmarkInBookmarkTab(student_bookmark_unclear);
		record.deleteBookmarkInBookmarkTab(student_bookmark_important);
								
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
		
	}
	
}
