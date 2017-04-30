package com.automation.main.impersonate;


import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import com.automation.main.page_helpers.*;
import com.automation.main.parent.BaseTest;
import com.automation.main.utilities.DriverSelector;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utils.ATUManager;
import utils.WaitDriverUtility;

import java.text.DateFormat;
import java.util.Date;

@Listeners({ATUReportsListener.class, ConfigurationListener.class, MethodListener.class})
public class TC9705VerifyTheImpersonateUserUI extends BaseTest {


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
    private TopBarHelper topBarHelper;
    private BottomFooter bottom_footer;
    private ImpersonateUser impersonateUserPage;


    String testName = "TC9705 Verify the Impersonate User UI";

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
        topBarHelper = PageFactory.initElements(driver, TopBarHelper.class);
        bottom_footer = PageFactory.initElements(driver, BottomFooter.class);
        impersonateUserPage = PageFactory.initElements(driver, ImpersonateUser.class);


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
    public void test() throws InterruptedException {
        //login as Administrator
        tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
        tegrity.loginCourses("Admin");
        WaitDriverUtility.waitToPageBeLoaded(driver);
        admin_dashboard_page.clickOnTargetSubmenuUsers("Impersonate User");
        WaitDriverUtility.waitToPageBeLoaded(driver);

        //Verify bar navigation is displayed
        topBarHelper.verifyNavigationBarDisplayed();
        //Verify header is displayed
        topBarHelper.verfiyThatTheLogoAtTheTopLeft();

        bottom_footer.verifyThatTheTegrityLogoDisplayedOnBottomLeftSide();

        //Verify breadCrubs is displayed
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement isBreadCrumbsDisplayed = wait.until(ExpectedConditions.elementToBeClickable(topBarHelper.breadcrumbs_box));
        ATUManager.asserIsTrueAndReport(isBreadCrumbsDisplayed != null, "verify breadCrumbs navigation is displayed", "", "");

        //Verify impersonate title text is Impersonate user
        WebElement impersonateUserTitle = impersonateUserPage.ImpersonateUserTitle;
        boolean isTitledisplayed = impersonateUserTitle.isDisplayed();
        boolean equals = impersonateUserTitle.getText().equals("Impersonate user");
        ATUManager.asserIsTrueAndReport(isTitledisplayed == equals, "verify Impersonate User Title is text", "", "");

        //Verify user field text is "Enter user to impersonate:"
        boolean isUserFieldTextValid = impersonateUserPage.userField.getText().equals("Enter user to impersonate:");
        ATUManager.asserIsTrueAndReport(isUserFieldTextValid, "verify field label is equals to 'Enter user to impersonate:'", "", "");

        //Verify button is clickable
        WebElement isButtonClickable = wait.until(ExpectedConditions.elementToBeClickable(impersonateUserPage.impersonateButton));
        ATUManager.asserIsTrueAndReport(isButtonClickable != null, " Impersonate button is clickable ", "", "");

        //Verify cursor have been changed on mouse over to 'hand'
        WaitDriverUtility.performHoverOnElement(driver, impersonateUserPage.impersonateButton);
        String displayedCursor = impersonateUserPage.impersonateButton.getCssValue("cursor");
        ATUManager.asserIsTrueAndReport(displayedCursor.equals("pointer"), "The cursor sholud be changed to hand", "true", "false");

        //Verify existence of hint with text
        WebElement toolTiptitle = impersonateUserPage.toolTip;
        ATUManager.asserIsTrueAndReport(toolTiptitle != null, "the hint should be displayed", "true", "true");

    }

}
