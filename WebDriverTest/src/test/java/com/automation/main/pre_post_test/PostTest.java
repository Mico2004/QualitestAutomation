package com.automation.main.pre_post_test;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.automation.main.page_helpers.*;
import org.testng.annotations.AfterClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.automation.main.utilities.DriverSelector;

import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import junitx.util.PropertyManager;
import utils.WaitDriverUtility;

@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class PostTest {

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
	public AdminDashboardViewCourseList view_page;

	@BeforeClass
	public void setup() {

		// System.setProperty("webdriver.edge.driver",
		// "src/test/resources/MicrosoftWebDriver.exe");
		// capability=DesiredCapabilities.internetExplorer();
		// capability.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING,false);
		//

		driver = DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());


		ATUReports.setWebDriver(driver);
		tegrity = PageFactory.initElements(driver, LoginHelperPage.class);

		record = PageFactory.initElements(driver, RecordingHelperPage.class);
		copy = PageFactory.initElements(driver, CopyMenu.class);
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
		view_page=PageFactory.initElements(driver, AdminDashboardViewCourseList.class);

		wait = new WebDriverWait(driver, 30);
		move_window = PageFactory.initElements(driver, MoveWindow.class);
		erp_window = PageFactory.initElements(driver, EditRecordingPropertiesWindow.class);

		Date curDate = new Date();
		String DateToStr = DateFormat.getInstance().format(curDate);
		System.out.println("Starting the test: PostTest at " + DateToStr);
		ATUReports.add("Message window.", "Starting the test: PostTest at " + DateToStr,
				"Starting the test: PostTest at " + DateToStr, LogAs.PASSED, null);
	}

	@AfterClass
	public void closeBroswer() {
		driver.quit();
	}



	// description = "get courses list"
	public void initializeCourseObject() throws InterruptedException {

		course = PageFactory.initElements(driver, CoursesHelperPage.class);
		course.courses = course.getCoursesListFromElement(course.course_list);
	}

	@Test( description = "PostTest")
	public void loginCourses() throws InterruptedException {
		// 1. Login with SuperUser.
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
		Thread.sleep(2000);

		tegrity.loginAdmin("Admin");

		Thread.sleep(2000);

		admin_dashboard_page.clickOnTargetSubmenuCourses("Manage Ad-hoc Courses / Enrollments (Course Builder)");
		for(int i=0; i<10; i++) {
			try {
				driver.switchTo().frame(0);
				break;
			} catch(Exception msg) {
				Thread.sleep(1000);
			}
		}


		// gets a list of the local properties courses
		List<String> created_course_list = new ArrayList<String>();

		for(int i=1;i<12; i++){
			if(i!=6 && i!=7)
				created_course_list.add(PropertyManager.getProperty("course"+i));

		}
		created_course_list.add(PropertyManager.getProperty("automationMultipleEnrollmentCourse Name"));




			int i=0;

		// delete all courses that are not in the local properties from the manage ad hock courses page
		while(mange_adhoc_course_enrollments.CourseIsDisplayedByIndex(i)){

				System.out.println("while");

				if(!mange_adhoc_course_enrollments.CourseByIndexNameEqualsToOneOfaList(created_course_list,i))
					mange_adhoc_course_enrollments.clickOnCourseDeleteButtonByIndex(i);
				else
					i++;


			}

		mange_adhoc_course_enrollments.setFilterSearchBox("bankvalid");

		mange_adhoc_course_enrollments.clickOnFilterButton();

		String bankCourseName=mange_adhoc_course_enrollments.first_course_name.getText();


		for(String window: driver.getWindowHandles()) {
			driver.switchTo().window(window);
			break;
		}




		mange_adhoc_course_enrollments.clickOnAdminDashboard();

		Thread.sleep(5000);

		// gets a list of the local properties users
		 i=0;

		List<String> created_user_list = new ArrayList<String>();

		for(i=1;i<6; i++){

			created_user_list.add(PropertyManager.getProperty("User"+i));

		}

		created_user_list.add("automationStudentUser1");

		created_user_list.add("automationInstructorUser1");

		admin_dashboard_page.clickOnTargetSubmenuUsers("Manage Ad-hoc Users (User Builder)");

		// 3. Click on create course href link
		for(int z=0; z<5; z++ ) {
			try {
				driver.switchTo().frame(0);
				break;
			} catch(Exception msg) {
				Thread.sleep(1000);
			}
		}

		i=0;

		Thread.sleep(3000);

		// delete all users that are not in the local properties from the manage ad hock users page
		while(mange_adhoc_users_page.userIsDisplayedByIndex(i)){

			System.out.println("while");

			if(!mange_adhoc_users_page.userByIndexNameEqualsToOneOfaList(created_user_list,i))
				mange_adhoc_users_page.clickOnUserDeleteButtonByIndex(i);
			else
				i++;

			Thread.sleep(1000);
		}


		for(String window: driver.getWindowHandles()) {
			driver.switchTo().window(window);
			break;
		}

		// remove all memberships that are not in the local properties from the bank course membership dialog

/*  DONT UNCOMMENT THIS !!!
		mange_adhoc_course_enrollments.clickOnFirstCourseMembershipButton();

		i=1;
		Thread.sleep(10000);

		while(mangage_adhoc_courses_membership_window.isMembershipDisplayedByIndex(i)){



			System.out.println("while");

			if(!mangage_adhoc_courses_membership_window.MembershipByIndexIdEqualsToOneOfaList(created_user_list,i)) {

				mangage_adhoc_courses_membership_window.selectUserFromUserListByIndex(i);

				String text=mangage_adhoc_courses_membership_window.getUserTextFromUserListByIndex(i);

				mangage_adhoc_courses_membership_window.clickOnRemoveSelectedUserToInstructorList();

				mangage_adhoc_courses_membership_window.waitForInstructorNotToBeSelected(i);
			}
			else {
				i++;
			}
		}


DONT UNCOMMENT THIS !!!  */
		Thread.sleep(5000);

		// purge all recordings from recordings which are not in the local properties courses



		mange_adhoc_course_enrollments.clickOnAdminDashboard();

		admin_dashboard_page.clickOnTargetSubmenuCourses("View Course List");

		// course1=Abawsserverautomation-qa-119102016124444_Name

		view_page.waitForVisibility(view_page.pageingDropDown);

		view_page.waitForVisibility(view_page.first_course_link);

		view_page.pageingDropDown.click();

		view_page.waitForVisibility(view_page.pageingDropDown_thirdOption);

		view_page.pageingDropDown_thirdOption.click();

		view_page.waitForVisibility(view_page.first_course_link);

		String courseName=PropertyManager.getProperty("course1").split("_")[0];

		String courseDate=courseName.split("-")[2];

		view_page.setElementText(view_page.course_name_textbox,courseDate);

		wait.until(ExpectedConditions.elementToBeClickable(view_page.nameFilterButton));

		view_page.nameFilterButton.click();

		view_page.waitForVisibility(view_page.doNotContainFilterOption);

		Thread.sleep(1000);

		view_page.doNotContainFilterOption.click();

		view_page.waitForVisibility(view_page.first_course_link);

		Thread.sleep(2500);

		view_page.first_checkbox.click();

		view_page.checkOrUncheckCourseCheckbox(false,bankCourseName);

		view_page.purge();

		Thread.sleep(10000);

		ATUReports.add("Post Test Done","Done","Done", LogAs.PASSED, null);
	

	
	}
}
