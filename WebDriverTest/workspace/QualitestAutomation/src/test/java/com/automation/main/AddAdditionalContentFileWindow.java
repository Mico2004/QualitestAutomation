package com.automation.main;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.sql.Driver;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;

public class AddAdditionalContentFileWindow extends Page {

	public AddAdditionalContentFileWindow(WebDriver browser) {
		super(browser);
		// TODO Auto-generated constructor stub
	}

	@FindBy(id = "ModalDialogHeader")
	WebElement additional_content_file_title;
	@FindBy(id = "InfoText")
	WebElement additional_content_file_info;
	@FindBy(id = "UploadFile")
	WebElement select_upload_additional_file;
	@FindBy(id = "AddFileButton")
	WebElement add_additional_file_button;
	@FindBy(xpath = "//*[@id=\"addFileWindow\"]/form/div[1]/small[1]/b")
	WebElement add_additional_file_selected_name;
	@FindBy(xpath = "//*[@id=\"addFileWindow\"]/form/div[1]/div[4]")
	WebElement upload_progress_bar;
	@FindBy(id ="CancelButton")
	WebElement cancel_additional_file_button;
	@FindBy(id ="ModalDialogHeaderWrap")
	WebElement background;
	@FindBy(xpath ="//*[@id=\"addFileWindow\"]/form/div[1]/small[1]")
	WebElement uploading_file;
	@FindBy(xpath ="//*[@id=\"addFileWindow\"]/form/div[1]/small[2]/em")
	WebElement elapsed_time;
	@FindBy(xpath ="//*[@id=\"addFileWindow\"]/form/div[1]/small[3]/em")
	WebElement estimated_time;
	@FindBy(xpath ="//*[@id=\"addFileWindow\"]/form/div[1]/small[5]")
	WebElement uploaded_percent;
	@FindBy(xpath ="//*[@id=\"addFileWindow\"]/form/div[1]/small[4]/em")
	WebElement uploaded_speed;
	
	
	
	/// verify Add Additional Content File title
	public void verifyAdditionalContentFileWindowTitle() throws InterruptedException {
		Thread.sleep(2000);
		String val = additional_content_file_title.getText();
		if (val.equals("Add Additional Content File")) {
			System.out.println("Add Additional Content File window title verified ");
			ATUReports.add("Add Additional Content File window title verified ", LogAs.PASSED, null);
		} else {
			System.out.println("Add Additional Content File window not verified  ");
			ATUReports.add("Add Additional Content File window title not verified  ", LogAs.FAILED, null);
		}
		Assert.assertEquals("Add Additional Content File", val);

	}

	////// upload additional content
	public void uploadFile(String path) throws Exception {

		// from here you can use as it wrote
		path = System.getProperty("user.dir") + path;
		select_upload_additional_file.sendKeys(path);
//		StringSelection ss = new StringSelection(path);	
//		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
//		
//		Robot robot = new Robot();
//		//focus on the webpage
//		robot.mouseMove(35,35);
//
//		clickElement(driver.findElement(By.id("main")));
//		//clickElement(driver.findElement(By.id("main")));
//		Actions builder = new Actions(driver);
//		builder.moveToElement(driver.findElement(By.id("main"))).build().perform();
//
//		builder.click();
//		// native key strokes for CTRL, V and ENTER keys
//		robot.keyPress(KeyEvent.VK_CONTROL);
//		robot.keyPress(KeyEvent.VK_V);
//		robot.keyRelease(KeyEvent.VK_V);
//		robot.keyRelease(KeyEvent.VK_CONTROL);
//		Thread.sleep(5000);
//		robot.keyPress(KeyEvent.VK_ENTER);
//		robot.keyRelease(KeyEvent.VK_ENTER);

	}

	/// upload a file by giving its string path
	public void uploadFileByPath(String path, ConfirmationMenu confirm) throws Exception {

		String file_name = path.substring(51);
		//select_upload_additional_file.click();
		Thread.sleep(2000);

		uploadFile(path);
		System.out.println("file selected successfully");
		Thread.sleep(2000);
		verifyAdditionalContentFileNamePriorToSelectButton();/// verify its
																/// location
		add_additional_file_button.click();/// add
		verifyProgressBar();
		confirm.waitForVisibility(confirm.ok_button);
		confirm.clickOnOkButtonAfterConfirmAddAdditionalContentFile(file_name);

	}

	/// verify Add Additional Content File info
	public void verifyAdditionalContentFileWindowInfo() throws InterruptedException {
		Thread.sleep(2000);
		String val = additional_content_file_info.getText();
		if (val.equals("Select a file to upload as additional content to this course.")) {
			System.out.println("Add Additional Content File window info verified ");
			ATUReports.add("Add Additional Content File window info verified ", LogAs.PASSED, null);
		} else {
			System.out.println("Add Additional Content File window info not verified  ");
			ATUReports.add("Add Additional Content File window info not verified  ", LogAs.FAILED, null);
		}
		Assert.assertEquals("Select a file to upload as additional content to this course.", val);

	}

	// verify selected file displayed in search box
	public void verifyAdditionalContentFileName(String name) throws InterruptedException {
		Thread.sleep(3000);
		String val = add_additional_file_selected_name.getText();
		if (val.equals("name of selected file is correctly displayed in inputbox.")) {
			System.out.println("name of selected file is correctly displayed in inputbox.");
			ATUReports.add("name of selected file is correctly displayed in inputbox.", LogAs.PASSED, null);
		} else {
			System.out.println("name of selected file is not correctly displayed in inputbox.");
			ATUReports.add("name of selected file is not correctly displayed in inputbox.", LogAs.FAILED, null);
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
			ATUReports.add("location of selected file is prior to select button", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("location of selected file is not prior to select button");
			ATUReports.add("location of selected file is not prior to select button", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}

	}

	// verify location of progressbar
	public void verifyProgressBar() throws InterruptedException {
		Thread.sleep(500);
		if(isElemenetDisplayed(By.xpath("//*[@id=\"addFileWindow\"]/form/div[1]/div[4]"))){
			Point bar=upload_progress_bar.getLocation();
			Point Upload = select_upload_additional_file.getLocation();
			if (bar.getY()>Upload.getY()) {
				System.out.println("progress bar is visible+location is ok");
				ATUReports.add("progress bar is visible+location is ok", LogAs.PASSED, null);
				Assert.assertTrue(true);
				return;
			}
		} else {
			System.out.println("progress bar is not Displayed");
			ATUReports.add("progress bar is not Displayed", LogAs.PASSED, null);
			Assert.assertTrue(true);
		}
	}

	/// selecting a file for upload by giving its string path then cancel it 
	public void toUploadFileByPathThenSelectFile(String path) throws Exception {

		String file_name = path.substring(51);
		//select_upload_additional_file.click();
		Thread.sleep(2000);

		uploadFile(path);
		System.out.println("file selected successfully");
		Thread.sleep(2000);
		verifyAdditionalContentFileNamePriorToSelectButton();/// verify its
		add_additional_file_button.click();
	

	}
//verify disabled select button used when uploading 
	public void verifyDisabledSelectButton() {
		// TODO Auto-generated method stub
	///	waitForVisibility(select_upload_additional_file);
	System.out.println(select_upload_additional_file.getAttribute("disabled"));
		if (select_upload_additional_file.getAttribute("disabled").equals("true")) {
			System.out.println("select button is disabled");
			ATUReports.add("select button is disabled", LogAs.PASSED, null);

			Assert.assertTrue(true);
			return;
		} else {
			System.out.println("select button is not disabled");
			ATUReports.add("select button is not disabled", LogAs.FAILED, null);

			Assert.assertTrue(false);
		}
	}
	/// upload a file by giving its string path
		public void uploadFileByPathNoConfirmation(String path) throws Exception {

			String file_name = path.substring(51);
			//select_upload_additional_file.click();
			Thread.sleep(2000);


			uploadFile(path);
			Thread.sleep(2000);
			System.out.println("file selected successfully");
			Thread.sleep(2000);
			verifyAdditionalContentFileNamePriorToSelectButton();/// verify its
																	/// location
			add_additional_file_button.click();/// add
			verifyProgressBar();
			
		}
		// verify selected file name displayed prior to select button
		public void verifyAdditionalContentButtonsLocation() throws InterruptedException {
			Thread.sleep(2000);
			Point file_text = add_additional_file_selected_name.getLocation();
			Point select_button = select_upload_additional_file.getLocation();
            Point cancel=cancel_additional_file_button.getLocation();
            Point add=add_additional_file_button.getLocation();
            
			if ((select_button.getX() > file_text.getX()) && (select_button.getY() > file_text.getY())&&(add.getX()>cancel.getX())&&(add.getY()==cancel.getY())&&(add.getY()>select_button.getY())&&(add.getY()>select_button.getY())) {
				System.out.println("location of selected file is correct");
				ATUReports.add("location of selected file is correct", LogAs.PASSED, null);
				Assert.assertTrue(true);
			} else {
				System.out.println("location of selected file is not correct");
				ATUReports.add("location of selected file is not correct", LogAs.FAILED, null);
				Assert.assertTrue(false);
			}

		}
		//verify speed: in additional content file uploading
		public void verifyUploadSpeed()
		{
			if ((uploaded_speed.getText().contains("Speed:"))&&(uploaded_speed.getText().contains("kB/s"))) {
				System.out.println("speed is verified");
				ATUReports.add("speed is verified", LogAs.PASSED, null);
				Assert.assertTrue(true);
			} else {
				System.out.println("speed is not verified");
				ATUReports.add("speed is not verified", LogAs.FAILED, null);
				Assert.assertTrue(false);
			}
		}
		//verify uploaded percent in additional content file uploading
		public void verifyUploadedPercent()
		{
			String percent=uploaded_percent.getText();
			System.out.println(percent);	
			String number=percent.substring(10,percent.length()-1);
			System.out.println(number);	
			double numberInt= Double.valueOf(number);
			if ((percent.contains("Uploaded: "))&&(percent.contains("%"))&&(numberInt>=0)&&(numberInt<=100)) {
				System.out.println("uploaded percent is verified");
				ATUReports.add("uploaded percent is verified", LogAs.PASSED, null);
				Assert.assertTrue(true);
			} else {
				System.out.println("uploaded percent is not verified");
				ATUReports.add("uploaded percent is not verified", LogAs.FAILED, null);
				Assert.assertTrue(false);
			}
		}
		//verify file name while uploading a file
		public void verifyUploadFileName(String file)
		{
			if ((uploading_file.getText().equals("Uploading file: "+file))) {
				System.out.println("file name is verified");
				ATUReports.add("file name is verified", LogAs.PASSED, null);
				Assert.assertTrue(true);
			} else {
				System.out.println("file name is not verified");
				ATUReports.add("file name is not verified", LogAs.FAILED, null);
				Assert.assertTrue(false);
			}
		}
		//verify file name while uploading a file
		public void verifyUploadElapsedTime()
		{
			if ((elapsed_time.getText().contains("Elapsed Time: "))) {
				System.out.println("elapsed time is verified");
				ATUReports.add("elapsed time is verified", LogAs.PASSED, null);
				Assert.assertTrue(true);
			} else {
				System.out.println("elapsed time is not verified");
				ATUReports.add("elapsed time is not verified", LogAs.FAILED, null);
				Assert.assertTrue(false);
			}
		}
		//verify file name while uploading a file
		public void verifyUploadEstimatedTime()
		{
			if ((estimated_time.getText().contains("Estimated Time: "))) {
				System.out.println("estimated time is verified");
				ATUReports.add("estimated time is verified", LogAs.PASSED, null);
				Assert.assertTrue(true);
			} else {
				System.out.println("estimated time is not verified");
				ATUReports.add("estimated time is not verified", LogAs.FAILED, null);
				Assert.assertTrue(false);
			}
		}
	///	Verify that upload section consists information as descripted
		public void  verifyUploadInfoCorrectness(String file_name)
		{
			verifyUploadEstimatedTime();
			verifyUploadSpeed();
			verifyUploadedPercent();
			verifyUploadFileName(file_name);
			verifyUploadElapsedTime();
			
		
		}
		
}
