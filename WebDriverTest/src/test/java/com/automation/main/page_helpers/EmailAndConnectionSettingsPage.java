package com.automation.main.page_helpers;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;

public class EmailAndConnectionSettingsPage extends Page {

	public EmailAndConnectionSettingsPage(WebDriver browser) {
		super(browser);
		// TODO Auto-generated constructor stub
	}


	@FindBy(id="sending-field") public WebElement sending_user;
	@FindBy(id="administrator-field")public  WebElement admin_email;
	@FindBy(id="helpdesk-field") public WebElement helpdesk_email;
	@FindBy(id="connect-field") public WebElement connect_checkbox;
	@FindBy(id="ApplyChangesButton")public  WebElement ok_button;
	@FindBy(id="CancelButton") public WebElement cancel_button;
	@FindBy(id="smtp-field") public WebElement smtp_server;
	@FindBy(id="port-field") public WebElement smtp_port;
	@FindBy(id="user-field") public WebElement smtp_user_id;
	@FindBy(id="password-field") public WebElement smtp_user_password;
	
	
	
	
	///fill email setting page using sending user,admin email,helpdesk email
	public void fillNewEmailSetting(String user,String adm_email,String help_email,ConfirmationMenu confirm)
	{   
		sending_user.clear();
		sending_user.sendKeys(user);
		System.out.println("clicked user name");
		admin_email.clear();
		admin_email.sendKeys(adm_email);
		System.out.println("clicked admin email");
		helpdesk_email.clear();
		helpdesk_email.sendKeys(help_email);
		System.out.println("clicked helpdesk email");
		if(!connect_checkbox.isSelected())
		{ try {
			
		
			connect_checkbox.click();
		System.out.println("clicked checkbox");
		}
		catch(Exception e)
		{
			System.out.println("error clicking checkbox");
		}
		}
		ok_button.click();
		System.out.println("clicked on ok");
		confirm.waitForVisibility(confirm.ok_button);
		try {
			confirm.clickOnOkButtonAfterConfirmEmailSetting();
			System.out.println("confirmation ok");

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("confirmation failed");

			e.printStackTrace();
		}
	}

	public void waitForThePageToLoad(){
		try {
		
			wait.until(ExpectedConditions.visibilityOf(sending_user));
			wait.until(ExpectedConditions.visibilityOf(admin_email));
			wait.until(ExpectedConditions.visibilityOf(helpdesk_email));
			wait.until(ExpectedConditions.visibilityOf(smtp_server));
			
		} catch(Exception e){
			e.printStackTrace();
			ATUReports.add(time +e.getMessage(), "Success.", "Failed.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
	}
	
	
	
	public void cleanAllOptionsAndPutMail() {

		try { 
		sending_user.clear();
		admin_email.clear();
		smtp_port.clear();
		smtp_server.clear();
		smtp_user_id.clear();
		smtp_user_password.clear();
		helpdesk_email.clear();
		
		System.out.println("Clear all the fields in the page.");
		ATUReports.add(time +" Clear all the fields in the page.", "Success.", "Success.", LogAs.PASSED, null);
		
		if(!connect_checkbox.isSelected()) {
			System.out.println("The checkbox: " + connect_checkbox.getText() + " is not selected");
			ATUReports.add("The checkbox: " + connect_checkbox.getText() + " is not selected", LogAs.PASSED, null);
			Assert.assertTrue(true);
			
		} else {
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", connect_checkbox);			
			System.out.println("The checkbox: " + connect_checkbox.getText() + " is not selected");
			ATUReports.add("The checkbox: " + connect_checkbox.getText() + " is not selected", LogAs.PASSED, null);
		}
			
		helpdesk_email.sendKeys("qtautomationtest@mailinator.com");
		admin_email.sendKeys("qtautomationtest@mailinator.com");
		System.out.println("The email of helpdesk and adminstator was change.");
		ATUReports.add("The email of helpdesk and adminstator was change.", LogAs.PASSED, null);
		
		clickElement(ok_button);
		
		} catch (Exception e) {
		
			e.printStackTrace();
			ATUReports.add(time +" Not clear all the fields in the page.", "Success.", "Failed.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}

	
	
	
}
