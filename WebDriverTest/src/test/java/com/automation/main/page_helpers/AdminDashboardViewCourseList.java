package com.automation.main.page_helpers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

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

@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class AdminDashboardViewCourseList extends Page {
	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}

	public AdminDashboardViewCourseList(WebDriver browser) {
		super(browser);
		// TODO Auto-generated constructor stub
	}

	@FindBy(id = "gs_FriendlyName")WebElement course_name_search_box;
	@FindBy(id = "jqg_gridData_1") WebElement first_course_in_list_checkbox;
	@FindBy(id = "cb_gridData") WebElement first_checkbox;
	@FindBy(id = "gridData_FriendlyName") WebElement course_name;
	@FindBy(css = ".linksStyle") public WebElement first_course_link;
	@FindBy(xpath = "//*[@id=\"2\"]/td[5]/a") WebElement second_course_link;
	@FindBy(xpath = ".//*[@id='1']/td[6]") WebElement first_instructor_ids;
	@FindBy(xpath = "//*[@id=\"main\"]/div[1]/h2") WebElement all_courses_title;
	@FindBy(xpath =".//*[@id='tegrityBreadcrumbsBox']/li/a")WebElement adminDashboard;
	@FindBy(className = "linksStyle") List<WebElement> courses_link;
	@FindBy (css = ".btn.btn-default.btn-menu.nolink") WebElement courseTasks;
	
	// @FindBy(id = "ctl00_ContentPlaceHolder1_txtSearch") WebElement
	// filter_search_input;
	// @FindBy(id = "ctl00_ContentPlaceHolder1_btnSearch") WebElement
	// filter_search_button;
	// @FindBy(id =
	// "ctl00_ContentPlaceHolder1_TegrityCourseRepeater_ctl00_LinkButton3")
	// WebElement first_course_membership_button;
	// @FindBy(partialLinkText = "Admin Dashboard") WebElement
	// admin_dashboard_link;
	// @FindBy(id =
	// "ctl00_ContentPlaceHolder1_TegrityCourseRepeater_ctl00_LinkButton2")
	// WebElement first_course_delete_button;

	// public boolean isFirstCourseShownInTheList() {
	// try {
	// wait.until(ExpectedConditions.elementToBeClickable(first_course_in_list_checkbox));
	// System.out.println("First checkbox of first course is shown.");
	// ATUReports.add("Is first checkbox of first coursw shown?", "True",
	// "True", LogAs.PASSED, null);
	// return true;
	// } catch (Exception msg) {
	// ATUReports.add("Is first checkbox of first coursw shown?", "True",
	// "False", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
	// return false;
	// }
	// }

	public void waitForThePageToLoad(){
		try{
			wait.until(ExpectedConditions.visibilityOf(adminDashboard));
			wait.until(ExpectedConditions.visibilityOf(courseTasks));
			wait.until(ExpectedConditions.visibilityOf(first_checkbox));
			wait.until(ExpectedConditions.visibilityOf(course_name));
			
		}catch(Exception e){
			ATUReports.add("Loading 'All courses admin' page failed",  LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}
	

	public void moveToCoursesThroughGet(String url){
		
	
		driver.get(url);
		System.out.println("Moving to the course through get method.");
		ATUReports.add("Moving to the course through get method", LogAs.PASSED, null);		
		new WebDriverWait(driver, 30).until(ExpectedConditions.attributeContains(By.xpath("//*[@id=\"main\"]/div[2]/ul/li[1]"), "class", "active"));
		
	}
	
	public void searchForTargetCourseName(String course_name) {
		try {
			waitForVisibility(first_course_link);
			course_name_search_box.sendKeys(course_name);
			course_name_search_box.sendKeys(Keys.ENTER);
			System.out.println("Searched for course named: " + course_name);
			ATUReports.add("Searched for course", "Success", "Success", LogAs.PASSED, null);
		} catch (Exception msg) {
			System.out.println("Fail to search for course named: " + course_name);
			ATUReports.add("Searched for course", "Success", "Fail", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}

	}

	public void clickOnFirstCourseLink() {
		try {
			waitForVisibility(first_course_link);
			first_course_link.click();
			System.out.println("Clicked on first course link.");
			ATUReports.add("Clicked on first course link.", "Success.", "Success.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} catch (Exception msg) {
			System.out.println("Fail to click on first course link.");
			ATUReports.add("Clicked on first course link.", "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}

	/// clcik link by its first characters
	public String clickOnCourseLinkStartingWith(String word) {
		try {

			for (WebElement e : courses_link) {
				if (e.getText().startsWith(word)) {
					e.click();
					System.out.println("Clicked on first course link.");
					ATUReports.add("Clicked on first course link.", "Success.", "Success.", LogAs.PASSED, null);
					return e.getText();
				}
			}

		} catch (Exception msg) {
			System.out.println("Fail to click on first course link.");
			ATUReports.add("Clicked on first course link.", "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
       return null;
	}

	/// verify all courses page
	public void verifyAllCoursesPage() {
		waitForVisibility(all_courses_title);
		System.out.println(all_courses_title.getText());
		if (all_courses_title.getText().contains("All Courses")) {
			System.out.println("title matches");
			ATUReports.add("title matches", "All courses", "matches", "matches", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("title doesnt matche");
			ATUReports.add("title doesnt matche", "All courses", "matches", "not matches", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}
	
	public String getTheFirstInstructorIDS(){
		waitForVisibility(first_instructor_ids);
		return first_instructor_ids.getText();
	}
	
	
}
