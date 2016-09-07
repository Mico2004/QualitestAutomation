package com.automation.main;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
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
		
		RecordingHelperPage record = PageFactory.initElements(driver, RecordingHelperPage.class);
		
		for(int i=0; i<10; i++) {
			try {
				driver.switchTo().frame(0);
				break;
			} catch (Exception e) {
				Thread.sleep(1000);
			}
		}
			
		int i = 0; 
		while(!isElementPresent(By.id("PlayButton_Img")) || i < 20) {
				i++;
				System.out.println("element is not visable");
				Thread.sleep(2000);	
		}
		
		System.out.println("Wait that the element edit chapter will click from the list.");
		ATUReports.add("Wait that the element edit chapter will click from the list.", "True.", "True.", LogAs.PASSED, null);
		
		for(String window_handler: driver.getWindowHandles()) {
			driver.switchTo().window(window_handler);
			break;
		}
		
		record.waitForVisibility(driver.findElements(By.cssSelector(".optionList>li>a")).get(1));
		driver.findElements(By.cssSelector(".optionList>li>a")).get(1).click();
		driver.findElements(By.cssSelector(".optionList>li>a")).get(1).click();
		
		System.out.println("The element edit chapter was clicked");
		ATUReports.add("The element edit chapter was clicked", "True.", "True.", LogAs.PASSED, null);
		
		
		Thread.sleep(2000);
		
		String name = driver.findElement(By.id("NewTitle")).getAttribute("value");
		
		for(String window_handler: driver.getWindowHandles()) {
			driver.switchTo().window(window_handler);
			break;
		}
		
		//return to the course
	    driver.findElement(By.xpath(".//*[@id='tegrityBreadcrumbsBox']/li[2]/a")).click();
		
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
				Thread.sleep(1000);
			}
		}
		
			int i = 0; 
			while(!isElementPresent(By.id("PlayButton_Img")) || i < 20) {
					i++;
					System.out.println("element is not visable");
					Thread.sleep(2000);	
			}
			
			System.out.println("Wait that the element edit chapter will click from the list.");
			ATUReports.add("Wait that the element edit chapter will click from the list.", "True.", "True.", LogAs.PASSED, null);
			
		
		for(String window_handler: driver.getWindowHandles()) {
			driver.switchTo().window(window_handler);
			break;
		}
		
		record.waitForVisibility(driver.findElements(By.cssSelector(".optionList>li>a")).get(1));
		driver.findElements(By.cssSelector(".optionList>li>a")).get(1).click();
		driver.findElements(By.cssSelector(".optionList>li>a")).get(1).click();
		
		System.out.println("The element edit chapter was clicked");
		ATUReports.add("The element edit chapter was clicked", "True.", "True.", LogAs.PASSED, null);
		
		
		//changing the name of the title
		Thread.sleep(4000);
		driver.findElement(By.id("NewTitle")).clear();
		driver.findElement(By.id("NewTitle")).sendKeys(target_name);
		
		// click on the apply
		driver.findElement(By.cssSelector(".btn.btn-primary.btnApply")).click();
		Thread.sleep(2000);
		
		
		System.out.println("Click on the apply.");
		ATUReports.add("Click on the apply.", "True.", "True.", LogAs.PASSED, null);
		//click on the ok
		while(record.isElementPresent(By.cssSelector("#ModalDialogHeader"))){
			
			WebElement ie = record.getStaleElem(By.cssSelector("#ModalDialogHeader"),driver);		
			String message = getTextFromWebElement(ie);
			if(message.contains("Success")){
				confirm_menu.clickOnOkButtonAfterEditRecord();
				break;
			} else if(message.contains("Error")) {
				ATUReports.add("Get an error while click on apply.", LogAs.WARNING, null);
				confirm_menu.clickOnOkButtonAfterErrorClickOnTheApply();	
				System.out.println("Get an error while click on apply.");	
				driver.findElement(By.cssSelector(".btn.btn-primary.btnApply")).click();
				Thread.sleep(2000);
			}
			else Thread.sleep(3000);
		}
		
		//return to the course
	    driver.findElement(By.xpath(".//*[@id='tegrityBreadcrumbsBox']/li[2]/a")).click();
	
		
	}
	
	
	public void addCaptionSrtToFirstChapterRecording() throws InterruptedException, AWTException {
		
		ConfirmationMenu confirm_menu = PageFactory.initElements(driver, ConfirmationMenu.class);
		RecordingHelperPage record = PageFactory.initElements(driver, RecordingHelperPage.class);
		
		
		for(int i=0; i<10; i++) {
			try {
				driver.switchTo().frame(0);
				break;
			} catch (Exception e) {
				Thread.sleep(1000);
			}
		}
		
		int i = 0 ; 
		while(!isElementPresent(By.id("PlayButton_Img")) || i<20) {
			  i++;
			System.out.println("element is not visable");
			Thread.sleep(2000);	
		}
		
		System.out.println("Wait that the element add closed caption will click from the list.");
		ATUReports.add("Wait that the element add closed caption will click from the list.", "True.", "True.", LogAs.PASSED, null);
		
		Thread.sleep(2000);
		for(String window_handler: driver.getWindowHandles()) {
			driver.switchTo().window(window_handler);
			break;
		}
		record.waitForVisibility(driver.findElements(By.cssSelector(".optionList>li>a")).get(4));
		driver.findElements(By.cssSelector(".optionList>li>a")).get(4).click();
		
		Thread.sleep(2000);
		
	
		System.out.println("The element add caption was clicked");
		ATUReports.add("The elementadd caption was clicked", "True.", "True.", LogAs.PASSED, null);
		

		String path = System.getProperty("user.dir") + "\\workspace\\QualitestAutomation\\resources\\documents\\CloseCaption.srt";
		
		driver.findElement(By.id("UploadFile")).sendKeys(path);

		
		Thread.sleep(2000);
		driver.findElement(By.id("AddCaptioning")).click();
		Thread.sleep(1000);
		
		System.out.println("The file close caption was added.");
		ATUReports.add("The file close caption was added.", "True.", "True.", LogAs.PASSED, null);
		
		for(String window_handler: driver.getWindowHandles()) {
			driver.switchTo().window(window_handler);
			break;
		}
		
		while(record.isElementPresent(By.cssSelector("#ModalDialogHeader"))){		
			WebElement ie = record.getStaleElem(By.cssSelector("#ModalDialogHeader"),driver);		
			String message = getTextFromWebElement(ie);
			if(message.contains("Success")){
				confirm_menu.clickOnOkButtonAfterAddCloseCaptioning();
				break;
			} else if(message.contains("Error")) {
				ATUReports.add("Get an error while click on apply.", LogAs.WARNING, null);
				confirm_menu.clickOnOkButtonAfterErrorClickOnTheApply();	
				System.out.println("Get an error while click on apply.");	
				driver.findElement(By.id("AddCaptioning")).click();
				Thread.sleep(2000);
			}
			else Thread.sleep(3000);
		}
	
	}
	


	// Change first chapter recording name
	public void changeFirstChapterRecordingNameToTargetName(String target_name) throws InterruptedException {
		
		ConfirmationMenu confirm_menu = PageFactory.initElements(driver, ConfirmationMenu.class);
		RecordingHelperPage record = PageFactory.initElements(driver, RecordingHelperPage.class);
		
		for(int i=0; i<10; i++) {
			try {
				driver.switchTo().frame(0);
				break;
			} catch (Exception e) {
				Thread.sleep(1000);
			}
		}
		
		
		int index = 0; 
		while(!isElementPresent(By.id("PlayButton_Img")) || index< 20) {
			index++;
				System.out.println("element is not visable");
				Thread.sleep(2000);	
		}
			
		System.out.println("Wait that the element edit chapter will click from the list.");
		ATUReports.add("Wait that the element edit chapter will click from the list.", "True.", "True.", LogAs.PASSED, null);
		
		for(String window_handler: driver.getWindowHandles()) {
			driver.switchTo().window(window_handler);
			break;
		}
		
		System.out.println(driver.findElements(By.cssSelector(".optionList>li>a")).get(1).getText());
		driver.findElements(By.cssSelector(".optionList>li>a")).get(1).click();
		driver.findElements(By.cssSelector(".optionList>li>a")).get(1).click();
		driver.findElements(By.cssSelector(".optionList>li>a")).get(1).click();
		
		System.out.println("The element edit chapter was clicked");
		ATUReports.add("The element edit chapter was clicked", "True.", "True.", LogAs.PASSED, null);
		
		Thread.sleep(2000);
		
		driver.findElement(By.id("NewTitle")).clear();
		driver.findElement(By.id("NewTitle")).sendKeys(target_name);
		
		driver.findElement(By.cssSelector(".btn.btn-primary.btnApply")).click();
		Thread.sleep(2000);
		
		//click on the ok
		while(record.isElementPresent(By.cssSelector("#ModalDialogHeader"))){
			
			WebElement ie = record.getStaleElem(By.cssSelector("#ModalDialogHeader"),driver);		
			String message = getTextFromWebElement(ie);
			if(message.contains("Success")){
				confirm_menu.clickOnOkButtonAfterEditRecord();
				break;
			} else if(message.contains("Error")) {
				ATUReports.add("Get an error while click on apply.", LogAs.WARNING, null);
				confirm_menu.clickOnOkButtonAfterErrorClickOnTheApply();	
				System.out.println("Get an error while click on apply.");	
				driver.findElement(By.cssSelector(".btn.btn-primary.btnApply")).click();
				Thread.sleep(2000);
			}
			else Thread.sleep(3000);
		}
		
		//return to the course
	    driver.findElement(By.xpath(".//*[@id='tegrityBreadcrumbsBox']/li[2]/a")).click();	
	
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
				Thread.sleep(1000);
			}
		}
		
		int index = 0; 
		while(!isElementPresent(By.id("PlayButton_Img")) || index< 20) {
			index++;
				System.out.println("element is not visable");
				Thread.sleep(2000);	
		}
		
		System.out.println("Wait the the element edit chapter will click from the list.");
		ATUReports.add("Wait the the element edit chapter will click from the list.", "True.", "True.", LogAs.PASSED, null);

		for(String window_handler: driver.getWindowHandles()) {
			driver.switchTo().window(window_handler);
			break;
		}
		
		record.waitForVisibility(driver.findElements(By.cssSelector(".optionList>li>a")).get(1));
		driver.findElements(By.cssSelector(".optionList>li>a")).get(1).click();
		driver.findElements(By.cssSelector(".optionList>li>a")).get(1).click();
		Thread.sleep(2000);
		
		System.out.println("The element edit chapter was Clicked");
		ATUReports.add("The element edit chapter was Clicked.", "True.", "True.", LogAs.PASSED, null);
		
		driver.findElement(By.id("NewKeyword")).clear();
		driver.findElement(By.id("NewKeyword")).sendKeys(target_keyword);
		
		driver.findElement(By.cssSelector(".btn.btn-primary.btnApply")).click();
		Thread.sleep(2000);
		
		//click on the ok
		while(record.isElementPresent(By.cssSelector("#ModalDialogHeader"))){	
			WebElement ie = record.getStaleElem(By.cssSelector("#ModalDialogHeader"),driver);		
			String message = getTextFromWebElement(ie);
			if(message.contains("Success")){
				confirm_menu.clickOnOkButtonAfterEditRecord();
				break;
			} else if(message.contains("Error")) {
				ATUReports.add("Get an error while click on apply.", LogAs.WARNING, null);
				confirm_menu.clickOnOkButtonAfterErrorClickOnTheApply();	
				System.out.println("Get an error while click on apply.");	
				driver.findElement(By.cssSelector(".btn.btn-primary.btnApply")).click();
				Thread.sleep(2000);
			}
			else Thread.sleep(3000);
		}
		
		//return to the course
	    driver.findElement(By.xpath(".//*[@id='tegrityBreadcrumbsBox']/li[2]/a")).click();	
		
	    System.out.println("Wait the the element edit chapter will click from the list.");
		ATUReports.add("Wait the the element edit chapter will click from the list.", "True.", "True.", LogAs.PASSED, null);
	}
}
