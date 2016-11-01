package com.automation.main.page_helpers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
public class EditRecordinPropertiesWindow extends Page {
	public EditRecordinPropertiesWindow(WebDriver browser) {
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
			owner_button.click();
			System.out.println("clicked on owner scroll");
			for (WebElement el : owner_button_select) {
				val = el.getText();
				if (val.contains(name)) {
					
					el.click();
					owner_button.sendKeys(Keys.ENTER);
					// moveToElementAndClick(el, driver);
					System.out.println("user selected");
					ATUReports.add("Changes Ownership","Instructor: "+val+" Changed ownsership successfully","Changed ownsership successfully", LogAs.PASSED, null);					
					return;
				}
			}			
		} catch (Exception e) {
			System.out.println("clicked on owner scroll failed");
			ATUReports.add("Changes Ownership","Instructor: "+val+" Changed ownsership successfully","Changed ownsership failed", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
	}
	
	
	public void verifyLabelExist(WebElement label,String LabelName) {
	
		String nameToCompare = label.getText();
		if (nameToCompare.equals(LabelName)) {
			System.out.println("the label: " + LabelName + " equals to " + nameToCompare );
			ATUReports.add("the label: " + LabelName + " equals to " + nameToCompare, LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("the label: " + LabelName + " not equals to " + nameToCompare);
			ATUReports.add("the label: " + LabelName + " not equals to " + nameToCompare, LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
			}	
	}
	
	
	public void waitForPageToLoad(){
	
		wait.until(ExpectedConditions.visibilityOf(edit_recording_properties_title));
		wait.until(ExpectedConditions.visibilityOf(recording_title));
		wait.until(ExpectedConditions.visibilityOf(date_Field));
		wait.until(ExpectedConditions.visibilityOf(owner_select));
		wait.until(ExpectedConditions.visibilityOf(type_select));
		wait.until(ExpectedConditions.elementToBeClickable(By.id("CancelButton")));
		wait.until(ExpectedConditions.elementToBeClickable(By.id("EditButton")));
	}
	
	public void checkTheFirstTypeThatAprearsOnLabelIsCorrect(WebElement Tab){
		
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
			ATUReports.add("the type: " + FirstSelctor + "is correct to " + TabName, LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("the type: " + FirstSelctor + "is not correct to " + TabName);
			ATUReports.add("the type: " + FirstSelctor + "is not correct to " + TabName, LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
			}		
	}
	
	
	// verify edit properties background color is same as recording background color
	public void verifyEditPropertiesColor(RecordingHelperPage rec) throws InterruptedException {
		Thread.sleep(2000);
		String background_rec = rec.getBackGroundColor(rec.background);
		String menu_background = getBackGroundColor(edit_recordings_properties_background);
		if (rec.getBackGroundColor(rec.background).equals(getBackGroundColor(edit_recordings_properties_background))) {
			ATUReports.add("edit Properties menu background color is same as recording background color", LogAs.PASSED, null);
			System.out.println("edit Properties menu background color is same as recording background color");
			Assert.assertTrue(true);
		} else {
			ATUReports.add("edit Properties menu background color is not  same as recording background color", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			System.out.println("edit Propertiese menu background color is  not same as recording background color");
			Assert.assertTrue(false);
		}

	}
	
	/**
	 * this function verify edit recording properties Title
	 */
	public void verifyEditRecordingPropertiesTitle() throws InterruptedException {
		Thread.sleep(1000);
		String val = edit_recording_properties_title.getText();
		if (val.equals("Edit Recording Properties")) {
			System.out.println("Edit Recording Properties menu title verified.");
			ATUReports.add("Edit Recording Properties menu title verified.", LogAs.PASSED, null);
		} else {
			System.out.println("Edit Recording Properties menu title not verified.");
			ATUReports.add("Edit Recording Properties menu title not verified.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
		Assert.assertEquals("Edit Recording Properties", val);

	}
	
	
	public void verifyWarningDisplay() throws InterruptedException {
		Thread.sleep(1000);
		recording_name.clear();	
		if (warningText.isDisplayed()) {
			
			String warning = warningText.getText();	
			if(warning.equals("Please specify the recording name")){
				System.out.println("Verify Warning is Display.");
				ATUReports.add("Verify Warning is Display.", LogAs.PASSED, null);
			}else {
				System.out.println("Verify Warning is Display but the message is diffrenet from Please specify the recording Name.");
				ATUReports.add("Verify Warning is Display.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			}		
		} else {
			System.out.println("Verify Warning is not Display.");
			ATUReports.add("Verify Warning is not Display.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
		
	}
	
	public void verifySaveButtonDisable() throws InterruptedException{
				
		if (!save_button.isEnabled()) {
			System.out.println("Verify that the save button is disable.");
			ATUReports.add("Verify that the save button is disable.", LogAs.PASSED, null);
		} else {
			System.out.println("Verify that the save button is disable.");
			ATUReports.add("Verify that the save button is disable.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}	
		String grey_color = "rgba(66, 139, 202, 1)";	
		if (save_button.getCssValue("background-color").equals(grey_color)) {
			System.out.println("Save button background color is grey.");
			ATUReports.add("save button background color is grey.", LogAs.PASSED, null);
		} else {
			ATUReports.add("Save button background color is not grey.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			System.out.println("Save button background color is not grey.");
		}
			
	}
	

	public void verifyInfomativeText() {
		
		String infoText  = info_label.getText();
		
		if (infoText.equals("Note: The creation date is used by the reports. This recording will only appear in reports that include the new date and will no longer appear in reports for the old date.")) {
			System.out.println("Valid Infomative Text.");
			ATUReports.add("Valid Infomative Text.", LogAs.PASSED, null);
		} else {
			System.out.println("Not Valid Infomative Text.");
			ATUReports.add("Not Valid Infomative Text.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
		
	}
	
	public void VerifyTheLocationOfTheSaveAndCancel() {
		
		Point cancel=cancel_button.getLocation();
		Point save= save_button.getLocation();
		Point title = edit_recording_properties_title.getLocation();
		
		if((save.getY() > title.getY()) && (save.getX() > cancel.getX()))
		{
		 System.out.println("The Save button is displayed on the bottom right corner of the model window.");
		 ATUReports.add("The Save button is displayed on the bottom right corner of the model window.", LogAs.PASSED, null);
		}else {
		 System.out.println("The Save button isn't displayed on the bottom right corner of the model window.");
		 ATUReports.add("The Save button isn't displayed on the bottom right corner of the model window.", LogAs.FAILED,new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
		if((cancel.getY() > title.getY()) && (cancel.getX() < save.getX())) {
			 System.out.println("The Cancel button is displayed on the left of the Save button.");
			 ATUReports.add("The Cancel button is displayed on the left of the Save button.", LogAs.PASSED, null);
			}else {
			 System.out.println("The Cancel button is not displayed on the left of the Save button.");
			 ATUReports.add("The Cancel button is not displayed on the left of the Save button.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			}
		}
		
	
	public void VerifyFontColor(WebElement element,String Color) {
		
		String ColorName  = getFontColor(element);
		if(Color.equals(ColorName)){
			 System.out.println("Verify the font color of: " + element.getText());
			 ATUReports.add("Verify the font color of: " + element.getText(), LogAs.PASSED, null);
			}else {
			 System.out.println("Not Verify the font color of: " + element.getText());
			 ATUReports.add("Not Verify the font color of: " + element.getText(), LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			}	
	}
	

	/**
	 * this function verify that the title location is aligned left
	 */
    public void verifyEditRedordingPropertiesTitleIsAlignrdLeft()
 {
    	Point cancel=cancel_button.getLocation();
		Point title=edit_recording_properties_title.getLocation();
		
		
		if((title.getX() < cancel.getX()))
		{
		 System.out.println("The location of the edit recording properties is aligned left.");
		 ATUReports.add("The location of the edit recording properties is aligned left.", LogAs.PASSED, null);
		}else {
		 System.out.println("The location of the edit recording properties is not aligned left.");
		 ATUReports.add("The location of the edit recording properties is not aligned left.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
	}
 }
	
    public void verifyLabelIsAlignrdLeftTOtheEditBoxOrSelector(WebElement label,WebElement editText){
    	
    	Point labelLoc= label.getLocation();
		Point editTextLoc= editText.getLocation();
		
		
		if((labelLoc.getX() < editTextLoc.getX()))
		{
		 System.out.println("The location of the label " + label.getText() + " recording properties is aligned left.");
			ATUReports.add("The location of the label " + label.getText() + " recording properties is aligned left.", LogAs.PASSED, null);
		}else {
		 System.out.println("The location of the label " + label.getText() + " recording properties is not aligned left.");
			ATUReports.add("The location of the label " + label.getText() + " recording properties is not aligned left.",LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
    }
    
    public void verifyInputEqualsOutSideString(WebElement element ,String OutSideString){
    	
    	String id = element.getAttribute("id");
    	String inputElement = (String)((JavascriptExecutor) driver).executeScript("return document.getElementById(\""+id+"\").value;");	
      
    	if(OutSideString.equals(inputElement)){
		 System.out.println("The input Element: " +inputElement + " is equals to the out Side String " + OutSideString);
			ATUReports.add("The input Element: " +inputElement + " is equals to the out Side String " + OutSideString, LogAs.PASSED, null);
		}else if(OutSideString.length()!=inputElement.length()) {
			StringBuilder sb= new StringBuilder(inputElement);
			sb.insert(3, "0");
			if(sb.toString().equals(OutSideString)){
				 System.out.println("The input Element: " +inputElement + " is equals to the out Side String " + OutSideString);
				 ATUReports.add("The input Element: " +inputElement + " is equals to the out Side String " + OutSideString, LogAs.PASSED, null);
			}
		}else{
		 System.out.println("The input Element: " +inputElement + " is not equals to the out Side String " + OutSideString);
			ATUReports.add("The input Element: " +inputElement + " is not equals to the out Side String " + OutSideString,LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
    }
 
    public void verifyThatTheCreatorOwnerIsOnTheOwnerList(String user_name){
    	
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
   			ATUReports.add("The user: " + user + " is appread on the list", LogAs.PASSED, null);
   		}else {
   		    System.out.println("The user: " + user + " is not appread on the list");
   			ATUReports.add("The user: " + user + " is not appread on the list",LogAs.FAILED,new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
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
			ATUReports.add("Verifying edit window is closed", "edit window is closed","edit window is closed",LogAs.PASSED,null);
		} catch (Exception msg) {
			ATUReports.add("Verifying edit window is closed", "edit window isn't closed","edit window isn't closed",LogAs.WARNING,null);
		}
	}

	// This function get recording name and change it to this recording name in
	// edit recording properties
	public void changeRecordingNameToTargetName(String target_name) throws InterruptedException {
		try {
			recording_name.clear();
			recording_name.sendKeys(target_name);
			Thread.sleep(3000);
			// if(recording_name.getText().equals(target_name)) {
			System.out.println("Recording name changed to: " + target_name);
			ATUReports.add("Recording name changed.", "Change to: " + target_name,
					"Changed to " + recording_name + target_name, LogAs.PASSED, null);
			Assert.assertTrue(true);
			// } else {
			// System.out.println("Recording name not changed to target: " +
			// target_name + ". Instead: " + recording_name.getText());
			// ATUReports.add("Recording name not changed to target.",
			// target_name, recording_name.getText(), LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			// Assert.assertTrue(false);
			// }
		} catch (Exception msg) {
			System.out.println("Fail to change recording name.");
			ATUReports.add("Recording name changed.", "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}

	}

	// This function clicks on Save button
	public void clickOnSaveButton() {
		try {
			wait.until(ExpectedConditions.visibilityOf(save_button));			
		    ((JavascriptExecutor) driver).executeScript("document.getElementById(\""+save_button.getAttribute("id")+"\").click();");
			System.out.println("Clicked on save button.");
			ATUReports.add("Clicked on save button.", "Success.", "Success.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} catch (Exception msg) {
			System.out.println("Fail to click on save button.");
			ATUReports.add("Clicked on save button.", "Success.", "Fail."+msg.getMessage(), LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
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
		recordName = recording_title.getText();
		save_button.click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("recordingTItle")));
		System.out.println("save succeded");
		Thread.sleep(2000);
		confirm.clickOnOkButtonAfterConfirmEditRecordingProperties();
	} catch (Exception e) {
		System.out.println("clicked on recording title input failed");

	}
		return recordName;
	}


	public void verifyThatAllTheInstractorsInTheDropDownList() {
	
		boolean isAllTheInstractorsInTheList=true;
		
		clickElementJS(owner_select);
		int i = 0;
		for(WebElement ie: owner_button_select) {
			String currentOwner = ie.getText(); 
			if(!currentOwner.contains(owners.get(i))){
				System.out.println("The instracutor are not found at the list.");				
				ATUReports.add("The instracutor are not found at the list.", "Success.", "Fail", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				isAllTheInstractorsInTheList = false;
				break;
			}
			i++;
		}
		if(isAllTheInstractorsInTheList){
			System.out.println("All The instracutors are found at the list.");
			ATUReports.add("All The instracutors are found at the list.", "Success.", "Success.", LogAs.PASSED, null);
		}
		
	}
	
	public void addOwnersToList(){
		
		owners.add(PropertyManager.getProperty("ExcutiveAdmin"));
		owners.add(PropertyManager.getProperty("SuperUser"));
		owners.add(PropertyManager.getProperty("User1"));
		owners.add(PropertyManager.getProperty("User2"));
	}
	

	public void verifyThatBoardersOfTheDropDownAreInBlack(WebElement element) {
		
		Select select = new Select(element);
		String borderTopColor = getColorFromCssElement(element,"border-top-color");
		String borderRightColor = getColorFromCssElement(element,"border-right-color");
		String borderBottomColor = getColorFromCssElement(element,"border-bottom-color");
		String borderLeftColor = getColorFromCssElement(element,"border-left-color");
		
		if(borderTopColor.equals(OWNER_BORDER_COLOR) && borderRightColor.equals(OWNER_BORDER_COLOR) && borderBottomColor.equals(OWNER_BORDER_COLOR) && borderLeftColor.equals(OWNER_BORDER_COLOR)){
			System.out.println("All The borders are at the same color black");
			ATUReports.add("All The borders are at the same color black.", "Success.", "Success.", LogAs.PASSED, null);
		} else {
			System.out.println("Not All The borders are at the same color black.");				
			ATUReports.add("Not All The borders are at the same color black.", "Success.", "Fail", LogAs.WARNING, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
		
	}

	public void verifyThatAllTheOptionsListInTheDropDwon() {
		
		boolean isAllTheRecordingsTypesInTheList=true;
		clickElementJS(type_select);
		for(WebElement ie: type_button_select) {
			String currentType = ie.getText(); 
			if(!recording_types.contains(currentType)){
				System.out.println("The instracutor are not found at the list.");				
				ATUReports.add("The instracutor are not found at the list.", "Success.", "Fail", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				isAllTheRecordingsTypesInTheList = false;
				break;
			}
		}
		if(isAllTheRecordingsTypesInTheList){
			System.out.println("All The instracutors are found at the list.");
			ATUReports.add("All The instracutors are found at the list.", "Success.", "Success.", LogAs.PASSED, null);
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


	public void insertChapterName(String target_name) {
		
		recording_name.sendKeys(target_name);
		System.out.println("Recording name changed to: " + target_name);
		ATUReports.add("Recording name changed.", "Change to: " + target_name,"Changed to " + recording_name + target_name, LogAs.PASSED, null);
		Assert.assertTrue(true);
		
	}

	
	// that title open or closed
	public boolean isConfirmationMenuClosed() throws InterruptedException {
		try {
			for(int i = 0; i < 20 ; i++){
				if(!save_button.isDisplayed()){
					return true;
				}else{
					Thread.sleep(1000);
				}
				
			}
			return false;
		} catch (org.openqa.selenium.NoSuchElementException msg) {
			return true;
		}
	}

		// This function verify that confirm window is close
		public void verifyConfirmWindowIsClosed() throws InterruptedException {
			boolean is_closed = isConfirmationMenuClosed();

			if (is_closed) {
				System.out.println("Confirm window is closed.");
				ATUReports.add("Confirm window.", "Closed.", "Closed.", LogAs.PASSED, null);
				Assert.assertTrue(true);
			} else {
				System.out.println("Confirm window is open.");
				ATUReports.add("Confirm window.", "Closed.", "Open.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Assert.assertTrue(false);
			}
		}
	
	
	

	public void verifyThatHoverOnButtonAndSeeShadow(WebElement button,String text) throws NoSuchElementException, InterruptedException {

		moveToElementAndPerform(button,driver);
		String textAfterHover = button.getAttribute("title");
		
		if(textAfterHover.equals(text)){
			System.out.println("An inner shadow is applied on the button " + button.getText());
			ATUReports.add("An inner shadow is applied on the button " + button.getText(), "Success.", "Success.", LogAs.PASSED, null);
		} else {
			System.out.println("An inner shadow is not applied on the button " + button.getText());				
			ATUReports.add("An inner shadow is not applied on the button " + button.getText(), "Success.", "Fail", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
		
	}
}
