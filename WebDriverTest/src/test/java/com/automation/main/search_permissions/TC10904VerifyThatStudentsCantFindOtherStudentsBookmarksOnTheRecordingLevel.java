package com.automation.main.search_permissions;

import java.util.Date;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import com.automation.main.page_helpers.BottomFooter;
import com.automation.main.page_helpers.ConfirmationMenu;
import com.automation.main.page_helpers.CopyMenu;
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
import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;



@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC10904VerifyThatStudentsCantFindOtherStudentsBookmarksOnTheRecordingLevel {

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
	public PlayerPage player_page;
	WebDriver driver;
	WebDriverWait wait;
	public static WebDriver thread_driver;
	public CopyMenu copy;
	String current_course;
	String validNewName;
	String difftenet_student_bookmark,student_bookmark;
	
	@BeforeClass
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
		edit_recording_properties_window = PageFactory.initElements(driver, EditRecordingPropertiesWindow.class);
		publish_window = PageFactory.initElements(driver, PublishWindow.class);
		search_page = PageFactory.initElements(driver, SearchPage.class);	
		bottom_footer = PageFactory.initElements(driver, BottomFooter.class);	
		edit_recording = PageFactory.initElements(driver, EditRecording.class);
		
		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC10904VerifyThatStudentsCantFindOtherStudentsBookmarksOnTheRecordingLevel at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC10904VerifyThatStudentsCantFindOtherStudentsBookmarksOnTheRecordingLevel at " + DateToStr,
		 "Starting the test: TC10904VerifyThatStudentsCantFindOtherStudentsBookmarksOnTheRecordingLevel at " + DateToStr, LogAs.PASSED, null);
		
	}
	
	@AfterClass
	public void closeBroswer() {
		this.driver.quit();
	}
	
	@Test(description = "TC10904 Verify that Students can't find other Student's bookmarks on the recording level")
	public void test10904() throws InterruptedException
	{
		//1.Open tegrity "Login page"
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
				
		// pre preconditions
		//1.different Student's bookmark and one of his own
		for(int number_of_user = 0 ; number_of_user <2; number_of_user++){
		
			if(number_of_user == 0){
				tegrity.loginCourses("User3");
			} else {
				tegrity.loginCourses("User4");
			}
				
		//1.1.Open some course for that.
		current_course = course.selectCourseThatStartingWith("Ab");
						
		//1.2.move to the player to add bookmark 
		record.clickOnTargetRecordingAndOpenItsPlayback(record.getFirstRecordingTitle());
			
		//1.3.display recording and click on the stop button
		player_page.verifyTimeBufferStatusForXSec(2);

		///1.4.add new unclear bookmark
		if(number_of_user == 0){
			player_page.addTargetBookmark("UnclearDiffStudent");
			difftenet_student_bookmark = "UnclearDiffStudent";
		} else {
			player_page.addTargetBookmark("UnclearStudent");
			student_bookmark ="UnclearStudent";
		}
		player_page.exitInnerFrame();
					
		//1.5.sign out
		record.signOut();
					
	}
		//*End of preconditions*		
		//2.Login as the Student from the precondition
		tegrity.loginCourses("User4");
		
		//3.Open some course for that.
		current_course = course.selectCourseThatStartingWith("Ab");
						
		//4.move to the player to add bookmark 
		record.clickOnTargetRecordingAndOpenItsPlayback(record.getFirstRecordingTitle());
			
		//5.display recording and click on the stop button
		player_page.verifyTimeBufferStatusForXSec(2);
		player_page.exitInnerFrame();

		//6.click on the Search field
		player_page.clickElementJS(player_page.search_box);
		
		//7.The bookmark is found and displayed in the search results
		player_page.verifySearchForRecordingExist(student_bookmark);
		
		//8.click on the Search field
		player_page.exitInnerFrame();
		player_page.clickElementJS(player_page.search_box);
				
		//9.*search for the other student's bookmark*
		player_page.searchRecord(difftenet_student_bookmark);
		
		//10.*It is NOT found*
		player_page.verifySearchResultIsEmpty();
		
		//10.after test - delete the bookmarks
		player_page.returnToRecordingPageByNameAsUserOrGuest(current_course, record);
		
		//11.enter to bookmark tab as Student
		record.clickOnBookmarksTab();
		
		//12.delete bookmark
		record.deleteBookmarkInBookmarkTab(student_bookmark);
		
		//13.sign out
		record.signOut();
		
		//14.enter to the other student
		tegrity.loginCourses("User3");
				
		//15.Open some course for that.
		course.selectCourseThatStartingWith("Ab");
		
		//11.enter to bookmark tab as Student
		record.clickOnBookmarksTab();
		
		//12.delete bookmark
		record.deleteBookmarkInBookmarkTab(difftenet_student_bookmark);
						
	
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);

		
	}
	
}
