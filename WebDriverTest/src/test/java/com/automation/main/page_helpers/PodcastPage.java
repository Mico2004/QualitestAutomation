package com.automation.main.page_helpers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.collections.iterators.ListIteratorWrapper;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;import com.automation.main.page_helpers.Page;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
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

@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class PodcastPage extends Page {
	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}

	public PodcastPage(WebDriver browser) {
		super(browser);
		// TODO Auto-generated constructor stub
	}

	@FindBy(css = ".entry>h3>a>span") List<WebElement> podcast_titles;
	@FindBy(css = ".entry>h3>a") List <WebElement> podcast_hrefs;
	@FindBy(css = ".enclosure>a") List <WebElement> podcast_audio_mp3;
	
	
	// This function get podcast title, and click on it from the podcast titles list
	public String getTargetPodcastHref(String podcast_title) throws InterruptedException {	
		for(int i = 0; i < podcast_titles.size(); i++) {
			if(podcast_titles.get(i).getText().contains(podcast_title)) {
					return podcast_hrefs.get(i).getAttribute("href");
			}
		}
		return null;
	}
	
	// This function get podcast title, and click on it from the podcast titles list
	public void clickOnTargetPodcast(String podcast_title) throws InterruptedException {	
		for(int i = 0; i < podcast_titles.size(); i++) {
			if(podcast_titles.get(i).getText().contains(podcast_title)) {
				try {
					podcast_titles.get(i).click();
					System.out.println("Clicked on podcast title: " + podcast_title);
					ATUReports.add("Clicked on podcast title: " + podcast_title, LogAs.PASSED, null);
					Assert.assertTrue(true);
				} catch(Exception e) {
					System.out.println("Fail click on podcast title: " + podcast_title);
					ATUReports.add("Fail click on podcast title: " + podcast_title, LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
					Assert.assertTrue(false);
				}
				Thread.sleep(Page.TIMEOUT_TINY);
			}
		}
	}

	
	// This function get podcast title, and click on it from the podcast titles list
	public void clickOnTargetPodcastMp3(String podcast_title) throws InterruptedException {	
		for(int i = 0; i < podcast_titles.size(); i++) {
			if(podcast_titles.get(i).getText().contains(podcast_title)) {
				try {
					podcast_audio_mp3.get(i).click();
					System.out.println("Clicked on podcast title: " + podcast_title);
					ATUReports.add("Clicked on podcast title: " + podcast_title, LogAs.PASSED, null);
					Assert.assertTrue(true);
				} catch(Exception e) {
					System.out.println("Fail click on podcast title: " + podcast_title);
					ATUReports.add("Fail click on podcast title: " + podcast_title, LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
					Assert.assertTrue(false);
				}
				Thread.sleep(Page.TIMEOUT_TINY);
			}
		}
	}
	
	// This function watch all podcast(mp3)
	public void clickAndWatchAllPodcast() throws InterruptedException {
		waitForVisibility(podcast_titles.get(0));
		for(int i=0; i<podcast_titles.size(); i++) {
			try {
				driver.findElements(By.cssSelector(".enclosure>a")).get(i).click();
				Thread.sleep(Page.TIMEOUT_TINY);
				driver.navigate().back();
				waitForVisibility(podcast_titles.get(0));
				Thread.sleep(Page.TIMEOUT_TINY);
			} catch(Exception msg) {
				System.out.println("Fail to watch all podcasts.");
				ATUReports.add("Watch all podcasts.", "True.", "True.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Assert.assertTrue(false);
			}
			
		}
		System.out.println("Watch all podcasts.");
		ATUReports.add("Watch all podcasts.", "True.", "True.", LogAs.PASSED, null);
		Assert.assertTrue(true);
	}
}
