package com.automation.main;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;import com.automation.main.page_helpers.Page;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Listeners;

import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;

@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class EditRecording extends Page {
	public EditRecording(WebDriver browser) {
		super(browser);
		// TODO Auto-generated constructor stub
	}

	public String getTitleOffirstChapterRecordingName() throws InterruptedException{
		for(int i=0; i<10; i++) {
			try {
				driver.switchTo().frame(0);
				break;
			} catch (Exception e) {
				Thread.sleep(Page.TIMEOUT_TINY);
			}
		}
		
		
		for(int i=0; i<20; i++) {
			try {
				if(driver.findElement(By.id("PlayButton_Img")).isDisplayed()) {
					System.out.println("2222");
					break;
				} else {
					Thread.sleep(Page.TIMEOUT_TINY);
				}
			} catch (Exception e) {
				Thread.sleep(Page.TIMEOUT_TINY);
			}
				
		}
		
		
		
		for(String window_handler: driver.getWindowHandles()) {
			driver.switchTo().window(window_handler);
			break;
		}
		
		System.out.println(driver.findElements(By.cssSelector(".optionList>li>a")).get(1).getText());
		driver.findElements(By.cssSelector(".optionList>li>a")).get(1).click();
		driver.findElements(By.cssSelector(".optionList>li>a")).get(1).click();
		driver.findElements(By.cssSelector(".optionList>li>a")).get(1).click();
		
//		driver.findElements(By.cssSelector(".optionList>li>a")).get(1).click();
		Thread.sleep(Page.TIMEOUT_TINY);
		
		String name = driver.findElement(By.id("NewTitle")).getAttribute("value");
		
		for(String window_handler: driver.getWindowHandles()) {
			driver.switchTo().window(window_handler);
			break;
		}
		

		driver.findElements(By.cssSelector(".ng-scope.ng-binding")).get(1).click();
		Thread.sleep(Page.TIMEOUT_TINY);
		
		return name;
		
	}
	
	public void changeFirstChapterRecordingNameToTargetNameNew(String target_name) throws InterruptedException {
		
		ConfirmationMenu confirm_menu = PageFactory.initElements(driver, ConfirmationMenu.class);
		RecordingHelperPage record = PageFactory.initElements(driver, RecordingHelperPage.class);
		
		
		for(int i=0; i<10; i++) {
			try {
				driver.switchTo().frame(0);
				break;
			} catch (Exception e) {
				Thread.sleep(Page.TIMEOUT_TINY);
			}
		}
		
		
			while(!isElementPresent(By.id("PlayButton_Img"))) {
					System.out.println("element is not visable");
					Thread.sleep(Page.TIMEOUT_TINY);	
			}
		
		for(String window_handler: driver.getWindowHandles()) {
			driver.switchTo().window(window_handler);
			break;
		}
		
		record.waitForVisibility(driver.findElements(By.cssSelector(".optionList>li>a")).get(1));
		driver.findElements(By.cssSelector(".optionList>li>a")).get(1).click();
		driver.findElements(By.cssSelector(".optionList>li>a")).get(1).click();
		
		
		//changing the name of the title
		Thread.sleep(Page.TIMEOUT_TINY);
		driver.findElement(By.id("NewTitle")).clear();
		driver.findElement(By.id("NewTitle")).sendKeys(target_name);
		
		// click on the apply
		driver.findElement(By.cssSelector(".btn.btn-primary.btnApply")).click();
		Thread.sleep(Page.TIMEOUT_TINY);
		
		
		//click on the ok
		while(record.isElementPresent(By.cssSelector("#ModalDialogHeader"))){
			
			WebElement ie = record.getStaleElem(By.cssSelector("#ModalDialogHeader"),driver);		
			if(ie.getText().contains("Success")){
				confirm_menu.clickOnOkButtonAfterEditRecord();
				break;
			   }			
			else Thread.sleep(Page.TIMEOUT_TINY);
		}
		
		//return to the course
	    driver.findElement(By.xpath(".//*[@id='tegrityBreadcrumbsBox']/li[2]/a")).click();
	
		
	}


	// Change first chapter recording name
	public void changeFirstChapterRecordingNameToTargetName(String target_name) throws InterruptedException {
		for(int i=0; i<10; i++) {
			try {
				driver.switchTo().frame(0);
				break;
			} catch (Exception e) {
				Thread.sleep(Page.TIMEOUT_TINY);
			}
		}
		
		
		for(int i=0; i<20; i++) {
			try {
				if(driver.findElement(By.id("PlayButton_Img")).isDisplayed()) {
					System.out.println("2222");
					break;
				} else {
					Thread.sleep(Page.TIMEOUT_TINY);
				}
			} catch (Exception e) {
				Thread.sleep(Page.TIMEOUT_TINY);
			}
				
		}
			
		for(String window_handler: driver.getWindowHandles()) {
			driver.switchTo().window(window_handler);
			break;
		}
		
		
		
		
		System.out.println(driver.findElements(By.cssSelector(".optionList>li>a")).get(1).getText());
		driver.findElements(By.cssSelector(".optionList>li>a")).get(1).click();
		driver.findElements(By.cssSelector(".optionList>li>a")).get(1).click();
		driver.findElements(By.cssSelector(".optionList>li>a")).get(1).click();
		
//		driver.findElements(By.cssSelector(".optionList>li>a")).get(1).click();
		Thread.sleep(Page.TIMEOUT_TINY);
		
		driver.findElement(By.id("NewTitle")).clear();
		driver.findElement(By.id("NewTitle")).sendKeys(target_name);
		
		driver.findElement(By.cssSelector(".btn.btn-primary.btnApply")).click();
		Thread.sleep(Page.TIMEOUT_TINY);
		
		for(int i=0; i<60; i++) {
			try {
				if(driver.findElement(By.id("PlayButton_Img")).isDisplayed()) {
//					System.out.println("2222");
					break;
				} else {
					Thread.sleep(Page.TIMEOUT_TINY);
				}
			} catch (Exception e) {
				Thread.sleep(Page.TIMEOUT_TINY);
			}
				
		}
		
		for(String window_handler: driver.getWindowHandles()) {
			driver.switchTo().window(window_handler);
			break;
		}
		

		driver.findElement(By.cssSelector(".btn.btn-default")).click();
		Thread.sleep(Page.TIMEOUT_TINY);
	
	}
	
	// set target keyword for first chapter 
	public void setTargetKeywordForFirstChapter(String target_keyword) throws InterruptedException {
			
		ConfirmationMenu confirm_menu = PageFactory.initElements(driver, ConfirmationMenu.class);
		RecordingHelperPage record = PageFactory.initElements(driver, RecordingHelperPage.class);
		
		
		for(int i=0; i<10; i++) {
			try {
				driver.switchTo().frame(0);
				break;
			} catch (Exception e) {
				Thread.sleep(Page.TIMEOUT_TINY);
			}
		}
		
		while(!isElementPresent(By.id("PlayButton_Img"))) {
			System.out.println("element is not visable");
			Thread.sleep(Page.TIMEOUT_TINY);	
	}

		for(String window_handler: driver.getWindowHandles()) {
			driver.switchTo().window(window_handler);
			break;
		}
		
		record.waitForVisibility(driver.findElements(By.cssSelector(".optionList>li>a")).get(1));
		driver.findElements(By.cssSelector(".optionList>li>a")).get(1).click();
		driver.findElements(By.cssSelector(".optionList>li>a")).get(1).click();
		Thread.sleep(Page.TIMEOUT_TINY);
		
		driver.findElement(By.id("NewKeyword")).clear();
		driver.findElement(By.id("NewKeyword")).sendKeys(target_keyword);
		
		driver.findElement(By.cssSelector(".btn.btn-primary.btnApply")).click();
		Thread.sleep(Page.TIMEOUT_TINY);
		
		//click on the ok
		while(record.isElementPresent(By.cssSelector("#ModalDialogHeader"))){
			
			WebElement ie = record.getStaleElem(By.cssSelector("#ModalDialogHeader"),driver);		
			if(ie.getText().contains("Success")){
				confirm_menu.clickOnOkButtonAfterEditRecord();
				break;
			   }			
			else Thread.sleep(Page.TIMEOUT_TINY);
		}
		
		//return to the course
	    driver.findElement(By.xpath(".//*[@id='tegrityBreadcrumbsBox']/li[2]/a")).click();	
		
	}
}
