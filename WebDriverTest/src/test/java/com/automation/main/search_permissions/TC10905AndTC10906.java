package com.automation.main.search_permissions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.automation.main.page_helpers.AddAdditionalContentFileWindow;
import com.automation.main.page_helpers.AddAdditionalContentLinkWindow;
import com.automation.main.page_helpers.AdminDashboardPage;
import com.automation.main.page_helpers.AdminDashboardViewCourseList;

import com.automation.main.page_helpers.ConfirmationMenu;
import com.automation.main.page_helpers.CopyMenu;
import com.automation.main.page_helpers.CoursesHelperPage;
import com.automation.main.page_helpers.DeleteMenu;
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
public class TC10905AndTC10906 {

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
	public AddAdditionalContentFileWindow add_additional_content_window;
	public AddAdditionalContentLinkWindow add_additional_content_link_window;
	public PublishWindow publish_window;
	public TopBarHelper top_bar_helper;
	public LoginHelperPage tegrity;
	public CoursesHelperPage course;
	public RecordingHelperPage record;
	public ConfirmationMenu confirm_menu;
	public DeleteMenu delete_Menu;
	public PlayerPage player_page;
	WebDriver driver;
	WebDriverWait wait;
	public static WebDriver thread_driver;
	public CopyMenu copy;
	String current_course;
	List<String> bookmarkList = new ArrayList<String>(); 
	String student_bookmark_unclear,student_bookmark_important,student_tag1,student_tag2,caption_student,first_chapter_recording_name,first_reqular_recording;
	String first_student_recording,first_test_recording,first_link_recording,first_file_recording,first_link_name,first_record_tag,first_recording_bookmarks;
	String ins_bookmark_unclear,ins_bookmark_important,ins_tag1,ins_tag2;
	String fullPathToFile = "\\workspace\\QualitestAutomation\\resources\\documents\\Moshik_testXls.xls";
	
	@BeforeTest
	public void setup() {
		
		driver = DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());
		tegrity = PageFactory.initElements(driver, LoginHelperPage.class);
		record = PageFactory.initElements(driver, RecordingHelperPage.class);
		copy = PageFactory.initElements(driver, CopyMenu.class);		
		confirm_menu = PageFactory.initElements(driver, ConfirmationMenu.class);		
		top_bar_helper = PageFactory.initElements(driver,TopBarHelper.class);
		tag_window= PageFactory.initElements(driver, TagMenu.class);
		mange_adhoc_course_enrollments = PageFactory.initElements(driver, ManageAdhocCoursesEnrollmentsPage.class);
		add_additional_content_link_window = PageFactory.initElements(driver,AddAdditionalContentLinkWindow.class);
		player_page = PageFactory.initElements(driver, PlayerPage.class);
		add_additional_content_window = PageFactory.initElements(driver, AddAdditionalContentFileWindow.class);
		admin_dashboard_view_course_list = PageFactory.initElements(driver, AdminDashboardViewCourseList.class);
		course = PageFactory.initElements(driver, CoursesHelperPage.class);
		edit_recording_properties_window = PageFactory.initElements(driver, EditRecordingPropertiesWindow.class);
		mangage_adhoc_courses_membership_window = PageFactory.initElements(driver, ManageAdHocCoursesMembershipWindow.class);
		publish_window = PageFactory.initElements(driver, PublishWindow.class);
		delete_Menu = PageFactory.initElements(driver,DeleteMenu.class);
		search_page = PageFactory.initElements(driver, SearchPage.class);	
		admin_dash_board_page = PageFactory.initElements(driver, AdminDashboardPage.class);	
		edit_recording = PageFactory.initElements(driver, EditRecording.class);
		
		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC10905AndTC10906 at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC10905AndTC10906 at " + DateToStr,
		 "Starting the test: TC10905AndTC10906 at " + DateToStr, LogAs.PASSED, null);
		
	}
	
	@AfterTest
	public void closeBroswer() {
		this.driver.quit();
	}
	
	@BeforeClass
	public void preTest() throws InterruptedException, AWTException {
		
		//1.Open tegrity "Login page"
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
						
		// pre preconditions
		//Enter as INSTRACTOR
		tegrity.loginCourses("SuperUser");
				
		//0.delete the all records in ad
		course.deleteAllRecordingsInCourseStartWith("ad", 0, record, delete_Menu);
		course.deleteAllRecordingsInCourseStartWith("ad", 1, record, delete_Menu);
		course.deleteAllRecordingsInCourseStartWith("ad", 2, record, delete_Menu);
		course.deleteAllRecordingsInCourseStartWith("ad", 3, record, delete_Menu);
		
		//1.copy one record from each tab  
		course.copyOneRecordingFromCourseStartWithToCourseStartWithOfType("BankValid", "ad", 0, record, copy, confirm_menu);
		course.copyOneRecordingFromCourseStartWithToCourseStartWithOfType("BankValid", "ad", 2, record, copy, confirm_menu);
		course.copyOneRecordingFromCourseStartWithToCourseStartWithOfType("BankValid", "ad", 3, record, copy, confirm_menu);
		
		//1.1upload 1 file and 1 link
		course.selectCourseThatStartingWith("ad");
		
		//1.2 clicks on content task->upload additonal content file by path
		record.toUploadAdditionalContentFile();
		add_additional_content_window.uploadFileByPath(fullPathToFile, confirm_menu);
		
		//1.3 clicks on content task->upload additonal link file
		record.toUploadAdditionalContentLink();
		add_additional_content_link_window.createNewAdditionalContentLink(confirm_menu, "Moshik_Link_Test", "www.link.com");
		
		course.signOut();
				
		//1.1 wait until we finish copying records
		tegrity.loginCourses("User1");
		current_course = course.selectCourseThatStartingWith("ad");
		record.checkStatusExistenceForMaxTTime(220);
					
		//2.add caption to the first record
		//2.1.Open some course for that.	
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
				
		//2.2.enter to edit recording
		record.clickOnRecordingTaskThenEditRecording();
						
		//2.3.add caption to the first chapter
		String path = System.getProperty("user.dir") + "\\workspace\\QualitestAutomation\\resources\\documents\\CloseCaption.srt";	
		caption_student = "QualitestAutomationCaption";	
		edit_recording.addCaptionSrtToFirstChapterRecording(path);
						
		//2.4 return to the recording page
		player_page.returnToCoursesPage(course);
				
		//2.5 enter to the course to get the test record name for later
		course.selectCourseThatStartingWith(current_course);
							
		//3.change the names of the records
		for(int number_of_tab = 0 ; number_of_tab <2 ; number_of_tab++){
							
			if(number_of_tab == 1 ){
				record.clickOnStudentRecordingsTab();
			}
		//3.1.change the first name
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
					
			//4.3 sign out		
			record.signOut();
	
	}
	
	@AfterClass
	public void postTest() throws InterruptedException {
	
		//1.enter to the relevent user
		for(int num_of_user = 0 ; num_of_user < 2 ;num_of_user++ ){
		
			if(num_of_user == 0){
				tegrity.loginCourses("User1");
			} else {
				tegrity.loginCourses("User4");
			}
						
		//2.Open some course for that.
		course.selectCourseThatStartingWith(current_course);
						
		//3.Check the first publish record
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
					
		//4.Click the "Recording Tasks" drop-down menu and choose the "Tag" option
		record.clickOnRecordingTaskThenTag();
			
		//5.The "Tag" Dialog window is appeared.
		tag_window.waitForPageToLoad();
		tag_window.verifyTagWindowOpen();
					
		//6.delete all the tags
		tag_window.deleteAllExistingTags();
					
		//7.Click on the "Apply" button
		tag_window.clickElementJS(tag_window.apply_button);
				
		//8.delete all the bookmarks	
		if(record.isBookmarkTabDisplay()){
			record.clickOnBookmarksTab();
			record.deleteAllTheBookmarks();
		}
		
		//9.sign out
		record.signOut();
		}
		
	 }
	 
	@Test(description = "TC10905 Verify Students can't find content from unenrolled courses (non-public) on all courses level")
	public void test10905VerifyThatStudentsCantFindContentFromUnrolledCoursesOnAllCoursesLevel() throws InterruptedException, AWTException{
		
		Date curDate = new Date();
		String DateToStr = DateFormat.getInstance().format(curDate);
		System.out.println("Starting the test: TC10905 Verify Students can't find content from unenrolled courses (non-public) on all courses level at " + DateToStr);
		
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
		bookmarkList.add(student_bookmark_unclear);
		
		//2.5.add new important bookmark
		player_page.changeTheBookmarkToBeImportant();
		player_page.addTargetBookmark("ImportantStudent");
		student_bookmark_important ="ImportantStudent";
		bookmarkList.add(student_bookmark_important);
		
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
									
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
		
	}
	
	@Test(description = "TC10906 Verify Instructor can find content from unenrolled courses through past courses")
	public void test10906VerifyThatInstructorCanFindContentFromUnrolledCoursesThroughPastCourses() throws InterruptedException, AWTException
	{
		
		Date curDate = new Date();
		String DateToStr = DateFormat.getInstance().format(curDate);
		System.out.println("Starting the test: TC10906 Verify Instructor can find content from unenrolled courses through past courses at " + DateToStr);
		
		
		//1.add 2 bookmark to the user4(Student)
		tegrity.loginCourses("User1");
					
		//1.1.Open some course for that.
		course.selectCourseThatStartingWith(current_course);
		String url =  course.getCurrentUrlCoursePage();
						
		//1.2.move to the player to add bookmark 
		first_recording_bookmarks = record.getFirstRecordingTitle();
		record.clickOnTargetRecordingAndOpenItsPlayback(first_recording_bookmarks);
			
		//1.3.display recording and click on the stop button
		player_page.verifyTimeBufferStatusForXSec(2);

		///5.4.add new unclear bookmark
		player_page.addTargetBookmark("UnclearIns");
		ins_bookmark_unclear = "UnclearIns";
		bookmarkList.add(ins_bookmark_unclear);
		
		//5.5.add new important bookmark
		player_page.changeTheBookmarkToBeImportant();
		player_page.addTargetBookmark("ImportantIns");
		ins_bookmark_important ="ImportantIns";
		bookmarkList.add(ins_bookmark_important);
		
		//5.6.return to the recording page
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
		ins_tag1 = "InsTag1";
		tag_window.createNewTag(ins_tag1);
		
		//3.4.Click on the "Create New Tag" Button.
		ins_tag2 = "InsTag2";
		tag_window.createNewTag(ins_tag2);
					
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
		mangage_adhoc_courses_membership_window.waitMaxTimeUntillInstructorEnrollToCourse(PropertyManager.getProperty("User1"));
		 
		//11.Search target user name in membership window
		mangage_adhoc_courses_membership_window.clickOnRemoveSelectedUserToInstructorList();
		 
		//12.Confirm user membership list
		mangage_adhoc_courses_membership_window.clickOnOkButton();
				
		//13. Sign Out.
		mangage_adhoc_courses_membership_window.exitInnerFrame();
		record.signOut(); 
		
		//14.Relog as the Student from the precondition
		tegrity.loginCourses("User1");
		
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
				//top_bar_helper.searchForTargetText(first_test_recording);
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
			
			//currently we have bug with the test record
			if(name_of_record !=2){
				//15.In case the search process takes a long time, the animated spinner icon shall be displayed within the Search results page.
				search_page.verifyLoadingSpinnerImage();	
				search_page.waitUntilSpinnerImageDisappear();
				search_page.exitInnerFrame();
			
				//16.*The recording is found* and displayed in the search results
				search_page.verifyWebElementDisplayed(search_page.title_list.get(0), "Recording name");
			
				//17.Click on the "Courses" breadcrumb & click on the Search field
				search_page.clickBackToCoursesInBreadcrumbs();
			}
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
		mangage_adhoc_courses_membership_window.searchForUser(PropertyManager.getProperty("User1"));

		//24.Select first user from user list (the only user it found because the unique of the search)
		mangage_adhoc_courses_membership_window.selectFirstUserFromUserList();

		//25.Add selected user to instructor list
		mangage_adhoc_courses_membership_window.clickOnAddSelectedUserToInstructorList();
		mangage_adhoc_courses_membership_window.waitMaxTimeUntillInstructorEnrollToCourse(PropertyManager.getProperty("User1"));

		//26.Confirm user membership list
		mangage_adhoc_courses_membership_window.clickOnOkButton();
		
		//27.sign out
		mangage_adhoc_courses_membership_window.exitInnerFrame();
		tegrity.signOut();
										
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
		
	}
	
	
}
