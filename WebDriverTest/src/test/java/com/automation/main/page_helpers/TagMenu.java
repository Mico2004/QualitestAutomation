package com.automation.main.page_helpers;

import java.util.ArrayList;
import java.util.List;

import org.omg.Messaging.SyncScopeHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
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
	@FindBy(id = "ModalDialogHeaderWrap") public WebElement tag_window_title_background;
	@FindBy(css = ".emphasis") WebElement tag_window_text;
	@FindBy(xpath = ".//*[@id='tagRecordingWindow']/form/div[1]/table/tbody/tr/th[2]") WebElement nameLabel;
	@FindBy(xpath = ".//*[@id='tagRecordingWindow']/form/div[1]/table/tbody/tr/th[3]") WebElement privateLabel;
	@FindBy(xpath = ".//*[@id='tagRecordingWindow']/form/div[1]/table/tbody/tr/th[4]") WebElement editLabel;
	@FindBy(xpath =".//*[@id='tagRecordingWindow']/form/div[1]/div[1]/table/tbody/tr[1]/td[1]/input") public WebElement first_tag_checkbox;
	@FindBy(xpath =".//*[@id='tagRecordingWindow']/form/div[1]/div[1]/table/tbody/tr[1]/td[3]/input")public WebElement first_private_checkbox;
	@FindBy(xpath =".//*[@id='tagRecordingWindow']/form/div[1]/div[1]/table/tbody/tr[1]/td[2]") public WebElement first_name;
	@FindBy(xpath =".//*[@id='tagRecordingWindow']/form/div[1]/div[1]/table/tbody") public WebElement tableOfTags;
	@FindBy(xpath =".//*[@id='tagRecordingWindow']/form/div[1]/div[2]/div[1]")public WebElement edit_background;
	@FindBy(xpath =".//*[@id='tagRecordingWindow']/form/div[1]/div[2]/div[1]/span") public WebElement edit_title;
	@FindBy(xpath =".//*[@id='tagRecordingWindow']/form/div[1]/div[2]/div[2]/div/label")public WebElement private_edit_label;
	@FindBy(xpath =".//*[@id='tagRecordingWindow']/form/div[1]/div[2]/div[3]/button[1]")public WebElement cancel_edit_button;
	@FindBy(xpath =".//*[@id='tagRecordingWindow']/form/div[1]/div[2]/div[3]/button[2]")public WebElement submit_edit_button;
	@FindBy(xpath =".//*[@id='tagRecordingWindow']/form/div[1]/div[2]/div[3]/button[3]")public WebElement delete_button;
	
	@FindBy(id = "CreateButton") public WebElement create_new_tag_button;
	@FindBy(id = "CancelButton") public WebElement cancel_button;
	@FindBy(id = "ApplyButton") public WebElement apply_button;
	@FindBy(id = "CheckAll") public List<WebElement> header_checkbox;
	@FindBy(id = "isPrivate") public WebElement privte_checkbox;
	@FindBy(id = "tagName")public WebElement edit_new_tag_input;
	@FindBy(css = ".btnWrap>.btn.btn-primary") List<WebElement> delete_submit_tags_list;
	@FindBy(css = ".deleteTag.tagIcon")public List<WebElement> delete_tag_button_list;
	@FindBy(css = ".editTag.tagIcon")public List<WebElement> edit_tag_button_list;
	public final List<String> instructors = new ArrayList<String>();
	public final String EDIT_GREY = "#5d5d5d";
	public final String TABLE_BLACK = "#333333";
	public final String TABLE_GREY = "#d3d3d3";
	public final String TABLE_WHITE = "#ffffff";
	
	// This function create new tag
	public void createNewTag(String new_tag) throws InterruptedException {
		clickElementJS(create_new_tag_button);
		sendStringwithAction(edit_new_tag_input, new_tag);		
		clickElementWithOutIdJS(submit_edit_button);
		Thread.sleep(1000);
	//	for(WebElement we: delete_submit_tags_list) {
	//		if(we.getText().equals("Submit")) {
	//			clickElement(we);
	//			break;
	//		}
	//	}
		System.out.println("Created tag: " + new_tag);
		ATUReports.add("Created tag.", new_tag, new_tag, LogAs.PASSED, null);
	}
	
	// This function delete all tags if there any exist
	public void deleteAllExistingTags() throws InterruptedException {
		while(delete_tag_button_list.size()>0) {
			clickElement(delete_tag_button_list.get(0));
			clickElementWithOutIdJS(delete_button);
//			for(WebElement we: delete_submit_tags_list) {
//				if(we.getText().equals("Delete")) {
//					clickElement(we);
//					break;
//				}
//			}
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
	
	// This function verify that tag window is close
		public void verifyTagWindowClose() throws InterruptedException {
			
			Thread.sleep(500);
			
			boolean is_closed = isTagWindowClosed();			
			if(is_closed) {
				System.out.println("Tag window is close.");
				ATUReports.add("Tag window.", "Close.", "Close.", LogAs.PASSED, null);
				Assert.assertTrue(true);
			} else {
				System.out.println("Tag window is open.");
				ATUReports.add("Tag window.", "Close.", "Open.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Assert.assertTrue(false);
			}
		}
	
	// This function verify that tag window is open
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
		
	// This function verify that tag window is open
	public void verifyTagEditWindowOpen() {
		
		if(edit_title.isDisplayed()) {
			System.out.println("Tag Edit window is open.");
			ATUReports.add("Tag window.", "Open.", "Open.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Tag Edit window is close.");
			ATUReports.add("Tag window.", "Open.", "Close.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}
	
	// This function verify that tag window is open
	public void verifyTagDeleteWindowOpen() {
		
		if(edit_title.isDisplayed()) {
			System.out.println("Tag delete window is open.");
			ATUReports.add("Tag delete window.", "Open.", "Open.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Tag delete window is close.");
			ATUReports.add("Tagdelete window.", "Open.", "Close.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
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
			wait.until(ExpectedConditions.visibilityOf(cancel_button));
			wait.until(ExpectedConditions.visibilityOf(apply_button));
			
			}catch(Exception e){
				e.getMessage();
				ATUReports.add(e.getMessage(), "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			}
		
	}
	
	public void waitForEditPageToLoad() {
		
		try{
			wait.until(ExpectedConditions.visibilityOf(edit_background));
			wait.until(ExpectedConditions.visibilityOf(edit_title));
			wait.until(ExpectedConditions.visibilityOf(submit_edit_button));
			wait.until(ExpectedConditions.visibilityOf(cancel_edit_button));
			wait.until(ExpectedConditions.visibilityOf(edit_new_tag_input));
		
			}catch(Exception e){
				e.getMessage();
				ATUReports.add(e.getMessage(), "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			}
		
	}
	
	
	// verify Publish menu background color is same as recording background color
	public void verifyTagColor(RecordingHelperPage rec, WebElement background) throws InterruptedException {
				
				try{
				String background_rec = rec.getBackGroundColor(rec.background);
				String menu_background = getBackGroundColor(background);
				if (background_rec.equals(menu_background)) {
					ATUReports.add("Tag menu background color is same as recording background color", LogAs.PASSED, null);
					System.out.println("Tag menu background color is same as recording background color");				
				} else {
					ATUReports.add("Tag menu background color is not same as recording background color", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
					System.out.println("Tag menu background color is not same as recording background color");
				}
				}catch(Exception e){
					e.getMessage();
					ATUReports.add(e.getMessage(), "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));

					}
			}

	
	
	public String getBackGroundImageColor(WebElement element)// get background by an
	// element
	{
		String text = "";
		try {
			text = element.getCssValue("background-image").toString();
		} catch (Exception msg) {
			return "";
		}
		try {
			// Split css value of rgb
			String[] splited_structure_displayed = text.split("0%");
			String splited_third_structure_displayed = splited_structure_displayed[1].substring(2, 18);
			String[] numbers = splited_third_structure_displayed.replace("rgb(", "").replace(")", "").split(",");
			int number1 = Integer.parseInt(numbers[0]);
			numbers[1] = numbers[1].trim();
			int number2 = Integer.parseInt(numbers[1]);	
			numbers[2] = numbers[2].trim();
			int number3 = Integer.parseInt(numbers[2]);
			return String.format("#%02x%02x%02x", number1, number2, number3);
		} catch (Exception msg) {
			return text;
		}

	}
	
	
	public void validateTheTagsListRowsIsSortedByTagName() {
		
		List<String> nameOfTags = new ArrayList<String>();
	    List<WebElement> rows = tableOfTags.findElements(By.tagName("tr"));
	    int rowNumber = rows.size();
	    
	    for(int i = 0 ; i< rowNumber ; i++) {
	    	WebElement currentRaw = rows.get(i);
	    	List<WebElement> cols = currentRaw.findElements(By.tagName("td"));
	    	String currentName = cols.get(1).getText();
	    	nameOfTags.add(currentName); 
	    }    
    	for(int i=0 ; i< rowNumber - 1 ;i++){
    		if(nameOfTags.get(i).compareTo(nameOfTags.get(i+1)) > 0 ) {
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
		
		List<WebElement> rows = tableOfTags.findElements(By.tagName("tr"));		
		int rowNumber = rows.size();	    
	    for(int i = 0 ; i< rowNumber ; i++) {
	    	WebElement currentRaw = rows.get(i);
	    	List<WebElement> cols = currentRaw.findElements(By.tagName("td"));
	    	WebElement currentCols = cols.get(0);
	    	WebElement currentCheckbox = currentCols.findElement(By.tagName("input"));
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
			List<WebElement> rows = tableOfTags.findElements(By.tagName("tr"));		
			int rowNumber = rows.size();	    
		    for(int i = 0 ; i< rowNumber ; i++) {
		    	WebElement currentRaw = rows.get(i);
		    	List<WebElement> cols = currentRaw.findElements(By.tagName("td"));
		    	WebElement currentCols = cols.get(0);
		    	WebElement currentCheckbox = currentCols.findElement(By.tagName("input"));
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
			List<WebElement> rows = tableOfTags.findElements(By.tagName("tr"));		
			int rowNumber = rows.size();	    
		    for(int i = 0 ; i< rowNumber ; i++) {
		    	WebElement currentRaw = rows.get(i);
		    	List<WebElement> cols = currentRaw.findElements(By.tagName("td"));
		    	WebElement currentCols = cols.get(0);
		    	WebElement currentCheckbox = currentCols.findElement(By.tagName("input"));
		    	clickElementWithOutIdJS(currentCheckbox);
			}
		}catch(Exception e){
			e.printStackTrace();
			ATUReports.add(e.getMessage(), "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			}		
	}

	public void verifyTheHeaderCheckboxIsChecked() {
		try{
			if(header_checkbox.get(1).isSelected()){
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
			if(!header_checkbox.get(1).isSelected()){
				 ATUReports.add("Verify header checkbox is unchecked.","Success.", "Success.", LogAs.PASSED, null);
				System.out.println("Verify header checkbox is unchecked.");
			}
			else{
				ATUReports.add("Not Verify header checkbox is unchecked.","Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
    			System.out.println("Not Verify header checkbox is unchecked.");
			}
		
		}catch(Exception e){
			e.printStackTrace();
			ATUReports.add(e.getMessage(), "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			}		
		
	}

	public void verifyTheTableColor(String choosenColor) {
		String text;
		try {                              
			if(choosenColor.equals("Edit_Grey")){
				text = getBackGroundColor(edit_background);
			}
			else text = getBackGroundColor(first_name);
			boolean ifThisColorOk = false;
			switch(choosenColor){
			case "White":
				if(text.equals(TABLE_WHITE)){
					ifThisColorOk = true;
				}
				break;
			case "Grey":
				if(text.equals(TABLE_GREY)){
					ifThisColorOk = true;
				}
				break;
			case "Black":
				if(text.equals(TABLE_BLACK)){
					ifThisColorOk = true;
				}
				break;
			case "Edit_Grey":			
				if(text.equals(EDIT_GREY)){
					ifThisColorOk = true;
				}
				break;
					
			}
		if(ifThisColorOk){
			 ATUReports.add("Verify that the color: " +choosenColor +" is the background color." ,"Success.", "Success.", LogAs.PASSED, null);
				System.out.println("Verify that the color: " +choosenColor +" is the background color.");
			}
			else{
				ATUReports.add("Not Verify that the color: " +choosenColor +" is the background color.","Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
 			System.out.println("Not Verify that the color: " +choosenColor +" is the background color.");
			}
		
		}catch(Exception e){
			e.printStackTrace();
			ATUReports.add(e.getMessage(), "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			}		
		
	}

	public void verifyEditTextboxEditableAndContainsTheTagName(String name) {
		
		try {
			if(edit_new_tag_input.getAttribute("type").equals("text")){
				 ATUReports.add("Verify edit textbox is editable.","Success.", "Success.", LogAs.PASSED, null);
				 System.out.println("Verify edit textbox is editable.");
				}
				else{
					ATUReports.add("Not Verify edit textbox is editable.","Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
	    			System.out.println("Not Verify edit textbox is editable.");
				}
				
			if(edit_new_tag_input.getAttribute("value").equals(name)){
				 ATUReports.add("Verify edit textbox is Contains the tag name.","Success.", "Success.", LogAs.PASSED, null);
					System.out.println("Verify edit textbox is Contains the tag name.");
				}
				else{
					ATUReports.add("Not Verify edit textbox is Contains the tag name.","Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
	    			System.out.println("Not Verify edit textbox is Contains the tag name.");
				}
			
		
		}catch(Exception e){
			e.printStackTrace();
			ATUReports.add(e.getMessage(), "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}		
		
	}

	public String getFirstTagName() {
		try {
			wait.until(ExpectedConditions.visibilityOf(first_name));
			return first_name.getText();
		}catch(Exception e){
			e.printStackTrace();
			ATUReports.add(e.getMessage(), "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
		return pageTitle;		
	}

	public void saveAllTheInstractors() {
		
		instructors.clear();
		List<WebElement> rows = tableOfTags.findElements(By.tagName("tr"));		
		int rowNumber = rows.size();	    
	    for(int i = 0 ; i< rowNumber ; i++) {
	    	WebElement currentRaw = rows.get(i);
	    	List<WebElement> cols = currentRaw.findElements(By.tagName("td"));
	    	String currentName = cols.get(1).getText();
	    	instructors.add(currentName);
		} 	
	  
	}

	public void verifyCreatedNameIsDisplayedInTheEditTagList(String name){
		waitForVisibility(tableOfTags);
		List<WebElement> rows = tableOfTags.findElements(By.tagName("tr"));		
		int rowNumber = rows.size();	    
	    for(int i = 0 ; i< rowNumber ; i++) {
	    	WebElement currentRaw = rows.get(i);
	    	List<WebElement> cols = currentRaw.findElements(By.tagName("td"));
	    	String currentName = cols.get(1).getText();
	    	if(currentName.equals(name)){
	    		ATUReports.add("The name: " +name +" found on the list." ,"Success.", "Success.", LogAs.PASSED, null);
    			System.out.println("The name: " +name +" found on the list.");
    			return;
	    	}	
	    }
	    ATUReports.add("The name: " +name +" isn't found on the list.","Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		System.out.println("The name: " +name +" isn't found on the list.");
		} 	
		
	public void verifyAllInstractorAreDisplay() {
		try{
			List<WebElement> rows = tableOfTags.findElements(By.tagName("tr"));		
			List<String> currInst = new ArrayList<>();
			int rowNumber = rows.size();	    
		    for(int i = 0 ; i< rowNumber ; i++) {
		    	WebElement currentRaw = rows.get(i);
		    	List<WebElement> cols = currentRaw.findElements(By.tagName("td"));
		    	String currentName = cols.get(1).getText();
		    	currInst.add(currentName);
			}    
		    for(String instructor : instructors){
		    	if(!searchInstractorInTheInstractorList(instructor,currInst)){
		    		ATUReports.add("The instructor:" +instructor +" isn't found on the list." ,"Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
	    			System.out.println("The instructor:" +instructor +" isn't found on the list.");
	    			return;
		    	}
		    }
		    ATUReports.add("all the instuctors are found on the list.","Success.", "Success.", LogAs.PASSED, null);
			System.out.println("all the instuctors are found on the list.");
		    	    
		}catch(Exception e){
			e.printStackTrace();
			ATUReports.add(e.getMessage(), "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			}		
	}
	
	public boolean searchInstractorInTheInstractorList(String inst,List<String> currentInstructors) {

		boolean isTheInstractorsContains = false;
		for(String instructor: currentInstructors){
			if(inst.equals(instructor)){
				isTheInstractorsContains = true;
				break;					
			}
		}
		return isTheInstractorsContains;
	}

	public void verifyCheckboxInUndefinedState() {
		try{
		String className = header_checkbox.get(1).getAttribute("class");
		if(className.equals("select-all-cb ng-isolate-scope ng-pristine ng-valid")){
			 ATUReports.add("Verify checkbox in undefined state.","Success.", "Success.", LogAs.PASSED, null);
			 System.out.println("Verify checkbox in undefined state.");
			}
			else{
				ATUReports.add("Not Verify checkbox in undefined state.","Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
    			System.out.println("Not Verify checkbox in undefined state.");
			}
		}catch(Exception e){
			e.printStackTrace();
			ATUReports.add(e.getMessage(), "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}	
	}
	
}
