package com.automation.main.page_helpers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.internal.ExactComparisonCriteria;
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

	@FindBy(id = "gs_FriendlyName")public WebElement course_name_search_box;
	@FindBy(id = "jqg_gridData_1") public WebElement first_course_in_list_checkbox;
	@FindBy(id = "cb_gridData") public WebElement first_checkbox;
	@FindBy(id = "gs_FriendlyName") public  WebElement course_name_textbox;
	@FindBy(id = "gs_AairsCourseId") public WebElement course_id_textbox;
	@FindBy(id = "gridData_FriendlyName") WebElement course_name;
	@FindBy(css = ".linksStyle") public WebElement first_course_link;
	@FindBy(xpath = "//*[@id=\"2\"]/td[5]/a") WebElement second_course_link;
	@FindBy(xpath = ".//*[@id='1']/td[6]") WebElement first_instructor_ids;
	@FindBy(xpath = "//*[@id=\"main\"]/div[1]/h2") WebElement all_courses_title;
	@FindBy(xpath =".//*[@id='tegrityBreadcrumbsBox']/li/a")WebElement adminDashboard;
	@FindBy(xpath =".//*[@id='gview_gridData']/div[2]/div/table/thead/tr[2]/th[6]/div/table/tbody/tr/td[2]/a/img") public WebElement idFilterButton;
	@FindBy(xpath =".//*[@id='gview_gridData']/div[2]/div/table/thead/tr[2]/th[5]/div/table/tbody/tr/td[2]/a") public WebElement nameFilterButton;
	@FindBy(xpath =".//*[@id='sopt_menu']/li[1]/a/table/tbody/tr/td[2]")	public WebElement containFilterOption;
	@FindBy(xpath =".//*[@id='sopt_menu']/li[2]/a/table/tbody/tr/td[2]")	public WebElement doNotContainFilterOption;
	@FindBy(xpath =".//*[@role='listbox']")	public WebElement pageingDropDown;
	@FindBy(xpath =".//*[@role='listbox']/option[3]")	public WebElement pageingDropDown_thirdOption;
	@FindBy(xpath =".//*[@id='archiveOrPurgeCoursesWindow']/div/div[1]/div[2]/input")	public WebElement delete;
	@FindBy(xpath =".//*[@id='archiveOrPurgeCoursesWindow']//button[1]") public WebElement purgeButton;
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
	// ATUReports.add(time +" Is first checkbox of first coursw shown?", "True",
	// "True", LogAs.PASSED, null);
	// return true;
	// } catch (Exception msg) {
	// ATUReports.add(time +" Is first checkbox of first coursw shown?", "True",
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
			e.printStackTrace();
			ATUReports.add("Loading 'All courses admin' page failed" + e.getMessage(),  LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
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
			ATUReports.add(time +" Searched for course", "Success", "Success", LogAs.PASSED, null);
		} catch (Exception msg) {
			System.out.println("Fail to search for course named: " + course_name);
			ATUReports.add(time +" Searched for course", "Success", "Fail", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}

	}

	public void clickOnFirstCourseLink() {
		try {
			waitForVisibility(first_course_link);
			first_course_link.click();
			System.out.println("Clicked on first course link.");
			ATUReports.add(time +" Clicked on first course link.", "Success.", "Success.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} catch (Exception msg) {
			System.out.println("Fail to click on first course link.");
			ATUReports.add(time +" Clicked on first course link.", "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}

	/// clcik link by its first characters
	public String clickOnCourseLinkStartingWith(String word) {
		String linkTest;
		try {

			for (WebElement e : courses_link) {
				if (e.getText().startsWith(word)) {
					linkTest = e.getText();
					e.click();
					System.out.println("Clicked on first course link.");
					ATUReports.add(time +" Clicked on first course link.", "Success.", "Success.", LogAs.PASSED, null);
					return linkTest;
				}
			}

		} catch (Exception msg) {
			System.out.println("Fail to click on first course link.");
			ATUReports.add(time +" Clicked on first course link.", "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
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
			ATUReports.add(time +" title matches", "All courses", "matches", "matches", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("title doesnt matche");
			ATUReports.add(time +" title doesnt matche", "All courses", "matches", "not matches", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}
	
	public String getTheFirstInstructorIDS(){
		waitForVisibility(first_instructor_ids);
		return first_instructor_ids.getText();
	}



	// clicks on purge button - fill the delete confirmation - click on final purge
	public void purge(){
		try {
            waitForVisibility(courseTasks);

			courseTasks.click();

			waitForVisibility(driver.findElement(By.xpath("//*[@id='ddCoursesArchive']/li/ul/li[2]")));

			WebElement purge = driver.findElement(By.xpath("//*[@id='ddCoursesArchive']/li/ul/li[2]"));

			purge.click();

			waitForVisibility(delete);

			delete.sendKeys("DELETE");

			wait.until(ExpectedConditions.elementToBeClickable(purgeButton));

			purgeButton.click();

			ATUReports.add(time +" Succeeded to Purge", "Success", "Success", LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));

		}catch(Exception e){
			System.out.println(e.getMessage());

			ATUReports.add(time +" Failed to purge", "Success", e.getMessage(), LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));

		}


	}

	// check or uncheck checkbox of a specific course
	public void checkOrUncheckCourseCheckbox(boolean check, String courseName){

		try {

			wait.until(ExpectedConditions.elementToBeClickable(first_course_in_list_checkbox));

			wait.until(ExpectedConditions.elementToBeClickable(first_course_link));

			wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//a[text()='" + courseName + "']")));

			WebElement courseNameElement = driver.findElement(By.xpath(".//a[text()='" + courseName + "']"));

			WebElement courseNameElementParent = courseNameElement.findElement(By.xpath("./.."));

			WebElement courseNameElementParent2 = courseNameElementParent.findElement(By.xpath("./.."));

			WebElement checkbox = courseNameElementParent2.findElement(By.xpath("./td[1]/input"));

			waitForVisibility(checkbox);

			if(check && !checkbox.isSelected()) {
				checkbox.click();
                wait.until(ExpectedConditions.elementToBeSelected(checkbox));
			}

			else if(!check && checkbox.isSelected()) {
				checkbox.click();
				wait.until(ExpectedConditions.not(ExpectedConditions.elementToBeSelected(checkbox)));
			}

		}catch ( Exception e){

			System.out.println(e.getMessage());

			Assert.assertTrue(false);

		}



	}

	
}
