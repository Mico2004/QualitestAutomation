package com.automation.main;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class EmailAndConnectionSettingsPage extends Page {

	public EmailAndConnectionSettingsPage(WebDriver browser) {
		super(browser);
		// TODO Auto-generated constructor stub
	}


	@FindBy(id="sending-field") WebElement sending_user;
	@FindBy(id="administrator-field") WebElement admin_email;
	@FindBy(id="helpdesk-field") WebElement helpdesk_email;
	@FindBy(id="connect-field") WebElement connect_checkbox;
	@FindBy(id="ApplyChangesButton") WebElement ok_button;
	@FindBy(id="CancelButton") WebElement cancel_button;
	
	
	///fill email setting page using sending user,admin email,helpdesk email
	public void fillNewEmailSetting(String user,String adm_email,String help_email,ConfirmationMenu confirm)
	{   
		sending_user.clear();
		sending_user.sendKeys(user);
		System.out.println("clicked user name");
		admin_email.clear();
		admin_email.sendKeys(adm_email);
		System.out.println("clicked admin email");
		helpdesk_email.clear();
		helpdesk_email.sendKeys(help_email);
		System.out.println("clicked helpdesk email");
		if(!connect_checkbox.isSelected())
		{ try {
			
		
			connect_checkbox.click();
		System.out.println("clicked checkbox");
		}
		catch(Exception e)
		{
			System.out.println("error clicking checkbox");
		}
		}
		ok_button.click();
		System.out.println("clicked on ok");
		confirm.waitForVisibility(confirm.ok_button);
		try {
			confirm.clickOnOkButtonAfterConfirmEmailSetting();
			System.out.println("confirmation ok");

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("confirmation failed");

			e.printStackTrace();
		}
	}

	
	
	
}
