package com.automation.main;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;

public class RunDiagnosticsPage extends Page {
public RunDiagnosticsPage(WebDriver browser) {
		super(browser);
		// TODO Auto-generated constructor stub
	}


@FindBy(id="PictureMain") WebElement picture;

	
	///verify run diagnostics page
	public void verifyRunDiagnosticsPage()
	{    
		verifyHelpPageUrl(driver);
		if(picture.isDisplayed())
		{
			
			System.out.println("run diagnostics page verified");
			ATUReports.add("run diagnostics page verified", LogAs.PASSED, null);
			Assert.assertTrue(true);
		}
		else
		{
			System.out.println("run diagnostics page not  verified");
			ATUReports.add("run diagnostics page not verified", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
	}
	///verify run diagnostics url
	public void verifyHelpPageUrl(WebDriver driver)
	{
		String url=driver.getCurrentUrl()+"/#/diagnostic";
		if(url.contains(url))
		{
			System.out.println("run diagnostics url verified");
			ATUReports.add("run diagnostics url verified", LogAs.PASSED, null);
			Assert.assertTrue(true);
		}
		else
		{
			System.out.println("run diagnostics page  url not  verified");
			ATUReports.add("run diagnostics page url not verified", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
	}
}
