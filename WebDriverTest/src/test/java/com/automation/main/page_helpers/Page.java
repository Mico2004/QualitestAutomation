package com.automation.main.page_helpers;

import java.awt.AWTException;
import java.awt.Robot;
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
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;


import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.Command;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.LoggingAssert;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;
import junitx.util.PropertyManager;


///@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class Page {
	public String pageTitle;
	public String pageUrl;
	public WebDriver driver;
	public WebDriverWait wait;
	@FindBy(id = "InstituteLogotype")
	public WebElement logo;
	@FindBy(id = "SignOutLink")
	public WebElement sign_out;

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
		String text = element.getText();
		try {

			wait.until(ExpectedConditions.elementToBeClickable(element));
			element.click();
			System.out.println("Clicked on " + element.getText() + " element");
			ATUReports.add("Clicked on " + text + " element", "Clicked succeeded.", "Clicked succeeded..", LogAs.PASSED,
					null);
		} catch (Exception msg) {

			try {
				System.out.println("Clicked failed trying again with JS");
				String id = element.getAttribute("id");
				((JavascriptExecutor) driver).executeScript("document.getElementById(\"" + id + "\").click();");
				ATUReports.add("Clicked on " + text + " element", "Clicked succeeded.", "Clicked succeeded..", LogAs.PASSED,
						null);

			} catch (Exception e1) {
				
			}
		}

	}
	
	public void clickElementJS(WebElement element) // clicking element
	{
		String text = element.getText();
		try {
			String id = element.getAttribute("id");
			((JavascriptExecutor) driver).executeScript("document.getElementById(\"" + id + "\").click();");
			ATUReports.add("Clicked on " + text + " element", "Clicked succeeded.", "Clicked succeeded..", LogAs.PASSED,
					null);		
		} catch (Exception msg) {

			try {
				System.out.println("Clicked failed trying again with selenium");
				wait.until(ExpectedConditions.elementToBeClickable(element));
				element.click();
				System.out.println("Clicked on " + element.getText() + " element");
				ATUReports.add("Clicked on " + text + " element", "Clicked succeeded.", "Clicked succeeded..", LogAs.PASSED,
						null);
			} catch (Exception e1) {
				ATUReports.add("Clicked on " + text + " element", "Clicked succeeded.", "Clicked failed:"+msg.getMessage(), LogAs.WARNING,
						null);
				
			}
		}

	}

	// clicking element
	public void clickElementWithDescription(WebElement element, String description) {
		try {
			element.click();
			System.out.println("Clicked on " + description + " element.");
			ATUReports.add("Clicked on " + description + " element.", "True.", "True.", LogAs.PASSED, null);
		} catch (Exception msg) {
			System.out.println("Fail to click on " + description + " element.");
			ATUReports.add("Clicked on " + description + " element.", "True.", "False", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}

	}

	public void setElementText(WebElement element, String text) // setting
																// element text
	{
		element.clear();
		element.sendKeys(text);
		Assert.assertEquals(text, element.getAttribute("value"));

	}
	
	public void setElementTextJS(WebElement element, String text) // setting
	// element text
	{	
		String id="";
	
		try {
			id = element.getAttribute("id");
			((JavascriptExecutor) driver).executeScript("document.getElementById(\"" + id + "\").setAttribute(\"text\", \""+text+"\");");
			ATUReports.add("Set text: " + id + " element", "Text was set", "Text was set", LogAs.PASSED,
					null);		
		} catch (Exception msg) {

			try {
				System.out.println("Set text failed trying again with selenium");
				wait.until(ExpectedConditions.elementToBeClickable(element));
				element.sendKeys(text);
				System.out.println("Set text:" + element.getText());
				ATUReports.add("Set text: " + id + " element", "Text was set", "Text was set", LogAs.PASSED,
						null);		
			} catch (Exception e1) {
				ATUReports.add("Set text: " + id + " element", "Text was set", "Text was set", LogAs.PASSED,
						new CaptureScreen(ScreenshotOf.BROWSER_PAGE));	
				
			}
		}
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
		try {
			Thread.sleep(1000);	
			wait.until(ExpectedConditions.visibilityOf(element));
		} catch (Exception e) {
			System.out.println("Waiting for element visibiliy failed");
			ATUReports.add("Waiting for element visibility",element.getText(),"Element is visibile before timout","Element is not visible after timeout",LogAs.WARNING,null);
			e.printStackTrace();
		}
	}

	public boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public String getTextFromWebElement(WebElement element) {

		try {
			return element.getText();
		} catch (StaleElementReferenceException e) {
			System.out.println("Attempting to recover from StaleElementReferenceException ...");
			return getTextFromWebElement(element);
		} catch (NoSuchElementException ele) {
			System.out.println("Attempting to recover from NoSuchElementException ...");
			return getTextFromWebElement(element);
		}

	}

	public boolean isElemenetDisplayed(By by) {
		try {
			driver.findElement(by).isDisplayed();

			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public WebElement getStaleElem(By by, WebDriver driver) {
		try {
			return driver.findElement(by);
		} catch (StaleElementReferenceException e) {
			System.out.println("Attempting to recover from StaleElementReferenceException ...");
			return getStaleElem(by, driver);
		} catch (NoSuchElementException ele) {
			System.out.println("Attempting to recover from NoSuchElementException ...");
			return getStaleElem(by, driver);
		}
	}

	/// hover to element and click on it
	public void moveToElementAndClick(WebElement element, WebDriver driver) throws NoSuchElementException {
		waitForVisibility(element);
		try {
			Actions builder = new Actions(driver);
			builder.moveToElement(element).click().build().perform();
			System.out.println("Hovered and clicked on WebElement.");
			ATUReports.add("Hovered and clicked on WebElement.", "True.", "True.", LogAs.PASSED, null);
			Assert.assertTrue(true);

		} catch (NoSuchElementException e) {
			System.out.println("Not hovered and clicked on WebElement.");
			ATUReports.add("Hovered and clicked on WebElement.", "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
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

	
	public void moveToElementAndPerform(WebElement element, WebDriver driver) throws NoSuchElementException, InterruptedException {


		waitForVisibility(element);
		try {
			Robot robot = new Robot();
			robot.mouseMove(-100, 100);
			Actions builder = new Actions(driver);
			Action mouseOver = builder.moveToElement(element).build();
			mouseOver.perform();

		} catch (NoSuchElementException e) {
		
			/// Log.();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		String text = "";
		try {
			text = element.getCssValue("background-color").toString();
		} catch (Exception msg) {
			return "";
		}
		try {
			// Split css value of rgb
			String[] numbers = text.replace("rgba(", "").replace(")", "").split(",");
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
					ATUReports.add(" load page succeeded",Url,LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP)); // if
																													// list
				break;
			} catch (Exception e) {
				ATUReports.add(" load page failed",Url ,LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
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

	public boolean verifyColor(String expected, WebElement element) throws InterruptedException {// returns//
																									// if//
																									// color//	// record.moveToElementAndClick(record.recording_tasks_button, driver);
		waitForVisibility(element);
		String actual_color = element.getCssValue("color");
		return expected.equals(actual_color);

	}
	
	
	public  boolean isElementPresent(WebElement element) {
		boolean flag = false;
		try {
			if (element.isDisplayed()|| element.isEnabled())
				flag = true;
		} catch (NoSuchElementException e) {
			flag = false;
		} catch (StaleElementReferenceException e) {
			flag = false;
		}
		return flag;
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
			ATUReports.add(" date is  not correctly displaied", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
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
			ATUReports.add(" time is  not correctly displaied", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		} else {

			ATUReports.add(" time is correctly displaied", LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
			Assert.assertTrue(true);
		}
	}

	/// sign out from any page except Login page
	public void signOut() {

		try {	
		System.out.println("signOut1");		
		((JavascriptExecutor) driver).executeScript("document.getElementById(\"SignOutLink\").click();");
		Thread.sleep(2000);
		new WebDriverWait(driver, 10).until(ExpectedConditions.titleContains("Tegrity Lecture Capture"));
		System.out.println("signOut3");
		for (int second = 0;second<=60; second++) {
		
			if (second >= 60) {
				System.out.println("LogOut from user not succeeded.");
				ATUReports.add(" Login page timeout", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Assert.assertTrue(false);
			}
		
				if (driver.getTitle().equals("Tegrity Lecture Capture"))// check// if// element// is// present
				{
					System.out.println("Signout from user succeeded");
					ATUReports.add("Sign Out succeeded", LogAs.PASSED, null);
					Assert.assertTrue(true);
					break;
				} else if(driver.getTitle().equals("Tegrity - Courses")){
					System.out.println("LogOut from user not succeeded 1");
					Thread.sleep(4000);
					((JavascriptExecutor) driver).executeScript("document.getElementById(\"SignOutLink\").click();");					
				}else{
					Thread.sleep(3000);
				}
			}		
		} catch (Exception e) {
			System.out.println("LogOut from user not succeeded 3");
			ATUReports.add("Sign Out failed", e.getMessage(), LogAs.FAILED,new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}	
		System.out.println("signOut5");
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
				ATUReports.add(" no underline", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
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
	public String getFontColor(WebElement element) {
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

	// This function get WebElement and String, and force that WebElement to be
	// selected
	public void forceWebElementToBeSelected(WebElement web_element, String description) {
		if (web_element.isSelected()) {
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
				ATUReports.add(description + " selected.", "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Assert.assertTrue(false);
			}
		}
	}

	// This function get WebElement and String, and force that WebElement to be
	// unselected
	public void forceWebElementToBeUnselected(WebElement web_element, String description) {
		try{
			waitForVisibility(web_element);
		if (!web_element.isSelected()) {
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
				ATUReports.add(description + " unselected.", "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Assert.assertTrue(false);
			}
		}
		}catch(Exception e){
			System.out.println("Fail to unselect " + description + ".");
			ATUReports.add(description + " unselected.", "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);			
		}
	}

	// This function verify that WebElement is displayed, and String with
	// description
	public void verifyWebElementDisplayed(WebElement web_element, String description) {
			
		
		
		waitForVisibility(web_element);
		if (web_element.isDisplayed()) {
			System.out.println(description + " is displayed.");
			ATUReports.add(description + " is displayed.", "True.", "True.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println(description + " is not displayed.");
			ATUReports.add(description + " is not displayed.", "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}

	// This function verify that WebElement is not displayed, and String with
	// description
	public void verifyWebElementNotDisplayed(WebElement web_element, String description) {
		if (!web_element.isDisplayed()) {
			System.out.println(description + " is not displayed.");
			ATUReports.add(description + " is not displayed.", "True.", "True.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println(description + " is displayed.");
			ATUReports.add(description + " is displayed.", "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}

	// This function verify that WebElement title/hint is target string
	public void verifyWebElementHaveTargetAttributeTitle(WebElement web_element, String target_text) {
		if (web_element.getAttribute("title").replaceAll("  ", " ").equals(target_text.replaceAll("  ", " "))) {
			System.out.println(target_text + " is displayed in title.");
			ATUReports.add("Target text is displayed in title.", target_text, target_text, LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println(target_text + " is not displayed in title.");
			ATUReports.add("Target text is not displayed in title.", target_text, web_element.getAttribute("title"),
					LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}

	// This function "send" string to WebElement
	public void sendStringToWebElement(WebElement we, String string_to_send) {
		try {
			we.click();
			we.sendKeys(string_to_send);
			System.out.println("String sent to WebElement: " + string_to_send);
			ATUReports.add("String send to WebElement.", string_to_send, string_to_send, LogAs.PASSED, null);
		} catch (Exception msg) {
			System.out.println("String do not send to WebElement: " + string_to_send);
			ATUReports.add("String do not send to WebElement.", string_to_send, "", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
	}

	// Verify that
	// The next result display below the current result in case there is next
	// result.

	public void verifyThatNextResultDisplayBelowCurrentResultInCaseThereIsNextResult(List<WebElement> list,int number) {

		if (list.size() > 1) {
			boolean not_correct = false;
			int prepoint = list.get(0).getLocation().y;
			for (int i = 1; i < list.size() - 1; i++) {
				int currpoint = list.get(i).getLocation().y;
				if (prepoint < currpoint) {
					prepoint = currpoint;
					continue;
				} else {
					System.out.println("!!!!!!!!");
					System.out.println(prepoint);
					System.out.println(currpoint);
					System.out.println("!!!!!!!");
					not_correct = true;
					break;
				}
			}

			if (!not_correct) {
				System.out.println("Verified that next result display below the current result.");
				ATUReports.add("Verified that next result display below the current result.", "True.", "True.",
						LogAs.PASSED, null);
			} else {
				System.out.println("Not verified that next result display below the current result.");
				ATUReports.add("Verified that next result display below the current result.", "True.", "False",
						LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			}

		 } else {
			   System.out.println("There is 1 or 0 results.");
			   if(number == 2)
			   ATUReports.add("There is 1 or 0 results.", "Expect for more then 1 results.", "1 or 0 results.",
			     LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			   else {
			    ATUReports.add("There is 1 or 0 results.", "Expect exactly 1 results.", "1 or 0 results.",
			      LogAs.PASSED, null);
			   }
		 }
	}
	// Verify that
		// The next result display below the current result in case there is next
		// result.
		public void verifyThatNextResultDisplayBelowCurrentResultInCaseThereIsNextResultOneResult(List<WebElement> list) {
			if (list.size() > 1) {
				boolean not_correct = false;
				int prepoint = list.get(0).getLocation().y;
				for (int i = 1; i < list.size() - 1; i++) {
					int currpoint = list.get(i).getLocation().y;
					if (prepoint < currpoint) {
						prepoint = currpoint;
						continue;
					} else {
						System.out.println("!!!!!!!!");
						System.out.println(prepoint);
						System.out.println(currpoint);
						System.out.println("!!!!!!!");
						not_correct = true;
						break;
					}
				}

				if (!not_correct) {
					System.out.println("Verified that next result display below the current result.");
					ATUReports.add("Verified that next result display below the current result.", "True.", "True.",
							LogAs.PASSED, null);
				} else {
					System.out.println("Not verified that next result display below the current result.");
					ATUReports.add("Verified that next result display below the current result.", "True.", "False",
							LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				}
			} else {
				System.out.println("There is 1 or 0 results.");
				ATUReports.add("There is 1 or 0 results.", "Expect for more then 1 results.", "1 or 0 results.",
						LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			}
		}
	
	/// this function verifies visibility of university logo all helpers will
	/// inherit from page
	public void verifyLogoVisibilityAndLocation() {
		org.openqa.selenium.Point logo_loc = logo.getLocation();
		if (logo.isDisplayed()) {

			System.out.println("verified that logo is displaied");
			ATUReports.add("verified that logo is displaied", "university logo", "visible", "visible", LogAs.PASSED,
					null);
			Assert.assertTrue(true);
			System.out.println(logo_loc.getX() + " " + logo_loc.getY());
			if ((logo_loc.getX() < 100) && (logo_loc.getY() < 100)) {
				System.out.println("verified logo location");
				ATUReports.add("verify logos' location", "university logo", "accurate location", "accurate location",
						LogAs.PASSED, null);
				Assert.assertTrue(true);
			} else {
				System.out.println("not verified logo location");
				ATUReports.add("verify logos' location", "university logo", "accurate location", "bad location",
						LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Assert.assertTrue(false);
			}

		} else {
			System.out.println("Not verified that logo is displaied");
			ATUReports.add("Not verified that logo is displaied", "university logo", "visible", "not visible",
					LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}

	// This function get two WebElement and check if the first is to the right
	// (by location) to the second
	public void isFirstWebElementToTheRightSecondWebElement(WebElement first_webelement, WebElement second_webelement) {
		if (first_webelement.getLocation().x <= second_webelement.getLocation().x) {
			System.out.println("The first WebElement is to the right to the second WebElement.");
			ATUReports.add("The first WebElement is to the right to the second WebElement.", "True.", "True.",
					LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("The first WebElement is not to the right to the second WebElement.");
			ATUReports.add("The first WebElement is to the right to the second WebElement.", "True.", "False.",
					LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}

	// This function get WebElement and excpected text, and check if that text
	// appear in the WebElement
	public void verifyWebElementTargetText(WebElement web_element, String target_text) {
		if (web_element.getText().equals(target_text)) {
			System.out.println("Verified that target web element have target text: " + target_text);
			ATUReports.add("WebElement have target text.", target_text, target_text, LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Not verified that target web element have target text: " + target_text + " != "
					+ web_element.getText());
			ATUReports.add("WebElement have target text.", target_text, web_element.getText(), LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}

	// This function verify that there is vertical scrolling
	public void verifyThereVerticalScrolling() {
		boolean scrolling_bar = (boolean) ((JavascriptExecutor) driver).executeScript(
				"return Math.max(document.body.scrollHeight, document.body.offsetHeight, document.documentElement.clientHeight, document.documentElement.scrollHeight, document.documentElement.offsetHeight)>document.documentElement.clientHeight ;");
		if (scrolling_bar) {
			System.out.println("Verified that there is vertical scrolling.");
			ATUReports.add("Verify there is vertical scrolling.", "True.", "True.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Not verified that there is vertical scrolling.");
			ATUReports.add("Verify there is vertical scrolling.", "True.", "False", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}

	// This function verify that there is horizontal scrolling
	public void verifyThereHorizontalScrolling() {
		boolean scrolling_bar = (boolean) ((JavascriptExecutor) driver).executeScript(
				"return Math.max(document.body.scrollWidth, document.body.offsetWidth, document.documentElement.clientWidth, document.documentElement.scrollWidth, document.documentElement.offsetWidth)>document.documentElement.clientWidth ;");
		if (scrolling_bar) {
			System.out.println("Verified that there is horizontal scrolling.");
			ATUReports.add("Verify there is horizontal scrolling.", "True.", "True.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Not verified that there is horizontal scrolling.");
			ATUReports.add("Verify there is horizontal scrolling.", "True.", "False", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}

	// This function verify that WebElement is selected
	public void verifyWebElementSelected(WebElement webElement) {
		if (webElement.isSelected()) {
			System.out.println("Verfied that WebElement selected.");
			ATUReports.add("Verfied that WebElement selected.", "True.", "True.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Not verifed that WebElement selected.");
			ATUReports.add("Verfied that WebElement selected.", "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}

	// This function verify that WebElement is not selected
	public void verifyWebElementNotSelected(WebElement webElement) {
		if (webElement.isSelected()) {
			System.out.println("Not verfied that WebElement not selected.");
			ATUReports.add("Verfied that WebElement not selected.", "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		} else {
			System.out.println("Verfied that WebElement not selected.");
			ATUReports.add("Verfied that WebElement not selected.", "True.", "True.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		}
	}

	// This function exit inner frame
	public void exitInnerFrame() {
		for (String handler : driver.getWindowHandles()) {
			driver.switchTo().window(handler);
			break;
		}
	}

	// This function get into index frame
	public void getIntoFrame(int frame_index) {
		// driver.switchTo().frame(frame_index);
		for (int i = 0; i < 10; i++) {
			try {
				driver.switchTo().frame(frame_index);
				break;
			} catch (Exception msg) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	// This function get string and verify that it is valid date
	// Valid date format: mm/dd/yyyy
	public void verifyThatTargetStringIsValidDate(String target_date) {
		String[] splited_date = target_date.split("/");
		int mm = Integer.parseInt(splited_date[0]);
		int dd = Integer.parseInt(splited_date[1]);

		int yyyy = Integer.parseInt(splited_date[2]);
		if ((0 < mm) && (0 < dd) && (999 < yyyy) && (12 >= mm) && (31 >= dd) && (9999 >= yyyy)) {
			System.out.println("Target date is valid: " + target_date);
			ATUReports.add("Target date is valid: " + target_date, "True.", "True.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Target date is invalid: " + target_date);
			ATUReports.add("Target date is valid: " + target_date, "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}

	// This function verify that WebElement target attribute is target string
	public void verifyWebElementHaveTargetAttributeWithTargetValue(WebElement web_element, String target_attribute,
			String target_text) {
		if (web_element.getAttribute(target_attribute).replaceAll("  ", " ")
				.equals(target_text.replaceAll("  ", " "))) {
			System.out.println(target_text + " is displayed in " + target_attribute + " attribute.");
			ATUReports.add("Target text is displayed in " + target_attribute + " attribute.", target_text, target_text,
					LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println(target_text + " is not displayed in " + target_attribute + " attribute.");
			ATUReports.add("Target text is not displayed in " + target_attribute + " attribute.", target_text,
					web_element.getAttribute(target_attribute), LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}

	// This function send keys to input, and verify that this keys appear in
	// input
	public void sendKeysToWebElementInput(WebElement web_element, String target_input) {
		try {
			web_element.clear();
			web_element.sendKeys(target_input);

			if (web_element.getAttribute("value").equals(target_input)) {
				System.out.println("Target keys sent to WebElement: " + target_input);
				ATUReports.add("Target keys sent.", target_input, target_input, LogAs.PASSED, null);
				Assert.assertTrue(true);
			} else {
				System.out.println("Target keys sent: " + target_input + ", but not appear in the input itself: "
						+ web_element.getAttribute("value"));
				ATUReports.add("Target keys send.", target_input, web_element.getAttribute("value"), LogAs.FAILED,
						null);
				Assert.assertTrue(false);
			}
		} catch (Exception msg) {
			System.out.println("Fail to sent target keys: " + target_input);
			ATUReports.add("Target keys sent.", "True.", "False", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}

	// This function verify that element contains one of the elements of the
	// list
	public String verifyTargetStringContainOneOfElementOfList(String target_string, List<String> list) {
		for (String next : list) {
			if (target_string.contains(next)) {
				System.out.println("Verfied that element: " + target_string + " in the list.");
				ATUReports.add("Verfied that element: " + target_string + " in the list.", "True.", "True.",
						LogAs.PASSED, null);
				Assert.assertTrue(true);
				return next;
			}
		}

		System.out.println("Not verfied that element: " + target_string + " in the list.");
		ATUReports.add("Verfied that element: " + target_string + " in the list.", "True.", "False.", LogAs.FAILED,
				null);
		Assert.assertTrue(false);
		return null;
	}

	// This function get list of WebElement and return list of each WebElement
	// text
	public List<String> getTextContentListOfWebElementList(List<WebElement> webelements_list) {
		List<String> text_content_list = new ArrayList<String>();

		for (WebElement webElement : webelements_list) {
			String text_content = webElement.getText();
			if (text_content.length() != 0) {
				text_content_list.add(text_content);
			}

		}

		return text_content_list;
	}

	// This function list of WebElement and return list of each WebElement title
	// attribute
	public List<String> getTitleAttributeListOfWebElementList(List<WebElement> webelements_list) {
		List<String> title_attribute_list = new ArrayList<String>();

		for (WebElement webElement : webelements_list) {
			String tittle_attribute = webElement.getAttribute("title");
			if (tittle_attribute.length() != 0) {
				title_attribute_list.add(tittle_attribute);
			}
		}

		return title_attribute_list;
	}

	// This function get string and return same string as one word (without
	// spaces)
	public String removeSpacesFromString(String target_string) {
		return target_string.replaceAll(" ", "");
	}

	public void waitForAlert(long timeOutInSeconds) {
		int i = 0;
		while (i < timeOutInSeconds) {
			try {
				driver.switchTo().alert();
				break;
			} catch (NoAlertPresentException e) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				i++;
				continue;
			}
		}
	}

	public boolean isAlertPresent() {
		try {		
			driver.switchTo().alert();
			driver.switchTo().defaultContent();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}

	public void clickOkInAlertIfPresent() {
		try {
			System.out.println("Trying to obtain alert...");
			// Get a handle to the open alert, prompt or confirmation
			Alert alert = driver.switchTo().alert();
			try {
				// Get the text of the alert or prompt
				System.out.println("Selecting OK in alert: '" + alert.getText().replace("\n", "") + "'");
			} catch (Exception ex) {
				System.out.println(ex.getClass().getSimpleName() + " occured on alert processing.");
			}
			// And acknowledge the alert (equivalent to clicking "OK")					
			driver.switchTo().alert().accept();				
			Thread.sleep(1000);
			// makeScreenshot();
		} catch (NoAlertPresentException e) {
			System.out.println(e.getMessage());
			System.out.println("No alerts appeared");
		} catch (Exception e) {
			System.out.println(e.getClass().getSimpleName() + " occured on alert processing.");
		}

	}

	public boolean handlesClickIsNotVisible(WebElement element) {
		WebElement div = driver.findElement(By.xpath("//*[@id='main']"));
		Actions builder = new Actions(driver);
		builder.moveToElement(div, 10, 10).click().build().perform();
		String elementText = element.getText();
		System.out.println("s1");
		builder.sendKeys(Keys.PAGE_UP);
		builder.sendKeys(Keys.PAGE_UP);
		int i = 0;
		boolean elementFound = false;

		do {

			builder.sendKeys(Keys.PAGE_DOWN);
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("s3");
			builder.moveToElement(element).build().perform();
			System.out.println("s5");
			int z=0;
			while (z<3 && elementFound==false){
			try {
				builder.moveToElement(div, 10, 10).click().build().perform();
				//builder.moveToElement(element).build().perform();					
				element.click();
				elementFound = true;
				Thread.sleep(1500);
			} catch (WebDriverException e) {
				elementFound = false;	
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			z++;
			}
			
			i++;
			System.out.println("s6");
			} while (!elementFound && i < 10);
		System.out.println("s7");
		if (elementFound) {
			ATUReports.add("Scanning page succeeded: the element was clicked", elementText, "The element is clicked.",
					"The element is clicked.", LogAs.PASSED, null);
			System.out.println("s8");
			Assert.assertTrue(true);
			return true;
			
		} else {
			ATUReports.add("Scanning page failed: the element wasn't found", elementText, "The element is clicked.",
					"The element wasn't", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
			return false;
		}

	}
	public String getPreSetTimeStamp(){
		
		System.out.println((PropertyManager.getProperty("User1")).substring(5, PropertyManager.getProperty("User1").length()));
		return (PropertyManager.getProperty("User1")).substring(5, PropertyManager.getProperty("User1").length());
	}
	
	public String getFQDN(){
		
		 String login_url = driver.getCurrentUrl();
		System.out.println(login_url.split("/")[2].substring(0, login_url.split("/")[2].length() - 12));
		return login_url.split("/")[2].substring(0, login_url.split("/")[2].length() - 12);
	}

	public void fluentWaitInvisibility(WebElement element, int timeout, int pooling) {
		try {
			Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(timeout, TimeUnit.SECONDS)
					.pollingEvery(pooling, TimeUnit.MILLISECONDS);
			wait.until(ExpectedConditions.not(ExpectedConditions.visibilityOf(element)));
		} catch (Exception e) {
			ATUReports.add("Error", e.getMessage(), LogAs.WARNING, null);
		}
	}
	
	public void fluentWaitVisibility(WebElement element, int timeout, int pooling) {
		try {
			Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(timeout, TimeUnit.SECONDS)
					.pollingEvery(pooling, TimeUnit.MILLISECONDS);
			wait.until(ExpectedConditions.visibilityOf(element));
		} catch (Exception e) {
			ATUReports.add("Error", e.getMessage(), LogAs.WARNING, null);
		}
	}
	public void waitForContentOfTabToLoad(String initialCourseText,WebElement element) {
		try {
			System.out.println("waitForCoursesFrameToLoad1");
			String id=element.getAttribute("id");
			new WebDriverWait(driver, 8).until(
					ExpectedConditions.not(ExpectedConditions.textToBe(By.id(id), initialCourseText)));
			System.out.println("waitForCoursesFrameToLoad2");
		} catch (Exception e) {
			System.out.println("Catch waitForCoursesFrameToLoad");
			ATUReports.add("The tab content failed to load: " + e.getMessage(), LogAs.WARNING,
					new CaptureScreen(ScreenshotOf.BROWSER_PAGE));

		}
	}


}

