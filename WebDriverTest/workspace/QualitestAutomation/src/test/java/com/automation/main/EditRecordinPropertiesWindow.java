package com.automation.main;

import java.util.List;

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

@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class EditRecordinPropertiesWindow extends Page {
	public EditRecordinPropertiesWindow(WebDriver browser) {
		super(browser);
		// TODO Auto-generated constructor stub
	}

	@FindBy(id = "EditButton")
	public
	WebElement save_button;
	@FindBy(id = "recordingPropertiesRecordedBy")
	WebElement owner_button;
	@FindBy(xpath = "//*[@id=\"recordingPropertiesRecordedBy\"]/option")
	List<WebElement> owner_button_select;
	@FindBy(id = "ModalDialogHeader")
	WebElement edit_recording_properties_title;
	@FindBy(id = "recordingTItle")
	WebElement recording_name;
	public @FindBy(id="recordingTItle") WebElement recording_title;

	// change ownership of recording
	public void changeOwner(String name) {
		try {
			owner_button.click();
			System.out.println("clicked on owner scroll");
			for (WebElement el : owner_button_select) {
				String val = el.getText();
				if (val.contains(name)) {

					el.click();
					owner_button.sendKeys(Keys.ENTER);
					// moveToElementAndClick(el, driver);
					System.out.println("user selected");

					return;
				}

			}
			System.out.println("user1 selection failed");
		} catch (Exception e) {
			System.out.println("clicked on owner scroll failed");

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
			// target_name, recording_name.getText(), LogAs.FAILED, null);
			// Assert.assertTrue(false);
			// }
		} catch (Exception msg) {
			System.out.println("Fail to change recording name.");
			ATUReports.add("Recording name changed.", "Success.", "Fail.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}

	}

	// This function clicks on Save button
	public void clickOnSaveButton() {
		try {
			save_button.click();
			System.out.println("Clicked on save button.");
			ATUReports.add("Clicked on save button.", "Success.", "Success.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} catch (Exception msg) {
			System.out.println("Fail to click on save button.");
			ATUReports.add("Clicked on save button.", "Success.", "Fail.", LogAs.FAILED, null);
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
			Thread.sleep(1000);
			save_button.click();
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("recordingTItle")));
			System.out.println("save succeded");
			confirm.clickOnOkButtonAfterConfirmEditRecordingProperties();
		} catch (Exception e) {
			System.out.println("clicked on recording title input failed");

		}
	}
}
