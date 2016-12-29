package com.automation.main.page_helpers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;
import junit.framework.Assert;

public class ImpersonateUser extends Page{

		public ImpersonateUser(WebDriver browser) {
			super(browser);
			// TODO Auto-generated constructor stub
		}
		
		@FindBy(xpath=".//*[@id='tegrityBreadcrumbsBox']/li/a")
		public WebElement  AdminDashboard;
		@FindBy(id="CustomizeHeading")
		public WebElement ImpersonateUserTitle;
		@FindBy(css=".btn.btn-default.ng-scope")
		public WebElement impersonateButton;
		@FindBy(css="#user-field")
		public WebElement userField;
		
		
		
		// the function enter the user id and press on impersonate 
		public void EnterTheUserIdAndPressOnImpersonate(String user)
		{		
			sendKeysToWebElementInput(userField, user);
			clickElementWithOutIdJS(impersonateButton);		
		}
	
	
}
