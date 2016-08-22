package com.automation.main;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
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

	
	

	// Change first chapter recording name
	public void changeFirstChapterRecordingNameToTargetName(String target_name) throws InterruptedException {
		for(int i=0; i<10; i++) {
			try {
				driver.switchTo().frame(0);
				break;
			} catch (Exception e) {
				Thread.sleep(1000);
			}
		}
		
		for(int i=0; i<20; i++) {
			try {
				if(driver.findElement(By.id("PlayButton_Img")).isDisplayed()) {
					System.out.println("2222");
					break;
				} else {
					Thread.sleep(1000);
				}
			} catch (Exception e) {
				Thread.sleep(1000);
			}
				
		}
		
		for(String window_handler: driver.getWindowHandles()) {
			driver.switchTo().window(window_handler);
			break;
		}
		
		driver.findElements(By.cssSelector(".optionList>li>a")).get(1).click();
		Thread.sleep(1000);
		
		driver.findElement(By.id("NewTitle")).clear();
		driver.findElement(By.id("NewTitle")).sendKeys(target_name);
		
		driver.findElement(By.cssSelector(".btn.btn-primary.btnApply")).click();
		Thread.sleep(2000);
		
		for(int i=0; i<60; i++) {
			try {
				if(driver.findElement(By.id("PlayButton_Img")).isDisplayed()) {
					System.out.println("2222");
					break;
				} else {
					Thread.sleep(1000);
				}
			} catch (Exception e) {
				Thread.sleep(1000);
			}
				
		}
		
		for(String window_handler: driver.getWindowHandles()) {
			driver.switchTo().window(window_handler);
			break;
		}
		

		driver.findElement(By.cssSelector(".btn.btn-default")).click();
		Thread.sleep(2000);
	
	}
	
	// set target keyword for first chapter 
	public void setTargetKeywordForFirstChapter(String target_keyword) throws InterruptedException {
		for(int i=0; i<10; i++) {
			try {
				driver.switchTo().frame(0);
				break;
			} catch (Exception e) {
				Thread.sleep(1000);
			}
		}
		
		for(int i=0; i<20; i++) {
			try {
				if(driver.findElement(By.id("PlayButton_Img")).isDisplayed()) {
					System.out.println("2222");
					break;
				} else {
					Thread.sleep(1000);
				}
			} catch (Exception e) {
				Thread.sleep(1000);
			}
				
		}
		
		for(String window_handler: driver.getWindowHandles()) {
			driver.switchTo().window(window_handler);
			break;
		}
		
		driver.findElements(By.cssSelector(".optionList>li>a")).get(1).click();
		Thread.sleep(1000);
		
		driver.findElement(By.id("NewKeyword")).clear();
		driver.findElement(By.id("NewKeyword")).sendKeys(target_keyword);
		
		driver.findElement(By.cssSelector(".btn.btn-primary.btnApply")).click();
		Thread.sleep(2000);
		
		for(int i=0; i<60; i++) {
			try {
				if(driver.findElement(By.id("PlayButton_Img")).isDisplayed()) {
					System.out.println("2222");
					break;
				} else {
					Thread.sleep(1000);
				}
			} catch (Exception e) {
				Thread.sleep(1000);
			}
				
		}
		
		for(String window_handler: driver.getWindowHandles()) {
			driver.switchTo().window(window_handler);
			break;
		}
		

		driver.findElement(By.cssSelector(".btn.btn-default")).click();
		Thread.sleep(2000);
	}
}
