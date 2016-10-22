package com.automation.main.page_helpers;

import java.util.List;

import org.omg.Messaging.SyncScopeHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;import com.automation.main.page_helpers.Page;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
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
public class BottomFooter extends Page {
	public BottomFooter(WebDriver browser) {
		super(browser);
		// TODO Auto-generated constructor stub
	}
	
	@FindBy(id = "TegrityLogo")WebElement tegrity_logo;


	// Verify that the tegrity logo is displayed on the bottom footer bar in the left side
	public void verifyThatTheTegrityLogoDisplayedOnBottomLeftSide() {
		WebElement Institute_logo = driver.findElement(By.id("InstituteLogotype"));
		WebElement search_box = driver.findElement(By.id("tegritySearchBox"));
		
		if((Institute_logo.getLocation().y < tegrity_logo.getLocation().y) &&
				(tegrity_logo.getLocation().x < search_box.getLocation().x)) {
			System.out.println("Verifed that the Tegrity logo is displayed on botton in the left side.");
			ATUReports.add("Verifed that the Tegrity logo is displayed on botton in the left side.", "True.", "True.", LogAs.PASSED, null);
		} else {
			System.out.println("Not verifed that the Tegrity logo is displayed on botton in the left side.");
			ATUReports.add("Verifed that the Tegrity logo is displayed on botton in the left side.", "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
	}
	
	
	
	

}
