package com.automation.main.pre_post_test;

import java.text.DateFormat;
import java.util.Date;

import junitx.util.PropertyManager;
import org.jfree.ui.about.SystemProperties;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ReporterConfig;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import com.automation.main.page_helpers.AdminDashboardPage;
import com.automation.main.page_helpers.ConfirmationMenu;
import com.automation.main.page_helpers.CopyMenu;
import com.automation.main.page_helpers.CoursesHelperPage;
import com.automation.main.page_helpers.CreateNewCourseWindow;
import com.automation.main.page_helpers.CreateNewUserWindow;
import com.automation.main.page_helpers.DeleteMenu;
import com.automation.main.page_helpers.EditRecordingPropertiesWindow;
import com.automation.main.page_helpers.LoginHelperPage;
import com.automation.main.page_helpers.ManageAdHocCoursesMembershipWindow;
import com.automation.main.page_helpers.ManageAdhocCoursesEnrollmentsPage;
import com.automation.main.page_helpers.ManageAdhocUsersPage;
import com.automation.main.page_helpers.MoveWindow;
import com.automation.main.page_helpers.RecordingHelperPage;
import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;

@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TestSuitePreSetCopyRecordings_PastCoursesSm {

	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}

	public LoginHelperPage tegrity;
	public CoursesHelperPage course;
	public RecordingHelperPage record;
	public ConfirmationMenu confirm_menu;
	public DeleteMenu delete_menu;
	public MoveWindow move_window;
	WebDriver driver;
	WebDriverWait wait;
	public static WebDriver thread_driver;
	CopyMenu copy;
	String currentCourse;
	String targetCourse;
	String clickedRecording;
	DesiredCapabilities capability;
	public AdminDashboardPage admin_dashboard_page;
	public ManageAdhocCoursesEnrollmentsPage mange_adhoc_course_enrollments;
	public CreateNewCourseWindow create_new_course_window;
	public ManageAdhocUsersPage mange_adhoc_users_page;
	public CreateNewUserWindow create_new_user_window;
	public ManageAdHocCoursesMembershipWindow mangage_adhoc_courses_membership_window;
	public EditRecordingPropertiesWindow erp_window;
	String PastCourse="";

	@BeforeClass
	public void setup() {

		System.setProperty("webdriver.chrome.driver", "c:/selenium-drivers/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
	
		ATUReports.setWebDriver(driver);
		tegrity = PageFactory.initElements(driver, LoginHelperPage.class);
		record = PageFactory.initElements(driver, RecordingHelperPage.class);
		copy = PageFactory.initElements(driver, CopyMenu.class);
		course = PageFactory.initElements(driver, CoursesHelperPage.class);
		delete_menu = PageFactory.initElements(driver, DeleteMenu.class);
		confirm_menu = PageFactory.initElements(driver, ConfirmationMenu.class);
		move_window = PageFactory.initElements(driver, MoveWindow.class);
		mange_adhoc_course_enrollments = PageFactory.initElements(driver, ManageAdhocCoursesEnrollmentsPage.class);
		create_new_course_window = PageFactory.initElements(driver, CreateNewCourseWindow.class);
		mange_adhoc_users_page = PageFactory.initElements(driver, ManageAdhocUsersPage.class);
		create_new_user_window = PageFactory.initElements(driver, CreateNewUserWindow.class);
		admin_dashboard_page = PageFactory.initElements(driver, AdminDashboardPage.class);
		mangage_adhoc_courses_membership_window = PageFactory.initElements(driver,
				ManageAdHocCoursesMembershipWindow.class);

		wait = new WebDriverWait(driver, 30);
		move_window = PageFactory.initElements(driver, MoveWindow.class);
		erp_window = PageFactory.initElements(driver, EditRecordingPropertiesWindow.class);
		
		Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TestSuitePreSetCopyRecordings_PastCoursesSm at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TestSuitePreSetCopyRecordings_PastCoursesSm " + DateToStr,
		 "Starting the test: TestSuitePreSetCopyRecordings_PastCoursesSm at " + DateToStr, LogAs.PASSED, null);
	
	}

	@AfterClass
	public void closeBroswer() {
		this.driver.quit();
	}

	@Test(description = "Login course page")
	public void loginCourses() throws InterruptedException {
		
		//1.Enter the university
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);

		//2.Login as INSTRUCTOR
		tegrity.loginCourses("User1");

		boolean existInPastList=false;

		for(int i=10; i<12;i++) {

			existInPastList=false;
			if(i==10)
				PastCourse= PropertyManager.getProperty("course10");
			else
				PastCourse= PropertyManager.getProperty("course11");

			if(!course.verifyCourseExistWithCourseList(PastCourse, course.getCourseList())) {

				existInPastList=true;
				//adding one recording to the pastCourseA
				//1.Click on the 'Past Courses' tab*
				course.clickOnPastCoursesTabButton();

			//2.Select the past course
				course.selectCourseThatStartingWith(PastCourse);

			//3.move the course to active courses
				record.clickOnCourseTaskThenMoveToActiveCourses();

			//4.click on the ok after moving to active courses
				confirm_menu.clickOnOkButtonAfterMoveToPastCoursesOrActiveCourses("The course was successfully moved to active courses");

			//5.return to the courses page
				record.returnToCourseListPage();
			}
			//6.copy on record to pastcoursesA
			course.copyOneRecordingFromCourseStartWithToCourseStartWithOfType("Ab", PastCourse, 0, record, copy, confirm_menu);

			//7.Select the past course
			course.selectCourseThatStartingWith(PastCourse);

			//8.wait until the moving will finish
			record.checkStatusExistenceForMaxTTime(220);


				//9.move to pass courses
			record.clickOnCourseTaskThenMoveToPastCourses();

				//10.click on the ok after moving to past courses
			confirm_menu.clickOnOkButtonAfterMoveToPastCoursesOrActiveCourses("The course was successfully moved to past courses");

			record.returnToCourseListPage();

		}
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);

	}
}
