package com.automation.main.impersonate;


import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import com.automation.main.impersonate.helper.LocationCalculator;
import com.automation.main.page_helpers.*;
import com.automation.main.parent.BaseTest;
import com.automation.main.ping.helper.LogInAsAnotherUser;
import com.automation.main.utilities.DriverSelector;
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
public class TC12670VerifyImpersonationWorksWithAllSupportedInputTypes extends BaseTest {


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
    CreateNewUserWindow createNewUserWindow;
    LocationCalculator locationCalculator;
    private String commonCourseName;
    public ManageAdhocUsersPage manageAdhocUsersPage;
    private ManageAdHocCoursesMembershipWindow manageAdHocCoursesMembershipWindow;
    private List<String> courseListBeforeImpersonate;
    ManageAdhocCoursesEnrollmentsPage manageAdhocCoursesEnrollmentsPage;
    String supportedTypesNames;
    String currentNameType;
    String testName = "TEG-12670 Verify impersonation works with all supported input types";
    String impersonateTabId;
    List<String> userNamesIDs;

    @BeforeClass
    public void setup() {


        driver = DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());

        initPages();
        // TODO: 11/05/2017 User name as email does not working, mikael will check it.

        supportedTypesNames = "test@test,test.test,1122333,test_test";


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
        playerPage = PageFactory.initElements(driver, PlayerPage.class);
        createNewUserWindow = PageFactory.initElements(driver, CreateNewUserWindow.class);
        manageAdhocUsersPage = PageFactory.initElements(driver, ManageAdhocUsersPage.class);
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
//        //login as admin and create a users with the following supported types : email, dot in middle name,only numeric name, name with under line.
        loginAsAdminAndCreateUsersWithSupportedTypesNames();

        testSupportedTypesAsAdminOrHDadmin("Admin");

        testSupportedTypesAsAdminOrHDadmin("HelpdeskAdmin");


    }

    private void testSupportedTypesAsAdminOrHDadmin(String adminOrHDadmin) throws InterruptedException {
        for (String userNameType : userNamesIDs) {
            currentNameType = userNameType;
            saveCoursesListBeforeImpersonate();
            runTestAsDiffrentUser(adminOrHDadmin);
            ATUManager.asserIsTrueAndReport(true, "Impersonate as the following user failed : " + currentNameType, "", "");
        }
    }

    private void runTestAsDiffrentUser(String user) throws InterruptedException {
        tegrity.loginCourses(user);
        WaitDriverUtility.waitToPageBeLoaded(driver);
        admin_dashboard_page.clickOnTargetSubmenuUsers("Impersonate User");
        WaitDriverUtility.waitToPageBeLoaded(driver);
        try {
            impersonateUserPage.enterAsAnotherUser(currentNameType);
        } catch (Exception e) {
            ATUManager.asserIsTrueAndReport(false, "Impersonate as the following user failed : " + currentNameType, "", "");
        }
        WaitDriverUtility.waitToPageBeLoaded(driver);
        WaitDriverUtility.switchToNewTabAndDoNotCloseOther(driver, "Tegrity - Courses");
        initPages();
        initializeCourseObject();
        validateCoursesListAreCorrect();
        WaitDriverUtility.switchToMainTabAndCloseOthersTabs(driver, impersonateTabId);

        impersonateUserPage.signOut();
    }

    private void validateCoursesListAreCorrect() {

        List<String> afterImpersonate = course.getCourseList();
        boolean areListCoursesMatches = afterImpersonate.size() == courseListBeforeImpersonate.size();
        boolean equals = afterImpersonate.equals(courseListBeforeImpersonate);

        ATUManager.asserIsTrueAndReport(areListCoursesMatches && equals, "List of displayed courses is correct for this User : " + currentNameType, "", "");

    }

    private void loginAsAdminAndCreateUsersWithSupportedTypesNames() throws InterruptedException {
        userNamesIDs = new ArrayList<>();

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
        String[] splitedArray = supportedTypesNames.split(",");
        for (String userName : splitedArray) {
            userName = userName + System.currentTimeMillis();
            manageAdhocUsersPage.clickOnNewUser();
            createNewUserWindow.waitForVisibility(createNewUserWindow.ok_button);
            createNewUserWindow.createNewUser(userName, userName, userName + "@abdc.com", "111", "111");
            userNamesIDs.add(userName);
        }
        driver.navigate().back();

        admin_dashboard_page.clickOnTargetSubmenuCourses("Manage Ad-hoc Courses / Enrollments (Course Builder)");
        manageAdhocCoursesEnrollmentsPage.enrollStudentsToCourse(commonCourseName, userNamesIDs, manageAdHocCoursesMembershipWindow);
        //back to administration page
        driver.navigate().back();
        impersonateTabId = driver.getWindowHandle();
        admin_dashboard_page.signOut();
    }

    private void setCommonCourse() throws InterruptedException {
        tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
        tegrity.loginCourses("User1",true);
        WaitDriverUtility.waitToPageBeLoaded(driver);
        initializeCourseObject();
        commonCourseName = course.selectCourseThatStartingWith("abc");
        record.signOut();
    }

    private void saveCoursesListBeforeImpersonate() {
        LogInAsAnotherUser logInAsAnotherUser = new LogInAsAnotherUser();
        logInAsAnotherUser.openAnotherSession(currentNameType, false);
        logInAsAnotherUser.getDriver().navigate().back();
        courseListBeforeImpersonate = logInAsAnotherUser.course.getCourseList();
        logInAsAnotherUser.getDriver().quit();
    }

}
