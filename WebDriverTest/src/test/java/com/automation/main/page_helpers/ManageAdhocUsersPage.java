package com.automation.main.page_helpers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
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

	@FindBy(id = "ctl00_ContentPlaceHolder1_lbNewUser")
	public WebElement new_user_button;
	@FindBy(xpath = ".//*[@id='tegrityBreadcrumbsBox']/li/a") WebElement to_admin_dashboard;
	@FindBy(xpath="//div[@id='contentDIV']/table/tbody") WebElement contentTable;
	@FindBy(id = "ctl00_ContentPlaceHolder1_txtSearch") WebElement filter_search_user_input;
	@FindBy(id = "ctl00_ContentPlaceHolder1_btnSearch") WebElement filter_search_button;

	public void clickOnNewUser() throws InterruptedException {
		       try{
					driver.switchTo().frame(0);

					new WebDriverWait(driver, 40).until(ExpectedConditions.visibilityOf(new_user_button));

					new_user_button.click();

					System.out.println("Clicked on new user button.");
		    	   
			} catch (NoSuchFrameException msg) {
				   try {
					   new WebDriverWait(driver, 40).until(ExpectedConditions.visibilityOf(new_user_button));

					   new_user_button.click();

					    System.out.println("Clicked on new user button.");

				   }catch(Exception e){

					     System.out.println(msg.getMessage());

					     ATUReports.add(time +" Not clicked on new user link", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));

				   }




					
			}
		}

	

	// back to admin dash board
	public void toAdminDashBoard() throws InterruptedException {
		to_admin_dashboard.click();
		Thread.sleep(2000);
	}
	
	/// create new user
	public void createNewUser(String name, CreateNewUserWindow create_new_user_window) throws InterruptedException {

		// 3. Click on create course href link
		driver.switchTo().frame(0);
		Thread.sleep(4000);
		clickOnNewUser();
		Thread.sleep(3000);

		create_new_user_window.createNewUser(name, name, "abc@com.com", "111", "111");
		Thread.sleep(1000);

		try {

			driver.switchTo().alert().accept();
		} catch (Exception msg) {

		}
	}
	
	// This function get username and search for it
	public void searchForTargetUser(String username_to_search) throws InterruptedException {
		getIntoFrame(0);
		waitForVisibility(filter_search_user_input);
		sendKeysToWebElementInput(filter_search_user_input, username_to_search);
		
		try{
			filter_search_button.click();
			System.out.println("Searched for target username: " + username_to_search);
			ATUReports.add(time +" Searched for target username: " + username_to_search, "True.", "True.", LogAs.PASSED, null);
			Assert.assertTrue(true);
			exitInnerFrame();
		} catch(Exception msg) {
			System.out.println("Fail to search for target username: " + username_to_search);
			ATUReports.add(time +" Searched for target username: " + username_to_search, "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
		
	}
	

	public void waitForPageToLoad(){
		try{
			wait.until(ExpectedConditions.visibilityOf(to_admin_dashboard));
			getIntoFrame(0);
			waitForVisibility(new_user_button, 40);
			wait.until(ExpectedConditions.visibilityOf(filter_search_button));
			wait.until(ExpectedConditions.visibilityOf(filter_search_user_input));
			waitForVisibility(contentTable, 40);
			
		}catch(Exception e){
			ATUReports.add(time +" Loading 'Manage Ad-Hock Users' page failed" + e.getMessage(),  LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}


	// [M.E] check if current user is equal to one of the local properties users
	public boolean userByIndexNameEqualsToOneOfaList(List<String> userList,int index){

		try{

			String text=getUserIdByIndex(index+2).getText();

			boolean isOnTheList=false;

			for(String str: userList) {
				if(str.trim().contains(text))
					return true;
			}
			return false;


		}catch(Exception e){

			System.out.println(e.getLocalizedMessage());

		}

		return false;

	}

	public WebElement getDeleteButtonByIndex(int i){

		return driver.findElement(By.id("ctl00_ContentPlaceHolder1_rptUserBuilder_ctl0"+i+"_lbDelUser"));

	}

	public WebElement getUserIdByIndex(int index){

		try{

			return driver.findElement(By.xpath(".//*[@id='contentDIV']//tr["+(index)+"]/td[1]"));

		}catch(Exception e){

			System.out.println(e.getMessage());

			return null;
		}


	}

	// [M.E] user displayed by index
	public boolean userIsDisplayedByIndex(int i){
		try{
			return driver.findElement(By.xpath(".//*[@id='ctl00_ContentPlaceHolder1_rptUserBuilder_ctl0"+i+"_lbDelUser']")).isDisplayed();
		}catch(NoSuchElementException e){
			return false;
		}

	}

    //[M.E] Delete a user according to it's position (i)
	public boolean clickOnUserDeleteButtonByIndex(int i) {
		try {
			waitForVisibility(getUserIdByIndex(i));

			String text=getUserIdByIndex(i).getText();

			WebElement userDeleteButton=getDeleteButtonByIndex(i);

			wait.until(ExpectedConditions.elementToBeClickable(userDeleteButton));

			userDeleteButton.click();

			System.out.println("Clicked on course delete button according to index");

			waitForAlert(60);

			clickOkInAlertIfPresent();

			Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(120, TimeUnit.SECONDS)
					.pollingEvery(5, TimeUnit.MILLISECONDS);

			wait.until(ExpectedConditions.not(ExpectedConditions.textToBePresentInElement(contentTable,text)));

			Thread.sleep(3000);

		} catch (Exception msg) {
			System.out.println("Fail to click on index course delete button. ");
			Assert.assertTrue(false);
			return false;
		}
		return true;
	}

	// [M.E] waits for the list to refresh by comparing previous text with current
	public void waitForFilterToComplete(){

		try {

			String text = contentTable.getText();

			for (int i = 0; i < 60; i++) {

				if (!contentTable.getText().equals(text))
					break;

				text = contentTable.getText();

				Thread.sleep(4000);


			}
		}catch (Exception e){

			System.out.println(e);

			Assert.assertTrue(false);

		}
	}




}