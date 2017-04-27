package com.automation.main.ping;


import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import com.automation.main.page_helpers.*;
import com.automation.main.parent.BaseTest;
import com.automation.main.ping.helper.LogInAsAnotherUser;
import com.automation.main.utilities.DriverSelector;
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
import java.util.List;

//@Listeners({ATUReportsListener.class, ConfigurationListener.class, MethodListener.class})
public class TC9158ValidatePingWhenNewlyAddedRecordingsOnRecordingsTab extends BaseTest {


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
    WebDriver driver;
    WebDriverWait wait;
    public static WebDriver thread_driver;
    CopyMenu copy;
    int theNumberOfRecordingsBefore;
    String commonCourseName;
    String nameOfCopiedRecord;

    String testName = "TC9158 Validate Ping When Newly Added Recordings On Recordings Tab";

    @BeforeClass
    public void setup() {


        driver = DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());

        tegrity = PageFactory.initElements(driver, LoginHelperPage.class);

        record = PageFactory.initElements(driver, RecordingHelperPage.class);
        copy = PageFactory.initElements(driver, CopyMenu.class);
        delete_menu = PageFactory.initElements(driver, DeleteMenu.class);
        admin_dashboard_page = PageFactory.initElements(driver, AdminDashboardPage.class);
        admin_dashboard_view_course_list = PageFactory.initElements(driver, AdminDashboardViewCourseList.class);
        move_window = PageFactory.initElements(driver, MoveWindow.class);
        confirmation_menu = PageFactory.initElements(driver, ConfirmationMenu.class);

        wait = new WebDriverWait(driver, 30);

        Date curDate = new Date();
        String DateToStr = DateFormat.getInstance().format(curDate);
        System.out.println("Starting the test: " + testName + "at " + DateToStr);
        ATUReports.add("Message window.", "Starting the test: " + testName + " at " + DateToStr,
                "Starting the test: " + testName + " at " + DateToStr, LogAs.PASSED, null);
    }


    public void initializeCourseObject() throws InterruptedException {
        course = PageFactory.initElements(driver, CoursesHelperPage.class);
        course.courses = course.getCoursesListFromElement(course.course_list);
    }


    @Test()
    public void test24925() throws InterruptedException//
    {

        validatePing();

    }

    private void validatePing() throws InterruptedException {
        tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);

        tegrity.loginCourses("User2");// log in courses page
        initializeCourseObject();

        commonCourseName = course.selectCourseThatStartingWith("abc");
        theNumberOfRecordingsBefore = record.getCourseRecordingList().size();

        WaitDriverUtility.waitToPageBeLoaded(driver);
        String recordingToClickOnCheckBox = record.getCourseRecordingList().get(0);

        record.clickCheckBoxByName(recordingToClickOnCheckBox);
        LogInAsAnotherUser logInAsAnotherUser = new LogInAsAnotherUser();
        createRecordsAsInstructor(commonCourseName, logInAsAnotherUser);

        boolean isUplouded = waitUntilNewRecordingsBeUploud(theNumberOfRecordingsBefore);

        ATUManager.asserIsTrueAndReport(isUplouded, "waiting for recordings be uplouded", "true", "true");

        int targetRecordingIndex = record.getTargetRecordingIndex(recordingToClickOnCheckBox);
        WebElement checkboxAccordingToIndex = record.getCheckboxAccordingToIndex(targetRecordingIndex);
        boolean isSelected = checkboxAccordingToIndex.isSelected();
        ATUManager.asserIsTrueAndReport(isSelected, "the checkbox should be clicked at this point", "true", "true");
        record.signOut();
    }


    private void createRecordsAsInstructor(String commonCourseName, LogInAsAnotherUser logInAsAnotherUser) throws InterruptedException {
        logInAsAnotherUser.openAnotherSession("SuperUser");
        WebDriver insDriver = logInAsAnotherUser.getDriver();
        insDriver.navigate().back();
        WaitDriverUtility.waitToPageBeLoaded(insDriver);
        CoursesHelperPage insCourse = logInAsAnotherUser.course;
        RecordingHelperPage insRecordPage = logInAsAnotherUser.record;


        insCourse.selectCourseThatStartingWith("BankValid");
        nameOfCopiedRecord = insRecordPage.clickTheFirstCheckBoxOfRecordingsByIndex(1);

        insRecordPage.copyRecordsToAnotherCourse(commonCourseName);

        ConfirmationMenu confirmationMenuIns = PageFactory.initElements(logInAsAnotherUser.getDriver(), ConfirmationMenu.class);
        WaitDriverUtility.waitToElementVisibility(confirmationMenuIns.ok_button, logInAsAnotherUser.getDriver());
        confirmationMenuIns.clickOnOkButton();
        insDriver.quit();
    }


    private boolean waitUntilNewRecordingsBeUploud(int theCurrentNumber) {

        List<String> courseRecordingList = record.getCourseRecordingList();
        int timeOut = 60;
        while (timeOut > 0) {
            if (courseRecordingList.size() > theCurrentNumber) {
                return true;
            }
            WaitDriverUtility.sleepInSeconds(1);
            courseRecordingList = record.getCourseRecordingList();
        }
        return false;
    }


}
