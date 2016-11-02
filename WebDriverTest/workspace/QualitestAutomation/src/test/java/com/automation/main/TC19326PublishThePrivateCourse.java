package com.automation.main;



import java.text.DateFormat;
import java.util.Date;
import org.openqa.selenium.WebDriver;import com.automation.main.page_helpers.Page;import com.automation.main.page_helpers.Page;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;


@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC19326PublishThePrivateCourse {

	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}
	public TC19326PublishThePrivateCourse() {
		// TODO Auto-generated constructor stub
	}
	public CourseSettingsPage course_settings_page;
	public TopBarHelper top_bar_helper;
	public LoginHelperPage tegrity;
	public CoursesHelperPage course;
	public RecordingHelperPage record;
	public ConfirmationMenu confirm_menu;
	public MoveWindow move_window;
	public DeleteMenu delete_menu;
	WebDriver driver;
	WebDriverWait wait;
	public static WebDriver thread_driver;
	CopyMenu copy;
	String currentCourse;
	String targetCourse;
	String clickedRecording;
    DesiredCapabilities capability;
    public AdminDashboardPage admin_dashboard_page;
    public AdminDashboardViewCourseList admin_dashboard_view_course_list;
    public PlayerPage player_page;
    public AdminCourseSettingsPage admin_course_settings_page;
    
    @BeforeClass
	public void setup() {

    	driver = DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());
    	
		
		ATUReports.setWebDriver(driver);
	
		tegrity = PageFactory.initElements(driver, LoginHelperPage.class);

		record = PageFactory.initElements(driver, RecordingHelperPage.class);
		copy = PageFactory.initElements(driver, CopyMenu.class);
		
		confirm_menu = PageFactory.initElements(driver, ConfirmationMenu.class);
		
		move_window = PageFactory.initElements(driver, MoveWindow.class);
		
		delete_menu = PageFactory.initElements(driver, DeleteMenu.class);
		
		course_settings_page = PageFactory.initElements(driver, CourseSettingsPage.class);
		
		admin_dashboard_page = PageFactory.initElements(driver, AdminDashboardPage.class);
		
		top_bar_helper = PageFactory.initElements(driver, TopBarHelper.class);
		admin_dashboard_view_course_list = PageFactory.initElements(driver, AdminDashboardViewCourseList.class);
		admin_course_settings_page = PageFactory.initElements(driver, AdminCourseSettingsPage.class);
		player_page = PageFactory.initElements(driver, PlayerPage.class);
		
		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC19326PublishThePrivateCourse at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC19326PublishThePrivateCourse at " + DateToStr,
		 "Starting the test: TC19326PublishThePrivateCourse at " + DateToStr, LogAs.PASSED, null);	
	}
	
	
	@AfterClass
	public void closeBroswer() {
		this.driver.quit();
	}

	
	// description = "get courses list"
	public void initializeCourseObject() throws InterruptedException {

		course = PageFactory.initElements(driver, CoursesHelperPage.class);
		course.courses = course.getStringFromElement(course.course_list);
	}

	@Test
	public void loginCourses() throws InterruptedException
	{
		// TODO: Replace in the end all Thread sleep with waits (WebdriverWait)
		
		// Preset login as User1 and get Private course + Logout
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
		tegrity.loginCourses("User1");// log in courses page
		initializeCourseObject();
		
		String private_course = course.selectCourseThatStartingWith("User");
		
		top_bar_helper.clickOnSignOut();
		
		
		// 1. Login as ADMIN 
		tegrity.loginAdmin("Admin");
		Thread.sleep(Page.TIMEOUT_TINY);
		
		//1.1 pretest make sure that the course will be public
		admin_dashboard_page.clickOnTargetSubmenuCourses("Manage Course Settings");		
		Thread.sleep(Page.TIMEOUT_TINY);		
		
		admin_course_settings_page.makeSureThatLockMakeThisCoursePublicUnSelected();
		admin_course_settings_page.clickOnSaveButton();
		Thread.sleep(Page.TIMEOUT_TINY);	
		
		// 2. Click the "Course List" link.
		admin_dashboard_page.clickOnTargetSubmenuCourses("View Course List");
		Thread.sleep(Page.TIMEOUT_TINY);
		
		// 3. Select the INSTRUCTOR's private course in the list and click it.
		admin_dashboard_view_course_list.searchForTargetCourseName(private_course);
		Thread.sleep(Page.TIMEOUT_TINY);
		admin_dashboard_view_course_list.clickOnFirstCourseLink();
		Thread.sleep(Page.TIMEOUT_TINY);
//		driver.navigate().to(url_private_course);
//		Thread.sleep(Page.TIMEOUT_TINY);
		
		String url_private_course = driver.getCurrentUrl();
		
		// 4. Select the "Course Tasks -> Course Settings" menu item.
		record.clickOnCourseTaskThenCourseSettings();
		
		// 5. "Course Settings" page is displayed
		course_settings_page.verifyCourseSettingDisplay();
		Thread.sleep(Page.TIMEOUT_TINY);
		
		// 6. Check the "Make this course publicly visible" checkbox
		// 7. Checkbox is checked.
		course_settings_page.makeSureThatMakeCoursePublicIsSelected();
		
		Thread.sleep(Page.TIMEOUT_TINY);
		
		// 8. Press the "OK" button.
		course_settings_page.clickOnOkButton();
		Thread.sleep(Page.TIMEOUT_TINY);
		
		// 9. USER is redirected to "Course Details" page.
		String url_of_redirected_back_to_private_course = driver.getCurrentUrl();
		
		if(url_of_redirected_back_to_private_course.equals(url_private_course)) {
			System.out.println("Redirected to course detial page successfuly.");
			ATUReports.add("Redirected back to course.", "True.", "True.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Fail to redirected to course page.");
			ATUReports.add("Redirected back to course.", "True.", "Fale.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		
		// 10. Login as INSTRUCTOR.
		top_bar_helper.clickOnSignOut();
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
		tegrity.loginCourses("User1");// log in courses page
		initializeCourseObject();
		
		Thread.sleep(Page.TIMEOUT_TINY);
		
		// 11. Click the "Public Courses" tab.
		course.clickOnPublicCoursesTab();
		
		Thread.sleep(Page.TIMEOUT_TINY);
		
		// 12. Verify that INSTRUCTOR's private course is displayed in "Public Courses" tab.
		course.verifyCourseExist(private_course);
		
		// 13. Sign out.
		top_bar_helper.clickOnSignOut();
		
		
		// 14. Login as Guest.	
		tegrity.loginAsguest();
		
		Thread.sleep(Page.TIMEOUT_TINY);
		
		// 15. Verify that INSTRUCTOR's private course is displayed in "Public Courses" tab.
		course.clickOnPublicCoursesTab();
		
		// 16. Open the course.	
		course.selectCourseThatStartingWith(private_course);

		Thread.sleep(Page.TIMEOUT_TINY);
//		course.moveToElement(driver.findElement(ById(private_course), driver)
		
		// 17. Play one of it's recordings.
		record.clickOnRecordingTitleInIndex(1);
		
		// 18. Sign out.
		top_bar_helper.clickOnSignOut();
		
		// 19. Login as Student.
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
		tegrity.loginCourses("User4");// log in courses page
		initializeCourseObject();
		
		// 20. Verify that INSTRUCTOR's private course is displayed in "Public Courses" tab.
		// 21. Open the course.
		Thread.sleep(Page.TIMEOUT_TINY);
		course.clickOnPublicCoursesTab();
		Thread.sleep(Page.TIMEOUT_TINY);
		course.selectCourseThatStartingWith(private_course);
		Thread.sleep(Page.TIMEOUT_TINY);
		
		// 22. Play one of it's recordings	The recording is being played
		String recoring_to_play = record.getFirstRecordingTitle();
		record.clickOnTargetRecordingAndOpenItsPlayback(recoring_to_play);
		player_page.verifyTimeBufferStatusForXSec(5);
		

		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
		
	}
}
