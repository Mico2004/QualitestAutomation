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
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utils.ATUManager;
import utils.WaitDriverUtility;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC9146ValidateCourseListPingAsStudent extends BaseTest {


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

    private int theNumberOfRecordsBeenCopied = 1;
    private int numberOfRecordsfromSpecificCourseBefore;
    private int theCurrentNewRecordingsNumberBefore;
    private String dateOfRecordsCourseBefore;
    String commonCourseName;
    String nameOfCopiedRecord;
    boolean isShouldToIncreased = true;

    @BeforeClass
    public void setup() {


        driver = DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());

        //ATUReports.setWebDriver(driver);
        //ATUReports.add("set driver", true);
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
        System.out.println("Starting the test: ValidateCourseListPingAsStudent at " + DateToStr);
        ATUReports.add("Message window.", "Starting the test: ValidateCourseListPingAsStudent at " + DateToStr,
                "Starting the test: ValidateCourseListPingAsStudent at " + DateToStr, LogAs.PASSED, null);
    }


    // description = "get courses list"
    public void initializeCourseObject() throws InterruptedException {

        course = PageFactory.initElements(driver, CoursesHelperPage.class);
        course.courses = course.getCoursesListFromElement(course.course_list);
    }


    @Test(description = "Validate Course List Ping AsS tudent")
    public void test24925() throws InterruptedException//
    {
        tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);


        tegrity.loginCourses("User2");// log in courses page
        initializeCourseObject();

        commonCourseName = course.selectCourseThatStartingWith("abc");


        driver.navigate().back();
        WaitDriverUtility.waitToPageBeLoaded(driver);

        initTheBeforeValues();

        LogInAsAnotherUser logInAsAnotherUser = new LogInAsAnotherUser();
        createRecordsAsInstructor(commonCourseName, logInAsAnotherUser);

        //Validate ping after creating record
        validatePing();

        //before deleting the record we store the current status again for validate ping after deleting record
        initTheBeforeValues();

        deleteRecording(commonCourseName);
        theNumberOfRecordsBeenCopied = -theNumberOfRecordsBeenCopied;

        //Validate ping after deleting record
        validatePing();
        this.record.signOut();
        this.driver.quit();

    }

    private void validatePing() {
        validateNewRecordingsNumberIsIncreased();
        validateQuantityOfRecordsFromSpecificCourseIsValid(commonCourseName);
        vaiidateDateIsToday();
    }

    private void initTheBeforeValues() {
        theCurrentNewRecordingsNumberBefore = course.getTheCurrentNewRecordingsNumber();

        String quantityOfRecordsfromSpecificCourseBefore = course.getRecordsCoursQuntity(commonCourseName);
        numberOfRecordsfromSpecificCourseBefore = extractTheNumberFromString(quantityOfRecordsfromSpecificCourseBefore);
        dateOfRecordsCourseBefore = course.getDateOfRecordsCourse(commonCourseName);
    }

    private void deleteRecording(String recordName) {
        LogInAsAnotherUser logInAsAnotherUser = new LogInAsAnotherUser();
        logInAsAnotherUser.openAnotherSession("SuperUser");

        RecordingHelperPage record = logInAsAnotherUser.record;
        int indexOfRecordFromRecordName = record.getIndexOfRecordFromRecordName(recordName)+1;
        record.clickTheFirstCheckBoxOfRecordingsByIndex(indexOfRecordFromRecordName);
        try {
            record.clickOnRecordingTaskThenDelete();
            delete_menu = PageFactory.initElements(logInAsAnotherUser.getDriver(), DeleteMenu.class);
            delete_menu.clickOnDeleteButton();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("the record been deleted");
        logInAsAnotherUser.getDriver().quit();
    }

    private void vaiidateDateIsToday() {
        String monthFormat= "MM";
        Calendar c = Calendar.getInstance();

        int month = c.get(Calendar.MONTH);
        if (month<10){
            monthFormat="M";
        }
        String dateAfterUploadingRecord = course.getDateOfRecordsCourse(commonCourseName);
        Date date = new Date();
        DateFormat formatter = new SimpleDateFormat(monthFormat+"/dd/yyyy");
        String currentDate = formatter.format(date);
        boolean isTodayDate =  dateAfterUploadingRecord.equals(currentDate);
        ATUManager.asserIsTrueAndReport(isTodayDate, "validate that the number of records is Increased", "true", "true");

    }

    private void validateQuantityOfRecordsFromSpecificCourseIsValid(String commonCourseName) {
        String quantityOfRecordsfromSpecificCourseAfter = course.getRecordsCoursQuntity(commonCourseName);
        int numberOfRecordsfromSpecificCourseAfter = extractTheNumberFromString(quantityOfRecordsfromSpecificCourseAfter);
        boolean isIncreased = (numberOfRecordsfromSpecificCourseAfter - numberOfRecordsfromSpecificCourseBefore) == theNumberOfRecordsBeenCopied;
        ATUManager.asserIsTrueAndReport(isIncreased, "validate that the number of records is Increased", "true", "true");
    }

    private void validateNewRecordingsNumberIsIncreased(){
        boolean isNewRecordingsNumberChanged = waitUntilNewRecordingsNumberBeChanged(theCurrentNewRecordingsNumberBefore);
        ATUManager.asserIsTrueAndReport(isNewRecordingsNumberChanged, "verify that new recordings number has changed ", "true", "false");
        System.out.println("the instructor created a new records");
        int theNewRecordingsNumberAfterAdding = course.getTheCurrentNewRecordingsNumber();
        boolean areNewRecordingsIncreased = (theNewRecordingsNumberAfterAdding - theCurrentNewRecordingsNumberBefore) == theNumberOfRecordsBeenCopied;
        ATUManager.asserIsTrueAndReport(areNewRecordingsIncreased, "checking if new recordings number is increased", "true", "false");
    }

    private int extractTheNumberFromString(String quantityOfRecordsfromSpecificCourse) {
        String substring = null;
        try {
            substring  = quantityOfRecordsfromSpecificCourse.substring(0, 2);
        } catch (Exception e) {
            substring  = quantityOfRecordsfromSpecificCourse.substring(0, 1);
        }
        String trimed = substring.trim();
        return Integer.parseInt(trimed);
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
        WaitDriverUtility.waitToElementVisibility(confirmationMenuIns.ok_button,logInAsAnotherUser.getDriver());
        confirmationMenuIns.clickOnOkButton();
        insDriver.quit();
    }


    private boolean waitUntilNewRecordingsNumberBeChanged(int theCurrentNumber) {

        int numberToCheck = course.getTheCurrentNewRecordingsNumber();
        int timeOut = 60;

        while (timeOut > 0) {

            if ((numberToCheck - theCurrentNumber) == theNumberOfRecordsBeenCopied) {
                return true;
            }
            WaitDriverUtility.sleepInSeconds(1);
            numberToCheck = course.getTheCurrentNewRecordingsNumber();
        }
        return false;
    }


}
