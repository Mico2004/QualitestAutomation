package com.automation.main.ping.recording;


import Client.Robotil;
import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import com.automation.main.page_helpers.*;
import com.automation.main.parent.BaseTest;
import com.automation.main.utilities.DriverSelector;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import utils.WaitDriverUtility;

import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class PingWhenEditRecordingsPropertiesOnRecordings extends BaseTest {


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
        System.out.println("Starting the test: PingWhenEditRecordingsPropertiesOnRecordings at " + DateToStr);
        ATUReports.add("Message window.", "Starting the test: PingWhenEditRecordingsPropertiesOnRecordings at " + DateToStr,
                "Starting the test: PingWhenEditRecordingsPropertiesOnRecordings at " + DateToStr, LogAs.PASSED, null);
    }


    // description = "get courses list"
    public void initializeCourseObject() throws InterruptedException {

        course = PageFactory.initElements(driver, CoursesHelperPage.class);
        course.courses = course.getCoursesListFromElement(course.course_list);
    }


    @Test(description = "Validate Ping when newly added recordings on Recordings Tab")
    public void test24925() throws InterruptedException//
    {
        tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);

        tegrity.loginCourses("User2");// log in courses page
        initializeCourseObject();

        // 2. Get full name of Ab course.
        course.selectCourseThatStartingWith("abc");
        List<String> courseRecordingListBeforeRenaming = record.getTheCurrentRecordesNamesList(driver);
        String recordToRename = courseRecordingListBeforeRenaming.get(0);

        //login as instructor
        LogInAsAnotherUser logInAsAnotherUser = new LogInAsAnotherUser();
        logInAsAnotherUser.openAnotherSession("User1");

        String newName = "new Name";

        //delete recording
        int theIndexOfRecordToRename = getTheIndexOfRecordToRename(logInAsAnotherUser, recordToRename);
        logInAsAnotherUser.renameRecord(newName, theIndexOfRecordToRename);

        int timeOut = 60;
        boolean isRecordRenamed = false;

        while (timeOut > 0) {

            WaitDriverUtility.sleepInSeconds(1);
            List<String> courseRecordingListAfterRenaming = record.getTheCurrentRecordesNamesList(driver);
            Assert.assertEquals(courseRecordingListBeforeRenaming.size(), courseRecordingListAfterRenaming.size());

            for (int i = 0; i < courseRecordingListAfterRenaming.size(); i++) {
                if (courseRecordingListAfterRenaming.get(i).equals(newName)) {
                    isRecordRenamed = true;
                }
            }

            timeOut--;
        }

        Assert.assertTrue(isRecordRenamed);

        record.signOut();

        driver.quit();
        logInAsAnotherUser.getDriver().quit();

    }

    public int getTheIndexOfRecordToRename(LogInAsAnotherUser logInAsAnotherUser, String recordToRename) {
        WebDriver secondDriver = logInAsAnotherUser.getDriver();
        List<WebElement> recordingIsSelected = secondDriver.findElements(By.className("recordingTitle"));

        for (int i = 0; i < recordingIsSelected.size(); i++) {
            String recordText = recordingIsSelected.get(i).findElement(By.cssSelector("a")).getText();
            if (recordText.equals(recordText)) {
                return i;
            }
        }
        throw new RuntimeException("the record name not found on the list");
    }


}
