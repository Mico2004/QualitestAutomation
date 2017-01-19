package com.automation.main.page_helpers;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
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
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;


@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class EditRecording extends Page {
	
	@FindBy(xpath = ".//*[@id='tegrityBreadcrumbsBox']/li[2]/a")
	public WebElement Breadcrumbs;
	@FindBy(css = ".btn.btn-primary.btnApply")
	public WebElement ApplyButton;
	@FindBy(id = "AddCaptioning")
	public WebElement AddCaptioningButton;
	@FindBy(id = "UploadFile")
	public WebElement UploadFile;
	@FindBy(id = "NewTitle")
	public WebElement ChapterName;
	@FindBy(css= ".optionList>li>a")
	List<WebElement> listButtons;
	@FindBy(id = "NewKeyword")
	public WebElement NewKeywordButton;
	@FindBy(id = "ModalDialogHeader")
	public WebElement header;

	
	
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
		while(!isElementPresent(By.id("PlayButton_Img")) && i < 20) {
				i++;
				System.out.println("element is not visable");
				Thread.sleep(1500);	
		}
		
		System.out.println("Wait that the element edit chapter will click from the list.");
		ATUReports.add(time +" Wait that the element edit chapter will click from the list.", "True.", "True.", LogAs.PASSED, null);
		
		for(String window_handler: driver.getWindowHandles()) {
			driver.switchTo().window(window_handler);
			break;
		}
		
		record.waitForVisibility(listButtons.get(1));
		clickElement(listButtons.get(1));
		clickElement(listButtons.get(1));
		//driver.findElements(By.cssSelector(".optionList>li>a")).get(1).click();
		//driver.findElements(By.cssSelector(".optionList>li>a")).get(1).click();
		
		System.out.println("The element edit chapter was clicked");
		ATUReports.add(time +" The element edit chapter was clicked", "True.", "True.", LogAs.PASSED, null);
		
		
		Thread.sleep(2000);
		
		String name = ChapterName.getAttribute("value");
		
		for(String window_handler: driver.getWindowHandles()) {
			driver.switchTo().window(window_handler);
			break;
		}
		
		//return to the course
		clickElement(Breadcrumbs);
		return name;
		
	}
	
	public void changeFirstChapterRecordingNameToTargetNameNew(String target_name) throws InterruptedException {
		
		ConfirmationMenu confirm_menu = PageFactory.initElements(driver, ConfirmationMenu.class);
		RecordingHelperPage record = PageFactory.initElements(driver, RecordingHelperPage.class);
		try {
		
		for(int i=0; i<10; i++) {
			try {
				driver.switchTo().frame(0);
				break;
			} catch (Exception e) {
				Thread.sleep(1000);
			}
		}
		
			int i = 0; 
			while(!isElementPresent(By.id("PlayButton_Img")) && i < 20) {
					i++;
					System.out.println("element is not visable");
					Thread.sleep(1500);	
			}
			
			System.out.println("Wait that the element edit chapter will click from the list.");
			ATUReports.add(time +" Wait that the element edit chapter will click from the list.", "True.", "True.", LogAs.PASSED, null);
			
		
		for(String window_handler: driver.getWindowHandles()) {
			driver.switchTo().window(window_handler);
			break;
		}
		
		record.waitForVisibility(listButtons.get(1));
		clickElement(listButtons.get(1));
		clickElement(listButtons.get(1));
		
		System.out.println("The element edit chapter was clicked");
		ATUReports.add(time +" The element edit chapter was clicked", "True.", "True.", LogAs.PASSED, null);
		
		
		//changing the name of the title
		Thread.sleep(4000);
		ChapterName.clear();
		ChapterName.sendKeys(target_name);
		
		// click on the apply
		clickElement(ApplyButton);
		Thread.sleep(2000);
		
		System.out.println("Click on the apply.");
		ATUReports.add(time +" Click on the apply.", "True.", "True.", LogAs.PASSED, null);
		
//		int numberOfAttempts = 0;
//		//click on the ok
//		while(record.isElementPresent(By.cssSelector("#ModalDialogHeader"))){		
//			WebElement ie = record.getStaleElem(By.cssSelector("#ModalDialogHeader"),driver,5);		
//			String message = getTextFromWebElement(ie,5);
//			if(message.contains("Success")){
//				confirm_menu.clickOnOkButtonAfterEditRecord();
//				break;
//			} else if(message.contains("Error")) {
//				ATUReports.add(time +" Get an error while click on apply.", LogAs.WARNING, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
//				confirm_menu.clickOnOkButtonAfterErrorClickOnTheApply();	
//				System.out.println("Get an error while click on apply.");	
//				clickElement(ApplyButton);
//				numberOfAttempts++;
//				if(numberOfAttempts == 5) {
//					ATUReports.add(time +" Get an error while click on apply.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
//					Assert.assertTrue(false);
//				}
//			}
//			else Thread.sleep(3000);
//		}
		
		new WebDriverWait(driver, 60).until(ExpectedConditions.textToBePresentInElement(header, "Success"));
		confirm_menu.clickOnOkButtonAfterEditRecord();
			
		//return to the course
		clickElement(Breadcrumbs);
		
		}catch(Exception e){
			e.getMessage();
			ATUReports.add(e.getMessage(), "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
	
		
	}
	
	
	public void addCaptionSrtToFirstChapterRecording(String path) throws InterruptedException, AWTException {
		
		ConfirmationMenu confirm_menu = PageFactory.initElements(driver, ConfirmationMenu.class);
		RecordingHelperPage record = PageFactory.initElements(driver, RecordingHelperPage.class);
		
		try {
		for(int i=0; i<10; i++) {
			try {
				driver.switchTo().frame(0);
				break;
			} catch (Exception e) {
				Thread.sleep(1000);
			}
		}
		
		int i = 0 ; 
		while(!isElementPresent(By.id("PlayButton_Img")) && i<20) {
			  i++;
			System.out.println("element is not visable");
			Thread.sleep(1500);	
		}
		
		System.out.println("Wait that the element add closed caption will click from the list.");
		ATUReports.add(time +" Wait that the element add closed caption will click from the list.", "True.", "True.", LogAs.PASSED, null);
		
		Thread.sleep(2000);
		for(String window_handler: driver.getWindowHandles()) {
			driver.switchTo().window(window_handler);
			break;
		}
		

		record.waitForVisibility(listButtons.get(4));
		clickElement(listButtons.get(4));
		clickElement(listButtons.get(4));
		Thread.sleep(2000);
		
	
		System.out.println("The element add caption was clicked");
		ATUReports.add(time +" The elementadd caption was clicked", "True.", "True.", LogAs.PASSED, null);
		

		UploadFile.sendKeys(path);

		
		Thread.sleep(2000);
		clickElementJS(AddCaptioningButton);
		Thread.sleep(1000);
		
		System.out.println("The file close caption was added.");
		ATUReports.add(time +" The file close caption was added.", "True.", "True.", LogAs.PASSED, null);
		
		for(String window_handler: driver.getWindowHandles()) {
			driver.switchTo().window(window_handler);
			break;
		}
		
		new WebDriverWait(driver, 60).until(ExpectedConditions.textToBePresentInElement(header, "Success"));
		confirm_menu.clickOnOkButtonAfterAddCloseCaptioning();
			
		}catch(Exception e){
			e.getMessage();
			ATUReports.add(e.getMessage(), "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
	}
	


	// Change first chapter recording name
	public void changeFirstChapterRecordingNameToTargetName(String target_name) throws InterruptedException {
		
		ConfirmationMenu confirm_menu = PageFactory.initElements(driver, ConfirmationMenu.class);
		RecordingHelperPage record = PageFactory.initElements(driver, RecordingHelperPage.class);
		try{
			
		for(int i=0; i<10; i++) {
			try {
				driver.switchTo().frame(0);
				break;
			} catch (Exception e) {
				Thread.sleep(1000);
			}
		}
		
		
		int index = 0; 
		while(!isElementPresent(By.id("PlayButton_Img")) && index< 20) {
			index++;
				System.out.println("element is not visable");
				Thread.sleep(2000);	
		}
			
		System.out.println("Wait that the element edit chapter will click from the list.");
		ATUReports.add(time +" Wait that the element edit chapter will click from the list.", "True.", "True.", LogAs.PASSED, null);
		
		for(String window_handler: driver.getWindowHandles()) {
			driver.switchTo().window(window_handler);
			break;
		}
		
		record.waitForVisibility(listButtons.get(1));
		clickElement(listButtons.get(1));
		clickElement(listButtons.get(1));
		
		System.out.println("The element edit chapter was clicked");
		ATUReports.add(time +" The element edit chapter was clicked", "True.", "True.", LogAs.PASSED, null);
		
		Thread.sleep(2000);
		
		ChapterName.clear();
		ChapterName.sendKeys(target_name);
		
		clickElement(ApplyButton);
		Thread.sleep(2000);
		int numberOfAttempts = 0;
		//click on the ok
		while(record.isElementPresent(By.cssSelector("#ModalDialogHeader"))){
			
			WebElement ie = record.getStaleElem(By.cssSelector("#ModalDialogHeader"),driver,5);		
			String message = getTextFromWebElement(ie,5);
			if(message.contains("Success")){
				confirm_menu.clickOnOkButtonAfterEditRecord();
				break;
			} else if(message.contains("Error")) {
				ATUReports.add(time +" Get an error while click on apply.", LogAs.WARNING, null);
				confirm_menu.clickOnOkButtonAfterErrorClickOnTheApply();	
				System.out.println("Get an error while click on apply.");	
				clickElement(ApplyButton);
				numberOfAttempts++;
				if(numberOfAttempts == 5) {
					ATUReports.add(time +" Get an error while click on apply.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
					Assert.assertTrue(false);
				}
			}
			else Thread.sleep(3000);
		}
		
		//return to the course
		clickElement(Breadcrumbs);
		}catch(Exception e){
			e.getMessage();
			ATUReports.add(e.getMessage(), "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
	
	}
	
	// set target keyword for first chapter 
	public void setTargetKeywordForFirstChapter(String target_keyword) throws InterruptedException {
			
		ConfirmationMenu confirm_menu = PageFactory.initElements(driver, ConfirmationMenu.class);
		RecordingHelperPage record = PageFactory.initElements(driver, RecordingHelperPage.class);
		
		try{
		for(int i=0; i<10; i++) {
			try {
				driver.switchTo().frame(0);
				break;
			} catch (Exception e) {
				Thread.sleep(1000);
			}
		}
		
		int index = 0; 
		while(!isElementPresent(By.id("PlayButton_Img")) && index< 20) {
			index++;
				System.out.println("element is not visable");
				Thread.sleep(1500);	
		}
		
		System.out.println("Wait the the element edit chapter will click from the list.");
		ATUReports.add(time +" Wait the the element edit chapter will click from the list.", "True.", "True.", LogAs.PASSED, null);

		for(String window_handler: driver.getWindowHandles()) {
			driver.switchTo().window(window_handler);
			break;
		}
		
		record.waitForVisibility(listButtons.get(1));
		clickElement(listButtons.get(1));
		clickElement(listButtons.get(1));
		Thread.sleep(2000);
		
		System.out.println("The element edit chapter was Clicked");
		ATUReports.add(time +" The element edit chapter was Clicked.", "True.", "True.", LogAs.PASSED, null);
		
		NewKeywordButton.clear();
		NewKeywordButton.sendKeys(target_keyword);
		
		clickElement(ApplyButton);
		Thread.sleep(2000);
		int numberOfAttempts = 0;
		//click on the ok
		while(record.isElementPresent(By.cssSelector("#ModalDialogHeader"))){	
			WebElement ie = record.getStaleElem(By.cssSelector("#ModalDialogHeader"),driver,5);		
			String message = getTextFromWebElement(ie,5);
			if(message.contains("Success")){
				confirm_menu.clickOnOkButtonAfterEditRecord();
				break;
			} else if(message.contains("Error")) {
				ATUReports.add(time +" Get an error while click on apply.", LogAs.WARNING, null);
				confirm_menu.clickOnOkButtonAfterErrorClickOnTheApply();	
				System.out.println("Get an error while click on apply.");	
				clickElement(ApplyButton);
				if(numberOfAttempts == 5) {
					ATUReports.add(time +" Get an error while click on apply.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
					Assert.assertTrue(false);
				}
			}
			else Thread.sleep(3000);
		}
		
		//return to the course
		clickElement(Breadcrumbs);	
		
	    System.out.println("Wait the the element edit chapter will click from the list.");
		ATUReports.add(time +" Wait the the element edit chapter will click from the list.", "True.", "True.", LogAs.PASSED, null);
		}catch(Exception e){
			e.getMessage();
			ATUReports.add(e.getMessage(), "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
	}
	}
}
