package com.automation.main.impersonate;


import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import com.automation.main.page_helpers.*;
import com.automation.main.parent.BaseTest;
import com.automation.main.ping.helper.LogInAsAnotherUser;
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
import java.util.Date;
import java.util.List;

@Listeners({ATUReportsListener.class, ConfigurationListener.class, MethodListener.class})
    public class TC9711VerifyThatImpersonatedUsersIDIsNotCaseSensitive extends BaseTest {



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
    private String impersonateTabId;

    WebDriverWait wait;
    public static WebDriver thread_driver;
    CopyMenu copy;
    private AdvancedServiceSettingsPage advancedServiceSettingsPage;
    private CourseSettingsPage courseSettingsPage;
    private ImpersonateUser impersonateUserPage;
    private SearchPage searchPage;
    private PlayerPage playerPage;
    private List<String> courseListBeforeImpersonate;
    private String commonCourseName;


    String testName = "TC9711 Verify That Impersonated Users ID Is Not Case Sensitive";

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
        playerPage = PageFactory.initElements(driver, PlayerPage.class);


    }


    public void initializeCourseObject() throws InterruptedException {
        course = PageFactory.initElements(driver, CoursesHelperPage.class);
        course.courses = course.getCoursesListFromElement(course.course_list);
    }


    @Test()
    public void test() throws InterruptedException {


       // login as student and save the list courses names
        saveCoursesListBeforeImpersonate();


        tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);


        //save the main tab id
        impersonateTabId = driver.getWindowHandle();

        runTestAsDiffrentUser("Admin",true);

        runTestAsDiffrentUser("Admin",false);


        runTestAsDiffrentUser("HelpdeskAdmin",true);

        runTestAsDiffrentUser("HelpdeskAdmin",false);
    }

    private void saveCoursesListBeforeImpersonate() {
        LogInAsAnotherUser logInAsAnotherUser = new LogInAsAnotherUser();
        logInAsAnotherUser.openAnotherSession("User3",true);
        logInAsAnotherUser.getDriver().navigate().back();
        courseListBeforeImpersonate = logInAsAnotherUser.course.getCourseList();
        logInAsAnotherUser.getDriver().quit();
    }

    private void runTestAsDiffrentUser(String user, boolean isUpperCase) throws InterruptedException {
        tegrity.loginCourses(user);
        WaitDriverUtility.waitToPageBeLoaded(driver);
        admin_dashboard_page.clickOnTargetSubmenuUsers("Impersonate User");
        WaitDriverUtility.waitToPageBeLoaded(driver);
        String instructor = PropertyManager.getProperty("User3");
        instructor = getUpperOrMixedText(instructor,isUpperCase);
        impersonateUserPage.enterAsAnotherUser(instructor);
        WaitDriverUtility.waitToPageBeLoaded(driver);
        WaitDriverUtility.switchToNewTabAndDoNotCloseOther(driver, "Tegrity - Courses");
        initPages();
        initializeCourseObject();
        validateCoursesListAreCorrect(isUpperCase);
        WaitDriverUtility.switchToMainTabAndCloseOthersTabs(driver, impersonateTabId);
        impersonateUserPage.signOut();
    }

    private String getUpperOrMixedText(String text, boolean upperOrMixed) {

        if (upperOrMixed) {
            return text.toUpperCase();
        }

        char[] chars = text.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (i % 2 == 0) {
                chars[i] = Character.toLowerCase(chars[i]);
            } else {
                chars[i] = Character.toUpperCase(chars[i]);
            }
        }
        return String.copyValueOf(chars);
    }

    private void validateCoursesListAreCorrect(boolean isUpperCase) {
        String description="mixed";
        if (isUpperCase){
            description ="upper case";
        }
        List<String> afterImpersonate = course.getCourseList();
        boolean areListCoursesMatches = afterImpersonate.size() == courseListBeforeImpersonate.size();
        boolean equals = afterImpersonate.equals(courseListBeforeImpersonate);

        ATUManager.asserIsTrueAndReport(areListCoursesMatches && equals, "List of displayed courses is correct for this user, with "+description+" name", "", "");

    }


    private void prepareDataTest() throws InterruptedException {
        tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
        tegrity.loginCourses("SuperUser");
        WaitDriverUtility.waitToPageBeLoaded(driver);
        initializeCourseObject();

        course.selectCourseThatStartingWith("BankValid");

        record.clickOnRecordingsTab();
        selectAndCopy();
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
