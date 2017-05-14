package com.automation.main.impersonate;


import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import com.automation.main.page_helpers.*;
import com.automation.main.parent.BaseTest;
import com.automation.main.utilities.DriverSelector;
import com.automation.main.validator.ui.CourseUiValidator;
import junitx.util.PropertyManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utils.ATUManager;
import utils.WaitDriverUtility;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Listeners({ATUReportsListener.class, ConfigurationListener.class, MethodListener.class})
public class TC12679VerifyTheImpersonatedNewUserCourseContentPage extends BaseTest {



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
    private PlayerPage playerPage;
    private ManageAdhocUsersPage manageAdhocUsersPage;
    private CreateNewUserWindow createNewUserWindow;
    private ManageAdhocCoursesEnrollmentsPage manageAdhocCoursesEnrollmentsPage;
    private ManageAdHocCoursesMembershipWindow manageAdHocCoursesMembershipWindow;
    private String userName = "impersonateTest";
    private String impersonateTabId;


    private String commonCourseName;

    String userToImpersonate ;


    String testName = "TC 12679 Verify the impersonated New User Course Content Page";

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
        playerPage = PageFactory.initElements(driver, PlayerPage.class);

        manageAdhocUsersPage = PageFactory.initElements(driver, ManageAdhocUsersPage.class);
        createNewUserWindow = PageFactory.initElements(driver, CreateNewUserWindow.class);
        manageAdhocCoursesEnrollmentsPage = PageFactory.initElements(driver, ManageAdhocCoursesEnrollmentsPage.class);
        manageAdHocCoursesMembershipWindow = PageFactory.initElements(driver, ManageAdHocCoursesMembershipWindow.class);




    }


    public void initializeCourseObject() throws InterruptedException {
        course = PageFactory.initElements(driver, CoursesHelperPage.class);
        course.courses = course.getCoursesListFromElement(course.course_list);
    }


    @Test()
    public void test() throws InterruptedException {

        setCommonCourse();

        //login as Administrator
        loginAsAdminAndEnableStudentTesting();

        loginAsAdminAndCreateUsersWithSupportedTypesNames();


        //login as Instructor and enable all settings course
        loginAsInsAndEnableAllCourseSettings();

        //login As ins and add Student recordings, Test recordings, Additional content to the course
        prepareDataTest();

        tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);

        runTestAsDiffrentUser("Admin");

        runTestAsDiffrentUser("HelpdeskAdmin");


    }

    private void loginAsAdminAndCreateUsersWithSupportedTypesNames() throws InterruptedException {


        tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
        tegrity.loginCourses("Admin");
        WaitDriverUtility.waitToPageBeLoaded(driver);
        admin_dashboard_page.clickOnTargetSubmenuUsers("Manage Ad-hoc Users (User Builder)");
        for (int i = 0; i < 5; i++) {
            try {
                driver.switchTo().frame(0);
                break;
            } catch (Exception msg) {
                Thread.sleep(1000);
            }
        }
            userName = userName + System.currentTimeMillis();
            manageAdhocUsersPage.clickOnNewUser();
            createNewUserWindow.waitForVisibility(createNewUserWindow.ok_button);
            createNewUserWindow.createNewUser(userName, userName, userName + "@abdc.com", "111", "111");

        List<String> oneUser= new ArrayList<String>();
        oneUser.add(userName);
        driver.navigate().back();
        admin_dashboard_page.clickOnTargetSubmenuCourses("Manage Ad-hoc Courses / Enrollments (Course Builder)");
        manageAdhocCoursesEnrollmentsPage.enrollInstructorToCourse(commonCourseName, oneUser, manageAdHocCoursesMembershipWindow);
        ATUManager.asserIsTrueAndReport(true,"the user with name "+userName+" have been created","true","true");

        userToImpersonate = userName;
        //back to administration page
        driver.navigate().back();
        impersonateTabId = driver.getWindowHandle();
        admin_dashboard_page.signOut();
    }

    private void runTestAsDiffrentUser(String user) throws InterruptedException {
        tegrity.loginCourses(user);
        WaitDriverUtility.waitToPageBeLoaded(driver);
        admin_dashboard_page.clickOnTargetSubmenuUsers("Impersonate User");
        WaitDriverUtility.waitToPageBeLoaded(driver);
        impersonateUserPage.enterAsAnotherUser(userToImpersonate);
        WaitDriverUtility.waitToPageBeLoaded(driver);
        WaitDriverUtility.switchToNewTab(driver, "Tegrity - Courses");
        initPages();
        initializeCourseObject();
        course.selectCourseByName(commonCourseName);
        ATUManager.asserIsTrueAndReport(true,"impersonate to "+userToImpersonate,"","");

        WaitDriverUtility.waitToPageBeLoaded(driver);
        WaitDriverUtility.sleepInSeconds(1);
        record.clickOnRecordingInIndex(1);

        record.clickOnTheFirstCaptherWithOutTheExpand();
        playerPage.verifyTimeBufferStatusForXSec(10);
        playerPage.addTargetBookmark("First recording bookmark");
        WaitDriverUtility.sleepInSeconds(3);
        playerPage.returnToRecordingPageByNameAsUserOrGuest(commonCourseName, record);

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

        ATUManager.asserIsTrueAndReport(!targetRecordingExist, "The record should not be exist at this point", "", "");

        advancedServiceSettingsPage.signOut();
    }

    private void setCommonCourse() throws InterruptedException {
        tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
        tegrity.loginCourses("User1",true);
        WaitDriverUtility.waitToPageBeLoaded(driver);
        initializeCourseObject();
        commonCourseName = course.selectCourseThatStartingWith("abc");
        record.signOut();
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
        tegrity.loginCourses(userToImpersonate,false);
        WaitDriverUtility.waitToPageBeLoaded(driver);
        initializeCourseObject();
        course.selectCourseByName(commonCourseName);
        record.clickOnCourseTaskThenCourseSettings();
        courseSettingsPage.checkAllCourseSettingsCheckboxs();
        courseSettingsPage.clickOnOkButton();
        ATUManager.asserIsTrueAndReport(true,"The all course settigs is enable","","");
        record.signOut();
    }

}
