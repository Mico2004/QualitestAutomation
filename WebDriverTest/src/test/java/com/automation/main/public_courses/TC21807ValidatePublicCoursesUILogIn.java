package com.automation.main.public_courses;


import java.util.Date;
import java.util.List;
import java.text.DateFormat;

import org.apache.xml.dtm.ref.DTMDefaultBaseIterators.DescendantIterator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.automation.main.page_helpers.AdminCourseSettingsPage;
import com.automation.main.page_helpers.AdminDashboardPage;
import com.automation.main.page_helpers.AdminDashboardViewCourseList;
import com.automation.main.page_helpers.ConfirmationMenu;
import com.automation.main.page_helpers.CopyMenu;
import com.automation.main.page_helpers.CourseSettingsPage;
import com.automation.main.page_helpers.CoursesHelperPage;
import com.automation.main.page_helpers.CreateNewUserWindow;
import com.automation.main.page_helpers.DeleteMenu;
import com.automation.main.page_helpers.LoginHelperPage;
import com.automation.main.page_helpers.ManageAdHocCoursesMembershipWindow;
import com.automation.main.page_helpers.ManageAdhocCoursesEnrollmentsPage;
import com.automation.main.page_helpers.ManageAdhocUsersPage;
import com.automation.main.page_helpers.MoveWindow;
import com.automation.main.page_helpers.PlayerPage;
import com.automation.main.page_helpers.RecordingHelperPage;
import com.automation.main.page_helpers.TopBarHelper;
import com.automation.main.utilities.DriverSelector;

import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;

@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC21807ValidatePublicCoursesUILogIn {

	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}

	public TC21807ValidatePublicCoursesUILogIn() {
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

		// 
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
		
		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC21807ValidatePublicCoursesUILogIn at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC21807ValidatePublicCoursesUILogIn at " + DateToStr,
		 "Starting the test: TC21807ValidatePublicCoursesUILogIn at " + DateToStr, LogAs.PASSED, null);	
		
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

	@Test (description = "TC 21807 Validate Public Courses UI LogIn")
	public void test21807() throws Exception
	{
		// 1. Make sure to two users which are enrolled to the same course, onse as INSTRUCTOR and other as STUDENT (User1 and User4).
		// 2. Make sure the course is publicly visible (Click on "Manage Course Settings" in the "Course" section).
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
		tegrity.loginAdmin("Admin");	
		initializeCourseObject();	
		Thread.sleep(2000);
		
		admin_dashboard_page.clickOnTargetSubmenuCourses("Manage Course Settings");		
		Thread.sleep(2000);		
		
		admin_course_settings_page.makeSureThatLockMakeThisCoursePublicUnSelected();
		admin_course_settings_page.clickOnSaveButton();
		
		admin_course_settings_page.waitForVisibility(driver.findElement(By.id("SignOutLink")));
		driver.findElement(By.id("SignOutLink")).click();
		
		// 3. Log in as INSTRUCTOR.
		tegrity.loginCourses("User1");
		
		// 4. If "Past Courses" tab is not present, the "Public Courses" tab is displayed right to the "Active Courses" else  "Public Courses" tab is displayed to the right of "Past Courses" tab.
		course.verifyTabsOrder();
		
		// 5. Hover over "Public Courses" tab.
		// 5.1. Hint is diplayed to the user.
		// 5.2. Change the button color background.
		course.verifyHoveringOverPublicCoursesButton();
		
		// 6. Press on the "Public course" tab.
		course.clickOnPublicCoursesTab();
		
		// 7. Validate the "New Recordings" text is aligned to the right.
		// 8. Validate total quantity of new recordings in the course is displayed on the right side of the course line.
		course.verifyNewRecordingsTextAndQuantityOfNewRecordingsDisplayedOnTheRightSide();
		
		// 9. Validate the page caption ‘Courses’ is displayed at the top left of the page.
		course.verifyCoursesDisplayedTopLeft();
		
		// 10. Validate the ‘Active Courses’ tab is displayed under the page caption.
		course.verifyActiveCoursesTabDisplayedUnderThePageHeading();
		
		// 11. Validate the list of courses is sorted by their names (AaBaCc sorting is used).
		course.verifyThatListOfCoursesIsSortedByTheirNames();
		
		// 12. Validate every course record contains information as follows: course name: ‘X recording(s) – Y new | last updated: mm/dd/yyyy’.	
		Thread.sleep(2000);
		course.verifyEveryCourseRecordContainsCorrectInformation();
		
		// 13. Validate total quantity of new recordings in the course is displayed on the right side of the course line.
		course.verifyThatTotalQuantitiyNewRecordingDisplayedOnTheRightSideOfTheCourseLine();
		
		// 14. Validate when a user hovers over the course name the hint with the course name is displayed to the user.
		course.verifyThatWhenUserHoverOverFirstCourseNameTheHingWithTheCourseNameDispalyed();
		
		// 15. Sign off.
		top_bar_helper.clickOnSignOut();
		
		// 16. Click on "Login as guest" blue button (at the bottom of the login screen).
		tegrity.loginAsguest();
		Thread.sleep(2000);
		
		// 17. The courses list page is displayed (under the tab "Public Courses" which is the only one available).
		course.verifyThatCoursesListDisplayedUnderPublicCoursesWhichIsTheOnlyOneAvaiable();
		
		// 18. Validate the tag new recordings isn't displayed to the right.
		course.verifyThatTagNewRecordingsNotDisplayedToTheRightForGuestUser();
		
		// 19. Validate the page caption ‘Courses’ is displayed at the top left of the page.
		course.verifyCoursesDisplayedTopLeft();
		
		// 20. Validate the list of courses is sorted by their names (AaBaCc sorting is used).
		course.verifyThatListOfCoursesIsSortedByTheirNames();
		
		
		// 21. Validate every course record contains information as follows: course name: ‘X recording(s) – Y new | last updated: mm/dd/yyyy’.
		Thread.sleep(2000);
		course.verifyEveryCourseRecordContainsCorrectInformation();
		
		// 22. Validate total quantity of new recordings in the course isn't displayed on the right side of the course line.
		course.verifyThatTagNewRecordingsNotDisplayedToTheRightForGuestUser();
		
		// 23. Validate when a user hovers over the course name the hint with the course name is displayed to the user.
		course.verifyThatWhenUserHoverOverFirstCourseNameTheHingWithTheCourseNameDispalyed();
		
		// 24. Sign out.
		top_bar_helper.clickOnSignOut();
		
		// 25. Log in as STUDENT.
		tegrity.loginCourses("User4");
		
		// 26. If "Past Courses" tab is not present, the "Public Courses" tab is displayed right to the "Active Courses" else  "Public Courses" tab is displayed to the right of "Past Courses" tab.
		course.verifyTabsOrder();
		
		// 27. Hover over "Public Courses" tab.
		// 27.1. Hint is diplayed to the user.
		// 27.2. Change the button color background.
		course.verifyHoveringOverPublicCoursesButton();
		
		// 28. Press on the "Public course" tab.
		course.clickOnPublicCoursesTab();
		
		// 29. Validate the "New Recordings" text is aligned to the right.
		// 30. Validate total quantity of new recordings in the course is displayed on the right side of the course line.
		course.verifyNewRecordingsTextAndQuantityOfNewRecordingsDisplayedOnTheRightSide();
		
		// 31. Validate the page caption ‘Courses’ is displayed at the top left of the page.
		course.verifyCoursesDisplayedTopLeft();
		
		// 32. Validate the ‘Active Courses’ tab is displayed under the page caption.
		course.verifyActiveCoursesTabDisplayedUnderThePageHeading();
		
		// 33. Validate the list of courses is sorted by their names (AaBaCc sorting is used).
		course.verifyThatListOfCoursesIsSortedByTheirNames();
		
		
		// 34. Validate every course record contains information as follows: course name: ‘X recording(s) – Y new | last updated: mm/dd/yyyy’.
		Thread.sleep(2000);
		course.verifyEveryCourseRecordContainsCorrectInformation();
		
		// 35. Validate total quantity of new recordings in the course is displayed on the right side of the course line.
		course.verifyThatTotalQuantitiyNewRecordingDisplayedOnTheRightSideOfTheCourseLine();
		
		// 36. Validate when a user hovers over the course name the hint with the course name is displayed to the user
		course.verifyThatWhenUserHoverOverFirstCourseNameTheHingWithTheCourseNameDispalyed();
		
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
		
		
}}
