package com.automation.main;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;

@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class DeleteMenu extends Page {
	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}

	public DeleteMenu(WebDriver browser) {
		super(browser);
		// TODO Auto-generated constructor stub
	}
	@FindBy(id="ModalDialogHeader") WebElement delete_menu_title;
    @FindBy(id = "DeleteButton") WebElement delete_button;
	@FindBy(xpath = "//*[@id=\"recordingsList\"]/option") List<WebElement> recording_list;
	@FindBy(id = "CancelButton") WebElement cancel_button;
	@FindBy(xpath = "html/body/div[2]/div") WebElement outside_of_delete_menu_scope;
	@FindBy(id = "ModalDialogHeaderWrap")WebElement delete_menu_background;
	@FindBy(id = "InfoText") WebElement info_text;
	@FindBy(id = "recordingsList") WebElement recording_list_select;
	@FindBy(id = "deleteContentSelector") WebElement additional_content_recording_list_select;
	@FindBy(css = "#deleteContentSelector>option") List<WebElement> additional_content_delete_recording_list;
	
	String[] delete_recording_string_list;
	// This function clicks on delete button of copy menu
	public void clickOnDeleteButton() throws InterruptedException {	
		try {
			delete_button.click();
			System.out.println("Clicked on delete button.");
			ATUReports.add("Click Delete button", "Clicked on delete button", "Clicked on delete button", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} catch(Exception e) {
			System.out.println("Fail click on delete button.");
			ATUReports.add("Click Delete button", "Clicked on delete button", "Fail click on delete button", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		Thread.sleep(3000);
	}
	
	public List<String> getRecordingList() {
		List<String> current_recording_list = new ArrayList<String>();
		
		for(int i = 0; i < recording_list.size(); i++) {
			current_recording_list.add(recording_list.get(i).getText());
		}
		
		return current_recording_list;
	}
	
	public boolean isDeleteMenuClose() {
		try {
			delete_button.isDisplayed();
			return false;
		} catch (Exception msg) {
			return true;
		}
	}
	
	
	// This function clicks on cancel button of delete menu
		public void clickOnCancelButton() throws InterruptedException {	
			try {
				cancel_button.click();
				System.out.println("Clicked on cancel button.");
				ATUReports.add("Click cancel button", "Clicked on cancel button", "Clicked on cancel button", LogAs.PASSED, null);
				Assert.assertTrue(true);
			} catch(Exception e) {
				System.out.println("Fail click on cancel button.");
				ATUReports.add("Click cancel button", "Clicked on cancel button", "Fail click on delete cancel", LogAs.FAILED, null);
				Assert.assertTrue(false);
			}
			Thread.sleep(3000);
		}
		
		// This function send ESC keyboard to delete menu.
		// Its target is to close the delete menu by sending this key.
		public void clickEscOnKeyBoardToCloseDeleteWindow() throws InterruptedException {
			try {
				outside_of_delete_menu_scope.sendKeys(Keys.ESCAPE);
				System.err.println("Clicked on ESC button.");
				ATUReports.add("Clicked on ESC button.", LogAs.PASSED, null);
				Assert.assertTrue(true);
			} catch (Exception e) {
				System.out.println("Fail click on ESC button.");
				ATUReports.add("Fail click on ESC button.", LogAs.FAILED, null);
				Assert.assertTrue(false);
			}
			Thread.sleep(3000);
		}
		/// verify move menu title
		public void verifyDeleteMenuTitle() throws InterruptedException {
			Thread.sleep(2000);
			String val = delete_menu_title.getText();
			if (val.equals("Delete")) {
				ATUReports.add("delete menu title verified ", LogAs.PASSED, null);
				System.out.println("delete menu title verified ");
			} else {
				ATUReports.add("delete menu title not verified  ", LogAs.FAILED, null);
				System.out.println("delete menu title not verified  ");
			}
			Assert.assertEquals("Delete", val);

		}
		// verify move menu background color is same as recording background color
		public void verifyDeleteColor(RecordingHelperPage rec) throws InterruptedException {
			Thread.sleep(2000);
		String background_rec=rec.getBackGroundColor(rec.background);
		String menu_background=getBackGroundColor(delete_menu_background);
			if (rec.getBackGroundColor(rec.background).equals(getBackGroundColor(delete_menu_background))) {
				ATUReports.add("move menu background color is same as recording background color", LogAs.PASSED, null);
				System.out.println("move menu background color is same as recording background color");
				Assert.assertTrue(true);
			} else {
				ATUReports.add("move menu background color is not  same as recording background color", LogAs.FAILED, null);
				System.out.println("move menu background color is  not same as recording background color");

				Assert.assertTrue(false);
			}

		}

		// "verify info text"
		public void verifyInfoText() throws InterruptedException {
			String infotext = "Are you sure you want to delete the following items?";
			String info = info_text.getText();
			if (infotext.equals(info)) {
				ATUReports.add("info text verified", LogAs.PASSED, null);
				Assert.assertTrue(true);
				System.out.println(" info text verified");
			} else {
				ATUReports.add(" info text not unverified", LogAs.FAILED, null);
				System.out.println(" info text not verified");
				Assert.assertTrue(false);
			}

		}

		// verify courses in courses page match the one in delete menu
		public void verifySelectedRecordingsInDeleteMenu(List<String> recordings) throws InterruptedException {
		    delete_recording_string_list = getStringFromElement(recording_list);
			if (delete_recording_string_list.length != recordings.size()) {
				Assert.assertTrue(false);
				ATUReports.add("different numbers of  recordings in delete menu and recording page ", LogAs.FAILED, null);
			} else {
				for (String e : delete_recording_string_list) {
				
				
					if (!recordings.contains(e)) {
						Assert.assertTrue(false);
						ATUReports.add("recording in delete menu and recording page are not matched ", LogAs.FAILED, null);
						System.out.println("recording in delete menu and recording page are not matched ");
					}
				}
				Assert.assertTrue(true);
				ATUReports.add("recording in delete menu and recording page are matched ", LogAs.PASSED, null);
				System.out.println("recording in delete menu and recording page are matched ");
			}

		}

	

		


		///verify element location such as buttons,list of courses
		 public void verifyDeleteMenuElementsLocation()
		 {
			    
				
				Point deletebutton = delete_button.getLocation();
				Point cancelbutton = cancel_button.getLocation();
				Point delete_menu_list = null;
				
				try {
					delete_menu_list = recording_list_select.getLocation();
				} catch(Exception msg) {
					delete_menu_list = additional_content_recording_list_select.getLocation();
				}
				
				
				
				Point info=info_text.getLocation();
				Point title=delete_menu_title.getLocation();

				if((info.getY() > title.getY()) &&(delete_menu_list.getY() > info.getY())  &&(cancelbutton.getX() < deletebutton.getX()) && (cancelbutton.getY() == deletebutton.getY())&& (cancelbutton.getY() >delete_menu_list.getY()))
			 {
				 System.out.println("elements location are verified in delete menu");
					ATUReports.add("elements location are verified in delete menu", LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
			 }	 else {
				 System.out.println("elements location are not verified in delete menu");
					ATUReports.add("elements location are not verified in delete menu", LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
			}
		 }
		 
		 // This function verify that delete window is displayed
		 public void verifyDeleteWindowDisplayed() {
			 boolean is_closed = isDeleteMenuClose();
			 
			 if(!is_closed) {
				 System.out.println("Delete window is displayed.");
				 ATUReports.add("Delete window.", "Displayed.", "Displayed.", LogAs.PASSED, null);
				 Assert.assertTrue(true);
			 } else {
				 System.out.println("Delete window is not displayed.");
				 ATUReports.add("Delete window.", "Displayed.", "Not displayed.", LogAs.FAILED, null);
				 Assert.assertTrue(false);
			 }
		 }
		 
		 // This function verify that delete window is not displayed
		 public void verifyDeleteWindowNotDisplayed() {
			 boolean is_closed = isDeleteMenuClose();
			 
			 if(is_closed) {
				 System.out.println("Delete window is not displayed.");
				 ATUReports.add("Delete window.", "Not displayed.", "Not displayed.", LogAs.PASSED, null);
				 Assert.assertTrue(true);
			 } else {
				 System.out.println("Delete window is displayed.");
				 ATUReports.add("Delete window.", "Not displayed.", "Displayed.", LogAs.FAILED, null);
				 Assert.assertTrue(false);
			 }
		 }
		 
		 // This function verifty that delete window title is: Delete
		 public void verifyDeleteWindowTitleIsDelete() {
			 try {
				 wait.until(ExpectedConditions.visibilityOf(delete_menu_title));
				 
				 String window_title = delete_menu_title.getText();
					
				 if(window_title.equals("Delete")) {
					 System.out.println("Delete window title is: Delete");
					 ATUReports.add("Delete window title.", "Delete.", "Delete.", LogAs.PASSED, null);
					 Assert.assertTrue(true);
				 } else {
					 System.out.println("Delete window title is: " + window_title);
					 ATUReports.add("Delete window title.", "Delete.", window_title, LogAs.PASSED, null);
					 Assert.assertTrue(false);
				 }
			 } catch (Exception msg) {
				 System.out.println("Delete window not displayed.");
				 ATUReports.add("Delete window title.", "Not displayed.", "Not displayed.", LogAs.PASSED, null);
				 Assert.assertTrue(false);
			 }
			 
			 
		 }
		 
		 // This function return delete recording list of additional content
		 public List<String> additionalContentGetRecordingList() {
			 List<String> current_recording_list = new ArrayList<>();
			 
			 
			 for(int i=0; i<additional_content_delete_recording_list.size(); i++) {
				 current_recording_list.add(additional_content_delete_recording_list.get(i).getText());
			 }
			 
			 return current_recording_list;
		 }
		 
		 // This function get String of recording and verify that this recording appear in Additional Content Delete recording list
		 public void verifyTargetRecordingInAdditionalContentDeleteWindowRecordingList(String target_recording) {
			 if(additionalContentGetRecordingList().contains(target_recording)) {
				 System.out.println("Target recording in recording list.");
				 ATUReports.add("Recording list.", "Target recording in the list.", "Target recording in the list.", LogAs.PASSED, null);
				 Assert.assertTrue(true);
			 } else {
				 System.out.println("Target recording not in recording list.");
				 ATUReports.add("Recording list.", "Target recording in the list.", "Target recording not in the list.", LogAs.FAILED, null);
				 Assert.assertTrue(false);
			 }
		 }
	

}
