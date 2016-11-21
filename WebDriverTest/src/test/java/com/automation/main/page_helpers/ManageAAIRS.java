package com.automation.main.page_helpers;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Listeners;

import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;


@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class ManageAAIRS extends Page {
	public ManageAAIRS(WebDriver browser) {
		super(browser);
		
	}
	
	@FindBy(xpath = ".//*[@id='DATASET_MAIN']/a")
	public WebElement editButtonAuthorization;
	@FindBy(id = "DATASET_Excel_Import_AUTO_CONFIG_21")
	public WebElement excelImportAuthorization;
	@FindBy(id = ".//*[@id='DATASET_SORTABLE")
	public WebElement Authorization;
	@FindBy(xpath = ".//*[@id='AUTH_MAIN']/a")
	public WebElement editButtonDirectLoginAuthentication;
	@FindBy(id = "AUTH_Excel_Import_AUTO_CONFIG_8")
	public WebElement excelImportAuthentication;
	@FindBy(id = "AUTH_SORTABLE")
	public WebElement DirectLoginAuthentication;
	@FindBy(xpath = ".//*[@id='tegrityBreadcrumbsBox']/li/a")
	public WebElement adminDashboard;
	@FindBy(id = "ManageAAIRS")
	public WebElement manageAAIRS;
	@FindBy(xpath = ".//*[@id='EzSetupRoot1_RadPageView2Integration']/div[1]")
	public WebElement aarisTitle;
	@FindBy(id = "DATASET_MAIN")
	public WebElement Main;
	@FindBy(xpath = ".//*[@id='DATASET_MAIN']/tablet/body")
	public WebElement AuthorizationTable;
	@FindBy(xpath = ".//*[@id='AUTH_MAIN']/table/tbody")
	public WebElement DirectLoginAuthenticationTable ;
	
	
	public void AddExcelImportToAuthentication(){
		
		clickElement(editButtonAuthorization);
		
		
		List<WebElement> tableRows = AuthorizationTable.findElements(By.xpath("tr"));
		List<WebElement> tableCells= AuthorizationTable.findElements(By.xpath("tr/td"));
		
		for(WebElement row : tableRows) {
		    List<WebElement> cells = row.findElements(By.xpath("td"));
		   }
		for(WebElement cell : tableCells) {
		   
		}
		
		dragAndDrop(excelImportAuthorization,Authorization);
		clickElement(editButtonDirectLoginAuthentication);
		dragAndDrop(excelImportAuthentication,DirectLoginAuthentication);
		
	}
	
	public void waitForPageToLoad(){
		
		waitForVisibility(adminDashboard);
		waitForVisibility(manageAAIRS);
		driver.switchTo().frame(0);
		waitForVisibility(aarisTitle);
		waitForVisibility(Main);
	}
	
	public boolean clickOnAdminDashboard() {
		try {
			waitForVisibility(adminDashboard);	
			adminDashboard.click();
			System.out.println("Clicked on admin dashboard link.");
			ATUReports.add("Clicked on admin dashboard link.", "Clicked succeeded.", "Clicked succeeded..", LogAs.PASSED,null);
			return true;
		} catch (Exception msg) {
			System.out.println("Fail to click on admin dashboard link.");
			ATUReports.add("Fail on admin dashboard link.", "Clicked succeeded.", "Clicked succeeded..", LogAs.PASSED,null);
			return false;
		}
	}

}	

















