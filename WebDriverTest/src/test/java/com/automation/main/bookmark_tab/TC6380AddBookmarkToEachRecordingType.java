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
public class TC6380AddBookmarkToEachRecordingType {

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
	List<String> recordNames = new ArrayList<String>();
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
		 System.out.println("Starting the test: TC6380AddBookmarkToEachRecordingType at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC6380AddBookmarkToEachRecordingType at " + DateToStr, "Starting the test: TC6380AddBookmarkToEachRecordingType at " + DateToStr, LogAs.PASSED, null);	
	}

	@AfterClass
	public void closeBroswer() {
		this.driver.quit();
	}
		
	// @Parameters({"web","title"}) in the future
	@Test (description="TC6380 Add bookmark to each recording type")
	public void test6380() throws InterruptedException {
		
		//1.Enter the university
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);	
		
		//2.pre test - Login as INSTRUCTOR 
		tegrity.loginCourses("User1");
		
		//3.Click on certain course from the courses list.
		String course_name = course.selectCourseThatStartingWith("Ab");
		
		//0 - for regular recording ,1- for student recording ,2 - for test recording
		for(int type_of_tab = 0; type_of_tab < 3; type_of_tab++) {
		
		if(type_of_tab == 1){
			record.clickOnStudentRecordingsTab();
		} else if(type_of_tab == 2){
			record.clickOnTestsTab();
		}
		 
		//4.open the recording for watching 
		record.pressViewButtonAndSelect("Duration");
		if(type_of_tab < 2) { 		
			recordName = record.getFirstRecordingTitle();
			record.verifyFirstExpandableRecording();		
		} else{
			record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
			record.toEditRecordingPropertiesMenu();	
			edit_recording_properties_window.waitForPageToLoad();
			recordName =edit_recording_properties_window.getRecordName(confirm_menu);
			record.clickElementWithOutIdJS(record.first_course_title_tests);
		}
	
		recordNames.add(recordName);
		record.clickOnTheFirstCaptherWithOutTheExpand();
		
		//5.display recording and click on the stop button
		player_page.verifyTimeBufferStatusForXSec(2);

		///6.Add a bookmark to "Bookmarks and Notes" window
		if(type_of_tab == 0) {
			bookmark_to_add= "TestUnclearIns";
		}else if(type_of_tab == 1){
			bookmark_to_add= "TestUnclearStu";
		}else {
			bookmark_to_add= "TestUnclearTest";
		}
		time = player_page.addTargetBookmark(bookmark_to_add);
		bookmarksName.add(bookmark_to_add);
		bookmarksNameAndTime.put(bookmark_to_add,time);
		
		//7.The bookmark appears in the window and display in the seek bar.
		player_page.makeSureThatTheBookmarkIsCanBeenSeeingOnTheSeekBar("instructor");
		player_page.exitInnerFrame();
		
		//8.Click on course name breadcrumbs
		player_page.returnToRecordingPageByNameAsUserOrGuest(course_name,record);
		
		}
		
		//9.Click on the bookmarks tab 
		record.clickOnBookmarksTab();
		
		//10.verify all bookmarks are displayed (Instructor and Student)
		record.verifyThatAllTheBookmarksDisplayInTheBookmarkTab(bookmarksName);
		
		//The bookmarks that you created before display in the list with the correct details (title, recording name, bookmark type, right bookmark color, date)
		for(int bookmark_number = 0 ;bookmark_number < bookmarksNameAndTime.size() ;  bookmark_number++){
			
			//10. Date of the bookmark in the following format:(mm/dd/yy), the date should be between the 'x' indicator and the bookmark icon.
			record.verifyThatTheRecordingDateInTheRightFormat(record.recordings_list_date.get(bookmark_number));
			
			//11.Type of bookmark (important, unclear, instructor bookmark), Bookmark indicators are either a blue ribbon (instructor bookmark), a yellow ribbon (important), or red ribbon 
			record.verifyThatTheColorOfTheBookmarkEqualToTheType(bookmark_number);
		
			//12 static text 'recording:' followed by: recording name
			record.verifyRecordingfollowedByRecordingName(recordNames.get(bookmarksNameAndTime.size()-(1 +bookmark_number)),bookmark_number);
			
			//13.Type of bookmark indicator with static text '| type:' followed by: (important (red), unclear(yellow), instructor(blue))
			record.verifyTypeOfBookmarkIndicatorFollowByColor(bookmark_number);
			
			//14.Click on each bookmark link thet you created before
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
		
		//19.Sign Out
		player_page.exitInnerFrame();
		record.signOut();
		
		//20.Login as STUDENT
		tegrity.loginCourses("User4");
				
		//21.Click on certain course from the courses list.
		course.selectCourseThatStartingWith("Ab");
		
		//22.Open the 'Bookmarks' tab
		record.clickOnBookmarksTab();
		
		//23.verify that the bookmark from the test tab is not display
		record.verifyBookmarkIsNotDisplay(bookmarksName.get(2));
		
		//24. verify that all the rest are display
		String record_name_test = bookmarksName.get(2);
		bookmarksName.remove(2);
		record.verifyThatAllTheBookmarksDisplayInTheBookmarkTab(bookmarksName);
		
		//25. sign out
		record.signOut();
		
		//26.Login as INSTRACTOR
		tegrity.loginCourses("User1");
				
		//27.Click on certain course from the courses list.
		course.selectCourseThatStartingWith("Ab");
		
		//28.Open the 'Bookmarks' tab
		record.clickOnBookmarksTab();
		
		//29. delete all the bookmarks
		bookmarksName.add(record_name_test);
		for(int bookmark_number = 0 ;bookmark_number < bookmarksNameAndTime.size() ;  bookmark_number++){
			
			record.deleteBookmarkInBookmarkTab(bookmarksName.get(bookmark_number));
		}
	
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
	}
	
}