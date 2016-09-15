package com.automation.main.search_field_and_search_results_page;

//precondition student first course must have recordings in recordings tab as well as in student recordings tab


import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.DateFormat;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.automation.main.page_helpers.AddAdditionalContentLinkWindow;
import com.automation.main.page_helpers.AdminDashboardPage;
import com.automation.main.page_helpers.AdminDashboardViewCourseList;
import com.automation.main.page_helpers.BottomFooter;
import com.automation.main.page_helpers.ConfirmationMenu;
import com.automation.main.page_helpers.CopyMenu;
import com.automation.main.page_helpers.CourseSettingsPage;
import com.automation.main.page_helpers.CoursesHelperPage;
import com.automation.main.page_helpers.EditRecording;
import com.automation.main.page_helpers.LoginHelperPage;
import com.automation.main.page_helpers.RecordingHelperPage;
import com.automation.main.page_helpers.SearchPage;
import com.automation.main.page_helpers.TopBarHelper;
import com.automation.main.utilities.DriverSelector;

import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import atu.testng.reports.utils.Utils;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;


@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC18833ValidateTheSourceTypeAsLinkInSearchFieldOnTheCourseLevel {

	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}
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
//		driver=new InternetExplorerDriver(capability);
		driver = DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());
		ATUReports.add("selected browser type", LogAs.PASSED, new CaptureScreen( ScreenshotOf.DESKTOP));

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
		
		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC18833ValidateTheSourceTypeAsLinkInSearchFieldOnTheCourseLevel at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC18833ValidateTheSourceTypeAsLinkInSearchFieldOnTheCourseLevel at " + DateToStr,
		 "Starting the test: TC18833ValidateTheSourceTypeAsLinkInSearchFieldOnTheCourseLevel at " + DateToStr, LogAs.PASSED, null);	
		
	}
	
	
	@AfterClass
	public void closeBroswer() {
		driver.quit();
	}


	// @Parameters({"web","title"}) in the future
	@Test
	public void loadPage() throws InterruptedException {
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);

	}
	
	// description = "get courses list"
	public void initializeCourseObject() throws InterruptedException {

		course = PageFactory.initElements(driver, CoursesHelperPage.class);
		course.courses = course.getStringFromElement(course.course_list);
	}

	
	@Test(dependsOnMethods = "loadPage", description = "Login course page")
	public void loginCourses() throws Exception
	{
		// 1. Validate there is link in this course.  Search input specified shall be case-insensitive.
		tegrity.loginCourses("User1");
		initializeCourseObject();
		
		String current_course = course.selectCourseThatStartingWith("Ab");
		String url =  course.getCurrentUrlCoursePage(); 
		// Make course public
		record.clickOnCourseTaskThenCourseSettings();
		course_settings_page.makeSureThatMakeCoursePublicIsSelected();
		course_settings_page.clickOnOkButton();
		Thread.sleep(1000);
		
		
		// Upload additional content link
        // record.clickOnAdditionContentTab();
		record.toUploadAdditionalContentLink();
		
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyhhmmss");
		String new_additional_link_title = "newname" + sdf.format(date);
		String new_additional_link_url = "http://www." + new_additional_link_title + ".com";
		
		add_additional_content_link_window.createNewAdditionalContentLink(confirm_menu, new_additional_link_title, new_additional_link_url);	
		record.waitForVisibility(record.sign_out);
		record.signOut();
		
		
		// Looping for Student, Guest and ADMIN
		for(int type_of_user = 0; type_of_user < 4; type_of_user++) {
			if(type_of_user == 0) {
				// 2. Login as INSTRUCTOR.
				tegrity.loginCourses("User1");
			} else if (type_of_user == 1) {
				// 2. Login as Student.
				tegrity.loginCourses("User4");
			} else if (type_of_user == 2) {
				// 2. Login as guest
				tegrity.loginAsguest();
			} else {
				// 2. Login as ADMIN
				tegrity.loginAdmin("Admin");
			}
			Thread.sleep(3000);
			
			if(type_of_user < 3) {
				// 3. Open some course.
				course.selectCourseThatStartingWith(current_course);
			} else {
				// Click on "view course list" under "courses" section.
				admin_dash_board_page.clickOnTargetSubmenuCourses("View Course List");
				
				// In "All courses" page, search for Ab course.
				Thread.sleep(8000);
				admin_dashboard_view_course_list.moveToCoursesThroughGet(url);
			}
			
			
			// 4. Set the focus to the field with a mouse pointer.
			top_bar_helper.search_box_field.click();
			
			// 5. Search the URL of the link that we mentioned in the preconditions and press ENTER.
			top_bar_helper.searchForTargetText(new_additional_link_url);
			Thread.sleep(2000);
			
			// 5.1. In case the search process takes a long time, the animated spinner icon shall be displayed within the Search results page.
			search_page.verifyLoadingSpinnerImage();
			search_page.waitUntilSpinnerImageDisappear();
			
			// 5.2. The breadcrumb structure displayed as follows: "> Courses > Course name > X results found for: "search_criterion". (X seconds)".
			if(type_of_user < 3) {
				search_page.verfiyBreadcrumbStructureDisplayedAsCoursesCoursenameXresultsfound(current_course, new_additional_link_url);
			} else {
				search_page.verfiyBreadcrumbStructureDisplayedAsCoursesCoursenameXresultsfoundForAdminDashboard(current_course, new_additional_link_url);
			}
			
			// 5.3. The link icon is displayed.
			search_page.verifyLinkIconDisplayedIndexSearchResult(1);
			
			// 5.4. The course title in the format as follows: "Course: course_name.
			search_page.verifyDisplayCourseTitleForSearchInsideTargetCourse(current_course);
			
			// 5.5. The source title in the format as follows: " Source: Link".
			search_page.verifyThatSourceTitleInTheFormatSourceLink();
			
			// 5.6. The next result display below the current result in case there is next result.
			search_page.verifyThatNextResultDisplayBelowCurrentResultInCaseThereIsNextResultAddicnalCont();
			
			// 6. Hover over the link icon.
			search_page.moveToElementAndPerform(search_page.link_icon_list.get(0), driver);
			
			// 6.1. The word "Link" displayed as a hint.
			search_page.verifyWebElementHaveTargetAttributeTitle(search_page.link_icon_list.get(0), "Link");
			
			// 7. Click on the link icon.
			String current_url = driver.getCurrentUrl();
			search_page.link_icon_list.get(0).click();
			
			// 7.1. The website open in new Tab/window.
			boolean is_website_opened_in_new_tab = false;
			for(String handler: driver.getWindowHandles()) {
				driver.switchTo().window(handler);
				if(!current_url.equals(driver.getCurrentUrl())) {
					is_website_opened_in_new_tab = true;
					break;
				}
			}
			
			if(is_website_opened_in_new_tab) {
				System.out.println("Website open in new tab.");
				ATUReports.add("Website open in new tab.", "True.", "True.", LogAs.PASSED, null);
				Assert.assertTrue(true);
			} else {
				System.out.println("Website not open in new tab.");
				ATUReports.add("Website open in new tab.", "True.", "False.", LogAs.FAILED, null);
				Assert.assertTrue(false);
			}
			
			// Switch back to main window, and close new window
			driver.close();
			for(String handler: driver.getWindowHandles()) {
				driver.switchTo().window(handler);
				break;
			}
			
			// 8. Click on title of the link.
			current_url = driver.getCurrentUrl();
			search_page.title_urls_list.get(0).click();
			Thread.sleep(1000);
			
			// 8.1. The website open in new Tab/window.
			is_website_opened_in_new_tab = false;
			for(String handler: driver.getWindowHandles()) {
				driver.switchTo().window(handler);
				if(!current_url.equals(driver.getCurrentUrl())) {
					is_website_opened_in_new_tab = true;
					break;
				}
			}
			
			if(is_website_opened_in_new_tab) {
				System.out.println("Website open in new tab.");
				ATUReports.add("Website open in new tab.", "True.", "True.", LogAs.PASSED, null);
				Assert.assertTrue(true);
			} else {
				System.out.println("Website not open in new tab.");
				ATUReports.add("Website open in new tab.", "True.", "False.", LogAs.FAILED, null);
				Assert.assertTrue(false);
			}
			
			// Switch back to main window, and close new window
			driver.close();
			
			for(String handler: driver.getWindowHandles()) {
				driver.switchTo().window(handler);
				break;
			}
			if(type_of_user < 3) {
				record.returnToCourseListPage();
			}
			// Signout
			record.signOut();
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
