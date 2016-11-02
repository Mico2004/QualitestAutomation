package com.automation.main;

import org.openqa.selenium.WebDriver;import com.automation.main.page_helpers.Page;import com.automation.main.page_helpers.Page;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AddAdditionalContentLinkWindow extends Page{

	public AddAdditionalContentLinkWindow(WebDriver browser) {
		super(browser);
		// TODO Auto-generated constructor stub
	}
	@FindBy(id = "TitleField")
	WebElement additional_content_link_title_input;
	@FindBy(id = "UrlField")
	WebElement additional_content_link_url_input;
	@FindBy(id = "ModalDialogHeader")
	WebElement additional_content_link_title;
	@FindBy(id = "InfoText")
	WebElement additional_content_link_info;
	@FindBy(id = "AddLinkButton")
	WebElement add_additional_link_button;
	@FindBy(xpath = "//*[@id=\"addFileWindow\"]/form/div[1]/div[4]")
	WebElement upload_progress_bar;
	@FindBy(id ="CancelButton")
	WebElement cancel_additional_file_button;
	
	
//fill title input
public void fillTitle(String title)
{
	additional_content_link_title_input.sendKeys(title);
}
//fill url input
public void fillUrl(String url)
{
	additional_content_link_url_input.sendKeys(url);
}
///create new additional content link by title and url
	public void createNewAdditionalContentLink(ConfirmationMenu confirm, String title,String url) throws InterruptedException
{
fillTitle(title);
fillUrl(url);
add_additional_link_button.click();
waitForVisibility(confirm.ok_button);
confirm.clickOnOkButtonAfterConfirmAddAdditionalContentLink();
}
}
