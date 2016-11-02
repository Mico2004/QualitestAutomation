package com.automation.main.admin_course_view_recording_tasks;



import java.util.List;
import java.text.DateFormat;
import java.util.Date;


import org.omg.Messaging.SyncScopeHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.automation.main.page_helpers.AdminDashboardPage;
import com.automation.main.page_helpers.AdminDashboardViewCourseList;
import com.automation.main.page_helpers.ConfirmationMenu;
import com.automation.main.page_helpers.CopyMenu;
import com.automation.main.page_helpers.CoursesHelperPage;
import com.automation.main.page_helpers.DeleteMenu;
import com.automation.main.page_helpers.LoginHelperPage;
import com.automation.main.page_helpers.MoveWindow;
import com.automation.main.page_helpers.RecordingHelperPage;
import com.automation.main.utilities.DriverSelector;

import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import atu.testng.reports.utils.Utils;
import junitx.util.PropertyManager;

@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC24925ValidateMoveRecordingDropdownAndSearchFunctionalities {

	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}

	
	public ConfirmationMenu confirmation_menu;
	public LoginHelperPage tegrity;
	public CoursesHelperPage course;
	public RecordingHelperPage record;
	public DeleteMenu delete_menu;
	public MoveWindow move_window;
	public AdminDashboardPage admin_dashboard_page;
	public AdminDashboardViewCourseList admin_dashboard_view_course_list;
	WebDriver driver;
	WebDriverWait wait;
	public static WebDriver thread_driver;
	CopyMenu copy;
	String currentCourse;
	String targetCourse;
	String clickedRecording;
    DesiredCapabilities capability;
	@BeforeClass
	public void setup() {


		driver = DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());
		
		//ATUReports.setWebDriver(driver);
		//ATUReports.add("set driver", true);
		tegrity = PageFactory.initElements(driver, LoginHelperPage.class);

		record = PageFactory.initElements(driver, RecordingHelperPage.class);
		copy = PageFactory.initElements(driver, CopyMenu.class);
		delete_menu = PageFactory.initElements(driver, DeleteMenu.class);
		admin_dashboard_page = PageFactory.initElements(driver, AdminDashboardPage.class);
		admin_dashboard_view_course_list = PageFactory.initElements(driver, AdminDashboardViewCourseList.class);
		move_window = PageFactory.initElements(driver, MoveWindow.class);
		confirmation_menu = PageFactory.initElements(driver, ConfirmationMenu.class);
			
		wait = new WebDriverWait(driver, 30);
		
		 Date curDate = new Date();
		 String DateToStr = DateFormat.getInstance().format(curDate);
		 System.out.println("Starting the test: TC24925ValidateMoveRecordingDropdownAndSearchFunctionalities at " + DateToStr);
		 ATUReports.add("Message window.", "Starting the test: TC24925ValidateMoveRecordingDropdownAndSearchFunctionalities at " + DateToStr,
		 "Starting the test: TC24925ValidateMoveRecordingDropdownAndSearchFunctionalities at " + DateToStr, LogAs.PASSED, null);
	}
	
	@AfterClass
	public void quitBrowser() {
		driver.quit();
	}


	
	// description = "get courses list"
	public void initializeCourseObject() throws InterruptedException {

		course = PageFactory.initElements(driver, CoursesHelperPage.class);
		course.courses = course.getCoursesListFromElement(course.course_list);
	}

	
	@Test(description = "TC 24925 Validate Move Recording Dropdown And Search Functionalities")
	public void test24925() throws InterruptedException//
	{
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);

		// 1. Login as User1.
		tegrity.loginCourses("User1");// log in courses page
		initializeCourseObject();
		
		// 2. Get full name of Ab course.
		String source_course_name = course.selectCourseThatStartingWith("Ab");
		String url =  course.getCurrentUrlCoursePage(); 
		System.out.println("Source course name for this test is: " + source_course_name);
		ATUReports.add("Source course name for this test is: "+ source_course_name, LogAs.PASSED, null);
		
		// 2.1. Get full name of abc course.
		record.returnToCourseListPage();
		String target_course_name = course.selectCourseThatStartingWith("abc");
		System.out.println("Target course name for this test is: " + target_course_name);
		ATUReports.add("Target course name for this test is: "+ target_course_name, LogAs.PASSED, null);
		
		// 3. Logout.
		record.signOut();
		
		
		// Repeat the TC as an HD admin
		for(int login_as=0; login_as<2; login_as++) {
			
			// 4. Login as Admin.
			if (login_as==0) {
				tegrity.loginAdmin("Admin");
				Thread.sleep(5000);
			} else {
				tegrity.loginAdmin("HelpdeskAdmin");
				Thread.sleep(5000);
			}
			
			// 5. Click on "view course list" under "courses" section.
			admin_dashboard_page.clickOnTargetSubmenuCourses("View Course List");
			
			// 6. move to the course through url
			Thread.sleep(5000);
			admin_dashboard_view_course_list.moveToCoursesThroughGet(url);	
			Thread.sleep(1000);
			
			// Repeat TC for "Student recording" and "Tests" tabs
			for(int selected_tab=0; selected_tab<3; selected_tab++) {
				
				if(selected_tab == 1) {
					record.clickOnStudentRecordingsTab();
				} else if (selected_tab==2) {
					record.clickOnTestsTab();
				}
				Thread.sleep(1000);
				
				wait.until(ExpectedConditions.visibilityOf(record.getCheckbox()));
				
				// 8. Click on a checkbox of one recording.
				record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
				
				// 9. Hover over "Recording tasks" menu.
				// 10. Click on the menu item "Move".
				record.clickOnRecordingTaskThenMove();
				Thread.sleep(2000);
				
				// 11. Click on the text field, and write an instuctor name which does not exist (like "aaaaaadfasdasdaa").
				driver.findElement(By.id("members_value")).sendKeys("aaaaaadssdfafaasa");
				Thread.sleep(3000);
				
				// 12. The dropdown is displaying an informative text "No Results"
				String dropdown_result = driver.findElements(By.cssSelector(".angucomplete-searching.ng-binding")).get(1).getText();
				
				if(dropdown_result.equals("No results found")) {
					System.out.println("Dropdown list opened with the text: No results found");
					ATUReports.add("Dropdown list opened with the text.", "Text: No results found", "Text: No results found", LogAs.PASSED, null);
					Assert.assertTrue(true);
				} else {
					System.out.println("Dropdown list opened with the text: " + dropdown_result);
					ATUReports.add("Dropdown list opened with the text.", "Text: No results found", "Text: " + dropdown_result, LogAs.FAILED, null);
					Assert.assertTrue(false);
				}
				
				// 13. Click on the button "List Courses".
				driver.findElement(By.id("SearchButton")).click();
				Thread.sleep(1000);
				
				// 14. An alert is displaying the informative text: "Please select an instructor"
				confirmation_menu.clickOnOkButtonAfterErrorNoInstructorSelected();
				Thread.sleep(2000);
				
				// 15. Click on the text field, and write the name of the (existing) student.
				driver.findElement(By.id("members_value")).clear();
				driver.findElement(By.id("members_value")).sendKeys(PropertyManager.getProperty("User4"));
				Thread.sleep(3000);
				
				// 16. The dropdown is displaying an informative text "No Results"
				dropdown_result = driver.findElements(By.cssSelector(".angucomplete-searching.ng-binding")).get(1).getText();
				
				if(dropdown_result.equals("No results found")) {
					System.out.println("Dropdown list opened with the text: No results found");
					ATUReports.add("Dropdown list opened with the text.", "Text: No results found", "Text: No results found", LogAs.PASSED, null);
					Assert.assertTrue(true);
				} else {
					System.out.println("Dropdown list opened with the text: " + dropdown_result);
					ATUReports.add("Dropdown list opened with the text.", "Text: No results found", "Text: " + dropdown_result, LogAs.FAILED, null);
					Assert.assertTrue(false);
				}
				
				// 17. Click on the text field, and write the name of the (existing) instuctor which exists only in another university.
				driver.findElement(By.id("members_value")).clear();
				driver.findElement(By.id("members_value")).sendKeys("kosins1");
				Thread.sleep(3000);
				
				// 18. The dropdown is displaying an informative text "No Results".
				dropdown_result = driver.findElements(By.cssSelector(".angucomplete-searching.ng-binding")).get(1).getText();
				
				if(dropdown_result.equals("No results found")) {
					System.out.println("Dropdown list opened with the text: No results found");
					ATUReports.add("Dropdown list opened with the text.", "Text: No results found", "Text: No results found", LogAs.PASSED, null);
					Assert.assertTrue(true);
				} else {
					System.out.println("Dropdown list opened with the text: " + dropdown_result);
					ATUReports.add("Dropdown list opened with the text.", "Text: No results found", "Text: " + dropdown_result, LogAs.FAILED, null);
					Assert.assertTrue(false);
				}
				
				// 19. Click on the text field, and write a sub string of the name of the (existing) instructor.
				String username = PropertyManager.getProperty("User1");
				driver.findElement(By.id("members_value")).clear();
				driver.findElement(By.id("members_value")).sendKeys(PropertyManager.getProperty("User1").substring(0, PropertyManager.getProperty("User1").length()-3));
				Thread.sleep(3000);
				
				// 20. The user is displayed in the drop down list.
				dropdown_result = driver.findElement(By.cssSelector(".angucomplete-title.ng-scope.ng-binding")).getText();
				
				if(dropdown_result.equals(username)) {
					System.out.println("Dropdown list opened with the text: " + username);
					ATUReports.add("Dropdown list opened with the text.", username, username, LogAs.PASSED, null);
					Assert.assertTrue(true);
				} else {
					System.out.println("Dropdown list opened with the text: " + dropdown_result);
					ATUReports.add("Dropdown list opened with the text.", username, dropdown_result, LogAs.FAILED, null);
					Assert.assertTrue(false);
				}
				
				// 21. Click ,on the dropdown list, on the name of the instructor who belongs to this course when it is displayed on the list.
				driver.findElement(By.cssSelector(".angucomplete-title.ng-scope.ng-binding")).click();
				Thread.sleep(1000);
				
				// 22. The chosen name displays on the text field for search (near "list courses" button).
				String chosen_name = driver.findElement(By.id("members_value")).getAttribute("value");
				
				if(chosen_name.equals(username)) {
					System.out.println("Chosen name displays on the text field for search.");
					ATUReports.add("Chosen name displays on the text field for the search.", username, username, LogAs.PASSED, null);
					Assert.assertTrue(true);
				} else {
					System.out.println("Chosen name not displays on the text field for search.");
					ATUReports.add("Chosen name displays on the text field for the search.", username, chosen_name, LogAs.FAILED, null);
					Assert.assertTrue(false);
				}
				
				// 23. Click on the button "List Courses".
				driver.findElement(By.id("SearchButton")).click();
				Thread.sleep(1000);
				
				// 24. There shall be an informative text displayed below the input field: "Choose a course that you would like to move your selected recording(s) to.".
				move_window.verifyChooseACourseThatMoveAndItsPlaceBelowTheInstructorSearchField();
				
				// 25. The list of available courses is consist only from 'Active Courses' without any user 'Past Courses'.
				List<String> courses_list_of_instructor = copy.getCourseList();
				
				boolean not_found = true;
				for(int i=0; i<courses_list_of_instructor.size(); i++) {
					if(courses_list_of_instructor.get(i).contains("PastCourse")) {
						not_found=false;
						System.out.println("There is past course in the list.");
						ATUReports.add("Course list without past courses.", "True.", "False.", LogAs.FAILED, null);
						Assert.assertTrue(false);
					}
				}
				
				if (not_found) {
					System.out.println("Courses list not contains past courses.");
					ATUReports.add("Course list without past courses.", "True.", "True.", LogAs.PASSED, null);
					Assert.assertTrue(true);
				}
				
				// 26. A text field displays which contain the text: "Search course by title...".
				if(driver.findElement(By.id("courseSearchText")).getAttribute("placeholder").equals("Search course by title ...")) {
					System.out.println("A course search field status is correct.");
					ATUReports.add("A course search field status.", "Correct.", "Correct.", LogAs.PASSED, null);
					Assert.assertTrue(true);
				} else {
					System.out.println("A course search field status is not correct.");
					ATUReports.add("A course search field status.", "Correct.", "Not correct.", LogAs.FAILED, null);
					Assert.assertTrue(false);
				}
				
				// 27. The "Search" button is displayed next to the search field.
				move_window.verifyThatSearchButtonRightOfTheCourseSearchFieldAboveTheCancelMoveRecordings();
				
				// 28. Inside of the 'Search' text box, search for a past course of the instructor you chose earlier.
				copy.sendKeysToSearchInputBox("PastCourse");
				copy.clickOnSearchButton();	
				Thread.sleep(2000);
				
				// 29. The past course shouldn't be displayed inside the course list textbox.
				List<String> searched_course_list = copy.getCourseList();
				
				if(searched_course_list.size() == 0) {
					System.out.println("None course dispalyed inside the course list.");
					ATUReports.add("Course list.", "No course displayed in course list.", "No course displayed in the course list.", LogAs.PASSED, null);
					Assert.assertTrue(true);
				} else {
					System.out.println("There is course(s) displayed inside the course list.");
					ATUReports.add("Course list.", "No course displayed in course list.", "There is course(s) displayed in the course list.", LogAs.FAILED, null);
					Assert.assertTrue(false);
				}
				
				// 30. Inside of the 'Search' text box, search for an existing university course which the chosen user isn't enrolled to.
				copy.sendKeysToSearchInputBox("NoneEnroollCourse");
				copy.clickOnSearchButton();	
				Thread.sleep(2000);
				
				// 31. The course shouldn't be displayed inside the course list textbox.
				searched_course_list = copy.getCourseList();
				
				if(searched_course_list.size() == 0) {
					System.out.println("None course dispalyed inside the course list.");
					ATUReports.add("Course list.", "No course displayed in course list.", "No course displayed in the course list.", LogAs.PASSED, null);
					Assert.assertTrue(true);
				} else {
					System.out.println("There is course(s) displayed inside the course list.");
					ATUReports.add("Course list.", "No course displayed in course list.", "There is course(s) displayed in the course list.", LogAs.FAILED, null);
					Assert.assertTrue(false);
				}
				
				// 32. Inside of the 'Search' text box, search for a substring of one of the Active courses.
				copy.sendKeysToSearchInputBox(target_course_name.substring(0, target_course_name.length()-3));
				copy.clickOnSearchButton();
				Thread.sleep(2000);
				
				// 33. The course should be displayed inside the course list textbox.
				searched_course_list = copy.getCourseList();
				
				if(searched_course_list.contains(target_course_name)) {
					System.out.println("The course is displayed in course list.");
					ATUReports.add("Course list.", "The course displyed.", "The course displayed.", LogAs.PASSED, null);
					Assert.assertTrue(true);
				} else {
					System.out.println("The course is not displayed in course list.");
					ATUReports.add("Course list.", "The course displyed.", "The course not displayed.", LogAs.PASSED, null);
					Assert.assertTrue(false);
				}
				
				// 34. Inside of the 'Search' text box, search for a the whole string of one of the Active courses.
				copy.sendKeysToSearchInputBox(target_course_name);
				copy.clickOnSearchButton();
				Thread.sleep(2000);
				
				// 35. The course should be displayed inside the course list textbox.
				searched_course_list = copy.getCourseList();
				
				if(searched_course_list.contains(target_course_name)) {
					System.out.println("The course is displayed in course list.");
					ATUReports.add("Course list.", "The course displyed.", "The course displayed.", LogAs.PASSED, null);
					Assert.assertTrue(true);
				} else {
					System.out.println("The course is not displayed in course list.");
					ATUReports.add("Course list.", "The course displyed.", "The course not displayed.", LogAs.PASSED, null);
					Assert.assertTrue(false);
				}
				
				// 36. Click on cancel.
				copy.clickOnCancelButton(record);
			}
			
			// 37. Logout.
			driver.findElement(By.id("SignOutLink")).click();
			Thread.sleep(2000);
		}
		
		System.out.println("Done.");
		ATUReports.add("Message window.", "Done.", "Done.", LogAs.PASSED, null);

	}
	


}
