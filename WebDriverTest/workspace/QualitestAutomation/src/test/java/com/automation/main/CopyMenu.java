package com.automation.main;

import java.awt.RenderingHints.Key;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;import com.automation.main.page_helpers.Page;import com.automation.main.page_helpers.Page;
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

	@FindBy(xpath = "//*[@id=\"courseListSelect\"]/option") List<WebElement> course_list;
	@FindBy(xpath = "//*[@id=\"ModalDialogHeaderWrap\"]")
	WebElement copy_title;
	@FindBy(id = "InfoText")
	WebElement info_text;
	@FindBy(id = "courseSearchText")
	WebElement search_box;
	@FindBy(name = "searchCourse")
	WebElement search_button;
	@FindBy(id = "CopyButton")
	WebElement CopyRecording;
	@FindBy(id = "CancelButton")
	WebElement CancelButton;
	String[] copy_course_list;// string of courses name
	@FindBy(xpath = "//*[@id=\"courseListSelect\"]/option[1]")
	WebElement first_course_on_the_list;
	@FindBy(id = "CancelButton")
	WebElement cancel_button;
	@FindBy(xpath = "html/body/div[2]/div")
	WebElement outside_of_copy_menu_scope;
	@FindBy(id = "CopyButton")
	WebElement copy_button;
	@FindBy(id = "courseListSelect")
	WebElement course_list_select;
	

	// This function clicks on copy button of copy menu
	public void clickOnCopyButton() throws InterruptedException {
		try {
			wait.until(ExpectedConditions.visibilityOf(copy_button));
			copy_button.click();
			System.out.println("Clicked on copy button");
			ATUReports.add("Clicked on copy button", LogAs.PASSED, null);
			Assert.assertTrue(true);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("CopyButton")));
			
		} catch (Exception e) {
			System.out.println("Fail click on copy button");
			ATUReports.add("Fail click on copy button", LogAs.FAILED, null);
			Assert.assertTrue(false);
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
			ATUReports.add("All courses selected.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			ATUReports.add("All courses selected.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}

		return course_list_text;
	}

	// This function clicks on cancel button of copy menu
	public void clickOnCancelButton(RecordingHelperPage rec) throws InterruptedException {
		try {
			Thread.sleep(Page.TIMEOUT_TINY);
			rec.clickElement(this.cancel_button);
			System.out.println("Clicked on cancel button");
			ATUReports.add("Clicked on cancel button.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} catch (Exception e) {
			System.out.println("Fail click on cancel button");
			ATUReports.add("Fail click on cancel button.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		Thread.sleep(Page.TIMEOUT_TINY);
	}

	// This function send ESC keyboard to copy menu.
	// Its target is to close the copy menu by sending this key.
	public void clickEscOnKeyBoardToCloseCopyWindow() throws InterruptedException {
		try {
			search_box.sendKeys(Keys.ESCAPE);
			ATUReports.add("Clicked on ESC button.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} catch (Exception e) {
			ATUReports.add("Fail click on ESC button.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		Thread.sleep(Page.TIMEOUT_TINY);
	}

	// This function get course name, and select it course from course list,
	// then it return true if it success, and false otherwise.
	public boolean selectTargetCourseFromCourseList(String target_course_name) throws InterruptedException {

		String selected_course = null;
		wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOf(first_course_on_the_list));
		
		for (int i = 0; i < course_list.size(); i++) {
			selected_course = course_list.get(i).getText();
			if (selected_course.equals(target_course_name)) {
				clickElement(course_list.get(i));
				System.out.println("course is selected from course list: " + target_course_name);
				ATUReports.add("course is selected from course list: " + target_course_name, LogAs.PASSED, null);
				Assert.assertTrue(true);
				break;
			}

		}
		if (selected_course == null) {
			System.out.println("course is not selected from course list: " + target_course_name);
			ATUReports.add("course is not selected from course list: " + target_course_name, LogAs.FAILED, null);
			Assert.assertTrue(false);
			return false;
		}

		Thread.sleep(Page.TIMEOUT_TINY);
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
				ATUReports.add("the course: " + selectedCourse + " is selected from course list.", LogAs.PASSED, null);
				Assert.assertTrue(true);
				break;
			}

		}
		if (selectedCourse == null) {
			ATUReports.add("course is not selected from course list.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}

		Thread.sleep(Page.TIMEOUT_TINY);
		return selectedCourse;
	}

	/// verify copy menu title
	public void verifyCopyMenuTitle() throws InterruptedException {
		Thread.sleep(Page.TIMEOUT_TINY);
		String val = copy_title.getText();
		if (val.equals("Copy")) {
			System.out.println("copy menu title verified ");
			ATUReports.add("copy menu title verified ", LogAs.PASSED, null);
		} else {
			System.out.println("copy menu title not verified  ");
			ATUReports.add("copy menu title not verified  ", LogAs.FAILED, null);
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
			ATUReports.add("different numbers of  courses in copy menu and courses page ", LogAs.FAILED, null);
		} else {
			for (String e : copy_course_list) {
				course_copy_map.add(e);
			}
			for (String s : course.courses) {
				if (!course_copy_map.contains(s)) {
					Assert.assertTrue(false);
					ATUReports.add("courses in copy menu and courses page are not matched ", LogAs.FAILED, null);
				}
			}
			Assert.assertTrue(true);
			System.out.println("courses in both courses page and copy menu are matched");
			ATUReports.add("courses in both courses page and copy menu are matched", LogAs.PASSED, null);
}

	}

	// verify search course box
	public void verifySearchCourseBox() {
		if (verifyElement(search_box)) {
			System.out.println("search course box is verified");
			ATUReports.add("search course box is verified", LogAs.PASSED, null);
		} else {
			System.out.println("search course box is not verified");
			ATUReports.add("search course box is not verified", LogAs.PASSED, null);
		}
		Assert.assertTrue(verifyElement(search_box));
	}

	// verify search course box text
	public void verifySearchCourseBoxText() {
		try {
			Assert.assertEquals(search_box.getAttribute("placeholder"), "Search course by title...");
			System.out.println("search box text is verified");
			ATUReports.add("search box text is verified", LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
		} catch (Exception e) {
			System.out.println("search box text is not verified");
			ATUReports.add("search box text is not verified", LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
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
			ATUReports.add("elements location are verified in copy menu", LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
	 }	 else {
		 System.out.println("elements location are not verified in copy menu");
			ATUReports.add("elements location are not verified in copy menu", LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
	}
 }

	// verify copy menu background color is same as recording background color
	public void verifyMenuColor(RecordingHelperPage rec) throws InterruptedException {
		Thread.sleep(Page.TIMEOUT_TINY);
		if (rec.getBackGroundColor(rec.background).equals(getBackGroundColor(copy_title))) {
			System.out.println("copy menu background color is same as recording background color");
			ATUReports.add("copy menu background color is same as recording background color", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("copy menu background color is not  same as recording background color");
			ATUReports.add("copy menu background color is not  same as recording background color", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}

	}

	// "verify info text"
	public void verifyInfoText() throws InterruptedException {
		String infotext = "Choose course(s) that you would like to copy your selected recording(s) to.";
		try {
		if (infotext.equals(info_text.getText())) {
			System.out.println("info text verified");
			ATUReports.add("info text verified", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("info text unverified");
			ATUReports.add("failed info text unverified", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		} catch(Exception e) {
			System.out.println("failed to get the info text");
			ATUReports.add("failed to get the info text", LogAs.FAILED, null);
			Assert.assertTrue(false);

		}
	}

	// This function clicks on search button of copy menu
	public void clickOnSearchButton() throws InterruptedException {
		try {
			search_button.click();
			System.out.println("Clicked on search button.");
			ATUReports.add("Click the Search button", "Clicked on search button", "Clicked on search button", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} catch (Exception e) {
			System.out.println("Fail click on search button.");
			ATUReports.add("Click the Search button", "Clicked on search button", "Fail click on search button", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}
		Thread.sleep(Page.TIMEOUT_TINY);
	}

	// This function return course list
	public List<String> getCourseList() {
		List<String> course_list_text = new ArrayList<String>();

		for (int i = 0; i < course_list.size(); i++) {
			course_list_text.add(course_list.get(i).getText());
		}

		return course_list_text;
	}

	// This function delete value in search input box
	public void deleteValueInSearchInputBox() {
		try {
			search_box.sendKeys(Keys.CONTROL + "a");
			search_box.sendKeys(Keys.DELETE);
			System.out.println("Value in search input box deleted successfully");
			ATUReports.add("Delete text in search box", "Value in search input box deleted successfully", "Value in search input box deleted successfully", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} catch (Exception e) {
			System.out.println("Value in search input box not deleted");
			ATUReports.add("Delete text in search box", "Value in search input box deleted successfully", "Value in search input box not deleted", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}

	}

	// This function get string from the user and write it / send keys of it to
	// search input box of copy menu
	public void sendKeysToSearchInputBox(String keys_to_send) {
		try {
			search_box.clear();
			search_box.sendKeys(keys_to_send);
			System.out.println("The following keys sent successfully: " + keys_to_send);
			ATUReports.add("Enter text in search box", keys_to_send, "Success", "Success", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} catch (Exception e) {
			System.out.println("The following keys not sent unsuccessfully: " + keys_to_send);
			ATUReports.add("Enter text in search box", keys_to_send, "Success", "Failed", LogAs.FAILED, null);
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
				ATUReports.add("Text is displayed in search textbox", is_this_text_displayed, current_text_in_search_box, LogAs.PASSED, null);
				Assert.assertTrue(true);
			} else {
				System.out.println("Text is not displayed in search textbox");
				ATUReports.add("Text is not displayed in search textbox", is_this_text_displayed, current_text_in_search_box, LogAs.FAILED, null);
				Assert.assertTrue(false);
			}
		} catch (Exception e) {
			System.out.println("Failed to get text from search box.");
			ATUReports.add("Failed to get text from search box.", LogAs.FAILED, null);
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
					ATUReports.add("Target course is selected: " + target_course_name, LogAs.PASSED, null);
					Assert.assertTrue(true);
					return true;
				}

			}

		}

		System.out.println("Target course is not selected: " + target_course_name);
		ATUReports.add("Target course is not selected: " + target_course_name, LogAs.FAILED, null);
		Assert.assertTrue(false);
		return false;

	}

	public void verifyBlueColor(String expected) {
		String blue3 = "#3399ff";
		String blue = "#1e90ff";
		String blue2 = "#26a0da";/// blue in ie chrome and firefox
		if ((expected.equals(blue)) || (expected.equals(blue2)) || (expected.equals(blue3))) {
			System.out.println("color of course selected is blue");
			ATUReports.add("color of course selected is blue", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("color of course selected is not blue...");
			ATUReports.add("color of course selected is not blue...", LogAs.FAILED, null);
			Assert.assertTrue(false);
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
//		 * Assert.fail("timeout"); ATUReports.add("timeout", LogAs.FAILED,
//		 * null); } try { if (driver.findElement(By.id("MoveTask2")).)// check
//		 * if list of courses are present {rec.copy_button.click();
//		 * ATUReports.add("copy menu verified ", LogAs.PASSED, null);
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
//		Thread.sleep(Page.TIMEOUT_TINY);
//		try {
//
//			if (isElementPresent(By.id("copyCourseWindow")))
//                System.out.println("copy menu verified");
//				ATUReports.add("copy menu verified", LogAs.PASSED, null);
//		} catch (Exception e) {
//		System.out.println("no copy menu verification");
//			ATUReports.add("no copy menu verification", LogAs.FAILED, null);
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
			Thread.sleep(Page.TIMEOUT_TINY);
			try {

				if (isElementPresent(By.id("copyCourseWindow")))
                System.out.println("click succeeded");
					ATUReports.add("click succeeded", LogAs.PASSED, null);
			} catch (Exception e) {
				System.out.println("click failed ");
				ATUReports.add("click failed ", LogAs.FAILED, null);

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
			ATUReports.add("All courses selected.", LogAs.PASSED, null);
			System.out.println("All courses selected.");
			Assert.assertTrue(true);
		} else {
		System.out.println("All courses selected.");
			ATUReports.add("All courses selected.", LogAs.FAILED, null);
			Assert.assertTrue(false);
		}

		//return course_list_text;
	}
	
	
	// This function clicks on copy button of copy menu
	public void clickOnCopyButton(RecordingHelperPage rec) throws InterruptedException {	
			try {
				copy_button.click();
				ATUReports.add("Clicked on copy button.", LogAs.PASSED, null);
				System.out.println("Clicked on copy button.");
				Assert.assertTrue(true);
			} catch(Exception e) {
				System.out.println("Fail click on copy button.");
				ATUReports.add("Fail click on copy button.", LogAs.FAILED, null);
				Assert.assertTrue(false);
			}
			Thread.sleep(Page.TIMEOUT_TINY);
		}
		
		
	public void ClickOnCopyButtonWithoutChoosingCourse() throws InterruptedException {
			try {
				wait.until(ExpectedConditions.elementToBeClickable(copy_button));
				copy_button.click();
				System.out.println("Get the Error Dailog manu.");
				ATUReports.add("Get the Error Dailog manu.", LogAs.PASSED, null);
				Assert.assertTrue(true);
			} catch(Exception e) {
				ATUReports.add("Failed To get the Error Dailog.", LogAs.FAILED, null);
				System.out.println("Failed To get the Error Dailog menu.");
				Assert.assertTrue(false);
			}
			Thread.sleep(Page.TIMEOUT_TINY);
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
							ATUReports.add("course is selected from course list: " + selected_course, LogAs.PASSED, null);
							Assert.assertTrue(true);
							break;
						}

					}
					if (selected_course == null) {
						System.out.println("course is not selected from course list: " + selected_course);
						ATUReports.add("course is not selected from course list: " + selected_course, LogAs.FAILED, null);
						Assert.assertTrue(false);
						return false;
					}

					Thread.sleep(Page.TIMEOUT_TINY);
					return true;
				}
				
				
		// There is a text field to search the instructor, and it contains the default text "Type Instructor Name...".
		// This function verify it
		public void verifyThatAdminRecordingTasksMoveContainsDefaultTextTypeInstructorName() {
			if (driver.findElement(By.id("members_value")).getAttribute("placeholder").equals("Type Instructor Name...")) {
				System.out.println("Verified that it contain default text: Type Instructor Name...");
				ATUReports.add("Verify default text,", "Verified", "Verified", LogAs.PASSED, null);
				Assert.assertTrue(true);
			} else {
				System.out.println("Verified that it not contain default text: Type Instructor Name...");
				ATUReports.add("Verify default text,", "Verified", "Not verified", LogAs.FAILED, null);
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
				ATUReports.add("Is list courses button is displayed next to the search filed.", "True.", "True.", LogAs.PASSED, null);
				Assert.assertTrue(true);
			} else {
				System.out.println("Not verified that list courses button is displayed next to the search field.");
				ATUReports.add("Is list courses button is displayed next to the search filed.", "True.", "False.", LogAs.FAILED, null);
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
				ATUReports.add("Is cancel button is displayed next to move recordings button.", "True.", "True.", LogAs.PASSED, null);
				Assert.assertTrue(true);
			} else {
				System.out.println("Not verified that cancel button is displayed next to move recordings button.");
				ATUReports.add("Is cancel button is displayed next to move recordings button.", "True.", "False.", LogAs.PASSED, null);
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
				ATUReports.add("Message verfieid.", "Verified.", "Verified.", LogAs.PASSED, null);
				Assert.assertTrue(true);
			} else {
				System.out.println("Not verified message: Choose course(s) that you would like to copy your selected recording(s) to.");
				ATUReports.add("Message verfieid.", "Verified.", "Not verified.", LogAs.FAILED, null);
				Assert.assertTrue(false);
			}
			
			Point search_field_location = driver.findElement(By.id("members_value")).getLocation();
			Point message_location = choose_a_course_that_you_would_like_message.getLocation();
			
			if(search_field_location.y < message_location.y) {
				System.out.println("Location of the message is correct below instructor search field.");
				ATUReports.add("Location of message below instructor search field.", "Correct.", "Correct.", LogAs.PASSED, null);
				Assert.assertTrue(true);
			} else {
				System.out.println("Location of the message is not correct below instructor search field.");
				ATUReports.add("Location of the message.", "Correct.", "Not correct: " + search_field_location.y + "!<" + message_location.y, LogAs.FAILED, null);
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
				ATUReports.add("Verify location of search button.", "Verified.", "Verified.", LogAs.PASSED, null);
				Assert.assertTrue(true);
			} else {
				System.out.println("Not verified that search button to the right of the course search field and above the cancel / copy recordings.");
				ATUReports.add("Verify location of search button.", "Verified.", "Not verified.", LogAs.FAILED, null);
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
		
		// This function verify that copy menu open
		public void verifyThatCopyMenuOpen() {
			boolean is_closed = isCopyMenuClosed();
			
			if(!is_closed) {
				System.out.println("Copy menu is open.");
				ATUReports.add("Copy menu.", "Open.", "Open.", LogAs.PASSED, null);
				Assert.assertTrue(true);
			} else {
				System.out.println("Copy menu is close.");
				ATUReports.add("Copy menu.", "Open.", "Close.", LogAs.FAILED, null);
				Assert.assertTrue(false);
			}
		}
		
		
		// This function verify that copy menu close
		protected void verifyThatCopyMenuClose() {
			boolean is_closed = isCopyMenuClosed();
			
			if(!is_closed) {
				System.out.println("Copy menu is open.");
				ATUReports.add("Copy menu.", "Close.", "Open.", LogAs.FAILED, null);
				Assert.assertTrue(false);
			} else {
				System.out.println("Copy menu is close.");
				ATUReports.add("Copy menu.", "Close.", "Close.", LogAs.PASSED, null);
				Assert.assertTrue(true);
			}
		}

}
