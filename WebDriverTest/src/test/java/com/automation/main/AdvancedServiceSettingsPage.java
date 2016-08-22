package com.automation.main;

import java.awt.Robot;
import java.util.List;

import javax.xml.xpath.XPath;

import org.apache.xalan.xsltc.compiler.sym;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;

public class AdvancedServiceSettingsPage extends Page {
	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}

	public AdvancedServiceSettingsPage(WebDriver browser) {
		super(browser);
		// TODO Auto-generated constructor stub
	}

	@FindBy(xpath = "//*[@id=\"main\"]/div/div[5]/div/label/input")
	WebElement eula_checkbox;
	@FindBy(xpath = "//*[@id=\"main\"]/div/div[6]/button[1]")
	WebElement ok;
	@FindBy(xpath = "//*[@id=\"main\"]/div/div[6]/button[2]")
	WebElement cancel;
	@FindBy(xpath="//*[@id='main']/div/div[4]/div[1]/label/input") WebElement enable_student_testing_checkbox;
	
	@FindBy(xpath=".//*[@id='main']/div/div[1]/div[1]/label/input") WebElement enable_youtube_integration;
	@FindBy(xpath=".//*[@id='main']/div/div[2]/div[1]/label/input") WebElement enable_automated_capitioning;
	@FindBy(xpath=".//*[@id='tegrityBreadcrumbsBox']/li/a") WebElement adminDashboard;

	public void enableYoutbeCapitionStudent(ConfirmationMenu confirm) {
		waitForVisibility(enable_student_testing_checkbox);	
		waitForVisibility(enable_youtube_integration);
		waitForVisibility(enable_automated_capitioning);
		try {
			if (!enable_student_testing_checkbox.isSelected()) {
				enable_student_testing_checkbox.click();
				System.out.println("checking succefully on enable studnet testing checkbox");
				ATUReports.add("Click studnet testing checkbox", "studnet testing checkbox status", "Success select", "Success select",
						LogAs.PASSED, null);
			} else {
				System.out.println("already checked Enable student testing checkbox");
				ATUReports.add("already  checked Enable student testing checkbox", "student testing checkbox status", "Success select",
						"Success select", LogAs.PASSED, null);
				
			}
			if (!enable_youtube_integration.isSelected()) {
					enable_youtube_integration.click();
					System.out.println("checking succefully on enable youtube checkbox");
					ATUReports.add("Click youtube checkbox", "studnet youtube status", "Success select", "Success select",
							LogAs.PASSED, null);
			} else {
				System.out.println("already checked Enable youtube checkbox checkbox");
				ATUReports.add("already checked Enable youtube checkbox checkbox", "already checked Enable youtube checkbox checkbox", "Success select",
						"Success select", LogAs.PASSED, null);
			}
			if (!enable_automated_capitioning.isSelected()) {
						enable_automated_capitioning.click();
						System.out.println("checking succefully on enable Captioning checkbox");
						ATUReports.add("Click Captioning checkbox", "studnet Captioning status", "Success select", "Success select",
								LogAs.PASSED, null);
			} else {
				System.out.println("already checked automated capitioning checkbox");
				ATUReports.add("already checked automated capitioning checkbox", "already checked automated capitioning checkbox", "Success select",
						"Success select", LogAs.PASSED, null);
			}
			
			WebElement wi = driver.findElement(By.xpath("//*[@id='main']"));
			Actions builder = new Actions(driver);
			builder.sendKeys(Keys.PAGE_DOWN);
			builder.moveToElement(wi).build().perform();
			new WebDriverWait(driver, 30).until(ExpectedConditions.elementToBeClickable(ok));
			ok.click();
			
			confirm.waitForVisibility(confirm.ok_button);
			confirm.clickOnOkButton();
		} catch (Exception e) {
			System.out.println("failed clicking on student testing checkbox");
			ATUReports.add("Click student testing checkbox", "student testing checkbox status", "Success select", "failed select",
					LogAs.FAILED, null);
		}
	}
	
	
	/// click on eula check box if not selected
	public String clickOnEulaCheckboxAndClickOk(WebDriver driver, ConfirmationMenu confirm) throws Exception {
		String eula = "-1";
		waitForVisibility(eula_checkbox);
		Robot r = new Robot();
		WebElement element;
		try {
			if (!eula_checkbox.isSelected()) {
				eula_checkbox.click();
				eula_checkbox.sendKeys(Keys.TAB);
				driver.switchTo().frame(1);
				Thread.sleep(1000);
				element = driver.findElement(By.xpath("/html/body"));
				element.clear();
				element.sendKeys("hello");
				eula = driver.findElement(By.xpath("/html/body/p")).getText();
				driver.switchTo().defaultContent();	
				
				WebElement wi = driver.findElement(By.xpath("//*[@id='main']"));
				Actions builder = new Actions(driver);
				builder.sendKeys(Keys.PAGE_DOWN);
				builder.moveToElement(wi).build().perform();
				new WebDriverWait(driver, 30).until(ExpectedConditions.elementToBeClickable(ok));
				ok.click();
				
				confirm.waitForVisibility(confirm.ok_button);
				confirm.clickonokbuttonafterEulaChangeSetting();
				System.out.println("clicked succefully on eula checkbox");
				ATUReports.add("Click eula checkbox", "eula checkbox status", "Success select", "Success select",
						LogAs.PASSED, null);
				return eula;
			} else {
				System.out.println("already clicked on eula checkbox");
				eula_checkbox.sendKeys(Keys.TAB);
				driver.switchTo().frame(1);
				element = driver.findElement(By.xpath("/html/body"));
				eula = driver.findElement(By.xpath("/html/body/p")).getText();
				if (eula.isEmpty()) {
					element.sendKeys("hello");
					System.out.println("body was empty so filled it again...");
					eula = driver.findElement(By.xpath("/html/body/p")).getText();
					driver.switchTo().defaultContent();
					wait.until(ExpectedConditions.elementToBeClickable(ok));
					ok.click();
					confirm.waitForVisibility(confirm.ok_button);
					confirm.clickonokbuttonafterEulaChangeSetting();
					ATUReports.add("Click eula checkbox", "eula checkbox status", "Success select", "Success select",
							LogAs.PASSED, null);
					return eula;
				} 

				driver.switchTo().defaultContent();
				cancel.click();
				System.out.println("body is not empty so clicking on cancel button");
				ATUReports.add("Click eula checkbox", "eula checkbox status", "Success select", "Success select",
						LogAs.PASSED, null);
			}
		} catch (Exception e) {
			
		}
		return eula;

	}

	public void enableStudyTestingCheckboxAndClickOk(ConfirmationMenu confirm){
		waitForVisibility(enable_student_testing_checkbox);
		
		try {
			if (!enable_student_testing_checkbox.isSelected()) {
				enable_student_testing_checkbox.click();
				ok.click();
				confirm.waitForVisibility(confirm.ok_button);
				confirm.clickOnOkButton();
				System.out.println("checking succefully on enable studnet testing checkbox");
				ATUReports.add("Click studnet testing checkbox", "studnet testing checkbox status", "Success select", "Success select",
						LogAs.PASSED, null);
			} else {
				cancel.click();
				System.out.println("already checked Enable student testing checkbox");
				ATUReports.add("already  checked Enable student testing checkbox", "student testing checkbox status", "Success select",
						"Success select", LogAs.PASSED, null);

			}
		} catch (Exception e) {
			System.out.println("failed clicking on student testing checkbox");
			ATUReports.add("Click student testing checkbox", "student testing checkbox status", "Success select", "failed select",
					LogAs.FAILED, null);
		}
	}
		
		public void enableYotubeCheckboxAndClickOk(ConfirmationMenu confirm){
			waitForVisibility(enable_youtube_integration);
			
			try {
				if (!enable_youtube_integration.isSelected()) {
					enable_youtube_integration.click();
					ok.click();
					confirm.waitForVisibility(confirm.ok_button);
					confirm.clickOnOkButton();
					System.out.println("checking succefully on enable youtube checkbox");
					ATUReports.add("Click youtube checkbox", "studnet youtube status", "Success select", "Success select",
							LogAs.PASSED, null);
				} else {
					cancel.click();
					System.out.println("already checked Enable youtube checkbox");
					ATUReports.add("already selected youtube checkbox", "youtube checkbox status", "Success select",
							"Success select", LogAs.PASSED, null);

				}
			} catch (Exception e) {
				System.out.println("failed clicking on youtube checkbox");
				ATUReports.add("Click youtube checkbox", "student youtube status", "Success select", "failed select",
						LogAs.FAILED, null);
			}
		
	
	}
		
	public void enableCaptioningCheckboxAndClickOk(ConfirmationMenu confirm){
			waitForVisibility(enable_automated_capitioning);
			
			try {
				if (!enable_automated_capitioning.isSelected()) {
					enable_automated_capitioning.click();
					ok.click();
					confirm.waitForVisibility(confirm.ok_button);
					confirm.clickOnOkButton();
					System.out.println("checking succefully on enable Captioning checkbox");
					ATUReports.add("Click Captioning checkbox", "studnet Captioning status", "Success select", "Success select",
							LogAs.PASSED, null);
				} else {
					cancel.click();
					System.out.println("already checked Enable Captioning checkbox");
					ATUReports.add("already selected Captioning checkbox", "Captioning checkbox status", "Success select",
							"Success select", LogAs.PASSED, null);

				}
			} catch (Exception e) {
				System.out.println("failed clicking on Captioning checkbox");
				ATUReports.add("Click Captioning checkbox", "student Captioning status", "Success select", "failed select",
						LogAs.FAILED, null);
			}
		
	
	}
	
	
	/// uncheck on eula check box if selected
	public void disableEulaCheckboxAndClickOk(ConfirmationMenu confirm) {
		waitForVisibility(eula_checkbox);

		try {
			if (eula_checkbox.isSelected()) {
				eula_checkbox.click();
				ok.click();
				confirm.waitForVisibility(confirm.ok_button);
				confirm.clickonokbuttonafterEulaChangeSetting();
				System.out.println("unclicked succefully on eula checkbox");
				ATUReports.add("unClick eula checkbox", "eula checkbox status", "Success select", "Success select",
						LogAs.PASSED, null);
			} else {
				cancel.click();
				System.out.println("already unchecked eula checkbox");
				ATUReports.add("already unchecked eula checkbox", "eula checkbox status", "Success select",
						"Success select", LogAs.PASSED, null);

			}
		} catch (Exception e) {
			System.out.println("failed clicking on eula checkbox");
			ATUReports.add("unClick eula checkbox", "eula checkbox status", "Success select", "failed select",
					LogAs.FAILED, null);
		}

	}
	
	// This function clicks on Ok button
	public void clickOnOkbutton() {
		clickElement(ok);
		try {
			Thread.sleep(1000);
		} catch(Exception msg) {
			System.out.println("Fail to Thread.sleep(1000).");
		}
		clickElement(driver.findElement(By.cssSelector(".btn.btn-default")));
		
	}
}
