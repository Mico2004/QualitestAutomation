package com.automation.main;

import java.security.Identity;
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
import org.openqa.selenium.support.ui.WebDriverWait;
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

import java.awt.AWTException;
import java.awt.Frame;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.security.Identity;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.lang.model.util.ElementScanner6;
import javax.print.DocFlavor.STRING;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
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
import net.sourceforge.htmlunit.corejs.javascript.ast.ParseProblem;

@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class PlayerPage extends Page {
	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}

	public PlayerPage(WebDriver browser) {
		super(browser);
		// TODO Auto-generated constructor stub
	}

	@FindBy(id = "PlaceHolder_StatusBarAreaTextBox")
	WebElement time_buffer_status;
	@FindBy(id = "PauseButton_Img")
	WebElement pause_button;
	@FindBy(id = "Add_Img")
	WebElement add_bookmark_button;
	@FindBy(id = "InputTextArea") WebElement bookmark_input_text;
	@FindBy(css = ".BookmarkSelected>.BookmarkButton") List<WebElement> selected_bookmark_buttons_list;
	@FindBy(id = "tegritySearchBox")
	WebElement search_box;
	@FindBy(id = "NumbOfRes")
	WebElement list_of_results;
	@FindBy(id = "undefined_TXT")
	WebElement search_result_title;
	@FindBy(linkText= "Course")
	WebElement course_breadcrumbs;
	@FindBy(xpath= "//*[@id=\"tegrityBreadcrumbsBox\"]/li[2]/a")
	WebElement course_name_breadcrumbs;
	@FindBy(xpath = "//*[@id=\"playerContainer\"]")
	WebElement iframe;

	// This function get as input number of seconds.
	// It will check if the player plays for this number of seconds.
	public boolean verifyTimeBufferStatusForXSec(int seconds) throws InterruptedException {
		if (seconds <= 0) {
			System.out.println("Please give positive number of seconds as input for verifing buffer status.");
			ATUReports.add("Please give positive number of seconds as input for verifing buffer status.", LogAs.FAILED,
					null);
			Assert.assertTrue(false);
			return false;
		}

		// Wait for switching frame
		for (int i = 0; i < 25; i++) {
			try {
				driver.switchTo().frame(0);
				System.out.println("Switching to player frame.");
				ATUReports.add("Switching to player frame.", "Success to switch to player frame.",
						"Success to switch to player frame.", LogAs.PASSED, null);
				Assert.assertTrue(true);
				break;
			} catch (org.openqa.selenium.NoSuchFrameException msg) {
				if (i == 24) {
					System.out.println("Fail to switch to player frame.");
					ATUReports.add("Switching to player frame.", "Success to switch to player frame.",
							"Fail to switch to player frame.", LogAs.FAILED, null);
					Assert.assertTrue(false);
					return false;
				} else {
					Thread.sleep(1000);
				}
			}
		}

		// Thread.sleep(10000);

		// Wait max 45sec to load the player
		try {
			(new WebDriverWait(driver, 45))
					.until(ExpectedConditions.presenceOfElementLocated(By.id("PauseButton_Img")));
			System.out.println("Player shows pause button.");
			ATUReports.add("Player.", "Player shows pause button.", "Player shows pause button.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} catch (Exception msg) {
			System.out.println("Player not shows pause button.");
			ATUReports.add("Player.", "Player shows pause button.", "Player not shows pause button.", LogAs.FAILED,
					null);
			Assert.assertTrue(false);
			return false;
		}

		String initial_second = "";
		// waitForVisibility(pause_button);

		int i = 0;
		int max_buffering_waiting_time = 30;
		int j_buffering_time = 0;
		while (i < seconds) {
			i++;
			Thread.sleep(2500);

			try {
				String current_second = time_buffer_status.getText().split("/")[0].split(":")[2];

				// System.out.println(current_second);
				// System.out.println(initial_second);
				if (initial_second.equals(current_second)) {
					System.out.println("The time buffer status is not moving in seconds.");
					ATUReports.add("The time buffer.", "The time buffer is moving correctly.",
							"The time buffer status is not moving in seconds.", LogAs.FAILED, null);
//					Assert.assertTrue(false);
					return false;
				}
				initial_second = current_second;
			} catch (Exception msg) {
				i--;
				j_buffering_time++;

				if (j_buffering_time == max_buffering_waiting_time) {
					System.out.println("The time buffer status is stuck on Buffering status and not moving.");
					ATUReports.add("The time buffer.", "The time buffer is moving correctly.",
							"The time buffer status is stuck on Buffering status and not moving.", LogAs.FAILED, null);
//					Assert.assertTrue(false);
					return false;
				}
			}
		}

		System.out.println("The time buffer is moving correctly.");
		ATUReports.add("The time buffer.", "The time buffer is moving correctly.",
				"The time buffer is moving correctly.", LogAs.PASSED, null);
		Assert.assertTrue(true);
		return true;
	}

	// TODO: get video with alert in FireFox and rework on this function.
	public boolean verifyTimeBufferStatusForXSecAlertVersion(int seconds) throws InterruptedException {
		try {
			System.out.println(driver.switchTo().alert().getText());
			driver.switchTo().alert().accept();
		} catch (org.openqa.selenium.NoAlertPresentException ex) {
			if (seconds <= 0) {
				System.out.println("Please give positive number of seconds as input for verifing buffer status.");
				ATUReports.add("Please give positive number of seconds as input for verifing buffer status.",
						LogAs.FAILED, null);
				Assert.assertTrue(false);
				return false;
			}

			driver.switchTo().frame(0);
			String initial_seconds = "00";
			waitForVisibility(pause_button);
			while (pause_button.isDisplayed()) {
				Thread.sleep(1200);
				String current_seconds = time_buffer_status.getText().split("/")[0].split(":")[2];
				if (initial_seconds.equals(current_seconds)) {
					System.out.println("The time buffer status is not moving in seconds.");
					ATUReports.add("The time buffer status is not moving in seconds.", LogAs.FAILED, null);
					Assert.assertTrue(false);
					return false;
				}
			}

			System.out.println("The time buffer is moving correctly.");
			ATUReports.add("The time buffer is moving correctly.", LogAs.PASSED, null);
			Assert.assertTrue(true);
			return true;
		}

		return false;
	}

	public void verifyPartiallyUrl(String partially_url) {
		String current_url = driver.getCurrentUrl();

		if (current_url.contains(partially_url)) {
			System.out.println("Current url is contains partially url from podcast page: " + current_url + " contains "
					+ partially_url);
			ATUReports.add("Current url is contains partially url from podcast page: " + current_url + " contains "
					+ partially_url, LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Current url is not contains partially url from podcast page: " + current_url + " != "
					+ partially_url);
			ATUReports.add("Current url is not contains partially url from podcast page: " + current_url + " != "
					+ partially_url, LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
	}

	// This function verify that you cannot add bookmark
	public void verifyThatUserCannotAddBookmark() {
		if (add_bookmark_button.isDisplayed()) {
			System.out.println("Not verfied that user cannot add bookmark.");
			ATUReports.add("Verfied that user cannot add bookmark.", "True.", "False.", LogAs.FAILED, null);
		} else {
			System.out.println("Verfied that user cannot add bookmark.");
			ATUReports.add("Verfied that user cannot add bookmark.", "True.", "True.", LogAs.PASSED, null);
		}
	}

	/// to players dialog page
	public void toPlayersDialog() throws Exception {

		Robot robot;
		try {
			robot = new Robot();

			robot.mouseMove(500, 500);
			robot.keyPress(KeyEvent.VK_CONTROL);
			Thread.sleep(200);
			robot.keyPress(KeyEvent.VK_ALT);
			Thread.sleep(200);
			robot.keyPress(KeyEvent.VK_L);
			Thread.sleep(200);
			robot.keyRelease(KeyEvent.VK_ALT);
			Thread.sleep(200);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			Thread.sleep(200);
			robot.keyRelease(KeyEvent.VK_L);
			Thread.sleep(200);
			System.out.println("reached players dialog");
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			System.out.println("couldnt reach players dialog");
		}
	}

	/// verify search invalid recording name or recording from another course
	/// returns empty list
	public void verifySearchReturnEmptyList(String to_search) {
		try {

			Thread.sleep(2000);
			search_box.clear();
			Thread.sleep(1000);
			search_box.sendKeys(to_search);
			Robot robot = new Robot();
			robot.mouseMove(-1000, 100);
			robot.keyPress(KeyEvent.VK_ENTER);
			Thread.sleep(200);
			robot.keyRelease(KeyEvent.VK_ENTER);
			Thread.sleep(1000);
			for (String handler : driver.getWindowHandles()) {
				driver.switchTo().window(handler);
				break;
			}
			driver.switchTo().frame(0);
			waitForVisibility(list_of_results);
			if (list_of_results.getText().contains("No results found for:")) {
				System.out.println("no results found for " + to_search);
				ATUReports.add("search for results", to_search, "empty list", "empty list", LogAs.PASSED, null);
				Assert.assertTrue(true);
			} else {
				System.out.println("results found for " + to_search);
				ATUReports.add("search for results", to_search, "empty list", "not empty list", LogAs.FAILED, null);
				Assert.assertTrue(false);
			}

		} catch (Exception e) {
			System.out.println("problem with searching");
			ATUReports.add("problem with searching", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
	}

	//// search for course
	public void verifySearchForRecordingExist(String to_search) {
		try {

			Thread.sleep(2000);
			search_box.clear();
			Thread.sleep(1000);
			search_box.sendKeys(to_search);
			Robot robot = new Robot();
			robot.mouseMove(-1000, 100);
			robot.keyPress(KeyEvent.VK_ENTER);
			Thread.sleep(200);
			robot.keyRelease(KeyEvent.VK_ENTER);
			Thread.sleep(1000);
			for (String handler : driver.getWindowHandles()) {
				driver.switchTo().window(handler);
				break;
			}
			driver.switchTo().frame(0);
			waitForVisibility(list_of_results);
			if (list_of_results.getText().contains("No results found for:")) {
				System.out.println("no results found for " + to_search);
				ATUReports.add("search for results", to_search, "empty list", "empty list", LogAs.PASSED, null);
				Assert.assertTrue(true);
			} else {
				System.out.println("results found for " + to_search);
				ATUReports.add("search for results", to_search, "empty list", "not empty list", LogAs.FAILED, null);
				Assert.assertTrue(false);
			}

		} catch (Exception e) {
			System.out.println("problem with searching");
			ATUReports.add("problem with searching", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
	}

	/// verify search result page by recording name that was searched
	public void verifySearchResultPage(String recording) {
		waitForVisibility(search_result_title);
		String text = search_result_title.getText();
		if ((text.contains(recording)) && (text.contains("- Search Results"))) {
			System.out.println("result search page verified for recording: " + recording);
			ATUReports.add("result search page verified for recording: ", recording, "contains", "contains",
					LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("result search page not verified for recording: " + recording);
			ATUReports.add("result search page  verified for recording: ", recording, "contains", "not contains",
					LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
	}

	/// The breadcrumb structure is displayed as follows: "> Courses > course
	/// name".
	public void verifyBreadcrumbsForSearcRecoding(String course_name) {
		if ((course_name.equals(course_name_breadcrumbs.getText())) && (course_breadcrumbs.isDisplayed())) {
			System.out.println("course breadcrumbs and course name breadcrumbs were verified");
			ATUReports.add("course breadcrumbs and course name breadcrumbs were verified", "breadcrumbs", "contains",
					"contains", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("course breadcrumbs and course name breadcrumbs were not verified");
			ATUReports.add("course breadcrumbs and course name breadcrumbs were verified", "breadcrumbs", "contains",
					"not contains", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
	}
	
	// This function add String to bookmark
	public void addTargetBookmark(String target_bookmark) {
		System.out.println(time_buffer_status.getText());
		sendStringToWebElement(bookmark_input_text, target_bookmark);
		clickElement(add_bookmark_button);
		System.out.println(time_buffer_status.getText());
		
		System.out.println("Target bookmark added.");
		ATUReports.add("Target bookmark added.", "True.", "True.", LogAs.PASSED, null);
	}
	
	// This function delete all bookmarks
	public void deleteAllBookmark() throws InterruptedException {
		while(selected_bookmark_buttons_list.size()>0) {
			for(WebElement we: selected_bookmark_buttons_list) {
				if(we.getCssValue("name").equals("Delete")) {
					moveToElement(we, driver).perform();
					Thread.sleep(5000);
					clickElement(we);
					break;
				}
			}
		}
	}

}
