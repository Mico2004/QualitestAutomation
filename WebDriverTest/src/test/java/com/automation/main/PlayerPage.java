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
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
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
	@FindBy(id = "InputTextArea")
	WebElement bookmark_input_text;
	@FindBy(css = ".BookmarkButton")
	WebElement delete_button;
	@FindBy(css = ".BookmarkSelected>.BookmarkButton")
	List<WebElement> selected_bookmark_buttons_list;
	@FindBy(xpath = ".//*[@id='BookmarkList']/div")
	List<WebElement> bookmark_list;
	@FindBy(id = "tegritySearchBox")
	WebElement search_box;
	@FindBy(xpath = ".//*[@id='scrollableArea']/div[2]/div/div/div/accordion/div/div[1]/div[2]/div/div[3]/a/div[2]/p[2]")
	WebElement second_record_player;
	@FindBy(id = "NumbOfRes")
	WebElement list_of_results;
	@FindBy(xpath = "html/body/div[3]/div[6]/div")
	WebElement search_result_title;
	@FindBy(linkText = "Course")
	WebElement course_breadcrumbs;
	@FindBy(xpath = "//*[@id=\"tegrityBreadcrumbsBox\"]/li[2]/a")
	WebElement course_name_breadcrumbs;
	@FindBy(xpath = "//*[@id=\"playerContainer\"]")
	WebElement iframe;
	public @FindBy(xpath = "//*[contains(@id, 'SearchResult_')]") /// "SearchResult_");
	List<WebElement> search_result;
	@FindBy(id = "TimeSliderButton")
	WebElement recording_time_coursor;
	@FindBy(id = "PlaceHolder_StatusBarAreaTextBox")
	WebElement player_timer;
	public @FindBy(xpath = "//*[contains(@id, 'SearchResult_')]/td[2]") /// "SearchResult_");
	List<WebElement> play_time_search_result;
	@FindBy(css = "#tegrityBreadcrumbsBox>.ng-scope>.ng-scope.ng-binding")
	public 
	List<WebElement> breadcrumbs_box_elements_list;
	@FindBy(id = "tegrityTopNavigationBar")
	WebElement top_bar;
	@FindBy(id = "PlayButton_Img")
	public
	WebElement play_button;
	public @FindBy(id = "//*[contains(@id, 'Row_Span_CC_')]") /// "SearchResult_");
	WebElement caption;
	public @FindBy(xpath = "//*[@id=\"DownDiv\"]/table/tbody/tr[1]/th") /// "SearchResult_");
	List<WebElement> columns_title_text;
	public @FindBy(css = "#InputTextArea") 
	WebElement bookmark_text_infut_field;
	public @FindBy(xpath = "//*[@id=\"BookmarkList\"]/div/div/div[3]") /// "SearchResult_");
	List<WebElement> bookmark_names;
	public @FindBy(xpath = "//*[@id=\"BookmarkList\"]/div/div/div[2]") /// "SearchResult_");
	List<WebElement> bookmark_duration_time;
	public @FindBy(id = "NextButton_Img") WebElement next_button;

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
					System.out.println("Switching to player frame.");
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
//		if((driver instanceof InternetExplorerDriver) || (driver instanceof ChromeDriver)){
			Thread.sleep(5000);
			exitInnerFrame();
			Thread.sleep(500);
			getIntoFrame(0);
//		}
		
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
		
//		if((driver instanceof InternetExplorerDriver) || (driver instanceof ChromeDriver)){
			Thread.sleep(5000);
			exitInnerFrame();
			Thread.sleep(500);
			getIntoFrame(0);
//		}

		

		String initial_second = "";
		// waitForVisibility(pause_button);

		int i = 0;
		int max_buffering_waiting_time = 30;
		int j_buffering_time = 0;
		int k_same_buffering_time = 0;
		while (i < seconds) {
			i++;
			Thread.sleep(2500);

			try {
				String current_second = time_buffer_status.getText().split("/")[0].split(":")[2];

				// System.out.println(current_second);
				// System.out.println(initial_second);
				if (initial_second.equals(current_second)) {
					k_same_buffering_time++;
					i--;
					j_buffering_time++;
					
					if(k_same_buffering_time == 10) {
						System.out.println("The time buffer status is not moving in seconds.");
						ATUReports.add("The time buffer.", "The time buffer is moving correctly.",
								"The time buffer status is not moving in seconds.", LogAs.WARNING, null);
						// Assert.assertTrue(false);
						return false;
					}
				}
				initial_second = current_second;
			} catch (Exception msg) {
				i--;
				j_buffering_time++;

				if (j_buffering_time == max_buffering_waiting_time) {
					System.out.println("The time buffer status is stuck on Buffering status and not moving.");
					ATUReports.add("The time buffer.", "The time buffer is moving correctly.",
							"The time buffer status is stuck on Buffering status and not moving.", LogAs.WARNING, null);
					// Assert.assertTrue(false);
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
				ATUReports.add("search for results", to_search, "empty list", "not empty list", LogAs.FAILED, null);
				Assert.assertTrue(false);
			} else {
				System.out.println("results found for " + to_search);
				ATUReports.add("search for results", to_search, "empty list", "empty list", LogAs.PASSED, null);		
				Assert.assertTrue(true);
			}

		} catch (Exception e) {
			System.out.println("problem with searching");
			ATUReports.add("problem with searching", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
	}

	/// verify search result page by recording name that was searched
	public void verifySearchResultPage(String recording) throws InterruptedException {
		
		
		 new WebDriverWait(driver, 5).until(ExpectedConditions.visibilityOf(search_result_title));
		
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
		int index = 1;
		while (bookmark_list.size() > 0) {
			for (WebElement we : bookmark_list) {
				try {     
				moveToElement(we, driver).perform();
					Thread.sleep(1000);
					clickElement(driver.findElement(By.xpath(".//*[@id='BookmarkList']/div[" +Integer.toString(index) + "]/img[3]" )));
				}catch(Exception Ex) {
				
				}
			}
		}
	}

	/// The breadcrumb structure is displayed as follows: "> Courses > course
	/// name".
	public void verifyBreadcrumbsForSearcRecording(String course_name) { //// checking
																			//// if
																			//// course
																			//// name
																			//// displayed
																			//// and
																			//// the
																			//// word
																			//// "Course"
		if ((course_name.equals(breadcrumbs_box_elements_list.get(1).getText()))
				&& (breadcrumbs_box_elements_list.get(0).getText().equals("Courses"))) {
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

	/// The search results statistics in the format as follows: "X results found
	/// for: search criterion. (XX sec)"
	public void verifyResultsStatisticsInFormat(String record_name) {
		System.out.println(list_of_results.getText());
		String result = list_of_results.getText();
		int i = 0;
		while (!(result.charAt(i++) == ' ')) {
			//// to get number of found records
		}
		int end = result.length() - 1;
		while (!(result.charAt(end--) == '(')) {

			/// to get seconds
		}
		String res_num = result.substring(0, i - 1);
		if ((Integer.parseInt(result.substring(0, i - 1)) >= 0)) {
			System.out.println("list is bigger or equal to 0 : " + res_num);
		} else {
			System.out.println("list is smaller then 0 : " + res_num);
			ATUReports.add("list is smaller then 0 ", res_num, "bigger", "smaller", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		String seconds = result.substring(end + 2, result.length() - 5);
		if ((Double.valueOf(result.substring(end + 2, result.length() - 5)) >= 0)) {
			System.out.println("seconds are bigger or equal to 0 : " + seconds);
		} else {
			System.out.println("seconds are then 0 : " + seconds);
			ATUReports.add("list is smaller then 0 ", seconds, "bigger", "smaller", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		String sentence = res_num + " results found for: " + record_name + ". (" + seconds + " sec)";
		System.out.println(sentence);
		if (result.equals(sentence)) {
			System.out.println("The search results statistics in the format as follows: "
					+ "X results found for: search criterion. (XX sec)");
			ATUReports.add(
					"The search results statistics in the format as follows: "
							+ "X results found for: search criterion. (XX sec)",
					"parameters", "contains", "contains", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("The search results statistics Not  in the format as followsed ");
			ATUReports.add(
					"The search results statistics in the format as follows: "
							+ "X results found for: search criterion. (XX sec)",
					"parameters", "contains", "not contains", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}

	}

	// verify that given a time of searched recording ,clicking on it will start
	// recording on the time mentioned in searched recording result
	/// gets a string of time stamp and verifies its equal to player play time
	public void veirfySearchRecordingClickedAndGetsNewTimeLocation(int index) {
		String location = clickOnSearchResult(index);
		System.out.println(location);
		for (String handler : driver.getWindowHandles()) {
			driver.switchTo().window(handler);
		}
		driver.switchTo().frame(0);
		waitForVisibility(player_timer);
		System.out.println("player timer visible");
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.id("PlaceHolder_StatusBarAreaTextBox"),
				"Buffering"));
		String timer = player_timer.getText();
		System.out.println(timer);
		if (timer.contains(location)) {
			System.out.println("player starts playing at: " + location);
			ATUReports.add("player starts playing at correct time", "parameters", "correct", "correct", LogAs.PASSED,
					null);
			Assert.assertTrue(true);

		} else {
			System.out.println("player not starts playing at: " + location);
			ATUReports.add("player starts playing at correct time", "parameters", "correct", " not correct",
					LogAs.PASSED, null);
			Assert.assertTrue(false);
		}
	}

	/// click on search result by its index
	public String clickOnSearchResult(int index) {
		String time_stamp_to_return = play_time_search_result.get(index).getText();
		try {
			search_result.get(index).click();/// click on search result row
			System.out.println("click succeded");
			Assert.assertTrue(true);
		} catch (Exception e) {
			System.out.println("click failed");
			Assert.assertTrue(false);

		}

		return time_stamp_to_return;

	}

	/// return to recordings page
	public void returnToCoursesPage(CoursesHelperPage course) {
		for (String handler : driver.getWindowHandles()) {
			driver.switchTo().window(handler);
			break;
		}
		if ((breadcrumbs_box_elements_list.get(0).getText()).equals("Courses")) {
			System.out.println("correct Courses ");
			ATUReports.add("correct Courses ", "contains", "contains", "contains", LogAs.PASSED, null);
			Assert.assertTrue(true);
			try {
				breadcrumbs_box_elements_list.get(0).click();
				waitForVisibility(course.first_course_button);
				System.out.println("verify course page");
				ATUReports.add("verify course page", "breadcrumbs", "contains", "contains", LogAs.PASSED, null);
				Assert.assertTrue(true);

			} catch (Exception e) {
					
			}
		} else {
			System.out.println("course name unknown");
			ATUReports.add("course name unknown", "breadcrumbs", "contains", "not contains", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
	}

	/// this function verifies top bar is in top right and under it located the
	/// search box
	public void veriySearchBoxLocation() {
		Point topbar = top_bar.getLocation();
		Point searcbox = search_box.getLocation();
		if ((topbar.getX() > searcbox.getX()) && (topbar.getY() < searcbox.getY())) {
			System.out.println("position is correct");
			ATUReports.add("verify search box location is under top-bar", "location", "location correct",
					"location correct", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("position is not correct");
			ATUReports.add("verify search box location is under top-bar", "location", "location correct",
					"location not correct", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
	}

	//// Validate the text in the Tegrity Player page: "Search in this
	//// recording..."
	public void verifySearchBoxHint() {
		if (search_box.getAttribute("title").equals("Search in this recording...")) {
			System.out.println("hint in search box is correct");
			ATUReports.add("hint in search box is correct", "hint", "Search in this recording...",
					"Search in this recording...", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("hint in search box is not correct");
			ATUReports.add("hint in search box is correct", "hint", "Search in this recording...", "bad message",
					LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
	}

	/// The breadcrumb structure is displayed as follows: " > Admin Dashboard >
	/// Courses > course name"
	public void verifyBreadcrumbsForSearcRecordingAsAdmin(String course_name) { //// checking
																				//// if
																				//// course
																				//// name
																				//// displayed
																				//// and
																				//// the
																				//// word
																				//// "Course"
		if ((course_name.equals(breadcrumbs_box_elements_list.get(2).getText()))
				&& (breadcrumbs_box_elements_list.get(1).getText().equals("Courses"))
				&& (breadcrumbs_box_elements_list.get(0).getText().equals("Admin Dashboard"))) {
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

	/// return to recordings page
	public void returnToRecordingPageByNameAsAdmin(String course_name, RecordingHelperPage rec) {
		for (String handler : driver.getWindowHandles()) {
			driver.switchTo().window(handler);
		}
		if ((breadcrumbs_box_elements_list.get(2).getText()).equals(course_name)) {
			System.out.println("correct course name");
			ATUReports.add("correct course name", "contains", "contains", "contains", LogAs.PASSED, null);
			Assert.assertTrue(true);
			try {
				breadcrumbs_box_elements_list.get(2).click();
				waitForVisibility(rec.first_recording);
				System.out.println("verify recordings page");
				ATUReports.add("verify recordings page", "breadcrumbs", "contains", "contains", LogAs.PASSED, null);
				Assert.assertTrue(true);

			} catch (Exception e) {

			}
		} else {
			System.out.println("course name unknown");
			ATUReports.add("course name unknown", "breadcrumbs", "contains", "not contains", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
	}

	/// return to recordings page
	public void returnToCoursesPageAsAdmin(CoursesHelperPage course) {
		for (String handler : driver.getWindowHandles()) {
			driver.switchTo().window(handler);
		}
		if ((breadcrumbs_box_elements_list.get(1).getText()).equals("Courses")) {
			System.out.println("correct Courses ");
			ATUReports.add("correct Courses ", "contains", "contains", "contains", LogAs.PASSED, null);
			Assert.assertTrue(true);
			try {
				breadcrumbs_box_elements_list.get(1).click();
				waitForVisibility(course.first_course_button);
				System.out.println("verify course page");
				ATUReports.add("verify course page", "breadcrumbs", "contains", "contains", LogAs.PASSED, null);
				Assert.assertTrue(true);

			} catch (Exception e) {

			}
		} else {
			System.out.println("course name unknown");
			ATUReports.add("course name unknown", "breadcrumbs", "contains", "not contains", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
	}

	/// click on pause then return caption string in particukar time
	public String getCaptionInTime(String seconds) throws Exception {
		String caption_to = null;
		int time_out = 0;
		while (!player_timer.getText().contains(seconds)) {
			if (time_out == 120) {
				System.out.println("time out");
				Assert.assertTrue(false);
				ATUReports.add("Time out", LogAs.FAILED, null);
				return caption_to;
			}
			Thread.sleep(500);
			time_out++;
		}
		try {
			pause_button.click();
			System.out.println("clicked on play button to pause");
			caption_to = driver.findElement(By.id("CC")).getText();
			System.out.println(caption_to);
			play_button.click();
		} catch (Exception e) {

			System.out.println("failed click on play button to pause");
			ATUReports.add("failed clicking", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		ATUReports.add("got caption string in time", LogAs.PASSED, null);
		Assert.assertTrue(true);
		return caption_to;
	}

	/// return to recordings page
	public void returnToRecordingPageByNameAsUserOrGuest(String course_name, RecordingHelperPage rec) {
		for (String handler : driver.getWindowHandles()) {
			driver.switchTo().window(handler);
			break;
		}
		if ((breadcrumbs_box_elements_list.get(1).getText()).equals(course_name)) {
			System.out.println("correct course name");
			ATUReports.add("correct course name", "contains", "contains", "contains", LogAs.PASSED, null);
			Assert.assertTrue(true);
			try {
				breadcrumbs_box_elements_list.get(1).click();
				waitForVisibility(rec.first_recording);
				System.out.println("verify recordings page");
				ATUReports.add("verify recordings page", "breadcrumbs", "contains", "contains", LogAs.PASSED, null);
				Assert.assertTrue(true);

			} catch (Exception e) {

			}
		} else {
			System.out.println("course name unknown");
			ATUReports.add("course name unknown", "breadcrumbs", "contains", "not contains", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
	}

	//// The search results on a recording level is displayed in the table with
	//// the columns as follows:"Location", "Time", "Context".
	public void verifySearchColumns() {

		System.out.println(columns_title_text.get(0).getText());
		System.out.println(columns_title_text.get(1).getText());
		System.out.println(columns_title_text.get(2).getText());

		if ((columns_title_text.get(0).getText().contains("Location"))
				&& (columns_title_text.get(1).getText().equals("Time"))
				&& (columns_title_text.get(2).getText().equals("Context"))) {
			System.out.println("verified columns search text title");
			ATUReports.add("verify columns search text title", "columns title", "displayed", "displayed", LogAs.PASSED,
					null);
			Assert.assertTrue(true);
		} else {
			System.out.println("not verified columns search text title");
			ATUReports.add("verify columns search text title", "columns title", "displayed", "not displayed",
					LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
	}

	//// this function adds a bookmark
	public void addBookMark(String bookmark, String time_to_add_bookmark) throws Exception {

		bookmark_text_infut_field.sendKeys(bookmark);
		System.out.println("bookmark name written successfully");
		add_bookmark_button.click();
		System.out.println("clicked add bookmark button");
		Thread.sleep(3000);
		verifyBookMarkVisibility(time_to_add_bookmark, bookmark);

	}

	/// this function verifies added bookmark according to its name and capture
	/// time
	public void verifyBookMarkVisibility(String time_to_add_bookmark, String bookmark_name) {
		int index = 0;
		for (WebElement e : bookmark_names) {

			if (e.getText().equals(bookmark_name)) {
				System.out.println("bookmark name found");
				System.out.println(bookmark_duration_time.get(index).getText());
				if (bookmark_duration_time.get(index).getText().equals(time_to_add_bookmark)) {
					System.out.println("verified bookmark name and time");
					ATUReports.add("verify bookmark name and time", "bookmark name and time", "displayed correctly",
							"displayed correctly", LogAs.PASSED, null);
					Assert.assertTrue(true);
					return;
				}
			}

			index++;
		}
		System.out.println("not verified bookmark name and time");
		ATUReports.add("verify bookmark name and time", "bookmark name and time", "displayed correctly",
				"not displayed or displayed not correctly", LogAs.FAILED, null);
		Assert.assertTrue(false);

	}

	/// click on pause in specific time then add bookmark
	public void addBookmarkInSpecificTime(String bookmark, String seconds) throws Exception {

		int time_out = 0;
		while (!player_timer.getText().contains(seconds)) {
			if (time_out == 120) {
				System.out.println("time out");
				Assert.assertTrue(false);
				ATUReports.add("Time out", LogAs.FAILED, null);

			}
			Thread.sleep(500);
			time_out++;
		}
		try {
			pause_button.click();
			System.out.println("clicked on play button to pause");
			addBookMark(bookmark, seconds);

			play_button.click();
		} catch (Exception e) {

			System.out.println("failed click on play button to pause");
			ATUReports.add("failed clicking", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		ATUReports.add("got right time", LogAs.PASSED, null);
		Assert.assertTrue(true);

	}
	
	
	// This function watch the recording completely
	public void watchTheRecordingCompletely() throws InterruptedException {
		String pre_time_buffer = time_buffer_status.getText().split("/")[0].trim();
		boolean looping = true;
		int seconds = 0;
		
		while(looping) {
			next_button.click();
			String buffering = time_buffer_status.getText();
			if(buffering.equals("Buffering")) {
				Thread.sleep(1000);
				continue;
			} else if (buffering.split("/")[0].trim().equals(pre_time_buffer)) {
				break;
			} else {
				pre_time_buffer = buffering.split("/")[0].trim();
				continue;
			}
		}
		
		looping = true;
		
		while(looping) {
			String buffering = time_buffer_status.getText();
			String CurrentTime = buffering.split("/")[0].trim();
			String EndTime = buffering.split("/")[1].trim();
			
			//get the last digit 
			String AlmostEndTime = EndTime.substring(EndTime.length() - 1,EndTime.length());
			int lastDigit = Integer.parseInt(AlmostEndTime);
			lastDigit--;
			AlmostEndTime = EndTime.substring(0, EndTime.length()-1) + String.valueOf(lastDigit) ;
					
			if(buffering.equals("Buffering")) {
				Thread.sleep(1000);
				continue;
			} else if (CurrentTime.equals(EndTime)) {
				break;
			} else if(CurrentTime.equals(AlmostEndTime)){	
				Thread.sleep(4000);
				break;
			} else {
				Thread.sleep(1000);
			}
			
			
		}
		
		System.out.println("Finshed.");
		
	}

	public String getSecondRecord() {
		waitForVisibility(second_record_player);
		return second_record_player.getText();
	}

}
