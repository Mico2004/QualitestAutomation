package com.automation.main.page_helpers;

import java.io.File;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
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
public class ManageExcelCoursesEnrollments extends Page {
	public ManageExcelCoursesEnrollments(WebDriver browser) {
		super(browser);
		
	}

	@FindBy(xpath = ".//*[@id='tegrityBreadcrumbsBox']/li/a")
	public WebElement adminDashboard;
	@FindBy(id = "FileUpload1")
	public WebElement browseButton;
	@FindBy( id = "SelfRegConfig")
	public WebElement ManageExceLCoursesEnrollmentsTitle;
	@FindBy(id = "btnExcelExport")
	public WebElement exportToExcel;
	@FindBy(id = "UploadFile")
	public WebElement importButton;
	@FindBy(id = "txtLog")
	public WebElement textBox;
	
	
	public void waitForPageToLoad(){
	
	try {
		waitForVisibility(adminDashboard);
		waitForVisibility(ManageExceLCoursesEnrollmentsTitle);
		waitForVisibility(exportToExcel);
		waitForVisibility(browseButton);
		waitForVisibility(importButton);
	}catch(Exception e){
    	e.getMessage();
    	ATUReports.add(e.getMessage(), "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));

    	}
	}
	
	
	public void uploadExcelFile(String path){
	
		path = System.getProperty("user.dir") + path;	
		File dir = new File(path);
		if(dir.exists()){
			System.out.println("File is exist.");
			ATUReports.add("file is exist ", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("File is not exist.");
			ATUReports.add("File is not exist.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		// from here you can use as it wrote	
		browseButton.sendKeys(path);
	
	}
	
	public void checkThatTheUploadSucceded(){
		
		try {
		waitForVisibility(textBox);
		new WebDriverWait(driver, 60).until(ExpectedConditions.invisibilityOfElementWithText(By.id("txtLog"), "You have not specified a file."));
		String text = textBox.getText();
		if(text.equals("Start Import Courses Fetching existing coursesMemberships were updatedCourses were updated Import Process Done.")){
			System.out.println("File was upload.");
			ATUReports.add("File was upload.", "True.", "True.", LogAs.PASSED,null);
		} else {
			System.out.println("File was not upload.");
			ATUReports.add("File was not upload.", "True", "False.", LogAs.FAILED,new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
		}catch(Exception e){
	    	e.getMessage();
	    	ATUReports.add(e.getMessage(), "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));

	    	}
		
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
			ATUReports.add("Fail on admin dashboard link.", "Clicked succeeded.", "Clicked succeeded..", LogAs.FAILED,new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			return false;
		}
	}

}
