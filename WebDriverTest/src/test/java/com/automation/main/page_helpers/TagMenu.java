package com.automation.main.page_helpers;

import java.util.ArrayList;
import java.util.List;

import org.omg.Messaging.SyncScopeHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
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
public class TagMenu extends Page {
	public TagMenu(WebDriver browser) {
		super(browser);
		// TODO Auto-generated constructor stub
	}
	@FindBy(id = "ModalDialogHeader") WebElement tag_window_title;
	@FindBy(id = "ModalDialogHeaderWrap") WebElement tag_window_title_background;
	@FindBy(css = ".emphasis") WebElement tag_window_text;
	@FindBy(xpath = ".//*[@id='tagRecordingWindow']/form/div[1]/table/tbody/tr/th[2]") WebElement nameLabel;
	@FindBy(xpath = ".//*[@id='tagRecordingWindow']/form/div[1]/table/tbody/tr/th[3]") WebElement privateLabel;
	@FindBy(xpath = ".//*[@id='tagRecordingWindow']/form/div[1]/table/tbody/tr/th[4]") WebElement editLabel;
	@FindBy(xpath =".//*[@id='tagRecordingWindow']/form/div[1]/div[1]/table/tbody/tr[1]/td[1]/input") public WebElement first_tag_checkbox;
	@FindBy(xpath =".//*[@id='tagRecordingWindow']/form/div[1]/div[1]/table/tbody/tr[1]/td[3]/input")public WebElement first_private_checkbox;
	@FindBy(xpath =".//*[@id='tagRecordingWindow']/form/div[1]/div[1]/table/tbody") public WebElement tableOfTags;
	@FindBy(id = "CreateButton") public WebElement create_new_tag_button;
	@FindBy(id = "CancelButton") public WebElement cancel_button;
	@FindBy(id = "ApplyButton") public WebElement apply_button;
	@FindBy(id = "CheckAll") public WebElement header_checkbox;
	@FindBy(id = "tagName") WebElement edit_new_tag_input;
	@FindBy(css = ".btnWrap>.btn.btn-primary") List<WebElement> delete_submit_tags_list;
	@FindBy(css = ".deleteTag.tagIcon")public List<WebElement> delete_tag_button_list;
	@FindBy(css = ".editTag.tagIcon")public List<WebElement> edit_tag_button_list;
	List<WebElement> rows = tableOfTags.findElements(By.tagName("tr"));
	List<WebElement> cols = tableOfTags.findElements(By.tagName("td"));
	
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
	
	// This function return true if tag window is closed,
	// and false if it is open
	public boolean isTagWindowClosed() {
		try {
			if(tag_window_title.getText().equals("Tag")) {
				return false;
			} else {
				return true;
			}
			
		} catch (org.openqa.selenium.NoSuchElementException msg) {
			return true;
		}
	}
	
	// This function verify that publish window is open
	public void verifyTagWindowOpen() {
		boolean is_closed = isTagWindowClosed();
		
		if(!is_closed) {
			System.out.println("Tag window is open.");
			ATUReports.add("Tag window.", "Open.", "Open.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Tag window is close.");
			ATUReports.add("Tag window.", "Open.", "Close.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}
		
	/**
	 * this function waits for tag window to display
	 */
	public void waitForPageToLoad() {
		
		try{
			wait.until(ExpectedConditions.visibilityOf(tag_window_title));
			wait.until(ExpectedConditions.visibilityOf(tag_window_title_background));
			wait.until(ExpectedConditions.visibilityOf(tag_window_text));
			wait.until(ExpectedConditions.visibilityOf(nameLabel));
			wait.until(ExpectedConditions.visibilityOf(privateLabel));
			wait.until(ExpectedConditions.elementToBeClickable(By.id("CancelButton")));
			wait.until(ExpectedConditions.elementToBeClickable(By.id("ApplyButton")));
			
			}catch(Exception e){
				e.getMessage();
				ATUReports.add(e.getMessage(), "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			}
		
	}
	
	// verify Publish menu background color is same as recording background color
	public void verifyTagColor(RecordingHelperPage rec) throws InterruptedException {
				
				try{
				String background_rec = rec.getBackGroundColor(rec.background);
				String menu_background = getBackGroundColor(tag_window_title_background);
				if (rec.getBackGroundColor(rec.background).equals(getBackGroundColor(tag_window_title_background))) {
					ATUReports.add("Tag menu background color is same as recording background color", LogAs.PASSED, null);
					System.out.println("Tag menu background color is same as recording background color");
					Assert.assertTrue(true);
				} else {
					ATUReports.add("Tag menu background color is not same as recording background color", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
					System.out.println("Tag menu background color is not same as recording background color");
					Assert.assertTrue(false);
				}
				}catch(Exception e){
					e.getMessage();
					ATUReports.add(e.getMessage(), "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));

					}
			}

	
	public void validateTheTagsListRowsIsSortedByTagName() {
		
		List<String> nameOfTags = new ArrayList<String>();
	    List<WebElement> rows = tableOfTags.findElements(By.tagName("tr"));
	    int rowNumber = rows.size();
	    
	    for(int i = 0 ; i< rowNumber ; i++) {
	    	List<WebElement> cols = tableOfTags.findElements(By.tagName("td"));
	    	String currentName = cols.get(2).getText();
	    	nameOfTags.add(currentName); 
	    }    
    	for(int i=0 ; i< rowNumber - 1 ;i++){
    		if(nameOfTags.get(i).compareTo(nameOfTags.get(i+1)) < 0 ) {
    			ATUReports.add("Not Verify that tags list rows sorted by TagName.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
    			System.out.println("Not Verify that tags list rows sorted by TagName.");
    			return;
    		}
    	}
    	ATUReports.add("Verify that tags list rows sorted by TagName.", LogAs.PASSED, null);
		System.out.println("Verify that tags list rows sorted by TagName.");
	}

	public void verifyAllTheTagCheckboxesAreChecked() {
		try{
	    int rowNumber = rows.size();	    
	    for(int i = 0 ; i< rowNumber ; i++) {
	    	WebElement currentCheckbox = cols.get(1);
	    	if(!currentCheckbox.isSelected()){
	    		ATUReports.add("Not Verify all the tag checkboxes are checked.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
    			System.out.println("Not Verify all the tag checkboxes are checked.");
    			return;
	    	}
	    } 
	    ATUReports.add("Verify all the tag checkboxes are checked.", LogAs.PASSED, null);
		System.out.println("Verify all the tag checkboxes are checked.");
	
		}catch(Exception e){
			e.printStackTrace();
			ATUReports.add(e.getMessage(), "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));

			}	
	}

	public void verifyAllTheTagCheckboxesAreUnChecked() {
		try{
		    int rowNumber = rows.size();    
		    for(int i = 0 ; i< rowNumber ; i++) {
		    	WebElement currentCheckbox = cols.get(1);
		    	if(currentCheckbox.isSelected()){
		    		ATUReports.add("Not Verify all the tag checkboxes are unchecked.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
	    			System.out.println("Not Verify all the tag checkboxes are unchecked.");
	    			return;
		    	}
		    } 
		    ATUReports.add("Verify all the tag checkboxes are unchecked.", LogAs.PASSED, null);
			System.out.println("Verify all the tag checkboxes are unchecked.");
		}catch(Exception e){
			e.printStackTrace();
			ATUReports.add(e.getMessage(), "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));

			}			
	}

	public void CheckAllTheTagCheckboxes() {
		
		try{
			int rowNumber = rows.size();    
			for(int i = 0 ; i< rowNumber ; i++) {
				WebElement currentCheckbox = cols.get(1);
				clickElement(currentCheckbox);
			}
		}catch(Exception e){
			e.printStackTrace();
			ATUReports.add(e.getMessage(), "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			}		
	}

	public void verifyTheHeaderCheckboxIsChecked() {
		try{
			if(header_checkbox.isSelected()){
				 ATUReports.add("Verify header checkboxe is checked.","Success.", "Success.", LogAs.PASSED, null);
				System.out.println("Verify header checkboxe is checked.");
			}
			else{
				ATUReports.add("Not Verify header checkboxe is checked.","Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
    			System.out.println("Not Verify header checkboxe is checked.");
			}
		
		}catch(Exception e){
			e.printStackTrace();
			ATUReports.add(e.getMessage(), "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			}		
	}

	public void verifyTheHeaderCheckboxIsUnChecked() {
		
		try{
			if(!header_checkbox.isSelected()){
				 ATUReports.add("Verify header checkboxe is unchecked.","Success.", "Success.", LogAs.PASSED, null);
				System.out.println("Verify header checkboxe is unchecked.");
			}
			else{
				ATUReports.add("Not Verify header checkboxe is unchecked.","Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
    			System.out.println("Not Verify header checkboxe is unchecked.");
			}
		
		}catch(Exception e){
			e.printStackTrace();
			ATUReports.add(e.getMessage(), "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			}		
		
	}
}
