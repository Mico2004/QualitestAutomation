package com.automation.main.add_additional_content_file;


import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import com.automation.main.page_helpers.AddAdditionalContentFileWindow;
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
import com.automation.main.utilities.DriverSelector;

import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.util.Date;
import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;

public class TC15467CancelTheAdding {
	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}

	public EditRecordingPropertiesWindow erp_window;
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
		erp_window = PageFactory.initElements(driver, EditRecordingPropertiesWindow.class);
		admin_dashboard_page = PageFactory.initElements(driver, AdminDashboardPage.class);
		mange_adhoc_course_enrollments = PageFactory.initElements(driver, ManageAdhocCoursesEnrollmentsPage.class);
		create_new_course_window = PageFactory.initElements(driver, CreateNewCourseWindow.class);
		mange_adhoc_users_page = PageFactory.initElements(driver, ManageAdhocUsersPage.class);
		create_new_user_window = PageFactory.initElements(driver, CreateNewUserWindow.class);
		mangage_adhoc_courses_membership_window = PageFactory.initElements(driver,
				ManageAdHocCoursesMembershipWindow.class);
		
		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC15467CancelTheAdding at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC15467CancelTheAdding at " + DateToStr, "Starting the test: TC15467CancelTheAdding at " + DateToStr, LogAs.PASSED, null);	
	}
	
	@AfterClass
	public void closeBroswer() {
		this.driver.quit();
	}
	
	@Test (description="TC 15467 Cancel The Adding")
public void test15467() throws Exception {
		String fullPathToFile = "\\src\\test\\resources\\resouces-to-upload\\Moshik_Cancel.doc";
		String file_name = "Moshik_Cancel.doc";
		// 1.load page
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
		/// 2.login as instructor
		tegrity.loginCourses("User1");
		// 3.Select course
		course.selectCourseThatStartingWith("Ab");
		record.waitForVisibility(record.getCheckbox());
			
		record.waitForVisibility(record.getCheckbox());
		// 3.1 click on additional content tab

		// 4.Select "Course tasks -> Add Additional Content File" menu item
		record.toUploadAdditionalContentFile();
		add_additional_content_window.waitForVisibility(add_additional_content_window.add_additional_file_button);
		// 5.verify additional content file title info
		add_additional_content_window.verifyAdditionalContentFileWindowTitle();
		add_additional_content_window.verifyAdditionalContentFileWindowInfo();
		/// 6.verify The name of the selected file (in line with the format
		/// extension) is displayed prior to the "Select" button inside upload
		/// path function so is
		// 7. add file and cancel uploading
		add_additional_content_window.toUploadFileByPathThenSelectFile(fullPathToFile);
		//add_additional_content_window.waitForVisibility(add_additional_content_window.upload_progress_bar);
		Thread.sleep(100);
		add_additional_content_window.clickOnCancelButton();
		//confirm_menu.clickOnOkButtonAfterConfirmAddAdditionalContentFile(file_name);
		
		record.clickOnAdditionContentTab();
		wait.until(ExpectedConditions.attributeContains(By.xpath("//*[@id=\"main\"]/div[2]/ul/li[3]"), "class", "active"));
		record.convertAdditionalContantListToNames();
	    record.verifyNoAdditionalContentFileName(file_name);

		// 4.Select "Course tasks -> Add Additional Content File" menu item
		record.clickOnRecordingsTab();
		record.toUploadAdditionalContentFile();
		add_additional_content_window.waitForVisibility(add_additional_content_window.add_additional_file_button);
		// 5.verify additional content file title info
		add_additional_content_window.verifyAdditionalContentFileWindowTitle();
		add_additional_content_window.verifyAdditionalContentFileWindowInfo();
		// 7. add file and cancel uploading
		add_additional_content_window.toUploadFileByPathThenSelectFile(fullPathToFile);
		 if(driver instanceof FirefoxDriver){
			 add_additional_content_window.clickEscOnKeyBoardToCloseCopyWindow();		 
		 } else  {
			 Actions action = new Actions(driver);
			 action.sendKeys(Keys.ESCAPE).build().perform();
		 }
		 record.clickOnAdditionContentTab();
		 wait.until(ExpectedConditions.attributeContains(By.xpath("//*[@id=\"main\"]/div[2]/ul/li[3]"), "class", "active"));
		 record.convertAdditionalContantListToNames();
		 record.verifyNoAdditionalContentFileName(file_name);	
		 System.out.println("Done.");
		 ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
	}
}
