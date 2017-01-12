package com.automation.main.page_helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Listeners;

import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;
import junitx.util.PropertyManager;

@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class EditRecordingPropertiesWindow extends Page {
	public EditRecordingPropertiesWindow(WebDriver browser) {
		super(browser);
		// TODO Auto-generated constructor stub
	}

	@FindBy(id = "EditButton")
	public WebElement save_button;
	@FindBy(id = "CancelButton")
	public WebElement cancel_button;
	@FindBy(id = "recordingPropertiesRecordedBy")
	WebElement owner_button;
	@FindBy(xpath = "//*[@id=\"recordingPropertiesRecordedBy\"]/option")
	public List<WebElement> owner_button_select;
	@FindBy(id = "ModalDialogHeader")
	WebElement edit_recording_properties_title;
	@FindBy(id = "recordingTItle")
	WebElement recording_name;
	public @FindBy(id="recordingTItle")
	WebElement recording_title;
	public @FindBy(id="dateField")
	WebElement date_Field;
	@FindBy(id = "ModalDialogHeaderWrap")
	public WebElement edit_recordings_properties_background;
	@FindBy (xpath =".//*[@id='editRecordingWindow']/form/div[1]/div[1]/label" )
	public WebElement name_label;
	@FindBy(xpath =".//*[@id='editRecordingWindow']/form/div[1]/div[2]/label" )
	public WebElement date_label;
	@FindBy(xpath =".//*[@id='editRecordingWindow']/form/div[1]/div[3]/label" )
	public WebElement owner_label;
	@FindBy(xpath =".//*[@id='editRecordingWindow']/form/div[1]/div[4]/label" )
	public WebElement type_label;
	@FindBy(xpath = "//*[@id=\"RecordingProperties\"]/option")
	public List<WebElement> type_button_select;
	@FindBy(xpath =".//*[@id='editRecordingWindow']/form/div[1]/div[2]/p" )
	public WebElement info_label;
	@FindBy(id = "recordingPropertiesRecordedBy")
	public WebElement owner_select;
	@FindBy(id = "RecordingProperties")
	public WebElement type_select;
	@FindBy(id = "WarningText")
	public WebElement warningText;
	
	public static final String REGULAR_RECORDING = "Regular recording";
	public static final String TEST_RECORDING = "Proctoring recording";
	public static final String STUDENT_RECORDING = "Student recording";
	public final String SAVE_FONT_COLOR = "#ffffff"; 
	public final String CANCEL_FONT_COLOR = "#333333";
	public final String OWNER_BORDER_COLOR = "#000000";
	public final List<String> recording_types = Arrays.asList("Regular recording","Proctoring recording","Student recording");
	public final List<String> owners = new ArrayList<String>();
	
	// change ownership of recording
	public void changeOwner(String name) {
		String val="";
		try {
			waitForVisibility(owner_button);
			clickElementJS(owner_button);
			System.out.println("clicked on owner scroll");
			for (WebElement el : owner_button_select) {
				val = el.getText();
				if (val.contains(name)) {					
					clickElement(el);
					System.out.println("user selected");
					ATUReports.add(time +" Changes Ownership","Instructor: "+val+" Changed ownsership successfully","Changed ownsership successfully", LogAs.PASSED, null);					
					return;
				}
			}			
		} catch (Exception e) {
			System.out.println("clicked on owner scroll failed");
			e.printStackTrace();
			ATUReports.add(time +" Changes Ownership"+ e.getMessage(),"Instructor: "+val+" Changed ownsership successfully","Changed ownsership failed", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
	}
	
	public void verifyLabelExist(WebElement label,String LabelName) {
		try{
		String nameToCompare = label.getText();
		if (nameToCompare.equals(LabelName)) {
			System.out.println("the label: " + LabelName + " equals to " + nameToCompare );
			ATUReports.add(time +" the label: " + LabelName + " equals to " + nameToCompare, "Success.", "Success.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("the label: " + LabelName + " not equals to " + nameToCompare);
			ATUReports.add(time +" the label: " + LabelName + " not equals to " + nameToCompare, "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}	
		}catch(Exception e){
			e.getMessage();
			ATUReports.add(e.getMessage(), "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
	}
	
	public void waitForPageToLoad(){
	
		try{
		wait.until(ExpectedConditions.visibilityOf(edit_recording_properties_title));
		wait.until(ExpectedConditions.visibilityOf(recording_title));
		wait.until(ExpectedConditions.visibilityOf(date_Field));
		wait.until(ExpectedConditions.visibilityOf(owner_select));
		wait.until(ExpectedConditions.visibilityOf(type_select));
		wait.until(ExpectedConditions.elementToBeClickable(By.id("CancelButton")));
		wait.until(ExpectedConditions.elementToBeClickable(By.id("EditButton")));
		
		}catch(Exception e){
			e.getMessage();
			ATUReports.add(e.getMessage(), "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
	}
	
	public void checkTheFirstTypeThatAprearsOnLabelIsCorrect(WebElement Tab){
		
		try{
			
	
		String TabName = Tab.getText();
		String FirstSelctor = new Select(type_select).getFirstSelectedOption().getText();
		boolean IsWeInTheCorrectTypeOption = false;
		
		switch(TabName){
			case "Recordings":
			if(FirstSelctor.equals(REGULAR_RECORDING))	{
				IsWeInTheCorrectTypeOption = true;
			}
			break;
			case "Student Recordings":
				if(FirstSelctor.equals(STUDENT_RECORDING))	{
					IsWeInTheCorrectTypeOption = true;
				}
				break;
			case "Tests":
				if(FirstSelctor.equals(TEST_RECORDING))	{
					IsWeInTheCorrectTypeOption = true;				
				}
			break;
		}
		
		if(IsWeInTheCorrectTypeOption){
			System.out.println("the type: " + FirstSelctor + "is correct to " + TabName);
			ATUReports.add(time +" the type: " + FirstSelctor + "is correct to " + TabName,"Success.", "Success.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("the type: " + FirstSelctor + "is not correct to " + TabName);
			ATUReports.add(time +" the type: " + FirstSelctor + "is not correct to " + TabName,"Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}	
		}catch(Exception e){
			e.getMessage();
			ATUReports.add(e.getMessage(), "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));

			}
	}

	// verify edit properties background color is same as recording background color
	public void verifyEditPropertiesColor(RecordingHelperPage rec) throws InterruptedException {
		
		try{
		Thread.sleep(2000);	
		String background_rec = rec.getBackGroundColor(rec.background);
		String menu_background = getBackGroundColor(edit_recordings_properties_background);
		if (background_rec.equals(menu_background)) {
			ATUReports.add(time +" edit Properties menu background color is same as recording background color","Success.", "Success.", LogAs.PASSED, null);
			System.out.println("edit Properties menu background color is same as recording background color");
			Assert.assertTrue(true);
		} else {
			ATUReports.add(time +" edit Properties menu background color is not  same as recording background color","Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			System.out.println("edit Propertiese menu background color is  not same as recording background color");
			Assert.assertTrue(false);
		}
		}catch(Exception e){
			e.getMessage();
			ATUReports.add(e.getMessage(), "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));

			}
	}
	
	/**
	 * this function verify edit recording properties Title
	 */
	public void verifyEditRecordingPropertiesTitle() throws InterruptedException {
		try{
		Thread.sleep(1000);
		String val = edit_recording_properties_title.getText();
		if (val.equals("Edit Recording Properties")) {
			System.out.println("Edit Recording Properties menu title verified.");
			ATUReports.add(time +" Edit Recording Properties menu title verified.", "Success.", "Success.", LogAs.PASSED, null);
		} else {
			System.out.println("Edit Recording Properties menu title not verified.");
			ATUReports.add(time +" Edit Recording Properties menu title not verified.","Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
		Assert.assertEquals("Edit Recording Properties", val);
		}catch(Exception e){
			e.getMessage();
			ATUReports.add(e.getMessage(), "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));

			}
	}
	
	public void verifyWarningDisplay() throws InterruptedException {

		try {
		Thread.sleep(1000);
		recording_name.clear();	
		if (warningText.isDisplayed()) {
			
			String warning = warningText.getText();	
			if(warning.equals("Please specify the recording name")){
				System.out.println("Verify Warning is Display.");
				ATUReports.add(time +" Verify Warning is Display.", "Success.", "Success.", LogAs.PASSED, null);
			}else {
				System.out.println("Verify Warning is Display but the message is diffrenet from Please specify the recording Name.");
				ATUReports.add(time +" Verify Warning is Display.", "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			}		
		} else {
			System.out.println("Verify Warning is not Display.");
			ATUReports.add(time +" Verify Warning is not Display.","Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
		}catch(Exception e){
			e.getMessage();
			ATUReports.add(e.getMessage(), "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));

		}
	}
	
	public void verifySaveButtonDisable() throws InterruptedException{

		try {		
		if (!save_button.isEnabled()) {
			System.out.println("Verify that the save button is disable.");
			ATUReports.add(time +" Verify that the save button is disable.","Success.", "Success.", LogAs.PASSED, null);
		} else {
			System.out.println("Not Verify that the save button is disable.");
			ATUReports.add(time +" Not Verify that the save button is disable.","Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}	
		String grey_color = "rgba(66, 139, 202, 1)";	
		if (save_button.getCssValue("background-color").equals(grey_color)) {
			System.out.println("Save button background color is grey.");
			ATUReports.add(time +" save button background color is grey.","Success.", "Success.", LogAs.PASSED, null);
		} else {
			ATUReports.add(time +" Save button background color is not grey.","Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			System.out.println("Save button background color is not grey.");
		}
		}catch(Exception e){
			e.getMessage();
			ATUReports.add(e.getMessage(), "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));

		}	
	}
	
	public void verifySaveButtonEnable() throws InterruptedException{

		try {		
		if (save_button.isEnabled()) {
			System.out.println("Verify that the save button is enable.");
			ATUReports.add(time +" Verify that the save button is enable.","Success.", "Success.", LogAs.PASSED, null);
		} else {
			System.out.println("Not Verify that the save button is enable.");
			ATUReports.add(time +" Not Verify that the save button is enable.", "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}	
		String grey_color = "rgba(66, 139, 202, 1)";	
		if (!save_button.getCssValue("background-color").equals(grey_color)) {
			System.out.println("Save button background color is not grey.");
			ATUReports.add(time +" save button background color is not grey.","Success.", "Success.", LogAs.PASSED, null);
		} else {
			ATUReports.add(time +" Save button background color is grey.", "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			System.out.println("Save button background color is grey.");
		}
		}catch(Exception e){
			e.getMessage();
			ATUReports.add(e.getMessage(), "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));

		}	
	}

	public void changeNameOfRecord(RecordingHelperPage record, ConfirmationMenu confirm_menu) throws InterruptedException {	
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyhhmmss"); 
		String new_recording_name = "NewName" + sdf.format(date);	
		record.toEditRecordingPropertiesMenu();
		changeRecordingName(new_recording_name, confirm_menu);	
	}
	
	
	
	public void verifyInfomativeText() {
		
		try {
			String infoText  = info_label.getText();
			
			if (infoText.equals("Note: The creation date is used by the reports. This recording will only appear in reports that include the new date and will no longer appear in reports for the old date.")) {
				System.out.println("Valid Infomative Text.");
				ATUReports.add(time +" Valid Infomative Text.","Success.", "Success.", LogAs.PASSED, null);
			} else {
				System.out.println("Not Valid Infomative Text.");
				ATUReports.add(time +" Not Valid Infomative Text.", "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			}
		} catch (Exception e) {	
			e.printStackTrace();
			ATUReports.add(e.getMessage(), "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
		
	}
	
	public void VerifyTheLocationOfTheSaveAndCancel() {
		
		try{
			
		Point cancel=cancel_button.getLocation();
		Point save= save_button.getLocation();
		Point title = edit_recording_properties_title.getLocation();
		
		if((save.getY() > title.getY()) && (save.getX() > cancel.getX()))
		{
		 System.out.println("The Save button is displayed on the bottom right corner of the model window.");
		 ATUReports.add(time +" The Save button is displayed on the bottom right corner of the model window.","Success.", "Success.", LogAs.PASSED, null);
		}else {
		 System.out.println("The Save button isn't displayed on the bottom right corner of the model window.");
		 ATUReports.add(time +" The Save button isn't displayed on the bottom right corner of the model window.", "Success.", "Fail.", LogAs.FAILED,new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
		if((cancel.getY() > title.getY()) && (cancel.getX() < save.getX())) {
			 System.out.println("The Cancel button is displayed on the left of the Save button.");
			 ATUReports.add(time +" The Cancel button is displayed on the left of the Save button.","Success.", "Success.", LogAs.PASSED, null);
			}else {
			 System.out.println("The Cancel button is not displayed on the left of the Save button.");
			 ATUReports.add(time +" The Cancel button is not displayed on the left of the Save button.", "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			}
		}catch(Exception e){
			e.getMessage();
			ATUReports.add(e.getMessage(), "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));

			}
		}
		
	/**
	 * this function verify that the title location is aligned left
	 */
    public void verifyEditRedordingPropertiesTitleIsAlignrdLeft()
 {
    	try{
    	
    	Point cancel=cancel_button.getLocation();
		Point title=edit_recording_properties_title.getLocation();

		if((title.getX() < cancel.getX())){
		 System.out.println("The location of the edit recording properties is aligned left.");
		 ATUReports.add(time +" The location of the edit recording properties is aligned left.","Success.", "Success.", LogAs.PASSED, null);
		}else {
		 System.out.println("The location of the edit recording properties is not aligned left.");
		 ATUReports.add(time +" The location of the edit recording properties is not aligned left.", "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
		}catch(Exception e){
			e.getMessage();
			ATUReports.add(e.getMessage(), "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			}
	}
 
    public void verifyLabelIsAlignrdLeftTOtheEditBoxOrSelector(WebElement label,WebElement editText){
    	
    try{
    	Point labelLoc= label.getLocation();
		Point editTextLoc= editText.getLocation();
	
		if((labelLoc.getX() < editTextLoc.getX()))
		{
		 System.out.println("The location of the label " + label.getText() + " recording properties is aligned left.");
			ATUReports.add(time +" The location of the label " + label.getText() + " recording properties is aligned left.","Success.", "Success.", LogAs.PASSED, null);
		}else {
		 System.out.println("The location of the label " + label.getText() + " recording properties is not aligned left.");
			ATUReports.add(time +" The location of the label " + label.getText() + " recording properties is not aligned left.", "Success.", "Fail.",LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
    }catch(Exception e){
    	e.getMessage();
    	ATUReports.add(e.getMessage(), "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));

    	}
    }
    
    public void verifyInputEqualsOutSideString(WebElement element ,String OutSideString){
    	try{
    	String id = element.getAttribute("id");
    	String inputElement = (String)((JavascriptExecutor) driver).executeScript("return document.getElementById(\""+id+"\").value;");	
      
    	if(OutSideString.equals(inputElement)){
		 System.out.println("The input Element: " +inputElement + " is equals to the out Side String " + OutSideString);
			ATUReports.add(time +" The input Element: " +inputElement + " is equals to the out Side String " + OutSideString,"Success.", "Success.", LogAs.PASSED, null);
		}else if(OutSideString.length()!=inputElement.length()) {
			StringBuilder sb= new StringBuilder(inputElement);
			sb.insert(3, "0");
			if(sb.toString().equals(OutSideString)){
				 System.out.println("The input Element: " +inputElement + " is equals to the out Side String " + OutSideString);
				 ATUReports.add(time +" The input Element: " +inputElement + " is equals to the out Side String " + OutSideString,"Success.", "Success.", LogAs.PASSED, null);
			}
		}else{
		 System.out.println("The input Element: " +inputElement + " is not equals to the out Side String " + OutSideString);
			ATUReports.add(time +" The input Element: " +inputElement + " is not equals to the out Side String " + OutSideString, "Success.", "Fail.",LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
    }catch(Exception e){
    	e.getMessage();
    	ATUReports.add(e.getMessage(), "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));

    	}
    }
 
    public void verifyThatTheCreatorOwnerIsOnTheOwnerList(String user_name){
    	
    	try{
    	String user = PropertyManager.getProperty(user_name);
    	boolean isCreationUserAppreadOnTheList = false;
    	for(WebElement e:owner_button_select) {
    		String instractorName = e.getText();
    		if(instractorName.contains(user)){
    			isCreationUserAppreadOnTheList = true;
    			break;
    		}
    	}
    	
      	if(isCreationUserAppreadOnTheList){
   		    System.out.println("The user: " + user + " is appread on the list");
   			ATUReports.add(time +" The user: " + user + " is appread on the list","Success.", "Success.", LogAs.PASSED, null);
   		}else {
   		    System.out.println("The user: " + user + " is not appread on the list");
   			ATUReports.add(time +" The user: " + user + " is not appread on the list", "Success.", "Fail.",LogAs.FAILED,new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
   		}
    }catch(Exception e){
    	e.getMessage();
    	ATUReports.add(e.getMessage(), "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));

    	}
    		
    }
	
	// This function return true if edit recording properties menu is closed,
	// and false if it is open
	public boolean isEditRecordingProperiesClosed() {
		try {
			edit_recording_properties_title.isDisplayed();
			return false;
		} catch (org.openqa.selenium.NoSuchElementException msg) {
			return true;
		}
	}
	
	public void waitUntilEditRecordingProperiesClosed() {
		try {
			new WebDriverWait(driver, 120)
			.until(ExpectedConditions.invisibilityOfElementLocated(By.id("ModalDialogHeader")));		
			ATUReports.add(time +" Verifying edit window is closed", "edit window is closed","edit window is closed",LogAs.PASSED,null);
		} catch (Exception msg) {
			ATUReports.add(time +" Verifying edit window is closed", "edit window isn't closed","edit window isn't closed",LogAs.WARNING,null);
		}
	}

	// This function get recording name and change it to this recording name in
	// edit recording properties
	public void changeRecordingNameToTargetName(String target_name) throws InterruptedException {
		try {
			recording_name.clear();
			recording_name.sendKeys(target_name);
			Thread.sleep(3000);
			System.out.println("Recording name changed to: " + target_name);
			ATUReports.add(time +" Recording name changed.", "Change to: " + target_name,"Changed to " + recording_name + target_name, LogAs.PASSED, null);
			Assert.assertTrue(true);	
		} catch (Exception msg) {
			msg.printStackTrace();
			System.out.println("Fail to change recording name.");
			ATUReports.add(time +" Recording name changed.", "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}

	// This function clicks on Save button
	public void clickOnSaveButton() {
		try {
			wait.until(ExpectedConditions.visibilityOf(save_button));			
		    ((JavascriptExecutor) driver).executeScript("document.getElementById(\""+save_button.getAttribute("id")+"\").click();");
			System.out.println("Clicked on save button.");
			ATUReports.add(time +" Clicked on save button.", "Success.", "Success.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} catch (Exception msg) {
			System.out.println("Fail to click on save button.");
			ATUReports.add(time +" Clicked on save button.", "Success.", "Fail."+msg.getMessage(), LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}

	public void clickOnCancelButton() {
		try {
			wait.until(ExpectedConditions.visibilityOf(cancel_button));			
		    ((JavascriptExecutor) driver).executeScript("document.getElementById(\""+cancel_button.getAttribute("id")+"\").click();");
			System.out.println("Clicked on cancel button.");
			ATUReports.add(time +" Clicked on cancel button.", "Success.", "Success.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} catch (Exception msg) {
			System.out.println("Fail to click on cancel button.");
			ATUReports.add(time +" Clicked on cancel button.", "Success.", "Fail."+msg.getMessage(), LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}

	// change title name of recording
	public void changeRecordingName(String name, ConfirmationMenu confirm) {
		try {
			recording_title.click();
			System.out.println("clicked on recording title input");
			Thread.sleep(1000);
			recording_title.clear();
			recording_title.sendKeys(name);
			System.out.println("enetered new name");
			save_button.click();
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("recordingTItle")));
			System.out.println("save succeded");
			Thread.sleep(2000);
			confirm.clickOnOkButtonAfterConfirmEditRecordingProperties();
		} catch (Exception e) {
			System.out.println("clicked on recording title input failed");

		}
	}
	
	public String getRecordName(ConfirmationMenu confirm) {
		String recordName = null;
	try {
		recordName = recording_title.getAttribute("value");
		cancel_button.click();
		System.out.println("cancel succeded");
	} catch (Exception e) {
		System.out.println("clicked on recording title input failed");
		e.getMessage();
		ATUReports.add(e.getMessage(), "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));

	}
		return recordName;
	}
	
	public void ChooseDiffrenetType(String type) {
		
		try {
			for(WebElement ie: type_button_select) {
				String currentType = ie.getText();	
				if(currentType.equals(type)){
					clickElement(ie);
					System.out.println("Click on the Type: " + currentType);				
					ATUReports.add(time +" Click on the Type: " + currentType, "Success.", "Success", LogAs.PASSED, null);
					Assert.assertTrue(true);
					return;
				}
			}
			
			System.out.println("No Found new instracutor at the list.");				
			ATUReports.add(time +" No Found new instracutor at the list.", "Success.", "Fail", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);	
			
		}catch(Exception e){
			e.getMessage();
			ATUReports.add(e.getMessage(), "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
	}

	public void verifyThatTheTypeWasChoosen(String type){
		
		try {
			String currentType = new Select(type_select).getFirstSelectedOption().getText();
				if(currentType.equals(type)){
					System.out.println("Verify that the type was choosen.");				
					ATUReports.add(time +" Verify that the type was choosen.", "Success.", "Success", LogAs.PASSED, null);
					Assert.assertTrue(true);
					return;
				}
			
			
			System.out.println("Verify that the type was choosen.");				
			ATUReports.add(time +" Verify that the type was choosen.", "Success.", "Fail", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);	
			
		}catch(Exception e){
			e.getMessage();
			ATUReports.add(e.getMessage(), "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
	}

	public void verifyUserIsNotOnTheOwnerList(String User) {
		
		boolean isAllTheInstractorsInTheList=true;
		try{
		clickElementJS(owner_select);
		int i = 0;
		for(WebElement ie: owner_button_select) {
			String currentOwner = ie.getText(); 
			if(currentOwner.contains(User)){
				System.out.println("The user " + currentOwner +  " is found at the list.");				
				ATUReports.add(time +" The user " + currentOwner +  " is found at the list.", "Success.", "Fail", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				isAllTheInstractorsInTheList = false;
				break;
			}
			i++;
		}
		if(isAllTheInstractorsInTheList){
			System.out.println("The user is not found at the list.");
			ATUReports.add(time +" The user is not found at the list.", "Success.", "Success.", LogAs.PASSED, null);
		}
	}catch(Exception e){
		e.getMessage();
		ATUReports.add(e.getMessage(), "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
	}
		
	}
	
	public void verifyThatAllTheTypeInTheDropDownList() {
	
		boolean isAllTheInstractorsInTheList=true;
		try{
		clickElementJS(owner_select);
		int i = 0;
		for(WebElement ie: owner_button_select) {
			String currentOwner = ie.getText(); 
			if(!searchOwnerInTheOwnerList(currentOwner)){
				System.out.println("The " + currentOwner + "is not supposed to be at the list of owners at that course.");				
				ATUReports.add(time +" The " + currentOwner + "is not supposed to be at the list of owners at that course.", "Success.", "Fail.", LogAs.WARNING, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				isAllTheInstractorsInTheList = false;
				break;
			}
			i++;
		}
		if(isAllTheInstractorsInTheList){
			System.out.println("All The Owners are found at the list of owners.");
			ATUReports.add(time +" All The Owners are found at the list of owners.", "Success.", "Success.", LogAs.PASSED, null);
		}
	}catch(Exception e){
		e.getMessage();
		ATUReports.add(e.getMessage(), "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
	}
  }
	
	public void verifyThatAllTheTypeInTheDropDownListDemo() {
		
		boolean isAllTheInstractorsInTheList=true;
		try{
		clickElementJS(owner_select);
		int i = 0;
		for(WebElement ie: owner_button_select) {
			String currentOwner = ie.getText(); 
			if(!searchOwnerInTheOwnerList(currentOwner)){
				System.out.println("The " + currentOwner + "is not supposed to be at the list of owners at that course.");				
				ATUReports.add(time +" Verify that all the right owners are at that dropdown list.", "The " + currentOwner + "is not supposed to be at the list of owners at that course.", "The " + currentOwner + "is at the list of owners at that course.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				isAllTheInstractorsInTheList = false;
				break;
			}
			i++;
		}
		if(isAllTheInstractorsInTheList){
			System.out.println("All The Owners are found at the list of owners.");
			ATUReports.add(time +" Verify that all the right owners are at that dropdown list.",  "All the owners are at the list of owners at that course.", "All the owners are at the list of owners at that course.", LogAs.PASSED, null);
		}
	}catch(Exception e){
		e.getMessage();
		ATUReports.add(e.getMessage(), "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
	}
  }
	

	
	
	
	public boolean searchOwnerInTheOwnerList(String currentOwner) {

		boolean isTheOwnerContains = false;
		for(String Owner: owners){
			if(currentOwner.contains(Owner)){
				isTheOwnerContains = true;
				break;					
			}
		}
		return isTheOwnerContains;
	}
	
	public void addOwnersToList(String OwnerType,int UniqueTest){
		try{
		owners.clear();
		if(OwnerType.equals("Instractor")){
			owners.add(PropertyManager.getProperty("ExcutiveAdmin"));
			owners.add(PropertyManager.getProperty("SuperUser"));
			owners.add(PropertyManager.getProperty("User1"));
			owners.add(PropertyManager.getProperty("User2"));
		} else if( OwnerType.equals("Student")) {
			owners.add(PropertyManager.getProperty("User3"));
			owners.add(PropertyManager.getProperty("User4"));
		} else {
			owners.add(PropertyManager.getProperty("ExcutiveAdmin"));
			owners.add(PropertyManager.getProperty("SuperUser"));
			owners.add(PropertyManager.getProperty("User1"));
			owners.add(PropertyManager.getProperty("User2"));
			owners.add(PropertyManager.getProperty("User3"));
			owners.add(PropertyManager.getProperty("User4"));
		}
		if(UniqueTest == 1){
			owners.add("InstructorTemp");
		}
		if(UniqueTest == 2){
			owners.add("StudentTemp");
		}
		if(UniqueTest == 3){
			owners.add("InstructorTemp");
			owners.add("StudentTemp");
		}
		if(UniqueTest == 4){
			owners.add("automationInstructorUser");
		}
		if(UniqueTest == 5){		
			owners.add("automationStudentUser");
		}
		if(UniqueTest == 6){
			owners.add("automationInstructorUser");
			owners.add("automationStudentUser");
		}
		
		
	}catch(Exception e){
		e.getMessage();
		ATUReports.add(e.getMessage(), "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
	}

	}
	
	public void verifyThatBoardersOfTheDropDownAreInBlack(WebElement element) {
		
		try{
		Select select = new Select(element);
		String borderTopColor = getColorFromCssElement(element,"border-top-color");
		String borderRightColor = getColorFromCssElement(element,"border-right-color");
		String borderBottomColor = getColorFromCssElement(element,"border-bottom-color");
		String borderLeftColor = getColorFromCssElement(element,"border-left-color");
		
		if(borderTopColor.equals(OWNER_BORDER_COLOR) && borderRightColor.equals(OWNER_BORDER_COLOR) && borderBottomColor.equals(OWNER_BORDER_COLOR) && borderLeftColor.equals(OWNER_BORDER_COLOR)){
			System.out.println("All The borders are at the same color black");
			ATUReports.add(time +" All The borders are at the same color black.", "Success.", "Success.", LogAs.PASSED, null);
		} else {
			System.out.println("Not All The borders are at the same color black.");				
			ATUReports.add(time +" Not All The borders are at the same color black.", "Success.", "Fail", LogAs.WARNING, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
		}catch(Exception e){
			e.getMessage();
			ATUReports.add(e.getMessage(), "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}	
	}

	public void verifyThatAllTheOptionsListInTheDropDwon() {
		
		try{		
		boolean isAllTheRecordingsTypesInTheList=true;
		clickElementJS(type_select);
		for(WebElement ie: type_button_select) {
			String currentType = ie.getText(); 
			if(!recording_types.contains(currentType)){
				System.out.println("The drop down is not contians all the types.");				
				ATUReports.add(time +" The drop down is not contians all the types.", "Success.", "Fail", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				isAllTheRecordingsTypesInTheList = false;
				break;
			}
		}
		if(isAllTheRecordingsTypesInTheList){
			System.out.println("The drop down is contians all the types.");
			ATUReports.add(time +" The drop down is contians all the types.", "Success.", "Success.", LogAs.PASSED, null);
		}	
		}catch(Exception e){
			e.getMessage();
			ATUReports.add(e.getMessage(), "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}	
	}
	
	public void insertChapterName(String target_name) {

		try {
		recording_name.sendKeys(target_name);
		System.out.println("Recording name changed to: " + target_name);
		ATUReports.add(time +" Recording name changed.", "Change to: " + target_name,"Changed to " + recording_name + target_name, LogAs.PASSED, null);
		Assert.assertTrue(true);
		} catch (Exception e) {
			e.getMessage();
			ATUReports.add(e.getMessage(), "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}

	}
	
	// that title open or closed
	public boolean isConfirmationMenuClosed() throws InterruptedException {
		try {
		if(new WebDriverWait(driver, 80).until(ExpectedConditions.invisibilityOfElementLocated(By.id("EditButton")))){
			System.out.println("The Edit Recording Properites window was close at time.");
			ATUReports.add(time +" The Edit Recording Properites window was close at time.", "Success.", "Success.", LogAs.PASSED, null);
			return true;
		}else {
			System.out.println("The Edit Recording Properites window was not close at time.");
			ATUReports.add(time +" The Edit Recording Properites window was not close at time.",  "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			return false;
		}}catch (org.openqa.selenium.TimeoutException e) {
			e.getMessage();
			ATUReports.add(e.getMessage(), "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			return false;
		}
		
	}

	// This function verify that confirm window is close
	public void verifyConfirmWindowIsClosed() throws InterruptedException {
			boolean is_closed = isConfirmationMenuClosed();

			if (is_closed) {
				System.out.println("Edit recordings properties window is closed.");
				ATUReports.add(time +" Edit recordings properties window.", "Closed.", "Closed.", LogAs.PASSED, null);
				Assert.assertTrue(true);
			} else {
				System.out.println("Edit recordings properties window is open.");
				ATUReports.add(time +" Edit recordings properties window.", "Closed.", "Open.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Assert.assertTrue(false);
			}
		}
	
	public void verifyThatHoverOnButtonAndSeeShadow(WebElement button,String text) throws NoSuchElementException, InterruptedException {
		
		try{
		moveToElementAndPerform(button,driver);
		String textAfterHover = button.getAttribute("title");
		
		if(textAfterHover.equals(text)){
			System.out.println("An inner shadow is applied on the button " + button.getText());
			ATUReports.add(time +" An inner shadow is applied on the button " + button.getText(), "Success.", "Success.", LogAs.PASSED, null);
		} else {
			System.out.println("An inner shadow is not applied on the button " + button.getText());				
			ATUReports.add(time +" An inner shadow is not applied on the button " + button.getText(), "Success.", "Fail", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
		}catch(Exception e){
			e.getMessage();
			ATUReports.add(e.getMessage(), "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
	}
	
	// The date is in the following format: 'XX/XX/XXXX'.
	public String verifyThatTheCalendarInTheRightFormat(){
		
		String id = date_Field.getAttribute("id");
		String correctDate = (String)((JavascriptExecutor) driver).executeScript("return document.getElementById(\""+id+"\").value;");	
		
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		sdf.setLenient(false);
		try {

			//if not valid, it will throw ParseException
			Date date = sdf.parse(correctDate);
			System.out.println(date);
			System.out.println("The date is in the following format: 'XX/XX/XXXX'");
			ATUReports.add(time +" The date is in the following format: 'XX/XX/XXXX'", "Success.", "Success.", LogAs.PASSED, null);
			Assert.assertTrue(true);
			
		} catch (ParseException e) {
			System.out.println("The date is not in the following format: 'XX/XX/XXXX'" );				
			ATUReports.add(time +" The date is not in the following format: 'XX/XX/XXXX'", "Success.", "Fail", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}

		  String[]parts= correctDate.split("/");

		  if(parts[0].length() == 1) {
			  parts[0] = "0" + parts[0];
		  } 
		  if(parts[1].length() == 1) {
			  parts[1] = "0" + parts[1];
		  }
		  correctDate = parts[0] + "/"  + parts[1] + "/"  + parts[2];
		
		return correctDate;
	}

	public String clickOnDifferentOwnerThatTheExist(String recordBy) {
		
		try {
		String[]splitRecordBy= recordBy.split(" ");
		String recordByToCheck  =splitRecordBy[2];	
		for(WebElement ie: owner_button_select) {
			String currentOwner = ie.getText();	
			String[]splitOwner= currentOwner.split(" ");
			String OwnerToCheck = splitOwner[1].substring(1, splitOwner[1].length()-1);
			if(!OwnerToCheck.contains(recordByToCheck)){
				clickElement(ie);
				System.out.println("Click on the instractur: " + currentOwner);				
				ATUReports.add(time +" The instracutor is found at the list.", "Success.", "Success", LogAs.PASSED, null);
				Assert.assertTrue(true);
				String OwnerToReturn = "recorded by: " + OwnerToCheck;
				return OwnerToReturn;
			}		
		}
		
		}catch(Exception e){
			e.getMessage();
			ATUReports.add(e.getMessage(), "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
		
		System.out.println("No Found new instracutor at the list.");				
		ATUReports.add(time +" No Found new instracutor at the list.", "Success.", "Fail", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		Assert.assertTrue(false);
		return null;
	}

	public String getRecordBy(String recordByName) {
		
		String[]splitOwner= recordByName.split(" ");
		String OwnerToCheck = splitOwner[1].substring(1, splitOwner[1].length()-1);
			
		return  "recorded by: "+OwnerToCheck;
		
	}

	public String getNewRecordNameForTest(String recordByName) {
		
		String[]splitOwner= recordByName.split(" ");
		String OwnerToCheck = splitOwner[1].substring(1, splitOwner[1].length()-1);
		
		return  OwnerToCheck +" (" + splitOwner[0] +")";
		
	}

	public String getOwner(String UserName) {
		
		String[]splitOwner= UserName.split(" ");
		String OwnerToCheck = splitOwner[1].substring(1, splitOwner[1].length()-1);
		
		return OwnerToCheck;
	}

	public String getOwnerName(String User) {
		
		String strToReturn = null;
		for(WebElement ie: owner_button_select) {
			String currentOwner = ie.getText(); 
			if(currentOwner.contains(User)){
				strToReturn = getOwner(currentOwner);
				break;
			}
		}
		return strToReturn;
	}

	public void clickOnTheGreyArea() throws InterruptedException {
		try {
			if(driver instanceof FirefoxDriver){
				cancel_button.sendKeys(Keys.ESCAPE);
			} else {
				Actions action = new Actions(driver);
				action.sendKeys(Keys.ESCAPE).build().perform();
			}
			ATUReports.add(time +" Clicked on ESC button.","Success.", "Success.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} catch (Exception e) {
			ATUReports.add(time +" Fail click on ESC button.","Success.", "Fail." ,LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
		Thread.sleep(2000);
	}

	public void changeDate(String date) {
		try {
		date_Field.clear();
		date_Field.sendKeys(date);
		System.out.println("changing the date to: " + date);		
		ATUReports.add(time +" changing the date to: " + date,"Success.", "Success." ,LogAs.PASSED, null);
		Assert.assertTrue(true);
		} catch (Exception e) {
			e.getMessage();
			ATUReports.add(time +" Fail changing the date to:" + date + e.getMessage(), LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
		
	}
	
}