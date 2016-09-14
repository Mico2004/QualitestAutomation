package com.automation.main;


import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.ListModel;

import org.eclipse.jetty.util.ArrayUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
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
public class  TCTCTC {

	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}
	
	public DeleteMenu delete_menu;
	public EditRecordinPropertiesWindow edit_recording_properties_window;
	public PlayerPage player_page;
	public AdminDashboardViewCourseList admin_dashboard_view_course_list;
	public AdminDashboardPage admin_dash_board_page;
	public CourseSettingsPage course_settings_page;
	public AddAdditionalContentLinkWindow add_additional_content_link_window;
	public EditRecording edit_recording;
	public BottomFooter bottom_footer;
	public SearchPage search_page;
	public TopBarHelper top_bar_helper;
	public LoginHelperPage tegrity;
	public CoursesHelperPage course;
	public RecordingHelperPage record;
	public ConfirmationMenu confirm_menu;
	WebDriver driver;
	WebDriverWait wait;
	public static WebDriver thread_driver;
	CopyMenu copy;
	String current_course;
	String targetCourse;
	String clickedRecording;
    DesiredCapabilities capability;
	@BeforeClass
	public void setup() {

		
//		System.setProperty("webdriver.ie.driver", "src/test/resources/IEDriverServer.exe");
//			capability=DesiredCapabilities.internetExplorer();
//			capability.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING,true);
//			
//		driver=new InternetExplorerDriver(capability);
		driver = DriverSelector.getDriver(DriverSelector.getBrowserTypeByProperty());
		ATUReports.add("selected browser type", LogAs.PASSED, new CaptureScreen( ScreenshotOf.DESKTOP));

//		
		//ATUReports.setWebDriver(driver);
		//ATUReports.add("set driver", true);
		tegrity = PageFactory.initElements(driver, LoginHelperPage.class);

		record = PageFactory.initElements(driver, RecordingHelperPage.class);
		copy = PageFactory.initElements(driver, CopyMenu.class);
		course =PageFactory.initElements(driver, CoursesHelperPage.class);
		
		confirm_menu = PageFactory.initElements(driver, ConfirmationMenu.class);
		
		top_bar_helper = PageFactory.initElements(driver,TopBarHelper.class);
		search_page = PageFactory.initElements(driver, SearchPage.class);
		
		bottom_footer = PageFactory.initElements(driver, BottomFooter.class);
		
		edit_recording = PageFactory.initElements(driver, EditRecording.class);
		
		add_additional_content_link_window = PageFactory.initElements(driver, AddAdditionalContentLinkWindow.class);
		course_settings_page = PageFactory.initElements(driver, CourseSettingsPage.class);
		admin_dash_board_page = PageFactory.initElements(driver, AdminDashboardPage.class);
		admin_dashboard_view_course_list = PageFactory.initElements(driver, AdminDashboardViewCourseList.class);
		player_page = PageFactory.initElements(driver, PlayerPage.class);
		edit_recording_properties_window = PageFactory.initElements(driver, EditRecordinPropertiesWindow.class);
		delete_menu = PageFactory.initElements(driver, DeleteMenu.class);
		
		
		
	}
	
//	
//	@AfterClass
//	public void closeBroswer() {
//		driver.quit();
//	}


	// @Parameters({"web","title"}) in the future
	@Test
	public void loadPage() throws InterruptedException {
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);

	}
	
	// description = "get courses list"
	public void initializeCourseObject() throws InterruptedException {

		course = PageFactory.initElements(driver, CoursesHelperPage.class);
		course.courses = course.getStringFromElement(course.course_list);
	}

	
	@Test(dependsOnMethods = "loadPage", description = "Login course page")
	public void loginCourses() throws Exception
	{
		
		// 1. Login as INSTRUCTOR.
		tegrity.loginCourses("User1");// log in courses page
		initializeCourseObject();		
		
		Thread.sleep(1000);
		
		// 2. select course
		String targetCourse = course.selectCourseThatStartingWith("Ab");
		System.out.println("choosing course that start with AB");
		
		Thread.sleep(1000);
		
		// 3. select record
		record.SelectOneCheckBoxOrVerifyAlreadySelected(record.checkbox);
		System.out.println("choosing record");
		
		Thread.sleep(1000);
		
		// 4. move to copy menu
		record.clickOnRecordingTaskThenCopy();
		System.out.println("moving to copy menu");
		
		Thread.sleep(1000);
		
		//5. verify that the search bar is empty
		copy.verifySearchCourseBoxText();
		System.out.println("verify that the search bar is empty");
		
		Thread.sleep(1000);
		
		//6. copy the list of the courses in the copy_menu
		List <String> nameOfCourses = copy.getCourseList();
		
		
		Thread.sleep(1000);
		
		//7. click on the search bar
		copy.clickOnSearchButton();
		System.out.println("click on the search bar");
		
		
		Thread.sleep(1000);
		
		//8. verify that the list of courses in the copy_menu wasn't change 
		List <String> nameOfCoursesAfterClickingTheSearchButton = copy.getCourseList();
		
		int sizeBefore =nameOfCourses.size();
		int sizeAfter = nameOfCoursesAfterClickingTheSearchButton.size();
		nameOfCoursesAfterClickingTheSearchButton.removeAll(nameOfCourses);
		if((!nameOfCoursesAfterClickingTheSearchButton.isEmpty()) || (sizeAfter != sizeBefore)) {
			System.out.println("Error: the size of the list after clicking on the search button is invalid ");
			return;
			
		}
		
		Thread.sleep(1000);
		
		//9. wrote invalid course in the search bar and click on search button 
		 copy.sendKeysToSearchInputBox("Invalid Course");
		 copy.clickOnSearchButton();
		 nameOfCoursesAfterClickingTheSearchButton = copy.getCourseList();
		 sizeAfter = nameOfCoursesAfterClickingTheSearchButton.size();
		 if(sizeAfter != 0 ){
			 System.out.println("Error: the size of the list after clicking on the search button is invalid");
			return;
		 }
		 else {
			 
			 System.out.println("the size of the list after clicking on the search button is valid");
		 }
		
		 Thread.sleep(1000);
		 
		 //10. getting back to the original course List
		 copy.deleteValueInSearchInputBox();
		 copy.clickOnSearchButton();
		 
		 Thread.sleep(1000);
		 
		 //11. wrote non-premission course  name and verify that the list is empty
		 String course_name_not_have_premissions = "BankValidRecordings";
		 copy.sendKeysToSearchInputBox(course_name_not_have_premissions);
		 copy.isTextDisplayedInSearchBox(course_name_not_have_premissions);
		 copy.clickOnSearchButton();
		 nameOfCoursesAfterClickingTheSearchButton = copy.getCourseList();
		 sizeAfter = nameOfCoursesAfterClickingTheSearchButton.size();
		 if(sizeAfter != 0 ){
			 System.out.println("Error: the size of the list after clicking on the search button is invalid");
			return;
		 } else {
				 
				 System.out.println("the size of the list after clicking on the search button is valid");
			 }
		
		 Thread.sleep(1000);
		 
		 //12. getting back to the original course List
		 copy.deleteValueInSearchInputBox();
		 copy.clickOnSearchButton();
		 
		 Thread.sleep(1000);
		 
		 //13. copy the first course name that wrote in the list 
		 String otherCourse  =copy.selectCourseFromCourseListOtherThenCurrentCourse(targetCourse);
		 
		 Thread.sleep(1000);
		 
		 //14. check that the selected course is the only one that appread after the searching
		 copy.sendKeysToSearchInputBox(otherCourse);
		 copy.isTextDisplayedInSearchBox(otherCourse);
		 copy.clickOnSearchButton();
		 
		 nameOfCoursesAfterClickingTheSearchButton = copy.getCourseList();
		 sizeAfter = nameOfCoursesAfterClickingTheSearchButton.size();
		 
		 if(sizeAfter != 1 || !(nameOfCoursesAfterClickingTheSearchButton.contains(otherCourse)) ){
			 System.out.println("Error: the size of the list after clicking on the search button is invalid");
			return;
		 } else {
				 
				 System.out.println("the size of the list after clicking on the search button is valid");
			 }
		 
		 Thread.sleep(1000);
		 
		//15. getting back to the original course List
		 copy.deleteValueInSearchInputBox();
		 copy.clickOnSearchButton();
		 
		 Thread.sleep(1000);
		 
		 //16. getting the list of that pattern that apperad on some courses 
		 nameOfCourses = copy.getCourseList();
		 String course_name_that_part_of_serveral_courses = "Name";
		 List <String> nameOfCoursesThatContainThePattern = new ArrayList<String>();
		 
			for(String course_name: nameOfCourses) {
				if (course_name.contains(course_name_that_part_of_serveral_courses)) {
					nameOfCoursesThatContainThePattern.add(course_name);
				}
			}
			
			Thread.sleep(1000);	
			
		 //17. search the courses that have the pattern in the search and compare with the second list
			copy.sendKeysToSearchInputBox(course_name_that_part_of_serveral_courses);
			copy.isTextDisplayedInSearchBox(course_name_that_part_of_serveral_courses);
			
			Thread.sleep(1000);
			
			
			copy.clickOnSearchButton();
			nameOfCoursesAfterClickingTheSearchButton = copy.getCourseList();
			sizeBefore = nameOfCoursesThatContainThePattern.size();
			sizeAfter = nameOfCoursesAfterClickingTheSearchButton.size();	
			nameOfCoursesAfterClickingTheSearchButton.removeAll(nameOfCoursesThatContainThePattern);
			
			if((!nameOfCoursesAfterClickingTheSearchButton.isEmpty()) || (sizeAfter != sizeBefore)) {
				 System.out.println("Error: the size of the list after clicking on the search button is invalid");
				return;
			 } else {
					 
					 System.out.println("the size of the list after clicking on the search button is valid");
			 }
			 
	}
}
