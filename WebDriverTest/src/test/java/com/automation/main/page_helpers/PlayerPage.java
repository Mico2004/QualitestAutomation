package com.automation.main.page_helpers;



import java.util.List;
import java.util.Locale;

import org.apache.commons.collections.functors.SwitchTransformer;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;


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
	@FindBy(id = "PauseButton")
	WebElement pause_button;
	@FindBy(id = "Add_Img")
	public WebElement add_bookmark_button;
	@FindBy(id = "InputTextArea")
	WebElement bookmark_input_text;
	@FindBy(css = ".BookmarkButton")
	public WebElement delete_button;
	@FindBy(css = ".BookmarkSelected>.BookmarkButton")
	List<WebElement> selected_bookmark_buttons_list;
	@FindBy(xpath = ".//*[@id='BookmarkList']/div")
	List<WebElement> bookmark_list;
	@FindBy(xpath = ".//*[@id='BookmarkList']/div/div")
	List<WebElement> bookmark_list_numbers;
	@FindBy(css = "#BookmarkList")
	WebElement bookmark_listCss;
	@FindBy(id = "tegritySearchBox")
	public	WebElement search_box;
	@FindBy(id = "ddComboDiv")
	WebElement bookmarkType;
	@FindBy(xpath = ".//*[@id='scrollableArea']/div[2]/div/div/div/accordion/div/div[1]/div[2]/div/div[3]/a/div[2]/p[2]")
	WebElement second_record_player;
	@FindBy(id = "NumbOfRes")
	WebElement list_of_results;
	@FindBy(xpath = ".//*[@id='Projector_CPTN']/div/span") ////html/body/div[2]/div[4]/div/span")
	WebElement search_result_title;
	@FindBy(xpath = ".//*[@id='Links_CPTN_TEXT']/span")
	WebElement bookmarks_title;
	@FindBy(css = "#undefined_TXT")
	WebElement course_breadcrumbs;
	@FindBy(xpath = "//*[@id=\"tegrityBreadcrumbsBox\"]/li[2]/a")
	WebElement course_name_breadcrumbs;
	@FindBy(xpath = "//*[@id=\"playerContainer\"]")
	public  WebElement iframe;
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
	public WebElement play_button;
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
	public @FindBy(id = "NextButton_Img")
	WebElement next_button;
	@FindBy(id = "InstituteLogotype")
	WebElement tegrity_universty_logo;
	@FindBy(xpath =".//*[@id='DownDiv']/table/tbody/tr[1]/th[3]")
	public WebElement context_title;
	@FindBy(css = ".SearchResultContext")
	public List<WebElement> SearchResultContext;
	@FindBy(id= "TegrityLogo")
	WebElement tegrity_logo;
	@FindBy(id= "Back30_Img")
	WebElement Back30_seconds;
	@FindBy(id= "PrevButton_Img")
	WebElement PrevButton;
	@FindBy(id= "NextButton_Img")
	WebElement NextButton;
	@FindBy(id= "VolumeImage")
	WebElement VolumeImage;	
	@FindBy(id= "ControlPanelDivTextBox")
	WebElement ControlPanelDivTextBox;
	@FindBy(id= "VolumeSliderDiv")
	WebElement VolumeSliderDiv;
	@FindBy(id= "VolumeSliderButton")
	WebElement VolumeSliderButton;
	@FindBy(id= "FastForwardSliderButton_Img")
	WebElement FastForwardSlide;
	@FindBy(id= "TimeSliderLine")
	WebElement TimeSliderLine;
	@FindBy(css= ".SearchResultTime")
	List<WebElement> SearchResultTimes;
	@FindBy(css= ".SearchResultLocation")
	public List<WebElement> SearchResultlocation;
	@FindBy(xpath = ".//*[@id='ddPopupDiv']/div[2]/div")
	public WebElement important;
	@FindBy(xpath = ".//*[@id='ddPopupDiv']/div[1]/div")
	public WebElement inclear;
	@FindBy(id= "ChapterMarksHolderDiv")
	public WebElement seek_bar;
	@FindBy(css = ".BookmarkTime")
	public List<WebElement> bookmarkTimeList;
	@FindBy(css = ".BookmarkTextInst")
	public List<WebElement> bookmarkIsnstNames;
	@FindBy(css = ".BookmarkText")
	public List<WebElement> bookmarkStudentNames;
	@FindBy(css = ".BookmarkButton")
	public List<WebElement> bookmarks_buttons;
	private String timeReturn;
	
	// This function get as input number of seconds.
	// It will check if the player plays for this number of seconds.
	public boolean verifyTimeBufferStatusForXSec(int seconds) throws InterruptedException {
		if (seconds <= 0) {
			System.out.println("Please give positive number of seconds as input for verifing buffer status.");
			ATUReports.add(time +" Please give positive number of seconds as input for verifing buffer status.", LogAs.FAILED,new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
			return false;
		}

		// Wait for switching frame		
			try {
				getIntoFrame(0);	
				ATUReports.add(time +" Switching to player frame.", "Success to switch to player frame.",
						"Success to switch to player frame.", LogAs.PASSED, null);
			} catch (Exception msg) {			
					System.out.println("Switching to player frame.");
					ATUReports.add(time +" Switching to player frame.", "Success to switch to player frame.",
							"Fail to switch to player frame.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
					Assert.assertTrue(false);							
			}

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
			ATUReports.add(time +" Player.", "Player shows pause button.", "Player shows pause button.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} catch (Exception msg) {
			System.out.println("Player not shows pause button.");
			ATUReports.add(time +" Player.", "Player shows pause button.", "Player not shows pause button.", LogAs.FAILED,
					new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
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
						ATUReports.add(time +" The time buffer.", "The time buffer is moving correctly.",
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
					ATUReports.add(time +" The time buffer.", "The time buffer is moving correctly.",
							"The time buffer status is stuck on Buffering status and not moving.", LogAs.WARNING, null);
					// Assert.assertTrue(false);
					return false;
				}
			}
		}

		System.out.println("The time buffer is moving correctly.");
		ATUReports.add(time +" The time buffer.", "The time buffer is moving correctly.",
				"The time buffer is moving correctly.", LogAs.PASSED, null);
		Assert.assertTrue(true);
		return true;
	}
	
	public void changeTheBookmarkToBeImportant() {
	
		clickElementJS(bookmarkType);
		clickElementWithOutIdJS(important);	
	}
	
	public void verifyUniversityLogoVisibilityAndLocation() throws InterruptedException {
		
		waitForVisibility(tegrity_universty_logo);
		if(isElemenetDisplayed(By.id("InstituteLogotype"))){
			System.out.println("The tegrity University logo is displayed.");
			ATUReports.add(time +" The tegrity logo is displayed.",LogAs.PASSED, null);
			Assert.assertTrue(true);
			
		} else {
			System.out.println("The tegrity University logo is not displayed.");
			ATUReports.add(time +" The tegrity University logo is not displayed.",LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);	
		}
		Thread.sleep(1000);
		Point logo_location = tegrity_universty_logo.getLocation();
		Point serach_box= search_box.getLocation();
		
		if((logo_location.x < serach_box.x)) {
			System.out.println("Verifed that logo at the top left cornner.");
			ATUReports.add(time +" Verifed that logo at the top left cornner", "True.", "True.", LogAs.PASSED, null);
		} else {
			System.out.println("Not Verifed that logo at the top left cornner.");
			ATUReports.add(time +" Not Verifed that logo at the top left cornner.", "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
		
	}
	
	public void verifyBackgroundColor(String Color , WebElement element){
		
	String colorToCheck = ""; 	
	String text = "";
		try {
			text = element.getCssValue("background-color").toString();
			// Split css value of rgb
			String[] numbers = text.replace("rgba(", "").replace(")", "").split(",");
			int number1 = Integer.parseInt(numbers[0]);
			numbers[1] = numbers[1].trim();
			int number2 = Integer.parseInt(numbers[1]);
			numbers[2] = numbers[2].trim();
			int number3 = Integer.parseInt(numbers[2]);
			colorToCheck =  String.format("#%02x%02x%02x", number1, number2, number3);
		} catch (Exception msg) {
				System.out.println("Not Verifed that the color is dark grey.");
				ATUReports.add(time +" Not Verifed that the color is dark grey.", "True.", "False.", LogAs.FAILED, null);
		}	
	if(colorToCheck.equals(Color)) {
		System.out.println("Verifed that the color is dark grey.");
		ATUReports.add(time +"  Verifed that the color is dark grey.", "True.", "True.", LogAs.PASSED, null);
	} else {
		System.out.println("Not Verifed that the color is dark grey.");
		ATUReports.add(time +" Not Verifed that the color is dark grey.", "True.", "False.", LogAs.FAILED, null);
	}
	

	}
	
	public void verifySearchResultsTableAreInRows(){
		
		if(SearchResultContext.size() != SearchResultTimes.size() ||  SearchResultContext.size()!= SearchResultlocation.size()) {		
			System.out.println("The size of the lists aren't equal.");
			ATUReports.add(time +" The size of the lists aren't equal.", "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));				
		} else {
			System.out.println("The size of the lists are at the same size.");
			ATUReports.add(time +" The size of the lists are at the same size.", "True.", "True..", LogAs.PASSED, null);
				
		}
		
		int i=0;
		for (WebElement e : driver.findElements(By.cssSelector(".SearchResultLocation"))) {			    		
				String current_element = getTextFromWebElement(e,5);						
				if (!current_element.equals("Recording Chapter") && !current_element.equals("Recording Title") ) {
					System.out.println("Not Verify that the results of the row of location are fine." + current_element);
					ATUReports.add(time +" Not verify that the results of the row of location are fine." + current_element, "True.", "false", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
					break;
				}
				i++;
			   } 		
		
		System.out.println("Verify that the results of the row of location are fine.");
		ATUReports.add(time +" Verify that the results of the row of location are fine.", "True.", "false", LogAs.PASSED, null);
		
		i=0;
		for (WebElement e : driver.findElements(By.cssSelector(".SearchResultTime"))) {			    		
				String current_element = getTextFromWebElement(e,5);						
				
				if (!checkThatTheTimeIsValid(current_element)) {
					System.out.println("Not verify that the results of the row of time are fine.");
					ATUReports.add(time +" Not verify that the results of the row of time are fine.", "True.", "false", LogAs.FAILED,new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
					break;
				}
				i++;
			   } 	
	
		System.out.println("Verify that the results of the row of time are fine.");
		ATUReports.add(time +" Verify that the results of the row of time are fine.", "True.", "false", LogAs.PASSED, null);
		
		i=0;
		for (WebElement e : driver.findElements(By.cssSelector(".SearchResultContext"))) {			    		
				String current_element = getTextFromWebElement(e,5);						
				
				if (current_element.isEmpty()) {
					System.out.println("Not Verify that the results of the row of context are fine.");
					ATUReports.add(time +" Not verify that the results of the row of context are fine.", "True.", "false", LogAs.FAILED, null);
					break;
				}
				i++;
			   } 	
	
		System.out.println("Verify that the results of the row of context are fine.");
		ATUReports.add(time +" Verify that the results of the row of context are fine.", "True.", "false", LogAs.PASSED, null);
		
	}
	
	public boolean checkThatTheTimeIsValid(String time){
	
		DateFormat df = new SimpleDateFormat("h:mm:ss",Locale.US);
	    try {
	        df.parse(time);
	        return true;
	    } catch ( ParseException exc ) {
	    	return false;
	    }  		
	}
	
	public void verifyTegrityLogoVisibilityAndLocation(){
		
		waitForVisibility(tegrity_logo);
		if(isElemenetDisplayed(By.id("TegrityLogo"))){
			System.out.println("The tegrity logo is displayed.");
			ATUReports.add(time +" The tegrity logo is displayed.",LogAs.PASSED, null);
			Assert.assertTrue(true);
			
		} else {
			System.out.println("The tegrity logo is not displayed.");
			ATUReports.add(time +" The tegrity logo is not displayed.",LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);	
		}
		
		Point logo_location = tegrity_logo.getLocation();
		Point play_button_location= play_button.getLocation();
		
		if((play_button_location.x < logo_location.x)) {
			System.out.println("Verifed that logo at the buttom right cornner.");
			ATUReports.add(time +" Verifed that logo at the top left cornner", "True.", "True.", LogAs.PASSED, null);
		} else {
			System.out.println("Not Verifed that logo at the buttom right cornner.");
			ATUReports.add(time +" Not Verifed that logo at the top left cornner.", "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
	
	}
	
	public void verifyWebElementIsDisplay(WebElement webelement ){
		
		waitForVisibility(webelement);
		if (isElementPresent(webelement)) {
			System.out.println("Verfied that " + webelement.getAttribute("id").toString() + " displayed.");
			ATUReports.add(time +" Verfied that" + webelement.getAttribute("id").toString() + "displayed.", "True.", "True.", LogAs.PASSED, null);
			
		} else {
			System.out.println("Not Verfied that " + webelement.getAttribute("id").toString() + " displayed.");
			ATUReports.add(time +" Not Verfied that " + webelement.getAttribute("id").toString() + " displayed.", "True.", "False.", LogAs.FAILED,  new CaptureScreen(ScreenshotOf.BROWSER_PAGE));

		}
	
	}
	
	public void verifybookmarkTitle(){
		
		//verify that bookmarks title is displayed 		
		verifyWebElementIsDisplay(bookmarks_title);
		
		//verify that the bookmarks book is display
		verifyWebElementIsDisplay(bookmark_listCss);
		
	}
	
	public void verifyPlayersButtonsAndTimeBuffer() {
		
		//verify that play button is display
		verifyWebElementIsDisplay(play_button);
		
		//verify that play button is display
		verifyWebElementIsDisplay(NextButton);
		
		//verify that play button is display
		verifyWebElementIsDisplay(PrevButton);
		
		//verify that play button is display
		verifyWebElementIsDisplay(Back30_seconds);
		
		//verify that play button is display
		verifyWebElementIsDisplay(TimeSliderLine);
		
		//verify that play button is display
		verifyWebElementIsDisplay(VolumeSliderDiv);
		
		//verify that play button is display
		verifyWebElementIsDisplay(VolumeImage);
				
		//verify that play button is display
		verifyWebElementIsDisplay(ControlPanelDivTextBox);
		
		//verify that play button is display
		verifyWebElementIsDisplay(VolumeSliderButton);
		
		//verify that play button is display
		verifyWebElementIsDisplay(FastForwardSlide);
		
	}
	
	// TODO: get video with alert in FireFox and rework on this function.
	public boolean verifyTimeBufferStatusForXSecAlertVersion(int seconds) throws InterruptedException {
		try {
			System.out.println(driver.switchTo().alert().getText());
			driver.switchTo().alert().accept();
		} catch (org.openqa.selenium.NoAlertPresentException ex) {
			if (seconds <= 0) {
				System.out.println("Please give positive number of seconds as input for verifing buffer status.");
				ATUReports.add(time +" Please give positive number of seconds as input for verifing buffer status.",
						LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
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
					ATUReports.add(time +" The time buffer status is not moving in seconds.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
					Assert.assertTrue(false);
					return false;
				}
			}

			System.out.println("The time buffer is moving correctly.");
			ATUReports.add(time +" The time buffer is moving correctly.", LogAs.PASSED, null);
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
			ATUReports.add(time +" Current url is contains partially url from podcast page: " + current_url + " contains "
					+ partially_url, LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Current url is not contains partially url from podcast page: " + current_url + " != "
					+ partially_url);
			ATUReports.add(time +" Current url is not contains partially url from podcast page: " + current_url + " != "
					+ partially_url, LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}

	// This function verify that you cannot add bookmark
	public void verifyThatUserCannotAddBookmark() {
		if (add_bookmark_button.isDisplayed()) {
			System.out.println("Not verfied that user cannot add bookmark.");
			ATUReports.add(time +" Verfied that user cannot add bookmark.", "True.", "False.", LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		} else {
			System.out.println("Verfied that user cannot add bookmark.");
			ATUReports.add(time +" Verfied that user cannot add bookmark.", "True.", "True.", LogAs.INFO, null);
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
			search_box.sendKeys(to_search + Keys.ENTER);	
			Thread.sleep(1000);
			search_box.clear();
			
			System.out.println("search the record: " + to_search);
			ATUReports.add(time +" search the record: " + to_search, LogAs.PASSED, null);
			
			for (String handler : driver.getWindowHandles()) {
				driver.switchTo().window(handler);
				break;
			}
			driver.switchTo().frame(0);
			waitForVisibility(list_of_results);
			Thread.sleep(1000);
			if (list_of_results.getText().contains("No results found for:")) {
				System.out.println("no results found for " + to_search);
				ATUReports.add(time +" search for results", to_search, "empty list", "empty list", LogAs.PASSED, null);
				Assert.assertTrue(true);
			} else {
				System.out.println("results found for " + to_search);
				ATUReports.add(time +" search for results", to_search, "empty list", "not empty list", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Assert.assertTrue(false);
			}

		} catch (Exception e) {
			System.out.println("problem with searching");
			ATUReports.add(time +" problem with searching", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}

	public void searchRecord(String to_search) throws InterruptedException{

		Thread.sleep(2000);
		search_box.clear();
		search_box.sendKeys(to_search + Keys.ENTER);	
		Thread.sleep(1000);
		search_box.clear();
		
		System.out.println("search the record: " + to_search);
		ATUReports.add(time +" search the record: " + to_search, LogAs.PASSED, null);
		
	}
	

	//// search for course
	public void verifySearchForRecordingExist(String to_search) {
		try {

			Thread.sleep(2000);
			search_box.clear();
			search_box.sendKeys(to_search + Keys.ENTER);	
			Thread.sleep(1000);
			search_box.clear();
			for (String handler : driver.getWindowHandles()) {
				driver.switchTo().window(handler);
				break;
			}
			driver.switchTo().frame(0);
			waitForVisibility(list_of_results);
			if (list_of_results.getText().contains("No results found for:")) {
				System.out.println("no results found for " + to_search);
				ATUReports.add(time +" search for results", to_search, "empty list", "not empty list", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Assert.assertTrue(false);
			} else {
				System.out.println("results found for " + to_search);
				ATUReports.add(time +" search for results", to_search, "empty list", "empty list", LogAs.PASSED, null);		
				Assert.assertTrue(true);
			}

		} catch (Exception e) {
			System.out.println("problem with searching");
			ATUReports.add(time +" problem with searching", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}

	/// verify search result page by recording name that was searched
	public void verifySearchResultPage(String recording) throws InterruptedException {
			

		WebElement titleResults = driver.findElements(By.id("undefined_TXT")).get(2);
		WebElement titleResults2 = driver.findElements(By.id("undefined_TXT")).get(3);
		
		String text = titleResults.getText(); 
		String text2 = titleResults2.getText(); 
		
		if ((text.contains(recording)) && (text.contains("- Search Results"))) {
			System.out.println("result search page verified for recording: " + recording);
			ATUReports.add(time +" result search page verified for recording: ", recording, "contains", "contains",
					LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else if((text2.contains(recording)) && (text2.contains("- Search Results"))){
			System.out.println("result search page verified for recording: " + recording);
			ATUReports.add(time +" result search page verified for recording: ", recording, "contains", "contains",
					LogAs.PASSED, null);
			Assert.assertTrue(true);
		}
		else{
			System.out.println("result search page not verified for recording: " + recording);
			ATUReports.add(time +" result search page  verified for recording: ", recording, "contains", "not contains",
					LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}
	

	/// The breadcrumb structure is displayed as follows: "> Courses > course
	/// name".
	public void verifyBreadcrumbsForSearcRecoding(String course_name) {
				
		String courses_name = breadcrumbs_box_elements_list.get(1).getText();
		String courses = breadcrumbs_box_elements_list.get(0).getText();		
		if (course_name.equals(courses_name) &&  courses.equals("Courses")) {
			System.out.println("course breadcrumbs and course name breadcrumbs were verified");
			ATUReports.add(time +" course breadcrumbs and course name breadcrumbs were verified", "breadcrumbs", "contains",
					"contains", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("course breadcrumbs and course name breadcrumbs were not verified");
			ATUReports.add(time +" course breadcrumbs and course name breadcrumbs were verified", "breadcrumbs", "contains",
					"not contains", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}
	
	
	public void verifySearchResultIsEmpty() {
		if(SearchResultTimes.size() == 0 && SearchResultContext.size() == 0 && SearchResultlocation.size()==0) {
			System.out.println("Verified that search result is empty.");
			ATUReports.add(time +" Verified that search result is empty.", "True.", "True.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Not verified that search result is empty.");
			ATUReports.add(time +" Verified that search result is empty.", "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
//			Assert.assertTrue(false);
		}
	}
	
	
	public void verifySearchResultIsNotEmpty() {
		if(SearchResultTimes.size() >= 1 && SearchResultContext.size() >= 1 && SearchResultlocation.size()>= 1) {
			System.out.println("Verified that search result is not empty.");
			ATUReports.add(time +" Verified that search result is not empty.", "True.", "True.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Not verified that search result is not empty.");
			ATUReports.add(time +" Not verified that search result is not empty.", "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
//			Assert.assertTrue(false);
		}
	}
	

	// This function add String to bookmark
	public void addTargetBookmark(String target_bookmark) throws InterruptedException {
		System.out.println(time_buffer_status.getText());
		sendStringToWebElement(bookmark_input_text, target_bookmark);
		clickElementJS(add_bookmark_button);
		//Thread.sleep(500);
		//String timeToReturn =  time_buffer_status.getText();
		System.out.println(time_buffer_status.getText());
		System.out.println("Target bookmark added.");
		ATUReports.add(time +" Target bookmark added.", "True.", "True.", LogAs.PASSED, null);
		//return timeToReturn;
	}
	
	
	public String getTimeOfBookmark(String target_bookmark) throws InterruptedException{
		
		String time = null,timeReturn = null;
		try {
		Thread.sleep(500);	
		int size = bookmark_list_numbers.size();	
		for(int i = 0  ;i<size ; i++){
			
			String currentName = driver.findElement(By.xpath(".//*[@id='BookmarkList']/div[" + Integer.toString(i+1)+ "]/div/div[3]")).getText();
			if(currentName.equals(target_bookmark)){
				time = driver.findElement(By.xpath(".//*[@id='BookmarkList']/div["+ Integer.toString(i+1)+ "]/div/div[2]")).getText();
				break;
			}
		}
		String timeToReturn =  time_buffer_status.getText();
		String[] SplitTime = timeToReturn.split("/");
		timeReturn = time + " /" + SplitTime[1];
		System.out.println(timeReturn);
		return timeReturn;
		
		} catch (Exception e) {
			e.printStackTrace();
			ATUReports.add(e.getMessage(), "Success.", "Failed.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			return timeReturn;
		}
		
	}
	
	// This function delete all bookmarks
	public void deleteAllBookmark() throws InterruptedException {
		try{
		int size = bookmark_list.size();		
		if(size>0){
				for (WebElement we : bookmark_list) {
							moveToElementAndPerform(we, driver);
						    clickElementWithOutIdJS(bookmarks_buttons.get(2));						   
					}
				Thread.sleep(500);
				if( bookmark_list.size() > 0){
					  clickElementWithOutIdJS(driver.findElement(By.xpath(".//*[@id='BookmarkList']/div/img[3]")));		
				}							
		} else {
			System.out.println("There no bookmarks here.");
			ATUReports.add(time +" There no bookmarks here.", "True.", "True.", LogAs.PASSED, null);
		}
		} catch (Exception e) {
			e.printStackTrace();
			ATUReports.add(time +e.getMessage(), "Success.", "Failed.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
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
			ATUReports.add(time +" course breadcrumbs and course name breadcrumbs were verified", "breadcrumbs", "contains",
					"contains", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("course breadcrumbs and course name breadcrumbs were not verified");
			ATUReports.add(time +" course breadcrumbs and course name breadcrumbs were verified", "breadcrumbs", "contains",
					"not contains", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}

	/// The search results statistics in the format as follows: "X results found
	/// for: search criterion. (XX sec)"
	public void verifyResultsStatisticsInFormat(String record_name) {
		String sentence;
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
			ATUReports.add(time +" list is bigger or equal then 0 ", res_num, "bigger", "smaller", LogAs.PASSED,null);
			Assert.assertTrue(true);
		} else {
			System.out.println("list is smaller then 0 : " + res_num);
			ATUReports.add(time +" list is smaller then 0 ", res_num, "bigger", "smaller", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
		String seconds = result.substring(end + 2, result.length() - 5);
		if ((Double.valueOf(result.substring(end + 2, result.length() - 5)) >= 0)) {
			System.out.println("seconds are bigger or equal to 0 : " + seconds);
		} else {
			System.out.println("seconds are then 0 : " + seconds);
			ATUReports.add(time +" list is smaller then 0 ", seconds, "bigger", "smaller", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
		int NumOfresult = Integer.parseInt(res_num);
		
		if(NumOfresult <= 1){
			sentence = res_num + " result found for: " + record_name + ". (" + seconds + " sec)";
		}
		else sentence = res_num + " results found for: " + record_name + ". (" + seconds + " sec)";
		System.out.println(sentence);
		if (sentence.contains(result)) {
			System.out.println("The search results statistics in the format as follows: "+ "X results found for: search criterion. (XX sec)");
			ATUReports.add(time +" The search results statistics in the format as follows: "
							+ "X results found for: search criterion. (XX sec)","parameters", "contains", "contains", LogAs.PASSED, null);
			Assert.assertTrue(true);	
		} else {
			System.out.println("The search results statistics Not in the format as followsed ");
			ATUReports.add(
					"The search results statistics in the format as follows: "
							+ "X results found for: search criterion. (XX sec)",
					"parameters", "contains", "not contains", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
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
		wait.until(ExpectedConditions.invisibilityOfElementWithText(By.id("PlaceHolder_StatusBarAreaTextBox"),"Buffering"));
		String timer = player_timer.getText();
		String subTimer = timer.substring(0,6); 
		System.out.println(timer);
		if (timer.contains(subTimer)) {
			System.out.println("player starts playing at: " + location);
			ATUReports.add(time +" player starts playing at correct time", "parameters", "correct", "correct", LogAs.PASSED,
					null);
			Assert.assertTrue(true);

		} else {
			System.out.println("player not starts playing at: " + location);
			ATUReports.add(time +" player not starts playing at correct time", "parameters", "correct", " not correct",
					LogAs.FAILED, null);
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
			ATUReports.add(time +" correct Courses ", "contains", "contains", "contains", LogAs.PASSED, null);
			Assert.assertTrue(true);
			try {
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", breadcrumbs_box_elements_list.get(0));								
				waitForVisibility(course.first_course_button);
				System.out.println("verify course page");
				ATUReports.add(time +" verify course page", "breadcrumbs", "contains", "contains", LogAs.PASSED, null);
				Assert.assertTrue(true);

			} catch (Exception e) {
					
			}
		} else {
			System.out.println("course name unknown");
			ATUReports.add(time +" course name unknown", "breadcrumbs", "contains", "not contains", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}
	
	/// return to recordings page
	public void returnToAdminPage(AdminDashboardPage admin ) {
		for (String handler : driver.getWindowHandles()) {
			driver.switchTo().window(handler);
			break;
		}
		if ((breadcrumbs_box_elements_list.get(0).getText()).equals("Admin Dashboard")) {
			System.out.println("correct Admin");
			ATUReports.add(time +" correct Admin", "contains", "contains", "contains", LogAs.PASSED, null);
			Assert.assertTrue(true);
			try {
				breadcrumbs_box_elements_list.get(0).click();
				waitForVisibility(admin.CoursesBox);
				System.out.println("verify admin dashboard page");
				ATUReports.add(time +" verify course page", "breadcrumbs", "contains", "contains", LogAs.PASSED, null);
				Assert.assertTrue(true);

			} catch (Exception e) {
					
			}
		} else {
			System.out.println("course name unknown");
			ATUReports.add(time +" course name unknown", "breadcrumbs", "contains", "not contains", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
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
			ATUReports.add(time +" verify search box location is under top-bar", "location", "location correct",
					"location correct", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("position is not correct");
			ATUReports.add(time +" verify search box location is under top-bar", "location", "location correct",
					"location not correct", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}

	//// Validate the text in the Tegrity Player page: "Search in this
	//// recording..."
	public void verifySearchBoxHint() {
		if (search_box.getAttribute("title").equals("Search in this recording...")) {
			System.out.println("hint in search box is correct");
			ATUReports.add(time +" hint in search box is correct", "hint", "Search in this recording...",
					"Search in this recording...", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("hint in search box is not correct");
			ATUReports.add(time +" hint in search box is correct", "hint", "Search in this recording...", "bad message",
					LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}

	/// The breadcrumb structure is displayed as follows: " > Admin Dashboard > Courses > course name"
	public void verifyBreadcrumbsForSearcRecordingAsAdmin(String course_name) { // checking if course name displayed and the word "Course"
		if ((course_name.equals(breadcrumbs_box_elements_list.get(2).getText()))
				&& (breadcrumbs_box_elements_list.get(1).getText().equals("Courses"))
				&& (breadcrumbs_box_elements_list.get(0).getText().equals("Admin Dashboard"))) {
			System.out.println("course breadcrumbs and course name breadcrumbs were verified");
			ATUReports.add(time +" course breadcrumbs and course name breadcrumbs were verified", "breadcrumbs", "contains",
					"contains", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("course breadcrumbs and course name breadcrumbs were not verified");
			ATUReports.add(time +" course breadcrumbs and course name breadcrumbs were verified", "breadcrumbs", "contains",
					"not contains", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
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
			ATUReports.add(time +" correct course name", "contains", "contains", "contains", LogAs.PASSED, null);
			Assert.assertTrue(true);
			try {
				breadcrumbs_box_elements_list.get(2).click();
				waitForVisibility(rec.first_recording);
				System.out.println("verify recordings page");
				ATUReports.add(time +" verify recordings page", "breadcrumbs", "contains", "contains", LogAs.PASSED, null);
				Assert.assertTrue(true);

			} catch (Exception e) {

			}
		} else {
			System.out.println("course name unknown");
			ATUReports.add(time +" course name unknown", "breadcrumbs", "contains", "not contains", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
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
			ATUReports.add(time +" correct Courses ", "contains", "contains", "contains", LogAs.PASSED, null);
			Assert.assertTrue(true);
			try {
				breadcrumbs_box_elements_list.get(1).click();
				waitForVisibility(course.first_course_button);
				System.out.println("verify course page");
				ATUReports.add(time +" verify course page", "breadcrumbs", "contains", "contains", LogAs.PASSED, null);
				Assert.assertTrue(true);

			} catch (Exception e) {

			}
		} else {
			System.out.println("course name unknown");
			ATUReports.add(time +" course name unknown", "breadcrumbs", "contains", "not contains", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
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
				ATUReports.add(time +" Time out", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				return caption_to;
			}
			Thread.sleep(500);
			time_out++;
		}
		try {
			//driver.switchTo().frame(driver.findElement(By.id("playerContainer")));
			pause_button.click();
			System.out.println("clicked on play button to pause");
			caption_to = driver.findElement(By.id("CC")).getText();
			System.out.println(caption_to);
			play_button.click();
		} catch (Exception e) {

			System.out.println("failed click on play button to pause");
			e.printStackTrace();
			ATUReports.add(time +" failed clicking", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
		ATUReports.add(time +" got caption string in time", LogAs.PASSED, null);
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
			ATUReports.add(time +" correct course name", "contains", "contains", "contains", LogAs.PASSED, null);
			Assert.assertTrue(true);
			try {
				breadcrumbs_box_elements_list.get(1).click();
				waitForVisibility(rec.first_recording);
				System.out.println("verify recordings page");
				ATUReports.add(time +" verify recordings page", "breadcrumbs", "contains", "contains", LogAs.PASSED, null);
				Assert.assertTrue(true);

			} catch (Exception e) {

			}
		} else {
			System.out.println("course name unknown");
			ATUReports.add(time +" course name unknown", "breadcrumbs", "contains", "not contains", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}
	
	/// return to recordings page
	public void returnToCoursePageByNameAsUserOrGuest(CoursesHelperPage course){
		
		for (String handler : driver.getWindowHandles()) {
			driver.switchTo().window(handler);
			break;
		}
		if ((breadcrumbs_box_elements_list.get(0).getText()).equals("Courses")) {
			System.out.println("correct name: Courses ");
			ATUReports.add(time +" correct name: Courses", "contains", "contains", "contains", LogAs.PASSED, null);
			Assert.assertTrue(true);
			try {
				breadcrumbs_box_elements_list.get(0).click();
				waitForVisibility(course.first_course_button);
				System.out.println("verify course page");
				ATUReports.add(time +" verify course page", "breadcrumbs", "contains", "contains", LogAs.PASSED, null);
				Assert.assertTrue(true);

			} catch (Exception e) {

			}
		} else {
			System.out.println("course name unknown");
			ATUReports.add(time +" course name unknown", "breadcrumbs", "contains", "not contains", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
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
			ATUReports.add(time +" verify columns search text title", "columns title", "displayed", "displayed", LogAs.PASSED,
					null);
			Assert.assertTrue(true);
		} else {
			System.out.println("not verified columns search text title");
			ATUReports.add(time +" verify columns search text title", "columns title", "displayed", "not displayed",
					LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}

	//// this function adds a bookmark
	public void addBookMark(String bookmark, String time_to_add_bookmark) throws Exception {

		Thread.sleep(500);
		bookmark_text_infut_field.sendKeys(bookmark);
		System.out.println("bookmark name written successfully");
		
		Thread.sleep(500);
		clickElement(add_bookmark_button);
		if(!bookmark_text_infut_field.getText().isEmpty()){
			clickElementJS(add_bookmark_button);
		}
		System.out.println("clicked add bookmark button");
		Thread.sleep(4000);
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
				
				String SecondAfter = time_to_add_bookmark.substring(time_to_add_bookmark.length() - 1,time_to_add_bookmark.length());
				int lastDigit = Integer.parseInt(SecondAfter);
				lastDigit++;
				SecondAfter = time_to_add_bookmark.substring(0, time_to_add_bookmark.length()-1) + String.valueOf(lastDigit) ;
				
				if (bookmark_duration_time.get(index).getText().equals(time_to_add_bookmark) || bookmark_duration_time.get(index).getText().equals(SecondAfter)) {
					System.out.println("verified bookmark name and time");
					ATUReports.add(time +" verify bookmark name and time", "bookmark name and time", "displayed correctly",
							"displayed correctly", LogAs.PASSED, null);
					Assert.assertTrue(true);
					return;
				}
			}

			index++;
		}
		System.out.println("not verified bookmark name and time");
		ATUReports.add(time +" verify bookmark name and time", "bookmark name and time", "displayed correctly",
				"not displayed or displayed not correctly", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		Assert.assertTrue(false);

	}

	/// click on pause in specific time then add bookmark
	public void addBookmarkInSpecificTime(String bookmark, String seconds) throws Exception {

		int time_out = 0;
		while (!player_timer.getText().contains(seconds)) {
			if (time_out == 120) {
				System.out.println("time out");
				ATUReports.add(time +" Time out", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Assert.assertTrue(false);
			}
			Thread.sleep(500);
			time_out++;
		}
		try {
			pause_button.click();
			System.out.println("clicked on play button to pause");
			ATUReports.add(time +" clicked on play button to pause", LogAs.PASSED, null);
			addBookMark(bookmark, seconds);
			play_button.click();
		} catch (Exception e) {

			System.out.println("failed click on play button to pause");
			ATUReports.add(time +" failed clicking", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
		ATUReports.add(time +" got right time", LogAs.PASSED, null);
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
				
			if(buffering.equals("Buffering")) {
				Thread.sleep(1000);
				continue;
			}
			
			String CurrentTime = buffering.split("/")[0].trim();
			String EndTime = buffering.split("/")[1].trim();

			//get the last digit 
			String AlmostEndTime = EndTime.substring(EndTime.length() - 1,EndTime.length());
			int lastDigit = Integer.parseInt(AlmostEndTime);
			lastDigit--;
			AlmostEndTime = EndTime.substring(0, EndTime.length()-1) + String.valueOf(lastDigit) ;
					
			if (CurrentTime.equals(EndTime)) {
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
	
	
	public void verifySearchResultNumberAsWritten(){
		
		// get the number of result from the breadcrumbs
		String structure_displayed  = list_of_results.getText();
		String[] splited_structure_result = structure_displayed.trim().split(" ");
		int resultNumber = Integer.parseInt(splited_structure_result[0]);
			
		//get the number of result from list 
		if(SearchResultlocation.size() == SearchResultTimes.size() && SearchResultTimes.size() == SearchResultContext.size() )
			System.out.println("Verifed that the lists size are equal.");
		
		int list_size =  SearchResultlocation.size();
	
		if(resultNumber == list_size){
			System.out.println("Verifed that the result number is as written at the breadcrumbs.");
		ATUReports.add(time +" Verifed that the result number is as written at the breadcrumbs.", "True.", "True.", LogAs.PASSED, null);
		} else {
		System.out.println("Not Verifed that the result number is as written at the breadcrumbs.");
		ATUReports.add(time +" Not Verifed that the result number is as written at the breadcrumbs." , "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}		
		
	}


	public void checkThatWeStartTheRecordFromThisTime(String time) throws InterruptedException {
		try{
			Thread.sleep(5000);
			exitInnerFrame();
			Thread.sleep(500);
			getIntoFrame(0);
			new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(time_buffer_status));
			//new WebDriverWait(driver, 4).until(ExpectedConditions.not(ExpectedConditions.textToBePresentInElement(time_buffer_status, "Buffering")));
			new WebDriverWait(driver, 15).until(ExpectedConditions.textToBePresentInElement(time_buffer_status,time));
			System.out.println("Verifed that the record start from the bookmark.");
			ATUReports.add(time +" Verifed that the record start from the bookmark.", "True.", "True.", LogAs.PASSED, null);
		} catch (Exception e) {
			System.out.println("Not Verifed that the record start from the bookmark.");
			ATUReports.add(time +" Not Verifed that the record start from the bookmark." , "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}		
	}
	public void VeirfyDeleteAndEditBookmarkNotDisplayInstractor() {
		
		try{	
			if(delete_button.isDisplayed() || add_bookmark_button.isDisplayed()){
				System.out.println("Not Verifed that the delete button or the add button aren't display.");
				ATUReports.add(time +" Not Verifed that the delete button or the add button aren't display." , "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				} else {
				System.out.println("Verifed that the delete button or the add button aren't display.");
				ATUReports.add(time +" Verifed that the delete button or the add button aren't display.", "True.", "True.", LogAs.PASSED, null);
				}			
		} catch (Exception e) {
			System.out.println("Verifed that the delete button or the add button aren't display.");
			ATUReports.add(time +" Verifed that the delete button or the add button aren't display.", "True.", "True.", LogAs.PASSED, null);
		}	
	}

	public void makeSureThatTheBookmarkIsCanBeenSeeingOnTheSeekBar(String bookmarkType) {
	
		try{	
			List<WebElement> bookmarkSigns = seek_bar.findElements(By.xpath("//*[contains(@id,'TimeSlider.Bookmark')]"));
			boolean IsTheBookmarkOnTheSeekBar = false;
			for(WebElement element: bookmarkSigns){	
				String bookmarkColor = element.getAttribute("src");
				if(bookmarkColor != null){
					if(bookmarkType.equals("instructor")){
						if(bookmarkColor.contains("BM_TAG_I")){
							IsTheBookmarkOnTheSeekBar = true;
							break;
						}
					} else if(bookmarkType.equals("unclear")){
						if(bookmarkColor.contains("BM_TAG_T1")){
							IsTheBookmarkOnTheSeekBar = true;
							break;
						}
					} else if(bookmarkType.equals("important")){
						if(bookmarkColor.contains("BM_TAG_T2")){
							IsTheBookmarkOnTheSeekBar = true;
							break;
						}
					}
				}
			}
			if(IsTheBookmarkOnTheSeekBar) {
				System.out.println("Verifed that the bookmark " + bookmarkType +" is on the seek bar.");
				ATUReports.add(time +" Verifed that the bookmark " + bookmarkType +" is on the seek bar.", "True.", "True.", LogAs.PASSED, null);
			} else {
				System.out.println("Not Verifed that the bookmark " + bookmarkType +" is on the seek bar.");
				ATUReports.add(time +" Not Verifed that the bookmark " + bookmarkType +" is on the seek bar." , "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			}
			} catch (Exception e) {
				e.getMessage();
				ATUReports.add(e.getMessage() , "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			}	
	}

	public void verifybookmarkIsntFoundInBookmarkList(String bookmark_to_check,String type) {
		
		if(bookmark_list.size()>0){
				if(type.equals("Ins")){
					for (WebElement we : bookmarkIsnstNames) { 
						String bookmark = we.getText();
						if(bookmark.equals(bookmark_to_check)){
							System.out.println("Not Verify that the bookmarks " + bookmark_to_check + " isn't found.");
							ATUReports.add(time +" Not Verify that the bookmarks " + bookmark_to_check + " isn't found." , "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
						}
					}
				} else {
					for (WebElement we : bookmarkStudentNames) { 
						String bookmark = we.getText();
						if(bookmark.equals(bookmark_to_check)){
							System.out.println("Not Verify that the bookmarks " + bookmark_to_check + " isn't found." );
							ATUReports.add(time +" Not Verify that the bookmarks " + bookmark_to_check + " isn't found."  , "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
						}
					}
				}
				System.out.println("Verify that the bookmarks " + bookmark_to_check + " isn't found.");
				ATUReports.add(time +" Verify that the bookmarks " + bookmark_to_check + " isn't found", "True.", "True.", LogAs.PASSED, null);			
	} else {
		System.out.println("Verify that the bookmarks " + bookmark_to_check + " isn't found.");
		ATUReports.add(time +" Verify that the bookmarks " + bookmark_to_check + " isn't found.", "True.", "True.", LogAs.PASSED, null);
	}	
}	
	public void verifybookmarkIsFoundInBookmarkList(String bookmark_to_check,String type) {
			
		if(bookmark_list.size()>0){
				if(type.equals("Ins")){
					for (WebElement we : bookmarkIsnstNames) { 
						String bookmark = we.getText();
						if(bookmark.equals(bookmark_to_check)){
							System.out.println("Verify that the bookmarks " + bookmark_to_check + " is found.");
							ATUReports.add(time +" Verify that the bookmarks " + bookmark_to_check + " is found", "True.", "True.", LogAs.PASSED, null);
							return;
							}
						}
					} else {
						for (WebElement we : bookmarkStudentNames) { 
							String bookmark = we.getText();
							if(bookmark.equals(bookmark_to_check)){
								System.out.println("Verify that the bookmarks " + bookmark_to_check + " is found.");
								ATUReports.add(time +" Verify that the bookmarks " + bookmark_to_check + " is found", "True.", "True.", LogAs.PASSED, null);
								return;
							}
						}
					}
					System.out.println("Not Verify that the bookmarks " + bookmark_to_check + " is found." );
					ATUReports.add(time +" Not Verify that the bookmarks " + bookmark_to_check + " is found."  , "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));		
		} else {
			System.out.println("There isn't bookmarks in the list.");
			ATUReports.add(time +" There isn't bookmarks in the list." , "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}		
  }	
}
