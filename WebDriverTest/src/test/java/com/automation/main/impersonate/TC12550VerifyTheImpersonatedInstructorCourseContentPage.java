package com.automation.main.impersonate;


import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import com.automation.main.impersonate.helper.LocationCalculator;
import com.automation.main.page_helpers.*;
import com.automation.main.parent.BaseTest;
import com.automation.main.utilities.DriverSelector;
import com.automation.main.validator.ui.CourseUiValidator;
import junitx.util.PropertyManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utils.ATUManager;
import utils.WaitDriverUtility;

import java.text.DateFormat;
import java.util.Date;

@Listeners({ATUReportsListener.class, ConfigurationListener.class, MethodListener.class})
public class TC12550VerifyTheImpersonatedInstructorCourseContentPage extends BaseTest {


    {
        System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");
    }


    public ConfirmationMenu confirmation_menu;
    public LoginHelperPage tegrity;
    public CoursesHelperPage course;
    public RecordingHelperPage record;
    public DeleteMenu delete_menu;
    public MoveWindow move_window;
    public AdminDashboardPage admin_dashboard_page;
    public AdminDashboardViewCourseList admin_dashboard_view_course_list;
    WebDriverWait wait;
    public static WebDriver thread_driver;
    CopyMenu copy;
    private AdvancedServiceSettingsPage advancedServiceSettingsPage;
    private CourseSettingsPage courseSettingsPage;
    private ImpersonateUser impersonateUserPage;
    private SearchPage searchPage;
    private PlayerPage playerPage;

    LocationCalculator locationCalculator;
    private String commonCourseName;


    String testName = "TC 12550 Verify the impersonated Instructor Course Content Page";

    @BeforeClass
    public void setup() {


        driver = DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());

        initPages();


        wait = new WebDriverWait(driver, 30);

        Date curDate = new Date();
        String DateToStr = DateFormat.getInstance().format(curDate);
        System.out.println("Starting the test: " + testName + "at " + DateToStr);
        ATUReports.add("Message window.", "Starting the test: " + testName + " at " + DateToStr,
                "Starting the test: " + testName + " at " + DateToStr, LogAs.PASSED, null);
    }

    private void initPages() {
        tegrity = PageFactory.initElements(driver, LoginHelperPage.class);

        record = PageFactory.initElements(driver, RecordingHelperPage.class);
        copy = PageFactory.initElements(driver, CopyMenu.class);
        delete_menu = PageFactory.initElements(driver, DeleteMenu.class);
        admin_dashboard_page = PageFactory.initElements(driver, AdminDashboardPage.class);
        admin_dashboard_view_course_list = PageFactory.initElements(driver, AdminDashboardViewCourseList.class);
        move_window = PageFactory.initElements(driver, MoveWindow.class);
        confirmation_menu = PageFactory.initElements(driver, ConfirmationMenu.class);
        advancedServiceSettingsPage = PageFactory.initElements(driver, AdvancedServiceSettingsPage.class);
        courseSettingsPage = PageFactory.initElements(driver, CourseSettingsPage.class);
        impersonateUserPage = PageFactory.initElements(driver, ImpersonateUser.class);
        searchPage = PageFactory.initElements(driver, SearchPage.class);
        playerPage = PageFactory.initElements(driver,PlayerPage.class);


    }


    public void initializeCourseObject() throws InterruptedException {
        course = PageFactory.initElements(driver, CoursesHelperPage.class);
        course.courses = course.getCoursesListFromElement(course.course_list);
    }


    @Test()
    public void test() throws InterruptedException {

        //login as Administrator
        loginAsAdminAndEnableStudentTesting();

        //login as Instructor and enable all settings course
        loginAsInsAndEnableAllCourseSettings();

        //login As ins and add Student recordings, Test recordings, Additional content to the course
        prepareDataTest();


        tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);

        runTestAsDiffrentUser("Admin");

        runTestAsDiffrentUser("HelpdeskAdmin");


    }

    private void runTestAsDiffrentUser(String user) throws InterruptedException {
        tegrity.loginCourses(user);
        WaitDriverUtility.waitToPageBeLoaded(driver);
        admin_dashboard_page.clickOnTargetSubmenuUsers("Impersonate User");
        WaitDriverUtility.waitToPageBeLoaded(driver);
        String instructor = PropertyManager.getProperty("User1");
        impersonateUserPage.enterAsAnotherUser(instructor);
        WaitDriverUtility.waitToPageBeLoaded(driver);
        WaitDriverUtility.switchToNewTab(driver,"Tegrity - Courses");
        initPages();
        initializeCourseObject();
        course.selectCourseByName(commonCourseName);

        WaitDriverUtility.waitToPageBeLoaded(driver);
        WaitDriverUtility.sleepInSeconds(1);
        record.clickOnRecordingInIndex(1);

        record.clickOnTheFirstCaptherWithOutTheExpand();
        playerPage.verifyTimeBufferStatusForXSec(10);
        playerPage.addTargetBookmark("First recording bookmark");
        WaitDriverUtility.sleepInSeconds(3);
        playerPage.returnToRecordingPageByNameAsUserOrGuest(commonCourseName,record);

        record.clickOnCourseTaskThenCourseSettings();
        courseSettingsPage.enableStudentTesting();

        WaitDriverUtility.waitToPageBeLoaded(driver);
        CourseUiValidator courseUiValidator = new CourseUiValidator(record);
        courseUiValidator.validateTabsAreDisplayedAtCorrectPosition();
        courseUiValidator.validateAllDropDownOnPage();

        //Delete record and verify is not displayed
        String theRecordingName = record.getTheRecordingNameIndex(1);
        record.clickCheckBoxByName(theRecordingName);
        record.clickOnRecordingTaskThenDelete();
        delete_menu = PageFactory.initElements(driver, DeleteMenu.class);
        delete_menu.clickOnDeleteButton();
        boolean targetRecordingExist = record.isTargetRecordingExist(theRecordingName);

        ATUManager.asserIsTrueAndReport(!targetRecordingExist,"The record should not be exist at this point","","");

        advancedServiceSettingsPage.signOut();
    }


    private void prepareDataTest() throws InterruptedException {
        tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
        tegrity.loginCourses("SuperUser");
        WaitDriverUtility.waitToPageBeLoaded(driver);
        initializeCourseObject();

        course.selectCourseThatStartingWith("BankValid");

        record.clickOnRecordingsTab();
        selectAndCopy();

        //adding student recording
        record.clickOnStudentRecordingsTab();
        selectAndCopy();

        //adding test recording
        record.clickOnTestsTab();
        selectAndCopy();

        //adding addition content
        addAdditionalContent();
        record.signOut();
    }

    private void addAdditionalContent() throws InterruptedException {
        record.clickOnAdditionContentTab();
        record.clickCheckBoxByIndex(1);
        record.clickOnContentTaskThenCopy();
        copy.selectTargetCourseFromCourseList(commonCourseName);
        copy.clickOnCopyButton();
    }

    private void selectAndCopy() throws InterruptedException {
        record.clickCheckBoxByIndex(1);
        record.copyRecordsToAnotherCourse(commonCourseName);
        confirmation_menu.clickOnOkButton();
    }

    private void loginAsAdminAndEnableStudentTesting() throws InterruptedException {
        tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
        tegrity.loginCourses("Admin");
        WaitDriverUtility.waitToPageBeLoaded(driver);
        admin_dashboard_page.clickOnTargetSubmenuAdvancedServices("Advanced Service Settings");
        advancedServiceSettingsPage.enableStudyTestingCheckboxAndClickOk(confirmation_menu);
        advancedServiceSettingsPage.signOut();
        System.out.println("student is enable to testing");
    }

    private void loginAsInsAndEnableAllCourseSettings() throws InterruptedException {
        tegrity.loginCourses("User1");
        WaitDriverUtility.waitToPageBeLoaded(driver);
        initializeCourseObject();
        commonCourseName = course.selectCourseThatStartingWith("abc");
        record.clickOnCourseTaskThenCourseSettings();
        courseSettingsPage.checkAllCourseSettingsCheckboxs();
        courseSettingsPage.clickOnOkButton();
        record.signOut();
    }

}
