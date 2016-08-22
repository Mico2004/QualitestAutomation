package com.automation.main;

import org.apache.http.util.Asserts;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import junit.framework.Assert;

public class EulaPage extends Page {
	public EulaPage(WebDriver browser) {
		super(browser);
		// TODO Auto-generated constructor stub
	}
	@FindBy(xpath="//*[@id=\"main\"]/div/div[1]/p") WebElement eula_message;
	@FindBy(xpath="//*[@id=\"main\"]/div/div[2]/form/input[1]") WebElement accept_button;
	@FindBy(xpath="//*[@id=\"main\"]/div/div[2]/form/input[2]") WebElement decline_button;
	
	
	//this function verifies visibility of accept decline and eula message later compares eula message equals message was configured
	public void verifyElementsOfEula(String message)
	{
		if((accept_button.isDisplayed())&&(decline_button.isDisplayed())&&(eula_message.isDisplayed()))
		{
			System.out.println("accept and decline buttons and eula message are visible");
			ATUReports.add("accept and decline buttons and eula message are not visible","3 elements","visible"," not visible",LogAs.PASSED, null);

			if(message.equals(eula_message.getText()))
		{
			System.out.println(" eula message matches the one written in  admin advanced services");
			ATUReports.add(" eula message matches the one written in  admin advanced services","eula message and the one in admin advanced settings","equal","equal",LogAs.PASSED, null);
			Assert.assertTrue(true);
		}
		else {
			System.out.println(" eula message dont  matches the one written in  admin advanced services");
			ATUReports.add(" eula message matches the one written in  admin advanced services","eula message and the one in admin advanced settings","equal"," not equal",LogAs.FAILED, null);

			Assert.assertTrue(false);
		}
		}
		else 
		{
			System.out.println("accept and decline buttons and eula message are not visible");
			ATUReports.add("accept and decline buttons and eula message are not visible","3 elements","visible"," not visible",LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
	
	}
	
	//click on decline
	public void clickOnDecline()
	{try {
		
	
		if (decline_button.isDisplayed()) {
			decline_button.click();
			System.out.println("click on decline");
			ATUReports.add("click on decline", LogAs.PASSED, null);
			Assert.assertTrue(true);
		}
		else {
			System.out.println("click on decline failed");
			ATUReports.add("click on decline failed", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
	}
	catch(Exception e)
	{
		System.out.println("click on decline exception");
		ATUReports.add("click on decline exception", LogAs.FAILED, null);
		Assert.assertTrue(false);
	}
	}
	//click on accept
		public void clickOnAccept()
		{try {
			
		
			if (accept_button.isDisplayed()) {
				accept_button.click();
				System.out.println("click on accept");
				ATUReports.add("click on accept", LogAs.PASSED, null);
				Assert.assertTrue(true);
			}
			else {
				System.out.println("click on accept failed");
				ATUReports.add("click on accept failed", LogAs.FAILED, null);
				Assert.assertTrue(false);
			}
		}
		catch(Exception e)
		{
			System.out.println("click on accept exception");
			ATUReports.add("click on accept exception", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		}
}
