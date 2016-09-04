package com.automation.main;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.http.util.Asserts;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
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
import junit.framework.Assert;
import junitx.util.PropertyManager;

@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class LoginHelperPage extends Page {

	@FindBy(name = "UserName")
	WebElement usernamefield;
	@FindBy(name = "Password")
	public WebElement passfield;
	@FindBy(id = "ButtonLogin")
	WebElement button_login;
	@FindBy(css = "#main > form > div.form-container > div:nth-child(2) > span")
	WebElement errorHeader;
	@FindBy(css = ".btn.btn-primary")
	WebElement eula_accept_button;
	@FindBy(xpath = "//*[@id=\"main\"]/form/div[2]/div[6]/button")
	WebElement Login_as_guest_button;
	@FindBy(xpath = "//*[@id=\"main\"]/form/div[2]/div[6]/p")
	WebElement Login_as_guest_info;

	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}

	public LoginHelperPage(WebDriver driver) throws Exception {
		super(driver);
		setPageTitle("Tegrity Lecture Capture");
		/// setPageUrl("https://qualitest4-prod.tegrity.com/#/login");
		// setPageUrl("https://reg-qabr.tegrity.com/#/login");
		setPageUrl("https://awsserverautomation-qa-5.tegrity.com/#/login");
		
//		setPageUrl("https://awsserverautomation1.tegrity.com/#/login");
		/// setPageUrl(DriverSelector.setDriverUniversity(System.getProperty("University"))););////"https://reg-qabr.tegrity.com/#/login"
		  //  setPageUrl(DriverSelector.setDriverUniversity(System.getProperty("University")));
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

	public void fillUser(String user_name)// fill user name
	{
		String user = PropertyManager.getProperty(user_name);
		setUserText(user);

	}

	public void fillPass()// fill password
	{
		String pass = PropertyManager.getProperty("Password");
		setPassText(pass);

	}

	public void loginCourses(String user_name) throws InterruptedException// login
																			// courses
	{
		Thread.sleep(2000);
		System.out.println("loginCourses1"); 
		wait.until(ExpectedConditions.visibilityOf(usernamefield));
		System.out.println("loginCourses2");
		wait.until(ExpectedConditions.visibilityOf(passfield));
		System.out.println("loginCourses3");
		wait.until(ExpectedConditions.visibilityOf(button_login));
		System.out.println("loginCourses4");
		wait.until(ExpectedConditions.titleContains("Tegrity Lecture Capture"));
		System.out.println("loginCourses5");
		Thread.sleep(1000);
		fillUser(user_name);
		System.out.println("loginCourses6");
		fillPass();		
		try {
			clickElement(button_login);		
			ATUReports.add("Login as", user_name, "Success login", "Success login", LogAs.PASSED, null);
		} catch (Exception e) {
			ATUReports.add("Login as", user_name, "Success login", "Success fail", LogAs.FAILED, null);
		}

		Thread.sleep(1000);
		if (driver.getCurrentUrl().contains("eula")) {
			try {
				eula_accept_button.click();
				System.out.println("Clicked on accept EULA button");
			} catch (Exception msg) {
				System.out.println("No EULA button.");
			}
		}

		for (int second = 0;; second++) {
			if (second >= 60)
				Assert.fail("timeout");
			try {
				if (driver.getTitle().equals("Tegrity - Courses"))//check if tegrity courses home page is visible
				{ 
					ATUReports.add("Tegrity courses home page is visible", "Course List page is displayed",
							"Course List page is displayed", LogAs.PASSED, null);

					break;
				} else {
					button_login.click();
				}
			} catch (Exception e) {
				System.out.println("Tegrity courses home page didn't load");
			}

			Thread.sleep(1000);
		}
		Thread.sleep(3000);
		Assert.assertEquals(driver.getTitle(), "Tegrity - Courses");
	}

	public void loginAdmin(String user_name) throws InterruptedException// login
																		// courses
	{
		Thread.sleep(2000);
		waitForVisibility(button_login);
		fillUser(user_name);

		fillPass();

		waitForVisibility(button_login);

		try {		
			clickElement(button_login);
			ATUReports.add("Login as", user_name, "Success login", "Success login", LogAs.PASSED, null);
		} catch (Exception e) {
			ATUReports.add("Login as", user_name, "Success login", "Success fail", LogAs.FAILED, null);
		}

		// for (int second = 0;; second++) {
		// if (second >= 60)
		// Assert.fail("timeout");
		// try {
		// if (driver.getTitle().equals("Tegrity - Courses"))// check if tegrity
		// courses home page is visible
		// { //
		// ATUReports.add("Tegrity courses home page is visible", "Course List
		// page is displayed", "Course List page is displayed", LogAs.PASSED,
		// null);
		//
		// break;
		// }
		// } catch (Exception e) {
		// ATUReports.add("Tegrity courses home page didn't load", "Course List
		// page is displayed", "Course List page is not displayed",
		// LogAs.FAILED, null);
		// }
		//
		// Thread.sleep(1000);
		// }
		// Thread.sleep(1000);
		//
		// Assert.assertEquals(driver.getTitle(),"Tegrity - Courses");
	}

	/// login using parameter not with local property
	public void loginCoursesByParameter(String user_name) throws InterruptedException// login
	// courses
	{
		Thread.sleep(2000);
		waitForVisibility(usernamefield);
		setUserText(user_name);
		fillPass();

		waitForVisibility(button_login);

		try {
			clickElement(button_login);
			ATUReports.add("Login as", user_name, "Success login", "Success login", LogAs.PASSED, null);
		} catch (Exception e) {
			ATUReports.add("Login as", user_name, "Success login", "Success fail", LogAs.FAILED, null);
		}

		Thread.sleep(1000);
		
		if(!(driver instanceof ChromeDriver)) {
			if(!isElementPresent(By.id("CoursesHeading"))) {		
				try {
				eula_accept_button.click();
				System.out.println("Clicked on accept EULT button");
			} catch (Exception msg) {
				System.out.println("No EULA button.");
			}
		  }
		} else if (driver.getCurrentUrl().contains("eula")) {
			
			try {
				eula_accept_button.click();
				System.out.println("Clicked on accept EULT button");
			} catch (Exception msg) {
				System.out.println("No EULA button.");
			}	  
		}
		
		

		for (int second = 0;; second++) {
			if (second >= 60)
				Assert.fail("timeout");
			try {
				if (driver.getTitle().equals("Tegrity - Courses"))// check if
				// tegrity
				// courses
				// home page
				// is
				// visible
				{ //
					ATUReports.add("Tegrity courses home page is visible", "Course List page is displayed",
							"Course List page is displayed", LogAs.PASSED, null);

					break;
				}
			} catch (Exception e) {
				ATUReports.add("Tegrity courses home page didn't load", "Course List page is displayed",
						"Course List page is not displayed", LogAs.FAILED, null);
			}

			Thread.sleep(1000);
		}
		Thread.sleep(1000);

		Assert.assertEquals(driver.getTitle(), "Tegrity - Courses");
	}

	/// login as guest
	public void loginAsguest() throws InterruptedException {
		
		waitForVisibility(Login_as_guest_button);
		
		try {
			clickElement(Login_as_guest_button);
			ATUReports.add("Login as guest", "click on login as guest", "Success login", "Success login", LogAs.PASSED,
					null);
		} catch (Exception e) {
			ATUReports.add("Login as guest", "click on login as guest", "Success login", "Success fail", LogAs.FAILED,
					null);
		}
		
		for (int second = 0;; second++) {
			if (second >= 60)
				Assert.fail("timeout");
			try {
				if (driver.getTitle().equals("Tegrity - Courses"))//check if tegrity courses home page is visible
				{ 
					ATUReports.add("Tegrity courses home page is visible", "Course List page is displayed",
							"Course List page is displayed", LogAs.PASSED, null);

					break;
				} else {
					Login_as_guest_button.click();
				}
			} catch (Exception e) {
				System.out.println("Tegrity courses home page didn't load");
			}

			Thread.sleep(1000);
		}
		Thread.sleep(3000);
		Assert.assertEquals(driver.getTitle(), "Tegrity - Courses");
	
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
					LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
	}
	
	// This function verify that login as guest button is not displayed
	public void verifyThatLoginAsGuestButtonNotDisplayed() {
		verifyWebElementNotDisplayed(Login_as_guest_button, "Login as guest button");
	}
	
	// This function verify that the text: "Some courses may allow guest access" is not displayed
	public void verifyThatTheTextSomeCoursesMyAllowGuestAccessNotDisplayed() {
		verifyWebElementNotDisplayed(Login_as_guest_info, "The text: Some courses may allow guest access");
	}

 }
	
	

