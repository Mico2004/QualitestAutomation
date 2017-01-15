package com.automation.main.page_helpers;

import java.util.ArrayList;
import java.util.HashMap;
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
public class MoveWindow extends Page {
	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}

	public MoveWindow(WebDriver browser) {
		super(browser);
		// TODO Auto-generated constructor stub
	}

	@FindBy(id = "MoveButton")
	public WebElement move_button;
	@FindBy(id = "ModalDialogHeader")
	public WebElement move_menu_title;
	@FindBy(id="members_value")
	public WebElement instructorTextBox;
	@FindBy(xpath = "//*[@id=\"courseListSelect\"]/option")
	public  List<WebElement> course_list;
	@FindBy(id = "CancelButton")
	public WebElement cancel_button;
	@FindBy(id = "InfoText")
	public WebElement info_text;
	@FindBy(id = "courseSearchText")
	public WebElement search_box;
	@FindBy(id = "MoveButton")
	public WebElement moveRecording;
	@FindBy(id = "CancelButton")
	public WebElement cancelButton;
	@FindBy(name = "searchCourse")
	public WebElement search_button;
	public String[] move_course_list;// string of courses name
	@FindBy(id = "ModalDialogHeaderWrap")
	public WebElement move_menu_background;
	@FindBy(id = "courseListSelect")
	public WebElement course_list_select;
	// This function clicks on delete button of copy menu
	public void clickOnMoveRecordings() throws InterruptedException {
		try {
			waitForVisibility(move_button);
			String id = "MoveButton";
			((JavascriptExecutor) driver).executeScript("document.getElementById(\"" + id + "\").click();");
			System.out.println("Clicked on move recordings button.");
			ATUReports.add(time +" Clicked on move recordings button.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} catch (Exception e) {
			System.out.println("Fail click on move recordings button.");
			ATUReports.add(time +" Fail click on move recordings button.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
		Thread.sleep(2000);
	}

	// This function return true if move menu is closed,
	// and false if it is open
	public boolean isMoveMenuClosed() {
		try {
			move_menu_title.isDisplayed();
			return false;
		} catch (org.openqa.selenium.NoSuchElementException msg) {
			return true;
		}
	}
	
	// This function verify that Move menu open
	public void verifyThatCopyMenuOpen() {
		boolean is_closed = isMoveMenuClosed();
		
		if(!is_closed) {
			System.out.println("Move menu is open.");
			ATUReports.add(time +" Move menu.", "Open.", "Open.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Move menu is close.");
			ATUReports.add(time +" Move menu.", "Open.", "Close.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}
	
	// This function verify that Move menu close
	public void verifyThatMoveMenuClose() {
		boolean is_closed = isMoveMenuClosed();
		
		if(!is_closed) {
			System.out.println("Move menu is open.");
			ATUReports.add(time +" Move menu.", "Close.", "Open.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		} else {
			System.out.println("Move menu is close.");
			ATUReports.add(time +" Move menu.", "Close.", "Close.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		}
	}
	
	// This function return course list
	public List<String> getCourseList() {
		List<String> course_list_text = new ArrayList<String>();

		for (int i = 0; i < course_list.size(); i++) {
			course_list_text.add(course_list.get(i).getText());
		}

		return course_list_text;
	}

	// This function clicks on delete button of copy menu
	public void clickOnCancelButton() throws InterruptedException {
		try {
			wait.until(ExpectedConditions.visibilityOf(cancel_button));
			String id = "CancelButton";
			((JavascriptExecutor) driver).executeScript("document.getElementById(\"" + id + "\").click();");
			System.out.println("Clicked on cancel button.");
			ATUReports.add(time +" Clicked on cancel button.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} catch (Exception e) {
			System.out.println("Fail click on cancel button.");
			ATUReports.add(time +" Fail click on cancel button.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
		Thread.sleep(1000);
	}

	/// verify move menu title
	public void verifyMoveMenuTitle() throws InterruptedException {
		Thread.sleep(2000);
		String val = move_menu_title.getText();
		if (val.equals("Move")) {
			ATUReports.add(time +" move menu title verified ", LogAs.PASSED, null);
			System.out.println("move menu title verified ");
		} else {
			ATUReports.add(time +" move menu title not verified  ", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			System.out.println("move menu title not verified  ");
		}
		Assert.assertEquals("Move", val);

	}

	// verify move menu background color is same as recording background color
	public void verifyMenuColor(RecordingHelperPage rec) throws InterruptedException {
		Thread.sleep(2000);
	String background_rec=rec.getBackGroundColor(rec.background);
	String menu_background=getBackGroundColor(move_menu_background);
		if (rec.getBackGroundColor(rec.background).equals(getBackGroundColor(move_menu_background))) {
			ATUReports.add(time +" move menu background color is same as recording background color", LogAs.PASSED, null);
			System.out.println("move menu background color is same as recording background color");
			Assert.assertTrue(true);
		} else {
			ATUReports.add(time +" move menu background color is not  same as recording background color", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			System.out.println("move menu background color is  not same as recording background color");

			Assert.assertTrue(false);
		}

	}

	// "verify info text"
	public void verifyInfoText() throws InterruptedException {
		String infotext = "Choose a course that you would like to move your selected recording(s) to";
		String info = info_text.getText();
		if (infotext.equals(info)) {
			ATUReports.add(time +"  info text verified", LogAs.PASSED, null);
			Assert.assertTrue(true);
			System.out.println(" info text verified");
		} else {
			ATUReports.add(time +"  info text not unverified", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			System.out.println(" info text not verified");
			Assert.assertTrue(false);
		}

	}

	// verify courses in courses page match the one in copy menu
	public void verifyCoursesInMoveMenu(CoursesHelperPage course) throws InterruptedException {
		HashSet<String> course_copy_map = new HashSet<String>();////
		move_course_list = getStringFromElement(course_list);
		if (move_course_list.length != course.courses.length - 1) {
			Assert.assertTrue(false);
			ATUReports.add(time +" different numbers of  courses in move menu and courses page ", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		} else {
			for (String e : course.courses) {
				course_copy_map.add(e);
			}
			for (String s : move_course_list) {
				if (!course_copy_map.contains(s)) {
					Assert.assertTrue(false);
					ATUReports.add(time +" courses in move menu and courses page are not matched ", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
					System.out.println("courses in move menu and courses page are not matched ");
				}
			}
			Assert.assertTrue(true);
			ATUReports.add(time +" courses in both courses page and move menu are matched", LogAs.PASSED, null);
			System.out.println("courses in both courses page and move menu are matched");
		}

	}

	// verify search course box text
	public void verifySearchCourseBoxText() {
		try {
			Assert.assertEquals(search_box.getAttribute("placeholder"), "Search course by title ...");
			ATUReports.add(time +" search box text is verified", LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
			System.out.println("search box text is verified");
		} catch (Exception e) {
			ATUReports.add(time +" search box text is not verified", LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
			System.out.println("search box text is not verified");
		}

	}

	///verify element location such as buttons,search courses
 public void verifyMoveMenuElementsLocation()
 {
	    Point searchbox = search_box.getLocation();
		Point searchboxbutton = search_button.getLocation();
		Point movebutton = move_button.getLocation();
		Point cancelbutton = cancel_button.getLocation();
		Point copy_menu_course=course_list_select.getLocation();
		Point info=info_text.getLocation();
		Point title=move_menu_title.getLocation();

		if((info.getY() > title.getY()) &&(copy_menu_course.getY() > info.getY()) &&(searchbox.getX() < searchboxbutton.getX()) &&(searchbox.getY() > copy_menu_course.getY()) &&(searchboxbutton.getY() > copy_menu_course.getY()) &&(cancelbutton.getX() < movebutton.getX()) && (cancelbutton.getY() == movebutton.getY())&&(movebutton.getX()<searchboxbutton.getX())&&(searchbox.getY() < movebutton.getY()))
	 {
		 System.out.println("elements location are verified in move menu");
			ATUReports.add(time +" elements location are verified in move menu", LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
	 }	 else {
		 System.out.println("elements location are not verified in move menu");
			ATUReports.add(time +" elements location are not verified in move menu", LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
	}
 }

	/// verify source course is not in move window course list
	public void verifySourceCourseNotInMoveMenuCourseList(String source_course) throws InterruptedException {

		move_course_list = getStringFromElement(course_list);
		HashSet<String> move_list = new HashSet<String>();
		for (String s : move_course_list) {
			move_list.add(s);
		}

		if (!move_list.contains(source_course)) {
			Assert.assertTrue(true);
			ATUReports.add(time +" courses in move menu dont contain source course", LogAs.PASSED, null);
			System.out.println("courses in move menu dont contain source course");
		}
		else{
		Assert.assertTrue(false);
		ATUReports.add(time +" courses in move menu contain source course....", LogAs.PASSED, null);
		System.out.println("courses in move menu  contain source course....");
		}
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
	// The "Move Recording(s)" button is displayed at the bottom right of the window.
	// The "Cancel" button is displayed left to the "Move Recording(s)" button.
	public void verifyThatCancelButtonDisplayedLeftToMoveRecordingsButtonInAdminMoveRecordingWindow() {
		Point cancel_button = driver.findElement(By.id("CancelButton")).getLocation();
		Point move_recrodings_button = driver.findElement(By.id("MoveButton")).getLocation();
		
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
	// A "Choose a course that you would like to move your selected recording(s) to" text below the Instructor's search field
	public void verifyChooseACourseThatMoveAndItsPlaceBelowTheInstructorSearchField() {
		WebElement choose_a_course_that_you_would_like_message = driver.findElement(By.id("InfoText"));
		
		if(choose_a_course_that_you_would_like_message.getText().equals("Choose a course that you would like to move your selected recording(s) to")) {
			System.out.println("Verified message: Choose a course that you would like to move your selected recording(s) to");
			ATUReports.add(time +" Message verfieid.", "Verified.", "Verified.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Not verified message: Choose a course that you would like to move your selected recording(s) to");
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
	// A "Search" button to the right of the course search field & above the "Cancel"/"Move recording(s)"
	public void verifyThatSearchButtonRightOfTheCourseSearchFieldAboveTheCancelMoveRecordings() {
		Point search_button_location = driver.findElement(By.id("SearchButton")).getLocation();
		Point course_search_text_location = driver.findElement(By.id("courseSearchText")).getLocation();
		Point move_recordings_button_location = driver.findElement(By.id("MoveButton")).getLocation();
		
		if ((search_button_location.y < move_recordings_button_location.y) && (course_search_text_location.x < search_button_location.x)) {
			System.out.println("Verified that search button to the right of the course search field and above the cancel / move recordings.");
			ATUReports.add(time +" Verify location of search button.", "Verified.", "Verified.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Not verified that search button to the right of the course search field and above the cancel / move recordings.");
			ATUReports.add(time +" Verify location of search button.", "Verified.", "Not verified.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}
	
	// This function verify the Move window is open
	public void verifyThatMoveWindowIsOpen() {
		boolean is_closed = isMoveMenuClosed();
		
		if(!is_closed) {
			System.out.println("Verify that move window open.");
			ATUReports.add(time +" Move window.", "Open.", "Open.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		} else {
			System.out.println("Not verfied that move window open.");
			ATUReports.add(time +" Move window.", "Open.", "Close.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		}
	}
	
	// This function verify the Move window is close
	protected void verifyThatMoveWindowIsClose() {
		boolean is_closed = isMoveMenuClosed();
		
		if(!is_closed) {
			System.out.println("Not verified that move window close.");
			ATUReports.add(time +" Move window.", "Close.", "Open.", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			Assert.assertTrue(false);
		} else {
			System.out.println("Verfied that move window close.");
			ATUReports.add(time +" Move window.", "Close.", "Close.", LogAs.PASSED, null);
			Assert.assertTrue(true);
		}
	}
	
   public void setInstructorName(String instructorName){
	   try{
		   
		   
	   }catch(Exception e){
		   ATUReports.add(time +" Failed to Set Instructor name", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
	   }
	   
	   
	   
	   
   }
}
