package com.automation.main.page_helpers;

import java.awt.RenderingHints.Key;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
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
public class CopyMenu extends Page {
	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}

	public CopyMenu(WebDriver browser) {
		super(browser);
		// TODO Auto-generated constructor stub
	}

	@FindBy(xpath = "//*[@id=\"courseListSelect\"]/option")
	public List<WebElement> course_list;
	@FindBy(id = "courseListSelect")
	public WebElement course_list_textbox;
	@FindBy(xpath = "//*[@id=\"ModalDialogHeaderWrap\"]")
	public WebElement copy_title;
	@FindBy(id = "InfoText")
	public WebElement info_text;
	@FindBy(id ="ModalDialogHeader")
	public WebElement copy_title2;
	@FindBy(id = "courseSearchText")
	public WebElement search_box;
	@FindBy(name = "searchCourse")
	public WebElement search_button;
	@FindBy(id = "CopyButton")
	public  WebElement CopyRecording;
	@FindBy(id = "CancelButton")
	public  WebElement CancelButton;
	public String[] copy_course_list;// string of courses name
	@FindBy(xpath = "//*[@id=\"courseListSelect\"]/option[1]")
	public WebElement first_course_on_the_list;
	@FindBy(id = "CancelButton")
	public WebElement cancel_button;
	@FindBy(xpath = "html/body/div[2]/div")
	public WebElement outside_of_copy_menu_scope;
	@FindBy(id = "CopyButton")
	public WebElement copy_button;
	@FindBy(id = "courseListSelect")
	public WebElement course_list_select;
	@FindBy(id= "members_value")
	public WebElement instructor_name;
	@FindBy(css = ".angucomplete-title.ng-scope.ng-binding")
	public WebElement dropdownInstructor;
	@FindBy(id= "SearchButton")
	public WebElement listCourses;
	
	// This function clicks on copy button of copy menu
	public void clickOnCopyButton()  {
			
		String id = "CopyButton";
		try{
			System.out.println("Copy1");
			waitForVisibility(copy_button);			
			System.out.println("Copy2");
			((JavascriptExecutor) driver).executeScript("document.getElementById(\""+id+"\").click();");		
			System.out.println("Clicked on copy button");
			ATUReports.add(time +" Clicked on copy button", LogAs.PASSED, null);			
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("CopyButton")));
			Assert.assertTrue(true);
		}catch(Exception e){
				System.out.println(e.getMessage() +" message 2"+e.getLocalizedMessage());		
		}
	}

	// This function go over all course in copy menu select them.
	public List<String> selectAllCoursesFromCourseList() {

		List<String> course_list_text = new ArrayList<String>();

		waitForVisibility(first_course_on_the_list);

		for (int i = 0; i < course_list.size(); i++) {
			course_list_text.add(course_list.get(i).getText());
			course_list.get(i).sendKeys(Keys.LEFT_CONTROL);
			clickElement(course_list.get(i));
		}

		if (course_list_text.size() == course_list.size()) {
			ATUReports.add(time +" All courses selected.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			ATUReports.add(time +" All courses selected.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}

		return course_list_text;
	}

	// This function clicks on cancel button of copy menu
	public void clickOnCancelButton(RecordingHelperPage rec) throws InterruptedException {
		try {
			Thread.sleep(1000);
			waitForVisibility(cancel_button);			
			cancel_button.click();
			System.out.println("Clicked on cancel button");
			ATUReports.add(time +" Clicked on cancel button.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} catch (Exception e) {
			System.out.println("Fail click on cancel button");
			ATUReports.add(time +" Fail click on cancel button.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
		Thread.sleep(3000);
	}

	// This function send ESC keyboard to copy menu.
	// Its target is to close the copy menu by sending this key.
	public void clickEscOnKeyBoardToCloseCopyWindow() throws InterruptedException {
		try {
			search_box.sendKeys(Keys.ESCAPE);
			ATUReports.add(time +" Clicked on ESC button.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} catch (Exception e) {
			ATUReports.add(time +" Fail click on ESC button.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
		Thread.sleep(3000);
	}

	// This function get course name, and select it course from course list,
	// then it return true if it success, and false otherwise.
	public boolean selectTargetCourseFromCourseList(String target_course_name) throws InterruptedException {
		
		try {
		String selected_course = null;
		Thread.sleep(1500);
		waitForVisibility(first_course_on_the_list);
		for (int i = 0; i < course_list.size(); i++) {
			selected_course = course_list.get(i).getText();
			if (selected_course.equals(target_course_name)) {
				WebElement element = course_list.get(i);
				clickElement(element);
				System.out.println("course is selected from Copy manu course list: " + target_course_name);
				ATUReports.add(time +" course is selected from Copy manu course list: " + target_course_name, LogAs.PASSED, null);
				Assert.assertTrue(true);
				break;
			}
			
		}
		
		if (selected_course == null) {
			System.out.println("course is not selected from Copy manu course list: " + target_course_name);
			ATUReports.add(time +" course is not selected from Copy manu course list: " + target_course_name, LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
			return false;
		}

		Thread.sleep(1000);
		return true;
		} catch (Exception msg) {
			msg.printStackTrace();
			ATUReports.add(msg.getMessage(),LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
			return false;
		}
	}
	
	
	// This function get a list of courses, and select all ,
	// then it return true if it success, and false otherwise.
	public boolean selectTargetCourseFromCourseList(List <String> target_courses_name) throws InterruptedException {
		int z=0;
		String selected_course = null;
		Thread.sleep(1500);
		wait = new WebDriverWait(driver, 30);
		System.out.println("s1");
		System.out.println(target_courses_name.size());
		wait.until(ExpectedConditions.visibilityOf(first_course_on_the_list));
		System.out.println("s2");
		for(z=0;z<target_courses_name.size();z++){
			System.out.println(target_courses_name.size());
		for (int i = 0; i < course_list.size(); i++) {
			selected_course = course_list.get(i).getText();
			System.out.println(course_list.get(i).getText());
			if (selected_course.equals(target_courses_name.get(z))) {
				System.out.println(target_courses_name.get(z));
				clickElement(course_list.get(i));
				System.out.println("course is selected from Copy manu course list: " + target_courses_name.get(z));
				ATUReports.add(time +" course is selected from Copy manu course list: " + target_courses_name.get(z), LogAs.PASSED, null);
				Assert.assertTrue(true);
				break;
			}

		}
		}
		
		
		if (selected_course == null) {
			System.out.println("course is not selected from Copy manu course list: " + target_courses_name.get(z));
			ATUReports.add(time +" course is not selected from Copy manu course list: " + target_courses_name.get(z), LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
			return false;
		}

		Thread.sleep(3000);
		return true;
	}

	// This function get current course name, and select one course from course
	// list which is not the current course,
	// then it return selected course name.
	public String selectCourseFromCourseListOtherThenCurrentCourse(String currentCourse) throws InterruptedException {

		waitForVisibility(first_course_on_the_list);

		String selectedCourse = null;

		for (int i = 0; i < course_list.size(); i++) {
			selectedCourse = course_list.get(i).getText();
			if (!selectedCourse.equals(currentCourse)) {
				clickElement(course_list.get(i));
				ATUReports.add(time +" the course: " + selectedCourse + " is selected from course list.", LogAs.PASSED, null);
				Assert.assertTrue(true);
				break;
			}

		}
		if (selectedCourse == null) {
			ATUReports.add(time +" course is not selected from course list.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}

		Thread.sleep(3000);
		return selectedCourse;
	}

	/// verify copy menu title
	public void verifyCopyMenuTitle() throws InterruptedException {
		Thread.sleep(2000);
		String val = copy_title.getText();
		if (val.equals("Copy")) {
			System.out.println("copy menu title verified ");
			ATUReports.add(time +" copy menu title verified ", LogAs.PASSED, null);
		} else {
			System.out.println("copy menu title not verified  ");
			ATUReports.add(time +" copy menu title not verified  ", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
		Assert.assertEquals("Copy", val);

	}

	// verify courses in courses page match the one in copy menu
	public void verifyCoursesInCopyMenu(CoursesHelperPage course) throws InterruptedException {
		HashSet<String> course_copy_map = new HashSet<String>();////
		copy_course_list = getStringFromElement(course_list);
		if (copy_course_list.length != course.courses.length) {
			Assert.assertTrue(false);
			System.out.println("different numbers of  courses in copy menu and courses page ");
			ATUReports.add(time +" different numbers of  courses in copy menu and courses page ", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		} else {
			for (String e : copy_course_list) {
				course_copy_map.add(e);
			}
			for (String s : course.courses) {
				if (!course_copy_map.contains(s)) {
					Assert.assertTrue(false);
					ATUReports.add(time +" courses in copy menu and courses page are not matched ", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				}
			}
			Assert.assertTrue(true);
			System.out.println("courses in both courses page and copy menu are matched");
			ATUReports.add(time +" courses in both courses page and copy menu are matched", LogAs.PASSED, null);
}

	}

	// verify search course box
	public void verifySearchCourseBox() {
		if (verifyElement(search_box)) {
			System.out.println("search course box is verified");
			ATUReports.add(time +" search course box is verified", LogAs.PASSED, null);
		} else {
			System.out.println("search course box is not verified");
			ATUReports.add(time +" search course box is not verified", LogAs.FAILED,  new CaptureScreen(ScreenshotOf.DESKTOP));
		}
		Assert.assertTrue(verifyElement(search_box));
	}

	// verify search course box text
	public void verifySearchCourseBoxText() {
		try {
			Assert.assertEquals(search_box.getAttribute("placeholder"), "Search course by title...");
			System.out.println("search box text is verified");
			ATUReports.add(time +" search box text is verified", LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
		} catch (Exception e) {
			System.out.println("search box text is not verified");
			ATUReports.add(time +" search box text is not verified", LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
		}

	}
	
	///verify element location such as buttons,search courses
    public void verifyCopyMenuElementsLocation()
 {
	    Point searchbox = search_box.getLocation();
		Point searchboxbutton = search_button.getLocation();
		Point copybutton = CopyRecording.getLocation();
		Point cancelbutton = CancelButton.getLocation();
		Point copy_menu_course=course_list_select.getLocation();
		Point info=info_text.getLocation();
		Point title=copy_title.getLocation();

		if((info.getY() > title.getY()) &&(copy_menu_course.getY() > info.getY()) &&(searchbox.getX() < searchboxbutton.getX()) &&(searchbox.getY() > copy_menu_course.getY()) &&(searchboxbutton.getY() > copy_menu_course.getY()) &&(cancelbutton.getX() < copybutton.getX()) && (cancelbutton.getY() == copybutton.getY())&&(copybutton.getX()<searchboxbutton.getX())&&(searchbox.getY() < copybutton.getY()))
	 {
		 System.out.println("elements location are verified in copy menu");
			ATUReports.add(time +" elements location are verified in copy menu", LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
	 }	 else {
		 System.out.println("elements location are not verified in copy menu");
			ATUReports.add(time +" elements location are not verified in copy menu", LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
	}
 }

	// verify copy menu background color is same as recording background color
	public void verifyMenuColor(RecordingHelperPage rec) throws InterruptedException {
		Thread.sleep(2000);
		if (rec.getBackGroundColor(rec.background).equals(getBackGroundColor(copy_title))) {
			System.out.println("copy menu background color is same as recording background color");
			ATUReports.add(time +" copy menu background color is same as recording background color", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("copy menu background color is not  same as recording background color");
			ATUReports.add(time +" copy menu background color is not  same as recording background color", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}

	}

	// "verify info text"
	public void verifyInfoText() throws InterruptedException {
		String infotext = "Choose course(s) that you would like to copy your selected recording(s) to.";
		try {
		if (infotext.equals(info_text.getText())) {
			System.out.println("info text verified");
			ATUReports.add(time +" info text verified", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("info text unverified");
			ATUReports.add(time +" failed info text unverified", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
		} catch(Exception e) {
			System.out.println("failed to get the info text");
			ATUReports.add(time +" failed to get the info text", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);

		}
	}

	// This function clicks on search button of copy menu
	public void clickOnSearchButton() throws InterruptedException {
		try {
			search_button.click();
			System.out.println("Clicked on search button.");
			ATUReports.add(time +" Click the Search button", "Clicked on search button", "Clicked on search button", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} catch (Exception e) {
			System.out.println("Fail click on search button.");
			ATUReports.add(time +" Click the Search button", "Clicked on search button", "Fail click on search button", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
		Thread.sleep(3000);
	}

	// This function return course list
	public List<String> getCourseList() {
		List<String> course_list_text = new ArrayList<String>();

		for (int i = 0; i < course_list.size(); i++) {
			String optionText = course_list.get(i).getText();
			if (!optionText.contains("select a type")){
				course_list_text.add(course_list.get(i).getText());
			}
		}

		return course_list_text;
	}

	// This function delete value in search input box
	public void deleteValueInSearchInputBox() {
		try {
			search_box.sendKeys(Keys.CONTROL + "a");
			search_box.sendKeys(Keys.DELETE);
			System.out.println("Value in search input box deleted successfully");
			ATUReports.add(time +" Delete text in search box", "Value in search input box deleted successfully", "Value in search input box deleted successfully", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} catch (Exception e) {
			System.out.println("Value in search input box not deleted");
			ATUReports.add(time +" Delete text in search box", "Value in search input box deleted successfully", "Value in search input box not deleted", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}

	}
	
	public void copyFromAdmin(String keys_to_send){
		try {
			instructor_name.clear();
			instructor_name.sendKeys(keys_to_send);
			System.out.println("The following keys sent successfully: " + keys_to_send);
			ATUReports.add(time +" Enter text in search box", keys_to_send, "Success", "Success", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} catch (Exception e) {
			System.out.println("The following keys not sent unsuccessfully: " + keys_to_send);
			ATUReports.add(time +" Enter text in search box", keys_to_send, "Success", "Failed", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
		waitForVisibility(dropdownInstructor);
		clickElement(dropdownInstructor);	
		clickElementJS(listCourses);		
	}
	
	
	

	// This function get string from the user and write it / send keys of it to
	// search input box of copy menu
	public void sendKeysToSearchInputBox(String keys_to_send) {
		try {
			search_box.clear();
			search_box.sendKeys(keys_to_send);
			System.out.println("The following keys sent successfully: " + keys_to_send);
			ATUReports.add(time +" Enter text in search box", keys_to_send, "Success", "Success", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} catch (Exception e) {
			System.out.println("The following keys not sent unsuccessfully: " + keys_to_send);
			ATUReports.add(time +" Enter text in search box", keys_to_send, "Success", "Failed", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}

	}

	// This function get string from the user and checks if that string is same
	// as the text
	// in search text box
	public void isTextDisplayedInSearchBox(String is_this_text_displayed) throws InterruptedException {
		try {
			String current_text_in_search_box = search_box.getAttribute("value");
			
			if (current_text_in_search_box.equals(is_this_text_displayed)) {
				System.out.println("Text is displayed in search textbox");
				ATUReports.add(time +" Text is displayed in search textbox", is_this_text_displayed, current_text_in_search_box, LogAs.PASSED, null);
				Assert.assertTrue(true);
			} else {
				System.out.println("Text is not displayed in search textbox");
				ATUReports.add(time +" Text is not displayed in search textbox", is_this_text_displayed, current_text_in_search_box, LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Assert.assertTrue(false);
			}
		} catch (Exception e) {
			System.out.println("Failed to get text from search box.");
			ATUReports.add(time +" Failed to get text from search box.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}

	}

	// This function get course name, and select it course from course list,
	// then it return true if it success, and false otherwise.

	public boolean isTargetCourseSelected(String target_course_name) throws InterruptedException {
		waitForVisibility(first_course_on_the_list);

		String selected_course = null;

		for (int i = 0; i < course_list.size(); i++) {
			selected_course = course_list.get(i).getText();
			if (selected_course.equals(target_course_name)) {
				if (course_list.get(i).isSelected()) {
					System.out.println("Target course is selected: " + target_course_name);
					ATUReports.add(time +" Target course is selected: " + target_course_name, LogAs.PASSED, null);
					Assert.assertTrue(true);
					return true;
				}

			}

		}

		System.out.println("Target course is not selected: " + target_course_name);
		ATUReports.add(time +" Target course is not selected: " + target_course_name, LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		Assert.assertTrue(false);
		return false;

	}

	public void verifyBlueColor(String expected) {
		String blue3 = "#3399ff";
		String blue = "#1e90ff";
		String blue2 = "#26a0da";
		String blue4 = "#0e76bf";/// blue in ie chrome and firefox
		if ((expected.equals(blue)) || (expected.equals(blue2)) || (expected.equals(blue3)) || expected.equals(blue4)) {
			System.out.println("The color of that button is blue.");
			ATUReports.add(time +" The color of that button is blue.", LogAs.PASSED, null);
		} else {
			System.out.println("color of course selected is not blue...");
			ATUReports.add(time +" color of course selected is not blue...", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
	}

	// verify copy menu
	public String verifyCopyMenu(RecordingHelperPage rec) throws InterruptedException {

	
		 String clickedRecordingName = rec.first_course_title.getText();
//		rec.waitForVisibility(rec.recording_tasks_button);
//		rec.moveToElementAndClick(rec.recording_tasks_button, driver);
//		// rec.copy_button.click();
//		/*
//		 * for (int second = 0;; second++) { if (second >= 60) {
//		 * Assert.fail("timeout"); ATUReports.add(time +" timeout", LogAs.FAILED,
//		 * null); } try { if (driver.findElement(By.id("MoveTask2")).)// check
//		 * if list of courses are present {rec.copy_button.click();
//		 * ATUReports.add(time +" copy menu verified ", LogAs.PASSED, null);
//		 * Assert.assertTrue(true); break;
//		 * 
//		 * } } catch (Exception e) { }
//		 * 
//		 */ for (int i = 0; i < 10; i++)
//			rec.recording_tasks_button.sendKeys(Keys.TAB);// solution
//															// to
//		rec.copy_button.click(); // solve
//		// hover
//		// and
//		// click
//		Thread.sleep(500);
//		try {
//
//			if (isElementPresent(By.id("copyCourseWindow")))
//                System.out.println("copy menu verified");
//				ATUReports.add(time +" copy menu verified", LogAs.PASSED, null);
//		} catch (Exception e) {
//		System.out.println("no copy menu verification");
//			ATUReports.add(time +" no copy menu verification", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
//
//		}
		 rec.clickOnRecordingTaskThenCopy();
		 
		 return clickedRecordingName ;
	}
	
	// verify copy menu
		public void verifyCopyMenuByContentTasks(RecordingHelperPage rec) throws InterruptedException {

			waitForVisibility(rec.checkbox);
			rec.checkbox.click();
			
			rec.waitForVisibility(rec.content_tasks_button);
			rec.moveToElementAndClick(rec.content_tasks_button, driver);
			// rec.copy_button.click();
		 for (int i = 0; i < 10; i++)
				rec.content_tasks_button.sendKeys(Keys.TAB);// solution
																// to
			rec.copy_button.click(); // solve
			// hover
			// and
			// click
			Thread.sleep(500);
			try {

				if (isElementPresent(By.id("copyCourseWindow")))
                System.out.println("click succeeded");
					ATUReports.add(time +" click succeeded", LogAs.PASSED, null);
			} catch (Exception e) {
				System.out.println("click failed ");
				ATUReports.add(time +" click failed ", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));

			}
	 
		}
	// This function return true if copy menu is closed,
	// and false if it is open
	public boolean isCopyMenuClosed() {
		try {
			copy_title.isDisplayed();
			return false;
		} catch (org.openqa.selenium.NoSuchElementException msg) {
			return true;
		}
	}
	
	// This function go over all course in copy menu select target courses from this list
	public void selectTargetCourses(List<String> target_courses) {

		List<String> course_list_text = new ArrayList<String>();

		waitForVisibility(first_course_on_the_list);

		for (int i = 0; i < course_list.size(); i++) {
			if (target_courses.contains(course_list.get(i).getText())) {
				course_list_text.add(course_list.get(i).getText());
				if(!(driver instanceof ChromeDriver)) {
					course_list.get(i).sendKeys(Keys.LEFT_CONTROL);
				}
				clickElement(course_list.get(i));
			}
		}

		if (course_list_text.size() == target_courses.size()) {
			ATUReports.add(time +" All courses selected.", LogAs.PASSED, null);
			System.out.println("All courses selected.");
			Assert.assertTrue(true);
		} else {
		System.out.println("All courses selected.");
			ATUReports.add(time +" All courses selected.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}

		//return course_list_text;
	}
	
	
	// This function clicks on copy button of copy menu
	public void clickOnCopyButton(RecordingHelperPage rec) throws InterruptedException {	
			try {
				copy_button.click();
				ATUReports.add(time +" Clicked on copy button.", LogAs.PASSED, null);
				System.out.println("Clicked on copy button.");
				Assert.assertTrue(true);
			} catch(Exception e) {
				System.out.println("Fail click on copy button.");
				ATUReports.add(time +" Fail click on copy button.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Assert.assertTrue(false);
			}
			Thread.sleep(3000);
		}
		
		
	public void ClickOnCopyButtonWithoutChoosingCourse() throws InterruptedException {
			try {
				wait.until(ExpectedConditions.elementToBeClickable(copy_button));
				copy_button.click();
				System.out.println("Get the Error Dailog manu.");
				ATUReports.add(time +" Get the Error Dailog manu.", LogAs.PASSED, null);
				Assert.assertTrue(true);
			} catch(Exception e) {
				ATUReports.add(time +" Failed To get the Error Dailog.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				System.out.println("Failed To get the Error Dailog menu.");
				Assert.assertTrue(false);
			}
			Thread.sleep(3000);
		}
		
		// This function get course name, and select it course from course list,
				// then it return true if it success, and false otherwise.
	public boolean selectTargetCourseFromCourseListThatStartWith(String name_starting_with) throws InterruptedException {

					waitForVisibility(first_course_on_the_list);
					String selected_course = null;
					for (int i = 0; i < course_list.size(); i++) {
						selected_course = course_list.get(i).getText();
						if (selected_course.startsWith(name_starting_with)) {
							clickElement(course_list.get(i));
							System.out.println("course is selected from course list: " + selected_course);
							ATUReports.add(time +" course is selected from course list: " + selected_course, LogAs.PASSED, null);
							Assert.assertTrue(true);
							break;
						}

					}
					if (selected_course == null) {
						System.out.println("course is not selected from course list: " + selected_course);
						ATUReports.add(time +" course is not selected from course list: " + selected_course, LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
						Assert.assertTrue(false);
						return false;
					}

					Thread.sleep(3000);
					return true;
				}
	
	
	

				
				
		// There is a text field to search the instructor, and it contains the default text "Type Instructor Name...".
		// This function verify it
		public void verifyThatAdminRecordingTasksMoveContainsDefaultTextTypeInstructorName() {
			if (driver.findElement(By.id("members_value")).getAttribute("placeholder").equals("Type Instructor Name...")) {
				System.out.println("Verified that it contain default text: Type Instructor Name...");
				ATUReports.add(time +" Verify default text,", "Verified", "Verified", LogAs.PASSED, null);
				Assert.assertTrue(true);
			} else {
				System.out.println("Verified that it not contain default text: Type Instructor Name...");
				ATUReports.add(time +" Verify default text,", "Verified", "Not verified", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Assert.assertTrue(false);
			}
		}
		
		// This function verify that 
		// The "List Courses" button is displayed next to the search field.
		public void verifyThatListCoursesButtonIsDisplayedNextToTheSearchFieldInAdminMoveRecordingWindow() {
			Point search_field = driver.findElement(By.id("members_value")).getLocation();
			Point list_courses_button = driver.findElement(By.id("SearchButton")).getLocation();
			
			if ((search_field.x <= list_courses_button.x) && (search_field.y <= list_courses_button.y)) {
				System.out.println("Verified that list courses button is displayed next to the search field.");
				ATUReports.add(time +" Is list courses button is displayed next to the search filed.", "True.", "True.", LogAs.PASSED, null);
				Assert.assertTrue(true);
			} else {
				System.out.println("Not verified that list courses button is displayed next to the search field.");
				ATUReports.add(time +" Is list courses button is displayed next to the search filed.", "True.", "False.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Assert.assertTrue(false);
			}
				
		}
		
		// This function verify that
		// The "Copy Recording(s)" button is displayed at the bottom right of the window.
		// The "Cancel" button is displayed left to the "Copy Recording(s)" button.
		public void verifyThatCancelButtonDisplayedLeftToMoveRecordingsButtonInAdminMoveRecordingWindow() {
			Point cancel_button = driver.findElement(By.id("CancelButton")).getLocation();
			Point move_recrodings_button = driver.findElement(By.id("CopyButton")).getLocation();
			
			if ((cancel_button.x <= move_recrodings_button.x) && (cancel_button.y <= move_recrodings_button.y)) {
				System.out.println("Verified that cancel button is displayed next to move recordings button.");
				ATUReports.add(time +" Is cancel button is displayed next to move recordings button.", "True.", "True.", LogAs.PASSED, null);
				Assert.assertTrue(true);
			} else {
				System.out.println("Not verified that cancel button is displayed next to move recordings button.");
				ATUReports.add(time +" Is cancel button is displayed next to move recordings button.", "True.", "False.", LogAs.PASSED, null);
				Assert.assertTrue(false);
			}
		}
		
		// Function that verify
		// A "Choose a course that you would like to copy your selected recording(s) to" text below the Instructor's search field
		public void verifyChooseACourseThatCopyAndItsPlaceBelowTheInstructorSearchField() {
			WebElement choose_a_course_that_you_would_like_message = driver.findElement(By.id("InfoText"));
			
			System.out.println(choose_a_course_that_you_would_like_message.getText());
			
			if(choose_a_course_that_you_would_like_message.getText().equals("Choose course(s) that you would like to copy your selected recording(s) to.")) {
				System.out.println("Verified message: Choose course(s) that you would like to copy your selected recording(s) to.");
				ATUReports.add(time +" Message verfieid.", "Verified.", "Verified.", LogAs.PASSED, null);
				Assert.assertTrue(true);
			} else {
				System.out.println("Not verified message: Choose course(s) that you would like to copy your selected recording(s) to.");
				ATUReports.add(time +" Message verfieid.", "Verified.", "Not verified.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Assert.assertTrue(false);
			}
			
			Point search_field_location = driver.findElement(By.id("members_value")).getLocation();
			Point message_location = choose_a_course_that_you_would_like_message.getLocation();
			
			if(search_field_location.y < message_location.y) {
				System.out.println("Location of the message is correct below instructor search field.");
				ATUReports.add(time +" Location of message below instructor search field.", "Correct.", "Correct.", LogAs.PASSED, null);
				Assert.assertTrue(true);
			} else {
				System.out.println("Location of the message is not correct below instructor search field.");
				ATUReports.add(time +" Location of the message.", "Correct.", "Not correct: " + search_field_location.y + "!<" + message_location.y, LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Assert.assertTrue(false);
			}
		}
		
		
		// Function that verify that
		// A "Search" button to the right of the course search field & above the "Cancel"/"Copy recording(s)"
		public void verifyThatSearchButtonRightOfTheCourseSearchFieldAboveTheCancelCopyRecordings() {
			Point search_button_location = driver.findElement(By.id("SearchButton")).getLocation();
			Point course_search_text_location = driver.findElement(By.id("courseSearchText")).getLocation();
			Point copy_recordings_button_location = driver.findElement(By.id("CopyButton")).getLocation();
			
			if ((search_button_location.y < copy_recordings_button_location.y) && (course_search_text_location.x < search_button_location.x)) {
				System.out.println("Verified that search button to the right of the course search field and above the cancel / copy recordings.");
				ATUReports.add(time +" Verify location of search button.", "Verified.", "Verified.", LogAs.PASSED, null);
				Assert.assertTrue(true);
			} else {
				System.out.println("Not verified that search button to the right of the course search field and above the cancel / copy recordings.");
				ATUReports.add(time +" Verify location of search button.", "Verified.", "Not verified.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Assert.assertTrue(false);
			}
		}
		
		// This function get course name, and check if that course is selected course from course list,
		// then it return true if it true, and false otherwise.
		public boolean isTargetCourseFromCourseListSelected(String target_course_name) throws InterruptedException {

			waitForVisibility(first_course_on_the_list);

			String selected_course = null;

			for (int i = 0; i < course_list.size(); i++) {
				selected_course = course_list.get(i).getText();
				if (selected_course.equals(target_course_name)) {
					return course_list.get(i).isSelected();
				}

			}
			
			return false;
		}
		
		public void VerifyThatCourseIsNotDisplayedInTheListOfCourseDestination(String target_course_name)  {

			try{
				waitForVisibility(first_course_on_the_list);
				new WebDriverWait(driver, 5).until(ExpectedConditions.not(ExpectedConditions.textToBePresentInElement(course_list_textbox, target_course_name)));
				System.out.println("verify that the course is not display in the list of course destination");
				ATUReports.add(time +" verify that the course is not display in the list of course destination", "Success.", "Success.",LogAs.PASSED, null);		
			}catch(org.openqa.selenium.TimeoutException msg){
				System.out.println("Not verify that the course is not display in the list of course destination");
				ATUReports.add(time +" verify that the course is not display in the list of course destination", "Success.", "Failed.",LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));		
			}	
		}
		
	
		// This function verify that copy menu open
		public void verifyThatCopyMenuOpen() {
			boolean is_closed = isCopyMenuClosed();
			
			if(!is_closed) {
				System.out.println("Copy menu is open.");
				ATUReports.add(time +" Copy menu.", "Open.", "Open.", LogAs.PASSED, null);
				Assert.assertTrue(true);
			} else {
				System.out.println("Copy menu is close.");
				ATUReports.add(time +" Copy menu.", "Open.", "Close.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Assert.assertTrue(false);
			}
		}
		
		
		// This function verify that copy menu close
		public void verifyThatCopyMenuClose() {
			boolean is_closed = isCopyMenuClosed();
			
			if(!is_closed) {
				System.out.println("Copy menu is open.");
				ATUReports.add(time +" Copy menu.", "Close.", "Open.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				Assert.assertTrue(false);
			} else {
				System.out.println("Copy menu is close.");
				ATUReports.add(time +" Copy menu.", "Close.", "Close.", LogAs.PASSED, null);
				Assert.assertTrue(true);
			}
		}

		/**
		 * this function waits for tag window to display
		 */
		public void waitForPageToLoad() {
			
			try{
				wait.until(ExpectedConditions.visibilityOf(copy_title));
				wait.until(ExpectedConditions.visibilityOf(copy_title2));
				wait.until(ExpectedConditions.visibilityOf(info_text));
				wait.until(ExpectedConditions.visibilityOf(cancel_button));
				wait.until(ExpectedConditions.visibilityOf(copy_button));
				
				}catch(Exception e){
					e.getMessage();
					ATUReports.add(e.getMessage(), "Success.", "Fail.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
				}
			
		}
		
	

}
