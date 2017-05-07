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
import com.automation.main.validator.ui.RecordingsUiValidator;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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
public class TC6325ValidateCourseContentPageUIasInstructor extends BaseTest {


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
    private AdvancedServiceSettingsPage advancedServiceSettingsPage;
    private CourseSettingsPage courseSettingsPage;
    private String commonCourseName;


    String testName = "6325 Validate Course Content Page UI as Instructor";

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
     tegrity.loginCourses("User1");
     WaitDriverUtility.waitToPageBeLoaded(driver);
     initializeCourseObject();
     commonCourseName = course.selectCourseThatStartingWith("abc");

     WaitDriverUtility.waitToPageBeLoaded(driver);
       record.clickOnCourseTaskThenCourseSettings();
       courseSettingsPage.enableStudentTesting();

       CourseUiValidator courseUiValidator = new CourseUiValidator(record);
       RecordingsUiValidator recordingsUiValidator = new RecordingsUiValidator(record);
       validateRecordingsNameTypeA();
       courseUiValidator.validateRecordingInformation();
       courseUiValidator.checkIfChaptersAreDisplayed();
       record.verifyThatRecordingChaptersAreDisplySequentially();
       record.verifyRecordingChaptersContainsOrdinalNumberAndContainLengthFromToInAFormat();
       record.verifyThatRecordingChaptersContainImagePreview();
       recordingsUiValidator.validateOnHoverAndPlaySymbole();

       //clicking on recording title will collapsed the chapters section
       record.first_recording.click();
       //clicking on recording title will expand the chapters section
       record.first_recording.click();

       validateBreadcrumbsPositiobAndSpecialSymbol();
       validateUnderlineDisplayedOnHover();

       record.breadcrumbs_courses_link.click();

       course.selectCourseByName(commonCourseName);

       verifyCourseNameDisplayed();

       record.clickOnCourseTaskThenCourseSettings();
       courseSettingsPage.enableStudentTesting();
       courseUiValidator.validateStartRecordingsAtRightTop();

       verifyRecordingsTabOpenByDefault();

       //view
       courseUiValidator.validateViewDropDown();
       //course task
       courseUiValidator.validateCourseTaskDropDown();

     //Clicking on All checkbox will selected the other checkBoxs
     record.clickAllCheckBoxAndVerifyOtherCheckBoxisSelected(true);

     //Clicking on All checkbox will selected the other checkBoxs
     record.clickAllCheckBoxAndVerifyOtherCheckBoxisSelected(false);

     record.convertRecordingsListToDate();/// check sort by date
     record.verifyRecordingSortedByDate(record.recordings_list_date_string);

     driver.quit();
    }


    private void verifyCourseNameDisplayed() {
        String courseName = record.course_title.getText();
        ATUManager.asserIsTrueAndReport(courseName.equals(commonCourseName),"The course name caption is displayed at the top left corner (Under the  'Breadcrumb' )","","");
    }


    private void verifyRecordingsTabOpenByDefault() {
        if (record.recording_tasks_button.isDisplayed()){
            ATUManager.asserIsTrueAndReport(true,"validate recording tab open by default","","");
            return;
        }
        ATUManager.asserIsTrueAndReport(false,"validate recording tab open by default","","");


    }

    private void validateUnderlineDisplayedOnHover() {
        record.moveToElement(record.breadcrumbs_courses_link, driver).perform();
        String textDecoratiob = record.breadcrumbs_courses_link.getCssValue("text-decoration");
        ATUManager.asserIsTrueAndReport(textDecoratiob.contains("underline"),"The exact link the user has hovered over becomes underlined.","","");

    }

    private void validateBreadcrumbsPositiobAndSpecialSymbol() {
        LocationCalculator locationCalculator = new LocationCalculator();
        //verify Bread crumbs located under logo
        locationCalculator.isElementUnderOnAnotherElement(record.breadcrumbs, record.logo);
        String theRightSymbolOnBreadCrumbs = record.breadcrumbs.findElements(By.tagName("li")).get(0).getText();
        ATUManager.asserIsTrueAndReport(theRightSymbolOnBreadCrumbs.contains(">"), "the '>' should be displayed in breadcrumbs link", "", "");
    }

    private void validateRecordingsNameTypeA() {
        record.waitForVisibility(record.first_recording);
        WebElement elementParent = WaitDriverUtility.getElementParent(record.first_recording);
        boolean isAlinkTag = elementParent.getTagName().equals("a");
        ATUManager.asserIsTrueAndReport(isAlinkTag, "The cursor change its view to the hand pointer", "", "");
    }

    private void TheGreaterThanSymbolTurnsToV() {
        String script = "return window.getComputedStyle(document.querySelector('#Recording1'),':before').getPropertyValue('size')";
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String content = (String) js.executeScript(script);
        System.out.println(content);
    }

    private void validateCourseCrumbExistAndClick() {

        WebElement courseCrumb = WaitDriverUtility.waitForElementBeDisplayed(driver, By.cssSelector("a[title='" + commonCourseName + "']"), 10);
        courseCrumb.click();
        WaitDriverUtility.sleepInSeconds(1);
        WaitDriverUtility.waitToPageBeLoaded(driver);
        boolean isTestedCoursePageDisplayed = driver.getTitle().contains("Tegrity - " + commonCourseName);
        ATUManager.asserIsTrueAndReport(isTestedCoursePageDisplayed, "After clicking on breadCrumbs the course page should be displayed", "", "");
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
