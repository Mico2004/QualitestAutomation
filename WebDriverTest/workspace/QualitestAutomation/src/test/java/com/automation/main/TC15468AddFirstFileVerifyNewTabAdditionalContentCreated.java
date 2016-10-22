package com.automation.main;


import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;import com.automation.main.page_helpers.Page;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import java.text.DateFormat;
import java.util.Date;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;

public class TC15468AddFirstFileVerifyNewTabAdditionalContentCreated {
	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}

	public EditRecordinPropertiesWindow erp_window;
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
	WebDriver driver2;
	public AdminDashboardPage admin_dashboard_page;
	public ManageAdhocCoursesEnrollmentsPage mange_adhoc_course_enrollments;
	public CreateNewCourseWindow create_new_course_window;
	public ManageAdhocUsersPage mange_adhoc_users_page;
	public CreateNewUserWindow create_new_user_window;
	public ManageAdHocCoursesMembershipWindow mangage_adhoc_courses_membership_window;
	public AddAdditionalContentFileWindow add_additional_content_window;
	String instructor1;
	String instructor2;
	List<String> for_enroll;
	
	@AfterClass
	public void closeBroswer() {
		this.driver.quit();
	}

	@BeforeClass
	public void setup() {

		driver = DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());
		

		tegrity = PageFactory.initElements(driver, LoginHelperPage.class);

		wait = new WebDriverWait(driver, 30);
		add_additional_content_window = PageFactory.initElements(driver, AddAdditionalContentFileWindow.class);
		record = PageFactory.initElements(driver, RecordingHelperPage.class);
		copy = PageFactory.initElements(driver, CopyMenu.class);
		delete_menu = PageFactory.initElements(driver, DeleteMenu.class);
		course = PageFactory.initElements(driver, CoursesHelperPage.class);
		confirm_menu = PageFactory.initElements(driver, ConfirmationMenu.class);

		wait = new WebDriverWait(driver, 30);
		move_window = PageFactory.initElements(driver, MoveWindow.class);
		erp_window = PageFactory.initElements(driver, EditRecordinPropertiesWindow.class);
		admin_dashboard_page = PageFactory.initElements(driver, AdminDashboardPage.class);

		mange_adhoc_course_enrollments = PageFactory.initElements(driver, ManageAdhocCoursesEnrollmentsPage.class);

		create_new_course_window = PageFactory.initElements(driver, CreateNewCourseWindow.class);

		mange_adhoc_users_page = PageFactory.initElements(driver, ManageAdhocUsersPage.class);

		create_new_user_window = PageFactory.initElements(driver, CreateNewUserWindow.class);

		mangage_adhoc_courses_membership_window = PageFactory.initElements(driver,
				ManageAdHocCoursesMembershipWindow.class);
		
		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC15468AddFirstFileVerifyNewTabAdditionalContentCreated at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC15468AddFirstFileVerifyNewTabAdditionalContentCreated at " + DateToStr, "Starting the test: TC15468AddFirstFileVerifyNewTabAdditionalContentCreated at " + DateToStr, LogAs.PASSED, null);	
	}

	@Test
	public void test15468() throws Exception {
		String fullPathToFile = "\\workspace\\QualitestAutomation\\resources\\documents\\additional_file.doc";
		String file_name = "additional_file.doc";
		// 1.load page
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
		/// 2.login as instructor
		tegrity.loginCourses("User1");
		// 3.Select course
		course.selectCourseThatStartingWith("Ab");
		record.waitForVisibility(record.recordings_tab);
		//3.1 try to delete older file if exists
		if(record.additional_content_tab.isDisplayed())
		{
			record.returnToCourseListPage();
			course.waitForVisibility(course.course_list.get(1));
			course.deleteAllRecordingsInCourseStartWith("Ab", 1, record, delete_menu);
			System.out.println("recordings tab is now empty");
			course.selectCourseThatStartingWith("Ab");
			record.waitForVisibility(record.recordings_tab);
			System.out.println("inside courses page");
			record.verifyNoAdditionalContentTab();
		}
		//4.verify no additional content tab
		record.verifyNoAdditionalContentTab();
		// 4.Select "Course tasks -> Add Additional Content File" menu item
		record.toUploadAdditionalContentFile();
		Thread.sleep(Page.TIMEOUT_TINY);
		// 5.verify additional content file title info
		add_additional_content_window.verifyAdditionalContentFileWindowTitle();
		add_additional_content_window.verifyAdditionalContentFileWindowInfo();
		/// 6.verify The name of the selected file (in line with the format
		/// extension) is displayed prior to the "Select" button inside upload
		/// path function so is
		// add file
		add_additional_content_window.uploadFileByPath(fullPathToFile, confirm_menu);

		// 7.check if redirected to additional content tab

		if (driver.findElement(By.xpath("//*[@id=\"main\"]/div[2]/ul/li[3]")).getAttribute("class").equals("active")) {
			System.out.println("redirected to additional content tab");
			ATUReports.add("redirected to additional content tab", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("not redirected to additional content tab");
			ATUReports.add("not redirected to additional content tab", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		record.clickOnAdditionContentTab();
	


		driver.quit();///////////////////////// for download file!!!!!!!!!!!!!
////////set up for download file

		System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
		driver = new ChromeDriver();
		

		tegrity = PageFactory.initElements(driver, LoginHelperPage.class);

		wait = new WebDriverWait(driver, 30);
		add_additional_content_window = PageFactory.initElements(driver, AddAdditionalContentFileWindow.class);
		record = PageFactory.initElements(driver, RecordingHelperPage.class);
		copy = PageFactory.initElements(driver, CopyMenu.class);
		delete_menu = PageFactory.initElements(driver, DeleteMenu.class);
		course = PageFactory.initElements(driver, CoursesHelperPage.class);
		confirm_menu = PageFactory.initElements(driver, ConfirmationMenu.class);

		wait = new WebDriverWait(driver, 30);
		move_window = PageFactory.initElements(driver, MoveWindow.class);
		erp_window = PageFactory.initElements(driver, EditRecordinPropertiesWindow.class);
		admin_dashboard_page = PageFactory.initElements(driver, AdminDashboardPage.class);

		mange_adhoc_course_enrollments = PageFactory.initElements(driver, ManageAdhocCoursesEnrollmentsPage.class);

		create_new_course_window = PageFactory.initElements(driver, CreateNewCourseWindow.class);

		mange_adhoc_users_page = PageFactory.initElements(driver, ManageAdhocUsersPage.class);

		create_new_user_window = PageFactory.initElements(driver, CreateNewUserWindow.class);

		mangage_adhoc_courses_membership_window = PageFactory.initElements(driver,
				ManageAdHocCoursesMembershipWindow.class);

		///// end of set up

		// 1.load page
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
		/// 2.login as instructor
		tegrity.loginCourses("User1");
		// 3.Select course
		course.selectCourseThatStartingWith("Ab");
		Thread.sleep(Page.TIMEOUT_TINY);
		/// 4.select additional content tab
		record.clickOnAdditionContentTab();
		Thread.sleep(Page.TIMEOUT_TINY);
	
		/// 5.select file by its name
		record.selectAdditionalContentByName(file_name);
		Thread.sleep(Page.TIMEOUT_TINY);
		// 6.verify downloaded file is valid using md5
		record.VerifyDownloadedFileIsValid(file_name);
		
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);

	}
}
