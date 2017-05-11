package com.automation.main.ping.helper;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import com.automation.main.page_helpers.*;
import com.automation.main.utilities.DriverSelector;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import utils.WaitDriverUtility;

import java.util.List;

/**
 * Created by Lenovo on 23/04/2017.
 */
public class LogInAsAnotherUser {

    private ConfirmationMenu confirmation_menu;
    private LoginHelperPage tegrity;
    public CoursesHelperPage course;
    public RecordingHelperPage record;
    private DeleteMenu delete_menu;
    private MoveWindow move_window;
    private AdminDashboardPage admin_dashboard_page;
    private AdminDashboardViewCourseList admin_dashboard_view_course_list;
    WebDriver driver;
    WebDriverWait wait;
    public static WebDriver thread_driver;
    CopyMenu copy;

    public void openAnotherSession(String user,boolean isUserFromPropertiesFile) {
        initBrowser();

        try {
            tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
            tegrity.loginCourses(user,isUserFromPropertiesFile);// log in courses page
            initializeCourseObject();
            course.selectCourseThatStartingWith("abc");
            record.clickOnRecordingsTab();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void initBrowser() {
        driver = DriverSelector.getDriver(DriverSelector.BrowserType.Chrome);

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
    }

    public void initializeCourseObject() throws InterruptedException {

        course = PageFactory.initElements(driver, CoursesHelperPage.class);
        course.courses = course.getCoursesListFromElement(course.course_list);
    }

    public WebDriver getDriver() {
        return driver;
    }

    public List<String> getListOfCurrentRecordings() {
        return record.getTheCurrentRecordesNamesList(driver);
    }

    public void deleteTheFirstRecored() {
        findAndClickAtCheckBoxRecord(0);

        WaitDriverUtility.sleepInSeconds(1);
        WaitDriverUtility.waitForElementBeDisplayed(driver, By.id("RecordingTasks"), 10);
        expendAndClickOnDropList("DeleteTask2");

        WaitDriverUtility.waitToPageBeLoaded(driver);
        driver.switchTo().parentFrame();
        WaitDriverUtility.waitForElementBeDisplayed(driver, By.id("DeleteButton"), 10).click();
    }

    public void findAndClickAtCheckBoxRecord(int i) {
        List<WebElement> recordingIsSelected = driver.findElements(By.className("recordingData"));
        recordingIsSelected.get(i).findElement(By.cssSelector("input")).click();
    }

    private void expendAndClickOnDropList(String optionElementIdToClick) {
        try {
            System.out.println("clickOnRecordingTaskThen1");
            WaitDriverUtility.waitForElementBeDisplayed(driver, By.id("RecordingTasks"), 10);
            ((JavascriptExecutor) driver).executeScript("document.getElementById(\"" + optionElementIdToClick + "\").click();");
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ModalDialogHeader")));
            ATUReports.add(" Select Recording Tasks -> " + optionElementIdToClick + " menu items", optionElementIdToClick + " window is displayed",
                    optionElementIdToClick + " window is displayed", LogAs.PASSED, null);
            Assert.assertTrue(true);
            return;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("clickOnRecordingTaskThen6");
            ATUReports.add(" Select Recording Tasks -> " + optionElementIdToClick + " menu items", optionElementIdToClick + " window is displayed",
                    optionElementIdToClick + " window isn't displayed", LogAs.FAILED, new CaptureScreen(CaptureScreen.ScreenshotOf.BROWSER_PAGE));
            System.out.println(optionElementIdToClick + " window not displayed");
            Assert.assertTrue(false);
        }
    }

    public void renameRecord(String newName,int index){
        findAndClickAtCheckBoxRecord(index);
        WaitDriverUtility.sleepInSeconds(1);
        WaitDriverUtility.waitForElementBeDisplayed(driver, By.id("RecordingTasks"), 10);
        expendAndClickOnDropList("EditRecordingProperties");
        WaitDriverUtility.waitToPageBeLoaded(driver);
        driver.switchTo().parentFrame();
        WebElement recordNameTextBox = WaitDriverUtility.waitForElementBeDisplayed(driver, By.id("recordingTItle"), 10);
        recordNameTextBox.clear();
        recordNameTextBox.sendKeys(newName);
        WaitDriverUtility.waitForElementBeDisplayed(driver, By.id("EditButton"), 10).click();
        WebElement alertWindow = WaitDriverUtility.waitForElementBeDisplayed(driver, By.id("alertWindow"), 30);
        alertWindow.findElement(By.tagName("button")).click();

    }

    public void killWebDriver() {
        driver.quit();
    }

}
