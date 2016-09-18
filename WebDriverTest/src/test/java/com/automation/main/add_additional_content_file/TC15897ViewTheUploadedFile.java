package com.automation.main.add_additional_content_file;

import java.io.File;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.automation.main.page_helpers.AddAdditionalContentFileWindow;
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

public class TC15897ViewTheUploadedFile {
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
		 System.out.println("Starting the test: TC15897ViewTheUploadedFile at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC15897ViewTheUploadedFile at " + DateToStr,
		 "Starting the test: TC15897ViewTheUploadedFile at " + DateToStr, LogAs.PASSED, null);	

	}
	
	@AfterClass
	public void closeBroswer() {		
		driver.quit();
	}

	@Test
	public void test15897() throws Exception {
		String fullPathToFile1 = "\\workspace\\QualitestAutomation\\resources\\documents\\Moshik_testMp4.mp4";
		String file_name1 = "Moshik_testMp4.mp4";
		String fullPathToFile2 = "\\workspace\\QualitestAutomation\\resources\\documents\\additional_file.doc";
		String file_name2 = "additional_file.doc";
	
		// 1.load page
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
		/// 2.login as instructor
		tegrity.loginCourses("User1");
		// 3.Select course+delete previous files
		course.deleteAllRecordingsInCourseStartWith("Ab", 1, record, delete_menu);
		course.selectCourseThatStartingWith("Ab");
		Thread.sleep(3000);
		//3.1 try to delete older file if exists
		String download_path1= System.getProperty("user.home") + File.separatorChar +"Downloads"+ File.separatorChar+file_name1;
		String download_path2= System.getProperty("user.home") + File.separatorChar +"Downloads"+ File.separatorChar+file_name2;
        ///delete old downloaded files
		record.tryToDeleteOlderFile(download_path1);
		record.tryToDeleteOlderFile(download_path2);
		// 4.Select "Course tasks -> Add Additional Content File" menu item
		record.toUploadAdditionalContentFile();
		Thread.sleep(2000);
		// 5.add file
		
		add_additional_content_window.uploadFileByPath(fullPathToFile1, confirm_menu);
        record.waitForVisibility(record.content_tasks_button);
    	// 4.Select "Course tasks -> Add Additional Content File" menu item
        record.toUploadAdditionalContentFile();
		Thread.sleep(2000);
		// 5.add file
		add_additional_content_window.uploadFileByPath(fullPathToFile2, confirm_menu);
        record.waitForVisibility(record.content_tasks_button);
		// 7.check if redirected to additional content tab

		record.signOut();/////////////////////////end of precondition!!!!!!!!!!!!

		//////// set up for download file
	

		///// end of set up

		// 1.load page
		tegrity.waitForVisibility(tegrity.passfield);
		/// 2.login as instructor
		tegrity.loginCourses("User1");
		
		// 3.Select course
		course.selectCourseThatStartingWith("Ab");
		record.waitForVisibility(record.additional_content_tab);
		
		/// 4.select additional content tab
		record.clickOnAdditionContentTab();
		record.waitForVisibility(record.getCheckbox());
	    record.convertAdditionalContantListToNames();
		
	    ///5.verify size is correct :file 1 should be with mb file 2 should be in kb
	    ///path to 2 files
	    String mega= System.getProperty("user.dir") +fullPathToFile1;
	    String kb = System.getProperty("user.dir")+fullPathToFile2;
	   
	    ///round up  double format
	    DecimalFormat df = new DecimalFormat("#.#");
	    df.setRoundingMode(RoundingMode.CEILING);
	    String m=df.format(record.convertFileSize(mega,"mb"));
	    String k=df.format(record.convertFileSize(kb,"kb"));
	    
	    ///6.find if file1 should be represented in mega and file 2 in kb
	    if((Double.valueOf(m)>=1)&&(Double.valueOf(m)<1024)&&(Double.valueOf(k)>=1)&&(Double.valueOf(k)<1024))
	    {           
					System.out.println("file represented in correct units");
					ATUReports.add("file represented in megas", LogAs.PASSED, null);
					Assert.assertTrue(true);
				} else {
					System.out.println("file is bigger or smaller for mega representation");
					ATUReports.add("file is bigger or smaller for mega representation", LogAs.FAILED, null);
					Assert.assertTrue(false);
				}
	    Thread.sleep(5000);
	///record.waitForVisibility(driver.findElement(By.xpath("//*[@id=\"ItemSize2\"]/span")));
	    if((driver.findElement(By.xpath("//*[@id=\"ItemSize2\"]/span")).getText().contains("Mb"))&&(driver.findElement(By.xpath("//*[@id=\"ItemSize1\"]/span")).getText().contains("Kb")))
	    {
					System.out.println("files represented in additional Content list correctly");
					ATUReports.add("files represented in additional Content list correctly", LogAs.PASSED, null);
					Assert.assertTrue(true);
				} else {
					System.out.println("files not represented in additional Content list correctly");
					ATUReports.add("files not represented in additional Content list correctly", LogAs.FAILED, null);
					Assert.assertTrue(false);
				}
		
	    
	    
	    System.out.println(driver.findElement(By.id("RecordingDate1")).getText());
		///7.verify date
	    record.verifyDate(driver.findElement(By.id("RecordingDate1")).getText());
	    record.verifyDate(driver.findElement(By.id("RecordingDate2")).getText());
		driver.quit();

		///check  download
		////////set up for download file

		System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();

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
		Thread.sleep(3000);
		/// 4.select additional content tab
		record.clickOnAdditionContentTab();
		Thread.sleep(3000);
	
		/// 5.select file by its name
		record.selectAdditionalContentByName(file_name1);
		Thread.sleep(5000);
		// 6.verify downloaded file is valid using md5
		record.VerifyDownloadedFileIsValid(file_name1);
		/// 5.select file by its name
		record.selectAdditionalContentByName(file_name2);
		Thread.sleep(5000);
		// 6.verify downloaded file is valid using md5
		record.VerifyDownloadedFileIsValid(file_name2);
	    //7.sign out and login as student 
		record.signOut();
		
		/////////////////////student ////////////////////////
		
		tegrity.waitForVisibility(tegrity.passfield);
		tegrity.loginCourses("User4");
		// 3.Select course
				course.selectCourseThatStartingWith("Ab");
				record.waitForVisibility(record.additional_content_tab);
				/// 4.select additional content tab
				record.clickOnAdditionContentTab();
				Thread.sleep(3000);
			    record.convertAdditionalContantListToNames();
				///5.verify size is correct :file 1 should be with mb file 2 should be in kb
			  ///path to 2 files
			   
			    ///6.find if file1 should be represented in mega and file 2 in kb
			    if((Double.valueOf(m)>=1)&&(Double.valueOf(m)<1024)&&(Double.valueOf(k)>=1)&&(Double.valueOf(k)<1024))
			    {           
							System.out.println("file represented in correct units");
							ATUReports.add("file represented in megas", LogAs.PASSED, null);
							Assert.assertTrue(true);
						} else {
							System.out.println("file is bigger or smaller for mega representation");
							ATUReports.add("file is bigger or smaller for mega representation", LogAs.FAILED, null);
							Assert.assertTrue(false);
						}
			    Thread.sleep(6000);
			///record.waitForVisibility(driver.findElement(By.xpath("//*[@id=\"ItemSize2\"]/span")));
			    if((driver.findElement(By.xpath("//*[@id=\"ItemSize2\"]/span")).getText().contains("Mb"))&&(driver.findElement(By.xpath("//*[@id=\"ItemSize1\"]/span")).getText().contains("Kb")))
			    {
							System.out.println("files represented in additional Content list correctly");
							ATUReports.add("files represented in additional Content list correctly", LogAs.PASSED, null);
							Assert.assertTrue(true);
						} else {
							System.out.println("files not represented in additional Content list correctly");
							ATUReports.add("files not represented in additional Content list correctly", LogAs.FAILED, null);
							Assert.assertTrue(false);
						}
				System.out.println(driver.findElement(By.id("RecordingDate1")).getText());
				///7.verify date
			    record.verifyDate(driver.findElement(By.id("RecordingDate1")).getText());
			    record.verifyDate(driver.findElement(By.id("RecordingDate2")).getText());
				driver.quit();

				///check  download
		////////set up for download file

				System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
				driver = new ChromeDriver();
				driver.manage().window().maximize();

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
				tegrity.loginCourses("User4");
				// 3.Select course
				course.selectCourseThatStartingWith("Ab");
				Thread.sleep(3000);
				/// 4.select additional content tab
				record.clickOnAdditionContentTab();
				Thread.sleep(3000);
			
				/// 5.select file by its name
				record.selectAdditionalContentByName(file_name1);
				Thread.sleep(5000);
				// 6.verify downloaded file is valid using md5
				record.VerifyDownloadedFileIsValid(file_name1);
				/// 5.select file by its name
				record.selectAdditionalContentByName(file_name2);
				Thread.sleep(5000);
				// 6.verify downloaded file is valid using md5
				record.VerifyDownloadedFileIsValid(file_name2);
	         	

				System.out.println("Done.");
				ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
		
	}
}
