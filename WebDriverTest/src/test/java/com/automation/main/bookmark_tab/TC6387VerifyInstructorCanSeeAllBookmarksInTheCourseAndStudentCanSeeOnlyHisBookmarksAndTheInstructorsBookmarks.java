package com.automation.main.bookmark_tab;

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
import com.automation.main.page_helpers.DeleteMenu;
import com.automation.main.page_helpers.EditRecordingPropertiesWindow;
import com.automation.main.page_helpers.LoginHelperPage;
import com.automation.main.page_helpers.MoveWindow;
import com.automation.main.page_helpers.PlayerPage;
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
import java.util.Hashtable;
import java.util.List;


@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC6387VerifyInstructorCanSeeAllBookmarksInTheCourseAndStudentCanSeeOnlyHisBookmarksAndTheInstructorsBookmarks {

	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}
	
	public AdminDashboardViewCourseList admin_dashboard_view_course_list;
	public LoginHelperPage tegrity;
	public CoursesHelperPage course;
	public RecordingHelperPage record;
	public PlayerPage player_page;
	public AdminDashboardPage admin_dash_board_page;
	public DeleteMenu delete_menu;
	WebDriver driver;
	WebDriverWait wait;
	public TagMenu tag_window;
	public static WebDriver thread_driver;
	public ConfirmationMenu confirm_menu;
	public CopyMenu copy;
	public MoveWindow move_window;
	public EditRecordingPropertiesWindow edit_recording_properties_window;
	DesiredCapabilities capability;
	String bookmark_to_add;
	String recordName,time;
	boolean isDeleteDisplay;
	Hashtable<String,String> bookmarksNameAndTime = new Hashtable<String,String>();
	List<String> bookmarksName = new ArrayList<String>();
	String course_name;
	@BeforeClass
	public void setup() {

			driver = DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());
			player_page = PageFactory.initElements(driver, PlayerPage.class);
			course = PageFactory.initElements(driver, CoursesHelperPage.class);
			admin_dash_board_page = PageFactory.initElements(driver, AdminDashboardPage.class);
			admin_dashboard_view_course_list = PageFactory.initElements(driver, AdminDashboardViewCourseList.class);
			confirm_menu = PageFactory.initElements(driver, ConfirmationMenu.class);
			tegrity = PageFactory.initElements(driver, LoginHelperPage.class);
			tag_window= PageFactory.initElements(driver, TagMenu.class);
			record = PageFactory.initElements(driver, RecordingHelperPage.class);
			copy = PageFactory.initElements(driver, CopyMenu.class);
			delete_menu = PageFactory.initElements(driver, DeleteMenu.class);
			move_window = PageFactory.initElements(driver, MoveWindow.class);
			edit_recording_properties_window = PageFactory.initElements(driver, EditRecordingPropertiesWindow.class);
			
		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC6387VerifyInstructorCanSeeAllBookmarksInTheCourseAndStudentCanSeeOnlyHisBookmarksAndTheInstructorsBookmarks at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC6387VerifyInstructorCanSeeAllBookmarksInTheCourseAndStudentCanSeeOnlyHisBookmarksAndTheInstructorsBookmarks at " + DateToStr, "Starting the test: TC6387VerifyInstructorCanSeeAllBookmarksInTheCourseAndStudentCanSeeOnlyHisBookmarksAndTheInstructorsBookmarks at " + DateToStr, LogAs.PASSED, null);	
	}

	@AfterClass
	public void closeBroswer() {
		this.driver.quit();
	}
		
	// @Parameters({"web","title"}) in the future
	@Test (description="TC6387 Verify Instructor can see all bookmarks in the course and student can see only his bookmarks and the instructors bookmarks")
	public void test6387() throws InterruptedException {
		
		//1.Enter the university
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);	
				
		for(int type_of_recording = 0; type_of_recording < 2; type_of_recording++) {
			
			bookmarksName.clear();
			bookmarksNameAndTime.clear();
			
		//0 - for regular recording ,1- for student user , 2- for another student user
		for(int type_of_user = 0; type_of_user < 3; type_of_user++) {
						
			if(type_of_user == 0){
				//2.Login as INSTRUCTOR 
				tegrity.loginCourses("User1");		
			} else if(type_of_user == 1) {
				//2.Login as STUDENT
				tegrity.loginCourses("User4");
			} else {
				//2.Login as STUDENT
				tegrity.loginCourses("User3");
			}
				
			//3.Click on the course that mentioned in the preconditions
			course_name = course.selectCourseThatStartingWith("Ab");	
			
			if(type_of_recording == 1) {
				record.clickOnStudentRecordingsTab();
			}
			
			//4.Click on the title of one of the recordings and then click on one of the chapters to play it
			recordName = record.getFirstRecordingTitle();
			record.verifyFirstExpandableRecording();
			record.clickOnTheFirstCaptherWithOutTheExpand();
				
			//5.The tegrity player is displayed and the recording began to play
			player_page.verifyTimeBufferStatusForXSec(2);

			//6.Add a bookmark with the text: "Instructor"
			if(type_of_user == 0){
				bookmark_to_add= "Instructor";
			}else if(type_of_user == 1){
				player_page.changeTheBookmarkToBeImportant();
				bookmark_to_add= "Student 1";
			} else {
				bookmark_to_add= "Student 2";
			}
			time = player_page.addTargetBookmark(bookmark_to_add);
			//8. save the bookmark name and time for later
			bookmarksName.add(bookmark_to_add);
			bookmarksNameAndTime.put(bookmark_to_add, time);
			
			//7.The bookmark is added and displayed in the seek bar on the time it created	
			player_page.verifybookmarkIsFoundInBookmarkList(bookmarksName.get(0), "Ins");
			player_page.makeSureThatTheBookmarkIsCanBeenSeeingOnTheSeekBar("instructor");
				if(type_of_user == 1 ){
					player_page.verifybookmarkIsFoundInBookmarkList(bookmarksName.get(1), "Stu");
					player_page.makeSureThatTheBookmarkIsCanBeenSeeingOnTheSeekBar("important");
			}	else if(type_of_user == 2) {
					player_page.verifybookmarkIsntFoundInBookmarkList(bookmarksName.get(1), "Stu");
					player_page.verifybookmarkIsFoundInBookmarkList(bookmarksName.get(2), "Stu");
					player_page.makeSureThatTheBookmarkIsCanBeenSeeingOnTheSeekBar("unclear");	
			}
			
		
			//9. Return to the course by clicking on the course name on the breadcrumbs
			player_page.returnToRecordingPageByNameAsUserOrGuest(course_name, record);
						
			//10.Click on the "Bookmarks" tab
			record.clickOnBookmarksTab();
			
			//11.The bookmark you added is displayed with the correct details
			if(type_of_user == 2){
				String student1 = bookmarksName.get(1);	
				//.verify that the bookmark student2 is not display
				record.verifyBookmarkIsNotDisplay(bookmarksName.get(1));
				bookmarksName.remove(1);
				//adding him back for later use
				bookmarksName.add(1,student1);
			}
			record.verifyThatAllTheBookmarksDisplayInTheBookmarkTab(bookmarksName);
				
			//12.sign out
			record.signOut();
			
			}
				
			//13.Log in as the instructor that mentioned in the preconditions
			tegrity.loginCourses("User1");
		
			//14.Click on the course that mentioned in the preconditions
			course.selectCourseThatStartingWith("Ab");
					
			//15.Click on the bookmarks tab 
			record.clickOnBookmarksTab();
			
			//15.verify all bookmarks are displayed (Instructor and Student)
			record.verifyThatAllTheBookmarksDisplayInTheBookmarkTab(bookmarksName);
			
			//The bookmarks that you created before display in the list with the correct details (title, recording name, bookmark type, right bookmark color, date)
			for(int bookmark_number = 0 ;bookmark_number < bookmarksNameAndTime.size() ;  bookmark_number++){
				
				//16. Date of the bookmark in the following format:(mm/dd/yy), the date should be between the 'x' indicator and the bookmark icon.
				record.verifyThatTheRecordingDateInTheRightFormat(record.recordings_list_date.get(bookmark_number));
				
				//17.Type of bookmark (important, unclear, instructor bookmark), Bookmark indicators are either a blue ribbon (instructor bookmark), a yellow ribbon (important), or red ribbon 
				record.verifyThatTheColorOfTheBookmarkEqualToTheType(bookmark_number);
			
				//18 static text 'recording:' followed by: recording name
				record.verifyRecordingfollowedByRecordingName(recordName,bookmark_number);
				
				//19.Type of bookmark indicator with static text '| type:' followed by: (important (red), unclear(yellow), instructor(blue))
				record.verifyTypeOfBookmarkIndicatorFollowByColor(bookmark_number);
				
				//20.Click on each bookmark link thet you created before
				time = bookmarksNameAndTime.get(bookmarksName.get(bookmark_number));
				record.clickOnTheTargetBookmark(bookmarksName.get(bookmark_number));
				
				//15.The recording starts to play from the bookmark time and is displayed in the seek bar.
				player_page.checkThatWeStartTheRecordFromThisTime(time);
				
				//16.display recording and click on the stop button
				player_page.exitInnerFrame();
				player_page.verifyTimeBufferStatusForXSec(2);
				
				//17.return to the course page
				player_page.returnToRecordingPageByNameAsUserOrGuest(course_name, record);
				
				//18.Click on the bookmarks tab 
				record.clickOnBookmarksTab();
			}
			
			//19.Sign Out =
			record.signOut();
				
			//20.Login as INSTRUCTOR 
			tegrity.loginCourses("User1");		
									
			//21.Click on certain course from the courses list.
			course.selectCourseThatStartingWith("Ab");
				
			//22.Open the 'Bookmarks' tab
			record.clickOnBookmarksTab();
				
			//23. delete all the bookmarks
			for(int bookmark_number = 0 ;bookmark_number < bookmarksNameAndTime.size() ;  bookmark_number++){
					
					record.deleteBookmarkInBookmarkTab(bookmarksName.get(bookmark_number));
				}
			
			//24.Sign Out
			record.signOut();
			}
					
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
	}
	
}