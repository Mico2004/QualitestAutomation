package com.automation.main;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
public class ManageAdhocUsersPage extends Page {
	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}

	public ManageAdhocUsersPage(WebDriver browser) {
		super(browser);
		// TODO Auto-generated constructor stub
	}

	@FindBy(id="ctl00_ContentPlaceHolder1_lbNewUser")
	public WebElement new_user_button;
	@FindBy(linkText="Admin Dashboard") WebElement to_admin_dashboard;
	
	public void clickOnNewUser() throws InterruptedException {
		for(int i=0; i<10; i++) {
			try {
				new_user_button.click();
				System.out.println("Clicked on new user button.");
				break;
			} catch (Exception msg) {
				System.out.println("Not clicked on new user button.");
				Thread.sleep(1000);
			}
		}
		
	}
//back to admin dash board
	public void toAdminDashBoard() throws InterruptedException
{
	to_admin_dashboard.click();
	Thread.sleep(2000);
}
	///create new user
	public void createNewUser(String name,CreateNewUserWindow create_new_user_window) throws InterruptedException
	{
	
		// 3. Click on create course href link
		driver.switchTo().frame(0);
		Thread.sleep(4000);
		clickOnNewUser();
		Thread.sleep(2000);

		create_new_user_window.createNewUser(name, name, "abc@com.com", "111", "111");
		Thread.sleep(1000);

		try {

			driver.switchTo().alert().accept();
		} catch (Exception msg) {

		}
	}

}