package com.automation.main;

import java.io.File;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.lang.model.util.ElementScanner6;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.bcel.generic.RETURN;
import org.eclipse.jetty.util.log.Log;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.sun.jna.platform.win32.OaIdl.EXCEPINFO;
import com.thoughtworks.selenium.webdriven.commands.DragAndDrop;

import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;
import junit.framework.Assert;

///@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class Page {
	public String pageTitle;
	public String pageUrl;
	public WebDriver driver;
	public WebDriverWait wait;
	@FindBy(id = "SignOutLink")
	WebElement sign_out;

	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}

	public Page(WebDriver browser) {
		driver = browser;
		wait = new WebDriverWait(driver, 30);
		ATUReports.setWebDriver(driver);
	}

	public void clickElement(WebElement element) // clicking element
	{
		try {
			element.click();
			System.out.println("Clicked on element.");
			ATUReports.add("Clicked on element.", "True.", "True.", LogAs.PASSED, null);
		} catch(Exception msg) {
			System.out.println("Fail to click on element.");
			ATUReports.add("Clicked on element.", "True.", "False", LogAs.FAILED, null);
		}
		
	}

	public void setElementText(WebElement element, String text) // setting
																// element text
	{
		element.clear();
		element.sendKeys(text);
		Assert.assertEquals(text, element.getAttribute("value"));

	}

	public boolean verifyElement(WebElement element) {
		try {
			element.getTagName();
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public String getPageUrl() {
		return pageUrl;
	}

	public String getPageTitle() {
		return pageTitle;
	}

	public void setPageUrl(String web) {
		pageUrl = web;
	}

	public void setPageTitle(String title) {
		pageTitle = title;
	}

	public void waitForVisibility(WebElement element)// visibility of an element
	{
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	public boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	/// hover to element and click on it
	public void moveToElementAndClick(WebElement element, WebDriver driver) throws NoSuchElementException {
		try {
			Actions builder = new Actions(driver);
			builder.moveToElement(element).click().build().perform();

		} catch (NoSuchElementException e) {
			/// Log.();
		}
	}

	public Action moveToElement(WebElement element, WebDriver driver) throws NoSuchElementException {
		try {
			Actions builder = new Actions(driver);
			Action move_to = builder.moveToElement(element).build();
			return move_to;

		} catch (NoSuchElementException e) {
			return null;
			/// Log.();
		}
	}

	public boolean isClickable(WebElement driver) {// is element clickable
		try {

			wait.until(ExpectedConditions.elementToBeClickable(driver));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public void refresh() {
		driver.navigate().refresh();
	}

	public String getBackGroundColor(WebElement element)// get background by an
														// element
	{
		String text = element.getCssValue("background-color").toString();
		// Split css value of rgb
		String[] numbers = text.replace("rgba(", "").replace(")", "").split(",");
		int number1 = Integer.parseInt(numbers[0]);
		numbers[1] = numbers[1].trim();
		int number2 = Integer.parseInt(numbers[1]);
		numbers[2] = numbers[2].trim();
		int number3 = Integer.parseInt(numbers[2]);
		return String.format("#%02x%02x%02x", number1, number2, number3);
	}

	public String[] getStringFromElement(List<WebElement> text)/// text
																/// extracted
																/// from
																/// elements
	{
		String[] linkTexts = new String[text.size()];
		int i = 0;

		// extract the link texts of each link element
		for (WebElement e : text) {
			linkTexts[i] = e.getText();
			i++;
		}
		return linkTexts;
	}

	public void loadPage(String Url, String title) throws InterruptedException {/// load
																				/// page
																				/// withe
																				/// webdriver

		driver.get(Url);// "https://mta-qabr.tegrity.com/#/login"
		for (int second = 0;; second++) {
			if (second >= 60)
				Assert.fail("timeout");
			try {
				if (driver.getTitle().equals(title))// check
					ATUReports.add(" load page succeeded", LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP)); // if
																													// list
																													// of
																													// courses
																													// are
																													// present
																													// "Tegrity
																													// Lecture
																													// Capture"

				break;
			} catch (Exception e) {
				ATUReports.add(" load page failed", LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
			}

			Thread.sleep(1000);
		}

	}

	// description="verify element is present")
	public boolean verifyElementTest(WebElement element) throws InterruptedException {
		for (int second = 0;; second++) {
			if (second >= 60)
				return false;
			try {
				if (verifyElement(element))// check if element is present
				{
					return true;

				}
			} catch (Exception e) {
			}

			Thread.sleep(1000);
		}
	}

	public boolean verifyColor(String expected, WebElement element) throws InterruptedException {// returns
																									// if
																									// color
																									// is
																									// equal
																									// or
																									// not

		//
		// record.moveToElementAndClick(record.recording_tasks_button, driver);
		waitForVisibility(element);
		String actual_color = element.getCssValue("color");
		return expected.equals(actual_color);

	}

	/// verify correct date
	public void verifyDate(String date) {
		String[] split = date.split("/");
		String checkdays = split[1];

		String checkmonths = split[0];

		String checkyear = split[2];
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date datecurrent = new Date();
		if ((Integer.valueOf(checkmonths) > 12) || (Integer.valueOf(checkmonths) < 1)
				|| (Integer.valueOf(checkdays) < 1) || (Integer.valueOf(checkdays) > 31)
				|| (Integer.valueOf(checkyear) < 1995)) {
			System.out.println(" date is not correctly displaied");
			ATUReports.add(" date is  not correctly displaied", LogAs.FAILED, null);
			Assert.assertTrue(false);
		} else {
			System.out.println(" date is correctly displaied");
			ATUReports.add(" date is correctly displaied", LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
			Assert.assertTrue(true);
		}
	}

	/// verify correct date
	public void verifyTimeDuration(String duration) {
		String[] split = duration.split(":");
		String checkminutes = split[1];

		String checkhour = split[0];

		String checkseconds = split[2];

		if ((Integer.valueOf(checkhour) > 24) || (Integer.valueOf(checkhour) < 0) || (Integer.valueOf(checkminutes) < 0)
				|| (Integer.valueOf(checkminutes) > 59) || (Integer.valueOf(checkseconds) > 59)
				|| (Integer.valueOf(checkseconds) < 0)) {
			ATUReports.add(" time is  not correctly displaied", LogAs.FAILED, null);
			Assert.assertTrue(false);
		} else {

			ATUReports.add(" time is correctly displaied", LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
			Assert.assertTrue(true);
		}
	}

	/// sign out from any page except Login page
	public void signOut() throws InterruptedException {
		sign_out.click();

		for (int second = 0;; second++) {
			if (second >= 60) {
				Assert.assertTrue(false);
				ATUReports.add(" Login page timeout", LogAs.FAILED, null);
			}
			try {
				if (driver.getTitle().equals("Tegrity Lecture Capture"))// check
																		// if
																		// element
																		// is
																		// present
				{
					Assert.assertTrue(true);
					ATUReports.add(" Login page correctly displaied", LogAs.PASSED, null);
					break;
				}
			} catch (Exception e) {
			}

			Thread.sleep(1000);
		}
	}

	// counts number of times pattern string appears in sourcecode
	public int patternAppearenceinString(String sourcecode, String pattern) {
		int counter = 0;
		int index = sourcecode.indexOf(pattern);

		while (index != -1) {
			counter++;
			sourcecode = sourcecode.substring(index + 1);
			index = sourcecode.indexOf(pattern);
		}

		return counter;
	}

	/// search if word exists in a String
	public static boolean searchWordInString(String subString, String mainString) {
		boolean foundme = false;
		int max = mainString.length() - subString.length();

		// Java's Default "contains()" Method
		System.out.println(mainString.contains(subString) ? "mainString.contains(subString) Check Passed.."
				: "mainString.contains(subString) Check Failed..");

		// Implement your own Contains Method with Recursion
		checkrecusion: for (int i = 0; i <= max; i++) {
			int n = subString.length();

			int j = i;
			int k = 0;

			while (n-- != 0) {
				if (mainString.charAt(j++) != subString.charAt(k++)) {
					continue checkrecusion;
				}
			}
			foundme = true;
			break checkrecusion;
		}
		System.out.println(foundme ? "\nImplement your own Contains() Method - Result: Yes, Match Found.."
				: "\nImplement your own Contains() Method - Result:  Nope - No Match Found..");
		return foundme;
	}

	// this function returns true if element has underline or not
	public boolean verifyUnderlineCss(WebElement element, WebDriver driver) throws InterruptedException {
		String underline = "";
		for (int second = 0;; second++) {
			if (second >= 40) {

				Assert.assertTrue(false);
				ATUReports.add(" no underline", LogAs.FAILED, null);
				return false;
			}
			try {
				Thread.sleep(500);
				moveToElement(element, driver).perform();
				underline = element.getCssValue("text-decoration");

				if (underline.equals("underline"))// check if element is present
				{
					ATUReports.add(" has underline", LogAs.PASSED, null);
					System.out.println(" has underline");
					Assert.assertTrue(true);
					return true;

				}
			} catch (Exception e) {
			}

			Thread.sleep(1000);
		}
	}

	/// claculate md5 for a file used to compare 2 files
	public static String getMD5Sum(String filePath) throws Exception {

		MessageDigest md = MessageDigest.getInstance("MD5");

		try (InputStream is = Files.newInputStream(Paths.get(filePath))) {
			DigestInputStream dis = new DigestInputStream(is, md);
			int read = 0;
			do {
				read = dis.read();
			} while (read > -1);
		}
		byte[] digest = md.digest();
		digest.toString();
		String result = "";

		for (int i = 0; i < digest.length; i++) {
			result += Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1);
		}
		return result;
	}

	/// this functions convert any size to kb,mb,gb,bytes
	public double convertFileSize(String path, String unit) {
		double result = 0;
		try {
			File file = new File(path);
			if (file.exists()) {

				double bytes = file.length();
				double kilobytes = (bytes / 1024);
				double megabytes = (kilobytes / 1024);
				double gigabytes = (megabytes / 1024);

				switch (unit) {
				case "bytes":
					return bytes;
				case "kb":
					return kilobytes;
				case "mb":
					return megabytes;
				case "gb":
					return gigabytes;

				}

			} else {
				System.out.println("file doe not exist");
				return -1;
			}

		} catch (Exception e) {
			System.out.println("an error occured");
		}
		return -1;
	}

	// get font color by an element
	public String getFontColor(WebElement element)
	{
		String text = element.getCssValue("color").toString();
		// Split css value of rgb
		String[] numbers = text.replace("rgba(", "").replace(")", "").split(",");
		int number1 = Integer.parseInt(numbers[0]);
		numbers[1] = numbers[1].trim();
		int number2 = Integer.parseInt(numbers[1]);
		numbers[2] = numbers[2].trim();
		int number3 = Integer.parseInt(numbers[2]);
		return String.format("#%02x%02x%02x", number1, number2, number3);
	}
	
	// This function get WebElement and String, and force that WebElement to be selected
	public void forceWebElementToBeSelected(WebElement web_element, String description) {
		if(web_element.isSelected()) {
			System.out.println(description + " already selected.");
			ATUReports.add(description + " selected.", "True.", "True.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			try {
				web_element.click();
				System.out.println(description + " selected.");
				ATUReports.add(description + " selected.", "True.", "True.", LogAs.PASSED, null);
				Assert.assertTrue(true);
			} catch (Exception msg) {
				System.out.println("Fail to select " + description + ".");
				ATUReports.add(description + " selected.", "True.", "False.", LogAs.FAILED, null);
				Assert.assertTrue(false);
			}
		}
	}
	
	// This function get WebElement and String, and force that WebElement to be unselected
	public void forceWebElementToBeUnselected(WebElement web_element, String description) {
		if(!web_element.isSelected()) {
			System.out.println(description + " already unselected.");
			ATUReports.add(description + " unselected.", "True.", "True.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			try {
				web_element.click();
				System.out.println(description + " unselected.");
				ATUReports.add(description + " unselected.", "True.", "True.", LogAs.PASSED, null);
				Assert.assertTrue(true);
			} catch (Exception msg) {
				System.out.println("Fail to unselect " + description + ".");
				ATUReports.add(description + " unselected.", "True.", "False.", LogAs.FAILED, null);
				Assert.assertTrue(false);
			}
		}
	}
	
	// This function verify that WebElement is displayed, and String with description
	public void verifyWebElementDisplayed(WebElement web_element, String description) {
		if(web_element.isDisplayed()) {
			System.out.println(description + " is displayed.");
			ATUReports.add(description + " is displayed.", "True.", "True.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println(description + " is not displayed.");
			ATUReports.add(description + " is displayed.", "True.", "False.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
	}
	
	// This function verify that WebElement is not displayed, and String with description
	public void verifyWebElementNotDisplayed(WebElement web_element, String description) {
		if(!web_element.isDisplayed()) {
			System.out.println(description + " is not displayed.");
			ATUReports.add(description + " is not displayed.", "True.", "True.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println(description + " is not displayed.");
			ATUReports.add(description + " is not displayed.", "True.", "False.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
	}
	
	// This function verify that WebElement title/hint is target string
	public void verifyWebElementHaveTargetAttributeTitle(WebElement web_element, String target_text) {
		if(web_element.getAttribute("title").replaceAll("  ", " ").equals(target_text.replaceAll("  ", " "))) {
			System.out.println(target_text + " is displayed in title.");
			ATUReports.add("Target text is displayed in title.", target_text, target_text, LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println(target_text + " is not displayed in title.");
			ATUReports.add("Target text is displayed in title.", target_text, web_element.getAttribute("title"), LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
	}
	
	// This function "send" string to WebElement
	public void sendStringToWebElement(WebElement we, String string_to_send) {
		try {
			we.sendKeys(string_to_send);
			System.out.println("String sent to WebElement: " + string_to_send);
			ATUReports.add("String send to WebElement.", string_to_send, string_to_send, LogAs.PASSED, null);
		} catch(Exception msg) {
			System.out.println("String do not send to WebElement: " + string_to_send);
			ATUReports.add("String send to WebElement.", string_to_send, "", LogAs.FAILED, null);
		}
	}
}
