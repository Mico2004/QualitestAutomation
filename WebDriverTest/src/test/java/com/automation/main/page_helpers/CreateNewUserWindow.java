package com.automation.main.page_helpers;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;

@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class CreateNewUserWindow extends Page {
	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}

	public CreateNewUserWindow(WebDriver browser) {
		super(browser);
		// TODO Auto-generated constructor stub
	}

	@FindBy(id="ctl00_ContentPlaceHolder1_ucNewUserControl_ucDialog_UserIDTextBox") WebElement user_id_input;
	@FindBy(id="ctl00_ContentPlaceHolder1_ucNewUserControl_ucDialog_UserNameTextBox") WebElement user_name_input;
	@FindBy(id="ctl00_ContentPlaceHolder1_ucNewUserControl_ucDialog_EmailTextBox") WebElement email_input;
	@FindBy(id="ctl00_ContentPlaceHolder1_ucNewUserControl_ucDialog_txtPassword") WebElement password_input;
	@FindBy(id="ctl00_ContentPlaceHolder1_ucNewUserControl_ucDialog_txtConfPassword") WebElement confirm_password_input;
	@FindBy(id="ctl00_ContentPlaceHolder1_ucNewUserControl_ucDialog_btnOK") WebElement ok_button;
	@FindBy(id="ctl00_ContentPlaceHolder1_ucNewUserControl_ucDialog_btnCancel") WebElement cancel_button;
	@FindBy(id="ctl00_ContentPlaceHolder1_ucNewUserControl_ucDialog_pDialog") WebElement create_new_user_modal_window;
	
	public boolean setEmail(String email) {
		try {
			wait.until(ExpectedConditions.visibilityOf(email_input));
			email_input.sendKeys(email);
			return true;
		} catch (Exception msg) {
			return false;
		}
	}
	
	public boolean setPassword(String password) {
		try {
			wait.until(ExpectedConditions.visibilityOf(password_input));
			password_input.sendKeys(password);
			return true;
		} catch (Exception msg) {
			return false;
		}
	}

	public boolean setConfirmPassword(String confirm_password) {
		try {
			wait.until(ExpectedConditions.visibilityOf(confirm_password_input));
			confirm_password_input.sendKeys(confirm_password);
			return true;
		} catch (Exception msg) {
			return false;
		}
	}
	
	public boolean setUserId(String user_id) {
		try {
			wait.until(ExpectedConditions.visibilityOf(user_id_input));
			user_id_input.sendKeys(user_id);
			return true;
		} catch (Exception msg) {
			return false;
		}
	}
	
	
	public boolean setUserName(String user_name) {
		try {
			wait.until(ExpectedConditions.visibilityOf(user_name_input));
			user_name_input.sendKeys(user_name);
			return true;
		} catch (Exception msg) {
			return false;
		}
		
	}
	
	public boolean clickOnOkButton() {
		try {
			wait.until(ExpectedConditions.visibilityOf(ok_button));
			ok_button.click();
			return true;
		} catch (Exception msg) {
			return false;
		}
	}
	
	public void createNewUser(String user_name, String user_id, String email, String password, String confirm_password) throws InterruptedException  {
		try {
			setUserName(user_name);
			setUserId(user_id);
			setEmail(email);
			setPassword(password);
			setConfirmPassword(confirm_password);
			clickOnOkButton();		
			waitForAlert(60);
			driver.switchTo().alert().accept();					
			System.out.println("New user created. Username: " + user_name + ". User id: " + user_id + ". Password: " + password);
			ATUReports.add(time +" User Creation",user_name,"New User was created","New User was created",LogAs.PASSED,null);
		} catch (Exception e) {	
			System.out.println("NoAlertPresentException"+e.getMessage());
			ATUReports.add(time +" No Alert Present Exception: "+e.getMessage(),LogAs.WARNING, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				
			}
	}
		
	
	

}
