package com.automation.main.add_additional_content_file;

import java.io.File;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
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
import com.automation.main.page_helpers.EditRecordingPropertiesWindow;
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

public class TC15470AddFileWithTheSameTitleExistingLink {
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
			 System.out.println("Starting the test: TC15470AddFileWithTheSameTitleExistingLink at " + DateToStr);
			 ATUReports.add("Message window.", "Starting the test: TC15470AddFileWithTheSameTitleExistingLink at " + DateToStr, "Starting the test: TC15470AddFileWithTheSameTitleExistingLink at " + DateToStr, LogAs.PASSED, null);	

		}

		@Test (description="TC 15470 Add File With The Same Title Existing Link")
		public void test15470() throws Exception {                                           
			String fullPathToFile = "\\src\\test\\resources\\resouces-to-upload\\SameTitle.doc";
			String file_name = "SameTitle.doc";
			// 1.load page
			tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
			/// 2.login as instructor
			tegrity.loginCourses("User1");
			
		     course.waitForVisibility(course.course_list.get(1));
			// 4.Select course
			course.selectCourseThatStartingWith("Ab");
			record.waitForVisibility(record.course_task_button);
	    
			
			///4.pre-test try upload link with same name
			record.toUploadAdditionalContentLink();
			add_additional_content_link_window.waitForVisibility(add_additional_content_link_window.additional_content_link_title_input);
			add_additional_content_link_window.createNewAdditionalContentLink(confirm_menu,"SameTitle.doc","SameTitle.doc");
			record.waitForVisibility(record.course_task_button);
			// 4.Select "Course tasks -> Add Additional Content File" menu item
			record.toUploadAdditionalContentFile();
	
			// 5.verify additional content file title info
			add_additional_content_file_window.verifyAdditionalContentFileWindowTitle();
			add_additional_content_file_window.verifyAdditionalContentFileWindowInfo();
			/// 6.verify The name of the selected file (in line with the format
			/// extension) is displayed prior to the "Select" button inside upload
			/// path function so is
			// add file
			add_additional_content_file_window.uploadFileByPath(fullPathToFile, confirm_menu);

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
			// 8.Click on file's title:Standard open file/download dialog is
			// displayed
			
			record.waitForVisibility(record.additional_content_tab);
			record.convertAdditionalContantListToNames();
			///9.Verify that number mark isn't added to file's title
	    	record.verifyNoCopiesExist(record.additional_content_list_names,file_name);
			
	    	System.out.println("Done.");
	    	ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
	    	
		}

}
