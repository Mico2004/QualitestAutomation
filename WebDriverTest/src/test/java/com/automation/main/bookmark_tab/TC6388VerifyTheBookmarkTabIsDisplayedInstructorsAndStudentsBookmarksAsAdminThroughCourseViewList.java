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
public class TC6388VerifyTheBookmarkTabIsDisplayedInstructorsAndStudentsBookmarksAsAdminThroughCourseViewList {

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
	List<String> bookmarksNameIns = new ArrayList<String>();
	List<String> bookmarksNameStu = new ArrayList<String>();
	String url;
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
		 System.out.println("Starting the test: TC6388VerifyTheBookmarkTabIsDisplayedInstructorsAndStudentsBookmarksAsAdminThroughCourseViewList at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC6388VerifyTheBookmarkTabIsDisplayedInstructorsAndStudentsBookmarksAsAdminThroughCourseViewList at " + DateToStr, "Starting the test: TC6388VerifyTheBookmarkTabIsDisplayedInstructorsAndStudentsBookmarksAsAdminThroughCourseViewList at " + DateToStr, LogAs.PASSED, null);	
	}

	@AfterClass
	public void closeBroswer() {
		this.driver.quit();
	}
		
	// @Parameters({"web","title"}) in the future
	@Test (description="Verify the bookmark tab is displayed (instructors and students bookmarks) as admin through Course View list")
	public void test6388() throws InterruptedException {
		
		//1.Enter the university
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);	
			
		for(int type_of_recording = 0; type_of_recording < 2; type_of_recording++) {
			
			bookmarksNameIns.clear();
			bookmarksNameStu.clear();
			bookmarksNameAndTime.clear();
		
		//0 - for regular recording ,1- for student user
		for(int type_of_user = 0; type_of_user < 2; type_of_user++) {
				
		//*+preconditions:+*Recordings add unclear and important bookmarks
		if(type_of_user == 0){
			//2.pre test - Login as INSTRUCTOR 
			tegrity.loginCourses("User1");
		} else {
			//2.pre test - Login as STUDENT
			tegrity.loginCourses("User4");
		}
					
		//3.Click on certain course from the courses list.
		course.selectCourseThatStartingWith("Ab");
		url =  course.getCurrentUrlCoursePage();
		
		if(type_of_recording == 1) {
			record.clickOnStudentRecordingsTab();
		}
				
		//4.open the recording for watching 
		record.pressViewButtonAndSelect("Duration");
		recordName = record.getFirstRecordingTitle();
		record.verifyFirstExpandableRecording();
		record.clickOnTheFirstCaptherWithOutTheExpand();
				
		//5.display recording and click on the stop button
		player_page.verifyTimeBufferStatusForXSec(2);

		///6.add new unclear bookmark
		if(type_of_user == 0){
			bookmark_to_add= "TestUnclearIns";
			bookmarksNameIns.add(bookmark_to_add);
		}else {
			bookmark_to_add= "TestUnclearStu";
			bookmarksNameStu.add(bookmark_to_add);
		}
		player_page.addTargetBookmark(bookmark_to_add);
		String time = player_page.getTimeOfBookmark(bookmark_to_add);
		bookmarksNameAndTime.put(bookmark_to_add,time);
				
		//7.add new important bookmark
		player_page.changeTheBookmarkToBeImportant();
		if(type_of_user == 0){
			bookmark_to_add= "TestImportantIns";
			bookmarksNameIns.add(bookmark_to_add);
		}else {
			bookmark_to_add= "TestImportantStu";
			bookmarksNameStu.add(bookmark_to_add);
		}	
		player_page.addTargetBookmark(bookmark_to_add);
		time = player_page.getTimeOfBookmark(bookmark_to_add);
		bookmarksNameAndTime.put(bookmark_to_add,time);
		player_page.exitInnerFrame();
		
		//8. sign out
		player_page.signOut();
		
		}
				
		//9.Log in as Admin
		tegrity.loginAdmin("Admin");
			
		//10.Click on "view course list" under "courses" section.
		admin_dash_board_page.clickOnTargetSubmenuCourses("View Course List");	
		
		//11. In "All courses" page, search for Ab course.
		admin_dashboard_view_course_list.waitForThePageToLoad();
		admin_dashboard_view_course_list.moveToCoursesThroughGet(url);
				
		//12.Click on the course that mentioned in the preconditions
		//course.selectCourseThatStartingWith("Ab");	
			
		//13.Click on the "Bookmarks" tab
		record.clickOnBookmarksTab();	
	
		//14.Only the instructor's bookmarks list is displayed
		record.verifyThatAllTheBookmarksDisplayInTheBookmarkTab(bookmarksNameIns);
		
		//14.1 verify that we doesn't see the students bookmarks
		record.verifyBookmarkIsNotDisplay(bookmarksNameStu.get(0));
		record.verifyBookmarkIsNotDisplay(bookmarksNameStu.get(1));
		
		//15.There is no 'X' (delete) on the right side of the date
		for(int bookmark_number = 0 ;bookmark_number < bookmarksNameIns.size() ;  bookmark_number++){
			record.verifyDeleteBookmarNotDisplay(bookmark_number);
		}
			
		//16.Click on each bookmark link that you created before
		time = bookmarksNameAndTime.get(bookmarksNameIns.get(0));
		record.clickOnTheTargetBookmark(bookmarksNameIns.get(0));
		
		//17.The recording starts to play from the bookmark time and is displayed in the seek bar.
		player_page.checkThatWeStartTheRecordFromThisTime(time);
		
		//18.The recording starts to play from the right time of the bookmark
		player_page.exitInnerFrame();
		player_page.verifyTimeBufferStatusForXSec(2);
		
		//19.The instructor bookmarks are displayed on the seek bar (instructor: blue color)
		player_page.makeSureThatTheBookmarkIsCanBeenSeeingOnTheSeekBar("instructor");
		
		//The instructor bookmarks are displayed on "Bookmarks" window 
		player_page.verifybookmarkIsFoundInBookmarkList(bookmarksNameIns.get(0), "Ins");
		player_page.verifybookmarkIsFoundInBookmarkList(bookmarksNameIns.get(1), "Ins");
		
		//20.The instructor bookmarks are displayed on "Bookmarks" window and the admin can't add or delete the bookmarks.
		player_page.VeirfyDeleteAndEditBookmarkNotDisplayInstractor();
							
		//21.Sign Out
		player_page.exitInnerFrame();
		record.signOut();
				
		//22.Login as INSTRUCTOR 
		tegrity.loginCourses("User1");		
									
		//23.Click on certain course from the courses list.
		course.selectCourseThatStartingWith("Ab");
				
		//25.Open the 'Bookmarks' tab
		record.clickOnBookmarksTab();
				
		//26. delete all the bookmarks
		for(int bookmark_number = 0 ;bookmark_number < bookmarksNameStu.size() ;  bookmark_number++){
					
			record.deleteBookmarkInBookmarkTab(bookmarksNameStu.get(bookmark_number));
		}
			
		for(int bookmark_number = 0 ;bookmark_number < bookmarksNameIns.size() ;  bookmark_number++){
				
			record.deleteBookmarkInBookmarkTab(bookmarksNameIns.get(bookmark_number));
		}
		
			
			//24.Sign Out
			record.signOut();
			}
					
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
	}
	
}