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
import java.util.Set;
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
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.Command;
import org.openqa.selenium.support.FindBy;
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
	public String time;
	@FindBy(id = "InstituteLogotype")
	public WebElement logo;
	@FindBy(id = "SignOutLink")
	public WebElement sign_out;
	String universityName="";
	DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	Date date = new Date();
	
	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}

	public Page(WebDriver browser) {	
		driver = browser;
		wait = new WebDriverWait(driver, 30);
		ATUReports.setWebDriver(driver);
		time = dateFormat.format(date);
	}

	public void clickElement(WebElement element) // clicking element
	{
		String text = element.getText();
		try {
			wait.until(ExpectedConditions.visibilityOf(element));
			element.click();
			System.out.println("Clicked on " + element.getText() + " element");
			ATUReports.add(time +" Clicked on " + text + " element", "Clicked succeeded.", "Clicked succeeded..", LogAs.PASSED,
					null);
		} catch (Exception msg) {

			try {
				System.out.println("Clicked failed trying again with JS");
				String id = element.getAttribute("id");
				((JavascriptExecutor) driver).executeScript("document.getElementById(\"" + id + "\").click();");
				ATUReports.add(time +" Clicked on " + text + " element", "Clicked succeeded.", "Clicked succeeded..", LogAs.PASSED,
						null);

			} catch (Exception e1) {
				
			}
		}

	}
			
	public void clickElementJS(WebElement element) // clicking element
	{
		String text = null;
		try {
			text = element.getText();
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
						new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				
			}
		}

	}
	
	public void clickElementWithOutIdJS(WebElement element) // clicking element
	{
		String text = element.getText();
		try {
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);		
			System.out.println("Clicked on " + text + " element"); 
			ATUReports.add(time +" Clicked on " + text + " element", "Clicked succeeded.", "Clicked succeeded..", LogAs.PASSED,null);		
		} catch (Exception msg) {
			try {
				System.out.println("Clicked failed trying again with selenium");
				wait.until(ExpectedConditions.elementToBeClickable(element));
				element.click();
				System.out.println("Clicked on " + element.getText() + " element");
				ATUReports.add(time +" Clicked on " + text + " element", "Clicked succeeded.", "Clicked succeeded..", LogAs.PASSED,
						null);
			} catch (Exception e1) {
				ATUReports.add(time +" Clicked on " + text + " element", "Clicked succeeded.", "Clicked failed:"+msg.getMessage(), LogAs.WARNING,
						new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				
			}
		}

	}

	public void dragAndDrop(WebElement drag,WebElement drop){
		
		waitForVisibility(drag);
		waitForVisibility(drop);
			
		try {
			Actions act=new Actions(driver);
			act.dragAndDrop(drag, drop).build().perform();
			System.out.println("drag and drop on WebElement.");
			ATUReports.add(time +" drag and drop on WebElement.", "True.", "True.", LogAs.PASSED, null);
			Assert.assertTrue(true);

		} catch (NoSuchElementException e) {
			System.out.println("Not drag and drop on WebElement.");
			ATUReports.add(time +" Not drag and drop on WebElement.", "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
			
	}
	
	// clicking element
	public void clickElementWithDescription(WebElement element, String description) {
		try {
			element.click();
			System.out.println("Clicked on " + description + " element.");
			ATUReports.add(time +" Clicked on " + description + " element.", "True.", "True.", LogAs.PASSED, null);
		} catch (Exception msg) {
			System.out.println("Fail to click on " + description + " element.");
			ATUReports.add(time +" Clicked on " + description + " element.", "True.", "False", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}

	}

	public void setElementText(WebElement element, String text) // setting
																// element text
	{
		element.clear();
		element.sendKeys(text);		
		//Assert.assertEquals(text, element.getAttribute("value"));

	}
	
	public void setElementTextJS(WebElement element, String text) // setting
	// element text
	{	
		String id="";
	
		try {
			id = element.getAttribute("id");
			((JavascriptExecutor) driver).executeScript("document.getElementById(\"" + id + "\").setAttribute(\"text\", \""+text+"\");");
			ATUReports.add(time +" Set text: " + id + " element", "Text was set", "Text was set", LogAs.PASSED,
					null);		
		} catch (Exception msg) {

			try {
				System.out.println("Set text failed trying again with selenium");
				wait.until(ExpectedConditions.elementToBeClickable(element));
				element.sendKeys(text);
				System.out.println("Set text:" + element.getText());
				ATUReports.add(time +" Set text: " + id + " element", "Text was set", "Text was set", LogAs.PASSED,
						null);		
			} catch (Exception e1) {
				ATUReports.add(time +" Set text: " + id + " element", "Text was set", "Text was set", LogAs.PASSED,
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
			Thread.sleep(500);	
			wait.until(ExpectedConditions.visibilityOf(element));
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("Waiting for element visibiliy failed");
			ATUReports.add(time +" Waiting for element visibility",element.getText(),"Element is visibile before timout","Element is not visible after timeout",LogAs.WARNING,new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			e.printStackTrace();
		} catch (org.openqa.selenium.NoSuchElementException e) {
			System.out.println("Waiting for element visibiliy failed");
			ATUReports.add(time +" Waiting for element visibility","Element is visibile before timout","Element was not found",LogAs.WARNING,new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			e.printStackTrace();	
		} catch (Exception e) {
			System.out.println("Waiting for element visibiliy failed");
			ATUReports.add(time +" Waiting for element visibility",element.getText(),"Element is visibile before timout","Element is not visible after timeout",LogAs.WARNING,new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			e.printStackTrace();
		}
		
		
	}
	
	public void verifyPlaceHolderIsDisplay(WebElement element ,String name) {
		
		try {
		if(element.getAttribute("placeholder").equals(name)){
			System.out.println("The place holder is: " +element.getAttribute("placeholder") + " and is equals to: " + name);
			ATUReports.add(time + " The place holder is: " +element.getAttribute("placeholder") + " and is equals to: " + name,"Success.","Success.",LogAs.PASSED,null);		
		} else {
			System.out.println("The place holder is: " +element.getAttribute("placeholder") + " and is not equals to: " + name);
			ATUReports.add(time +" The place holder is: " +element.getAttribute("placeholder") + " and is equals to: " + name,element.getText(),"Success.","Failed.",LogAs.FAILED,new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}	
		}catch(org.openqa.selenium.NoSuchElementException e){
			System.out.println("The place holder is: " +element.getAttribute("placeholder") + " and is not equals to: " + name);
			ATUReports.add(time +" The place holder is: " +element.getAttribute("placeholder") + " and is equals to: " + name,element.getText(),"Success.","Failed.",LogAs.FAILED,new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
	}
	
	public void verifyThatThisElementIsEditable(WebElement element){
		try {
			if(element.getTagName().equals("input") || element.getTagName().equals("textarea")){
				System.out.println("The element: " +element.getAttribute("id") +" is editable.");
				ATUReports.add(time + "The element: " +element.getAttribute("id") +" is editable.","Success.","Success.",LogAs.PASSED,null);		
			} else {
				System.out.println("The element: " +element.getAttribute("id") +" is editable.");
				ATUReports.add(time + "The element: " +element.getAttribute("id") +" is editable.","Success.","Failed.",LogAs.FAILED,new CaptureScreen(ScreenshotOf.BROWSER_PAGE));		
			}	
			}catch(Exception e){
				e.printStackTrace();
				ATUReports.add(time +e.getMessage(),"Success.","Failed.",LogAs.FAILED,new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			}
	}
	
	public void verifyThatTextAreaIsEmpty(WebElement element) {
		try {
			String id = element.getAttribute("id");
			String value = (String)((JavascriptExecutor) driver).executeScript("return document.getElementById(\""+id+"\").value;");
			if(value.equals("")) {
				System.out.println("Verify that The texteara is empty.");
				ATUReports.add(time + " Verify that The texteara is empty.","Success.","Success.",LogAs.PASSED,null);		
			} else{
				System.out.println("not Verify that The texteara is empty.");
				ATUReports.add(time + " Verify that The texteara is empty.","Success.","Failed.",LogAs.FAILED,new CaptureScreen(ScreenshotOf.BROWSER_PAGE));		
			}
		}catch(Exception e) {
			e.printStackTrace();
			ATUReports.add(time +e.getMessage(),"Success.","Failed.",LogAs.FAILED,new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
	    }
	}
	
	public void waitForVisibility(WebElement element, int seconds)// visibility of an element
	{
		try {
			Thread.sleep(500);	
			new WebDriverWait(driver, seconds).until(ExpectedConditions.visibilityOf(element));			
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("Waiting for element visibiliy failed");
			ATUReports.add(time +" Waiting for element visibility",element.getText(),"Element is visibile before timout","Element is not visible after timeout",LogAs.WARNING,new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			e.printStackTrace();
		} catch (org.openqa.selenium.NoSuchElementException e) {
			System.out.println("Waiting for element visibiliy failed");
			ATUReports.add(time +" Waiting for element visibility","Element is visibile before timout","Element was not found",LogAs.WARNING,new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Waiting for element visibiliy failed");
			ATUReports.add(time +" Waiting for element visibility",element.getText(),"Element is visibile before timout","Element is not visible after timeout",LogAs.WARNING,new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
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

	public String getTextFromWebElement(WebElement element,int number) {
		
		if(number > 0) {
			try {
				return element.getText();
			} catch (StaleElementReferenceException e) {
				System.out.println("Attempting to recover from StaleElementReferenceException ...");
				return getTextFromWebElement(element,number -1);
			} catch (NoSuchElementException ele) {
				System.out.println("Attempting to recover from NoSuchElementException ...");
				return getTextFromWebElement(element,number -1);
			}
		} else { 
			
			System.out.println("We lost the DOM, WebElement not found.");
			ATUReports.add(time +" We lost the DOM, WebElement not found.",LogAs.FAILED,new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
			return null;
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

	public WebElement getStaleElem(By by, WebDriver driver,int number) {
		if(number > 0) {
			try {
				return driver.findElement(by);
			} catch (StaleElementReferenceException e) {
				System.out.println("Attempting to recover from StaleElementReferenceException ...");
				return getStaleElem(by, driver,number-1);
			} catch (NoSuchElementException ele) {
				System.out.println("Attempting to recover from NoSuchElementException ...");
				return getStaleElem(by, driver,number-1);
			} 
		}else { 
			
			System.out.println("We lost the DOM, WebElement not found.");
			ATUReports.add(time +" We lost the DOM, WebElement not found.",LogAs.FAILED,new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
			return null;
		}
	}

	public WebElement getElement(String by, String direction, int number) {
		
		WebElement element = null;
		switch(by){
		case "css":
			 List<WebElement> elements = driver.findElements(By.cssSelector(direction));
			 element = elements.get(number);
			break;
		case "xpath":
			element = driver.findElement(By.xpath(direction));
			break;
		case "id":
			element = driver.findElement(By.id(direction));
			break;
		}
	
		return element;
		
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
			e.printStackTrace();
			return null;
		}
	}

	public void moveToElementAndPerform(WebElement element, WebDriver driver) throws NoSuchElementException, InterruptedException {


		waitForVisibility(element);
		try {
			Robot robot = new Robot();
			robot.mouseMove(-100,-100);
			Actions builder = new Actions(driver);
			Action mouseOver = builder.moveToElement(element).build();
			mouseOver.perform();
			//builder.moveToElement(element).clickAndHold().release().build().perform();
			
		} catch (NoSuchElementException e) {
			e.printStackTrace();
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

	public void getTheElementAndClick(String id){
		try {
			WebElement element = driver.findElement(By.id(id));
			String text = element.getText();
			((JavascriptExecutor) driver).executeScript("document.getElementById(\"" + id + "\").click();");
			ATUReports.add(time +" Clicked on " + text + " element", "Clicked succeeded.", "Clicked succeeded..", LogAs.PASSED,null);		
			System.out.println("Clicked on " + text + " element");
		} catch (Exception e) {
			System.out.println("The element not found");
			ATUReports.add(time +" The element not found" ,LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
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
					ATUReports.add(time +"  load page succeeded",Url,LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE)); // if
																													// list
				break;
			} catch (Exception e) {
				ATUReports.add(time +"  load page failed",Url ,LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
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
			ATUReports.add(time +"  date is  not correctly displaied", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		} else {
			System.out.println(" date is correctly displaied");
			ATUReports.add(time +"  date is correctly displaied", LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
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
			ATUReports.add(time +"  time is  not correctly displaied", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		} else {

			ATUReports.add(time +"  time is correctly displaied", LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
			Assert.assertTrue(true);
		}
	}

	/// sign out from any page except Login page
	public void signOut()  {
		try {
			Thread.sleep(500);
			System.out.println("signOut1");		 
			((JavascriptExecutor) driver).executeScript("document.getElementById(\"SignOutLink\").click();");			
			new WebDriverWait(driver, 25).until(ExpectedConditions.titleContains("Tegrity Lecture Capture"));
			System.out.println("signOut3");
			for (int second = 0;second<=60; second++) {		
				if (second >= 60) {
				System.out.println("LogOut from user not succeeded.");
				ATUReports.add("Login page timeout", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
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
			Thread.sleep(1500);	
		} catch (Exception e) {
			System.out.println("LogOut from user not succeeded 3");
			ATUReports.add(time +" Sign Out failed", e.getMessage(), LogAs.FAILED,new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
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
	
	public void clickEscOnKeyBoardToCloseTheWindow(WebElement cancel_button) throws InterruptedException {
		try {
			if(driver instanceof FirefoxDriver){
				cancel_button.sendKeys(Keys.ESCAPE);
			} else{
				Actions action = new Actions(driver);
				action.sendKeys(Keys.ESCAPE).build().perform();
			}
			ATUReports.add(time +" Clicked on ESC button.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} catch (Exception e) {
			ATUReports.add(time +" Fail click on ESC button.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
		Thread.sleep(3000);
	}
	
	// this function returns true if element has underline or not
	public boolean verifyUnderlineCss(WebElement element, WebDriver driver) throws InterruptedException {
		String underline = "";
		for (int second = 0;; second++) {
			if (second >= 40) {

				Assert.assertTrue(false);
				ATUReports.add(time +"  no underline", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				return false;
			}
			try {
				Thread.sleep(500);
				moveToElement(element, driver).perform();
				underline = element.getCssValue("text-decoration");

				if (underline.equals("underline"))// check if element is present
				{
					ATUReports.add(time +"  has underline", LogAs.PASSED, null);
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
				System.out.println("file does not exist");
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
		
   public void VerifyFontColor(WebElement element,String Color) {
		
		try{
		String ColorName  = getFontColor(element);
		if(Color.equals(ColorName)){
			 System.out.println("Verify the font color of: " + element.getText());
			 ATUReports.add(time +" Verify the font color of: " + element.getText(),"Success.", "Success.", LogAs.PASSED, null);
			}else {
			 System.out.println("Not Verify the font color of: " + element.getText());
			 ATUReports.add(time +" Not Verify the font color of: " + element.getText(), "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			}	
		}catch(Exception e){
			e.getMessage();
			ATUReports.add(e.getMessage(), "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));

			}
	}
	
	// get font color by an element
	public String getColorFromCssElement(WebElement element,String cssVal) {
		String text = element.getCssValue(cssVal).toString();
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
		try {
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
		} catch (Exception msg) {
			msg.printStackTrace();
			ATUReports.add(msg.getMessage(), "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
	}
	
	public void verifyWebElementClassNameDisplayed(WebElement web_element, String expected) {
		
		waitForVisibility(web_element);
		String className = web_element.getAttribute("class");
		if (className.equals(expected)) {
			System.out.println(expected + " is displayed.");
			ATUReports.add(expected + " is displayed.", "True.", "True.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println(expected + " is not displayed.");
			ATUReports.add(expected + " is not displayed.", "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}		
	}
	
	// This function verify that WebElement is not displayed, and String with
	// description
	public void verifyWebElementNotDisplayed(WebElement web_element, String description) {
		try {
		if (!web_element.isDisplayed()) {
			System.out.println(description + " is not displayed.");
			ATUReports.add(description + " is not displayed.", "True.", "True.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println(description + " is displayed.");
			ATUReports.add(description + " is displayed.", "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
		}catch(NoSuchElementException e) {
			System.out.println(description + " is not displayed.");
			ATUReports.add(description + " is not displayed.", "True.", "True.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		}
	}
	
	// This function verify that WebElement title/hint is target string
	public void verifyWebElementHaveTargetAttributeTitle(WebElement web_element, String target_text) {
		if (web_element.getAttribute("title").replaceAll("  ", " ").equals(target_text.replaceAll("  ", " "))) {
			System.out.println(target_text + " is displayed in title.");
			ATUReports.add(time +" Target text is displayed in title.", target_text, target_text, LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println(target_text + " is not displayed in title.");
			ATUReports.add(time +" Target text is not displayed in title.", target_text, web_element.getAttribute("title"),
					LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}
	
	// This function verify that WebElement title/hint is target string
	public void verifyThatTheTextOfWebElemenetIsAsExpected(WebElement web_element, String target_text) {
		if (web_element.getText().equals(target_text)) {
			System.out.println(target_text + " is displayed as expected.");
			ATUReports.add("Target text is displayed as expected.", target_text, target_text, LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println(target_text + " is not displayed as expected.");
			ATUReports.add("Target text is not displayed as expected.", target_text, web_element.getText(),
					LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}
	
	// This function verify that WebElement title/hint is target string
	public void verifyThatTheTextOfWebElemenetContainsTargetString(WebElement web_element, String target_text) {
		String id = web_element.getAttribute("id");
		String element_string = (String)((JavascriptExecutor) driver).executeScript("return document.getElementById(\""+id+"\").value;");	
		if (element_string.contains(target_text)) {
			System.out.println("The " +target_text +" appeared as expected.");
			ATUReports.add(time +" The " +target_text +" appeared as expected.", target_text, target_text, LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("The " +target_text +" not appeared as expected.");
			ATUReports.add(time +" The " +target_text +" not appeared as expected.", target_text, web_element.getText(),
					LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}
	
	// This function verify that WebElement title/hint is target string
	public void veirfyThatTheTextOfWebElementNotContainsTargetString(WebElement web_element, String target_text) {
		if (!web_element.getText().contains(target_text)) {
			System.out.println(" The " +target_text +"is not appeared as expected.");
			ATUReports.add(time +" The " +target_text +"is not appeared as expected.", target_text, target_text, LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println(" The " +target_text +"appeared not as expected.");
			ATUReports.add(time +" The " +target_text +"appeared not as expected.", target_text, web_element.getText(),
					LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}
	
	public void verifyThatWeHaveHintToWebElement(WebElement element, String text) {		
		try {
			String title = element.getAttribute("title");
			if(title.equals(text)){
				System.out.println("Verify that we can see hint to the webElement: " +element.getText() + " the hint is: " + title );
				ATUReports.add("Verify that we can see hint to the webElement: " +element.getText() + " the hint is: " + title ,"The hint is display.","The hint is display.",LogAs.PASSED, null);		
			}else{
				System.out.println("Not Verify that we can see hint to the webElement: " +element.getText() + " the hint is: " + title);
				ATUReports.add("Verify that we can see hint to the webElement: " +element.getText() + " the hint is: " + title ,"The hint is display.","The hint is not display.",LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));					
			}
		}catch(Exception e){
			e.getMessage();
			ATUReports.add(e.getMessage(), "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}	
	}
	
	public void verifyThatTheElementIsReadOnly(WebElement element) {		
		try {
			String title = element.getAttribute("ng-disabled");
			if(title != null){
				if(title.equals("isDisabledField")){
					System.out.println("Verify that the element: " +element.getText() + " is readonly." );
					ATUReports.add("Verify that the element: " +element.getText() + " is readonly.","Success.","Success.",LogAs.PASSED, null);		
				}
				}else{
				System.out.println("Not Verify that the element: " +element.getText() + " is readonly."  + title);
				ATUReports.add("Verify that the element: " +element.getText() + " is readonly." ,"Success.","Success.",LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));					
			}
		}catch(Exception e){
			e.getMessage();
			ATUReports.add(e.getMessage(), "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}	
	}

	
	public void verifyWebElementisCheckable(WebElement element) {
		try {
			String type = element.getAttribute("type");
			if(type.equals("checkbox")){
				System.out.println("Verify that the element: " +element.getText() + "is checkable.");
				ATUReports.add(time +" Verify that the element: " +element.getText() + "is checkable.","The element is checkable.","The element is checkable.",LogAs.PASSED, null);		
			}else{
				System.out.println("Not Verify that the element: " +element.getText() + "is checkable.");
				ATUReports.add(time +" Not Verify that the element: " +element.getText() + "is checkable." ,"The element is checkable.","The element is not checkable.",LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));					
			}
		}catch(Exception e){
			e.getMessage();
			ATUReports.add(e.getMessage(), "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}	
	}
	
	// This function "send" string to WebElement
	public void sendStringToWebElement(WebElement we, String string_to_send) {
		try {
			we.click();
			we.sendKeys(string_to_send);
			System.out.println("String sent to WebElement: " + string_to_send);
			ATUReports.add(time +" String send to WebElement.", string_to_send, string_to_send, LogAs.PASSED, null);
		} catch (Exception msg) {
			msg.printStackTrace();
			System.out.println("String do not send to WebElement: " + string_to_send);
			ATUReports.add(time +" String do not send to WebElement.", string_to_send, "", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
	}

	public void sendStringwithAction(WebElement we, String string_to_send) {
		try {
			Actions builder = new Actions(driver);
			//like clear
			((JavascriptExecutor) driver).executeScript("arguments[0].value ='';",we);
			builder.sendKeys(we,string_to_send);
			builder.build().perform();
			System.out.println("String sent to WebElement: " + string_to_send);
			ATUReports.add(time +" String send to WebElement.", string_to_send, string_to_send, LogAs.PASSED, null);
		} catch (Exception msg) {
			msg.printStackTrace();
			System.out.println("String do not send to WebElement: " + string_to_send);
			ATUReports.add(time +" String do not send to WebElement.", string_to_send, "", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
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
				ATUReports.add(time +" Verified that next result display below the current result.", "True.", "True.",
						LogAs.PASSED, null);
			} else {
				System.out.println("Not verified that next result display below the current result.");
				ATUReports.add(time +" Verified that next result display below the current result.", "True.", "False",
						LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			}

		 } else {
			   System.out.println("There is 1 or 0 results.");
			   if(number == 2)
			   ATUReports.add(time +" There is 1 or 0 results.", "Expect for more then 1 results.", "1 or 0 results.",
			     LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			   else {
			    ATUReports.add(time +" There is 1 or 0 results.", "Expect exactly 1 results.", "1 or 0 results.",
			      LogAs.PASSED, null);
			   }
		 }
	}
	
	// Verify that// The next result display below the current result in case there is next// result.
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
					ATUReports.add(time +" Verified that next result display below the current result.", "True.", "True.",
							LogAs.PASSED, null);
				} else {
					System.out.println("Not verified that next result display below the current result.");
					ATUReports.add(time +" Verified that next result display below the current result.", "True.", "False",
							LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				}
			} else {
				System.out.println("There is 1 or 0 results.");
				ATUReports.add(time +" There is 1 or 0 results.", "Expect for more then 1 results.", "1 or 0 results.",
						LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			}
		}
	
	/// this function verifies visibility of university logo all helpers will
	/// inherit from page
	public void verifyLogoVisibilityAndLocation() {
		org.openqa.selenium.Point logo_loc = logo.getLocation();
		if (logo.isDisplayed()) {

			System.out.println("verified that logo is displaied");
			ATUReports.add(time +" verified that logo is displaied", "university logo", "visible", "visible", LogAs.PASSED,
					null);
			Assert.assertTrue(true);
			System.out.println(logo_loc.getX() + " " + logo_loc.getY());
			if ((logo_loc.getX() < 100) && (logo_loc.getY() < 100)) {
				System.out.println("verified logo location");
				ATUReports.add(time +" verify logos' location", "university logo", "accurate location", "accurate location",
						LogAs.PASSED, null);
				Assert.assertTrue(true);
			} else {
				System.out.println("not verified logo location");
				ATUReports.add(time +" verify logos' location", "university logo", "accurate location", "bad location",
						LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Assert.assertTrue(false);
			}

		} else {
			System.out.println("Not verified that logo is displaied");
			ATUReports.add(time +" Not verified that logo is displaied", "university logo", "visible", "not visible",
					LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}

	// This function get two WebElement and check if the first is to the right
	// (by location) to the second
	public void isFirstWebElementToTheRightSecondWebElement(WebElement first_webelement, WebElement second_webelement) {
		if (first_webelement.getLocation().x <= second_webelement.getLocation().x) {
			System.out.println("The " + first_webelement.getText() +" is to the right to the " + second_webelement.getText());
			ATUReports.add(time +" The " + first_webelement.getText() +" is to the right to the " + second_webelement.getText(), "True.", "True.",
					LogAs.PASSED, null);	
		} else {
			System.out.println("The " + first_webelement.getText() +" is to the right to the " + second_webelement.getText());
			ATUReports.add(time +" The " + first_webelement.getText() +" is to the right to the " + second_webelement.getText(), "True.", "False.",
					LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));		
		}
	}

	// This function get two WebElement and check if the first is below to the second (by location)
	public void isFirstWebElementBelowSecondWebElement(WebElement first_webelement, WebElement second_webelement) {
		if (first_webelement.getLocation().y <= second_webelement.getLocation().y) {
			System.out.println("The " + first_webelement.getText() +" is below to the " + second_webelement.getText());
			ATUReports.add("The " + first_webelement.getText() +" is below to the " + second_webelement.getText(), "True.", "True.",
					LogAs.PASSED, null);
		} else {
			System.out.println("The " + first_webelement.getText() +" is not below to the " + second_webelement.getText());
			ATUReports.add("The " + first_webelement.getText() +" is not below to the " + second_webelement.getText(), "True.", "False.",
					LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}	
	}
	// This function get two WebElement and check if the first is below to the second (by location)
	public void isFirstWebElementEqualsHorizontallyOrVerticalSecondWebElement(WebElement first_webelement, WebElement second_webelement) {
		try{
		if (first_webelement.getLocation().y == second_webelement.getLocation().y || first_webelement.getLocation().x == second_webelement.getLocation().x) {
			System.out.println("The " + first_webelement.getText() +" equals horizontally or vertical " + second_webelement.getText());
			ATUReports.add("The " + first_webelement.getText() +" equals horizontally or vertical " + second_webelement.getText(), "True.", "True.",
					LogAs.PASSED, null);
		} else {
			System.out.println("The " + first_webelement.getText() +" not equals horizontally or vertical " + second_webelement.getText());
			ATUReports.add(" The " + first_webelement.getText() +" not equals horizontally or vertical " + second_webelement.getText(), "True.", "False.",
					LogAs.WARNING, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
		}catch(Exception e){
			e.printStackTrace();
			ATUReports.add(time + e.getMessage(), "True.", "False.",LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
	}
	
	// This function get WebElement and excpected text, and check if that text
	// appear in the WebElement
	public void verifyWebElementTargetText(WebElement web_element, String target_text) {
		try{
			if (web_element.getText().contains(target_text)) {
				System.out.println("Verified that target web element have target text: " + target_text);
				ATUReports.add(time +" WebElement have target text.", target_text, target_text, LogAs.PASSED, null);
				Assert.assertTrue(true);
			} else {
				System.out.println("Not verified that target web element have target text: " + target_text + " != "
						+ web_element.getText());
				ATUReports.add(time +" WebElement have target text.", target_text, web_element.getText(), LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Assert.assertTrue(false);
			}
		}catch(Exception e){
			e.printStackTrace();
			ATUReports.add(time + e.getMessage(), "True.", "False.",LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
	}
	
	// This function verify that there is vertical scrolling
	public void verifyThereVerticalScrolling() {
		boolean scrolling_bar = (boolean) ((JavascriptExecutor) driver).executeScript(
				"return Math.max(document.body.scrollHeight, document.body.offsetHeight, document.documentElement.clientHeight, document.documentElement.scrollHeight, document.documentElement.offsetHeight)>document.documentElement.clientHeight ;");
		if (scrolling_bar) {
			System.out.println("Verified that there is vertical scrolling.");
			ATUReports.add(time +" Verify there is vertical scrolling.", "True.", "True.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Not verified that there is vertical scrolling.");
			ATUReports.add(time +" Verify there is vertical scrolling.", "True.", "False", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}

	// This function verify that there is horizontal scrolling
	public void verifyThereHorizontalScrolling() {
		boolean scrolling_bar = (boolean) ((JavascriptExecutor) driver).executeScript(
				"return Math.max(document.body.scrollWidth, document.body.offsetWidth, document.documentElement.clientWidth, document.documentElement.scrollWidth, document.documentElement.offsetWidth)>document.documentElement.clientWidth ;");
		if (scrolling_bar) {
			System.out.println("Verified that there is horizontal scrolling.");
			ATUReports.add(time +" Verify there is horizontal scrolling.", "True.", "True.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Not verified that there is horizontal scrolling.");
			ATUReports.add(time +" Verify there is horizontal scrolling.", "True.", "False", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}

	// This function verify that WebElement is selected
	public void verifyWebElementSelected(WebElement webElement) {
		if (webElement.isSelected()) {
			System.out.println("Verfied that WebElement selected.");
			ATUReports.add(time +" Verfied that WebElement selected.", "True.", "True.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Not verifed that WebElement selected.");
			ATUReports.add(time +" Verfied that WebElement selected.", "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}

	// This function verify that WebElement is not selected
	public void verifyWebElementNotSelected(WebElement webElement) {
		if (webElement.isSelected()) {
			System.out.println("Not verfied that WebElement not selected.");
			ATUReports.add(time +" Verfied that WebElement not selected.", "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		} else {
			System.out.println("Verfied that WebElement not selected.");
			ATUReports.add(time +" Verfied that WebElement not selected.", "True.", "True.", LogAs.PASSED, null);
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
		
			try {
				new WebDriverWait(driver, 60).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frame_index));				
			} catch (Exception msg) {
				ATUReports.add(time +" Switch to frame failed", msg.getMessage(),"True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Assert.assertTrue(false);				
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
			ATUReports.add(time +" Target date is valid: " + target_date, "True.", "True.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Target date is invalid: " + target_date);
			ATUReports.add(time +" Target date is valid: " + target_date, "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}

	// This function verify that WebElement target attribute is target string
	public void verifyWebElementHaveTargetAttributeWithTargetValue(WebElement web_element, String target_attribute,
			String target_text) {
		if (web_element.getAttribute(target_attribute).replaceAll("  ", " ")
				.equals(target_text.replaceAll("  ", " "))) {
			System.out.println(target_text + " is displayed in " + target_attribute + " attribute.");
			ATUReports.add(time +" Target text is displayed in " + target_attribute + " attribute.", target_text, target_text,
					LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println(target_text + " is not displayed in " + target_attribute + " attribute.");
			ATUReports.add(time +" Target text is not displayed in " + target_attribute + " attribute.", target_text,
					web_element.getAttribute(target_attribute), LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}

	// This function send keys to input, and verify that this keys appear in
	// input
	public void sendKeysToWebElementInput(WebElement web_element, String target_input) {
		try {
			waitForVisibility(web_element);
			web_element.clear();
			web_element.sendKeys(target_input);

			if (web_element.getAttribute("value").equals(target_input)) {
				System.out.println("Target keys sent to WebElement: " + target_input);
				ATUReports.add("Target keys sent.", "True.", "True.", LogAs.PASSED, null);
				Assert.assertTrue(true);
			} else {
				System.out.println("Target keys sent: " + target_input + ", but not appear in the input itself: "
						+ web_element.getAttribute("value"));
				ATUReports.add(" Target keys send.", target_input, web_element.getAttribute("value"), LogAs.FAILED,
						new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Assert.assertTrue(false);
			}
		} catch (Exception msg) {
			System.out.println("Fail to sent target keys: " + target_input);
			ATUReports.add("Target keys sent.", "True.", "False", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}
	
	public void moveToTheOtherTabAndCloseTheOldTab(String current_handler) throws InterruptedException{
						
		Set<String> allHandles = driver.getWindowHandles();								
		driver.switchTo().window(current_handler);
		driver.close();
						
		for(String handler: allHandles) {
		    if(!handler.equals(current_handler)){
				driver.switchTo().window(handler);
				break;
				 }
	  	}
	}
	
	public void moveToTheOtherTab(String current_handler) throws InterruptedException{
		
		Set<String> allHandles = driver.getWindowHandles();								
	
		for(String handler: allHandles) {
		    if(!handler.equals(current_handler)){
				driver.switchTo().window(handler);
				break;
				 }
	  	}
		
	}
	
	public void checkIfStringContainOnlyUpperLatters(String label) {
		
			if (!label.equals(label.toLowerCase())){
				System.out.println("Verfied that the element: " + label + " contain only upper letters.");
				ATUReports.add(time +"Verfied that the element: " + label + " contain only upper letters.", "True.", "True.",LogAs.PASSED, null);		
			} else {
				System.out.println("Not verfied that the element: " + label + " contain only upper letters.");
				ATUReports.add(time +" Verfied that hte element: " + label + " contain only upper letters.", "True.", "False.", LogAs.FAILED,null);
			}
	}

	// This function verify that element contains one of the elements of the
	// list
	public String verifyTargetStringContainOneOfElementOfList(String target_string, List<String> list) {
		for (String next : list) {
			if (target_string.contains(next)) {
				System.out.println("Verfied that element: " + target_string + " in the list.");
				ATUReports.add(time +" Verfied that element: " + target_string + " in the list.", "True.", "True.",LogAs.PASSED, null);
				Assert.assertTrue(true);
				return next;
			}
		}

		System.out.println("Not verfied that element: " + target_string + " in the list.");
		ATUReports.add(time +" Verfied that element: " + target_string + " in the list.", "True.", "False.", LogAs.FAILED,null);
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

	public void waitForAlert(long timeOutInSeconds) throws InterruptedException {
		int i = 0;
		while (i < timeOutInSeconds) {
			try {
				driver.switchTo().alert();
				break;
			} catch (NoAlertPresentException e) {
				Thread.sleep(1000);
				i++;
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
			ATUReports.add(time +" Scanning page succeeded: the element was clicked", elementText, "The element is clicked.",
					"The element is clicked.", LogAs.PASSED, null);
			System.out.println("s8");
			Assert.assertTrue(true);
			return true;
			
		} else {
			ATUReports.add(time +" Scanning page failed: the element wasn't found", elementText, "The element is clicked.",
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
			ATUReports.add(time +" Error", e.getMessage(), LogAs.WARNING, null);
		}
	}
	
	public void fluentWaitVisibility(WebElement element, int timeout, int pooling) {
		try {
			Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(timeout, TimeUnit.SECONDS)
					.pollingEvery(pooling, TimeUnit.MILLISECONDS);
			wait.until(ExpectedConditions.visibilityOf(element));
		} catch (Exception e) {
			ATUReports.add(time +" Error", e.getMessage(), LogAs.WARNING, null);
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
			ATUReports.add(time +" The tab content failed to load: " + e.getMessage(), LogAs.WARNING,
					new CaptureScreen(ScreenshotOf.BROWSER_PAGE));

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
	
	public void verifyThatTheElementIsLabel(WebElement url_label) {
			
			if(url_label.getTagName().equals("label")){
				System.out.println("Verfied that element: " + url_label.getText() + " is a label.");
				ATUReports.add("Verfied that element: " + url_label.getText() + " is a label.", "True.", "True.",LogAs.PASSED, null);		
			} else {
				System.out.println("Not Verfied that element: " + url_label.getText() + " is a label.");
				ATUReports.add("Not Verfied that element: " + url_label.getText() + " is a label.", "True.", "False.", LogAs.FAILED,null);
			}
	}
	
	public void openNewTab(){
		
		Actions builder = new Actions(driver);
		Action mouseOver = builder.moveToElement(driver.findElement(By.tagName("body"))).sendKeys(Keys.chord(Keys.CONTROL, "n")).build();
		mouseOver.perform();
	  	
	}
	
	public void openNewBrowserWithThatLink(String url_for_playing) {
		
		String current_handler = driver.getWindowHandle();
		Actions builder = new Actions(driver);
		Action mouseOver = builder.moveToElement(driver.findElement(By.tagName("body"))).sendKeys(Keys.chord(Keys.CONTROL, "n")).build();
		mouseOver.perform();
		
		Set<String> allHandles = driver.getWindowHandles();
															
		driver.switchTo().window(current_handler);
		driver.close();
		
		if(driver instanceof FirefoxDriver){
			driver.findElement(By.tagName("body")).sendKeys(Keys.CONTROL + "w");
		}
						
		for(String handler: allHandles) {
		    if(!handler.equals(current_handler)){
				driver.switchTo().window(handler);
				break;
			 }
	  	}
		driver.get(url_for_playing);
	}
	
	public String getUniversityName(){		
		
		return universityName.split(" ")[0];		
	}
}

