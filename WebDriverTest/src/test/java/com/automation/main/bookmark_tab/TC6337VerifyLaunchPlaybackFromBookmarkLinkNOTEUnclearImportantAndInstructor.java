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
import com.automation.main.page_helpers.LoginHelperPage;
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
import java.util.Date;
import java.util.Hashtable;


@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC6337VerifyLaunchPlaybackFromBookmarkLinkNOTEUnclearImportantAndInstructor {

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
	DesiredCapabilities capability;
	String bookmark_to_add;
	String recordName;
	boolean isDeleteDisplay;
	Hashtable<String,String> bookmarksNameAndTime = new Hashtable<String,String>();
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
			
		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC6337VerifyLaunchPlaybackFromBookmarkLinkNOTEUnclearImportantAndInstructor at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC6337VerifyLaunchPlaybackFromBookmarkLinkNOTEUnclearImportantAndInstructor at " + DateToStr, "Starting the test: TC6337VerifyLaunchPlaybackFromBookmarkLinkNOTEUnclearImportantAndInstructor at " + DateToStr, LogAs.PASSED, null);	
	}

	@AfterClass
	public void closeBroswer() {
		this.driver.quit();
	}
		
	// @Parameters({"web","title"}) in the future
	@Test (description="TC6329 Verify launch playback from bookmark link  - NOTE (Unclear, Important and instructor)")
	public void test6337() throws InterruptedException {
		
		//1.Enter the university
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);	
		
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
		}else {
			bookmark_to_add= "TestUnclearStu";
		}
		player_page.addTargetBookmark(bookmark_to_add);
		String time = player_page.getTimeOfBookmark(bookmark_to_add);
		bookmarksNameAndTime.put(bookmark_to_add,time);
		
		//7.add new important bookmark
		player_page.changeTheBookmarkToBeImportant();
		if(type_of_user == 0){
			bookmark_to_add= "TestImportantIns";
		}else {
			bookmark_to_add= "TestImportantStu";
		}	
		player_page.addTargetBookmark(bookmark_to_add);
		time = player_page.getTimeOfBookmark(bookmark_to_add);
		bookmarksNameAndTime.put(bookmark_to_add,time);
		player_page.exitInnerFrame();
			
		//8. sign out
		player_page.signOut();
		
		}
		//0 - for regular recording ,1- for student user
		for(int type_of_user = 0; type_of_user < 2; type_of_user++) {
				
			if(type_of_user == 0){
					//9. Login as INSTRUCTOR 
					tegrity.loginCourses("User1");
			} else {
					//9. - Login as STUDENT
					tegrity.loginCourses("User4");
			}
		
			//10.Click on the course that mentioned in the preconditions
			String course_name = course.selectCourseThatStartingWith("Ab");
		
			//11.Click on the "Bookmarks" tab
			record.clickOnBookmarksTab();
				
			//12.Click on regular recording 'Unclear' bookmark(in instarcor the unclear and import are the same
			String bookmarkName = record.clickOnRegularRecordingBookmark("instructor bookmark");
			
			//13.The current recording should start playing from the current bookmark time
			String time  = bookmarksNameAndTime.get(bookmarkName);
			player_page.checkThatWeStartTheRecordFromThisTime(time);
			
			//14.check that the bookmark should display in blue on the seek bar
			player_page.makeSureThatTheBookmarkIsCanBeenSeeingOnTheSeekBar("instructor");
				
			//15.display recording and click on the stop button
			player_page.exitInnerFrame();
			player_page.verifyTimeBufferStatusForXSec(2);
			
			//16.Return to the bookmark tab
			player_page.returnToRecordingPageByNameAsUserOrGuest(course_name, record);
			record.clickOnBookmarksTab();
				
			//17.Click on regular recording 'important' bookmark(in instarcor the unclear and import are the same
			bookmarkName = record.clickOnRegularRecordingBookmark("instructor bookmark");
			
			//18.The current recording should start playing on the currnt bookmark time
			time  = bookmarksNameAndTime.get(bookmarkName);
			player_page.checkThatWeStartTheRecordFromThisTime(time);
			
			//19.check that the bookmark should display in blue on the seek bar
			player_page.makeSureThatTheBookmarkIsCanBeenSeeingOnTheSeekBar("instructor");
			
			//20.display recording and click on the stop button
			player_page.exitInnerFrame();
			player_page.verifyTimeBufferStatusForXSec(2);
			
			//21.Return to the bookmark tab
			player_page.returnToRecordingPageByNameAsUserOrGuest(course_name, record);
			record.clickOnBookmarksTab();
				
			//22.Click on Student recording 'Unclear' bookmark
			bookmarkName = record.clickOnRegularRecordingBookmark("unclear");
			
			//23.The current recording should start playing on the currnt bookmark time
			time  = bookmarksNameAndTime.get(bookmarkName);
			player_page.checkThatWeStartTheRecordFromThisTime(time);
			
			//24.check that the bookmark should display in red on the seek bar
			player_page.makeSureThatTheBookmarkIsCanBeenSeeingOnTheSeekBar("unclear");
			
			//25.display recording and click on the stop button
			player_page.exitInnerFrame();
			player_page.verifyTimeBufferStatusForXSec(2);
			
			//26.Return to the bookmark tab
			player_page.returnToRecordingPageByNameAsUserOrGuest(course_name, record);
			record.clickOnBookmarksTab();
				
			//27.Click on regular recording 'important' bookmark(in instarcor the unclear and import are the same
			bookmarkName = record.clickOnRegularRecordingBookmark("important");
			
			//28.The current recording should start playing on the currnt bookmark time
			time  = bookmarksNameAndTime.get(bookmarkName);
			player_page.checkThatWeStartTheRecordFromThisTime(time);
			
			//29.check that the bookmark should display in yellow on the seek bar
			player_page.makeSureThatTheBookmarkIsCanBeenSeeingOnTheSeekBar("important");
			
			//30.display recording and click on the stop button
			player_page.exitInnerFrame();
			player_page.verifyTimeBufferStatusForXSec(2);
			
			//31.sign out
			player_page.exitInnerFrame();
			record.signOut();
		}
		
		//post test delete all the bookmarks 
		//29. Login as INSTRUCTOR
		for(int type_of_user = 0; type_of_user < 2; type_of_user++) {
			
			if(type_of_user == 0){
					//9. Login as INSTRUCTOR 
					tegrity.loginCourses("User1");
			} else {
					//9. - Login as STUDENT
					tegrity.loginCourses("User4");
			}
			
		//30.Click on certain course from the courses list.
		course.selectCourseThatStartingWith("Ab");
		
		//31.open the recording for watching 
		record.verifyFirstExpandableRecording();
		record.clickOnTheFirstCaptherWithOutTheExpand();
		
		//32.display recording and click on the stop button
		player_page.verifyTimeBufferStatusForXSec(2);
		
		//33. delete all the bookmarks
		player_page.deleteAllBookmark();
		
		//34. sign out
		player_page.exitInnerFrame();
		record.signOut();
		
		}
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
	}		
}

