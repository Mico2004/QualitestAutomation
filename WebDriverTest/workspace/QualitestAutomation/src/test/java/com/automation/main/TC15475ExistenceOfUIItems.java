package com.automation.main;

import java.io.File;
import java.util.List;


import org.testng.annotations.AfterClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;import com.automation.main.page_helpers.Page;import com.automation.main.page_helpers.Page;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import java.text.DateFormat;
import java.util.Date;


public class TC15475ExistenceOfUIItems {
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
			 System.out.println("Starting the test: TC15475ExistenceOfUIItems at " + DateToStr);
			 ATUReports.add("Message window.", "Starting the test: TC15475ExistenceOfUIItems at " + DateToStr, "Starting the test: TC15475ExistenceOfUIItems at " + DateToStr, LogAs.PASSED, null);	

		}

		@Test
		public void test15475() throws Exception {
		
			//////pre-conditions!!!!100 mb is already default
			String fullPathToFile = "\\workspace\\QualitestAutomation\\resources\\documents\\chromedriver_win32.zip";
			String file_name = "chromedriver_win32.zip";
			// 1.load page
			tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);	
			/// 2.login as instructor
			tegrity.loginCourses("User1");	
		
			// 3.Select course
			course.selectCourseThatStartingWith("Ab");
			record.waitForVisibility(record.background);
	    	//3.1 get university background color
			String universit_color=record.getBackGroundColor(record.background);
			
			//3.2 try to delete older file if exists
			String download_path= System.getProperty("user.home") + File.separatorChar +"Downloads"+ File.separatorChar+file_name;
			record.tryToDeleteOlderFile(download_path);
			
			// 4.Select "Course tasks -> Add Additional Content File" menu item
			record.toUploadAdditionalContentFile();
			add_additional_content_window.waitForVisibility(add_additional_content_window.add_additional_file_button);
			//5. Window title background same as the color selected for university
	    	String additional_content_background=add_additional_content_window.getBackGroundColor(add_additional_content_window.background);
			if(additional_content_background.equals(universit_color))
			{
				System.out.println("same additional content background color");
				ATUReports.add("same additional content background color","background color ","equal","equal" ,LogAs.PASSED, null);
				Assert.assertTrue(true);
			}
			else
			{
				System.out.println("same additional content background color");
				ATUReports.add("same additional content background color","background color ","equal"," not equal" ,LogAs.FAILED, null);
				Assert.assertTrue(false);
			}
			// 5.verify additional content file title+ info
			add_additional_content_window.verifyAdditionalContentFileWindowTitle();
			add_additional_content_window.verifyAdditionalContentFileWindowInfo();
		///6.upload file,check progressbar location
			add_additional_content_window.uploadFileByPathNoConfirmation(fullPathToFile);
            //7.verify uploading correctness:elapsed time,estimated time,percentage,speed and
			add_additional_content_window.verifyUploadInfoCorrectness(file_name);
            confirm_menu.waitForVisibility(confirm_menu.add_additional_content_confirm_note);
            confirm_menu.clickOnOkButtonAfterConfirmAddAdditionalContentFile(file_name);
            
			// 8.check if redirected to additional content tab

			if (driver.findElement(By.xpath("//*[@id=\"main\"]/div[2]/ul/li[3]")).getAttribute("class").equals("active")) {
				System.out.println("redirected to additional content tab");
				ATUReports.add("redirected to additional content tab", LogAs.PASSED, null);
				Assert.assertTrue(true);
			} else {
				System.out.println("not redirected to additional content tab");
				ATUReports.add("not redirected to additional content tab", LogAs.FAILED, null);
				Assert.assertTrue(false);
			}
			
            //9.signout
			record.waitForVisibility(record.sign_out);
			record.signOut();
			Thread.sleep(Page.TIMEOUT_TINY);
			//tegrity.waitForVisibility(tegrity.usernamefield);
			//10.login as student
			tegrity.loginCourses("User4");
			course.waitForVisibility(course.first_course_button);
			//11.login course
			course.selectCourseThatStartingWith("Ab");
			record.waitForVisibility(record.additional_content_tab);
			//12.click on additional content tab	
			record.clickOnAdditionContentTab();
			
		//13.select uploaded additional content
			Thread.sleep(Page.TIMEOUT_TINY);
			record.convertAdditionalContantListToNames();
			record.selectAdditionalContentByName(file_name);
			
			System.out.println("Done.");
			ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
		}

}
