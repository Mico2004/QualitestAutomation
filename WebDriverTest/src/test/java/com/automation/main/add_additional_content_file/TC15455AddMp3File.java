package com.automation.main.add_additional_content_file;

import java.io.File;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
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
import com.automation.main.page_helpers.EditRecordingPropertiesWindow;
import com.automation.main.page_helpers.LoginHelperPage;
import com.automation.main.page_helpers.ManageAdHocCoursesMembershipWindow;
import com.automation.main.page_helpers.ManageAdhocCoursesEnrollmentsPage;
import com.automation.main.page_helpers.ManageAdhocUsersPage;
import com.automation.main.page_helpers.MoveWindow;
import com.automation.main.page_helpers.RecordingHelperPage;
import com.automation.main.utilities.DriverSelector;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;

public class TC15455AddMp3File {

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
		 System.out.println("Starting the test: TC15455AddMp3File at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC15455AddMp3File at " + DateToStr, "Starting the test: TC15455AddMp3File at " + DateToStr, LogAs.PASSED, null);	
	}

	@AfterClass
	public void closeBroswer() {
		
		this.driver.quit();
	}
	
	
	@Test (description="TC 15455 Add Mp3 File")
	public void test15455() throws Exception {

		String fullPathToFile = "\\src\\test\\resources\\resouces-to-upload\\Moshik_testMp3.mp3";
		String file_name = "Moshik_testMp3.mp3";
		// 1.load page
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
		/// 2.login as instructor
		tegrity.loginCourses("User1");
	    course.waitForVisibility(course.sign_out);
		// 3.Select course
		course.selectCourseThatStartingWith("Ab");
		
		//3.1 try to delete older file if exists
		String download_path= System.getProperty("user.home") + File.separatorChar +"Downloads"+ File.separatorChar+file_name;
		record.tryToDeleteOlderFile(download_path);
		
		// 4.Select "Course tasks -> Add Additional Content File" menu item
		record.toUploadAdditionalContentFile();
		
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
		// 8.Click on file's title:Standard open file/download dialog is
		// displayed

		record.convertRecordingsListToNames();
		driver.quit();///////////////////////// for download file!!!!!!!!!!!!!

		//////// set up for download file

		//System.setProperty("webdriver.chrome.driver", "c:/selenium-drivers/chromedriver.exe");
		///driver = new ChromeDriver();
		//driver.manage().window().maximize();

//		System.setProperty("webdriver.gecko.driver", "c:/selenium-drivers/geckodriver.exe");
//		DesiredCapabilities capabilities = DesiredCapabilities.firefox();
//		capabilities.setCapability("marionette", true);
//		FirefoxProfile fxProfile = new FirefoxProfile();
//	    fxProfile.setPreference("browser.download.folderList",2);
//	    fxProfile.setPreference("browser.helperApps.alwaysAsk.force", false);
//	    fxProfile.setPreference("browser.download.manager.showWhenStarting",false);
//	    fxProfile.setPreference("browser.download.dir",System.getProperty("user.home") + File.separatorChar + "Downloads" + File.separatorChar);
//	    fxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk" ,"audio/mp3;audio/mpeg3;audio/x-mpeg-3" );
//	    capabilities.setCapability(FirefoxDriver.PROFILE, fxProfile);
//		driver = new FirefoxDriver(capabilities);
		System.setProperty("webdriver.chrome.driver","c:/selenium-drivers/chromedriver.exe");
		String downloadFilepath = System.getProperty("user.home") + File.separatorChar + "Downloads" ;

		HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
		chromePrefs.put("profile.default_content_settings.popups", 0);
		chromePrefs.put("download.default_directory", downloadFilepath);
		ChromeOptions options = new ChromeOptions();
		HashMap<String, Object> chromeOptionsMap = new HashMap<String, Object>();
		options.setExperimentalOption("prefs", chromePrefs);
		options.addArguments("--test-type");
		options.addArguments("--disable-extensions"); //to disable browser extension popup

		DesiredCapabilities cap = DesiredCapabilities.chrome();
		cap.setCapability(ChromeOptions.CAPABILITY, chromeOptionsMap);
		cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		cap.setCapability(ChromeOptions.CAPABILITY, options);
		driver = new ChromeDriver(cap);
		
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

		///// end of set up

		// 1.load page
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
		/// 2.login as instructor
		tegrity.loginCourses("User1");
		// 3.Select course
		course.selectCourseThatStartingWith("Ab");
	
		/// 4.select additional content tab
		record.clickOnAdditionContentTab();
		
		///5.try do delete older file
		
		
		/// 5.select file by its name
		record.selectAdditionalContentByName(file_name);
	
		// 6.verify downloaded file is valid using md5
		record.VerifyDownloadedFileIsValid(file_name);

		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
	}

}
