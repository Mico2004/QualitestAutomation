package com.automation.main.ping;


import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import com.automation.main.page_helpers.*;
import com.automation.main.ping.helper.LogInAsAnotherUser;
import com.automation.main.utilities.DriverSelector;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC9159ValidatePingWhenDeletedRecordingsOnRecordingsTab {

    // Set Property for ATU Reporter Configuration
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
        System.out.println("Starting the test: TC24925ValidateMoveRecordingDropdownAndSearchFunctionalities at " + DateToStr);
        ATUReports.add("Message window.", "Starting the test: TC24925ValidateMoveRecordingDropdownAndSearchFunctionalities at " + DateToStr,
                "Starting the test: TC24925ValidateMoveRecordingDropdownAndSearchFunctionalities at " + DateToStr, LogAs.PASSED, null);
    }

    @AfterClass
    public void quitBrowser() {
        driver.quit();
    }


    // description = "get courses list"
    public void initializeCourseObject() throws InterruptedException {

        course = PageFactory.initElements(driver, CoursesHelperPage.class);
        course.courses = course.getCoursesListFromElement(course.course_list);
    }


    @Test(description = "TC 24925 Validate Move Recording Dropdown And Search Functionalities")
    public void test24925() throws InterruptedException//
    {
        tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);

        tegrity.loginCourses("User2");// log in courses page
        initializeCourseObject();

        // 2. Get full name of Ab course.
        course.selectCourseThatStartingWith("abc");
        List<String> courseRecordingListFromStudentBrowser = record.getTheCurrentRecordesNamesList(driver);

        //login as instructor
        LogInAsAnotherUser logInAsAnotherUser = new LogInAsAnotherUser();
        logInAsAnotherUser.openAnotherSession("User1",true);

        //delete helper
        logInAsAnotherUser.deleteTheFirstRecored();
        //check the list of student recordings
        Assert.assertTrue(checkIfRecordingsListHasChanged(courseRecordingListFromStudentBrowser));

        logInAsAnotherUser.killWebDriver();

        // 3. Logout.
        record.signOut();

    }

    private boolean checkIfRecordingsListHasChanged(List<String> oldRecordingsListNames) {
        List<String> theCurrentRecordesNamesList = record.getTheCurrentRecordesNamesList(driver);
        int expectedNumberOfRecordings = oldRecordingsListNames.size() - 1;
        int timeOutInSeconds = 60;
        while (timeOutInSeconds > 0) {
            if (expectedNumberOfRecordings == theCurrentRecordesNamesList.size()) {
                return true;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            theCurrentRecordesNamesList = record.getTheCurrentRecordesNamesList(driver);
        }
        return false;
    }

    @BeforeMethod
    private void verifyThereIsRecordings() {
        LogInAsAnotherUser logInAsAnotherUser = new LogInAsAnotherUser();
        logInAsAnotherUser.openAnotherSession("User1",true);
        List<String> listOfCurrentRecordings = logInAsAnotherUser.getListOfCurrentRecordings();
        if (listOfCurrentRecordings.size() > 0) {
            return;
        }
    }

}
