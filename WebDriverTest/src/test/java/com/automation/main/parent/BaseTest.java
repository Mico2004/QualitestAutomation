package com.automation.main.parent;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import com.automation.main.page_helpers.*;
import com.automation.main.utilities.DriverSelector;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import utils.ATUManager;

import java.text.DateFormat;
import java.util.Date;


public class BaseTest extends GroupsManger implements BasicTest {
    public ManageAdhocCoursesEnrollmentsPage mange_adhoc_course_enrollments;
    public ManageAdHocCoursesMembershipWindow mangage_adhoc_courses_membership_window;
    public EditRecordingPropertiesWindow edit_recording_properties_window;
    public PlayerPage player_page;
    public AdminDashboardViewCourseList admin_dashboard_view_course_list;
    public AdminDashboardPage admin_dash_board_page;
    public CourseSettingsPage course_settings_page;
    public AddAdditionalContentLinkWindow add_additional_content_link_window;
    public EditRecording edit_recording;
    public BottomFooter bottom_footer;
    public SearchPage search_page;
    public TopBarHelper top_bar_helper;
    public LoginHelperPage tegrity;
    public CoursesHelperPage course;
    public RecordingHelperPage record;
    public ConfirmationMenu confirm_menu;
    WebDriverWait wait;
    public static WebDriver thread_driver;
    public CopyMenu copy;
    public String current_course;
    public String targetCourse;
    public String clickedRecording;
    public DesiredCapabilities capability;
    public ConfirmationMenu confirmationMenu;
    public CustomAnalysisPage customAnalysisPage;

    protected  WebDriver driver;

    @BeforeSuite
    private void beforeSuite(){
        System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

    }

    @BeforeMethod(alwaysRun = true)
    public void printTestDeatils(ITestContext result){

        String testName = result.getAllTestMethods()[0].getTestClass().getName();
//                result.getInstanceName();
        Date curDate = new Date();
        String DateToStr = DateFormat.getInstance().format(curDate);
        System.out.println("Starting the test: " + testName + "at " + DateToStr);
        ATUReports.add("Message window.", "Starting the test: " + testName + " at " + DateToStr,
                "Starting the test: " + testName + " at " + DateToStr, LogAs.PASSED, null);
    }
    @AfterMethod
    public void tearDownTest(ITestResult testResult) {

        int status = testResult.getStatus();
        if (status == ITestResult.SUCCESS) {
            System.out.println("Done.");
            ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);
        }

        if (status == ITestResult.FAILURE) {
            System.out.println("the test has failed "+testResult.getThrowable().getMessage());
            ATUReports.add("Message window." + testResult.getThrowable().getMessage(), "Done.", "Done.", LogAs.FAILED, null);
        }

        if (driver!=null){
            driver.quit();
        }
    }

    @BeforeClass
    public void initPageObjects(){
        driver = DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());
        ATUReports.add("selected browser type", LogAs.PASSED, new CaptureScreen( CaptureScreen.ScreenshotOf.DESKTOP));

        tegrity = PageFactory.initElements(driver, LoginHelperPage.class);

        record = PageFactory.initElements(driver, RecordingHelperPage.class);
        copy = PageFactory.initElements(driver, CopyMenu.class);

        confirm_menu = PageFactory.initElements(driver, ConfirmationMenu.class);

        top_bar_helper = PageFactory.initElements(driver,TopBarHelper.class);
        search_page = PageFactory.initElements(driver, SearchPage.class);

        bottom_footer = PageFactory.initElements(driver, BottomFooter.class);
        mangage_adhoc_courses_membership_window = PageFactory.initElements(driver, ManageAdHocCoursesMembershipWindow.class);
        edit_recording = PageFactory.initElements(driver, EditRecording.class);
        mange_adhoc_course_enrollments = PageFactory.initElements(driver, ManageAdhocCoursesEnrollmentsPage.class);
        add_additional_content_link_window = PageFactory.initElements(driver, AddAdditionalContentLinkWindow.class);
        course_settings_page = PageFactory.initElements(driver, CourseSettingsPage.class);
        admin_dash_board_page = PageFactory.initElements(driver, AdminDashboardPage.class);
        admin_dashboard_view_course_list = PageFactory.initElements(driver, AdminDashboardViewCourseList.class);
        player_page = PageFactory.initElements(driver, PlayerPage.class);
        edit_recording_properties_window = PageFactory.initElements(driver, EditRecordingPropertiesWindow.class);
        confirmationMenu = PageFactory.initElements(driver, ConfirmationMenu.class);
         customAnalysisPage = PageFactory.initElements(driver, CustomAnalysisPage.class);
    }

    public void initializeCourseObject() throws InterruptedException {

        course = PageFactory.initElements(driver, CoursesHelperPage.class);
        course.courses = course.getCoursesListFromElement(course.course_list);
    }

    public void login(String userName,boolean isUserFromPropertiesFile){
        try {
            tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
            initializeCourseObject();
            tegrity.loginCourses(userName,isUserFromPropertiesFile);
        } catch (Exception e) {
            ATUManager.asserIsTrueAndReport(false,"Login as "+userName+" has failed !","","");
        }
    }

}
