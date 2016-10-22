package com.automation.main.search;


import java.util.List;
import java.text.DateFormat;
import java.util.Date;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;import com.automation.main.page_helpers.Page;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.automation.main.page_helpers.AddAdditionalContentFileWindow;
import com.automation.main.page_helpers.AddAdditionalContentLinkWindow;
import com.automation.main.page_helpers.AdminDashboardPage;
import com.automation.main.page_helpers.AdminDashboardViewCourseList;
import com.automation.main.page_helpers.BottomFooter;
import com.automation.main.page_helpers.ConfirmationMenu;
import com.automation.main.page_helpers.CopyMenu;
import com.automation.main.page_helpers.CourseSettingsPage;
import com.automation.main.page_helpers.CoursesHelperPage;
import com.automation.main.page_helpers.DeleteMenu;
import com.automation.main.page_helpers.EditRecording;
import com.automation.main.page_helpers.LoginHelperPage;
import com.automation.main.page_helpers.PlayerPage;
import com.automation.main.page_helpers.RecordingHelperPage;
import com.automation.main.page_helpers.SearchPage;
import com.automation.main.page_helpers.TopBarHelper;

import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;

@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC18882ValidateTheSourceTypeAsFileInSearchFieldOnTheAllCoursesLevel {

	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}
	
	public DeleteMenu delete_menu;
	public AddAdditionalContentFileWindow add_additional_content_window;
	public PlayerPage player_page;
	public AdminDashboardViewCourseList admin_dashboard_view_course_list;
	public AdminDashboardPage admin_dash_board_page;
	public CourseSettingsPage course_settings_page;
	public AddAdditionalContentLinkWindow add_additional_content_link_window;
	public EditRecording edit_recording;
	public BottomFooter bottom_footer;
	public SearchPage search_page;
	public TopBarHelper top_bar_helper;
	public LoginHelperPage tegrity;
	public CoursesHelperPage course;
	public RecordingHelperPage record;
	public ConfirmationMenu confirm_menu;
	WebDriver driver;
	WebDriverWait wait;
	public static WebDriver thread_driver;
	CopyMenu copy;
	String current_course;
	String targetCourse;
	String clickedRecording;
    DesiredCapabilities capability;
	@BeforeClass
	public void setup() {

		
//		System.setProperty("webdriver.ie.driver", "src/test/resources/IEDriverServer.exe");
//			capability=DesiredCapabilities.internetExplorer();
//			capability.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING,true);
//			
		/*
		 * This test is Chrome only test!
		 */
		driver=new ChromeDriver();
//		driver = DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());
		ATUReports.add("selected browser type", LogAs.PASSED, new CaptureScreen( ScreenshotOf.DESKTOP));

//		

//		
		//ATUReports.setWebDriver(driver);
		//ATUReports.add("set driver", true);
		tegrity = PageFactory.initElements(driver, LoginHelperPage.class);

		record = PageFactory.initElements(driver, RecordingHelperPage.class);
		copy = PageFactory.initElements(driver, CopyMenu.class);
		
		confirm_menu = PageFactory.initElements(driver, ConfirmationMenu.class);
		
		top_bar_helper = PageFactory.initElements(driver,TopBarHelper.class);
		search_page = PageFactory.initElements(driver, SearchPage.class);
		
		bottom_footer = PageFactory.initElements(driver, BottomFooter.class);
		
		edit_recording = PageFactory.initElements(driver, EditRecording.class);
		
		add_additional_content_link_window = PageFactory.initElements(driver, AddAdditionalContentLinkWindow.class);
		course_settings_page = PageFactory.initElements(driver, CourseSettingsPage.class);
		admin_dash_board_page = PageFactory.initElements(driver, AdminDashboardPage.class);
		admin_dashboard_view_course_list = PageFactory.initElements(driver, AdminDashboardViewCourseList.class);
		player_page = PageFactory.initElements(driver, PlayerPage.class);
		
		add_additional_content_window = PageFactory.initElements(driver, AddAdditionalContentFileWindow.class);
		
		delete_menu = PageFactory.initElements(driver, DeleteMenu.class);
		
		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC18882ValidateTheSourceTypeAsFileInSearchFieldOnTheAllCoursesLevel at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC18882ValidateTheSourceTypeAsFileInSearchFieldOnTheAllCoursesLevel at " + DateToStr,
		 "Starting the test: TC18882ValidateTheSourceTypeAsFileInSearchFieldOnTheAllCoursesLevel at " + DateToStr, LogAs.PASSED, null);	
	}
	
	
	@AfterClass
	public void closeBroswer() {
		this.driver.quit();
	}

	
	// description = "get courses list"
	public void initializeCourseObject() throws InterruptedException {

		course = PageFactory.initElements(driver, CoursesHelperPage.class);
		course.courses = course.getCoursesListFromElement(course.course_list);
	}

	
	@Test(description = "TC 18882 Validate The Source Type As File In Search Field On The All Courses Level")
	public void test18882() throws Exception
	{
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);

		// 1. Validate there is file in this course. Search input specified shall be case-insensitive.
		tegrity.loginCourses("SuperUser");
		initializeCourseObject();
		
		String current_course = course.selectCourseThatStartingWith("Ab");
		
		// Make course public
		record.clickOnCourseTaskThenCourseSettings();
		course_settings_page.makeSureThatMakeCoursePublicIsSelected();
		course_settings_page.clickOnOkButton();
		Thread.sleep(Page.TIMEOUT_TINY);
		
		record.returnToCourseListPage();
		
		course.selectCourseThatStartingWith("Ab");
		course.deleteAllRecordingsInCourseStartWith("Ab", 1, record, delete_menu);
		course.selectCourseThatStartingWith("BankValid");
		Thread.sleep(Page.TIMEOUT_TINY);
		record.clickOnAdditionContentTab();
		Thread.sleep(Page.TIMEOUT_TINY);
		
		List<String> current_additional_content_list = record.getCoursAdditionalContentList();
		String target_additional_content_for_the_test = null;
		for(int i = 0; i<current_additional_content_list.size(); i++) {
			if(record.isElemenetDisplayed(By.id("ItemSize" + Integer.toString(i+1)))){
			if(driver.findElement(By.id("ItemSize" + Integer.toString(i+1))).getText().contains("file")) {
				record.selectIndexCheckBox(i+1);
				target_additional_content_for_the_test = current_additional_content_list.get(i);
				break;
			}
		  }	
		}
		
		Thread.sleep(Page.TIMEOUT_TINY);
		record.clickOnContentTaskThenCopy();
		copy.selectTargetCourseFromCourseListThatStartWith("Ab");
		Thread.sleep(Page.TIMEOUT_TINY);
		
		copy.clickOnCopyButton();
		Thread.sleep(Page.TIMEOUT_TINY);
		confirm_menu.clickOnOkButton();
		Thread.sleep(Page.TIMEOUT_TINY);
		
		System.out.println("Target additional content for the test is: " + target_additional_content_for_the_test);
		
		record.signOut();
		Thread.sleep(Page.TIMEOUT_TINY);
		
		
		// Looping for INSTRUCTOR, Student and Guest
		for(int type_of_user = 0; type_of_user < 3; type_of_user++) {
			if(type_of_user == 0) {
				// 2. Login as INSTRUCTOR.
				tegrity.loginCourses("User1");
			} else if (type_of_user == 1) {
				// 2. Login as Student.
				tegrity.loginCourses("User4");
			} else if (type_of_user == 2) {
				// 2. Login as guest
				tegrity.loginAsguest();
			}
			Thread.sleep(Page.TIMEOUT_TINY);	
			
			// 3. Set the focus to the field with a mouse pointer.
			top_bar_helper.search_box_field.click();
			
			// 4. Search some "File" and press ENTER.
			top_bar_helper.searchForTargetText(target_additional_content_for_the_test);
			Thread.sleep(Page.TIMEOUT_TINY);
			
			// 4.1. In case the search process takes a long time, the animated spinner icon shall be displayed within the Search results page.
			search_page.verifyLoadingSpinnerImage();
			search_page.waitUntilSpinnerImageDisappear();
			
			// 4.2. The breadcrumb structure displayed as follows: "> Courses > X results found for: "search_criterion". (X seconds)".
			search_page.verfiyBreadcrumbStructureDisplayedAsCoursesXResultsFound(current_course, target_additional_content_for_the_test);
			
			// 4.3. The file icon is displayed.
			search_page.verifyLinkIconDisplayedIndexSearchResult(1);
			
			// 4.4. The course title in the format as follows: "Course: course_name.
			search_page.verifyDisplayCourseTitleForSearchInsideTargetCourse(current_course);
			
			// 4.5. The source title in the format as follows: " Source: File".
			search_page.verifyThatSourceTitleForTargetRecordingInTargetFormat(target_additional_content_for_the_test, "Source: File");
			
			// 4.6. The next result display below the current result in case there is next result.
			Thread.sleep(Page.TIMEOUT_TINY);
			search_page.verifyThatNextResultDisplayBelowCurrentResultInCaseThereIsNextResultAddicnalCont();
			
			// 5. Click on the file icon.
			// 5.1. The file download to the local client's machine.
			search_page.link_icon_list.get(0).click();
			Thread.sleep(Page.TIMEOUT_TINY);
			
			
			
			// 6. Click on title of the file.
			// 6.1. The file download to the local client's machine.
			search_page.clickOnChapterTitleOfRecordingInTargetIndex(1);
			
			// 7. Sign Out.
			record.signOut();
			Thread.sleep(Page.TIMEOUT_TINY);	
		}
		
		// Unpublic Ab course1. 
		tegrity.loginCourses("User1");
						
		course.selectCourseThatStartingWith("Ab");
						
		// Make course public
		record.clickOnCourseTaskThenCourseSettings();
		course_settings_page.makeSureThatMakeCoursePublicIsUnSelected();
		course_settings_page.clickOnOkButton();
		
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
		
	}
}
