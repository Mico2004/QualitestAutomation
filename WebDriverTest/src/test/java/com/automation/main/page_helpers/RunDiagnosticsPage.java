package com.automation.main.page_helpers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;
import junitx.util.PropertyManager;

public class RunDiagnosticsPage extends Page {
public RunDiagnosticsPage(WebDriver browser) {
		super(browser);
		// TODO Auto-generated constructor stub
	}


@FindBy(id="PictureMain") WebElement picture;

	
	///verify run diagnostics page
	public void verifyRunDiagnosticsPage(String university)
	{    
		verifyHelpPageUrl(driver,university);
		if(picture.isDisplayed())
		{
			
			System.out.println("run diagnostics page verified");
			ATUReports.add(time +" run diagnostics page verified", LogAs.PASSED, null);
			Assert.assertTrue(true);
		}
		else
		{
			System.out.println("run diagnostics page not  verified");
			ATUReports.add(time +" run diagnostics page not verified", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}
	///verify run diagnostics url
	public void verifyHelpPageUrl(WebDriver driver,String university)
	{
	   
	    
		String url=university+"/#/diagnostic";
		if(url.contains(driver.getCurrentUrl()))
		{
			System.out.println("run diagnostics url verified");
			ATUReports.add(time +" run diagnostics url verified", LogAs.PASSED, null);
			Assert.assertTrue(true);
		}
		else
		{
			System.out.println("run diagnostics page  url not  verified");
			ATUReports.add(time +" run diagnostics page url not verified", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}
}
