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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC6363DeleteRecordingWithBookmarksAndVerifyTheBookmarksDisappearFromTheBookmarkTab {

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
	List<String> bookmarksName = new ArrayList<String>();
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
		 System.out.println("Starting the test: TC6363DeleteRecordingWithBookmarksAndVerifyTheBookmarksDisappearFromTheBookmarkTab at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC6363DeleteRecordingWithBookmarksAndVerifyTheBookmarksDisappearFromTheBookmarkTab at " + DateToStr, "Starting the test: TC6363DeleteRecordingWithBookmarksAndVerifyTheBookmarksDisappearFromTheBookmarkTab at " + DateToStr, LogAs.PASSED, null);	
	}

	@AfterClass
	public void closeBroswer() {
		this.driver.quit();
	}
		
	// @Parameters({"web","title"}) in the future
	@Test (description="TC6363 Delete recording with bookmarks and verify the bookmarks disappear from the bookmark tab")
	public void test6363() throws InterruptedException {
		
		//1.Enter the university
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);	
		
		//2.pre test - Login as INSTRUCTOR 
		tegrity.loginCourses("User1");
		
		//3.Click on certain course from the courses list.
		course.selectCourseThatStartingWith("Ab");
		
		//0 - for regular recording ,1- for student user
		for(int type_of_user = 0; type_of_user < 2; type_of_user++) {
		
		//*+preconditions:+*Recordings add unclear and important bookmarks
		if(type_of_user == 1){
			record.clickOnStudentRecordingsTab();		
		}
			
		//4.open the recording for watching 
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
		bookmarksName.add(bookmark_to_add);
		player_page.exitInnerFrame();
			
		//8. sign out
		player_page.signOut();
				
		//9. Login as INSTRUCTOR 
		tegrity.loginCourses("User1");
	
		//10.Click on the course that mentioned in the preconditions
		course.selectCourseThatStartingWith("Ab");
		
		if(type_of_user == 1){
			record.clickOnStudentRecordingsTab();		
		}
		
		//11.Check a recording with at least one bookmark
		record.verifyIndexRecordingHaveBookmark(1);
		String record_name = record.getFirstRecordingTitle();
		
		//12.click on the first checkbox
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
		
		//13.select "Recording tasks >>Delete" and
		record.clickOnRecordingTaskThenDelete();
		
		//14.click on the "delete" button in the Delete dialog window 
		delete_menu.clickOnDeleteButton();
				
		//15.The recording disappears from the recordings list
		record.verifyThatTargetRecordingNotExistInRecordingList(record_name);
				
		//16.Click on the Bookmarks tab after the recording disappears from the recordings list
		record.refresh();
		record.waitForThePageToLoad();
		
		//17. if the bookmark tab is display we check that our bookmark is not appeared
		if(record.isBookmarkTabDisplay()){			
			
			//18.Click on bookmark tab
			record.clickOnBookmarksTab();
		
			//19. verify that the out bookamrk is not display in the bookmark tab
			record.verifyBookmarkIsNotDisplay(record_name);
			} 
		
		}
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
	}		
}

