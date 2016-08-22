package com.automation.main.privatecourses;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.automation.main.AdminDashboardPage;
import com.automation.main.AdminDashboardViewCourseList;
import com.automation.main.ConfirmationMenu;
import com.automation.main.CopyMenu;
import com.automation.main.CourseSettingsPage;
import com.automation.main.CoursesHelperPage;
import com.automation.main.DeleteMenu;
import com.automation.main.DriverSelector;
import com.automation.main.LoginHelperPage;
import com.automation.main.MoveWindow;
import com.automation.main.RecordingHelperPage;
import com.automation.main.TopBarHelper;

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
	
    
    @BeforeClass
	public void setup() {

    	driver = DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());
    	
//		
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
	}
	
//	
//	@AfterClass
//	public void closeBroswer() {
//		this.driver.quit();
//	}


	
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
		String url_private_course = driver.getCurrentUrl();
		
		top_bar_helper.clickOnSignOut();
		
		
		// 1. Login as ADMIN 
		tegrity.loginAdmin("Admin");
		Thread.sleep(3000);
		// 2. Click the "Course List" link.
		admin_dashboard_page.clickOnTargetSubmenuCourses("View Course List");
		Thread.sleep(3000);
		
		// 3. Select the INSTRUCTOR's private course in the list and click it.
//		admin_dashboard_view_course_list.searchForTargetCourseName(private_course);
//		Thread.sleep(3000);
//		admin_dashboard_view_course_list.clickOnFirstCourseLink();
//		Thread.sleep(3000);
		driver.navigate().to(url_private_course);
		Thread.sleep(3000);
		
		// 4. Select the "Course Tasks -> Course Settings" menu item.
		record.clickOnCourseTaskThenCourseSettings();
		
		// 5. "Course Settings" page is displayed
		course_settings_page.verifyCourseSettingDisplay();
		Thread.sleep(2000);
		
		// 6. Check the "Make this course publicly visible" checkbox
		// 7. Checkbox is checked.
//		course_settings_page.selectMakeCoursePublicAndVerifyThatItSelected();
		
		Thread.sleep(2000);
		
		// 8. Press the "OK" button.
		course_settings_page.clickOnOkButton();
		Thread.sleep(2000);
		
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
		
		Thread.sleep(1000);
		
		// 11. Click the "Public Courses" tab.
		course.clickOnPublicCoursesTab();
		
		Thread.sleep(1000);
		
		// 12. Verify that INSTRUCTOR's private course is displayed in "Public Courses" tab.
		course.verifyCourseExist(private_course);
		
		// 13. Sign out.
		top_bar_helper.clickOnSignOut();
		
		Thread.sleep(2000);
		
		// 14. Login as Guest.	
		tegrity.loginAsguest();
		
		Thread.sleep(2000);
		
		// 15. Verify that INSTRUCTOR's private course is displayed in "Public Courses" tab.
		course.clickOnPublicCoursesTab();
		
		// 16. Open the course.
		course.selectCourseThatStartingWith(private_course);
		
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
		Thread.sleep(2000);
		course.clickOnPublicCoursesTab();
		Thread.sleep(2000);
		course.selectCourseThatStartingWith(private_course);
		Thread.sleep(2000);
		// 22. Play one of it's recordings	The recording is being played
		String recoring_to_play = record.getFirstRecordingTitle();
		record.clickOnTargetRecordingAndOpenItsPlayback(recoring_to_play);

		
		
	
	}
}
