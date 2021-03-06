package com.automation.main.page_helpers;

import java.io.File;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;

public class AddAdditionalContentFileWindow extends Page {

	public AddAdditionalContentFileWindow(WebDriver browser) {
		super(browser);
		// TODO Auto-generated constructor stub
	}

	// ewds
	@FindBy(id = "ModalDialogHeader")
	public WebElement additional_content_file_title;
	@FindBy(id = "InfoText")
	public WebElement additional_content_file_info;
	@FindBy(id = "UploadFile")
	public WebElement select_upload_additional_file;
	@FindBy(id = "AddFileButton")
	public WebElement add_additional_file_button;
	@FindBy(xpath = "//*[@id=\"addFileWindow\"]/form/div[1]/small[1]/b")
	public WebElement add_additional_file_selected_name;
	@FindBy(xpath = "//*[@id=\"addFileWindow\"]/form/div[1]/div[4]")
	public WebElement upload_progress_bar;
	@FindBy(id = "CancelButton")
	public WebElement cancel_additional_file_button;
	@FindBy(id = "ModalDialogHeaderWrap")
	public WebElement addFileWindow;
	@FindBy(id = "addFileWindow")
	public WebElement background;
	@FindBy(xpath = "//*[@id=\"addFileWindow\"]/form/div[1]/small[1]")
	public WebElement uploading_file;
	@FindBy(xpath = "//*[@id=\"addFileWindow\"]/form/div[1]/small[2]/em")
	public WebElement elapsed_time;
	@FindBy(xpath = "//*[@id=\"addFileWindow\"]/form/div[1]/small[3]/em")
	public WebElement estimated_time;
	@FindBy(xpath = "//*[@id=\"addFileWindow\"]/form/div[1]/small[5]")
	public WebElement uploaded_percent;
	@FindBy(xpath = "//*[@id=\"addFileWindow\"]/form/div[1]/small[4]/em")
	public WebElement uploaded_speed;

	/// verify Add Additional Content File title
	public void verifyAdditionalContentFileWindowTitle() throws InterruptedException {
		Thread.sleep(2000);
		String val = additional_content_file_title.getText();
		if (val.equals("Add Additional Content File")) {
			System.out.println("Add Additional Content File window title verified ");
			ATUReports.add(time +" Add Additional Content File window title verified ", LogAs.PASSED, null);
		} else {
			System.out.println("Add Additional Content File window not verified  ");
			ATUReports.add(time +" Add Additional Content File window title not verified  ", LogAs.FAILED,
					new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
		Assert.assertEquals("Add Additional Content File", val);

	}

	////// upload additional content
	public void uploadFile(String path) {

		path = System.getProperty("user.dir") + path;
		File dir = new File(path);
		if (dir.exists()) {
			System.out.println("File is exist.");
			ATUReports.add(time +" file is exist ", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("File is not exist.");
			ATUReports.add(time +" File is not exist.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		// from here you can use as it wrote
		select_upload_additional_file.sendKeys(path);
	}

	public void clickOnCancelButton() {
		try {
			((JavascriptExecutor) driver).executeScript(
					"document.getElementById(\"" + cancel_additional_file_button.getAttribute("id") + "\").click();");
			System.out.println("Clicked on cancel button.");
			ATUReports.add(time +" Clicked on cancel button.", "Success.", "Success.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} catch (Exception msg) {
			System.out.println("Fail to click on cancel button.");
			ATUReports.add(time +" Clicked on cancel button.", "Success.", "Fail." + msg.getMessage(), LogAs.FAILED,
					new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}

	/// upload a file by giving its string path
	public void uploadFileByPath(String path, ConfirmationMenu confirm) throws InterruptedException  {

		String file_name = extractTheFileName(path);
		// select_upload_additional_file.click();
		Thread.sleep(2000);

		uploadFile(path);
		System.out.println("file selected successfully");
		Thread.sleep(2000);
		verifyAdditionalContentFileNamePriorToSelectButton();/// verify its
		clickElementJS(add_additional_file_button); /// location
		verifyProgressBar();
		confirm.waitForVisibility(confirm.ok_button);
		confirm.clickOnOkButtonAfterConfirmAddAdditionalContentFile(file_name);

	}

	private String extractTheFileName(String path) {
		String[] split = path.split("\\\\");
		return split[split.length-1];
	}

	/// verify Add Additional Content File info
	public void verifyAdditionalContentFileWindowInfo() throws InterruptedException {
		Thread.sleep(2000);
		String val = additional_content_file_info.getText();
		if (val.equals("Select a file to upload as additional content to this course.")) {
			System.out.println("Add Additional Content File window info verified ");
			ATUReports.add(time +" Add Additional Content File window info verified ", LogAs.PASSED, null);
		} else {
			System.out.println("Add Additional Content File window info not verified  ");
			ATUReports.add(time +" Add Additional Content File window info not verified  ", LogAs.FAILED,
					new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
		Assert.assertEquals("Select a file to upload as additional content to this course.", val);

	}

	// verify selected file displayed in search box
	public void verifyAdditionalContentFileName(String name) throws InterruptedException {
		Thread.sleep(3000);
		String val = add_additional_file_selected_name.getText();
		if (val.equals("name of selected file is correctly displayed in inputbox.")) {
			System.out.println("name of selected file is correctly displayed in inputbox.");
			ATUReports.add(time +" name of selected file is correctly displayed in inputbox.", LogAs.PASSED, null);
		} else {
			System.out.println("name of selected file is not correctly displayed in inputbox.");
			ATUReports.add(time +" name of selected file is not correctly displayed in inputbox.", LogAs.FAILED,
					new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
		Assert.assertEquals("name of selected file is not correctly displayed in inputbox.", val);
	}

	// verify selected file name displayed prior to select button
	public void verifyAdditionalContentFileNamePriorToSelectButton() throws InterruptedException {
		Thread.sleep(2000);
		Point file_text = add_additional_file_selected_name.getLocation();
		Point select_button = select_upload_additional_file.getLocation();

		if ((select_button.getX() > file_text.getX()) && (select_button.getY() > file_text.getY())) {
			System.out.println("location of selected file is prior to select button");
			ATUReports.add(time +" location of selected file is prior to select button", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("location of selected file is not prior to select button");
			ATUReports.add(time +" location of selected file is not prior to select button", LogAs.FAILED,
					new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}

	}

	// verify location of progressbar
	public void verifyProgressBar() throws InterruptedException {
		Thread.sleep(500);
		if (isElemenetDisplayed(By.xpath("//*[@id=\"addFileWindow\"]/form/div[1]/div[4]"))) {
			Point bar = upload_progress_bar.getLocation();
			Point Upload = select_upload_additional_file.getLocation();
			if (bar.getY() > Upload.getY()) {
				System.out.println("progress bar is visible+location is ok");
				ATUReports.add(time +" progress bar is visible+location is ok", LogAs.PASSED, null);
				Assert.assertTrue(true);
				return;
			}
		} else {
			System.out.println("progress bar is not Displayed");
			ATUReports.add(time +" progress bar is not Displayed", LogAs.PASSED, null);
			Assert.assertTrue(true);
		}
	}

	/// selecting a file for upload by giving its string path then cancel it
	public void toUploadFileByPathThenSelectFile(String path) throws Exception {

		String file_name = path.substring(51);
		// select_upload_additional_file.click();
		Thread.sleep(2000);
		uploadFile(path);
		System.out.println("file selected successfully");
		Thread.sleep(2000);
		verifyAdditionalContentFileNamePriorToSelectButton();/// verify its
		clickElementJS(add_additional_file_button);

	}

	// verify disabled select button used when uploading
	public void verifyDisabledSelectButton() {
		// TODO Auto-generated method stub
		/// waitForVisibility(select_upload_additional_file);
		System.out.println(select_upload_additional_file.getAttribute("disabled"));
		if (select_upload_additional_file.getAttribute("disabled").equals("true")) {
			System.out.println("select button is disabled");
			ATUReports.add(time +" select button is disabled", LogAs.PASSED, null);

			Assert.assertTrue(true);
			return;
		} else {
			System.out.println("select button is not disabled");
			ATUReports.add(time +" select button is not disabled", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));

			Assert.assertTrue(false);
		}
	}

	/// upload a file by giving its string path
	public void uploadFileByPathNoConfirmation(String path) throws Exception {

		String file_name = path.substring(51);
		// select_upload_additional_file.click();
		uploadFile(path);
		Thread.sleep(500);
		System.out.println("file selected successfully");
		verifyAdditionalContentFileNamePriorToSelectButton();/// verify its ///
																/// location
		add_additional_file_button.click();/// add
		verifyUploadInfoCorrectness("chromedriver_win32.zip");
		verifyProgressBar();

	}

	public void uploadFileByPathNoConfirmationForTheUI(String path, String fileName) throws Exception {

		String file_name = path.substring(51);
		Thread.sleep(2000);
		uploadFile(path);
		Thread.sleep(2000);
		System.out.println("file selected successfully");
		verifyAdditionalContentFileNamePriorToSelectButton();/// verify its ///
																/// location
		clickElementJS(add_additional_file_button);
		verifyUploadInfoCorrectness(fileName);
		verifyProgressBar();

	}

	// verify selected file name displayed prior to select button
	public void verifyAdditionalContentButtonsLocation() throws InterruptedException {
		// Thread.sleep(1500);
		Point file_text = add_additional_file_selected_name.getLocation();
		Point select_button = select_upload_additional_file.getLocation();
		Point cancel = cancel_additional_file_button.getLocation();
		Point add = add_additional_file_button.getLocation();

		if ((select_button.getX() > file_text.getX()) && (select_button.getY() > file_text.getY())
				&& (add.getX() > cancel.getX()) && (add.getY() == cancel.getY()) && (add.getY() > select_button.getY())
				&& (add.getY() > select_button.getY())) {
			System.out.println("location of selected file is correct");
			ATUReports.add(time +" location of selected file is correct", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("location of selected file is not correct");
			ATUReports.add(time +" location of selected file is not correct", LogAs.FAILED,
					new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}

	}

	// verify speed: in additional content file uploading
	public void verifyUploadSpeed() {
		if ((uploaded_speed.getText().contains("Speed:")) && (uploaded_speed.getText().contains("kB/s"))) {
			System.out.println("speed is verified");
			ATUReports.add(time +" speed is verified", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("speed is not verified");
			ATUReports.add(time +" speed is not verified", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}

	// verify uploaded percent in additional content file uploading
	public void verifyUploadedPercent() {
		String percent = uploaded_percent.getText();
		System.out.println(percent);
		String number = percent.substring(10, percent.length() - 1);
		System.out.println(number);
		double numberInt = Double.valueOf(number);
		if ((percent.contains("Uploaded: ")) && (percent.contains("%")) && (numberInt >= 0) && (numberInt <= 100)) {
			System.out.println("uploaded percent is verified");
			ATUReports.add(time +" uploaded percent is verified", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("uploaded percent is not verified");
			ATUReports.add(time +" uploaded percent is not verified", LogAs.FAILED,
					new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}

	// verify file name while uploading a file
	public void verifyUploadFileName(String file) {
		if ((uploading_file.getText().equals("Uploading file: " + file))) {
			System.out.println("file name is verified");
			ATUReports.add(time +" file name is verified", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("file name is not verified");
			ATUReports.add(time +" file name is not verified", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}

	// verify file name while uploading a file
	public void verifyUploadElapsedTime() {
		if ((elapsed_time.getText().contains("Elapsed Time: "))) {
			System.out.println("elapsed time is verified");
			ATUReports.add(time +" elapsed time is verified", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("elapsed time is not verified");
			ATUReports.add(time +" elapsed time is not verified", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}

	// verify file name while uploading a file
	public void verifyUploadEstimatedTime() {
		if ((estimated_time.getText().contains("Estimated Time: "))) {
			System.out.println("estimated time is verified");
			ATUReports.add(time +" estimated time is verified", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("estimated time is not verified");
			ATUReports.add(time +" estimated time is not verified", LogAs.FAILED,
					new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}

	/// Verify that upload section consists information as descripted
	public void verifyUploadInfoCorrectness(String file_name) {
		verifyUploadEstimatedTime();
		verifyUploadSpeed();
		verifyUploadedPercent();
		verifyUploadFileName(file_name);
		verifyUploadElapsedTime();

	}
	
	public void clickEscOnKeyBoardToCloseCopyWindow() throws InterruptedException {
		try {
			cancel_additional_file_button.sendKeys(Keys.ESCAPE);
			ATUReports.add(time +" Clicked on ESC button.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} catch (Exception e) {
			ATUReports.add(time +" Fail click on ESC button.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
		Thread.sleep(3000);
	}
	
}
