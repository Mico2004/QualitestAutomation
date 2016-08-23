package com.automation.main;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import junit.framework.Assert;

public class EmailLoginPage extends Page {
	public EmailLoginPage(WebDriver browser) {
		super(browser);
		// TODO Auto-generated constructor stub
	}

	@FindBy(id = "home-email") WebElement email_field;
	@FindBy(id = "copy-button") WebElement copy;
	
	@FindBy(id = "signIn") WebElement sign_in;
	
	///login email
	public void LoginEmailPage(String email_input_field) throws InterruptedException
	{try {
		  
		  waitForVisibility(email_field);
		  email_field.clear();
		  email_field.sendKeys(email_input_field);
		 copy.click();
			System.out.println("login successfully");
			ATUReports.add("Login as", email_input_field, "Success login", "Success login", LogAs.PASSED, null);
			Assert.assertTrue(true);
	}
	catch(Exception e)
	{
		System.out.println("failed sign in  to gmail inbox");
		ATUReports.add("Login as", "qualitestmcgrawhill", "Success login", "Success fail", LogAs.FAILED, null);
		Assert.assertTrue(false);
	}
	}
	
}
