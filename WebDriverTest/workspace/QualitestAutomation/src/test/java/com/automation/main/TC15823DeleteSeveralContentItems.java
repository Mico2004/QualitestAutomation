package com.automation.main;


import java.awt.AWTException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Platform;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.w3c.dom.stylesheets.LinkStyle;

import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import atu.testng.reports.utils.Utils;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;
import junitx.util.PropertyManager;

@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class TC15823DeleteSeveralContentItems {

	// Set Property for ATU Reporter Configuration
	{
		System.setProperty("atu.reporter.config", "src/test/resources/atu.properties");

	}

	public TopBarHelper top_bar_helper;
	public LoginHelperPage tegrity;
	public CoursesHelperPage course;
	public RecordingHelperPage record;
	public ConfirmationMenu confirm_menu;
	public MoveWindow move_window;
	public DeleteMenu delete_menu;
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
    	
//		driver.manage().window().maximize();
		ATUReports.setWebDriver(driver);
	
		tegrity = PageFactory.initElements(driver, LoginHelperPage.class);

		record = PageFactory.initElements(driver, RecordingHelperPage.class);
		copy = PageFactory.initElements(driver, CopyMenu.class);
		
		confirm_menu = PageFactory.initElements(driver, ConfirmationMenu.class);
		
		move_window = PageFactory.initElements(driver, MoveWindow.class);
		
		delete_menu = PageFactory.initElements(driver, DeleteMenu.class);
		
		top_bar_helper = PageFactory.initElements(driver, TopBarHelper.class);
	}
	
	
	@AfterClass
	public void closeBroswer() {
		this.driver.quit();
	}


	
	// description = "get courses list"
	public void initializeCourseObject() throws InterruptedException {

		course = PageFactory.initElements(driver, CoursesHelperPage.class);
		course.courses = course.getStringFromElement(course.course_list);
	}

	@Test
	public void loginCourses() throws InterruptedException
	{
		// 1. Login as INSTRUCTOR (User1).
		tegrity.loadPage(tegrity.pageUrl, tegrity.pageTitle);
		tegrity.loginCourses("User1");// log in courses page
		initializeCourseObject();
		
		// 2. Select course (Ab).
		currentCourse = course.selectCourseThatStartingWith("Ab");
		System.out.println("Current course: " + currentCourse);
		
		// 3. Click the "Additional Content" tab.
		record.clickOnAdditionContentTab();
		Thread.sleep(1000);
		
		// 4. Select content item.
		record.selectIndexCheckBox(1);
		record.selectIndexCheckBox(2);
		String first_target_additional_content = record.getNameTargetIndexAdditionalContent(1);
		String second_target_additional_content = record.getNameTargetIndexAdditionalContent(2);
		
		// 5. Select "Content Tasks -> Delete" menu item.
		record.clickOnContentTaskThenDelete();
		
		// 6. "Delete" window is displayed.
		delete_menu.verifyDeleteWindowDisplayed();
		
		// 7. Verify that only selected item is displayed in "List of Items".
		delete_menu.verifyTargetRecordingInAdditionalContentDeleteWindowRecordingList(first_target_additional_content);
		delete_menu.verifyTargetRecordingInAdditionalContentDeleteWindowRecordingList(second_target_additional_content);
		
		// 8. Click the "Delete" button.
		delete_menu.clickOnDeleteButton();
		
		// 9. Message box is closed.
		confirm_menu.verifyConfirmWindowIsClosed();
		
		// 10. Delete window is closed.
		delete_menu.verifyDeleteWindowNotDisplayed();
		
		// 11. Verify that selected content item is deleted.
		record.verifyTargetAdditionalContentNotInAdditionalContentList(first_target_additional_content);
		record.verifyTargetAdditionalContentNotInAdditionalContentList(second_target_additional_content);
		
		
	
	
	}
}
