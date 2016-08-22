package com.automation.main;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

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

		@FindBy(xpath="//*[@id=\"main\"]/div/div[5]/div/label/input") WebElement eula_checkbox;
		@FindBy(xpath="//*[@id=\"main\"]/div/div[6]/button[1]") WebElement ok;
		@FindBy(xpath="//*[@id=\"main\"]/div/div[6]/button[2]") WebElement cancel;
		
		///click on eula check box if not selected
		public String clickOnEulaCheckboxAndClickOk(WebDriver driver,ConfirmationMenu confirm)
		{   String eula="-1";
			waitForVisibility(eula_checkbox);
			
			WebElement element;
			try {
			if(!eula_checkbox.isSelected())
			{   
				eula_checkbox.click();
				driver.switchTo().frame(1);
                Thread.sleep(1000);
				element=driver.findElement(By.xpath("/html/body/p"));
				eula=element.getText();
				driver.switchTo().defaultContent();
		    	ok.click();
		    	  
					  confirm.waitForVisibility(confirm.ok_button);
					   confirm.clickonokbuttonafterEulaChangeSetting();
				System.out.println("clicked succefully on eula checkbox");
				ATUReports.add("Click eula checkbox","eula checkbox status", "Success select", "Success select", LogAs.PASSED, null);
			return eula;
			}
			else {
				driver.switchTo().frame(1);
				element=driver.findElement(By.xpath("/html/body/p"));
				 Thread.sleep(1000);
				eula=element.getText();
			
				System.out.println("already clicked on eula checkbox");
				cancel.click();
				ATUReports.add("Click eula checkbox","eula checkbox status", "Success select", "Success select", LogAs.PASSED, null);
			}
			} catch (Exception e) {
				System.out.println("failed clicking on eula checkbox");
				ATUReports.add("Click eula checkbox","eula checkbox status", "Success select", "failed select", LogAs.PASSED, null);
		}
		return eula;

		}
		///uncheck on eula check box if  selected
				public void disableEulaCheckboxAndClickOk(ConfirmationMenu confirm)
				{
					waitForVisibility(eula_checkbox);

				try {
					if(eula_checkbox.isSelected())
					{
					   eula_checkbox.click();
					   ok.click();
					  confirm.waitForVisibility(confirm.ok_button);
					   confirm.clickonokbuttonafterEulaChangeSetting();
					   System.out.println("unclicked succefully on eula checkbox");
						ATUReports.add("unClick eula checkbox","eula checkbox status", "Success select", "Success select", LogAs.PASSED, null);
					}
					else {
						cancel.click();
						System.out.println("already unchecked eula checkbox");
						ATUReports.add("already unchecked eula checkbox","eula checkbox status", "Success select", "Success select", LogAs.PASSED, null);
						
					}
					} catch (Exception e) {
						System.out.println("failed clicking on eula checkbox");
						ATUReports.add("unClick eula checkbox","eula checkbox status", "Success select", "failed select", LogAs.FAILED, null);
				}

				}
}
