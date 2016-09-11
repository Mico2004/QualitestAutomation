package com.automation.main;

import java.awt.Point;
import java.util.List;

import org.omg.Messaging.SyncScopeHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Listeners;

import com.gargoylesoftware.htmlunit.WebWindowNotFoundException;
import com.thoughtworks.selenium.condition.Not;

import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;

@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class SearchPage extends Page {
	public SearchPage(WebDriver browser) {
		super(browser);
		// TODO Auto-generated constructor stub
	}
	
	@FindBy(css = ".loading-spinner-img")WebElement loading_spinner_image;
	@FindBy(xpath = ".//*[@id='main']/div[2]/div[1]/div[2]/span/a")WebElement title_first_chapter;
	@FindBy(id = "tegrityBreadcrumbsBox") WebElement breadcrumbs_box;
	@FindBy(css = ".video-thumbnail") List<WebElement> video_thumbnails_list;
	@FindBy(css = ".thumbnail-image") List<WebElement> thumbnail_images_list;
	@FindBy(css = ".duration") List<WebElement> duration_time_list; 
	@FindBy(css = ".title") List<WebElement> title_list;
	@FindBy(css = ".title>.ng-binding") List<WebElement> title_urls_list;
	@FindBy(css = ".search-course.ng-binding") List<WebElement> course_titles_list;
	@FindBy(css = ".search-recording.ng-scope") List<WebElement> recording_titles_list;
	@FindBy(css = ".search-recording.ng-scope>.ng-binding") List<WebElement> recording_link_titles_list;
	@FindBy(xpath= ".//*[@id='main']/div[2]/div/div[2]/div/span[2]/a") WebElement search_first_record_title_name;
	@FindBy(css = ".search-source.ng-binding") List<WebElement> source_titles_list;
	@FindBy(css = ".video-wrap.linkToFocus") List<WebElement> video_wrap_link_to_focus_list;
	@FindBy(css = ".linkToFocus") List<WebElement> link_icon_list;
	@FindBy(css = "#tegrityBreadcrumbsBox>.ng-scope>.ng-scope.ng-binding") List<WebElement> breadcrumbs_box_elements_list;
	
	
	// This function verify that loading spinner image displayed
	public void verifyLoadingSpinnerImage() throws InterruptedException {

			Thread.sleep(500);
			if(isElemenetDisplayed(By.cssSelector(".loading-spinner-img"))){	
				System.out.println("Verfied loading spinner image displayed.");
				ATUReports.add("Verfied loading spinner image displayed.", "True.", "True.", LogAs.PASSED, null);
			} else {
				System.out.println("The spinner image wasn't displayed.");
				ATUReports.add("The spinner image wasn't displayed.", "True.", "True.", LogAs.PASSED, null);
			}
	}
	
	//The breadcrumb structure displayed as follows: "> Courses > Course name > X results found for: "search_criterion". (X seconds)".
	public void verfiyBreadcrumbStructureDisplayedAsCoursesCoursenameXresultsfound(String course_name, String searching_criterion) throws InterruptedException {
		
		String structure_displayed = breadcrumbs_box.getText();
		
		String[] splited_structure_displayed = structure_displayed.split(">");
		
		if(splited_structure_displayed.length <4) {
			String array = splited_structure_displayed.toString();
			System.out.println("the error breadcrmbs is:" + array);
			ATUReports.add("the error breadcrmbs is:" + array, "True.", "True.", LogAs.WARNING, null);
			return;
		}
		String[] splited_third_structure_displayed = splited_structure_displayed[3].trim().split(" ");
		String third_structure = splited_third_structure_displayed[0] + " results found for: \"" + searching_criterion + "\". " + splited_third_structure_displayed[splited_third_structure_displayed.length-2] +" seconds)";

		
		if((splited_structure_displayed[1].trim().equals("Courses")) &&
				(splited_structure_displayed[2].trim().equals(course_name)) && 
				(splited_structure_displayed[3].trim().equals(third_structure))) {
			System.out.println("Verfid breadcrumb structure displayed as required.");
			ATUReports.add("Verfid breadcrumb structure displayed as required.", "True.", "True.", LogAs.PASSED, null);
		} else {
			System.out.println("Not verfid breadcrumb structure displayed as required.");
			ATUReports.add("Verfid breadcrumb structure displayed as required.", "True.", "False", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			ATUReports.add("splited_structure_displayed" +structure_displayed, "True.", "False", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			
		}
	
	}
	
	// This function verify that
	// The start time of the chapter within recording is displayed over the chapter icon.
	// The time is displayed in the "hh:mm:ss" format.
	public void verifyDurationTimeDisplayed() {
		String duration_time = duration_time_list.get(0).getText();
		String[] splited_duration_time = duration_time.split(":");
		
		if((Integer.parseInt(splited_duration_time[0]) >= 0) && 
				(Integer.parseInt(splited_duration_time[1]) >= 0) && 
				(Integer.parseInt(splited_duration_time[2]) >= 0)) {
			System.out.println("Verified duration time displaying format.");
			ATUReports.add("Verified duration time displaying format.", "True.", "True.", LogAs.PASSED, null);
		} else {
			System.out.println("Not verified duration time displaying format.");
			ATUReports.add("Verified duration time displaying format.", "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
	}
	
	
	// This function verify that
	//  The search criterion is displayed with "..." at the beginning/end of search result item title if the search criterion is long.
	public void verifyThatTitlesWith3DotsAtTheBeginningEndDisplayed() {
		boolean end = false;
		boolean both = false;
		boolean beginning = false;
		
		for(WebElement title: title_list) {
			if(end && both && beginning) {
				System.out.println("Verified that 3 dots appears at the beginning/end and both.");
				ATUReports.add("Verified that 3 dots appears at the beginning/end and both.", "True.", "True.", LogAs.PASSED, null);
				return;
			} else {
				if(!end) {
					if((title.getText().endsWith("...")) && (!title.getText().startsWith("..."))) {
						end = true;
					}
				}
				if(!beginning) {
					if((title.getText().startsWith("...")) && (!title.getText().endsWith("..."))) {
						beginning = true;
					}
				}
				if(!both) {
					if((title.getText().startsWith("...")) && (title.getText().endsWith("..."))) {
						both = true;
					}
				}
			}
		}
		
		System.out.println("Not verified that 3 dots appears at the beginning/end and both.");
		ATUReports.add("Not Verified that 3 dots appears at the beginning/end and both.", "True.", "True.", LogAs.PASSED, null);
		return;
	}
	

	// Verify that
	// The course title in the format as follows: "Course: course_name.
	public void verifyDisplayCourseTitleForSearchInsideTargetCourse(String course_name) {
		String compare_with = "Course: " + course_name;	
		for(WebElement course_title: course_titles_list){
		if(course_title.getText().equals(compare_with)) {
			System.out.println("Verifed course title format.");
			ATUReports.add("Verifed course title format.", "True.", "True.", LogAs.PASSED, null);
			return;
		}
	  }
		System.out.println("Not verifed course title format.");
		ATUReports.add("Verifed course title format.", "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
	}
	
	// Verify that
	// The recording title in the format as follows: "Recording: recording_title.
	public void verifyRecordingTitleDisplayInCorrectFormat() {	
		if((recording_titles_list.get(0).getText().startsWith("Recording:")) &&
				(recording_titles_list.get(0).getText().substring("Recording: ".length()).length()>0)){
			System.out.println("Verifed recording title format.");
			ATUReports.add("Verifed recording title format.", "True.", "True.", LogAs.PASSED, null);
		} else {
			System.out.println("Not verifed recording title format.");
			ATUReports.add("Verifed recording title format.", "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
	}
	
	// Verify that
	// The source title in the format as follows: " Source: Recording Chapter"
	public void verifySourceTitleDisplayInCorrectFormat() {	
		if((source_titles_list.get(0).getText().startsWith("Source:")) &&
				(source_titles_list.get(0).getText().substring("Recording: ".length()).length()>0)){
			System.out.println("Verifed source title format.");
			ATUReports.add("Verifed source title format.", "True.", "True.", LogAs.PASSED, null);
		} else {
			System.out.println("Not verifed source title format.");
			ATUReports.add("Verifed source title format.", "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
	}
	
	// Verify that 
	// The next result display below the current result in case there is next result.
	public void verifyThatNextResultDisplayBelowCurrentResultInCaseThereIsNextResult() {
		if(video_thumbnails_list.size()>1) {
			boolean not_correct = false;
			int prepoint = video_thumbnails_list.get(0).getLocation().y;
			for(int i=1; i<video_thumbnails_list.size()-1; i++) {
				int currpoint = video_thumbnails_list.get(i).getLocation().y;
				if(prepoint < currpoint) {
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
			
			if(!not_correct) {
				System.out.println("Verified that next result display below the current result.");
				ATUReports.add("Verified that next result display below the current result.", "True.", "True.", LogAs.PASSED, null);
			} else {
				System.out.println("Not verified that next result display below the current result.");
				ATUReports.add("Verified that next result display below the current result.", "True.", "False", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			}
		} else {
			System.out.println("There is 1 or 0 results.");
			ATUReports.add("There is 1 or 0 results.", "Expected more than 1.", "1 or 0 results.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
	}
	
	// Verify that 
	// The next result display below the current result in case there is next result.
	public void verifyThatNextResultDisplayBelowCurrentResultInCaseThereIsNextResultAddicnalCont() {
		if(video_thumbnails_list.size()>1) {
			boolean not_correct = false;
			int prepoint = video_thumbnails_list.get(0).getLocation().y;
			for(int i=1; i<video_thumbnails_list.size()-1; i++) {
				int currpoint = video_thumbnails_list.get(i).getLocation().y;
				if(prepoint < currpoint) {
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
			
			if(!not_correct) {
				System.out.println("Verified that next result display below the current result.");
				ATUReports.add("Verified that next result display below the current result.", "True.", "True.", LogAs.PASSED, null);
			} else {
				System.out.println("Not verified that next result display below the current result.");
				ATUReports.add("Verified that next result display below the current result.", "True.", "False", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			}
		} else {
			System.out.println("There is 1 or 0 results.");
			ATUReports.add("There is 1 or 0 results.", "There is 1 or 0 results.", "1 or 0 results.", LogAs.PASSED, null);
		}
	}
	
	// This function waits until spinner image disappear
	public void waitUntilSpinnerImageDisappear() throws InterruptedException {
		for(int i=0; i<20; i++) {
			try {
				Thread.sleep(900);
				if(loading_spinner_image.isDisplayed()) {
					Thread.sleep(100);
				} else {
					break;
				}
			} catch(Exception msg) {
				break;
			}
		}
	}
	
	// This function click on the chapter icon of recording in target index
	public void clickOnChapterIconOfRecordingInTargetIndex(int index) throws InterruptedException {
		for(int i=0; i<10; i++) {
			try {
				video_thumbnails_list.get(index-1).click();
				Thread.sleep(2000);
				System.out.println("Clicked on target icon of recording in index: " + index);
				ATUReports.add("Clicked on target icon of recording in index: " + index, "True.", "True.", LogAs.PASSED, null);
				return;
			} catch(Exception msg) {
				Thread.sleep(1000);
			}
		}
		
		System.out.println("Not clicked on target icon of recording in index: " + index);
		ATUReports.add("Clicked on target icon of recording in index: " + index, "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
	}
	
	// This function click on title of the chapter of recording in target index
	public void clickOnChapterTitleOfRecordingInTargetIndex(int index) throws InterruptedException {
		for(int i=0; i<10; i++) {
			try {
				//title_urls_list.get(index-1).click();
				title_first_chapter.click();
				Thread.sleep(2000);
				System.out.println("Clicked on target title recording in index: " + index);
				ATUReports.add("Clicked on target title recording in index: " + index, "True.", "True.", LogAs.PASSED, null);
				return;
			} catch(Exception msg) {
				Thread.sleep(1000);
			}
		}
		
		System.out.println("Not clicked on target title recording in index: " + index);
		ATUReports.add("Clicked on target title recording in index: " + index, "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
	}
	
	
	// This function click on recording title of the chapter of recording in target index
	public void clickOnRecordingTitleOfChapterOfRecordingInTargetIndex(int index) throws InterruptedException {
		for(int i=0; i<10; i++) {
			try {

				recording_link_titles_list.get(index-1).click();
				Thread.sleep(2000);
				System.out.println("Clicked on target recording title of recording in index: " + index);
				ATUReports.add("Clicked on target recording title of recording in index: " + index, "True.", "True.", LogAs.PASSED, null);
				return;
			} catch(Exception msg) {
				Thread.sleep(1000);
			}
		}
		
		System.out.println("Not clicked on target recording title of recording in index: " + index);
		ATUReports.add("Clicked on target recording title of recording in index: " + index, "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
	}
	
	// This function verify that search result contains one result with target title
	public void verifyResultContainOneResultWithTargetTitle(String target_title) {
		if((title_list.size()==1) && (title_list.get(0).getText().equals(target_title))) {
			System.out.println("Verifed that search results contains one result with target result: " + target_title);
			ATUReports.add("Verifed that search results contains one result with target result.", target_title, target_title, LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Not verifed that search results contains one result with target result: " + target_title);
			ATUReports.add("Verifed that search results contains one result with target result.", target_title, title_list.get(0).getText(), LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}
	
	// This function verify that search result is empty
	public void verifySearchResultIsEmpty() {
		if(title_list.size() == 0) {
			System.out.println("Verified that search result is empty.");
			ATUReports.add("Verified that search result is empty.", "True.", "True.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Not verified that search result is empty.");
			ATUReports.add("Verified that search result is empty.", "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
//			Assert.assertTrue(false);
		}
	}
	
	
	// Verify that
	// The source title in the format as follows: " Source: Link".
	public void verifyThatSourceTitleInTheFormatSourceLink() {
		if(source_titles_list.size()>0) {
			if(source_titles_list.get(0).getText().equals("Source: Link")) {
				System.out.println("Verifed source title format.");
				ATUReports.add("Verifed source title format.", "True.", "True.", LogAs.PASSED, null);
			} else {
				System.out.println("Not verifed source title format.");
				ATUReports.add("Verifed source title format.", "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			}
		} else {
			System.out.println("There is no source titles.");
		}
	}
	
	// Verify that the link icon is displayed in search result in target index
	public void verifyLinkIconDisplayedIndexSearchResult(int index) {
		if(driver.findElements(By.cssSelector(".linkToFocus>.ng-binding")).get(index-1).getCssValue("background-image").contains("icon_assets.png")) {
			System.out.println("Verifed that link icon displayed in index: " + index);
			ATUReports.add("Verifed that link icon displayed in index: " + index, "True.", "True.", LogAs.PASSED, null);
		} else {
			System.out.println("Not verifed that link icon displayed in index: " + index);
			ATUReports.add("Verifed that link icon displayed in index: " + index, "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
	}
	
	//The breadcrumb structure displayed as follows: "> Admin Dashboard > Courses > Course name > X results found for: "search_criterion". (X seconds)".
	public void verfiyBreadcrumbStructureDisplayedAsCoursesCoursenameXresultsfoundForAdminDashboard(String course_name, String searching_criterion) throws InterruptedException {
		waitForVisibility(breadcrumbs_box);
		Thread.sleep(5000);
		String structure_displayed = breadcrumbs_box.getText();
			
		String[] splited_structure_displayed = structure_displayed.split(">");
		
		if(splited_structure_displayed.length <5) {
			String array = splited_structure_displayed.toString();
			System.out.println("the error breadcrmbs is:" + array);
			ATUReports.add("the error breadcrmbs is:" + array, "True.", "True.", LogAs.WARNING, null);
			return;
		}
		String[] splited_third_structure_displayed = splited_structure_displayed[4].trim().split(" ");
		String third_structure = splited_third_structure_displayed[0] + " results found for: \"" + searching_criterion + "\". " + splited_third_structure_displayed[splited_third_structure_displayed.length-2] +" seconds)";

		
		if((splited_structure_displayed[1].trim().equals("Admin Dashboard")) &&
				(splited_structure_displayed[2].trim().equals("Courses")) &&
				(splited_structure_displayed[3].trim().equals(course_name)) && 
				(splited_structure_displayed[4].trim().equals(third_structure))) {
			System.out.println("Verfid breadcrumb structure displayed as required.");
			ATUReports.add("Verfid breadcrumb structure displayed as required.", "True.", "True.", LogAs.PASSED, null);
		} else {
			System.out.println("Not verfid breadcrumb structure displayed as required.");
			ATUReports.add("splited_structure_displayed" +structure_displayed, "True.", "False", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			ATUReports.add("Verfid breadcrumb structure displayed as required.", "True.", "False", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
		
	}
	
	// Verify that the source title in the format as follows: " Source: Closed Caption".
	public void verifyThatSourceTitleInTheFormatSourceClosedCaption() {
		if(source_titles_list.size()>0) {
			if(source_titles_list.get(0).getText().equals("Source: Closed Caption")) {
				System.out.println("Verifed source title format.");
				ATUReports.add("Verifed source title format.", "True.", "True.", LogAs.PASSED, null);
			} else {
				System.out.println("Not verifed source title format.");
				ATUReports.add("Verifed source title format.", "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			}
		} else {
			System.out.println("There is no source titles.");
		}
	}
	
	// This function clicks back to course
	public void clickBackToCourseInBreadcrumbs() {
		clickElement(breadcrumbs_box_elements_list.get(1));
	}
	
	// This function clicks back to courses
	public void clickBackToCoursesInBreadcrumbs() {
		clickElement(breadcrumbs_box_elements_list.get(0));
	}

	// This function clicks back to course for Admin dashboard
	public void clickBackToCourseInBreadcrumbsForAdminDashBoard() {
		clickElement(breadcrumbs_box_elements_list.get(2));
	}
	
	// Verify that the source title in target format
	public void verifyThatSourceTitleForTargetRecordingInTargetFormat(String recording_title, String target_format) {
		if(source_titles_list.size()>0) {
			for(int i=0; i<source_titles_list.size(); i++) {
				if(source_titles_list.get(i).getText().equals(target_format)) {
					if(title_list.get(i).getText().equals(recording_title)) {
						System.out.println("Verifed source title format.");
						ATUReports.add("Verifed source title format.", "True.", "True.", LogAs.PASSED, null);
						return;
					}
				}
			}
		} else {
			System.out.println("There is no source titles.");
		}
	}
	
	
	//The breadcrumb structure displayed as follows: "> Courses > X results found for: "search_criterion". (X seconds)".
	public void verfiyBreadcrumbStructureDisplayedAsCoursesXResultsFound(String course_name, String searching_criterion) throws InterruptedException {
		
		waitForVisibility(breadcrumbs_box);
		String structure_displayed = breadcrumbs_box.getText();
			
		String[] splited_structure_displayed = structure_displayed.split(">");
		
		if(splited_structure_displayed.length <3) {
			String array = splited_structure_displayed.toString();
			System.out.println("the error breadcrmbs is:" + array);
			ATUReports.add("the error breadcrmbs is:" + array, "True.", "True.", LogAs.WARNING, null);
			return;
		}
		String[] splited_third_structure_displayed = splited_structure_displayed[2].trim().split(" ");
		String third_structure = splited_third_structure_displayed[0] + " results found for: \"" + searching_criterion + "\". " + splited_third_structure_displayed[splited_third_structure_displayed.length-2] +" seconds)";

			
		if((splited_structure_displayed[1].trim().equals("Courses")) &&
				(splited_structure_displayed[2].trim().equals(third_structure))) {
			System.out.println("Verfid breadcrumb structure displayed as required.");
			ATUReports.add("Verfid breadcrumb structure displayed as required.", "True.", "True.", LogAs.PASSED, null);
		} else {
			System.out.println("Not verfid breadcrumb structure displayed as required.");
			ATUReports.add("splited_structure_displayed" +structure_displayed, "True.", "False", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			ATUReports.add("Verfid breadcrumb structure displayed as required.", "True.", "False", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
		
	}
	
	
	// Verify that the bookmark icon is displayed in search result in target index
	public void verifyBookmarkIconDisplayedIndexSearchResult(int index) {
		if(driver.findElements(By.cssSelector(".linkToFocus>.ng-binding")).get(index-1).getCssValue("background-image").contains("icon_assets.png")) {
			System.out.println("Verifed that bookmark icon displayed in index: " + index);
			ATUReports.add("Verifed that bookmark icon displayed in index: " + index, "True.", "True.", LogAs.PASSED, null);
		} else {
			System.out.println("Not verifed that bookmark icon displayed in index: " + index);
			ATUReports.add("Verifed that bookmark icon displayed in index: " + index, "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
	}
}
