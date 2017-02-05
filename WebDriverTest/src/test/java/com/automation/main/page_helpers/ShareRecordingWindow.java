package com.automation.main.page_helpers;

import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;

@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class ShareRecordingWindow extends Page{
	
	@FindBy(id = "ModalDialogHeaderWrap")
	public WebElement share_recording_background;
	@FindBy(id = "ModalDialogHeader")
	public WebElement share_recording_title;
	@FindBy(id = "InfoText")
	public WebElement get_direct_link;
	@FindBy(id="playerState")
	public WebElement checkbox_embed_player;
	@FindBy(id="anonymousState")
	public WebElement checkbox_anonymous_users;
	@FindBy(xpath=".//*[@id='shareRecordingWindow']/form/div[1]/div[5]/div/a[1]/img")
	public WebElement facebook_button;
	@FindBy(xpath=".//*[@id='shareRecordingWindow']/form/div[1]/div[5]/div/a[2]/img")
	public WebElement tweet_button;
	@FindBy(xpath=".//*[@id='shareRecordingWindow']/form/div[1]/div[5]/div/a[3]/img")
	public WebElement google_plus_button;
	@FindBy(xpath=".//*[@id='shareRecordingWindow']/form/div[1]/div[5]/div/a[4]/img")
	public WebElement tumblr_button;
	@FindBy(id="okButton")
	public WebElement okButton;
	@FindBy(xpath=".//*[@id='shareRecordingWindow']/form/div[1]/div[1]/label")
	public WebElement url_label;
	@FindBy(xpath=".//*[@id='shareRecordingWindow']/form/div[1]/div[3]/label")
	public WebElement Embed_label;
	@FindBy(xpath=".//*[@id='shareRecordingWindow']/form/div[1]/div[2]/label")
	public WebElement Embed_label_frame;
	@FindBy(id="iframeRecordingUrl")
	public WebElement iframe_link;
	@FindBy(id="sharedRecordingUrl")
	public WebElement url_link;
	@FindBy(xpath=".//*[@id='shareRecordingWindow']/form/div[1]/div[5]/p")
	public WebElement share_title;
	@FindBy(xpath=".//*[@id='shareRecordingWindow']/form/div[1]/div[4]/label")
	public WebElement anonymous_users_label;
	
	public ShareRecordingWindow(WebDriver browser) {
		super(browser);
		// TODO Auto-generated constructor stub
	}

	// verify share recording background color is same as recording background color
		public void verifyShareRecordingColor(RecordingHelperPage rec) throws InterruptedException {
			
			try{
			String background_rec = rec.getBackGroundColor(rec.background);
			String menu_background = getBackGroundColor(share_recording_background);
			if (background_rec.equals(menu_background)) {
				ATUReports.add(time +" edit Properties menu background color is same as recording background color","Success.", "Success.", LogAs.PASSED, null);
				System.out.println("edit Properties menu background color is same as recording background color");
				Assert.assertTrue(true);
			} else {
				ATUReports.add(time +" edit Properties menu background color is not  same as recording background color","Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				System.out.println("edit Propertiese menu background color is  not same as recording background color");
				Assert.assertTrue(false);
			}
			}catch(Exception e){
				e.getMessage();
				ATUReports.add(e.getMessage(), "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));

				}
		}
		
		public void verifyInfomativeText() {
			
			try {
				String infoText  = get_direct_link.getText();
				
				if (infoText.equals("Get a direct link to this recording to post in emails, web pages, etc.")) {
					System.out.println("Valid Infomative Text.");
					ATUReports.add(time +" Valid Infomative Text.","Success.", "Success.", LogAs.PASSED, null);
				} else {
					System.out.println("Not Valid Infomative Text.");
					ATUReports.add(time +" Not Valid Infomative Text.", "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				}
			} catch (Exception e) {	
				e.printStackTrace();
				ATUReports.add(e.getMessage(), "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			}
			
		}
		
		public void waitForPageToLoad(){
			
			try{
			wait.until(ExpectedConditions.visibilityOf(share_recording_title));
			wait.until(ExpectedConditions.visibilityOf(get_direct_link));
			wait.until(ExpectedConditions.visibilityOf(facebook_button));
			wait.until(ExpectedConditions.visibilityOf(okButton));
	
			
			}catch(Exception e){
				e.getMessage();
				ATUReports.add(e.getMessage(), "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			}
		}

		public void verifyUrlIsValid(PlayerPage player) throws InterruptedException {
			String id = url_link.getAttribute("id");
			String url = (String)((JavascriptExecutor) driver).executeScript("return document.getElementById(\""+id+"\").value;");	
			if(!url.contains("playbackToken")){
				System.out.println("The url doesn't contains playbacktoken.");
				ATUReports.add(time +" The url doesn't contains playbacktoken.","Success.", "Success.", LogAs.PASSED, null);
			} else {
				System.out.println("The url contains playbacktoken.");
				ATUReports.add(time +" The url contains playbacktoken.", "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			}
			
			String current_handler = driver.getWindowHandle();
			openNewTab();
			Set<String> allHandles = driver.getWindowHandles();
			
			for(String handler: allHandles) {
			    if(!handler.equals(current_handler)){
					driver.switchTo().window(handler);
					break;
				 }
			}
			driver.get(url);
			
			player.verifyTimeBufferStatusForXSec(5);
			player.exitInnerFrame();
			driver.close();
			
			for(String handler: allHandles) {
			    if(handler.equals(current_handler)){
					driver.switchTo().window(handler);
					break;
				 }
			}		
		}

		public void veirfyThatClickingOnTheTextSelectTheEntireTextInTheTextbox(WebElement element) {
		
			if(element.getAttribute("ng-click").equals("select($event)")){
				clickElementJS(element);
				System.out.println("Veirfy that clicking on the text select the entire text in the textbox.");
				ATUReports.add(time +" Veirfy that clicking on the text select the entire text in the textbox.","Success.", "Success.", LogAs.PASSED, null);
			} else {
				System.out.println("Not Veirfy that clicking on the text select the entire text in the textbox.");
				ATUReports.add(time +" Veirfy that clicking on the text select the entire text in the textbox.", "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			}		
		}

		public void checkThatTheUrlIsValid() {
			String url = url_link.getText();
			if(url.contains(pageUrl)) {		
				System.out.println("Veirfy that the url is vaild.");
				ATUReports.add(time +" Veirfy that the url is vaild.","Success.", "Success.", LogAs.PASSED, null);
			} else {
				System.out.println("Not Veirfy that the url is vaild.");
				ATUReports.add(time +" Veirfy that the url is vaild.", "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			}	
		}
		
		
		//verify that the frame link contains:** src="//<UniversityUrl>/embeddedPlayer.htm?recordingId=(anything)&playbackToken=(anything) ** frameborder=0** allowfullscreen# 
		public void checkThatTheIframeIsValid(LoginHelperPage tegrity) {	
			try{
				String id = iframe_link.getAttribute("id");
				String iframe_url = (String)((JavascriptExecutor) driver).executeScript("return document.getElementById(\""+id+"\").value;");	
				String[] split_iframe =  iframe_url.split("&playbackToken=");
				String[] split_iframe_2 = split_iframe[0].split("recordingId=");
				String[] split_iframe_3 = split_iframe[1].split(" ");
				String universityName = tegrity.getPageUrl().substring(8);	
			
				String string_to_check = "src=\"//" + universityName + "/embeddedPlayer.htm?recordingId="  + split_iframe_2[1] + "&playbackToken=" +split_iframe_3[0] + " frameborder=\"0\" allowfullscreen";
			
				if(iframe_url.contains(string_to_check)){
					System.out.println("Veirfy that the frame link is valid.");
					ATUReports.add(time +" Veirfy that the frame link is valid.","Success.", "Success.", LogAs.PASSED, null);
				} else {
					System.out.println("Not Veirfy that the frame link is valid." + iframe_url);
					ATUReports.add(time +"Not Veirfy that the frame link is valid." + iframe_url, "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				}
			}catch(Exception e){
				e.getMessage();
				ATUReports.add(e.getMessage(), "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			}
		}

		public String getPlayerUrl() {
			String id = url_link.getAttribute("id");
			String url = (String)((JavascriptExecutor) driver).executeScript("return document.getElementById(\""+id+"\").value;");	
			return url;
		}
		
		public void verifyIfThePlaybackTokenIsAppearedInTheLink(boolean shouldApread) {
			String id = url_link.getAttribute("id");
			String iframe_url = (String)((JavascriptExecutor) driver).executeScript("return document.getElementById(\""+id+"\").value;");	
			
			if(iframe_url.contains("playbackToken") && shouldApread == true) {
				System.out.println("Veirfy that the frame link is valid.");
				ATUReports.add(time +" Veirfy that the frame link is valid.","Success.", "Success.", LogAs.PASSED, null);
			} else if(!iframe_url.contains("playbackToken") && shouldApread == false){
				System.out.println("Veirfy that the frame link is valid.");
				ATUReports.add(time +" Veirfy that the frame link is valid.","Success.", "Success.", LogAs.PASSED, null);			
			} else {
				System.out.println("Not Veirfy that the frame link is valid." + iframe_url);
				ATUReports.add(time +"Not Veirfy that the frame link is valid." + iframe_url, "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			}
		}		
}
