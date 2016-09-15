package com.automation.main.add_additional_content_file;

import java.util.List;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.automation.main.page_helpers.AddAdditionalContentFileWindow;
import com.automation.main.page_helpers.AddAdditionalContentLinkWindow;
import com.automation.main.page_helpers.AdminDashboardPage;
import com.automation.main.page_helpers.ConfirmationMenu;
import com.automation.main.page_helpers.CopyMenu;
import com.automation.main.page_helpers.CoursesHelperPage;
import com.automation.main.page_helpers.CreateNewCourseWindow;
import com.automation.main.page_helpers.CreateNewUserWindow;
import com.automation.main.page_helpers.DeleteMenu;
import com.automation.main.page_helpers.EditRecordinPropertiesWindow;
import com.automation.main.page_helpers.LoginHelperPage;
import com.automation.main.page_helpers.ManageAdHocCoursesMembershipWindow;
import com.automation.main.page_helpers.ManageAdhocCoursesEnrollmentsPage;
import com.automation.main.page_helpers.ManageAdhocUsersPage;
import com.automation.main.page_helpers.MoveWindow;
import com.automation.main.page_helpers.RecordingHelperPage;
import com.automation.main.utilities.DriverSelector;

import java.text.DateFormat;
import java.util.Date;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;

public class TC15489TryToAddFileDuringAnotherUpload {
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
			public AddAdditionalContentFileWindow add_additional_content_file_window;
			public AddAdditionalContentLinkWindow add_additional_content_link_window;
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
				add_additional_content_file_window = PageFactory.initElements(driver, AddAdditionalContentFileWindow.class);
				add_additional_content_link_window = PageFactory.initElements(driver, AddAdditionalContentLinkWindow.class);
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
				 System.out.println("Starting the test: TC15489TryToAddFileDuringAnotherUpload at " + DateToStr);
				 ATUReports.add("Message window.", "Starting the test: TC15489TryToAddFileDuringAnotherUpload at " + DateToStr, "Starting the test: TC15489TryToAddFileDuringAnotherUpload at " + DateToStr, LogAs.PASSED, null);

			}

			@Test
			public void test15489() throws Exception {
				String fullPathToFile = "\\workspace\\QualitestAutomation\\resources\\documents\\chromedriver_win32.zip";
				String file_name ="chromedriver_win32.zip";
				// 1.load page
				tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
				/// 2.login as instructor
				tegrity.loginCourses("User1");
				
				//3.delete additional content
			///	course.deleteAllRecordingsInCourseStartWith("Ab", 1, record, delete_menu);
			     course.waitForVisibility(course.course_list.get(1));
				// 4.Select course
				course.selectCourseThatStartingWith("Ab");
				record.waitForVisibility(record.course_task_button);
		  
				// 5.Select "Course tasks -> Add Additional Content File" menu item
				record.toUploadAdditionalContentFile();
				Thread.sleep(2000);
				// 6.verify additional content file title info
				add_additional_content_file_window.verifyAdditionalContentFileWindowTitle();
				add_additional_content_file_window.verifyAdditionalContentFileWindowInfo();
				/// 7.verify The name of the selected file (in line with the format
				/// extension) is displayed prior to the "Select" button inside upload
				/// path function so is
				// add file
				add_additional_content_file_window.uploadFileByPathNoConfirmation(fullPathToFile);
                //8. Verify that "Select" button is disabled while file is uploading
			     add_additional_content_file_window.verifyDisabledSelectButton();
				
				//11.click on ok
				confirm_menu.waitForVisibility(confirm_menu.ok_button);
				confirm_menu.clickOnOkButtonAfterConfirmAddAdditionalContentFile(file_name);
				
				// 10.check if redirected to additional content tab
				if (driver.findElement(By.xpath("//*[@id=\"main\"]/div[2]/ul/li[3]")).getAttribute("class").equals("active")) {
					System.out.println("redirected to additional content tab");
					ATUReports.add("redirected to additional content tab", LogAs.PASSED, null);
					Assert.assertTrue(true);
				} else {
					System.out.println("not redirected to additional content tab");
					ATUReports.add("not redirected to additional content tab", LogAs.FAILED, null);
					Assert.assertTrue(false);
				}
				
				System.out.println("Done.");
				ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);

			}
}
