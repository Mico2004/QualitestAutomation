package com.automation.main.page_helpers;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import com.automation.main.page_helpers.Page;
import com.automation.main.utilities.DriverSelector;

import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;
import junitx.util.PropertyManager;
import utils.ATUManager;
import utils.UniversityConfigure;
import utils.WaitDriverUtility;

import java.net.HttpURLConnection;
import java.net.URL;

@Listeners({ATUReportsListener.class, ConfigurationListener.class, MethodListener.class})
public class LoginHelperPage extends Page {
    // Set Property for ATU Reporter Configuration
    {
        System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");
    }

    @FindBy(name = "UserName")
    public WebElement usernamefield;
    @FindBy(name = "Password")
    public WebElement passfield;
    @FindBy(id = "ButtonLogin")
    public WebElement button_login;
    @FindBy(css = "[class=\"form-row\"]>.error-text")
    public WebElement errorHeader;
    @FindBy(css = ".btn.btn-primary")
    public WebElement eula_accept_button;
    @FindBy(xpath = "//button[@ng-click=\"loginAsGuest()\"]")
    public WebElement Login_as_guest_button;
    @FindBy(xpath = "//*[@id=\"main\"]/form/div[2]/div[6]/p")
    public WebElement Login_as_guest_info;
    private CoursesHelperPage course;


    public LoginHelperPage(WebDriver driver) throws Exception {
        super(driver);
        String urlToUniversity = UniversityConfigure.getURlUniversityName();
        setPageTitle("Tegrity Lecture Capture");
        setPageUrl(urlToUniversity);
    }

    public void setUserText(String text) {
        setElementText(usernamefield, text);
    }

    public void setPassText(String text) {

        setElementText(passfield, text);
    }

    public boolean checkError(String text)// check if there is an error
    {
        try {
            return errorHeader.getText().equals(text);
        } catch (NoSuchElementException e) {
            return false;
        }

    }

    public void fillUserFromProperyFile(String user_name)// fill user name
    {
        String user = PropertyManager.getProperty(user_name);
        ATUManager.asserIsTrueAndReport(true,"User with name : '"+user+"' sign in");
        setUserText(user);
    }

    public void fillPass()// fill password
    {
        String pass = PropertyManager.getProperty("Password");
        setPassText(pass);

    }

    public void initializeCourse() {
        course = PageFactory.initElements(driver, CoursesHelperPage.class);
    }


    public void loginCourses(String user_name, boolean isUserFromPropertiesFile) throws InterruptedException {
        isMHCampuseUp();
        initializeCourse();

        try {
            waitForVisibility(usernamefield);
            waitForVisibility(button_login);
            waitForVisibility(passfield);
            if (isUserFromPropertiesFile) {
                fillUserFromProperyFile(user_name);
                user_name = PropertyManager.getProperty(user_name);
            } else {
                setUserText(user_name);
                ATUManager.asserIsTrueAndReport(true,"User with name : '"+user_name+"' sign in");

            }
            fillPass();
            clickElementJS(button_login);
            try {
                new WebDriverWait(driver, 30).until(ExpectedConditions.not(ExpectedConditions.titleContains("Tegrity Lecture Capture")));
            } catch (TimeoutException e) {
                ATUReports.add("Login Timeout (Screenshot)", user_name, "Login Failed", LogAs.FAILED,
                        new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
                Assert.assertTrue(false);

            }
            if (driver.getTitle().contains("Tegrity EULA")) {
                try {
                    eula_accept_button.click();
                    System.out.println("Clicked on accept Eula button");
                    ATUReports.add("Click on EULA accept", user_name, "Accept clicked", "Accept clicked", LogAs.PASSED, null);
                    new WebDriverWait(driver, 30)
                            .until(ExpectedConditions.not(ExpectedConditions.titleContains("Tegrity - Courses")));
                    ;
                } catch (Exception msg) {
                    System.out.println("No EULA button.");
                    ATUReports.add("Click on EULA accept", user_name, "Accept clicked", "Acceot wasn't clicked", LogAs.FAILED,
                            new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
                    Assert.assertTrue(false);
                }
            } else if (driver.getTitle().contains("Tegrity - Courses")) {
                ATUReports.add("Tegrity courses home page is visible", user_name, "Course List page is displayed",
                        "Course List page is displayed", LogAs.PASSED, null);
            }
        } catch (Exception e) {
            ATUReports.add("Login Failed (Screenshot)", user_name, "Login Success", "Login failed", LogAs.FAILED,
                    new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
            Assert.assertTrue(false);
        }
        Thread.sleep(2000);
        try {
            new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfAllElements(course.course_list));
        } catch (Exception e) {
            System.out.println("No Courses for the user");
        }

    }// login


    public void loginCourses(String user_name) throws InterruptedException// login
    // courses
    {
        isMHCampuseUp();
        initializeCourse();
        try {
            waitForVisibility(usernamefield);
            waitForVisibility(button_login);
            waitForVisibility(passfield);
            fillUserFromProperyFile(user_name);
            fillPass();
            clickElementJS(button_login);
            try {
                new WebDriverWait(driver, 30).until(ExpectedConditions.not(ExpectedConditions.titleContains("Tegrity Lecture Capture")));
            } catch (TimeoutException e) {
                ATUReports.add("Login Timeout (Screenshot)", PropertyManager.getProperty(user_name), "Login Failed", LogAs.FAILED,
                        new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
                Assert.assertTrue(false);

            }
            if (driver.getTitle().contains("Tegrity EULA")) {
                try {
                    eula_accept_button.click();
                    System.out.println("Clicked on accept Eula button");
                    ATUReports.add("Click on EULA accept", PropertyManager.getProperty(user_name), "Accept clicked", "Accept clicked", LogAs.PASSED, null);
                    new WebDriverWait(driver, 30)
                            .until(ExpectedConditions.not(ExpectedConditions.titleContains("Tegrity - Courses")));
                    ;
                } catch (Exception msg) {
                    System.out.println("No EULA button.");
                    ATUReports.add("Click on EULA accept", PropertyManager.getProperty(user_name), "Accept clicked", "Acceot wasn't clicked", LogAs.FAILED,
                            new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
                    Assert.assertTrue(false);
                }
            } else if (driver.getTitle().contains("Tegrity - Courses")) {
                if (PropertyManager.getProperty(user_name)!=null){
                    user_name = PropertyManager.getProperty(user_name);
                }

                ATUReports.add("Tegrity courses home page is visible", user_name, "Course List page is displayed",
                        "Course List page is displayed", LogAs.PASSED, null);
            }
        } catch (Exception e) {
            ATUReports.add("Login Failed (Screenshot)", PropertyManager.getProperty(user_name), "Login Success", "Login failed", LogAs.FAILED,
                    new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
            Assert.assertTrue(false);
        }
        Thread.sleep(2000);
        try {
            new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfAllElements(course.course_list));
        } catch (Exception e) {
            System.out.println("No Courses for the user");
        }

    }

    public void loginAdmin(String user_name) throws InterruptedException// login
    // courses
    {
        try {


            isMHCampuseUp();
            waitForVisibility(usernamefield);
            waitForVisibility(button_login);
            waitForVisibility(passfield);
            fillUserFromProperyFile(user_name);
            fillPass();
            clickElementJS(button_login);
            new WebDriverWait(driver, 30).until(ExpectedConditions.titleContains("Tegrity"));
            WaitDriverUtility.waitToPageBeLoaded(driver);
            ATUReports.add("Login as", PropertyManager.getProperty(user_name), "Success login", "Success login", LogAs.PASSED, null);
        } catch (Exception e) {
            ATUReports.add("Login as", PropertyManager.getProperty(user_name), "Success login", "Success fail (Screenshot)", LogAs.FAILED,
                    new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
            Assert.assertTrue(false);
        }

    }

    /// login using parameter not with local property
    public void loginCoursesByParameter(String user_name) throws InterruptedException// login
    // courses
    {
        isMHCampuseUp();
        initializeCourse();
        try {
            waitForVisibility(usernamefield);
            waitForVisibility(button_login);
            waitForVisibility(passfield);
            setUserText(user_name);
            fillPass();
            clickElementJS(button_login);
            try {
                new WebDriverWait(driver, 30)
                        .until(ExpectedConditions.not(ExpectedConditions.titleContains("Tegrity Lecture Capture")));
            } catch (TimeoutException e) {
                ATUReports.add(time + " Login Timeout (Screenshot)", user_name, "Login Success", "Login Success", LogAs.FAILED,
                        new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
                Assert.assertTrue(false);

            }
            if (driver.getTitle().contains("Tegrity EULA")) {
                try {
                    eula_accept_button.click();
                    System.out.println("Clicked on accept Eula button");
                    ATUReports.add(time + " Click on EULA accept", user_name, "Accept clicked", "Accept clicked", LogAs.PASSED, null);
                    new WebDriverWait(driver, 30)
                            .until(ExpectedConditions.not(ExpectedConditions.titleContains("Tegrity - Courses")));
                    ;
                } catch (Exception msg) {
                    System.out.println("No EULA button.");
                    ATUReports.add(time + " Click on EULA accept", user_name, "Accept clicked", "Acceot wasn't clicked", LogAs.FAILED,
                            new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
                    Assert.assertTrue(false);
                }
            } else if (driver.getTitle().contains("Tegrity - Courses")) {
                ATUReports.add(time + " Tegrity courses home page is visible", user_name, "Course List page is displayed",
                        "Course List page is displayed", LogAs.PASSED, null);
            }
        } catch (Exception e) {
            ATUReports.add(time + " Login Failed (Screenshot)", user_name, "Login Success", "Login Failed", LogAs.FAILED,
                    new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
            Assert.assertTrue(false);
        }
        try {
            new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfAllElements(course.course_list));
        } catch (Exception e) {
            System.out.println("No Courses for the user");
        }
    }

    /// login as guest
    public void loginAsguest() throws InterruptedException {
        try {
            isMHCampuseUp();
            initializeCourse();
            waitForVisibility(usernamefield);
            waitForVisibility(button_login);
            waitForVisibility(passfield);
            try {
                new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(Login_as_guest_button));
                clickElementWithOutIdJS(Login_as_guest_button);
            } catch (TimeoutException e) {
                try {
                    driver.navigate().refresh();
                    new WebDriverWait(driver, 15).until(ExpectedConditions.visibilityOf(Login_as_guest_button));
                    clickElement(Login_as_guest_button);
                    String[] urlArray = driver.getCurrentUrl().split("/");
                    urlArray[4] = "courses";
                    String url = urlArray[0] + urlArray[1] + urlArray[2] + urlArray[3] + urlArray[4];
                    driver.get(url);
                    new WebDriverWait(driver, 15).until(ExpectedConditions.not(ExpectedConditions.titleContains("Tegrity - Courses")));
                } catch (Exception ex) {
                    ATUReports.add(time + " Login as guest button isn't visible", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
                }
            }
            try {
                new WebDriverWait(driver, 30)
                        .until(ExpectedConditions.not(ExpectedConditions.titleContains("Tegrity Lecture Capture")));
            } catch (TimeoutException e) {
                ATUReports.add(time + " Login Timeout (Screenshot)", "Login Success", "Login Failed", LogAs.FAILED,
                        new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
                Assert.assertTrue(false);
            }

            if (driver.getTitle().contains("Tegrity - Courses")) {
                ATUReports.add(time + " Tegrity courses home page is visible", "Course List page is displayed",
                        "Course List page is displayed", LogAs.PASSED, null);
            } else {
                ATUReports.add(time + " Login Failed (Screenshot)", "Login Success", "Login Failed", LogAs.FAILED,
                        new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
                Assert.assertTrue(false);
            }
        } catch (Exception e) {
            ATUReports.add(time + " Login Failed (Screenshot)", "Login Success", "Login Failed", LogAs.FAILED,
                    new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
            Assert.assertTrue(false);
        }
        try {
            new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfAllElements(course.course_list));
        } catch (Exception e) {
            System.out.println("No Courses for the user");
        }
    }

    /// verify visibilty of login as guest button
    public void verifyGuestButton() {
        // TODO Auto-generated method stub
        if (Login_as_guest_button.isDisplayed()) {
            System.out.println("login as guest button is verified");
            ATUReports.add(time + " verify guest button", "login button as guest", "visible", "visible", LogAs.PASSED, null);
            Assert.assertTrue(true);
        } else {
            System.out.println("login as guest button is not verified");
            ATUReports.add(time + " verify guest button", "login button as guest", "visible", "not visible", LogAs.FAILED,
                    null);
            Assert.assertTrue(false);
        }
    }

    /// verify visibilty of :Some courses may allow guest access+location
    public void verifyGuestInfo() {
        // TODO Auto-generated method stub
        Point login = button_login.getLocation();
        Point info = Login_as_guest_info.getLocation();

        if ((Login_as_guest_info.getText().equals("Some courses may allow guest access")
                && (info.getY() > login.getY()))) {
            System.out.println("login as guest info is verified+location");
            ATUReports.add(time + " verify guest info line+location", "login guest info line", "visible", "visible",
                    LogAs.PASSED, null);
            Assert.assertTrue(true);
        } else {
            System.out.println("login as guest info,location is not verified");
            ATUReports.add(time + " verify guest info line location", "login guest info line", "visible", "not visible",
                    LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
            Assert.assertTrue(false);
        }
    }

    // This function verify that login as guest button is not displayed
    public void verifyThatLoginAsGuestButtonNotDisplayed() {
        verifyWebElementNotDisplayed(Login_as_guest_button, "Login as guest button");
    }

    // This function verify that the text: "Some courses may allow guest access"
    // is not displayed
    public void verifyThatTheTextSomeCoursesMyAllowGuestAccessNotDisplayed() {
        verifyWebElementNotDisplayed(Login_as_guest_info, "The text: Some courses may allow guest access");
    }

    // Check if MHCampus is up
    private void isMHCampuseUp() {
        try {
            URL obj;

            HttpURLConnection con;

            String env = "";

            if (getEnvironment().equals("QA"))
                env = "https://login-aws-qa.mhcampus.com/Nagios/RestApiHealthCheck.ashx";
            else
                return;

            obj = new URL(env);

            con = (HttpURLConnection) obj.openConnection();

            con.setRequestMethod("GET");

            if (con.getResponseCode() != 200) {

                System.out.println("MHCampus are updating, wait 20 minutes");

                Thread.sleep(1200000);

                isMHCampuseUp();

            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());

        }
    }


    // return MHCampuse environment by parsing the course1(local properties) string
    public String getEnvironment() {
        String course1 = PropertyManager.getProperty("course1");

        if (course1.contains("-qa-"))
            return "QA";

        else if (course1.contains("-qabr"))
            return "QALV";

        else if (course1.contains("-perf"))
            return "PERF";

        else
            return "PRODUCTION";
    }
}


