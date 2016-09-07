package com.automation.main;

import java.util.List;

import org.omg.Messaging.SyncScopeHelper;
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
public class TagMenu extends Page {
	public TagMenu(WebDriver browser) {
		super(browser);
		// TODO Auto-generated constructor stub
	}
	
	@FindBy(id = "CreateButton") WebElement create_new_tag_button;
	@FindBy(id = "CancelButton") WebElement cancel_button;
	@FindBy(id = "ApplyButton") WebElement apply_button;
	@FindBy(id = "tagName") WebElement edit_new_tag_input;
	@FindBy(css = ".btnWrap>.btn.btn-primary") List<WebElement> delete_submit_tags_list;
	@FindBy(css = ".deleteTag.tagIcon") List<WebElement> delete_tag_button_list;
	

	// This function create new tag
	public void createNewTag(String new_tag) {
		clickElement(create_new_tag_button);
		sendStringToWebElement(edit_new_tag_input, new_tag);
		
		for(WebElement we: delete_submit_tags_list) {
			if(we.getText().equals("Submit")) {
				clickElement(we);
				break;
			}
		}
		
		System.out.println("Created tag: " + new_tag);
		ATUReports.add("Created tag.", new_tag, new_tag, LogAs.PASSED, null);
	}
	
	// This function delete all tags if there any exist
	public void deleteAllExistingTags() throws InterruptedException {
		while(delete_tag_button_list.size()>0) {
			clickElement(delete_tag_button_list.get(0));
			for(WebElement we: delete_submit_tags_list) {
				if(we.getText().equals("Delete")) {
					clickElement(we);
					break;
				}
			}
			Thread.sleep(1000);
		}
	}
	
	// This function click on apply button
	public void clickOnApplyButton() {
		clickElement(apply_button);
	}
	
	// This function click on edit for select tag
	public void clickOnEditForSelectedTag() {
		clickElement(driver.findElement(By.cssSelector(".editTag.tagIcon")));
	}
	
	// This function change selected tag to target tag
	public void changeSelectedTagToTargetTag(String target_tag) {
		clickOnEditForSelectedTag();
		edit_new_tag_input.clear();
		
		sendStringToWebElement(edit_new_tag_input, target_tag);
		
		for(WebElement we: delete_submit_tags_list) {
			if(we.getText().equals("Submit")) {
				clickElement(we);
				break;
			}
		}
		
		System.out.println("Edited tag: " + target_tag);
		ATUReports.add("Edited tag.", target_tag, target_tag, LogAs.PASSED, null);
		
	}
	

}
