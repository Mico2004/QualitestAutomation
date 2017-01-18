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
public class TC6368MoveARecordingWithABookmarkAndVerifyTheBookmarkTabDisappearsInTheSourceCourseAndAppearsInTheDestinationCourse {

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
			move_window = PageFactory.initElements(driver, MoveWindow.class);
			
		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC6368MoveARecordingWithABookmarkAndVerifyTheBookmarkTabDisappearsInTheSourceCourseAndAppearsInTheDestinationCourse at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC6368MoveARecordingWithABookmarkAndVerifyTheBookmarkTabDisappearsInTheSourceCourseAndAppearsInTheDestinationCourse at " + DateToStr, "Starting the test: TC6368MoveARecordingWithABookmarkAndVerifyTheBookmarkTabDisappearsInTheSourceCourseAndAppearsInTheDestinationCourse at " + DateToStr, LogAs.PASSED, null);	
	}

	@AfterClass
	public void closeBroswer() {
		this.driver.quit();
	}
		
	// @Parameters({"web","title"}) in the future
	@Test (description="TC6368 Move a recording with a bookmark and verify the bookmark tab disappears in the source course and appears in the destination course")
	public void test6368() throws InterruptedException {
		
		
		//1.Enter the university
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);	
		
		//2.pre test - Login as INSTRUCTOR 
		tegrity.loginCourses("User1");
		
		//0 - for regular recording ,1- for student user
		for(int type_of_user = 0; type_of_user < 2; type_of_user++) {
		
		//3.Click on certain course from the courses list.
		String course_name = course.selectCourseThatStartingWith("Ab");
			
		//*+preconditions:+*Recordings add unclear and important bookmarks
		if(type_of_user == 1){
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
		}else {
			bookmark_to_add= "TestUnclearStu";
		}
		player_page.addTargetBookmark(bookmark_to_add);
		String time = player_page.getTimeOfBookmark(bookmark_to_add);
		bookmarksNameAndTime.put(bookmark_to_add,time);
		player_page.exitInnerFrame();
			
		//8. sign out
		player_page.signOut();
				
		//9. Login as INSTRUCTOR 
		tegrity.loginCourses("User1");
	
		//10.Click on the course that mentioned in the preconditions
		course.selectCourseThatStartingWith(course_name);
		
		if(type_of_user == 1){
			record.clickOnStudentRecordingsTab();		
		}
		
		//11.Check a recording with at least one bookmark
		record.verifyIndexRecordingHaveBookmark(1);
		
		//12.Select the recording with the bookmark icon 
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
		
		//13.Hover over the "Recording Tasks" menu 
		//14.Click on "move" option 
		record.clickOnRecordingTaskThenMove();
		
		//15.A move window dialog opens
		move_window.verifyThatMoveWindowIsOpen();
		
		//16.click on the "delete" button in the Delete dialog window 
		copy.selectTargetCourseFromCourseListThatStartWith("abc");
				
		//17.Click "Move Recording(s)" button.
		move_window.clickOnMoveRecordings();
						
		//18.Click "OK" button.
		confirm_menu.clickOnOkButtonAfterConfirmMoveRecording();
		
		//19. Message window is closed.
		confirm_menu.verifyConfirmWindowIsClosed();
		
		if(type_of_user == 1){
			record.clickOnStudentRecordingsTab();		
		}
		
		//20.verify that the status is clear
		record.checkStatusExistenceForMaxTTime(220);
		
		//21.The bokkmarks tab disappear 
		record.refresh();
		record.waitForThePageToLoad();
		record.verifyNoSBookmarkTab();
		
		//22.Click on courses breadcrumbs
		record.returnToCourseListPage();
		
		//23.Click on the course name that you moved the recording to
		course.selectCourseThatStartingWith("abc");
		
		//24.Click on the bookmarks tab
		record.clickOnBookmarksTab();
		
		//25.The recording moved to the current course and the bookmark is displayed in the bookmarks tab.
		record.verifyThatTheBookmarkDisplayInTheBookmarkTab(bookmark_to_add);
		
		//26.Click on the bookmark link 
		record.clickOnTheTargetBookmark(bookmark_to_add);
		
		//26.The current recording should start playing on the currnt bookmark time
		time  = bookmarksNameAndTime.get(bookmark_to_add);
		player_page.checkThatWeStartTheRecordFromThisTime(time);
			
		//27.display recording and click on the stop button
		player_page.exitInnerFrame();
		player_page.verifyTimeBufferStatusForXSec(2);
		
		//28.display recording and click on the stop button
		player_page.exitInnerFrame();
		player_page.returnToCoursePageByNameAsUserOrGuest(course);
		
		//post test-delete all the bookmarks
		//29.Click on the course name that you moved the recording to
		course.selectCourseThatStartingWith("abc");
				
		//30.Click on the bookmarks tab
		record.clickOnBookmarksTab();
				
		//31. delete the bookmark
		record.deleteBookmarkInBookmarkTab(bookmark_to_add);
		
		//32. return beck to the course page
		record.returnToCourseListPage();
		
		}
		
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
	}
	
}

