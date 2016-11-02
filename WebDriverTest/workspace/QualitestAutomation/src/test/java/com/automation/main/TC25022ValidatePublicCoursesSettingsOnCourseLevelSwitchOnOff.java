package com.automation.main;



import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;import com.automation.main.page_helpers.Page;import com.automation.main.page_helpers.Page;
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
import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;


@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC25022ValidatePublicCoursesSettingsOnCourseLevelSwitchOnOff {

	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}

	public TC25022ValidatePublicCoursesSettingsOnCourseLevelSwitchOnOff() {
		// TODO Auto-generated constructor stub
	}

	public PlayerPage player_page;
	public ManageAdHocCoursesMembershipWindow mange_ad_hoc_courses_membership_window;
	public ManageAdhocCoursesEnrollmentsPage manage_adhoc_courses_enrollments_page;
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
	public ManageAdhocUsersPage mange_adhoc_users_page;
	public CreateNewUserWindow create_new_user_window;
	public AdminCourseSettingsPage admin_course_settings_page;

	@BeforeClass
	public void setup() {

		driver = DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());

		
		ATUReports.setWebDriver(driver);

		tegrity = PageFactory.initElements(driver, LoginHelperPage.class);

		record = PageFactory.initElements(driver, RecordingHelperPage.class);
		copy = PageFactory.initElements(driver, CopyMenu.class);
		
		mange_adhoc_users_page = PageFactory.initElements(driver, ManageAdhocUsersPage.class);
		create_new_user_window = PageFactory.initElements(driver, CreateNewUserWindow.class);

		confirm_menu = PageFactory.initElements(driver, ConfirmationMenu.class);

		move_window = PageFactory.initElements(driver, MoveWindow.class);

		delete_menu = PageFactory.initElements(driver, DeleteMenu.class);

		course_settings_page = PageFactory.initElements(driver, CourseSettingsPage.class);

		admin_dashboard_page = PageFactory.initElements(driver, AdminDashboardPage.class);

		top_bar_helper = PageFactory.initElements(driver, TopBarHelper.class);
		admin_dashboard_view_course_list = PageFactory.initElements(driver, AdminDashboardViewCourseList.class);
		manage_adhoc_courses_enrollments_page = PageFactory.initElements(driver, ManageAdhocCoursesEnrollmentsPage.class);
		mange_ad_hoc_courses_membership_window = PageFactory.initElements(driver, ManageAdHocCoursesMembershipWindow.class);
		player_page = PageFactory.initElements(driver, PlayerPage.class);
		admin_course_settings_page = PageFactory.initElements(driver, AdminCourseSettingsPage.class);
		wait = new WebDriverWait(driver, 30);
		
//		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		
		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC25022ValidatePublicCoursesSettingsOnCourseLevelSwitchOnOff at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC25022ValidatePublicCoursesSettingsOnCourseLevelSwitchOnOff at " + DateToStr,
		 "Starting the test: TC25022ValidatePublicCoursesSettingsOnCourseLevelSwitchOnOff at " + DateToStr, LogAs.PASSED, null);	
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
	public void loginCourses() throws Exception
	{
		// 1. Make sure to have two users which are enrolled to the same course (abc), first as INSTRUCTOR (User1) and the other as STUDENT (User4).
		// 2. Log in as INSTRUCTOR.
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
		
		//pretest make sure that the public is visable
		tegrity.loginAdmin("Admin");	
		Thread.sleep(Page.TIMEOUT_TINY);
		
		admin_dashboard_page.clickOnTargetSubmenuCourses("Manage Course Settings");		
		Thread.sleep(Page.TIMEOUT_TINY);		
		
		admin_course_settings_page.makeSureThatLockMakeThisCoursePublicUnSelected();
		admin_course_settings_page.clickOnSaveButton();
		
		admin_course_settings_page.waitForVisibility(driver.findElement(By.id("SignOutLink")));
		driver.findElement(By.id("SignOutLink")).click();
		
		tegrity.loginCourses("User1");// log in courses page
		initializeCourseObject();
		
		// 3. Click on the course from the precondition.
		String selected_course_name = course.selectCourseThatStartingWith("abc");
		
		
		// 4. Click on "Course Tasks"-->"Course Settings".
		record.clickOnCourseTaskThenCourseSettings();
		
		
		// 5. Check the option "Make this course publicly visible".
		course_settings_page.makeSureThatMakeCoursePublicIsSelected();
		
		
		// 6. Click OK.
		course_settings_page.clickOnOkButton();
		
		// 7. Click on the Courses breadcrumb.
		record.returnToCourseListPage();
		
		// 8. Click on "Public Courses".
		course.clickOnPublicCoursesTab();
		
		Thread.sleep(Page.TIMEOUT_TINY);
		
		// 9. Make sure the course is displayed in the the 'Public Courses' tab.
		course.verifyCourseExist(selected_course_name);
		
		// 10. Make sure the course is displayed in the the 'Active Courses' tab.
		course.clickOnActiveCoursesTabButton();
		course.verifyCourseExist(selected_course_name);
		
		// 11. Sign Out.
		top_bar_helper.clickOnSignOut();
		
		// 12. Log in as Student (From Precondition).
		tegrity.loginCourses("User4");
		
		// 13. Make sure the course is displayed in the the 'Active Courses' tab.
		course.verifyCourseExist(selected_course_name);
		
		// 14. Make sure the course is displayed in the the 'Public Courses' tab.
		course.clickOnPublicCoursesTab();
		Thread.sleep(Page.TIMEOUT_TINY);
		course.verifyCourseExist(selected_course_name);
		
		// 15. Sign Out.
		top_bar_helper.clickOnSignOut();
		
		// 16. Log in as Instructor.
		tegrity.loginCourses("User1");
		
		// 17. Click on "Public Courses".
		course.clickOnPublicCoursesTabButton();
		Thread.sleep(Page.TIMEOUT_TINY);
		
		// 18. Click on the same course as before.
		course.selectCourseThatStartingWith(selected_course_name);
		
		// 19. Click on "Course Tasks"-->"Course Settings".
		record.clickOnCourseTaskThenCourseSettings();
		
		// 20. Uncheck the option "Make this course publicly visible".
		course_settings_page.makeSureThatMakeCoursePublicIsUnSelected();
		
		// 21. Click OK.
		course_settings_page.clickOnOkButton();
		
		// 22. Click on the Courses breadcrumb.
		record.returnToCourseListPage();
		
		// 23. Make sure the course is displayed in the the 'Active Courses' tab.
		course.verifyCourseExist(selected_course_name);
		
		// 24. Make sure the course isn't displayed in the the 'Public Courses' tab.
		course.verifyThatTargetCourseIsNotExistInPublicCourses(selected_course_name);
		
		// 25. Sign Out.
		top_bar_helper.clickOnSignOut();
		
		// 26. Log in as Student (From Precondition).
		tegrity.loginCourses("User4");
		
		// 27. Make sure the course is displayed in the 'Active Courses' tab.
		List<String> CourseList = course.getCourseList();
		
		course.verifyCourseExistWithCourseList(selected_course_name,CourseList);
		
		// 28. Make sure the course isn't displayed in the the 'Public Courses' tab.
		course.verifyThatTargetCourseIsNotExistInPublicCourses(selected_course_name);
		
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
		
}}
