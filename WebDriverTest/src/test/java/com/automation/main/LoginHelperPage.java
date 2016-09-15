package com.automation.main;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import org.openqa.selenium.support.FindBy;

import org.openqa.selenium.support.ui.ExpectedConditions;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import com.automation.main.Page;
import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;
import junitx.util.PropertyManager;

@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class LoginHelperPage extends Page {
	// Set Property for ATU Reporter Configuration
		{
			System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");		
		}
		
	@FindBy(name = "UserName")
	WebElement usernamefield;
	@FindBy(name = "Password")
	public WebElement passfield;
	@FindBy(id = "ButtonLogin")
	WebElement button_login;
	@FindBy(css = "[class=\"form-row\"]>.error-text")
	WebElement errorHeader;
	@FindBy(css = ".btn.btn-primary")
	WebElement eula_accept_button;
	@FindBy(xpath = "//*[@id=\"main\"]/form/div[2]/div[6]/button")
	WebElement Login_as_guest_button;
	@FindBy(xpath = "//*[@id=\"main\"]/form/div[2]/div[6]/p")
	WebElement Login_as_guest_info;
	private CoursesHelperPage course;

	

	public LoginHelperPage(WebDriver driver) throws Exception {
		super(driver);
		setPageTitle("Tegrity Lecture Capture");
		/// setPageUrl("https://qualitest4-prod.tegrity.com/#/login");
		// setPageUrl("https://reg-qabr.tegrity.com/#/login");
		setPageUrl("https://awsserverautomation-qa-1.tegrity.com/#/login");
		
		// setPageUrl("https://awsserverautomation1.tegrity.com/#/login");
		/// setPageUrl(DriverSelector.setDriverUniversity(System.getProperty("University"))););////"https://reg-qabr.tegrity.com/#/login"
		// setPageUrl(DriverSelector.setDriverUniversity(System.getProperty("University")));
		
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
		setUserText(user);

	}

	public void fillPass()// fill password
	{
		String pass = PropertyManager.getProperty("Password");
		setPassText(pass);

	}
	public void initializeCourse(){
		course = PageFactory.initElements(driver, CoursesHelperPage.class);
	}

	public void loginCourses(String user_name) throws InterruptedException// login
																			// courses
	{
		
		initializeCourse();
		try {
			waitForVisibility(usernamefield);
			waitForVisibility(button_login);
			waitForVisibility(passfield);
			fillUserFromProperyFile(user_name);
			fillPass();
			clickElement(button_login);
			try {
				new WebDriverWait(driver, 30)
						.until(ExpectedConditions.not(ExpectedConditions.titleContains("Tegrity Lecture Capture")));
			} catch (TimeoutException e) {
				ATUReports.add("Login Timeout (Screenshot)", user_name, "Login Success", LogAs.FAILED,
						new CaptureScreen(ScreenshotOf.BROWSER_PAGE));

			}
			if (driver.getTitle().contains("Tegrity EULA")) {
				try {
					eula_accept_button.click();
					System.out.println("Clicked on accept Eula button");
					ATUReports.add("Click on EULA accept", "Accept clicked", "Accept clicked", LogAs.PASSED, null);
					new WebDriverWait(driver, 30)
							.until(ExpectedConditions.not(ExpectedConditions.titleContains("Tegrity - Courses")));
					;
				} catch (Exception msg) {
					System.out.println("No EULA button.");
					ATUReports.add("Click on EULA accept", "Accept clicked", "Acceot wasn't clicked", LogAs.FAILED,
							new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				}
			} else if (driver.getTitle().contains("Tegrity - Courses")) {
				ATUReports.add("Tegrity courses home page is visible", "Course List page is displayed",
						"Course List page is displayed", LogAs.PASSED, null);
			}
		} catch (Exception e) {
			ATUReports.add("Login Failed (Screenshot)", user_name, "Login Success", LogAs.FAILED,
					new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
		Thread.sleep(2000);
		try{
			new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfAllElements(course.course_list));
		}catch(Exception e){
			System.out.println("No Courses for the user");
		}
	
	}

	public void loginAdmin(String user_name) throws InterruptedException// login
																		// courses
	{
		try {
			Thread.sleep(2000);
			waitForVisibility(usernamefield);
			waitForVisibility(button_login);
			waitForVisibility(passfield);
			fillUserFromProperyFile(user_name);
			fillPass();
			clickElement(button_login);
			new WebDriverWait(driver, 30).until(ExpectedConditions.titleContains("Tegrity"));
			ATUReports.add("Login as", user_name, "Success login", "Success login", LogAs.PASSED, null);
		} catch (Exception e) {
			ATUReports.add("Login as", user_name, "Success login", "Success fail (Screenshot)", LogAs.FAILED,
					new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}

	}

	/// login using parameter not with local property
	public void loginCoursesByParameter(String user_name) throws InterruptedException// login
	// courses
	{
		initializeCourse();
		try {
			waitForVisibility(usernamefield);
			waitForVisibility(button_login);
			waitForVisibility(passfield);
			setUserText(user_name);
			fillPass();
			clickElement(button_login);
			try {
				new WebDriverWait(driver, 30)
						.until(ExpectedConditions.not(ExpectedConditions.titleContains("Tegrity Lecture Capture")));
			} catch (TimeoutException e) {
				ATUReports.add("Login Timeout (Screenshot)", user_name, "Login Success", LogAs.FAILED,
						new CaptureScreen(ScreenshotOf.BROWSER_PAGE));

			}
			if (driver.getTitle().contains("Tegrity EULA")) {
				try {
					eula_accept_button.click();
					System.out.println("Clicked on accept Eula button");
					ATUReports.add("Click on EULA accept", "Accept clicked", "Accept clicked", LogAs.PASSED, null);
					new WebDriverWait(driver, 30)
							.until(ExpectedConditions.not(ExpectedConditions.titleContains("Tegrity - Courses")));
					;
				} catch (Exception msg) {
					System.out.println("No EULA button.");
					ATUReports.add("Click on EULA accept", "Accept clicked", "Acceot wasn't clicked", LogAs.FAILED,
							new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				}
			} else if (driver.getTitle().contains("Tegrity - Courses")) {
				ATUReports.add("Tegrity courses home page is visible", "Course List page is displayed",
						"Course List page is displayed", LogAs.PASSED, null);
			}
		} catch (Exception e) {
			ATUReports.add("Login Failed (Screenshot)", user_name, "Login Success", LogAs.FAILED,
					new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
		try{
			new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfAllElements(course.course_list));
		}catch(Exception e){
			System.out.println("No Courses for the user");
		}
	}

	/// login as guest
	public void loginAsguest() throws InterruptedException {
		try {
			initializeCourse();
			waitForVisibility(usernamefield);
			waitForVisibility(button_login);
			waitForVisibility(passfield);
			waitForVisibility(Login_as_guest_button);
			clickElement(Login_as_guest_button);
			try {
				new WebDriverWait(driver, 30)
						.until(ExpectedConditions.not(ExpectedConditions.titleContains("Tegrity Lecture Capture")));
			} catch (TimeoutException e) {
				ATUReports.add("Login Timeout (Screenshot)", "Login Success", LogAs.FAILED,
						new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			}
			if (driver.getTitle().contains("Tegrity - Courses")) {
				ATUReports.add("Tegrity courses home page is visible", "Course List page is displayed",
						"Course List page is displayed", LogAs.PASSED, null);
			} else {
				ATUReports.add("Login Failed (Screenshot)",  "Login Success", LogAs.FAILED,
						new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			}
		} catch (Exception e) {
			ATUReports.add("Login Failed (Screenshot)",  "Login Success", LogAs.FAILED,
					new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
		try{
			new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfAllElements(course.course_list));
		}catch(Exception e){
			System.out.println("No Courses for the user");
		}
	}

	/// verify visibilty of login as guest button
	public void verifyGuestButton() {
		// TODO Auto-generated method stub
		if (Login_as_guest_button.isDisplayed()) {
			System.out.println("login as guest button is verified");
			ATUReports.add("verify guest button", "login button as guest", "visible", "visible", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("login as guest button is not verified");
			ATUReports.add("verify guest button", "login button as guest", "visible", "not visible", LogAs.FAILED,
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
			ATUReports.add("verify guest info line+location", "login guest info line", "visible", "visible",
					LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("login as guest info+location is not verified");
			ATUReports.add("verify guest info line+location", "login guest info line", "visible", "not visible",
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

}
